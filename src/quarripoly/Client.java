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
import java.net.Socket;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Client {

    private Socket client;
    private ObjectInputStream fromServer;
    private ObjectOutputStream toServer;
    
    public Thread send;
    
    private static Datas data = null , dataIn = null;
    
    
    /**
     * Client Setup 
     * @param serverIP IP address of the server
     * @param port 
     */
    public Client(String serverIP, int port) 
    {
        try {
            client = new Socket(serverIP, port);
            fromServer = new ObjectInputStream(client.getInputStream());
            toServer = new ObjectOutputStream(client.getOutputStream());
            toServer.flush();
            
            sendData();
            receiveData();
        } catch (IOException ex) {

            Alert.display("Error", "Can't find any  Server  in " + serverIP );
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
     * Sends data using a output stream
     */
    private void sendData()
    {
        send = new Thread(new Runnable() {
            Datas data;
            @Override
            public void run() {
                
                while(!MainMenuController.terminate)
                {
                    data = getData();
                    if(data != null)
                    {
                        try {
                          
                            toServer.writeObject(data);
                            
                            toServer.flush();
                            data = null;
                            setData(null);
                        } catch (IOException ex) {

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
     * Receives data using a input stream
     */
    private void receiveData()
    {
         Thread receive = new Thread(new Runnable() {
             
            @Override
            public void run() {
               
                while(!MainMenuController.terminate){
                try {
                    if(client.getInputStream().available()>0)
                    {
                    
                        dataIn = (Datas) fromServer.readObject();
                       
                    }
                    
                    
                } catch (IOException ex) {
                    System.out.println("IOEx in receive");
                } catch (ClassNotFoundException ex) {
                    System.out.println("CNFEx in receive");
                }catch(Exception e){
                    System.out.println("Ex in receive");
                }
                }
                
            }
        });
        receive.start();
    }
    
    
    
    
    
    /**
     * Set client Data with synchronized method
     * sets data to be sent
     * @param data the data to be sent
     */
    public static synchronized void setData(Datas data)
    {
       Client.data =data;
        
    }
    
    
    
    /**
     * Get client Data with synchronized method
     * @return the data to be sent
     */
    public static synchronized  Datas getData()
    {
        return Client.data;
    }
    
    
    
    
    /**
     * receive server Data with synchronized method
     * @return  server data 
     */
    public static synchronized Datas getDataIn()
    {
        return Client.dataIn;
     }
   
    
    
    
    /**
     * Sets input data null
     */
   public static synchronized void nullIn()
   {
       dataIn=null;
   }
   
   
    
}
