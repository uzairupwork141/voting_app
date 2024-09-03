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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gouhar Ali
 */
public class START_VOTING extends javax.swing.JFrame {

    /**
     * Creates new form START_VOTING
     */
    Connection con ;
    PreparedStatement str;
    ResultSet rs;
    public START_VOTING() {
        initComponents();
        setBackground(new Color(0,0,0,0));
        ConnectDB c = new ConnectDB();
        con = c.Connect();
        getCandidate();
         CurrentDate();
         CurrentTime();
         
    }
   
    
    public void clear (){
        VNAMEtxt.setText("");
        VCNICtxt.setText("");
        table1.clearSelection();
        CIDtxt.setText("");
        img.setIcon(null);
        CNAMEtxt.setText("");
        
    }
    
    
    public START_VOTING(String Eid,String Ename) {
        initComponents();
        setBackground(new Color(0,0,0,0));
        
        EIDtxt.setText(Eid);
        ENAMEtxt.setText(Ename);
        
        ConnectDB c = new ConnectDB();
        con = c.Connect();
        getCandidate();
       
    }
    
    
        
    public String CurrentDate() {    
          
       DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
       LocalDateTime now = LocalDateTime.now();  
       System.out.println(dtf.format(now));  
       return dtf.format(now);
    }   
    
     public String CurrentTime() {    
          
      DateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
      String dateString = dateFormat.format(new Date()).toString();
      System.out.println("Current time in AM/PM: "+dateString);
      return dateString ;
      
    }   
    
    
    
    
    public void getCandidate()
    {
        int c;
        try {
           
          
            str=con.prepareStatement ("SELECT* from candidate_detail where EID='"+EIDtxt.getText()+"'");
            rs=str.executeQuery();
            ResultSetMetaData rss=(ResultSetMetaData) rs.getMetaData();
            c=rss.getColumnCount();
            DefaultTableModel model = new DefaultTableModel(new Object[] { "ID", "IMAGE  تصویر","NAME  نام" }, 0){
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
                    Object row []={rs.getString("ID") , "NO IMAGE",rs.getString("NAME")};
                    model.addRow(row);
                    return;
                }
                
                ImageIcon image = new ImageIcon(Img);
                Image im = image.getImage();
                Image myImg = im.getScaledInstance(160,145,Image.SCALE_SMOOTH);
                ImageIcon newImage = new ImageIcon(myImg);
                Object row []={rs.getString("ID") ,newImage ,rs.getString("NAME")};
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
                        if(Img==null){
                            img.setIcon(null);
                            return;
                        }
                        //Resize The ImageIcon
                        ImageIcon image = new ImageIcon(Img);
                        Image im = image.getImage();
                        Image myImg = im.getScaledInstance(160,150,Image.SCALE_SMOOTH);
                        ImageIcon newImage = new ImageIcon(myImg);
                        img.setIcon(newImage);
                        
            }else 
            {
                JOptionPane.showMessageDialog(this,"NO DATA AVALIBLE","NO DATA",2);
            }
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this,ex,"CONNECTION ",3);
        }
        
    }
     
     
     
     
     
     
     
     
     
     
      public boolean submitVote(){
       
       try{
                    
                       str=con.prepareStatement("INSERT INTO `voter_detail`( `NAME`, `cnic`, `EID`,`VOTED`,`DATE`,`TIME`)VALUES(?,?,?,?,?,?)");
                       str.setString(1, VNAMEtxt.getText());
                       str.setString(2, VCNICtxt.getText());
                       str.setString(3, EIDtxt.getText());
                       str.setString(4, CIDtxt.getText());
                       str.setString(5, CurrentDate());
                       str.setString(6, CurrentTime());
                       str.executeUpdate();
                       return true; 
                        
                }catch(Exception e)
                {
                       
                       JOptionPane.showMessageDialog(this,e,"ERROR",3);
                       return false; 
                }
        }
    
     
     
      public boolean checkVoter(String Cnic){
     

        try {
            
            str=con.prepareStatement ("SELECT* FROM `voter_detail` WHERE CNIC='"+Cnic+"'");
            rs=str.executeQuery();
            if (rs.next()){
                       return true;
            }
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this,ex,"CONNECTION ",3);
           
        }
        return false;
    }
     
      
     
      
      
