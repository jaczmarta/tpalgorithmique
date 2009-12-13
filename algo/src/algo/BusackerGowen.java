package algo;

import graphs.FlowCostGraph;
import graphs.OrientedValuedGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * algorithme de Busacker et Gowen recherchant le flot max de cout min dans un réseau de flots
 * On cherche le chemin de s à t de cout min dans le graphe d'ecart pour augmenter le flot.
 * @author Rémi
 */
public class BusackerGowen
{

    private FlowCostGraph G;

    /**
     * constructeur
     * @param G le graphe sur lequel on veut appliquer l'algorithme
     */
    public BusackerGowen(FlowCostGraph G)
    {
        this.G = new FlowCostGraph(G);
    }

    /**
     * @return the G
     */
    public FlowCostGraph getG()
    {
        return G;
    }

    /**
     * @param G the G to set
     */
    public void setG(FlowCostGraph G)
    {
        this.G = new FlowCostGraph(G);
    }

    /**
     * lance l'algorithme de Busacker et Gowen
     */
    public void runAlgorithm()
    {
        int wantedFlow = Integer.MAX_VALUE / 10;

        //initialisation du flot à zéro
        int T = G.size();
        for (int i = 0; i < T; i++)
        {
            for (int j = 0; j < T; j++)
            {
                if (G.exists(i, j))
                {
                    G.setFlow(i, j, 0);
                }

            }
        }

        List<Integer> path = new ArrayList<Integer>();

        do
        {

            path = G.getResultingNetworkWithCosts().getShortestPath(G.indexOfSource(), G.indexOfSink());

            if (!path.isEmpty())
            {
                int delta = getPossibleAugmentation(path);
                updateFlow(path, delta);
            }

        } while (!path.isEmpty() && (wantedFlow != G.getGraphFlow()));

        //G.checkFlow();

    }

    /**
     * calcule l'augmentation possible de flot pour un chemin augmentant
     */
    private int getPossibleAugmentation(List<Integer> path)
    {
        int min = Integer.MAX_VALUE;

        int v = 0;
        for (int k = 0; k < path.size() - 1; k++)
        {
            int i = path.get(k);
            int j = path.get(k + 1);

            if (G.exists(i, j))
            {
                v = G.getCapacity(i, j) - G.getFlow(i, j);
            } else if (G.exists(j, i))
            {
                v = G.getFlow(j, i);
            } else
            {
                System.err.println("BusackerGowen::getPossibleAugmentation : invalid edge (" + i + ", " + j + ")");
                System.exit(-1);
            }

            if (v < min)
            {
                min = v;
            }
        }

        return min;
    }

    /**
     * met à jour le flot selon l'augmentation sur un chemin
     */
    private void updateFlow(List<Integer> path, int delta)
    {
        for (int k = 0; k < path.size() - 1; k++)
        {
            int i = path.get(k);
            int j = path.get(k + 1);

            if (G.exists(i, j))
            {
                G.addFlow(i, j, delta);
            } else if (G.exists(j, i))
            {
                G.addFlow(j, i, -delta);
            } else
            {
                System.err.println("BusackerGowen::updateFlow : invalid edge (" + i + ", " + j + ")");
                System.exit(-1);
            }
        }

    }
}
