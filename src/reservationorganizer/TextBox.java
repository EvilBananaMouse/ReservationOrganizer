package reservationorganizer;
import processing.core.PApplet;

public class TextBox {
	
	private String text;
	private float posX;
	private float posY;
	private float length, width;
	private float fontSize;
	private int maxCharacters;
	
	private boolean isTyping;
	private boolean isRed;
	private boolean isRect;
	
	public TextBox(String s, float fontSize, float posX, float posY, float length, float width, int maxCharacters) {
		text = s;
		this.fontSize = fontSize;
		this.posX = posX;
		this.posY = posY;
		this.length = length;
		this.width = width;
		this.maxCharacters = maxCharacters;
		
		isTyping = false;
		isRed = false;
		isRect = true;
	}
	
	public TextBox(int n, float fontSize, float posX, float posY, float length, float width, int maxCharacters) {
		text = "" + n;
		this.fontSize = fontSize;
		this.posX = posX;
		this.posY = posY;
		this.length = length;
		this.width = width;
		this.maxCharacters = maxCharacters;
		
		isTyping = false;
		isRed = false;
		isRect = true;
	}
	
	public TextBox(String s, float fontSize, float posX, float posY, float length, float width, int maxCharacters, boolean isRed, boolean isRect) {
		text = s;
		this.fontSize = fontSize;
		this.posX = posX;
		this.posY = posY;
		this.length = length;
		this.width = width;
		this.maxCharacters = maxCharacters;
		
		isTyping = false;
		this.isRed = isRed;
		this.isRect = isRect;
	}
	
	public void draw(PApplet p) {
		if(isRect) {
			if(isTyping)
				p.stroke(99,255,32);
			else
				p.stroke(0);
			p.fill(255);
			p.rect(posX - 3, posY, posX + length, posY + width);
		}
		
		if(isRed)
			p.fill(255,0,0); // red
		else
			p.fill(0); // black
		p.textSize(fontSize);
		p.text(text, posX, posY, posX + length, posY + width);
	}
	
	public void addCharacter (char c) {
		if(text.length() < maxCharacters)
			text += "" + c;
	}
	
	public void subtractCharacter () {
		if(text.length() > 0)
			text = text.substring(0,text.length() - 1);
	}
	
	public void updatePosition(float posX, float posY) {
		this.posX = posX;
		this.posY = posY;
	}
	
	public void setInTextBox (float mouseX, float mouseY) {
		if (mouseX > posX && mouseX < posX + length && mouseY > posY && mouseY < posY + width) 
			isTyping = true;
		else
			isTyping = false;
		
	}

	public boolean getIsTyping() {
		return isTyping;
	}

	public String getText() {
		return text;
	}

	public void makeIsTypingFalse() {
		isTyping = false;
	}
	
	public void updateText(String s) {
		text = s;
	}

	public void updateText(int n) {
		text = "" + n;
	}
	
}
