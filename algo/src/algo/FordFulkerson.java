/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import graphs.FlowCostGraph;
import java.util.List;

import values.IValues;

/**
 * Classe implémentant l'algorithme de Ford Fulkerson cherchant un flot max dans un graphe.
 * @author Rémi
 */
public class FordFulkerson
{

    private FlowCostGraph G;

    /**
     * constructeur par paramètre
     * @param G le graphe sur lequel va s'effectuer l'algorithme
     */
    public FordFulkerson(FlowCostGraph G)
    {
        //copie en profondeur
        this.G = new FlowCostGraph(G);
    }

    /**
     * lance l'algorithme et retourne le graphe de flots avec le flot max
     * @return le graphe de flots avec le flot max
     */
    public FlowCostGraph getMaxFlowGraph()
    {

        initFlow();
        List<Integer> betterChain;

        do
        {
            betterChain = G.getResultingNetwork().getShortestPath(G.indexOfSource(), G.indexOfSink());
            if (!betterChain.isEmpty())
            {
                int delta = BasicMaxFlowMinCost.getPossibleIncrease(G, betterChain);
                updateFlow(betterChain, delta);

            }
        } while (!betterChain.isEmpty());


        return this.G;
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
     * initialisation du flot à 0
     */
    private void initFlow()
    {
        for (int i = 0; i < G.size(); i++)
        {
            for (int j = 0; j < G.size(); j++)
            {
                G.setFlow(i, j, 0);
            }
        }
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
                System.err.println("FordFulkerson::updateFlow : invalid edge (" + i + ", " + j + ")");
                System.exit(-1);
            }
        }

    }
}
