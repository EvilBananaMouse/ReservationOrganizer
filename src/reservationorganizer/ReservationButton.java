package reservationorganizer;
import processing.core.PApplet;

// this class describes the button that lets you create reservations
public class ReservationButton{

	private float topLeftX;
	private float topLeftY;
	private float width;
	private float height;
	private boolean isSelected;
	private boolean isHovering;
	private String title;
	
	private float menuX, menuY;
	private float menuHeight, menuWidth;
	private float downScaler;
	
	// TB = TextBox
	private TextBox guestsTB;
	private float guestsTBX, guestsTBY;
	private float guestsTBLength, guestsTBWidth;
	private int guestsTBMaxChars;
	private int guests;
	
	private TextBox hourTB;
	private float hourTBX, hourTBY;
	private float hourTBLength, hourTBWidth;
	private int hourTBMaxChars;
	private int hour;
	
	private TextBox minuteTB;
	private float minuteTBX, minuteTBY;
	private float minuteTBLength, minuteTBWidth;
	private int minuteTBMaxChars;
	private int minute;
	
	private TextBox nameTB;
	private float nameTBX, nameTBY;
	private float nameTBLength, nameTBWidth;
	private int nameTBMaxChars;
	private String name;
	
	private TextBox phoneNumberTB;
	private float phoneNumberTBX, phoneNumberTBY;
	private float phoneNumberTBLength, phoneNumberTBWidth;
	private int phoneNumberTBMaxChars;
	private String phoneNumber;
	
	private TextBox notesTB;
	private float notesTBX, notesTBY;
	private float notesTBLength, notesTBWidth;
	private int notesTBMaxChars;
	private String notes;
	
	private String finalizeText;
	private float finalizeX, finalizeY;
	private float finalizeLength, finalizeWidth;
	private boolean finalizeIsSelected;
	
	
	
	@SuppressWarnings("static-access")
	public ReservationButton (float topLeftY, float leftSideHolderX, float wholeWindowHeight, float wholeWindowWidth) {
		topLeftX = 0;
		this.topLeftY = topLeftY;
		width = leftSideHolderX;
		height = 100;
		title = "Make Reservation";
		isSelected = false;
		
		downScaler = (float) 0.2;
		menuX = wholeWindowWidth * downScaler;
		menuY = wholeWindowHeight * downScaler;
		menuWidth = wholeWindowWidth - 2 * menuX;
		menuHeight = wholeWindowHeight - 2 * menuY;
		
		guestsTBX = menuX + menuWidth * (float) 0.1;
		guestsTBY = menuY + menuHeight * (float) 0.1;
		guestsTBLength = 56;
		guestsTBWidth = 30;
		guestsTBMaxChars = 4;
		guestsTB = new TextBox("", 22, guestsTBX, guestsTBY, guestsTBLength, guestsTBWidth, guestsTBMaxChars, false, true);
		
		hourTBX = guestsTBX;
		hourTBY = guestsTBY + guestsTBWidth + 3;
		hourTBLength = 25;
		hourTBWidth = 30;
		hourTBMaxChars = 2;
		hourTB = new TextBox("", 22, hourTBX, hourTBY, hourTBLength, hourTBWidth, hourTBMaxChars, false, true);
		
		minuteTBX = hourTBX + hourTBLength + 6;
		minuteTBY = hourTBY;
		minuteTBLength = 25;
		minuteTBWidth = 30;
		minuteTBMaxChars = 2;
		minuteTB = new TextBox("", 22, minuteTBX, minuteTBY, minuteTBLength, minuteTBWidth, minuteTBMaxChars, false, true);
		
		nameTBX = guestsTBX;
		nameTBY = hourTBY + hourTBWidth + 3;
		nameTBLength = 310;
		nameTBWidth = 30;
		nameTBMaxChars = 30;
		nameTB = new TextBox("", 22, nameTBX, nameTBY, nameTBLength, nameTBWidth, nameTBMaxChars, false, true);
		
		phoneNumberTBX = guestsTBX;
		phoneNumberTBY = nameTBY + nameTBWidth + 3;
		phoneNumberTBLength = 310;
		phoneNumberTBWidth = 30;
		phoneNumberTBMaxChars = 12;
		phoneNumberTB = new TextBox("", 22, phoneNumberTBX, phoneNumberTBY, phoneNumberTBLength, phoneNumberTBWidth, phoneNumberTBMaxChars, false, true);

		notesTBX = guestsTBX;
		notesTBY = phoneNumberTBY + (phoneNumberTBWidth + 3) * 3;
		notesTBLength = 310;
		notesTBWidth = 150;
		notesTBMaxChars = 150;
		notesTB = new TextBox("", 22, notesTBX, notesTBY, notesTBLength, notesTBWidth, notesTBMaxChars, false, true);
		
		finalizeText = "Finalize";
		finalizeX = notesTBX + notesTBLength + 9;
		finalizeY = notesTBY;
		finalizeLength = notesTBLength;
		finalizeWidth = notesTBWidth;
		finalizeIsSelected = false;
		
	}
	
