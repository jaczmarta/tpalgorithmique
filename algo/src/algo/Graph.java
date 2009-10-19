/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

/**
 * représente un graphe quelconque (valué ou non, arcs labellisés par des objets Value)
 * @author remi
 */
public abstract class Graph<Value> {

    //Nombre par défaut de sommets (constructeur par défaut)
    public static final int DEFAULT_NUM_NODES = 1000;

    //matrice des labels des arcs
    protected Value[][] value;

    /**
     * représente l'abscence de valeur
     * @return la représentation formelle de l'abscence de valeur
     */
    public static Object noValue() {
        return null;
    }

    /**
     * constructeur par défaut
     */
    public Graph() {
        value = (Value[][]) new Object[DEFAULT_NUM_NODES][DEFAULT_NUM_NODES];
        for (int i = 0; i < DEFAULT_NUM_NODES; i++) {
            for (int j = 0; j < DEFAULT_NUM_NODES; j++) {
                value[i][j] = (Value) noValue();
            }
        }
    }

    /**
     * constructeur par valeur;
     * @param adj la matrice d'adjacence du graphe (matrice des labels des arcs)
     */
    public Graph(Value[][] value) {

        //copie en profondeur

        int T = value.length;
        this.value = (Value[][]) new Object[T][T];

        for (int i = 0; i < T; i++) {
            for (int j = 0; j < T; j++) {
                this.value[i][j] = value[i][j];
            }
        }
    }

    /**
     * retourne le label de l'arc (i, j)
     * @param i sommet de départ
     * @param j sommet d'arrivée
     * @return le label de l'arc (i, j)
     */
    public Value get(int i, int j) {
        try {
            if ((i < 0) || (j < 0) || (i >= value.length) || (j >= value.length)) {
                throw new ArrayIndexOutOfBoundsException("Graph::get : (" + i + ", " + j + ")");
            } else {
                return value[i][j];
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * modifie le label de l'arc (i, j)
     * @param i le sommet de départ
     * @param j le sommet d'arrivée
     * @param v le nouveau label de l'arc (i, j)
     */
    public void set(int i, int j, Value v) {
        try {
            if ((i < 0) || (j < 0) || (i >= value.length) || (j >= value.length)) {
                throw new ArrayIndexOutOfBoundsException("Graph::set : (" + i + ", " + j + ")");
            } else {
                value[i][j] = v;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * le nombre de sommets
     * @return le nombre de sommets
     */
    protected int numNodes() {
        return value.length;
    }
}
