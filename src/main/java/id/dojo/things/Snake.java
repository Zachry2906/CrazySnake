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
    private int row;
    private int column;
    private int atasbawah = 1; // 1 = atas, 2 = bawah
    private int kanankiri = 1; // 0 = atas, 1 = kanan, 2 = bawah, 3 = kiri

    public Snake(Builder builder) {
        super(builder.getName(), builder.getAppearance());
        this.head = builder.getPosition();
        this.size = builder.getSize();
        body = new ArrayList<>();
    }

    public void generateBody(){
        int x = head.getX();
        for (int i = 1; i < size; i++) {
            body.add(new Points(x, head.getY() - i));
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
        if (head.getX() == 28) { // Kepala ular berada di baris 24 (batas bawah)
            return null;
        } else if (head.getY() == 28 && head.getX() + 1 != 1) { // Kepala ular berada di batas kanan
            return null;
        } else  if (head.getX() == 1) { // Kepala ular berada di baris 0 (batas atas)
            return null;
        } else if (head.getY() == 1) { // Kepala ular berada di batas kiri
            return null;
        } else {
            return new Points(head.getX(), head.getY() + 1);
        }
    }
    
    @Override
    public void stepForward(Board board) {
        row = board.getRow();
        column = board.getCol();
        board.putObject(head, null);
        for (Points bodPosisi : body) {
            board.putObject(bodPosisi, null);
        }

        int oldX = head.getX();
        int oldY = head.getY();
    
        Points oldHead = new Points(head.getX(), head.getY());
        head.setY(head.getY() + 1);
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

        if (head.getY() == oldY && head.getX() > oldX) {
            atasbawah = 1;
        } else if (head.getY() == oldY && head.getX() < oldX) {
            atasbawah = 2;
        }
    }
    
    @Override
    public Points checkLeft() {
        if (head.getX() == 1 && head.getY() == 1) {
            head.setX(head.getX() + 1);
                return new Points(head.getX(), head.getY() - 1); 
        } else if (head.getY() == 28 && head.getX() == 28){
            if (kanankiri == 1 || kanankiri == 2) { 
                return new Points(head.getX(), head.getY() + 1); 
            } else { 
                return null; 
            }
        } else if (head.getX() == 28 && head.getY() != 1) {
            return null;
        } else {
            return new Points(head.getX(), head.getY() - 1);
        }
    }
    
    @Override
    public void turnLeft(Board board) {
        board.putObject(head, null);
        for (Points bodPosisi : body) {
            board.putObject(bodPosisi, null);
        }
    
        Points oldHead = new Points(head.getX(), head.getY());
        if (head.getY() == 28 && head.getX() == 1) { 
            head.setY(head.getY() - 1);
        } else if (head.getY() == column - 2 && head.getX() == column - 2) {
            head.setY(head.getY() - 1);
        } else if (head.getY() == column - 2 && head.getX() != 1) {
            head.setX(head.getX() - 1);
            kanankiri = 2;
        }  else if (head.getX() == 1 && head.getY() != 28) {
            head.setY(head.getY() - 1);
        } else if (head.getY() == 1) {
            head.setX(head.getX() + 1);
        } else if (head.getX() == 1 && head.getY() == 1){
            head.setX(head.getX() + 1);
        } else {
            Random random = new Random();
            int arah = random.nextInt(2);

            if (kanankiri == 1) {
                if (arah == 1 ) {
                    head.setX(head.getX() - 1);
                } else if (arah == 2 || arah == 0) {
                    head.setY(head.getY() - 1);
                }
            } else {
                if (arah == 1 ) {
                    head.setX(head.getX() + 1);
                } else if (arah == 2 || arah == 0) {
                    head.setY(head.getY() + 1);
                }
            }
        }
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
        kanankiri = 1;
    }
    
    @Override
    public Points checkRight() {
        if (head.getX() == 1 && head.getY() == 1) {
            if (kanankiri == 1) { 
                return new Points(head.getX(), head.getY() - 1); 
            } else { 
                return null;
            }
        } else if (head.getY() == 28 && head.getX() == 28) {
            if (kanankiri == 1) { 
                return new Points(head.getX(), head.getY() + 1); 
            } else { 
                return null; 
            }
        } else if (head.getY() == 28 && head.getX() != 1) {
            return null;
        } else {
            return new Points(head.getX(), head.getY() + 1);
        }
    }
    
    @Override
    public void turnRight(Board board) {
        // Remove crnt positions from the board
        board.putObject(head, null);
        for (Points bodPosisi : body) {
            board.putObject(bodPosisi, null);
        }
    
        Points oldHead = new Points(head.getX(), head.getY());
        if (head.getX() == 28 && head.getY() != 1) { 
            head.setX(head.getX() - 1);
            kanankiri = 1;
        } else 
        if (head.getY() == column - 2 && head.getX() == column - 2) {
            head.setY(head.getX() + 1);
        } else if (head.getY() == column - 2) {
            head.setX(head.getX() + 1);
        } else if (head.getX() == 1  && head.getY() != 28) {
            head.setY(head.getY() - 1);
        } else if(head.getY() == 1 && head.getX() == 1) {
            head.setX(head.getX() + 1);
        } else {
            Random random = new Random();
            int arah = random.nextInt(2);

            if (kanankiri == 2) {
                if (arah == 1 ) {
                    head.setX(head.getX() - 1);
                } else if (arah == 2 || arah == 0) {
                    head.setY(head.getY() + 1);
                }
            } else {
                if (arah == 1 ) {
                    head.setX(head.getX() + 1);
                } else if (arah == 2 || arah == 0) {
                    head.setY(head.getY() - 1);
                }
            }
        }
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
        kanankiri = 2;
    }

    
    

    //EOC
}
