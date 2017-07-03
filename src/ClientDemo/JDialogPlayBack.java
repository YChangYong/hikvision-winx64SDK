/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JDialogPlayBack.java
 *
 * Created on 2009-11-2, 19:36:07
 */
/**
 *
 * @author Administrator
 */

package ClientDemo;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.examples.win32.W32API.HWND;
import com.sun.jna.ptr.IntByReference;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*****************************************************************************
 *类 ：JDialogPlayBack
 *类描述 ：远程文件回放操作
 ****************************************************************************/
 class JDialogPlayBack extends javax.swing.JDialog {


     static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
     NativeLong m_lPlayBackHandle;//回放句柄
     NativeLong m_lDownloadHandle;//下载句柄
     NativeLong m_lUserID;//用户ID
     Timer Downloadtimer;//下载用定时器
     Timer Playbacktimer;//回放用定时器
    
     IntByReference m_nFileTime;//文件总时间
     IntByReference m_nTotalFrames;//文件总帧数
     
     int m_nTotalSecond;//总秒数
     int m_nTotalMinute;//总分钟数
     int m_nTotalHour;//总小时
     
     boolean m_bGetMaxTime;//是否已获得总播放时间,在计时器响函数里,只需要调用一次
     boolean m_bSaveFile;//是否在保存文件

    /*************************************************
    函数:      JDialogPlayBack
    函数描述:	构造函数   Creates new form JDialogPlayBack
     *************************************************/
    public JDialogPlayBack(java.awt.Frame parent, boolean modal, NativeLong lUserID) {
        super(parent, modal);
        initComponents();
        initialDialog();
        m_lUserID = lUserID;
        m_lPlayBackHandle = new NativeLong(-1);
        m_lDownloadHandle = new NativeLong(-1);
        m_nFileTime = new IntByReference(0);
        m_nTotalFrames = new IntByReference(0);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableFile = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabelType = new javax.swing.JLabel();
        jLabelFileType = new javax.swing.JLabel();
        jComboBoxRecodType = new javax.swing.JComboBox();
        jComboBoxFlieType = new javax.swing.JComboBox();
        jRadioButtonByCardNumber = new javax.swing.JRadioButton();
        jTextFieldCardNumber = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldChannelNumber = new javax.swing.JTextField();
        jButtonSearch = new javax.swing.JButton();
        jPanelTime = new javax.swing.JPanel();
        jLabelStartTime = new javax.swing.JLabel();
        jLabelEndTime = new javax.swing.JLabel();
        jTextFieldsYear = new javax.swing.JTextField();
        jTextFieldsMonth = new javax.swing.JTextField();
        jTextFieldsDay = new javax.swing.JTextField();
        jTextFieldsHour = new javax.swing.JTextField();
        jTextFieldsMinute = new javax.swing.JTextField();
        jTextFieldsSecond = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldeMonth = new javax.swing.JTextField();
        jTextFieldeDay = new javax.swing.JTextField();
        jTextFieldeHour = new javax.swing.JTextField();
        jTextFieldeMinute = new javax.swing.JTextField();
        jTextFieldeSecond = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldeYear = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanelPlayControl = new javax.swing.JPanel();
        panelPlayBack = new java.awt.Panel();
        jSliderPlayback = new javax.swing.JSlider();
        jToolBar2 = new javax.swing.JToolBar();
        jButtonPlay = new javax.swing.JButton();
        jButtonStop = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jButtonNormal = new javax.swing.JButton();
        jButtonPause = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jButtonSlow = new javax.swing.JButton();
        jButtonFast = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jButtonCapture = new javax.swing.JButton();
        jToggleButtonExit = new javax.swing.JToggleButton();
        jButtonDownload = new javax.swing.JButton();
        jProgressBar = new javax.swing.JProgressBar();
        jPanelPlayInfo = new javax.swing.JPanel();
        jTextFieldPlayTime = new javax.swing.JTextField();
        jButtonSave = new javax.swing.JButton();
        jButtonStopSave = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jTextFieldFileName = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTextFieldTotalBytes = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("按文件回放");
        getContentPane().setLayout(null);

        jTableFile.setModel(this.initialTableModel());
        jScrollPane1.setViewportView(jTableFile);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 390, 530, 180);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("查找信息"));
        jPanel1.setLayout(null);

        jLabelType.setText("录像类型");
        jPanel1.add(jLabelType);
        jLabelType.setBounds(10, 30, 60, 15);

        jLabelFileType.setText("文件属性");
        jPanel1.add(jLabelFileType);
        jLabelFileType.setBounds(10, 60, 60, 15);

        jComboBoxRecodType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "全部", "定时录像", "移动侦测", "报警触发", "报警|动测", "报警&动测", "命令触发", "手动录像" }));
        jPanel1.add(jComboBoxRecodType);
        jComboBoxRecodType.setBounds(90, 30, 110, 21);

        jComboBoxFlieType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "全部", "解锁", "锁定" }));
        jPanel1.add(jComboBoxFlieType);
        jComboBoxFlieType.setBounds(90, 60, 110, 21);
        jPanel1.add(jRadioButtonByCardNumber);
        jRadioButtonByCardNumber.setBounds(90, 120, 70, 21);

        jTextFieldCardNumber.setText("0");
        jPanel1.add(jTextFieldCardNumber);
        jTextFieldCardNumber.setBounds(90, 150, 190, 21);

        jLabel14.setText("通道号");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(10, 90, 36, 15);

        jTextFieldChannelNumber.setText("1");
        jPanel1.add(jTextFieldChannelNumber);
        jTextFieldChannelNumber.setBounds(90, 90, 110, 21);

        jButtonSearch.setText("查找");
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonSearch);
        jButtonSearch.setBounds(10, 340, 60, 23);

        jPanelTime.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanelTime.setLayout(null);

        jLabelStartTime.setText("开始时间");
        jPanelTime.add(jLabelStartTime);
        jLabelStartTime.setBounds(10, 10, 50, 50);

        jLabelEndTime.setText("结束时间");
        jPanelTime.add(jLabelEndTime);
        jLabelEndTime.setBounds(10, 70, 60, 50);
        jPanelTime.add(jTextFieldsYear);
        jTextFieldsYear.setBounds(80, 10, 30, 21);
        jPanelTime.add(jTextFieldsMonth);
        jTextFieldsMonth.setBounds(150, 10, 30, 21);
        jPanelTime.add(jTextFieldsDay);
        jTextFieldsDay.setBounds(210, 10, 30, 21);
        jPanelTime.add(jTextFieldsHour);
        jTextFieldsHour.setBounds(80, 40, 30, 21);
        jPanelTime.add(jTextFieldsMinute);
        jTextFieldsMinute.setBounds(150, 40, 30, 21);
        jPanelTime.add(jTextFieldsSecond);
        jTextFieldsSecond.setBounds(210, 40, 30, 21);

        jLabel1.setText("年");
        jPanelTime.add(jLabel1);
        jLabel1.setBounds(120, 10, 20, 15);

        jLabel2.setText("月");
        jPanelTime.add(jLabel2);
        jLabel2.setBounds(190, 10, 20, 15);

        jLabel3.setText("日");
        jPanelTime.add(jLabel3);
        jLabel3.setBounds(250, 10, 30, 15);

        jLabel4.setText("时");
        jPanelTime.add(jLabel4);
        jLabel4.setBounds(120, 40, 20, 15);

        jLabel5.setText("分");
        jPanelTime.add(jLabel5);
        jLabel5.setBounds(190, 40, 20, 15);

        jLabel6.setText("秒");
        jPanelTime.add(jLabel6);
        jLabel6.setBounds(250, 40, 30, 15);
        jPanelTime.add(jTextFieldeMonth);
        jTextFieldeMonth.setBounds(150, 70, 30, 21);
        jPanelTime.add(jTextFieldeDay);
        jTextFieldeDay.setBounds(210, 70, 30, 21);
        jPanelTime.add(jTextFieldeHour);
        jTextFieldeHour.setBounds(80, 100, 30, 21);
        jPanelTime.add(jTextFieldeMinute);
        jTextFieldeMinute.setBounds(150, 100, 30, 21);
        jPanelTime.add(jTextFieldeSecond);
        jTextFieldeSecond.setBounds(210, 100, 30, 21);

        jLabel7.setText("年");
        jPanelTime.add(jLabel7);
        jLabel7.setBounds(120, 70, 20, 15);

        jLabel8.setText("月");
        jPanelTime.add(jLabel8);
        jLabel8.setBounds(190, 70, 20, 15);

        jLabel9.setText("日");
        jPanelTime.add(jLabel9);
        jLabel9.setBounds(250, 70, 30, 15);

        jLabel10.setText("时");
        jPanelTime.add(jLabel10);
        jLabel10.setBounds(120, 100, 20, 15);

        jLabel11.setText("分");
        jPanelTime.add(jLabel11);
        jLabel11.setBounds(190, 100, 20, 15);

        jLabel12.setText("秒");
        jPanelTime.add(jLabel12);
        jLabel12.setBounds(250, 100, 30, 15);
        jPanelTime.add(jTextFieldeYear);
        jTextFieldeYear.setBounds(80, 70, 30, 21);

        jPanel1.add(jPanelTime);
        jPanelTime.setBounds(10, 190, 270, 130);

        jLabel13.setText("卡号");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(10, 150, 40, 15);

        jLabel20.setText("是否按卡号");
        jPanel1.add(jLabel20);
        jLabel20.setBounds(10, 120, 80, 15);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 10, 290, 380);

        jPanelPlayControl.setBorder(javax.swing.BorderFactory.createTitledBorder("回放控制"));
        jPanelPlayControl.setLayout(null);

        panelPlayBack.setBackground(new java.awt.Color(240, 255, 255));
        panelPlayBack.setForeground(new java.awt.Color(153, 255, 255));
        jPanelPlayControl.add(panelPlayBack);
        panelPlayBack.setBounds(10, 20, 390, 300);

        jSliderPlayback.setValue(0);
        jSliderPlayback.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jSliderPlaybackMouseReleased(evt);
            }
        });
        jPanelPlayControl.add(jSliderPlayback);
        jSliderPlayback.setBounds(0, 330, 400, 20);

        jToolBar2.setRollover(true);

        jButtonPlay.setBackground(new java.awt.Color(204, 204, 255));
        jButtonPlay.setFont(new java.awt.Font("微软雅黑", 0, 12));
        jButtonPlay.setForeground(new java.awt.Color(51, 51, 255));
        jButtonPlay.setText("Play");
        jButtonPlay.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButtonPlay.setFocusable(false);
        jButtonPlay.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonPlay.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPlayActionPerformed(evt);
            }
        });
        jToolBar2.add(jButtonPlay);

        jButtonStop.setFont(new java.awt.Font("微软雅黑", 0, 12));
        jButtonStop.setForeground(new java.awt.Color(51, 51, 255));
        jButtonStop.setText("Stop");
        jButtonStop.setFocusable(false);
        jButtonStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStopActionPerformed(evt);
            }
        });
        jToolBar2.add(jButtonStop);

        jLabel17.setText("      ");
        jToolBar2.add(jLabel17);

        jButtonNormal.setFont(new java.awt.Font("微软雅黑", 0, 12));
        jButtonNormal.setForeground(new java.awt.Color(51, 51, 255));
        jButtonNormal.setText("Normal");
        jButtonNormal.setFocusable(false);
        jButtonNormal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonNormal.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNormalActionPerformed(evt);
            }
        });
        jToolBar2.add(jButtonNormal);

        jButtonPause.setFont(new java.awt.Font("微软雅黑", 0, 12));
        jButtonPause.setForeground(new java.awt.Color(51, 51, 255));
        jButtonPause.setText("Pause");
        jButtonPause.setFocusable(false);
        jButtonPause.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonPause.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPauseActionPerformed(evt);
            }
        });
        jToolBar2.add(jButtonPause);

        jLabel18.setText("      ");
        jToolBar2.add(jLabel18);

        jButtonSlow.setFont(new java.awt.Font("微软雅黑", 0, 12));
        jButtonSlow.setForeground(new java.awt.Color(51, 51, 255));
        jButtonSlow.setText("Slow");
        jButtonSlow.setFocusable(false);
        jButtonSlow.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonSlow.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonSlow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSlowActionPerformed(evt);
            }
        });
        jToolBar2.add(jButtonSlow);

        jButtonFast.setFont(new java.awt.Font("微软雅黑", 0, 12));
        jButtonFast.setForeground(new java.awt.Color(51, 51, 255));
        jButtonFast.setText("Fast");
        jButtonFast.setFocusable(false);
        jButtonFast.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonFast.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonFast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFastActionPerformed(evt);
            }
        });
        jToolBar2.add(jButtonFast);

        jLabel19.setText("      ");
        jToolBar2.add(jLabel19);

        jButtonCapture.setFont(new java.awt.Font("微软雅黑", 0, 12));
        jButtonCapture.setForeground(new java.awt.Color(51, 51, 255));
        jButtonCapture.setText("Capture");
        jButtonCapture.setFocusable(false);
        jButtonCapture.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonCapture.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonCapture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCaptureActionPerformed(evt);
            }
        });
        jToolBar2.add(jButtonCapture);

        jPanelPlayControl.add(jToolBar2);
        jToolBar2.setBounds(10, 350, 380, 25);

        getContentPane().add(jPanelPlayControl);
        jPanelPlayControl.setBounds(300, 10, 410, 380);

        jToggleButtonExit.setText("退出");
        jToggleButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonExitActionPerformed(evt);
            }
        });
        getContentPane().add(jToggleButtonExit);
        jToggleButtonExit.setBounds(630, 580, 60, 23);

        jButtonDownload.setText("下载");
        jButtonDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDownloadActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonDownload);
        jButtonDownload.setBounds(20, 580, 90, 23);
        getContentPane().add(jProgressBar);
        jProgressBar.setBounds(130, 580, 410, 20);

        jPanelPlayInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanelPlayInfo.setLayout(null);
        jPanelPlayInfo.add(jTextFieldPlayTime);
        jTextFieldPlayTime.setBounds(0, 0, 160, 21);

        jButtonSave.setText("保存");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });
        jPanelPlayInfo.add(jButtonSave);
        jButtonSave.setBounds(0, 140, 70, 23);

        jButtonStopSave.setText("停止");
        jButtonStopSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStopSaveActionPerformed(evt);
            }
        });
        jPanelPlayInfo.add(jButtonStopSave);
        jButtonStopSave.setBounds(80, 140, 70, 23);

        jLabel15.setText("文件名");
        jPanelPlayInfo.add(jLabel15);
        jLabel15.setBounds(10, 30, 40, 15);
        jPanelPlayInfo.add(jTextFieldFileName);
        jTextFieldFileName.setBounds(0, 50, 160, 21);

        jLabel16.setText("总字节数");
        jPanelPlayInfo.add(jLabel16);
        jLabel16.setBounds(10, 80, 48, 15);
        jPanelPlayInfo.add(jTextFieldTotalBytes);
        jTextFieldTotalBytes.setBounds(0, 100, 160, 21);

        getContentPane().add(jPanelPlayInfo);
        jPanelPlayInfo.setBounds(550, 390, 160, 180);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*************************************************
    函数:      "查找"  按钮单击相应函数
    函数描述:	根据搜索信息搜索录像文件
     *************************************************/
    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonSearchActionPerformed
    {//GEN-HEADEREND:event_jButtonSearchActionPerformed
        //单击搜索文件
        ((DefaultTableModel) jTableFile.getModel()).getDataVector().removeAllElements();//搜索前先清空列表
        //把改变显示到列表控件
        ((DefaultTableModel) jTableFile.getModel()).fireTableStructureChanged();

        HCNetSDK.NET_DVR_FILECOND m_strFilecond = new HCNetSDK.NET_DVR_FILECOND();
        m_strFilecond.struStartTime = new HCNetSDK.NET_DVR_TIME();
        m_strFilecond.struStopTime = new HCNetSDK.NET_DVR_TIME();
        m_strFilecond.struStartTime.dwYear = Integer.parseInt(jTextFieldsYear.getText());//开始时间
        m_strFilecond.struStartTime.dwMonth = Integer.parseInt(jTextFieldsMonth.getText());
        m_strFilecond.struStartTime.dwDay = Integer.parseInt(jTextFieldsDay.getText());
        m_strFilecond.struStartTime.dwHour = Integer.parseInt(jTextFieldsHour.getText());
        m_strFilecond.struStartTime.dwMinute = Integer.parseInt(jTextFieldsMinute.getText());
        m_strFilecond.struStartTime.dwSecond = Integer.parseInt(jTextFieldsSecond.getText());
        m_strFilecond.struStopTime.dwYear = Integer.parseInt(jTextFieldeYear.getText());//结束时间
        m_strFilecond.struStopTime.dwMonth = Integer.parseInt(jTextFieldeMonth.getText());
        m_strFilecond.struStopTime.dwDay = Integer.parseInt(jTextFieldeDay.getText());
        m_strFilecond.struStopTime.dwHour = Integer.parseInt(jTextFieldeHour.getText());
        m_strFilecond.struStopTime.dwMinute = Integer.parseInt(jTextFieldeMinute.getText());
        m_strFilecond.struStopTime.dwSecond = Integer.parseInt(jTextFieldeSecond.getText());
        m_strFilecond.lChannel = new NativeLong(Integer.parseInt(jTextFieldChannelNumber.getText()));//通道号
        m_strFilecond.dwFileType = jComboBoxFlieType.getSelectedIndex();//文件类型
        m_strFilecond.dwIsLocked = 0xff;
        m_strFilecond.dwUseCardNo = jRadioButtonByCardNumber.isSelected() ? 1 : 0;  //是否使用卡号
        if (m_strFilecond.dwUseCardNo == 1)
        {
            m_strFilecond.sCardNumber = jTextFieldCardNumber.getText().getBytes();//卡号
            System.out.printf("卡号%s", m_strFilecond.sCardNumber);
        }

        NativeLong lFindFile = hCNetSDK.NET_DVR_FindFile_V30(m_lUserID, m_strFilecond);
        HCNetSDK.NET_DVR_FINDDATA_V30 strFile = new HCNetSDK.NET_DVR_FINDDATA_V30();
        long findFile = lFindFile.longValue();
        if (findFile > -1)
        {
            System.out.println("file" + findFile);
        }
        NativeLong lnext;
        strFile = new HCNetSDK.NET_DVR_FINDDATA_V30();

        while (true)
        {
            lnext = hCNetSDK.NET_DVR_FindNextFile_V30(lFindFile, strFile);
            if (lnext.longValue() == HCNetSDK.NET_DVR_FILE_SUCCESS)
            {
                //搜索成功
                DefaultTableModel FileTableModel = ((DefaultTableModel) jTableFile.getModel());//获取表格模型
                Vector<String> newRow = new Vector<String>();
               
                //添加文件名信息
                String[] s = new String[2];
                s = new String(strFile.sFileName).split("\0", 2);
                newRow.add(new String(s[0]));

                int iTemp;
                String MyString;
                if (strFile.dwFileSize < 1024 * 1024)
                {
                    iTemp = (strFile.dwFileSize) / (1024);
                    MyString = iTemp + "K";
                }
                else
                {
                    iTemp = (strFile.dwFileSize) / (1024 * 1024);
                    MyString = iTemp + "M   ";
                    iTemp = ((strFile.dwFileSize) % (1024 * 1024)) / (1204);
                    MyString = MyString + iTemp + "K";
                }
                newRow.add(MyString);                            //添加文件大小信息
                newRow.add(strFile.struStartTime.toStringTime());//添加开始时间信息
                newRow.add(strFile.struStopTime.toStringTime()); //添加结束时间信息

                FileTableModel.getDataVector().add(newRow);
                ((DefaultTableModel) jTableFile.getModel()).fireTableStructureChanged();
            }
            else
            {
                if (lnext.longValue() == HCNetSDK.NET_DVR_ISFINDING)
                {//搜索中
                    System.out.println("搜索中");
                    continue;
                }
                else
                {
                    if (lnext.longValue() == HCNetSDK.NET_DVR_FILE_NOFIND)
                    {
                        JOptionPane.showMessageDialog(this, "没有搜到文件");
                        return;
                    }
                    else
                    {
                        System.out.println("搜索文件结束");
                        boolean flag = hCNetSDK.NET_DVR_FindClose_V30(lFindFile);
                        if (flag == false)
                        {
                            System.out.println("结束搜索失败");
                        }
                        return;
                    }
                }
            }
        }
}//GEN-LAST:event_jButtonSearchActionPerformed

    /*************************************************
    函数:      "Pause"  按钮单击相应函数
    函数描述:  暂停播放
     *************************************************/
    /*************************************************
    函数:      "退出"  按钮单击相应函数
    函数描述:  销毁对话框
     *************************************************/
    private void jToggleButtonExitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jToggleButtonExitActionPerformed
    {//GEN-HEADEREND:event_jToggleButtonExitActionPerformed
        StopPlay();
        dispose();
    }//GEN-LAST:event_jToggleButtonExitActionPerformed

    /*************************************************
    函数:      "下载"  按钮单击相应函数
    函数描述:  下载选中文件
     *************************************************/
    private void jButtonDownloadActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonDownloadActionPerformed
    {//GEN-HEADEREND:event_jButtonDownloadActionPerformed
       //如果不在下载,开始下载
        if (m_lDownloadHandle.intValue() == -1)
        {
            //未选择文件时提示选择要下载的文件
            if (jTableFile.getSelectedRow() == -1)
            {
                JOptionPane.showMessageDialog(this, "请选择要下载的文件");
                return;
            }

            //获取文件名
            DefaultTableModel FileTableModel = ((DefaultTableModel) jTableFile.getModel());
            String sFileName = FileTableModel.getValueAt(jTableFile.getSelectedRow(), 0).toString();

            //暂且将文件名作为保存的名字
            m_lDownloadHandle = hCNetSDK.NET_DVR_GetFileByName(m_lUserID, sFileName, sFileName);
            if (m_lDownloadHandle.intValue() >= 0)
            {
                hCNetSDK.NET_DVR_PlayBackControl(m_lDownloadHandle, HCNetSDK.NET_DVR_PLAYSTART, 0, null);
                jButtonDownload.setText("停止下载");
                jProgressBar.setValue(0);
                jProgressBar.setVisible(true);

                //开始计时器
                Downloadtimer = new Timer();//新建定时器
                Downloadtimer.schedule(new DownloadTask(), 0, 1000);//0秒后开始响应函数
            } else
            {
                JOptionPane.showMessageDialog(this, "下载文件失败");
                return;
            }
        }
        //如果在下载,停止下载
        else
        {
            	hCNetSDK.NET_DVR_StopGetFile(m_lDownloadHandle);
		m_lDownloadHandle.setValue(-1);
		jButtonDownload.setText("下载");
		jProgressBar.setValue(0);
		jProgressBar.setVisible(false);
                Downloadtimer.cancel();
        }

    }//GEN-LAST:event_jButtonDownloadActionPerformed

    /*************************************************
    函数:      "保存"  按钮单击相应函数
    函数描述:  保存正在回放的码流
     *************************************************/
    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonSaveActionPerformed
    {//GEN-HEADEREND:event_jButtonSaveActionPerformed
      if(m_lPlayBackHandle.intValue() == -1)
      {
          JOptionPane.showMessageDialog(this, "请先播放文件");
          return;
      }
       JFileChooser myJFileChooser = new JFileChooser();
       myJFileChooser.showSaveDialog(this);
       String sFileName = myJFileChooser.getSelectedFile() + ".mp4";

       	if (hCNetSDK.NET_DVR_PlayBackSaveData(m_lPlayBackHandle, sFileName))
	{
		m_bSaveFile = true;
	}
	else
	{
		JOptionPane.showMessageDialog(this, "保存文件失败");
	}
    }//GEN-LAST:event_jButtonSaveActionPerformed

     /*************************************************
    函数:      "停止"  按钮单击相应函数
    函数描述:  停止保存
     *************************************************/
     private void jButtonStopSaveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonStopSaveActionPerformed
     {//GEN-HEADEREND:event_jButtonStopSaveActionPerformed
         if (m_bSaveFile)
         {
             hCNetSDK.NET_DVR_StopPlayBackSave(m_lPlayBackHandle);
             m_bSaveFile = false;
             JOptionPane.showMessageDialog(this, "停止保存成功");
         }
    }//GEN-LAST:event_jButtonStopSaveActionPerformed

    /*************************************************
    函数:      "进度条"  鼠标Release时间按钮响应函数
    函数描述:  根据设置的进度播放文件
     *************************************************/
     private void jSliderPlaybackMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jSliderPlaybackMouseReleased
     {//GEN-HEADEREND:event_jSliderPlaybackMouseReleased
           int iPos = jSliderPlayback.getValue();
         if (m_lPlayBackHandle.intValue() >= 0)
	{
		if ((iPos >=0) && (iPos <=100))
		{
			if (iPos == 100)
			{
				StopPlay();
				iPos = 99;
			}
			else
			{
				if(hCNetSDK.NET_DVR_PlayBackControl(m_lPlayBackHandle, HCNetSDK.NET_DVR_PLAYSETPOS, iPos, null))
				{
					System.out.println("设置播放进度成功");
				}
				else
				{
					System.out.println("设置播放进度失败");
				}
			}
		}
	}
     }//GEN-LAST:event_jSliderPlaybackMouseReleased

    /*************************************************
    函数:      "Play"  按钮单击相应函数
    函数描述:  播放选中的文件
     *************************************************/
     private void jButtonPlayActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonPlayActionPerformed
     {//GEN-HEADEREND:event_jButtonPlayActionPerformed
          if(jTableFile.getSelectedRow() == -1)
       {
           JOptionPane.showMessageDialog(this, "请选择要播放的文件");
           return;
       }

        //如果已经在回放
        if(m_lPlayBackHandle.intValue() != -1)
       {
           hCNetSDK.NET_DVR_StopPlayBack(m_lPlayBackHandle);
           m_lPlayBackHandle.setValue(-1);
       }

       //获取文件名
        DefaultTableModel FileTableModel = ((DefaultTableModel) jTableFile.getModel());
        String sFileName = FileTableModel.getValueAt(jTableFile.getSelectedRow(), 0).toString();
        String sSize = FileTableModel.getValueAt(jTableFile.getSelectedRow(),1).toString();

        //获取窗口句柄
        HWND hwnd = new HWND();
        hwnd.setPointer(Native.getComponentPointer(panelPlayBack));//获取窗口的指针

        //调用接口开始回放
        m_lPlayBackHandle = hCNetSDK.NET_DVR_PlayBackByName(m_lUserID, sFileName, hwnd);
        if (m_lPlayBackHandle.longValue() > -1)
        {
            System.out.println("回放成功");
        }
        else
        {
             JOptionPane.showMessageDialog(this, "回放失败");
             return;
        }

        //调用playControl才会开始播放
        if (!hCNetSDK.NET_DVR_PlayBackControl(m_lPlayBackHandle, HCNetSDK.NET_DVR_PLAYSTART, 0, null))
        {
            JOptionPane.showMessageDialog(this, "开始回放失败");
            return;
        }
        else
        {
            jTextFieldFileName.setText(sFileName);
            jTextFieldTotalBytes.setText(sSize);
            //开始计时器
            Playbacktimer = new Timer();//新建定时器
            Playbacktimer.schedule(new PlaybackTask(), 0, 1000);//0秒后开始响应函数
        }
     }//GEN-LAST:event_jButtonPlayActionPerformed

    /*************************************************
    函数:      "Stop"  按钮单击相应函数
    函数描述:  停止播放
     *************************************************/
     private void jButtonStopActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonStopActionPerformed
     {//GEN-HEADEREND:event_jButtonStopActionPerformed
               StopPlay();
     }//GEN-LAST:event_jButtonStopActionPerformed

    /*************************************************
    函数:      "Normal"  按钮单击相应函数
    函数描述:  恢复正常播放
     *************************************************/
     private void jButtonNormalActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonNormalActionPerformed
     {//GEN-HEADEREND:event_jButtonNormalActionPerformed
               if (m_lPlayBackHandle.intValue() == -1)
        {
            return;
        }

        if (!hCNetSDK.NET_DVR_PlayBackControl(m_lPlayBackHandle, HCNetSDK.NET_DVR_PLAYNORMAL, 0, null))
        {
            JOptionPane.showMessageDialog(this, "恢复正常播放失败");
        }
     }//GEN-LAST:event_jButtonNormalActionPerformed

    /*************************************************
    函数:      "Pause"  按钮单击相应函数
    函数描述:  暂停播放
     *************************************************/
     private void jButtonPauseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonPauseActionPerformed
     {//GEN-HEADEREND:event_jButtonPauseActionPerformed
          if (m_lPlayBackHandle.intValue() == -1)
        {
            return;
        }

        if (!hCNetSDK.NET_DVR_PlayBackControl(m_lPlayBackHandle, HCNetSDK.NET_DVR_PLAYPAUSE, 0, null))
        {
            JOptionPane.showMessageDialog(this, "暂停失败");
        }
     }//GEN-LAST:event_jButtonPauseActionPerformed

    /*************************************************
    函数:      "Slow"  按钮单击相应函数
    函数描述:    慢放
     *************************************************/
     private void jButtonSlowActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonSlowActionPerformed
     {//GEN-HEADEREND:event_jButtonSlowActionPerformed
           hCNetSDK.NET_DVR_PlayBackControl(m_lPlayBackHandle, hCNetSDK.NET_DVR_PLAYSLOW, 0, null);
     }//GEN-LAST:event_jButtonSlowActionPerformed

    /*************************************************
    函数:      "Fast"  按钮单击相应函数
    函数描述:    快放
     *************************************************/
     private void jButtonFastActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonFastActionPerformed
     {//GEN-HEADEREND:event_jButtonFastActionPerformed
          hCNetSDK.NET_DVR_PlayBackControl(m_lPlayBackHandle, hCNetSDK.NET_DVR_PLAYFAST, 0, null);
     }//GEN-LAST:event_jButtonFastActionPerformed

    /*************************************************
    函数:      "Capture"  按钮单击相应函数
    函数描述:  截图
     *************************************************/
     private void jButtonCaptureActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCaptureActionPerformed
     {//GEN-HEADEREND:event_jButtonCaptureActionPerformed
           if(m_lPlayBackHandle.intValue() == -1)
        {
            return;
        }
        String sPicName = "C:/Picture/" + Integer.parseInt(jTextFieldChannelNumber.getText()) + System.currentTimeMillis() + ".bmp";
	if (hCNetSDK.NET_DVR_PlayBackCaptureFile(m_lPlayBackHandle,sPicName))
	{
		System.out.println("抓图:" + sPicName);
		return;
	}
	else
	{
		JOptionPane.showMessageDialog(this, "抓图失败");
	}
     }//GEN-LAST:event_jButtonCaptureActionPerformed

    /*************************************************
    函数:      initialTableModel
    函数描述:  初始化文件列表
     *************************************************/
    private DefaultTableModel initialTableModel()
    {
        String tabeTile[];
        tabeTile = new String[]{"文件名称","大小","开始时间","结束时间"};
        DefaultTableModel fileTableModel = new DefaultTableModel(tabeTile, 10);
        return fileTableModel;
    }

    /*************************************************
    函数:      initialDialog
    函数描述:  初始化搜索时间信息
     *************************************************/
     private void initialDialog()
     {
         Date today = new Date();//现在时间,用于设置时间框里的时间(当天的0:0:0-23:59:59)
         Calendar now = Calendar.getInstance();//日历对象                                                                       //得到当前日期
         now.setTime(today); //设置时间

         //开始时间
         jTextFieldsYear.setText(now.get(Calendar.YEAR) + "");
         jTextFieldsMonth.setText((now.get(Calendar.MONTH) + 1) + "");
         jTextFieldsDay.setText(1 + "");
         jTextFieldsHour.setText("0");
         jTextFieldsMinute.setText("0");
         jTextFieldsSecond.setText("0");

         //结束时间
         jTextFieldeYear.setText(now.get(Calendar.YEAR) + "");
         jTextFieldeMonth.setText((now.get(Calendar.MONTH) + 1) + "");
         jTextFieldeDay.setText(now.get(Calendar.DATE) + "");
         jTextFieldeHour.setText("23");
         jTextFieldeMinute.setText("59");
         jTextFieldeSecond.setText("59");

         jProgressBar.setVisible(false);

           this.addWindowListener(new WindowAdapter()
         {
             public void windowClosing(WindowEvent e)
             {
                 StopPlay();
             }
         });
     }

   /*************************************************
    类:      DownloadTask
    类描述:  下载定时器响应函数
     *************************************************/
    class DownloadTask extends java.util.TimerTask
    {
        //定时器函数 
        @Override
        public void run()
        {
        	int  iPos = hCNetSDK.NET_DVR_GetDownloadPos(m_lDownloadHandle);
                System.out.println(iPos);
                if (iPos < 0)		               //failed
			{
				hCNetSDK.NET_DVR_StopGetFile(m_lDownloadHandle);
				jProgressBar.setVisible(false);
				jButtonDownload.setText("下载");
				m_lDownloadHandle.setValue(-1);
			        JOptionPane.showMessageDialog(null, "获取下载进度失败");
                                Downloadtimer.cancel();
			}
			if (iPos == 100)		//end download
			{
				hCNetSDK.NET_DVR_StopGetFile(m_lDownloadHandle);
				jProgressBar.setVisible(false);
				jButtonDownload.setText("下载");
				m_lDownloadHandle.setValue(-1);
                                JOptionPane.showMessageDialog(null, "下载完毕");
                                Downloadtimer.cancel();
			}
			if (iPos > 100)		        //download exception for network problems or DVR hasten
			{
				hCNetSDK.NET_DVR_StopGetFile(m_lDownloadHandle);
				jProgressBar.setVisible(false);
				jButtonDownload.setText("下载");
				m_lDownloadHandle.setValue(-1);
			        JOptionPane.showMessageDialog(null, "由于网络原因或DVR忙,下载异常终止");
                                Downloadtimer.cancel();
			}
			else
			{
				jProgressBar.setValue(iPos);
			}
        }
    }

   /*************************************************
    类:      PlaybackTask
    类描述:  回放定时器响应函数
     *************************************************/
    class PlaybackTask extends java.util.TimerTask
    {
        //定时器函数 
        @Override
        public void run()
        {
            	IntByReference nCurrentTime = new IntByReference(0);
                IntByReference nCurrentFrame = new IntByReference(0);
                IntByReference nPos = new IntByReference(0);
                int nHour, nMinute, nSecond;
            	if (!m_bGetMaxTime)
		{
			hCNetSDK.NET_DVR_PlayBackControl(m_lPlayBackHandle, HCNetSDK.NET_DVR_GETTOTALTIME, 0, m_nFileTime);
			if (m_nFileTime.getValue() == 0)
			{
				return;
			}

			if (hCNetSDK.NET_DVR_PlayBackControl(m_lPlayBackHandle, HCNetSDK.NET_DVR_GETTOTALFRAMES, 0, m_nTotalFrames))
			{
				if (m_nTotalFrames.getValue() == 0)
				{
					return;
				}
			}
			else
			{
				System.out.println("获取总帧数失败");
			}

			m_nTotalHour = m_nFileTime.getValue()/3600;
			m_nTotalMinute = (m_nFileTime.getValue()%3600)/60;
			m_nTotalSecond = m_nFileTime.getValue()%60;
			m_bGetMaxTime = true;
		}

		hCNetSDK.NET_DVR_PlayBackControl(m_lPlayBackHandle, HCNetSDK.NET_DVR_PLAYGETTIME, 0, nCurrentTime);
		if (nCurrentTime.getValue() >= m_nFileTime.getValue())
		{
			nCurrentTime.setValue(m_nFileTime.getValue());
		}
		nHour = (nCurrentTime.getValue()/3600)%24;
		nMinute =(nCurrentTime.getValue()%3600)/60;
		nSecond = nCurrentTime.getValue()%60;
		hCNetSDK.NET_DVR_PlayBackControl(m_lPlayBackHandle, HCNetSDK.NET_DVR_PLAYGETFRAME, 0, nCurrentFrame);
		if (nCurrentFrame.getValue() > m_nTotalFrames.getValue())
		{
			nCurrentFrame.setValue(m_nTotalFrames.getValue());
		}

                 String sPlayTime;//播放时间
                 sPlayTime = String.format("%02d:%02d:%02d/%02d:%02d:%02d ",nHour,nMinute,nSecond,m_nTotalHour,m_nTotalMinute,m_nTotalSecond);
                jTextFieldPlayTime.setText(sPlayTime);

		hCNetSDK.NET_DVR_PlayBackControl(m_lPlayBackHandle, HCNetSDK.NET_DVR_PLAYGETPOS, 0, nPos);
		if (nPos.getValue() > 100)//200 indicates network exception
		{
			StopPlay();
                        JOptionPane.showMessageDialog(JDialogPlayBack.this, "由于网络原因或DVR忙,回放异常终止!");
		}
		else
		{
			jSliderPlayback.setValue(nPos.getValue());
			if (nPos.getValue() == 100)
			{
				StopPlay();
			}
		}	
        }
    }

   /*************************************************
    函数:      StopPlay
    函数描述:  停止播放
     *************************************************/
    private void StopPlay()
    {
        	if (m_lPlayBackHandle.intValue() >= 0)
	{
		if (m_bSaveFile)
		{
			if (!hCNetSDK.NET_DVR_StopPlayBackSave(m_lPlayBackHandle))
			{
				System.out.println("停止保存失败");
			}
			else
			{
				m_bSaveFile = false;
			}

		}
		if (!hCNetSDK.NET_DVR_StopPlayBack(m_lPlayBackHandle))
		{
                    System.out.println("NET_DVR_StopPlayBack failed");
                    return;
		}
		else
		{
                panelPlayBack.repaint();
		m_lPlayBackHandle.setValue(-1) ;
		jSliderPlayback.setValue(-1);
		Playbacktimer.cancel();
                jTextFieldFileName.setText("");
                jTextFieldTotalBytes.setText("");
                jTextFieldPlayTime.setText("");
                jSliderPlayback.setValue(0);
		}
	}
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonCapture;
    private javax.swing.JButton jButtonDownload;
    private javax.swing.JButton jButtonFast;
    private javax.swing.JButton jButtonNormal;
    private javax.swing.JButton jButtonPause;
    private javax.swing.JButton jButtonPlay;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JButton jButtonSlow;
    private javax.swing.JButton jButtonStop;
    private javax.swing.JButton jButtonStopSave;
    private javax.swing.JComboBox jComboBoxFlieType;
    private javax.swing.JComboBox jComboBoxRecodType;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelEndTime;
    private javax.swing.JLabel jLabelFileType;
    private javax.swing.JLabel jLabelStartTime;
    private javax.swing.JLabel jLabelType;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelPlayControl;
    private javax.swing.JPanel jPanelPlayInfo;
    private javax.swing.JPanel jPanelTime;
    private javax.swing.JProgressBar jProgressBar;
    private javax.swing.JRadioButton jRadioButtonByCardNumber;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jSliderPlayback;
    private javax.swing.JTable jTableFile;
    private javax.swing.JTextField jTextFieldCardNumber;
    private javax.swing.JTextField jTextFieldChannelNumber;
    private javax.swing.JTextField jTextFieldFileName;
    private javax.swing.JTextField jTextFieldPlayTime;
    private javax.swing.JTextField jTextFieldTotalBytes;
    private javax.swing.JTextField jTextFieldeDay;
    private javax.swing.JTextField jTextFieldeHour;
    private javax.swing.JTextField jTextFieldeMinute;
    private javax.swing.JTextField jTextFieldeMonth;
    private javax.swing.JTextField jTextFieldeSecond;
    private javax.swing.JTextField jTextFieldeYear;
    private javax.swing.JTextField jTextFieldsDay;
    private javax.swing.JTextField jTextFieldsHour;
    private javax.swing.JTextField jTextFieldsMinute;
    private javax.swing.JTextField jTextFieldsMonth;
    private javax.swing.JTextField jTextFieldsSecond;
    private javax.swing.JTextField jTextFieldsYear;
    private javax.swing.JToggleButton jToggleButtonExit;
    private javax.swing.JToolBar jToolBar2;
    private java.awt.Panel panelPlayBack;
    // End of variables declaration//GEN-END:variables

}
