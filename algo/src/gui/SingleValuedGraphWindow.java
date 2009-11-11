package gui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import graphs.OrientedValuedGraph;

public class SingleValuedGraphWindow extends AbstractGraphWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected OrientedValuedGraph graph;
	
	public SingleValuedGraphWindow(OrientedValuedGraph g) {  
		super(g);
		graph = new OrientedValuedGraph(g);
	}
	
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == saveMenuItem) {
			try {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("."));
				int saveResult = chooser.showDialog(chooser, "Enregistrer");
				if (saveResult == JFileChooser.APPROVE_OPTION) {
					 
					String fileName = new String(chooser.getSelectedFile().toString());
					try {
					    graph.serialize(fileName+".ovg");
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
						new SingleValuedGraphWindow(graph);
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
