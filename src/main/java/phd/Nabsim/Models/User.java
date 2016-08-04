package phd.Nabsim.Models;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * User class which contains information on the receiver of the 
 * notification. Data is based on the survey information available and
 * is sometimes assumed. 
 * 
 * Contains a number of "subject" attributes from which, once a notification
 * is fired at the user,  a random choice is made between them to select the
 * subject for the notification.
 * 
 * @author kfraser
 *
 */
public class User {
	
	private String id;
	
	private ArrayList<MobileApp> favoriteApps;
	
	private ArrayList<Subject> activities; // never remove from this list - always populate
	private ArrayList<Subject> groups; // never remove from this list - always populate
	private ArrayList<Subject> events; // these change week by week, as chosen, remove from list - only populate if not null
	
	private Subject mostEnjoyableEvent; // set to null when chosen - only populate if not null
	private Subject mostLookedForwardEvent; // set to null when chosen - only populate if not null
	
	private ArrayList<Subject> randomChoice;
    private Random randomGenerator;
    
    private Personality personality;
    
    private ArrayList<Notification> notifications;
	
	public User(){
		randomChoice = new ArrayList<Subject>();
		activities = new ArrayList<Subject>();
		groups = new ArrayList<Subject>();
		events = new ArrayList<Subject>();
		mostEnjoyableEvent = null;
		mostLookedForwardEvent = null;
		
        randomGenerator = new Random();
	}
		
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ArrayList<MobileApp> getFavoriteApps() {
		return favoriteApps;
	}
	public void setFavoriteApps(ArrayList<MobileApp> favoriteApps) {
		this.favoriteApps = favoriteApps;
	}	
	
	public Subject getSubject(String senderIdentity){
		Subject chosenSubject = new Subject();
		populateList(senderIdentity);
		
		if(!randomChoice.isEmpty()){
			int index = randomGenerator.nextInt(randomChoice.size());
	        chosenSubject = randomChoice.get(0);
		}
		else{
			chosenSubject.setSubject("no subject found");
			chosenSubject.setGround_truth("no subject found");
		}
		
		return chosenSubject;
	}
	
	/**
	 * resets the list of possible subjects (one per type) and 
	 * adds new randomly selected subjects (removes events that have 
	 * already been chosen)
	 */
	private void populateList(String senderIdentity){

		randomChoice = new ArrayList<Subject>();
		Subject chosenSubject = new Subject();
		int index;
		
		// Select one activity subject (depending on sender identity - i.e. friend, stranger, family or partner)
		if(!activities.isEmpty()){
			if(senderIdentity.equals("stranger")){
				chosenSubject = activities.get(0);
			}
			if(senderIdentity.equals("friend")){
				int rand = (int)(Math.random()*2);
				switch(rand){
				case 0:
					chosenSubject = activities.get(1);
					break;
				case 1:
					chosenSubject = activities.get(2);
					break;
				}
			}
			if(senderIdentity.equals("partner")){
				chosenSubject = activities.get(3);
			}
	        randomChoice.add(0,chosenSubject);
		}
        
        // Select one group subject randomly
		if(!groups.isEmpty()){
	        index = randomGenerator.nextInt(groups.size());
	        chosenSubject = groups.get(index);
	        randomChoice.add(chosenSubject);
		}
        
        // Select one event subject randomly - if not null add to list
		if(!events.isEmpty()){
	        index = randomGenerator.nextInt(events.size());
	        chosenSubject = events.get(index);
	        if(chosenSubject.getGround_truth() != "" || 
	        		chosenSubject.getGround_truth() != null){
	        	randomChoice.add(chosenSubject);
	        }
		}
        
        // Add most enjoyable event of the week - set to null
        if(mostEnjoyableEvent != null){
        	randomChoice.add(mostEnjoyableEvent);
        	mostEnjoyableEvent = null;
        }
        
        // Add most looked forward to event of the week - set to null
        if(mostLookedForwardEvent  != null){
        	randomChoice.add(mostLookedForwardEvent);
        	mostLookedForwardEvent = null;
        }
	}

	public ArrayList<Subject> getActivities() {
		return activities;
	}

	public void setActivities(ArrayList<Subject> activities) {
		this.activities = activities;
	}

	public Personality getPersonality() {
		return personality;
	}

	public void setPersonality(Personality personality) {
		this.personality = personality;
	}

	public ArrayList<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(ArrayList<Notification> notifications) {
		this.notifications = notifications;
	}
	
}
