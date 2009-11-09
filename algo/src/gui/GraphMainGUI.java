package gui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import graphs.AbstractGraph;

public class GraphMainGUI extends Window implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private Hashtable<String, AbstractGraph> graphList;
	
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
	
	JButton createGraphButton;
	JButton floydButton;
	JButton floydModifButton;		
	JButton generateGraphButton; 
		
	public GraphMainGUI() {
        super("Générateur de graphe");
        graphList = new Hashtable<String, AbstractGraph>();
		setButtons(getContentPane());
		setMenus(getContentPane());
		pack();	
        setVisible(true);
	}
	
	public void setMenus(Container pane) {		
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
	
	public void setButtons(Container pane) {
		GridBagConstraints grid = new GridBagConstraints();
		pane.setLayout(new GridBagLayout());
		
		createGraphButton = new JButton("Créer un graphe");
		grid.fill = GridBagConstraints.HORIZONTAL;
		grid.gridx = 0;       //aligned with button 2
		grid.gridy = 0;       //third row
		grid.insets = new Insets(5,5,5,5);  //top padding
		createGraphButton.addActionListener(this);
		pane.add(createGraphButton, grid);
		
		floydButton 		= new JButton("Appliquer Floyd");
		grid.fill = GridBagConstraints.HORIZONTAL;
		grid.gridx = 1;       //aligned with button 2
		grid.gridy = 0;       //third row
		grid.insets = new Insets(5,5,5,5);  //top padding
		floydButton.addActionListener(this);
		pane.add(floydButton, grid);
		
		floydModifButton 	= new JButton("Appliquer Floyd modifié");
		grid.fill = GridBagConstraints.HORIZONTAL;
		grid.gridx = 1;       //aligned with button 2
		grid.gridy = 1;       //third row
		grid.insets = new Insets(5,5,5,5);  //top padding
		floydModifButton.addActionListener(this);
		pane.add(floydModifButton, grid);
		
		generateGraphButton = new JButton("Générer un graphe");
		grid.fill = GridBagConstraints.HORIZONTAL;
		grid.gridx = 2;
		grid.gridy = 0;       //third row
		grid.insets = new Insets(5,5,5,5);  //top padding
		generateGraphButton	.addActionListener(this);
		pane.add(generateGraphButton, grid);
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == newGraphMenuItem){
			try {
				build();
			} 
			catch (Exception exception) {
				System.out.println(exception.getMessage());
			}
		
		}
		else if (source == createGraphButton) {
			new ManualCreation();
		}
		else if (source == applyFloydMenuItem) {
			try {
	            
			} 
			catch (Exception exception) {
				System.out.println(exception.getMessage());
			}
		}
		
		else if (source == generateGraphMenuItem) {
			try {
	            
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


