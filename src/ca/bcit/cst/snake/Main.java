package ca.bcit.cst.snake;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Stage mainStage;

    @Override
    public void start(Stage primaryStage) {
        startGame(primaryStage);
    }

    private void startGame(Stage stage) {
        if (mainStage != null)
            mainStage.close();

        mainStage = stage;

        GameBoard board = new GameBoard();
        board.setOnGameOver(event -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    startGame(new Stage());
                }
            });
        });
        Scene scene = new Scene(board);

        stage.setScene(scene);
        stage.setTitle("Snake");
        stage.show();

        Thread gameThread = new Thread(board, "GameBoard");
        gameThread.setDaemon(true);
        gameThread.start();
    }
}
