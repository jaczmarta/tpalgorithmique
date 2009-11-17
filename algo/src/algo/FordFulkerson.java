/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import graphs.FlowCostGraph;
import java.util.List;

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
    public FlowCostGraph getMaxFlow()
    {

        initFlow();

        List<Integer> chaineAmeliorante;

        do
        {
            chaineAmeliorante = G.getResultingNetwork().getShortedPath(G.indexOfSource(), G.indexOfSink());
            if (!chaineAmeliorante.isEmpty())
            {
                int delta = getPossibleAugmentation(chaineAmeliorante);
                updateFlow(chaineAmeliorante, delta);

            }
        } while (!chaineAmeliorante.isEmpty());


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
        this.G = G;
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
                System.err.println("FordFulkerson::getPossibleAugmentation : invalid edge (" + i + ", " + j + ")");
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
                System.err.println("FordFulkerson::updateFlow : invalid edge (" + i + ", " + j + ")");
                System.exit(-1);
            }
        }

    }
}
