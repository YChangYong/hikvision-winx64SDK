package control;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.examples.win32.W32API;
import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.ptr.NativeLongByReference;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.Timer;

/**
 * Created by wsx on 2017-06-29.
 * 总的流程：初始化SDK->用户注册服务->开始预览->云台控制->停止预览->用户注销服务->释放SDK资源
 */
public class ControlFrame extends JFrame{
    private HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    private PlayCtrl playControl = PlayCtrl.INSTANCE;

    private HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;//设备信息
    private HCNetSDK.NET_DVR_CLIENTINFO m_strClientInfo;//用户参数

    private boolean bRealPlay;//是否在预览.
    private boolean bRecordVideo;
    private NativeLong lUserID;//用户句柄
    private static NativeLong lPreviewHandle;//预览句柄
    private NativeLongByReference m_lPort;//回调预览时播放库端口指针

    FRealDataCallBack fRealDataCallBack;//预览回调函数实现

    private final String m_sDeviceIP = "192.168.134.88";
    private final String username = "admin";
    private final String password  = "wsl87654321.";
    private final short port = 8000;


    public Component getJFrameObject() {
        return this;
    }



    public ControlFrame()
    {
        bRealPlay = false;
        bRecordVideo = false;
        lUserID = new NativeLong(-1);
        lPreviewHandle = new NativeLong(-1);
        m_lPort = new NativeLongByReference(new NativeLong(-1));
        fRealDataCallBack= new FRealDataCallBack();

        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                UserExit();
            }
        });
    }

    /**
     * 初始化SDK和用户注册
     */
    public void initSDKandUserSignup()
    {
        boolean initSuc = hCNetSDK.NET_DVR_Init();
        if (initSuc != true)
        {
            System.out.println("初始化失败");
        }

        if(bRealPlay)
        {
            System.out.println("注册新用户请先停止当前预览!");
            return;
        }
        if(lUserID.longValue() > -1)
        {
            hCNetSDK.NET_DVR_Logout_V30(lUserID);
            lUserID = new NativeLong(-1);
        }
        m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
        lUserID = hCNetSDK.NET_DVR_Login_V30(m_sDeviceIP, port, username, password, m_strDeviceInfo);
        long userID = lUserID.longValue();
        if (userID == -1)
        {
            System.out.println("注册失败");
            return;
        }
    }

    /**
     * 开始预览
     * @param isCallBack   判断是否回调预览,0,不回调 1 回调
     */
    public void StartRealPlay(int isCallBack)
    {
        if (lUserID.intValue() == -1)
        {
            System.out.println("请先注册");
            return;
        }

        if (bRealPlay == false) {
            int iChannelNum = m_strDeviceInfo.byStartChan;
            if (iChannelNum == -1) {
                System.out.println("预览通道号错误");
                return;
            }

            m_strClientInfo = new HCNetSDK.NET_DVR_CLIENTINFO();
            m_strClientInfo.lChannel = new NativeLong(iChannelNum);

            if (isCallBack == 0) {
                final W32API.HWND hwnd = new W32API.HWND(Native.getComponentPointer(this));
                m_strClientInfo.hPlayWnd = hwnd;
                lPreviewHandle = hCNetSDK.NET_DVR_RealPlay_V30(lUserID,
                        m_strClientInfo, null, null, true);
            } else {
                //回调预览
                m_strClientInfo.hPlayWnd = null;
                lPreviewHandle = hCNetSDK.NET_DVR_RealPlay_V30(lUserID,
                        m_strClientInfo, fRealDataCallBack, null, true);
            }
            long previewSucValue = lPreviewHandle.longValue();
            if (previewSucValue == -1) {
                System.out.println("预览失败");
                return;
            }
            bRealPlay = true;
        }
        else
        {
            System.out.println("使bRealPlay = false");
            return;
        }
    }

    /**
     * 用户离开操作：停止预览->用户注销登录->释放SDK资源
     */
    public void UserExit()
    {
        if (lPreviewHandle.longValue() > -1)
        {
            hCNetSDK.NET_DVR_StopRealPlay(lPreviewHandle);
        }

        if(lUserID.intValue() > -1)
        {
            hCNetSDK.NET_DVR_Logout_V30(lUserID);
        }

        hCNetSDK.NET_DVR_Cleanup();
    }


    /**
     * 控制转的函数
     * @param lRealHandle   预览句柄
     * @param iPTZCommand   操作命令
     * @param iStop          是否停止这个命令
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
     *
     * @param lRealHandle   预览句柄
     * @param iPTZCommand   控制命令
     * @param time_ms       命令持续时间（毫秒）
     */
    public void Rotate(NativeLong lRealHandle, int iPTZCommand, long time_ms)
    {
        PTZControlAll(lRealHandle, HCNetSDK.PAN_LEFT, 0);

        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                PTZControlAll(lRealHandle, iPTZCommand, 1);
            }
        }, time_ms);
    }


    class FRealDataCallBack implements HCNetSDK.FRealDataCallBack_V30
    {
        //预览回调
        public void invoke(NativeLong lRealHandle, int dwDataType, ByteByReference pBuffer, int dwBufSize, Pointer pUser)
        {
            W32API.HWND hwnd = new W32API.HWND(Native.getComponentPointer(getJFrameObject()));
            switch (dwDataType)
            {
                case HCNetSDK.NET_DVR_SYSHEAD: //系统头

                    if (!playControl.PlayM4_GetPort(m_lPort)) //获取播放库未使用的通道号
                    {
                        break;
                    }

                    if (dwBufSize > 0)
                    {
                        if (!playControl.PlayM4_SetStreamOpenMode(m_lPort.getValue(), PlayCtrl.STREAME_REALTIME))  //设置实时流播放模式
                        {
                            break;
                        }

                        if (!playControl.PlayM4_OpenStream(m_lPort.getValue(), pBuffer, dwBufSize, 1024 * 1024)) //打开流接口
                        {
                            break;
                        }

                        if (!playControl.PlayM4_Play(m_lPort.getValue(), hwnd)) //播放开始
                        {
                            break;
                        }
                    }
                case HCNetSDK.NET_DVR_STREAMDATA:   //码流数据
                    if ((dwBufSize > 0) && (m_lPort.getValue().intValue() != -1))
                    {
                        if (!playControl.PlayM4_InputData(m_lPort.getValue(), pBuffer, dwBufSize))  //输入流数据
                        {
                            break;
                        }
                    }
            }
        }
    }

    /**
     * 抓图bmp到工程根目录下的pictures文件夹中
     */
    public void CapturePicture()
    {
        String sPicName = "./pictures/"+ System.currentTimeMillis() + ".bmp";
        if (hCNetSDK.NET_DVR_CapturePicture(lPreviewHandle, sPicName))
        {
            System.out.println("抓图:" + sPicName);
        }
        else
        {
            System.out.println("抓图失败");
            return;
        }

    }

    public static void cmddemo()
    {
        ControlFrame cf = new ControlFrame();
        cf.initSDKandUserSignup();
        cf.StartRealPlay(0);
        while(true)
        {
            System.out.println("1.控制\t2.抓图");
            Scanner s = new Scanner(System.in);
            int cmd = s.nextInt();
            switch(cmd) {
                case 1:
                    System.out.println("(23.左\t24.右)\t\t加个空格后面跟时间，单位毫秒");
                    int direction = s.nextInt();
                    long time = s.nextLong();
                    if (direction != 23 && direction != 24) {
                        System.out.println("指令错误");
                        continue;
                    }
                    if (time > 10000 || time < 0) {
                        System.out.println("指令错误");
                        continue;
                    }
                    cf.Rotate(lPreviewHandle, direction, time);
                    break;
                case 2:
                    cf.CapturePicture();
                    break;
                default:
                    System.out.println("命令错误");
                    break;
            }
        }
    }

    public static void CollectPic() {
        ControlFrame cf = new ControlFrame();
        cf.initSDKandUserSignup();
        cf.StartRealPlay(0);
        for (int i = 0; i < 5; i++) {
            cf.CapturePicture();
            cf.Rotate(lPreviewHandle, HCNetSDK.PAN_LEFT, 1000);
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void RecordVideo()
    {
        if(bRecordVideo == false) {
            String videoname = "./videos/" + String.valueOf(System.currentTimeMillis()) + ".mp4";

            boolean isvideobegin = hCNetSDK.NET_DVR_SaveRealData(lUserID, videoname);
            if(isvideobegin == false)
            {
                System.out.println("开始录像失败");
                return;
            }
            System.out.println("开始录像");
            bRecordVideo = true;
        } else {
            boolean isvideoend = hCNetSDK.NET_DVR_StopSaveRealData(lUserID);
            if(isvideoend == false)
            {
                System.out.println("结束录像失败");
                return;
            }
            System.out.println("结束录像");
            bRecordVideo = false;
        }
    }

    public static void main(String args[])
    {
        ControlFrame cf = new ControlFrame();
        cf.initSDKandUserSignup();
        cf.StartRealPlay(0);
        cf.RecordVideo();
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                cf.RecordVideo();
            }
        }, 10000);
        //cmddemo();
        //CollectPic();
//        ControlFrame cf = new ControlFrame();
//        cf.initSDKandUserSignup();
//        cf.StartRealPlay();
    }
}
