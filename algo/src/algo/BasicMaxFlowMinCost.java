/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import graphs.FlowCostGraph;
import java.util.List;

/**
 * algorithme de base pour chercher le flot max de cout min dans un graphe de flot avec des couts
 * La méthode consiste a chercher un flot max puis de reduire le cout en eliminant les cycles de cout
 * negatifs dans le graphe d'ecart
 * @author Rémi
 */
public class BasicMaxFlowMinCost
{

    private FlowCostGraph G;

    public BasicMaxFlowMinCost(FlowCostGraph G)
    {
        setG(G);
    }

    public void runAlgorithm()
    {

        buildFlow();

        List<Integer> circuitWithNegativeCost;

        do
        {
            circuitWithNegativeCost = getCircuitWithNegativeCost();

            if (!circuitWithNegativeCost.isEmpty())
            {
                updateFLow(circuitWithNegativeCost);
                System.out.println("updateFlow()");
            }
        } while (!circuitWithNegativeCost.isEmpty());

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
        this.G = G;
    }

    /**
     * construit un flot de départ à l'aide de l'algorithme de Ford-Fulkerson
     */
    private void buildFlow()
    {
        FordFulkerson ff = new FordFulkerson(G);
        G = ff.getMaxFlow();
    }

    /**
     * cherche un circuit de cout negatif à l'aide de l'algorithme de Warshall-Floyd
     * @return une liste des sommets représentant un circuit à cout négatif
     */
    private List<Integer> getCircuitWithNegativeCost()
    {

        FloydWarshall fw = new FloydWarshall(G.getResultingNetworkWithCosts());
        fw.runAlgorithm();

        return fw.getCircuitWithNegativeCost();

    }

    /**
     * augmente le flot de manière à faire disparaitre le circuit
     * @param path un circuit à cout négatif
     */
    private void updateFLow(List<Integer> path)
    {
        int delta = getPossibleAugmentation(path);

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
                System.err.println("BasicMaxFlowMinCost::updateFlow : invalid edge (" + i + ", " + j + ")");
                System.exit(-1);
            }
        }
        
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
                System.err.println("BasicMaxFlowMinCost::getPossibleAugmentation : invalid edge (" + i + ", " + j + ")");
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
        

    }
} 
