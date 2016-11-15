/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import quarripoly.Client;
import quarripoly.Datas;
import quarripoly.Server;

/**
 * FXML Controller class
 *
 * @author xenon
 */
public class ResultController extends Controller implements Initializable {

    @FXML
    Label resultLabel;
    @FXML
    Label requestLabel;
    @FXML
    Button playAgainBtn;
    boolean rematch;
    boolean hasRequested;
    boolean isServer;
    Timer timer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (GamePlayController.isTurn) {
            resultLabel.setText("You have Won");
        } else {
            resultLabel.setText("You have Lost");
        }
        requestLabel.setVisible(false);
      //  playAgainBtn.setVisible(false);

        hasRequested = false;
        rematch = false;
        isServer = PlayMenuController.serverFlag;

        Thread t = new Thread(new Runnable() {
            int it = 30;

            @Override
            public void run() {
                
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                       // playAgainBtn.setText("Play Again!! (" + it + ")");
                        it--;
                        if (it <= 0) {
                            playAgainBtn.setDisable(true);
                          //  playAgainBtn.setText("Play Again!!");
                            timer.cancel();
                        }
                    }
                }, 100, 1000);
                while (true) {
                    if (isServer && Server.getDataIn() != null) {
                        Datas data = Server.getDataIn();
                        flagging(data);
                        Server.nullIn();
                        break;
                    } else if (!isServer && Client.getDataIn() != null) {
                        Datas data = Client.getDataIn();
                        flagging(data);
                        Client.nullIn();
                        break;
                    }
                }
            }

        });
        t.start();
    }

    
    /**
     * Play again after one game finished handling
     * @param e 
     */
    public void playAgainPressed(ActionEvent e) {
            if(hasRequested){
                if(isServer) Server.setData(new Datas(null, true, null));
                else Client.setData(new Datas(null, true, null));
//                System.out.println("Problem in Executing pap");
              //  timer.cancel();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        startGame();
                       // System.out.println("Problem in Executing pap");
                    }
                });
                
            }
            else{
                rematch = true;
                if(isServer) Server.setData(new Datas(null, true, null));
                else Client.setData(new Datas(null, true, null));
                requestLabel.setText("Your request has been sent");
                requestLabel.setVisible(true);
            }
    }

    
    
    /**
     *  Load MainMenu view
     * @param e 
     */
    public void mainMenuPressed(ActionEvent e) {
        if (isServer) {
            Server.setData(new Datas(null, false, null));
        }
        else Client.setData(new Datas(null, false, null));
        MainMenuController.terminate = true;
        sceneChange("/fxml/MainMenu.fxml");
    }

    
    /**
     * Exit button action handling
     * @param e 
     */
    public void exitPressed(ActionEvent e) {
        System.exit(0);
    }

    
    
    
    /**
     * Game Start and Load Game Play view 
     */
    private void startGame() {
        if (isServer) {
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
            Datas data = new Datas(null, player2turn, null);
            Server.setData(data);
            while (true) {
                if (Server.getDataIn() != null) {
                    Server.nullIn();
                    break;
                }
            }
        } else {
            while (true) {
                if (Client.getDataIn() != null) {
                    Datas data = Client.getDataIn();
                    GamePlayController.isTurn = data.getFlag();
                    Client.nullIn();
                    break;
                }
            }

            Client.setData(new Datas(null, true, null));
        }

       Platform.runLater(new Runnable() {
            @Override
            public void run() {
                sceneChange("/fxml/GamePlay.fxml", true);
            }
        });
    }

    

    
    /**
     * set request label visible
     * 
     * @param data 
     */
    private void flagging(Datas data) {
        System.out.println("in flaging");
        if (data.getFlag()) {
            if (rematch) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        startGame();
                    }
                });
            } else {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        hasRequested = true;
                        requestLabel.setText("A Rematch has been Requested");
                        requestLabel.setVisible(true);                  
                    }
                });
            }
        } else {
            playAgainBtn.setDisable(true);
            playAgainBtn.setText("Play Again!!");
            timer.cancel();
        }
        System.out.println("exiting flaging");
    }

}
