package reservationorganizer;
import java.util.ArrayList;
import processing.core.PApplet;

public class Reservation {

	private int guests;
	private int hour, minute;
	private String name, phoneNumber, notes;
	private boolean isSeated;
	private boolean isValid;
	private int totalMinute;
	
	private int assignedTable;
	private ArrayList<Table> tables;
	private ArrayList<Integer> possiblesSeats;
	private ArrayList<Integer> possiblesTime;
	private ArrayList<Integer> possibles;
	
	private float TLX, TLY, length, width;
	private boolean isSelected;
	
	public Reservation (int guests, int hour, int minute, String name, String phoneNumber, String notes, ArrayList<Table> tables) {
		this.guests = guests;
		this.hour = hour;
		this.minute = minute;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.notes = notes;
		isSeated = false;
		isValid = true;
		isSelected = false;
		totalMinute = hour * 60 + minute;
		this.tables = tables;
		assignTable();
		TLX = 0;
		TLY = 0;
		length = 0;
		width = 0;
	}
	
	public void draw(PApplet p, float TLX, float TLY, float length, float width){
		this.TLX = TLX;
		this.TLY = TLY;
		this.length = length;
		this.width = width;
		if(isSelected)
			p.stroke(99,255,32);
		else
			p.stroke(0,0,0);
		p.fill(255,255,255);
		p.rect(TLX, TLY, TLX + length, TLY + width);
		
		p.fill(0);
		p.textSize(18);
		p.text(getLastName() + getInbetweenSpace() + getTextTime(), TLX + 3, TLY, TLX + length, TLY + width/2);
		p.text("" + assignedTable, TLX + 3, TLY + width/2, TLX + length, TLY + width);
	}

	private String getInbetweenSpace() {
		return "   ";
	}

	private String getTextTime() {
		String textTime = new String();
		if(hour < 10)
			textTime += "0";
		textTime += hour;
		textTime += ":";
		if(minute < 10)
			textTime += "0";
		textTime += minute;
		return textTime;
	}

	private String getLastName() {
		String lastName = new String();
		int starter = name.indexOf(" ");
		lastName = name.substring(starter+1);
		if(lastName.length() > 8)
			lastName = lastName.substring(starter+1, starter+10);
		return lastName;
	}

	private void assignTable() {
		possiblesSeats = new ArrayList<Integer> ();
		possiblesTime = new ArrayList<Integer> ();
		possibles = new ArrayList<Integer> ();
		
		for(Table table: tables) {
			possiblesSeats.add(table.getTableNumber());
			possiblesTime.add(table.getTableNumber());
		}
		validSeats();
		validTime();
		if(isValid)
			seatsAndTimeMatchUp();
		if(isValid) {
			//assignedTable = possibles.get(0);
			assignedTable = tables.get(possibles.get(0)).getTableNumber();
		}
				
		}

	private void seatsAndTimeMatchUp() {
		for(int i = 0; i < possiblesSeats.size(); i++) {
			for(int j = 0; j < possiblesTime.size(); j++) {
				if(possiblesSeats.get(i) == possiblesTime.get(j)) {
					possibles.add(i);
				}
			}
		}
		if(possibles.size() == 0) {
			Main.errorMessage.updateText("No tables with enough seats and time!");
			isValid = false;
		}
	}

	private void validTime() {
		int i = 0;
		for(Table table: tables) {
			boolean thisTableGood = true;
			int reseatTime = table.getHoursReseat() * 60 + table.getMinutesReseat();
			ArrayList<Integer> tableReservationTimes = table.getTimesReservations();
			for(int j = 0; j < reseatTime; j++ ) {
				if(!thisTableGood)
					break;
				for(int k = 0; k < tableReservationTimes.size(); k++) {
					if(!thisTableGood)
						break;
					else if(totalMinute + j > tableReservationTimes.get(k) && totalMinute + j < tableReservationTimes.get(k) + reseatTime) {
						thisTableGood = false;
						possiblesTime.remove(i);
					}		
				}
			}
			if(thisTableGood)
				i++;
		}
		if(possiblesTime.size() == 0) {
			Main.errorMessage.updateText("No time for this reservation!");
			isValid = false;
		}
	}

	private void validSeats() {
		int i = 0;
		for(Table table: tables) {
			if(guests > table.getSeats())
				possiblesSeats.remove(i);
			else
				i++;
		}
		if(possiblesSeats.size() == 0) {
			Main.errorMessage.updateText("No table with enough seats!");
			isValid = false;
		}
	}

	public boolean getIsValid() {
		return isValid;
	}

	public int getAssignedTable() {
		return assignedTable;
	}

	public int getTime() {
		return totalMinute;
	}
	
	public void updateTablesAvailable(ArrayList<Table> tables) {
		this.tables = tables;
	}

	
}
