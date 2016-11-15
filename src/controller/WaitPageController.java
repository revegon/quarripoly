/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import quarripoly.Datas;
import quarripoly.Server;
import quarripoly.ServerHandler;

/**
 * FXML Controller class
 *
 * @author xenon
 */
public class WaitPageController extends Controller implements Initializable {

    @FXML
    private Label ipShower;
    @FXML
    private Label portShower;

    ServerHandler serverHandler;
    ExecutorService es;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ipShower.setText("Use Server IP Address: " + Inet4Address.getLocalHost().getHostAddress());
            portShower.setText("Use port: " + PortTakerController.port);
        } catch (UnknownHostException ex) {
            System.out.println(ex);
        }
        es = Executors.newCachedThreadPool();
        serverHandler = new ServerHandler(new Runnable() {
            @Override
            public void run() {
                Server server = new Server(Integer.parseInt(PortTakerController.port));
                serverHandler.setServer(server);
                server.accept();

                // randomly turn initializing
                boolean player1Turn, player2turn;
                Random r = new Random();
                int definer = r.nextInt();
                if (definer % 2 == 0) {
                    player1Turn = true;
                    player2turn = false;
                } else {
                    player1Turn = false;
                    player2turn = true;
                }
                GamePlayController.isTurn = player1Turn;

                //synchronizing
                Datas data = new Datas(null, player2turn, GamePlayController.player1Username);
                Server.setData(data);
                while (true) {
                    if (Server.getDataIn() != null) {
                        GamePlayController.player2Username = Server.getDataIn().getMsg();
                        Server.nullIn();
                        break;
                    }
                }

                //changing scene
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        sceneChange("/fxml/GamePlay.fxml", true);
                    }
                });

            }
        });
        es.execute(serverHandler);
    }

    
  
    
    
    /**
     * Cancel Button Action handling
     * stops server 
     * Load PlayMenu View
     * @param e 
     */
    public void cancelBtnPressed(ActionEvent e) {
        try {
            serverHandler.getServer().close();
        } catch (IOException ex) {
            System.out.println("got ex trying to close server");
        } catch (Exception ex) {
            System.out.println(ex.getClass().toString());
        }

        sceneChange("/fxml/playMenu.fxml");

    }
    
    
    

}
