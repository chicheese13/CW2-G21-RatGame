import javafx.scene.image.Image;
/**
 * @version 2.0
 * @author Armand Dorosz, Callum Young, Dylan Lewis
 * 
 *         Sterilisation class, which generates ready instance of the male sex
 *         changer,
 *         which is placed on the level
 *
 */

public class RenderTile {
	
	private Image renderImage;

	/**
	 * Method for rendering the tile Image provided with string
	 * @param imageText
	 */
	public RenderTile(String imageText) {
		try {
			this.renderImage = new Image(imageText);
		} catch (Exception e) {
			this.renderImage = new Image("Textures/notfound.png");
		}
	}

	public Image getImage() {
		return this.renderImage;
	}
}
