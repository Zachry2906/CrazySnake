package id.dojo;

import id.dojo.things.Snake;
import id.dojo.things.Fruit;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Run {
    static String ud = null;

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
        if (snake.getDirection() == null){
            snake.turnRight(game.getBoard());
        }else if (snake.getDirection() == "kiri"){
            snake.turnLeft(game.getBoard()); 
            ud = "kiri";        
        } else if (snake.getDirection() == "kanan"){
            snake.turnRight(game.getBoard());
            ud = "kanan";
        }
        ud = "up";
    }

    public static void controlDown(){
        if (snake.getDirection() == null){
            snake.turnLeft(game.getBoard());
        }else if(snake.getDirection() == "kiri"){
            snake.turnLeft(game.getBoard());
        } else if (snake.getDirection() == "kanan"){
            snake.turnRight(game.getBoard());
        }
        ud = "down";
    }

    public static void controlLeft(){
        if (ud == "up"){
            snake.turnLeft(game.getBoard());
            System.out.println("belok kiri");
        } else {
            snake.turnRight(game.getBoard());
        }
    }

    public static void controlRight(){
        if (ud == "up"){
            snake.turnRight(game.getBoard());
        } else {
            snake.turnLeft(game.getBoard());
        }
    }


    public static void main(String[] args) {
        // controls();
        int row = 25;
        int col = 25;
        int posX = 5;
        int posY = 20;


        snake = Snake.getBuilder()
                .setName("Ularku")
                .setAppearance(" o")
                .setPosition(posX, posY)
                .setSize(4)
                .setSpeed(1000)
                .build();
        snake.generateBody();

        Fruit fruit = Fruit.getBuilder()
                .setName("Buah")
                .setAppearance(" o")
                .setPosition(10, 10)
                .build();

        game = Game.getBuilder()
                .createBoard(row, col)
                .createWall()
                .createSnake(snake)
                .createFruit(fruit)
                .generateFruit()
                .generatePopulation()
                .build();


        try {
            game.render();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}