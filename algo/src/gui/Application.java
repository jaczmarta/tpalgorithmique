package gui;

import java.io.IOException;




/**
 * Classe principale de l'application: ouverture de l'interface graphique
 * 
 * @see Fenetre
 *
 */
public class Application {
	
	public static void main(String[] args) throws IOException {
		GraphMainGUI gui = new GraphMainGUI();
		gui.setVisible(true);
	}

    
}

