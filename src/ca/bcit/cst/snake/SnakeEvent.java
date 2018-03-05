package ca.bcit.cst.snake;

import javafx.event.Event;
import javafx.event.EventType;

public class SnakeEvent extends Event {

    public static final EventType<SnakeEvent> COLLISION_EVENT = new EventType<>("Collision Event");
    public static final EventType<SnakeEvent> GAMEOVER_EVENT= new EventType<>("GameOver Event");

    public SnakeEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
