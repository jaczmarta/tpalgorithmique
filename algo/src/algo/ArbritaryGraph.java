package algo;

import java.util.Random;

/**
 * Sous-classe de Graph<Type>
 * => le tableau d'entier correspondant au Type contient les paramètres des arcs.
 * Arbitrairement, on a:
 * 		- 0 => flot de l'arc
 * 		- 1 => capacité
 * 		- 2 => cout
 * On pourra en rajouter par la suite (flotMin par exemple)
 * 
 * @author tolivieri
 *
 */
public class ArbritaryGraph extends Graph<int[]> {
	
	/**
	 * represente le nombre de parametres d'un arc (flot, cout, capacite, etc...)
	 * 
	 */
	private int nbParams;
	
	/**
     * représente l'infini/pas de valeur
     * Integer.MAX_VALUE/10 pour éviter les débordements lors d'additions etc...
     * @return la valeur formelle de l'infini/pas de valeur
     */
    public static Integer noValue() {
        return Integer.MAX_VALUE / 10;
    }
    
    public ArbritaryGraph() {
    	
    }
    
    /**
     * Constructeur d'initialisation: chaque arc prend "noValue()" comme valeur
     * @param nbVertices le nombre de sommets
     * @param nbParams le nombre de paramètres par arc
     * @see noValue()
     * @see nbParams
     */
    public ArbritaryGraph(int nbVertices, int nbParams) {
    	this.nbParams = nbParams;
    	values = new int[nbVertices][nbVertices][nbParams];
    	for (int i = 0; i < nbVertices; i++) {
            for (int j = 0; j < nbVertices; j++) {
            	for (int k = 0; k < nbParams; k++) {
            		values[i][j][k] = noValue();
            	}
            }
        }
    }
    
