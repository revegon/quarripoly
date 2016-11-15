/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quarripoly;
//not used
public class CustomException extends Exception{
    String massage;
    
    public CustomException()
    {
        massage = null;
    }
    
    public CustomException(String msg)
    {
        this.massage = msg;
    }
    
    public String getMassage()
    {
        return this.massage;
    }
}
