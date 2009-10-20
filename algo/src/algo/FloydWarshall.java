/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

/**
 * algorithme de Floyd Warshall
 * @author remi
 */
public class FloydWarshall {

    //représente une distance infinie
    public int INFINITY;

    //matrice delta des chemins les plus courts
    private int[][] delta;

    //matrice du routage :
    //P[i][j] contient le sommet précédent j sur un plus court chemin de i à j
    private int[][] P;
    OrientedIntValuedGraph G;

    /**
     * constructeur par défaut
     */
    public FloydWarshall() {
        delta = new int[0][0];
        P = new int[0][0];
        G = new OrientedIntValuedGraph();
        INFINITY = OrientedIntValuedGraph.noValue();
    }

    /**
     * constructeur par valeur
     * @param G un graphe orienté valué par des entiers
     */
    public FloydWarshall(OrientedIntValuedGraph G) {
        this.G = G;
        int numNodes = G.numNodes();
        delta = new int[numNodes][numNodes];
        P = new int[numNodes][numNodes];
        INFINITY = OrientedIntValuedGraph.noValue();
    }

    /**
     * lance l'algorithme de Floyd Warshall
     */
    public void runAlgorithm() {
        initDeltaAndP();
        
        //algorithme
        for (int k = 0; k < G.numNodes(); k++) {
            for (int j = 0; j < G.numNodes(); j++) {
                for (int i = 0; i < G.numNodes(); i++) {
                	int ij = delta[i][j];
					int ik = delta[i][k];
					int kj = delta[k][j];
                    
                    if (ij == G.noValue()) {	// s'il n'y a pas de chemin de i a j
    					if ((ik != G.noValue()) && (kj != G.noValue())) {	// alors s'il existe i->k->j
    						P[i][j] = k;
    						delta[i][j] = ik + kj;
    					}
    				}
    				else if ((ik != G.noValue()) && (kj != G.noValue())) { // s'il existe deja un chemin de i a j, et qu'il existe k tel que (i->k->j)
    					delta[i][j] = min(ij, ik, kj);
    					if (ij > (ik + kj))
    						P[i][j] = k;
    				}
                }
            }
        }
    }

    public void initDeltaAndP() {
        for (int i = 0; i < G.numNodes(); i++) {
            for (int j = 0; j < G.numNodes(); j++) {
            	if (i == j) {
            		P[i][j] = i;
                    delta[i][j] = 0;
            	}
            	else if (!G.get(i, j).equals(G.noValue())) {
                    P[i][j] = i;
                    delta[i][j] = G.get(i, j);
                } 
            	else {
                    P[i][j] = INFINITY;
                    delta[i][j] = INFINITY;
                }
            }
        }
    }
    
    /**
     * affiche la matrice delta
     */
    public void showDelta() {
        try {

            if (delta == null) {
                throw new NullPointerException("FloydWarshall::showP : delta==null");
            } else {

                String str = "";
                int T = delta.length;
                for (int i = 0; i < T; i++) {
                    for (int j = 0; j < T; j++) {
                        str += delta[i][j] + "\t";
                    }
                    str += "\n";
                }

                System.out.println(str.replace(G.noValue().toString(), "N"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * affiche la matrice P
     */
    public void showP() {
        try {

            if (P == null) {
                throw new NullPointerException("FloydWarshall::showP : P==null");
            } else {

                String str = "";
                int T = P.length;
                for (int i = 0; i < T; i++) {
                    for (int j = 0; j < T; j++) {
                        str += P[i][j] + "\t";
                    }
                    str += "\n";
                }

                System.out.println(str.replace(OrientedIntValuedGraph.noValue().toString(), "N"));
            }

        } catch (Exception e) {
            e.printStackTrace();
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
    private int min(int a, int b, int c) {
        if ((b == OrientedIntValuedGraph.noValue()) || (c == OrientedIntValuedGraph.noValue())) {
            return a;
        } else {
            return (a < b + c ? a : b + c);
        }
    }
}
