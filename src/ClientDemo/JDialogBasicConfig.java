/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JDialogBasicConfig.java
 *
 * Created on 2009-10-28, 11:50:54
 */
/**
 *
 * @author Administrator
 */

package ClientDemo;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import javax.swing.JOptionPane;

/*****************************************************************************
 *类 ：JDialogBasicConfig
 *类描述 ：设备基本信息显示
 ****************************************************************************/
public class JDialogBasicConfig extends javax.swing.JDialog
{

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    private HCNetSDK.NET_DVR_DEVICECFG m_strDeviceCfg;//设备信息
    private NativeLong m_lUserID;//用户ID

    /*************************************************
    函数:      构造函数
    函数描述:	Creates new form JDialogBasicConfig
     *************************************************/
    public JDialogBasicConfig(java.awt.Frame parent, boolean modal, NativeLong lUserID)
    {
        super(parent, modal);

        m_lUserID = lUserID;
        initComponents();
        initDialog();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldDeviceName = new javax.swing.JTextField();
        jTextFieldChannelNumber = new javax.swing.JTextField();
        jTextFieldAlarmInNumber = new javax.swing.JTextField();
        jComboBoxCycleRecord = new javax.swing.JComboBox();
        jTextFieldSerial = new javax.swing.JTextField();
        jComboBoxDeviceType = new javax.swing.JComboBox();
        jTextFieldHDNumber = new javax.swing.JTextField();
        jTextFieldAlarmOutNumber = new javax.swing.JTextField();
        jTextFieldTelCtlVersion = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldSoftwareVersion = new javax.swing.JTextField();
        jTextFieldDSPVersion = new javax.swing.JTextField();
        jTextFieldHardWareVersion = new javax.swing.JTextField();
        jTextFieldPanelVersion = new javax.swing.JTextField();
        jButtonExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("基本信息");
        getContentPane().setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("基本配置信息"));
        jPanel1.setLayout(null);

        jLabel1.setText("设备名称");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(30, 30, 60, 15);

        jLabel2.setText("通道个数");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(30, 60, 60, 15);

        jLabel3.setText("报警输入数");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(30, 90, 70, 15);

        jLabel4.setText("产品序列号");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(30, 150, 70, 15);

        jLabel5.setText("设备类型");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(250, 30, 60, 15);

        jLabel6.setText("硬盘数");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(250, 60, 50, 15);

        jLabel7.setText("报警输出数");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(250, 90, 70, 15);

        jLabel8.setText("遥控器ID");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(250, 120, 70, 15);

        jLabel9.setText("循环录像");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(30, 120, 60, 15);
        jPanel1.add(jTextFieldDeviceName);
        jTextFieldDeviceName.setBounds(110, 30, 120, 21);
        jPanel1.add(jTextFieldChannelNumber);
        jTextFieldChannelNumber.setBounds(110, 60, 120, 21);
        jPanel1.add(jTextFieldAlarmInNumber);
        jTextFieldAlarmInNumber.setBounds(110, 90, 120, 21);

