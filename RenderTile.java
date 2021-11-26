import javafx.scene.image.Image;
public class RenderTile {
	private Image renderImage;
	
	public RenderTile(String imageText) {
		this.renderImage = new Image (imageText);
	}

	public Image getImage() {
		return this.renderImage;
	}
}
