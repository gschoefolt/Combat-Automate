package model;

public class Cell {
    private State state;
    private State futureState;
    private Integer posX, posY;
    
    public Cell(Integer x, Integer y) {
    	this.state = State.DEAD;
    	this.posX = x;
    	this.posY = y;
    }
    
    public void deadOrAlive() {
    	this.state = this.state.equals(State.DEAD) ? State.LIVE : State.DEAD;
    }
    
    public State getState() {
    	return this.state;
    }
    
    public void setFutureState(State s) {
    	this.futureState = s;
    }
    
    public State getFutureState() {
    	return this.futureState;
    }
    
    public Integer getX() {
    	return this.posX;
    }
    
    public Integer getY() {
    	return this.posY;
    }
    
    public void updateState() {
    	this.state = this.futureState;
    }
    
}
