/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import java.util.ArrayList;
import java.util.List;

/**
 * algorithme de Bellman-Ford calculant les plus court chemin d'un sommet source vers tous les autres
 * @author Rémi
 */
public class BellmanFord
{

    //sommet source
    private int source;
    //le graphe
    private OrientedValuedGraph G;
    //tableau des distances
    private int[] distances;
    //tableau du routage
    private int[] routage;
    //variables utilisées dans l'algo
    private int[] oldDistances;
    private boolean stable;

    /**
     * constructeur
     * @param source le sommet source à partir duquel on veut calculer les distances
     * @param G gaphe sur lequel s'applique l'algorithme
     */
    public BellmanFord(int source, OrientedValuedGraph G)
    {
        setG(G);
        setSource(source);

        distances = new int[G.size()];
        oldDistances = new int[G.size()];

        routage = new int[G.size()];

        init();

        stable = false;

    }

    /**
     * initialisation de l'algorithme
     */
    private void init()
    {

        for (int i = 0; i < getG().size(); i++)
        {
            distances[i] = getG().getValue(getSource(), i);
            oldDistances[i] = distances[i];
            routage[i] = getSource();
        }
    }

    /**
     * lance l'algo
     */
    public void runAlgorithm()
    {
        int k = 0;

        int cout = 0;

        while ( (!stable) && k < G.size() )
        {
            k++;
            stable = true;

            for (int i = 0; i < distances.length; i++)
            {
                oldDistances[i] = distances[i];
            }

            for (int i = 0; i < G.size(); i++)
            {
                for (int j = 0; j < G.size(); j++)
                {                    
                    if (G.exists(j, i))
                    {
                        cout = oldDistances[j] + G.getValue(j, i);
                        if (cout < distances[i])
                        {
                            distances[i] = cout;
                            routage[i] = j;
                            stable = false;
                        }
                    }
                }
            }
        }
    }

    public List<Integer> getPath(int j) {

        List<Integer> path = new ArrayList<Integer>();

        if ( j == source ) {
            path.add( j );
        } else if ( distances[j] < G.infinityValue()) {
            path.addAll( getPath(routage[j]) );
            path.add( j );
        }

        return path;
        
    }

    /**
     * @return the source
     */
    public int getSource()
    {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(int source)
    {
        this.source = source;
    }

    /**
     * @return the G
     */
    public OrientedValuedGraph getG()
    {
        return G;
    }

    /**
     * @param G the G to set
     */
    public void setG(OrientedValuedGraph G)
    {
        this.G = G;
    }
}