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
    private int currentDirection; // 0 = atas, 1 = kanan, 2 = bawah, 3 = kiri
    private int previousDirection;

    public Game(Builder build) {
        this.board = build.board;
        this.walls = build.walls;
        this.speed = build.speed;
        this.snake = build.snake;
    }

    public void render() throws InterruptedException, IOException {
        while (true){
            board.displayBoard();
        // Memeriksa apakah ular bisa bergerak maju
        switch (getNewDirection()) {
            case 0:
            case 4:
            case 5:
            case 6:
             // Atas
                if (snake.checkForward() != null) {
                    System.out.println("Lurus");
                    snake.stepForward(board);
                } else {
                    // Dapatkan arah acak baru yang berbeda dari arah sebelumnya
                    getNewDirection();
                }
                break;
            case 1:
            case 2:
            case 8:
             // Kanan
                if (snake.checkRight() != null) {
                    System.out.println("Kanan");
                    snake.turnRight(board);
                } else {
                    getNewDirection();
                }
                break;
            case 3:
            case 9:
            case 7:
            case 10:
             // Kiri
                if (snake.checkLeft() != null) {
                    System.out.println("Kiri");
                    snake.turnLeft(board);
                } else {
                    getNewDirection();
                }
                break;
        }

        // Memberikan jeda waktu untuk animasi gerakan
        Thread.sleep(50);

            new ProcessBuilder("clear").inheritIO().start().waitFor();
        }
    }

    private int getNewDirection() {
    Random random = new Random();
    int newDirection = random.nextInt(10);
    System.out.println("newDirection: " + newDirection);
    return newDirection;
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
