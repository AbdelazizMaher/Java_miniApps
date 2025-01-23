import java.applet.Applet;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

public class DragBall extends Applet implements Runnable{
	private Thread th;
	
	private boolean firstTime = true;
	private boolean play = true;
	private int xPos;
	private int yPos;
	private boolean xFlag = false;
	private boolean yFlag = false;
	
	private final int distance = 10;
	
	public void init() {
		xPos = getWidth() / 2;
		yPos = getHeight() / 2;
		
		th = new Thread(this);
		
		
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				xPos = e.getX();
				yPos = e.getY();
				repaint();
			}
			
			public void mouseMoved(MouseEvent e) {}
		});	
	}
	public void paint(Graphics g) {
		g.setColor(Color.MAGENTA);
		g.fillOval(xPos, yPos, 50, 50);		
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
	
			try {
				Thread.sleep(100);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}
}