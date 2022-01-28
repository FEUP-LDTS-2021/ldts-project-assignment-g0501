package pt.up.ldts.observer;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pt.up.ldts.gui.LanternaGUI;

public class KeyboardObserverTest {

    KeyboardObserver observer;
    @BeforeEach
    public void setup(){
        observer = new KeyboardObserver(new LanternaGUI(50,50));
    }
    @Test
    public void isWasd1(){
        KeyStroke key = Mockito.mock(KeyStroke.class);
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn(Character.valueOf('a'));
        Assertions.assertTrue(observer.isWasd(key));
    }
    @Test
    public void isWasd6(){
        KeyStroke key = Mockito.mock(KeyStroke.class);
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn(Character.valueOf('o'));
        Assertions.assertFalse(observer.isWasd(key));
    }
    @Test
    public void isArrow1(){
        KeyStroke key = Mockito.mock(KeyStroke.class);
        Mockito.when(key.getKeyType()).thenReturn(KeyType.ArrowDown);
        Assertions.assertTrue(observer.isArrow(key));
    }
    @Test
    public void isArrow5(){
        KeyStroke key = Mockito.mock(KeyStroke.class);
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Assertions.assertFalse(observer.isArrow(key));
    }
    @Test
    public void isPauseQuit2(){
        KeyStroke key = Mockito.mock(KeyStroke.class);
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn(Character.valueOf('q'));
        Assertions.assertTrue(observer.isPauseorQuit(key));
    }

    @Test
    public void isPauseQuit1(){
        KeyStroke key = Mockito.mock(KeyStroke.class);
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Character);
        Mockito.when(key.getCharacter()).thenReturn(Character.valueOf('p'));
        Assertions.assertTrue(observer.isPauseorQuit(key));
    }


}
