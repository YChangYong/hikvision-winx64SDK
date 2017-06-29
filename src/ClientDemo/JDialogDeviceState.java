/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JDialogDeviceState.java
 *
 * Created on 2009-10-30, 9:00:41
 */
/**
 *
 * @author Administrator
 */

package ClientDemo;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*****************************************************************************
 *类 ：JDialogDeviceState
 *类描述 ：设备状态显示
 ****************************************************************************/
public class JDialogDeviceState extends javax.swing.JDialog
{

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    private NativeLong m_lUserID;//用户句柄
    private HCNetSDK.NET_DVR_WORKSTATE_V30 m_strWorkState;
    private HCNetSDK.NET_DVR_IPPARACFG m_strIpparaCfg;
    private HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;
    private boolean[] bChannelEnabled;//用来获取对应通道号的通道是否有效
    private int iTotlaLink;//总链接个数
    private String m_sDeviceIP;

    /*************************************************
    函数:      JDialogDeviceState
    函数描述:	构造函数   Creates new form JDialogDeviceState
     *************************************************/
    public JDialogDeviceState(java.awt.Frame parent, boolean modal, NativeLong lUserID, HCNetSDK.NET_DVR_DEVICEINFO_V30 strDeviceInfo, String sIP)
    {
        super(parent, modal);
        m_lUserID = lUserID;
        m_strDeviceInfo = strDeviceInfo;
        bChannelEnabled = new boolean[HCNetSDK.MAX_ANALOG_CHANNUM];//默认初始值为false
        iTotlaLink = 0;
        m_sDeviceIP = sIP;
        initComponents();
        showState();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanelChannelState = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableChannelState = new javax.swing.JTable();
        jPanelHDState = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableHDState = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldDeviceIP = new javax.swing.JTextField();
        jTextFieldDeviceState = new javax.swing.JTextField();
        jTextFieldTotalLink = new javax.swing.JTextField();
        jButtonRefresh = new javax.swing.JButton();
        jButtonExit = new javax.swing.JButton();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("设备状态");
        getContentPane().setLayout(null);

        jPanelChannelState.setBorder(javax.swing.BorderFactory.createTitledBorder("通道状态"));

        jTableChannelState.setModel(this.initialChannelTableModel());
        jScrollPane1.setViewportView(jTableChannelState);

        javax.swing.GroupLayout jPanelChannelStateLayout = new javax.swing.GroupLayout(jPanelChannelState);
        jPanelChannelState.setLayout(jPanelChannelStateLayout);
        jPanelChannelStateLayout.setHorizontalGroup(
            jPanelChannelStateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelChannelStateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelChannelStateLayout.setVerticalGroup(
            jPanelChannelStateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelChannelStateLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        getContentPane().add(jPanelChannelState);
        jPanelChannelState.setBounds(10, 80, 650, 280);

        jPanelHDState.setBorder(javax.swing.BorderFactory.createTitledBorder("硬盘状态"));

        jTableHDState.setModel(this.initialHDTableModel());
        jScrollPane3.setViewportView(jTableHDState);

        javax.swing.GroupLayout jPanelHDStateLayout = new javax.swing.GroupLayout(jPanelHDState);
        jPanelHDState.setLayout(jPanelHDStateLayout);
        jPanelHDStateLayout.setHorizontalGroup(
            jPanelHDStateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHDStateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelHDStateLayout.setVerticalGroup(
            jPanelHDStateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHDStateLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanelHDState);
        jPanelHDState.setBounds(10, 360, 650, 270);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("设备状态"));
        jPanel1.setLayout(null);

        jLabel2.setText("IP地址");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(30, 20, 50, 15);

        jLabel3.setText("设备状态");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(230, 20, 60, 15);

        jLabel4.setText("总链接路数");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(430, 20, 70, 15);
        jPanel1.add(jTextFieldDeviceIP);
        jTextFieldDeviceIP.setBounds(100, 20, 100, 21);
        jPanel1.add(jTextFieldDeviceState);
        jTextFieldDeviceState.setBounds(310, 20, 70, 21);
        jPanel1.add(jTextFieldTotalLink);
        jTextFieldTotalLink.setBounds(510, 20, 70, 21);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 20, 650, 50);

        jButtonRefresh.setText("刷新");
        jButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonRefresh);
        jButtonRefresh.setBounds(470, 640, 60, 23);

        jButtonExit.setText("退出");
        jButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExitActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonExit);
        jButtonExit.setBounds(570, 640, 60, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*************************************************
    函数:      "刷新"  按钮单击响应函数
    函数描述:	刷新设备状态
     *************************************************/
 private void jButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshActionPerformed
     showState();
 }//GEN-LAST:event_jButtonRefreshActionPerformed

