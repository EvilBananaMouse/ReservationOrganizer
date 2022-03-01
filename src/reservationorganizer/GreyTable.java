package reservationorganizer;
import processing.core.PApplet;

public class GreyTable {
	private float x;
	private float y;
	private float fontSize;
	private float size;
	private float minimumSize;
	private String tableNumber;
	
	private float OGx; // original x coordiante (top-left)
	private float OGy; 
	
	private float BLx; // bottom left X coordinate
	private float BLy;
	private float TRx;
	private float TRy;
	
	private float OGBLx; // original bottom left x coordiante
	private float OGBLy;
	private float OGTRx;
	private float OGTRy;
	private float OGBRx, OGBRy;
	
	private boolean isResizing;
	
	public GreyTable (float x, float y, float fontSize, float size, int tableNumber, int iteration, boolean isResizing) {
		this.x = x;
		this.y = y;
		this.fontSize = fontSize;
		this.size = size;
		this.tableNumber = "" + tableNumber;
		
		OGx = x;
		OGy = y;
		
		BLx = x;
		BLy = y + size;
		TRx = x + size;
		TRy = y;
		
		OGBLx = x;
		OGBLy = y + size;
		OGTRx = x + size;
		OGTRy = y;
		OGBRx = x + size;
		OGBRy = y + size;
		
		this.isResizing = isResizing;
		minimumSize = 30;
	}
	
	public void draw(PApplet p, int indexLatestGreyTable) {
		if(!isResizing) {
			p.stroke(0,0,0);
			p.line(BLx, BLy, OGBLx, OGBLy);
			p.line(TRx, TRy, OGTRx, OGTRy);
		}
			
		p.fill(255,255,255);
		p.stroke(159,160,164);
		if(isResizing) {
			if(x - OGBRx < y - OGBRy)
				p.rect(x, OGBRy - size, OGBRx, OGBRy);
			else
				p.rect(OGBRx - size, y, OGBRx, OGBRy);
		}
		else
			p.rect(x, y, size + x, size + y);
			
		p.fill(159,160,164);
		p.textSize(fontSize);
		if(isResizing) {
			if(x - OGBRx < y - OGBRy)
				p.text(tableNumber, size/2 + x, size/2 + OGBRy - size);
			else
				p.text(tableNumber, size/2 + OGBRx - size, size/2 + y);
		}
		else
			p.text(tableNumber, size/2 + x, size/2 + y);
	}
	
	public void updatePosition(float mouseX, float mouseY) {
		x = mouseX;
		y = mouseY; 
		
		BLx = x;
		BLy = y + size;
		TRx = x + size;
		TRy = y;
	}
	
	public float getX () {
		return x;
	}
	
	public float getY () {
		return y;
	}

	public void resize(float mouseX, float mouseY) {
		if(mouseX - OGBRx > mouseY - OGBRy)
			size = OGBRy - mouseY;
		else
			size = OGBRx - mouseX;
		if(size < minimumSize) {
			size = minimumSize;
			x = OGBRx - minimumSize;
			y = OGBRy - minimumSize;
		}
		else {
			x = mouseX;
			y = mouseY;
		}
		fontSize = size * (float) 0.28;
		
	}

	public float getSize() {
		return size;
	}

	public float getResizingX() {
		if(x - OGBRx < y - OGBRy)
			return x;
		else
			return OGBRx - size;
	}

	public float getResizingY() {
		if(x - OGBRx < y - OGBRy)
			return OGBRy - size;
		else
			return y;
	}

}
