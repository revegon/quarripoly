/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import quarripoly.Client;
import quarripoly.Datas;
import quarripoly.Server;

public class GamePlayController extends Controller implements Initializable {

    int ptq=0;
    
    @FXML
    Label player1Label;
    @FXML
    Label player2Label;
    @FXML
    Label warningLabel; // showing warning

    @FXML
    ImageView curser0, curser1, curser2, curser3, curser4, curser5, curser6, curser7; //cursor positions
    @FXML
    ImageView img00, img01, img02, img03, img04, img05, img06, img07;
    @FXML
    ImageView img10, img11, img12, img13, img14, img15, img16, img17;
    @FXML
    ImageView img20, img21, img22, img23, img24, img25, img26, img27;
    @FXML
    ImageView img30, img31, img32, img33, img34, img35, img36, img37;
    @FXML
    ImageView img40, img41, img42, img43, img44, img45, img46, img47;
    @FXML
    ImageView img50, img51, img52, img53, img54, img55, img56, img57;
    @FXML
    ImageView img60, img61, img62, img63, img64, img65, img66, img67;
    @FXML
    ImageView img70, img71, img72, img73, img74, img75, img76, img77;

    private int[][] board; //board dimension
    private int[] freepos;  // free position of the column
    static int pos; // cursor position

    static KeyEvent event;
    static Socket socket;

    public static String player1Username;
    public static String player2Username;
    private Image player1, player2;

    private boolean isServer;   //checking server

    // defines whose turn
    public static boolean isTurn;
    private static boolean stop;

    private Border selectBorder;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pos = 0;
        stop = false;
        warningLabel.setVisible(false);

        isServer = PlayMenuController.serverFlag;

        selectBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

        if (isTurn) {
            player1Label.setBorder(selectBorder);
            player2Label.setBorder(Border.EMPTY);
        } else {
            player2Label.setBorder(selectBorder);
            player1Label.setBorder(Border.EMPTY);
        }

        player1Label.setText(player1Username);
        player2Label.setText(player2Username);

        //initializing the board and free pos
        board = new int[8][8];
        freepos = new int[8];
        for (int i = 0; i < 8; i++) {
            freepos[i] = 7;
            for (int j = 0; j < 8; j++) {
                board[i][j] = 0;
            }
        }

        File file = new File("res/curser.png");
        Image image = new Image(file.toURI().toString());
        curser0.setImage(image);

        file = new File("res/player1.png");
        player1 = new Image(file.toURI().toString());

        file = new File("res/player2.png");
        player2 = new Image(file.toURI().toString());

        Thread t = new Thread(new Runnable() {
            KeyEvent event;
            boolean isSync = true;  // checking data synchronization

            @Override
            public void run() {
                while (!stop) {
                    event = getEvent();
                    
                    //when user is in trun
                    if (event != null && isTurn && isSync) {
                        System.out.print(event.getCode() + " ");
                        isSync = false;
                        if (isServer) {
                            Server.setData(new Datas(event, isTurn, null));
                            while (true) { // 1 persistence
                                if (Server.getDataIn() != null) {
                                    Datas data = Server.getDataIn();
                                    
                                        isSync = true;
                                    
                                    Server.nullIn();
                                    System.out.println("nulled" + ptq++);
                                    break;
                                }
                            }
                        } else {
                            Client.setData(new Datas(event, isTurn, null));
                            while (true) {
                                if (Client.getDataIn() != null) {
                                    Datas data = Client.getDataIn();
                                    
                                        isSync = true;
                                    
                                    Client.nullIn();
                                    System.out.println("nulled" + ptq++);
                                    break;
                                }
                            }
                        }

                        keyPressed(event); //working on the event
                        setEvent(null);  // set event value null

                    }
                    //when user is not in trun
                    else if (!isTurn) {
                        // 1 persistence
                        if (isServer && Server.getDataIn() != null) {
                            Datas data = Server.getDataIn();
                            Server.setData(new Datas(null, true, null));
                            System.out.print(data.getKeyEvent().getCode() + " ");
                            keyPressed(data.getKeyEvent());
                            Server.nullIn();
                            System.out.println("nulled" + ptq++);
                        }
                        if (!isServer && Client.getDataIn() != null) {
                            Client.setData(new Datas(null, true, null));
                            Datas data = Client.getDataIn();
                            System.out.print(data.getKeyEvent().getCode() + " ");
                            keyPressed(data.getKeyEvent());
                            Client.nullIn();
                            System.out.println("nulled" + ptq++);
                        }
                    }
                }
            }
        });

