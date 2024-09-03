/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package voting_app;

import code_files.ConnectDB;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.awt.Color;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gouhar Ali
 */
public class Results extends javax.swing.JFrame {

    /**
     * Creates new form Results
     */
    
    Connection con ;
    PreparedStatement str;
    ResultSet rs;
    PreparedStatement str2;
    ResultSet rs2;
    public Results() {
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
                EIDtxt.addItem(rs2.getString("ID"));
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
    
     
     
     
     
     
     
    public int getNoOfVotes(String id)
    {
        int c;
        try {
           
          
            str2=con.prepareStatement ("SELECT COUNT(*) from voter_detail where VOTED='"+id+"'");
            rs2=str2.executeQuery();
            if (rs2.next()) {                
                c=rs2.getInt("count(*)");
                return c;
            }
            
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(this,ex,"CONNECTION ",3);
        }
        return 0;
    }
    
    
    
    
    
    public int getTotalNoOfVotes(String id)
    {
        int c;
        try {
           
          
            str2=con.prepareStatement ("SELECT COUNT(*) from voter_detail where EID='"+id+"'");
            rs2=str2.executeQuery();
            if (rs2.next()) {                
                c=rs2.getInt("count(*)");
                return c;
            }
            
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(this,ex,"CONNECTION ",3);
        }
        return 0;
    }
     
     
     
     
     
     
     
      public void getCandidate()
    {
        int c;
        try {
           
          
            str=con.prepareStatement ("SELECT* from candidate_detail where EID='"+EIDtxt.getSelectedItem().toString()+"'");
            rs=str.executeQuery();
            ResultSetMetaData rss=(ResultSetMetaData) rs.getMetaData();
            c=rss.getColumnCount();
            DefaultTableModel model = new DefaultTableModel(new Object[] { "ID", "IMAGE  تصویر","NAME  نام","VOTES" }, 0){
                @Override
                public Class<?> getColumnClass(int column) {
                    switch (column) {
                        case 1: return ImageIcon.class;
                        default: return String.class;
                    }
                }
            };
            table1.setModel(model);
            table1.setDefaultEditor(Object.class, null);

            model.setRowCount(0);
                
            while (rs.next()){
                byte[] Img = rs.getBytes("img");
                if(Img==null){
                    Object row []={rs.getString("ID") , "NO IMAGE",rs.getString("NAME"),getNoOfVotes(rs.getString("ID"))};
                    model.addRow(row);
                    return;
                }
                
                ImageIcon image = new ImageIcon(Img);
                Image im = image.getImage();
                Image myImg = im.getScaledInstance(160,145,Image.SCALE_SMOOTH);
                ImageIcon newImage = new ImageIcon(myImg);
                Object row []={rs.getString("ID") ,newImage ,rs.getString("NAME"),getNoOfVotes(rs.getString("ID"))};
                model.addRow(row);
                
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
            
            str=con.prepareStatement ("SELECT * FROM `candidate_detail` WHERE ID='"+i+"'");
            rs=str.executeQuery();
                    if (rs.next()){
                        CIDtxt.setText(rs.getString("ID"));
                        CNAMEtxt.setText(rs.getString("NAME"));
                        byte[] Img = rs.getBytes("img");
                        //if no img
                        if(Img==null){
                           
                            img.setIcon(null);
                            double obtend=Double. valueOf(getNoOfVotes(rs.getString("ID")));
                            VOTEStxt.setText(""+String.format("%.0f", obtend));


                            double total=Double. valueOf(getTotalNoOfVotes(EIDtxt.getSelectedItem().toString()));
                            System.out.println(total);
                            double per =obtend/total*100;

                            PERtxt.setText(""+String.format("%.2f", per) +"%");
                            return;
                        }
                        
                        
                        
                        
                        
                        
                        //Resize The ImageIcon
                        ImageIcon image = new ImageIcon(Img);
                        Image im = image.getImage();
                        Image myImg = im.getScaledInstance(160,150,Image.SCALE_SMOOTH);
                        ImageIcon newImage = new ImageIcon(myImg);
                        img.setIcon(newImage);
                        
                        
                        
                        double obtend=Double. valueOf(getNoOfVotes(rs.getString("ID")));
                        VOTEStxt.setText(""+String.format("%.0f", obtend));
                        
                        
                        double total=Double. valueOf(getTotalNoOfVotes(EIDtxt.getSelectedItem().toString()));
                        System.out.println(total);
                        double per =obtend/total*100;
                        
                        PERtxt.setText(""+String.format("%.2f", per) +"%");
                        
                    }else 
                    {
                        JOptionPane.showMessageDialog(this,"NO DATA AVALIBLE","NO DATA",2);
                    }
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this,ex,"CONNECTION ",3);
        }
        
    }
      
      
      
    public void clear (){
       
        table1.clearSelection();
        CIDtxt.setText("");
        img.setIcon(null);
        CNAMEtxt.setText("");
        VOTEStxt.setText("");
        PERtxt.setText("");
        
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
        pane12 = new panal.pane1();
        jLabel3 = new javax.swing.JLabel();
        EIDtxt = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        ENAMEtxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        pane31 = new panal.pane3();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        img = new javax.swing.JLabel();
        CIDtxt = new javax.swing.JLabel();
        CNAMEtxt = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        PERtxt = new javax.swing.JLabel();
        VOTEStxt = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new panal.Table3();
        pane14 = new panal.pane1();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        pane15 = new panal.pane1();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bgpanal1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("X");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        pane21.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 0, 20, 30));

