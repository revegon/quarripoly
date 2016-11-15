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
import quarripoly.Client;
import quarripoly.Datas;

public class AddressTakerController extends Controller implements Initializable {

    @FXML
    TextField usernameField;
    @FXML
    TextField ipAddressField;
    @FXML
    TextField portField;
    @FXML
    Label warningLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        warningLabel.setVisible(false);
    }

    /**
     * Ok Button Pressed 
     * Loading Game Play View
     * @param e
     */
    public void okBtnPresssed(ActionEvent e) {
        if (isValid()) // if(true)
        {
            GamePlayController.player1Username = usernameField.getText();
            String ipAddress = ipAddressField.getText();
            String port = portField.getText();

            Client client = new Client(ipAddress, Integer.parseInt(port));

            //getting initialized data
            while (true) {
                if (Client.getDataIn() != null) {
                    Datas data = Client.getDataIn();
                    GamePlayController.isTurn = data.getFlag();
                    GamePlayController.player2Username = data.getMsg();
                    Client.nullIn();
                    break;
                }
            }
            Client.setData(new Datas(null, false, GamePlayController.player1Username));

            //changing scene
            sceneChange("/fxml/GamePlay.fxml", true);

        } else {
            warningLabel.setVisible(true);
        }
    }

    
    
    /**
     * Back Button Pressed
     * Load Play Menu View
     * @param e 
     */
    public void backbtnpressed(ActionEvent e) {
        sceneChange("/fxml/playMenu.fxml");
    }

    
    
    
    /**
     * Validation of username, port, IP address
     * @return true if the field are correct otherwise false
     */
    private boolean isValid() {
        String ip = ipAddressField.getText();
        String port = portField.getText();
        String username = usernameField.getText();
        if (port == null || username == null || ip == null) {
            return false;
        } else if (port.length() > 5 || ip.length() > 15 || port.length()<=0 || 
                username.length() <= 0 || ip.length() <= 0) {
            return false;
        }

        // port validation
        for (int i = 0; i < port.length(); i++) {
            if (port.charAt(i) < '0' && port.charAt(i) > '9') {
                return false;
            }
        }

        // ip Validation
        try {
            if (ip == null || ip.isEmpty()) {
                return false;
            }

            String[] parts = ip.split("\\.");
            if (parts.length != 4) {
                return false;
            }

            for (String s : parts) {
                int i = Integer.parseInt(s);
                if ((i < 0) || (i > 255)) {
                    return false;
                }
            }
            if (ip.endsWith(".")) {
                return false;
            }

            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
