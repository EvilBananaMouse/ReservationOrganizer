package reservationorganizer;
import processing.core.PApplet;
import java.util.ArrayList;


// this class describes the tables of the restaurant
public class Table {
	private int seats;
	private int hoursReseat;
	private int minutesReseat;
	private float x;
	private float y;
	private static float defaultSize;
	private float fontSize;
	private float size;
	private int tableNumber;
	
	private boolean isEditSelected;
	private float editButtonSize;
	private float editButtonX, editButtonY;
	
	private boolean isMoving;
	
	private boolean isResizing;
	private float resizeButtonSize;
	private float resizeButtonX, resizeButtonY;
	
	private boolean isEditMenuOpen;
	private float editMenuLength;
	private float editMenuWidth;
	
	private ArrayList<GreyTable> greyTable;
	private boolean doesGreyTableExist;
	
	private boolean isIntiallyHovering;
	
	private TextBox tableNumberTextBox;
	private float tableNumberTextBoxX, tableNumberTextBoxY;
	private float tableNumberTextBoxLength, tableNumberTextBoxWidth;
	private int tableNumberTextBoxMaxChars;
	
	private TextBox hoursReseatTextBox;
	private float hoursReseatTextBoxX, hoursReseatTextBoxY;
	private float hoursReseatTextBoxLength, hoursReseatTextBoxWidth;
	private int hoursReseatTextBoxMaxChars;
	
	private TextBox minutesReseatTextBox;
	private float minutesReseatTextBoxX, minutesReseatTextBoxY;
	private float minutesReseatTextBoxLength, minutesReseatTextBoxWidth;
	private int minutesReseatTextBoxMaxChars;
	
	private TextBox seatsTextBox;
	private float seatsTextBoxX, seatsTextBoxY;
	private float seatsTextBoxLength, seatsTextBoxWidth;
	private int seatsTextBoxMaxChars;
	
