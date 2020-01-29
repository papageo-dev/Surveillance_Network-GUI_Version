
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class SearchGUI extends JFrame{
	
	private JPanel panel;
	private JTextField suspectName;
	private JButton findButton;
	private JButton visualNetButton;
	private boolean found = false;
	
	public SearchGUI(Registry registry) {
		
		panel = new JPanel(); //Initialize JPanel panel
        suspectName = new JTextField("Please enter suspect's name"); //Initialize JTextField "suspectName", with a prompt message
        findButton = new JButton("Find"); //Initialize JButton "findButton"
        visualNetButton = new JButton("Visualize Network"); //Initialize JButton "visualNet"
        
        //Set a tip for JTextField "suspectName"
        suspectName.setToolTipText("Please enter suspect's name");
        //Set a tip for JButton "findButton"
        findButton.setToolTipText("Search for this suspect");
        //Set a tip for JButton "visualNet"
        visualNetButton.setToolTipText("Show the visualization of Suspects Network");
        
        //Create a Listener for the JTextField suspectName
        suspectName.addFocusListener(new FocusListener() {
            //If user click on JTextField suspectName
        	public void focusGained(FocusEvent e) {
        		//Replace prompt message with space character
                suspectName.setText("");
            }
        	//If user NOT click on JTextField suspectName
            public void focusLost(FocusEvent e) {
                //Do nothing
            }
        });
        
        //Create a Listener for the JButton findButton
        findButton.addActionListener(new ActionListener() {
        	
			public void actionPerformed(ActionEvent e) {
				
				//Create a new temporary Suspect
				Suspect tempS = new Suspect();
				
				if (e.getSource().equals(findButton)) {
					
					//Initialize a String variable with suspect's entered name
					String name = suspectName.getText();
					//Search in ArrayList<> allSuspects
					for (Suspect s : registry.getAllSuspects()) {
						if (name.equals(s.getName())) { //If found this suspect
							tempS = s;
							found = true; 
						}
					}
					if (found==true) {
						//Create/open a new JPanel "Suspect Page"
						new SuspectGUI(tempS, registry);
						//Close this JPanel
						panel.setVisible(false);
					}
					else {
						//Show a NOT found message
						JOptionPane.showMessageDialog(SearchGUI.this, "Suspect " + name + " NOT found!",
								                      "Message", JOptionPane.ERROR_MESSAGE);
					}
				}	
			}
        });
        
        //Create a Listener for the JButton visualNetButton
        visualNetButton.addActionListener(new ActionListener() {
        	
			public void actionPerformed(ActionEvent e) {
				
				if (e.getSource().equals(visualNetButton)) {
					//Create/open a new JPanel "Visualize Network"
					new VisualizeNetwork(registry);
					//Close this JPanel
					panel.setVisible(false);
				}	
			}
        });
        
        //Initialize JFrame
        panel.add(suspectName); //Add JTextField "suspectName", on JPanel "panel"
        panel.add(findButton); //Add JButton "findButton", on JPanel "panel"
        panel.add(visualNetButton); //Add JButton "visualNetButton" on JPanel "panel"
        
        this.setContentPane(panel);
        this.setTitle("Find Suspect");
        this.setVisible(true);
        this.setSize(270, 100);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
