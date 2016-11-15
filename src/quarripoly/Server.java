/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quarripoly;

import controller.MainMenuController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Server{
    
    
    public ServerSocket serverSocket;
    public Socket server;
    private ObjectOutputStream toClient;
    private ObjectInputStream fromClient;
    
    
    private static Datas data=null, dataIn=null;
    
/**
 * Creates a server socket to connect with client
 * @param port port address 
 */
    public Server(int port)
    {
        try {
            
            serverSocket = new ServerSocket(port);

        } catch (IOException ex) {
           Alert.display("Error", "Can't create the Server");
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
                Scene scene = new Scene(root);
                Quarripoly.mainWindow.setScene(scene);
                Quarripoly.mainWindow.show();
            } catch (IOException ex1) {
                Alert.display("Error", "System just Crashed");
                System.exit(0);
            }
        }
        
        
    }
    
    
    
    
    
    /**
     * creates a output stream to send data
     */
    private void sendData()
    {
        Thread send = new Thread(new Runnable() {
            
            Datas data;
            
            @Override
            public void run() {
                
                while(!MainMenuController.terminate)
                {
                    data =getData();
                    if(data != null)
                    {
                        try {
                           
                            toClient.writeObject(data);
                            toClient.flush();
                            //data = null;
                            setData(null);
                        } catch (IOException ex) {
//                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);

                                System.out.println("OIE in send data");
                        }catch(Exception e){
                            System.out.println("EX in send data");
                        }
                        
                    }
                }
               
            }
        });
        send.start();
    }
    
    
    
    
    /**
     * creates a input stream to receive data
     */
    
    private void receiveData()
    {
        Thread receive = new Thread(new Runnable() {
            @Override
            public void run() {
                
                while(!MainMenuController.terminate){
                try {
                    if(server.getInputStream().available()>0)
                    {
                       
                        dataIn = (Datas) fromClient.readObject();
                       
//                        fXMLController.show(dataIn);
                       // fXMLController.viewer.appendText(dataIn.getMsg());
                    }
                    
                    
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }catch(Exception e){
                    System.out.println("Ex in receive");
                }
                }
            }
        });
        receive.start();
    }
    
    
    
    
    /**
     * set the data to be sent
     * @param data the data to be sent
     */
    public static synchronized void setData(Datas data)
    {
       Server.data =data;
        
    }
    
    
    /**
     * get the data to be sent
     * @return data to be sent
     */
    
    public static synchronized  Datas getData()
    {
        return Server.data;
    }
    
    
    /**
     * get the input data from server
     * @return input data
     */
    public  static synchronized Datas getDataIn()
    {
        return Server.dataIn;
    }
    
    
    /**
     * null the input data 
     */
    public static synchronized void nullIn()
    {
        dataIn= null;
    }
    
    
    
    /**
     * stops the server socket from taking any client
     * @throws IOException 
     */
    public synchronized void close() throws IOException
    {
        serverSocket.close();
    }

    
    
    /**
     * accepts the client and creates the data input-output streams
     */
   public void accept() 
   {
        try {
            server = serverSocket.accept();
            
            toClient = new ObjectOutputStream(server.getOutputStream());
            toClient.flush();
            fromClient = new ObjectInputStream(server.getInputStream());
            
            sendData();
            receiveData();
            
        } catch (IOException ex) {
            System.out.println("server was crashed");
        }
   }
    
}
