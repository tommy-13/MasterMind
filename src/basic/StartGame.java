package basic;

import game4.Game4;
import menu.Menu;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StartGame extends StateBasedGame {
	
	private final static String gameName = "Mastermind";
	private GameGlobals gameGlobals;
	private InputState inputState = new InputState();

	
	public StartGame() throws SlickException {
		super(gameName);
		
		/* add states (screens) */
		gameGlobals = new GameGlobals();
		gameGlobals.menu = new Menu(gameGlobals, inputState);
		gameGlobals.game = new Game4(gameGlobals, inputState);
		
		this.addState(gameGlobals.menu);
		this.addState(gameGlobals.game);
		
		/* init sound */
		SoundEffect.init();
	}
	

	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		/* Initialize screens */
		this.getState(GameWindow.MENU).init(gameContainer, this);
		this.getState(GameWindow.GAME4).init(gameContainer, this);
		
		/* set first screen */
		this.enterState(GameWindow.MENU);
	}

	
	
	public static void main(String[] args) {
		
		/* create window */
		AppGameContainer appGameContainer;
		try {
			appGameContainer = new AppGameContainer(new StartGame());
			appGameContainer.setIcon("res/mastermind32.png");
			appGameContainer.setDisplayMode(600, 600, false);
			appGameContainer.start();
		} catch(SlickException e) {
			e.printStackTrace();
		}
	}
}
