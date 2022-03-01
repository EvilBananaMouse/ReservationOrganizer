package reservationorganizer;

import java.util.ArrayList;
import processing.core.PApplet;

public class Main extends PApplet {

	private static final int WIDTH = 1200, HEIGHT = 700;
	private static final float FRAMERATE = 60;
	private TitleBar titleBar;
	private float titleBarHeight;
	private float leftSideHolderX;
	
	public TableCreationButton tableButton;
	public ReservationButton reservationButton;
	public ArrayList<Table> tables;
	public float defaultTableSize;
	public ArrayList<Reservation> reservations;
	public float reservationDisplayWidth;
	public static TextBox errorMessage;
	public int errorMessageCounter;
	
	

	public static void main(String[] args) {
		PApplet.main("reservationorganizer.Main");	
	}

	public void settings() {
		System.out.println("---SETTINGS---");
		size(WIDTH, HEIGHT);
		//fullScreen();
	}

	public void setup() {
		System.out.println("---SETUP---");

		frameRate(FRAMERATE);
		rectMode(CORNERS);
		titleBarHeight = 51;
		leftSideHolderX = 150;
		titleBar = new TitleBar(titleBarHeight, (float) WIDTH, leftSideHolderX, (float) HEIGHT);
		tableButton = new TableCreationButton(titleBarHeight, leftSideHolderX);
		reservationButton = new ReservationButton(tableButton.getBLY(), leftSideHolderX, (float) HEIGHT, (float) WIDTH);
		reservationDisplayWidth = 50;
		tables = new ArrayList<Table>();
		defaultTableSize = 75;
		reservations = new ArrayList<Reservation>();
		errorMessage = new TextBox("", 16, 900, 0, 300, titleBarHeight, 10000, true, false );
		errorMessageCounter = 0;
		
		System.out.println("---DRAW---");
	}

	public void draw() {
		background(255);
		textAlign(CENTER);
		titleBar.draw(this);
		tableButton.draw(this); 
		for(Table table: tables) {
			textAlign(CENTER);
			table.draw1(this, tableButton.getIsSelected());
		}
		for(Table table: tables) {
			textAlign(LEFT);
			table.draw2(this, tableButton.getIsSelected());
		}
		int j = 0;
		for(Reservation reservation: reservations) {
			reservation.draw(this, 0, reservationButton.getBLY() + j, leftSideHolderX, reservationDisplayWidth);
			j += reservationDisplayWidth;
		}
		textAlign(CENTER);
		reservationButton.draw(this);
		textAlign(LEFT);
		reservationButton.drawMenu(this);
		textAlign(CENTER);
		reservationButton.drawFinalizeButton(this);
		errorMessage.draw(this);
		if(!errorMessage.getText().equals("")); // if error message exists
			errorMessageClearer();
	}
	
	private void errorMessageClearer() {
		int totalSeconds = (int) FRAMERATE * 5;
		errorMessageCounter++;
		if(errorMessageCounter > totalSeconds) {
			errorMessage.updateText("");
			errorMessageCounter = 0;
		}
	}

	public void mouseClicked () {
		tableButton.setSelect(mouseX, mouseY);
		reservationButton.setSelect(mouseX, mouseY);
		if(tableButton.getIsSelected() && tableButton.isInside(mouseX, mouseY))
			reservationButton.makeIsSelectedFalse();
		else if(reservationButton.getIsSelected() && reservationButton.isInside(mouseX, mouseY))
			tableButton.makeIsSelectedFalse();
		for(Table table: tables) {
			if(table.getIsEditMenuOpen()) {
				table.setInTableNumberTextBox(mouseX, mouseY);
				table.setInHoursReseatTextBox(mouseX, mouseY);
				table.setInMinutesReseatTextBox(mouseX, mouseY);
				table.setInSeatsTextBox(mouseX, mouseY);
			}
			if(table.getIsEditSelected() && table.inEditButton(mouseX, mouseY)) {
				table.openEditMenu(tableButton.getIsSelected());
				table.makeEditSelectTrue();
			}
			else if(table.inEditMenu(mouseX, mouseY)); // so table is not deselected if you just click somewhere in the edit menu
			else if(table.inResizeButton(mouseX, mouseY)); // if u just click somewhere in resizeButton
			else
				if(!inAnyEditMenu())
					table.setEditSelect(mouseX, mouseY);
		}
		if(isInFloor() && tableButton.getIsSelected() && spaceForTable()) {
				tables.add(new Table(mouseX, mouseY, getUniqueTableNumber(), defaultTableSize));	
				updateTablesForReservations();
		}
		
		if(reservationButton.getIsSelected()) {
			reservationButton.setInTextBoxes(mouseX, mouseY);
			reservationButton.setSelectFinalize(mouseX, mouseY);
			if(reservationButton.getFinalizeIsSelected()) {
				if(reservationButton.isFinalizePossible()) {
					reservationButton.finalizeReservation();
					reservations.add(new Reservation(reservationButton.getGuests(), reservationButton.getHour(), reservationButton.getMinute(), reservationButton.getName(), reservationButton.getPhoneNumber(), reservationButton.getNotes(), tables));
					if(!reservations.get(reservations.size()-1).getIsValid())
						reservations.remove(reservations.size()-1);
					else {
						addNewestReservationTimeToTable();
						reservationButton.closeMenu();
						putReservationsInOrder(); 
					}
						
				}
			}
		}
	}
	
