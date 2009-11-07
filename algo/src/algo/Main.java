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
            FlowCostGraph fg = new FlowCostGraph(6);
            fg.set(0, 1, 2, 2, 1);
            fg.set(0, 3, 0, 4, 1);
            fg.set(1, 2, 1, 1, 1);
            fg.set(1, 4, 0, 2, 1);
            fg.set(1, 3, 1, 2, 1);
            fg.set(2, 5, 1, 2, 1);
            fg.set(3, 4, 1, 1, 1);
            fg.set(4, 5, 1, 2, 1);

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

        
        System.out.println("-----------------------------BUSACKER ET GOWEN ---------------------------------");
        {
            //Exemple ex2 feuille td 5
            FlowCostGraph fg = new FlowCostGraph(7);
            fg.set(0, 1, 0, 5, 4);
            fg.set(0, 2, 0, 8, 2);
            fg.set(1, 3, 0, 4, 8);
            fg.set(1, 4, 0, 2, 5);
            fg.set(2, 4, 0, 5, 2);
            fg.set(2, 5, 0, 2, 6);
            fg.set(3, 6, 0, 7, 3);
            fg.set(4, 6, 0, 3, 5);
            fg.set(5, 6, 0, 3, 4);

            BusackerGowen bg = new BusackerGowen(fg);
            bg.runAlgorithm();
            bg.getG().show();
            System.out.println("Flot = "+bg.getG().getGraphFlow());
            System.out.println("Cout = "+bg.getG().getGraphCost());
        }








    }
}