//      
//    public boolean forgetpass(){
//        try{      
//            String USER=JOptionPane.showInputDialog("ENTER USERNAME");
//            String CODE=JOptionPane.showInputDialog("ENTER ADMIN CODE");
//            if (USER==null || CODE==null){
//                JOptionPane.showMessageDialog(null,"REQUEST CANCELLED");
//                return false;
//            }
//            
//           
//                   
//            str=con.prepareStatement ("SELECT * FROM `logs` WHERE USERNAME=? AND CODE=?");
//            str.setString(1,USER);
//            str.setString(2,CODE);
//            rs=str.executeQuery();
//            if (rs.next()){
//                String newpass=JOptionPane.showInputDialog("ENTER NEW Password");
//                if (newpass==null || newpass.equals(""))
//                {
//                    JOptionPane.showMessageDialog(null ,"EMPTY FIELD\nERROR");
//                }else{
//                    str=con.prepareStatement ("UPDATE `logs` SET `PASSWORD`='"+newpass+"' WHERE ID="+rs.getInt("ID"));
//                    str.execute();
//                    JOptionPane.showMessageDialog(null ,"PASSWORD:-> "+newpass+"\nPASSWORD SAVED");
//                }
//                                    
//            }else
//            {
//                JOptionPane.showMessageDialog(null , "INCORRECT INFO");
//            }
//            
//
//        }catch(Exception e){
//            System.out.println(e);
//        }
//    }
     

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
        jLabel6 = new javax.swing.JLabel();
        pane11 = new panal.pane1();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        VNAMEtxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        VCNICtxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        pane22 = new panal.pane2();
        jLabel4 = new javax.swing.JLabel();
        EIDtxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        ENAMEtxt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new panal.Table3();
        jLabel17 = new javax.swing.JLabel();
        pane31 = new panal.pane3();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        img = new javax.swing.JLabel();
        CIDtxt = new javax.swing.JLabel();
        CNAMEtxt = new javax.swing.JLabel();
        pane12 = new panal.pane1();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        pane13 = new panal.pane1();
        jLabel18 = new javax.swing.JLabel();
        pane14 = new panal.pane1();
        jLabel19 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();

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
        pane21.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 0, 20, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("VOTEING SECTION");
        pane21.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 1130, 30));

        bgpanal1.add(pane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1210, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("VOTER INFORMATION                                                                                                    ووٹر کی معلومات");
        pane11.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 850, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("ووٹر کا نام");
        pane11.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 60, 100, 30));

        VNAMEtxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        VNAMEtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        VNAMEtxt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        VNAMEtxt.setNextFocusableComponent(VCNICtxt);
        VNAMEtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VNAMEtxtActionPerformed(evt);
            }
        });
        pane11.add(VNAMEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 60, 310, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("VOTER CNIC NO#");
        pane11.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 100, 30));

        VCNICtxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        VCNICtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        VCNICtxt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        VCNICtxt.setNextFocusableComponent(VNAMEtxt);
        VCNICtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VCNICtxtActionPerformed(evt);
            }
        });
        VCNICtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                VCNICtxtKeyTyped(evt);
            }
        });
        pane11.add(VCNICtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, 310, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("ووٹر کا شناختی کارڈ نمبر");
        pane11.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 100, 150, 30));

        jLabel13.setBackground(new java.awt.Color(0, 0, 0));
        jLabel13.setOpaque(true);
        pane11.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 50, 140, 5));

        jLabel14.setBackground(new java.awt.Color(0, 0, 0));
        jLabel14.setOpaque(true);
        pane11.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 195, 5));

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 0, 0));
        jLabel33.setText("* (No Dash)");
        pane11.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 80, 30));

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 0, 0));
        jLabel34.setText("* (Required)");
        pane11.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 90, 30));

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 0, 0));
        jLabel35.setText("( لازمی ) *");
        pane11.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 100, 70, 30));

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 0, 0));
        jLabel37.setText("( لازمی ) *");
        pane11.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 70, 90, 20));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("VOTER NAME");
        pane11.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 100, 30));

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 0, 0));
        jLabel38.setText("* (Required)");
        pane11.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 80, 30));

        bgpanal1.add(pane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 870, 140));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("الیکشن حوالہ نمبر");
        pane22.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, 110, 30));

        EIDtxt.setEditable(false);
        EIDtxt.setBackground(new java.awt.Color(255, 204, 204));
        EIDtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        EIDtxt.setText("2");
        EIDtxt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        EIDtxt.setFocusable(false);
        pane22.add(EIDtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 140, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("انتخابات کا نام ");
        pane22.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 60, 100, 30));

        ENAMEtxt.setEditable(false);
        ENAMEtxt.setBackground(new java.awt.Color(255, 204, 204));
        ENAMEtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ENAMEtxt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        ENAMEtxt.setFocusable(false);
        ENAMEtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ENAMEtxtActionPerformed(evt);
            }
        });
        pane22.add(ENAMEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 60, 270, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Election ID");
        pane22.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Election Name");
        pane22.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, 80, 30));

        jLabel11.setBackground(new java.awt.Color(0, 0, 0));
        jLabel11.setOpaque(true);
        pane22.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, 5, 50));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("ELECTION INFORMATION                                                                                          الیکشن کی معلومات");
        pane22.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 850, 40));

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setOpaque(true);
        pane22.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 40, 160, 5));

        jLabel16.setBackground(new java.awt.Color(0, 0, 0));
        jLabel16.setOpaque(true);
        pane22.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 220, 5));

        bgpanal1.add(pane22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 870, 100));

        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        table1.setShowGrid(true);
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table1);

        bgpanal1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, 870, 335));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imges/icons8-elections-100.png"))); // NOI18N
        jLabel17.setText("VOTEING SECTION");
        jLabel17.setOpaque(true);
        bgpanal1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 860, -1));

        img.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jDesktopPane1.add(img);
        img.setBounds(0, 0, 160, 150);

        pane31.add(jDesktopPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, 160, 150));

        CIDtxt.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        CIDtxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CIDtxt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pane31.add(CIDtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 110, 50));

        CNAMEtxt.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        CNAMEtxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CNAMEtxt.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Candidate Name    امیدوار کا نام", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        pane31.add(CNAMEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 280, 70));

        pane12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pane12MouseClicked(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imges/icons8-elections-100.png"))); // NOI18N
        jLabel20.setText("VOTE   ووٹ");
        jLabel20.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel20.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel20.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        pane12.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 220, 160));

        pane31.add(pane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 550, 240, 160));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("ووٹ دینے کے لیے نیچے بٹن پر کلک کریں");
        pane31.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 280, 30));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imges/icons8-arrow-down-60.png"))); // NOI18N
        pane31.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 490, 220, 60));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel24.setText("ID#");
        jLabel24.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pane31.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, 50, 50));

        jLabel26.setBackground(new java.awt.Color(255, 255, 255));
        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("ووٹ ڈالنے سے پہلے امیدوار کا نام چیک کریں۔");
        pane31.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 280, 20));

        jLabel27.setBackground(new java.awt.Color(255, 255, 255));
        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("یہاں ووٹ دیں۔ ");
        pane31.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 280, 40));

        jLabel28.setBackground(new java.awt.Color(0, 0, 0));
        jLabel28.setOpaque(true);
        pane31.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 244, 5));

        jLabel29.setBackground(new java.awt.Color(255, 255, 255));
        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("VOTE HERE");
        pane31.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 280, 30));

        jLabel30.setBackground(new java.awt.Color(255, 255, 255));
        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 255));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Please check Candidate name before Voting");
        pane31.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 300, 20));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 255));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("To Vote Click Below Button");
        pane31.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 460, 220, 30));

        jLabel36.setBackground(new java.awt.Color(255, 255, 255));
        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Note");
        pane31.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 300, 20));

        bgpanal1.add(pane31, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 40, 300, 720));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 255));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Select the candidate here you want to vote                                                      یہاں اس امیدوار کو منتخب کریں جسے آپ ووٹ دینا چاہتے ہیں۔ ");
        bgpanal1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 870, 30));

        pane13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pane13MouseClicked(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imges/icons8-clear-64.png"))); // NOI18N
        jLabel18.setText("CLEAR FORM");
        pane13.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 6, 240, 50));

        bgpanal1.add(pane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 790, 300, 60));

        pane14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pane14MouseClicked(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imges/icons8-back-64.png"))); // NOI18N
        jLabel19.setText("BACK");
        pane14.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 240, 60));

        bgpanal1.add(pane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 790, 300, 60));

        jLabel39.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("SOFTWARE BY Muhammad Uzair                 PH no : 03335323758");
        bgpanal1.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 800, 470, 40));

        getContentPane().add(bgpanal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1210, 860));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ENAMEtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ENAMEtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ENAMEtxtActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table1MouseClicked
        // TODO add your handling code here:
       tableclick();
    }//GEN-LAST:event_table1MouseClicked

    private void pane13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane13MouseClicked
        // TODO add your handling code here:
        clear();
        
    }//GEN-LAST:event_pane13MouseClicked

    private void pane12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane12MouseClicked
        // TODO add your handling code here:
        
        
        if(EIDtxt.getText().equals("") || VNAMEtxt.getText().equals("") || VCNICtxt.getText().equals("") || CIDtxt.getText().equals("") || CNAMEtxt.getText().equals("")){
            JOptionPane.showMessageDialog(this,"ERROR :-\nMissing Inforamation :-\nVoter Name\n   or\nVoter Cnic\n    or\nCandidate not Selected","Error",2);
            return;
        }
        
        if(checkVoter(VCNICtxt.getText())){
            JOptionPane.showMessageDialog(this, "Can Not VOTE\nThis User Already VOTED","Duplicate Vote Error",2);
            return;
        }
        
        
        if(submitVote()==true){
           
            String name = CNAMEtxt.getText();
            clear();
            JOptionPane.showMessageDialog(this, "<html><b style=\"color:green; font-size:24px;\">YOU VOTED, THANK YOU (ووٹ دینے کا شکریہ)"+"</b></html>.","SUCCESS",1);
            
        }else{
            JOptionPane.showMessageDialog(this, "ERROR","Database error",2);
        }
        
        
        
    }//GEN-LAST:event_pane12MouseClicked

    private void pane14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pane14MouseClicked
        // TODO add your handling code here:
        dispose();
        new SELECT_ELECTION().setVisible(true);
    }//GEN-LAST:event_pane14MouseClicked

    private void VNAMEtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VNAMEtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VNAMEtxtActionPerformed

    private void VCNICtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VCNICtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VCNICtxtActionPerformed

    private void VCNICtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VCNICtxtKeyTyped
        // TODO add your handling code here:
        new FieldsLimits().only_numbers(evt);
        int cchar;
        if(VCNICtxt.getText().equals("")){
            cchar=0;
        }else{
            String size = VCNICtxt.getText();
            cchar=size.length();
        }
        
        
      
        new FieldsLimits().limit(evt,cchar,13);
        
    }//GEN-LAST:event_VCNICtxtKeyTyped

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
            java.util.logging.Logger.getLogger(START_VOTING.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(START_VOTING.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(START_VOTING.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(START_VOTING.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new START_VOTING().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CIDtxt;
    private javax.swing.JLabel CNAMEtxt;
    private javax.swing.JTextField EIDtxt;
    private javax.swing.JTextField ENAMEtxt;
    private javax.swing.JTextField VCNICtxt;
    private javax.swing.JTextField VNAMEtxt;
    private panal.bgpanal bgpanal1;
    private javax.swing.JLabel img;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private panal.pane1 pane11;
    private panal.pane1 pane12;
    private panal.pane1 pane13;
    private panal.pane1 pane14;
    private panal.pane2 pane21;
    private panal.pane2 pane22;
    private panal.pane3 pane31;
    private panal.Table3 table1;
    // End of variables declaration//GEN-END:variables
}
