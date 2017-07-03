/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JDialogChannelConfig.java
 *
 * Created on 2009-9-14, 19:31:34
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
 *类 ：JDialogChannelConfig
 *类描述 ：通道参数配置
 ****************************************************************************/
public class JDialogChannelConfig extends javax.swing.JDialog
{

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    private HCNetSDK.NET_DVR_PICCFG_V30 m_struPicCfg;//图片参数
    private HCNetSDK.NET_DVR_COMPRESSIONCFG_V30 m_struCompressionCfg;//压缩参数
    private HCNetSDK.NET_DVR_RECORD_V30 m_struRemoteRecCfg;//录像参数
    private HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;//设备信息
    private HCNetSDK.NET_DVR_SHOWSTRING_V30 m_strShowString;//叠加字符参数
    private NativeLong lUserID;//用户ID

    /*************************************************
    函数:      JDialogChannelConfig
    函数描述:	构造函数
     *************************************************/
    public JDialogChannelConfig(java.awt.Frame parent, boolean modal, NativeLong lUserId, HCNetSDK.NET_DVR_DEVICEINFO_V30 strDeviceInfo)
    {
        super(parent, modal);

        lUserID = lUserId;
        m_strDeviceInfo = strDeviceInfo;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelChannelConfig = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelChannelName = new javax.swing.JLabel();
        jComboBoxChannelNumber = new javax.swing.JComboBox();
        jTextFieldChannelName = new javax.swing.JTextField();
        jPanelCompressPara = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBoxCompressType = new javax.swing.JComboBox();
        jComboBoxImageQulity = new javax.swing.JComboBox();
        jComboBoxFrameRate = new javax.swing.JComboBox();
        jComboBoxStreamType = new javax.swing.JComboBox();
        jComboBoxMaxBitRate = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxResolution = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jComboBoxBitRateType = new javax.swing.JComboBox();
        jComboBoxBpInterval = new javax.swing.JComboBox();
        jTextFieldBitRate = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jButtonCompressOK = new javax.swing.JButton();
        jTextFieldIInterval = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jCheckBoxRecord = new javax.swing.JCheckBox();
        jButtonSetRecord = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jComboBoxPreRecordTime = new javax.swing.JComboBox();
        jComboBoxRecordDelay = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldSaveDays = new javax.swing.JTextField();
        jCheckBoxRebundancy = new javax.swing.JCheckBox();
        jCheckBoxAudioRec = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jCheckBoxHideArea = new javax.swing.JCheckBox();
        jCheckBoxSignalLost = new javax.swing.JCheckBox();
        jCheckBoxMotion = new javax.swing.JCheckBox();
        jCheckBoxHideAlarm = new javax.swing.JCheckBox();
        jButtonSetHideArea = new javax.swing.JButton();
        jButtonSetSignalLost = new javax.swing.JButton();
        jButtonSetMotion = new javax.swing.JButton();
        jButtonSetHideAlarm = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jCheckBoxOSD = new javax.swing.JCheckBox();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextFieldOSDX = new javax.swing.JTextField();
        jTextFieldOSDY = new javax.swing.JTextField();
        jCheckBoxOSDDate = new javax.swing.JCheckBox();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jComboBoxOSDFormat = new javax.swing.JComboBox();
        jComboBoxDateFormat = new javax.swing.JComboBox();
        jComboBoxTimeFormat = new javax.swing.JComboBox();
        jSeparator2 = new javax.swing.JSeparator();
        jCheckBoxChannelName = new javax.swing.JCheckBox();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTextFieldChannekNameX = new javax.swing.JTextField();
        jTextFieldChannelNameY = new javax.swing.JTextField();
        jButtonString = new javax.swing.JButton();
        jButtonSetChanConfig = new javax.swing.JButton();
        jButtonExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("通道参数设置");
        setBounds(new java.awt.Rectangle(0, 0, 600, 800));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFont(new java.awt.Font("宋体", 0, 10));
        getContentPane().setLayout(null);

        jPanelChannelConfig.setBorder(javax.swing.BorderFactory.createTitledBorder("通道配置信息"));

        jLabel1.setText("通道号");

        jLabelChannelName.setText("通道名称");

        jComboBoxChannelNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxChannelNumberActionPerformed(evt);
            }
        });

        jTextFieldChannelName.setText("0");

        jPanelCompressPara.setBorder(javax.swing.BorderFactory.createTitledBorder("压缩参数配置"));

        jLabel2.setText("压缩参数类型");

        jLabel3.setText("图像质量");

        jLabel4.setText("视频帧率");

        jLabel5.setText("码流类型");

        jLabel6.setText("位率上限");

        jComboBoxCompressType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "录像(主码流)", "网传(子码流)", "事件触发" }));
        jComboBoxCompressType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCompressTypeActionPerformed(evt);
            }
        });

        jComboBoxImageQulity.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "最好", "次好", "较好", "一般", "较差", "差" }));

        jComboBoxFrameRate.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "全部", "1/16", "1/8", "1/4", "1/2", "1", "2", "4", "6", "8", "10", "12", "16", "20", "15", "18", "22" }));

        jComboBoxStreamType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "视频流", "复合流" }));

        jComboBoxMaxBitRate.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "32K", "48K", "64K", "80K", "96K", "128K", "160K", "192K", "224k", "256K", "320K", "384K", "448K", "512K", "640K", "768K", "896K", "1024K", "1280K", "1536K", "1792K", "2048K", "自定义" }));

        jLabel17.setText("分辨率");

        jLabel8.setText("I帧间隔");

        jComboBoxResolution.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DCIF", "CIF", "QCIF", "4CIF", "2CIF", "VGA", "UXGA", "SVGA", "HD720p", "SXGA" }));

        jLabel7.setText("位率类型");

        jLabel9.setText("BP帧间隔");

        jComboBoxBitRateType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "定码率", "变码率" }));

        jComboBoxBpInterval.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "BBP帧", "BP帧", "单P帧" }));

        jTextFieldBitRate.setText("0");

        jLabel10.setText("kbps");

        jButtonCompressOK.setText("保存压缩参数");
        jButtonCompressOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCompressOKActionPerformed(evt);
            }
        });

        jTextFieldIInterval.setText("0");

        javax.swing.GroupLayout jPanelCompressParaLayout = new javax.swing.GroupLayout(jPanelCompressPara);
        jPanelCompressPara.setLayout(jPanelCompressParaLayout);
        jPanelCompressParaLayout.setHorizontalGroup(
            jPanelCompressParaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCompressParaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelCompressParaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCompressParaLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxCompressType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelCompressParaLayout.createSequentialGroup()
                        .addGroup(jPanelCompressParaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelCompressParaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jComboBoxFrameRate, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxImageQulity, 0, 64, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelCompressParaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelCompressParaLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBoxStreamType, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelCompressParaLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addGroup(jPanelCompressParaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBoxMaxBitRate, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextFieldBitRate))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel10)))
                        .addGap(24, 24, 24)
                        .addGroup(jPanelCompressParaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel8))
                        .addGroup(jPanelCompressParaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelCompressParaLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jComboBoxResolution, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelCompressParaLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldIInterval, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(25, 25, 25)
                        .addGroup(jPanelCompressParaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanelCompressParaLayout.createSequentialGroup()
                                .addGroup(jPanelCompressParaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelCompressParaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBoxBitRateType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBoxBpInterval, 0, 67, Short.MAX_VALUE)))
                            .addComponent(jButtonCompressOK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
        );
        jPanelCompressParaLayout.setVerticalGroup(
            jPanelCompressParaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCompressParaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelCompressParaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBoxCompressType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelCompressParaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jComboBoxImageQulity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxStreamType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxBitRateType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jComboBoxResolution, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelCompressParaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jComboBoxFrameRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxMaxBitRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxBpInterval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldIInterval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCompressParaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldBitRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCompressOK)
                    .addComponent(jLabel10)))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("录像参数"));

        jCheckBoxRecord.setText("定时录像");

        jButtonSetRecord.setText("设置");
        jButtonSetRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetRecordActionPerformed(evt);
            }
        });

        jLabel11.setText("预录时间");

        jLabel12.setText("录像延时");

        jComboBoxPreRecordTime.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "不预录", "5秒", "10秒", "15秒", "20秒", "25秒", "30秒", "不受限制" }));

        jComboBoxRecordDelay.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5秒", "10秒", "30秒", "1分", "2分", "5分", "10分" }));

        jLabel13.setText("保存时间");

        jLabel14.setText("天");

        jTextFieldSaveDays.setText("0");

        jCheckBoxRebundancy.setText("冗余录像");

        jCheckBoxAudioRec.setText("记录音频");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSaveDays, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)
                        .addGap(54, 54, 54)
                        .addComponent(jCheckBoxRebundancy)
                        .addGap(109, 109, 109)
                        .addComponent(jCheckBoxAudioRec))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBoxRecord)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonSetRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxPreRecordTime, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxRecordDelay, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxRecord)
                    .addComponent(jButtonSetRecord)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jComboBoxPreRecordTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxRecordDelay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextFieldSaveDays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxRebundancy)
                    .addComponent(jCheckBoxAudioRec)
                    .addComponent(jLabel14)))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("图像参数配置"));

        jCheckBoxHideArea.setText("遮盖");
        jCheckBoxHideArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxHideAreaActionPerformed(evt);
            }
        });

        jCheckBoxSignalLost.setText("视频信号丢失报警");
        jCheckBoxSignalLost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxSignalLostActionPerformed(evt);
            }
        });

        jCheckBoxMotion.setText("移动侦测");
        jCheckBoxMotion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMotionActionPerformed(evt);
            }
        });

        jCheckBoxHideAlarm.setText("遮挡报警");
        jCheckBoxHideAlarm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxHideAlarmActionPerformed(evt);
            }
        });

        jButtonSetHideArea.setText("设置");
        jButtonSetHideArea.setEnabled(false);
        jButtonSetHideArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetHideAreaActionPerformed(evt);
            }
        });

        jButtonSetSignalLost.setText("设置");
        jButtonSetSignalLost.setEnabled(false);
        jButtonSetSignalLost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetSignalLostActionPerformed(evt);
            }
        });

        jButtonSetMotion.setText("设置");
        jButtonSetMotion.setEnabled(false);
        jButtonSetMotion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetMotionActionPerformed(evt);
            }
        });

        jButtonSetHideAlarm.setText("设置");
        jButtonSetHideAlarm.setEnabled(false);
        jButtonSetHideAlarm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetHideAlarmActionPerformed(evt);
            }
        });

        jCheckBoxOSD.setText("显示osd");

        jLabel15.setText("Y坐标");

        jLabel16.setText("X坐标");

        jTextFieldOSDX.setText("0");

        jTextFieldOSDY.setText("0");

        jCheckBoxOSDDate.setText("显示日期");

        jLabel18.setText("时间格式");

        jLabel19.setText("OSD属性");

        jLabel20.setText("日期格式");

        jComboBoxOSDFormat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "透明 不闪烁", "不透明 闪烁", "不透明 不闪烁" }));

        jComboBoxDateFormat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "XXXX-XX-XX (年月日)", "XX-XX-XXXX (月日年)", "XXXX年XX月XX日", "XX月XX日XXXX年", "XX-XX-XXXX 日月年", "XX日XX月XXXX年" }));

        jComboBoxTimeFormat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24小时制", "12小时制" }));

        jCheckBoxChannelName.setText("通道名");

        jLabel21.setText("X坐标");

        jLabel22.setText("Y坐标");

        jTextFieldChannekNameX.setText("0");

        jTextFieldChannelNameY.setText("0");

        jButtonString.setText("叠加字符");
        jButtonString.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStringActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
                        .addGap(97, 97, 97))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBoxHideArea)
                            .addComponent(jCheckBoxSignalLost))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonSetSignalLost)
                            .addComponent(jButtonSetHideArea))
                        .addGap(52, 52, 52)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jCheckBoxMotion)
                            .addComponent(jCheckBoxHideAlarm))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButtonSetMotion)
                            .addComponent(jButtonSetHideAlarm)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jCheckBoxOSD)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel16)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldOSDY, 0, 0, Short.MAX_VALUE)
                            .addComponent(jTextFieldOSDX, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jCheckBoxOSDDate))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel18)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBoxTimeFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jComboBoxOSDFormat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxDateFormat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(87, 87, 87))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jCheckBoxChannelName)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel21)
                        .addGap(15, 15, 15)
                        .addComponent(jTextFieldChannekNameX, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel22)
                        .addGap(31, 31, 31)
                        .addComponent(jTextFieldChannelNameY, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonString)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 157, Short.MAX_VALUE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxMotion)
                    .addComponent(jCheckBoxHideArea)
                    .addComponent(jButtonSetMotion)
                    .addComponent(jButtonSetHideArea))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jCheckBoxHideAlarm)
                        .addComponent(jButtonSetHideAlarm)
                        .addComponent(jButtonSetSignalLost))
                    .addComponent(jCheckBoxSignalLost))
                .addGap(8, 8, 8)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jCheckBoxOSD)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(jTextFieldOSDX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCheckBoxOSDDate)
                        .addComponent(jComboBoxOSDFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextFieldOSDY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxDateFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxTimeFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel20))
                .addGap(12, 12, 12)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextFieldChannekNameX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonString)
                    .addComponent(jLabel22)
                    .addComponent(jTextFieldChannelNameY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxChannelName))
                .addGap(49, 49, 49))
        );

        javax.swing.GroupLayout jPanelChannelConfigLayout = new javax.swing.GroupLayout(jPanelChannelConfig);
        jPanelChannelConfig.setLayout(jPanelChannelConfigLayout);
        jPanelChannelConfigLayout.setHorizontalGroup(
            jPanelChannelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelChannelConfigLayout.createSequentialGroup()
                .addGroup(jPanelChannelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelChannelConfigLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel1)
                        .addGap(44, 44, 44)
                        .addComponent(jComboBoxChannelNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(jLabelChannelName)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldChannelName, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelChannelConfigLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelChannelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanelCompressPara, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, 0, 610, Short.MAX_VALUE))))
                .addGap(114, 114, 114))
        );
        jPanelChannelConfigLayout.setVerticalGroup(
            jPanelChannelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelChannelConfigLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelChannelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxChannelNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldChannelName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelChannelName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelCompressPara, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanelChannelConfig);
        jPanelChannelConfig.setBounds(10, 10, 640, 550);

        jButtonSetChanConfig.setText("设置当前通道参数");
        jButtonSetChanConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetChanConfigActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonSetChanConfig);
        jButtonSetChanConfig.setBounds(30, 570, 160, 23);

        jButtonExit.setText("退出");
        jButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExitActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonExit);
        jButtonExit.setBounds(470, 570, 90, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*************************************************
    函数:      " 保存压缩参数"  按钮响应函数
    函数描述:	保存压缩参数到结构体
     *************************************************/
    private void jButtonCompressOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCompressOKActionPerformed
        int iCompressType = jComboBoxCompressType.getSelectedIndex();
        int dwDefBitRate = 0;
        switch (iCompressType)
        {
            case 0:
                m_struCompressionCfg.struNormHighRecordPara.byPicQuality = (byte) jComboBoxImageQulity.getSelectedIndex();
                m_struCompressionCfg.struNormHighRecordPara.byStreamType = (byte) jComboBoxStreamType.getSelectedIndex();
                m_struCompressionCfg.struNormHighRecordPara.byResolution = (byte) jComboBoxResolution.getSelectedIndex();
                m_struCompressionCfg.struNormHighRecordPara.byBitrateType = (byte) jComboBoxBitRateType.getSelectedIndex();
                m_struCompressionCfg.struNormHighRecordPara.dwVideoFrameRate = (byte) jComboBoxFrameRate.getSelectedIndex();
                if (jComboBoxMaxBitRate.getSelectedIndex() == 22)//位率上限
                {

                    dwDefBitRate = Integer.parseInt(jTextFieldBitRate.getText()) * 1024;
                    if (dwDefBitRate < 32 * 1024)
                    {
                        dwDefBitRate = 32 * 1024;
                    }
                    if (dwDefBitRate > 8192 * 1024)
                    {
                        dwDefBitRate = 8192 * 1024;
                    }
                    dwDefBitRate |= 0x80000000;
                    m_struCompressionCfg.struNormHighRecordPara.dwVideoBitrate = dwDefBitRate;
                } else
                {
                    m_struCompressionCfg.struNormHighRecordPara.dwVideoBitrate = jComboBoxMaxBitRate.getSelectedIndex() + 2;
                }
                m_struCompressionCfg.struNormHighRecordPara.wIntervalFrameI = (short) Integer.parseInt(jTextFieldIInterval.getText());
                m_struCompressionCfg.struNormHighRecordPara.byIntervalBPFrame = (byte) jComboBoxBpInterval.getSelectedIndex();
                break;

            case 1:
                m_struCompressionCfg.struNetPara.byPicQuality = (byte) jComboBoxImageQulity.getSelectedIndex();
                m_struCompressionCfg.struNetPara.byStreamType = (byte) jComboBoxStreamType.getSelectedIndex();
                m_struCompressionCfg.struNetPara.byResolution = (byte) jComboBoxResolution.getSelectedIndex();
                m_struCompressionCfg.struNetPara.byBitrateType = (byte) jComboBoxBitRateType.getSelectedIndex();
                m_struCompressionCfg.struNetPara.dwVideoFrameRate = (byte) jComboBoxFrameRate.getSelectedIndex();
                if (jComboBoxMaxBitRate.getSelectedIndex() == 22)//位率上限
                {
                    dwDefBitRate = Integer.parseInt(jTextFieldBitRate.getText()) * 1024;
                    if (dwDefBitRate < 32 * 1024)
                    {
                        dwDefBitRate = 32 * 1024;
                    }
                    if (dwDefBitRate > 8192 * 1024)
                    {
                        dwDefBitRate = 8192 * 1024;
                    }
                    dwDefBitRate |= 0x80000000;
                    m_struCompressionCfg.struNetPara.dwVideoBitrate = dwDefBitRate;
                } else
                {
                    m_struCompressionCfg.struNetPara.dwVideoBitrate = jComboBoxMaxBitRate.getSelectedIndex() + 2;
                }
                m_struCompressionCfg.struNetPara.wIntervalFrameI = (short) Integer.parseInt(jTextFieldIInterval.getText());
                m_struCompressionCfg.struNetPara.byIntervalBPFrame = (byte) jComboBoxBpInterval.getSelectedIndex();
                break;

            case 2:
                m_struCompressionCfg.struEventRecordPara.byPicQuality = (byte) jComboBoxImageQulity.getSelectedIndex();
                m_struCompressionCfg.struEventRecordPara.byStreamType = (byte) jComboBoxStreamType.getSelectedIndex();
                m_struCompressionCfg.struEventRecordPara.byResolution = (byte) jComboBoxResolution.getSelectedIndex();
                m_struCompressionCfg.struEventRecordPara.byBitrateType = (byte) jComboBoxBitRateType.getSelectedIndex();
                m_struCompressionCfg.struEventRecordPara.dwVideoFrameRate = (byte) jComboBoxFrameRate.getSelectedIndex();
                if (jComboBoxMaxBitRate.getSelectedIndex() == 22)//位率上限
                {
                    dwDefBitRate = Integer.parseInt(jTextFieldBitRate.getText()) * 1024;
                    if (dwDefBitRate < 32 * 1024)
                    {
                        dwDefBitRate = 32 * 1024;
                    }
                    if (dwDefBitRate > 8192 * 1024)
                    {
                        dwDefBitRate = 8192 * 1024;
                    }
                    dwDefBitRate |= 0x80000000;
                    m_struCompressionCfg.struEventRecordPara.dwVideoBitrate = dwDefBitRate;
                } else
                {
                    m_struCompressionCfg.struEventRecordPara.dwVideoBitrate = jComboBoxMaxBitRate.getSelectedIndex() + 2;
                }
                m_struCompressionCfg.struEventRecordPara.wIntervalFrameI = (short) Integer.parseInt(jTextFieldIInterval.getText());
                m_struCompressionCfg.struEventRecordPara.byIntervalBPFrame = (byte) jComboBoxBpInterval.getSelectedIndex();
                break;
        }
    }//GEN-LAST:event_jButtonCompressOKActionPerformed

    /*************************************************
    函数:     遮盖 "设置 "  按钮响应函数
    函数描述:	设置遮盖报警参数
     *************************************************/
    private void jButtonSetHideAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSetHideAreaActionPerformed
        int iChannelNumber = -1;
        int iIndex = jComboBoxChannelNumber.getSelectedIndex();
        if((iIndex < m_strDeviceInfo.byChanNum) && (iIndex >=0))
        {
            iChannelNumber = iIndex + m_strDeviceInfo.byStartChan;
        }
        else
        {
            iChannelNumber = 32 + (iIndex - m_strDeviceInfo.byChanNum) + m_strDeviceInfo.byStartChan;
        }
        
        JDialogHideArea dlg = new JDialogHideArea(this, false, lUserID, iChannelNumber, m_struPicCfg);
        ClientDemo.centerWindow(dlg);
        dlg.setVisible(true);
    }//GEN-LAST:event_jButtonSetHideAreaActionPerformed

    /*************************************************
    函数:      信号丢失 "设置 "  按钮响应函数
    函数描述:  设置信号丢失报警参数
     *************************************************/
    private void jButtonSetSignalLostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSetSignalLostActionPerformed
        JDialogVideoInLost dlg  = new JDialogVideoInLost(this, false, m_struPicCfg, m_strDeviceInfo);
        ClientDemo.centerWindow(dlg);
        dlg.setVisible(true);
    }//GEN-LAST:event_jButtonSetSignalLostActionPerformed

    /*************************************************
    函数:           移动侦测 "设置 "  按钮响应函数
    函数描述:	设置移动侦测报警参数
     *************************************************/
    private void jButtonSetMotionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSetMotionActionPerformed
        int iChannelNumber = -1;
        int iIndex = jComboBoxChannelNumber.getSelectedIndex();
        if((iIndex < m_strDeviceInfo.byChanNum) && (iIndex >=0))
        {
            iChannelNumber = iIndex + m_strDeviceInfo.byStartChan;
        }
        else
        {
            iChannelNumber = 32 + (iIndex - m_strDeviceInfo.byChanNum) + m_strDeviceInfo.byStartChan;
        }
        
        JDialogMotionDetect dlg = new JDialogMotionDetect(this, false, lUserID, iChannelNumber, m_struPicCfg, m_strDeviceInfo);
        ClientDemo.centerWindow(dlg);
        dlg.setVisible(true);
    }//GEN-LAST:event_jButtonSetMotionActionPerformed

    /*************************************************
    函数:           遮挡 "设置 "  按钮响应函数
    函数描述:	设置遮挡报警参数
     *************************************************/
    private void jButtonSetHideAlarmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSetHideAlarmActionPerformed
          int iChannelNumber = -1;
        int iIndex = jComboBoxChannelNumber.getSelectedIndex();
        if((iIndex < m_strDeviceInfo.byChanNum) && (iIndex >=0))
        {
            iChannelNumber = iIndex + m_strDeviceInfo.byStartChan;
        }
        else
        {
            iChannelNumber = 32 + (iIndex - m_strDeviceInfo.byChanNum) + m_strDeviceInfo.byStartChan;
        }

        JDialogHideAlarm dlg = new JDialogHideAlarm(this, false, lUserID, iChannelNumber, m_struPicCfg, m_strDeviceInfo);
        ClientDemo.centerWindow(dlg);
        dlg.setVisible(true);
    }//GEN-LAST:event_jButtonSetHideAlarmActionPerformed

    /*************************************************
    函数:     "叠加字符 "  按钮响应函数
    函数描述:	打开叠加字符设置对话框,设置叠加字符参数
     *************************************************/
    private void jButtonStringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStringActionPerformed
        int iChannelNumber = this.jComboBoxChannelNumber.getSelectedIndex() + m_strDeviceInfo.byStartChan;
        m_strShowString = new HCNetSDK.NET_DVR_SHOWSTRING_V30();
        IntByReference ibrBytesReturned = new IntByReference(0);//获取显示字符参数
        m_strShowString.write();
        Pointer lpStringConfig = m_strShowString.getPointer();
        boolean getDVRConfigSuc = hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_SHOWSTRING_V30,
                new NativeLong(iChannelNumber), lpStringConfig, m_strShowString.size(), ibrBytesReturned);
        m_strShowString.read();
        if (getDVRConfigSuc != true)
        {
            JOptionPane.showMessageDialog(this, "获取显示字符参数失败");
        }
        JDialogShowString dialogShowString = new JDialogShowString(this, true, m_strShowString, lUserID, iChannelNumber);//有模式对话框
        dialogShowString.setSize( 330, 300);
        ClientDemo.centerWindow(dialogShowString);
        dialogShowString.jComboBoxShowArea.setSelectedIndex(0);
        dialogShowString.showStringConfig();
        dialogShowString.setVisible(true);
    }//GEN-LAST:event_jButtonStringActionPerformed

    /*************************************************
    函数:           "设置当前通道参数 "  按钮响应函数
    函数描述:	设置当前通道参数
     *************************************************/
    private void jButtonSetChanConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSetChanConfigActionPerformed
        int iChannelNumber = -1;
        int iIndex = jComboBoxChannelNumber.getSelectedIndex();
        if((iIndex < m_strDeviceInfo.byChanNum) && (iIndex >=0))
        {
            iChannelNumber = iIndex + m_strDeviceInfo.byStartChan;
        }
        else
        {
            iChannelNumber = 32 + (iIndex - m_strDeviceInfo.byChanNum) + m_strDeviceInfo.byStartChan;
        }
        //参数赋值
        m_struPicCfg.sChanName = (jTextFieldChannelName.getText() + "\0").getBytes(); //通道名称

        //压缩参数在压缩参数栏中保存

        //录像参数
        m_struRemoteRecCfg.dwRecord = (jCheckBoxRecord.isSelected() == true) ? 1 : 0;
        m_struRemoteRecCfg.dwPreRecordTime = jComboBoxPreRecordTime.getSelectedIndex();
        m_struRemoteRecCfg.dwRecordTime = jComboBoxRecordDelay.getSelectedIndex();
        m_struRemoteRecCfg.dwRecorderDuration = Integer.parseInt(jTextFieldSaveDays.getText());
        m_struRemoteRecCfg.byRedundancyRec = (byte) ((jCheckBoxRebundancy.isSelected() == true) ? 1 : 0);
        m_struRemoteRecCfg.byAudioRec = (byte) ((jCheckBoxAudioRec.isSelected() == true) ? 1 : 0);

        //图像参数
        m_struPicCfg.dwEnableHide = (jCheckBoxHideArea.isSelected() == true) ? 1 : 0;
        m_struPicCfg.struMotion.byEnableHandleMotion = (byte) ((jCheckBoxMotion.isSelected() == true) ? 1 : 0);
        m_struPicCfg.struVILost.byEnableHandleVILost = (byte) ((jCheckBoxSignalLost.isSelected() == true) ? 1 : 0);
        m_struPicCfg.struHideAlarm.dwEnableHideAlarm = (jCheckBoxHideAlarm.isSelected() == true) ? 1 : 0;
        m_struPicCfg.dwShowOsd = (jCheckBoxOSD.isSelected() == true) ? 1 : 0;
        m_struPicCfg.wOSDTopLeftX = (short) Integer.parseInt(jTextFieldOSDX.getText());
        m_struPicCfg.wOSDTopLeftY = (short) Integer.parseInt(jTextFieldOSDY.getText());
        m_struPicCfg.byDispWeek = (byte) ((jCheckBoxOSDDate.isSelected() == true) ? 1 : 0);

        m_struPicCfg.byHourOSDType = (byte) jComboBoxTimeFormat.getSelectedIndex();
        m_struPicCfg.byOSDAttrib = (byte) (jComboBoxOSDFormat.getSelectedIndex() + 2);
        m_struPicCfg.byOSDType = (byte) jComboBoxDateFormat.getSelectedIndex();

        m_struPicCfg.dwShowChanName = (jCheckBoxChannelName.isSelected() == true) ? 1 : 0;
        m_struPicCfg.wShowNameTopLeftX = (short) Integer.parseInt(jTextFieldChannekNameX.getText());
        m_struPicCfg.wShowNameTopLeftY = (short) Integer.parseInt(jTextFieldChannelNameY.getText());

        m_struPicCfg.write();
        Pointer lpPicConfig = m_struPicCfg.getPointer();
        boolean setDVRConfigSuc = hCNetSDK.NET_DVR_SetDVRConfig(lUserID, HCNetSDK.NET_DVR_SET_PICCFG_V30,
                new NativeLong(iChannelNumber), lpPicConfig, m_struPicCfg.size());
        m_struPicCfg.read();
        if (setDVRConfigSuc != true)
        {
            JOptionPane.showMessageDialog(this, "设置图片参数失败");
            return;
        }

        setDVRConfigSuc = false;
        m_struCompressionCfg.write();
        lpPicConfig = m_struCompressionCfg.getPointer();
        setDVRConfigSuc = hCNetSDK.NET_DVR_SetDVRConfig(lUserID, HCNetSDK.NET_DVR_SET_COMPRESSCFG_V30,
                new NativeLong(iChannelNumber), lpPicConfig, m_struCompressionCfg.size());
        m_struCompressionCfg.read();
        if (setDVRConfigSuc != true)
        {
            JOptionPane.showMessageDialog(this, "设置压缩参数失败");
            System.out.println(hCNetSDK.NET_DVR_GetLastError());
            return;
        }

        setDVRConfigSuc = false;
        m_struRemoteRecCfg.write();
        lpPicConfig = m_struRemoteRecCfg.getPointer();
        setDVRConfigSuc = hCNetSDK.NET_DVR_SetDVRConfig(lUserID, HCNetSDK.NET_DVR_SET_RECORDCFG_V30,
                new NativeLong(iChannelNumber), lpPicConfig, m_struRemoteRecCfg.size());
        m_struRemoteRecCfg.read();
        if (setDVRConfigSuc != true)
        {
            System.out.println(hCNetSDK.NET_DVR_GetLastError());
            JOptionPane.showMessageDialog(this, "设置录像参数失败");
            return;
        }
        JOptionPane.showMessageDialog(this, "设置通道参数成功");
    }//GEN-LAST:event_jButtonSetChanConfigActionPerformed

    /*************************************************
    函数:     "退出 "  按钮响应函数
    函数描述:	销毁对话框
     *************************************************/
    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButtonExitActionPerformed

    /*************************************************
    函数:     "通道号 "组合框  选项改变事件响应函数
    函数描述:	获取并显示通道号中通道数对应的通道参数
     *************************************************/
    private void jComboBoxChannelNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxChannelNumberActionPerformed
        showChannelConfig();
    }//GEN-LAST:event_jComboBoxChannelNumberActionPerformed

    /*************************************************
    函数:     "压缩参数类型 "组合框  选项改变事件响应函数
    函数描述:	获取并显示压缩参数类型对应的参数
     *************************************************/
    private void jComboBoxCompressTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCompressTypeActionPerformed
        int iCompressType = jComboBoxCompressType.getSelectedIndex();
        switch (iCompressType)
        {
            case 0:
                jComboBoxImageQulity.setSelectedIndex(m_struCompressionCfg.struNormHighRecordPara.byPicQuality);
                jComboBoxStreamType.setSelectedIndex(m_struCompressionCfg.struNormHighRecordPara.byStreamType);
                jComboBoxResolution.setSelectedIndex(m_struCompressionCfg.struNormHighRecordPara.byResolution);
                jComboBoxBitRateType.setSelectedIndex(m_struCompressionCfg.struNormHighRecordPara.byBitrateType);
                jComboBoxFrameRate.setSelectedIndex(m_struCompressionCfg.struNormHighRecordPara.dwVideoFrameRate);
                if (((m_struCompressionCfg.struNormHighRecordPara.dwVideoBitrate >> 31) & 0x01) == 1)//位率上限
                {
                    jComboBoxMaxBitRate.setSelectedIndex(22);
                    jTextFieldBitRate.setText((m_struCompressionCfg.struNormHighRecordPara.dwVideoBitrate & 0x7fffffff) / 1024 + "");
                } else
                {
                    jComboBoxMaxBitRate.setSelectedIndex(m_struCompressionCfg.struNormHighRecordPara.dwVideoBitrate - 2);
                    jTextFieldBitRate.setText("");
                }
                jTextFieldIInterval.setText(m_struCompressionCfg.struNormHighRecordPara.wIntervalFrameI + "");
                jComboBoxBpInterval.setSelectedIndex(m_struCompressionCfg.struNormHighRecordPara.byIntervalBPFrame);
                break;

            case 1:
                jComboBoxImageQulity.setSelectedIndex(m_struCompressionCfg.struNetPara.byPicQuality);
                jComboBoxStreamType.setSelectedIndex(m_struCompressionCfg.struNetPara.byStreamType);
                jComboBoxResolution.setSelectedIndex(m_struCompressionCfg.struNetPara.byResolution);
                jComboBoxBitRateType.setSelectedIndex(m_struCompressionCfg.struNetPara.byBitrateType);
                jComboBoxFrameRate.setSelectedIndex(m_struCompressionCfg.struNetPara.dwVideoFrameRate);
                if (((m_struCompressionCfg.struNetPara.dwVideoBitrate >> 31) & 0x01) == 1)//位率上限
                {
                    jComboBoxMaxBitRate.setSelectedIndex(22);
                    jTextFieldBitRate.setText((m_struCompressionCfg.struNetPara.dwVideoBitrate & 0x7fffffff) / 1024 + "");
                } else
                {
                    jComboBoxMaxBitRate.setSelectedIndex(m_struCompressionCfg.struNetPara.dwVideoBitrate - 2);
                    jTextFieldBitRate.setText("");
                }
                jTextFieldIInterval.setText(m_struCompressionCfg.struNetPara.wIntervalFrameI + "");
                jComboBoxBpInterval.setSelectedIndex(m_struCompressionCfg.struNetPara.byIntervalBPFrame);
                break;

            case 2:
                jComboBoxImageQulity.setSelectedIndex(m_struCompressionCfg.struEventRecordPara.byPicQuality);
                jComboBoxStreamType.setSelectedIndex(m_struCompressionCfg.struEventRecordPara.byStreamType);
                jComboBoxResolution.setSelectedIndex(m_struCompressionCfg.struEventRecordPara.byResolution);
                jComboBoxBitRateType.setSelectedIndex(m_struCompressionCfg.struEventRecordPara.byBitrateType);
                jComboBoxFrameRate.setSelectedIndex(m_struCompressionCfg.struEventRecordPara.dwVideoFrameRate);
                if (((m_struCompressionCfg.struEventRecordPara.dwVideoBitrate >> 31) & 0x01) == 1)//位率上限
                {
                    jComboBoxMaxBitRate.setSelectedIndex(22);
                    jTextFieldBitRate.setText((m_struCompressionCfg.struEventRecordPara.dwVideoBitrate & 0x7fffffff) / 1024 + "");
                } else
                {
                    jComboBoxMaxBitRate.setSelectedIndex(m_struCompressionCfg.struEventRecordPara.dwVideoBitrate - 2);
                    jTextFieldBitRate.setText("");
                }
                jTextFieldIInterval.setText(m_struCompressionCfg.struEventRecordPara.wIntervalFrameI + "");
                jComboBoxBpInterval.setSelectedIndex(m_struCompressionCfg.struEventRecordPara.byIntervalBPFrame);
                break;
        }
    }//GEN-LAST:event_jComboBoxCompressTypeActionPerformed

    /*************************************************
    函数:       定时录像 "设置"  按钮单击响应函数
    函数描述:   弹出对话框显示和设置录像参数
     *************************************************/
    private void jButtonSetRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSetRecordActionPerformed
        JDialogRecordSchedule dialogRecordSchedule = new JDialogRecordSchedule(this, true, m_struRemoteRecCfg);//有模式对话框
        dialogRecordSchedule.setSize( 490, 435);
        ClientDemo.centerWindow(dialogRecordSchedule);
        dialogRecordSchedule.jComboBoxWeekDay.setSelectedIndex(0);

        dialogRecordSchedule.showRecordSchedule();
        dialogRecordSchedule.setVisible(true);
    }//GEN-LAST:event_jButtonSetRecordActionPerformed

     /*************************************************
    函数:       "遮盖"  checkbox  选项改变事件响应函数
    函数描述:   设置按钮enable属性
     *************************************************/
    private void jCheckBoxHideAreaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxHideAreaActionPerformed
    {//GEN-HEADEREND:event_jCheckBoxHideAreaActionPerformed
        jButtonSetHideArea.setEnabled(jCheckBoxHideArea.isSelected());
    }//GEN-LAST:event_jCheckBoxHideAreaActionPerformed

     /*************************************************
    函数:       "移动侦测"  checkbox  选项改变事件响应函数
    函数描述:   设置按钮enable属性
     *************************************************/
    private void jCheckBoxMotionActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxMotionActionPerformed
    {//GEN-HEADEREND:event_jCheckBoxMotionActionPerformed
         jButtonSetMotion.setEnabled(jCheckBoxMotion.isSelected());
    }//GEN-LAST:event_jCheckBoxMotionActionPerformed

     /*************************************************
    函数:       "视频信号丢失报警"  checkbox  选项改变事件响应函数
    函数描述:   设置按钮enable属性
     *************************************************/
    private void jCheckBoxSignalLostActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxSignalLostActionPerformed
    {//GEN-HEADEREND:event_jCheckBoxSignalLostActionPerformed
         jButtonSetSignalLost.setEnabled(jCheckBoxSignalLost.isSelected());
    }//GEN-LAST:event_jCheckBoxSignalLostActionPerformed

     /*************************************************
    函数:       "遮挡报警"  checkbox  选项改变事件响应函数
    函数描述:   设置按钮enable属性
     *************************************************/
    private void jCheckBoxHideAlarmActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxHideAlarmActionPerformed
    {//GEN-HEADEREND:event_jCheckBoxHideAlarmActionPerformed
        jButtonSetHideAlarm.setEnabled(jCheckBoxHideAlarm.isSelected());
    }//GEN-LAST:event_jCheckBoxHideAlarmActionPerformed

    /*************************************************
    函数:       showChannelConfig
    函数描述:	获取通道参数并显示
     *************************************************/
    public void showChannelConfig()
    {
        int iChannelNumber = -1;
        int iIndex = jComboBoxChannelNumber.getSelectedIndex();
        if((iIndex < m_strDeviceInfo.byChanNum) && (iIndex >=0))
        {
            iChannelNumber = iIndex + m_strDeviceInfo.byStartChan;
        }
        else
        {
            iChannelNumber = 32 + (iIndex - m_strDeviceInfo.byChanNum) + m_strDeviceInfo.byStartChan;
        }
        IntByReference ibrBytesReturned = new IntByReference(0);//获取图片参数
        m_struPicCfg = new HCNetSDK.NET_DVR_PICCFG_V30();
        m_struPicCfg.write();
        Pointer lpPicConfig = m_struPicCfg.getPointer();
        boolean getDVRConfigSuc = hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_PICCFG_V30,
                new NativeLong(iChannelNumber), lpPicConfig, m_struPicCfg.size(), ibrBytesReturned);
        m_struPicCfg.read();
        if (getDVRConfigSuc != true)
        {
            JOptionPane.showMessageDialog(this, "获取图片参数失败");
            return;
        }

        ibrBytesReturned = new IntByReference(0);//获取压缩参数
        getDVRConfigSuc = false;
        m_struCompressionCfg = new HCNetSDK.NET_DVR_COMPRESSIONCFG_V30();
        m_struCompressionCfg.write();
        lpPicConfig = m_struCompressionCfg.getPointer();
        getDVRConfigSuc = hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_COMPRESSCFG_V30,
                new NativeLong(iChannelNumber), lpPicConfig, m_struCompressionCfg.size(), ibrBytesReturned);
        m_struCompressionCfg.read();
        if (getDVRConfigSuc != true)
        {
            JOptionPane.showMessageDialog(null, "获取压缩参数失败");
            return;
        }

        ibrBytesReturned = new IntByReference(0);//获取录像参数
        getDVRConfigSuc = false;
        m_struRemoteRecCfg = new HCNetSDK.NET_DVR_RECORD_V30();
        m_struRemoteRecCfg.write();
        lpPicConfig = m_struRemoteRecCfg.getPointer();
        getDVRConfigSuc = hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_RECORDCFG_V30,
                new NativeLong(iChannelNumber), lpPicConfig, m_struRemoteRecCfg.size(), ibrBytesReturned);
        m_struRemoteRecCfg.read();
        if (getDVRConfigSuc != true)
        {
            System.out.println(hCNetSDK.NET_DVR_GetLastError());
            JOptionPane.showMessageDialog(this, "获取录像参数失败");
            return;
        }

        //显示参数
        String[] sName = new String[2];
        sName = new String(m_struPicCfg.sChanName).split("\0", 2);
        jTextFieldChannelName.setText(sName[0]);
        //压缩参数
        jComboBoxCompressType.setSelectedIndex(0);
        jComboBoxImageQulity.setSelectedIndex(m_struCompressionCfg.struNormHighRecordPara.byPicQuality);
        jComboBoxStreamType.setSelectedIndex(m_struCompressionCfg.struNormHighRecordPara.byStreamType);
        jComboBoxResolution.setSelectedIndex(m_struCompressionCfg.struNormHighRecordPara.byResolution);
        jComboBoxBitRateType.setSelectedIndex(m_struCompressionCfg.struNormHighRecordPara.byBitrateType);
        jComboBoxFrameRate.setSelectedIndex(m_struCompressionCfg.struNormHighRecordPara.dwVideoFrameRate);
        if (((m_struCompressionCfg.struNormHighRecordPara.dwVideoBitrate >> 31) & 0x01) == 1)//位率上限
        {
            jComboBoxMaxBitRate.setSelectedIndex(22);
            jTextFieldBitRate.setText((m_struCompressionCfg.struNormHighRecordPara.dwVideoBitrate & 0x7fffffff) / 1024 + "");
        } else
        {
            jComboBoxMaxBitRate.setSelectedIndex(m_struCompressionCfg.struNormHighRecordPara.dwVideoBitrate - 2);
            jTextFieldBitRate.setText("");
        }
        jTextFieldIInterval.setText(m_struCompressionCfg.struNormHighRecordPara.wIntervalFrameI + "");
        jComboBoxBpInterval.setSelectedIndex(m_struCompressionCfg.struNormHighRecordPara.byIntervalBPFrame);

        //录像参数
        jCheckBoxRecord.setSelected((m_struRemoteRecCfg.dwRecord > 0) ? true : false);
        jComboBoxPreRecordTime.setSelectedIndex(m_struRemoteRecCfg.dwPreRecordTime);
        jComboBoxRecordDelay.setSelectedIndex(m_struRemoteRecCfg.dwRecordTime);
        jTextFieldSaveDays.setText(m_struRemoteRecCfg.dwRecorderDuration + "");
        jCheckBoxRebundancy.setSelected((m_struRemoteRecCfg.byRedundancyRec > 0) ? true : false);
        jCheckBoxAudioRec.setSelected((m_struRemoteRecCfg.byAudioRec > 0) ? true : false);

        //图像参数
        jCheckBoxHideArea.setSelected((m_struPicCfg.dwEnableHide > 0) ? true : false);
        jCheckBoxMotion.setSelected((m_struPicCfg.struMotion.byEnableHandleMotion > 0) ? true : false);
        jCheckBoxSignalLost.setSelected((m_struPicCfg.struVILost.byEnableHandleVILost > 0) ? true : false);
        jCheckBoxHideAlarm.setSelected((m_struPicCfg.struHideAlarm.dwEnableHideAlarm > 0) ? true : false);
        jCheckBoxOSD.setSelected((m_struPicCfg.dwShowOsd > 0) ? true : false);
        jTextFieldOSDX.setText(m_struPicCfg.wOSDTopLeftX + "");
        jTextFieldOSDY.setText(m_struPicCfg.wOSDTopLeftY + "");
        jCheckBoxOSDDate.setSelected((m_struPicCfg.byDispWeek > 0) ? true : false);
        jComboBoxTimeFormat.setSelectedIndex(m_struPicCfg.byHourOSDType);
        jComboBoxOSDFormat.setSelectedIndex(m_struPicCfg.byOSDAttrib - 2);
        jComboBoxDateFormat.setSelectedIndex(m_struPicCfg.byOSDType);
        jCheckBoxChannelName.setSelected((m_struPicCfg.dwShowChanName > 0) ? true : false);
        jTextFieldChannekNameX.setText(m_struPicCfg.wShowNameTopLeftX + "");
        jTextFieldChannelNameY.setText(m_struPicCfg.wShowNameTopLeftY + "");

        //设置子结构窗口弹出按钮的可编辑性
        jButtonSetHideArea.setEnabled(jCheckBoxHideArea.isSelected());
        jButtonSetMotion.setEnabled(jCheckBoxMotion.isSelected());
        jButtonSetSignalLost.setEnabled(jCheckBoxSignalLost.isSelected());
        jButtonSetHideAlarm.setEnabled(jCheckBoxHideAlarm.isSelected());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCompressOK;
    private javax.swing.JButton jButtonExit;
    private javax.swing.JButton jButtonSetChanConfig;
    public javax.swing.JButton jButtonSetHideAlarm;
    public javax.swing.JButton jButtonSetHideArea;
    public javax.swing.JButton jButtonSetMotion;
    private javax.swing.JButton jButtonSetRecord;
    public javax.swing.JButton jButtonSetSignalLost;
    private javax.swing.JButton jButtonString;
    public javax.swing.JCheckBox jCheckBoxAudioRec;
    public javax.swing.JCheckBox jCheckBoxChannelName;
    public javax.swing.JCheckBox jCheckBoxHideAlarm;
    public javax.swing.JCheckBox jCheckBoxHideArea;
    public javax.swing.JCheckBox jCheckBoxMotion;
    public javax.swing.JCheckBox jCheckBoxOSD;
    public javax.swing.JCheckBox jCheckBoxOSDDate;
    public javax.swing.JCheckBox jCheckBoxRebundancy;
    public javax.swing.JCheckBox jCheckBoxRecord;
    public javax.swing.JCheckBox jCheckBoxSignalLost;
    public javax.swing.JComboBox jComboBoxBitRateType;
    public javax.swing.JComboBox jComboBoxBpInterval;
    public javax.swing.JComboBox jComboBoxChannelNumber;
    public javax.swing.JComboBox jComboBoxCompressType;
    public javax.swing.JComboBox jComboBoxDateFormat;
    public javax.swing.JComboBox jComboBoxFrameRate;
    public javax.swing.JComboBox jComboBoxImageQulity;
    public javax.swing.JComboBox jComboBoxMaxBitRate;
    public javax.swing.JComboBox jComboBoxOSDFormat;
    public javax.swing.JComboBox jComboBoxPreRecordTime;
    public javax.swing.JComboBox jComboBoxRecordDelay;
    public javax.swing.JComboBox jComboBoxResolution;
    public javax.swing.JComboBox jComboBoxStreamType;
    public javax.swing.JComboBox jComboBoxTimeFormat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelChannelName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelChannelConfig;
    private javax.swing.JPanel jPanelCompressPara;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    public javax.swing.JTextField jTextFieldBitRate;
    public javax.swing.JTextField jTextFieldChannekNameX;
    public javax.swing.JTextField jTextFieldChannelName;
    public javax.swing.JTextField jTextFieldChannelNameY;
    public javax.swing.JTextField jTextFieldIInterval;
    public javax.swing.JTextField jTextFieldOSDX;
    public javax.swing.JTextField jTextFieldOSDY;
    public javax.swing.JTextField jTextFieldSaveDays;
    // End of variables declaration//GEN-END:variables
}
