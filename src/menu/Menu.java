package menu;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import basic.Button;
import basic.GameColor;
import basic.GameGlobals;
import basic.GameWindow;
import basic.InputState;
import basic.SoundEffect;

public class Menu extends BasicGameState implements GameState {
	
	private GameGlobals gameGlobals;
	private InputState inputState;
	
	/* buttons */
	private Button btnEnd;
	private Button btnPlay;
	
	
	/* options */
	private Label lblHeadline;
	private Label lblOptionColor, lblOptionFields;
	
	private Label optionColor, optionFields;
	
	private Button btnFieldsPlus, btnFieldsMinus;
	private Button btnColorPlus, btnColorMinus;
	

	
	public Menu(GameGlobals gameGlobals, InputState inputState) {
		this.gameGlobals = gameGlobals;
		this.inputState = inputState;
	}

	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {
		
		int middle = (int) (gameContainer.getWidth() / 2 - 20);
		int middleGap   = 40;
		int plusOffset  = 95;
		int minusOffset = 65;
		
		int optionFieldsY = 200;
		int optionColorY  = 250;
		
		/* headline */
		Font headline = new AngelCodeFont("res/fonts/headline.fnt", "res/fonts/headline.png");
		lblHeadline   = new Label(middle +20,50,"MASTERMIND", headline, Label.CENTER);
		
		
		/* options */
		Font option     = new AngelCodeFont("res/fonts/option.fnt", "res/fonts/option.png");
		lblOptionFields = new Label(middle,optionFieldsY,"Felder:", option, Label.RIGHT);
		lblOptionColor  = new Label(middle,optionColorY,"Farben:", option, Label.RIGHT);
		
		optionFields = new Label(middle+middleGap,optionFieldsY,
				Integer.toString(gameGlobals.getFields()), option, Label.RIGHT);
		optionColor  = new Label(middle+middleGap,optionColorY,
				Integer.toString(gameGlobals.getColors()), option, Label.RIGHT);

		
		/* plus / minus */
		SpriteSheet sprMinus = new SpriteSheet("res/sprites/minus_strip3.png", 24, 24);
		SpriteSheet sprPlus  = new SpriteSheet("res/sprites/plus_strip3.png", 24, 24);
		
		btnFieldsMinus = new Button(middle+minusOffset,optionFieldsY+7, sprMinus);
		btnFieldsPlus  = new Button(middle+plusOffset, optionFieldsY+7, sprPlus);
		btnColorMinus = new Button(middle+minusOffset,optionColorY+7, sprMinus);
		btnColorPlus  = new Button(middle+plusOffset, optionColorY+7, sprPlus);

		
		
		/* buttons */
		SpriteSheet sprPlay = new SpriteSheet("res/sprites/btnSpielen01_strip3.png", 192, 48);
		SpriteSheet sprEnd  = new SpriteSheet("res/sprites/btnEnde01_strip3.png", 192, 48);
		
		btnPlay = new Button(200, 330, sprPlay);
		btnEnd  = new Button(200, 500, sprEnd);
		
			
		/* settings */
		gameContainer.setShowFPS(false);
		
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame,
			Graphics g) throws SlickException {
		
		/* background */
		g.setBackground(GameColor.background);
		
		
		/* headline */
		lblHeadline.draw(g);
		
		
		/* options */
		lblOptionFields.draw(g);
		lblOptionColor.draw(g);
		
		optionFields.draw(g);
		optionColor.draw(g);
		
		btnFieldsMinus.draw(g);
		btnFieldsPlus.draw(g);
		btnColorMinus.draw(g);
		btnColorPlus.draw(g);

		
		/* buttons */
		btnPlay.draw(g);
		btnEnd.draw(g);
		
		
	}

	
	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame,
			int delta) throws SlickException {
		
		Input input = gameContainer.getInput();
		
		inputState.setMousePosition(input.getMouseX(), input.getMouseY());
		inputState.mouseButtonLeft.setMouseState(input.isMousePressed(Input.MOUSE_LEFT_BUTTON),
				input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON));
		inputState.mouseButtonRight.setMouseState(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON),
				input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON));
		
		
		
		/* button: less/more fields */
		btnFieldsMinus.update(inputState, delta);
		if(btnFieldsMinus.isReleased()) {
			btnFieldsMinus.resetReleased();
			gameGlobals.setFields(getNewValue(gameGlobals.getFields(), 4, 6, false));
			optionFields.setText(Integer.toString(gameGlobals.getFields()));
			SoundEffect.CLICK.play();
		}
		
		btnFieldsPlus.update(inputState, delta);
		if(btnFieldsPlus.isReleased()) {
			btnFieldsPlus.resetReleased();
			gameGlobals.setFields(getNewValue(gameGlobals.getFields(), 6, 4, true));
			optionFields.setText(Integer.toString(gameGlobals.getFields()));
			SoundEffect.CLICK.play();
		}
		
		
		btnColorMinus.update(inputState, delta);
		if(btnColorMinus.isReleased()) {
			btnColorMinus.resetReleased();
			gameGlobals.setColors(getNewValue(gameGlobals.getColors(), 4, 10, false));
			optionColor.setText(Integer.toString(gameGlobals.getColors()));
			SoundEffect.CLICK.play();
		}
		
		btnColorPlus.update(inputState, delta);
		if(btnColorPlus.isReleased()) {
			btnColorPlus.resetReleased();
			gameGlobals.setColors(getNewValue(gameGlobals.getColors(), 10, 4, true));
			optionColor.setText(Integer.toString(gameGlobals.getColors()));
			SoundEffect.CLICK.play();
		}
		
		
		
		
		/* button: go to game */
		btnPlay.update(inputState, delta);
		if(btnPlay.isReleased()) {
			btnPlay.resetReleased();
			gameGlobals.game.setCode();
			stateBasedGame.enterState(GameWindow.GAME4, GameGlobals.transitionOut(), GameGlobals.transitionIn());
		}
		
		
		/* button: end the game */
		btnEnd.update(inputState, delta);
		if(btnEnd.isReleased()) {
			btnEnd.resetReleased();
			gameContainer.exit();
		}
		
		/* esc: end the game */
		if(input.isKeyDown(Input.KEY_ESCAPE)) {
			gameContainer.exit();
		}
		
	}

	@Override
	public int getID() {
		return GameWindow.MENU;
	}
	
	
	/**
	 * increase / decrease some value
	 * @param currentValue
	 * @param testBorder   border to test value against
	 * @param setBorder    new border if value==testBorder
	 * @param increase     true, if value shall be increased
	 * @return			   new value
	 */
	public int getNewValue(int currentValue, int testBorder, int setBorder, boolean increase) {
		if(currentValue == testBorder) {
			currentValue = setBorder;
		}
		else {
			if(increase) {
				currentValue++;
			}
			else {
				currentValue--;
			}
		}
		return currentValue;
	}

}
