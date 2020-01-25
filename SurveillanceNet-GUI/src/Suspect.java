import java.util.ArrayList;

public class Suspect {
	
	//Suspect's fields
	private String name; //Suspect's name
	private String codeName; //Suspect's code name
	private String originCountry; //Suspect's country of origin
	private String actionCity; //Suspect's city of action
	
	//Create an ArrayList object, that contains phone numbers used by suspect
	private ArrayList<String> phoneNumbers;
	
	//Create an ArrayList object, that contains suspect's potential partners
	private ArrayList<Suspect> potentialPartners;
	
	//Create an ArrayList object, that contains suspect's suggested partners
	private ArrayList<Suspect> suggestedPartners;
	
	//Create an ArrayList, that will contains all common partners, between aSuspect and current suspect
	private ArrayList<Suspect> commonPartners;
	
	//Suspect's constructor, without arguments
	public Suspect() {
		
	}

	//Suspect's constructor, with arguments
	public Suspect(String name, String codeName, String originCountry, String actionCity) {
		this.name = name; //Initialize suspect's name
		this.codeName = codeName; //Initialize suspect's code name
		this.originCountry = originCountry; //Initialize suspect's country of origin
		this.actionCity = actionCity; //Initialize suspect's city of action
		phoneNumbers = new ArrayList<String>(); //Initialize a list of phone numbers used by suspect
		potentialPartners = new ArrayList<Suspect>(); //Initialize a list of suspect's potential partners
		suggestedPartners = new ArrayList<Suspect>(); //Initialize a list of suspect's suggested partners
		commonPartners = new ArrayList<Suspect>(); //Initialize a list of common partners, between current suspect and another suspect
	}
	
	//Add a phone number used by suspect to the ArrayList: "phoneNumbers"
	public void addNumber(String number) {
		phoneNumbers.add(number);
	}
	
	//Add a suspect's potential partner to the ArrayList: "potentialPartners"
	public void addPotentialPartner(Suspect aSuspect) {
		
		//Add aSuspect in current suspect's list with potential partners, if he/she isn't already entered
		if (!this.name.equals(aSuspect.name) && !this.codeName.equals(aSuspect.codeName) && !this.isConnectedTo(aSuspect)) {
			this.potentialPartners.add(aSuspect);
		}
	}
	
	//Return true if current suspect and aSuspect are connected, else return false
	public boolean isConnectedTo(Suspect aSuspect) {
		
		boolean connected=false;
	
		//Search for the aSuspect's name in current suspect's ArrayList with potential partners
		for (Suspect s : potentialPartners) {
			//if found a suspect with the same name and code name, connected=true and break
			if (s.getName().contains(aSuspect.getName()) && s.getCodeName().contains(aSuspect.getCodeName())) {
				connected=true;
				if (connected) break;
			}
		}
		return connected;
	}
	
	//Return a list that contains common potential partners of current suspect and aSuspect
	public ArrayList<Suspect> getCommonPartners(Suspect aSuspect) {
		
		//Search in current suspect's and aSuspect's list with potential partners for common partners
		for (Suspect s1 : potentialPartners) {
			for (Suspect s2 : aSuspect.potentialPartners) {
				//If found a partner with the same name and code name in both lists, add to local ArrayList 'commonPartners'
				if (s1.getName().equals(s2.getName()) && s1.getCodeName().equals(s2.getCodeName())) {
					commonPartners.add(s2);
					break;
				}
			}
		}
		//Return list with all common partners
		return commonPartners; 
	}
	
	//Print suspect's potential partners info
	public void printPotentialPartners() {
		for (int i=0; i<potentialPartners.size(); i++) {
			/*If current suspect and the potential partner NOT coming from the same Country,
			 * print potential partner's name and code name
			 */
			if (!this.originCountry.equals(this.potentialPartners.get(i).getOriginCountry())) {
				System.out.println(potentialPartners.get(i).name + " (" + potentialPartners.get(i).codeName + ") ");
			}
			//If they are coming from the same Country, print potential partner's name, code name and a "*'
			else if (this.originCountry.equals(this.potentialPartners.get(i).getOriginCountry())) {
				System.out.println(potentialPartners.get(i).name + " (" + potentialPartners.get(i).codeName + ") " + "*");
			}
		}
	}
	
	//Return a list with suggested partners for aSuspect
	public ArrayList<Suspect> getSuggestedPartners() {
		
		Registry r = new Registry();
				
		for (Suspect s1 : getCommonPartners(this)) {
			for (Suspect s2 : r.getAllSuspects().get(getNumberOfPotentialPartners()).getCommonPartners(s1)) {
				//If found a partner who isn't in list, add to ArrayList 'suggestedPartners'
				if (!s1.getCommonPartners(s2).equals(s2.getCommonPartners(s1))) {
					suggestedPartners.add(s1);
				}
			}
		}
		
		//Test
		System.out.println(suggestedPartners);
		
		//Return a list with all suggested partners
		return suggestedPartners;	
	}

	//Return current suspect's name
	public String getName() {
		return name;
	}

	//Return current suspect's code name
	public String getCodeName() {
		return codeName;
	}
	
	//Return number of suspect's potential partners
	public int getNumberOfPotentialPartners(){
		return potentialPartners.size();
	}
	
	//Return a list with supsect's potential partners
	public ArrayList<Suspect> getPotentialPartners(){
		return potentialPartners;
	}
	
	//Return suspect's origin Country
	public String getOriginCountry() {
		return originCountry;
	}
	
	//Return a list with supsect's phone numbers
	public ArrayList<String> getPhoneNumbers(){
		return phoneNumbers;
	}
	
	//Return a list with suspect's suggested partners
	public ArrayList<Suspect> getListOfSuggestedPartners() {
		return suggestedPartners;
	}
	
	@Override
	public String toString() {
		return (this.name + ", " + this.codeName);
	}
	

}
