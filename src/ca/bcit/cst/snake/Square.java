package ca.bcit.cst.snake;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;


public class Square {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;
    public static final int ITEM_WIDTH = (int) (0.9 * WIDTH);
    public static final int ITEM_HEIGHT = (int) (0.9 * HEIGHT);

    private Item item = Item.EMPTY_ITEM;

    private static EventHandler<SnakeEvent> bodyCollisionEvent;

    public static void setBodyCollisionEvent(EventHandler<SnakeEvent> bodyCollisionEvent) {
        Square.bodyCollisionEvent = bodyCollisionEvent;
    }

    public void paint(GraphicsContext context, double col, double row) {
        context.setFill(item.getColor());
        context.fillRect(WIDTH * col, HEIGHT * row, ITEM_WIDTH, ITEM_HEIGHT);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        if (item != Item.EMPTY_ITEM && this.item != item) {
            this.item.invokeCollisionEvent();
        }
        if (item instanceof SnakeBody && this.item instanceof SnakeBody) {
            if (((SnakeBody) this.item).getNext() != item) {
                bodyCollisionEvent.handle(new SnakeEvent(SnakeEvent.COLLISION_EVENT));
            }
        }
        this.item = item;
    }

}
