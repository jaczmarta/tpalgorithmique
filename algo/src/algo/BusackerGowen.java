/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import java.util.ArrayList;
import java.util.List;

/**
 * algorithme de Busacker et Gowen recherchant le flot max de cout min dans un réseau de flots
 * @author Rémi
 */
public class BusackerGowen
{

    private FlowCostGraph G;
    private int flow;

    /**
     * constructeur
     * @param G le graphe sur lequel on veut appliquer l'algorithme
     */
    public BusackerGowen(FlowCostGraph G)
    {
        setG(G);
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
     * @return the flow
     */
    public int getFlow()
    {
        return flow;
    }

    /**
     * @param flow the flow to set
     */
    public void setFlow(int flow)
    {
        this.flow = flow;
    }

    public void runAlgorithm()
    {
        int wantedFlow = Integer.MAX_VALUE / 10;

        //initialisation du flot à zéro
        int T = G.size();
        for (int i = 0; i < T; i++)
        {
            for (int j = 0; j < T; j++)
            {
                G.setFlow(i, j, 0);
            }
        }

        List<Integer> path = new ArrayList<Integer>();

        do
        {

            path = G.getResultingNetwork().getShortedPath(G.indexOfSource(), G.indexOfSink());
            
            if (!path.isEmpty())
            {
                int delta = getPossibleAugmentation(path);
                updateFlow(path, delta);
            }
            
        } while (!path.isEmpty() && (wantedFlow != G.getGraphFlow()));
        
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
            int j = path.get(k+1);
            
            if ( G.exists(i, j ) )
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
            int j = path.get(k+1);

            if (G.exists(i, j))
            {
                G.addFlow(i, j, delta);
            } else if (G.exists(j, i))
            {
                G.addFlow(j, i, -flow);
            } else
            {
                System.err.println("BusackerGowen::updateFlow : invalid edge (" + i + ", " + j + ")");
                System.exit(-1);
            }
        }

    }
}
