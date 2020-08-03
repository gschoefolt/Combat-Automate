package model;

public class NeighborsGOL implements Neighbors {

	public State[] getNeighbors(Cell c, Automaton a) {
		State[] states = new State[9];
		/**
		 * 0 1 2
		 * 3 C 5
		 * 6 7 8
		 */

		Integer x = c.getX();
		Integer y = c.getY();
		
		states[0] = a.getCell(x - 1, y - 1).getState();
		states[1] = a.getCell(x, y - 1).getState();
		states[2] = a.getCell(x + 1, y - 1).getState();
		states[3] = a.getCell(x - 1, y).getState();
		
		states[4] = c.getState();
		
		states[5] = a.getCell(x + 1, y).getState();
		states[6] = a.getCell(x - 1, y + 1).getState();
		states[7] = a.getCell(x, y + 1).getState();
		states[8] = a.getCell(x + 1, y + 1).getState();
		
		return states;
	}
	
	@Override
	public String toString() {
		return "NeighborsGOL";
	}
	
}
