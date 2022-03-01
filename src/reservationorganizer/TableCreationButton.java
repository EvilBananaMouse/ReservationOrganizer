package reservationorganizer;
import processing.core.PApplet;

// this class describes the tableCreationButton
public class TableCreationButton {

	private static float topLeftX;
	private static float topLeftY;
	private static float width;
	private static float height;
	private static boolean isSelected;
	private static boolean isHovering;
	private static String title;
	
	@SuppressWarnings("static-access")
	public TableCreationButton (float topLeftY, float leftSideHolderX) {
		topLeftX = 0;
		this.topLeftY = topLeftY;
		width = leftSideHolderX;
		height = 100;
		title = "TableEdit Mode";
		isSelected = false;
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
	

	public boolean getIsSelected() {
		return isSelected;
	}
	
	public boolean getIsHovering() {
		return isHovering;
	}

	public float getBLY() {
		return topLeftY + height;
	}

	public void makeIsSelectedFalse() {
		isSelected = false;
	}

	public boolean isInside(float mouseX, float mouseY) {
		if( (mouseX >= topLeftX && mouseX <= topLeftX + width) && (mouseY >= topLeftY && mouseY <= topLeftY + height))
			return true;
		else
			return false;
	}
}
