
import java.io.File;
import java.io.Serializable;

import javax.sound.sampled.*;


public class SoundClip  implements Serializable {
	private File currentSound;
	private final String FILE_DIRECTORY_START = "src/Sounds/";
	private Clip playClip;
	private Level currentLevel;
	
	public SoundClip(String fileName, Level currentLevel) {
		//create a new file with the specified filename.
		this.currentSound = new File(FILE_DIRECTORY_START + fileName + ".wav");
		this.currentLevel = currentLevel;
		try {
			playClip = AudioSystem.getClip();
			playClip.open(AudioSystem.getAudioInputStream(this.currentSound));
			playClip.start();
			
			//Thread.sleep(playClip.getMicrosecondLength()/1000);
		} catch (Exception e) {
			System.out.println("Sound Error");
		}
	}
	
	public void resume() {
		playClip.start();
	}
	
	public void pause() {
		playClip.stop();
	}
	
	public void tick() {		
		if (playClip.getFrameLength() == playClip.getFramePosition()) {
			System.out.print("remove self");
			this.currentLevel.despawnSound(this);
		}
	}
}
