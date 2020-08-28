package basic;

public class MouseState {
	
	private boolean down;
	private boolean clicked;
	private boolean released;
	
	public MouseState() {
		down     = false;
		clicked  = false;
		released = false;
	}
	
	public void reset() {
		down = false;
		clicked = false;
		released = false;
	}
	
	public void setMouseState(boolean mouseClicked, boolean mouseDown) {
		clicked = mouseClicked;
		
		if(clicked) {
			down = true;
			released = false;
			return;
		}
		
		if(!clicked && !mouseDown) {
			down = false;
			released = true;
			return;
		}
		
		if(!clicked && mouseDown) {
			down = true;
			released = false;
			return;
		}
		
	}
	
	
	public boolean isDown() {
		return down;
	}
	public boolean isClicked() {
		return clicked;
	}
	public boolean isReleased() {
		return released;
	}
	
}
