package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.Game;
import model.Cell;
import model.Neighbors;
import model.NeighborsFredkin;
import model.NeighborsGOL;
import model.Rule;
import model.RuleFredkin;
import model.RuleGOL;
import model.State;

public class GameView extends JFrame {
	
	private static final long serialVersionUID = 1L;
		
	private final Font f = new Font("Arial", Font.PLAIN, 22);	
	private Integer[] sizeOfApp = {1280, 720};
	
	private Color[] playersColor = { Color.BLUE, Color.red };
	private Integer[] remainingCellsToPlace;
	
	private JButton[] btns;
	
    private Game game;
    private MenuView menuView;
    private Timer timer;
    
    private Integer idPlayerPlaying; // A la fin se remplit du joueur gagnant
    private Integer[] remainingCells;
    private Integer delay = 750;
    private boolean canGo = false, finish = false;
    
    private JPanel jpnlGrid;
    private JLabel jlblRemainingCells, jlblIteration;
    private JButton jbtnRestart, jbtnStart;

    
    /**
     * Constructeur de la classe, initiliase une nouvelle
     * fenetre JFrame
     * @param m le menuview pour avoir acces aux variables 
     * necessaires
     */
    public GameView(MenuView m) {
    	this.remainingCells = new Integer[2];
    	this.menuView = m;
    	this.idPlayerPlaying = menuView.getIdPlayerStart() == 0 ? 1 : 0; // Le joueur qui commence à placer les cellules n'est pas le même
    	
    	this.game = new Game(menuView.getExtension(), menuView.getGridSize());
    	
    	remainingCellsToPlace = new Integer[2];
    	remainingCellsToPlace[0] = menuView.getNbCellsAlive(); // Player 1
    	remainingCellsToPlace[1] = menuView.getNbCellsAlive(); // Player 2
    	
    	
    	// Panel des boutons du haut de l'application
    	JPanel jpnlMenu = new JPanel();
    	
    	JLabel jlblj1 = new JLabel("  Joueur 1 :  ");
    	jlblj1.setFont(f);
    	jpnlMenu.add(jlblj1);
    	
    	JPanel jColorj1 = new JPanel();
    	jColorj1.setSize(40, 40);
    	jColorj1.setBackground(playersColor[0]);
    	jpnlMenu.add(jColorj1);
    	
    	JLabel jlblj2 = new JLabel("  Joueur 2 :  ");
    	jlblj2.setFont(f);
    	jpnlMenu.add(jlblj2);
    	
    	JPanel jColorj2 = new JPanel();
    	jColorj2.setSize(40, 40);
    	jColorj2.setBackground(playersColor[1]);
    	jpnlMenu.add(jColorj2);
    	
    	jlblRemainingCells = new JLabel("  Nombre de cellules à placer : " + remainingCellsToPlace[0] + "  ");
    	jlblRemainingCells.setFont(f);
    	jpnlMenu.add(jlblRemainingCells);
    	
    	jbtnStart = new JButton(" Démarrer la partie ");
    	jbtnStart.addActionListener(e -> startGame());
    	jbtnStart.setFont(f);
    	jpnlMenu.add(jbtnStart);
    	
    	jlblIteration = new JLabel("  0 / " + menuView.getNbIterations() + "  ");
    	jlblIteration.setFont(f);
    	jpnlMenu.add(jlblIteration);
    	
    	jbtnRestart = new JButton("  Restart  ");
    	jbtnRestart.setFont(f);
    	jbtnRestart.setEnabled(false);
    	jbtnRestart.addActionListener(e -> restartGame());
    	jpnlMenu.add(jbtnRestart);
    	
    	// Panel des cellules
    	btns = new JButton[menuView.getGridSize()[1] * menuView.getGridSize()[0]];
    	
    	jpnlGrid = new JPanel();
    	jpnlGrid.setLayout(new GridLayout(menuView.getGridSize()[1], menuView.getGridSize()[0]));
    	
    	this.add(jpnlGrid, BorderLayout.CENTER);
    	this.add(jpnlMenu, BorderLayout.NORTH);
    	
    	
    	// Affichage de la fenetre
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setTitle("Jeu");
        this.setLocation(screenSize.width / 2 - (sizeOfApp[0] / 2), screenSize.height / 2 - (sizeOfApp[1] / 2));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(sizeOfApp[0], sizeOfApp[1]);
        this.setVisible(true);
        
        
        // Initialisation des automates des joueurs 
        this.addAutomatons();
        
        // Initialisation des boutons correspondants aux cellules
        new Thread(() -> this.initializeGrid()).start();
        try {
			Thread.sleep(50);
		} 
        catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

    
    /**
     * Méthode exécutée pour lancer le jeu, lancer un timer qui execute toutes les 
     * x millisecondes l'update de tous les automates et update la grille visuelle
     */
	private void startGame() {
		this.canGo = remainingCellsToPlace[0] == 0 && remainingCellsToPlace[1] == 0;
		
		// On vérifie que les joueurs ont placé toutes leurs cellules		
		if(canGo) {			
			jbtnStart.setEnabled(false);
			timer = new Timer(delay, e -> nextIteration());
			timer.start();
		}
		else {
			JOptionPane.showMessageDialog(this, "Les joueurs doivent placer leur cellules");
		}
		
	}
	

	/**
	 * Affiche un message du gagnant et permet de cliquer 
	 * le bouton pour recommencer une partie
	 */
	private void finishGame() {
		if(finish) {
			String msg = String.format("Le joueur %d a gagné !", (idPlayerPlaying + 1));
			JOptionPane.showMessageDialog(this,  msg);
		}
		else {
			if(idPlayerPlaying == -1) {
				JOptionPane.showMessageDialog(this, "Les joueurs sont ex aequo");
			}
			else {
				String msg = String.format("Le joueur %d est le gagnant avec %d cellules contre %d !", 
						idPlayerPlaying + 1, remainingCells[idPlayerPlaying], 
						remainingCells[idPlayerPlaying == 0 ? 1 : 0]);
				JOptionPane.showMessageDialog(this,  msg);
			}
		}
		
		jbtnRestart.setEnabled(true);
	}
	
	
	/**
	 * Permet de réafficher le menu afin de recommencer une partie
	 */
	private void restartGame() {
		this.dispose();
		this.menuView.setVisible(true);
	}
	
	
	/**
	 * Notifie le jeu "Game" d'une prochaine itération.
	 * Méthode appelé avec un timer avec un delay donné en variable
	 * globale
	 */
	private void nextIteration() {
		// On vérifie si on a un automate qui n'a plus de cellule
		if(game.isDead(0)) {
			idPlayerPlaying = 1;
			finish = true;
		}
		else if(game.isDead(1)) {
			idPlayerPlaying = 0;
			finish = true;
		}
		
		if(!finish) {
			if(game.getIteration() == menuView.getNbIterations()) {
				timer.stop();
				
				// On détermine qui est le gagnant
				if(remainingCells[0] > remainingCells[1]) {
					idPlayerPlaying = 0;
				}
				else if(remainingCells[0] < remainingCells[1]) {
					idPlayerPlaying = 1;
				}
				else {
					idPlayerPlaying = -1;
				}	
				
				finishGame();
			}
			else {
				game.nextIteration();
				
				Integer it = java.lang.Math.min(game.getIteration() + 1, menuView.getNbIterations());
				jlblIteration.setText("  " + it + " / " + menuView.getNbIterations() + "  ");
				
				for(int i = 0; i < playersColor.length; i++) {
					int ibis = i;
					new Thread(() -> updateGrid(game.getCells(ibis), ibis)).start();
					
					try {
						Thread.sleep(100);
					} 
					catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			}			
		}
		else {
			timer.stop();
			
			finishGame();
		}	
	}
	
	
	/**
	 * Met à jour l'affichage de la grille des boutons
	 * @param cells les cellules d'un automates
	 * @param playerId l'id du player a qui appartient ces cellules
	 */
	private void updateGrid(Cell[] cells, Integer playerId) {
		Integer compteur = 0;
		for(int i = 0; i < this.menuView.getGridSize()[0] * this.menuView.getGridSize()[1]; i++) {
			if(cells[i].getState().equals(State.LIVE)) {
				compteur ++;
				this.btns[i].setBackground(playersColor[playerId]);
			}
			else if(this.btns[i].getBackground().equals(playersColor[playerId])) {
				this.btns[i].setBackground(Color.WHITE);
			}
		}
		
		remainingCells[playerId] = compteur;
	}

	
	
	/**
	 * On ajoute les automates de chaque joueurs
	 * au jeu (Game) avec les règles (Rule) 
	 * et les voisins (Neighbors) correspondants
	 */
	private void addAutomatons() {
    	ArrayList<String> al = menuView.getPlayersAutomaton();
    	for(int i = 0; i < al.size(); i++) {
    		Rule r = null;
    		Neighbors n = null;
    		switch(al.get(i)) {    		 
    			case "Jeu de la vie":
    				r = new RuleGOL();
    				n = new NeighborsGOL();
    				break;
    			case "Fredkin":
    				r = new RuleFredkin();
    				n = new NeighborsFredkin();
    				break;
    		}
    		
    		game.addAutomaton(r, n);	
    	}
    	
    }
    
	
	
	/**
	 * Initialise la grille visuelle de l'application
	 * Créer un nouveau bouton correspondant à une cellule
	 * et associé une methode pour l'evenement clique de ce bouton
	 */
    private void initializeGrid() {
    	Integer compteur = 0;
    	for(int i = 0; i < menuView.getGridSize()[0]; i++) {
    		for(int j = 0; j < menuView.getGridSize()[1]; j++) {
    			Integer ibis = i;
    			Integer jbis = j;
    			
    			JButton btn = new JButton();
    			btn.setBackground(Color.WHITE);
    			btn.addActionListener(e -> clickBtnGrid(btn, jbis, ibis));
    			
    			jpnlGrid.add(btn);
    			
    			btns[compteur] = btn;
    			compteur++;
    		}
    	}
    }

    
    
    /**
     * Clique sur un bouton pour créer une cellule vivante 
     * pour le joueur actuellement affiché dans la 
     * JComboBox
     * @param x l'abscisse de la cellule
     * @param y l'ordonnee de la cellule
     * @param btn le bouton sur lequel on clique
     */
	private void clickBtnGrid(JButton btn, Integer x, Integer y) {
		if(!canGo) {
			if(btn.getBackground().equals(Color.WHITE)) {
				// On choisit cette cellule pour etre vivante				
				if(remainingCellsToPlace[idPlayerPlaying] == 0 && remainingCellsToPlace[idPlayerPlaying == 0 ? 1 : 0] > 0) {
					// Le joueur ne peut plus ajouter de cellule vivante					
					JOptionPane.showMessageDialog(this, "Le joueur " + (idPlayerPlaying + 1) + " ne peut plus choisir "
							+ "de cellule.");
				}
				else if(remainingCellsToPlace[0] == 0 && remainingCellsToPlace[1] == 0) {
					// Les joueurs peuvent débuter la partie
					this.canGo = true;
					JOptionPane.showMessageDialog(this, "Vous pouvez démarrer la partie");
				}
				else {
					// Le joueur choisit cette cellule pour etre vivante 					
					remainingCellsToPlace[idPlayerPlaying]--;
					jlblRemainingCells.setText("  Nombre de cellules à placer : " + remainingCellsToPlace[idPlayerPlaying] + "  ");
					btn.setBackground(playersColor[idPlayerPlaying]);
					btn.setForeground(Color.WHITE);			
					
					// On doit notifier le jeu de l'etat de la cellule qui change
					this.game.changeCell(idPlayerPlaying, y * menuView.getGridSize()[0] + x);
					
					idPlayerPlaying = idPlayerPlaying == 0 ? 1 : 0;
				}
			}
			else {
				// Le joueur n'a pas le droit de prendre cette cellule	
				JOptionPane.showMessageDialog(this, "Le joueur " + (idPlayerPlaying + 1) 
						+ " ne peut pas choisir cette cellule");
			}	
		}			
	}
	

}