    /*************************************************
    函数:      "退出"  按钮单击响应函数
    函数描述:	销毁窗口
     *************************************************/
 private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExitActionPerformed
     this.dispose();
 }//GEN-LAST:event_jButtonExitActionPerformed

    /*************************************************
    函数:      initialChannelTableModel
    函数描述:	初始化通道状态列表
     *************************************************/
    public DefaultTableModel initialChannelTableModel()
    {
        String tabeTile[];
        tabeTile = new String[]
                {
                    "通道号", "录像状态", "信号状态", "硬件状态", "连接数", "当前码率(bps)", "IPC链接数"
                };
        DefaultTableModel channelTableModel = new DefaultTableModel(tabeTile, 0);
        return channelTableModel;
    }

    /*************************************************
    函数:      initialHDTableModel
    函数描述:	初始化硬盘状态列表
     *************************************************/
    public DefaultTableModel initialHDTableModel()
    {
        String tabeTile[];
        tabeTile = new String[]
                {
                    "硬盘号", "硬盘容量(MB)", "剩余空间(MB)", "硬盘状态"
                };
        DefaultTableModel HDTableModel = new DefaultTableModel(tabeTile, 0);
        return HDTableModel;
    }

    /*************************************************
    函数:      showState
    函数描述:	调用接口获取设备状态并显示
     *************************************************/
    private void showState()
    {
        ((DefaultTableModel) jTableChannelState.getModel()).getDataVector().removeAllElements();//清空通道状态列表
        ((DefaultTableModel) jTableHDState.getModel()).getDataVector().removeAllElements();//清空硬盘状态列表
        iTotlaLink = 0;//清空总链接数目

        m_strWorkState = new HCNetSDK.NET_DVR_WORKSTATE_V30();//调用接口获取设备工作状态
        boolean getDVRConfigSuc = hCNetSDK.NET_DVR_GetDVRWorkState_V30(m_lUserID, m_strWorkState);
        if (getDVRConfigSuc != true)
        {
            System.out.println(hCNetSDK.NET_DVR_GetLastError());
            JOptionPane.showMessageDialog(this, "获取设备状态失败");
        }

        IntByReference ibrBytesReturned = new IntByReference(0);//获取IP接入配置参数
        getDVRConfigSuc = false;
        m_strIpparaCfg = new HCNetSDK.NET_DVR_IPPARACFG();
        m_strIpparaCfg.write();
        Pointer lpIpParaConfig = m_strIpparaCfg.getPointer();
        getDVRConfigSuc = hCNetSDK.NET_DVR_GetDVRConfig(m_lUserID, HCNetSDK.NET_DVR_GET_IPPARACFG, new NativeLong(0), lpIpParaConfig, m_strIpparaCfg.size(), ibrBytesReturned);
        m_strIpparaCfg.read();
        if (getDVRConfigSuc != true)
        {//设备不支持,则表示没有IP通道
            for (int iChannum = 0; iChannum < m_strDeviceInfo.byChanNum; iChannum++)
            {
                bChannelEnabled[iChannum] = true;
            }
        } else
        {//包含IP通道
            for (int iChannum = 0; iChannum < m_strDeviceInfo.byChanNum; iChannum++)
            {
                bChannelEnabled[iChannum] = (m_strIpparaCfg.byAnalogChanEnable[iChannum] == 1) ? true : false;
            }
        }

        //显示设备IP;
        jTextFieldDeviceIP.setText(m_sDeviceIP);

        //显示设备状态
        switch (m_strWorkState.dwDeviceStatic)
        {
            case 0:
                jTextFieldDeviceState.setText("正常");
                break;
            case 1:
                jTextFieldDeviceState.setText("CPU占用率太高,超过85%");
                break;
            case 2:
                jTextFieldDeviceState.setText("硬件错误");
                break;
            default:
                jTextFieldDeviceState.setText("未知");
                break;
        }

        //显示模拟通道状态
        for (int iChannum = 0; iChannum < m_strDeviceInfo.byChanNum; iChannum++)
        {
            DefaultTableModel channelTableModel = ((DefaultTableModel) jTableChannelState.getModel());//获取表格模型
            Vector<String> newRow = new Vector<String>();
            if (bChannelEnabled[iChannum] == true)
            { //通道有效
                newRow.add("Camera" + (iChannum + m_strDeviceInfo.byStartChan));//添加模拟通道号

                //是否录像
                if (0 == m_strWorkState.struChanStatic[iChannum].byRecordStatic)
                {
                    newRow.add("不录像");
                } else
                {
                    if (1 == m_strWorkState.struChanStatic[iChannum].byRecordStatic)
                    {
                        newRow.add("录像");
                    }
                }
                //信号状态
                if (0 == m_strWorkState.struChanStatic[iChannum].bySignalStatic)
                {
                    newRow.add("正常");
                } else
                {
                    if (1 == m_strWorkState.struChanStatic[iChannum].bySignalStatic)
                    {
                        newRow.add("信号丢失");
                    }
                }
                //硬件状态
                if (0 == m_strWorkState.struChanStatic[iChannum].byHardwareStatic)
                {
                    newRow.add("正常");
                } else
                {
                    if (1 == m_strWorkState.struChanStatic[iChannum].byHardwareStatic)
                    {
                        newRow.add("异常");
                    }
                }
                //连接数
                newRow.add(m_strWorkState.struChanStatic[iChannum].dwLinkNum + "");
                //此处增加总连接数
                iTotlaLink += m_strWorkState.struChanStatic[iChannum].dwLinkNum;
                //当前码率
                newRow.add(m_strWorkState.struChanStatic[iChannum].dwBitRate + "");
                //IPC连接数
                newRow.add(m_strWorkState.struChanStatic[iChannum].dwIPLinkNum + "");
            } else
            {
                continue;
            }
            channelTableModel.getDataVector().add(newRow);
            ((DefaultTableModel) jTableHDState.getModel()).fireTableStructureChanged();
        }

        //显示IP通道状态
        //首先获取IP参数,如果IP参数对应通道参数的byEnable为有效,则该通道有效,
        //再从工作参数结构体中取得相关状态参数,模拟通道号:0-31,IP通道号.32-64
        for (int iChannum = 0; iChannum < 32; iChannum++)
        {
            DefaultTableModel channelTableModel = ((DefaultTableModel) jTableChannelState.getModel());//获取表格模型
            Vector<String> newRow = new Vector<String>();
            if (m_strIpparaCfg.struIPChanInfo[iChannum].byEnable == 1)
            {//判断对应IP通道是否有效
                newRow.add("IPCamera" + (iChannum + 1));//添加IP通道号
                //是否录像参数
                if (0 == m_strWorkState.struChanStatic[32 + iChannum].byRecordStatic)
                {
                    newRow.add("不录像");
                } else
                {
                    if (1 == m_strWorkState.struChanStatic[32 + iChannum].byRecordStatic)
                    {
                        newRow.add("录像");
                    }
                }
                //信号状态
                if (0 == m_strWorkState.struChanStatic[32 + iChannum].bySignalStatic)
                {
                    newRow.add("正常");
                } else
                {
                    if (1 == m_strWorkState.struChanStatic[32 + iChannum].bySignalStatic)
                    {
                        newRow.add("信号丢失");
                    }
                }
                //硬件状态
                if (0 == m_strWorkState.struChanStatic[32 + iChannum].byHardwareStatic)
                {
                    newRow.add("正常");
                } else
                {
                    if (1 == m_strWorkState.struChanStatic[32 + iChannum].byHardwareStatic)
                    {
                        newRow.add("异常");
                    }
                }
                //连接数
                newRow.add(m_strWorkState.struChanStatic[32 + iChannum].dwLinkNum + "");
                //此处增加总连接数
                iTotlaLink += m_strWorkState.struChanStatic[32 + iChannum].dwLinkNum;
                //当前码率
                newRow.add(m_strWorkState.struChanStatic[32 + iChannum].dwBitRate + "");
                //IPC连接数
                newRow.add(m_strWorkState.struChanStatic[32 + iChannum].dwIPLinkNum + "");
                channelTableModel.getDataVector().add(newRow);
                ((DefaultTableModel) jTableHDState.getModel()).fireTableStructureChanged();
            }
        }

        //显示总链接数
        jTextFieldTotalLink.setText(iTotlaLink + "");

        //显示硬盘状态
        for (int i = 0; i < HCNetSDK.MAX_DISKNUM_V30; i++)
        {
            DefaultTableModel HDTableModel = ((DefaultTableModel) jTableHDState.getModel());//获取表格模型
            Vector<String> newRow = new Vector<String>();

            newRow.add("硬盘" + (i + 1));//添加硬盘号
            newRow.add(m_strWorkState.struHardDiskStatic[i].dwVolume + "");//添加硬盘容量
            newRow.add(m_strWorkState.struHardDiskStatic[i].dwFreeSpace + "");//添加硬盘剩余空间
            if (m_strWorkState.struHardDiskStatic[i].dwVolume != 0)
            {//添加硬盘状态
                switch (m_strWorkState.struHardDiskStatic[i].dwHardDiskStatic)
                {
                    case 0:
                        newRow.add("活动");
                        break;
                    case 1:
                        newRow.add("休眠");
                        break;
                    case 2:
                        newRow.add("不正常");
                        break;

                    default:
                        break;
                }
            } else
            {
                newRow.add("");//添加文件名信息
            }
            HDTableModel.getDataVector().add(newRow);
            ((DefaultTableModel) jTableHDState.getModel()).fireTableStructureChanged();
        }

        jTableChannelState.repaint();
        jTableHDState.repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonExit;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelChannelState;
    private javax.swing.JPanel jPanelHDState;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTableChannelState;
    private javax.swing.JTable jTableHDState;
    private javax.swing.JTextField jTextFieldDeviceIP;
    private javax.swing.JTextField jTextFieldDeviceState;
    private javax.swing.JTextField jTextFieldTotalLink;
    // End of variables declaration//GEN-END:variables
}
