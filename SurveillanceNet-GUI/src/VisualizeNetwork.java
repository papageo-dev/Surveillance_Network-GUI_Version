import java.awt.Color;

import javax.swing.*;

import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.algorithms.shortestpath.DistanceStatistics;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.*;
import edu.uci.ics.jung.visualization.control.*;
import edu.uci.ics.jung.visualization.renderers.*;

public class VisualizeNetwork {
	
	//Create a VisualizeNetwork object and create the graph
	public VisualizeNetwork(Registry registry) {
		this.createGraph(registry);
	}
	
	//Create the graph of Suspects Network
	public void createGraph(Registry registry) {
	
		//Create an UndirectedSparseGraph object
	    UndirectedSparseGraph<Suspect, Object> g = new UndirectedSparseGraph<Suspect, Object>();
	
	    //Add a vertex for each suspect
	    for (int i=0; i<registry.getAllSuspects().size(); i++) {
		    g.addVertex(registry.getAllSuspects().get(i));
	    }
	
	    //Add edges between suspects, if they are partners
	    for (int i=0; i<registry.getAllSuspects().size(); i++) {
	    	for (int j=0; j<registry.getAllSuspects().size(); j++) {
		    	if (registry.getAllSuspects().get(i).getPotentialPartners().contains(registry.getAllSuspects().get(j))) {
			    	g.addEdge(new Object(), registry.getAllSuspects().get(i), registry.getAllSuspects().get(j));
		    	}
		    }
	    }
	    
	    //Display a JFrame with Suspects Network graph
	    this.displayGraph(registry, g);
	}
	
	//Display a JFrame with Suspects Network Graph
	public void displayGraph(Registry registry, UndirectedSparseGraph<Suspect, Object> g) {
		
		//Create a VisualizationViewer object, to show the graph with a CircleLayout
		VisualizationViewer<Suspect, Object> suspectsNetViewer =
				new VisualizationViewer<>(new CircleLayout<Suspect, Object>(g));
		
		//Initialize the VisualizationViewer
		suspectsNetViewer.setGraphMouse(new DefaultModalGraphMouse<>());
		suspectsNetViewer.getRenderContext().setVertexLabelRenderer(new DefaultVertexLabelRenderer(Color.BLACK));
		//Set suspect's codeName, as label of vertex
		suspectsNetViewer.getRenderContext().setVertexLabelTransformer(s -> s == null ? "" : s.getCodeName());

		//Create/Initialize a JTextField, that displays graph diameter
		JTextField diameterTxt = new JTextField();
		diameterTxt.setEditable(false);
		diameterTxt.setText(String.format("Diameter = %.2f", DistanceStatistics.diameter(g)));
		diameterTxt.setToolTipText("Diameter of the graph");
	    
		
	    //Create/Initialize JFrame "frame" and JPanel "panel"
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.add(suspectsNetViewer);
	    panel.add(diameterTxt);
	    
	    JFrame frame = new JFrame();
	    frame.setTitle("Suspects Network");
	    frame.setLocationRelativeTo(null);
	    frame.setContentPane(panel);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
	}
}
