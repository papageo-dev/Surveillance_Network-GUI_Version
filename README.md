# Surveillance Network - GUI Version

Another version of [Surveillance Network](https://github.com/papageo-dev/Surveillance_Network.git) system for **monitoring** the **activity** of a network of **suspected criminals** and the **relationships** that exist between them, in Java.

# General Description

Every Suspect on the **Surveillance Network** is characterized by his/her **name**, his/her **code name**, the **country of origin**,      
the **city in which he/she operates** and a list of **telephone numbers** that he/she uses.                                  
Each suspect is associated with one or more than the rest of the network's suspects(potential partners).                    
There is a **connection** between two suspects, if they have **contacted** by phone at least once.

The system also maintains a central **Registry** of all **Communications**, which can be either PhoneCall or SMS.                     
Registry also maintains a list of all suspects. 

# New System Features

**All functions** of [Surveillance Network](https://github.com/papageo-dev/Surveillance_Network#system-features) have been **retained** and the following have been **added**:

1. **Graphical User Interface(GUI)**
   This version of **Surveillance Network** has a **Graphical User Interface**, through which the    
   user can **interact** with the program and use all its features.

2. User can **search** for a suspect in the system by name and see all the  
   **information/details** about him/her.
   
3. A new advanced function that returns a list of specific suspect's **suggested partners**.

4. The user can **enter** a phone number and **display** all suspicious SMS sent between this 
   number and all suspect phone numbers.
   
5. **Suspicious Network Visualization System:**
   Considering that the non-oriented graph of the suspects has as **nodes** the **suspects** and      
   as **acne** relationships with **potential partners**, appears corresponding **network**.
   Each **node** is labeled the **"Code Name"** of the respective suspect and **only one acme**    
   should appear between the suspects, in case they are **possible collaborators**.
