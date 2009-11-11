package graphs;

import java.util.ArrayList;
import java.util.List;

import values.FlowCostValues;

public class RandomGraphBuilder
{

    private static int DEFAULT_numVertices = 5;
    private static int DEFAULT_numEdges = 0;
    private static int DEFAULT_capacity_LowerBound = 1;
    private static int DEFAULT_capacityUpperBound = 1;
    private static int DEFAULT_costLowerBound = 1;
    private static int DEFAULT_costUpperBound = 1;
    
    private int numVertices;
    private int numEdges;
    private int capacityLowerBound;
    private int capacityUpperBound;
    private int costLowerBound;
    private int costUpperBound;
    

	/**
     * constructeur
     * fixe tous les paramètres à leurs valeurs par défaut
     */
    public RandomGraphBuilder()
    {
        numEdges			= DEFAULT_numEdges;
        numVertices 		= DEFAULT_numVertices;
        capacityLowerBound 	= DEFAULT_capacity_LowerBound;
        capacityUpperBound 	= DEFAULT_capacityUpperBound;
        costLowerBound 		= DEFAULT_costLowerBound;
        costUpperBound 		= DEFAULT_costUpperBound;
    }

    /**
     * generateRandomFlowGraph
     * génère un graphe
     * @return le graphe généré
     */
    public FlowCostGraph generateRandomFlowGraph()
    {

        FlowCostGraph G = new FlowCostGraph(getNumVertices());
        int edgesCounter = 0;
        
        //création chemin entre le premier et le dernier sommet
        {
        	//nombre de sommets intermédiaires (pas de chemin direct de s à t)
            int numVerticesForThisPath = generateInteger(1, getNumVertices() - 2);
            //liste des sommets rencontrés sur le chemin de s à t
            List<Integer> verticesOnThePath = new ArrayList<Integer>();
            
            //ajout du sommet source
            verticesOnThePath.add(G.indexOfSource());
            
            int k = 0;
            //ajout des sommets intermédiaires
            while (k < numVerticesForThisPath)
            {
            	//le puit ne peut être un intermédiaire            	
                int randomVertex = generateNewNext(G);
                if (!verticesOnThePath.contains(randomVertex))
                {
                	verticesOnThePath.add(randomVertex);
                	k++;
                }
            }

            int i = 0;
            //création des arcs
            while (i < numVerticesForThisPath)
            {
                G.set(verticesOnThePath.get(i), verticesOnThePath.get(i + 1), getRandomValues());
                i++;
                edgesCounter++;
            }
            G.set(verticesOnThePath.get(i), G.indexOfSink(), getRandomValues());
            edgesCounter++;
        }
        
        //création des derniers arcs
        {
        	if (edgesCounter > getNumEdges())
        	{
        		System.err.println("Warning: RandomGraphBuilder::generateRandomFlowGraph : enough edges have been created during the initialization");
        	}
        	else
        	{
	            while (edgesCounter <= getNumEdges()-1)
	            {
	                int i, j;

                    do
	                {
	                    i = generateNewOrigin(G);
	                    j = generateNewDestination(G);
	
	                } while (G.exists(i, j) || 
	                		((!G.exists(i, j)) && (G.exists(j, i))) || //arcs retours
	                		(i == j) ||
	                		(i == G.indexOfSink()) || //origine != puit
	                		(j == G.indexOfSource()) || //destination != source
	                		((i == G.indexOfSource()) && (j == G.indexOfSink())) //pas de chemin direct de s à t
	                		) ;
                    
	                G.set(i, j, getRandomValues());
	                edgesCounter++;
	                
	                /*
	                 * si le sommet origine n'est pas la source du graphe, on vérifie que cette origine n'est pas
	                 * une source isolée
	                 */
	                if (i != G.indexOfSource())
	                {
	                	//si i est une source est isolée différente de la source du graphe
		                while ((G.isSource(i)) && (i != G.indexOfSource()))
		                {
		                	int k = generateNewOrigin(G);
		                	while (G.exists(i, k) || 
			                		((!G.exists(i, k)) && (G.exists(k, i))) || //arcs retours
			                		(i == k))
		                	{
		                		//k != puit du graphe
		                		k = generateNewOrigin(G);
		                	}
			                G.set(k, i, getRandomValues());
			                edgesCounter++;
			                i = k;
		                }
	                }
	                
	                /*
	                 * si le sommet destination n'est pas le puit du graphe, on vérifie que cette destination 
	                 * n'est pas un puit
	                 */
	                if (j != G.indexOfSink())
	                {
		                while ((G.isSink(j) && (j != G.indexOfSink())))
		                {
		                	int k = generateNewDestination(G);
		                	while (G.exists(k, j) || 
			                		((!G.exists(k, j)) && (G.exists(j, k))) || //arcs retours
			                		(j == k)) 
		                	{
		                		// k != source du graphe
		                		k = generateNewDestination(G);
		                	}
			                G.set(j, k, getRandomValues());
			                edgesCounter++;
			                j = k;
		                }
	                }
	            }
            }
        }
        return G;

    }

