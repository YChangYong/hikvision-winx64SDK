package control;

import com.sun.jna.NativeLong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.TimerTask;

/**
 * Created by wsx on 2017-07-01.
 */
public class ControlFrame extends JFrame {

    private JButton left_bt;
    private JButton right_bt;
    private JButton screenshot_bt;
    private JButton startrecoedvideo_bt;
    private JButton endrecoedvideo_bt;
    private JButton playback_bt;
    private JButton contiscreenshot_bt;

    private NativeLong lPreviewHandle;
    private HCNetSDK hCNetSDK;
    private NativeLong lUserID;

    private boolean bRecordVideo;


    public ControlFrame(NativeLong lPreviewHandle, HCNetSDK hCNetSDK, NativeLong lUserID) {
        this.lPreviewHandle = lPreviewHandle;
        this.hCNetSDK = hCNetSDK;
        this.lUserID = lUserID;
        bRecordVideo = false;
        initComponents();
    }

    private void initComponents() {
        left_bt = new JButton("左");
        right_bt = new JButton("右");
        screenshot_bt = new JButton("抓图");
        startrecoedvideo_bt = new JButton("开始录像");
        endrecoedvideo_bt = new JButton("结束录像");
        playback_bt = new JButton("回放");
        contiscreenshot_bt = new JButton("连续抓图");

        left_bt.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                PTZControlAll(lPreviewHandle, HCNetSDK.PAN_LEFT, 0);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                PTZControlAll(lPreviewHandle, HCNetSDK.PAN_LEFT, 1);
            }
        });

        right_bt.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                PTZControlAll(lPreviewHandle, HCNetSDK.PAN_RIGHT, 0);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                PTZControlAll(lPreviewHandle, HCNetSDK.PAN_RIGHT, 1);
            }
        });

        screenshot_bt.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                CapturePicture();
            }
        });

        startrecoedvideo_bt.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startRecodeVideo();
            }
        });

        endrecoedvideo_bt.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                endRecodeVideo();
            }
        });

        contiscreenshot_bt.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                contiCapturePicture();
            }
        });

        playback_bt.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                searchFile();
            }
        });

        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(new GridLayout(4, 2));
        add(left_bt);
        add(startrecoedvideo_bt);
        add(right_bt);
        add(endrecoedvideo_bt);
        add(screenshot_bt);
        add(playback_bt);
        add(contiscreenshot_bt);
        setVisible(true);
    }


    /**
     * 控制转的函数
     *
     * @param lRealHandle 预览句柄
     * @param iPTZCommand 操作命令
     * @param iStop       是否停止这个命令
     */
    public void PTZControlAll(NativeLong lRealHandle, int iPTZCommand, int iStop) {
        int iSpeed = 0;
        if (lRealHandle.intValue() >= 0) {
            boolean ret;
            if (iSpeed >= 1)//有速度的ptz
            {
                ret = hCNetSDK.NET_DVR_PTZControlWithSpeed(lRealHandle, iPTZCommand, iStop, iSpeed);
                if (!ret) {
                    System.out.println("云台控制失败");
                    return;
                }
            } else//速度为默认时
            {
                ret = hCNetSDK.NET_DVR_PTZControl(lRealHandle, iPTZCommand, iStop);
                if (!ret) {
                    System.out.println("云台控制失败");
                    return;
                }
            }
        }
    }

    /**
     * 抓图bmp到工程根目录下的pictures文件夹中
     */
    public void CapturePicture() {
        String sPicName = "./pictures/" + System.currentTimeMillis() + ".bmp";
        if (hCNetSDK.NET_DVR_CapturePicture(lPreviewHandle, sPicName)) {
            System.out.println("抓图:" + sPicName);
        } else {
            System.out.println("抓图失败");
            return;
        }
    }

    public void startRecodeVideo() {
        if (bRecordVideo != false)
            return;

        String videoname = "./videos/" + String.valueOf(System.currentTimeMillis()) + ".mp4";
        boolean isvideobegin = hCNetSDK.NET_DVR_SaveRealData(lUserID, videoname);
        if (isvideobegin == false) {
            System.out.println("开始录像失败");
            return;
        }
        System.out.println("开始录像");
        bRecordVideo = true;
    }

    public void endRecodeVideo() {
        if (bRecordVideo != true)
            return;

        boolean isvideoend = hCNetSDK.NET_DVR_StopSaveRealData(lUserID);
        if (isvideoend == false) {
            System.out.println("结束录像失败");
            return;
        }
        System.out.println("结束录像");
        bRecordVideo = false;
    }


    public void contiCapturePicture() {
        for (int i = 0; i < 6; i++) {
            CapturePicture();

            hCNetSDK.NET_DVR_PTZControlWithSpeed(lPreviewHandle, HCNetSDK.PAN_LEFT, 0, 3);

            java.util.Timer timer = new java.util.Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    PTZControlAll(lPreviewHandle, HCNetSDK.PAN_LEFT, 1);
                }
            }, 1000);

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("连续抓图完毕");
    }

    public String searchFile()
    {
        JFileChooser jfc=new JFileChooser(new File("./videos/"));

        jfc.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) { //设定可用的文件的后缀名
                if(f.getName().endsWith(".mp4")||f.isDirectory()){
                    return true;
                }
                return false;
            }
            public String getDescription() {
                return "*.mp4";
            }
        });

        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY );
        jfc.showDialog(new JLabel(), "选择");

        File file=jfc.getSelectedFile();
        if(file.isDirectory()){
            System.out.println("文件夹:"+file.getAbsolutePath());
        }else if(file.isFile()){
            System.out.println("文件:"+file.getAbsolutePath());
        }
        System.out.println(jfc.getSelectedFile().getName());
        return null;
    }

}

