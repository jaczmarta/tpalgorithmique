package algo;

import graphs.FlowCostGraph;
import graphs.RandomGraphBuilder;

/**
 *
 * @author remi
 */
public class Main
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        /*
        System.out.println("----------------------------- FLOYD - WARSHALL ---------------------------------");
        {
        OrientedValuedGraph G = new OrientedValuedGraph(5);
        G.setValue(1, 3, 3);
        G.setValue(2, 1, -1);
        G.setValue(2, 4, -7);
        G.setValue(3, 0, 1);
        G.setValue(3, 2, 8);
        G.setValue(3, 4, 1);
        G.setValue(4, 0, 2);

        FloydWarshall fw = new FloydWarshall(G);
        fw.runAlgorithm();
        fw.showDelta();
        }

        System.out.println("----------------------------- FLOYD - WARSHALL CIRCUIT ABSORBANT ---------------------------------");
        {
        OrientedValuedGraph G = new OrientedValuedGraph(6);
        G.setValue(0, 1, 3);
        G.setValue(1, 2, -1);
        G.setValue(1, 5, 3);
        G.setValue(2, 3, -2);
        G.setValue(3, 4, 1);
        G.setValue(4, 1, -3);

        FloydWarshall fw = new FloydWarshall(G);
        fw.runAlgorithm();
        //fw.showP();
        System.out.println(fw.getCircuitWithNegativeCost());
        }

        System.out.println("-----------------------------GRAPHE D'ECART ---------------------------------");
        {
        //Exemple ex1 feuille td 2
        FlowCostGraph fg = new FlowCostGraph(6);
        fg.set(0, 1, 2, 2, 1);
        fg.set(0, 3, 0, 4, 1);
        fg.set(1, 2, 1, 1, 1);
        fg.set(1, 4, 0, 2, 1);
        fg.set(1, 3, 1, 2, 1);
        fg.set(2, 5, 1, 2, 1);
        fg.set(3, 4, 1, 1, 1);
        fg.set(4, 5, 1, 2, 1);

        fg.getResultingNetworkWithCosts().show();
        System.out.println(fg.getResultingNetworkWithCosts().getShortestPath(0, 5));
        }

         *//*
        System.out.println("-----------------------------GRAPHE ALEATOIRE---------------------------------");
        {

        RandomGraphBuilder builder = new RandomGraphBuilder();
        builder.setNumVertices(5000);
        builder.setDensity(0.4);
        builder.setCapacityUpperBound(5);
        builder.setCostLowerBound(1);
        builder.setCostUpperBound(15);
        builder.generateRandomFlowGraph();//.show();
        }

         *//*
        System.out.println("-----------------------------BUSACKER ET GOWEN ---------------------------------");
        {
        //Exemple ex2 feuille td 5
        FlowCostGraph fg = new FlowCostGraph(7);
        fg.set(0, 1, 0, 5, 4);
        fg.set(0, 2, 0, 8, 2);
        fg.set(1, 3, 0, 4, 8);
        fg.set(1, 4, 0, 2, 5);
        fg.set(2, 4, 0, 5, 2);
        fg.set(2, 5, 0, 2, 6);
        fg.set(3, 6, 0, 7, 3);
        fg.set(4, 6, 0, 3, 5);
        fg.set(5, 6, 0, 3, 4);

        BusackerGowen bg = new BusackerGowen(fg);
        bg.runAlgorithm();
        //bg.getG().showWithoutZeros();
        System.out.println("Busacker et Gowen : ");
        System.out.println("Flot = " + bg.getG().getGraphFlow());
        System.out.println("Cout = " + bg.getG().getGraphCost());

        BasicMaxFlowMinCost bmfmc = new BasicMaxFlowMinCost(fg);
        bmfmc.runAlgorithm();
        //bmfmc.getG().show();
        System.out.println("Basique :");
        System.out.println("Flot = " + bmfmc.getG().getGraphFlow());
        System.out.println("Cout = " + bmfmc.getG().getGraphCost());
        }


        /*
        System.out.println("-----------------------------ALGO DE BASE FLOTMAXCOUTMIN ---------------------------------");
        {
        FlowCostGraph fg = new FlowCostGraph(5);
        fg.set(0, 2, 0, 3,8);
        fg.set(2, 4, 0, 8,7);
        fg.set(2, 3, 0, 3,8);
        fg.set(2, 1, 0, 3,4);
        fg.set(3, 4, 0, 2,6);
        fg.set(1, 3, 0, 7,4);

        BusackerGowen bg = new BusackerGowen(fg);
        bg.runAlgorithm();
        //bg.getG().show();
        System.out.println("\nBusacker et Gowen :");
        System.out.println("Flot = " + bg.getG().getGraphFlow());
        System.out.println("Cout = " + bg.getG().getGraphCost());

        FordFulkerson fw = new FordFulkerson(fg);
        //fw.getMaxFlowGraph().show();

        BasicMaxFlowMinCost bmfmc = new BasicMaxFlowMinCost(fg);
        bmfmc.runAlgorithm();
        //bmfmc.getG().show();
        System.out.println("Basique :");
        System.out.println("Flot = " + bmfmc.getG().getGraphFlow());
        System.out.println("Cout = " + bmfmc.getG().getGraphCost());

        }

         *//*

        System.out.println("-----------------------------BUSACKER ET GOWEN SUR GRAPHE ALEATOIRE ---------------------------------");
        {

        RandomGraphBuilder builder = new RandomGraphBuilder();
        builder.setNumVertices(300);
        builder.setDensity(0.7);
        builder.setCapacityLowerBound(5);
        builder.setCapacityUpperBound(10);
        builder.setCostLowerBound(1);
        builder.setCostUpperBound(100);

        FlowCostGraph G = builder.generateRandomFlowGraph();
        //G.show();


        BusackerGowen bg = new BusackerGowen(G);
        bg.runAlgorithm();
        //bg.getG().show();
        System.out.println("Busacker et Gowen :");
        System.out.println("Flot = " + bg.getG().getGraphFlow());
        System.out.println("Cout = " + bg.getG().getGraphCost());


        }
        /*

        System.out.println("-----------------------------ALGO BASIQUE SUR GRAPHE ALEATOIRE ---------------------------------");
        {

        RandomGraphBuilder builder = new RandomGraphBuilder();
        builder.setNumVertices(140);
        builder.setDensity(0.8);
        builder.setCapacityLowerBound(5);
        builder.setCapacityUpperBound(200);
        builder.setCostLowerBound(1);
        builder.setCostUpperBound(100);

        FlowCostGraph G = builder.generateRandomFlowGraph();


        BasicMaxFlowMinCost bmfmc = new BasicMaxFlowMinCost(G);
        bmfmc.runAlgorithm();

        System.out.println("Algo basique :");
        System.out.println("Flot = " + bmfmc.getG().getGraphFlow());
        System.out.println("Cout = " + bmfmc.getG().getGraphCost());


        }

         *//*

        System.out.println("----------------------------- TEST DES DEUX ALGOS ---------------------------------");
        {
            int nbTests = 1000;
            int cpt = 0;
            int faux = 0;


            while (cpt < nbTests)
            {
                cpt++;

                //System.out.print(cpt + "...");



                RandomGraphBuilder builder = new RandomGraphBuilder();
                builder.setNumVertices(30);
                builder.setDensity(0.7);
                builder.setCapacityLowerBound(2);
                builder.setCapacityUpperBound(100);
                builder.setCostLowerBound(1);
                builder.setCostUpperBound(10);

                FlowCostGraph G = builder.generateRandomFlowGraph();

                //System.out.print("generated...");


                BusackerGowen bg = new BusackerGowen(G);
                bg.runAlgorithm();

                //System.out.print("Busacker...");

                BasicMaxFlowMinCost bmfmc = new BasicMaxFlowMinCost(G);
                bmfmc.runAlgorithm();

                //System.out.print("Gowen...");

                boolean testFlot = (bg.getG().getGraphFlow() == bmfmc.getG().getGraphFlow());
                boolean testCout = (bg.getG().getGraphCost() == bmfmc.getG().getGraphCost());

                //System.out.print("test...");

                if (!testFlot || !testCout)
                {
                    faux++;
                    //System.out.println("NOTok");
                } else {
                    //System.out.println("ok");
                }


            }

            System.out.println("faux: " + faux);
        }
        *//*
        System.out.println("----------------------------- PARTIE PRATIQUE ---------------------------------");
        {
        FlowCostGraph fg = new FlowCostGraph(6);
        fg.set(0, 1, 0, 4, 3);
        fg.set(0, 2, 0, 1, 2);
        fg.set(1, 3, 0, 3, 2);
        fg.set(1, 4, 0, 1, 2);
        fg.set(2, 1, 0, 2, 2);
        fg.set(2, 4, 0, 1, 2);
        fg.set(3, 5, 0, 2, 4);
        fg.set(4, 3, 0, 2, 1);
        fg.set(4, 5, 0, 3, 3);

        BusackerGowen bg = new BusackerGowen(fg);
        bg.runAlgorithm();
        //bg.getG().show();
        System.out.println("\nBusacker et Gowen :");
        System.out.println("Flot = " + bg.getG().getGraphFlow());
        System.out.println("Cout = " + bg.getG().getGraphCost());


        BasicMaxFlowMinCost bmfmc = new BasicMaxFlowMinCost(fg);
        bmfmc.runAlgorithm();
        //bmfmc.getG().show();
        System.out.println("Basique :");
        System.out.println("Flot = " + bmfmc.getG().getGraphFlow());
        System.out.println("Cout = " + bmfmc.getG().getGraphCost());

        }

         */
         System.out.println("----------------------------- TESTS ---------------------------------");
        {

             Test test = new Test();
             test.setNumVerticesFrom(7);
             test.setNumVerticesTo(50);
             test.setNumTests(50);

             
             test.setDensity(0.3);
             test.setMaxCap(5);
             test.runTest();
             test.outputResults("basic01.txt", "busacker01.txt");

             test.setDensity(0.5);
             test.setMaxCap(100);
             test.runTest();
             test.outputResults("basic02.txt", "busacker02.txt");

             test.setDensity(0.7);
             test.setMaxCap(10000);
             test.runTest();
             test.outputResults("basic03.txt", "busacker03.txt");
        }

            

    }
}
