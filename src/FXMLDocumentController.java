import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
//import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Guy Junior
 */
public class FXMLDocumentController implements Initializable, Parametres{
    /*
     * Variables globales correspondant à des objets définis dans la vue (fichier .fxml)
     * Ces variables sont ajoutées à la main et portent le même nom que les fx:id dans Scene Builder
     */
    @FXML
    private Label score;
	
	/**
	 * La valeur de la tuile la plus haute.
	 */
    @FXML
	private Label max;	
	@FXML
    private Label objectif;
	
	/**
	 * Nombre de deplacement fait.
	 */
	@FXML
	private Label deplacement;	
	@FXML
    private GridPane grille;
	
	/**
	 * Panneau recouvrant toute la fenêtre.
	 */
    @FXML
    private Pane fond; 
	
	/**
	 * Message Victoire/ Defaite.
	 */
	@FXML
	private Pane messa;
	@FXML 
	private Label mess;

    // variables globales non définies dans la vue (fichier .fxml)
    private Pane p; 
    private Label c ;
	private Grille g = new Grille();
	private int obj=OBJECTIF;
	private int departClicX, departClicY; //Utilisées pour le deplacement souris
	
	/**
	 * Initialise la partie.
	 */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
		grille.getStyleClass().add("gridpane");
		g.nouvelleCase(fond);								//On commence par ajouter 2 cases
		g.nouvelleCase(fond);
		max.setText(Integer.toString(g.getValeurMax()));	//La tuile avec ma plus grande valeur sert de max
		objectif.setText(Integer.toString(obj));			//L'objectif est celui ecrit dans Parametres
		messa= new Pane();									//On créé le message Victoire/Defaite mais on le masque
		mess= new Label();
		mess.setVisible(false);
		messa.setVisible(false);
	}
	
	/**
	 * Retient la position de depart de la souris pour les deplacements souris.
	 */
	@FXML
	private void handleD (MouseEvent event){
		departClicX = (int) event.getX();	//Position abscisse de la souris lors du debut du clic
        departClicY = (int) event.getY();	//Position ordonné de la souris lors du debut du clic
	}
	
	/**
	 * Lanceur du deplacement normal souris. Lorsque l'utilisateur deplace avec la souris. 
	 */
    @FXML
    private void handleDragAction(MouseEvent event) {
        int x = (int) event.getX();			//Position abscisse de la souris à la fin du clic
        int y = (int) event.getY();			//Position ordonné de la souris à la fin du clic
		int direction=0;
		int diffX=x-departClicX;			//Différence entre l'arrivé et le départ
		int diffY=y-departClicY;
        if (Math.abs(diffX) > Math.abs(diffY)) {		//On prend le deplacement le plus important entre le vertical et l'horizontal
            if (diffX<-10){								/*Il faut que le deplacement de la souris soit relativement important
														pour declancher le deplacement des tuiles*/
				direction=GAUCHE;
			}else if (diffX>10){
				direction=DROITE;
			}
		} else {
			if (diffY<-10){
				direction=HAUT;
			}else if (diffY>10){
				direction=BAS;
			}
		}
		lancerDeplacerCase(direction);
	}
	
	/**
	 * Lanceur de l'aide pour 1 deplacement. Lorsque l'on appuie sur le bonton Aide.
	 */
	@FXML
	public void handle(MouseEvent me){ 
		Grille g2= (Grille) g.clone();				/*On fait une copie de la grille, c'est ici qu'on testera les deplacements
													pour choisir le meilleur, sans modifier la vraie grille
													On gardera le deplacement qui produit le score le + haut*/
		int scoreMax=g.getScore();
		int direction=0;
		boolean b2;									//Représente si le deplacement est possible
		b2=g2.lanceurDeplacerCases(GAUCHE, false);	/*On simule un deplacement vers la gauche
													Le false en argument montre que l'on simule seulement
													Il empeche la modification de l'inteface graphique*/
		if (b2){
			scoreMax=g2.getScore();					//On retient ce score comme scoreMax
			direction= GAUCHE;
		}
		g2= (Grille) g.clone();
		b2=g2.lanceurDeplacerCases(HAUT, false);	//On simule un deplacement vers le haut
		if (b2 && g2.getScore()>=scoreMax){
			scoreMax=g2.getScore();
			direction= HAUT;
		}
		g2= (Grille) g.clone();
		b2=g2.lanceurDeplacerCases(DROITE, false);	//On simule un deplacement vers la droite
		if (b2 && g2.getScore()>=scoreMax){
			scoreMax=g2.getScore();
			direction= DROITE;
		}
		g2= (Grille) g.clone();
		b2=g2.lanceurDeplacerCases(BAS, false);		//On simule un deplacement vers le bas
		if (b2 && g2.getScore()>=scoreMax){
			direction= BAS;
		}
		lancerDeplacerCase(direction);
	}
	/**
	 * Lanceur pour que l'Ia termine la partie. Lorsque l'on appuie sur le bouton Terminer.
	 * Même procédé que pour l'aide.
	 */
	@FXML
	public void handleall(MouseEvent me){	
		while (!g.partieFinie()){				/*Même stratégie que pour 1 coup, 
												répetéé tant que la partie n'est pas finie*/
			Grille g2= (Grille) g.clone();
			int scoreMax=g.getScore();
			int direction=0;
			boolean b2;
			b2=g2.lanceurDeplacerCases(GAUCHE, false);
			if (b2){
				scoreMax=g2.getScore();
				direction= GAUCHE;
			}
			g2= (Grille) g.clone();
			b2=g2.lanceurDeplacerCases(HAUT, false); 
			if (b2 && g2.getScore()>=scoreMax){
				scoreMax=g2.getScore();
				direction= HAUT;
			}
			g2= (Grille) g.clone();
			b2=g2.lanceurDeplacerCases(DROITE, false); 
			if (b2 && g2.getScore()>=scoreMax){
				scoreMax=g2.getScore();
				direction= DROITE;
			}
			g2= (Grille) g.clone();
			b2=g2.lanceurDeplacerCases(BAS, false); 
			if (b2 && g2.getScore()>=scoreMax){
				direction= BAS;
			}
			lancerDeplacerCase(direction);
		}
		g.gameOver(fond);
	}
	
	/**
	 * Lanceur du deplacement normal clavier. Lorsque l'utilisateur appuie sur le clavier.
	 * Si c'est possible, le deplacement est fait dans la direction demandée.
	 * La touche "q" correspond à un deplacement vers la gauche.
	 * La touche "d" correspond à un deplacement vers la droite.
	 * La touche "s" correspond à un deplacement vers le bas.
	 * La touche "z" correspond à un deplacement vers le haut.
	 */
    @FXML
    public void keyPressed(KeyEvent ke) {	
        String touche = ke.getText();
		int direction=0;
        if (touche.compareTo("q") == 0) {			//On recupère la touche appuyée et on l'associe à une direction
			direction= GAUCHE; 
        } else if (touche.compareTo("d") == 0) { 
			direction= DROITE;
        } else if (touche.compareTo("s") == 0) {
			direction = BAS;
		} else if (touche.compareTo("z") == 0) {
			direction = HAUT;
		}
		lancerDeplacerCase(direction);
        /*Task task = new Task<Void>() { // on définit une tâche parallèle pour mettre à jour la vue
            @Override
            public Void call() throws Exception { // implémentation de la méthode protected abstract V call() dans la classe Task
                while (x != objectifx) { // si la tuile n'est pas à la place qu'on souhaite attendre en abscisse
                    if (x < objectifx) {
                        x += 1; // si on va vers la droite, on modifie la position de la tuile pixel par pixel vers la droite
                    } else {
                        x -= 1; // si on va vers la gauche, idem en décrémentant la valeur de x
                    }
                    // Platform.runLater est nécessaire en JavaFX car la GUI ne peut être modifiée que par le Thread courant, contrairement à Swing où on peut utiliser un autre Thread pour ça
                    Platform.runLater(new Runnable() { // classe anonyme
                        @Override
                        public void run() {
                            //javaFX operations should go here
                            p.relocate(x, y); // on déplace la tuile d'un pixel sur la vue, on attend 5ms et on recommence jusqu'à atteindre l'objectif
                            p.setVisible(true);    
                        }
                    }
                    );
                    Thread.sleep(5);
                } // end while
                return null; // la méthode call doit obligatoirement retourner un objet. Ici on n'a rien de particulier à retourner. Du coup, on utilise le type Void (avec un V majuscule) : c'est un type spécial en Java auquel on ne peut assigner que la valeur null
            } // end call

        };
        Thread th = new Thread(task); // on crée un contrôleur de Thread
        th.setDaemon(true); // le Thread s'exécutera en arrière-plan (démon informatique)
        th.start(); // et on exécute le Thread pour mettre à jour la vue (déplacement continu de la tuile horizontalement)*/
    }
	
	/**
	 * Lanceur du deplacement. Il deplace les tuiles et met à jour l'interface graphique.
	 * @param direction Direction dans laquelle se fait le deplacement. 
	 */
	private void lancerDeplacerCase(int direction){
		boolean b2 = g.lanceurDeplacerCases(direction, true);		/*On effectue le vrai deplacement 
															dans la direction qui a été retenue*/
		max.setText(Integer.toString(g.getValeurMax()));	//On met à jour les labels
		score.setText(Integer.toString(g.getScore()));
		deplacement.setText(Integer.toString(Integer.parseInt(deplacement.getText()) + 1));
		if (g.partieFinie()) {						//Si la partie est perdue
			g.gameOver(fond);
		}
		if (mess.isVisible()){						/*Permet de supprimer le message de victoire 
													lorsque l'utilisateur continue à jouer*/
			mess.setVisible(false);
			messa.setVisible(false);
		}
		if (g.getValeurMax()>=obj){					//Si il a atteint l'objectif
			Object[] messVic=g.victory(fond);		//On recupere le Label et le Pane dans un tableau
			mess=(Label) messVic[0];			
			messa=(Pane) messVic[1];
			obj*=2;									//L'objectif double
			objectif.setText(Integer.toString(obj));
		}
		if (b2) {
			g.nouvelleCase(fond);					//On ajoute une nouvelle case
		}
	}
}
