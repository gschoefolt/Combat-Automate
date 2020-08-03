package model;

public class RuleGOL implements Rule {

	/**
	 * On s'attend a recevoir un tableau de longueur 9
	 */
	public State next(State ...states) {
		State nextState;
		
		if(states.length != 9)  {
			throw new IllegalArgumentException("Longueur != 9");
		}
		
		Integer nbNeighbors = 0;
		for(int i = 0; i < states.length; i++) {
			if(states[i].equals(State.LIVE) && i != 4) {
				nbNeighbors++;
			}
		}
		
		if( ((nbNeighbors == 2 || nbNeighbors == 3) && states[4].equals(State.LIVE)) ||
				(nbNeighbors == 3 && states[4].equals(State.DEAD)) )  {
			// Survie si deux ou trois voisines ou nait si trois voisins
			nextState = State.LIVE;
		}
		else {
			nextState = State.DEAD;
		}
		
		return nextState;
	}
	
	@Override
	public String toString() {
		return "RuleGOL";
	}
}
