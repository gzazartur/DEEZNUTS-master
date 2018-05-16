package console;

import core.BoardView;
import core.MoveCommand;
import core.State;
import core.XY;
import entities.MasterSquirrel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ConsoleUI implements UI, GameCommandType {

    private PrintStream outputStream;
    private BufferedReader inputStream;
    private State state;
    private GameCommand command;
    private boolean threaded;

    public ConsoleUI(State state, boolean threaded) {
        this.state = state;
        this.outputStream = System.out;
        this.inputStream = new BufferedReader(new InputStreamReader(System.in));
        this.threaded = threaded;
    }

    @Override
    public GameCommand getCommand() throws ScanException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // alles beim Alten wenn nicht multithreaded
        if(!threaded)
            return getCommandSingleThread();

        if(command == null)
            return null;
        else {
            GameCommand temp = command;
            command = null;
            return temp;
        }
    }


    @Override
    public void multiThreadCommandProcess() throws ScanException {
        while(true)
			try {
				this.command = getCommandSingleThread();
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
    }


    private GameCommand getCommandSingleThread() throws ScanException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method[] methods = GameCommandType.class.getDeclaredMethods();
		int count = 0;
    	
    	CommandTypeInfo[] commandTypeInfos = new CommandTypeInfo[9];
        
        for(Method method : methods) {
        	AsCommand ac = method.getAnnotation(AsCommand.class);
        	commandTypeInfos[count++] = new CommandTypeInfo(ac.getName(), ac.getHelpText(), this, method);
        }

    	
    	CommandScanner commandScanner = new CommandScanner(commandTypeInfos, inputStream, outputStream);
        Command command;
        command = commandScanner.next();
        
        CommandTypeInfo cmdType = command.getCommandType();
        
        Object result = cmdType.getMethod().invoke(cmdType.getTarget(), command.getParameters());
        
        return (GameCommand) result;
    }


    @Override
    public void render(BoardView view) {
        for (int y = 0; y < view.getSize().getY(); y++) {
            for (int x = 0; x < view.getSize().getX(); x++) {

                char c;

                switch (view.getEntityType(x, y)) {
                    case BAD_BEAST:
                        c = 'B';
                        break;
                    case GOOD_BEAST:
                        c = 'b';
                        break;
                    case BAD_PLANT:
                        c = 'P';
                        break;
                    case GOOD_PLANT:
                        c = 'p';
                        break;
                    case MASTER_SQUIRREL:
                        c = 'M';
                        break;
                    case MINI_SQUIRREL:
                        c = 'm';
                        break;
                    case WALL:
                        c = '#';
                        break;
                    case NOTHING:
                    default:
                        c = ' ';
                        break;
                }

                System.out.print(c);
            }

            System.out.println();
        }

    }


    @Override
    public void help() {
		Method[] methods = GameCommandType.class.getDeclaredMethods();
		for (Method m : methods) {
			AsCommand ac = m.getAnnotation(AsCommand.class);
			if (ac != null)
				System.out.println(ac.getName() + '\t' + ac.getHelpText());
		}	
    }

    @Override
    public void exit() {
        System.out.println("Bye bye");
        System.exit(0);
    }

    @Override
    public void all() {
        System.out.println(state.getBoard().getEntitySet());
    }

    @Override
    public void masterEnergy() {
        System.out.println(state.getBoard().getMasterSquirrel().getEnergy());
    }

    @Override
    public void spawnMini(Integer energy, Integer x, Integer y) throws NotEnoughEnergyException { 
        MasterSquirrel daddy = state.getBoard().getMasterSquirrel();
        XY direction = new XY(x,y);
        
        if (state.getBoard().getMasterSquirrel().getEnergy() >= energy) {
            state.getBoard().insertMiniSquirrel(energy, direction, daddy);
        } else {
            throw new NotEnoughEnergyException("Das MasterSquirrel hat nur " + (state.getBoard().getMasterSquirrel().getEnergy()) + " Energie");
        }
    }

	@Override
	public MoveCommand left() {
		return new MoveCommand(new XY(-1, 0));		
	}

	@Override
	public MoveCommand up() {
		return new MoveCommand(new XY(0, -1));
	}

	@Override
	public MoveCommand down() {
		return new MoveCommand(new XY(0, 1));
	}

	@Override
	public MoveCommand right() {
		return new MoveCommand(new XY(1, 0));
	}
}
