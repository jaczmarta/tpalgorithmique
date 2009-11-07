/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

/**
 * Représente un graphe pour les problèmes de flots
 * @author Rémi
 */
public class FlowCostGraph extends AbstractGraph<FlowCostValues>
{

    /**
     * constructeur par paramètre
     * @param flowValues la matrice d'adjacence du graphe
     */
    public FlowCostGraph(FlowCostValues[][] flowValues)
    {
        super(flowValues);
    }

    /**
     * constructeur par spécification de la taille du graphe
     * @param size
     */
    public FlowCostGraph(int size)
    {
        super(size);
    }

    /**
     * représente l'abscence de valeur d'un arc dans ce graphe
     * @return l'objet représentant l'abscence de valeur d'un arc
     */
    public FlowCostValues noValue()
    {
        return FlowCostValues.noValue();
    }

    /**
     * retourne la valeur d'un arc (i, j)
     * @param i point d'entrée de l'arc
     * @param j point de sortie de l'arc
     * @return le label de l'arc (i, j)
     */
    @Override
    public FlowCostValues get(int i, int j)
    {
        checkIndex(i);
        checkIndex(j);
        return (FlowCostValues) values[i][j];
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
     * getValue
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
     * setValue
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

    /**
     * mutateur pour un arc
     * @param i point d'entrée de l'arc
     * @param j point de sortie de l'arc
     * @param flowValues le label de l'arc
     */
    @Override
    public void set(int i, int j, FlowCostValues flowValues)
    {
        setFlow(i, j, flowValues.getFlow());
        setCapacity(i, j, flowValues.getCapacity());
        setCost(i, j, flowValues.getCost());
    }

    /**
     * mutateur pour un arc par tous les parametres
     * @param i point d'entree de l'arc
     * @param j point de sortie de l'arc
     * @param flow flot de l'arc
     * @param capacity capacite de l'arc
     * @param cost cout de l'arc
     */
    public void set(int i, int j, int flow, int capacity, int cost) {
        setFlow(i, j, flow);
        setCapacity(i, j, capacity);
        setCost(i, j, cost);

    }

    /**
     * ajoute un entier à un flot
     * @param i point d'entrée de l'arc
     * @param j point de sortie de l'arc
     * @param flow augmentation voulue
     */
    public void addFlow(int i, int j, int flow) {
        setFlow(i, j, getFlow(i, j)+flow);
    }

    /**
     * retourne le flot du graphe
     * @return le flot du graphe, ie la somme des flots des arcs sortants de s
     */
    public int getGraphFlow() {
        int F = 0;
        for (int i = 0 ; i < size() ; i++) {
            F += getFlow( indexOfSource() , i);
        }
        return F;
    }

    /**
     * retourne le cout du graphe
     * @return
     */
    public int getGraphCost() {
        int C = 0;
        for (int i = 0 ; i < size() ; i++) {
            for (int j = 0 ; j < size() ; j++) {
                if ( exists(i, j) ){
                    C += getFlow(i, j)*getCost(i, j);
                }
            }
        }
        return C;

    }

    /**
     * indexOfSource
     * @return index de la source
     */
    public int indexOfSource()
    {
        return 0;
    }

    /**
     * indexOfSing
     * @return index du puit
     */
    public int indexOfSink()
    {
        return size() - 1;
    }

    /**
     * calcule et retourne le graphe d'écart du graphe courant
     * @return le graphe d'écart du graphe courant
     */
    public OrientedValuedGraph getResultingNetwork()
    {

        OrientedValuedGraph Ge = new OrientedValuedGraph(size());

        for (int i = 0; i < size(); i++)
        {
            for (int j = 0; j < size(); j++)
            {

                if (exists(i, j))
                {

                    int xij = getFlow(i, j);
                    int cij = getCapacity(i, j);

                    if (xij < cij)
                    {
                        Ge.setValue(i, j, getCost(i, j));
                    }

                    if (xij > 0)
                    {
                        Ge.setValue(j, i, -getCost(i, j));
                    }
                }
            }
        }

        return Ge;
    }
}