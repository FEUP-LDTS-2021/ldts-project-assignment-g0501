package model;

import com.googlecode.lanterna.graphics.TextGraphics;
import model.element.*;
import model.element.fruit.*;
import view.LanternaGUI;

import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.floor;
import static java.lang.Math.random;

public class Arena implements Drawable {

    int height;
    int width;
    Fruit fruit1;
    Fruit fruit2;
    Door door = null;
    Boolean door_open = false;

    private List<Drawable> elements = new ArrayList<>();
    private List<Snake> snakes = new ArrayList<>();
    private List<Wall> walls = new ArrayList<>();
    private final List<Fruit> POSSIBLE_FRUITS = new ArrayList<>();

    public Arena(Snake snake, LanternaGUI screen) {
        height = screen.getHeight()-1;
        width = screen.getWidth()-1;
        elements.add(snake);
        snakes.add(snake);
        POSSIBLE_FRUITS.add(new Apple(new Position(0,0)));
        POSSIBLE_FRUITS.add(new Orange(new Position(0,0)));
        POSSIBLE_FRUITS.add(new Kiwi(new Position(0,0)));
        POSSIBLE_FRUITS.add(new Banana(new Position(0,0)));
        POSSIBLE_FRUITS.add(new Peach(new Position(0,0)));
        POSSIBLE_FRUITS.add(new Grape(new Position(0,0)));
        POSSIBLE_FRUITS.add(new Cherry(new Position(0,0)));
        addFruits();
    }

    @Override
    public void draw(TextGraphics screen) {
        for(Drawable drawable:elements){
            drawable.draw(screen);
        }
    }

    public Boolean execute(){
        for(Snake snake:snakes){
            snake.move(height,width);
            if(!door_open){
                checkEatFruits(snake);
            }
            if(checkChallengeWin()){
                return true;
            }
            check_snake_collisions(snake);
            check_wall_collisions(snake);
            if(!snake.isAlive())
                return true;
        }
        return false;
    }

    public void checkEatFruits(Snake snake){
        if(snake.getSnakeHead().getPosition().equals(fruit1.getPosition())){
            snake.eatFruit(fruit1,height,width);
            addFruits();
        }
        if(snake.getSnakeHead().getPosition().equals(fruit2.getPosition())){
            snake.eatFruit(fruit2,height,width);
            addFruits();
        }
    }

    public void check_snake_collisions(Snake snake){
        Position SnakeHeadPosition = snake.getSnakeHead().getPosition();
        for(Element b : snake.getSnake().subList(1,snake.getSnake().size())){
            if(b.getPosition().equals(SnakeHeadPosition))
                snake.set_Alive(false);

        }
    }

    public void addFruits(){
        elements.remove(fruit1);
        elements.remove(fruit2);
        double number1 = floor(random() * POSSIBLE_FRUITS.size());
        double number2 = number1;
        while(number2 == number1){
            number2 = floor(random() * POSSIBLE_FRUITS.size());
        }
        Fruit f1 = POSSIBLE_FRUITS.get(((int) number1));
        Fruit f2 = POSSIBLE_FRUITS.get(((int) number2));
        f1.setPosition(new Position((int)floor(random()*(width)),(int)floor(random()*(height))));
        f2.setPosition(new Position((int)floor(random()*(width)),(int)floor(random()*(height))));
        fruit1 = f1;
        fruit2 = f2;
        elements.add(fruit1);
        elements.add(fruit2);
    }

    public void check_wall_collisions(Snake snake){
        Position SnakeHeadPosition = snake.getSnakeHead().getPosition();
        for(Wall w : walls){
            if(w.getPosition().equals(SnakeHeadPosition))
                snake.set_Alive(false);
        }
    }

    public void buildWalls(String File_name){
        buildGeneralWalls();
        try {
            File myObj = new File(File_name);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String [] position = data.split(" ");
                int x = Integer.parseInt(position[0]);
                int y = Integer.parseInt(position[1]);
                walls.add(new Wall(new Position(x,y)));
                elements.add(new Wall(new Position(x,y)));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void buildGeneralWalls(){
        for(int i = 0;i<=width;i++){
            walls.add(new Wall(new Position(i,0)));
            elements.add(new Wall(new Position(i,0)));
            walls.add(new Wall(new Position(i,height)));
            elements.add(new Wall(new Position(i,height)));
        }
        for(int i = 1;i<height;i++){
            walls.add(new Wall(new Position(0,i)));
            elements.add(new Wall(new Position(0,i)));
            walls.add(new Wall(new Position(width,i)));
            elements.add(new Wall(new Position(width,i)));
        }
    }

    public void buildDoor(Position position){
        door = new Door(position);
    }

    public void openDoor(){
        door_open = true;
        elements.add(door);
        elements.remove(fruit1);
        elements.remove(fruit2);
    }

    public boolean checkChallengeWin(){
        return snakes.get(0).getSnakeHead().getPosition().equals(door.getPosition());
    }

}
