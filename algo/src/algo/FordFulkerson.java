/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import graphs.FlowCostGraph;
import graphs.OrientedValuedGraph;
import java.util.List;

import values.IValues;

/**
 * Classe implémentant l'algorithme de Ford Fulkerson cherchant un flot max dans un graphe.
 * utilise en fait l'algorithme d'edmond-Karp
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
        int cpt = 0;

        do
        {
            betterChain = getBetterChainWithEdmondKarp();//G.getResultingNetwork().getShortestPath(G.indexOfSource(), G.indexOfSink());
            if (!betterChain.isEmpty())
            {
                cpt++;
                int delta = getPossibleIncrease(G, betterChain);
                updateFlow(betterChain, delta);

            }
        } while (!betterChain.isEmpty());

        //System.out.println("cpt = "+cpt);


        return this.G;
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
                System.err.println("BasicMaxFlowMinCost::getPossibleAugmentation : invalid edge (" + i + ", " + j + ") ; path = " + path);
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

    private List<Integer> getBetterChainWithEdmondKarp()
    {
        /**
         * selon l'algo d'edmond-karp, la meilleure chaine est un chemin de s à t le plus
         * court possible mais en nombre d'arcs. On construit donc le graphe d'écart
         * avec des 1 comme valuation, et on lance bellman
         */
        OrientedValuedGraph Ge = new OrientedValuedGraph(G.size());

        for (int i = 0; i < G.size(); i++)
        {
            for (int j = 0; j < G.size(); j++)
            {

                if (G.exists(i, j))
                {

                    long xij = G.getFlow(i, j);
                    long cij = G.getCapacity(i, j);

                    if (xij < cij)
                    {
                        Ge.setValue(i, j, 1);
                    }

                    if (xij > 0)
                    {
                        Ge.setValue(j, i, 1);
                    }
                }
            }
        }

        BellmanFord bf = new BellmanFord(G.indexOfSource(), Ge);
        bf.runAlgorithm();
        return bf.getPath(G.indexOfSink());

    }
}
