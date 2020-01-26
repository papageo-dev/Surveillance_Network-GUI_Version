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
		
		//Initialize JTextFields
		nameTxt = new JTextField(tempS.getName());
		codeNameTxt = new JTextField(tempS.getCodeName());
		enterPhoneNumTxt = new JTextField("Please enter suspect's phone number");
		
		//Initialize JButtons
		findSMSButton = new JButton("Find SMS");
		backButton = new JButton("Return to Search Screen");
		
		//Set tips for JButtons
		findSMSButton.setToolTipText("Search for suspicious SMS");
		backButton.setToolTipText("Search for another suspect");
		
		//Initialize JLabels
		partnersLabel = new JLabel("Partners");
		suggestedPartnersLabel = new JLabel("Suggested Partners--->");


		//Initialize JList<> "phoneList" and DefaultListModel "phoneListModel"
		phoneList = new JList<String>();
		phoneListModel = new DefaultListModel<String>();
		
		//Add suspect's phone numbers to "phoneListModel"
		for(int i=0; i<tempS.getPhoneNumbers().size(); i++) {
			phoneListModel.addElement(tempS.getPhoneNumbers().get(i));
		}
		//Set DefaultListModel "phoneListModel", to JList "phoneList"
		phoneList.setModel(phoneListModel);
		
		
		//Initialize JList<> "partnersList" and DefaultListModel "partnersListModel"
		partnersList = new JList<String>();
		partnersListModel = new DefaultListModel<String>();
		
		//Add suspect's potential partners to "partnersListModel"
		for (int i=0; i<tempS.getNumberOfPotentialPartners(); i++){
			partnersListModel.addElement(tempS.getPotentialPartners().get(i).toString());
		}
		//Set DefaultListModel "partnersListModel", to JList "partnersList"
		partnersList.setModel(partnersListModel);
		
		
		//Initialize JList<> "suggestedPartnersList" and DefaultListModel "suggestedPartnersListModel"
		suggestedPartnersList = new JList<String>();
		suggestedPartnersListModel = new DefaultListModel<String>();
		suggestedPartnersListModel.addElement("John Papas");
		
		//Add suspect's suggested partners to "suggestedPartnersListModel"
		for (int i=0; i<tempS.getListOfSuggestedPartners().size(); i++) {
			suggestedPartnersListModel.addElement(tempS.getListOfSuggestedPartners().get(i).getName());
		}
		//Set DefaultListModel "suggestedPartnersListModel", to JList "suggestedPartnersList"
		suggestedPartnersList.setModel(suggestedPartnersListModel);
		
		
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
		
		
		//Initialize JList<> "suspectsSuspiciousSMSList" and DefaultListModel "suspectsSuspiciousSMSListModel"
		suspectsSuspiciousSMSList = new JList<String>();
		suspectsSuspiciousSMSListModel = new DefaultListModel<String>();
		
	
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
					
					if (!enteredNum.isEmpty() || enteredNum!=null) {
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
					}
					//Set DefaultListModel "suspectsSuspiciousSMSListModel", to JList "suspectsSuspiciousSMSList"
					suspectsSuspiciousSMSList.setModel(suspectsSuspiciousSMSListModel);

					//Display JList "suspectsSuspiciousSMSList"
					suspectsSuspiciousSMSList.removeAll();
					suspectsSuspiciousSMSList.revalidate();
					suspectsSuspiciousSMSList.repaint();
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
		
				
		//Add graphic elements on JPanel "suspectInfopanel"
	    suspectInfopanel.add(nameTxt);
	    suspectInfopanel.add(codeNameTxt);
	    suspectInfopanel.add(phoneList);
	    suspectInfopanel.setSize(50, 100);
	    suspectInfopanel.setBorder(BorderFactory.createLineBorder(Color.black));
	    
	    //Add graphic elements on JPanel "findSMSpanel"
	    findSMSpanel.add(enterPhoneNumTxt);
	    findSMSpanel.add(suspectsSuspiciousSMSList);
	    findSMSpanel.add(findSMSButton);
	    findSMSpanel.setBorder(BorderFactory.createLineBorder(Color.black));

	    //Add graphic elements on JPanel "partnersPanel"
	    //partnersPanel.setLayout(new GridLayout(1,0));
	    partnersPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    partnersPanel.add(partnersLabel);
	    partnersPanel.add(partnersList);
	    partnersPanel.setBorder(BorderFactory.createLineBorder(Color.black));

	    //Add graphic elements on JPanel "suggPartnersPanel"
	    //suggPartnersPanel.setLayout(new GridLayout(1,0));
	    suggPartnersPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    suggPartnersPanel.add(suggestedPartnersLabel);
	    suggPartnersPanel.add(suggestedPartnersList);
	    suggPartnersPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Add graphic elements on JPanel "sameCountryPanel"
	    //sameCountryPanel.setLayout(new GridLayout(1, 0));
	    sameCountryPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    sameCountryPanel.add(suspectsFromSameCountryList);
	    sameCountryPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Add graphic elements on JPanel "panel"
	    panel.add(suspectInfopanel);
	    panel.add(findSMSpanel);
	    panel.add(partnersPanel);
	    panel.add(suggPartnersPanel);
	    panel.add(sameCountryPanel);
	    //backButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
	    //backButton.setAlignmentX(Component.BOTTOM_ALIGNMENT);
		panel.add(backButton);	
		
		
		//Initialize the JPanel panel 
		this.setContentPane(panel);
	    this.setTitle("Suspect Page");
		this.setVisible(true);
		this.setResizable(true);
		this.setSize(370, 500);
		this.setLocation(200, 0);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
