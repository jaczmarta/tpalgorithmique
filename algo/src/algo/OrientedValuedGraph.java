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
    public OrientedValuedGraph( int[][] costValues ) {
        super(costValues.length);

        int T = costValues.length;
        IntValues[][] val = new IntValues[T][T];
        for (int i = 0 ; i < T ; i++) {
            for (int j = 0 ; j < T ; j++) {
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
     * a l'aide de l'algorithme de Dijkstra
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

            //va contenir les distances min de i vers tous les autres sommets
            int[] distances = new int[size()];

            //va contenir le routage de i vers tous les autres sommets dans les pcc
            int[] routage = new int[size()];

            //pile nécessaire
            Stack<Integer> stack = new Stack<Integer>();

            init(i, distances, routage, stack);

            runDikjstra(i, distances, routage, stack);

            shortedPath = getPath(i, j, routage);


            return shortedPath;

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return shortedPath;
    }

    /**
     * initialisation de l'algorithme de dijkstra
     */
    private void init(int i, int[] distances, int[] routage, Stack<Integer> stack)
    {

        //initialisation distances et routage
        for (int k = 0; k < distances.length; k++)
        {
            if (!IntValues.noValue().equals(get(i, k)))
            {

                distances[k] = getValue(i, k);
            } else
            {
                distances[k] = infinityValue();
            }

            routage[k] = i;
        }

        stack.push(i);

    }

    /**
     * algorithme de dijkstra
     */
    private void runDikjstra(int i, int[] distances, int[] routage, Stack<Integer> stack)
    {
        int t;

        for (int k = 0; k < size() - 1; k++)
        {
            t = chooseVertice(i, stack, distances);
            stack.push(t);
            updateDistances(t, stack, distances, routage);
        }
    }

    /**
     * fonction de choix d'un sommet de l'algorithme de dijkstra
     */
    private int chooseVertice(int i, Stack<Integer> stack, int[] distances)
    {
        int result = i;

        for (int s = 0; s < distances.length; s++)
        {
            if (!stack.contains(s))
            {
                if (distances[s] < distances[result])
                {
                    result = s;
                }
            }
        }

        return result;
    }

    /**
     * fonction de mise à jour des distances dans l'algorithme de dijkstra
     */
    private void updateDistances(int t, Stack<Integer> stack, int[] distances, int[] routage)
    {
        for (int s = 0; s < distances.length; s++)
        {
            if (!stack.contains(s))
            {
                if (distances[t] + getValue(t, s) < distances[s])
                {
                    distances[s] = distances[t] + getValue(t, s);
                    routage[s] = t;
                }
            }
        }
    }

    /**
     * retourne le plus court chemin de i à j après avoir fait tourné l'algoritme de dijkstra
     */
    private List<Integer> getPath(int i, int j, int[] routage)
    {
        List<Integer> list = new ArrayList<Integer>();
        if (j == i)
        {
            list.add(i);
        } else
        {
            list.addAll(getPath(i, routage[j], routage));
            list.add(j);
        }

        return list;

    }
}
