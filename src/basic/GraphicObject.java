package basic;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;


/**
 * models an object to set on the screen
 * @author tommy
 *
 */

public abstract class GraphicObject {
	
	protected int x;
	protected int y;
	protected Shape collisionMask;
	
	
	/**
	 * draw the image of this component on the screen
	 * @param g Graphics to draw onto
	 */
	public abstract void draw(Graphics g);
	
	/**
	 * update this component
	 * @param input input from the user
	 * @param delta time step
	 */
	public abstract void update(InputState inputState, int delta);
	
	/**
	 * set the position of this component
	 * @param x
	 * @param y
	 */
	public GraphicObject(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * set the position of this component to default values (0,0)
	 */
	public GraphicObject() {
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * get the x position of this component
	 * @return x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * set the x position of this component
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
		if(collisionMask != null) {
			collisionMask.setX(x);
		}
	}
	
	/**
	 * get the y position of this component
	 * @return y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * set the y position of this component
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
		if(collisionMask != null) {
			collisionMask.setY(y);
		}
	}
	
	/**
	 * get the collision shape of this component
	 * @return collisionShape
	 */
	public Shape getCollisionShape() {
		return collisionMask;
	}
	
	/**
	 * check for a collision with another collision shape
	 * @param graphicObject
	 * @return
	 */
	public boolean isCollision(GraphicObject graphicObject) {
		return collisionMask.contains(graphicObject.getCollisionShape());
	}
	
	/**
	 * check for a collision with point (x,y)
	 * @param x
	 * @param y
	 * @return true, if collision
	 */
	public boolean isCollision(int x, int y) {
		return collisionMask.contains(x,y);
	}
}