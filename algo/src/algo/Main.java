package algo;

import java.util.ArrayList;

/**
 *
 * @author remi
 */
public class Main
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {

        System.out.println("----------------------------- FLOYD - WARSHALL ---------------------------------");
        {
            OrientedValuedGraph G = new OrientedValuedGraph(5);
            G.setValue(1, 3, 3);
            G.setValue(2, 1, -1);
            G.setValue(2, 4, -7);
            G.setValue(3, 0, 1);
            G.setValue(3, 2, 8);
            G.setValue(3, 4, 1);
            G.setValue(4, 0, 2);

            FloydWarshall fw = new FloydWarshall(G);
            fw.runAlgorithm();
            fw.showDelta();
        }

        System.out.println("-----------------------------GRAPHE D'ECART ---------------------------------");
        {
            //Exemple ex1 feuille td 2
            FlowGraph fg = new FlowGraph(6);
            fg.set(0, 1, new FlowValues(2, 2, 1));
            fg.set(0, 3, new FlowValues(0, 4, 1));
            fg.set(1, 2, new FlowValues(1, 1, 1));
            fg.set(1, 4, new FlowValues(0, 2, 1));
            fg.set(1, 3, new FlowValues(1, 2, 1));
            fg.set(2, 5, new FlowValues(1, 2, 1));
            fg.set(3, 4, new FlowValues(1, 1, 1));
            fg.set(4, 5, new FlowValues(1, 2, 1));

            fg.getResultingNetwork().show();
            System.out.println(fg.getResultingNetwork().getShortedPath(0, 5));
        }


        System.out.println("-----------------------------GRAPHE ALEATOIRE---------------------------------");
        {
            RandomGraphBuilder builder = new RandomGraphBuilder();
            builder.setNumVertices(10);
            builder.setDensity(0.4);
            builder.setCapacityBound(5);
            builder.setCostBound(1);
            builder.generateRandomFlowGraph().show();
        }








    }
}
