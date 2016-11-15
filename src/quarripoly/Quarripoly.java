/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quarripoly;

import static controller.MainMenuController.terminate;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Quarripoly extends Application {
    public static  Stage mainWindow;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
       mainWindow = primaryStage;
       
        
       
       Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
       Scene scene = new Scene(root, 300, 400);
        
       mainWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                terminate=true;
                System.exit(0);
            }
        });
       mainWindow.setTitle("Quarripoly");
       mainWindow.setScene(scene);
       mainWindow.setResizable(false);
       mainWindow.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
 
    
}
