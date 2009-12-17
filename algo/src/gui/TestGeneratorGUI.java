package gui;

import graphs.FlowCostGraph;
import graphs.RandomGraphBuilder;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
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

public class TestGeneratorGUI extends Window  {
	private static final long serialVersionUID = 1L;

	JMenuBar menuBar;
		JMenu fileMenu;
			JMenuItem openMenuItem;
			JMenuItem quitMenuItem;

	JButton testButton;
	
	int numVertices;
	JTextField numVerticesEntry;
	JLabel verticesLabel;

	double density;
	JTextField densityEntry;
	JLabel densityLabel;
	
	int minCost;
	JTextField minCostEntry;
	JLabel minCostLabel;

	int maxCost;
	JTextField maxCostEntry;
	JLabel maxCostLabel;
	
	int minCapacity;
	JTextField minCapacityEntry;
	JLabel minCapacityLabel;

	int maxCapacity;
	JTextField maxCapacityEntry;
	JLabel maxCapacityLabel;
	
	int numTests;
	JTextField numTestsEntry;
	JLabel numTestsLabel;
	
	JSplitPane launchPane;
	JPanel cutomizationPane;
	
	JScrollPane statusPane;
	JTextArea statusArea;
	
	JScrollPane logPane;
	JTextArea logArea;
	
	public TestGeneratorGUI() {
        super("Test des méthodes de FlotMaxCoutMin");
        init();
		setVisible(true);
        setLocation(new Point(200, 200));
		pack();
	}
	
	
	public void init() {
		initMenuBar();
		initLaunchPane();
		initProgressPane();
		initMainPane();
	}
	
