/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import graphs.FlowCostGraph;
import java.util.List;

import values.IValues;

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
        this.G = new FlowCostGraph(G);
    }

    public void runAlgorithm()
    {

        buildFlow();
        List<Integer> circuitWithNegativeCost = getCircuitWithNegativeCost();

        while (!circuitWithNegativeCost.isEmpty())
        {
            updateFLow(circuitWithNegativeCost);
            circuitWithNegativeCost = getCircuitWithNegativeCost();
        }

        G.checkFlow();

    }

    /**
     * construit un flot de départ à l'aide de l'algorithme de Ford-Fulkerson
     */
    private void buildFlow()
    {
        FordFulkerson ff = new FordFulkerson(G);
        G = ff.getMaxFlowGraph();
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
        int delta = 1;

        for (int k = 0; k < path.size(); k++)
        {
            int i = path.get(k);
            //on augmente le flot sur tout le circuit => aussi sur l'arc (dernier_du_circuit, premier_du_circuit)
            int j = path.get((k + 1) % path.size());

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
    public static int getPossibleIncrease(FlowCostGraph graph, List<Integer> path)
    {
        int increase = 0;
        int increaseMax = IValues.infinity;
        for (int k = 0; k < path.size() - 1; k++)
        {
            int i = path.get(k);
            int j = path.get(k + 1);

            if (graph.exists(i, j))
            {
                increase = graph.getCapacity(i, j) - graph.getFlow(i, j);
            } else if (graph.exists(j, i))
            {
                increase = graph.getFlow(j, i);
            } else
            {
                System.err.println("BasicMaxFlowMinCost::getPossibleAugmentation : invalid edge (" + i + ", " + j + ")");
                System.exit(-1);
            }
            increaseMax = Math.min(increaseMax, increase);

        }
        return increaseMax;
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
}
