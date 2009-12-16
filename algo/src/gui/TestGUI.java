package gui;

import graphs.FlowCostGraph;
import graphs.RandomGraphBuilder;

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import algo.BasicMaxFlowMinCost;
import algo.BusackerGowen;

public class TestGUI extends Window
{

    private static final long serialVersionUID = 1L;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem openMenuItem;
    JMenuItem quitMenuItem;
    StringBuffer choices;
    int numVertices = 10;
    JTextField numVerticesEntry;
    JLabel verticesLabel;
    int numEdges;
    double density = 0.5;
    JTextField densityEntry;
    JLabel densityLabel;
    int minCost = 1;
    JTextField minCostEntry;
    JLabel minCostLabel;
    int maxCost = 5;
    JTextField maxCostEntry;
    JLabel maxCostLabel;
    int minCapacity = 1;
    JTextField minCapacityEntry;
    JLabel minCapacityLabel;
    int maxCapacity = 5;
    JTextField maxCapacityEntry;
    JLabel maxCapacityLabel;
    int numTests = 1;
    JTextField numTestsEntry;
    JLabel numTestsLabel;
    JButton createGraphButton;

    public TestGUI()
    {
        super("Test des méthodes de FlotMaxCoutMin");
        init();
        setVisible(true);
        pack();
    }

    public void init()
    {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMenuBar();

        GridBagConstraints grid = new GridBagConstraints();
        getContentPane().setLayout(new GridBagLayout());
        grid.insets = new Insets(5, 5, 5, 5);  //top padding
        {
            verticesLabel = new JLabel();
            verticesLabel.setText("Nombre de sommets:");
            grid.gridx = 0;
            grid.gridy = 0;
            getContentPane().add(verticesLabel, grid);

            numVerticesEntry = new JTextField("", 5);
            grid.gridx = 1;
            grid.gridy = 0;
            getContentPane().add(numVerticesEntry, grid);
        }
        {
            densityLabel = new JLabel();
            densityLabel.setText("Densité:");
            grid.gridx = 0;
            grid.gridy = 1;
            getContentPane().add(densityLabel, grid);

            densityEntry = new JTextField("", 5);
            grid.gridx = 1;
            grid.gridy = 1;
            getContentPane().add(densityEntry, grid);
        }

        costSelection(grid);
        capacitySelection(grid);
        {
            numTestsLabel = new JLabel();
            numTestsLabel.setText("Nombre de tests à éffectuer:");
            grid.gridx = 0;
            grid.gridy = 9;
            getContentPane().add(numTestsLabel, grid);

            numTestsEntry = new JTextField("", 5);
            grid.gridx = 1;
            grid.gridy = 9;
            getContentPane().add(numTestsEntry, grid);
        }
        {
            createGraphButton = new JButton("Tester !");
            grid.gridx = 3;
            grid.gridy = 10;
            getContentPane().add(createGraphButton, grid);
            createGraphButton.addActionListener(this);
        }

    }

