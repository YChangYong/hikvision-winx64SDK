/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JDialogFormatDisk.java
 *
 * Created on 2009-10-9, 16:15:51
 */
/**
 *
 * @author Administrator
 */

package ClientDemo;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.NativeLongByReference;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

/*****************************************************************************
 *类 ：JDialogFormatDisk
 *类描述 ：格式化硬盘
 ****************************************************************************/
public class JDialogFormatDisk extends javax.swing.JDialog
{

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    private NativeLong m_lUserId;
    private NativeLong m_lFormatHandle;
    private HCNetSDK.NET_DVR_HDCFG m_struHDCfg;

    /*************************************************
    函数:      JDialogFormatDisk
    函数描述:	构造函数   Creates new form JDialogFormatDisk
     *************************************************/
    public JDialogFormatDisk(java.awt.Frame parent, boolean modal, NativeLong userId)
    {
        super(parent, modal);
        initComponents();
        jProgressBar.setMinimum(0);
        jProgressBar.setMaximum(100);

        m_lUserId = userId;
        m_lFormatHandle = new NativeLong(-1);
        m_struHDCfg = new HCNetSDK.NET_DVR_HDCFG();
        m_struHDCfg.write();
        Pointer lpPicConfig = m_struHDCfg.getPointer();
        IntByReference ibrBytesReturned = new IntByReference(0);
        boolean getDVRConfigSuc = hCNetSDK.NET_DVR_GetDVRConfig(m_lUserId, HCNetSDK.NET_DVR_GET_HDCFG,
                new NativeLong(0), lpPicConfig, m_struHDCfg.size(), ibrBytesReturned);
        m_struHDCfg.read();
        if (getDVRConfigSuc != true)
        {
            System.out.println("获取硬盘信息失败");
        } else
        {
            jComboBoxDisk.addItem("全部硬盘");
            for (int i = 0; i < m_struHDCfg.dwHDCount; i++)
            {
                String s = "硬盘" + m_struHDCfg.struHDInfo[i].dwHDNo;
                jComboBoxDisk.addItem(s);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonFormat = new javax.swing.JButton();
        jComboBoxDisk = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jProgressBar = new javax.swing.JProgressBar();
        jLabel2 = new javax.swing.JLabel();
        jButtonExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("格式化硬盘");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButtonFormat.setText("格式化");
        jButtonFormat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFormatActionPerformed(evt);
            }
        });

        jLabel1.setText("盘符");

        jLabel3.setText("0");

        jLabel2.setText("进度:");

        jButtonExit.setText("退出");
        jButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxDisk, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                        .addGap(41, 41, 41))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonFormat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 144, Short.MAX_VALUE)
                        .addComponent(jButtonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonExit, jButtonFormat});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxDisk, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonFormat)
                    .addComponent(jButtonExit))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*************************************************
    函数:      "格式化"按钮响应函数
    函数描述:	调用接口格式化硬盘
     *************************************************/
    private void jButtonFormatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFormatActionPerformed
        NativeLongByReference pCurrentFormatDisk = new NativeLongByReference(new NativeLong(-1));
        NativeLongByReference pCurrentDiskPos = new NativeLongByReference(new NativeLong(-1));
        NativeLongByReference pFormatStatic = new NativeLongByReference(new NativeLong(-1));
        int iDiskNumber = jComboBoxDisk.getSelectedIndex();
        if (iDiskNumber == 0)
        {
            m_lFormatHandle = hCNetSDK.NET_DVR_FormatDisk(m_lUserId, new NativeLong(0xff));//0xFF表示所有硬盘
            if (m_lFormatHandle.intValue() < 0)
            {
                int err = hCNetSDK.NET_DVR_GetLastError();
                if (err == HCNetSDK.NET_DVR_DISK_FORMATING)
                {
                    JOptionPane.showMessageDialog(this, "硬盘正在格式化,不能启动操作");
                } else
                {
                    JOptionPane.showMessageDialog(this, "格式化失败");
                }
            } else
            {
                System.out.println("正在格式化!");
                do
                {
                    hCNetSDK.NET_DVR_GetFormatProgress(m_lFormatHandle, pCurrentFormatDisk, pCurrentDiskPos, pFormatStatic);
                    System.out.println("正在格式化硬盘:" + pCurrentFormatDisk.getValue().intValue());//正在格式化的硬盘号
                    jProgressBar.setValue(pCurrentDiskPos.getValue().intValue());
                    try
                    {//等待500ms
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e)
                    {
                    }
                } while (pFormatStatic.getValue().intValue() == 0);//正在格式化
                System.out.println("static" + pFormatStatic.getValue().intValue());
                if (pFormatStatic.getValue().intValue() == 2)
                {
                    JOptionPane.showMessageDialog(this, "格式化出错!");
                    return;
                }
                if (pFormatStatic.getValue().intValue() == 3)
                {
                    JOptionPane.showMessageDialog(this, "网络异常!");
                    return;
                }
                if (pFormatStatic.getValue().intValue() == 3)
                {
                    JOptionPane.showMessageDialog(this, "格式化完成!");
                    return;
                }
            }
        } else
        {//格式化单个硬盘
            m_lFormatHandle = hCNetSDK.NET_DVR_FormatDisk(m_lUserId, new NativeLong(m_struHDCfg.struHDInfo[iDiskNumber - 1].dwHDNo));
            if (m_lFormatHandle.intValue() < 0)
            {
                int err = hCNetSDK.NET_DVR_GetLastError();
                if (err == HCNetSDK.NET_DVR_DISK_FORMATING)
                {
                    JOptionPane.showMessageDialog(this, "硬盘正在格式化,不能启动操作");
                } else
                {
                    System.out.println(err);
                    JOptionPane.showMessageDialog(this, "格式化失败");
                }
            } else
            {
                System.out.println("正在格式化!");
                do
                {
                    hCNetSDK.NET_DVR_GetFormatProgress(m_lFormatHandle, pCurrentFormatDisk, pCurrentDiskPos, pFormatStatic);
                    jProgressBar.setValue(pCurrentDiskPos.getValue().intValue());
                    try
                    {//等待500ms
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e)
                    {
                    }
                } while (pFormatStatic.getValue().intValue() == 0);//正在格式化

                if (pFormatStatic.getValue().intValue() == 2)
                {
                    JOptionPane.showMessageDialog(this, "格式化出错!");
                    return;
                }
                if (pFormatStatic.getValue().intValue() == 3)
                {
                    JOptionPane.showMessageDialog(this, "网络异常!");
                    return;
                }
                if (pFormatStatic.getValue().intValue() == 3)
                {
                    JOptionPane.showMessageDialog(this, "格式化完成!");
                    return;
                }
            }
        }

    }//GEN-LAST:event_jButtonFormatActionPerformed

    /*************************************************
    函数:      "退出"  按钮响应函数
    函数描述:	销毁窗口
     *************************************************/
    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButtonExitActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonExit;
    private javax.swing.JButton jButtonFormat;
    private javax.swing.JComboBox jComboBoxDisk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar;
    // End of variables declaration//GEN-END:variables
}
