/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rémi
 */
public class RandomGraphBuilder
{

    private int numVertices;
    private static int DEFAULT_numVertices = 10;
    private int numEdges;
    private static int DEFAULT_numEdges = 0;
    private int capacityBound;
    private static int DEFAULT_capacityBound = 1;
    private int costBound;
    private static int DEFAULT_costBound = 1;

    /**
     * generateRandomFlowGraph
     * génère un graphe
     * @return le graphe généré
     */
    public FlowGraph generateRandomFlowGraph()
    {

        FlowGraph G = new FlowGraph(getNumVertices());


        int edgesCounter = 0;
        //création chemin entre le premier et le dernier sommet
        {
            int numEdgesForThisPath = generateInteger(0, getNumVertices() - 1);
            int i = indexOfSource();

            //liste de chemins aléatoires pour le chemin entre s et t
            List<Integer> randomVertices = new ArrayList<Integer>();

            randomVertices.add(indexOfSource());

            int k = 0;
            while (randomVertices.size() <= numEdgesForThisPath)
            {
                int randomVertice = generateInteger(0, getNumVertices() - 1);
                if (!randomVertices.contains(randomVertice))
                {
                    randomVertices.add(randomVertice);
                }
                k++;
            }


            while (i < numEdgesForThisPath)
            {
                G.set(randomVertices.get(i), randomVertices.get(i + 1), getRandomValues());
                i++;
                edgesCounter++;
            }
            G.set(randomVertices.get(i), indexOfSink(), getRandomValues());
            edgesCounter++;
        }
        //création des derniers arcs
        {
            while (edgesCounter <= getNumEdges())
            {
                int i, j;
                do
                {
                    i = generateInteger(0, getNumVertices() - 1);
                    j = generateInteger(0, getNumVertices() - 1);

                } while (!G.exists(i, j) || (i == j));

                G.set(i, j, getRandomValues());

                edgesCounter++;
            }
        }

        return G;

    }

    /**
     * getRandomValues
     * retourne un objet FlowValue aléatoire conformément aux bornes fixées
     * @return un FlowValue aléatoire conformément aux bornes fixées
     */
    private FlowValues getRandomValues()
    {
        int flow = 0;
        int capacity = generateInteger(1, getCapacityBound());
        int cost = generateInteger(1, getCostBound());

        return new FlowValues(flow, capacity, cost);


    }

    /**
     * @return the numVertices
     */
    public int getNumVertices()
    {
        return numVertices;
    }

    /**
     * @param numVertices the numVertices to set
     */
    public void setNumVertices(int numVertices)
    {
        this.numVertices = numVertices;
    }

    /**
     * @return the numEdges
     */
    public int getNumEdges()
    {
        return numEdges;
    }

    /**
     * @param numEdges the numEdges to set
     */
    public void setNumEdges(int numEdges)
    {
        this.numEdges = numEdges;
    }

    /**
     * @return the capacityBound
     */
    public int getCapacityBound()
    {
        return capacityBound;
    }

    /**
     * @param capacityBound the capacityBound to set
     */
    public void setCapacityBound(int capacityBound)
    {
        this.capacityBound = capacityBound;
    }

    /**
     * @return the costBound
     */
    public int getCostBound()
    {
        return costBound;
    }

    /**
     * @param costBound the costBound to set
     */
    public void setCostBound(int costBound)
    {
        this.costBound = costBound;
    }

    /**
     * setDensity
     * fixe la densité du graphe en fonction du nombre de sommets
     * densite = nombre d'arcs/nombre d'arcs possibles
     */
    public void setDensity(double density)
    {
        try {
            if ( (density < 0) || (density > 1)) {
                throw new IllegalArgumentException();
            } else {
                setNumEdges( (int) (density * getNumVertices() * (getNumVertices() - 1)) );
            }
        } catch (Exception e) {
            System.err.println("Warning : RandomGraphBuilder::setDensity : invalid argument. Density not set");
        }
    }

    /**
     * getDensity
     * accesseur densité du graphe
     * densité = nombre d'arcs/nombre d'arcs possibles
     */
    public double getDensity()
    {
        //on interdit les boucles
        return getNumVertices() * (getNumEdges() - 1);
    }

    /**
     * indexOfSource
     * @return index de la source
     */
    private int indexOfSource()
    {
        return 0;
    }

    /**
     * indexOfSing
     * @return index du puit
     */
    private int indexOfSink()
    {
        return getNumVertices() - 1;
    }

    /**
     * @return un entier comprit entre min et max
     */
    public static int generateInteger(int min, int max)
    {
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }
}
