/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JDialogCheckTime.java
 *
 * Created on 2009-10-9, 14:41:20
 */
/**
 *
 * @author  Administrator
 */
package ClientDemo;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

/*****************************************************************************
 *类 ：JDialogCheckTime
 *类描述 ：校时
 ****************************************************************************/
public class JDialogCheckTime extends javax.swing.JDialog
{

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    private NativeLong m_lUserId;//用户ID

    /*************************************************
    函数:      JDialogCheckTime
    函数描述:	构造函数   Creates new form JDialogCheckTime
     *************************************************/
    public JDialogCheckTime(java.awt.Frame parent, boolean modal, NativeLong userId)
    {
        super(parent, modal);
        initComponents();
        m_lUserId = userId;
        Date today = new Date();
        Calendar now = Calendar.getInstance();//日历对象                                                                       //得到当前日期
        now.setTime(today); //设置进去
        jTextFieldYear.setText(now.get(Calendar.YEAR) + "");
        jTextFieldMonth.setText((now.get(Calendar.MONTH) + 1) + "");
        jTextFieldDay.setText(now.get(Calendar.DATE) + "");
        jTextFieldHour.setText(now.get(Calendar.HOUR_OF_DAY) + "");
        jTextFieldMinute.setText(now.get(Calendar.MINUTE) + "");
        jTextFieldSecond.setText(now.get(Calendar.SECOND) + "");

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jToggleButtonExit = new javax.swing.JToggleButton();
        jTextFieldDay = new javax.swing.JTextField();
        jToggleButtonCheckTime = new javax.swing.JToggleButton();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldHour = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldMonth = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldYear = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldMinute = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldSecond = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("校时");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jToggleButtonExit.setText("退出");
        jToggleButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonExitActionPerformed(evt);
            }
        });

        jToggleButtonCheckTime.setText("校时");
        jToggleButtonCheckTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonCheckTimeActionPerformed(evt);
            }
        });

        jLabel2.setText("月");

        jLabel3.setText("日");

        jLabel6.setText("秒");

        jLabel1.setText("年");

        jLabel4.setText("时");

        jLabel5.setText("分");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jTextFieldHour, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jTextFieldYear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jToggleButtonCheckTime, 0, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextFieldMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldDay, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextFieldMinute, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jToggleButtonExit, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldSecond, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldMinute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldSecond, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButtonExit)
                    .addComponent(jToggleButtonCheckTime))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleName(null);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*************************************************
    函数:      " 退出"  按钮响应函数
    函数描述:	销毁对话框
     *************************************************/
    private void jToggleButtonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_jToggleButtonExitActionPerformed

    /*************************************************
    函数:       "校时"  按钮响应函数
    函数描述:	 调用接口校时
     *************************************************/
    private void jToggleButtonCheckTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonCheckTimeActionPerformed
        HCNetSDK.NET_DVR_TIME strCurTime = new HCNetSDK.NET_DVR_TIME();
        strCurTime.dwYear = Integer.parseInt(jTextFieldYear.getText());
        strCurTime.dwMonth = Integer.parseInt(jTextFieldMonth.getText());
        strCurTime.dwDay = Integer.parseInt(jTextFieldDay.getText());
        strCurTime.dwHour = Integer.parseInt(jTextFieldHour.getText());
        strCurTime.dwMinute = Integer.parseInt(jTextFieldMinute.getText());
        strCurTime.dwSecond = Integer.parseInt(jTextFieldSecond.getText());
        strCurTime.write();
        Pointer lpPicConfig = strCurTime.getPointer();
        boolean setDVRConfigSuc = hCNetSDK.NET_DVR_SetDVRConfig(m_lUserId, HCNetSDK.NET_DVR_SET_TIMECFG,
                new NativeLong(0), lpPicConfig, strCurTime.size());
        strCurTime.read();
        if (setDVRConfigSuc != true)
        {
            JOptionPane.showMessageDialog(this, "校时失败");
            return;
        } else
        {
            JOptionPane.showMessageDialog(this, "校时成功");
            return;
        }

    }//GEN-LAST:event_jToggleButtonCheckTimeActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextFieldDay;
    private javax.swing.JTextField jTextFieldHour;
    private javax.swing.JTextField jTextFieldMinute;
    private javax.swing.JTextField jTextFieldMonth;
    private javax.swing.JTextField jTextFieldSecond;
    private javax.swing.JTextField jTextFieldYear;
    private javax.swing.JToggleButton jToggleButtonCheckTime;
    private javax.swing.JToggleButton jToggleButtonExit;
    // End of variables declaration//GEN-END:variables
}
