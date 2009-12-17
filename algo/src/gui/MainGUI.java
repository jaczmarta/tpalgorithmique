package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainGUI extends Window {
	
	private static final long serialVersionUID = 1L;

	JMenuBar menuBar;
		JMenu fileMenu;
			JMenuItem quitMenuItem;
	
	JButton generateGraphButton;
	JButton testTheMethodsButton;
    
	public MainGUI() {
        super("Graphes & co");
        init();
		setVisible(true);
		pack();
	}
	
	
	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMenuBar();
		
        GridBagConstraints grid = new GridBagConstraints();
        getContentPane().setLayout(new GridBagLayout());        
        grid.insets = new Insets(5,5,5,5);  //top padding
               
        
        {
        	generateGraphButton = new JButton("Générer un graphe");
			grid.gridx = 0; 
			grid.gridy = 0; 
			getContentPane().add(generateGraphButton, grid);
			generateGraphButton.addActionListener(this);
        }
        
        {
        	testTheMethodsButton = new JButton("Comparer les méthodes");
			grid.gridx = 0; 
			grid.gridy = 1; 
			getContentPane().add(testTheMethodsButton, grid);
			testTheMethodsButton.addActionListener(this);
        }
        
	}
	
	public void setMenuBar() {
		menuBar 	= new JMenuBar();
		fileMenu 	= new JMenu("Fichier");	
		
		quitMenuItem = new JMenuItem("Quitter");

		fileMenu.add(quitMenuItem);								
		menuBar	.add(fileMenu);

		quitMenuItem.addActionListener(this);

		menuBar	.setVisible(true);
		setJMenuBar(menuBar);
	}
	
	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();
		if (source == quitMenuItem) {
			System.exit(1);
		}
		else if (source == generateGraphButton) {
	        new GraphGeneratorGUI();
		}
		else if (source == testTheMethodsButton) {
	        new TestGeneratorGUI();
		}
    }
}


