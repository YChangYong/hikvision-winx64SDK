package control;

import ClientDemo.JFramePTZControl;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.examples.win32.W32API;
import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.ptr.NativeLongByReference;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.Timer;

/**
 * Created by wsx on 2017-06-29.
 */
public class ControlFrame extends JFrame{
    private HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;

    private PlayCtrl playControl = PlayCtrl.INSTANCE;
    private HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;//设备信息
    private HCNetSDK.NET_DVR_CLIENTINFO m_strClientInfo;//用户参数

    private boolean bRealPlay;//是否在预览.
    private NativeLong lUserID;//用户句柄
    private static NativeLong lPreviewHandle;//预览句柄
    private NativeLongByReference m_lPort;//回调预览时播放库端口指针

    private final String m_sDeviceIP = "192.168.134.88";
    private final String username = "admin";
    private final String password  = "wsl87654321.";
    private final short port = 8000;





    public ControlFrame()
    {
        bRealPlay = false;
        lUserID = new NativeLong(-1);
        lPreviewHandle = new NativeLong(-1);
        m_lPort = new NativeLongByReference(new NativeLong(-1));

        this.setLocationRelativeTo(null);
        this.setSize(800,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                UserExit();
            }
        });
    }

    public void StartRealPlay()
    {
        if (lUserID.intValue() == -1)
        {
            System.out.println("请先注册");
            return;
        }

        if (bRealPlay == false) {
            int iChannelNum = 1;
            if (iChannelNum == -1) {
                System.out.println("请选择要预览的通道");
                return;
            }

            m_strClientInfo = new HCNetSDK.NET_DVR_CLIENTINFO();
            m_strClientInfo.lChannel = new NativeLong(iChannelNum);

            int isCallBack = 0;//在此判断是否回调预览,0,不回调 1 回调
            if (isCallBack == 0) {
                final W32API.HWND hwnd = new W32API.HWND(Native.getComponentPointer(this));
                m_strClientInfo.hPlayWnd = hwnd;
                lPreviewHandle = hCNetSDK.NET_DVR_RealPlay_V30(lUserID,
                        m_strClientInfo, null, null, true);
            } else {
                //回调预览
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
            hCNetSDK.NET_DVR_StopRealPlay(lPreviewHandle);
            bRealPlay = false;
            if(m_lPort.getValue().intValue() != -1)
            {
                playControl.PlayM4_Stop(m_lPort.getValue());
                m_lPort.setValue(new NativeLong(-1));
            }
        }

    }
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
            lUserID = new NativeLong(-1);//---------------------------------------------------
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
     * @param lRealHandle
     * @param iPTZCommand
     * @param iStop
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


    public void Rotate(NativeLong lRealHandle, int iPTZCommand, long time_ms)
    {
        PTZControlAll(lRealHandle, HCNetSDK.PAN_LEFT, 0);

        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                PTZControlAll(lRealHandle, HCNetSDK.PAN_LEFT, 1);
            }
        }, time_ms);
    }

    public static void main(String args[])
    {
        ControlFrame cf = new ControlFrame();
        cf.initSDKandUserSignup();
        cf.StartRealPlay();
        Scanner s = new Scanner(System.in);
        while(true) {
            long time = s.nextLong();
            cf.Rotate(lPreviewHandle, HCNetSDK.PAN_LEFT, time);
        }
    }
}
