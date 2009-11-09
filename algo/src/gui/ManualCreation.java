package gui;

import graphs.RandomGraphBuilder;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ManualCreation extends Window {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JButton okButton;
	
	int numVertices = 10;
	JTextField numVerticesEntry;
	JLabel verticesLabel;
	
	int numEdges;

	double density = 0.5;
	JTextField densityEntry;
	JLabel densityLabel;

	int minFlow = 0;
	JTextField minFlowEntry;
	JLabel minFlowLabel;

	int maxFlow = 5;
	JTextField maxFlowEntry;
	JLabel maxFlowLabel;
	
	int minCost = 1;
	JTextField minCostEntry;
	JLabel minCostLabel;

	int maxCost = 5;
	JTextField maxCostEntry;
	JLabel maxCostLabel;
	
	int minCapacity = 1;
	JTextField minCapacityEntry;
	JLabel minCapacityLabel;

	int maxCapacity = 5;
	JTextField maxCapacityEntry;
	JLabel maxCapacityLabel;
	 
	public ManualCreation() {
		super("Création manuelle");
		init();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();	
        setVisible(true);
	}	
	
	public void init() {
        GridBagConstraints grid = new GridBagConstraints();
        getContentPane().setLayout(new GridBagLayout());        
        grid.insets = new Insets(5,5,5,5);  //top padding
        
        {
	        verticesLabel = new JLabel();    
	        verticesLabel.setText("Nombre de sommets:");
			grid.gridx = 0;
			grid.gridy = 0;
			getContentPane().add(verticesLabel, grid);
	
			numVerticesEntry = new JTextField("", 5);     
			grid.gridx = 1;
			grid.gridy = 0;
			getContentPane().add(numVerticesEntry, grid);      
        }
        
        {
        	densityLabel = new JLabel();    
        	densityLabel.setText("Densité:");
			grid.gridx = 0;
			grid.gridy = 1;
			getContentPane().add(densityLabel, grid);
	
			densityEntry = new JTextField("", 5);     
			grid.gridx = 1;
			grid.gridy = 1;
			getContentPane().add(densityEntry, grid);
        }
        
        {
        	minFlowLabel = new JLabel();    
        	minFlowLabel.setText("Flot minimum:");
			grid.gridx = 0;
			grid.gridy = 2;
			grid.insets = new Insets(5,5,5,5);
			getContentPane().add(minFlowLabel, grid);
	
			minFlowEntry = new JTextField("", 5);     
			grid.gridx = 1;
			grid.gridy = 2;
			getContentPane().add(minFlowEntry, grid);
        }
        
        {
        	maxFlowLabel = new JLabel();    
        	maxFlowLabel.setText("Flot maximum:");
			grid.gridx = 0;
			grid.gridy = 3;
			getContentPane().add(maxFlowLabel, grid);
	
			maxFlowEntry = new JTextField("", 5);
			grid.gridx = 1;
			grid.gridy = 3;
			getContentPane().add(maxFlowEntry, grid);
        }
        
        {
        	minCostLabel = new JLabel();    
        	minCostLabel.setText("Cout minimum:");
			grid.gridx = 0;
			grid.gridy = 4;
			getContentPane().add(minCostLabel, grid);
	
			minCostEntry = new JTextField("", 5);     
			grid.gridx = 1;
			grid.gridy = 4;
			getContentPane().add(minCostEntry, grid);
        }
        
        {
        	maxCostLabel = new JLabel();    
        	maxCostLabel.setText("Cout maximum:");
			grid.gridx = 0;
			grid.gridy = 5;
			getContentPane().add(maxCostLabel, grid);
	
			maxCostEntry = new JTextField("", 5);     
			grid.gridx = 1;
			grid.gridy = 5;
			grid.insets = new Insets(5,5,5,5);
			getContentPane().add(maxCostEntry, grid);
        }
        
        {
        	minCapacityLabel = new JLabel();    
        	minCapacityLabel.setText("Capacité mimum:");
			grid.gridx = 0;
			grid.gridy = 6;
			getContentPane().add(minCapacityLabel, grid);
	
			minCapacityEntry = new JTextField("", 5);    
			grid.gridx = 1;
			grid.gridy = 6;
			getContentPane().add(minCapacityEntry, grid);
        }
        
        {
        	maxCapacityLabel = new JLabel();    
        	maxCapacityLabel.setText("Capacité maximum:");
			grid.gridx = 0;
			grid.gridy = 7;
			getContentPane().add(maxCapacityLabel, grid);
	
			maxCapacityEntry = new JTextField("", 5);   
			grid.gridx = 1;
			grid.gridy = 7;
			grid.insets = new Insets(5,5,5,5);
			getContentPane().add(maxCapacityEntry, grid);
        }
        
        {
        	okButton = new JButton("OK");
			grid.gridx = 3; 
			grid.gridy = 8; 
			getContentPane().add(okButton, grid);
			okButton.addActionListener(this);
        	
        }
        
	}
	
	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();
		if (source == okButton) {
			try {
				numVertices = Integer.parseInt(numVerticesEntry.getText(),10);
				density = Double.parseDouble(densityEntry.getText());
				minFlow = Integer.parseInt(minFlowEntry.getText());
				maxFlow = Integer.parseInt(maxFlowEntry.getText());
				minCost = Integer.parseInt(minCostEntry.getText());
				maxCost = Integer.parseInt(maxCostEntry.getText());
				minCapacity = Integer.parseInt(minCapacityEntry.getText());
				maxCapacity = Integer.parseInt(maxCapacityEntry.getText());
				
				RandomGraphBuilder builder = new RandomGraphBuilder();
	            builder.setNumVertices(numVertices);
	            builder.setDensity(density);
	            builder.setCapacityLowerBound(minCapacity);
	            builder.setCapacityUpperBound(maxCapacity);
	            builder.setCostBound(maxCost);
	            
	            new FlowCostGraphGUI(builder.generateRandomFlowGraph());
			}
			catch (NumberFormatException exception) {
				int n = JOptionPane.showConfirmDialog(this,
					       "Tous les champs n'ont pas été remplis correctement.\n" +
					       "Poursuivre avec les valeurs manquantes par défaut ?",
					       "Warning", 
					        JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					
					RandomGraphBuilder builder = new RandomGraphBuilder();
			        builder.setNumVertices(numVertices);
			        builder.setCapacityLowerBound(minCapacity);
			        builder.setCapacityUpperBound(maxCapacity);
			        builder.setCostBound(maxCost);

					try {
			        	builder.setDensity(Double.parseDouble(densityEntry.getText()));
			        }
					catch (IllegalArgumentException e) {
						int m = JOptionPane.showConfirmDialog(this,
							       "La densité doit être comprise entre 0 et 1.\n" +
							       "Poursuivre avec la densité par défaut (0.5) ?",
							       "Warning", 
							        JOptionPane.YES_NO_OPTION);
						if (m == JOptionPane.NO_OPTION) {
							return;
						}
					}
			        try {
			          	new FlowCostGraphGUI(builder.generateRandomFlowGraph());
			        }			        
			        catch (Exception e) {
						new ErrorMessage(e);
			        }
				  }

			}
			catch (Exception e) {
				Object[] options = {"OK", "Details"};
				int n = JOptionPane.showOptionDialog(this,
				    "Erreur inattendue:\n"
				    + e.getMessage(),
				    "Erreur",
				    JOptionPane.YES_NO_CANCEL_OPTION,
				    JOptionPane.QUESTION_MESSAGE,
				    null,
				    options,
				    options[1]);

				if (n == JOptionPane.NO_OPTION) {
					new ErrorMessage(e);
				}
			}
		}
    }
	
}
