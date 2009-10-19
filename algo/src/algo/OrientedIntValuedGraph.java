/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

    /**
     * constructeur par défaut
     */
    public OrientedIntValuedGraph() {

        value = new Integer[DEFAULT_NUM_NODES][DEFAULT_NUM_NODES];
        for (int i = 0; i < DEFAULT_NUM_NODES; i++) {
            for (int j = 0; j < DEFAULT_NUM_NODES; j++) {
                value[i][j] = noValue();
            }
        }

    }

    /**
     * constructeur par valeur
     * @param mat la matrice d'adjacence du graphe
     */
    public OrientedIntValuedGraph(int[][] mat) {

        //copie en profondeur

        int T = mat.length;
        this.value = new Integer[T][T];

        for (int i = 0; i < T; i++) {
            for (int j = 0; j < T; j++) {
                this.value[i][j] = mat[i][j];
            }
        }
    }
}
