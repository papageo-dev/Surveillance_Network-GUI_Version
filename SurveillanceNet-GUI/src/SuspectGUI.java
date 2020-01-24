import java.util.ArrayList;
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
	private JList<String> partnersList;
	private JList<String> suggestedPartnersList;
	private JList<String> suspectsFromSameCountryList;
	private JList<String> suspectsSuspiciousSMSList;
	private String[] phoneListArray = new String[15];
	private String[] partnersListArray = new String[30];
	private String[] suggestedPartnersListArray = new String[30];
	private String[] suspectsFromSameCountryArray = new String[30];
	private String[] suspectsSuspiciousSMSArray = new String[100];
	private JScrollPane scrollPanePhoneNumbers = new JScrollPane();
	private JScrollPane scrollPanePartners = new JScrollPane();
	private JScrollPane scrollPaneSuggestedPartners = new JScrollPane();
	private JScrollPane scrollPaneSuspectsFromSameCountry = new JScrollPane();
	private JScrollPane scrollPaneSuspectsSuspiciousSMS = new JScrollPane();
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
		
		//Copy all suspect's phone numbers, from ArrayList<> "phoneNumbers", to Array[] "phoneListArray"
		for (int i=0; i<tempS.getPhoneNumbers().size(); i++) {
			phoneListArray[i]=tempS.getPhoneNumbers().get(i);
		}
		//Initialize JList<> "phoneList" and add suspect's phone numbers, from Array[] "phoneListArray"
		phoneList = new JList<String>(phoneListArray);
		//Show suspect's phone numbers
		scrollPanePhoneNumbers  = new JScrollPane(phoneList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
		//Initialize JList<> "partnersList" and add suspect's phone numbers, from Array[] "partnersListArray"
		int x=0;
		for (int i=0; i<tempS.getNumberOfPotentialPartners(); i++) {
			partnersListArray[x]=tempS.getPotentialPartners().get(i).toString();
			x++;
		}
		//Show suspect's partners
		partnersList = new JList<String>(partnersListArray);
		scrollPanePartners = new JScrollPane(partnersList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//Initialize JList<> "suggestedPartnersList" and add suspect's phone numbers, from Array[] "suggestedPartnersListArray"
		suggestedPartnersListArray[0]="John Papas";
		for (int i=0; i<tempS.getListOfSuggestedPartners().size(); i++) {
			suggestedPartnersListArray[i]=tempS.getListOfSuggestedPartners().get(i).getName();
		}
		//Show suggested partners for this suspect
		suggestedPartnersList = new JList<String>(suggestedPartnersListArray);
		scrollPaneSuggestedPartners = new JScrollPane(suggestedPartnersList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//Initialize JList<> "suspectsFromSameCountryList" and add suspect's phone numbers, from Array[] "suspectsFromSameCountryArray"
		//Set a message and and the name of country at the first line of the scrollPane
		suspectsFromSameCountryArray[0]="Suspects coming from " + tempS.getOriginCountry();
		int j=1;
		for (int i=0; i<registry.getAllSuspects().size(); i++) {
			if (registry.getAllSuspects().get(i).getOriginCountry().equals(tempS.getOriginCountry())) {
				suspectsFromSameCountryArray[j]=registry.getAllSuspects().get(i).getName();
				j++;
			}
		}
		//Show all suspects, that coming from the current suspect's origin country 
		suspectsFromSameCountryList = new JList<String>(suspectsFromSameCountryArray);
		scrollPaneSuspectsFromSameCountry = new JScrollPane(suspectsFromSameCountryList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//Initialize scrollPaneSuspectsSuspiciousSMS
		suspectsSuspiciousSMSArray[0]="                                                          ";
		suspectsSuspiciousSMSList = new JList<String>(suspectsSuspiciousSMSArray);
		scrollPaneSuspectsSuspiciousSMS = new JScrollPane(suspectsSuspiciousSMSList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
		
	
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
					//Initialize an ArrayList, that contains suspect's suspicious messages
					ArrayList<String> tempSMS = new ArrayList<String>();
					
					if (!enteredNum.isEmpty() || enteredNum!=null) {
						//Search in ArrayList "allCommunications"
					    for (int i=0; i<registry.getAllCommunications().size(); i++) {
					    	//If current communication is a SMS
						    if (registry.getAllCommunications().get(i) instanceof SMS) {
						    	//If entered phone number equals one of two numbers, between communication
							    if (enteredNum.equals(registry.getAllCommunications().get(i).phoneNumber1) || enteredNum.equals(registry.getAllCommunications().get(i).phoneNumber2)) {
							        //Add the message's content to ArrayList "tempSMS"
							    	tempSMS.add(((SMS) registry.getAllCommunications().get(i)).getContentText());
							    }
						    }
					    }
					}
					
					System.out.println(tempSMS);
					//Copy ArrayListe "tempSMS" to Array "suspectsSuspiciousSMSArray"
					tempSMS.toArray(suspectsSuspiciousSMSArray);
					
					//scrollPaneSuspectsSuspiciousSMS.removeAll();
					suspectsSuspiciousSMSList = new JList<String>(suspectsSuspiciousSMSArray);
					scrollPaneSuspectsSuspiciousSMS.add(new JScrollPane(suspectsSuspiciousSMSList));
					scrollPaneSuspectsSuspiciousSMS.revalidate();
					scrollPaneSuspectsSuspiciousSMS.repaint();
					
					
					scrollPaneSuspectsSuspiciousSMS = new JScrollPane(suspectsSuspiciousSMSList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
		
				
		//Add graphic elements on JPanel suspectInfopanel
		suspectInfopanel.setLayout(new GridLayout(1,0));
		suspectInfopanel.setAlignmentY(Component.TOP_ALIGNMENT);
		suspectInfopanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    suspectInfopanel.add(nameTxt);
	    suspectInfopanel.add(codeNameTxt);
	    suspectInfopanel.add(scrollPanePhoneNumbers);
	    suspectInfopanel.setSize(50, 100);
	    suspectInfopanel.setBorder(BorderFactory.createLineBorder(Color.black));
	    
	    //Add graphic elements on JPanel findSMSpanel
	    findSMSpanel.setLayout(new GridLayout(1,0));
	    findSMSpanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    findSMSpanel.add(enterPhoneNumTxt);
	    findSMSpanel.add(scrollPaneSuspectsSuspiciousSMS);
	    findSMSpanel.add(findSMSButton);
	    findSMSpanel.setBorder(BorderFactory.createLineBorder(Color.black));

	    
	    //Add graphic elements on JPanel findSMSpanel
	    partnersPanel.setLayout(new GridLayout(1,0));
	    partnersPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    partnersPanel.add(partnersLabel);
	    partnersPanel.add(scrollPanePartners);
	    partnersPanel.setBorder(BorderFactory.createLineBorder(Color.black));

	    //Add graphic elements on JPanel suggPartnersPanel
	    suggPartnersPanel.setLayout(new GridLayout(1,0));
	    suggPartnersPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    suggPartnersPanel.add(suggestedPartnersLabel);
	    suggPartnersPanel.add(scrollPaneSuggestedPartners);
	    suggPartnersPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Add graphic elements on JPanel sameCountryPanel
	    sameCountryPanel.setLayout(new GridLayout(1, 0));
	    sameCountryPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    sameCountryPanel.add(scrollPaneSuspectsFromSameCountry);
	    sameCountryPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Add graphic elements on JPanel panel
	    panel.add(suspectInfopanel);
	    panel.add(findSMSpanel);
	    panel.add(partnersPanel);
	    panel.add(suggPartnersPanel);
	    panel.add(sameCountryPanel);
	    backButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
	    backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(backButton);	
		
		
		//Initialize the JPanel panel 
		this.setContentPane(panel);
	    this.setTitle("Suspect Page");
		this.setVisible(true);
		this.setResizable(true);
		this.setSize(500, 600);
		this.setLocation(200, 0);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