	private void putReservationsInOrder() {
		int index = 0;
		for(Reservation reservation: reservations) {
			if(reservations.get(reservations.size() - 1).getTime() < reservation.getTime())
				break;
			else
				index++;
		}
		if(index >= reservations.size())
			index = reservations.size() - 1;
		Reservation temp = reservations.get(index);
		reservations.set(index, reservations.get(reservations.size()-1));
		reservations.set(reservations.size()-1, temp);
		
		boolean done = true;
		for(int i = 0; i < reservations.size(); i++) {
			if(i + 1 >= reservations.size())
				break;
			if(reservations.get(i +1).getTime() < reservations.get(i).getTime())
				done = false;
		}
		if(!done)
			putReservationsInOrder();
	}

	private void addNewestReservationTimeToTable() {
		int latest = reservations.size()-1;
		int tableNumber = reservations.get(latest).getAssignedTable();
		for(Table table: tables) {
			if(tableNumber == table.getTableNumber())
				table.addReservationTime(reservations.get(latest).getTime());
		}
	}

	private int getUniqueTableNumber() {
		int tableNumber = -1;
		boolean unique = false;
		while(!unique) {
			tableNumber++;
			unique = true;
			for(Table table: tables) {
				if(tableNumber == table.getTableNumber())
					unique = false;
			}
		}
		return tableNumber;	
	}
	
	private boolean isTableNumberUnique(int tableNumber) {
		if(tableNumber == -1)
			return false;
		boolean unique = true;
		for(Table table: tables) {
			if(tableNumber == table.getTableNumber())
				unique = false;
		}
		return unique;
	}

	public void keyPressed() {
		for(Table table: tables) {
			if(table.getIsTypingTableNumberTextBox()) {
				if(keyCode == BACKSPACE)
					table.subtractCharacterTableNumberTextBox();
				else if(keyCode == ENTER) {
					if(isTableNumberUnique(table.getGreyTableNumber())) 
						table.finalizeTableNumber();
					else if(table.isNothingEnteredTableNumberTextBox())
						errorMessage.updateText("Nothing entered for table number.");
					else
						errorMessage.updateText("Must have unique table number.");
					table.makeIsTypingTableNumberTextBoxFalse();
				}
				else
					table.addCharacterTableNumberTextBox(key);
			}
			else if(table.getIsTypingHoursReseatTextBox()) {
				if(keyCode == BACKSPACE)
					table.subtractCharacterHoursReseatTextBox();
				else if(keyCode == ENTER) {
					
					table.finalizeHoursReseat();
					table.makeIsTypingHoursReseatTextBoxFalse();
				}
				else
					table.addCharacterHoursReseatTextBox(key);
			}
			else if(table.getIsTypingMinutesReseatTextBox()) {
				if(keyCode == BACKSPACE)
					table.subtractCharacterMinutesReseatTextBox();
				else if(keyCode == ENTER) {
					
					table.finalizeMinutesReseat();
					table.makeIsTypingMinutesReseatTextBoxFalse();
				}
				else
					table.addCharacterMinutesReseatTextBox(key);
			}
			else if(table.getIsTypingSeatsTextBox()) {
				if(keyCode == BACKSPACE)
					table.subtractCharacterSeatsTextBox();
				else if(keyCode == ENTER) {
					
					table.finalizeSeats();
					table.makeIsTypingSeatsTextBoxFalse();
				}
				else
					table.addCharacterSeatsTextBox(key);
			}
		}
		
		updateTablesForReservations();
		
		if(reservationButton.getIsSelected()) {
			if(keyCode == BACKSPACE) 
				reservationButton.subtractCharacterForSelectedTextBox();
			else if(keyCode == SHIFT);
			else if(keyCode == ENTER) 
				reservationButton.finalizeForSelectedTextBox();
			else
				reservationButton.addCharacterForSelectedTextBox(key);
			}
	}
	

