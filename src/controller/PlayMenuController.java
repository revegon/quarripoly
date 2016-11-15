/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;


public class PlayMenuController extends Controller implements Initializable{
    
    //detemines whether it is server (true) or client (false)
   public static boolean serverFlag;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MainMenuController.terminate=false;
    }    
    
    
    
    /**
     * Load PortTaker View and make server flag true
     * @param e 
     */
    public void create(ActionEvent e) 
    {
        serverFlag = true;
        sceneChange("/fxml/PortTaker.fxml");
    }
    
    
    /**
     * Load AddressTaker View and make server flag false
     * @param e 
     */
    public void join(ActionEvent e)
    {
            serverFlag=false;
            sceneChange("/fxml/AddressTaker.fxml");
    }
    
    
    /**
     * Load MainMenu View 
     * @param e 
     */
    public void back(ActionEvent e)
    {
        sceneChange("/fxml/MainMenu.fxml");
    }

   
}
