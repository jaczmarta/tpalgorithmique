package algo;


/**
 * représente un graphe quelconque (valué ou non, arcs labellisés par des objets Type)
 * @author remi
 */

public abstract class Graph<Type> {

    public static final int DEFAULT_NB_VERTICES = 1000; //Nombre par défaut de sommets
    protected Type[][] values; //matrice des labels des arcs

    /**
     * Constructeur sans paramètre
     */
    public Graph() {
        values = (Type[][]) new Object[DEFAULT_NB_VERTICES][DEFAULT_NB_VERTICES];
        for (int i = 0; i < DEFAULT_NB_VERTICES; i++) {
            for (int j = 0; j < DEFAULT_NB_VERTICES; j++) {
                values[i][j] = (Type) noValue();
            }
        }
    }

    /**
     * Constructeur par valeur;
     * @param adj la matrice d'adjacence du graphe (matrice des labels des arcs)
     */
    public Graph(Type[][] values) {

        //copie en profondeur

        int size = values.length;
        this.values = (Type[][]) new Object[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.values[i][j] = values[i][j];
            }
        }
    }

    /**
     * retourne le label de l'arc (i, j)
     * @param i sommet de départ
     * @param j sommet d'arrivée
     * @return le label de l'arc (i, j)
     */
    public Type get(int i, int j) {
        try {
            if ((i < 0) || (j < 0) || (i >= values.length) || (j >= values.length)) {
                throw new ArrayIndexOutOfBoundsException("Graph::get : (" + i + ", " + j + ")");
            } 
            else {
                return values[i][j];
            }
        } 
        catch (Exception e) {
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
    public void set(int i, int j, Type v) {
        try {
            if ((i < 0) || (j < 0) || (i >= values.length) || (j >= values.length)) {
                throw new ArrayIndexOutOfBoundsException("Graph::set : (" + i + ", " + j + ")");
            } 
            else {
                values[i][j] = v;
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * le nombre de sommets
     * @return le nombre de sommets
     */
    protected int size() {
        return values.length;
    }
    

    /**
     * représente l'abscence de valeur
     * @return la représentation formelle de l'abscence de valeur
     */
    public static Object noValue() {
        return null;
    }

        /**
         * @return un entier comprit entre min et max
         */
    public int generateInteger(int min, int max) {
        return (int)Math.floor(Math.random()*(max-min+1))+min;
    }
}