	public void setSelect(float mouseX, float mouseY) {
		if( (mouseX >= topLeftX && mouseX <= topLeftX + width) && (mouseY >= topLeftY && mouseY <= topLeftY + height))
			isSelected = !isSelected;
	}
	
	public void draw (PApplet p) {
		if(isSelected)
			p.stroke(99,255,32);
		else
			p.stroke(0,0,0);
		p.fill(255,255,255);
		p.rect(topLeftX, topLeftY, topLeftX + width, topLeftY + height);
		if(isSelected)
			p.fill(99,255,32);
		else
			p.fill(0);
		p.textSize(18);
		p.text(title, width/2 + topLeftX, height/(float) 1.8 + topLeftY);
	}
	
	public void drawMenu (PApplet p) {
		if(isSelected) {
			p.fill(245,245,245);
			p.rect(menuX, menuY, menuX + menuWidth, menuY + menuHeight);
			
			guestsTB.draw(p);
			hourTB.draw(p);
			minuteTB.draw(p);
			nameTB.draw(p);
			phoneNumberTB.draw(p);
			notesTB.draw(p);
			
			p.textSize(22);
			p.text("Amount of Guests", guestsTBX + guestsTBLength + 3, guestsTBY, guestsTBX + menuWidth, guestsTBY + guestsTBWidth);
			p.text("Hour and Minute", minuteTBX + minuteTBLength + 3, minuteTBY, minuteTBX + menuWidth, minuteTBY + minuteTBWidth);
			p.text("First and Last Name", nameTBX + nameTBLength + 3, nameTBY, nameTBX + menuWidth, nameTBY + nameTBWidth);
			p.text("Phone Number", phoneNumberTBX + phoneNumberTBLength + 3, phoneNumberTBY, phoneNumberTBX + menuWidth, phoneNumberTBY + phoneNumberTBWidth);
			p.text("Notes..." , notesTBX, notesTBY - guestsTBWidth - 3, notesTBX + menuWidth, notesTBY);
		}
	}
	
	public void drawFinalizeButton(PApplet p) {
		if(isSelected) {
			p.fill(255,255,255);
			if(finalizeIsSelected)
				p.stroke(99,255,32);
			else
				p.stroke(0,0,0);
			p.rect(finalizeX, finalizeY, finalizeX + finalizeLength, finalizeY + finalizeWidth);
			p.fill(0);
			p.textSize(40);
			p.text(finalizeText, finalizeX, finalizeY + finalizeWidth/4, finalizeX + finalizeLength,finalizeY + finalizeWidth);
		}
	}
	

	public boolean getIsSelected() {
		return isSelected;
	}
	
	public boolean getIsHovering() {
		return isHovering;
	}

	public void makeIsSelectedFalse() {
		isSelected = false;
	}

	public void setInTextBoxes(float mouseX, float mouseY) {
		guestsTB.setInTextBox(mouseX, mouseY);
		hourTB.setInTextBox(mouseX, mouseY);
		minuteTB.setInTextBox(mouseX, mouseY);
		nameTB.setInTextBox(mouseX, mouseY);
		phoneNumberTB.setInTextBox(mouseX, mouseY);
		notesTB.setInTextBox(mouseX, mouseY);
		
	}

	public void subtractCharacterForSelectedTextBox() {
		if(guestsTB.getIsTyping())
			guestsTB.subtractCharacter();
		else if(hourTB.getIsTyping())
			hourTB.subtractCharacter();
		else if(minuteTB.getIsTyping())
			minuteTB.subtractCharacter();
		else if(nameTB.getIsTyping())
			nameTB.subtractCharacter();
		else if(phoneNumberTB.getIsTyping())
			phoneNumberTB.subtractCharacter();
		else if(notesTB.getIsTyping())
			notesTB.subtractCharacter();
	}