	private ArrayList<Integer> timesReservations;
	
	
	
	
	// by default, 4 seats, and estimated 1 and half hours to reseat the table
	// table number will just be size of tables arrayList in Main after adding the table
	public Table(float mouseX, float mouseY, int tableNumber, float defaultTableSize) {
		seats = 4;
		hoursReseat = 1;
		minutesReseat = 0;
		x = mouseX;
		y = mouseY;
		defaultSize = defaultTableSize;
		size = defaultSize;
		fontSize = size * (float) 0.28;
		this.tableNumber = tableNumber;
		
		isEditSelected = true;
		
		resizeButtonSize = size * (float) 0.14;
		resizeButtonX = x - (resizeButtonSize / 2);
		resizeButtonY = y - (resizeButtonSize / 2);
		isResizing = false;
		
		isMoving = false;
		
		editButtonSize = resizeButtonSize;
		editButtonX = x + size * (float) 0.8;
		editButtonY = y + size * (float) 0.1;
		
		isEditMenuOpen = false;
		editMenuLength = 250;
		editMenuWidth = 120;
		
		greyTable = new ArrayList<GreyTable>();
		doesGreyTableExist = false;
		
		tableNumberTextBoxX = editButtonX + editMenuLength * (float) 0.1;
		tableNumberTextBoxY = editButtonY - editMenuWidth + editButtonSize + editMenuWidth * (float) 0.1;
		tableNumberTextBoxLength = 46;
		tableNumberTextBoxWidth = 30;
		tableNumberTextBoxMaxChars = 3;
		tableNumberTextBox = new TextBox(this.tableNumber, 22, tableNumberTextBoxX, tableNumberTextBoxY, tableNumberTextBoxLength, tableNumberTextBoxWidth, tableNumberTextBoxMaxChars);
		
		hoursReseatTextBoxX = tableNumberTextBoxX;
		hoursReseatTextBoxY = tableNumberTextBoxY + tableNumberTextBoxWidth + 3;
		hoursReseatTextBoxLength = 15;
		hoursReseatTextBoxWidth = 30;
		hoursReseatTextBoxMaxChars = 1;
		hoursReseatTextBox = new TextBox(hoursReseat, 22, hoursReseatTextBoxX, hoursReseatTextBoxY, hoursReseatTextBoxLength, hoursReseatTextBoxWidth, hoursReseatTextBoxMaxChars);
		
		minutesReseatTextBoxX = hoursReseatTextBoxX + hoursReseatTextBoxLength + 6;
		minutesReseatTextBoxY = tableNumberTextBoxY + tableNumberTextBoxWidth + 3;
		minutesReseatTextBoxLength = 25;
		minutesReseatTextBoxWidth = 30;
		minutesReseatTextBoxMaxChars = 2;
		minutesReseatTextBox = new TextBox(minutesReseat, 22, minutesReseatTextBoxX, minutesReseatTextBoxY, minutesReseatTextBoxLength, minutesReseatTextBoxWidth, minutesReseatTextBoxMaxChars);
		
		seatsTextBoxX = tableNumberTextBoxX;
		seatsTextBoxY = hoursReseatTextBoxY + hoursReseatTextBoxWidth + 3;
		seatsTextBoxLength = 46;
		seatsTextBoxWidth = 30;
		seatsTextBoxMaxChars = 2;
		seatsTextBox = new TextBox(seats, 22, seatsTextBoxX, seatsTextBoxY, seatsTextBoxLength, seatsTextBoxWidth, seatsTextBoxMaxChars);
		
		timesReservations = new ArrayList<Integer>();
	}
	// draws table itself, table number text, editMenuButton, and resizeButton
	public void draw1 (PApplet p, boolean isTableEditButtonSelected) {
		if(isEditSelected && isTableEditButtonSelected) {
			p.fill(255,255,255);
			p.stroke(99,255,32);
			p.rect(x, y, size + x, size + y);
			p.rect(editButtonX, editButtonY, editButtonX + editButtonSize, editButtonY + editButtonSize);
			p.rect(resizeButtonX, resizeButtonY, resizeButtonX + resizeButtonSize, resizeButtonY + resizeButtonSize);
			
			p.fill(99,255,32);
			p.textSize(fontSize);
			p.text(tableNumber, size/2 + x, size/2 + y);
			
		}
		else {
			p.fill(255,255,255);
			p.stroke(0,0,0);
			p.rect(x, y, x + size, y + size);
			
			p.fill(0);
			p.textSize(fontSize);
			p.text(tableNumber, size/2 + x, size/2 + y);
		}
	}
	
	// second draw function so i can change text alligning in main
	// draws editMenu
	public void draw2(PApplet p, boolean isTableEditButtonSelected) {
		if (isEditMenuOpen && isEditSelected && isTableEditButtonSelected) {
			p.fill(255,255,255);
			p.stroke(99,255,32);
			p.rect(editButtonX, editButtonY - editMenuWidth + editButtonSize, editMenuLength + editButtonX, editMenuWidth + editButtonY - editMenuWidth + editButtonSize);
			
			tableNumberTextBox.draw(p);
			hoursReseatTextBox.draw(p);
			minutesReseatTextBox.draw(p);
			seatsTextBox.draw(p);
			
			p.textSize(22);
			p.text("Table #", tableNumberTextBoxX + tableNumberTextBoxLength + 3, tableNumberTextBoxY, tableNumberTextBoxX + (editMenuLength), tableNumberTextBoxY + tableNumberTextBoxWidth);
			p.text("Hrs, Mins to Reseat", minutesReseatTextBoxX + minutesReseatTextBoxLength + 3, minutesReseatTextBoxY, minutesReseatTextBoxX + (editMenuLength), minutesReseatTextBoxY + minutesReseatTextBoxWidth);
			p.text("# of Seats", seatsTextBoxX + seatsTextBoxLength + 3, seatsTextBoxY, seatsTextBoxX + (editMenuLength), seatsTextBoxY + seatsTextBoxWidth);
		}
		if(doesGreyTableExist)
			for(GreyTable grey: greyTable) {
					grey.draw(p, greyTable.size());
			}
	}
	
	public void move(float mouseX, float mouseY) {
		isEditMenuOpen = false;
		isMoving = true;
		if(!doesGreyTableExist) 
			greyTable.add(new GreyTable(x,y, fontSize, size, tableNumber, greyTable.size(), isResizing));
		doesGreyTableExist = true;
		for(GreyTable grey: greyTable) {
			grey.updatePosition(mouseX, mouseY);
		}
	}
	
