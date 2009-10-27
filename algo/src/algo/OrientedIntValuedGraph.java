package algo;

/**
 * représente un graphe orienté valué par des entiers
 * @author root
 */
public class OrientedIntValuedGraph extends Graph<Integer> {

    /**
     * représente l'infini/pas de valeur
     * Integer.MAX_VALUE/10 pour éviter les débordements lors d'additions etc...
     * @return la valeur formelle de l'infini/pas de valeur
     */
    public static Integer noValue() {
        return Integer.MAX_VALUE / 10;
    }

    public OrientedIntValuedGraph() {

        values = new Integer[DEFAULT_NB_VERTICES][DEFAULT_NB_VERTICES];
        for (int i = 0; i < DEFAULT_NB_VERTICES; i++) {
            for (int j = 0; j < DEFAULT_NB_VERTICES; j++) {
                values[i][j] = noValue();
            }
        }

    }

    /**
     * constructeur par valeur
     * @param mat la matrice d'adjacence du graphe
     */
    public OrientedIntValuedGraph(int[][] mat) {

        //copie en profondeur

        int nbVertices = mat.length;
        values = new Integer[nbVertices][nbVertices];

        for (int i = 0; i < nbVertices; i++) {
            for (int j = 0; j < nbVertices; j++) {
                values[i][j] = mat[i][j];
            }
        }
    }

}
