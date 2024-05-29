package id.dojo;

import id.dojo.things.Snake;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Run {
    public static void main(String[] args) {
        int row = 30;
        int col = 30;
        int posX = 5;
        int posY = 3;
        Snake snake = Snake.getBuilder()
                .setName("Ularku")
                .setAppearance("<>")
                .setPosition(posX, posY)
                .setSize(4)
                .build();
        snake.generateBody();

        Game game = Game.getBuilder()
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