	public void resize(float mouseX, float mouseY) {
		isEditMenuOpen = false;
		isResizing = true;
		if(!doesGreyTableExist)
			greyTable.add(new GreyTable(x,y, fontSize, size, tableNumber, greyTable.size(), isResizing));
		doesGreyTableExist = true;
		for(GreyTable grey: greyTable) {
			grey.resize(mouseX, mouseY);
		}
	}

	public boolean wouldNewTableOverlap(float newX, float newY) {
		boolean wouldOverlap = false;
		if(newX >= x && newX <= x + size && newY >= y && newY <= y + size) // is cursor in table
			wouldOverlap = true;
		else if(newX + defaultSize >= x && newX + defaultSize <= x + size && newY + defaultSize >= y && newY + defaultSize <= y + size) // would bottom right of new table be in table
			wouldOverlap = true;
		else if(newX + defaultSize >= x && newX + defaultSize <= x + size && newY >= y && newY <= y + size) // would top right of new table be in table
			wouldOverlap = true;
		else if(newX >= x && newX <= x + size && newY + defaultSize >= y && newY + defaultSize <= y + size) // would bottom left of new table be in table
			wouldOverlap = true;
		if(isEditMenuOpen) {
			if(newX >= editButtonX && newX <= editButtonX + editMenuLength && newY >= editButtonY - editMenuWidth + editButtonSize && newY <= editButtonY - editMenuWidth + editButtonSize + editMenuWidth)
				wouldOverlap = true;
			else if(newX + defaultSize >= editButtonX && newX + defaultSize <= editButtonX + editMenuLength && newY + defaultSize >= editButtonY - editMenuWidth + editButtonSize && newY + defaultSize <= editButtonY - editMenuWidth + editButtonSize + editMenuWidth)
				wouldOverlap = true;
			else if(newX + defaultSize >= editButtonX && newX + defaultSize <= editButtonX + editMenuLength && newY >= editButtonY - editMenuWidth + editButtonSize && newY <= editButtonY - editMenuWidth + editButtonSize + editMenuWidth)
				wouldOverlap = true;
			else if(newX >= editButtonX && newX <= editButtonX + editMenuLength && newY + defaultSize >= editButtonY - editMenuWidth + editButtonSize && newY + defaultSize <= editButtonY - editMenuWidth + editButtonSize + editMenuWidth)
				wouldOverlap = true;
		}
		return wouldOverlap;
	}

	public void setEditSelect(float mouseX, float mouseY) {
		if( (mouseX >= x && mouseX <= x + size) && (mouseY >= y && mouseY <= y + size))
			isEditSelected = !isEditSelected;
		else {
			isEditSelected = false;
			isEditMenuOpen = false;
		}
	}
	
	public boolean inEditMenu(float mouseX, float mouseY) {
		boolean inEditMenu = false;
		if(isEditMenuOpen) {
			if(mouseX >= editButtonX && mouseX <= editButtonX + editMenuLength && mouseY >= editButtonY - editMenuWidth + editButtonSize && mouseY <= editButtonY - editMenuWidth + editButtonSize + editMenuWidth) 
				inEditMenu = true;
		}
		return inEditMenu;
	}
	
	public boolean getIsEditSelected() {
		return isEditSelected;
	}

	public boolean inEditButton(int mouseX, int mouseY) {
		if( (mouseX >= editButtonX && mouseX <= editButtonX + editButtonSize) && (mouseY >= editButtonY && mouseY <= editButtonY + editButtonSize))
			return true;
		else
			return false;
	}

	public void openEditMenu(boolean isTableEditButtonSelected) {
		if(isTableEditButtonSelected) {
			isEditMenuOpen = true;
			tableNumberTextBox.updateText(tableNumber);
			hoursReseatTextBox.updateText(hoursReseat);
			minutesReseatTextBox.updateText(minutesReseat);
			seatsTextBox.updateText(seats);
		}
		
	}

	public void makeEditSelectTrue() {
		isEditSelected = true;
	}

