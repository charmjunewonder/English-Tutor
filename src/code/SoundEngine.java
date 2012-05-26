package code;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javazoom.jl.player.Player;

/**
 * @author Eric
 * @version 0.1
 */


public class SoundEngine {
    private Player player;
    public SoundEngine(){
    }

    public void playSound(String filename){
	try {
	    FileInputStream fis     = new FileInputStream(filename);
	    BufferedInputStream bis = new BufferedInputStream(fis);
	    player = new Player(bis);
	}
	catch (Exception e) {
	    System.out.println("Problem playing file " + filename);
	    System.out.println(e);
	}

	// run in new thread to play in background
	new Thread() {
	    public void run() {
		try { player.play(); }
		catch (Exception e) { System.out.println(e); }
	    }
	}.start();
    }
}