    /**
     * @return un sommet origine différent du puit du graphe
     */
    public int generateNewOrigin(FlowCostGraph G) 
    {
    	int origin;
    	do
    	{
    		origin = generateInteger(0, getNumVertices() - 1);
    	} 
    	while (origin == G.indexOfSink());
    	
    	//le puit ne doit pas avoir d'arc sortant (i.e. ne peut être une source)
    	return origin;
    }
    
    /**
     * @return un sommet destination different de la source du graphe
     */
    public int generateNewDestination(FlowCostGraph G) 
    {
    	int destination;
    	do
    	{
    		destination = generateInteger(0, getNumVertices() - 1);
    	} 
    	while (destination == G.indexOfSource());
    	
        //la source ne doit pas avoir d'arc entrant (i.e. ne peut être une cible)
    	return destination;
    }
    
    /**
     * @return un sommet intermédiaire
     */
    public int generateNewNext(FlowCostGraph G) 
    {
    	int next;
    	do
    	{
    		next = generateInteger(0, getNumVertices() - 1);
    	} 
    	while ((next == G.indexOfSource()) || (next == G.indexOfSink()));
    	
    	return next;
    }
    
    /**
     * getRandomValues
     * retourne un objet FlowValue aléatoire conformément aux bornes fixées
     * @return un FlowValue aléatoire conformément aux bornes fixées
     */
    private FlowCostValues getRandomValues()
    {
        int capacity 	= generateInteger(getCapacityLowerBound(), getCapacityUpperBound());
        int cost 		= generateInteger(getCostLowerBound(), getCostUpperBound());
        
        return new FlowCostValues(0, capacity, cost);
    }

    /**
     * @return the numVertices
     */
    public int getNumVertices()
    {
        return numVertices;
    }

    /**
     * @param numVertices the numVertices to set
     */
    public void setNumVertices(int numVertices)
    {
        this.numVertices = numVertices;
    }

    /**
     * @return the numEdges
     */
    public int getNumEdges()
    {
        return numEdges;
    }

    /**
     * @param numEdges the numEdges to set
     */
    public void setNumEdges(int numEdges)
    {
        this.numEdges = numEdges;
    }

    /**
     * @return the capacityLowerBound
     */
    public int getCapacityLowerBound()
    {
        return capacityLowerBound;
    }

    /**
     * @param capacityLowerBound the capacityLowerBound to set
     */
    public void setCapacityLowerBound(int capacityLowerBound)
    {
        this.capacityLowerBound = capacityLowerBound;
    }

    /**
     * @return the capacityUpperBound
     */
    public int getCapacityUpperBound()
    {
        return capacityUpperBound;
    }

    /**
     * @param capacityUpperBound the capacityUpperBound to set
     */
    public void setCapacityUpperBound(int capacityBound)
    {
        this.capacityUpperBound = capacityBound;
    }

   

    public int getCostLowerBound() {
		return costLowerBound;
	}

	public void setCostLowerBound(int costLowerBound) {
		this.costLowerBound = costLowerBound;
	}

	public int getCostUpperBound() {
		return costUpperBound;
	}

	public void setCostUpperBound(int costUpperBound) {
		this.costUpperBound = costUpperBound;
	}

	/**
     * setDensity
     * fixe la densité du graphe en fonction du nombre max d'arcs => densié = 1 <=> graphe complet
     * densite = nombre d'arcs/nombre d'arcs possibles
     */
    public void setDensity(double density)
    {
            if ((density < 0) || (density > 1))
            {
                throw new IllegalArgumentException("Warning: RandomGraphBuilder::setDensity : invalid argument. Density not set");
            } else
            {
                setNumEdges((int) (density * maxEdges()));                
            }
    }

    /**
     * getDensity
     * accesseur densité du graphe
     * densité = nombre d'arcs/nombre d'arcs possibles
     */
    public double getDensity()
    {
        //on interdit les boucles
        return getNumVertices() * (getNumEdges() - 1);
    }

    /**
     * @return un entier comprit entre min et max
     */
    public static int generateInteger(int min, int max)
    {
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }
    
    /**
     * 
     * @return le nombre maximal d'aretes ( n(n-1) / 2 ) - 1 (car pas d'arete de s a t)
     */
    public int maxEdges() {
    	return ((getNumVertices() * (getNumVertices() -1 )) / 2) - 1;
    }
    
    /**
     * 
     * @return le nombre minimal d'aretes (i.e. le nombre de sommets)
     */
    public int minEdges() {
    	return getNumVertices();
    }
}