package id.dojo.things;

import id.dojo.model.Points;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Snake extends Thing implements AnimalBehavior{
    private Points head;
    private int size;
    private List<Points> body;
    static public int row;
    static public int column;
    static String direction = "kiri";

    public Snake(Builder builder) {
        super(builder.getName(), builder.getAppearance());
        this.head = builder.getPosition();
        this.size = builder.getSize();
        body = new ArrayList<>();
    }

    public void generateBody(){
        int x = head.getX();
        int y = head.getY();
        for (int i = 1; i < size; i++) {
            body.add(new Points(x, ++y));
        }
    }

    public static Builder getBuilder()
    {
        return new Builder();
    }

    public Points getHead() {
        return head;
    }

    public void setHead(Points head) {
        this.head = head;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    public static class Builder{
        private int size;
        private String name;
        private String appearance;
        private int posX, posY;

        public int getSize(){
            return size;
        }

        public  Builder setSize(int size){
            this.size = size;
            return this;
        }

        public String getName(){
            return name;
        }

        public  Builder setName(String name){
            this.name = name;
            return this;
        }

        public  Builder setAppearance(String appearance){
            this.appearance = appearance;
            return this;
        }

        public String getAppearance(){
            return appearance;
        }

        public Points getPosition(){
            return new Points(posX, posY);
        }

        public Builder setPosition(int posX, int posY){
            this.posX = posX;
            this.posY = posY;
            return this;
        }

        public Snake build(){
            return new Snake(this);
        }

        //END OF INNER CLASSS
    }

    @Override
    public Points checkForward() {

        int xPos = 0;
        int yPos = 0;

        // ke selatan
        if (head.getX() == 28 && head.getY()== 1){
            if (direction.equals("kiri")){
                xPos = head.getX();
                yPos = head.getY() + 1;
            } else {
                xPos = head.getX() - 1;
                yPos = head.getY();
            }
        } else if (head.getX() - 1 == body.getFirst().getX()){
            xPos = head.getX() + 1;
            yPos = head.getY();
            // ke timur
        } else if (head.getY() - 1 == body.getFirst().getY()){
            xPos = head.getX();
            yPos = head.getY() + 1;
            //ke barat
        } else if (head.getY() + 1 == body.getFirst().getY()){
            xPos = head.getX();
            yPos = head.getY() - 1;
            //keutara
        } else if (head.getX() == 1 && head.getY() == 28) {
            if (direction.equals("kiri")){
                xPos = head.getX();
                yPos = head.getY() - 1;
            } else {
                xPos = head.getX() + 1;
                yPos = head.getY();
            }
        }else if (head.getX() + 1 == body.getFirst().getX()){
            xPos = head.getX() - 1;
            yPos = head.getY() ;
        } 
        

        return new Points(xPos, yPos);
    }
    
    @Override
    public void stepForward(Board board) {
        column = board.getCol();
        row = board.getRow();
        Points position = checkForward();
        board.putObject(head, null);
        for (Points bodPosisi : body) {
            board.putObject(bodPosisi, null);
        }

        Points oldHead = new Points(head.getX(), head.getY());
        head.setX(position.getX());
        head.setY(position.getY());
        board.putObject(head, this);

        Points prev = oldHead;
        for (int i = 0; i < body.size(); i++) {
            Points crnt = body.get(i);
            Points temp = new Points(crnt.getX(), crnt.getY());
            crnt.setX(prev.getX());
            crnt.setY(prev.getY());
            prev = temp;
            board.putObject(crnt, this);
        }

    }
    
    @Override
    public Points checkLeft() {
        int xPos = 0;
        int yPos = 0;

        // selatan
        if (head.getX() - 1 == body.getFirst().getX()) {
            System.out.println("dari utara");
            xPos = head.getX();
            yPos = head.getY() + 1; // Mengubah head.getY() + 1 menjadi head.getY() - 1
            //selatan
        } else if (head.getY() - 1 == body.getFirst().getY()) {
            System.out.println("dari timur");
            xPos = head.getX() - 1;
            yPos = head.getY();
            //barat
        } else if (head.getY() + 1 == body.getFirst().getY()) {
            System.out.println("dari barat");
            xPos = head.getX() + 1; // Mengubah head.getX() menjadi head.getX() + 1
            yPos = head.getY();
            //timur
        } else if (head.getX() + 1 == body.getFirst().getX()) {
            System.out.println("dari selatan");
            xPos = head.getX();
            yPos = head.getY() + 1; // Mengubah head.getY() - 1 menjadi head.getY() + 1
            //utara
        } 
        if (xPos >= 0 && xPos < column && yPos >= 0 && yPos < row) {
            return new Points(xPos, yPos);
        } else {
            System.out.println("out of bound");
            return null;
        }
    }

    @Override
    public void turnLeft(Board board) {
        Points position = checkLeft();
        board.putObject(head, null);
        for (Points bodPosisi : body) {
            board.putObject(bodPosisi, null);
        }

        Points oldHead = new Points(head.getX(), head.getY());
        head.setX(position.getX());
        head.setY(position.getY());
        board.putObject(head, this);

        Points prev = oldHead;
        for (int i = 0; i < body.size(); i++) {
            Points crnt = body.get(i);
            Points temp = new Points(crnt.getX(), crnt.getY());
            crnt.setX(prev.getX());
            crnt.setY(prev.getY());
            prev = temp;
            board.putObject(crnt, this);
        }

        direction = "kiri";
    }

    @Override
    public Points checkRight() {
        
        int xPos = 0;
        int yPos = 0;
        
        // ke selatan
        if (head.getX() - 1 == body.getFirst().getX()){
            xPos = head.getX();
            yPos = head.getY() - 1;
        //timur
        } else if (head.getY() - 1 == body.getFirst().getY()){
            xPos = head.getX() + 1; // Mengubah head.getX() menjadi head.getX() + 1
            yPos = head.getY();
        //barat
        } else if (head.getY() + 1 == body.getFirst().getY()){
            xPos = head.getX() - 1; // Mengubah head.getX() menjadi head.getX() - 1
            yPos = head.getY();
        //utara
        } else if (head.getX() + 1 == body.getFirst().getX()){
            xPos = head.getX();
            yPos = head.getY() + 1;
        } else {
            xPos = head.getX();
            yPos = head.getY();
        }
        
        if (xPos >= 0 && xPos < column && yPos >= 0 && yPos < row) {
            return new Points(xPos, yPos);
        } else {
            System.out.println("out of bound");
            return null;
        }
    }

    @Override
    public void turnRight(Board board) {
        Points position = checkRight();
        board.putObject(head, null);
        for (Points bodPosisi : body) {
            board.putObject(bodPosisi, null);
        }

        Points oldHead = new Points(head.getX(), head.getY());
        head.setX(position.getX());
        head.setY(position.getY());
        board.putObject(head, this);

        Points prev = oldHead;
        for (int i = 0; i < body.size(); i++) {
            Points crnt = body.get(i);
            Points temp = new Points(crnt.getX(), crnt.getY());
            crnt.setX(prev.getX());
            crnt.setY(prev.getY());
            prev = temp;
            board.putObject(crnt, this);
        }
        direction = "kanan";
    }

    
    

    //EOC
}
