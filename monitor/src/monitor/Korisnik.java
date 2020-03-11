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
public class Korisnik implements Runnable
{ 
    private Server server;
    public Korisnik(Server server) 
    { 
        this.server = server;
    }
    public void run() 
    { 
        while (true) 
        { 
            try { Thread.currentThread().sleep(5000); }
            catch (InterruptedException e) { e.printStackTrace(); }
            int r=(int) (Math.random()*10);
            server.insert(r);			
	}		
    }
}