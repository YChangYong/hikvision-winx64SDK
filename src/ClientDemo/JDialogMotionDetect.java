/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JDialogMotionDetect.java
 *
 * Created on 2009-12-10, 11:18:41
 */
/**
 *
 * @author Administrator
 */

package ClientDemo;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.examples.win32.GDI32.RECT;
import com.sun.jna.examples.win32.User32.POINT;
import com.sun.jna.examples.win32.W32API.HDC;
import com.sun.jna.examples.win32.W32API.HWND;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;

/*****************************************************************************
 *类 ：JDialogMotionDetect
 *类描述 ：移动侦测参数配置
 ****************************************************************************/
public class JDialogMotionDetect extends javax.swing.JDialog {

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    static GDI32 gDi = GDI32.INSTANCE;
    static USER32 uSer = USER32.INSTANCE;

    public static final int MAX_MOTION_NUM = 4;

    private HCNetSDK.NET_DVR_PICCFG_V30 m_struPicCfg;//图像参数,初始化后直接指向通道配置里的图像参数结构体
    private HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;//设备信息参数
    private HCNetSDK.NET_DVR_CLIENTINFO m_strClientInfo;//预览参数
   
    private NativeLong m_lUserID;//用户句柄
    private NativeLong m_lPlayHandle;//预览句柄
    private int m_iChanShowNum;//父窗口传来的通道号,预览此通道
    private boolean m_bInitialed;//是否已经初始化

    private CheckListItem m_traggerAlarmOut[] = new CheckListItem[HCNetSDK.MAX_ALARMOUT_V30];//报警输出通道checkbox对应值
    private CheckListItem m_traggerRecord[] = new CheckListItem[HCNetSDK.MAX_CHANNUM_V30];//触发录像通道checkbox对应值

    private boolean m_bDrawArea;//draw motion detect area
    private boolean m_bSetMotion;//set motion detect area

    private FDrawFunGet MotionDetectGetCallBack; //显示移动侦测区域回调函数
    private FDrawFunSet MotionDetectSetCallBack;//设置移动侦测区域回调函数

     
    private int g_iDetectIndex = 0;			//motion detect zone index
    private int g_dwPrecision = 16;// the precision of the 22*11 detect unit under D1(704*576) resolution is 32*32, in the demothepicture is displayed in cif(352*288), and precision/2
    private RECT[] g_rectMotionDetectSet = new RECT[MAX_MOTION_NUM];	//motion detect zone display rectangle
    private RECT[] g_rectMotionDetectMouse = new RECT[MAX_MOTION_NUM];//mouse drawing line

    /*************************************************
    函数:      JDialogMotionDetect
    函数描述:   构造函数   Creates new form JDialogMotionDetect
     *************************************************/
    public JDialogMotionDetect(javax.swing.JDialog parent, boolean modal, NativeLong lUserID, int iChannelNum, HCNetSDK.NET_DVR_PICCFG_V30 struPicCfg, HCNetSDK.NET_DVR_DEVICEINFO_V30 strDeviceInfo)
    {
        super(parent, modal);
        initComponents();

        m_lUserID = lUserID;
        m_iChanShowNum = iChannelNum;
        m_lPlayHandle = new NativeLong(-1);
        m_struPicCfg = struPicCfg;
        m_strDeviceInfo = strDeviceInfo;

        MotionDetectSetCallBack = new FDrawFunSet();
        MotionDetectGetCallBack = new FDrawFunGet();

        for (int i = 0; i < MAX_MOTION_NUM; i++)
        {
            g_rectMotionDetectSet[i] = new RECT();
            g_rectMotionDetectMouse[i] = new RECT();
        }

        for (int i = 0; i < HCNetSDK.MAX_ALARMOUT_V30; i++)
        {
            m_traggerAlarmOut[i] = new CheckListItem(false, null);
        }

        for (int i = 0; i < HCNetSDK.MAX_CHANNUM_V30; i++)
        {
            m_traggerRecord[i] = new CheckListItem(false, null);
        }

        //初始化对话框,预览,list初始化等操作
        initialDialog();
    }

