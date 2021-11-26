
import java.io.File;
import javax.sound.sampled.*;


public class SoundClip {
	private File currentSound;
	private final String FILE_DIRECTORY_START = "src/Sounds/";
	
	
	public SoundClip(String fileName) {
		//create a new file with the specified filename.
		this.currentSound = new File(FILE_DIRECTORY_START + fileName + ".wav");
	}
	
	public void play() {
		try {
			Clip playClip = AudioSystem.getClip();
			playClip.open(AudioSystem.getAudioInputStream(this.currentSound));
			playClip.start();
			
			//Thread.sleep(playClip.getMicrosecondLength()/1000);
		} catch (Exception e) {
			System.out.println("Sound Error");
		}
	}
}
