import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class RatTitle extends Pane {
    private Text text;

    public RatTitle(String name) {
        text = new Text(name);
        text.setFont(text.getFont().font(40));
        text.setFill(Color.LIGHTGRAY);
        text.setEffect(new DropShadow(30, Color.BLACK));

        getChildren().addAll(text);
    }

    /**
     * @return double
     */
    public double getTitleWidth() {
        return text.getLayoutBounds().getWidth();
    }

    /**
     * @return double
     */
    public double getTitleHeight() {
        return text.getLayoutBounds().getHeight();
    }
}
