package state;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import observer.KeyboardObserver;
import game.Arena;
import game.Position;
import elements.Snake;
import game.Game;
import gui.LanternaGUI;

import java.io.IOException;

import static java.lang.Math.floor;

public class OriginalState extends State {
    Arena arena;


    public OriginalState(LanternaGUI screen) {
        super(screen);
        snake = new Snake(new Position(30,15),"#544EE7");
        arena = new Arena(snake,screen);
    }


    @Override
    public void step(Game game) throws  IOException{
        Boolean GameOver;
        screen.getScreen().clear();
        drawBackground("#64DF89");
        drawAllText("#000000");
        arena.draw(screen.getGraphics());
        checkInputPlay(game);
        GameOver = arena.execute();
        screen.getScreen().refresh();
        if(GameOver){
            screen.getScreen().stopScreen();
            screen.getScreen().close();
            changeState(game, new EndOriginalState(new LanternaGUI(screen.getHeight(),screen.getWidth()),snake.getSize()-2, (floor(((System.currentTimeMillis()-startTime-pauseTime)/1000f)*10)/10)));
        }
    }

    public void drawAllText(String color){
        drawText("Q to exit",color,new TerminalPosition(screen.getWidth()-9, screen.getHeight()));
        drawText("Score: " + (snake.getSize()-2),color,new TerminalPosition(1,screen.getHeight()));
        drawText("|  Timer: " + (floor(((System.currentTimeMillis()-startTime-pauseTime)/1000f)*10)/10) + "s",color,new TerminalPosition(12,screen.getHeight()));
        for(int i = 0; i<screen.getWidth();i++){
            screen.getGraphics().putString(new TerminalPosition(i, screen.getHeight()-1),"_");
        }
    }


    public void checkAction(Game game, KeyStroke key) throws IOException{
        if(key.getKeyType()==KeyType.Character) {
            switch (key.getCharacter().toString().toLowerCase()) {
                case ("q"): returnMenu(game);break;
                case ("p"): pause();break;
            }
        }
    }

}

