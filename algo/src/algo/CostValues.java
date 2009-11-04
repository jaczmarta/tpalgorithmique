/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

/**
 *
 * @author Rémi
 */
public class CostValues
{

    private int cost;

    public CostValues(int cost)
    {
        setCost(cost);
    }

    /**
     * @return the cost
     */
    public int getCost()
    {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(int cost)
    {
        this.cost = cost;
    }

    /**
     * abscence de cout
     * @return un cout à -1
     */
    public static CostValues noValue()
    {
        return new CostValues(-1);
    }
}
