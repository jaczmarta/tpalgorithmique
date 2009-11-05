/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

/**
 * représente la valeur d'un arc dans un graphe représentant un problème de flots
 * @author Rémi
 */
public class FlowValues extends AbstractValues
{

    private int flow;
    private int capacity;
    private int cost;

    /**
     * constructeur par défaut
     */
    public FlowValues()
    {
        flow = 0;
        capacity = 0;
        cost = 0;
    }

    /**
     * constructeur par valeurs
     * @param flow la valeur du flot
     * @param capacity la capacité
     * @param cost le cout
     */
    public FlowValues(int flow, int capacity, int cost)
    {
        setFlow(flow);
        setCapacity(capacity);
        setCost(cost);
    }

    /**
     * constructeur par clonage
     * @param flowValues l'objet à cloner
     */
    public FlowValues(FlowValues flowValues)
    {
        setFlow(flowValues.getFlow());
        setCapacity(flowValues.getCapacity());
        setCost(flowValues.getCost());
    }

    /**
     * @return the flow
     */
    public int getFlow()
    {
        return flow;
    }

    /**
     * @param flow the flow to set
     */
    public void setFlow(int flow)
    {
        this.flow = flow;
    }

    /**
     * @return the capacity
     */
    public int getCapacity()
    {
        return capacity;
    }

    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
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
     * toString
     */
    @Override
    public String toString()
    {
        if (equals(noValue())) {
            return "-/-/-";
        } else {
            return getFlow() + "/" + getCapacity() + "/" + getCost();
        }
    }

    /**
     * equals
     * @param flowValues paramètre à comparer
     * @return faux si le paramètre est null ou si l'un des paramètre de l'arc est différent de celui de this
     */
    public boolean equals(FlowValues flowValues)
    {
        if (flowValues == null)
        {
            return false;
        }
        if (flowValues.getFlow() != getFlow())
        {
            return false;
        }
        if (flowValues.getCapacity() != getCapacity())
        {
            return false;
        }
        if (flowValues.getCost() != getCost())
        {
            return false;
        }

        return true;

    }

    /**
     * abscence de valeur
     * @return flot, capacité, cout à -1
     */
    public static FlowValues noValue()
    {
        return new FlowValues(-1, -1, -1);
    }
}
