/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quarripoly;


public class ServerHandler extends Thread{
    
    Server server;
/**
 * takes a runnable and creates a thread
 * @param r Runnable object
 */
    public ServerHandler(Runnable r) {
        super(r);
    }
    
    
    /**
     * sets the server to handle
     * @param s Server object
     */
    public void setServer(Server s)
    {
        this.server = s;
    }
    
    
    /**
     * get the Server object
     * @return server
     */
    public Server getServer()
    {
        return server;
    }
}
