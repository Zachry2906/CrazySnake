package id.dojo;

import id.dojo.things.Board;
import id.dojo.things.Cell;
import id.dojo.things.Snake;
import id.dojo.things.Wall;

import java.util.List;

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

    public void render() {
        board.displayBoard();
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

        public Builder createWall(String name, String app, int row, int col) {
            // Method untuk membuat area game
            Cell cell = new Cell(name, app, row, col);

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (i == 0 || i == row - 1 || j == 0 || j == col - 1) {
                        System.out.print(app + " ");
                    } else {
                        System.out.print("  ");
                    }
                }
                System.out.println();
            }
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
