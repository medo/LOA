package eg.edu.guc.loa.controllers;

public class Room {

	private String ownerName;
	private int numberOfSubscribers;
	private boolean availableForPlaying;
	
	public Room(String name) {
		ownerName = name;
	}
	
	public String getName() {
		return this.ownerName;
	}
	
	public void setName(String name) {
		ownerName = name;
	}
	
	public int getNumberOfSubscribers() {
		return numberOfSubscribers;
	}
	
	public void setNumberOfSubscribers(int number) {
		numberOfSubscribers = number;
	}
	
	public boolean isAvailableForPlaying() {
		return numberOfSubscribers == 1;
	}
	
}
