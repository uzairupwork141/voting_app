/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package voting_app;

import code_files.ConnectDB;
import code_files.FieldsLimits;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gouhar Ali
 */
public class CANDIDATE extends javax.swing.JFrame {

    /**
     * Creates new form CANDIDATE
     */
    Connection con ;
    PreparedStatement str;
    ResultSet rs;
    String s= "";
    public CANDIDATE() {
        initComponents();
        setBackground(new Color(0,0,0,0));
        ConnectDB c = new ConnectDB();
        con = c.Connect();
        getcategory();
        getALLDATA();
    }
    
    
    
    
    
    
      public void Clear(){
          IDtxt.setText("");
          NAMEtxt.setText("");
          PHONEtxt.setText("");
          CNICtxt.setText("");
          DESIGNATIONtxt.setText("");
          img.setIcon(null);
          pathtxt.setText("");
          table1.clearSelection();
          s="";
          
          jButton1.show();
          jButton3.show();
          
          
      }
    
    
      
      
      
      
        
    public void getcategory(){
        
        try{
            str=con.prepareStatement("select* from `elections`");
            rs=str.executeQuery();
            while(rs.next()){
                CATEGORY.addItem(rs.getString("ID"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, e);

        }
        
    
    
    }
      
      
      
      
      
    
    public void submit(){
       
        if ( NAMEtxt.getText().equals("") || DESIGNATIONtxt.getText().equals(""))
        {  
            JOptionPane.showMessageDialog(this,"INCOMPLETE ENTERY");
            return;
        }
        
        if (CATEGORY.getSelectedItem().equals("Select"))
        {  
            JOptionPane.showMessageDialog(this,"Please Select Elections for candidate");
            return;
        }
       
       

                try{
                   
                    
                        if(s!=""){
                            str=con.prepareStatement("INSERT INTO `candidate_detail`( `NAME`, `PHONE`, `cnic`,`designation`,`EID`, `img`)VALUES(?,?,?,?,?,?)");
                            str.setString(1, NAMEtxt.getText());
                            str.setString(2, PHONEtxt.getText());
                            str.setString(3, CNICtxt.getText());
                            str.setString(4, DESIGNATIONtxt.getText());
                            str.setString(5, CATEGORY.getSelectedItem().toString());
                            
                            InputStream Img =new FileInputStream(new File(s));
                            str.setBlob(6, Img);
                            str.executeUpdate();
                            JOptionPane.showMessageDialog(this,"DATA SAVED","SUCCESS",1);
                            Clear();
                            getALLDATA();
                            return;
                        }
                        
                        
                       str=con.prepareStatement("INSERT INTO `candidate_detail`( `NAME`, `PHONE`, `cnic`,`designation`,`EID`)VALUES(?,?,?,?,?)");
                       str.setString(1, NAMEtxt.getText());
                       str.setString(2, PHONEtxt.getText());
                       str.setString(3, CNICtxt.getText());
                       str.setString(4, DESIGNATIONtxt.getText());
                       str.setString(5, CATEGORY.getSelectedItem().toString());
                       
                       str.executeUpdate();
                       JOptionPane.showMessageDialog(this,"DATA SAVED","SUCCESS",1);
                       Clear();
                       getALLDATA();
                       
                        
                        
                }catch(Exception e)
                {
                        JOptionPane.showMessageDialog(this,e,"ERROR",3);
                }
        }
    
    
    
    
    
    public void getALLDATA()
    {
        int c;
        try {
           
          
            str=con.prepareStatement ("SELECT* from candidate_detail ");
            rs=str.executeQuery();
            ResultSetMetaData rss=(ResultSetMetaData) rs.getMetaData();
            c=rss.getColumnCount();
            DefaultTableModel model = new DefaultTableModel(new Object[] { "ID", "NAME", "PHONE" ,"CNIC","DESIGNATION","Election.ID","PIC" }, 0){
                @Override
                public Class<?> getColumnClass(int column) {
                    switch (column) {
                        case 6: return ImageIcon.class;
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
                    Object row []={rs.getString("ID") ,rs.getString("NAME"),rs.getString("PHONE"),rs.getString("CNIC"),rs.getString("DESIGNATION"),rs.getString("EID") ,"NO IMAGE"};
                    model.addRow(row);
                    return;
                }
                
                ImageIcon image = new ImageIcon(Img);
                Image im = image.getImage();
                Image myImg = im.getScaledInstance(50,50,Image.SCALE_SMOOTH);
                ImageIcon newImage = new ImageIcon(myImg);
                Object row []={rs.getString("ID") ,rs.getString("NAME"),rs.getString("PHONE"),rs.getString("CNIC"),rs.getString("DESIGNATION"),rs.getString("EID"),newImage };
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
                        IDtxt.setText(rs.getString("ID"));
                        NAMEtxt.setText(rs.getString("NAME"));
                        PHONEtxt.setText(rs.getString("PHONE"));
                        CNICtxt.setText(rs.getString("CNIC"));
                        DESIGNATIONtxt.setText(rs.getString("DESIGNATION"));
                        CATEGORY.setSelectedItem(rs.getString("EID"));
                        byte[] Img = rs.getBytes("img");
                        if(Img==null){
                            img.setIcon(null);
                            jButton3.hide();
                            jButton1.hide();
                            return;
                        }
                        //Resize The ImageIcon
                        ImageIcon image = new ImageIcon(Img);
                        Image im = image.getImage();
                        Image myImg = im.getScaledInstance(130,130,Image.SCALE_SMOOTH);
                        ImageIcon newImage = new ImageIcon(myImg);
                        img.setIcon(newImage);
                        
                        jButton3.hide();
                        jButton1.hide();
                    }else 
                    {
                        JOptionPane.showMessageDialog(this,"NO DATA AVALIBLE","NO DATA",2);
                    }
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this,ex,"CONNECTION ",3);
        }
        
    }
     
     
     
     
     
     
    public void updateToMainTbl (String id) throws SQLException{
            
             
        if ( IDtxt.getText().equals("") ||NAMEtxt.getText().equals("") || DESIGNATIONtxt.getText().equals(""))
        {  
            JOptionPane.showMessageDialog(this,"Empty Fields","error",2);
            return;
        }

        if (CATEGORY.getSelectedItem().equals("Select"))
        {  
            JOptionPane.showMessageDialog(this,"Please Select Elections for candidate");
            return;
        }
          
            String name=NAMEtxt.getText();
            String phone=PHONEtxt.getText();
            String cnic=CNICtxt.getText();
            String designation=DESIGNATIONtxt.getText();
            String eid=CATEGORY.getSelectedItem().toString();
            
            
            
            str= con.prepareStatement("UPDATE `candidate_detail` SET `NAME`=?,`PHONE`=?,`CNIC`=?,`DESIGNATION`=?,`EID`=?  WHERE ID="+id);
        
            str.setString(1,  name);
            str.setString(2,  phone);
            str.setString(3,  cnic);
            str.setString(4, designation);
            str.setString(5, eid);
            
            str.executeUpdate();    
            
        
        JOptionPane.showMessageDialog(this, "UPDATED","update",1);
        
        getALLDATA();
    }
    
     
    public void delete(String id) throws SQLException{
        
         if ( IDtxt.getText().equals(""))
        {  
            JOptionPane.showMessageDialog(this,"ID Requied !");
            return;
        }
        
        int p = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete [ " + NAMEtxt.getText() + " ] candidate DATA?","Delete",JOptionPane.YES_NO_OPTION); 
        if(p==0){
            str = con.prepareStatement("DELETE FROM `candidate_detail` WHERE ID="+id);
            str.executeUpdate();

            str = con.prepareStatement("DELETE FROM `voter_detail` WHERE voted="+id);
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
        pane31 = new panal.pane3();
        jLabel3 = new javax.swing.JLabel();
        PHONEtxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        NAMEtxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        IDtxt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        CNICtxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        DESIGNATIONtxt = new javax.swing.JTextField();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        img = new javax.swing.JLabel();
        pathtxt = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        CATEGORY = new javax.swing.JComboBox<>();
        jButton6 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new panal.Table2();
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
        pane21.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 10, 30, 20));

        bgpanal1.add(pane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, 40));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("CANDIDATE MANAGMENT");
        jLabel2.setOpaque(true);
        bgpanal1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 1130, 60));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("PHONE");
        pane31.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, 100, 30));

        PHONEtxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        PHONEtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PHONEtxt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        PHONEtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                PHONEtxtKeyTyped(evt);
            }
        });
        pane31.add(PHONEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 60, 240, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("NAME *");
        pane31.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 100, 30));

        NAMEtxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NAMEtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        NAMEtxt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pane31.add(NAMEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 240, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("ID");
        pane31.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 110, 30));

        IDtxt.setEditable(false);
        IDtxt.setBackground(new java.awt.Color(255, 204, 204));
        IDtxt.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        IDtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        IDtxt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pane31.add(IDtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 140, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("ELECTION ID");
        pane31.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 100, 30));

        CNICtxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        CNICtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        CNICtxt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        CNICtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                CNICtxtKeyTyped(evt);
            }
        });
        pane31.add(CNICtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 240, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("DESIGNATION *");
        pane31.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 100, 30));

        DESIGNATIONtxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        DESIGNATIONtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        DESIGNATIONtxt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pane31.add(DESIGNATIONtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 110, 240, 30));

        img.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jDesktopPane1.add(img);
        img.setBounds(0, 0, 130, 130);

        pane31.add(jDesktopPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 50, 130, 130));

        pathtxt.setEditable(false);
        pathtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathtxtActionPerformed(evt);
            }
        });
        pane31.add(pathtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 180, 130, -1));

        jButton1.setText("Browse");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        pane31.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 210, 130, -1));

        jButton2.setBackground(new java.awt.Color(255, 204, 153));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setText("NEW");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        pane31.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 200, 100, 40));

        jButton3.setBackground(new java.awt.Color(204, 255, 204));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setText("SAVE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        pane31.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 200, 100, 40));

        jButton4.setBackground(new java.awt.Color(204, 255, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setText("UPDATE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        pane31.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 200, 100, 40));

        jLabel8.setBackground(new java.awt.Color(255, 204, 204));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("image size must be less then 1 MB");
        jLabel8.setOpaque(true);
        pane31.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 30, 210, -1));

        jButton5.setBackground(new java.awt.Color(255, 204, 204));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton5.setText("DELETE");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        pane31.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 200, 100, 40));

        jLabel9.setBackground(new java.awt.Color(255, 204, 204));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("image can not be updated");
        jLabel9.setOpaque(true);
        pane31.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 150, 20));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("CNIC");
        pane31.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 100, 30));

        CATEGORY.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        CATEGORY.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        pane31.add(CATEGORY, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, 240, 30));

        bgpanal1.add(pane31, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 1090, 260));

        jButton6.setBackground(new java.awt.Color(255, 153, 153));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton6.setText("BACK");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        bgpanal1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 720, 150, 30));

        table1.setModel(new javax.swing.table.DefaultTableModel(
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
        table1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        table1.setShowGrid(true);
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table1);

        bgpanal1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 1090, 290));

        jLabel11.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("SOFTWARE BY Muhammad Uzair              PH no : 03335323758");
        bgpanal1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 710, 460, 40));

        getContentPane().add(bgpanal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, 760));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            updateToMainTbl(IDtxt.getText());
        } catch (SQLException ex) {
            Logger.getLogger(CANDIDATE.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Clear();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void pathtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pathtxtActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
           
        JFileChooser fc =new JFileChooser();
        fc.showOpenDialog(this);
        File f = fc.getSelectedFile();
        String path = f.getAbsolutePath();
        
        Path pt = Paths.get(path);
        long bytes = 0;
        try {
            bytes = Files.size(pt);
        } catch (IOException ex) {
            Logger.getLogger(CANDIDATE.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        System.out.println(String.format("%,d bytes", bytes));
        System.out.println(String.format("%,d kilobytes", bytes / 1024));
        
        double kb=bytes / 1024;
        
        if(kb>=1000 && kb<2000 ){
            
        }
        
        if(kb>1000){
            JOptionPane.showMessageDialog(this, "Image size Grater then 1 MB","WARNING",2);
            return;
        }
            
        ImageIcon i = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT));
        img.setIcon(i);
        s=path;
        pathtxt.setText(s);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        submit();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            delete(IDtxt.getText());
        } catch (SQLException ex) {
            Logger.getLogger(CANDIDATE.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        new ADMIN_MAIN().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table1MouseClicked
        // TODO add your handling code here:
        tableclick();
    }//GEN-LAST:event_table1MouseClicked

    private void PHONEtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PHONEtxtKeyTyped
        // TODO add your handling code here:
        new FieldsLimits().only_numbers(evt);
        String cchar = PHONEtxt.getText();
        new FieldsLimits().limit(evt, cchar.length(),11);
        
    }//GEN-LAST:event_PHONEtxtKeyTyped

    private void CNICtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CNICtxtKeyTyped
        // TODO add your handling code here:
        
        new FieldsLimits().only_numbers(evt);
        String cchar = CNICtxt.getText();
        new FieldsLimits().limit(evt, cchar.length(),13);
        
        
    }//GEN-LAST:event_CNICtxtKeyTyped

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
            java.util.logging.Logger.getLogger(CANDIDATE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CANDIDATE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CANDIDATE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CANDIDATE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CANDIDATE().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CATEGORY;
    private javax.swing.JTextField CNICtxt;
    private javax.swing.JTextField DESIGNATIONtxt;
    private javax.swing.JTextField IDtxt;
    private javax.swing.JTextField NAMEtxt;
    private javax.swing.JTextField PHONEtxt;
    private panal.bgpanal bgpanal1;
    private javax.swing.JLabel img;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private panal.pane2 pane21;
    private panal.pane3 pane31;
    private javax.swing.JTextField pathtxt;
    private panal.Table2 table1;
    // End of variables declaration//GEN-END:variables
}
