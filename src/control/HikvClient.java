package control;

import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.examples.win32.W32API;
import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.ptr.NativeLongByReference;

/**
 * Created by wsx on 2017-06-29.
 * 总的流程：初始化SDK->用户注册服务->开始预览->云台控制->停止预览->用户注销服务->释放SDK资源
 */
public class HikvClient {
    private HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    private PlayCtrl playControl = PlayCtrl.INSTANCE;
    private Stitcher stitcher = Stitcher.INSTANCE;

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

    private JFrame realPlayFrame;//实时预览主框体
    private Panel realPlayPanel;
    private ControlFrame controlFrame;//控制窗体
    private JFrame PlayerFrame;//播放器窗体

    private JMenuBar controljMenuBar;
    private JMenu controljMenu;
    private JMenuItem controljMenuItem;



    public HikvClient()
    {
        bRealPlay = false;
        bRecordVideo = false;
        lUserID = new NativeLong(-1);
        lPreviewHandle = new NativeLong(-1);
        m_lPort = new NativeLongByReference(new NativeLong(-1));
        //fRealDataCallBack= new FRealDataCallBack();


        realPlayFrame = new JFrame();
        controljMenuBar = new JMenuBar();
        controljMenu = new JMenu("控制");
        controljMenuItem = new JMenuItem("控制面板");
        controljMenu.add(controljMenuItem);
        controljMenuBar.add(controljMenu);
        realPlayFrame.setJMenuBar(controljMenuBar);
        realPlayPanel = new Panel();
        realPlayFrame.add(realPlayPanel);


        realPlayFrame.setSize(800,600);
        realPlayFrame.setLocationRelativeTo(null);
        realPlayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        realPlayFrame.setVisible(true);

        realPlayFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                UserExit();
            }
        });

        controljMenuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(controlFrame == null) {
                    controlFrame = new ControlFrame(lPreviewHandle, hCNetSDK, lUserID, stitcher);
                }
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
                final W32API.HWND hwnd = new W32API.HWND(Native.getComponentPointer(realPlayPanel));
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


    class FRealDataCallBack implements HCNetSDK.FRealDataCallBack_V30
    {
        //预览回调
        public void invoke(NativeLong lRealHandle, int dwDataType, ByteByReference pBuffer, int dwBufSize, Pointer pUser)
        {
            W32API.HWND hwnd = new W32API.HWND(Native.getComponentPointer(realPlayPanel));
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



    public static void test()
    {
        HikvClient hk = new HikvClient();
        hk.initSDKandUserSignup();
        hk.StartRealPlay(0);
        
    }
   
    public static void main(String args[])
    {
        test();

        //cmddemo();
        //CollectPic();
//        ControlFrame cf = new ControlFrame();
//        cf.initSDKandUserSignup();
//        cf.StartRealPlay();
    }
}
