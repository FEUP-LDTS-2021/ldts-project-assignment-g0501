package pt.up.ldts.state.level;

import pt.up.ldts.gui.LanternaGUI;
import pt.up.ldts.state.LevelXState;


public class Level2State extends LevelXState {

    public Level2State(LanternaGUI screen, String filename) {
        super(screen,filename);
        setBackgroundColor("#D97EE3");
        setFINAL_SIZE(5);
        setLevel(2);
    }

}