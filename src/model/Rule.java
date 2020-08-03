package model;

public interface Rule {
	
    public State next(State ...states);
}
