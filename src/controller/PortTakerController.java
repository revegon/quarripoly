/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class PortTakerController extends Controller implements Initializable {

    @FXML
    TextField portField;
    @FXML
    TextField usernameField;

    @FXML
    Label warningLabel;

    public static String port;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        warningLabel.setVisible(false);
    }

    
    
    /**
     * OK button Pressed handling
     * Getting Inputs from the view 
     * Load wait page
     * @param e 
     */
    public void okBtnPresssed(ActionEvent e) {
        if (isValid()) {
            GamePlayController.player1Username = usernameField.getText();
            port = portField.getText();
            sceneChange("/fxml/WaitPage.fxml");
        } else {
            warningLabel.setVisible(true);
        }

    }

    
    
    
    /**
     * Back Button Pressed Handling
     * @param e 
     */
    public void backbtnpressed(ActionEvent e) {
        sceneChange("/fxml/playMenu.fxml");
    }

    
    
    
    /**
     * Check Input field validation
     * @return true for valid otherwise false
     */
    private boolean isValid() {
        String p = portField.getText();
        String username = usernameField.getText();
        if (p == null || username == null) {
            return false;
        } else if (p.length() > 5 || p.length() <= 0 || username.length() <= 0) {
            return false;
        }
        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) < '0' && p.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }
    
    
    
    
    
}
