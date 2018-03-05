package ca.bcit.cst.snake;

import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Apple extends Item {

    public static final Paint APPLE_COLOR = Color.RED;

    public Apple(int col, int row) {
        super(col, row, APPLE_COLOR);
    }

}
