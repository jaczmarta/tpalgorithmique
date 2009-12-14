package algo;

import java.util.List;
import values.IValues;
import graphs.OrientedValuedGraph;
import java.util.ArrayList;
import java.util.Collections;

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
        return (a < b + c ? a : b + c);
    }

    /**
     * cherche la présence d'un circuit absorbant dans le graphe
     * @return la liste des sommets représentant un circuit absorbant, une liste vide si le
     * graphe n'admet pas de circuit absorbant
     */
    public List<Integer> getCircuitWithNegativeCost()
    {
        if (this.hasAbsorbingCircuit())
        {
            for (int i = 0; i < delta.length; i++)
            {
                int memi = i;

                //circuit absorbant <=> il existe i tel que delta[i][i] < 0 (ou P[i][i] != i)
                if (delta[i][i] < 0)
                {
                    /* A COMMENTER */
                    List<Integer> pile1 = new ArrayList<Integer>();
                    List<Integer> pile2 = new ArrayList<Integer>();

                    int k = i;

                    pile1.add(k);
                    k = P[k][k];

                    while (!pile2.contains(k))
                    {

                        if (pile1.contains(k))
                        {
                            pile2.add(k);
                        }
                        pile1.add(k);

                        k = P[i][k];
                    }

                    Collections.reverse(pile2);

                    if (pile2.size() > 2)
                    { //ce if est moche
                        checkCircuit(pile2);
                        return pile2;
                    } else
                    {
                        //System.out.println("");
                        //i = memi;
                    }
                }
            }
        }
        return new ArrayList<Integer>();

    }

    private void checkCircuit(List<Integer> circuit)
    {
        int sum = 0;
        for (int i = 0; i < circuit.size(); i++)
        {
            int a = circuit.get(i);
            int b = circuit.get((i + 1) % circuit.size());

            if (!G.exists(a, b))
            {
                System.err.println("arc n'existe pas");
                break;
            }
            sum += G.getValue(a, b);

        }
        if (sum >= 0)
        {
            System.err.println("sum = " + sum);
        }
    }

    public boolean hasAbsorbingCircuit()
    {
        return absorbingCircuit;
    }

    private int plus(int x, int y)
    {
        if ((x == IValues.infinity) || (y == IValues.infinity))
        {
            return IValues.infinity;
        } else
        {
            return x + y;
        }
    }

    private boolean superieur(int x, int y)
    {
        if (y == IValues.infinity)
        {
            return false;
        } else
        {
            return (x > y);
        }
    }
}
