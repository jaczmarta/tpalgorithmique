/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;


import graphs.FlowCostGraph;
import graphs.OrientedValuedGraph;

import java.util.ArrayList;
import java.util.Collections;
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
    private OrientedValuedGraph Ge;

    public BasicMaxFlowMinCost(FlowCostGraph G)
    {
        this.G = new FlowCostGraph(G);
    }

    public void runAlgorithm()
    {

        buildFlow();
        List<Integer> circuitWithNegativeCost = getCircuitWithNegativeCost();

        int cpt = 0;
        while (!circuitWithNegativeCost.isEmpty())
        {
            cpt++;
            updateFLow(circuitWithNegativeCost);

            circuitWithNegativeCost = getCircuitWithNegativeCost();

        }

        //System.out.println("cpt = "+cpt);

    }

    /**
     * construit un flot de départ à l'aide de l'algorithme d'edmond-karp
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

        Ge = G.getResultingNetworkWithCosts();

        boolean hasCircuit = false;
        long[] L = new long[Ge.size()];
        
        for (int i = 0; i < Ge.size(); i++)
        {
        	if (i == 0)
        	{
        		L[i] = 0;
        	}
        	else
            {
                L[i] = IValues.infinity;
            }
        }
        hasCircuit = false;
        int[] pred = new int[Ge.size()];

        for (int x = 0; x < Ge.size(); x++)
        {
            for (int u = 0; u < Ge.size(); u++)
            {
                for (int v = 0; v < Ge.size(); v++)
                {
                    if (Ge.exists(u, v))
                    {
                        if (L[v] > L[u] + Ge.getValue(u, v))
                        {
                            L[v] = L[u] + Ge.getValue(u, v);
                            pred[v] = u;
                        }
                    }
                }
            }
        }
        for (int u = 0; u < Ge.size(); u++)
        {
            for (int v = 0; v < Ge.size(); v++)
            {
                if (Ge.exists(u, v))
                {
                    if (L[v] > L[u] + Ge.getValue(u, v))
                    {
                        hasCircuit = true;

                        List<Integer> pile = new ArrayList<Integer>();
                        int k = v;
                        while (!pile.contains(k))
                        {
                            pile.add(k);
                            k = pred[k];
                        }
                        List<Integer> circuit = pile.subList(pile.indexOf(k), pile.size());
                        Collections.reverse(circuit);
                        if (circuit.size() > 2)
                        {
                            return circuit;
                        } else
                        {
                            return new ArrayList<Integer>();
                        }

                    }
                }
            }
        }

        if (!hasCircuit)
        {
            return new ArrayList<Integer>();

        }

        return new ArrayList<Integer>();

    }

    /**
     * augmente le flot de manière à faire disparaitre le circuit
     * @param path un circuit à cout négatif
     */
    private void updateFLow(List<Integer> path)
    {
        int delta = getPossibleIncrease(path);
        //System.out.println("BASIC : augmentation = "+delta);

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
/*
    private void checkCircuit(List<Integer> circuit)
    {
        int sum = 0;
        for (int i = 0; i < circuit.size(); i++)
        {
            int a = circuit.get(i);
            int b = circuit.get((i + 1) % circuit.size());

            if (!Ge.exists(a, b))
            {
                System.err.println("arc n'existe pas");
                break;
            }
            sum += Ge.getValue(a, b);

        }
        if (sum >= 0)
        {
            System.err.println("sum = " + sum);
        }
    }
*/
    /**
     * calcule l'augmentation possible de flot pour un chemin augmentant
     */
    public int getPossibleIncrease(List<Integer> path)
    {
        int increase = 0;
        int increaseMax = IValues.infinity;
        for (int k = 0; k < path.size(); k++)
        {
            int i = path.get(k);
            int j = path.get((k + 1) % path.size());

            if (G.exists(i, j))
            {
                increase = G.getCapacity(i, j) - G.getFlow(i, j);
            } else if (G.exists(j, i))
            {
                increase = G.getFlow(j, i);
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
}
