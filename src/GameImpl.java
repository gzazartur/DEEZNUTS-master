import console.ConsoleUI;
import console.GameCommand;
import console.ScanException;
import core.MoveCommand;
import core.State;
import core.XY;
import entities.HandOperatedMasterSquirrel;
import entities.MasterSquirrel;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


public class GameImpl extends Game {

    private HandOperatedMasterSquirrel masterSquirrel;


    GameImpl(boolean threaded) {
        super(new State(), threaded);
        ui = new ConsoleUI(state, threaded);

        masterSquirrel = new HandOperatedMasterSquirrel(XY.generateRandomLocation(state.getBoard().getConfig().getBoardSize(), state.getBoard().getEntities()));

        state.getBoard().insertMasterSquirrel(masterSquirrel);
    }


    @Override
    protected void processInput() throws IOException, ScanException {

        GameCommand gameCommand = null;

        try {
			gameCommand = ui.getCommand();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
        masterSquirrel.setGameCommand(gameCommand);

    }

    @Override
    protected void render() {
        ui.render(state.flattenedBoard());
    }

}
