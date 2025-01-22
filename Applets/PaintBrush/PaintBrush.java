import java.applet.Applet;
import java.awt.*;
import java.awt.Button;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;

public class PaintBrush extends Applet {	
	private ArrayList<Shape> shapes;
	
	private Color colorState = Color.BLACK;	
	private int shapeState = LINE;
	private int strokeState = NORMAL;

	public static final int DOTTED = 0;
	public static final int SOLID = 1;
	public static final int NORMAL = 2;
	
	public static final int LINE = 0;
	public static final int RECT = 1;
	public static final int OVAL = 2;
	public static final int ERASER_RECT = 3;
	public static final int SMALL_CIRCLE = 4;	
	
	
	private Button lineButton;
	private Button rectangleButton;
	private Button ovalButton;
	private Button pencilButton;
	private Button eraserButton;	
	private Button redButton;
	private Button greenButton;
	private Button blueButton;
	private Button clearButton;
	private Button undoButton;
	
	private CheckboxGroup boxGroup;
	private Checkbox solidBox;
	private Checkbox dottedBox;
	
	private Shape tempShape;
	private boolean mousePressed = false;
	private boolean mouseDragged = false;
	private boolean undoPressed = false;
 	int i = 0;
	public void init() {
		clearButton = new Button("Clear");
		undoButton = new Button("Undo");

		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shapes.clear();
				i = 0;
				repaint();
			}
		});

		undoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (i > 0) {
					undoPressed = true;
					shapes.remove(i - 1);
					i--;
					repaint();
				}
			}
		});
		
		add(new Label("Functions:"));
		add(clearButton);	
		add(undoButton);
		
		lineButton = new Button("Line");
		rectangleButton = new Button("Rectangle");
		ovalButton = new Button("Oval");
		pencilButton = new Button("Pencil");
		eraserButton = new Button("Eraser");		

		lineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shapeState = LINE;
			}
		});

		rectangleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shapeState = RECT;
			}
		});
		

		ovalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shapeState = OVAL;
			}
		});

		pencilButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shapeState = SMALL_CIRCLE;
			}
		});
		

		eraserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shapeState = ERASER_RECT;
			}
		});
		
		add(new Label("   Paint Mode :"));
		add(lineButton);	
		add(rectangleButton);
		add(ovalButton);
		add(pencilButton);
		add(eraserButton);
		
		boxGroup = new CheckboxGroup();
		solidBox = new Checkbox("Solid", boxGroup, false);
		dottedBox = new Checkbox("Dotted", boxGroup, false);
		
		solidBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (solidBox.getState() == true)
					strokeState = SOLID;
				else
					strokeState = NORMAL;				
			}
		});

		dottedBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (dottedBox.getState() == true)
					strokeState = DOTTED;
				else
					strokeState = NORMAL;
			}
		});
		
		add(solidBox);
		add(dottedBox);

		redButton = new Button("RED");
		greenButton = new Button("Green");		
		blueButton = new Button("Blue");
		
		redButton.setBackground(Color.RED);
		greenButton.setBackground(Color.GREEN);
		blueButton.setBackground(Color.BLUE);
		
		redButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorState = Color.RED;
			}
		});

		greenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorState = Color.GREEN;
			}
		});	

		blueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorState = Color.BLUE;
			}
		});
		
		add(new Label("  Colors :"));
		add(redButton);	
		add(greenButton);
		add(blueButton);
		
		shapes = new ArrayList<>();
		addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) {
				mousePressed = true;
				if (shapeState != ERASER_RECT && shapeState != SMALL_CIRCLE) {					
					mouseDragged = true;
				}
				
				if (shapeState == ERASER_RECT || shapeState == SMALL_CIRCLE) {
					shapes.add(ShapeFactory.create(shapeState));
					shapes.get(i).setStartX(e.getX());
					shapes.get(i).setStartY(e.getY());
					repaint();					
				}

				tempShape = ShapeFactory.create(shapeState);
				tempShape.setStartX(e.getX());
				tempShape.setStartY(e.getY());
			}
			
			public void mouseReleased(MouseEvent e) {
				if (mouseDragged == false) {
					i++;
					mousePressed = false;					
				}							
			}
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				if (mouseDragged == true){
					mouseDragged = false;
					shapes.add(ShapeFactory.create(shapeState));
				}
				
				if (shapeState == ERASER_RECT || shapeState == SMALL_CIRCLE) {
					shapes.add(ShapeFactory.create(shapeState));
					i++;
					shapes.get(i).setStartX(e.getX());
					shapes.get(i).setStartY(e.getY());
				}
				else {
					shapes.get(i).setStartX(tempShape.getStartX());
					shapes.get(i).setStartY(tempShape.getStartY());	
				}			
				shapes.get(i).setXpos(e.getX());
				shapes.get(i).setYpos(e.getY());
				shapes.get(i).setColor(colorState);
				shapes.get(i).setState(strokeState);
				repaint();
			}
			
			public void mouseMoved(MouseEvent e) {}
		});	
	}
	
	public void paint(Graphics g) {
		if ((mousePressed == true && mouseDragged == false) || undoPressed == true) {
			int k = i;
			if (undoPressed == true) {
				k = i - 1;
				undoPressed = false;
			}
							
			for (int j = 0; j <= k; j++) {
				shapes.get(j).draw(g);
			}			
		}		
	}
}

