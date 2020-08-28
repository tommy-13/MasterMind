package basic;

public class InputState {

	public MouseState mouseButtonLeft;
	public MouseState mouseButtonRight;
	
	private int mousePositionX;
	private int mousePositionY;
	
	private boolean mouseWheelDown;
	private boolean mouseWheelUp;
	
	
	public InputState() {
		mouseButtonLeft = new MouseState();
		mouseButtonRight = new MouseState();
		mousePositionX = -1;
		mousePositionY = -1;
	}
	
	public void reset() {
		mousePositionX = -1;
		mousePositionY = -1;
		mouseButtonLeft.reset();
		mouseButtonRight.reset();
	}
	
	
	public void setMousePosition(int x, int y) {
		mousePositionX = x;
		mousePositionY = y;
	}
	
	public int getMouseX() {
		return mousePositionX;
	}
	
	public int getMouseY() {
		return mousePositionY;
	}

	public void setMouseWheel(int dWheel) {
		mouseWheelDown = false;
		mouseWheelUp = false;
		if(dWheel < 0) {
			mouseWheelDown = true;
		}
		if(dWheel > 0) {
			mouseWheelUp = true;
		}
	}
	
	public boolean getMouseWheelDown() {
		return mouseWheelDown;
	}
	public boolean getMouseWheelUp() {
		return mouseWheelUp;
	}
}
