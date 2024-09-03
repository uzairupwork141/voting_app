/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package voting_app;

import code_files.ConnectDB;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gouhar Ali
 */
public class VIEW_VOTERS extends javax.swing.JFrame {

    /**
     * Creates new form VIEW_VOTERS
    */
    Connection con ;
    PreparedStatement str;
    ResultSet rs;
    PreparedStatement str2;
    ResultSet rs2;
    public VIEW_VOTERS() {
        initComponents();
        setBackground(new Color(0,0,0,0));
        ConnectDB c = new ConnectDB();
        con = c.Connect();
        getElections();
    }
    
    
    
    
    public void getElections(){
        
        try{
            str2=con.prepareStatement("select* from `elections`");
            rs2=str2.executeQuery();
            while(rs2.next()){
                IDtxt.addItem(rs2.getString("ID"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }
    
    
    
    
    
    public String getCandidateName(String id)
    {
     
        try {
           
          
            str2=con.prepareStatement ("SELECT* from candidate_detail where ID='"+id+"'");
            rs2=str2.executeQuery();
            if(rs2.next()){
                return rs2.getString("NAME");
            }
            
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(this,ex,"CONNECTION ",3);
          
        }
        return null;
    }
    
    
    
    
    
    
    
     public String getElectionName(String id)
    {
     
        try {
           
          
            str2=con.prepareStatement ("SELECT* from elections where ID='"+id+"'");
            rs2=str2.executeQuery();
            if(rs2.next()){
                return rs2.getString("NAME");
            }
            
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(this,ex,"CONNECTION ",3);
          
        }
        return null;
    }
     
    
    public void delete (){
         try{
            DefaultTableModel df=(DefaultTableModel)table1.getModel();
            int row=table1.getSelectedRow();
            if(row<0){
                JOptionPane.showMessageDialog(this,"No Data Selected");
                return;
            }
            String id = (table1.getModel().getValueAt(row, 0).toString());
            String nna = (table1.getModel().getValueAt(row, 1).toString());
            int p = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete [ " + nna + " ] Voter DATA?","Delete",JOptionPane.YES_NO_OPTION);
            if (p==0){
                String Str="DELETE FROM `voter_detail` WHERE ID='"+ id+"'" ;
                str=con.prepareStatement(Str);
                str.execute();
                JOptionPane.showMessageDialog(this,"Sucessfully Deleted!");
                getAllVoters();
                
            }else{
                JOptionPane.showMessageDialog(this,"Request Cancelled!");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e);
        }
    }
     
     
    
    
    
    public void getAllVoters()
    {
        int c;
        try {
           
          
            str=con.prepareStatement ("SELECT* from voter_detail where EID='"+IDtxt.getSelectedItem().toString()+"'");
            rs=str.executeQuery();
            ResultSetMetaData rss=(ResultSetMetaData) rs.getMetaData();
            c=rss.getColumnCount();
            DefaultTableModel df=(DefaultTableModel)table1.getModel();
            df.setRowCount(0);
                
            while (rs.next()){
                Vector v2=new Vector();
                for (int a=1;a<=c;a++){
                   v2.add(rs.getString("ID"));
                   v2.add(rs.getString("NAME"));
                   v2.add(rs.getString("CNIC"));
                   v2.add(getCandidateName(rs.getString("VOTED")));
                   v2.add(rs.getString("date"));
                   v2.add(rs.getString("TIME"));
                   
                }
                df.addRow(v2);
                
            }
            
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(this,ex,"CONNECTION ",3);
        }
    }
    
    
    
    public void getAVoters(String searchBy , String search)
    {
        int c;
        try {
           
            if(searchBy.equals("ID")){
               str=con.prepareStatement ("SELECT* from voter_detail where EID='"+IDtxt.getSelectedItem().toString()+"' AND ID='"+search+"'"); 
            }else{
               str=con.prepareStatement ("SELECT* from voter_detail where EID='"+IDtxt.getSelectedItem().toString()+"' AND "+searchBy+" LIKE '%"+search+"%'");
            }
            
            rs=str.executeQuery();
            ResultSetMetaData rss=(ResultSetMetaData) rs.getMetaData();
            c=rss.getColumnCount();
            DefaultTableModel df=(DefaultTableModel)table1.getModel();
            df.setRowCount(0);
                
            while (rs.next()){
                Vector v2=new Vector();
                for (int a=1;a<=c;a++){
                   v2.add(rs.getString("ID"));
                   v2.add(rs.getString("NAME"));
                   v2.add(rs.getString("CNIC"));
                   v2.add(getCandidateName(rs.getString("VOTED")));
                   v2.add(rs.getString("date"));
                   v2.add(rs.getString("TIME"));
                   
                }
                df.addRow(v2);
                
            }
            
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(this,ex,"CONNECTION ",3);
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

        bgpanal2 = new panal.bgpanal();
        pane21 = new panal.pane2();
        jLabel1 = new javax.swing.JLabel();
        pane11 = new panal.pane1();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new panal.Table2();
        jLabel10 = new javax.swing.JLabel();
        SEARCHBYtxt = new javax.swing.JComboBox<>();
        SEARCHtxt = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        pane12 = new panal.pane1();
        jLabel3 = new javax.swing.JLabel();
        IDtxt = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        ENAMEtxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pane13 = new panal.pane1();
        jLabel7 = new javax.swing.JLabel();
        pane14 = new panal.pane1();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bgpanal2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("X");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        pane21.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 0, -1, 30));

        bgpanal2.add(pane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 33));

        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "VOTER ID", "VOTER NAME", "VOTER CNIC", "VOTED", "DATE", "TIME"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        table1.setShowGrid(true);
        jScrollPane1.setViewportView(table1);

        pane11.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 840, 320));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Search Voter by");
        pane11.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 130, 30));

        SEARCHBYtxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        SEARCHBYtxt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "NAME", "CNIC" }));
        pane11.add(SEARCHBYtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 150, 30));

        SEARCHtxt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pane11.add(SEARCHtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 320, 30));

        jButton1.setText("GET");
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        pane11.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, 60, 30));

        bgpanal2.add(pane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 860, 380));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("SELECT ELECTION");
        pane12.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 200, 20));

        IDtxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        IDtxt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        IDtxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                IDtxtMouseClicked(evt);
            }
        });
        IDtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDtxtActionPerformed(evt);
            }
        });
        pane12.add(IDtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 150, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("ELECTION ID");
        pane12.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 110, 30));

        ENAMEtxt.setEditable(false);
        ENAMEtxt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ENAMEtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ENAMEtxt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        ENAMEtxt.setFocusable(false);
        ENAMEtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ENAMEtxtActionPerformed(evt);
            }
        });
        pane12.add(ENAMEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 60, 340, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("ELECTION NAME");
        pane12.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, 110, 30));

        bgpanal2.add(pane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 860, 110));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("VOTERS INFORMATION");
        jLabel2.setOpaque(true);
        bgpanal2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 850, 60));

        pane13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pane13MouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 51, 51));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imges/icons8-trash-can-50.png"))); // NOI18N
        jLabel7.setText("DELETE");
        pane13.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 210, 60));

        bgpanal2.add(pane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 620, -1, 60));

        pane14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pane14MouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imges/icons8-back-64.png"))); // NOI18N
        jLabel6.setText("BACK");
        pane14.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 210, 60));

        bgpanal2.add(pane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 620, -1, 60));

        jLabel9.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("SOFTWARE BY Muhammad Uzair                 PH no : 03335323758");
        bgpanal2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 684, 470, -1));

        getContentPane().add(bgpanal2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 720));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void IDtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDtxtActionPerformed
        // TODO add your handling code here:
        if(IDtxt.getSelectedItem().equals("Select")){
            ENAMEtxt.setText("");
            DefaultTableModel df=(DefaultTableModel)table1.getModel();
            df.setRowCount(0);
            return;
        }
        
        
        getAllVoters();
        ENAMEtxt.setText(getElectionName(IDtxt.getSelectedItem().toString()));
    }//GEN-LAST:event_IDtxtActionPerformed

    private void IDtxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IDtxtMouseClicked
        // TODO add your handling code here:
        
        
        
    }//GEN-LAST:event_IDtxtMouseClicked

    private void ENAMEtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ENAMEtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ENAMEtxtActionPerformed

    private void pane14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane14MouseClicked
        // TODO add your handling code here:
        dispose();
        new ADMIN_MAIN().setVisible(true);
    }//GEN-LAST:event_pane14MouseClicked

    private void pane13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane13MouseClicked
        // TODO add your handling code here:
        
        delete();
    }//GEN-LAST:event_pane13MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(IDtxt.getSelectedItem().equals("Select")){
            JOptionPane.showMessageDialog(this,"Please Select Election ID","Error",3);
            return;
        }
        
        if(SEARCHtxt.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Please Enter Voter Detail","Error",3);
            getAllVoters();
            return;
        }
        
        
        getAVoters(SEARCHBYtxt.getSelectedItem().toString(), SEARCHtxt.getText());
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(VIEW_VOTERS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VIEW_VOTERS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VIEW_VOTERS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VIEW_VOTERS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VIEW_VOTERS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ENAMEtxt;
    private javax.swing.JComboBox<String> IDtxt;
    private javax.swing.JComboBox<String> SEARCHBYtxt;
    private javax.swing.JTextField SEARCHtxt;
    private panal.bgpanal bgpanal2;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private panal.pane1 pane11;
    private panal.pane1 pane12;
    private panal.pane1 pane13;
    private panal.pane1 pane14;
    private panal.pane2 pane21;
    private panal.Table2 table1;
    // End of variables declaration//GEN-END:variables
}
