
import javafx.scene.image.Image;
import java.math.BigDecimal;

public class Bomb extends Item {

	private Image bomb = new Image("/items/bomb.png");

	private final int NUMBER_OF_BOMB_FRAMES = 17;
	private final int EXPLOSION_FRAME = 6;

	private double tickCounter = 0;
	private double delayCount = 0;
	private int frameTime = 0;
	private int pictureNumber = 1;
	private int delayedPictureNumber = 4;
	private boolean timerStarted = false;

	public Bomb(Position objectPosition, Level currentLevel) {
		this.renderSprite = bomb;
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
	}

	public void startTimer() {

		SoundClip fuse = new SoundClip("Fuse");
		fuse.play();

	}

	private Image loadImage(int pictureNumber) {

		Image bomb = new Image("/bomb_images/bomb" + String.valueOf(pictureNumber) + ".png");

		return bomb;
	}

	private Image loadDelayImage(int delayedPictureNumber) {

		Image delayBomb = new Image("/delayed_bomb_images/bomb" + String.valueOf(delayedPictureNumber) + ".png");

		return delayBomb;
	}

	public void tick() {
		
		SoundClip background = new SoundClip("BombBackground");

		if (timerStarted == false) {
			
			if(delayCount == 0) {
				
				background.play();
			}

			if (delayCount % 1000 == 0) {
				SoundClip tick = new SoundClip("Tick");
				tick.play();
				this.renderSprite = loadDelayImage(delayedPictureNumber);
				if (delayedPictureNumber > 1) {
					delayedPictureNumber--;

				}
			}

			delayCount = delayCount + 12.5;

			if (delayCount >= 4000) {
				//background.stop();
				startTimer();
				timerStarted = true;
			}
		} else {

			if ((tickCounter == frameTime)) {
				
				if (pictureNumber == EXPLOSION_FRAME) {
					explode();
					SoundClip explosion = new SoundClip("Explosion");
					explosion.play();
				}

				this.renderSprite = loadImage(pictureNumber);
				if (pictureNumber < NUMBER_OF_BOMB_FRAMES) {
					pictureNumber++;
				}
			}

			tickCounter = tickCounter + 100;

			if (tickCounter % 100 == 0) {
				frameTime = frameTime + 100;
			}

			if (frameTime > NUMBER_OF_BOMB_FRAMES * 100) {
				
				this.currentLevel.despawnItem(this);
			}
		}
	}

	// Method to deal damage horizontally and vertically
	private void explode() {
		System.out.println("BOOM");

		// spawns new items for every every available tile on the x and y coords.
		this.currentLevel.spawnTempTile(new Explosion(
				new Position(this.getObjectPosition()[0], this.getObjectPosition()[1]), this.currentLevel));

		// add explosions north south east and west
		BigDecimal startX = this.getObjectPosition()[0];
		BigDecimal startY = this.getObjectPosition()[1];

		BigDecimal northY = startY.subtract(new BigDecimal("1"));
		BigDecimal southY = startY.add(new BigDecimal("1"));

		BigDecimal eastX = startX.subtract(new BigDecimal("1"));
		BigDecimal westX = startX.add(new BigDecimal("1"));

		// west
		while (this.currentLevel.tileAvailable(eastX.add(new BigDecimal("1")), startY, 'W')) {
			this.currentLevel.spawnTempTile(new Explosion(new Position(eastX, startY), this.currentLevel));
			eastX = eastX.subtract(new BigDecimal("1"));
		}

		// east
		while (this.currentLevel.tileAvailable(westX.subtract(new BigDecimal("1")), startY, 'E')) {
			this.currentLevel.spawnTempTile(new Explosion(new Position(westX, startY), this.currentLevel));
			westX = westX.add(new BigDecimal("1"));
		}

		while (this.currentLevel.tileAvailable(startX, northY.add(new BigDecimal("1")), 'N')) {
			this.currentLevel.spawnTempTile(new Explosion(new Position(startX, northY), this.currentLevel));
			northY = northY.subtract(new BigDecimal("1"));
		}

		// south
		while (this.currentLevel.tileAvailable(startX, southY.subtract(new BigDecimal("1")), 'S')) {
			this.currentLevel.spawnTempTile(new Explosion(new Position(startX, southY), this.currentLevel));
			southY = southY.add(new BigDecimal("1"));
		}

	}

	public Image getSprite() {
		return this.renderSprite;
	}

}
