/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package voting_app;

import code_files.ConnectDB;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Gouhar Ali
 */
public class ADMIN_LOGIN extends javax.swing.JFrame {

    /**
     * Creates new form ADMIN_LOGIN
     */
    Connection con ;
    PreparedStatement insert;
    ResultSet rs;
    public ADMIN_LOGIN() {
        initComponents();
        setBackground(new Color(0,0,0,0));
        ConnectDB c = new ConnectDB();
        con = c.Connect();
    }
    
     public void LOGIN(){
         try{
                if (unametxt.getText().equals("")&& passwordtxt.getText().equals("")){
                        
                        JOptionPane.showMessageDialog(this, "EMPTY FIELD DETECTED","ERROR",2);
                }
                else
                {

                    String emal=unametxt.getText();
                    String Password=passwordtxt.getText();

                        insert=con.prepareStatement("SELECT * FROM `admin_detail` WHERE username=? AND password=?");
                        insert.setString(1,emal);
                        insert.setString(2,Password);
                        rs=insert.executeQuery();
                        
                        if (rs.next()){
                            String g_Pass=rs.getString("password");
                            if (g_Pass.equals(Password)){
                               
                                JOptionPane.showMessageDialog(this, "LOGIN SUCCESFULL");
                                new ADMIN_MAIN().setVisible(true);
                                this.dispose();
                            }else{
                                JOptionPane.showMessageDialog(this, "INCORRECT INFO","ERROR",2);
                            }
                        }else{
                             JOptionPane.showMessageDialog(this, "INCORRECT INFO","ERROR",2);
                             
                        }
                }
                    
                }catch(Exception e){
                    JOptionPane.showMessageDialog(this, e);
                }
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgpanal1 = new panal.bgpanal();
        pane11 = new panal.pane1();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pane31 = new panal.pane3();
        unametxt = new javax.swing.JTextField();
        passwordtxt = new javax.swing.JPasswordField();
        jCheckBox1 = new javax.swing.JCheckBox();
        pane21 = new panal.pane2();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        pane22 = new panal.pane2();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        pane12 = new panal.pane1();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bgpanal1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("X");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        pane11.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 0, 20, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("ADMIN LOGIN");
        pane11.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 680, 20));

        bgpanal1.add(pane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 36));

        unametxt.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        unametxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        unametxt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pane31.add(unametxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 210, 30));

        passwordtxt.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        passwordtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordtxt.setToolTipText("");
        passwordtxt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        passwordtxt.setEchoChar('*');
        pane31.add(passwordtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 190, 210, 30));

        jCheckBox1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckBox1.setText("show password");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        pane31.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 220, 120, -1));

        pane21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pane21MouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imges/icons8-login-64.png"))); // NOI18N
        jLabel4.setText("LOGIN");
        pane21.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 300, 70));

        pane31.add(pane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 260, 320, 70));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("PASSWORD");
        pane31.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, 110, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imges/icons8-admin-100.png"))); // NOI18N
        jLabel2.setText("ADMIN LOGIN");
        pane31.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 450, 120));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("USERNAME");
        pane31.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, 110, 30));

        bgpanal1.add(pane31, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 470, 340));

        pane22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pane22MouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imges/icons8-back-64.png"))); // NOI18N
        jLabel6.setText("BACK");
        pane22.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 210, 60));

        bgpanal1.add(pane22, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 400, 230, 60));

        jLabel9.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("SOFTWARE BY Muhammad Uzair                 PH no : 03335323758");
        bgpanal1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 470, 40));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imges/icons8-elections-100.png"))); // NOI18N
        jLabel7.setText("VOTING APPLICATION");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        pane12.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 230, 320));

        bgpanal1.add(pane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 50, -1, 340));

        getContentPane().add(bgpanal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 470));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void pane21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane21MouseClicked
        // TODO add your handling code here:
        LOGIN();
    }//GEN-LAST:event_pane21MouseClicked

    private void pane22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane22MouseClicked
        // TODO add your handling code here:
        dispose();
        new MAIN().setVisible(true);
    }//GEN-LAST:event_pane22MouseClicked

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
        if (!jCheckBox1.isSelected()) {
            passwordtxt.setEchoChar('*');
        } else {
            passwordtxt.setEchoChar((char) 0);
        }
        
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ADMIN_LOGIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ADMIN_LOGIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ADMIN_LOGIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ADMIN_LOGIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ADMIN_LOGIN().setVisible(true);
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private panal.bgpanal bgpanal1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private panal.pane1 pane11;
    private panal.pane1 pane12;
    private panal.pane2 pane21;
    private panal.pane2 pane22;
    private panal.pane3 pane31;
    private javax.swing.JPasswordField passwordtxt;
    private javax.swing.JTextField unametxt;
    // End of variables declaration//GEN-END:variables
}