        t.start();
    }

    
    
    
    
    
    
    /**
     * Cursor moving to the left
     */
    public void changePosToLeft() {
        Image img = null;
        switch (pos) {
            case 1:
                img = curser1.getImage();
                curser0.setImage(img);
                curser1.setImage(null);
                pos--;
                break;
            case 2:
                img = curser2.getImage();
                curser1.setImage(img);
                curser2.setImage(null);
                pos--;
                break;
            case 3:
                img = curser3.getImage();
                curser2.setImage(img);
                curser3.setImage(null);
                pos--;
                break;
            case 4:
                img = curser4.getImage();
                curser3.setImage(img);
                curser4.setImage(null);
                pos--;
                break;
            case 5:
                img = curser5.getImage();
                curser4.setImage(img);
                curser5.setImage(null);
                pos--;
                break;
            case 6:
                img = curser6.getImage();
                curser5.setImage(img);
                curser6.setImage(null);
                pos--;
                break;
            case 7:
                img = curser7.getImage();
                curser6.setImage(img);
                curser7.setImage(null);
                pos--;
                break;
        }

    }

    
    
    
    
    
    /**
     * Cursor moving to the Right
     */
    public void changePosToRight() {
        Image img = null;
        switch (pos) {
            case 0:
                img = curser0.getImage();
                curser1.setImage(img);
                curser0.setImage(null);
                pos++;
                break;
            case 1:
                img = curser1.getImage();
                curser2.setImage(img);
                curser1.setImage(null);
                pos++;
                break;
            case 2:
                img = curser2.getImage();
                curser3.setImage(img);
                curser2.setImage(null);
                pos++;
                break;
            case 3:
                img = curser3.getImage();
                curser4.setImage(img);
                curser3.setImage(null);
                pos++;
                break;
            case 4:
                img = curser4.getImage();
                curser5.setImage(img);
                curser4.setImage(null);
                pos++;
                break;
            case 5:
                img = curser5.getImage();
                curser6.setImage(img);
                curser5.setImage(null);
                pos++;
                break;
            case 6:
                img = curser6.getImage();
                curser7.setImage(img);
                curser6.setImage(null);
                pos++;
                break;
        }

    }

    
    
    
    /**
     * Perform the action 
     */
    public void performAction() {
        if (freepos[pos] >= 0) {
            warningLabel.setVisible(false);
            if (isTurn) {
                setImage(player1, 1);
                player1Label.setBorder(Border.EMPTY);
                player2Label.setBorder(selectBorder);
            } else { 
                setImage(player2, 2);
                player1Label.setBorder(selectBorder);
                player2Label.setBorder(Border.EMPTY);
            }
            if (isVictorious(freepos[pos], pos)) {
                stop = true;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        sceneChange("/fxml/Result.fxml");
                    }
                });

            } else {
                isTurn = !isTurn;
                freepos[pos]--;
            }

        } else {
            warningLabel.setVisible(true);
        }

    }

    
    
    
    
    
    /**
     * Set the image in the board
     * @param image , the image to be set 
     * @param player, image set by whom 
     */
    private void setImage(Image image, int player) {
        int selected = freepos[pos] * 10 + pos;
        switch (selected) {
            case 0:
                img00.setImage(image);
                board[0][0] = player;
                break;
            case 1:
                img01.setImage(image);
                board[0][1] = player;
                break;
            case 2:
                img02.setImage(image);
                board[0][2] = player;
                break;
            case 3:
                img03.setImage(image);
                board[0][3] = player;
                break;
            case 4:
                img04.setImage(image);
                board[0][4] = player;
                break;
            case 5:
                img05.setImage(image);
                board[0][5] = player;
                break;
            case 6:
                img06.setImage(image);
                board[0][6] = player;
                break;
            case 7:
                img07.setImage(image);
                board[0][7] = player;
                break;
            case 10:
                img10.setImage(image);
                board[1][0] = player;
                break;
            case 11:
                img11.setImage(image);
                board[1][1] = player;
                break;
            case 12:
                img12.setImage(image);
                board[1][2] = player;
                break;
            case 13:
                img13.setImage(image);
                board[1][3] = player;
                break;
            case 14:
                img14.setImage(image);
                board[1][4] = player;
                break;
            case 15:
                img15.setImage(image);
                board[1][5] = player;
                break;
            case 16:
                img16.setImage(image);
                board[1][6] = player;
                break;
            case 17:
                img17.setImage(image);
                board[1][7] = player;
                break;
            case 20:
                img20.setImage(image);
                board[2][0] = player;
                break;
            case 21:
                img21.setImage(image);
                board[2][1] = player;
                break;
            case 22:
                img22.setImage(image);
                board[2][2] = player;
                break;
            case 23:
                img23.setImage(image);
                board[2][3] = player;
                break;
            case 24:
                img24.setImage(image);
                board[2][4] = player;
                break;
            case 25:
                img25.setImage(image);
                board[2][5] = player;
                break;
            case 26:
                img26.setImage(image);
                board[2][6] = player;
                break;
            case 27:
                img27.setImage(image);
                board[2][7] = player;
                break;
            case 30:
                img30.setImage(image);
                board[3][0] = player;
                break;
            case 31:
                img31.setImage(image);
                board[3][1] = player;
                break;
            case 32:
                img32.setImage(image);
                board[3][2] = player;
                break;
            case 33:
                img33.setImage(image);
                board[3][3] = player;
                break;
            case 34:
                img34.setImage(image);
                board[3][4] = player;
                break;
            case 35:
                img35.setImage(image);
                board[3][5] = player;
                break;
            case 36:
                img36.setImage(image);
                board[3][6] = player;
                break;
            case 37:
                img37.setImage(image);
                board[3][7] = player;
                break;
            case 40:
                img40.setImage(image);
                board[4][0] = player;
                break;
            case 41:
                img41.setImage(image);
                board[4][1] = player;
                break;
            case 42:
                img42.setImage(image);
                board[4][2] = player;
                break;
            case 43:
                img43.setImage(image);
                board[4][3] = player;
                break;
            case 44:
                img44.setImage(image);
                board[4][4] = player;
                break;
            case 45:
                img45.setImage(image);
                board[4][5] = player;
                break;
            case 46:
                img46.setImage(image);
                board[4][6] = player;
                break;
            case 47:
                img47.setImage(image);
                board[4][7] = player;
                break;
            case 50:
                img50.setImage(image);
                board[5][0] = player;
                break;
            case 51:
                img51.setImage(image);
                board[5][1] = player;
                break;
            case 52:
                img52.setImage(image);
                board[5][2] = player;
                break;
            case 53:
                img53.setImage(image);
                board[5][3] = player;
                break;
            case 54:
                img54.setImage(image);
                board[5][4] = player;
                break;
            case 55:
                img55.setImage(image);
                board[5][5] = player;
                break;
            case 56:
                img56.setImage(image);
                board[5][6] = player;
                break;
            case 57:
                img57.setImage(image);
                board[5][7] = player;
                break;
            case 60:
                img60.setImage(image);
                board[6][0] = player;
                break;
            case 61:
                img61.setImage(image);
                board[6][1] = player;
                break;
            case 62:
                img62.setImage(image);
                board[6][2] = player;
                break;
            case 63:
                img63.setImage(image);
                board[6][3] = player;
                break;
            case 64:
                img64.setImage(image);
                board[6][4] = player;
                break;
            case 65:
                img65.setImage(image);
                board[6][5] = player;
                break;
            case 66:
                img66.setImage(image);
                board[6][6] = player;
                break;
            case 67:
                img67.setImage(image);
                board[6][7] = player;
                break;
            case 70:
                img70.setImage(image);
                board[7][0] = player;
                break;
            case 71:
                img71.setImage(image);
                board[7][1] = player;
                break;
            case 72:
                img72.setImage(image);
                board[7][2] = player;
                break;
            case 73:
                img73.setImage(image);
                board[7][3] = player;
                break;
            case 74:
                img74.setImage(image);
                board[7][4] = player;
                break;
            case 75:
                img75.setImage(image);
                board[7][5] = player;
                break;
            case 76:
                img76.setImage(image);
                board[7][6] = player;
                break;
            case 77:
                img77.setImage(image);
                board[7][7] = player;
                break;
            default:
                System.out.println("unknown coordinate---------------------------------------");

        }
    }

    
    
    /**
     * Pause the game
     * unfinished*
     */
    public void pause() {
        System.out.println("prob aroused");
    }

    
    
    
    /**
     * Key Event Handling
     * @param event, the event to be handled 
     */
    public synchronized void keyPressed(KeyEvent event) {
//        switch (event.getCode()) {
//            case A:
//                changePosToLeft();
//                break;
//            case S:
//                changePosToRight();
//                break;
//            case DOWN:
//            case SPACE:
//                performAction();
//                break;
//            case ENTER:
//                pause();
//                break;
//            default:
//                System.out.println("Unrecog is pressed");
//        }

        
        if(event.getCode().toString().equals("A") || event.getCode().toString().equals("LEFT")) changePosToLeft();
        else if(event.getCode().toString().equals("S") || event.getCode().toString().equals("RIGHT")) changePosToRight();
        else if(event.getCode().toString().equals("SPACE") || event.getCode().toString().equals("DOWN")) performAction();
    }

    
    
    
    /**
     * Check the player whether victorious or not
     * @param x , coordinate x of the latest coin 
     * @param y , coordinate y of the latest coin 
     * @return true if the player is victorious otherwise false
     */
    public boolean isVictorious(int x, int y) {
        int num, type, i;
        if (isTurn) {
            type = 1;
        } else {
            type = 2;
        }

        //horizontal y =a
        for (num = 0, i = 0; true; i++) {
            if (x + i > 7) {
                break;
            }
            if (board[x + i][y] == type) {
                num++;
            } else {
                break;
            }
        }
        for (i = 1; true; i++) {
            if (x - i < 0) {
                break;
            }
            if (board[x - i][y] == type) {
                num++;
            } else {
                break;
            }
        }
        if (num >= 4) {
            return true;
        }

        // vertical x=a
        for (num = 0, i = 0; true; i++) {
            if (y + i > 7) {
                break;
            }
            if (board[x][y + i] == type) {
                num++;
            } else {
                break;
            }
        }
        for (i = 1; true; i++) {
            if (y - i < 0) {
                break;
            }
            if (board[x][y - i] == type) {
                num++;
            } else {
                break;
            }
        }
        if (num >= 4) {
            return true;
        }

        // angular(both same) y=x
        for (num = 0, i = 0; true; i++) {
            if (y + i > 7 || x + i > 7) {
                break;
            }
            if (board[x + i][y + i] == type) {
                num++;
            } else {
                break;
            }
        }
        for (i = 1; true; i++) {
            if (y - i < 0 || x - i < 0) {
                break;
            }
            if (board[x - i][y - i] == type) {
                num++;
            } else {
                break;
            }
        }
        if (num >= 4) {
            return true;
        }

        //negative angular(both inverse) y=-x
        for (num = 0, i = 0; true; i++) {
            if (x + i > 7 || y - i < 0) {
                break;
            }
            if (board[x + i][y - i] == type) {
                num++;
            } else {
                break;
            }
        }
        for (i = 1; true; i++) {
            if (y + i > 7 || x - i < 0) {
                break;
            }
            if (board[x - i][y + i] == type) {
                num++;
            } else {
                break;
            }
        }
        if (num >= 4) {
            return true;
        }

        return false;
    }

    
    
    /**
     * Set Key Event
     * @param e 
     */
    public synchronized static void setEvent(KeyEvent e) {
        event = e;
    }

    
    /**
     * Get key event
     * @return 
     */
    public synchronized KeyEvent getEvent() {
        return event;
    }
}
