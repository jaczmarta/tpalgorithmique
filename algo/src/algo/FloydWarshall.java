package algo;

import values.IValues;
import graphs.OrientedValuedGraph;

/**
 * algorithme de Floyd Warshall calculant les plus courts chemins entre toutes les paires de sommets
 * @author remi
 */
public class FloydWarshall
{

    //matrice delta des chemins les plus courts
    private long[][] delta;
    //matrice du routage :
    //P[i][j] contient le sommet précédent j sur un plus court chemin de i à j
    private int[][] P;
    private boolean absorbingCircuit;
    OrientedValuedGraph G;

    /**
     * constructeur par défaut
     */
    public FloydWarshall()
    {
        absorbingCircuit = false;
        delta = new long[0][0];
        P = new int[0][0];
        G = new OrientedValuedGraph();
    }

    /**
     * constructeur par valeur
     * @param G un graphe orienté valué par des entiers
     */
    public FloydWarshall(OrientedValuedGraph G)
    {
        absorbingCircuit = false;
        this.G = G;
        int size = G.size();
        delta = new long[size][size];
        P = new int[size][size];
    }

    /**
     * accesseur matrice delta
     * @return la matrice delta des distances min
     */
    public long[][] getDelta()
    {
        return delta;
    }

    /**
     * accesseur matrice P
     * @return la matrice P du routage
     */
    public int[][] getP()
    {
        return P;
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
                if (delta[j][k] != IValues.infinity)
                {
                    for (int i = 0; i < G.size(); i++)
                    {
                        if (delta[k][i] != IValues.infinity)
                        {
                            if (delta[j][i] != IValues.infinity) {

                                long deltajk = delta[j][k];
                                long deltaki = delta[k][i];
                                long sum = deltajk + deltaki;

                                if ((sum < deltajk) && (sum < deltaki) && (deltajk > 0) && (deltaki > 0)) {
                                    System.out.println("pb");
                                }

                                if ((delta[j][i] > (delta[j][k] + delta[k][i])))
                                {
                                    // delta[i][j] >= delta[i][k] + delta[k][j]
                                    // on passe par( min( xxx, yyy ) != xxx ) pour eviter les erreurs avec les infinis...
                                    P[j][i] = P[k][i];
                                    delta[j][i] = delta[j][k] + delta[k][i];
                                }

                            } else
                            {
                                P[j][i] = P[k][i];
                                delta[j][i] = delta[j][k] + delta[k][i];
                            }

                            if (i == j)
                            {
                                if (delta[i][i] < 0)
                                {
                                    this.absorbingCircuit = true;
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    private void initDeltaAndP()
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
                System.out.println(str.replace(Integer.toString(IValues.infinity), "-"));
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
        } //        else if (P[i][j] == i)
        //        {
        //            return "Pas de chemin de " + i + " a " + j;
        //        }
        else
        {
            return getPath(i, P[i][j]) + " -> " + j;
        }
    }

    public boolean hasAbsorbingCircuit()
    {
        return absorbingCircuit;
    }
}
