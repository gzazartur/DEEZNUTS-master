package console;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;


class CommandScanner {

    private CommandTypeInfo[] commandTypeInfo;
    private BufferedReader inputReader;
    private PrintStream outputStream;


    CommandScanner(CommandTypeInfo[] commandTypeInfo, BufferedReader inputReader, PrintStream outputStream) {
        this.commandTypeInfo = commandTypeInfo;
        this.inputReader = inputReader;
        this.outputStream = outputStream;
    }

    Command next() throws ScanException {

        System.out.println("Enter a command please: ");


        // Get user input
		String input;
		try {
			input = inputReader.readLine();
		} catch (IOException e) {
			throw new ScanException(null);
		}

		int split = input.indexOf(' ');

		String name;
		if (split != -1) {
			name = input.substring(0, split);
			input = input.substring(split + 1);
		} else {
			name = input;
			input = "";
		}

        // Search for matching command type
        CommandTypeInfo command = null;
        
        for (CommandTypeInfo cti : commandTypeInfo) {
            if (cti.getName().equals(name)) {
                command = cti;
            	break;
            }
        }


        // Throw exception if command is not available
        if (command == null)
            throw new ScanException("Error: Command <" + name + "> doesn't exist!");


		Object[] params = new Object[command.getParamTypes().length];
		
		for (int i = 0; i < command.getParamTypes().length; i++) {

			split = input.indexOf(' ');
			String param;
			if (split != -1) {
				param = input.substring(0, split);
				input = input.substring(split + 1);
			} else {
				param = input;
				input = "";
			}
			
			Class<?> paramType = command.getParamTypes()[i];
			try {
				params[i] = paramType.getConstructor(String.class).newInstance(param);
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				throw new ScanException(null);
			}

		}

        return new Command(command, params);
    }

}
