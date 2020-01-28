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
	
	    UndirectedSparseGraph<Suspect, Object> g = new UndirectedSparseGraph<Suspect, Object>();
	
	    for (int i=0; i<registry.getAllSuspects().size(); i++) {
		    g.addVertex(registry.getAllSuspects().get(i));
	    }
	
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
	
	//Display a JFrame with Suspects Network graph
	public void displayGraph(Registry registry, UndirectedSparseGraph<Suspect, Object> g) {
		
		VisualizationViewer<Suspect, Object> suspectsNetViewer =
				new VisualizationViewer<>(new CircleLayout<Suspect, Object>(g));
		
		suspectsNetViewer.setGraphMouse(new DefaultModalGraphMouse<>());
		suspectsNetViewer.getRenderContext().setVertexLabelRenderer(new DefaultVertexLabelRenderer(Color.BLACK));
		suspectsNetViewer.getRenderContext().setVertexLabelTransformer(s -> s == null ? "" : s.getCodeName());

		//Initialize a JTextField, that displays graph diameter
		JTextField diameterTxt = new JTextField();
		diameterTxt.setEditable(false);
		diameterTxt.setText(String.format("Diameter = %.2f", DistanceStatistics.diameter(g)));
	    
		
	    //Create/initialize JFrame "frame" and JPanel "panel"
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.add(suspectsNetViewer);
	    panel.add(diameterTxt);
	    panel.setSize(750, 750);
	    
	    JFrame frame = new JFrame();
	    frame.setTitle("Suspects Network");
	    frame.setLocationRelativeTo(null);
	    frame.setContentPane(panel);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
	}
}
