/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JDialogAlarmCfg.java
 *
 * Created on 2009-11-4, 10:08:45
 */
/**
 *
 * @author Administrator
 */

package ClientDemo;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;

/*****************************************************************************
 *类 ：JDialogAlarmCfg
 *类描述 ：报警输入,报警输出,异常参数配置
 ****************************************************************************/
public class JDialogAlarmCfg extends javax.swing.JDialog
{

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    private NativeLong m_lUserID;//用户ID
    private HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;//设备信息
    private CheckListItem m_exceptionAlarmOut[] = new CheckListItem[HCNetSDK.MAX_ALARMOUT_V30];//异常配置报警输出通道checkbox对应值
    private CheckListItem m_traggerAlarmOut[] = new CheckListItem[HCNetSDK.MAX_ALARMOUT_V30];//报警输入配置中报警输出通道checkbox对应值
    private CheckListItem m_traggerRecord[] = new CheckListItem[HCNetSDK.MAX_CHANNUM_V30];//报警输入配置中触发录像通道checkbox对应值
    private HCNetSDK.NET_DVR_EXCEPTION_V30 m_struExceptionInfo;//异常信息结构体
    private HCNetSDK.NET_DVR_ALARMINCFG_V30 m_struAlarmInCfg;//报警输入结构体
    private HCNetSDK.NET_DVR_ALARMOUTCFG_V30 m_struAlarmOutCfg;//报警输出结构体
    private boolean bInitialed;//Dialog是否已初始化,主要用在控制组合框状态改变响应函数的调用,只在初始化后调用

    /*************************************************
    函数:      构造函数
    函数描述:	Creates new form JDialogAlarmCfg
     *************************************************/
    public JDialogAlarmCfg(java.awt.Frame parent, boolean modal, NativeLong lUserID, HCNetSDK.NET_DVR_DEVICEINFO_V30 strDeviceInfo)
    {
        super(parent, modal);

        //赋值
        m_lUserID = lUserID;
        m_strDeviceInfo = strDeviceInfo;
        bInitialed = false;
        initComponents();

        for (int i = 0; i < HCNetSDK.MAX_ALARMOUT_V30; i++)
        {
            m_exceptionAlarmOut[i] = new CheckListItem(false, null);
            m_traggerAlarmOut[i] = new CheckListItem(false, null);
        }
         for (int i = 0; i < HCNetSDK.MAX_CHANNUM_V30; i++)
        {
            m_traggerRecord[i] = new CheckListItem(false, null);
        }



        //异常中的报警输出通道列表初始化
        jListExceptionTraggerAlarmOut.setCellRenderer(new CheckListItemRenderer());
        DefaultListModel listModelException = new DefaultListModel();
        jListExceptionTraggerAlarmOut.setModel(listModelException);
        jListExceptionTraggerAlarmOut.addMouseListener(new CheckListMouseListener());
        for (int i = 0; i < m_strDeviceInfo.byAlarmOutPortNum; i++)
        {
            m_exceptionAlarmOut[i] = new CheckListItem(false, "AlarmOut" + (i + 1));
            listModelException.addElement(m_exceptionAlarmOut[i]);    // 为异常List增加报警输出
        }
        showExceptionCfg();//调用接口获取异常参数并显示

        //报警输入里的触发报警输出通道list
        jListTraggerAlarmOut.setCellRenderer(new CheckListItemRenderer());
        DefaultListModel listModelTraggerAlarmOut = new DefaultListModel();
        jListTraggerAlarmOut.setModel(listModelTraggerAlarmOut);
        jListTraggerAlarmOut.addMouseListener(new CheckListMouseListener());
        for (int i = 0; i < m_strDeviceInfo.byAlarmOutPortNum; i++)
        {
            m_traggerAlarmOut[i] = new CheckListItem(false, "AlarmOut" + (i + 1));
            listModelTraggerAlarmOut.addElement(m_traggerAlarmOut[i]);    // 为报警输入触发报警输出List增加报警输出
        }
        //报警输入里的触发录像通道list
        jListTraggerRecord.setCellRenderer(new CheckListItemRenderer());
        DefaultListModel listModelTraggerRecord = new DefaultListModel();
        jListTraggerRecord.setModel(listModelTraggerRecord);
        jListTraggerRecord.addMouseListener(new CheckListMouseListener());
        for (int i = 0; i < m_strDeviceInfo.byChanNum; i++)
        {
            m_traggerRecord[i] = new CheckListItem(false, "Camara" + (i + 1));
            listModelTraggerRecord.addElement(m_traggerRecord[i]);    // 为报警输入触发录像通道List增加录像通道
        }

        //报警输入  通道号 combobox
        for (int i = 0; i < m_strDeviceInfo.byAlarmInPortNum; i++)
        {
            jComboBoxAlarmInChannel.addItem("AlarmIn" + (i + 1));
        }

        //报警输入 PTZ通道号 combobox
        for (int i = 0; i < m_strDeviceInfo.byChanNum; i++)
        {
            jComboBoxPTZChannel.addItem("通道" + (i + m_strDeviceInfo.byStartChan));
        }
        for (int i = 0; i < HCNetSDK.MAX_CRUISE_V30; i++)
        {
            jComboBoxCruise.addItem(i + 1);
        }
        for (int i = 0; i < HCNetSDK.MAX_TRACK_V30; i++)
        {
            jComboBoxTrack.addItem(i + 1);
        }
        for (int i = 0; i < HCNetSDK.MAX_PRESET_V30; i++)
        {
            jComboBoxPreset.addItem(i + 1);
        }
        jComboBoxPTZChannel.addActionListener(new java.awt.event.ActionListener()
        {

            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jComboBoxPTZChannelActionPerformed(evt);
            }
        });

        //报警输出通道
        for (int i = 0; i < m_strDeviceInfo.byAlarmOutPortNum; i++)
        {
            jComboBoxAlarmOutChannel.addItem("Alarm" + (i + 1));
        }

        bInitialed = true;
        jComboBoxAlarmInChannel.setSelectedIndex(0);//状态改变响应函数中获取并显示报警输入参数
        jComboBoxAlarmOutChannel.setSelectedIndex(0);//状态改变响应函数中获取并显示报警输出参数
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPaneAlarmCfg = new javax.swing.JTabbedPane();
        jPanelException = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxExceptionType = new javax.swing.JComboBox();
        jPanelExceptionTragger = new javax.swing.JPanel();
        jCheckBoxMoniter = new javax.swing.JCheckBox();
        jCheckBoxAudio = new javax.swing.JCheckBox();
        jCheckBoxCenter = new javax.swing.JCheckBox();
        jCheckBoxTraggerAlarmOut = new javax.swing.JCheckBox();
        jCheckBoxEMail = new javax.swing.JCheckBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListExceptionTraggerAlarmOut = new javax.swing.JList();
        jButtonComfirmException = new javax.swing.JButton();
        jButtonSetupException = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jButtonExit1 = new javax.swing.JButton();
        jPanelAlarmIn = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBoxAlarmInChannel = new javax.swing.JComboBox();
        jTextFieldDeviceAddress = new javax.swing.JTextField();
        jTextFieldAlarmInChannel = new javax.swing.JTextField();
        jTextFieldAlarmInName = new javax.swing.JTextField();
        jComboBoxAlarmType = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jCheckBoxMoniterAlarm = new javax.swing.JCheckBox();
        jCheckBoxAudioAlarm = new javax.swing.JCheckBox();
        jCheckBoxCenterAlarm = new javax.swing.JCheckBox();
        jCheckBoxEMailAlarm = new javax.swing.JCheckBox();
        jCheckBoxTraggerAlarmOutAlarm = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListTraggerAlarmOut = new javax.swing.JList();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListTraggerRecord = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxDate = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextFieldInEndMin1 = new javax.swing.JTextField();
        jTextFieldInBeginHour1 = new javax.swing.JTextField();
        jTextFieldInBeginMin1 = new javax.swing.JTextField();
        jTextFieldInEndHour1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jTextFieldInEndMin2 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jTextFieldInEndHour2 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jTextFieldInBeginMin2 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jTextFieldInBeginHour2 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jTextFieldInEndMin3 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jTextFieldInEndHour3 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jTextFieldInBeginMin3 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jTextFieldInBeginHour3 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jTextFieldInEndMin4 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jTextFieldInEndHour4 = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jTextFieldInBeginMin4 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jTextFieldInBeginHour4 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jTextFieldInEndMin5 = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jTextFieldInEndHour5 = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jTextFieldInBeginMin5 = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jTextFieldInBeginHour5 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jTextFieldInEndMin6 = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jTextFieldInEndHour6 = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jTextFieldInBeginMin6 = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jTextFieldInBeginHour6 = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        jTextFieldInEndMin7 = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jTextFieldInEndHour7 = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jTextFieldInBeginMin7 = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jTextFieldInBeginHour7 = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jTextFieldInEndMin8 = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jTextFieldInEndHour8 = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        jTextFieldInBeginMin8 = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        jTextFieldInBeginHour8 = new javax.swing.JTextField();
        jButtonConfirmInTime = new javax.swing.JButton();
        jCheckBoxAlarmInHandle = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        jComboBoxPTZChannel = new javax.swing.JComboBox();
        jRadioButtonCruise = new javax.swing.JRadioButton();
        jComboBoxCruise = new javax.swing.JComboBox();
        jRadioButtonPreset = new javax.swing.JRadioButton();
        jComboBoxPreset = new javax.swing.JComboBox();
        jRadioButtonTrack = new javax.swing.JRadioButton();
        jComboBoxTrack = new javax.swing.JComboBox();
        jButtonPTZConfirm = new javax.swing.JButton();
        jButtonSetupAlarmIn = new javax.swing.JButton();
        jButtonExit3 = new javax.swing.JButton();
        jPanelAlarmOut = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jComboBoxAlarmOutChannel = new javax.swing.JComboBox();
        jLabel63 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        jTextFieldAlarmOutName = new javax.swing.JTextField();
        jLabel66 = new javax.swing.JLabel();
        jTextFieldAlarmOutDelay = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        jTextFieldfHour1 = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jTextFieldfMin1 = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        jTextFieldsHour1 = new javax.swing.JTextField();
        jLabel71 = new javax.swing.JLabel();
        jTextFieldsMin1 = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jTextFieldfHour2 = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        jTextFieldfMin2 = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jTextFieldsHour2 = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        jTextFieldsMin2 = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jTextFieldfHour3 = new javax.swing.JTextField();
        jLabel80 = new javax.swing.JLabel();
        jTextFieldfMin3 = new javax.swing.JTextField();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jTextFieldsHour3 = new javax.swing.JTextField();
        jLabel83 = new javax.swing.JLabel();
        jTextFieldsMin3 = new javax.swing.JTextField();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jTextFieldfHour4 = new javax.swing.JTextField();
        jLabel86 = new javax.swing.JLabel();
        jTextFieldfMin4 = new javax.swing.JTextField();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jTextFieldsHour4 = new javax.swing.JTextField();
        jLabel89 = new javax.swing.JLabel();
        jTextFieldsMin4 = new javax.swing.JTextField();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jTextFieldfHour5 = new javax.swing.JTextField();
        jLabel92 = new javax.swing.JLabel();
        jTextFieldfMin5 = new javax.swing.JTextField();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jTextFieldsHour5 = new javax.swing.JTextField();
        jLabel95 = new javax.swing.JLabel();
        jTextFieldsMin5 = new javax.swing.JTextField();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jTextFieldfHour6 = new javax.swing.JTextField();
        jLabel98 = new javax.swing.JLabel();
        jTextFieldfMin6 = new javax.swing.JTextField();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jTextFieldsHour6 = new javax.swing.JTextField();
        jLabel101 = new javax.swing.JLabel();
        jTextFieldsMin6 = new javax.swing.JTextField();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jTextFieldfHour7 = new javax.swing.JTextField();
        jLabel104 = new javax.swing.JLabel();
        jTextFieldfMin7 = new javax.swing.JTextField();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jTextFieldsHour7 = new javax.swing.JTextField();
        jLabel107 = new javax.swing.JLabel();
        jTextFieldsMin7 = new javax.swing.JTextField();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jTextFieldfHour8 = new javax.swing.JTextField();
        jLabel110 = new javax.swing.JLabel();
        jTextFieldfMin8 = new javax.swing.JTextField();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jTextFieldsHour8 = new javax.swing.JTextField();
        jLabel113 = new javax.swing.JLabel();
        jTextFieldsMin8 = new javax.swing.JTextField();
        jLabel114 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        jComboBoxWeekDay = new javax.swing.JComboBox();
        jButtonConfirm = new javax.swing.JButton();
        jToggleButtonSetupAlarmOut = new javax.swing.JToggleButton();
        jButtonExit2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("报警参数配置");

