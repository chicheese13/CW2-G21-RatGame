import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Row extends Pane {
    private Text text;
    private DropShadow shadow = new DropShadow(5, Color.BLACK);

    public Row(String display) {
        text = new Text(display);
        text.setEffect(shadow);
    }
}