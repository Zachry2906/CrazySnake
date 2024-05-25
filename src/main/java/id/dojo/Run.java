package id.dojo;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Run {
    public static void main(String[] args) {
        int row = 30;
        int col = 20;
        Game game = Game.getBuilder()
                .createBoard(30, 30)
                .createWall("wall", "*", 30, 30)
                .build();


        game.render();
    }
}