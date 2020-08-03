package model;

public class Repetition implements Extension {
	
	public Cell getCell(Integer x, Integer y, Automaton a) {
		Cell c;
		if(x < 0) {
			// Toute la partie gauche			
			c = a.getCell(x + 1, y);
		}
		else if(x >= a.getGridX()) {
			// Toute la partie droite
			c = a.getCell(x - 1, y);
		}
		else {
			// x appartient a [0, a.getGridX() - 1]
			
			if(y < 0) {
				// Toute la partie haute
				c = a.getCell(x, y + 1);
			}
			else if(y >= a.getGridY()) {
				// Toute la partie basse
				c = a.getCell(x, y - 1);
			}
			else {
				// y appartient a [0, a.getGridY() - 1]
				c = a.getCell(x, y);
			}
		}
		
		return c;
	}
	
}
