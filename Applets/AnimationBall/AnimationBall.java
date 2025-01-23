import java.applet.Applet;
import java.awt.Graphics;
import java.awt.*;
import java.lang.InterruptedException;

public class AnimationBall extends Applet implements Runnable{
	private int xPos = getWidth();
	private int yPos = getHeight();
	private boolean xFlag = false;
	private boolean yFlag = false;

	private int xPos1 = 200;
	private int yPos1 = 200;
	private boolean xFlag1 = false;
	private boolean yFlag1 = false;
	
	private final int distance = 10;
	
	public void init() {
		Thread th = new Thread(this);
		th.start();
	}
	public void paint(Graphics g) {
		g.setColor(Color.MAGENTA);
		g.fillOval(xPos, yPos, 50, 50);

		g.setColor(Color.YELLOW);
		g.fillOval(xPos1, yPos1, 50, 50);		
	}
	
	public void moveBallx1() {
		if (xFlag == false)
			xPos += distance;
		else
			xPos -= distance;		
	}
	
	public void moveBally1() {	
		if (yFlag == false)
			yPos += 2 *distance;
		else
			yPos -= distance;		
	}

	public void moveBallx2() {	
		if (xFlag1 == false)
			xPos1 -= distance;
		else
			xPos1 += distance;		
	}
	
	public void moveBally2() {			
		if (yFlag1 == false)
			yPos1 -= 3 *distance;
		else
			yPos1 += distance;		
	}	
	public void run() {
		while (true) {
			moveBallx1();
			moveBally1();
												
			if (xPos + 50 > getWidth() && xFlag == false) {
				xFlag = true;				
			}
			else if (xPos < 0 && xFlag == true) {
				xFlag = false;								
			}									
			if (yPos + 50 > getHeight() && yFlag == false) {
				yFlag = true;				
			}
			else if (yPos < 0 && yFlag == true) {
				yFlag = false;								
			}

			moveBallx2();
			moveBally2();		
									
			if (xPos1 + 50 > getWidth() && xFlag1 == true) {
				xFlag1 = false;				
			}
			else if (xPos1 < 0 && xFlag1 == false) {
				xFlag1 = true;								
			}									
			if (yPos1 + 50 > getHeight() && yFlag1 == true) {
				yFlag1 = false;				
			}
			else if (yPos1 < 0 && yFlag1 == false) {
				yFlag1 = true;								
			}	

			if (xPos < xPos1 + 50 && xPos + 50 > xPos1 && yPos < yPos1 + 50 && yPos + 50 > yPos1) {
				xFlag = !xFlag;
				yFlag = !yFlag;
				xFlag1 = !xFlag1;
				yFlag1 = !yFlag1;
			}	
			try {
				Thread.sleep(100);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}
}