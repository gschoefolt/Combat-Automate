package model;

public class RuleFredkin implements Rule {

	/**
	 * On s'attend a recevoir un tableau de longueur 5
	 */
	public State next(State ...states) {
		State nextState;
		
		if(states.length != 5)  {
			throw new IllegalArgumentException("Longueur != 5");
		}
		
		Integer nbNeighbors = 0;
		for(int i = 0; i < states.length; i++) {
			if(states[i].equals(State.LIVE) && i != 2) {
				nbNeighbors++;
			}
		}
		
		if (nbNeighbors == 1 || nbNeighbors == 3)  {
			// Nait ou survie
			nextState = State.LIVE;
		}
		else {
			nextState = State.DEAD;
		}
		
		return nextState;
	}
	
	@Override
	public String toString() {
		return "RuleFredkin";
	}
}
