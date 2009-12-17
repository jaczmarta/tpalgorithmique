package gui;

import graphs.FlowCostGraph;
import graphs.OrientedValuedGraph;
import graphs.RandomGraphBuilder;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

public class GraphGeneratorGUI extends Window {
	private static final long serialVersionUID = 1L;

	JMenuBar menuBar;
		JMenu fileMenu;
			JMenuItem openMenuItem;
			JMenuItem quitMenuItem;
		
	int numVertices;
	JTextField numVerticesEntry;
	JLabel verticesLabel;

	double density;
	JTextField densityEntry;
	JLabel densityLabel;
	
	int minCost;
	JTextField minCostEntry;
	JLabel minCostLabel;

	int maxCost;
	JTextField maxCostEntry;
	JLabel maxCostLabel;
	
	int minCapacity;
	JTextField minCapacityEntry;
	JLabel minCapacityLabel;

	int maxCapacity;
	JTextField maxCapacityEntry;
	JLabel maxCapacityLabel;
		
	JButton createGraphButton;
    JPanel cutomizationPane;
	JSplitPane launchPane;
	
	public GraphGeneratorGUI() {
        super("Générateur de graphe");
        init();
        setLocation(new Point(200, 200));
		setVisible(true);
		pack();
	}
		
	public void init() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setMenuBar();
		initLaunchPane();       
	}
	
	public void initLaunchPane() {
		
		GridBagConstraints grid = new GridBagConstraints();
        getContentPane().setLayout(new GridBagLayout());        
        grid.insets = new Insets(5,5,5,5);  //top padding
               
        cutomizationPane = new JPanel();
		cutomizationPane.setLayout(new GridBagLayout());
        
        initVerticesSelection	(cutomizationPane, grid);
        initDensitySelection	(cutomizationPane, grid);
        initCostSelection		(cutomizationPane, grid);
        initCapacitySelection	(cutomizationPane, grid);
        
        {
        	createGraphButton = new JButton("Générer !");
			createGraphButton.addActionListener(this);
        }
		launchPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, cutomizationPane, createGraphButton);
        this.add(launchPane);
	}
	
	public void setMenuBar() {
		menuBar 	= new JMenuBar();
		fileMenu 	= new JMenu("Fichier");	
		
		openMenuItem = new JMenuItem("Ouvrir un graphe...");
		quitMenuItem = new JMenuItem("Quitter");

		fileMenu.add(openMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(quitMenuItem);		
						
		menuBar	.add(fileMenu);

		openMenuItem.addActionListener(this);
		quitMenuItem.addActionListener(this);

		menuBar	.setVisible(true);
		setJMenuBar(menuBar);
	}
	
	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();
		if (source == quitMenuItem) {
			System.exit(1);
		}
		else if (source == openMenuItem) {
			try {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("."));
				int openResult = chooser.showDialog(chooser, "Open");
				if (openResult == JFileChooser.APPROVE_OPTION) {
					 
					String fileName = new String(chooser.getSelectedFile().toString());
					try {
						String extension = fileName.substring(fileName.lastIndexOf('.'));
						if (extension.compareTo(".ovg") == 0) {
							OrientedValuedGraph graph = new OrientedValuedGraph(0);
							graph.deserialize(fileName);
							new SingleValuedGraphWindow(graph);
						}
						else if (extension.compareTo(".fcg") == 0) {
							FlowCostGraph graph = new FlowCostGraph(0);
							graph.deserialize(fileName);
							new FlowCostGraphWindow(graph);
						}
						else {
							new ErrorMessage("Format non reconnu.");
						}
					}
					catch (IOException erreur) {
						new ErrorMessage(erreur.getMessage());
					}
				}
			} 
			catch (Exception exception) {
				System.out.println(exception.getMessage());
			}
		}
		else if (source == createGraphButton) {
	        
			RandomGraphBuilder builder = new RandomGraphBuilder();
			try {
				numVertices 	= Integer.parseInt(numVerticesEntry.getText());
				density			= Double.parseDouble(densityEntry.getText());
				minCost 		= Integer.parseInt(minCostEntry.getText());
				maxCost 		= Integer.parseInt(maxCostEntry.getText());
				minCapacity 	= Integer.parseInt(minCapacityEntry.getText());
				maxCapacity 	= Integer.parseInt(maxCapacityEntry.getText());
				
		        boolean checkDensity = (density <= 1) && (density > 0);
		        boolean checkCapacity = minCapacity <= maxCapacity;
		        boolean checkCost = minCost <= maxCost;
		        
		        if (!checkDensity) {
	        		new ErrorMessage("Veuillez entrer une densité comprise entre 0 et 1.");
	        	}
	        	else if (!checkCapacity) {
	        		new ErrorMessage("Veuillez vérifier les capacités.");
	        	}
	        	else if (!checkCost) {
	        		new ErrorMessage("Veuillez vérifier les coûts.");
	        	}
		        else {
		            builder.setNumVertices(numVertices);
		            builder.setDensity(density);
		            builder.setCapacityLowerBound(minCapacity);
		            builder.setCapacityUpperBound(maxCapacity);
			        builder.setCostLowerBound(minCost);
			        builder.setCostUpperBound(maxCost);

	                FlowCostGraph G = builder.generateRandomFlowGraph();
		        	new FlowCostGraphWindow(G);
		        }
                
			}
            catch (NumberFormatException exception) {
				new ErrorMessage("Tous les champs n'ont pas été remplis correctement.");
            }
            catch (Exception e) {
                new ErrorMessage(e);
            }
		}
    }
	
	public void initCapacitySelection(JPanel cutomizationPane, GridBagConstraints grid) {
		{
        	minCapacityLabel = new JLabel();    
        	minCapacityLabel.setText("Capacité mimum:");
			grid.gridx = 0;
			grid.gridy = 6;
			cutomizationPane.add(minCapacityLabel, grid);
	
			minCapacityEntry = new JTextField("", 5);    
			grid.gridx = 1;
			grid.gridy = 6;
			cutomizationPane.add(minCapacityEntry, grid);
        }
        
        {
        	maxCapacityLabel = new JLabel();    
        	maxCapacityLabel.setText("Capacité maximum:");
			grid.gridx = 0;
			grid.gridy = 7;
			cutomizationPane.add(maxCapacityLabel, grid);
	
			maxCapacityEntry = new JTextField("", 5);   
			grid.gridx = 1;
			grid.gridy = 7;
			grid.insets = new Insets(5,5,5,5);
			cutomizationPane.add(maxCapacityEntry, grid);
        }
	}
	
	
	public void initCostSelection(JPanel cutomizationPane, GridBagConstraints grid) {
		{
        	minCostLabel = new JLabel();    
        	minCostLabel.setText("Cout minimum:");
			grid.gridx = 0;
			grid.gridy = 4;
			cutomizationPane.add(minCostLabel, grid);
	
			minCostEntry = new JTextField("", 5);     
			grid.gridx = 1;
			grid.gridy = 4;
			cutomizationPane.add(minCostEntry, grid);
        }
        
        {
        	maxCostLabel = new JLabel();    
        	maxCostLabel.setText("Cout maximum:");
			grid.gridx = 0;
			grid.gridy = 5;
			cutomizationPane.add(maxCostLabel, grid);
	
			maxCostEntry = new JTextField("", 5);     
			grid.gridx = 1;
			grid.gridy = 5;
			grid.insets = new Insets(5,5,5,5);
			cutomizationPane.add(maxCostEntry, grid);
        }   
		
	}
	
	public void initVerticesSelection(JPanel cutomizationPane, GridBagConstraints grid) {
        verticesLabel = new JLabel();    
        verticesLabel.setText("Nombre de sommets:");
		grid.gridx = 0;
		grid.gridy = 0;
		cutomizationPane.add(verticesLabel, grid);

		numVerticesEntry = new JTextField("", 5);     
		grid.gridx = 1;
		grid.gridy = 0;
		cutomizationPane.add(numVerticesEntry, grid);      
    }
    
	public void initDensitySelection(JPanel cutomizationPane, GridBagConstraints grid) {
    	densityLabel = new JLabel();    
    	densityLabel.setText("Densité:");
		grid.gridx = 0;
		grid.gridy = 1;
		cutomizationPane.add(densityLabel, grid);

		densityEntry = new JTextField("", 5);     
		grid.gridx = 1;
		grid.gridy = 1;
		cutomizationPane.add(densityEntry, grid);
    }
}
