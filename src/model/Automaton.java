package model;

public class Automaton {
    private Grid grid;
    private Neighbors neighbors;
    private Rule rule;
    private Extension extension;

    
    /**
     * Constructeur de l'automate
     * @param r la stratégie de règles de l'automate
     * @param n la stratégie de voisinage de l'automate
     * @param e la stratégie d'extension de l'automate
     * @param gridSize la taille de la grille 
     */
    public Automaton(Rule r, Neighbors n, Extension e, Integer[] gridSize) {
    	this.rule = r;
    	this.neighbors = n;
    	this.extension = e;
    	this.grid = new Grid(gridSize);
    }
    
    
    /**
     * Change une cellule de vivante à morte et inversement avec son index
     * @param indexCell l'index de la cellule
     */
    public void changeCell(Integer indexCell) {
    	this.grid.changeCell(indexCell);
    }
    
    
    /**
     * Obtient la largeur x de la grille
     * @return un entier correspondant à la la largeur
     */
    public Integer getGridX() {
    	return this.grid.getGridSize()[0];
    }
    
    
    /**
     * Obtient la longueur y de la grille
     * @return un entier correspondant à la la longueur
     */
    public Integer getGridY() {
    	return this.grid.getGridSize()[1];
    }
    
    
    /**
     * Obtient une cellule grâce à son abscisse et son ordonée.
     * Verifie les bornes de l'abscisse et de l'ordonnée pour déléguer 
     * à la stratégie si besoin.
     * @param x l'abscisse de la cellule
     * @param y l'ordonnée de la cellule
     * @return un objet Cell
     */
    public Cell getCell(Integer x, Integer y) {
    	Cell c;
    	if(x < 0 || y < 0 || x >= this.getGridX() || y >= this.getGridY()) {
    		c = this.extension.getCell(x, y, this);
    	}
    	else {
    		c = this.grid.getCell(y * getGridX() + x);
    	}
    	return c;
    }
    
    
    /**
     * Obtient une cellule de la grille grâce à son index
     * @param index l'index de la cellule
     * @return un objet Cell
     */
    public Cell getCell(Integer index) {
    	if(index >= 0 && index < this.getGridX() * this.getGridY()) {
    		return this.grid.getCell(index);
    	}    	
    	else {
    		return new Cell(999, 999);
    	}
    }
    
    
    /**
     * Obtient le tableau de la grille associée
     * @return
     */
    public Cell[] getCells() {
    	return this.grid.getCells();
    }
    
    
    /**
     * Met à jour le "futureState" de chaque cellules de la grille
     */
    public void update() {
    	for(int i = 0; i < this.grid.getGridSize()[0] * this.grid.getGridSize()[1]; i++) {
    		Cell c = this.grid.getCell(i);
    		c.setFutureState(this.rule.next(this.neighbors.getNeighbors(c, this)));    		
    	}
    }
    
    
    /**
     * Met à jour le "State" actuel de chaque cellule de la grille
     */
    public void updateCells() {
    	for(int i = 0; i < this.grid.getGridSize()[0] * this.grid.getGridSize()[1]; i++) {
    		this.grid.getCell(i).updateState();
    	}
    }

    
    /**
     * Vérifie si toutes les cellules de la grille sont morte
     * @return
     */
	public boolean isDead() {
		Integer compteur = 0;
		for(int i = 0; i < this.grid.getGridSize()[0] * this.grid.getGridSize()[1]; i++) {
			if(this.grid.getCell(i).getState().equals(State.DEAD)) compteur++;
		}
		
		return compteur == this.grid.getGridSize()[0] * this.grid.getGridSize()[1];
	}
	
}