package model;

public class Periodicity implements Extension {	
	
	public Cell getCell(Integer x, Integer y, Automaton a) {
		Cell c;
		if(x < 0) {
			// Toute la partie gauche
			
			if(y < 0) {
				c = a.getCell(a.getGridX() - 1, a.getGridY() - 1);
			}
			else if(y >= a.getGridY()) {
				c = a.getCell(a.getGridX() - 1, 0);
			}
			else {
				c = a.getCell(a.getGridX(), y);
			}
		}
		else if(x >= a.getGridX()) {
			// Toute la partie droite
			
			if(y < 0) {
				c = a.getCell(0, a.getGridY() - 1);
			}
			else if(y >= a.getGridY()) {
				c = a.getCell(0, 0);
			}
			else {
				c = a.getCell(0, y);
			}
		}
		else {
			// x appartient a [0, a.getGridX() - 1]
			
			if(y < 0) {
				// Toute la partie haute
				c = a.getCell(x, a.getGridY() - 1);
			}
			else if(y >= a.getGridY()) {
				// Toute la partie basse
				c = a.getCell(x, 0);
			}
			else {
				// y appartient a [0, a.getGridY() - 1]
				c = a.getCell(x, y);
			}
		}
		
		return c;
	}
	
}
