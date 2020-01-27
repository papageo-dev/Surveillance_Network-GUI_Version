
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class SearchGUI extends JFrame{
	
	private JPanel panel;
	private JTextField text1;
	private JButton findButton;
	private JButton visualNetButton;
	private boolean found = false;
	
	public SearchGUI(Registry registry) {
		
		panel = new JPanel(); //Initialize JPanel panel
        text1 = new JTextField("Please enter suspect's name"); //Initialize JTextField text1, with a prompt message
        findButton = new JButton("Find"); //Initialize JButton "findButton"
        visualNetButton = new JButton("Visualize Network"); //Initialize JButton "visualNet"
        
        //Initialize the JPanel panel 
        this.setContentPane(panel);
        this.setTitle("Find Suspect");
        this.setVisible(true);
        this.setSize(270, 100);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Set a tip for JTextField "text1"
        text1.setToolTipText("Please enter suspect's name");
        //Set a tip for JButton "findButton"
        findButton.setToolTipText("Search for this suspect");
        //Set a tip for JButton "visualNet"
        visualNetButton.setToolTipText("Show the visualization of Suspects Network");
        
        //Create a Listener for the JTextField text1
        text1.addFocusListener(new FocusListener() {
            //If user click on JTextField text1
        	public void focusGained(FocusEvent e) {
        		//Replace prompt message with space character
                text1.setText("");
            }
        	//If user NOT click on JTextField text1
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
					String name = text1.getText();
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
					new VisualizeNetwork();
					//Close this JPanel
					panel.setVisible(false);
				}	
			}
        });
        
        panel.add(text1); //Add JTextField "text1", on JPanel "panel"
        panel.add(findButton); //Add JButton "findButton", on JPanel "panel"
        panel.add(visualNetButton); //Add JButton "visualNetButton" on JPanel "panel"
	}

}
