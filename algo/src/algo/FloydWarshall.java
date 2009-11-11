package algo;

import values.FlowCostValues;
import values.IValues;
import graphs.OrientedValuedGraph;

/**
 * algorithme de Floyd Warshall calculant les plus courts chemins entre toutes les paires de sommets
 * @author remi
 */
public class FloydWarshall
{
    //matrice delta des chemins les plus courts
    private int[][] delta;
    //matrice du routage :
    //P[i][j] contient le sommet précédent j sur un plus court chemin de i à j
    private int[][] P;
    OrientedValuedGraph G;

    /**
     * constructeur par défaut
     */
    public FloydWarshall()
    {
        delta = new int[0][0];
        P = new int[0][0];
        G = new OrientedValuedGraph();
    }

    /**
     * constructeur par valeur
     * @param G un graphe orienté valué par des entiers
     */
    public FloydWarshall(OrientedValuedGraph G)
    {
        this.G = G;
        int size = G.size();
        delta = new int[size][size];
        P = new int[size][size];
    }

    /**
     * accesseur matrice delta
     * @return la matrice delta des distances min
     */
    public int[][] getDelta()
    {
        return delta;
    }

    /**
     * accesseur matrice P
     * @return la matrice P du routage
     */
    public int[][] getP()
    {
        return delta;
    }

    /**
     * lance l'algorithme de Floyd Warshall
     */
    public void runAlgorithm()
    {
        initDeltaAndP();

        //algorithme
        for (int k = 0; k < G.size(); k++)
        {
            for (int j = 0; j < G.size(); j++)
            {
                for (int i = 0; i < G.size(); i++)
                {
                    if (min(delta[i][j], delta[i][k], delta[k][j]) != delta[i][j])
                    {
                        // delta[i][j] >= delta[i][k] + delta[k][j]
                        // on passe par( min( xxx, yyy ) != xxx ) pour eviter les erreurs avec les infinis...
                        P[i][j] = P[k][j];
                    }
                    delta[i][j] = min(delta[i][j], delta[i][k], delta[k][j]);
                }
            }
        }

    }

    public void initDeltaAndP()
    {
        for (int i = 0; i < G.size(); i++)
        {
            for (int j = 0; j < G.size(); j++)
            {
                if (i == j)
                {
                    P[i][j] = i;
                    delta[i][j] = 0;
                } else if (G.exists(i, j))
                {
                    P[i][j] = i;
                    delta[i][j] = G.getValue(i, j);
                } else
                {
                    P[i][j] = IValues.infinity;
                    delta[i][j] = IValues.infinity;
                }
            }
        }
    }

    /**
     * affiche la matrice delta
     */
    public void showDelta()
    {
        OrientedValuedGraph d = new OrientedValuedGraph(delta);
        d.show();
    }

    /**
     * affiche la matrice P
     */
    public void showP()
    {
        try
        {

            if (P == null)
            {
                throw new NullPointerException("FloydWarshall::showP : P==null");
            } else
            {
                String str = "";
                int T = P.length;
                for (int i = 0; i < T; i++)
                {
                    for (int j = 0; j < T; j++)
                    {
                        str += P[i][j] + "\t";
                    }
                    str += "\n";
                }
                System.out.println(str.replace(FlowCostValues.noValue().toString(), "-"));
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * affiche le plus court chemin entre i et j
     * @param i
     * @param j
     */
    public void showPath(int i, int j)
    {
        try
        {
            G.checkIndex(i);
            G.checkIndex(j);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println(getPath(i, j));
    }

    private String getPath(int i, int j)
    {
        if (i == j)
        {
            return "" + j;
        } else if (P[i][j] == i)
        {
            return "Pas de chemin de " + i + " a " + j;
        } else
        {
            return getPath(i, P[i][j]) + " -> " + j;
        }
    }

    /**
     * retourne le minimum entre a et b+c
     * en prenant en compte les cas où b ou c = infini ( infini-x = infini )
     * @param a
     * @param b
     * @param c
     * @return min (a, b+c)
     */
    private int min(int a, int b, int c)
    {
        if ((b == IValues.infinity) || (c == IValues.infinity))
        {
            return a;
        } else
        {
            return (a < b + c ? a : b + c);
        }
    }
}