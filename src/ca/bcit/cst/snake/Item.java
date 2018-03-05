package ca.bcit.cst.snake;

import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Item {

    public static Item EMPTY_ITEM = new Item(-1, -1, Color.WHITE);

    protected int col, row;
    private EventHandler<SnakeEvent> collisionEventEventHandler;
    private Paint color;

    Item(int col, int row, Paint p) {
        this.col = col;
        this.row = row;
        this.color = p;
    }

    public void setOnCollision(EventHandler<SnakeEvent> event) {
        collisionEventEventHandler = event;
    }

    public void invokeCollisionEvent() {
        if (collisionEventEventHandler == null)
            return;
        collisionEventEventHandler.handle(new SnakeEvent(SnakeEvent.COLLISION_EVENT));
    }

    public int getCol() {
        return this.col;
    }

    public int getRow() {
        return this.row;
    }

    public Paint getColor() {
        return color;
    }
}
