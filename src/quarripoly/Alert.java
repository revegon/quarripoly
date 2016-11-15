/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quarripoly;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Alert {
    
    
    /**
     * shows a pop up window
     * @param title, title of the window
     * @param msg, the massage to be showed
     */
    public static void display(String title, String msg)
    {
        Stage displayer = new Stage();
        displayer.initModality(Modality.APPLICATION_MODAL);
        displayer.setTitle(title);
        
        Label massage= new Label(msg);
        massage.setPadding(new Insets(10, 0, 10, 0));
        Button okbtn = new Button("Ok");
        okbtn.setPadding(new Insets(10, 0, 10, 0));
        okbtn.setOnAction(e -> displayer.close());
        VBox layout = new VBox(massage, okbtn);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setAlignment(Pos.CENTER);
       
        Scene scene = new Scene(layout);
        displayer.setScene(scene);
        
        displayer.showAndWait();
    }
    
}
