package gui;

import java.io.IOException;

import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import values.IValues;

import graphs.OrientedValuedGraph;

public class OrientedValuedGUI  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Graph<Integer, String> graph;
	
	public OrientedValuedGUI() throws IOException {
		graph =new DirectedSparseMultigraph<Integer, String>();
		OrientedValuedGraph G = new OrientedValuedGraph(5);
        G.setValue(1, 3, 3);
        G.setValue(2, 1, -1);
        G.setValue(2, 4, -7);
        G.setValue(3, 0, 1);
        G.setValue(3, 2, 8);
        G.setValue(3, 4, 1);
        G.setValue(4, 0, 2);
        
        try {
    		IValues[][] edges = G.getValues();
	    	for (int i = 0; i < edges.length; i++) {
	    		graph.addVertex(i);
	    		for (int j = 0; j < edges.length; j++) {
	    			if (!edges[i][j].equals(G.noValue())) {
	    				graph.addEdge(i+" "+j+" "+edges[i][j].toString(), i, j);
	    			}
	    		}
	    	}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
	}
	
}
