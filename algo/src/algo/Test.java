/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import graphs.FlowCostGraph;
import graphs.RandomGraphBuilder;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

/**
 *
 * @author Rémi
 */
public class Test
{

    private static int DEFAULT_numVerticesFrom = 5;
    private static int DEFAULT_numVerticesTo = 50;
    private static double DEFAULT_density = 0.6;
    private static int DEFAULT_maxCap = 10;
    private static int DEFAULT_numTests = 10;
    private int numVerticesFrom;
    private int numVerticesTo;
    private double density;
    private int maxCap;
    private int numTests;
    private HashMap<Integer, Double> basicResults;
    private HashMap<Integer, Double> busackerGowenResults;
    private int minCap;

    /**
     * constructeur par défaut
     */
    public Test()
    {
        numVerticesFrom = DEFAULT_numVerticesFrom;
        numVerticesTo = DEFAULT_numVerticesTo;
        density = DEFAULT_density;
        maxCap = DEFAULT_maxCap;
        numTests = DEFAULT_numTests;

        basicResults = new HashMap<Integer, Double>();
        busackerGowenResults = new HashMap<Integer, Double>();
    }

    public void runTest()
    {
        try
        {

            File log = new File("log.txt");
            FileWriter fwLog = new FileWriter(log);

            long start;
            long time;
            double avancement = 0;
            ;

            for (int i = 0; i < numTests; i++)
            {
                System.out.println("\navancement : " + (i+1) + "/"+numTests);


                double timeBasic = 0;
                double timeBusackerGowen = 0;

                for (int numVertices = numVerticesFrom; numVertices < numVerticesTo; numVertices++)
                {
                    System.out.print(".");
                    avancement = ((double) ((numVertices - numVerticesFrom) * 100)) / (numVerticesTo - numVerticesFrom);

                    //System.out.print("Test "+i+" - numV : "+numVertices+" - generating...");
                    RandomGraphBuilder builder = new RandomGraphBuilder();
                    builder.setNumVertices(numVertices);
                    builder.setDensity(density);
                    builder.setCapacityLowerBound(minCap);
                    builder.setCapacityUpperBound(maxCap);
                    builder.setCostLowerBound(1);
                    builder.setCostUpperBound(5);

                    FlowCostGraph G2 = builder.generateRandomFlowGraph();

                    start = System.currentTimeMillis();
                    {

                        BusackerGowen bg = new BusackerGowen(G2);
                        bg.runAlgorithm();
                    }
                    time = System.currentTimeMillis() - start;

                    timeBusackerGowen += time;


                    FlowCostGraph G1 = builder.generateRandomFlowGraph();

                    //System.out.print("OK......basic...");

                    start = System.currentTimeMillis();
                    {
                        BasicMaxFlowMinCost bmfmc = new BasicMaxFlowMinCost(G1);
                        bmfmc.runAlgorithm();
                    }
                    time = System.currentTimeMillis() - start;

                    timeBasic += time;

                    if (i == 0)
                    {
                        basicResults.put(numVertices, timeBasic);
                        busackerGowenResults.put(numVertices, timeBusackerGowen);
                    } else
                    {
                        basicResults.put(numVertices, basicResults.get(numVertices) + timeBasic);
                        busackerGowenResults.put(numVertices, busackerGowenResults.get(numVertices) + timeBusackerGowen);
                    }


                }
                
                for (int numVertices = numVerticesFrom; numVertices < numVerticesTo; numVertices++)
                {
                    basicResults.put(numVertices, basicResults.get(numVertices) / numTests);
                    busackerGowenResults.put(numVertices, busackerGowenResults.get(numVertices) / numTests);
                }

               
            }

            fwLog.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        }




    }

    public void outputResults(String outputBasic, String outputBusackerGowen)
    {

        try
        {
            File fileBasic = new File(outputBasic);
            FileWriter fwBasic = new FileWriter(fileBasic);
            File fileBusackerGowen = new File(outputBusackerGowen);
            FileWriter fwBusackerGowen = new FileWriter(fileBusackerGowen);

            for (int n = numVerticesFrom; n < numVerticesTo; n++)
            {
                fwBasic.write(n + " " + basicResults.get(n) + " \n");
                fwBusackerGowen.write(n + " " + busackerGowenResults.get(n) + " \n");
            }

            fwBasic.close();
            fwBusackerGowen.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        }



    }

    /**
     * @return the numVerticesFrom
     */
    public int getNumVerticesFrom()
    {
        return numVerticesFrom;
    }

    /**
     * @param numVerticesFrom the numVerticesFrom to set
     */
    public void setNumVerticesFrom(int numVerticesFrom)
    {
        this.numVerticesFrom = numVerticesFrom;
    }

    /**
     * @return the numVerticesTo
     */
    public int getNumVerticesTo()
    {
        return numVerticesTo;
    }

    /**
     * @param numVerticesTo the numVerticesTo to set
     */
    public void setNumVerticesTo(int numVerticesTo)
    {
        this.numVerticesTo = numVerticesTo;
    }

    /**
     * @return the density
     */
    public double getDensity()
    {
        return density;
    }

    /**
     * @param density the density to set
     */
    public void setDensity(double density)
    {
        this.density = density;
    }

    /**
     * @return the maxCap
     */
    public int getMaxCap()
    {
        return maxCap;
    }

    /**
     * @param maxCap the maxCap to set
     */
    public void setMaxCap(int maxCap)
    {
        this.maxCap = maxCap;
    }

    /**
     * @return the numTests
     */
    public int getNumTests()
    {
        return numTests;
    }

    /**
     * @param numTests the numTests to set
     */
    public void setNumTests(int numTests)
    {
        this.numTests = numTests;
    }

    void setMinCap(int i)
    {
        this.minCap = i;
    }
}
