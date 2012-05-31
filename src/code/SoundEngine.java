package code;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javazoom.jl.player.Player;

/**
 * @author Eric
 * @version 0.1
 */


public class SoundEngine {
    //private Player player;
    public SoundEngine(){
    }

    public static void playSound(String filename){
	Player player = null;
	try {
	    FileInputStream fis     = new FileInputStream("sounds/" + filename + ".mp3");
	    BufferedInputStream bis = new BufferedInputStream(fis);
	    player = new Player(bis);
	}
	catch (Exception e) {
	    System.out.println("Problem playing file " + filename);
	    System.out.println(e);
	}
	final Player soundPlayer = player;
	// run in new thread to play in background
	new Thread() {
	    public void run() {
		try { 
		    soundPlayer.play();
		}catch (Exception e) { System.out.println(e); }
	    }
	}.start();
    }
}