        jComboBoxCycleRecord.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "否", "是" }));
        jPanel1.add(jComboBoxCycleRecord);
        jComboBoxCycleRecord.setBounds(110, 120, 120, 21);
        jPanel1.add(jTextFieldSerial);
        jTextFieldSerial.setBounds(110, 150, 340, 21);

        jPanel1.add(jComboBoxDeviceType);
        jComboBoxDeviceType.setBounds(320, 30, 130, 21);
        jPanel1.add(jTextFieldHDNumber);
        jTextFieldHDNumber.setBounds(320, 60, 130, 21);
        jPanel1.add(jTextFieldAlarmOutNumber);
        jTextFieldAlarmOutNumber.setBounds(320, 90, 130, 21);
        jPanel1.add(jTextFieldTelCtlVersion);
        jTextFieldTelCtlVersion.setBounds(320, 120, 130, 21);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 20, 480, 190);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("设备版本"));
        jPanel2.setLayout(null);

        jLabel10.setText("软件版本");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(30, 30, 70, 15);

        jLabel11.setText("DSP软件版本");
        jPanel2.add(jLabel11);
        jLabel11.setBounds(30, 60, 80, 15);

        jLabel12.setText("硬件版本");
        jPanel2.add(jLabel12);
        jLabel12.setBounds(250, 30, 60, 15);

        jLabel13.setText("前面板版本");
        jPanel2.add(jLabel13);
        jLabel13.setBounds(250, 60, 70, 15);
        jPanel2.add(jTextFieldSoftwareVersion);
        jTextFieldSoftwareVersion.setBounds(110, 30, 130, 21);
        jPanel2.add(jTextFieldDSPVersion);
        jTextFieldDSPVersion.setBounds(110, 60, 130, 21);
        jPanel2.add(jTextFieldHardWareVersion);
        jTextFieldHardWareVersion.setBounds(320, 30, 140, 21);
        jPanel2.add(jTextFieldPanelVersion);
        jTextFieldPanelVersion.setBounds(320, 60, 140, 21);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(10, 219, 480, 100);

        jButtonExit.setText("退出");
        jButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExitActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonExit);
        jButtonExit.setBounds(20, 330, 100, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*************************************************
    函数:      "退出" 按钮单击相应函数
    函数描述:	销毁窗口
     *************************************************/
    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButtonExitActionPerformed

    /*************************************************
    函数:      initDialog
    函数描述:	初始化控件,显示参数
     *************************************************/
    private void initDialog()
    {
        jComboBoxDeviceType.addItem("DVR");//1   0
        jComboBoxDeviceType.addItem("ATMDVR");//2   1
        jComboBoxDeviceType.addItem("DVS");//3  2
        jComboBoxDeviceType.addItem("DEC");//4  3
        jComboBoxDeviceType.addItem("ENC_DEC");//5  4
        jComboBoxDeviceType.addItem("DVR_HC");//6   5
        jComboBoxDeviceType.addItem("DVR_HT");//7   6
        jComboBoxDeviceType.addItem("DVR_HF");//8    7
        jComboBoxDeviceType.addItem("DVR_HS");//9  8
        jComboBoxDeviceType.addItem("DVR_HTS");//10  9
        jComboBoxDeviceType.addItem("DVR_HB");//11  10
        jComboBoxDeviceType.addItem("DVR_HCS");//12  11
        jComboBoxDeviceType.addItem("DVS_A");//13  12
        jComboBoxDeviceType.addItem("DVR_HC_S");//14   13
        jComboBoxDeviceType.addItem("DVR_HT_S");//15   14
        jComboBoxDeviceType.addItem("DVR_HF_S");//16   15
        jComboBoxDeviceType.addItem("DVR_HS_S");//17  16
        jComboBoxDeviceType.addItem("ATMDVR_S");//18   17
        jComboBoxDeviceType.addItem("LOWCOST_DVR");//19   18
        jComboBoxDeviceType.addItem("DEC_MAT");//20   19
        jComboBoxDeviceType.addItem("DVR_MOBILE");//21   20
        jComboBoxDeviceType.addItem("DVR_HD_S");//22   21
        jComboBoxDeviceType.addItem("DVR_HD_SL");//23   22
        jComboBoxDeviceType.addItem("DVR_HC_SL");//24   23
        jComboBoxDeviceType.addItem("DVR_HS_ST");//25   24
        jComboBoxDeviceType.addItem("DVS_HW");//26  25

        jComboBoxDeviceType.addItem("IPCAM");//30   26
        jComboBoxDeviceType.addItem("MEGA_IPCAM");//31   27
        jComboBoxDeviceType.addItem("IPCAM_X62MF");//32   28

        jComboBoxDeviceType.addItem("IPDOME");//40  29

        jComboBoxDeviceType.addItem("IPMOD");//50   30

        jComboBoxDeviceType.addItem("DS6101_HF_B");//63   31
        jComboBoxDeviceType.addItem("DS6001_HF_B");//60   32

        jComboBoxDeviceType.addItem("DS71XX_H");//71    33
        jComboBoxDeviceType.addItem("DS72XX_H_S");//72   34
        jComboBoxDeviceType.addItem("DS73XX_H_S");//73   35

        jComboBoxDeviceType.addItem("DS81XX_HS_S");//81   36
        jComboBoxDeviceType.addItem("DS81XX_HL_S");//82   37
        jComboBoxDeviceType.addItem("DS81XX_HC_S");//83   38
        jComboBoxDeviceType.addItem("DS81XX_HD_S");//84    39
        jComboBoxDeviceType.addItem("DS81XX_HE_S");//85    40
        jComboBoxDeviceType.addItem("DS81XX_HF_S");//86   41
        jComboBoxDeviceType.addItem("DS81XX_AH_S");//87   42
        jComboBoxDeviceType.addItem("DS81XX_AHF_S");//88   43

        jComboBoxDeviceType.addItem("DS90XX_HF_S");//90   44
        jComboBoxDeviceType.addItem("DS91XX_HF_S");//91   45
        jComboBoxDeviceType.addItem("DS91XX_HD_S");//92   46

        //函数获取设备配置参数
        IntByReference ibrBytesReturned = new IntByReference(0);
        m_strDeviceCfg = new HCNetSDK.NET_DVR_DEVICECFG();
        m_strDeviceCfg.write();
        Pointer lpPicConfig = m_strDeviceCfg.getPointer();
        boolean getDVRConfigSuc = hCNetSDK.NET_DVR_GetDVRConfig(m_lUserID, HCNetSDK.NET_DVR_GET_DEVICECFG,
                new NativeLong(0), lpPicConfig, m_strDeviceCfg.size(), ibrBytesReturned);
        m_strDeviceCfg.read();
        if (getDVRConfigSuc != true)
        {
            System.out.println(hCNetSDK.NET_DVR_GetLastError());
            JOptionPane.showMessageDialog(this, "获取设备参数失败");
        }

        String[] sName = new String[2];//设备名称
        sName = new String(m_strDeviceCfg.sDVRName).split("\0", 2);
        jTextFieldDeviceName.setText(sName[0]);
        jTextFieldChannelNumber.setText(m_strDeviceCfg.byChanNum + "");//通道数
        jTextFieldAlarmInNumber.setText(m_strDeviceCfg.byAlarmInPortNum + "");//报警输入个数
        jComboBoxCycleRecord.setSelectedIndex(m_strDeviceCfg.dwRecycleRecord);//是否循环录像
        jTextFieldSerial.setText(new String(m_strDeviceCfg.sSerialNumber));//序列号
        jTextFieldHDNumber.setText(m_strDeviceCfg.byDiskNum + "");//硬盘数量
        jTextFieldAlarmOutNumber.setText(m_strDeviceCfg.byAlarmOutPortNum + "");//报警输出个数
        jTextFieldTelCtlVersion.setText(m_strDeviceCfg.dwDVRID + "");//遥控器ID

        String sSoftWareVersion;//软件版本
        if (((m_strDeviceCfg.dwSoftwareVersion >> 24) & 0xFF) > 0)
        {
            sSoftWareVersion = String.format("V%d.%d.%d build %02d%02d%02d", (m_strDeviceCfg.dwSoftwareVersion >> 24) & 0xFF, (m_strDeviceCfg.dwSoftwareVersion >> 16) & 0xFF, m_strDeviceCfg.dwSoftwareVersion & 0xFFFF, (m_strDeviceCfg.dwSoftwareBuildDate >> 16) & 0xFFFF, (m_strDeviceCfg.dwSoftwareBuildDate >> 8) & 0xFF, m_strDeviceCfg.dwSoftwareBuildDate & 0xFF);
        } else
        {
            sSoftWareVersion = String.format("V%d.%d build %02d%02d%02d", (m_strDeviceCfg.dwSoftwareVersion >> 16) & 0xFFFF, m_strDeviceCfg.dwSoftwareVersion & 0xFFFF, (m_strDeviceCfg.dwSoftwareBuildDate >> 16) & 0xFFFF, (m_strDeviceCfg.dwSoftwareBuildDate >> 8) & 0xFF, m_strDeviceCfg.dwSoftwareBuildDate & 0xFF);
        }
        jTextFieldSoftwareVersion.setText(sSoftWareVersion);

        String sDSPSoftVersion;//DSP软件版本
        sDSPSoftVersion = String.format("V%d.%d build %02d%02d%02d", (m_strDeviceCfg.dwDSPSoftwareVersion >> 16) & 0xFFFF, m_strDeviceCfg.dwDSPSoftwareVersion & 0xFFFF, (m_strDeviceCfg.dwDSPSoftwareBuildDate >> 16) & 0xFFFF - 2000, (m_strDeviceCfg.dwDSPSoftwareBuildDate >> 8) & 0xFF, m_strDeviceCfg.dwDSPSoftwareBuildDate & 0xFF);
        jTextFieldDSPVersion.setText(sDSPSoftVersion);

        String sHardwareVersion;//硬件版本
        sHardwareVersion = String.format("0x%x", m_strDeviceCfg.dwHardwareVersion);
        jTextFieldHardWareVersion.setText(sHardwareVersion);

        String sPanelVersion;//前面板版本
        sPanelVersion = String.format("V%d", m_strDeviceCfg.dwPanelVersion);
        jTextFieldPanelVersion.setText(sPanelVersion);

        if (m_strDeviceCfg.byDVRType <= 26)
        {//显示设备类型
            jComboBoxDeviceType.setSelectedIndex(m_strDeviceCfg.byDVRType - 1);
        } else
        {
            if (m_strDeviceCfg.byDVRType >= 30 && m_strDeviceCfg.byDVRType <= 32)
            {
                jComboBoxDeviceType.setSelectedIndex(m_strDeviceCfg.byDVRType - 6);
            } else
            {
                if (m_strDeviceCfg.byDVRType == 40)
                {
                    jComboBoxDeviceType.setSelectedIndex(29);
                } else
                {
                    if (m_strDeviceCfg.byDVRType == 50)
                    {
                        jComboBoxDeviceType.setSelectedIndex(30);
                    } else
                    {
                        if (m_strDeviceCfg.byDVRType == 63)
                        {
                            jComboBoxDeviceType.setSelectedIndex(31);
                        } else
                        {
                            if (m_strDeviceCfg.byDVRType == 60)
                            {
                                jComboBoxDeviceType.setSelectedIndex(32);
                            } else
                            {
                                if (m_strDeviceCfg.byDVRType >= 71 && m_strDeviceCfg.byDVRType <= 73)
                                {
                                    jComboBoxDeviceType.setSelectedIndex(m_strDeviceCfg.byDVRType - 38);
                                } else
                                {
                                    if (m_strDeviceCfg.byDVRType >= 81 && m_strDeviceCfg.byDVRType <= 88)
                                    {
                                        jComboBoxDeviceType.setSelectedIndex(m_strDeviceCfg.byDVRType - 45);
                                    } else
                                    {
                                        if (m_strDeviceCfg.byDVRType >= 90 && m_strDeviceCfg.byDVRType <= 92)
                                        {
                                            jComboBoxDeviceType.setSelectedIndex(m_strDeviceCfg.byDVRType - 46);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonExit;
    private javax.swing.JComboBox jComboBoxCycleRecord;
    private javax.swing.JComboBox jComboBoxDeviceType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextFieldAlarmInNumber;
    private javax.swing.JTextField jTextFieldAlarmOutNumber;
    private javax.swing.JTextField jTextFieldChannelNumber;
    private javax.swing.JTextField jTextFieldDSPVersion;
    private javax.swing.JTextField jTextFieldDeviceName;
    private javax.swing.JTextField jTextFieldHDNumber;
    private javax.swing.JTextField jTextFieldHardWareVersion;
    private javax.swing.JTextField jTextFieldPanelVersion;
    private javax.swing.JTextField jTextFieldSerial;
    private javax.swing.JTextField jTextFieldSoftwareVersion;
    private javax.swing.JTextField jTextFieldTelCtlVersion;
    // End of variables declaration//GEN-END:variables
}
