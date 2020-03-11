/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JLabel;

public class Server
{   
    int velicina;
    int zauzeti[];
    int ogranicenje=15;
    final Lock lock = new ReentrantLock();
    private Condition slobodno=lock.newCondition();
    private Condition pristupiloKorisnika=lock.newCondition();
    private int ukupno=0;   
    ArrayList<JLabel> labels = new ArrayList();
    ArrayList<JLabel> actions = new ArrayList();
    public Server(int num,ArrayList<JLabel> labels,ArrayList<JLabel> actions) 
    { 
        velicina=num;
        zauzeti=new int[velicina];
    	slobodno = lock.newCondition();
	pristupiloKorisnika = lock.newCondition();
    }
    public void updateLabels(){
        for(int i = 0;i<velicina;i++){
            if(zauzeti[i] == 0){
                this.labels.get(i).setText("slobodno");
                this.labels.get(i).setForeground(Color.green);
            }
            else if(zauzeti[i]==1){
                this.labels.get(i).setText("zauzeto");
                this.labels.get(i).setForeground(Color.red);
            }       
            }
    }
    public void insert(int k) 
    { 
        lock.lock();
        while(ukupno>=ogranicenje-4)
        {
            try { slobodno.await();}
            catch (InterruptedException e) { e.printStackTrace(); }
	}
        this.actions.get(1).setText("Zauzimam mesta"+String.valueOf(k));
        ukupno+= k;
        this.updateLabels();
	//System.out.println("Pristupilo je " +k+" korisnka");
	System.out.println("Ukupno prisutnih na server " + ukupno + " korisnika\n");
	pristupiloKorisnika.signal();
	lock.unlock();	
        this.actions.get(1).setText("cekam");
    }
    public void remove() 
    { 
	lock.lock();
	while(ukupno<3)
            {
		try { pristupiloKorisnika.await(); } 
                catch (InterruptedException e) { e.printStackTrace(); }
            }
        int r=(int) (Math.random()*ukupno);
        ukupno-=r;
        this.actions.get(0).setText("Oslobadjam "+r+" mesto");
        this.updateLabels();
        System.out.println("Otislo je " + r + " korisnika sa servera");
        System.out.println("Ukupno prisutnih na server " + ukupno + " korisnika \n");
	if(ukupno<=15)
	{
            //ako na server ima manje od 15 korisnika onda on javlja da moze jos njih da mu pristupi
            slobodno.signal();
	}
        this.actions.get(0).setText("cekam");
	lock.unlock();
    }
}