	public boolean getDoesGreyTableExist() {
		return doesGreyTableExist;
	}

	public void updatePositionForMoving() {
		for(GreyTable grey: greyTable) {
			x = grey.getX();
			y = grey.getY();
		}
		editButtonX = x + size * (float) 0.8;
		editButtonY = y + size * (float) 0.1;
		
		resizeButtonX = x - (resizeButtonSize / 2);
		resizeButtonY = y - (resizeButtonSize / 2);
		
		doesGreyTableExist = false;
		greyTable.remove(0);
		
		updateTextBoxes();
		
		isMoving = false;
		
		
	}
	
	private void updateTextBoxes() {
		
		tableNumberTextBoxX = editButtonX + editMenuLength * (float) 0.1;
		tableNumberTextBoxY = editButtonY - editMenuWidth + editButtonSize + editMenuWidth * (float) 0.1;
		tableNumberTextBox.updatePosition(tableNumberTextBoxX, tableNumberTextBoxY);
		hoursReseatTextBoxX = tableNumberTextBoxX;
		hoursReseatTextBoxY = tableNumberTextBoxY + tableNumberTextBoxWidth + 3;
		hoursReseatTextBox.updatePosition(hoursReseatTextBoxX, hoursReseatTextBoxY);
		minutesReseatTextBoxX = hoursReseatTextBoxX + hoursReseatTextBoxLength + 6;
		minutesReseatTextBoxY = tableNumberTextBoxY + tableNumberTextBoxWidth + 3;
		minutesReseatTextBox.updatePosition(minutesReseatTextBoxX, minutesReseatTextBoxY);
		seatsTextBoxX = tableNumberTextBoxX;
		seatsTextBoxY = hoursReseatTextBoxY + hoursReseatTextBoxWidth + 3;
		seatsTextBox.updatePosition(seatsTextBoxX, seatsTextBoxY);
		
	}
	public void updatePositionForResizing() {
		for(GreyTable grey: greyTable) {
			x = grey.getResizingX();
			y = grey.getResizingY();
			size = grey.getSize();
		}
		editButtonX = x + size * (float) 0.8;
		editButtonY = y + size * (float) 0.1;
		
		resizeButtonX = x - (resizeButtonSize / 2);
		resizeButtonY = y - (resizeButtonSize / 2);
		
		doesGreyTableExist = false;
		greyTable.remove(0);
		
		updateTextBoxes();
		
		fontSize = size * (float) 0.28;
		
		isResizing = false;
		
		resizeButtonSize = size * (float) 0.14;
		resizeButtonX = x - (resizeButtonSize / 2);
		resizeButtonY = y - (resizeButtonSize / 2);
		
		editButtonSize = resizeButtonSize;
		editButtonX = x + size * (float) 0.8;
		editButtonY = y + size * (float) 0.1;
	}

	public void deleteGreyTable() {
		doesGreyTableExist = false;
		greyTable.remove(0);
	}

	public boolean isIntiallyHovering(float mouseX, float mouseY) {
		if(isIntiallyHovering)
			return true;
		else if (mouseX >= x && mouseX <= x + size && mouseY >= y && mouseY <= y + size) {
			isIntiallyHovering = true;
			return true;
		}
		else
			return false;
	}
	
	public void updateIsIntiallyHovering() {
		isIntiallyHovering = false;
	}

	public int getTableNumber() {
		return tableNumber;
	}

	public void setInTableNumberTextBox(float mouseX, float mouseY) {
		tableNumberTextBox.setInTextBox(mouseX, mouseY);
	}

	public boolean getIsEditMenuOpen() {
		return isEditMenuOpen;
	}

	public boolean getIsTypingTableNumberTextBox() {
		return (tableNumberTextBox.getIsTyping());
	}

	public void addCharacterTableNumberTextBox(char key) {
		tableNumberTextBox.addCharacter(key);
	}

	public void subtractCharacterTableNumberTextBox() {
		tableNumberTextBox.subtractCharacter();
	}

	public void finalizeTableNumber() {
		if(tableNumberTextBox.getText().length() > 0)
			tableNumber = Integer.parseInt(tableNumberTextBox.getText());
		else
			Main.errorMessage.updateText("Nothing for table number entered.");
	}

