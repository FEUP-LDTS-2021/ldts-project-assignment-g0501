package state;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import elements.Button;
import observer.KeyboardObserver;
import game.Position;
import game.Game;
import gui.LanternaGUI;
import state.level.Level1State;
import state.level.Level2State;
import state.level.Level3State;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScoreboardState extends State{

    KeyboardObserver observer;
    List<Button> buttonList = new ArrayList<>();
    Button actualbutton;

    public ScoreboardState(LanternaGUI screen) {
        super(screen);
        observer = new KeyboardObserver(screen);
        buttonList.add(new Button(new Position((screen.getWidth()/2)-7, 4),"  ORIGINAL   "));
        buttonList.add(new Button(new Position((screen.getWidth()/2)-7, 9)," MULTIPLAYER "));
        buttonList.add(new Button(new Position((screen.getWidth()/2)-7, 14),"    LEVEL1   "));
        buttonList.add(new Button(new Position((screen.getWidth()/2)-7, 19),"    LEVEL2   "));
        buttonList.add(new Button(new Position((screen.getWidth()/2)-7, 24),"    LEVEL3   "));
        actualbutton=buttonList.get(0);
    }

    public void step(Game game) throws IOException{
        screen.getScreen().clear();
        drawBackground("#31B2D8");
        drawAllText("#000097");
        drawButtons();
        screen.getScreen().refresh();
        checkInput(game);
    }


    public void drawText(String text, String color, TerminalPosition position){
        screen.getGraphics().setForegroundColor(TextColor.Factory.fromString(color));
        screen.getGraphics().putString(position, text);
    }

    public void drawBackground(String color){
        screen.getGraphics().setBackgroundColor(TextColor.Factory.fromString(color));
        for (int i = 0;i<screen.getWidth();i++){
            for (int j = 0;j<=screen.getHeight();j++)
                screen.getGraphics().putString(new TerminalPosition(i,j), " ");
        }
    }

    public void drawAllText(String color){
        drawText("SCOREBOARD",color,new TerminalPosition((screen.getWidth()/2)-5, 1));
        drawText("Press Q to exit",color,new TerminalPosition(screen.getWidth()-15,screen.getHeight()));
    }

    public void drawButtons(){
        for(Button b : buttonList){
            if (b.equals(actualbutton)){
                b.setHighlight(true);
            }
            b.draw(screen.getGraphics());
            b.setHighlight(false);
        }
    }

    public void checkInput(Game game) throws IOException {
        if(observer.readinput()){
            KeyStroke key = observer.getKeys().get(0);
            if(key.getKeyType()== KeyType.Enter){
                screen.getScreen().stopScreen();
                screen.getScreen().close();
                enterState(game);
            }
            if(key.getKeyType()== KeyType.Character && key.getCharacter().toString().equalsIgnoreCase("q")){
                screen.getScreen().stopScreen();
                screen.getScreen().close();
                changeState(game,new MenuState(new LanternaGUI(screen.getHeight(), screen.getWidth())));
            }
            if(key.getKeyType()== KeyType.ArrowDown){
                int index = (buttonList.indexOf(actualbutton)+1);
                if(index==buttonList.size()){
                    index = 0;
                }
                actualbutton = buttonList.get(index);
            }
            if(key.getKeyType()== KeyType.ArrowUp){
                int index = (buttonList.indexOf(actualbutton)-1) ;
                if(index==-1){
                    index = buttonList.size()-1;
                }
                actualbutton = buttonList.get(index);
            }
        }
    }

    public void enterState(Game game){
        switch(actualbutton.getText()){
            case "  ORIGINAL   ": changeState(game,new ShowScoreboardState(new LanternaGUI(screen.getHeight(), screen.getWidth()),"src/main/resources/Scoreboards/OriginalScoreBoard.txt"));break;
            case " MULTIPLAYER ": changeState(game,new ShowScoreboardState(new LanternaGUI(screen.getHeight(), screen.getWidth()),"src/main/resources/Scoreboards/MultiplayerScoreBoard.txt"));break;
            case "    LEVEL1   ": changeState(game,new ShowScoreboardState(new LanternaGUI(screen.getHeight(), screen.getWidth()),"src/main/resources/Scoreboards/Level1ScoreBoard.txt"));break;
            case "    LEVEL2   ": changeState(game,new ShowScoreboardState(new LanternaGUI(screen.getHeight(), screen.getWidth()),"src/main/resources/Scoreboards/Level2ScoreBoard.txt"));break;
            case "    LEVEL3   ": changeState(game,new ShowScoreboardState(new LanternaGUI(screen.getHeight(), screen.getWidth()),"src/main/resources/Scoreboards/Level3ScoreBoard.txt"));break;
        }
    }
}