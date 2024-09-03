/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package code_files;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;


/**
 *
 * @author User1
 */

public class ConnectDB {
Connection con ;
PreparedStatement insert;
ResultSet rs;
    public Connection Connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");  
            con= DriverManager.getConnection("jdbc:mysql://localhost/voting_db","root","");
        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null,"DATABASE ERROR\n"+e,"ERROR",2);
        }
        return con;
    }
}





