package game4;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.RoundedRectangle;

import basic.GameColor;
import basic.GameGlobals;
import basic.GraphicObject;
import basic.InputState;
import basic.SoundEffect;

/**
 * button in a game
 * @author tommy
 *
 */

public class ColorCircle extends GraphicObject  {
	
	private int colors;
	
	private boolean mouseEntered;
	
	private int colorID;
	private int radius;
	private boolean active;
	
	/**
	 * constructor for color circle
	 * @param x x-position
	 * @param y y-position
	 * @param radius radius of circle
	 * @param colors number of colors
	 */
	public ColorCircle(int x, int y, int radius, GameGlobals gameGlobals) {
		super(x,y);
		
		this.colors = gameGlobals.getColors();
		
		colorID = 100;
		this.radius = radius;
		collisionMask = new RoundedRectangle(x, y, 2*radius, 2*radius, radius);
		active = false;
	}
	

	@Override
	public void draw(Graphics g) {
		
		if(active && mouseEntered) {
			/* mouse is entered */
			g.setColor(GameColor.marked);
			g.fillOval(x-2, y-2, 2*radius+4, 2*radius+4);
		}
		
		g.setColor(GameColor.getColor(colorID));
		g.fillOval(x, y, 2*radius, 2*radius);
	}

	
	@Override
	public void update(InputState inputState, int delta) {
		
		int mouseX = inputState.getMouseX();
		int mouseY = inputState.getMouseY();
		
		mouseEntered = super.isCollision(mouseX, mouseY);
		boolean leftMouseClicked = inputState.mouseButtonLeft.isClicked();
		boolean rightMouseClicked = inputState.mouseButtonRight.isClicked();
		boolean mouseWheelUp = inputState.getMouseWheelUp();
		boolean mouseWheelDown = inputState.getMouseWheelDown();
		
		
		if(active && mouseEntered) {
			if(leftMouseClicked || mouseWheelDown) {
				colorID = (colorID + 1) % colors;
				SoundEffect.COLORCHANGE.play();
			}
			if(rightMouseClicked || mouseWheelUp) {
				if(colorID == 0) {
					colorID = colors-1;
				}
				else {
					colorID = colorID - 1;
				}
				SoundEffect.COLORCHANGE.play();
			}
		}
	}
	
	
	public int getColorID() {
		return colorID;
	}
	
	public void setColorID(int i) {
		colorID = i;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
}
