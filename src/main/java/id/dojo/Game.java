package id.dojo;

import id.dojo.model.Points;
import id.dojo.things.Board;
import id.dojo.things.Cell;
import id.dojo.things.Snake;
import id.dojo.things.Wall;

import java.io.IOException;
import java.util.List;
import java.util.Random;

//untuk mgnontrol jalannya game, mengatur board, dnake dan lain lain
public class Game {
    private final Board board;
    private List<Wall> walls;
    private Snake snake;
    private int speed;

    public Game(Builder build) {
        this.board = build.board;
        this.walls = build.walls;
        this.speed = build.speed;
        this.snake = build.snake;
    }

    public Board getBoard() {
        return board;
    }

    public void render() throws InterruptedException, IOException {
        while (true){
            board.displayBoard();
//            autoMovement();
            snake.stepForward(board);
        Thread.sleep(300);

            new ProcessBuilder("clear").inheritIO().start().waitFor();
        }
    }

    private Random random = new Random();

    public void autoMovement() {
        snake.stepForward(board);
        Points forward = snake.checkForward();
        Cell forwardCell = board.getBoard().get(forward.getX()).get(forward.getY());
        if (forwardCell.getThing() == null) {
            snake.stepForward(board);
        } else {
            Points left = snake.checkLeft();
            Cell leftCell = board.getBoard().get(left.getX()).get(left.getY());
    
            Points right = snake.checkRight();
            Cell rightCell = board.getBoard().get(right.getX()).get(right.getY());
    
            if (leftCell.getThing() == null && rightCell.getThing() == null) {
                // Both left and right are valid moves
                if (random.nextBoolean()) {
                    snake.turnLeft(board);
                } else {
                    snake.turnRight(board);
                }
            } else if (leftCell.getThing() == null) {
                snake.turnLeft(board);
            } else if (rightCell.getThing() == null) {
                snake.turnRight(board);
            } 
        }
    }


    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        Board board;
        List<Wall> walls;
        Snake snake;
        int speed;

        public Builder createBoard(int rows, int columns) {
            board = new Board("Board", "", rows, columns);

            return this;
        }

        public Builder createWall() {
            // Method untuk membuat area game
            int row = board.getRow();
            int col = board.getCol();

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (i == 0 || i == row - 1 || j == 0 || j == col - 1) {
                        board.putObject(new Points(i, j), new Wall(i, j));
                    }
                }
                System.out.println();
            }
            return this;
        }

        public Builder createSnake(Snake snake) {
            this.snake = snake;
            return this;
        }

        //dipakai untuk membuat objek ular dan buah
        public Builder generatePopulation(){
            board.putObject(snake.getHead(), snake);
            return this;
        }


        public Game build(){
            Game game = new Game(this);

            return game;
        }



        //END OG INNERCLASS
    }
    //EOC
}
