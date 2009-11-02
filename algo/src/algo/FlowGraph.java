/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

/**
 *
 * @author Rémi
 */
public class FlowGraph extends Graph<FlowValues>
{

    /**
     * constructeur par paramètre
     * @param flowValues la matrice d'adjacence du graphe
     */
    public FlowGraph(FlowValues[][] flowValues)
    {
        super(flowValues);
    }

    /**
     * getFlow
     * accesseur flot de l'arc (i, j)
     * @param i point d'entrée de l'arc
     * @param j point de sortie de l'arc
     * @return flow la valeur du flot à affecter
     *
     */
    public int getFlow(int i, int j)
    {
        return this.get(i, j).getFlow();
    }

    /**
     * getCapacity
     * accesseur de la capacité de l'arc (i, j)
     * @param i point d'entrée de l'arc
     * @param j point de sortie de l'arc
     * @return capacity capacité à affecter
     */
    public int getCapacity(int i, int j)
    {
        return this.get(i, j).getCapacity();
    }

    /**
     * getCost
     * accesseur du cout de l'arc (i, j)
     * @param i point d'entree de l'arc
     * @param j point de sortie de l'arc
     * @return cost cout à affecter
     *
     */
    public int getCost(int i, int j)
    {
        return this.get(i, j).getCost();
    }

    /**
     * setFlow
     * mutateur flot de l'arc (i, j)
     * @param i point d'entrée de l'arc
     * @param j point de sortie de l'arc
     * @param flow la valeur du flot à affecter
     *
     */
    public void setFlow(int i, int j, int flow)
    {
        this.get(i, j).setFlow(flow);
    }

    /**
     * setCapacity
     * mutateur de la capacité de l'arc (i, j)
     * @param i point d'entrée de l'arc
     * @param j point de sortie de l'arc
     * @param capacity capacité à affecter
     */
    public void setCapacity(int i, int j, int capacity)
    {
        this.get(i, j).setCapacity(capacity);
    }

    /**
     * setCost
     * mutateur du cout de l'arc (i, j)
     * @param i point d'entree de l'arc
     * @param j point de sortie de l'arc
     * @param cost cout à affecter
     * @param i
     * @param j
     * @param cost
     */
    public void setCost(int i, int j, int cost)
    {
        this.get(i, j).setCost(cost);
    }

    public void show()
    {
        try
        {
            if (values == null)
            {
                throw new NullPointerException("ArbritaryGraph::show : values==null");
            } else
            {
                String str = "";

                int T = ((Object[][]) values).length;

                for (int i = 0; i < T; i++)
                {
                    for (int j = 0; j < T; j++)
                    {
                        if (noValue().equals(get(i, j)))
                        {
                            str += noValue().toString();
                        } else
                        {
                            str += get(i, j).toString();
                        }
                        str += "\t";
                    }
                    str += "\n";
                }
                System.out.println(str.replace(noValue().toString(), "-/-/-"));
            }


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static FlowValues noValue()
    {
        return new FlowValues(-1, -1, -1);
    }
}
