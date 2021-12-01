import javafx.scene.image.Image;
public class RenderTile {
	private Image renderImage;
	
	public RenderTile(String imageText) {
		try {
			this.renderImage = new Image (imageText);
		} catch (Exception e) {
			this.renderImage = new Image ("Textures/notfound.png");
		}
	}

	public Image getImage() {
		return this.renderImage;
	}
}
