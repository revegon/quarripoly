/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quarripoly;

import java.io.Serializable;
import javafx.scene.input.KeyEvent;


public class Datas implements Serializable {
    
    private boolean flag;
    KeyEvent keyEvent;
    private String msg;
    
    /**
     * Constructor 
     * @param ke
     * @param flag 
     * @param msg 
     */
    public Datas(KeyEvent ke, boolean flag, String msg)
    {
        this.keyEvent = ke;
        this.msg = msg;
        this.flag = flag;
        
    }
    
    
    /**
     * get the key event in the data
     * @return key event
     */
    public KeyEvent getKeyEvent()
    {
        return keyEvent;
    }
    
    
    /**
     * get the massage in the data
     * @return the massage
     */
    public String getMsg()
    {
        return msg;
    }
    
    /**
     * get the flag in the data
     * @return flag value
     */
    
    public boolean getFlag()
    {
        return flag;
    }
}
