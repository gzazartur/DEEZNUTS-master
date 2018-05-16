package console;

import core.MoveCommand;

public interface GameCommandType {
	@AsCommand(getHelpText = "print this help", getName = "help")
	void help();
	
	@AsCommand(getHelpText = "quit this program", getName = "exit")
	void exit();
	
	@AsCommand(getHelpText = "print all entities", getName = "all")
	void all();
	
	@AsCommand(getHelpText = "move left", getName = "left")
	MoveCommand left();
	
	@AsCommand(getHelpText = "move up", getName = "up")
	MoveCommand up();
	
	@AsCommand(getHelpText = "move down", getName = "down")
	MoveCommand down();
	
	@AsCommand(getHelpText = "move right", getName = "right")
	MoveCommand right();
	
	@AsCommand(getHelpText = "print master energy", getName = "MasterEnergy")
	void masterEnergy();

	@AsCommand(getHelpText = "<energy> <x-direction> <y-direction> spawn mini squirrel", getName = "Spawnmini")
	void spawnMini(Integer energy, Integer x, Integer y) throws NotEnoughEnergyException;

}