	public void makeIsTypingTableNumberTextBoxFalse() {
		tableNumberTextBox.makeIsTypingFalse();
	}

	public void setInHoursReseatTextBox(int mouseX, int mouseY) {
		hoursReseatTextBox.setInTextBox(mouseX, mouseY);
	}

	public void setInMinutesReseatTextBox(int mouseX, int mouseY) {
		minutesReseatTextBox.setInTextBox(mouseX, mouseY);
	}

	public void setInSeatsTextBox(int mouseX, int mouseY) {
		seatsTextBox.setInTextBox(mouseX, mouseY);
	}

	public boolean getIsTypingHoursReseatTextBox() {
		return hoursReseatTextBox.getIsTyping();
	}

	public void subtractCharacterHoursReseatTextBox() {
		hoursReseatTextBox.subtractCharacter();
		
	}

	public void finalizeHoursReseat() {
		if(hoursReseatTextBox.getText().length() > 0)
			hoursReseat = Integer.parseInt(hoursReseatTextBox.getText());
		else
			Main.errorMessage.updateText("Nothing for hours to reseat entered.");
	}

	public void makeIsTypingHoursReseatTextBoxFalse() {
		hoursReseatTextBox.makeIsTypingFalse();
	}

	public void addCharacterHoursReseatTextBox(char key) {
		hoursReseatTextBox.addCharacter(key);
	}

	public boolean getIsTypingMinutesReseatTextBox() {
		return minutesReseatTextBox.getIsTyping();
	}

	public void subtractCharacterMinutesReseatTextBox() {
		minutesReseatTextBox.subtractCharacter();
	}

	public void finalizeMinutesReseat() {
		if(minutesReseatTextBox.getText().length() > 0)
			minutesReseat = Integer.parseInt(minutesReseatTextBox.getText());
		else
			Main.errorMessage.updateText("Nothing for minutes to reseat entered.");
	}

	public void makeIsTypingMinutesReseatTextBoxFalse() {
		minutesReseatTextBox.makeIsTypingFalse();
	}

	public void addCharacterMinutesReseatTextBox(char key) {
		minutesReseatTextBox.addCharacter(key);
	}
	
	public boolean getIsTypingSeatsTextBox() {
		return seatsTextBox.getIsTyping();
	}

	public void subtractCharacterSeatsTextBox() {
		seatsTextBox.subtractCharacter();
	}

	public void finalizeSeats() {
		if(seatsTextBox.getText().length() > 0)
			seats = Integer.parseInt(seatsTextBox.getText());
		else
			Main.errorMessage.updateText("Nothing for seats entered.");
	}

	public void makeIsTypingSeatsTextBoxFalse() {
		seatsTextBox.makeIsTypingFalse();
	}

	public void addCharacterSeatsTextBox(char key) {
		seatsTextBox.addCharacter(key);
	}
	public boolean getIsResizing() {
		return isResizing;
	}
	public boolean inResizeButton(float mouseX, float mouseY) {
		if(mouseX > resizeButtonX && mouseX < resizeButtonX + resizeButtonSize && mouseY > resizeButtonY && mouseY < resizeButtonY + resizeButtonSize) {
			isResizing = true;
			return true;
		}
		else
			return false;
	}
	public boolean getIsMoving() {
		return isMoving;
	}
	public int getGreyTableNumber() {
		if(tableNumberTextBox.getText().length() > 0)
			return Integer.parseInt(tableNumberTextBox.getText());
		else {
			Main.errorMessage.updateText("Nothing for table number entered.");
			return -1;
		}
	}
	public boolean isNothingEnteredTableNumberTextBox() {
		if(tableNumberTextBox.getText().length() > 0)
			return false;
		else
			return true;
				
	}
	public int getSeats() {
		return seats;
	}
	public int getMinutesReseat() {
		return minutesReseat;
	}
	public int getHoursReseat() {
		return hoursReseat;
	}
	public ArrayList<Integer> getTimesReservations() {
		return timesReservations;
	}
	public void addReservationTime(int time) {
		timesReservations.add(time);
		
	}
	

}
