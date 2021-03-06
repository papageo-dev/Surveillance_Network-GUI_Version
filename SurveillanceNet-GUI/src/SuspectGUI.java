import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class SuspectGUI extends JFrame {
	
	//Declaration of JFrame elements
	private JPanel panel;
	private JPanel suspectInfopanel;
	private JPanel findSMSpanel;
	private JPanel partnersPanel;
	private JPanel suggPartnersPanel;
	private JPanel sameCountryPanel;
	
	private JTextField nameTxt;
	private JTextField codeNameTxt;
	private JTextField enterPhoneNumTxt;
	
	private JList<String> phoneList;
	private DefaultListModel<String> phoneListModel;
	private JList<String> partnersList;
	private DefaultListModel<String> partnersListModel;
	private JList<String> suggestedPartnersList;
	private DefaultListModel<String> suggestedPartnersListModel;
	private JList<String> suspectsFromSameCountryList;
	private DefaultListModel<String> suspectsFromSameCountryListModel;
	private JList<String> suspectsSuspiciousSMSList;
	private DefaultListModel<String> suspectsSuspiciousSMSListModel;
	
	private JButton findSMSButton;
	private JButton backButton;
	
	private JLabel partnersLabel;
	private JLabel suggestedPartnersLabel;
	

	public SuspectGUI(Suspect tempS, Registry registry) {
		
		//Initialize JPanel(s)
		panel = new JPanel();
		suspectInfopanel = new JPanel();
		findSMSpanel = new JPanel();
		partnersPanel = new JPanel();
		suggPartnersPanel = new JPanel();
		sameCountryPanel = new JPanel();
		
		//Initialize JTextField(s)
		nameTxt = new JTextField(tempS.getName());
		codeNameTxt = new JTextField(tempS.getCodeName());
		enterPhoneNumTxt = new JTextField("Enter suspect's phone number");
		
		//Initialize JButton(s)
		findSMSButton = new JButton("Find SMS");
		backButton = new JButton("Return to Search Screen");
		
		//Set toolTip for JButton(s)
		findSMSButton.setToolTipText("Search for suspicious SMS");
		backButton.setToolTipText("Search for another suspect");
		
		//Initialize JLabel(s)
		partnersLabel = new JLabel("Partners");
		suggestedPartnersLabel = new JLabel("Suggested Partners--->");


		//Set phoneList
		//Initialize JList<> "phoneList" and DefaultListModel "phoneListModel"
		phoneList = new JList<String>();
		phoneListModel = new DefaultListModel<String>();
		
		//Add suspect's phone numbers to "phoneListModel"
		for(int i=0; i<tempS.getPhoneNumbers().size(); i++) {
			phoneListModel.addElement(tempS.getPhoneNumbers().get(i));
		}
		//Set DefaultListModel "phoneListModel", to JList "phoneList"
		phoneList.setModel(phoneListModel);
		
		//Set partnersList
		//Initialize JList<> "partnersList" and DefaultListModel "partnersListModel"
		partnersList = new JList<String>();
		partnersListModel = new DefaultListModel<String>();
		
		//Add suspect's potential partners to "partnersListModel"
		for (int i=0; i<tempS.getNumberOfPotentialPartners(); i++){
			partnersListModel.addElement(tempS.getPotentialPartners().get(i).toString());
		}
		//Set DefaultListModel "partnersListModel", to JList "partnersList"
		partnersList.setModel(partnersListModel);
		
		//Set suggestedPartnersList
		//Initialize JList<> "suggestedPartnersList" and DefaultListModel "suggestedPartnersListModel"
		suggestedPartnersList = new JList<String>();
		suggestedPartnersListModel = new DefaultListModel<String>();
		
		//Add suspect's suggested partners to "suggestedPartnersListModel"
		for (int i=0; i<tempS.getSuggestedPartners(registry).size(); i++) {
			suggestedPartnersListModel.addElement(tempS.getListOfSuggestedPartners().get(i).getName());
		}
		//Check if there aren't suggested partners for current Suspect
		if (tempS.getSuggestedPartners(registry).size()==0) {
			//Add a warning message in "suggestedPartnersListModel"
			suggestedPartnersListModel.addElement("There aren't suggested partners...");
		}
		//Set DefaultListModel "suggestedPartnersListModel", to JList "suggestedPartnersList"
		suggestedPartnersList.setModel(suggestedPartnersListModel);
		
		//Set suspectsFromSameCountryList
		//Initialize JList<> "suspectsFromSameCountryList" and DefaultListModel "suspectsFromSameCountryListModel"
		suspectsFromSameCountryList = new JList<String>();
		suspectsFromSameCountryListModel = new DefaultListModel<String>();
		suspectsFromSameCountryListModel.addElement("Suspects coming from " + tempS.getOriginCountry());
		
		//Search for suspects coming from the same Country and add them to "suspectsFromSameCountryListModel"
		for (int i=0; i<registry.getAllSuspects().size(); i++) {
			if (registry.getAllSuspects().get(i).getOriginCountry().equals(tempS.getOriginCountry())) {
				suspectsFromSameCountryListModel.addElement(registry.getAllSuspects().get(i).getName());
			}
		}
		//Set DefaultListModel "suspectsFromSameCountryListModel", to JList "suspectsFromSameCountryList"
		suspectsFromSameCountryList.setModel(suspectsFromSameCountryListModel);
		
		//Set suspectsSuspiciousSMSList
		//Initialize JList<> "suspectsSuspiciousSMSList" and DefaultListModel "suspectsSuspiciousSMSListModel"
		suspectsSuspiciousSMSList = new JList<String>();
		suspectsSuspiciousSMSListModel = new DefaultListModel<String>();
		
	
		//Set Listeners
		//Create a Listener for the JTextField enterPhoneNumTxt
		enterPhoneNumTxt.addFocusListener(new FocusListener() {
            //If user click on JTextField enterPhoneNumTxt
        	public void focusGained(FocusEvent e) {
        		//Replace prompt message with space character
        		enterPhoneNumTxt.setText("");
            }
        	//If user NOT click on JTextField enterPhoneNumTxt
            public void focusLost(FocusEvent e) {
                //Do nothing
            }
        });
		
		//Create a Listener for the JButton findSMSButton
		findSMSButton.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				
				//When user press the JButton "findSMSButton"
				if (e.getSource().equals(findSMSButton)) {
					
					//Copy user input phone number to a String variable "enteredNum"
					String enteredNum = enterPhoneNumTxt.getText();
					
					//Clear JList's content, from last search
					suspectsSuspiciousSMSListModel.clear();
					
					//Check if user's input is valid
					boolean validInput=false;
					if (enteredNum.trim().isEmpty() || Character.isLetter(enteredNum.charAt(0)) || enteredNum==null) {
						JOptionPane.showMessageDialog(SuspectGUI.this, "Enter a valid phone number and try again!",
			                      "Invalid Input", JOptionPane.ERROR_MESSAGE);
						validInput=false;
					}
					else {
						validInput=true;
					}
					
					//Search for Suspicious Messages
					if (validInput) {
						//Search in ArrayList "allCommunications"
					    for (int i=0; i<registry.getAllCommunications().size(); i++) {
					    	//If current communication is a SMS
						    if (registry.getAllCommunications().get(i) instanceof SMS) {
						    	//If entered phone number equals one of two numbers, between communication
							    if (enteredNum.equals(registry.getAllCommunications().get(i).phoneNumber1) || enteredNum.equals(registry.getAllCommunications().get(i).phoneNumber2)) {
							        //Add the message's content to ArrayList "tempSMS"
							    	suspectsSuspiciousSMSListModel.addElement(((SMS) registry.getAllCommunications().get(i)).getContentText());
							    }
						    }
					    }
					    //Check if there are Suspicious Messages for enteredNum
						if (suspectsSuspiciousSMSListModel.getSize()==0) {
							JOptionPane.showMessageDialog(SuspectGUI.this, "There are no suspicious messages for this phone number",
				                      "Message", JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							//Set DefaultListModel "suspectsSuspiciousSMSListModel", to JList "suspectsSuspiciousSMSList"
							suspectsSuspiciousSMSList.setModel(suspectsSuspiciousSMSListModel);

							//Display JList "suspectsSuspiciousSMSList"
							suspectsSuspiciousSMSList.removeAll();
							suspectsSuspiciousSMSList.revalidate();
							suspectsSuspiciousSMSList.repaint();
						}
					}
				}
			}
		});
		
		//Create a Listener for the JButton backButton
		backButton.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource().equals(backButton)) {
					panel.setVisible(false);
					new SearchGUI(registry);
				}
			}
		});
		
		
		//Initialize JFrame		
		//Add graphic elements on JPanel "suspectInfopanel"
		suspectInfopanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		nameTxt.setEditable(false);
		nameTxt.setPreferredSize(new Dimension(130, 20));
	    suspectInfopanel.add(nameTxt);
	    codeNameTxt.setPreferredSize(new Dimension(130, 20));
	    codeNameTxt.setEditable(false);
	    suspectInfopanel.add(codeNameTxt);
	    suspectInfopanel.add(phoneList);
	    suspectInfopanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
	    panel.add(suspectInfopanel);
	    
	    //Add graphic elements on JPanel "findSMSpanel"
	    enterPhoneNumTxt.setPreferredSize(new Dimension(175, 20));
	    findSMSpanel.add(enterPhoneNumTxt);
	    suspectsSuspiciousSMSList.setPreferredSize(new Dimension(230, 150));
	    findSMSpanel.add(suspectsSuspiciousSMSList);
	    findSMSpanel.add(findSMSButton);
	    findSMSpanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
	    panel.add(findSMSpanel);

	    //Add graphic elements on JPanel "partnersPanel"
	    partnersPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    partnersPanel.add(partnersLabel);
	    partnersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    partnersList.setPreferredSize(new Dimension(230, 150));
	    partnersPanel.add(partnersList);
	    partnersPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
	    panel.add(partnersPanel);

	    //Add graphic elements on JPanel "suggPartnersPanel"
	    suggPartnersPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    suggPartnersPanel.add(suggestedPartnersLabel);
	    suggestedPartnersList.setPreferredSize(new Dimension(220, 80));
	    suggPartnersPanel.add(suggestedPartnersList);
	    suggPartnersPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
	    panel.add(suggPartnersPanel);
		
		//Add graphic elements on JPanel "sameCountryPanel"
	    sameCountryPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    suspectsFromSameCountryList.setPreferredSize(new Dimension(330, 80));
	    sameCountryPanel.add(suspectsFromSameCountryList);
	    sameCountryPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
	    panel.add(sameCountryPanel);
		
		panel.add(backButton); //Return to "SearchGUI" screen	
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setContentPane(panel);
	    this.setTitle("Suspect Page");
		this.setVisible(true);
		this.setResizable(false);
		this.setSize(530, 670);
		this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
