import javafx.beans.binding.Bindings;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author Joshua Hall, Lorenzo Labarta Arilla
 * @version 1.0
 *          manage the sterilization visualization.
 */
public class MenuItem extends Pane {

    private Text text;
    /**
     * effects that will modify the text.
     */
    private Effect shadow = new DropShadow(5, Color.BLACK);
    private Effect blur = new BoxBlur(1, 1, 3);

    /**
     * create the area of the sterilization with its properties.
     * 
     * @param name
     */
    public MenuItem(String name) {
        Polygon bg = new Polygon(0, 0, 225, 0, 225, 30, 0, 30);
        bg.setStroke(Color.color(1, 1, 1, 0.9));
        bg.setEffect(new GaussianBlur());

        /**
         * change the colour of the polygon when it is pressed.
         */
        bg.fillProperty()
                .bind(Bindings.when(pressedProperty())
                        .then(Color.color(0, 0, 0, 0.75))
                        .otherwise(Color.color(0, 0, 0, 0.25)));

        /**
         * set text with the @param name
         * and add the effects on it.
         */
        text = new Text(name);
        text.setTranslateX(5);
        text.setTranslateY(20);
        text.setFont(Font.font(14));
        text.setFill(Color.WHITE);

        text.effectProperty().bind(
                Bindings.when(hoverProperty()).then(shadow).otherwise(blur));

        getChildren().addAll(bg, text);
    }

    /**
     * method that allows to run it
     * 
     * @param action
     */
    public void setOnAction(Runnable action) {
        setOnMouseClicked(e -> action.run());
    }
}
