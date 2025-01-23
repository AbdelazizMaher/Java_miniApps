import java.applet.Applet;
import java.awt.Graphics;
import java.awt.*;
import java.util.ArrayList;
import java.lang.InterruptedException;

public class AnimationBall extends Applet implements Runnable{
	private ArrayList<Ball> balls;

	public void init() {
		balls = new ArrayList<>();
		balls.add(new Ball(getWidth(), getHeight(), false, false));
		balls.add(new Ball(200, 200, false, false));
		
		Thread th = new Thread(this);
		th.start();
	}
	public void paint(Graphics g) {
		int i = 0;
		for(Ball ball : balls) {
			setColor(g, i++);
			if (i > 7) i = 0;
			
			if (ball.firstTimeDrawn())
				ball.setDrawn();
			
			g.fillOval(ball.xPos, ball.yPos, 50, 50);
		}	
	}
		
	public void run() {
		while (true) {			
			for (Ball ball : balls) {
				ball.decrementCheckRate();
				ball.resetCollision();
			}	
			
			for(Ball ball : balls) {
				if (!ball.firstTimeDrawn()) { 
					ball.moveX();
					ball.moveY();
					
					BallCollision.isXcolliding(ball, this);
					BallCollision.isYcolliding(ball, this);
				}
			}
			ArrayList<Ball> newBalls = new ArrayList<>();
			for (int i = 0; i < balls.size(); ++i) {
				for (int j = i + 1; j < balls.size(); ++j) {
					if (balls.get(i) == balls.get(j))
						continue;
					
					if (BallCollision.isColliding(balls.get(i), balls.get(j))) {
						newBalls.add(new Ball(balls.get(i).getXpos(), balls.get(i).getYpos(), false, false));	
					}
				}
			}
			
			balls.addAll(newBalls);
														
			try {
				Thread.sleep(100);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}
	private void setColor(Graphics g, int i) {
		switch(i) {
			case 0:
				g.setColor(Color.MAGENTA);
				break;
			case 1:
				g.setColor(Color.YELLOW);
				break;
			case 2:
				g.setColor(Color.DARK_GRAY);
				break;
			case 3:
				g.setColor(Color.BLACK);
				break;
			case 4:
				g.setColor(Color.RED);
				break;
			case 5:
				g.setColor(Color.ORANGE);
				break;
			case 6:
				g.setColor(Color.GREEN);
				break;
			case 7:
				g.setColor(Color.BLUE);
				break;				
		}
	}
}

class Ball {
	protected int xPos;
	protected int yPos;
	protected boolean xFlag;
	protected boolean yFlag;
	protected boolean hasCollided; 
	protected boolean firstTimeDrawn;
	protected int collisionCheckRate;
	protected final int distance = 10;
	
	public Ball(int xPos, int yPos, boolean xFlag, boolean yFlag) {
		this.xPos = xPos;
		this.yPos = yPos;
		
		this.xFlag = xFlag;
		this.yFlag = yFlag;
		
		this.hasCollided = false;
		this.firstTimeDrawn = false;
		this.collisionCheckRate = 0;
	}
	
	public void moveX() {
		if (xFlag == false)
			xPos += 2 * distance;
		else
			xPos -= distance;		
	}
	
	public void moveY() {	
		if (yFlag == false)
			yPos += 4 * distance;
		else
			yPos -= distance;		
	}
	
    public void resetCollision() {
        hasCollided = false;  
    }

    public void decrementCheckRate() {
        if (collisionCheckRate > 0) {
            collisionCheckRate--;
        }
    }	

    public boolean canCollide() {
        return collisionCheckRate == 0;
    }
	public boolean firstTimeDrawn() {
		return firstTimeDrawn == false;
	}
	public void setDrawn() {
		firstTimeDrawn = true;
	}
	
	public int getXpos() { return xPos; }
	public int getYpos() { return yPos; }
}

class BallCollision {
	public static void isXcolliding(Ball b, Applet applet) {
		if (b.xPos + 50 > applet.getWidth() && b.xFlag == false)
			b.xFlag = true;				
		else if (b.xPos < 0 && b.xFlag == true) 
			b.xFlag = false;								
	}
	
	public static void isYcolliding(Ball b, Applet applet) {
		if (b.yPos + 50 > applet.getHeight() && b.yFlag == false) 
			b.yFlag = true;				
		else if (b.yPos < 0 && b.yFlag == true) 
			b.yFlag = false;								
	}
	
	public static boolean isColliding(Ball b1, Ball b2) {
		if (b1.canCollide() && b2.canCollide()) {
			if (!b1.hasCollided && !b2.hasCollided) {
				if (b1.xPos < b2.xPos + 50 && b1.xPos + 50 > b2.xPos && b1.yPos < b2.yPos + 50 && b1.yPos + 50 > b2.yPos) {
					b1.xFlag = !b1.xFlag;
					b1.yFlag = !b1.yFlag;

					b2.xFlag = !b2.xFlag;
					b2.yFlag = !b2.yFlag;
					
					b1.collisionCheckRate = 5; 
					b2.collisionCheckRate = 5;
					b1.hasCollided = true;
					b2.hasCollided = true;	

					return true;	
				}
			}
		}
		return false;	
	}
}