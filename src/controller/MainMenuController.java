/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

//import quarripoly.controller.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;


public class MainMenuController extends Controller implements Initializable {
    
    
    //is terminate or not 
    public static boolean terminate=false;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    
    /**
     * View Play menu 
     * @param e 
     */
    public void onActionPlay(ActionEvent e)
    {
        
        sceneChange("/fxml/playMenu.fxml");
    }
    
    
     /**
     * View Help menu 
     * @param e 
     */
    public void onActionHelp(ActionEvent e)
    {
        sceneChange("/fxml/help.fxml");
    }
    
    
     /**
     * View About menu 
     * @param e 
     */
    public void onActionAbout(ActionEvent e)
    {
        sceneChange("/fxml/About.fxml");
    }
    
    
     /**
      * Exit Application
      */
    public void onActionExit()
    {
        terminate = true;
        System.exit(0);
    }
    
}