	public void finalizeForSelectedTextBox() {
		if(guestsTB.getIsTyping()) {
			if(guestsTB.getText().length() > 0)
				guests = Integer.parseInt(guestsTB.getText());
			else
				Main.errorMessage.updateText("Nothing for guests entered.");
		}
		else if(hourTB.getIsTyping()) {
			if(hourTB.getText().length() > 0)
				hour = Integer.parseInt(hourTB.getText());
			else
				Main.errorMessage.updateText("Nothing for hour entered.");
		}
		else if(minuteTB.getIsTyping()) {
			if(minuteTB.getText().length() > 0)
				minute = Integer.parseInt(minuteTB.getText());
			else
				Main.errorMessage.updateText("Nothing for minute entered.");
			
			if(minute > 45) {
				minute = 0;
				hour++;
				hourTB.updateText(hour);
			}
			else if(minute > 30) 
				minute = 45;
			else if(minute > 15)
				minute = 30;
			else if(minute > 0)
				minute = 15;
			else
				minute = 0;
			minuteTB.updateText(minute);
		}
		else if(nameTB.getIsTyping()) {
			if(nameTB.getText().length() > 0)
				name = nameTB.getText();
			else
				Main.errorMessage.updateText("Nothing for name entered.");
		}
		else if(phoneNumberTB.getIsTyping()) {
			if(phoneNumberTB.getText().length() > 0)
				phoneNumber = phoneNumberTB.getText();
			else
				Main.errorMessage.updateText("Nothing for phone number entered.");
		}
		else if(notesTB.getIsTyping()) {
			notes = notesTB.getText();
		}
			
	}

	public void addCharacterForSelectedTextBox(char key) {
		if(guestsTB.getIsTyping())
			guestsTB.addCharacter(key);
		else if(hourTB.getIsTyping())
			hourTB.addCharacter(key);
		else if(minuteTB.getIsTyping())
			minuteTB.addCharacter(key);
		else if(nameTB.getIsTyping())
			nameTB.addCharacter(key);
		else if(phoneNumberTB.getIsTyping())
			phoneNumberTB.addCharacter(key);
		else if(notesTB.getIsTyping())
			notesTB.addCharacter(key);
	}

	public void setSelectFinalize(float mouseX, float mouseY) {
		if(mouseX >= finalizeX && mouseX <= finalizeX + finalizeLength && mouseY >= finalizeY && mouseY <= finalizeY + finalizeWidth)
			finalizeIsSelected = !finalizeIsSelected;
	}

	public boolean getFinalizeIsSelected() {
		return finalizeIsSelected;
	}

	public boolean isFinalizePossible() {
		boolean isPossible = true;
		if(guestsTB.getText().length() <= 0) {
			Main.errorMessage.updateText("Nothing for guests entered.");
			isPossible = false;
		}
		else if(hourTB.getText().length() <= 0) {
			Main.errorMessage.updateText("Nothing for hour entered.");
			isPossible = false;
		}
		else if(minuteTB.getText().length() <= 0) {
			Main.errorMessage.updateText("Nothing for minute entered.");
			isPossible = false;
		}
		else if(nameTB.getText().length() <= 0) {
			Main.errorMessage.updateText("Nothing for name entered.");
			isPossible = false;
		}
		else if(phoneNumberTB.getText().length() <= 0) {
			Main.errorMessage.updateText("Nothing for phone number entered.");
			isPossible = false;
		}
		if(!isPossible)
			finalizeIsSelected = false;
		return isPossible;
	}
	
	// only called if finalization is possible, so need to check for errors
	public void finalizeReservation() {
		guests = Integer.parseInt(guestsTB.getText());
		hour = Integer.parseInt(hourTB.getText());
		minute = Integer.parseInt(minuteTB.getText());
		if(minute > 45) {
			minute = 0;
			hour++;
			hourTB.updateText(hour);
		}
		else if(minute > 30) 
			minute = 45;
		else if(minute > 15)
			minute = 30;
		else if(minute > 0)
			minute = 15;
		else
			minute = 0;
		minuteTB.updateText(minute);
		name = nameTB.getText();
		phoneNumber = phoneNumberTB.getText();
		notes = notesTB.getText();
		
		finalizeIsSelected = false;
	}
	
	public void closeMenu() {
		finalizeIsSelected = false;
		isSelected = false;
	}

	public int getGuests() {
		return guests;
	}
	
	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getNotes() {
		return notes;
	}

	public float getBLY() {
		return topLeftY + height;
	}

	public boolean isInside(float mouseX, float mouseY) {
		if( (mouseX >= topLeftX && mouseX <= topLeftX + width) && (mouseY >= topLeftY && mouseY <= topLeftY + height))
			return true;
		else
			return false;
	}

	
	
}
