/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JDialogShowString.java
 *
 * Created on 2009-9-18, 8:39:50
 */
/**
 *
 * @author Administrator
 */

package ClientDemo;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import javax.swing.JOptionPane;

/*****************************************************************************
 *类 ：   JDialogShowString
 *类描述 ：设置叠加字符
 ****************************************************************************/
public class JDialogShowString extends javax.swing.JDialog
{

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    private HCNetSDK.NET_DVR_SHOWSTRING_V30 m_strShowString;//叠加字符结构体
    private NativeLong lUserID;//用户ID
    private int iChannelNumber;//通道号

    /*************************************************
    函数:      JDialogShowString
    函数描述:	构造函数   Creates new form JDialogShowString
     *************************************************/
    public JDialogShowString(java.awt.Dialog parent, boolean modal, HCNetSDK.NET_DVR_SHOWSTRING_V30 strShowString, NativeLong UserID, int chanNumber)
    {
        super(parent, modal);
        m_strShowString = strShowString;
        lUserID = UserID;
        iChannelNumber = chanNumber;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jComboBoxShowArea = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jCheckBoxShow = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldSrtingX = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldStringY = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldString = new javax.swing.JTextField();
        jButtonSave = new javax.swing.JButton();
        jButtonSetUp = new javax.swing.JButton();
        jButtonExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("叠加字符");
        getContentPane().setLayout(null);

        jLabel1.setText("显示区域");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 10, 60, 15);

        jComboBoxShowArea.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8" }));
        jComboBoxShowArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxShowAreaActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBoxShowArea);
        jComboBoxShowArea.setBounds(90, 10, 70, 21);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("区域设置"));

        jCheckBoxShow.setText("显示字符");

        jLabel2.setText("X坐标");

        jLabel3.setText("Y坐标");

        jLabel4.setText("叠加字符");

        jButtonSave.setText("保存");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(217, Short.MAX_VALUE)
                        .addComponent(jButtonSave))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel4)))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTextFieldSrtingX, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel3)
                                        .addGap(12, 12, 12)
                                        .addComponent(jTextFieldStringY, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE))
                                    .addComponent(jTextFieldString, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)))
                            .addComponent(jCheckBoxShow))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBoxShow)
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldStringY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldSrtingX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jButtonSave))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextFieldString, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 40, 300, 180);

        jButtonSetUp.setText("确定");
        jButtonSetUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetUpActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonSetUp);
        jButtonSetUp.setBounds(130, 230, 60, 23);

        jButtonExit.setText("退出");
        jButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExitActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonExit);
        jButtonExit.setBounds(230, 230, 60, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*************************************************
    函数:      "保存" 按钮单击相应函数
    函数描述:	保存参数到结构体
     *************************************************/
    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        int iAreaNumber = jComboBoxShowArea.getSelectedIndex();
        m_strShowString.struStringInfo[iAreaNumber].wShowString = (short) ((this.jCheckBoxShow.isSelected() == true) ? 1 : 0);
        m_strShowString.struStringInfo[iAreaNumber].sString = (jTextFieldString.getText() + "\0").getBytes();
        m_strShowString.struStringInfo[iAreaNumber].wShowStringTopLeftY = (short) Integer.parseInt(jTextFieldStringY.getText());
        m_strShowString.struStringInfo[iAreaNumber].wShowStringTopLeftX = (short) Integer.parseInt(jTextFieldSrtingX.getText());
    }//GEN-LAST:event_jButtonSaveActionPerformed

    /*************************************************
    函数:      "设置" 按钮单击相应函数
    函数描述:	设置参数,调用接口向设备发送参数
     *************************************************/
    private void jButtonSetUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSetUpActionPerformed
        m_strShowString.write();
        Pointer lpShowString = m_strShowString.getPointer();
        boolean setDVRConfigSuc = hCNetSDK.NET_DVR_SetDVRConfig(lUserID, HCNetSDK.NET_DVR_SET_SHOWSTRING_V30,
                new NativeLong(this.iChannelNumber), lpShowString, m_strShowString.size());
        m_strShowString.read();
        if (setDVRConfigSuc == false)
        {
            JOptionPane.showMessageDialog(this, "设置显示字符参数失败");
            System.out.print("" + hCNetSDK.NET_DVR_GetLastError());
        } else
        {
            JOptionPane.showMessageDialog(this, "设置显示字符参数成功");
        }
    }//GEN-LAST:event_jButtonSetUpActionPerformed

    /*************************************************
    函数:      "退出" 按钮单击相应函数
    函数描述:	销毁窗口
     *************************************************/
    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButtonExitActionPerformed

    /*************************************************
    函数:      "显示区域" 选项改变时间相应函数
    函数描述:	显示对应区域的参数
     *************************************************/
    private void jComboBoxShowAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxShowAreaActionPerformed
        showStringConfig();
    }//GEN-LAST:event_jComboBoxShowAreaActionPerformed

    /*************************************************
    函数:      showStringConfig
    函数描述:	显示对应区域的参数
     *************************************************/
    void showStringConfig()
    {
        int iAreaNumber = jComboBoxShowArea.getSelectedIndex();
        this.jCheckBoxShow.setSelected((m_strShowString.struStringInfo[iAreaNumber].wShowString > 0) ? true : false);
        this.jTextFieldSrtingX.setText(m_strShowString.struStringInfo[iAreaNumber].wShowStringTopLeftX + "");
        this.jTextFieldStringY.setText(m_strShowString.struStringInfo[iAreaNumber].wShowStringTopLeftY + "");
        this.jTextFieldString.setText(new String(m_strShowString.struStringInfo[iAreaNumber].sString));
        String[] sName = new String[2];
        sName = new String(m_strShowString.struStringInfo[iAreaNumber].sString).split("\0", 2);
        this.jTextFieldString.setText(sName[0]);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonExit;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JButton jButtonSetUp;
    javax.swing.JCheckBox jCheckBoxShow;
    javax.swing.JComboBox jComboBoxShowArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    javax.swing.JTextField jTextFieldSrtingX;
    javax.swing.JTextField jTextFieldString;
    javax.swing.JTextField jTextFieldStringY;
    // End of variables declaration//GEN-END:variables
}