    /**
     * Constructeur par copie
     * @param G
     */
    public ArbritaryGraph(ArbritaryGraph G) {
    	this.nbParams = G.nbParams;
    	values = new int[G.values.length][G.values.length][nbParams];
    	for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values.length; j++) {
            	for (int k = 0; k < nbParams; k++) {
            		values[i][j][k] = G.values[i][j][k];
            	}
            }
        }
    }
    
    /**
     * Constructeur par valeur
     * @param mat
     * @param nbParams le nombre de paramètres (nécessaire!)
     */
    public ArbritaryGraph(int[][][] mat, int nbParams) {
    	//copie en profondeur

    	this.nbParams = nbParams;
        int size = mat.length;
        this.values = new int[size][size][5];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
            	for (int k = 0; k < nbParams; k++) {
            		values[i][j][k] = mat[i][j][k];
            	}
            }
        }
    }
    
    /**
     * Modifie les paramètres (cout, capacite, flot, etc...) de l'arc (i, j)
     * @param i le sommet de départ
     * @param j le sommet d'arrivée
     * @param v le tableau des nouveaux paramètres
     */
    public void set(Integer i, Integer j, Integer[] v) {
        try {
            if ((i < 0) || (j < 0) || (i >= values.length) || (j >= values.length)) {
                throw new ArrayIndexOutOfBoundsException("Graph::set : (" + i + ", " + j + ")");
            } else {
            	for (int k = 0; k < v.length; k++) {
	                values[i][j][k] = v[k];
            	}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrourne le nombre de sommets (ou taille) du graphe
     * @return le nombre de sommets
     */
    protected int size() {
        return values.length;
    }
    

    /**
     * Genere un graphe d'ecart
     * On considère que 
     * 		- getParamAt(i, j, 0) retourne le flot de l'arc (i,j)
     * 		- getParamAt(i, j, 1) retourne la capacité de l'arc (i, j)
     */
    public ArbritaryGraph generateDistanceGraph() {
    	ArbritaryGraph Ge = new ArbritaryGraph(this.values.length, nbParams);
		for (int i = 0; i < size(); i++) {
			for (int j = 0; j < size(); j++) {
				if (!((Integer)getParamAt(i, j, 0)).equals(noValue())) {
					Ge.setParamAt	(j, i, 0, getParamAt(i, j, 0));
					
					if (getParamAt(i, j, 0) < getParamAt(i, j, 1)) { //arc non saturé			
						Ge.setParamAt	(i, j, 0, getParamAt(i, j, 1) - getParamAt(i, j, 0));
					}
				}
			}					
		}
		return Ge;
	}
    
    /**
     * Retourne des paramètres de l'arc (i, j)
     * Par exemple: getParamAt(1, 3, 1) retourne la capacité de l'arc (1, 3)
     * (si les capacités sont à l'indice 1 dans values[i][j])
     * @param i
     * @param j
     * @param numParam
     * @return
     */
    public int getParamAt(int i, int j, int numParam) {
    	try {
    		return values[i][j][numParam];
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		return noValue();
    	}
    }
    
    /**
     * Modification du parametre "numParam" de l'arc (i, j)
     * Par exemple: setParamAt(0, 1, 0, 5) regle le flot de l'arc (0, 1) à 5 (si les valeurs de flots sont 
     * stockées dans values[i][j][0]).
     * @param i origine de l'arc à modifier
     * @param j destination
     * @param numParam l'indice correspondant au paramètre à modifier
     * @param val la nouvelle valeur
     */
    public void setParamAt(int i, int j, int numParam, int val) {
    	try {
    		values[i][j][numParam] = val;
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Affichage
     */
    public void show() {
    	try {
            if (values == null) {
                throw new NullPointerException("ArbritaryGraph::show : values==null");
            }
            else {
                String str = "";
                int T = values.length;
                for (int i = 0; i < T; i++) {
                    for (int j = 0; j < T; j++) {
                    	for (int k = 0; k < nbParams; k++) {
                    		str += values[i][j][k];
                    		if (k == nbParams - 1)
                    			str += "\t";
                    		else
                    			str += "/";
                    	}             	
                    }
                    str += "\n";
                }
                System.out.println(str.replace(noValue().toString(), "-"));
            }

        }
    	catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Genere un chemin de i a j
     * @param source l'origine du chemin
     * @param target la destination
     * @param min la valeur minimale du label (cout, flot, capacite, etc...) d'un arcs
     * @param max la valeur max d'un arcs
     * @return un graphe orienté valué comportant un chemin de source a target
     */
    public OrientedIntValuedGraph generatePath(int source, int target, int min, int max) {
    	OrientedIntValuedGraph result = getOrientedIntValuedGraphBy(0);
    	FloydWarshall tmpFloyd = new FloydWarshall(result);
    	tmpFloyd.runAlgorithm();
    	int[][] tmpDelta = tmpFloyd.getDelta();
    	
    	while (tmpDelta[source][target] == noValue()) {

    		int k = new Random().nextInt(tmpDelta.length);
    		if (tmpDelta[source][k] == noValue()) {
    			
    			int tmpVal = generateInteger(min, max);

    			result.set(source, k, tmpVal);
    			tmpFloyd = new FloydWarshall(result);
    			tmpDelta = tmpFloyd.getDelta();
    			
    			values[source][k][0] = tmpVal;
    			
    			generatePath(source, k, min, max);
    			generatePath(k, target, min, max);
    		}
    	}
    	return result;
    }
    
    /**
     * Crée un graphe orienté valué à partir d'une des caracteristiques: graphe des cout, graphe des flots, etc...
     * @param numParam
     * @return
     */
    public OrientedIntValuedGraph getOrientedIntValuedGraphBy(int numParam) {
    	int[][] tmpMatrix = new int[values.length][values.length];
    	for (int i = 0; i < values.length; i++) {
    		for (int j = 0; j < values.length; j++) {
    			tmpMatrix[i][j] = values[i][j][numParam];
    		}
    	}
    	return new OrientedIntValuedGraph(tmpMatrix);
    }
}
