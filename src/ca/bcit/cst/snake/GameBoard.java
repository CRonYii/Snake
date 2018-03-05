package ca.bcit.cst.snake;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameBoard extends StackPane implements Runnable {

    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;
    private static final long LOGIC_FRAME_RATE = 25;
    private static final long INTERVAL = 1000 / LOGIC_FRAME_RATE;
    private static final int SNAKE_DEFAULT_POS_X = WIDTH / 2;
    private static final int SNAKE_DEFAULT_POS_Y = HEIGHT / 2;

    private Canvas canvas;
    private GraphicsContext context;
    private Square[][] grid = new Square[WIDTH][HEIGHT];
    private Snake snake;
    private Apple apple;
    private SnakeBody.Direction direction = null;
    private boolean running = true;
    private boolean pause = false;
    private EventHandler<SnakeEvent> gameOverEvent;

    GameBoard() {
        initialize();
        setKeyboardControl();
    }

    public void run() {
        while (!pause) {
            long time = System.currentTimeMillis();

            execution();

            time = System.currentTimeMillis() - time;
            if (time < INTERVAL) {
                try {
                    Thread.sleep(INTERVAL - time);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        if (!running)
            gameOver();
    }

    private void gameOver() {
        String msg;
        msg = "Game Over";
        context.setFont(new Font(48));
        context.setFill(Color.RED);
        context.fillText(msg, WIDTH * Square.WIDTH / 3, HEIGHT * Square.HEIGHT / 2);
        context.setFill(Color.BLACK);
        context.strokeText(msg, WIDTH * Square.WIDTH / 3, HEIGHT * Square.HEIGHT / 2);

        msg = "Press Enter to start new game\nQ to quit";
        context.setFont(new Font(24));
        context.setFill(Color.RED);
        context.fillText(msg, WIDTH * Square.WIDTH / 4, HEIGHT * Square.HEIGHT * 0.9);
        context.setFill(Color.BLACK);
        context.strokeText(msg, WIDTH * Square.WIDTH / 4, HEIGHT * Square.HEIGHT * 0.9);

        this.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER:
                    gameOverEvent.handle(new SnakeEvent(SnakeEvent.GAMEOVER_EVENT));
                    break;
                case Q:
                    System.exit(0);
                    break;
            }
        });
    }

    private void initialize() {
        canvas = new Canvas(WIDTH * Square.WIDTH, HEIGHT * Square.HEIGHT);
        canvas.setFocusTraversable(true);
        context = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);

        Square.setBodyCollisionEvent(event -> {
            running = false;
        });

        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                Square s = new Square();
                grid[col][row] = s;
            }
        }
        itemBorn();
    }

    private void setKeyboardControl() {
        canvas.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W:
                    if (!pause && (snake.getSize() == 1 || direction != SnakeBody.Direction.DOWN))
                        direction = SnakeBody.Direction.UP;
                    break;
                case S:
                    if (!pause && (snake.getSize() == 1 || direction != SnakeBody.Direction.UP))
                        direction = SnakeBody.Direction.DOWN;
                    break;
                case A:
                    if (!pause && (snake.getSize() == 1 || direction != SnakeBody.Direction.RIGHT))
                        direction = SnakeBody.Direction.LEFT;
                    break;
                case D:
                    if (!pause && (snake.getSize() == 1 || direction != SnakeBody.Direction.LEFT))
                        direction = SnakeBody.Direction.RIGHT;
                    break;
                case P:
                    pause = !pause;
                    if (running && !pause)
                        (new Thread(this)).start();
                    break;
            }
        });
    }

    private void paint() {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                grid[col][row].paint(context, col, row);
            }
        }
    }

    private void setBoard() {
        resetBoard();
        setItem(apple);
        snake.forEach(body -> {
            setItem(body);
        });
    }

    private void resetBoard() {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                grid[col][row].setItem(Item.EMPTY_ITEM);
            }
        }
    }

    private void setItem(Item i) {
        getSquare(i.getCol(), i.getRow()).setItem(i);
    }

    private void itemBorn() {
        snake = new Snake(SNAKE_DEFAULT_POS_X, SNAKE_DEFAULT_POS_Y);
        generateApple();
    }

    private void generateApple() {
        int col, row;
        do {
            col = (int) (Math.random() * WIDTH);
            row = (int) (Math.random() * HEIGHT);
        } while (snake.isOneOfBody(col, row));
        apple = new Apple(col, row);
        apple.setOnCollision(event -> {
            snake.addBody();
            generateApple();
        });
    }

    public Square getSquare(int col, int row) {
        return grid[col][row];
    }

    public void setOnGameOver(EventHandler<SnakeEvent> eventHandler) {
        gameOverEvent = eventHandler;
    }

    private void execution() {
        snake.move(direction);
        setBoard();
        paint();
    }


}
