package graphs;

import java.util.ArrayList;
import java.util.List;

import algo.BellmanFord;

import values.IValues;
import values.IntValues;

/**
 * Représente un graphe de coûts.
 * @author Rémi
 */
public class OrientedValuedGraph extends AbstractGraph<IntValues>
{

    /**
     * constructeur par défaut
     */
    public OrientedValuedGraph()
    {
        super();
    }

    /**
     * constructeur par matrice d'adjacence
     * @param costValues la matrice d'adjacence du graphe
     */
    public OrientedValuedGraph(IntValues[][] costValues)
    {
        super(costValues);
    }

    /**
     * Constructeur par copie
     * @param g
     */
    public OrientedValuedGraph(OrientedValuedGraph g)
    {
    	super(g);
    }

    /**
     * Constructeur par copie sur un graphe de flot/capacité/cout
     * @param g
     */
    public OrientedValuedGraph(FlowCostGraph g, int label)
    {
    	values = new IntValues[g.size()][g.size()];
        for (int i = 0; i < values.length; i++)
        {
            for (int j = 0; j < values.length; j++)
            {
            	if (label == 0) {
            		this.setValue(i, j, g.getFlow(i, j));
            	}
            	else if (label == 1) {
            		this.setValue(i, j, g.getCapacity(i, j));
            	}
            	else if (label == 2) {
            		this.setValue(i, j, g.getCost(i, j));
            	}
            	else {
            		throw new IllegalArgumentException("Label inexistant");
            	}
            }
        }
    }

    /**
     * constructeur par matrice d'adjacence
     * @param costValues la matrice d'adjacence du graphe
     */
    public OrientedValuedGraph(long[][] costValues)
    {
//        super(costValues.length);

    	values = new IntValues[costValues.length][costValues.length];
        for (int i = 0; i < values.length; i++)
        {
            for (int j = 0; j < values.length; j++)
            {
                this.setValue(i, j, costValues[i][j]);
            }
        }
    }

    /**
     * constructeur - spécification de la taille du graphe
     * @param size
     */
    public OrientedValuedGraph(int size)
    {
        super(size);
    }

    /**
     * représente l'abscence de valeur
     * @return l'objet représentant l'abscence de valeurs d'un arc
     */
    public IValues noValue()
    {
        return IntValues.noValue();
    }

    /**
     * mutateur d'un arc
     * @param i point d'entrée de l'arc
     * @param j point de sortie de l'arc
     * @return le label de l'arc (i, j)
     */
    @Override
    public IntValues get(int i, int j)
    {
        return (IntValues) values[i][j];
    }

    /**
     * getValue
     * accesseur du cout d'un arc
     * @param i point d'entrée de l'arc
     * @param j point de sortie de l'arc
     * @return le cout de l'arc (i, j)
     */
    public long getValue(int i, int j)
    {
        return get(i, j).getValue();
    }

    /**
     * mutateur du coût d'un arc
     * @param i point d'entrée de l'arc
     * @param j point de sortie de l'arc
     * @param cost cout de l'arc (i, j)
     */
    public void setValue(int i, int j, long cost)
    {
        set(i, j, new IntValues(cost));
    }

    /**
     * getShortestPath
     * retourne le chemin le plus court entre le sommet i et le sommet j
     * a l'aide de l'algorithme de Bellman-ford
     * @param i point d'entrée de l'arc
     * @param j point de sortie de l'arc
     * @return chemin le plus court entre le sommet i et le sommet j
     */
    public List<Integer> getShortestPath(int i, int j)
    {

        List<Integer> shortedPath = new ArrayList<Integer>();

        try
        {
            checkIndex(i);
            checkIndex(j);

            BellmanFord bf = new BellmanFord(i, this);

            bf.runAlgorithm();

            shortedPath = bf.getPath(j);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return shortedPath;
    }

}