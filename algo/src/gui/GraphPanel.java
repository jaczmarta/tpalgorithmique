package gui;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphModel;

import algo.OrientedIntValuedGraph;

/**
 * Classe du panneau graphique
 * 
 * @see JScrollPane
 *
 * @author tolivieri
 */
public class GraphPanel extends JScrollPane {

	
	private static final long serialVersionUID = -2578790090805272257L;
	private JGraph graph;
	private GraphModel model;
	private ArrayList<DefaultGraphCell> cells;
	
	public GraphPanel() throws IOException {
		super();
		cells = new ArrayList<DefaultGraphCell>();
		
		// Construct Model and Graph
		model = new DefaultGraphModel();
		graph = new JGraph(model);
	
		// Control-drag should clone selection
		graph.setCloneable(false);
	
		// Enable edit without final RETURN keystroke
		graph.setInvokesStopCellEditing(true);
	
		// When over a cell, jump to its default port (we only have one, anyway)
		graph.setJumpToDefaultPort(true);
		
	}
	
	public void createGraph(Window frame) {
		Integer N = OrientedIntValuedGraph.noValue();
		int[][] mat = {
                /*          a   b   c   d   e  */
                /* a */{N, N, N, N, N},
                /* b */ {N, N, N, 3, N},
                /* c */ {N, -1, N, N, -7},
                /* d */ {1, N, 8, N, 1},
                /* e */ {2, N, N, N, N},};


        //on créé le graphe
        double x = 10;
        double y = 10;
        double width = 40;
        double height = 20;
        if (cells.size() > 0) {
            DefaultGraphCell[] cellsToRemove = new DefaultGraphCell[cells.size()];
            cells.toArray(cellsToRemove);
            model.remove(cellsToRemove);
            cells.clear();
        }
        
        for (int i = 0; i < mat.length; i++) {
        	for (int j = 0; j < mat.length; j++) {
        		if (mat[i][j] != N) {
        			DefaultGraphCell source = createVertex(Integer.toString(i), x, y, width, height);

        			x += 70;
	        		y += 70;
        			DefaultGraphCell target = createVertex(Integer.toString(j), x, y, width, height);
        			if (!cells.contains(source)) {
        				cells.add(source);
        			}
        			if (!cells.contains(target)) {
        				cells.add(target);
        			}

                    DefaultGraphCell currentLink = createEdge(source, target);
                    cells.add(currentLink);
    				
        			x += 20;
	        		y += 20;

	                graph.getGraphLayoutCache().insert(cells.toArray());
					frame.getContentPane().add(getGraph());
					frame.setContentPane(frame.getContentPane());
					frame.pack();
        		}
        	}
		}
	}
	
	private  DefaultGraphCell createVertex(String name, double x, double y, double width, double height) {
        DefaultGraphCell cell = new DefaultGraphCell(name);
        GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(x, y, width, height));
        GraphConstants.setBorder(cell.getAttributes(), BorderFactory.createRaisedBevelBorder());
        GraphConstants.setOpaque(cell.getAttributes(), true);
        GraphConstants.setGradientColor(cell.getAttributes(), Color.LIGHT_GRAY);
        cell.addPort(new Point2D.Double(0, 0));
        return cell;
    }

    private DefaultGraphCell createEdge(DefaultGraphCell source, DefaultGraphCell target) {
        DefaultEdge edge = new DefaultEdge();
        source.addPort();
        edge.setSource(source.getChildAt(source.getChildCount() -1));
        target.addPort();
        edge.setTarget(target.getChildAt(target.getChildCount() -1));
        GraphConstants.setLabelAlongEdge(edge.getAttributes(), true);
        GraphConstants.setLineEnd(edge.getAttributes(), GraphConstants.ARROW_CLASSIC);
        return edge;
    }
	/*
	public void addEdge(Vertex source, Vertex target) {
		if (!components.contains(source)) {
			addVertex(source);
		}
		if (!components.contains(target)) {
			addVertex(target);
		}
		
		DefaultGraphCell[] tmp = cells;
		cells = new DefaultGraphCell[tmp.length + 1];
		for (int i = 0; i < cells.length - 1; i++) {
			cells[i] = tmp[i];
		}
		// Create Edge
		DefaultEdge edge = new DefaultEdge();
		
		// Fetch the ports from the new vertices, and connect them with the edge
		edge.setSource(source.getChildAt(source.getChildCount() - 1));
		edge.setTarget(target.getChildAt(target.getChildCount() - 1));
		cells[cells.length - 1] = edge;	
	
		// Set Arrow Style for edge
		int arrow = GraphConstants.ARROW_CLASSIC;
		GraphConstants.setLineEnd(edge.getAttributes(), arrow);
		GraphConstants.setEndFill(edge.getAttributes(), true);
		
		graph.getGraphLayoutCache().insert(cells);
	}
	
	public static Vertex createVertex(String label, double x, double y, double w, double h, Color bg, boolean raised) {

		// Create vertex with the given name
		Vertex cell = new Vertex(label);
		
		// Set bounds
		GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(x, y, w, h));
		
		// Set fill color
		if (bg != null) {
			GraphConstants.setGradientColor(cell.getAttributes(), Color.orange);
			GraphConstants.setOpaque(cell.getAttributes(), true);
		}
	
		// Set raised border
		if (raised)
			GraphConstants.setBorder(cell.getAttributes(), BorderFactory.createRaisedBevelBorder());
		else
			// Set black border
			GraphConstants.setBorderColor(cell.getAttributes(), Color.black);
	
		// Add a Port
		DefaultPort port = new DefaultPort();
		cell.add(port);
		port.setParent(cell);

		return cell;
	}
	*/
	
	
	public JGraph getGraph() {
		return graph;
	}
	/*
	public void createCell() {
        // Création d’une cellule
        DefaultGraphCell myCell = new DefaultGraphCell();
        
        // Création d’une HashTable pour contenir les attributs.
        Map myHashTable = new Hashtable();
        
        // Même chose en passant par le modifieur
        GraphConstants.setBounds(myHashTable, new Rectangle2D.Double(120, 25, 25, 25));
        
        // Associer les nouveaux attributs (pour l’instant un seul) à la cellule
        myCell.setAttributes(new AttributeMap(myHashTable));


	}
	*/
	/*
	 // MouseListener that Prints the Cell on Doubleclick
	  graph.addMouseListener(new MouseAdapter() {
	    public void mousePressed(MouseEvent e) {
	      if (e.getClickCount() == 2) {
	        // Get Cell under Mousepointer
	        int x = e.getX(), y = e.getY();
	        Object cell = graph.getFirstCellForLocation(x, y);
	        // Print Cell Label
	        if (cell != null) {
	          String lab = graph.convertValueToString(cell);
	          System.out.println(lab);
	        }
	      }
	    }
	  });
	  */

}