    public void setMenuBar()
    {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("Fichier");

        openMenuItem = new JMenuItem("Ouvrir un graphique...");
        quitMenuItem = new JMenuItem("Quitter");

        fileMenu.add(openMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(quitMenuItem);

        menuBar.add(fileMenu);

        openMenuItem.addActionListener(this);
        quitMenuItem.addActionListener(this);

        menuBar.setVisible(true);
        setJMenuBar(menuBar);
    }

    public void actionPerformed(ActionEvent evt)
    {
        Object source = evt.getSource();
        if (source == quitMenuItem)
        {
            System.exit(1);
        } else if (source == openMenuItem)
        {
            try
            {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("."));
                int openResult = chooser.showDialog(chooser, "Open");
                if (openResult == JFileChooser.APPROVE_OPTION)
                {
//					String fileName = new String(chooser.getSelectedFile().toString());
//					try {
//						String extension = fileName.substring(fileName.lastIndexOf('.'));
//						if (extension.compareTo(".ovg") == 0) {
//							OrientedValuedGraph graph = new OrientedValuedGraph(0);
//							graph.deserialize(fileName);
//							new SingleValuedGraphWindow(graph);
//						}
//						else if (extension.compareTo(".fcg") == 0) {
//							FlowCostGraph graph = new FlowCostGraph(0);
//							graph.deserialize(fileName);
//							new FlowCostGraphWindow(graph);
//						}
//					}
//					catch (IOException erreur) {
//						System.out.println(erreur.getMessage());
//					}
                }
            } catch (Exception exception)
            {
                System.out.println(exception.getMessage());
            }
        } else if (source == createGraphButton)
        {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            try
            {
                numVertices = Integer.parseInt(numVerticesEntry.getText());
                density = Double.parseDouble(densityEntry.getText());
                minCost = Integer.parseInt(minCostEntry.getText());
                maxCost = Integer.parseInt(maxCostEntry.getText());
                minCapacity = Integer.parseInt(minCapacityEntry.getText());
                maxCapacity = Integer.parseInt(maxCapacityEntry.getText());
                numTests = Integer.parseInt(numTestsEntry.getText());


                doTheTests(numVertices, density, minCapacity, maxCapacity, minCost, maxCost);
                setCursor(null);

            } catch (NumberFormatException exception)
            {
                JOptionPane.showMessageDialog(this,
                        "Tous les champs n'ont pas été remplis correctement.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } catch (Exception e)
            {
                new ErrorMessage(e);
            }
        }
    }

    public void doTheTests(int numVertices, double density, int minCapacity, int maxCapacity, int minCost, int maxCost)
    {
        long start;
        long time;
        HashMap<Integer, Double> basicResults = new HashMap<Integer, Double>();
        HashMap<Integer, Double> busackerGowenResults = new HashMap<Integer, Double>();

        RandomGraphBuilder builder = new RandomGraphBuilder();
        builder.setCapacityLowerBound(minCapacity);
        builder.setCapacityUpperBound(maxCapacity);
        builder.setCostLowerBound(minCost);
        builder.setCostUpperBound(maxCost);

        int error = 0;

        long flowBasic, flowBusacker;
        long costBasic, costBusacker;
        for (int i = 0; i < numTests; i++)
        {
            System.out.println("\navancement : " + (i + 1) + "/" + numTests);


            double timeBasic = 0;
            double timeBusackerGowen = 0;

            for (int n = 7; n < numVertices; n++)
            {
                System.out.print(".");
                builder.setNumVertices(n);
                builder.setDensity(density);

                FlowCostGraph G = builder.generateRandomFlowGraph();
                BusackerGowen bg = new BusackerGowen(G);

                start = System.currentTimeMillis();
                {
                    bg.runAlgorithm();
                }
                time = System.currentTimeMillis() - start;
                flowBasic = bg.getG().getGraphFlow();
                costBasic = bg.getG().getGraphCost();
                timeBusackerGowen += time;


                BasicMaxFlowMinCost bmfmc = new BasicMaxFlowMinCost(G);
                start = System.currentTimeMillis();
                {
                    bmfmc.runAlgorithm();
                }
                time = System.currentTimeMillis() - start;
                flowBusacker = bmfmc.getG().getGraphFlow();
                costBusacker = bmfmc.getG().getGraphCost();
                timeBasic += time;

                if (i == 0)
                {
                    basicResults.put(n, timeBasic);
                    busackerGowenResults.put(n, timeBusackerGowen);
                } else
                {
                    basicResults.put(n, basicResults.get(n) + timeBasic);
                    busackerGowenResults.put(n, busackerGowenResults.get(n) + timeBusackerGowen);
                }

                boolean testFlow = (flowBasic == flowBusacker);
                boolean testCost = (costBasic == costBusacker);

                if (!testFlow || !testCost)
                {
                    error++;
                }
            }
            for (int n = 7; n < numVertices; n++)
            {
                basicResults.put(n, basicResults.get(n) / numTests);
                busackerGowenResults.put(n, busackerGowenResults.get(n) / numTests);
            }

        }
        JOptionPane.showMessageDialog(this,
                "Différences:" + error,
                "Comparaison",
                JOptionPane.INFORMATION_MESSAGE);

        displayGraphic(basicResults, busackerGowenResults);
    }

    public void displayGraphic(HashMap<Integer, Double> basic, HashMap<Integer, Double> bugow)
    {

        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries xy1 = new XYSeries("Basic");
        XYSeries xy2 = new XYSeries("Busacker & Gowen");

        for (int n = 7; n < numVertices; n++)
        {
            xy1.add(n, basic.get(n));
            xy2.add(n, bugow.get(n));
        }

        dataset.addSeries(xy1);
        dataset.addSeries(xy2);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Graphe ",
                "Nombre de sommets",
                "Temps (en millisecondes)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        chart.getXYPlot().setRenderer(new XYSplineRenderer());

        ChartFrame frame = new ChartFrame("Graphique", chart);
        frame.setVisible(true);
        frame.pack();

    }

    public void capacitySelection(GridBagConstraints grid)
    {
        {
            minCapacityLabel = new JLabel();
            minCapacityLabel.setText("Capacité mimum:");
            grid.gridx = 0;
            grid.gridy = 6;
            getContentPane().add(minCapacityLabel, grid);

            minCapacityEntry = new JTextField("", 5);
            grid.gridx = 1;
            grid.gridy = 6;
            getContentPane().add(minCapacityEntry, grid);
        }
        {
            maxCapacityLabel = new JLabel();
            maxCapacityLabel.setText("Capacité maximum:");
            grid.gridx = 0;
            grid.gridy = 7;
            getContentPane().add(maxCapacityLabel, grid);

            maxCapacityEntry = new JTextField("", 5);
            grid.gridx = 1;
            grid.gridy = 7;
            grid.insets = new Insets(5, 5, 5, 5);
            getContentPane().add(maxCapacityEntry, grid);
        }
    }

    public void costSelection(GridBagConstraints grid)
    {
        {
            minCostLabel = new JLabel();
            minCostLabel.setText("Cout minimum:");
            grid.gridx = 0;
            grid.gridy = 4;
            getContentPane().add(minCostLabel, grid);

            minCostEntry = new JTextField("", 5);
            grid.gridx = 1;
            grid.gridy = 4;
            getContentPane().add(minCostEntry, grid);
        }
        {
            maxCostLabel = new JLabel();
            maxCostLabel.setText("Cout maximum:");
            grid.gridx = 0;
            grid.gridy = 5;
            getContentPane().add(maxCostLabel, grid);

            maxCostEntry = new JTextField("", 5);
            grid.gridx = 1;
            grid.gridy = 5;
            grid.insets = new Insets(5, 5, 5, 5);
            getContentPane().add(maxCostEntry, grid);
        }

    }
}
