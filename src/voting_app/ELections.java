/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package voting_app;

import code_files.ConnectDB;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gouhar Ali
 */
public class ELections extends javax.swing.JFrame {

    /**
     * Creates new form ELections
     */
    Connection con ;
    PreparedStatement str;
    ResultSet rs;
    public ELections() {
        initComponents();
        setBackground(new Color(0,0,0,0));
        ConnectDB c = new ConnectDB();
        con = c.Connect();
        getALLDATA();

    }

    public void Clear(){
          IDtxt.setText("");
          NAMEtxt.setText("");
          DATEtxt.setText("");
          pane33.show();
         
      }
    
    
    
     public void submit(){
       
        if ( NAMEtxt.getText().equals("") || DATEtxt.getText().equals(""))
        {  
            JOptionPane.showMessageDialog(this,"INCOMPLETE ENTERY");
        }
        else
        {

                try{
                   
                    
                       str=con.prepareStatement("INSERT INTO `elections`( `NAME`, `DATE` )VALUES(?,?)");
                       str.setString(1, NAMEtxt.getText());
                       str.setString(2, DATEtxt.getText());
                       str.executeUpdate();
                       JOptionPane.showMessageDialog(this,"DATA SAVED","SUCCESS",1);
                       Clear();
                       getALLDATA();
                       
                        
                        
                }catch(Exception e)
                {
                        JOptionPane.showMessageDialog(this,e,"ERROR",3);
                }
        }
    }
    
    
    
    
    public void getALLDATA()
    {
        int c;
        try {
           
          
            str=con.prepareStatement ("SELECT* from elections ");
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
                   v2.add(rs.getString("DATE"));
                }
                df.addRow(v2);
                
            }
            
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(this,ex,"CONNECTION ",3);
        }
    }
    
    
    
    
    
    
     public void tableclick(){
        DefaultTableModel df=(DefaultTableModel)table1.getModel();
         int srow = table1.getSelectedRow();
         String i=df.getValueAt(srow, 0).toString();
        

        try {
            
            str=con.prepareStatement ("SELECT * FROM `elections` WHERE ID='"+i+"'");
            rs=str.executeQuery();
                    if (rs.next()){
                        IDtxt.setText(rs.getString("ID"));
                        NAMEtxt.setText(rs.getString("NAME"));
                        DATEtxt.setText(rs.getString("DATE"));
                        pane33.hide();
                    }else 
                    {
                        JOptionPane.showMessageDialog(this,"NO DATA AVALIBLE","NO DATA",2);
                    }
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this,ex,"CONNECTION ",3);
        }
        
    }
     
     
     
     
     
     
    public void updateToMainTbl (String id) throws SQLException{
            
             
        if ( IDtxt.getText().equals("") ||NAMEtxt.getText().equals("") || DATEtxt.getText().equals(""))
        {  
            JOptionPane.showMessageDialog(this,"Empty Fields","error",2);
            return;
        }

          
            String name=NAMEtxt.getText();
            String date=DATEtxt.getText();
            
            
            
            
            str= con.prepareStatement("UPDATE `elections` SET `NAME`=?,`DATE`=?  WHERE ID="+id);
        
            str.setString(1,  name);
            str.setString(2,  date);
            str.executeUpdate();    
            
        
        JOptionPane.showMessageDialog(this, "UPDATED","update",1);
        Clear();
        getALLDATA();
    }
    
     
    public void delete(String id) throws SQLException{
        
        if ( IDtxt.getText().equals(""))
        {  
            JOptionPane.showMessageDialog(this,"ID Requied !");
            return;
        }
        int p = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete [ " + NAMEtxt.getText() + " ] Election DATA?","Delete",JOptionPane.YES_NO_OPTION); 
        if(p==0){
            str = con.prepareStatement("DELETE FROM `elections` WHERE ID="+id);
            str.executeUpdate();

            str = con.prepareStatement("DELETE FROM `candidate_detail` WHERE EID="+id);
            str.executeUpdate();

            str = con.prepareStatement("DELETE FROM `voter_detail` WHERE EID="+id);
            str.executeUpdate();


            JOptionPane.showMessageDialog(this, "Record Deleted","Deleted",2);
            Clear();
            getALLDATA();
        }else{
            JOptionPane.showMessageDialog(this,"Request Cancelled!");
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
        pane21 = new panal.pane2();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pane11 = new panal.pane1();
        jLabel3 = new javax.swing.JLabel();
        IDtxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        NAMEtxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        DATEtxt = new javax.swing.JTextField();
        pane32 = new panal.pane3();
        pane34 = new panal.pane3();
        jLabel6 = new javax.swing.JLabel();
        pane33 = new panal.pane3();
        pane35 = new panal.pane3();
        jLabel7 = new javax.swing.JLabel();
        pane36 = new panal.pane3();
        pane37 = new panal.pane3();
        jLabel8 = new javax.swing.JLabel();
        pane38 = new panal.pane3();
        pane39 = new panal.pane3();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new panal.Table2();
        pane40 = new panal.pane3();
        pane41 = new panal.pane3();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bgpanal1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("X");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        pane21.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 0, 20, 30));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("ELECTIONS");
        pane21.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 700, 30));

        bgpanal1.add(pane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 31));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ID");
        pane11.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 50, 30));

        IDtxt.setEditable(false);
        IDtxt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        IDtxt.setFocusable(false);
        pane11.add(IDtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 120, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("NAME");
        pane11.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 90, 30));

        NAMEtxt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pane11.add(NAMEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 350, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("DATE");
        pane11.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 90, 30));

        DATEtxt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pane11.add(DATEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 350, 30));

        pane32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pane32MouseClicked(evt);
            }
        });
        pane32.add(pane34, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 470, -1, 100));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("DALETE");
        pane32.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 90, 30));

        pane11.add(pane32, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 120, 90, 50));

        pane33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pane33MouseClicked(evt);
            }
        });
        pane33.add(pane35, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 470, -1, 100));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("SAVE");
        pane33.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 90, 30));

        pane11.add(pane33, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 60, 90, 50));

        pane36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pane36MouseClicked(evt);
            }
        });
        pane36.add(pane37, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 470, -1, 100));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("UPDATE");
        pane36.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 90, 30));

        pane11.add(pane36, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 60, 90, 50));

        pane38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pane38MouseClicked(evt);
            }
        });
        pane38.add(pane39, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 470, -1, 100));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("NEW");
        pane38.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 90, 30));

        pane11.add(pane38, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 120, 90, 50));

        bgpanal1.add(pane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 720, 200));

        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "ELECTION", "DATE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table1);

        bgpanal1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 720, 190));

        pane40.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pane40MouseClicked(evt);
            }
        });
        pane40.add(pane41, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 470, -1, 100));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("BACK");
        pane40.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 80, 40));

        bgpanal1.add(pane40, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 510, 120, 40));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("MANAGE ELECTIONS");
        bgpanal1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 730, 40));

        jLabel12.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("SOFTWARE BY Muhammad Uzair              PH no : 03335323758");
        bgpanal1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 460, 40));

        getContentPane().add(bgpanal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 560));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void pane32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane32MouseClicked
        try {
            // TODO add your handling code here:
            delete(IDtxt.getText());
        } catch (SQLException ex) {
            Logger.getLogger(ELections.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_pane32MouseClicked

    private void pane33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane33MouseClicked
        // TODO add your handling code here:
        submit();
    }//GEN-LAST:event_pane33MouseClicked

    private void pane36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane36MouseClicked
        try {
            // TODO add your handling code here:
            updateToMainTbl(IDtxt.getText());
        } catch (SQLException ex) {
            Logger.getLogger(ELections.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_pane36MouseClicked

    private void pane38MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane38MouseClicked
        // TODO add your handling code here:
        Clear();
    }//GEN-LAST:event_pane38MouseClicked

    private void pane40MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane40MouseClicked
        // TODO add your handling code here:
        dispose();
        new ADMIN_MAIN().setVisible(true);
    }//GEN-LAST:event_pane40MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table1MouseClicked
        // TODO add your handling code here:
        tableclick();
    }//GEN-LAST:event_table1MouseClicked

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
            java.util.logging.Logger.getLogger(ELections.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ELections.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ELections.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ELections.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ELections().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField DATEtxt;
    private javax.swing.JTextField IDtxt;
    private javax.swing.JTextField NAMEtxt;
    private panal.bgpanal bgpanal1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private panal.pane1 pane11;
    private panal.pane2 pane21;
    private panal.pane3 pane32;
    private panal.pane3 pane33;
    private panal.pane3 pane34;
    private panal.pane3 pane35;
    private panal.pane3 pane36;
    private panal.pane3 pane37;
    private panal.pane3 pane38;
    private panal.pane3 pane39;
    private panal.pane3 pane40;
    private panal.pane3 pane41;
    private panal.Table2 table1;
    // End of variables declaration//GEN-END:variables
}
