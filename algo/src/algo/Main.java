/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

/**
 *
 * @author remi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.out.println("*************************************************************************");

        //matrice du sujet du tp
        Integer N = OrientedIntValuedGraph.noValue();

        int[][] mat = {
            /*		a   b   c   d   e  */
            /* a */{N, N, N, N, N},
            /* b */ {N, N, N, 3, N},
            /* c */ {N, -1, N, N, -7},
            /* d */ {1, N, 8, N, 1},
            /* e */ {2, N, N, N, N},};

        //on créé le graphe
        OrientedIntValuedGraph G1 = new OrientedIntValuedGraph(mat);

        //on créé une instance de l'algo de Floyd Warshall
        FloydWarshall fw = new FloydWarshall(G1);

        //on lance l'algo de Floyd Warshall
        fw.runAlgorithm();

        //Affichage de la matrice Delta
        fw.showDelta();

        System.out.println("*************************************************************************");



    }
}
