package model;

public enum State {
    LIVE("living"),
    DEAD("dead");
    
	private String name;
    
    State(String name) {
    	this.name = name;
    }
    
    @Override
    public String toString() {
    	return this.name;
    }
}