    /*************************************************
    函数:      initialDialog
    函数描述:   初始化对话框
     *************************************************/
    private void initialDialog()
    {
        //移动侦测区域  预览
        HWND hwnd = new HWND(Native.getComponentPointer(panelPlay));
        m_strClientInfo = new HCNetSDK.NET_DVR_CLIENTINFO();
        m_strClientInfo.lChannel = new NativeLong(m_iChanShowNum);
        m_strClientInfo.hPlayWnd = hwnd;
        m_lPlayHandle = hCNetSDK.NET_DVR_RealPlay_V30(m_lUserID,
                m_strClientInfo, null, null, true);
        if (m_lPlayHandle.intValue() == -1)
        {
            JOptionPane.showMessageDialog(this, "预览失败,错误值:" + hCNetSDK.NET_DVR_GetLastError());
        }

        //触发报警输出通道list
        jListTraggerAlarmOut.setCellRenderer(new CheckListItemRenderer());
        DefaultListModel listModelTraggerAlarmOut = new DefaultListModel();
        jListTraggerAlarmOut.setModel(listModelTraggerAlarmOut);
        jListTraggerAlarmOut.addMouseListener(new CheckListMouseListener());
        for (int i = 0; i < m_strDeviceInfo.byAlarmOutPortNum; i++)
        {
            m_traggerAlarmOut[i] = new CheckListItem(false, "AlarmOut" + (i + 1));
            listModelTraggerAlarmOut.addElement(m_traggerAlarmOut[i]);    // 为触发报警输出List增加报警输出
        }
        //触发录像通道list
        jListTraggerRecord.setCellRenderer(new CheckListItemRenderer());
        DefaultListModel listModelTraggerRecord = new DefaultListModel();
        jListTraggerRecord.setModel(listModelTraggerRecord);
        jListTraggerRecord.addMouseListener(new CheckListMouseListener());
        for (int i = 0; i < m_strDeviceInfo.byChanNum; i++)
        {
            m_traggerRecord[i] = new CheckListItem(false, "Camara" + (i + 1));
            listModelTraggerRecord.addElement(m_traggerRecord[i]);    // 为触发录像List增加报警输出
        }

        //将数据反映到对话框
        initialData();
        
        m_bInitialed = true;
    }

    /*************************************************
    函数:      initialData
    函数描述:   显示初始数据
     *************************************************/
    private void initialData()
    {
        if (m_struPicCfg.struMotion.byMotionSensitive == -1)
	{
		jComboBoxSensitive.setSelectedIndex(0);
	}
	else
	{
		jComboBoxSensitive.setSelectedIndex(m_struPicCfg.struMotion.byMotionSensitive);
	}

        jCheckBoxMonitorAlarm.setSelected((m_struPicCfg.struMotion.struMotionHandleType.dwHandleType & 0x01) == 1);
        jCheckBoxAudioAlarm.setSelected(((m_struPicCfg.struMotion.struMotionHandleType.dwHandleType >> 1) & 0x01) == 1);
        jCheckBoxCenter.setSelected(((m_struPicCfg.struMotion.struMotionHandleType.dwHandleType >> 2) & 0x01) == 1);
        jCheckBoxAlarmout.setSelected(((m_struPicCfg.struMotion.struMotionHandleType.dwHandleType >> 3) & 0x01) == 1);
        jCheckBoxJPEG.setSelected(((m_struPicCfg.struMotion.struMotionHandleType.dwHandleType >> 4) & 0x01) == 1);

        for (int i = 0; i < HCNetSDK.MAX_ALARMOUT_V30; i++)
        {
            m_traggerAlarmOut[i].setCheck(m_struPicCfg.struMotion.struMotionHandleType.byRelAlarmOut[i] == 1);
        }

        for (int i = 0; i < HCNetSDK.MAX_CHANNUM_V30; i++)
        {
            m_traggerRecord[i].setCheck(m_struPicCfg.struMotion.byRelRecordChan[i] == 1);
        }

        //显示布防时间参数
        showAlarmTime();
    }

