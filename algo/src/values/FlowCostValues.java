/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package values;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

/**
 * représente la valeur d'un arc dans un graphe représentant un problème de flots
 * @author Rémi
 */
public class FlowCostValues extends AbstractValues implements Serializable, Externalizable
{

    private int flow;
    private int capacity;
    private int cost;

    /**
     * constructeur par défaut
     */
    public FlowCostValues()
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
    public FlowCostValues(int flow, int capacity, int cost)
    {
        this.flow = flow;
        this.capacity = capacity;
        this.cost = cost;
    }

    /**
     * constructeur par clonage
     * @param flowValues l'objet à cloner
     */
    public FlowCostValues(FlowCostValues flowValues)
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
        if (equals(noValue()))
        {
            return "-/-/-";
        } else
        {
            return getFlow() + "/" + getCapacity() + "/" + getCost();
        }
    }

    /**
     * toString sans afficher les arcs sans flot
     */
    public String toStringWithoutZeros()
    {
        if (equals(noValue()) || (getFlow() == 0))
        {
            return "-/-/-";
        } else
        {
            return getFlow() + "/" + getCapacity() + "/" + getCost();
        }
    }

    /**
     * equals
     * @param flowValues paramètre à comparer
     * @return faux si le paramètre est null ou si l'un des paramètre de l'arc est différent de celui de this
     */
    public boolean equals(IValues flowValues)
    {
        if (flowValues == null)
        {
            return false;
        }
        if ( (((FlowCostValues) flowValues).getFlow() != getFlow()) && (((FlowCostValues) flowValues).getFlow() != 0))
        {
            return false;
        }
        if (((FlowCostValues) flowValues).getCapacity() != getCapacity())
        {
            return false;
        }
        if (((FlowCostValues) flowValues).getCost() != getCost())
        {
            return false;
        }

        return true;

    }

    /**
     * abscence de valeur
     * @return flot, capacité, cout à infini
     * @see IValues
     */
    public static FlowCostValues noValue()
    {
        return new FlowCostValues(IValues.infinity, IValues.infinity, IValues.infinity);
    }

	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.flow = in.readInt();
		this.capacity = in.readInt();
		this.cost = in.readInt();
	}

	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(flow);
		out.writeInt(capacity);
		out.writeInt(cost);
		
	}

}
