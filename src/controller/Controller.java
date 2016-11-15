/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import quarripoly.Quarripoly;


public class Controller {
    
    
    /**
     * Changes the Scene according to file without key event
     * @param file , is the path of fxml file 
     */
    protected void sceneChange(String file)
    {
        Parent root = null;
        try {
             root = FXMLLoader.load(getClass().getResource(file));
        } catch (IOException ex) {
            System.out.println(ex);
        }
        Quarripoly.mainWindow.setScene(new Scene(root));
        Quarripoly.mainWindow.show();
       
    }
    
    
    
    
    
    
    /**
     * Changes the Scene according to file with key event
     * @param file , is the path of fxml file 
     * @param b , just to overload the method
     */
     protected void sceneChange(String file, Boolean b)
     {
         Parent root = null;
        try {
             root = FXMLLoader.load(getClass().getResource(file));
        } catch (IOException ex) {
            System.out.println("ex");
        }
        Scene newScene = new Scene(root);
        newScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
             @Override
             public void handle(KeyEvent event) {
                 
                 GamePlayController.setEvent(event);
             }
         });
        Quarripoly.mainWindow.setScene(newScene);
        Quarripoly.mainWindow.show();
     }
     
     
     
}
