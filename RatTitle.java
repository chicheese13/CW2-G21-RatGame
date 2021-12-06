import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * @author Josh and Lorenzo
 * @version 1.0
 *          A class to simplify construction of the title on the menu screen.
 */
public class RatTitle extends Pane {
    private Text text;

    /**
     * @param text the text to be displayed as the title
     */
    public RatTitle(String title) {
        text = new Text(title);
        text.setFont(text.getFont().font(40));
        text.setFill(Color.LIGHTGRAY);
        text.setEffect(new DropShadow(30, Color.BLACK));

        getChildren().addAll(text);
    }

    /**
     * @return double returns the width of the title
     */
    public double getTitleWidth() {
        return text.getLayoutBounds().getWidth();
    }

    /**
     * @return double returns the height of the title
     */
    public double getTitleHeight() {
        return text.getLayoutBounds().getHeight();
    }
}
