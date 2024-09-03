/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package code_files;

import java.awt.event.KeyEvent;
import javax.swing.JTextField;


public class FieldsLimits{
    
    
    
    
    public void only_numbers_with_point(KeyEvent ke){
        
        if(!(ke.getKeyChar()>='0' && ke.getKeyChar()<='9' || ke.getKeyChar()=='.' ) ){
            ke.consume();
        }
        
    }
    
    public void only_numbers(KeyEvent ke){
        
        if(!(ke.getKeyChar()>='0' && ke.getKeyChar()<='9' ) ){
            ke.consume();
        }
        
    }
   
    
    public void limit(KeyEvent ke ,int cchar,int lmt){
        
        if(cchar>lmt){
            ke.consume();
        }
        
    }
    
}
