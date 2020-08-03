package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuView extends JFrame{ 
	
	private static final long serialVersionUID = 1L;
    
    private final Font f = new Font("Arial", Font.PLAIN, 22);
    private int[] sizeOfApp = new int[] { 800, 600 };
    
    private String[] extensions = new String[] { "Repetition", "Periodicite" };
    private String[] gridSizes = new String[] { "10x10", "25x25", "50x50", "100x100", "200x200"};
    private String[] automatons = new String[] { "Jeu de la vie", "Fredkin" };
    
    private JComboBox<String> jcbExtension, jcbSizeGrid, jcbAutomatonj1, jcbAutomatonj2;
    private JTextField jtxtCellsAlive, jtxtNbIterations;

    private Integer nbC, nbI;
    private Integer[] gridSize;
    private String extension;
    private ArrayList<String> playersAutomaton;
    private Integer idPlayerStarting;
        
    private Random rd;
    
    
    /**
     * Constructeur de la classe.
     * Initialise une interface proposant aux joueurs de choisir
     * plusieurs parametres pour debuter la partie      
     */
    public MenuView() { 
    	rd = new Random();
    	
    	// Taille de la grille
    	JPanel jpnlSizeGrid = new JPanel();
        
        JLabel jlblSizeGrid = new JLabel("Taille de la grille : ");
        jlblSizeGrid.setFont(f);
        jpnlSizeGrid.add(jlblSizeGrid);        
        
        jcbSizeGrid = new JComboBox<String>(gridSizes);
        jcbSizeGrid.setFont(f);
        jpnlSizeGrid.add(jcbSizeGrid);
        
        
        // Methode d'extension
        JPanel jpnlExtension = new JPanel();
        
        JLabel jlblExtension = new JLabel("Methode d'extension : ");
        jlblExtension.setFont(f);
        jpnlExtension.add(jlblExtension);        
        
        jcbExtension = new JComboBox<String>(extensions);
        jcbExtension.setFont(f);
        jpnlExtension.add(jcbExtension);
        
        
        // Nombre de cellules vivantes
        JPanel jpnlCellsAlive = new JPanel();
    	
    	JLabel jlblCellsAlive = new JLabel("Nombre de cellules vivantes pour chaque joueur : ");
    	jlblCellsAlive.setFont(f);
    	jpnlCellsAlive.add(jlblCellsAlive);
    	
    	jtxtCellsAlive = new JTextField();
    	jtxtCellsAlive.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
               verifKeyPressed(ke, jtxtCellsAlive);
            }
        });
    	jtxtCellsAlive.setColumns(3);
    	jtxtCellsAlive.setText("5");
    	jtxtCellsAlive.setFont(f);
    	jpnlCellsAlive.add(jtxtCellsAlive);
        
    	
    	// Nombre d'iterations 
    	JPanel jpnlNbIterations = new JPanel();
    	
    	JLabel jlblNbIterations = new JLabel("Nombre d'iterations : ");
    	jlblNbIterations.setFont(f);
    	jpnlNbIterations.add(jlblNbIterations);
    	
    	jtxtNbIterations = new JTextField();
    	jtxtNbIterations.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
               verifKeyPressed(ke, jtxtNbIterations);
            }
        });
    	jtxtNbIterations.setColumns(3);
    	jtxtNbIterations.setText("50");
    	jtxtNbIterations.setFont(f);
    	jpnlNbIterations.add(jtxtNbIterations);
    	
    	
    	// Les automates pour les joueurs
    	JPanel jpnlAutomatons = new JPanel();
    	
    	JLabel jlblJoueur1 = new JLabel(" Automate Joueur 1 : ");
    	jlblJoueur1.setFont(f);
    	jpnlAutomatons.add(jlblJoueur1);
    	
    	jcbAutomatonj1 = new JComboBox<String>(automatons);
    	jcbAutomatonj1.addActionListener(e -> selectedIndexChanged(jcbAutomatonj1));
    	jcbAutomatonj1.setFont(f);
    	jpnlAutomatons.add(jcbAutomatonj1);
    	
    	JLabel jlblJoueur2 = new JLabel(" Automate Joueur 2 : ");
    	jlblJoueur2.setFont(f);
    	jpnlAutomatons.add(jlblJoueur2);
    	
    	jcbAutomatonj2 = new JComboBox<String>(automatons);
    	jcbAutomatonj2.addActionListener(e -> selectedIndexChanged(jcbAutomatonj2));
    	jcbAutomatonj2.setSelectedIndex(1);
    	jcbAutomatonj2.setFont(f);
    	jpnlAutomatons.add(jcbAutomatonj2);
    	
    	
    	// Le bouton pour demarrer
    	JPanel jpnlStart = new JPanel();
    	
    	JButton jbtnStart = new JButton("Demarrer une partie");
    	jbtnStart.setFont(f);
    	jbtnStart.addActionListener(e -> startGame());
    	jpnlStart.add(jbtnStart);
    	
    	
    	// Ajout des JPanels
    	this.add(jpnlSizeGrid);
        this.add(jpnlExtension);
        this.add(jpnlCellsAlive);
        this.add(jpnlNbIterations);
        this.add(jpnlAutomatons);
        this.add(jpnlStart);        
        
        
        // On choisit le joueur qui commence à choisir aléatoirement
        if(rd.nextInt(101) > 50) {
        	jcbAutomatonj1.setEnabled(false);
        	idPlayerStarting = 1;
        }
        else {
        	jcbAutomatonj2.setEnabled(false);
        	idPlayerStarting = 0;
        }
        
        
        // Affichage de la fenetre
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLayout(new GridLayout(6, 1));
        this.setTitle("Menu du jeu");
        this.setLocation(screenSize.width / 2 - (sizeOfApp[0] / 2), screenSize.height / 2 - (sizeOfApp[1] / 2));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(sizeOfApp[0], sizeOfApp[1]);
        this.setVisible(true);        
    }
    
    
    /**
     * Méthode exécuté lors d'un changement d'index dans l'une des
     * deux JComboBox de choix des automates des joueurs
     * @param jcb la JComboBox qui a changé d'index
     */
    private void selectedIndexChanged(JComboBox<String> jcb) {
    	int index = jcb.getSelectedIndex();
		if(jcb == jcbAutomatonj1) {
			jcbAutomatonj2.setEnabled(true);
			if(index == jcbAutomatonj2.getSelectedIndex()) {
				// Le même que l'autre joueur
				jcbAutomatonj2.setSelectedIndex(index == 0 ? 1 : 0);
			}
		}
		else {
			jcbAutomatonj1.setEnabled(true);
			if(index == jcbAutomatonj1.getSelectedIndex()) {
				// Le même que l'autre joueur
				jcbAutomatonj1.setSelectedIndex(index == 0 ? 1 : 0);
			}
		}
	}


	/**
     * Méthode permettant de vérifier les touches utilisées dans les
     * JTextField. Ne permet de garder que les nombres et les touches pour 
     * supprimer.
     * @param key la touche appuyée
     * @param jtxt le JTextField associé
     */
    public void verifKeyPressed(KeyEvent key, JTextField jtxt) {
    	if (key.getKeyChar() >= '0' && key.getKeyChar() <= '9' 
        		|| key.getKeyCode() == KeyEvent.VK_DELETE
        		|| key.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
        	jtxt.setEditable(true);
        } 
        else {
        	jtxt.setEditable(false);
        }
    }

    
    /**
     * Methode verifiant les informations rentrees
     * Permet de lancer le jeu avec les parametres souhaites par les joueurs.
     * @see GameView.GameView()
     */
    public void startGame() {
    	if(jtxtCellsAlive.getText().length() == 0 
    			|| jtxtNbIterations.getText().length() == 0) {
    		JOptionPane.showMessageDialog(this, "Il manque des informations dans les champs");
    	}
    	else if(jcbAutomatonj1.getSelectedItem().toString().equals(jcbAutomatonj2.getSelectedItem().toString())) {
    		JOptionPane.showMessageDialog(this, "Les joueurs ne peuvent pas avoir le meme automate");
    	}
    	else {
    		// On peut demarrer une partie
    		String sizeGrid = jcbSizeGrid.getSelectedItem().toString();
    		
    		this.extension = jcbExtension.getSelectedItem().toString();    		
    		this.nbC = Integer.parseInt(jtxtCellsAlive.getText());
    		this.nbI = Integer.parseInt(jtxtNbIterations.getText());
    		
    		this.gridSize = new Integer[2];
    		this.gridSize[0] = Integer.parseInt(sizeGrid.split("x")[0]);
    		this.gridSize[1] = Integer.parseInt(sizeGrid.split("x")[1]);
    		
    		playersAutomaton = new ArrayList<String>();
    		playersAutomaton.add(jcbAutomatonj1.getSelectedItem().toString());
    		playersAutomaton.add(jcbAutomatonj2.getSelectedItem().toString());
    		
    		this.setVisible(false);
    		new GameView(this);
    	}    		
    }

    
    /**
     * Permet d'avoir l'extension choisie des joueurs
     * @return une chaine de caractère représentant l'extension choisie
     */
    public String getExtension() {
    	return this.extension;
    }

    
    /**
     * Permet d'avoir les automates choisi par les joueurs
     * @return une liste de chaine de caractère représentant l'automate choisi
     */
    public ArrayList<String> getPlayersAutomaton() {
    	return this.playersAutomaton;
    }

    
    /**
     * Permet d'avoir le nombre d'itération voulu par les joueurs
     * @return un entier représentant le nombre d'itération
     */
    public Integer getNbIterations() {
    	return this.nbI;
    }

    
    /**
     * Permet d'avoir la taille x et y de la grille choisie
     * @return un tableau avec la taille en x et la taille en y de la grille
     */
    public Integer[] getGridSize() {
    	return this.gridSize;
    }

    
    /**
     * Permet d'avoir le nombre de cellules vivantes par joueurs
     * @return un entier représentant le nombre de cellules vivantes
     */
    public Integer getNbCellsAlive() {
    	return this.nbC;
    }

    
    /**
     * Permet d'avoir l'id du player qui a commencé
     * @return un entier représentant l'id du joueur qui a débuté
     */
    public Integer getIdPlayerStart() {
    	return this.idPlayerStarting;
    }
        
}
