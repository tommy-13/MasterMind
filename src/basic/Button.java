package basic;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

/**
 * button in a game
 * @author tommy
 *
 */

public class Button extends GraphicObject {
	
	private Image imgNormal;
	private Image imgPressed;
	private Image imgEntered;
	private Image imgActive;
	
	private boolean pressed = false;
	private boolean released = false;
	
	
	public Button(int x, int y, Image imgNormal, Image imgPressed, Image imgEntered) {
		super(x,y);
		this.imgNormal  = imgNormal;
		this.imgPressed = imgPressed;
		this.imgEntered = imgEntered;
		
		imgActive = imgNormal;
		
		collisionMask = new Rectangle(x, y, imgActive.getWidth(), imgActive.getHeight());
	}
	
	public Button(int x, int y, SpriteSheet imgButtons) {
		this(x,y, imgButtons.getSprite(0, 0), imgButtons.getSprite(1, 0),
				imgButtons.getSprite(2, 0));
	}
	

	@Override
	public void draw(Graphics g) {
		imgActive.draw(x,y);
	}

	@Override
	public void update(InputState inputState, int delta) {
		
		int mouseX = inputState.getMouseX();
		int mouseY = inputState.getMouseY();
		
		boolean mouseEntered = super.isCollision(mouseX, mouseY);
		boolean mouseClicked = inputState.mouseButtonLeft.isClicked();
		boolean mouseReleased = inputState.mouseButtonLeft.isReleased();
		
		
		if(mouseEntered) {
			if(mouseClicked) {
				pressed = true;
				imgActive = imgPressed;
			}
			else if(pressed) {
				if(mouseReleased) {
					pressed = false;
					released = true;
					imgActive = imgEntered;
				}
				else {
					imgActive = imgPressed;
				}
			}
			else {
				imgActive = imgEntered;
			}
		}
		else {
			if(mouseReleased) {
				pressed = false;
			}
			imgActive = imgNormal;
		}
	}
	
	
	/**
	 * check if the mouse button was previously released
	 * @return true / false
	 */
	public boolean isReleased() {
		return released;
	}
	
	
	/**
	 * to be executed after button was released
	 */
	public void resetReleased() {
		released = false;
	}

}