        jPanelException.setLayout(null);

        jLabel1.setText("异常类型");
        jPanelException.add(jLabel1);
        jLabel1.setBounds(30, 40, 60, 15);

        jComboBoxExceptionType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "硬盘满", "硬盘出错", "网线断", "IP 地址冲突", "非法访问", "输入/输出视频制式不匹配", "视频信号异常" }));
        jComboBoxExceptionType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxExceptionTypeActionPerformed(evt);
            }
        });
        jPanelException.add(jComboBoxExceptionType);
        jComboBoxExceptionType.setBounds(100, 30, 180, 21);

        jPanelExceptionTragger.setBorder(javax.swing.BorderFactory.createTitledBorder("报警处理方式"));

        jCheckBoxMoniter.setText("监视器上警告");

        jCheckBoxAudio.setText("声音警告");

        jCheckBoxCenter.setText("上传中心");

        jCheckBoxTraggerAlarmOut.setText("触发报警输出");

        jCheckBoxEMail.setText("发送邮件");

        jScrollPane3.setViewportView(jListExceptionTraggerAlarmOut);

        javax.swing.GroupLayout jPanelExceptionTraggerLayout = new javax.swing.GroupLayout(jPanelExceptionTragger);
        jPanelExceptionTragger.setLayout(jPanelExceptionTraggerLayout);
        jPanelExceptionTraggerLayout.setHorizontalGroup(
            jPanelExceptionTraggerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelExceptionTraggerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelExceptionTraggerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxMoniter, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxEMail)
                    .addComponent(jCheckBoxAudio)
                    .addComponent(jCheckBoxCenter))
                .addGap(34, 34, 34)
                .addGroup(jPanelExceptionTraggerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxTraggerAlarmOut)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );
        jPanelExceptionTraggerLayout.setVerticalGroup(
            jPanelExceptionTraggerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelExceptionTraggerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelExceptionTraggerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxMoniter)
                    .addComponent(jCheckBoxTraggerAlarmOut))
                .addGroup(jPanelExceptionTraggerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelExceptionTraggerLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jCheckBoxAudio)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBoxCenter)
                        .addGap(26, 26, 26)
                        .addComponent(jCheckBoxEMail)
                        .addGap(60, 60, 60))
                    .addGroup(jPanelExceptionTraggerLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jPanelException.add(jPanelExceptionTragger);
        jPanelExceptionTragger.setBounds(20, 90, 400, 340);

        jButtonComfirmException.setText("确认");
        jButtonComfirmException.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonComfirmExceptionActionPerformed(evt);
            }
        });
        jPanelException.add(jButtonComfirmException);
        jButtonComfirmException.setBounds(40, 450, 70, 23);

        jButtonSetupException.setText("设置异常配置");
        jButtonSetupException.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetupExceptionActionPerformed(evt);
            }
        });
        jPanelException.add(jButtonSetupException);
        jButtonSetupException.setBounds(20, 520, 120, 23);

        jSeparator1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanelException.add(jSeparator1);
        jSeparator1.setBounds(10, 10, 570, 490);

        jButtonExit1.setText("退出");
        jButtonExit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExit1ActionPerformed(evt);
            }
        });
        jPanelException.add(jButtonExit1);
        jButtonExit1.setBounds(460, 520, 70, 23);

        jTabbedPaneAlarmCfg.addTab("异常配置信息", jPanelException);

        jLabel2.setText("报警输入");

        jLabel3.setText("IP设备地址");

        jLabel4.setText("IP输入通道");

        jLabel5.setText("报警输入名称");

        jLabel6.setText("报警器类型");

        jComboBoxAlarmInChannel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxAlarmInChannelActionPerformed(evt);
            }
        });

        jTextFieldAlarmInChannel.setText("           ");

        jTextFieldAlarmInName.setText("           ");

        jComboBoxAlarmType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "常开", "常闭" }));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("报警输入处理"));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("报警处理方式"));

        jCheckBoxMoniterAlarm.setText("监视器上警告");

        jCheckBoxAudioAlarm.setText("声音警告");

        jCheckBoxCenterAlarm.setText("上传中心");

        jCheckBoxEMailAlarm.setText("发送邮件");

        jCheckBoxTraggerAlarmOutAlarm.setText("触发报警输出");

        jScrollPane2.setViewportView(jListTraggerAlarmOut);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxMoniterAlarm)
                    .addComponent(jCheckBoxAudioAlarm)
                    .addComponent(jCheckBoxCenterAlarm)
                    .addComponent(jCheckBoxEMailAlarm))
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxTraggerAlarmOutAlarm)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jCheckBoxMoniterAlarm))
                    .addComponent(jCheckBoxTraggerAlarmOutAlarm))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jCheckBoxAudioAlarm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxCenterAlarm)
                        .addGap(4, 4, 4)
                        .addComponent(jCheckBoxEMailAlarm))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("触发录像通道"));

        jScrollPane1.setViewportView(jListTraggerRecord);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("布防时间")));
        jPanel2.setLayout(null);

        jLabel7.setText("日期");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(20, 20, 50, 15);

        jComboBoxDate.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" }));
        jComboBoxDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxDateActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBoxDate);
        jComboBoxDate.setBounds(80, 20, 90, 21);

        jLabel8.setText("Time4");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(20, 150, 42, 15);

        jLabel9.setText("Time3");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(20, 120, 40, 15);

        jLabel10.setText("Time2");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(20, 90, 40, 15);

        jLabel11.setText("Time5");
        jPanel2.add(jLabel11);
        jLabel11.setBounds(260, 60, 40, 15);

        jLabel12.setText("Time6");
        jPanel2.add(jLabel12);
        jLabel12.setBounds(260, 90, 40, 15);

        jLabel13.setText("Time7");
        jPanel2.add(jLabel13);
        jLabel13.setBounds(260, 120, 40, 15);

        jLabel14.setText("Time8");
        jPanel2.add(jLabel14);
        jLabel14.setBounds(260, 150, 40, 15);

        jLabel16.setText("Time1");
        jPanel2.add(jLabel16);
        jLabel16.setBounds(20, 60, 40, 15);
        jPanel2.add(jTextFieldInEndMin1);
        jTextFieldInEndMin1.setBounds(210, 60, 20, 21);
        jPanel2.add(jTextFieldInBeginHour1);
        jTextFieldInBeginHour1.setBounds(80, 60, 20, 21);
        jPanel2.add(jTextFieldInBeginMin1);
        jTextFieldInBeginMin1.setBounds(120, 60, 20, 21);
        jPanel2.add(jTextFieldInEndHour1);
        jTextFieldInEndHour1.setBounds(170, 60, 20, 21);

        jLabel15.setText("时");
        jPanel2.add(jLabel15);
        jLabel15.setBounds(190, 60, 20, 15);

        jLabel17.setText("分--");
        jPanel2.add(jLabel17);
        jLabel17.setBounds(140, 60, 30, 15);

        jLabel18.setText("时");
        jPanel2.add(jLabel18);
        jLabel18.setBounds(100, 60, 20, 15);

        jLabel19.setText("分");
        jPanel2.add(jLabel19);
        jLabel19.setBounds(230, 60, 20, 15);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("布防时间")));
        jPanel5.setLayout(null);

        jLabel20.setText("日期");
        jPanel5.add(jLabel20);
        jLabel20.setBounds(20, 20, 24, 15);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel5.add(jComboBox2);
        jComboBox2.setBounds(60, 20, 62, 21);

        jLabel21.setText("Time4");
        jPanel5.add(jLabel21);
        jLabel21.setBounds(20, 150, 42, 15);

        jLabel22.setText("Time3");
        jPanel5.add(jLabel22);
        jLabel22.setBounds(20, 120, 30, 15);

        jLabel23.setText("Time2");
        jPanel5.add(jLabel23);
        jLabel23.setBounds(20, 90, 30, 15);

        jLabel24.setText("Time5");
        jPanel5.add(jLabel24);
        jLabel24.setBounds(370, 60, 30, 15);

        jLabel25.setText("Time6");
        jPanel5.add(jLabel25);
        jLabel25.setBounds(350, 100, 30, 15);

        jLabel26.setText("Time7");
        jPanel5.add(jLabel26);
        jLabel26.setBounds(350, 130, 30, 15);

        jLabel27.setText("Time8");
        jPanel5.add(jLabel27);
        jLabel27.setBounds(360, 150, 30, 15);

        jLabel28.setText("Time1");
        jPanel5.add(jLabel28);
        jLabel28.setBounds(20, 60, 30, 15);

        jTextField5.setText("24");
        jPanel5.add(jTextField5);
        jTextField5.setBounds(190, 60, 20, 21);

        jTextField6.setText("12");
        jPanel5.add(jTextField6);
        jTextField6.setBounds(60, 60, 20, 21);

        jTextField7.setText("12");
        jPanel5.add(jTextField7);
        jTextField7.setBounds(100, 60, 20, 21);

        jTextField8.setText("12");
        jPanel5.add(jTextField8);
        jTextField8.setBounds(150, 60, 20, 21);

        jLabel29.setText("时");
        jPanel5.add(jLabel29);
        jLabel29.setBounds(170, 60, 20, 15);

        jLabel30.setText("分--");
        jPanel5.add(jLabel30);
        jLabel30.setBounds(120, 60, 30, 15);

        jLabel31.setText("时");
        jPanel5.add(jLabel31);
        jLabel31.setBounds(80, 60, 20, 15);

        jLabel32.setText("分");
        jPanel5.add(jLabel32);
        jLabel32.setBounds(210, 60, 20, 15);

        jPanel2.add(jPanel5);
        jPanel5.setBounds(0, 0, 0, 0);

        jLabel33.setText("分");
        jPanel2.add(jLabel33);
        jLabel33.setBounds(230, 90, 20, 15);
        jPanel2.add(jTextFieldInEndMin2);
        jTextFieldInEndMin2.setBounds(210, 90, 20, 21);

        jLabel34.setText("时");
        jPanel2.add(jLabel34);
        jLabel34.setBounds(190, 90, 20, 15);
        jPanel2.add(jTextFieldInEndHour2);
        jTextFieldInEndHour2.setBounds(170, 90, 20, 21);

        jLabel35.setText("分--");
        jPanel2.add(jLabel35);
        jLabel35.setBounds(140, 90, 30, 15);
        jPanel2.add(jTextFieldInBeginMin2);
        jTextFieldInBeginMin2.setBounds(120, 90, 20, 21);

        jLabel36.setText("时");
        jPanel2.add(jLabel36);
        jLabel36.setBounds(100, 90, 20, 15);
        jPanel2.add(jTextFieldInBeginHour2);
        jTextFieldInBeginHour2.setBounds(80, 90, 20, 21);

        jLabel37.setText("分");
        jPanel2.add(jLabel37);
        jLabel37.setBounds(230, 120, 20, 15);
        jPanel2.add(jTextFieldInEndMin3);
        jTextFieldInEndMin3.setBounds(210, 120, 20, 21);

        jLabel38.setText("时");
        jPanel2.add(jLabel38);
        jLabel38.setBounds(190, 120, 20, 15);
        jPanel2.add(jTextFieldInEndHour3);
        jTextFieldInEndHour3.setBounds(170, 120, 20, 21);

        jLabel39.setText("分--");
        jPanel2.add(jLabel39);
        jLabel39.setBounds(140, 120, 30, 15);
        jPanel2.add(jTextFieldInBeginMin3);
        jTextFieldInBeginMin3.setBounds(120, 120, 20, 21);

        jLabel40.setText("时");
        jPanel2.add(jLabel40);
        jLabel40.setBounds(100, 120, 20, 15);
        jPanel2.add(jTextFieldInBeginHour3);
        jTextFieldInBeginHour3.setBounds(80, 120, 20, 21);

        jLabel41.setText("分");
        jPanel2.add(jLabel41);
        jLabel41.setBounds(230, 150, 20, 15);
        jPanel2.add(jTextFieldInEndMin4);
        jTextFieldInEndMin4.setBounds(210, 150, 20, 21);

        jLabel42.setText("时");
        jPanel2.add(jLabel42);
        jLabel42.setBounds(190, 150, 20, 15);
        jPanel2.add(jTextFieldInEndHour4);
        jTextFieldInEndHour4.setBounds(170, 150, 20, 21);

        jLabel43.setText("分--");
        jPanel2.add(jLabel43);
        jLabel43.setBounds(140, 150, 30, 15);
        jPanel2.add(jTextFieldInBeginMin4);
        jTextFieldInBeginMin4.setBounds(120, 150, 20, 21);

        jLabel44.setText("时");
        jPanel2.add(jLabel44);
        jLabel44.setBounds(100, 150, 20, 15);
        jPanel2.add(jTextFieldInBeginHour4);
        jTextFieldInBeginHour4.setBounds(80, 150, 20, 21);

        jLabel45.setText("分");
        jPanel2.add(jLabel45);
        jLabel45.setBounds(460, 60, 20, 15);
        jPanel2.add(jTextFieldInEndMin5);
        jTextFieldInEndMin5.setBounds(440, 60, 20, 21);

        jLabel46.setText("时");
        jPanel2.add(jLabel46);
        jLabel46.setBounds(420, 60, 20, 15);
        jPanel2.add(jTextFieldInEndHour5);
        jTextFieldInEndHour5.setBounds(400, 60, 20, 21);

        jLabel47.setText("分--");
        jPanel2.add(jLabel47);
        jLabel47.setBounds(370, 60, 30, 15);
        jPanel2.add(jTextFieldInBeginMin5);
        jTextFieldInBeginMin5.setBounds(350, 60, 20, 21);

        jLabel48.setText("时");
        jPanel2.add(jLabel48);
        jLabel48.setBounds(330, 60, 20, 15);
        jPanel2.add(jTextFieldInBeginHour5);
        jTextFieldInBeginHour5.setBounds(310, 60, 20, 21);

        jLabel49.setText("分");
        jPanel2.add(jLabel49);
        jLabel49.setBounds(460, 90, 20, 15);
        jPanel2.add(jTextFieldInEndMin6);
        jTextFieldInEndMin6.setBounds(440, 90, 20, 21);

        jLabel50.setText("时");
        jPanel2.add(jLabel50);
        jLabel50.setBounds(420, 90, 20, 15);
        jPanel2.add(jTextFieldInEndHour6);
        jTextFieldInEndHour6.setBounds(400, 90, 20, 21);

        jLabel51.setText("分--");
        jPanel2.add(jLabel51);
        jLabel51.setBounds(370, 90, 30, 15);
        jPanel2.add(jTextFieldInBeginMin6);
        jTextFieldInBeginMin6.setBounds(350, 90, 20, 21);

        jLabel52.setText("时");
        jPanel2.add(jLabel52);
        jLabel52.setBounds(330, 90, 20, 15);
        jPanel2.add(jTextFieldInBeginHour6);
        jTextFieldInBeginHour6.setBounds(310, 90, 20, 21);

        jLabel53.setText("分");
        jPanel2.add(jLabel53);
        jLabel53.setBounds(460, 120, 20, 15);
        jPanel2.add(jTextFieldInEndMin7);
        jTextFieldInEndMin7.setBounds(440, 120, 20, 21);

        jLabel54.setText("时");
        jPanel2.add(jLabel54);
        jLabel54.setBounds(420, 120, 20, 15);
        jPanel2.add(jTextFieldInEndHour7);
        jTextFieldInEndHour7.setBounds(400, 120, 20, 21);

        jLabel55.setText("分--");
        jPanel2.add(jLabel55);
        jLabel55.setBounds(370, 120, 24, 15);
        jPanel2.add(jTextFieldInBeginMin7);
        jTextFieldInBeginMin7.setBounds(350, 120, 20, 21);

        jLabel56.setText("时");
        jPanel2.add(jLabel56);
        jLabel56.setBounds(330, 120, 20, 15);
        jPanel2.add(jTextFieldInBeginHour7);
        jTextFieldInBeginHour7.setBounds(310, 120, 20, 21);

        jLabel57.setText("分");
        jPanel2.add(jLabel57);
        jLabel57.setBounds(460, 150, 20, 15);
        jPanel2.add(jTextFieldInEndMin8);
        jTextFieldInEndMin8.setBounds(440, 150, 20, 21);

        jLabel58.setText("时");
        jPanel2.add(jLabel58);
        jLabel58.setBounds(420, 150, 20, 15);
        jPanel2.add(jTextFieldInEndHour8);
        jTextFieldInEndHour8.setBounds(400, 150, 20, 21);

        jLabel59.setText("分--");
        jPanel2.add(jLabel59);
        jLabel59.setBounds(370, 150, 30, 15);
        jPanel2.add(jTextFieldInBeginMin8);
        jTextFieldInBeginMin8.setBounds(350, 150, 20, 21);

        jLabel60.setText("时");
        jPanel2.add(jLabel60);
        jLabel60.setBounds(330, 150, 20, 15);
        jPanel2.add(jTextFieldInBeginHour8);
        jTextFieldInBeginHour8.setBounds(310, 150, 20, 21);

        jButtonConfirmInTime.setText("确定");
        jButtonConfirmInTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmInTimeActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonConfirmInTime);
        jButtonConfirmInTime.setBounds(490, 150, 60, 23);

        jCheckBoxAlarmInHandle.setText("报警输入处理");

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("PTZ联动")));
        jPanel6.setLayout(null);

        jLabel61.setText("通道号");
        jPanel6.add(jLabel61);
        jLabel61.setBounds(30, 20, 50, 15);

        jComboBoxPTZChannel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxPTZChannelActionPerformed(evt);
            }
        });
        jPanel6.add(jComboBoxPTZChannel);
        jComboBoxPTZChannel.setBounds(100, 20, 60, 21);

        buttonGroup1.add(jRadioButtonCruise);
        jRadioButtonCruise.setText("巡航");
        jPanel6.add(jRadioButtonCruise);
        jRadioButtonCruise.setBounds(30, 60, 60, 23);

        jPanel6.add(jComboBoxCruise);
        jComboBoxCruise.setBounds(100, 60, 60, 21);

        buttonGroup1.add(jRadioButtonPreset);
        jRadioButtonPreset.setText("预置点");
        jPanel6.add(jRadioButtonPreset);
        jRadioButtonPreset.setBounds(170, 60, 70, 23);

        jPanel6.add(jComboBoxPreset);
        jComboBoxPreset.setBounds(240, 60, 60, 21);

        buttonGroup1.add(jRadioButtonTrack);
        jRadioButtonTrack.setText("轨迹");
        jPanel6.add(jRadioButtonTrack);
        jRadioButtonTrack.setBounds(310, 60, 60, 23);

        jPanel6.add(jComboBoxTrack);
        jComboBoxTrack.setBounds(370, 60, 60, 21);

        jButtonPTZConfirm.setText("确定");
        jButtonPTZConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPTZConfirmActionPerformed(evt);
            }
        });
        jPanel6.add(jButtonPTZConfirm);
        jButtonPTZConfirm.setBounds(490, 50, 60, 23);

        jButtonSetupAlarmIn.setText("设置当前报警通道");
        jButtonSetupAlarmIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetupAlarmInActionPerformed(evt);
            }
        });

        jButtonExit3.setText("退出");
        jButtonExit3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExit3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelAlarmInLayout = new javax.swing.GroupLayout(jPanelAlarmIn);
        jPanelAlarmIn.setLayout(jPanelAlarmInLayout);
        jPanelAlarmInLayout.setHorizontalGroup(
            jPanelAlarmInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAlarmInLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAlarmInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAlarmInLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jButtonSetupAlarmIn)
                        .addGap(308, 308, 308)
                        .addComponent(jButtonExit3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAlarmInLayout.createSequentialGroup()
                        .addGroup(jPanelAlarmInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelAlarmInLayout.createSequentialGroup()
                                .addGroup(jPanelAlarmInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanelAlarmInLayout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextFieldAlarmInName))
                                    .addGroup(jPanelAlarmInLayout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(jComboBoxAlarmInChannel, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanelAlarmInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(jPanelAlarmInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldDeviceAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                                    .addComponent(jComboBoxAlarmType, 0, 81, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanelAlarmInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelAlarmInLayout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextFieldAlarmInChannel))
                                    .addComponent(jCheckBoxAlarmInHandle, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)))
                        .addGap(23, 23, 23))))
        );
        jPanelAlarmInLayout.setVerticalGroup(
            jPanelAlarmInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAlarmInLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAlarmInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBoxAlarmInChannel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldDeviceAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldAlarmInChannel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanelAlarmInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldAlarmInName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBoxAlarmType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxAlarmInHandle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAlarmInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonExit3)
                    .addComponent(jButtonSetupAlarmIn)))
        );

        jTabbedPaneAlarmCfg.addTab("报警输入", jPanelAlarmIn);

        jPanelAlarmOut.setLayout(null);

        jLabel62.setText("报警输出");
        jPanelAlarmOut.add(jLabel62);
        jLabel62.setBounds(40, 30, 48, 15);

        jComboBoxAlarmOutChannel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxAlarmOutChannelActionPerformed(evt);
            }
        });
        jPanelAlarmOut.add(jComboBoxAlarmOutChannel);
        jComboBoxAlarmOutChannel.setBounds(120, 30, 70, 21);

        jLabel63.setText("IP设备地址");
        jPanelAlarmOut.add(jLabel63);
        jLabel63.setBounds(210, 30, 70, 15);
        jPanelAlarmOut.add(jTextField1);
        jTextField1.setBounds(300, 30, 70, 21);

        jLabel64.setText("IP输出通道");
        jPanelAlarmOut.add(jLabel64);
        jLabel64.setBounds(400, 30, 70, 15);
        jPanelAlarmOut.add(jTextField2);
        jTextField2.setBounds(490, 30, 80, 21);

        jLabel65.setText("报警输出名称");
        jPanelAlarmOut.add(jLabel65);
        jLabel65.setBounds(40, 80, 90, 15);
        jPanelAlarmOut.add(jTextFieldAlarmOutName);
        jTextFieldAlarmOutName.setBounds(120, 80, 70, 21);

        jLabel66.setText("报警输出延时");
        jPanelAlarmOut.add(jLabel66);
        jLabel66.setBounds(210, 80, 90, 15);
        jPanelAlarmOut.add(jTextFieldAlarmOutDelay);
        jTextFieldAlarmOutDelay.setBounds(300, 80, 70, 21);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("报警输出触发时间"));

        jLabel67.setText("时间段1");

        jLabel68.setText("时");

        jLabel69.setText("分");

        jLabel70.setText("到");

        jLabel71.setText("时");

        jLabel72.setText("分");

        jLabel73.setText("时间段2");

        jLabel74.setText("时");

        jLabel75.setText("分");

        jLabel76.setText("到");

        jLabel77.setText("时");

        jLabel78.setText("分");

        jLabel79.setText("时间段3");

        jLabel80.setText("时");

        jLabel81.setText("分");

        jLabel82.setText("到");

        jLabel83.setText("时");

        jLabel84.setText("分");

        jLabel85.setText("时间段4");

        jLabel86.setText("时");

        jLabel87.setText("分");

        jLabel88.setText("到");

        jLabel89.setText("时");

        jLabel90.setText("分");

        jLabel91.setText("时间段5");

        jLabel92.setText("时");

        jLabel93.setText("分");

        jLabel94.setText("到");

        jLabel95.setText("时");

        jLabel96.setText("分");

        jLabel97.setText("时间段6");

        jLabel98.setText("时");

        jLabel99.setText("分");

        jLabel100.setText("到");

        jLabel101.setText("时");

        jLabel102.setText("分");

        jLabel103.setText("时间段7");

        jLabel104.setText("时");

        jLabel105.setText("分");

        jLabel106.setText("到");

        jLabel107.setText("时");

        jLabel108.setText("分");

        jLabel109.setText("时间段8");

        jLabel110.setText("时");

        jLabel111.setText("分");

        jLabel112.setText("到");

        jLabel113.setText("时");

        jLabel114.setText("分");

        jLabel115.setText("日期");

        jComboBoxWeekDay.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" }));
        jComboBoxWeekDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxWeekDayActionPerformed(evt);
            }
        });

        jButtonConfirm.setText("确定");
        jButtonConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel103, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jTextFieldfHour7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel104, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldfMin7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel105, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel106, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldsHour7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel107, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldsMin7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jTextFieldfHour6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel98, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldfMin6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel99, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel100, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldsHour6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldsMin6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jTextFieldfHour5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldfMin5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel93, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel94, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldsHour5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldsMin5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel96, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel85, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jTextFieldfHour4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel86, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldfMin4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldsHour4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel89, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldsMin4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel90, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jTextFieldfHour3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldfMin3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldsHour3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldsMin3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldfHour2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldfMin2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldsHour2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldsMin2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(42, 42, 42)))
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jTextFieldfHour1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldfMin1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldsHour1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldsMin1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jComboBoxWeekDay, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel109, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldfHour8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel110, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldfMin8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel111, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel112, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldsHour8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldsMin8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel114, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(109, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(420, Short.MAX_VALUE)
                .addComponent(jButtonConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel115)
                    .addComponent(jComboBoxWeekDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel67)
                    .addComponent(jTextFieldfHour1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel68)
                    .addComponent(jTextFieldfMin1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel69)
                    .addComponent(jLabel70)
                    .addComponent(jTextFieldsHour1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel71)
                    .addComponent(jTextFieldsMin1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel72))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel73)
                    .addComponent(jTextFieldfHour2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel74)
                    .addComponent(jTextFieldfMin2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel75)
                    .addComponent(jLabel76)
                    .addComponent(jTextFieldsHour2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel77)
                    .addComponent(jTextFieldsMin2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel78))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel79)
                    .addComponent(jTextFieldfHour3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel80)
                    .addComponent(jTextFieldfMin3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel81)
                    .addComponent(jLabel82)
                    .addComponent(jTextFieldsHour3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel83)
                    .addComponent(jTextFieldsMin3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel84))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel85)
                    .addComponent(jTextFieldfHour4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel86)
                    .addComponent(jTextFieldfMin4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel87)
                    .addComponent(jLabel88)
                    .addComponent(jTextFieldsHour4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel89)
                    .addComponent(jTextFieldsMin4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel90))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel91)
                    .addComponent(jTextFieldfHour5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel92)
                    .addComponent(jTextFieldfMin5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel93)
                    .addComponent(jLabel94)
                    .addComponent(jTextFieldsHour5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel95)
                    .addComponent(jTextFieldsMin5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel96))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel97)
                    .addComponent(jTextFieldfHour6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel98)
                    .addComponent(jTextFieldfMin6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel99)
                    .addComponent(jLabel100)
                    .addComponent(jTextFieldsHour6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel101)
                    .addComponent(jTextFieldsMin6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel102))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel103)
                    .addComponent(jTextFieldfHour7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel104)
                    .addComponent(jTextFieldfMin7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel105)
                    .addComponent(jLabel106)
                    .addComponent(jTextFieldsHour7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel107)
                    .addComponent(jTextFieldsMin7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel108))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel109)
                    .addComponent(jTextFieldfHour8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel110)
                    .addComponent(jTextFieldfMin8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel111)
                    .addComponent(jLabel112)
                    .addComponent(jTextFieldsHour8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel113)
                    .addComponent(jTextFieldsMin8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel114))
                .addGap(18, 18, 18)
                .addComponent(jButtonConfirm)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelAlarmOut.add(jPanel7);
        jPanel7.setBounds(20, 120, 560, 360);

        jToggleButtonSetupAlarmOut.setText("设置当前通道");
        jToggleButtonSetupAlarmOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonSetupAlarmOutActionPerformed(evt);
            }
        });
        jPanelAlarmOut.add(jToggleButtonSetupAlarmOut);
        jToggleButtonSetupAlarmOut.setBounds(40, 510, 117, 23);

        jButtonExit2.setText("退出");
        jButtonExit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExit2ActionPerformed(evt);
            }
        });
        jPanelAlarmOut.add(jButtonExit2);
        jButtonExit2.setBounds(450, 510, 70, 23);

        jTabbedPaneAlarmCfg.addTab("报警输出", jPanelAlarmOut);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPaneAlarmCfg, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPaneAlarmCfg, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*************************************************
    函数:      异常 "确认" 按钮单击响应函数
    函数描述:  保存对应异常类型的参数至结构体
     *************************************************/
    private void jButtonComfirmExceptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonComfirmExceptionActionPerformed
        int iExceptionType = jComboBoxExceptionType.getSelectedIndex();

        m_struExceptionInfo.struExceptionHandleType[iExceptionType].dwHandleType = 0;
        m_struExceptionInfo.struExceptionHandleType[iExceptionType].dwHandleType |= ((jCheckBoxMoniter.isSelected() == true ? 1 : 0) << 0);
        m_struExceptionInfo.struExceptionHandleType[iExceptionType].dwHandleType |= ((jCheckBoxAudio.isSelected() == true ? 1 : 0) << 1);
        m_struExceptionInfo.struExceptionHandleType[iExceptionType].dwHandleType |= ((jCheckBoxCenter.isSelected() == true ? 1 : 0) << 2);
        m_struExceptionInfo.struExceptionHandleType[iExceptionType].dwHandleType |= ((jCheckBoxTraggerAlarmOut.isSelected() == true ? 1 : 0) << 3);
        m_struExceptionInfo.struExceptionHandleType[iExceptionType].dwHandleType |= ((jCheckBoxEMail.isSelected() == true ? 1 : 0) << 4);

        for (int i = 0; i < m_strDeviceInfo.byAlarmOutPortNum; i++)
        {
            m_struExceptionInfo.struExceptionHandleType[iExceptionType].byRelAlarmOut[i] = (byte) ((m_exceptionAlarmOut[i].check == true) ? 1 : 0);
        }
    }//GEN-LAST:event_jButtonComfirmExceptionActionPerformed

    /*************************************************
    函数:       异常 "设置异常配置" 按钮单击响应函数
    函数描述:   调用接口设置异常参数
     *************************************************/
    private void jButtonSetupExceptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSetupExceptionActionPerformed
        m_struExceptionInfo.write();
        Pointer lpConfig = m_struExceptionInfo.getPointer();
        boolean setDVRConfigSuc = hCNetSDK.NET_DVR_SetDVRConfig(m_lUserID, HCNetSDK.NET_DVR_SET_EXCEPTIONCFG_V30,
                new NativeLong(0), lpConfig, m_struExceptionInfo.size());
        m_struExceptionInfo.read();
        if (setDVRConfigSuc != true)
        {
            JOptionPane.showMessageDialog(this, "设置异常配置失败");
            return;
        } else
        {
            JOptionPane.showMessageDialog(this, "设置异常配置成功");
        }
    }//GEN-LAST:event_jButtonSetupExceptionActionPerformed

    /*************************************************
    函数:      "异常类型" 下拉框   选项改变事件响应函数
    函数描述:  显示对应该异常类型的参数
     *************************************************/
    private void jComboBoxExceptionTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxExceptionTypeActionPerformed
        showExceptionTypeCfg();
    }//GEN-LAST:event_jComboBoxExceptionTypeActionPerformed

    /*************************************************
    函数:      "报警输入" 下拉框   选项改变事件响应函数
    函数描述:   显示对应报警输入通道的参数
     *************************************************/
    private void jComboBoxAlarmInChannelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxAlarmInChannelActionPerformed
        if (bInitialed)
        {
            showAlarmInCfg();
            jComboBoxDate.setSelectedIndex(0);//调用函数jComboBoxDateActionPerformed()
            jComboBoxPTZChannel.setSelectedIndex(0);//调用函数jComboBoxPTZChannelActionPerformed()
        }
    }//GEN-LAST:event_jComboBoxAlarmInChannelActionPerformed

    /*************************************************
    函数:      "日期" 下拉框   选项改变事件响应函数
    函数描述:   显示对应星期的时间段参数
     *************************************************/
    private void jComboBoxDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxDateActionPerformed
        int iWeekDay = jComboBoxDate.getSelectedIndex();
        jTextFieldInBeginHour1.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[0].byStartHour + "");
        jTextFieldInBeginMin1.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[0].byStartMin + "");
        jTextFieldInEndHour1.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[0].byStopHour + "");
        jTextFieldInEndMin1.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[0].byStopMin + "");

        jTextFieldInBeginHour2.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[1].byStartHour + "");
        jTextFieldInBeginMin2.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[1].byStartMin + "");
        jTextFieldInEndHour2.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[1].byStopHour + "");
        jTextFieldInEndMin2.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[1].byStopMin + "");

        jTextFieldInBeginHour3.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[2].byStartHour + "");
        jTextFieldInBeginMin3.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[2].byStartMin + "");
        jTextFieldInEndHour3.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[2].byStopHour + "");
        jTextFieldInEndMin3.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[2].byStopMin + "");

        jTextFieldInBeginHour4.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[3].byStartHour + "");
        jTextFieldInBeginMin4.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[3].byStartMin + "");
        jTextFieldInEndHour4.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[3].byStopHour + "");
        jTextFieldInEndMin4.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[3].byStopMin + "");

        jTextFieldInBeginHour5.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[4].byStartHour + "");
        jTextFieldInBeginMin5.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[4].byStartMin + "");
        jTextFieldInEndHour5.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[4].byStopHour + "");
        jTextFieldInEndMin5.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[4].byStopMin + "");

        jTextFieldInBeginHour6.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[5].byStartHour + "");
        jTextFieldInBeginMin6.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[5].byStartMin + "");
        jTextFieldInEndHour6.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[5].byStopHour + "");
        jTextFieldInEndMin6.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[5].byStopMin + "");

        jTextFieldInBeginHour7.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[6].byStartHour + "");
        jTextFieldInBeginMin7.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[6].byStartMin + "");
        jTextFieldInEndHour7.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[6].byStopHour + "");
        jTextFieldInEndMin7.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[6].byStopMin + "");

        jTextFieldInBeginHour8.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[7].byStartHour + "");
        jTextFieldInBeginMin8.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[7].byStartMin + "");
        jTextFieldInEndHour8.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[7].byStopHour + "");
        jTextFieldInEndMin8.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[7].byStopMin + "");
    }//GEN-LAST:event_jComboBoxDateActionPerformed

    /*************************************************
    函数:       PTZ联动  "通道号" 下拉框   选项改变事件响应函数
    函数描述:   选择对应星期的时间段参数
     *************************************************/
    private void jComboBoxPTZChannelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxPTZChannelActionPerformed
        if (bInitialed)
        {
            int iPTZChannel = jComboBoxPTZChannel.getSelectedIndex();
            jRadioButtonCruise.setSelected(m_struAlarmInCfg.byEnableCruise[iPTZChannel] == 1);
            jRadioButtonPreset.setSelected(m_struAlarmInCfg.byEnablePreset[iPTZChannel] == 1);
            jRadioButtonTrack.setSelected(m_struAlarmInCfg.byEnablePtzTrack[iPTZChannel] == 1);
            jComboBoxCruise.setSelectedIndex(m_struAlarmInCfg.byCruiseNo[iPTZChannel]);
            jComboBoxPreset.setSelectedIndex(m_struAlarmInCfg.byPresetNo[iPTZChannel]);
            jComboBoxTrack.setSelectedIndex(m_struAlarmInCfg.byPTZTrack[iPTZChannel]);
        }
    }//GEN-LAST:event_jComboBoxPTZChannelActionPerformed

    /*************************************************
    函数:      报警输入时间段  "确认"  按钮双击响应函数
    函数描述:   保存当日的时间段信息至结构体
     *************************************************/
    private void jButtonConfirmInTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmInTimeActionPerformed
        int iWeekDay = jComboBoxDate.getSelectedIndex();
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[0].byStartHour = (byte) Integer.parseInt(jTextFieldInBeginHour1.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[0].byStartMin = (byte) Integer.parseInt(jTextFieldInBeginMin1.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[0].byStopHour = (byte) Integer.parseInt(jTextFieldInEndHour1.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[0].byStopMin = (byte) Integer.parseInt(jTextFieldInEndMin1.getText());

        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[1].byStartHour = (byte) Integer.parseInt(jTextFieldInBeginHour2.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[1].byStartMin = (byte) Integer.parseInt(jTextFieldInBeginMin2.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[1].byStopHour = (byte) Integer.parseInt(jTextFieldInEndHour2.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[1].byStopMin = (byte) Integer.parseInt(jTextFieldInEndMin2.getText());

        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[2].byStartHour = (byte) Integer.parseInt(jTextFieldInBeginHour3.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[2].byStartMin = (byte) Integer.parseInt(jTextFieldInBeginMin3.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[2].byStopHour = (byte) Integer.parseInt(jTextFieldInEndHour3.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[2].byStopMin = (byte) Integer.parseInt(jTextFieldInEndMin3.getText());

        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[3].byStartHour = (byte) Integer.parseInt(jTextFieldInBeginHour4.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[3].byStartMin = (byte) Integer.parseInt(jTextFieldInBeginMin4.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[3].byStopHour = (byte) Integer.parseInt(jTextFieldInEndHour4.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[3].byStopMin = (byte) Integer.parseInt(jTextFieldInEndMin4.getText());

        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[4].byStartHour = (byte) Integer.parseInt(jTextFieldInBeginHour5.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[4].byStartMin = (byte) Integer.parseInt(jTextFieldInBeginMin5.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[4].byStopHour = (byte) Integer.parseInt(jTextFieldInEndHour5.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[4].byStopMin = (byte) Integer.parseInt(jTextFieldInEndMin5.getText());

        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[5].byStartHour = (byte) Integer.parseInt(jTextFieldInBeginHour6.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[5].byStartMin = (byte) Integer.parseInt(jTextFieldInBeginMin6.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[5].byStopHour = (byte) Integer.parseInt(jTextFieldInEndHour6.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[5].byStopMin = (byte) Integer.parseInt(jTextFieldInEndMin6.getText());

        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[6].byStartHour = (byte) Integer.parseInt(jTextFieldInBeginHour7.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[6].byStartMin = (byte) Integer.parseInt(jTextFieldInBeginMin7.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[6].byStopHour = (byte) Integer.parseInt(jTextFieldInEndHour7.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[6].byStopMin = (byte) Integer.parseInt(jTextFieldInEndMin7.getText());

        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[7].byStartHour = (byte) Integer.parseInt(jTextFieldInBeginHour8.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[7].byStartMin = (byte) Integer.parseInt(jTextFieldInBeginMin8.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[7].byStopHour = (byte) Integer.parseInt(jTextFieldInEndHour8.getText());
        m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[7].byStopMin = (byte) Integer.parseInt(jTextFieldInEndMin8.getText());
    }//GEN-LAST:event_jButtonConfirmInTimeActionPerformed

    /*************************************************
    函数:      报警输入PTZ  "确认"  按钮双击响应函数
    函数描述:   保存PTZ配置信息至结构体
     *************************************************/
    private void jButtonPTZConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPTZConfirmActionPerformed
        int iPTZChannel = jComboBoxPTZChannel.getSelectedIndex();
        m_struAlarmInCfg.byEnableCruise[iPTZChannel] = (byte) ((jRadioButtonCruise.isSelected() == true) ? 1 : 0);
        m_struAlarmInCfg.byEnablePreset[iPTZChannel] = (byte) ((jRadioButtonPreset.isSelected() == true) ? 1 : 0);
        m_struAlarmInCfg.byEnablePtzTrack[iPTZChannel] = (byte) ((jRadioButtonTrack.isSelected() == true) ? 1 : 0);
        m_struAlarmInCfg.byCruiseNo[iPTZChannel] = (byte) jComboBoxCruise.getSelectedIndex();
        m_struAlarmInCfg.byPresetNo[iPTZChannel] = (byte) jComboBoxPreset.getSelectedIndex();
        m_struAlarmInCfg.byPTZTrack[iPTZChannel] = (byte) jComboBoxTrack.getSelectedIndex();
    }//GEN-LAST:event_jButtonPTZConfirmActionPerformed

    /*************************************************
    函数:       PTZ联动  "通道号" 下拉框   选项改变事件响应函数
    函数描述:   选择对应星期的时间段参数
     *************************************************/
    private void jComboBoxAlarmOutChannelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxAlarmOutChannelActionPerformed
        if (bInitialed)
        {
            showAlarmOutCfg();
            jComboBoxWeekDay.setSelectedIndex(0);
        }
    }//GEN-LAST:event_jComboBoxAlarmOutChannelActionPerformed

    /*************************************************
    函数:      "日期" 下拉框   选项改变事件响应函数
    函数描述:   显示对应星期的时间段参数
     *************************************************/
    private void jComboBoxWeekDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxWeekDayActionPerformed
        int iWeekDay = jComboBoxWeekDay.getSelectedIndex();
        jTextFieldsHour1.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[0].byStartHour + "");
        jTextFieldsMin1.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[0].byStartMin + "");
        jTextFieldfHour1.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[0].byStopHour + "");
        jTextFieldfMin1.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[0].byStopMin + "");

        jTextFieldsHour2.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[1].byStartHour + "");
        jTextFieldsMin2.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[1].byStartMin + "");
        jTextFieldfHour2.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[1].byStopHour + "");
        jTextFieldfMin2.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[1].byStopMin + "");

        jTextFieldsHour3.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[2].byStartHour + "");
        jTextFieldsMin3.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[2].byStartMin + "");
        jTextFieldfHour3.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[2].byStopHour + "");
        jTextFieldfMin3.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[2].byStopMin + "");

        jTextFieldsHour4.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[3].byStartHour + "");
        jTextFieldsMin4.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[3].byStartMin + "");
        jTextFieldfHour4.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[3].byStopHour + "");
        jTextFieldfMin4.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[3].byStopMin + "");

        jTextFieldsHour5.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[4].byStartHour + "");
        jTextFieldsMin5.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[4].byStartMin + "");
        jTextFieldfHour5.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[4].byStopHour + "");
        jTextFieldfMin5.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[4].byStopMin + "");

        jTextFieldsHour6.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[5].byStartHour + "");
        jTextFieldsMin6.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[5].byStartMin + "");
        jTextFieldfHour6.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[5].byStopHour + "");
        jTextFieldfMin6.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[5].byStopMin + "");

        jTextFieldsHour7.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[6].byStartHour + "");
        jTextFieldsMin7.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[6].byStartMin + "");
        jTextFieldfHour7.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[6].byStopHour + "");
        jTextFieldfMin7.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[6].byStopMin + "");

        jTextFieldsHour8.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[7].byStartHour + "");
        jTextFieldsMin8.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[7].byStartMin + "");
        jTextFieldfHour8.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[7].byStopHour + "");
        jTextFieldfMin8.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[7].byStopMin + "");

    }//GEN-LAST:event_jComboBoxWeekDayActionPerformed

    /*************************************************
    函数:      报警输出时间段  "确认"  按钮单击响应函数
    函数描述:   保存当日的时间段信息至结构体
     *************************************************/
    private void jButtonConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmActionPerformed
        int iWeekDay = jComboBoxWeekDay.getSelectedIndex();
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[0].byStartHour = (byte) Integer.parseInt(jTextFieldfHour1.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[0].byStartMin = (byte) Integer.parseInt(jTextFieldfMin1.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[0].byStopHour = (byte) Integer.parseInt(jTextFieldsHour1.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[0].byStopMin = (byte) Integer.parseInt(jTextFieldsMin1.getText());

        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[1].byStartHour = (byte) Integer.parseInt(jTextFieldfHour2.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[1].byStartMin = (byte) Integer.parseInt(jTextFieldfMin2.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[1].byStopHour = (byte) Integer.parseInt(jTextFieldsHour2.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[1].byStopMin = (byte) Integer.parseInt(jTextFieldsMin2.getText());

        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[2].byStartHour = (byte) Integer.parseInt(jTextFieldfHour3.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[2].byStartMin = (byte) Integer.parseInt(jTextFieldfMin3.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[2].byStopHour = (byte) Integer.parseInt(jTextFieldsHour3.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[2].byStopMin = (byte) Integer.parseInt(jTextFieldsMin3.getText());

        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[3].byStartHour = (byte) Integer.parseInt(jTextFieldfHour4.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[3].byStartMin = (byte) Integer.parseInt(jTextFieldfMin4.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[3].byStopHour = (byte) Integer.parseInt(jTextFieldsHour4.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[3].byStopMin = (byte) Integer.parseInt(jTextFieldsMin4.getText());

        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[4].byStartHour = (byte) Integer.parseInt(jTextFieldfHour5.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[4].byStartMin = (byte) Integer.parseInt(jTextFieldfMin5.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[4].byStopHour = (byte) Integer.parseInt(jTextFieldsHour5.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[4].byStopMin = (byte) Integer.parseInt(jTextFieldsMin5.getText());

        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[5].byStartHour = (byte) Integer.parseInt(jTextFieldfHour6.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[5].byStartMin = (byte) Integer.parseInt(jTextFieldfMin6.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[5].byStopHour = (byte) Integer.parseInt(jTextFieldsHour6.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[5].byStopMin = (byte) Integer.parseInt(jTextFieldsMin6.getText());

        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[6].byStartHour = (byte) Integer.parseInt(jTextFieldfHour7.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[6].byStartMin = (byte) Integer.parseInt(jTextFieldfMin7.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[6].byStopHour = (byte) Integer.parseInt(jTextFieldsHour7.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[6].byStopMin = (byte) Integer.parseInt(jTextFieldsMin7.getText());

        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[7].byStartHour = (byte) Integer.parseInt(jTextFieldfHour8.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[7].byStartMin = (byte) Integer.parseInt(jTextFieldfMin8.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[7].byStopHour = (byte) Integer.parseInt(jTextFieldsHour8.getText());
        m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[7].byStopMin = (byte) Integer.parseInt(jTextFieldsMin8.getText());

    }//GEN-LAST:event_jButtonConfirmActionPerformed

    /*************************************************
    函数:      报警输出  "设置当前通道"  按钮单击事件响应函数
    函数描述:   调用接口设置报警输出参数
     *************************************************/
    private void jToggleButtonSetupAlarmOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonSetupAlarmOutActionPerformed
        m_struAlarmOutCfg.sAlarmOutName = (jTextFieldAlarmOutName.getText() + "\0").getBytes(); //通道名称
        m_struAlarmOutCfg.dwAlarmOutDelay = (byte) Integer.parseInt(jTextFieldAlarmOutDelay.getText());
        m_struAlarmOutCfg.write();
        Pointer lpConfig = m_struAlarmOutCfg.getPointer();
        boolean setDVRConfigSuc = hCNetSDK.NET_DVR_SetDVRConfig(m_lUserID, HCNetSDK.NET_DVR_SET_ALARMOUTCFG_V30,
                new NativeLong(jComboBoxAlarmOutChannel.getSelectedIndex()), lpConfig, m_struAlarmOutCfg.size());
        m_struAlarmOutCfg.read();
        if (setDVRConfigSuc != true)
        {
            JOptionPane.showMessageDialog(this, "设置失败");
            return;
        } else
        {
            JOptionPane.showMessageDialog(this, "设置成功");
        }
    }//GEN-LAST:event_jToggleButtonSetupAlarmOutActionPerformed

    /*************************************************
    函数:      报警输出  "设置当前报警通道"  按钮单击事件响应函数
    函数描述:   调用接口设置报警输入参数
     *************************************************/
    private void jButtonSetupAlarmInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSetupAlarmInActionPerformed
        m_struAlarmInCfg.sAlarmInName = (jTextFieldAlarmInName.getText() + "\0").getBytes(); //通道名称
        m_struAlarmInCfg.byAlarmInHandle = (byte) ((jCheckBoxAlarmInHandle.isSelected() == true) ? 1 : 0);
        m_struAlarmInCfg.byAlarmType = (byte) jComboBoxAlarmType.getSelectedIndex();

        m_struAlarmInCfg.struAlarmHandleType.dwHandleType = 0;
	 m_struAlarmInCfg.struAlarmHandleType.dwHandleType |= ((jCheckBoxMoniterAlarm.isSelected()?1:0) << 0);
	 m_struAlarmInCfg.struAlarmHandleType.dwHandleType |= ((jCheckBoxAudioAlarm.isSelected()?1:0) << 1);
	 m_struAlarmInCfg.struAlarmHandleType.dwHandleType |= ((jCheckBoxCenter.isSelected()?1:0) << 2);
	 m_struAlarmInCfg.struAlarmHandleType.dwHandleType |= ((jCheckBoxTraggerAlarmOutAlarm.isSelected()?1:0) << 3);
	 m_struAlarmInCfg.struAlarmHandleType.dwHandleType |= ((jCheckBoxEMailAlarm.isSelected()?1:0) << 4);

	for (int i = 0; i < HCNetSDK.MAX_ALARMOUT_V30; i++)
	{
		m_struAlarmInCfg.struAlarmHandleType.byRelAlarmOut[i] = (byte)(m_traggerAlarmOut[i].getCheck()?1:0);
	}

	for (int i = 0; i< HCNetSDK.MAX_CHANNUM_V30; i++)
	{
		m_struAlarmInCfg.byRelRecordChan[i] = (byte)(m_traggerRecord[i].getCheck()?1:0);
	}

        m_struAlarmInCfg.write();
        Pointer lpConfig = m_struAlarmInCfg.getPointer();
        boolean setDVRConfigSuc = hCNetSDK.NET_DVR_SetDVRConfig(m_lUserID, HCNetSDK.NET_DVR_SET_ALARMINCFG_V30,
                new NativeLong(jComboBoxAlarmOutChannel.getSelectedIndex()), lpConfig, m_struAlarmInCfg.size());
        m_struAlarmInCfg.read();
        if (setDVRConfigSuc != true)
        {
            JOptionPane.showMessageDialog(this, "设置失败");
            return;
        } else
        {
            JOptionPane.showMessageDialog(this, "设置成功");
        }
    }//GEN-LAST:event_jButtonSetupAlarmInActionPerformed

    /*************************************************
    函数:      "退出"   按钮单击相应函数
    函数描述:   销毁对话框
     *************************************************/
    private void jButtonExit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExit2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonExit2ActionPerformed

    /*************************************************
    函数:      "退出"   按钮单击相应函数
    函数描述:   销毁对话框
     *************************************************/
    private void jButtonExit3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExit3ActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonExit3ActionPerformed

    /*************************************************
    函数:      "退出"   按钮单击相应函数
    函数描述:   销毁对话框
     *************************************************/
    private void jButtonExit1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonExit1ActionPerformed
    {//GEN-HEADEREND:event_jButtonExit1ActionPerformed
         dispose();
    }//GEN-LAST:event_jButtonExit1ActionPerformed

    /*************************************************
    函数:      showExceptionCfg
    函数描述:   获取异常参数
     *************************************************/
    private void showExceptionCfg()
    {
        IntByReference ibrBytesReturned = new IntByReference(0);
        boolean getDVRConfigSuc = false;
        m_struExceptionInfo = new HCNetSDK.NET_DVR_EXCEPTION_V30();
        m_struExceptionInfo.write();
        Pointer lpConfig = m_struExceptionInfo.getPointer();
        getDVRConfigSuc = hCNetSDK.NET_DVR_GetDVRConfig(m_lUserID, HCNetSDK.NET_DVR_GET_EXCEPTIONCFG_V30,
                new NativeLong(0), lpConfig, m_struExceptionInfo.size(), ibrBytesReturned);
        m_struExceptionInfo.read();
        if (getDVRConfigSuc != true)
        {
            JOptionPane.showMessageDialog(this, "获取异常处理参数失败");
            return;
        }
        jComboBoxExceptionType.setSelectedIndex(0);
    }

    /*************************************************
    函数:      showExceptionTypeCfg
    函数描述:   显示对应类型异常参数
     *************************************************/
    private void showExceptionTypeCfg()
    {
        int iType = jComboBoxExceptionType.getSelectedIndex();

        jCheckBoxMoniter.setSelected((m_struExceptionInfo.struExceptionHandleType[iType].dwHandleType & 0x01) == 1);
        jCheckBoxAudio.setSelected(((m_struExceptionInfo.struExceptionHandleType[iType].dwHandleType >> 1) & 0x01) == 1);
        jCheckBoxCenter.setSelected(((m_struExceptionInfo.struExceptionHandleType[iType].dwHandleType >> 2) & 0x01) == 1);
        jCheckBoxTraggerAlarmOut.setSelected(((m_struExceptionInfo.struExceptionHandleType[iType].dwHandleType >> 3) & 0x01) == 1);
        jCheckBoxEMail.setSelected(((m_struExceptionInfo.struExceptionHandleType[iType].dwHandleType >> 4) & 0x01) == 1);

        for (int i = 0; i < m_strDeviceInfo.byAlarmOutPortNum; i++)
        {
            m_exceptionAlarmOut[i].setCheck(m_struExceptionInfo.struExceptionHandleType[iType].byRelAlarmOut[i] == 1);
        }
        jListExceptionTraggerAlarmOut.repaint();
    }

    /*************************************************
    函数:      showAlarmInCfg
    函数描述:   获取并显示报警输入参数
     *************************************************/
    private void showAlarmInCfg()
    {
        IntByReference ibrBytesReturned = new IntByReference(0);
        m_struAlarmInCfg = new HCNetSDK.NET_DVR_ALARMINCFG_V30();
        m_struAlarmInCfg.write();
        Pointer lpConfig = m_struAlarmInCfg.getPointer();
        boolean getDVRConfigSuc = hCNetSDK.NET_DVR_GetDVRConfig(m_lUserID, HCNetSDK.NET_DVR_GET_ALARMINCFG_V30,
                new NativeLong(jComboBoxAlarmInChannel.getSelectedIndex()), lpConfig, m_struAlarmInCfg.size(), ibrBytesReturned);
        m_struAlarmInCfg.read();
        if (getDVRConfigSuc != true)
        {
            JOptionPane.showMessageDialog(this, "获取报警输入参数失败");
            return;
        }

        jCheckBoxAlarmInHandle.setSelected(m_struAlarmInCfg.byAlarmInHandle == 1);//是否启用报警输入处理

        String[] sName = new String[2];//报警输入名称
        sName = new String(m_struAlarmInCfg.sAlarmInName).split("\0", 2);
        this.jTextFieldAlarmInName.setText(sName[0]);

        jComboBoxAlarmType.setSelectedIndex(m_struAlarmInCfg.byAlarmType);

        //触发报警输出参数,触发类型
        jCheckBoxMoniterAlarm.setSelected((m_struAlarmInCfg.struAlarmHandleType.dwHandleType & 0x01) == 1);
        jCheckBoxAudioAlarm.setSelected(((m_struAlarmInCfg.struAlarmHandleType.dwHandleType >> 1) & 0x01) == 1);
        jCheckBoxCenterAlarm.setSelected(((m_struAlarmInCfg.struAlarmHandleType.dwHandleType >> 2) & 0x01) == 1);
        jCheckBoxTraggerAlarmOutAlarm.setSelected(((m_struAlarmInCfg.struAlarmHandleType.dwHandleType >> 3) & 0x01) == 1);
        jCheckBoxEMailAlarm.setSelected(((m_struAlarmInCfg.struAlarmHandleType.dwHandleType >> 4) & 0x01) == 1);

        //触发报警输出的通道
        for (int i = 0; i < m_strDeviceInfo.byAlarmOutPortNum; i++)
        {
            m_traggerAlarmOut[i].setCheck(m_struAlarmInCfg.struAlarmHandleType.byRelAlarmOut[i] == 1);
        }
        jListTraggerRecord.repaint();

        for (int i = 0; i < m_strDeviceInfo.byChanNum; i++)
        {
            m_traggerRecord[i].setCheck(m_struAlarmInCfg.byRelRecordChan[i] == 1);
        }
        jListTraggerAlarmOut.repaint();
    }

    /*************************************************
    函数:      showAlarmOutCfg
    函数描述:   获取并显示报警输出参数
     *************************************************/
    private void showAlarmOutCfg()
    {
        IntByReference ibrBytesReturned = new IntByReference(0);
        m_struAlarmOutCfg = new HCNetSDK.NET_DVR_ALARMOUTCFG_V30();
        m_struAlarmOutCfg.write();
        Pointer lpConfig = m_struAlarmOutCfg.getPointer();
        boolean getDVRConfigSuc = hCNetSDK.NET_DVR_GetDVRConfig(m_lUserID, HCNetSDK.NET_DVR_GET_ALARMOUTCFG_V30,
                new NativeLong(jComboBoxAlarmOutChannel.getSelectedIndex()), lpConfig, m_struAlarmOutCfg.size(), ibrBytesReturned);
        m_struAlarmOutCfg.read();
        if (getDVRConfigSuc != true)
        {
            JOptionPane.showMessageDialog(this, "获取报警输出参数失败");
            return;
        }
        String[] sName = new String[2];//报警输入名称
        sName = new String(m_struAlarmOutCfg.sAlarmOutName).split("\0", 2);
        this.jTextFieldAlarmOutName.setText(sName[0]);

        jTextFieldAlarmOutDelay.setText(m_struAlarmOutCfg.dwAlarmOutDelay + "");
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonComfirmException;
    private javax.swing.JButton jButtonConfirm;
    private javax.swing.JButton jButtonConfirmInTime;
    private javax.swing.JButton jButtonExit1;
    private javax.swing.JButton jButtonExit2;
    private javax.swing.JButton jButtonExit3;
    private javax.swing.JButton jButtonPTZConfirm;
    private javax.swing.JButton jButtonSetupAlarmIn;
    private javax.swing.JButton jButtonSetupException;
    private javax.swing.JCheckBox jCheckBoxAlarmInHandle;
    private javax.swing.JCheckBox jCheckBoxAudio;
    private javax.swing.JCheckBox jCheckBoxAudioAlarm;
    private javax.swing.JCheckBox jCheckBoxCenter;
    private javax.swing.JCheckBox jCheckBoxCenterAlarm;
    private javax.swing.JCheckBox jCheckBoxEMail;
    private javax.swing.JCheckBox jCheckBoxEMailAlarm;
    private javax.swing.JCheckBox jCheckBoxMoniter;
    private javax.swing.JCheckBox jCheckBoxMoniterAlarm;
    private javax.swing.JCheckBox jCheckBoxTraggerAlarmOut;
    private javax.swing.JCheckBox jCheckBoxTraggerAlarmOutAlarm;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBoxAlarmInChannel;
    private javax.swing.JComboBox jComboBoxAlarmOutChannel;
    private javax.swing.JComboBox jComboBoxAlarmType;
    private javax.swing.JComboBox jComboBoxCruise;
    private javax.swing.JComboBox jComboBoxDate;
    private javax.swing.JComboBox jComboBoxExceptionType;
    private javax.swing.JComboBox jComboBoxPTZChannel;
    private javax.swing.JComboBox jComboBoxPreset;
    private javax.swing.JComboBox jComboBoxTrack;
    private javax.swing.JComboBox jComboBoxWeekDay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JList jListExceptionTraggerAlarmOut;
    private javax.swing.JList jListTraggerAlarmOut;
    private javax.swing.JList jListTraggerRecord;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanelAlarmIn;
    private javax.swing.JPanel jPanelAlarmOut;
    private javax.swing.JPanel jPanelException;
    private javax.swing.JPanel jPanelExceptionTragger;
    private javax.swing.JRadioButton jRadioButtonCruise;
    private javax.swing.JRadioButton jRadioButtonPreset;
    private javax.swing.JRadioButton jRadioButtonTrack;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPaneAlarmCfg;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextFieldAlarmInChannel;
    private javax.swing.JTextField jTextFieldAlarmInName;
    private javax.swing.JTextField jTextFieldAlarmOutDelay;
    private javax.swing.JTextField jTextFieldAlarmOutName;
    private javax.swing.JTextField jTextFieldDeviceAddress;
    private javax.swing.JTextField jTextFieldInBeginHour1;
    private javax.swing.JTextField jTextFieldInBeginHour2;
    private javax.swing.JTextField jTextFieldInBeginHour3;
    private javax.swing.JTextField jTextFieldInBeginHour4;
    private javax.swing.JTextField jTextFieldInBeginHour5;
    private javax.swing.JTextField jTextFieldInBeginHour6;
    private javax.swing.JTextField jTextFieldInBeginHour7;
    private javax.swing.JTextField jTextFieldInBeginHour8;
    private javax.swing.JTextField jTextFieldInBeginMin1;
    private javax.swing.JTextField jTextFieldInBeginMin2;
    private javax.swing.JTextField jTextFieldInBeginMin3;
    private javax.swing.JTextField jTextFieldInBeginMin4;
    private javax.swing.JTextField jTextFieldInBeginMin5;
    private javax.swing.JTextField jTextFieldInBeginMin6;
    private javax.swing.JTextField jTextFieldInBeginMin7;
    private javax.swing.JTextField jTextFieldInBeginMin8;
    private javax.swing.JTextField jTextFieldInEndHour1;
    private javax.swing.JTextField jTextFieldInEndHour2;
    private javax.swing.JTextField jTextFieldInEndHour3;
    private javax.swing.JTextField jTextFieldInEndHour4;
    private javax.swing.JTextField jTextFieldInEndHour5;
    private javax.swing.JTextField jTextFieldInEndHour6;
    private javax.swing.JTextField jTextFieldInEndHour7;
    private javax.swing.JTextField jTextFieldInEndHour8;
    private javax.swing.JTextField jTextFieldInEndMin1;
    private javax.swing.JTextField jTextFieldInEndMin2;
    private javax.swing.JTextField jTextFieldInEndMin3;
    private javax.swing.JTextField jTextFieldInEndMin4;
    private javax.swing.JTextField jTextFieldInEndMin5;
    private javax.swing.JTextField jTextFieldInEndMin6;
    private javax.swing.JTextField jTextFieldInEndMin7;
    private javax.swing.JTextField jTextFieldInEndMin8;
    private javax.swing.JTextField jTextFieldfHour1;
    private javax.swing.JTextField jTextFieldfHour2;
    private javax.swing.JTextField jTextFieldfHour3;
    private javax.swing.JTextField jTextFieldfHour4;
    private javax.swing.JTextField jTextFieldfHour5;
    private javax.swing.JTextField jTextFieldfHour6;
    private javax.swing.JTextField jTextFieldfHour7;
    private javax.swing.JTextField jTextFieldfHour8;
    private javax.swing.JTextField jTextFieldfMin1;
    private javax.swing.JTextField jTextFieldfMin2;
    private javax.swing.JTextField jTextFieldfMin3;
    private javax.swing.JTextField jTextFieldfMin4;
    private javax.swing.JTextField jTextFieldfMin5;
    private javax.swing.JTextField jTextFieldfMin6;
    private javax.swing.JTextField jTextFieldfMin7;
    private javax.swing.JTextField jTextFieldfMin8;
    private javax.swing.JTextField jTextFieldsHour1;
    private javax.swing.JTextField jTextFieldsHour2;
    private javax.swing.JTextField jTextFieldsHour3;
    private javax.swing.JTextField jTextFieldsHour4;
    private javax.swing.JTextField jTextFieldsHour5;
    private javax.swing.JTextField jTextFieldsHour6;
    private javax.swing.JTextField jTextFieldsHour7;
    private javax.swing.JTextField jTextFieldsHour8;
    private javax.swing.JTextField jTextFieldsMin1;
    private javax.swing.JTextField jTextFieldsMin2;
    private javax.swing.JTextField jTextFieldsMin3;
    private javax.swing.JTextField jTextFieldsMin4;
    private javax.swing.JTextField jTextFieldsMin5;
    private javax.swing.JTextField jTextFieldsMin6;
    private javax.swing.JTextField jTextFieldsMin7;
    private javax.swing.JTextField jTextFieldsMin8;
    private javax.swing.JToggleButton jToggleButtonSetupAlarmOut;
    // End of variables declaration//GEN-END:variables

    /******************************************************************************
     *类:   CheckListItemRenderer
     *JCheckBox   ListCellRenderer
     ******************************************************************************/
    public class CheckListItemRenderer extends JCheckBox implements ListCellRenderer
    {

        public Component getListCellRendererComponent(
                JList list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus)
        {
            CheckListItem item = (CheckListItem) value;
            this.setSelected(item.getCheck());
            this.setText(item.getText());
            this.setFont(list.getFont());
            this.setEnabled(list.isEnabled());
            return this;
        }
    }

    /******************************************************************************
     *类:   CheckListItem
     *
     ******************************************************************************/
    public class CheckListItem
    {

        boolean check;
        String text;

        public CheckListItem(boolean check, String text)
        {
            this.check = check;
            this.text = text;
        }

        public boolean getCheck()
        {
            return check;
        }

        public void setCheck(boolean _check)
        {
            check = _check;
        }

        public String getText()
        {
            return text;
        }

        public void setText(String _text)
        {
            text = _text;
        }
    }

    /******************************************************************************
     *类:   CheckListMouseListener
     *
     ******************************************************************************/
    class CheckListMouseListener extends MouseAdapter
    {

        public void mousePressed(MouseEvent e)
        {
            JList list = (JList) e.getSource();
            int index = list.locationToIndex(e.getPoint());
            CheckListItem item = (CheckListItem) list.getModel().getElementAt(index);
            item.setCheck(!item.getCheck());
            Rectangle rect = list.getCellBounds(index, index);
            list.repaint(rect);
        }
    }
}
