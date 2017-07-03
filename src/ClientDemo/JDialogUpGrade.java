/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JDialogUpGrade.java
 *
 * Created on 2009-10-29, 13:48:04
 */
/**
 *
 * @author Administrator
 */

package ClientDemo;

import com.sun.jna.NativeLong;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/*****************************************************************************
 *类 ：   JDialogUpGrade
 *类描述 ：设备升级
 ****************************************************************************/
public class JDialogUpGrade extends javax.swing.JDialog
{

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    private NativeLong m_lUserID;//用户ID
    private JFileChooser upgradeJFileChooser;//文件选择器
    private NativeLong m_lUpgradeHandle;//文件升级句柄
    Timer timer;//定时器

    /*************************************************
    函数:      JDialogUpGrade
    函数描述:	构造函数   Creates new form JDialogUpGrade
     *************************************************/
    public JDialogUpGrade(java.awt.Frame parent, boolean modal, NativeLong lUserID)
    {
        super(parent, modal);
        m_lUserID = lUserID;
        m_lUpgradeHandle = new NativeLong(-1);
        initComponents();
        jProgressBarUpgrade.setMinimum(0);
        jProgressBarUpgrade.setMaximum(100);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxNetEnvir = new javax.swing.JComboBox();
        jButtonSetNetEnvir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldFileDir = new javax.swing.JTextField();
        jButtonBrowse = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabelUpgradeState = new javax.swing.JLabel();
        jButtonUpgrade = new javax.swing.JButton();
        jButtonExit = new javax.swing.JButton();
        jProgressBarUpgrade = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("设备升级");
        getContentPane().setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setText("网络环境");

        jComboBoxNetEnvir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "LAN", "WAN" }));

        jButtonSetNetEnvir.setText("设置");
        jButtonSetNetEnvir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetNetEnvirActionPerformed(evt);
            }
        });

        jLabel2.setText("升级文件");

        jTextFieldFileDir.setText("c:\\digicap");

        jButtonBrowse.setText("浏览");
        jButtonBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBrowseActionPerformed(evt);
            }
        });

        jLabel3.setText("状态");

        jButtonUpgrade.setText("升级");
        jButtonUpgrade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpgradeActionPerformed(evt);
            }
        });

        jButtonExit.setText("退出");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jProgressBarUpgrade, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jButtonUpgrade, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(33, 33, 33)
                            .addComponent(jButtonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabelUpgradeState, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jButtonBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldFileDir, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jComboBoxNetEnvir, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(31, 31, 31)
                            .addComponent(jButtonSetNetEnvir, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxNetEnvir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSetNetEnvir))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldFileDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonBrowse)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabelUpgradeState, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressBarUpgrade, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonUpgrade)
                    .addComponent(jButtonExit))
                .addContainerGap())
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 10, 410, 210);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*************************************************
    函数:      "浏览" 按钮单击相应函数
    函数描述:	选择升级文件
     *************************************************/
    private void jButtonBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBrowseActionPerformed
        JFileChooser JFileChooser1 = new JFileChooser("c:/digicap");//启动一个文件选择器
        if (JFileChooser.APPROVE_OPTION == JFileChooser1.showOpenDialog(this))//如果文件选择完毕
        {
            openFile(JFileChooser1.getSelectedFile().getPath());//作为将来的接口
            String filepath = JFileChooser1.getSelectedFile().getPath();//获取被选择文件的路径
            jTextFieldFileDir.setText(filepath);//输出文件路径
            }
    }//GEN-LAST:event_jButtonBrowseActionPerformed

    /*************************************************
    函数:      "设置" 按钮单击相应函数
    函数描述:	设置网络环境
     *************************************************/
    private void jButtonSetNetEnvirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSetNetEnvirActionPerformed
        if (!hCNetSDK.NET_DVR_SetNetworkEnvironment(jComboBoxNetEnvir.getSelectedIndex()))
        {
            JOptionPane.showMessageDialog(this, "设置网络环境失败");
            return;
        }
    }//GEN-LAST:event_jButtonSetNetEnvirActionPerformed

    /*************************************************
    函数:      "升级" 按钮单击相应函数
    函数描述:	调用接口,开始升级
    *************************************************/
    private void jButtonUpgradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpgradeActionPerformed
        File fileUpgrade = new File(jTextFieldFileDir.getText());
        if (fileUpgrade.canRead() == false)
        {
            JOptionPane.showMessageDialog(this, "无效文件");
            return;
        }
        if (fileUpgrade.length() == 0)
        {
            JOptionPane.showMessageDialog(this, "文件为空");
            return;
        }
        m_lUpgradeHandle = hCNetSDK.NET_DVR_Upgrade(m_lUserID, jTextFieldFileDir.getText());
        if (m_lUpgradeHandle.intValue() < 0)
        {
            JOptionPane.showMessageDialog(this, "升级失败");
        } else
        {
            timer = new Timer();//新建定时器
            timer.schedule(new MyTask(), 0, 500);//0秒后开始响应函数
        }
    }//GEN-LAST:event_jButtonUpgradeActionPerformed

    /*************************************************
    类:      MyTask
    类描述:  定时器响应函数
     *************************************************/
    class MyTask extends java.util.TimerTask
    {//定时器函数 相当于c语言中的onTimer();

        @Override
        public void run()
        {
            int UpgradeStatic = hCNetSDK.NET_DVR_GetUpgradeState(m_lUpgradeHandle);
            int iPos = hCNetSDK.NET_DVR_GetUpgradeProgress(m_lUpgradeHandle);

            if (iPos > 0)
            {
                jProgressBarUpgrade.setValue(iPos);
            }
            if (UpgradeStatic == 2)
            {
                jLabelUpgradeState.setText("状态：正在升级设备，请等待......");
            } else
            {
                switch (UpgradeStatic)
                {
                    case -1:
                        jLabelUpgradeState.setText("升级失败");
                        break;
                    case 1:
                        jLabelUpgradeState.setText("状态：升级设备成功");
                        jProgressBarUpgrade.setValue(100);
                        break;
                    case 3:
                        jLabelUpgradeState.setText("状态：升级设备失败");
                        break;
                    case 4:
                        jLabelUpgradeState.setText("状态：网络断开,状态未知");
                        break;
                    case 5:
                        jLabelUpgradeState.setText("状态：升级文件语言版本不匹配");
                        break;
                    default:
                        break;
                }
                if (hCNetSDK.NET_DVR_CloseUpgradeHandle(m_lUpgradeHandle) == true)
                {
                    System.out.println("NET_DVR_CloseUpgradeHandle");
                }
                m_lUpgradeHandle = new NativeLong(-1);
                timer.cancel();//使用这个方法退出任务
            }
        }
    }

    void openFile(String fileName)
    {
        try
        {
            File file = new File(fileName);
            int size = (int) file.length();
            int chars_read = 0;
            FileReader in = new FileReader(file);
            char[] data = new char[size];
            while (in.ready())
            {
                chars_read += in.read(data, chars_read, size - chars_read);
                //read(目标数组、文件起始位置、文件结束位置)
                //返回读入的数据量
            }
            in.close();
        } catch (IOException e)
        {
            System.out.println(e.toString());
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBrowse;
    private javax.swing.JButton jButtonExit;
    private javax.swing.JButton jButtonSetNetEnvir;
    private javax.swing.JButton jButtonUpgrade;
    private javax.swing.JComboBox jComboBoxNetEnvir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelUpgradeState;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBarUpgrade;
    private javax.swing.JTextField jTextFieldFileDir;
    // End of variables declaration//GEN-END:variables
}
