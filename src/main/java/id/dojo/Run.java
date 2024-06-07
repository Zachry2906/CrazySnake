package id.dojo;

import id.dojo.things.Snake;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Run {

    static Snake snake;
    static Game game;

    static {
        System.loadLibrary("native");

        Thread runControl = new Thread(new Runnable() {
            @Override
            public void run() {
                controls();
            }
        });

        runControl.start();
    }

    public static native void controls();

    public static void controlUp(){
        snake.turnRight(game.getBoard());
    }

    public static void controlDown(){

    }

    public static void controlLeft(){
        snake.turnLeft(game.getBoard());

    }

    public static void controlRight(){
        snake.turnRight(game.getBoard());

    }


    public static void main(String[] args) {
        // controls();
        int row = 30;
        int col = 30;
        int posX = 5;
        int posY = 20;


        snake = Snake.getBuilder()
                .setName("Ularku")
                .setAppearance(" o")
                .setPosition(posX, posY)
                .setSize(4)
                .build();
        snake.generateBody();

        game = Game.getBuilder()
                .createBoard(row, col)
                .createWall()
                .createSnake(snake)
                .generatePopulation()
                .build();


        try {
            game.render();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}