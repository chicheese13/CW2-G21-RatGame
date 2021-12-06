import java.io.File;
import java.io.Serializable;
import javax.sound.sampled.*;

/**
 * Stores a sound file that is controlled through the various methods
 * 
 * @author Dylan Lewis
 * @version 1.0
 */
public class SoundClip implements Serializable {
	private File currentSound;
	private final String FILE_DIRECTORY_START = "src/Sounds/";
	private String soundName;
	private transient Clip playClip;
	private Level currentLevel;
	private boolean serialized;
	private int duration;

	/**
	 * Constructor
	 * 
	 * @param fileName     File name of sound
	 * @param currentLevel Current level being played
	 */
	public SoundClip(String fileName, Level currentLevel) {
		// create a new file with the specified filename.
		this.currentSound = new File(FILE_DIRECTORY_START + fileName + ".wav");
		this.currentLevel = currentLevel;
		try {
			playClip = AudioSystem.getClip();
			playClip.open(AudioSystem.getAudioInputStream(this.currentSound));
			playClip.start();
		} catch (Exception e) {
			System.out.println("Sound Error");
		}
	}

	/**
	 * Resumes playing sound
	 */
	public void resume() {
		playClip.start();
	}

	/**
	 * Pauses sound
	 */
	public void pause() {
		playClip.stop();
	}

	/**
	 * Sets serialization
	 */
	public void setSer() {
		this.serialized = true;
		this.duration = playClip.getFramePosition();
	}

	/**
	 * Ran every tick to perform updates
	 */
	public void tick() {
		if (serialized) {
			try {
				this.playClip = AudioSystem.getClip();
				playClip.open(
						AudioSystem.getAudioInputStream(this.currentSound));
				playClip.setFramePosition(this.duration);
				playClip.start();
			} catch (Exception e) {
				System.out.println("unable to find sound file");
			}
			this.serialized = false;
		} else {
			if (playClip.getFrameLength() == playClip.getFramePosition()) {
				System.out.print("remove self");
				this.currentLevel.despawnSound(this);
			}
		}
	}
}
