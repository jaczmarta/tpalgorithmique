/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

/**
 * Représente un graphe de coûts.
 * @author Rémi
 */
public class CostGraph extends Graph<CostValues>
{

    public CostGraph(CostValues[][] costValues)
    {
        super(costValues);
    }

    /**
     * getCost
     * accesseur du cout d'un arc
     * @param i point d'entrée de l'arc
     * @param j point de sortie de l'arc
     * @return le cout de l'arc (i, j)
     */
    public int getCost(int i, int j)
    {
        return values[i][j].getCost();
    }

    /**
     * mutateur du coût d'un arc
     * @param i point d'entrée de l'arc
     * @param j point de sortie de l'arc
     * @param cost cout de l'arc (i, j)
     */
    public void setCost(int i, int j, int cost)
    {
        values[i][j].setCost(cost);
    }
}
