package entities;

import console.GameCommand;
import core.EntityContext;
import core.MoveCommand;
import core.XY;


public class HandOperatedMasterSquirrel extends MasterSquirrel {

    private GameCommand gameCommand;
	
    public HandOperatedMasterSquirrel(XY location) {
        super(location);
    }

    @Override
    public void nextStep(EntityContext context) {
        System.out.println(this);
        super.nextStep(context);

        if (isStunned())
            return;
        
        if(gameCommand == null)
        	return;
        
        switch(gameCommand.getType()) {
        case MOVE:
        	context.tryMove(this, ((MoveCommand) gameCommand).getMoveDirection());
		case MASTERENERGY:
			break;
		case SPAWN:
			break;
		default:
			context.tryMove((MasterSquirrel)this, new MoveCommand(new XY(0,0)).getMoveDirection());
			break;
        }
        gameCommand = null;
    }

    @Override
    public String toString() {
        return "HandOperatedMasterSquirrel{ " + super.toString() + '}';
    }
    
    public void setGameCommand(GameCommand gameCommand) {
    	this.gameCommand = gameCommand;
    }

}