        bgpanal1.add(pane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, 30));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 50)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imges/icons8-result-100.png"))); // NOI18N
        jLabel2.setText("VOTING RESULT");
        jLabel2.setOpaque(true);
        bgpanal1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 1110, 80));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("SELECT ELECTION");
        pane12.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 210, 20));

        EIDtxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        EIDtxt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        EIDtxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EIDtxtMouseClicked(evt);
            }
        });
        EIDtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EIDtxtActionPerformed(evt);
            }
        });
        pane12.add(EIDtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 150, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("NOTE :");
        pane12.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 110, 30));

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
        pane12.add(ENAMEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 100, 350, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("ELECTION NAME");
        pane12.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, 110, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("To View Election Results Please Select an Election Using \"ELECTION ID\"");
        pane12.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 530, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("ELECTION ID");
        pane12.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 110, 30));

        bgpanal1.add(pane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 860, 160));

        img.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jDesktopPane1.add(img);
        img.setBounds(0, 0, 160, 150);

        pane31.add(jDesktopPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 160, 150));

        CIDtxt.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        CIDtxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CIDtxt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pane31.add(CIDtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 110, 50));

        CNAMEtxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        CNAMEtxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CNAMEtxt.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Candidate Name", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        pane31.add(CNAMEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 220, 60));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel24.setText("ID#");
        jLabel24.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pane31.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 50, 50));

        PERtxt.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        PERtxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PERtxt.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Percentage", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        pane31.add(PERtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 220, 90));

        VOTEStxt.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        VOTEStxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        VOTEStxt.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Votes", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        pane31.add(VOTEStxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 220, 110));

        bgpanal1.add(pane31, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 130, 240, 520));

        table1.setAutoCreateRowSorter(true);
        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "IMAGE", "VOTES"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        table1.setShowGrid(true);
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table1);

        bgpanal1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 860, 330));

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
        pane14.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 220, 60));

        bgpanal1.add(pane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 660, 240, 60));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("SELECT CANDIDATES");
        jLabel7.setOpaque(true);
        bgpanal1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 860, 30));

        pane15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pane15MouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imges/icons8-clear-64.png"))); // NOI18N
        jLabel8.setText("CLEAR");
        pane15.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 220, 60));

        bgpanal1.add(pane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 660, 240, 60));

        jLabel11.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("SOFTWARE BY Muhammad Uzair                 PH no : 03335323758");
        bgpanal1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 670, 470, 40));

        getContentPane().add(bgpanal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, 730));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void EIDtxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EIDtxtMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_EIDtxtMouseClicked

    private void EIDtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EIDtxtActionPerformed
        // TODO add your handling code here:
        if(EIDtxt.getSelectedItem().equals("Select")){
            ENAMEtxt.setText("");
            DefaultTableModel df=(DefaultTableModel)table1.getModel();
            df.setRowCount(0);
            return;
        }
        
       
        getCandidate();
        ENAMEtxt.setText(getElectionName(EIDtxt.getSelectedItem().toString()));
    }//GEN-LAST:event_EIDtxtActionPerformed

    private void ENAMEtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ENAMEtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ENAMEtxtActionPerformed

    private void pane14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane14MouseClicked
        // TODO add your handling code here:
        dispose();
        new ADMIN_MAIN().setVisible(true);
    }//GEN-LAST:event_pane14MouseClicked

    private void table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table1MouseClicked
        // TODO add your handling code here:
        tableclick();
    }//GEN-LAST:event_table1MouseClicked

    private void pane15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane15MouseClicked
        // TODO add your handling code here:
        
        clear();
    }//GEN-LAST:event_pane15MouseClicked

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
            java.util.logging.Logger.getLogger(Results.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Results.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Results.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Results.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Results().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CIDtxt;
    private javax.swing.JLabel CNAMEtxt;
    private javax.swing.JComboBox<String> EIDtxt;
    private javax.swing.JTextField ENAMEtxt;
    private javax.swing.JLabel PERtxt;
    private javax.swing.JLabel VOTEStxt;
    private panal.bgpanal bgpanal1;
    private javax.swing.JLabel img;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private panal.pane1 pane12;
    private panal.pane1 pane14;
    private panal.pane1 pane15;
    private panal.pane2 pane21;
    private panal.pane3 pane31;
    private panal.Table3 table1;
    // End of variables declaration//GEN-END:variables
}
