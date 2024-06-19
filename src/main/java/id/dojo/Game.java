package id.dojo;

import id.dojo.model.Points;
import id.dojo.things.Board;
import id.dojo.things.Cell;
import id.dojo.things.Fruit;
import id.dojo.things.Snake;
import id.dojo.things.Wall;

import java.io.IOException;
import java.util.List;
import java.util.Random;

//untuk mgnontrol jalannya game, mengatur board, dnake dan lain lain
public class Game {
    private final Board board;
    private final Fruit fruit;
    private List<Wall> walls;
    private Snake snake;
    static int speedd;
    static int stage = 1;


    public Game(Builder build) {
        this.board = build.board;
        this.walls = build.walls;
        this.snake = build.snake;
        this.fruit = build.fruit;
        speedd = snake.getSpeed();
    }

    public Board getBoard() {
        return board;
    }

    public void render() throws InterruptedException, IOException {
        while (true){
            board.displayBoard();
            snake.stepForward(fruit, board);
            newStage();
            System.out.println("STAGE : " + stage);
            System.out.println("Speed: " + speedd);
            System.out.println("Size : " + snake.getSize());
        Thread.sleep(speedd);

            new ProcessBuilder("clear").inheritIO().start().waitFor();
        }
    }

    private Random random = new Random();

    // public void autoMovement() {
    //     snake.stepForward(board);
    //     Points forward = snake.checkForward();
    //     Cell forwardCell = board.getBoard().get(forward.getX()).get(forward.getY());
    //     if (forwardCell.getThing() == null) {
    //         snake.stepForward(board);
    //     } else {
    //         Points left = snake.checkLeft();
    //         Cell leftCell = board.getBoard().get(left.getX()).get(left.getY());
    
    //         Points right = snake.checkRight();
    //         Cell rightCell = board.getBoard().get(right.getX()).get(right.getY());
    
    //         if (leftCell.getThing() == null && rightCell.getThing() == null) {
    //             // Both left and right are valid moves
    //             if (random.nextBoolean()) {
    //                 snake.turnLeft(board);
    //             } else {
    //                 snake.turnRight(board);
    //             }
    //         } else if (leftCell.getThing() == null) {
    //             snake.turnLeft(board);
    //         } else if (rightCell.getThing() == null) {
    //             snake.turnRight(board);
    //         } 
    //     }
    // }

    public void newStage(){
        if (snake.getSize() == 5 && speedd == 1000){
            System.out.println("masuk");
            snake.resetBody(board);
            snake.setSpeed(500);
            speedd = snake.getSpeed();
            stage++;
        } else if (snake.getSize() == 10 && speedd == 500){
            snake.resetBody(board);
            snake.setSpeed(250);
            speedd = snake.getSpeed();
            stage++;
        } else if (snake.getSize() == 15 && speedd == 250){
            snake.resetBody(board);
            snake.setSpeed(150);
            speedd = snake.getSpeed();
            stage++;
        } else if (snake.getSize() == 20 && speedd == 150){
            snake.resetBody(board);
            snake.setSpeed(100);
            speedd = snake.getSpeed();
            stage++;
        }
    }


    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        Board board;
        List<Wall> walls;
        Snake snake;
        Fruit   fruit;


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

        public Builder createFruit(Fruit fruit) {
            this.fruit = fruit;
            return this;
        }

        public Builder generateFruit() {
            board.putObject(fruit.getPosition(), fruit);
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
