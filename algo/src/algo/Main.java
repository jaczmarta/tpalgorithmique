package algo;

import java.util.ArrayList;

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
                /*          a   b   c   d   e  */
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
        
        //Affichage de la matrice P
        fw.showP();
        
        System.out.println("*************************************************************************");
        
        //matrice d'exemple pour l'affiichage du graphe d'ecart
        int[][][] mat2 = {
                /*		a   b   c   d   e  */
                /* a */{{N, N, N}, {N, N, N}, {N, N, N}, {N, N, N}, {N, N, N}},
                /* b */ {{N, N, N}, {N, N, N}, {N, N, N}, {N, N, N}, {N, N, N}},
                /* c */ {{N, N, N}, {N, N, N}, {N, N, N}, {N, N, N}, {N, N, N}},
                /* d */ {{N, N, N}, {N, N, N}, {N, N, N}, {N, N, N}, {N, N, N}},
                /* e */ {{N, N, N}, {N, N, N}, {N, N, N}, {N, N, N}, {N, N, N}}};

        //on créé le graphe
        ArbritaryGraph G2 = new ArbritaryGraph(mat2, 1);
                
        //Affichage du graphe d'écart
        //G2.generateDistanceGraph().show();
        G2.generatePath(4, 0, 1, 10);

        System.out.println("-----------------------------CHEMIN GENERE---------------------------------");
        G2.show();
        
        FloydWarshall fw2 = new FloydWarshall(G2.getOrientedIntValuedGraphBy(0));
        fw2.runAlgorithm();
        
        System.out.println("---------------------------------DELTA---------------------------------");
        fw2.showDelta();
        
        System.out.println("-----------------------------------P-----------------------------------");
        fw2.showP();
        
        System.out.println("-----------------------------GRAPHE D'ECART---------------------------------");
        G2.generateDistanceGraph().show();
        fw2.showPath(4, 0);
    }
}
