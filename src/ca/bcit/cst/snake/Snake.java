package ca.bcit.cst.snake;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Iterator;

public class Snake implements Iterable<SnakeBody> {

    private SnakeBody head;
    private SnakeBody tail;
    private int size;

    public Snake(int x, int y) {
        SnakeBody b = new SnakeBody(x, y);
        head = b;
        tail = b;
        size = 1;
    }

    public void move(SnakeBody.Direction d) {
        head.move(d);
    }

    public boolean isOneOfBody(int col, int row) {
        Iterator<SnakeBody> iterator = iterator();
        while (iterator.hasNext()) {
            SnakeBody b = iterator.next();
            if (b.col == col && b.row == row)
                return true;
        }
        return false;
    }

    public void addBody() {
        SnakeBody newBody = null;
        //
        if (tail.getDirection() == null) {
            addToTail(new SnakeBody(tail.col, tail.row));
            return;
        }
        //
        switch (tail.getDirection()) {
            case UP:
                newBody = new SnakeBody(tail.getCol(), tail.getRow() + 1);
                break;
            case DOWN:
                newBody = new SnakeBody(tail.getCol(), tail.getRow() - 1);
                break;
            case LEFT:
                newBody = new SnakeBody(tail.getCol() + 1, tail.getRow());
                break;
            case RIGHT:
                newBody = new SnakeBody(tail.getCol() - 1, tail.getRow());
                break;
        }
        addToTail(newBody);
    }

    private void addToTail(SnakeBody b) {
        if (size == 1)
            head.setNext(b);
        tail.setNext(b);
        b.setLast(tail);
        tail = b;
        size++;
    }

    private void addToHead(SnakeBody b) {
        if (size == 1)
            tail.setLast(b);
        head.setLast(b);
        b.setNext(head);
        head = b;
        size++;
    }

    public int getSize() {
        return size;
    }

    public SnakeBody getHead() {
        return head;
    }

    public SnakeBody getTail() {
        return tail;
    }

    @Override
    public Iterator<SnakeBody> iterator() {
        return new Iterator<SnakeBody>() {

            boolean first = true;
            SnakeBody current = head;

            @Override
            public boolean hasNext() {
                return first || current.getNext() != null;
            }

            @Override
            public SnakeBody next() {
                if (first) {
                    first = false;
                    return head;
                }
                current = current.getNext();
                return current;
            }
        };
    }
}

class SnakeBody extends Item {

    public enum Direction {UP, DOWN, LEFT, RIGHT}

    ;
    public static final Paint BODY_COLOR = Color.BLUE;

    private Direction direction = null;
    private SnakeBody last, next;


    public SnakeBody(int x, int y) {
        super(x, y, BODY_COLOR);
    }

    void move(Direction d) {
        if (d == null)
            return;
        Direction moveDirection = direction;
        if (direction == null) {
            moveDirection = d;
        }
        switch (moveDirection) {
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
        }
        if (next != null)
            next.move(direction);
        if (d != null)
            this.direction = d;
    }

    private void moveUp() {
        row -= 1;
        if (row < 0)
            row = GameBoard.HEIGHT - 1;
    }

    private void moveDown() {
        row += 1;
        if (row >= GameBoard.HEIGHT)
            row = 0;
    }

    private void moveLeft() {
        col -= 1;
        if (col < 0)
            col = GameBoard.WIDTH - 1;
    }

    private void moveRight() {
        col += 1;
        if (col >= GameBoard.WIDTH)
            col = 0;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setNext(SnakeBody next) {
        this.next = next;
    }

    public void setLast(SnakeBody last) {
        this.last = last;
    }

    public SnakeBody getNext() {
        return next;
    }

    public SnakeBody getLast() {
        return last;
    }
}