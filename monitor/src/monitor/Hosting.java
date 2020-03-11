/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitor;

/**
 *
 * @author Asija
 */

public class Hosting implements Runnable
{ 
    private Server server;
    public Hosting(Server server) 
    { 
	this.server = server;
    }
    public void run() 
    { 
        while (true) 
        { 
            try { Thread.currentThread().sleep(500); }
            catch (InterruptedException e) { e.printStackTrace(); }
            server.remove();		
	}
    }
}