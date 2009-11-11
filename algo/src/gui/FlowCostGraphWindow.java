package gui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import values.FlowCostValues;
import values.IValues;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;

import algo.BusackerGowen;

import graphs.FlowCostGraph;

public class FlowCostGraphWindow extends AbstractGraphWindow {

	private static final long serialVersionUID = 1L;
	private FlowCostGraph graph;
	JMenu orientedValuedGraphMenu;
		JMenuItem flowGraphMenuItem;
		JMenuItem capacityDistanceGraph;
		JMenuItem costDistanceGraph;
	
	/**
	 * @param g
	 */
	public FlowCostGraphWindow(FlowCostGraph g) {
		super(g);
		graph = new FlowCostGraph(g);
    }
	
	/**
	 * affichage du graphe issu de l'algo Busacker/Gowen
	 * @param g
	 * @param b
	 */
	public FlowCostGraphWindow(FlowCostGraph g, boolean b) {
		super("Busacker & Gowen");
		graph = new FlowCostGraph(g);
		graphToBeDrawn = new DirectedSparseMultigraph<Integer, String>();
		try {
    		IValues[][] edges = g.getValues();
	    	for (int i = 0; i < edges.length; i++) {
	    		graphToBeDrawn.addVertex(i);
	    		for (int j = 0; j < edges.length; j++) {
	    			if (!edges[i][j].equals(g.noValue())) {
	    				FlowCostValues f = (FlowCostValues)edges[i][j];
	    				if (f.getFlow() != 0) {
	    					graphToBeDrawn.addEdge("("+i+", "+j+") "+edges[i][j].toString(), i, j);
	    				}
	    			}
	    		}
	    	}
    		showGraph();
    	}
    	catch (Exception e) {
			new ErrorMessage(e);
    	}
    }
	
	public void setMenus(Container pane) {
		super.setMenus(pane);
		
		orientedValuedGraphMenu = new JMenu("Generer le graphe de...");
		flowGraphMenuItem 		= new JMenuItem("Flots");
		capacityDistanceGraph 	= new JMenuItem("Capacites");
		costDistanceGraph 		= new JMenuItem("Couts");
		
		orientedValuedGraphMenu.add(flowGraphMenuItem);
		orientedValuedGraphMenu.add(capacityDistanceGraph);
		orientedValuedGraphMenu.add(costDistanceGraph);
		
		graphMenu.add(orientedValuedGraphMenu);
		
		flowGraphMenuItem		.addActionListener(this);
		capacityDistanceGraph	.addActionListener(this);
		costDistanceGraph		.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent event) {
		super.actionPerformed(event);
		
		Object source = event.getSource();
		if (source == generateDistanceGraph) {
			new FlowCostGraphWindow(graph.getFullResultingNetwork());
		}
		else if (source == flowGraphMenuItem) {
			new SingleValuedGraphWindow(graph.getSubGraphBy(0));
		}
		else if (source == capacityDistanceGraph) {
			new SingleValuedGraphWindow(graph.getSubGraphBy(1));
		}
		else if (source == costDistanceGraph) {
			new SingleValuedGraphWindow(graph.getSubGraphBy(2));
		}
		else if (source == busackerGowenMenuItem) {
			BusackerGowen bg = new BusackerGowen(graph);
			bg.runAlgorithm();
			new FlowCostGraphWindow(bg.getG(), true);
			JOptionPane.showMessageDialog(this,
				    "Resume:\n" +
				    "Source: " + bg.getG().indexOfSource() + "\n" +
				    "Puit: " + bg.getG().indexOfSink() + "\n" +
				    "Flot: " + bg.getG().getGraphFlow() + "\n" +
				    "Cout: " + bg.getG().getGraphCost(),
				    "Informations",
				    JOptionPane.INFORMATION_MESSAGE);
		}
		else if (source == saveMenuItem) {
			try {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("."));
				int saveResult = chooser.showDialog(chooser, "Enregistrer");
				if (saveResult == JFileChooser.APPROVE_OPTION) {
					 
					String fileName = new String(chooser.getSelectedFile().toString());
					try {
					    graph.serialize(fileName+".fcg");
					}
					catch (IOException erreur) {
						System.out.println(erreur.getMessage());
					}
				}
			} 
			catch (Exception exception) {
				System.out.println(exception.getMessage());
			}
		}
		else if (source == openMenuItem) {
			try {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("."));
				int openResult = chooser.showDialog(chooser, "Ouvrir");
				if (openResult == JFileChooser.APPROVE_OPTION) {
					 
					String fileName = new String(chooser.getSelectedFile().toString());
					try {
						graph.deserialize(fileName);
						new FlowCostGraphWindow(graph);
					}
					catch (IOException erreur) {
						System.out.println(erreur.getMessage());
					}
				}
			} 
			catch (Exception exception) {
				System.out.println(exception.getMessage());
			}
		}
	}
}