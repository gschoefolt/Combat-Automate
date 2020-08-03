package controller;

import java.util.ArrayList;
import java.util.Random;

import model.Automaton;
import model.Cell;
import model.Extension;
import model.Neighbors;
import model.Periodicity;
import model.Repetition;
import model.Rule;
import model.State;

public class Game {
	private Integer[] gridSize;
    private Integer nbAutomates = 0, iteration = 0;
    private Extension extension;
    
    private ArrayList<Automaton> automatons;
    
    private Random random;
    
    /**
     * Constructeur du controller du jeu
     * @param gridSize la taille de la grille x * y
     * @param ext la strategie d'extension choisie
     */
    public Game(String ext, Integer[] gridSize) {
    	random = new Random(42);
    	this.gridSize = gridSize; 
    	
    	switch(ext) {
			case "Periodicite":
				this.extension = new Periodicity();
				break;
			case "Repetition":
				this.extension = new Repetition();
				break;
    	}
	
    	automatons = new ArrayList<Automaton>(); 
    }

    
    /**
     * Ajoute l'automate aux tableaux des automates
     * @param r une regle fixe pour l'automate
     * @param n les voisins concernes pour l'automate
     */
    public void addAutomaton(Rule r, Neighbors n) {
    	automatons.add(new Automaton(r, n, this.extension, this.gridSize));
    	nbAutomates++;
    }
    
    
    /**
     * Change une cellule de vivante a morte ou l'inverse pour un automate
     * @param playerId l'id du player (exemple : Player 1 => id 0
     * @param indexCell l'index de la cellule dans le tableau de la grille
     */
    public void changeCell(Integer playerId, Integer indexCell) {
    	automatons.get(playerId).changeCell(indexCell);    	
    }
    
    
    /**
     * Passe a l'iteration suivante.
     * Permet de mettre a jour chaque automate
     */
    public void nextIteration() {
    	this.iteration++;
    	
    	// On update les automates
    	for(int i = 0; i < this.automatons.size(); i++) {
    		this.automatons.get(i).update();
    	}
    	
    	// On fait les combats
    	for(int i = 0; i < this.gridSize[0] * this.gridSize[1]; i++) {
    		if(this.automatons.get(0).getCell(i).getFutureState().equals(State.LIVE) &&
    				this.automatons.get(1).getCell(i).getFutureState().equals(State.LIVE))
    			
    			// Si l'etat de la cellule est vivant pour les deux automates
    			if(random.nextInt(101) > 50) {    				
    				this.automatons.get(1).getCell(i).setFutureState(State.DEAD);
    			}
    			else {
    				this.automatons.get(0).getCell(i).setFutureState(State.DEAD);
    			}
    	}
    	
    	// On update les cellules de chaque automate
    	for(int i = 0; i < this.automatons.size(); i++) {
    		this.automatons.get(i).updateCells();
    	}
    }
    
    
    /**
     * Permet de recuperer le nombre d'iteration actuelle
     * @return le nombre d'iteration actuelle
     */
    public Integer getIteration() {
    	return this.iteration;
    }
    
    
    /**
     * Obtient le tableau des cellules pour un automate donné (=player)
     * @param playerId l'id de l'automate ou le joueur
     * @return le tableau de cellules correspondant à l'id de l'automate
     */
    public Cell[] getCells(Integer playerId) {
    	return this.automatons.get(playerId).getCells();
    }
    
    
    /**
     * Obtient vrai ou faux si l'automate n'a plus aucune cellule vivante
     * @param playerId l'id de l'automate ou le joueur
     * @return un boolean
     */
    public boolean isDead(Integer playerId) {
    	return this.automatons.get(playerId).isDead();
    }
    
}
