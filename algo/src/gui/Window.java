package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Window extends JFrame implements ActionListener {

	/**
	 * Classe principale de l'interface graphique
	 * 
	 * @see JFrame
	 * @see GraphPanel
	 * @see Popup
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Container container;
	private GraphPanel graphPanel;
	
	JMenuBar menuBar;
		JMenu fileMenu;
			JMenu newMenu;
				JMenuItem newGraphMenuItem;
			JMenuItem quitMenuItem;
		JMenu floydMenu;
			JMenuItem applyFloydMenuItem;
			JMenuItem applyFloydModifMenuItem;
		JMenu graphMenu;
			JMenuItem generateGraphMenuItem;
			
	JPanel panneauBouton;
	JPanel panneauImage;
	
	/**
	 * Constructeur unique de la classe: appel des m&eacute;thodes d'initialisation:
	 * super();
     * build();
	 * pack();	
	 */
	public Window() throws IOException {
        super();
        build();
		pack();	
	}
	
	
	/**
	 * Initialisation de la fen&ecirc;tre: d&eacute;finition du layout (BorderLayout), du titre, etc... Appel des m&eacute;thodes de cr&eacute;ation du fond (image) et des menus.
	 * @throws IOException
	 */
	private void build() throws IOException{
		
		setLayout(new BorderLayout());
		setTitle("Creation de graphes");
		setLocation(new Point(100, 100));		
		setResizable(true);
		setDefaultLookAndFeelDecorated(true); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.BLACK);
		setButtonAndJMenu();
		createGraph();
        setVisible(true);
	}
	
	/**
	 * M&eacute;thode de cr&eacute;ation du fond de la fen&ecirc;tre
	 * @throws IOException
	 */
	public void createGraph() throws IOException {
		container = getContentPane();
				
		graphPanel = new GraphPanel();	
		container.add(graphPanel.getGraph());

		setContentPane(container);
	}
	
	/**
	 * M&eacute;thode de cr&eacute;tion des menus
	 * @throws IOException
	 */
	public void setButtonAndJMenu() throws IOException {		
		menuBar = new JMenuBar();

		fileMenu 	= new JMenu("Fichier");		
		newMenu 	= new JMenu("Nouveau");
		floydMenu 	= new JMenu("Floyd-Warshall");
		graphMenu	= new JMenu("Graphe");
		
		newGraphMenuItem		= new JMenuItem("Graphe");
		quitMenuItem			= new JMenuItem("Quitter");
		applyFloydMenuItem		= new JMenuItem("Appliquer Floyd-Warshall");
		applyFloydModifMenuItem	= new JMenuItem("Appliquer Floyd-Warshall modifié");
		generateGraphMenuItem	= new JMenuItem("Générer un graphe");
		
		
		fileMenu.add(newMenu);
		fileMenu.addSeparator();
		fileMenu.add(quitMenuItem);		
		
		newMenu	.add(newGraphMenuItem);
		
		floydMenu.add(applyFloydMenuItem);
		floydMenu.add(applyFloydModifMenuItem);
		
		graphMenu.add(generateGraphMenuItem);
		
		menuBar	.add(fileMenu);
		menuBar	.add(floydMenu);
		menuBar	.add(graphMenu);
		menuBar	.setVisible(true);

		newGraphMenuItem		.addActionListener(this);
		quitMenuItem			.addActionListener(this);
		applyFloydMenuItem		.addActionListener(this);
		applyFloydModifMenuItem	.addActionListener(this);
		generateGraphMenuItem	.addActionListener(this);
		
		setJMenuBar(menuBar);
		
		
	}
	
	/**
	 * Ecouteurs d'e&acute;v&eacute;nements.
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == newGraphMenuItem){
			try {

		        graphPanel.createGraph(this);
		        
			} 
			catch (Exception exception) {
				System.out.println(exception.getMessage());
			}
		
		} 
		else if (source == quitMenuItem) {
			System.exit(1);
		}
	}
		
}