	private void updateTablesForReservations() {
		for(Reservation reservation: reservations) {
			reservation.updateTablesAvailable(tables);
		}
	}

	public boolean inAnyEditMenu() {
		boolean inAnyEditMenu = false;
		for(Table table: tables) {
			if(table.inEditMenu(mouseX, mouseY))
				inAnyEditMenu = true;
		}
		return inAnyEditMenu;
	}

	
	// returns true if in floor, false otherwise
	public boolean isInFloor() {
		if(mouseX > leftSideHolderX && mouseX < WIDTH - defaultTableSize && mouseY > titleBarHeight && mouseY < HEIGHT - defaultTableSize)
			return true;
		else
			return false;
	}
	
	
	// returns true if space for table, false otherwise
	public boolean spaceForTable() {
		boolean isSpace = true;
		for(Table table : tables) {
			if(table.wouldNewTableOverlap(mouseX, mouseY)) {
				isSpace = false;
			}
		}
		return isSpace;
	}
	
	// does every Table except one passed --- used for moving the table
	public boolean spaceForTable(int numberTableIgnore) {
		boolean isSpace = true;
		for(Table table : tables) {
			if(table.getTableNumber() != numberTableIgnore && table.wouldNewTableOverlap(mouseX, mouseY))
				isSpace = false;
		}
		return isSpace;
	}
	
	public void mouseDragged() {
		if(tableButton.getIsSelected())
			for(Table table: tables) {
				if(!table.getIsMoving() && (table.getIsResizing() || (table.getIsEditSelected() && table.inResizeButton(mouseX, mouseY))))
					table.resize(mouseX, mouseY);
				else if(!table.getIsResizing() && table.getIsEditSelected() && table.isIntiallyHovering(mouseX, mouseY))
					table.move(mouseX, mouseY);
			}
	}
	
	public void mouseMoved() {
		//System.out.println("x is " + mouseX + " and y is " + mouseY);
	}
	

	public void mouseReleased() {
		for(Table table: tables) {
			table.updateIsIntiallyHovering();
			if(table.getDoesGreyTableExist()) {
				if(table.getIsResizing() && isInFloor() && tableButton.getIsSelected() && spaceForTable(table.getTableNumber()))
					table.updatePositionForResizing();
				else if(isInFloor() && tableButton.getIsSelected() && spaceForTable(table.getTableNumber()))
					table.updatePositionForMoving();
				else {
					table.deleteGreyTable();
					errorMessage.updateText("Not valid space for table.");
				}
			}
		}
	}
}








/*
 TODO:
 	PRIORITY: 
 	
 	Tables can be moved - DONE
 	Table edit menus are drawn after all other tables - DONE
 	Table Size can change - DONE
 	Able to change table number, # of seats, reseating time - DONE 
 	Text next to table edit menu text boxes - DONE
 	Only unique table numbers possible - DONE
 	Closing edit menu resets status of strings in text boxes in that menu when reopened - DONE
 	Make reservation button - DONE
 	Reservation button can create reservations - DONE
 	Reservations can be seen on left hand side - DONE
 	Reservations are assigned to proper tables - DONE
 	Reservations on left hand side appear in order - DONE
 	Can change reservation assignments and other info - 
 	Can view reservation info - 
 	Editing table information in edit menu fixes reservations(e.g tableNumber, reseat time) -
 	
 	NON-PRIORITY:
 	
 	non-numeric characters cannot be entered into number-only text boxes, and vice versa
 	tables can be "blocked"
 	Cursors change (use mouseMoved)
 	Table Edit Menu will not be able to 'leave' the screen
 	When trying to resize table below min size, it does not form non-square shapes  - DONE
 	Tables cannot be made to 'leave' the screen (bottom and right) - DONE
 	Red error messages in top right - DONE
 	Error messages dissapear after ~5 seconds - DONE
 	Make grey table lines dashed
 	Top Right red X which will close edit menu
 */
