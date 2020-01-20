import java.util.ArrayList;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class SuspectGUI extends JFrame {
	
	//Declaration of JFrame elements
	private JPanel panel;
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
		
		//Initialize the JPamel panel
		panel = new JPanel();
		
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
			partnersListArray[x]=tempS.getPotentialPartners().get(i).getName();
			partnersListArray[x+1]=tempS.getPotentialPartners().get(i).getCodeName();
			x=x+2;
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
				
				Suspect s = registry.getAllSuspects().get(0);
				String enteredNum = enterPhoneNumTxt.getText();
				Communication tempSMS = new SMS();
				
				if (e.getSource().equals(findSMSButton)) {
					int x=0;
					for (int i=0; i<registry.getAllSuspects().size(); i++) {
						for (int j=0; j<registry.getAllCommunications().size(); j++) {
							for (int z=0; z<s.getPhoneNumbers().size(); z++) {
								//Check if entered phone number is a suspect's phone number 
								if (s.getPhoneNumbers().get(z).equals(enteredNum)) {
									//Add suspicious messages to array
									suspectsSuspiciousSMSArray[x]=((SMS) tempSMS).getContentText();
									x++;
								}
								else {
									suspectsSuspiciousSMSArray[0]="There are no suspicious messages for this number";
								}
							}
						}
						
					}
					//scrollPaneSuspectsSuspiciousSMS.removeAll();
					//suspectsSuspiciousSMSArray[0]= tempSMS.getContentText();
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
		
		
		//Initialize the JPanel panel 
		this.setContentPane(panel);
	    this.setTitle("Suspect Page");
		this.setVisible(true);
		this.setResizable(true);
	    this.setSize(500, 600);
		this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		//Add all graphic elements on JPanel panel
		panel.add(nameTxt);
		panel.add(codeNameTxt);
		panel.add(scrollPanePhoneNumbers);
		panel.add(enterPhoneNumTxt);
		panel.add(scrollPaneSuspectsSuspiciousSMS);
		panel.add(findSMSButton);
		panel.add(partnersLabel);
		panel.add(scrollPanePartners);
		panel.add(suggestedPartnersLabel);
		panel.add(scrollPaneSuggestedPartners);
		panel.add(scrollPaneSuspectsFromSameCountry);
		panel.add(backButton);		
		
	}

}