abstract class Shape {
	protected Color color;
	protected int state;
	protected int startX;
	protected int startY;
	protected int xPos;
	protected int yPos;

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }

    public int getState() { return state; }
    public void setState(int state) { this.state = state; }

    public int getStartX() { return startX; }
    public void setStartX(int startX) { this.startX = startX; }

    public int getStartY() { return startY; }
    public void setStartY(int startY) { this.startY = startY; }

    public int getXpos() { return xPos; }
    public void setXpos(int xPos) { this.xPos = xPos; }

    public int getYpos() { return yPos; }
    public void setYpos(int yPos) { this.yPos = yPos; }
	
	
    public void setRealStart() {
        if (xPos < startX) {
            int tempX = startX;
            startX = xPos;
            xPos = tempX;
        }
        if (yPos < startY) {
            int tempY = startY;
            startY = yPos;
            yPos = tempY;
        }
    }		
	
	abstract public void draw(Graphics g);
}


class Line extends Shape {

	Line() {}
	Line(int startX, int startY, int xPos, int yPos) {
		this.startX = startX;
		this.startY = startY;
		
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public void draw(Graphics g) {
		g.setColor(this.color);
		if (state == PaintBrush.DOTTED) {
			Graphics2D g2d = (Graphics2D)g;
			float[] dashPattern = {10, 10}; 
			g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
			g2d.drawLine(startX, startY, xPos, yPos);
		}			
		else
			g.drawLine(startX, startY, xPos, yPos);
	}
}

class Rectangle extends Shape {
	private int width;
	private int height;

	Rectangle() {}
	Rectangle(int startX, int startY, int xPos, int yPos) {
		this.startX = startX;
		this.startY = startY;
		
		this.width = Math.abs(xPos - startX);
		this.height = Math.abs(yPos - startY);
	}
	
	private void setDimensions() {
		this.width = Math.abs(xPos - startX);
		this.height = Math.abs(yPos - startY);
	}
	
	public void draw(Graphics g) {
		setDimensions();
		setRealStart();
				
		g.setColor(this.color);
		if (state == PaintBrush.SOLID)
			g.fillRect(startX, startY, width, height);
		else if (state == PaintBrush.DOTTED) {
			Graphics2D g2d = (Graphics2D)g;
			float[] dashPattern = {10, 10}; 
			g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
			g2d.drawRect(startX, startY, width, height);
		}
		else
			g.drawRect(startX, startY, width, height);		
	}
}

class Oval extends Shape {
	private int width;
	private int height;
	
	Oval() {}
	Oval(int startX, int startY, int xPos, int yPos) {
		this.startX = startX;
		this.startY = startY;
		
		this.width = Math.abs(xPos - startX);
		this.height = Math.abs(yPos - startY);
	}
	
	public void draw(Graphics g) {
		setRealStart();
		
		g.setColor(this.color);
		if (state == PaintBrush.SOLID)
			g.fillOval(startX, startY, Math.abs(xPos - startX), Math.abs(yPos - startY));
		else if (state == PaintBrush.DOTTED) {
			Graphics2D g2d = (Graphics2D)g;
			float[] dashPattern = {10, 10}; 
			g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
			g2d.drawOval(startX, startY, Math.abs(xPos - startX), Math.abs(yPos - startY));
		}		
		else
			g.drawOval(startX, startY, Math.abs(xPos - startX), Math.abs(yPos - startY));						
	}
}

class EraserRectangle extends Rectangle {
	
	private final int WIDTH = 20; 
	private final int HEIGHT = 20; 	
	private final Color COLOR = Color.WHITE;
	
	public void draw(Graphics g) {
		g.setColor(COLOR);
		g.fillRect(startX, startY, WIDTH, HEIGHT);
	}
}

class SmallCircle extends Oval {
	private final int WIDTH = 10; 
	private final int HEIGHT = 10;
	
	public void draw(Graphics g) {
		g.setColor(this.color);
		if (state == PaintBrush.DOTTED) {
			Graphics2D g2d = (Graphics2D)g;
			float[] dashPattern = {10, 10}; 
			g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
			g2d.drawOval(startX, startY, WIDTH, HEIGHT);			
		}			
		else {
			g.fillOval(startX, startY, WIDTH, HEIGHT);
		}					
	}		
}

class ShapeFactory {

    public static Shape create(int shapeType) {
        switch (shapeType) {
            case PaintBrush.LINE:
                return new Line();
            case PaintBrush.RECT:
                return new Rectangle();
            case PaintBrush.OVAL:
                return new Oval();
            case PaintBrush.ERASER_RECT:
                return new EraserRectangle();
            case PaintBrush.SMALL_CIRCLE:
                return new SmallCircle();				
			default:
				return null;
				
        }
    }
}

