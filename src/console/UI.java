package console;

import core.BoardView;
import core.MoveCommand;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


public interface UI {

    GameCommand getCommand() throws IOException, ScanException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
    void render(BoardView view);

    void multiThreadCommandProcess() throws ScanException;
}
