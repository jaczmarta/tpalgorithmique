/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
     * constructeur par matrice d'adjacence
     * @param costValues la matrice d'adjacence du graphe
     */
    public OrientedValuedGraph(int[][] costValues)
    {
        super(costValues.length);

        int T = costValues.length;
        IntValues[][] val = new IntValues[T][T];
        for (int i = 0; i < T; i++)
        {
            for (int j = 0; j < T; j++)
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
     * représente une valeur infinie d'un arc
     * @return la valeur infinie d'un arc
     */
    public int infinityValue()
    {
        //max_value/10 pour éviter les problèmes de débordement d'entiers
        return Integer.MAX_VALUE / 10;
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
    public int getValue(int i, int j)
    {
        return get(i, j).getValue();
    }

    /**
     * mutateur du coût d'un arc
     * @param i point d'entrée de l'arc
     * @param j point de sortie de l'arc
     * @param cost cout de l'arc (i, j)
     */
    public void setValue(int i, int j, int cost)
    {
        get(i, j).setValue(cost);
    }

    /**
     * getShortedPath
     * retourne le chemin le plus court entre le sommet i et le sommet j
     * a l'aide de l'algorithme de Bellman-ford
     * @param i point d'entrée de l'arc
     * @param j point de sortie de l'arc
     * @return chemin le plus court entre le sommet i et le sommet j
     */
    public List<Integer> getShortedPath(int i, int j)
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
