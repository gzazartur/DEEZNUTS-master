package console;

public class GameCommand {

	public static enum Type {
		MOVE, SPAWN, MASTERENERGY;
	}
	
	private Type t;
	
	public GameCommand(Type t) {
		this.t = t;
	}
	
	public Type getType() {
		return t;
	}
	
}
