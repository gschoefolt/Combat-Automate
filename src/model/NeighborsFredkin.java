package model;

public class NeighborsFredkin implements Neighbors {

	public State[] getNeighbors(Cell c, Automaton a) {
		State[] states = new State[5];
		/**
		 *   0 
		 * 1 2 3
		 *   4  
		 */
		
		Integer x = c.getX();
		Integer y = c.getY();
		
		states[0] = a.getCell(x, y - 1).getState();
		states[1] = a.getCell(x - 1, y).getState();
		states[2] = c.getState();
		states[3] = a.getCell(x + 1, y).getState();
		states[4] = a.getCell(x, y + 1).getState();
		
		return states;
	}
	
	@Override
	public String toString() {
		return "NeighborsFredkin";
	}
	
}
