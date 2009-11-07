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
    private int capacityLowerBound;
    private static int DEFAULT_capacity_LowerBound = 1;
    private int capacityUpperBound;
    private static int DEFAULT_capacityUpperBound = 1;
    private int costBound;
    private static int DEFAULT_costBound = 1;

    /**
     * constructeur
     * fixe tous les paramètres à leurs valeurs par défaut
     */
    public RandomGraphBuilder()
    {
        numEdges = DEFAULT_numEdges;
        numVertices = DEFAULT_numVertices;
        capacityLowerBound = DEFAULT_capacity_LowerBound;
        capacityUpperBound = DEFAULT_capacityUpperBound;
        costBound = DEFAULT_costBound;
    }

    /**
     * generateRandomFlowGraph
     * génère un graphe
     * @return le graphe généré
     */
    public FlowCostGraph generateRandomFlowGraph()
    {

        FlowCostGraph G = new FlowCostGraph(getNumVertices());


        int edgesCounter = 0;
        //création chemin entre le premier et le dernier sommet
        {
            int numEdgesForThisPath = generateInteger(0, getNumVertices() - 1);
            int i = G.indexOfSource();

            //liste de chemins aléatoires pour le chemin entre s et t
            List<Integer> randomVertices = new ArrayList<Integer>();

            randomVertices.add(G.indexOfSource());

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
            G.set(randomVertices.get(i), G.indexOfSink(), getRandomValues());
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
    private FlowCostValues getRandomValues()
    {
        int flow = 0;
        int capacity = generateInteger(getCapacityLowerBound(), getCapacityUpperBound());
        int cost = generateInteger(1, getCostBound());

        return new FlowCostValues(flow, capacity, cost);


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
     * @return the capacityLowerBound
     */
    public int getCapacityLowerBound()
    {
        return capacityLowerBound;
    }

    /**
     * @param capacityLowerBound the capacityLowerBound to set
     */
    public void setCapacityLowerBound(int capacityLowerBound)
    {
        this.capacityLowerBound = capacityLowerBound;
    }

    /**
     * @return the capacityUpperBound
     */
    public int getCapacityUpperBound()
    {
        return capacityUpperBound;
    }

    /**
     * @param capacityUpperBound the capacityUpperBound to set
     */
    public void setCapacityUpperBound(int capacityBound)
    {
        this.capacityUpperBound = capacityBound;
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
        try
        {
            if ((density < 0) || (density > 1))
            {
                throw new IllegalArgumentException();
            } else
            {
                setNumEdges((int) (density * getNumVertices() * (getNumVertices() - 1)));
            }
        } catch (Exception e)
        {
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
     * @return un entier comprit entre min et max
     */
    public static int generateInteger(int min, int max)
    {
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }
}
