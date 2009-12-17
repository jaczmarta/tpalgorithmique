package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import values.IValues;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import graphs.AbstractGraph;

public abstract class AbstractGraphWindow extends Window {

	private static final long serialVersionUID = 1L;
	protected Graph<Integer, String> graphToBeDrawn;
	protected AbstractGraph graph;
	
	JMenuBar menuBar;
		JMenu fileMenu;
			JMenuItem saveMenuItem;
			JMenuItem openMenuItem;
			JMenuItem quitMenuItem;
			
	public AbstractGraphWindow() {
		graphToBeDrawn = new DirectedSparseMultigraph<Integer, String>();
	}
	
	public AbstractGraphWindow (AbstractGraph g){
		super("Graphe orienté valué (Flot - Cout - Capacite)");
		graphToBeDrawn = new DirectedSparseMultigraph<Integer, String>();
		try {
    		IValues[][] edges = g.getValues();
	    	for (int i = 0; i < edges.length; i++) {
	    		graphToBeDrawn.addVertex(i);
	    		for (int j = 0; j < edges.length; j++) {
	    			if (!edges[i][j].equals(g.noValue())) {
	    				graphToBeDrawn.addEdge("("+i+", "+j+") "+edges[i][j].toString(), i, j);
	    			}
	    		}
	    	}
	    	
    	}
    	catch (Exception e) {
			new ErrorMessage(e);
    	}
    	showGraph();
	}
	
	public AbstractGraphWindow(String name) {
		super(name);
	    build();
	}

	public void showGraph() {
		Layout<Integer, String> layout = new CircleLayout<Integer, String>(graphToBeDrawn);
	    layout.setSize(new Dimension(500,500));
	    
	    VisualizationViewer<Integer,String> vv = new VisualizationViewer<Integer,String>(layout);
	    vv.setPreferredSize(new Dimension(860,860));
	    vv.setBackground(Color.WHITE);
	    
	    vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<Integer>());
	    vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<String>());

	    DefaultModalGraphMouse<Integer, String> mouse = new DefaultModalGraphMouse<Integer, String>();
	    mouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
	    vv.setGraphMouse(mouse); 

	    getContentPane().add(vv);
	    build();
	}
	
	public void build() {
		super.build();

		setSize(new Dimension(500,500));
		setResizable(true);
		setDefaultLookAndFeelDecorated(true); 
		
		setMenus(getContentPane());
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    pack();
	    setVisible(true); 
	}
	
	public void setMenus(Container pane) {		
		menuBar = new JMenuBar();

		fileMenu = new JMenu("Fichier");
		
		saveMenuItem = new JMenuItem("Enregistrer sous...");
		openMenuItem = new JMenuItem("Ouvrir un graphe");
		quitMenuItem = new JMenuItem("Quitter");

		fileMenu.add(saveMenuItem);
		fileMenu.add(openMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(quitMenuItem);		

		
		menuBar	.add(fileMenu);

		saveMenuItem.addActionListener(this);
		openMenuItem.addActionListener(this);
		quitMenuItem.addActionListener(this);

		menuBar	.setVisible(true);
		setJMenuBar(menuBar);
	}
	
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == quitMenuItem) {
			System.exit(1);
		}
	}
	
}
