/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
 * @author Guy Ju
 */
public class FXMLDocumentController implements Initializable, Parametres{
    /*
     * Variables globales correspondant à des objets définis dans la vue (fichier .fxml)
     * Ces variables sont ajoutées à la main et portent le même nom que les fx:id dans Scene Builder
     */
    @FXML
    private Label score;
    @FXML
    private Label max;	//La valeur de la tuile la plus haute
	@FXML
    private Label objectif;
	@FXML
    private Label deplacement;	//Nombre de deplacement fait
	@FXML
    private GridPane grille;
    @FXML
    private Pane fond; // panneau recouvrant toute la fenêtre
	@FXML
	private Pane messa;	//Message Victoire/ Defaite
	@FXML 
	private Label mess;

    // variables globales non définies dans la vue (fichier .fxml)
    private Pane p; 
    private Label c ;
	private Grille g = new Grille();
	private int obj=OBJECTIF;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
		grille.getStyleClass().add("gridpane");
		g.nouvelleCase(fond);		//On commence par ajouter 2 cases
		g.nouvelleCase(fond);
		max.setText(Integer.toString(g.getValeurMax()));	//La tuile avec ma plus grande valeur sert de max
		objectif.setText(Integer.toString(obj));			//L'objectif est celui ecrit dans Parametres
		messa= new Pane();									//On créé le message Victoire/Defaite mais on le masque
		mess= new Label();
		mess.setVisible(false);
		messa.setVisible(false);
	}
	
    /*@FXML
    private void handleDragAction(MouseEvent event) {
        System.out.println("Glisser/déposer sur la grille avec la souris");
        double x = event.getX();//translation en abscisse
        double y = event.getY();//translation en ordonnée
        if (x > y) {
            for (int i = 0; i < grille.getChildren().size(); i++) { //pour chaque colonne
                //for (int j = 0; j < grille.getRowConstraints().size(); j++) { //pour chaque ligne
                System.out.println("ok1");
                grille.getChildren().remove(i);*/

                /*Node tuile = grille.getChildren().get(i);
                 if (tuile != null) {
                 int rowIndex = GridPane.getRowIndex(tuile);
                 int rowEnd = GridPane.getRowIndex(tuile);
                 }*/
                // }
            /*}
        } else if (x < y) {
            System.out.println("ok2");
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    Pane p = new Pane();
                    p.getStyleClass().add("pane");
                    grille.add(p, i, j);
                    p.setVisible(true);
                    grille.getStyleClass().add("gridpane");
                }
            }
        }
    }*/
	@FXML
	public void handle(MouseEvent me){ //Appuie sur bouton aide
		Grille g2= (Grille) g.clone();	//On fait une copie de la grille, c'est ici qu'on testera les deplacements
										// pour choisir le meilleur, sans modifier la vraie grille
										//On gardera le deplacement qui produit le score le + haut
		int scoreMax=g.getScore();
		int direction=0;
		boolean b2;						//Représente si le deplacement est possible
		b2=g2.lanceurDeplacerCases(GAUCHE, false); //on simule un deplacement vers la gauche
													//Le false en argument montre que l'on simule seulement
													//Il empeche la modification de l'inteface graphique
		if (b2){
			scoreMax=g2.getScore();		//On retient ce score comme scoreMax
			direction= GAUCHE;
		}
		g2= (Grille) g.clone();
		b2=g2.lanceurDeplacerCases(HAUT, false); //on simule un deplacement vers le haut
		if (b2 && g2.getScore()>=scoreMax){
			scoreMax=g2.getScore();
			direction= HAUT;
		}
		g2= (Grille) g.clone();
		b2=g2.lanceurDeplacerCases(DROITE, false); //on simule un deplacement vers la droite
		if (b2 && g2.getScore()>=scoreMax){
			scoreMax=g2.getScore();
			direction= DROITE;
		}
		g2= (Grille) g.clone();
		b2=g2.lanceurDeplacerCases(BAS, false); //on simule un deplacement vers le bas
		if (b2 && g2.getScore()>=scoreMax){
			scoreMax=g2.getScore();
			direction= BAS;
		}
		b2 = g.lanceurDeplacerCases(direction, true);	//On effectue le vrai deplacement
		max.setText(Integer.toString(g.getValeurMax()));	//On met à jour les labels
		score.setText(Integer.toString(g.getScore()));
		deplacement.setText(Integer.toString(Integer.parseInt(deplacement.getText()) + 1));
		if (g.partieFinie()) {		//Si la partie est perdue
			g.gameOver(fond);
		}
		if (mess.isVisible()){		//Permet de supprimer le message de victoire lorsque l'utilisateur continue à jouer
			mess.setVisible(false);
			messa.setVisible(false);
		}
		if (g.getValeurMax()>=obj){	//Si il a atteint l'objectif
			Object[] messVic=g.victory(fond);	//On recupere le Label et le Pane dans un tableau
			mess=(Label) messVic[0];			
			messa=(Pane) messVic[1];
			obj*=2;								//L'objectif double
			objectif.setText(Integer.toString(obj));
		}
		if (b2) {
			g.nouvelleCase(fond);			//On ajoute une nouvelle case
		}
	}

	@FXML
	public void handleall(MouseEvent me){	//Termine le jeu d'un coup
		while (!g.partieFinie()){			//Même stratégie que pour 1 coup, répetéé tant que la partie n'est pas finie
			Grille g2= (Grille) g.clone();
			int scoreMax=g.getScore();
			int direction=0;
			boolean b2;
			b2=g2.lanceurDeplacerCases(GAUCHE, false); //on simule un deplacement vers la gauche
			if (b2){
				scoreMax=g2.getScore();
				direction= GAUCHE;
			}
			g2= (Grille) g.clone();
			b2=g2.lanceurDeplacerCases(HAUT, false); //on simule un deplacement vers le haut
			if (b2 && g2.getScore()>=scoreMax){
				scoreMax=g2.getScore();
				direction= HAUT;
			}
			g2= (Grille) g.clone();
			b2=g2.lanceurDeplacerCases(DROITE, false); //on simule un deplacement vers la droite
			if (b2 && g2.getScore()>=scoreMax){
				scoreMax=g2.getScore();
				direction= DROITE;
			}
			g2= (Grille) g.clone();
			b2=g2.lanceurDeplacerCases(BAS, false); //on simule un deplacement vers le bas
			if (b2 && g2.getScore()>=scoreMax){
				scoreMax=g2.getScore();
				direction= BAS;
			}
			b2 = g.lanceurDeplacerCases(direction, true);
			max.setText(Integer.toString(g.getValeurMax()));
			score.setText(Integer.toString(g.getScore()));
			deplacement.setText(Integer.toString(Integer.parseInt(deplacement.getText()) + 1));
			if (g.partieFinie()) {
				g.gameOver(fond);
			}
			if (mess.isVisible()){
				mess.setVisible(false);
				messa.setVisible(false);
			}
			if (g.getValeurMax()>=obj){
				Object[] messVic=g.victory(fond);
				mess=(Label) messVic[0];
				messa=(Pane) messVic[1];
				obj*=2;
				objectif.setText(Integer.toString(obj));
			}
			if (b2) {
				g.nouvelleCase(fond);
			}
		}
		g.gameOver(fond);
	}
    @FXML
    public void keyPressed(KeyEvent ke) {	//Lorsque l'utilisateur appuie sur une touche
        String touche = ke.getText();
		int direction=0;
        if (touche.compareTo("q") == 0) {
			direction= GAUCHE; 
        } else if (touche.compareTo("d") == 0) { 
			direction= DROITE;
        } else if (touche.compareTo("s") == 0) {
			direction = BAS;
		} else if (touche.compareTo("z") == 0) {
			direction = HAUT;
		}
		boolean b2 = g.lanceurDeplacerCases(direction, true);		//On effectue le deplacement
		max.setText(Integer.toString(g.getValeurMax()));			//On met à jour les Labels
		score.setText(Integer.toString(g.getScore()));
		deplacement.setText(Integer.toString(Integer.parseInt(deplacement.getText()) + 1));
		if (g.partieFinie()) { //Si la partie est perdue
			g.gameOver(fond);
		}
		if (mess.isVisible()){	//Permet de supprimer le message de victoire lorsque l'utilisateur continue à jouer
			mess.setVisible(false);
			messa.setVisible(false);
		}
		if (g.getValeurMax()>=obj){
			Object[] messVic=g.victory(fond);
			mess=(Label) messVic[0];
			messa=(Pane) messVic[1];
			obj*=2;
			objectif.setText(Integer.toString(obj));
		}
		if (b2) {
			g.nouvelleCase(fond);
		}
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
}