    /*************************************************
    函数:      showAlarmTime
    函数描述:   显示布防时间
     *************************************************/
    private void showAlarmTime()
    {
        int iWeekDay = jComboBoxDate.getSelectedIndex();
        jTextFieldInBeginHour1.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[0].byStartHour + "");
        jTextFieldInBeginMin1.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[0].byStartMin + "");
        jTextFieldInEndHour1.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[0].byStopHour + "");
        jTextFieldInEndMin1.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[0].byStopMin + "");

        jTextFieldInBeginHour2.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[1].byStartHour + "");
        jTextFieldInBeginMin2.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[1].byStartMin + "");
        jTextFieldInEndHour2.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[1].byStopHour + "");
        jTextFieldInEndMin2.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[1].byStopMin + "");

        jTextFieldInBeginHour3.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[2].byStartHour + "");
        jTextFieldInBeginMin3.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[2].byStartMin + "");
        jTextFieldInEndHour3.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[2].byStopHour + "");
        jTextFieldInEndMin3.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[2].byStopMin + "");

        jTextFieldInBeginHour4.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[3].byStartHour + "");
        jTextFieldInBeginMin4.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[3].byStartMin + "");
        jTextFieldInEndHour4.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[3].byStopHour + "");
        jTextFieldInEndMin4.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[3].byStopMin + "");

        jTextFieldInBeginHour5.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[4].byStartHour + "");
        jTextFieldInBeginMin5.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[4].byStartMin + "");
        jTextFieldInEndHour5.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[4].byStopHour + "");
        jTextFieldInEndMin5.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[4].byStopMin + "");

        jTextFieldInBeginHour6.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[5].byStartHour + "");
        jTextFieldInBeginMin6.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[5].byStartMin + "");
        jTextFieldInEndHour6.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[5].byStopHour + "");
        jTextFieldInEndMin6.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[5].byStopMin + "");

        jTextFieldInBeginHour7.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[6].byStartHour + "");
        jTextFieldInBeginMin7.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[6].byStartMin + "");
        jTextFieldInEndHour7.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[6].byStopHour + "");
        jTextFieldInEndMin7.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[6].byStopMin + "");

        jTextFieldInBeginHour8.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[7].byStartHour + "");
        jTextFieldInBeginMin8.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[7].byStartMin + "");
        jTextFieldInEndHour8.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[7].byStopHour + "");
        jTextFieldInEndMin8.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[7].byStopMin + "");
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPlay = new java.awt.Panel();
        jPanel1 = new javax.swing.JPanel();
        jCheckBoxGetMotionArea = new javax.swing.JCheckBox();
        jCheckBoxSetMotionArea = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxSensitive = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListTraggerRecord = new javax.swing.JList();
        jPanel3 = new javax.swing.JPanel();
        jCheckBoxMonitorAlarm = new javax.swing.JCheckBox();
        jCheckBoxCenter = new javax.swing.JCheckBox();
        jCheckBoxAudioAlarm = new javax.swing.JCheckBox();
        jCheckBoxJPEG = new javax.swing.JCheckBox();
        jCheckBoxAlarmout = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListTraggerAlarmOut = new javax.swing.JList();
        jPanel4 = new javax.swing.JPanel();
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
        jButtonConfirm = new javax.swing.JButton();
        jLabel53 = new javax.swing.JLabel();
        jTextFieldInBeginHour7 = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jTextFieldInBeginMin7 = new javax.swing.JTextField();
        jTextFieldInEndMin7 = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jTextFieldInEndHour7 = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jTextFieldInEndHour8 = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jTextFieldInEndMin8 = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        jTextFieldInBeginHour8 = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        jTextFieldInBeginMin8 = new javax.swing.JTextField();
        jComboBoxDate = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jButtonSave = new javax.swing.JButton();
        jButtonExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("移动侦测设置");

        panelPlay.setBackground(new java.awt.Color(204, 255, 255));
        panelPlay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelPlayMousePressed(evt);
            }
        });
        panelPlay.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelPlayMouseDragged(evt);
            }
        });
        panelPlay.setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("区域设置"));

        jCheckBoxGetMotionArea.setText("显示移动侦测区域");
        jCheckBoxGetMotionArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxGetMotionAreaActionPerformed(evt);
            }
        });

        jCheckBoxSetMotionArea.setText("设置移动侦测区域");
        jCheckBoxSetMotionArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxSetMotionAreaActionPerformed(evt);
            }
        });

        jLabel1.setText("灵敏度");

        jComboBoxSensitive.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "关闭", "0--最低", "1", "2", "3", "4", "5--最高" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxGetMotionArea)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxSetMotionArea)
                    .addComponent(jComboBoxSensitive, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxGetMotionArea)
                    .addComponent(jCheckBoxSetMotionArea))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxSensitive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("触发录像通道"));
        jPanel2.setPreferredSize(new java.awt.Dimension(304, 97));

        jScrollPane1.setViewportView(jListTraggerRecord);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("报警处理方式"));

        jCheckBoxMonitorAlarm.setText("监视器警告");

        jCheckBoxCenter.setText("上传中心");

        jCheckBoxAudioAlarm.setText("声音警告");

        jCheckBoxJPEG.setText("Email JPEG");

        jCheckBoxAlarmout.setText("触发报警输出");

        jScrollPane2.setViewportView(jListTraggerAlarmOut);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxMonitorAlarm)
                    .addComponent(jCheckBoxCenter)
                    .addComponent(jCheckBoxJPEG)
                    .addComponent(jCheckBoxAudioAlarm))
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxAlarmout)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxMonitorAlarm)
                    .addComponent(jCheckBoxAlarmout))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jCheckBoxAudioAlarm)
                        .addGap(2, 2, 2)
                        .addComponent(jCheckBoxCenter)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBoxJPEG))
                    .addComponent(jScrollPane2, 0, 0, Short.MAX_VALUE)))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("布防时间"));

        jLabel8.setText("时间段4");

        jLabel9.setText("时间段3");

        jLabel10.setText("时间段2");

        jLabel11.setText("时间段5");

        jLabel12.setText("时间段6");

        jLabel13.setText("时间段7");

        jLabel14.setText("时间段8");

        jLabel16.setText("时间段1");

        jLabel15.setText("时");

        jLabel17.setText("分 --");

        jLabel18.setText("时");

        jLabel19.setText("分");

        jLabel33.setText("分");

        jLabel34.setText("时");

        jLabel35.setText("分 --");

        jLabel36.setText("时");

        jLabel37.setText("分");

        jLabel38.setText("时");

        jLabel39.setText("分 --");

        jLabel40.setText("时");

        jLabel41.setText("分");

        jLabel42.setText("时");

        jLabel43.setText("分 --");

        jLabel44.setText("时");

        jLabel45.setText("分");

        jLabel46.setText("时");

        jLabel47.setText("分 --");

        jLabel48.setText("时");

        jLabel49.setText("分");

        jLabel50.setText("时");

        jLabel51.setText("分 --");

        jLabel52.setText("时");

        jButtonConfirm.setText("确定");
        jButtonConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmActionPerformed(evt);
            }
        });

        jLabel53.setText("时");

        jLabel54.setText("分 --");

        jLabel55.setText("分");

        jLabel56.setText("时");

        jLabel57.setText("分");

        jLabel58.setText("时");

        jLabel59.setText("时");

        jLabel60.setText("分 --");

        jComboBoxDate.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" }));
        jComboBoxDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxDateActionPerformed(evt);
            }
        });

        jLabel7.setText("日期");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jTextFieldInBeginHour1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldInBeginMin1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jTextFieldInBeginHour2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldInBeginMin2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jTextFieldInBeginHour3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldInBeginMin3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jTextFieldInBeginHour4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldInBeginMin4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBoxDate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTextFieldInEndHour1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldInEndMin1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTextFieldInEndHour2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldInEndMin2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTextFieldInEndHour3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldInEndMin3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTextFieldInEndHour4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldInEndMin4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(60, 60, 60)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jTextFieldInBeginHour5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldInBeginMin5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jTextFieldInBeginHour6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldInBeginMin6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jTextFieldInEndHour5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jTextFieldInEndHour6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTextFieldInBeginHour7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldInBeginMin7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldInEndHour7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jTextFieldInBeginHour8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldInBeginMin8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldInEndHour8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldInEndMin5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldInEndMin6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldInEndMin7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldInEndMin8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jButtonConfirm))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBoxDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldInEndHour1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(jTextFieldInEndMin1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldInEndHour2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34)
                            .addComponent(jTextFieldInEndMin2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldInEndHour3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38)
                            .addComponent(jTextFieldInEndMin3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldInEndHour4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel42)
                            .addComponent(jTextFieldInEndMin4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel41)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel35)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel39)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel43))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldInBeginHour1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(jTextFieldInBeginMin1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldInBeginHour2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36)
                            .addComponent(jTextFieldInBeginMin2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldInBeginHour3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel40)
                            .addComponent(jTextFieldInBeginMin3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldInBeginHour4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel44)
                            .addComponent(jTextFieldInBeginMin4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel10)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel9)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel8))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(15, 15, 15)
                                .addComponent(jLabel12))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldInEndHour5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel46))
                                .addGap(9, 9, 9)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldInEndHour6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel50)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldInBeginHour5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel48)
                                    .addComponent(jTextFieldInBeginMin5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel47))
                                .addGap(9, 9, 9)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldInBeginHour6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel52)
                                    .addComponent(jTextFieldInBeginMin6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel51))))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(15, 15, 15)
                                .addComponent(jLabel14))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldInEndHour7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel56)
                                    .addComponent(jTextFieldInBeginHour7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel53)
                                    .addComponent(jTextFieldInBeginMin7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel54))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldInEndHour8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel58)
                                    .addComponent(jTextFieldInBeginHour8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel59)
                                    .addComponent(jTextFieldInBeginMin8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel60)))))
                    .addComponent(jLabel45)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTextFieldInEndMin5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldInEndMin6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel49))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldInEndMin7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel55))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldInEndMin8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel57))))
                .addGap(10, 10, 10)
                .addComponent(jButtonConfirm))
        );

        jButtonSave.setText("确定");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jButtonExit.setText("退出");
        jButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(449, Short.MAX_VALUE)
                .addComponent(jButtonSave)
                .addGap(26, 26, 26)
                .addComponent(jButtonExit)
                .addGap(49, 49, 49))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSave)
                    .addComponent(jButtonExit))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*************************************************
    函数:      "日期"  组合框事件响应函数
    函数描述:   显示对应日期布防时间
     *************************************************/
    private void jComboBoxDateActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jComboBoxDateActionPerformed
    {//GEN-HEADEREND:event_jComboBoxDateActionPerformed
        if (m_bInitialed)
        {
            showAlarmTime();
        }
    }//GEN-LAST:event_jComboBoxDateActionPerformed

    /*************************************************
    函数:      "显示移动侦测区域"  多选框事件响应函数
    函数描述:   显示移动侦测区域
     *************************************************/
    private void jCheckBoxGetMotionAreaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxGetMotionAreaActionPerformed
    {//GEN-HEADEREND:event_jCheckBoxGetMotionAreaActionPerformed
        	if (m_lPlayHandle.intValue() < 0)
	{
		return;
	}
	if (jCheckBoxSetMotionArea.isSelected())
	{
		jCheckBoxSetMotionArea.setSelected(false);
		m_bDrawArea = false;
	}
	if (jCheckBoxGetMotionArea.isSelected())
	{
		hCNetSDK.NET_DVR_RigisterDrawFun(m_lPlayHandle,null, 0);
            
                try
            {
                Thread.sleep(200);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(JDialogMotionDetect.class.getName()).log(Level.SEVERE, null, ex);
            }
		
                hCNetSDK.NET_DVR_RigisterDrawFun(m_lPlayHandle,MotionDetectGetCallBack, 0);
	}
	else
	{
		hCNetSDK.NET_DVR_RigisterDrawFun(m_lPlayHandle,null, 0);
	}	
    }//GEN-LAST:event_jCheckBoxGetMotionAreaActionPerformed

    /*************************************************
    函数:      "设置移动侦测区域"  多选框事件响应函数
    函数描述:   设置移动侦测区域
     *************************************************/
    private void jCheckBoxSetMotionAreaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxSetMotionAreaActionPerformed
    {//GEN-HEADEREND:event_jCheckBoxSetMotionAreaActionPerformed
       	//Set motion detect zone
	if (m_lPlayHandle.intValue() < 0)
	{
		return;
	}
	if (jCheckBoxGetMotionArea.isSelected())
	{
		jCheckBoxGetMotionArea.setSelected(false);
	}
	if (jCheckBoxSetMotionArea.isSelected())
	{
		hCNetSDK.NET_DVR_RigisterDrawFun(m_lPlayHandle,null,0);
		g_iDetectIndex = 0;
		m_bDrawArea = true;
		m_bSetMotion = true;
	}
	else
	{
		m_bDrawArea = false;
		hCNetSDK.NET_DVR_RigisterDrawFun(m_lPlayHandle,null,0);
	}
    }//GEN-LAST:event_jCheckBoxSetMotionAreaActionPerformed

    /*************************************************
    函数:      预览窗口单击  事件响应函数
    函数描述:   注册回调函数开始画图
     *************************************************/
    private void panelPlayMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_panelPlayMousePressed
    {//GEN-HEADEREND:event_panelPlayMousePressed
       	if (m_bDrawArea)
	{
			POINT point = new POINT(evt.getX(), evt.getY());
                       hCNetSDK.NET_DVR_RigisterDrawFun(m_lPlayHandle, MotionDetectSetCallBack, 0);
			if (g_iDetectIndex >= MAX_MOTION_NUM)
			{
				g_iDetectIndex = 0;
			}

			g_rectMotionDetectMouse[g_iDetectIndex].left = point.x/g_dwPrecision*g_dwPrecision;
			g_rectMotionDetectMouse[g_iDetectIndex].top = point.y/g_dwPrecision*g_dwPrecision;
			g_rectMotionDetectMouse[g_iDetectIndex].right = g_rectMotionDetectMouse[g_iDetectIndex].left;
			g_rectMotionDetectMouse[g_iDetectIndex].bottom = g_rectMotionDetectMouse[g_iDetectIndex].top;

			g_rectMotionDetectSet[g_iDetectIndex].left = point.x/g_dwPrecision*g_dwPrecision;
			g_rectMotionDetectSet[g_iDetectIndex].top = point.y/g_dwPrecision*g_dwPrecision;

			g_rectMotionDetectSet[g_iDetectIndex].right = point.x/g_dwPrecision*g_dwPrecision+1;
			g_rectMotionDetectSet[g_iDetectIndex].bottom = point.y/g_dwPrecision*g_dwPrecision+1;
			g_iDetectIndex ++;
	}
    }//GEN-LAST:event_panelPlayMousePressed

    /*************************************************
    函数:      预览窗口鼠标按下移动  事件响应函数
    函数描述:   画框
     *************************************************/
    private void panelPlayMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_panelPlayMouseDragged
    {//GEN-HEADEREND:event_panelPlayMouseDragged
        if (m_bDrawArea)
        {
            POINT point = new POINT(evt.getX(), evt.getY());
            g_rectMotionDetectMouse[g_iDetectIndex - 1].right = point.x / g_dwPrecision * g_dwPrecision;
            g_rectMotionDetectMouse[g_iDetectIndex - 1].bottom = point.y / g_dwPrecision * g_dwPrecision;

            g_rectMotionDetectSet[g_iDetectIndex - 1].right = point.x / g_dwPrecision * g_dwPrecision;
            g_rectMotionDetectSet[g_iDetectIndex - 1].bottom = point.y / g_dwPrecision * g_dwPrecision;
        }
    }//GEN-LAST:event_panelPlayMouseDragged

    /*************************************************
    函数:      布防时间  "确定" 按钮  单击 事件响应函数
    函数描述:   保存对应日期布防时间
     *************************************************/
    private void jButtonConfirmActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonConfirmActionPerformed
    {//GEN-HEADEREND:event_jButtonConfirmActionPerformed
        int iWeekDay = jComboBoxDate.getSelectedIndex();
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[0].byStartHour = (byte) Integer.parseInt(jTextFieldInBeginHour1.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[0].byStartMin = (byte) Integer.parseInt(jTextFieldInBeginMin1.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[0].byStopHour = (byte) Integer.parseInt(jTextFieldInEndHour1.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[0].byStopMin = (byte) Integer.parseInt(jTextFieldInEndMin1.getText());

        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[1].byStartHour = (byte) Integer.parseInt(jTextFieldInBeginHour2.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[1].byStartMin = (byte) Integer.parseInt(jTextFieldInBeginMin2.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[1].byStopHour = (byte) Integer.parseInt(jTextFieldInEndHour2.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[1].byStopMin = (byte) Integer.parseInt(jTextFieldInEndMin2.getText());

        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[2].byStartHour = (byte) Integer.parseInt(jTextFieldInBeginHour3.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[2].byStartMin = (byte) Integer.parseInt(jTextFieldInBeginMin3.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[2].byStopHour = (byte) Integer.parseInt(jTextFieldInEndHour3.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[2].byStopMin = (byte) Integer.parseInt(jTextFieldInEndMin3.getText());

        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[3].byStartHour = (byte) Integer.parseInt(jTextFieldInBeginHour4.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[3].byStartMin = (byte) Integer.parseInt(jTextFieldInBeginMin4.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[3].byStopHour = (byte) Integer.parseInt(jTextFieldInEndHour4.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[3].byStopMin = (byte) Integer.parseInt(jTextFieldInEndMin4.getText());

        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[4].byStartHour = (byte) Integer.parseInt(jTextFieldInBeginHour5.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[4].byStartMin = (byte) Integer.parseInt(jTextFieldInBeginMin5.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[4].byStopHour = (byte) Integer.parseInt(jTextFieldInEndHour5.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[4].byStopMin = (byte) Integer.parseInt(jTextFieldInEndMin5.getText());

        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[5].byStartHour = (byte) Integer.parseInt(jTextFieldInBeginHour6.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[5].byStartMin = (byte) Integer.parseInt(jTextFieldInBeginMin6.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[5].byStopHour = (byte) Integer.parseInt(jTextFieldInEndHour6.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[5].byStopMin = (byte) Integer.parseInt(jTextFieldInEndMin6.getText());

        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[6].byStartHour = (byte) Integer.parseInt(jTextFieldInBeginHour7.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[6].byStartMin = (byte) Integer.parseInt(jTextFieldInBeginMin7.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[6].byStopHour = (byte) Integer.parseInt(jTextFieldInEndHour7.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[6].byStopMin = (byte) Integer.parseInt(jTextFieldInEndMin7.getText());

        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[7].byStartHour = (byte) Integer.parseInt(jTextFieldInBeginHour8.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[7].byStartMin = (byte) Integer.parseInt(jTextFieldInBeginMin8.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[7].byStopHour = (byte) Integer.parseInt(jTextFieldInEndHour8.getText());
        m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[7].byStopMin = (byte) Integer.parseInt(jTextFieldInEndMin8.getText());
    }//GEN-LAST:event_jButtonConfirmActionPerformed

    /*************************************************
    函数:      "退出" 按钮  单击事件响应函数
    函数描述:   销毁对话框
     *************************************************/
    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonExitActionPerformed
    {//GEN-HEADEREND:event_jButtonExitActionPerformed
     	if (m_lPlayHandle.intValue() >= 0)
	{
		hCNetSDK.NET_DVR_RigisterDrawFun(m_lPlayHandle, null, 0);
		hCNetSDK.NET_DVR_StopRealPlay(m_lPlayHandle);
		m_lPlayHandle.setValue(-1);
	}
        dispose();
    }//GEN-LAST:event_jButtonExitActionPerformed

    /*************************************************
    函数:      "确定" 按钮  单击事件响应函数
    函数描述:   保存设置到结构体
     *************************************************/
    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonSaveActionPerformed
    {//GEN-HEADEREND:event_jButtonSaveActionPerformed
        int i = 0,j = 0,k = 0;
        if (m_bSetMotion)
	{
		//clear
		for (i = 0; i < 64; i++)
		{
			for (j = 0; j < 96; j++)
			{
				 m_struPicCfg.struMotion.byMotionScope[i].byMotionScope[j] = 0;
			}
		}
		//save zone settings on the device
		for (k = 0; k < g_iDetectIndex; k++)
		{
			if (g_rectMotionDetectMouse[k].top <= g_rectMotionDetectMouse[k].bottom)
			{
				if (g_rectMotionDetectMouse[k].left <= g_rectMotionDetectMouse[k].right)
				{//draw from top-left to bottom-right
					for (i = g_rectMotionDetectMouse[k].top/g_dwPrecision;i<64 && i<g_rectMotionDetectMouse[k].bottom/g_dwPrecision; i++)
					{
						for (j = g_rectMotionDetectMouse[k].left/g_dwPrecision; j < g_rectMotionDetectMouse[k].right/g_dwPrecision; j++)
						{
							m_struPicCfg.struMotion.byMotionScope[i].byMotionScope[j] = 1;
						}
					}
				}
				else
				{//draw from top-right to bottom-left
					for (i = g_rectMotionDetectMouse[k].top/g_dwPrecision; i<64 && i < g_rectMotionDetectMouse[k].bottom/g_dwPrecision; i++)
					{
						for (j = g_rectMotionDetectMouse[k].right/g_dwPrecision; j < g_rectMotionDetectMouse[k].left/g_dwPrecision; j++)
						{
						        m_struPicCfg.struMotion.byMotionScope[i].byMotionScope[j] = 1;
						}
					}
				}
			}
			else
			{
				if (g_rectMotionDetectMouse[k].left <= g_rectMotionDetectMouse[k].right)
				{//draw from bottom-left to top-right
					for (i = g_rectMotionDetectMouse[k].bottom/g_dwPrecision; i < g_rectMotionDetectMouse[k].top/g_dwPrecision; i++)
					{
						for (j = g_rectMotionDetectMouse[k].left/g_dwPrecision; j < g_rectMotionDetectMouse[k].right/g_dwPrecision; j++)
						{
							  m_struPicCfg.struMotion.byMotionScope[i].byMotionScope[j] = 1;
						}
					}
				}
				else
				{//draw from bottom-right to top-left
					for (i = g_rectMotionDetectMouse[k].bottom/g_dwPrecision; i < g_rectMotionDetectMouse[k].top/g_dwPrecision; i++)
					{
						for (j = g_rectMotionDetectMouse[k].right/g_dwPrecision; j < g_rectMotionDetectMouse[k].left/g_dwPrecision; j++)
						{
							  m_struPicCfg.struMotion.byMotionScope[i].byMotionScope[j] = 1;
						}
					}
				}
			}
		}
	}

	if (jComboBoxSensitive.getSelectedIndex() == 0)
	{
		 m_struPicCfg.struMotion.byMotionSensitive = -1;
	}
	else
	{
		m_struPicCfg.struMotion.byMotionSensitive = (byte)(jComboBoxSensitive.getSelectedIndex() - 1);
	}

	m_struPicCfg.struMotion.struMotionHandleType.dwHandleType = 0;
	m_struPicCfg.struMotion.struMotionHandleType.dwHandleType |= ((jCheckBoxMonitorAlarm.isSelected()?1:0) << 0);
	m_struPicCfg.struMotion.struMotionHandleType.dwHandleType |= ((jCheckBoxAudioAlarm.isSelected()?1:0) << 1);
	m_struPicCfg.struMotion.struMotionHandleType.dwHandleType |= ((jCheckBoxCenter.isSelected()?1:0) << 2);
	m_struPicCfg.struMotion.struMotionHandleType.dwHandleType |= ((jCheckBoxAlarmout.isSelected()?1:0) << 3);
	m_struPicCfg.struMotion.struMotionHandleType.dwHandleType |= ((jCheckBoxJPEG.isSelected()?1:0) << 4);

	for (i = 0; i < HCNetSDK.MAX_ALARMOUT_V30; i++)
	{
		m_struPicCfg.struMotion.struMotionHandleType.byRelAlarmOut[i] = (byte)(m_traggerAlarmOut[i].getCheck()?1:0);
	}

	for (i = 0; i< HCNetSDK.MAX_CHANNUM_V30; i++)
	{
		m_struPicCfg.struMotion.byRelRecordChan[i] = (byte)(m_traggerRecord[i].getCheck()?1:0);
	}
    }//GEN-LAST:event_jButtonSaveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonConfirm;
    private javax.swing.JButton jButtonExit;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JCheckBox jCheckBoxAlarmout;
    private javax.swing.JCheckBox jCheckBoxAudioAlarm;
    private javax.swing.JCheckBox jCheckBoxCenter;
    private javax.swing.JCheckBox jCheckBoxGetMotionArea;
    private javax.swing.JCheckBox jCheckBoxJPEG;
    private javax.swing.JCheckBox jCheckBoxMonitorAlarm;
    private javax.swing.JCheckBox jCheckBoxSetMotionArea;
    private javax.swing.JComboBox jComboBoxDate;
    private javax.swing.JComboBox jComboBoxSensitive;
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
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
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
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jListTraggerAlarmOut;
    private javax.swing.JList jListTraggerRecord;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
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
    private java.awt.Panel panelPlay;
    // End of variables declaration//GEN-END:variables

    /*************************************************
    类:        FDrawFunSet
    函数描述:   设置移动侦测区域回调函数
     *************************************************/
    class FDrawFunSet implements HCNetSDK.FDrawFun
    {
        public void invoke(NativeLong lRealHandle, HDC hDc, int dwUser)
        {
        int i = 0;
	for (i=0; i < g_iDetectIndex; i++)
	{
		uSer.DrawEdge(hDc, g_rectMotionDetectSet[i], USER32.BDR_SUNKENOUTER, USER32.BF_RECT);
	}
	gDi.SetBkMode(hDc,GDI32.TRANSPARENT);
        }
    }

     /*************************************************
    类:        FDrawFunGet
    函数描述:   显示移动侦测区域回调函数
     *************************************************/
        class FDrawFunGet implements HCNetSDK.FDrawFun
    {
        public void invoke(NativeLong lRealHandle, HDC hDc, int dwUser)
        {
        RECT rect = new RECT();
            int i = 0, j = 0;
            POINT point = new POINT();
            for (i = 0; i < 64; i++)
            {
                for (j = 0; j < 96; j++)
                {
                    if (m_struPicCfg.struMotion.byMotionScope[i].byMotionScope[j] == 1)
                    {
                        point.x = j * g_dwPrecision;
                        point.y = i * g_dwPrecision;
                        rect.left = point.x;
                        rect.top = point.y;
                        rect.right = point.x + g_dwPrecision;
                        rect.bottom = point.y + g_dwPrecision;
                        uSer.DrawEdge(hDc, rect, USER32.BDR_SUNKENOUTER, USER32.BF_RECT);
                    }
                }
            }
            gDi.SetBkMode(hDc, GDI32.TRANSPARENT);
        }
    }

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
