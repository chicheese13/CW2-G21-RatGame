
import java.io.File;
import java.io.Serializable;

import javax.sound.sampled.*;

public class SoundClip implements Serializable {
    private File currentSound;
    private final String FILE_DIRECTORY_START = "src/Sounds/";
    private String soundName;
    transient private Clip playClip;
    private Level currentLevel;
    private boolean serialized;
    private int duration;

    public SoundClip(String fileName, Level currentLevel) {
        // create a new file with the specified filename.
        this.currentSound = new File(FILE_DIRECTORY_START + fileName + ".wav");
        this.currentLevel = currentLevel;
        try {
            playClip = AudioSystem.getClip();
            playClip.open(AudioSystem.getAudioInputStream(this.currentSound));
            playClip.start();
            // Thread.sleep(playClip.getMicrosecondLength()/1000);
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

    public void setSer() {
        this.serialized = true;
        this.duration = playClip.getFramePosition();
    }

    public void tick() {
        if (serialized) {
            try {
                this.playClip = AudioSystem.getClip();
                playClip.open(
                        AudioSystem.getAudioInputStream(this.currentSound));
                playClip.setFramePosition(this.duration);
                playClip.start();
            } catch (Exception e) {

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
