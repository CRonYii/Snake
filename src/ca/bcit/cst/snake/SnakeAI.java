package ca.bcit.cst.snake;

public class SnakeAI implements Runnable {

    private GameBoard board;

    public SnakeAI(GameBoard board) {
        this.board = board;
    }

    public void run() {
        // TODO
    }

    private void getGrid() {

    }

    class Node {

        int x, y;
        boolean walkable;
        Node parent;

        public Node(int x, int y, boolean walkable) {
            this.x = x;
            this.y = y;
            this.walkable = walkable;
        }

        public boolean isWalkable() {
            return walkable;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }
    }
}
