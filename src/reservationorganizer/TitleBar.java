package reservationorganizer;
import processing.core.PApplet;

// this class describes the title bar on top of the program
// it also describes the 'left side holder,' which sets the length of the left-hand side buttons
public class TitleBar {
	
	private static String title;
	private static float height;
	private static float width;
	private static float leftSideHolderX;
	private static float leftSideHolderLength;
	
	public TitleBar(float height, float width, float leftSideHolderX, float leftSideHolderLength) {
		this.height = height;
		this.width = width;
		this.leftSideHolderX = leftSideHolderX;
		this.leftSideHolderLength = leftSideHolderLength;
		title = new String("ReserveMe                 a project by Julian Henry");
	}
	
	public void draw (PApplet p) {
		p.stroke(0);
		p.line(leftSideHolderX, height, leftSideHolderX, leftSideHolderLength);
		p.line(leftSideHolderX+1, height, leftSideHolderX+1, leftSideHolderLength);
		p.line(0, 1, width, 1);
		p.line(0, height, width, height);
		p.line(0, height-1, width, height-1);
		
		p.fill(0);
		p.textSize(20);
		p.text(title, width/2, height/ (float) 1.6);
	}

}
