package menu;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

import basic.GraphicObject;
import basic.InputState;

public class Label extends GraphicObject {
	
	public static final int LEFT   = 0;
	public static final int CENTER = 1;
	public static final int RIGHT  = 2;
	
	private Font font;
	private String text;
	private int alignment;
	private int drawX;
	private int drawY;
	
	
	public Label(int x, int y, String text, Font font, int alignment) {
		super(x,y);
		this.text = text;
		this.font = font;
		this.alignment = alignment;
		setDrawingPoint(alignment);
	}
	
	private void setDrawingPoint(int alignment) {
		switch(alignment) {
		case LEFT:
			drawX = x;
			drawY = y;
			break;
		case CENTER:
			drawX = (int) (x - font.getWidth(text) / 2);
			drawY = y;
			break;
		case RIGHT:
			drawX = x - font.getWidth(text);
			drawY = y;
			break;
		default:
			drawX = x;
			drawY = y;
		}
	}

	
	@Override
	public void setX(int x) {
		super.setX(x);
		setDrawingPoint(alignment);
	}
	
	@Override
	public void setY(int y) {
		super.setY(y);
		setDrawingPoint(alignment);
	}

	
	@Override
	public void draw(Graphics g) {
		g.setFont(font);
		g.drawString(text, drawX, drawY);
	}
	

	@Override
	public void update(InputState inputState, int delta) {}

	
	public void setText(String text) {
		this.text = text;
		setDrawingPoint(alignment);
	}
}
