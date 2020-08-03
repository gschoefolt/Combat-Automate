package model;

public class Grid {
    private Integer[] size;
    private Cell[] cells;

    public Grid(Integer[] size) {
    	this.size = size;
    	this.cells = new Cell[size[0] * size[1]];
    	
    	this.initializeGrid(size[0], size[1]);
    }

    private void initializeGrid(Integer x, Integer y) {
    	Integer compteur = 0;
    	for(int i = 0; i < x; i++) {
    		for(int j = 0; j < y; j++) {
    			this.cells[compteur] = new Cell(j, i);
    			compteur++;
    		}
    	}
    }
    
    public void changeCell(Integer indexCell) {
    	this.cells[indexCell].deadOrAlive();  	
    }
    
    
    public Cell getCell(Integer index) {
    	return this.cells[index];
    }
    
    public Cell[] getCells() {
    	return this.cells;
    }
    
    public Integer[] getGridSize() {
    	return this.size;
    }
}
