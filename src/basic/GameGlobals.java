package basic;

import game4.Game4;
import menu.Menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.Transition;

public class GameGlobals {
	
	public final int numberOfTries = 10;
	
	public Menu menu;
	public Game4 game;
	
	private int colors;
	private int fields;
		

	public GameGlobals() throws SlickException {
		colors = 4;
		fields = 4;
	}
	
	public static Transition transitionOut() {
		return new FadeOutTransition(Color.black, 500);
	}
	public static Transition transitionIn() {
		return new FadeInTransition(Color.black, 500);
	}


	public int getColors() {
		return colors;
	}


	public void setColors(int colors) {
		this.colors = colors;
	}


	public int getFields() {
		return fields;
	}


	public void setFields(int fields) {
		this.fields = fields;
	}
}
