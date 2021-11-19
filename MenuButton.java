import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class MenuButton extends StackPane {
    private Text text;

    public MenuButton(String name) {
        text = new Text(name);
    }
}
