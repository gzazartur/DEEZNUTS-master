package core;

import console.GameCommand;

public class MoveCommand extends GameCommand {

    private XY moveDirection;

    public MoveCommand(XY moveDirection) {
    	super(Type.MOVE);
        this.moveDirection = moveDirection;
    }

    public XY getMoveDirection() {
        return moveDirection;
    }


}
