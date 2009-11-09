package gui;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import graphs.FlowCostGraph;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

import values.IValues;

public class FlowCostGraphGUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Graph<Integer, String> graph;
	
	public FlowCostGraphGUI() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public FlowCostGraphGUI(FlowCostGraph g)  throws IOException {
		graph =new DirectedSparseMultigraph<Integer, String>();
    	try {
    		IValues[][] edges = g.getValues();
	    	for (int i = 0; i < edges.length; i++) {
	    		graph.addVertex(i);
	    		for (int j = 0; j < edges.length; j++) {
	    			if (!edges[i][j].equals(g.noValue())) {
	    				graph.addEdge(i+" "+j+" "+edges[i][j].toString(), i, j);
	    			}
	    		}
	    	}
	    	Layout<Integer, String> layout = new CircleLayout<Integer, String>(graph);
		    layout.setSize(new Dimension(500,500));
		    VisualizationViewer<Integer,String> vv = new VisualizationViewer<Integer,String>(layout);
		    vv.setPreferredSize(new Dimension(640,640));
		    
		    vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<Integer>());
		    vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<String>());

		    DefaultModalGraphMouse<Integer, String> mouse = new DefaultModalGraphMouse<Integer, String>();
		    mouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
		    vv.setGraphMouse(mouse); 
		    
		    Window window = new Window("Graphe orienté valué (Flot - Cout - Capacite)");
		    window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    window.getContentPane().add(vv);
		    window.pack();
		    window.setVisible(true); 
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
	
}