	public void initLaunchPane() {
		cutomizationPane = new JPanel();
		cutomizationPane.setLayout(new GridBagLayout());
        
        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(5,5,5,5);
        
        initVerticesSelection	(cutomizationPane, grid);
        initDensitySelection	(cutomizationPane, grid);
        initCostSelection		(cutomizationPane, grid);
        initCapacitySelection	(cutomizationPane, grid);
        initNumTestsSelection	(cutomizationPane, grid);
         
        testButton = new JButton("Tester !");
        testButton.addActionListener(this);
		launchPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, cutomizationPane, testButton);
	}
	
	public void initProgressPane() {
        statusArea = new JTextArea(5, 20);
        statusArea.setVisible(true);
        statusArea.setEditable(false);
        statusArea.setLineWrap(true);
        statusArea.setCaretPosition(statusArea.getText().length());
        statusPane = new JScrollPane(statusArea);
        
        logArea = new JTextArea();
        logArea.setVisible(true);
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setCaretPosition(logArea.getText().length());
        logPane = new JScrollPane(logArea);  
	}
	
	public void initMainPane() {
		JSplitPane textPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, statusPane, logPane);
		JSplitPane mainPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, launchPane, textPane);
        mainPane.setOneTouchExpandable(true);
        mainPane.setPreferredSize(new Dimension(500, 500));
        this.add(mainPane);
	}
	
	public void initMenuBar() {
		menuBar 	= new JMenuBar();
		fileMenu 	= new JMenu("Fichier");	
		
		openMenuItem = new JMenuItem("Ouvrir un graphique...");
		quitMenuItem = new JMenuItem("Quitter");

		fileMenu.add(openMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(quitMenuItem);		
						
		menuBar	.add(fileMenu);

		openMenuItem.addActionListener(this);
		quitMenuItem.addActionListener(this);

		menuBar	.setVisible(true);
		setJMenuBar(menuBar);
	}
	
	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();
		if (source == quitMenuItem) {
			System.exit(1);
		}
		else if (source == openMenuItem) {
			try {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("."));
				int openResult = chooser.showDialog(chooser, "Open");
				if (openResult == JFileChooser.APPROVE_OPTION) {
					 // a faire...........
				}
			} 
			catch (Exception exception) {
				System.out.println(exception.getMessage());
			}
		}
		else if (source == testButton) {	        
			try {
				numVertices 	= Integer.parseInt(numVerticesEntry.getText());
				density			= Double.parseDouble(densityEntry.getText());
				minCost 		= Integer.parseInt(minCostEntry.getText());
				maxCost 		= Integer.parseInt(maxCostEntry.getText());
				minCapacity 	= Integer.parseInt(minCapacityEntry.getText());
				maxCapacity 	= Integer.parseInt(maxCapacityEntry.getText());
				numTests		= Integer.parseInt(numTestsEntry.getText());
				
	            

		        boolean checkVertices = numVertices > 5;
		        boolean checkDensity = (density <= 1) && (density > 0);
		        boolean checkCapacity = minCapacity <= maxCapacity;
		        boolean checkCost = minCost <= maxCost;
		        
	        	if (!checkVertices) {
	        		new ErrorMessage("Veuillez entrer un nombre de sommets > 5.");
	        	}
	        	else if (!checkDensity) {
	        		new ErrorMessage("Veuillez entrer une densité comprise entre 0 et 1.");
	        	}
	        	else if (!checkCapacity) {
	        		new ErrorMessage("Veuillez vérifier les capacités.");
	        	}
	        	else if (!checkCost) {
	        		new ErrorMessage("Veuillez vérifier les coûts.");
	        	}
		        else {
		        	doTheTests();
		        }
	        }
			catch (NumberFormatException exception) {
				new ErrorMessage("Tous les champs n'ont pas été remplis correctement.");
					       
			}
			catch (Exception e) {
				new ErrorMessage(e);
			}
		}
    }

	public void doTheTests() {
		long start;
        long time;
        
        HashMap<Integer, Double> basicResults = new HashMap<Integer, Double>();
        HashMap<Integer, Double> busackerGowenResults = new HashMap<Integer, Double>();

        int error = 0;

        int flowBasic, flowBusacker;
        int costBasic, costBusacker;
        for (int i = 0; i < numTests; i++)
        {

    		RandomGraphBuilder builder = new RandomGraphBuilder();
        	builder.setCapacityLowerBound(minCapacity);
            builder.setCapacityUpperBound(maxCapacity);
            builder.setCostLowerBound(minCost);
            builder.setCostUpperBound(maxCost);
            
        	updateInfo("\nTest: " + (i+1) + "/"+numTests);

            boolean testError = false;
            double timeBasic = 0;
            double timeBusackerGowen = 0;
            for (int n = 7; n <= numVertices; n++)
            {
            	updateStatus("\nTest: " + (i+1) + "/"+numTests+"\n\t"+n+" sommets..........");
            	updateLog("\n\t"+n+" sommets..........");
            	
            	builder.setNumVertices(n);
                builder.setDensity(density);
                
                FlowCostGraph G = new FlowCostGraph(builder.generateRandomFlowGraph());
                

                BusackerGowen bg = new BusackerGowen(G);
                start = System.currentTimeMillis();
                bg.runAlgorithm();
                time = System.currentTimeMillis() - start;
                timeBusackerGowen += time;
                flowBasic = bg.getG().getGraphFlow();
                costBasic = bg.getG().getGraphCost();
                

                
                BasicMaxFlowMinCost bmfmc = new BasicMaxFlowMinCost(G);
                start = System.currentTimeMillis();
                bmfmc.runAlgorithm();
                time = System.currentTimeMillis() - start;
                timeBasic += time;
                flowBusacker = bmfmc.getG().getGraphFlow();
                costBusacker = bmfmc.getG().getGraphCost();
                

                if (i == 0)
                {
                    basicResults.put(n, timeBasic);
                    busackerGowenResults.put(n, timeBusackerGowen);
                } 
                else
                {
                    basicResults.put(n, basicResults.get(n) + timeBasic);
                    busackerGowenResults.put(n, busackerGowenResults.get(n) + timeBusackerGowen);
                }
                
                boolean testFlow = (flowBasic == flowBusacker);
                boolean testCost = (costBasic == costBusacker);

                if (!testFlow || !testCost) {
                	System.out.println(flowBasic+" "+flowBusacker+" "+costBasic+" "+costBusacker+" "+n);
                    error++;
                    testError = true;
                }
                if (testError) {
                	updateLog("ERROR");
                }
                else {
                	updateLog("OK");
                }

            }
            for (int n = 7; n <= numVertices; n++)
            {
                basicResults.put(n, basicResults.get(n) / numTests);
                busackerGowenResults.put(n, busackerGowenResults.get(n) / numTests);
            }
           
        }
        updateInfo("\n\n"+error+" error(s)");

    	logArea.update(logArea.getGraphics());
        JOptionPane.showMessageDialog(this,
                               "Différences: "+error,
                               "Comparaison", 
                                JOptionPane.INFORMATION_MESSAGE);
        
        displayGraphic(basicResults, busackerGowenResults);
	}
	
	public void updateInfo(String info) {
		updateLog(info);
		updateStatus(info);
	}
	public void updateLog(String text) {
    	logArea.append(text);
	}
	
	public void updateStatus(String text) {
		statusArea.setText(text);
    	statusArea.update(statusArea.getGraphics());
	}
	
	
	public void displayGraphic(HashMap<Integer, Double> basic, HashMap<Integer, Double> bugow) {

		XYSeriesCollection dataset = new XYSeriesCollection();
		
        XYSeries basicSeries = new XYSeries("Basic");
        XYSeries bugowSeries = new XYSeries("Busacker & Gowen");
        
	    for (int n = 7; n <= numVertices; n++)
        {
	    	basicSeries.add(n, basic.get(n));
	    	bugowSeries.add(n, bugow.get(n));
        }

        dataset.addSeries(basicSeries);
        dataset.addSeries(bugowSeries);
	    
	    JFreeChart chart = ChartFactory.createXYLineChart(
	    		"Graphe ",
	    		"Nombre de sommets",
	    		"Temps (en secondes)",
	    		dataset,
	    		PlotOrientation.VERTICAL,
	    		true,
	    		true,
	    		false
	    );
	    
	    chart.getXYPlot().setRenderer(new XYSplineRenderer());
	    
		ChartFrame frame = new ChartFrame("Graphique", chart);
		frame.setVisible(true);
		frame.pack();
		
	}
	
	public void initCapacitySelection(JPanel cutomizationPane, GridBagConstraints grid) {
		{
        	minCapacityLabel = new JLabel();    
        	minCapacityLabel.setText("Capacité mimum:");
			grid.gridx = 0;
			grid.gridy = 6;
			cutomizationPane.add(minCapacityLabel, grid);
	
			minCapacityEntry = new JTextField("", 5);    
			grid.gridx = 1;
			grid.gridy = 6;
			cutomizationPane.add(minCapacityEntry, grid);
        }
        
        {
        	maxCapacityLabel = new JLabel();    
        	maxCapacityLabel.setText("Capacité maximum:");
			grid.gridx = 0;
			grid.gridy = 7;
			cutomizationPane.add(maxCapacityLabel, grid);
	
			maxCapacityEntry = new JTextField("", 5);   
			grid.gridx = 1;
			grid.gridy = 7;
			grid.insets = new Insets(5,5,5,5);
			cutomizationPane.add(maxCapacityEntry, grid);
        }
	}
	
	
	public void initCostSelection(JPanel cutomizationPane, GridBagConstraints grid) {
		{
        	minCostLabel = new JLabel();    
        	minCostLabel.setText("Cout minimum:");
			grid.gridx = 0;
			grid.gridy = 4;
			cutomizationPane.add(minCostLabel, grid);
	
			minCostEntry = new JTextField("", 5);     
			grid.gridx = 1;
			grid.gridy = 4;
			cutomizationPane.add(minCostEntry, grid);
        }
        
        {
        	maxCostLabel = new JLabel();    
        	maxCostLabel.setText("Cout maximum:");
			grid.gridx = 0;
			grid.gridy = 5;
			cutomizationPane.add(maxCostLabel, grid);
	
			maxCostEntry = new JTextField("", 5);     
			grid.gridx = 1;
			grid.gridy = 5;
			grid.insets = new Insets(5,5,5,5);
			cutomizationPane.add(maxCostEntry, grid);
        }   
		
	}
	
	public void initVerticesSelection(JPanel cutomizationPane, GridBagConstraints grid) {
        verticesLabel = new JLabel();    
        verticesLabel.setText("Nombre de sommets:");
		grid.gridx = 0;
		grid.gridy = 0;
		cutomizationPane.add(verticesLabel, grid);

		numVerticesEntry = new JTextField("", 5);     
		grid.gridx = 1;
		grid.gridy = 0;
		cutomizationPane.add(numVerticesEntry, grid);      
    }
    
	public void initDensitySelection(JPanel cutomizationPane, GridBagConstraints grid) {
    	densityLabel = new JLabel();    
    	densityLabel.setText("Densité:");
		grid.gridx = 0;
		grid.gridy = 1;
		cutomizationPane.add(densityLabel, grid);

		densityEntry = new JTextField("", 5);     
		grid.gridx = 1;
		grid.gridy = 1;
		cutomizationPane.add(densityEntry, grid);
    }

		
		
	public void initNumTestsSelection(JPanel cutomizationPane, GridBagConstraints grid) {
    	numTestsLabel = new JLabel();    
    	numTestsLabel.setText("Nombre de tests à éffectuer:");
		grid.gridx = 0;
		grid.gridy = 8;
		cutomizationPane.add(numTestsLabel, grid);

		numTestsEntry = new JTextField("", 5);     
		grid.gridx = 1;
		grid.gridy = 8;
		cutomizationPane.add(numTestsEntry, grid);
    }
}
