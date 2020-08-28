package game4;

import java.util.LinkedList;
import java.util.List;

import org.lwjgl.input.Mouse;
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

public class Game4 extends BasicGameState implements GameState {
	
	private GameGlobals gameGlobals;
	private InputState inputState;
	
	private int[] code;
	private int falseCodes;
	private ColorCircle[][] triedCodes;
	private ColorCircle[][] analysis;
	
	
	/* buttons */
	private Button checkButton;
	private Button backButton;
	private Button newGameButton;
	
	private boolean hasWon;
	
	
	/* for drawing */
	private int distanceToTop;
	private int distanceToLeft; 
	private int inputCircleRadius;
	private int distanceBetweenLines;
	private int analysisRadius;
	private int analysisX;
	private int height;
	private int width;
	private int distanceToBottom;
	private int buttonX;

	
	public Game4(GameGlobals gameGlobals, InputState inputState) {
		this.gameGlobals = gameGlobals;
		this.inputState = inputState;
		
		code = new int[6];
		falseCodes = 0;
		triedCodes = new ColorCircle[gameGlobals.numberOfTries][6];
		analysis   = new ColorCircle[gameGlobals.numberOfTries][6];
	}
	

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g)
			throws SlickException {
		
		g.setBackground(GameColor.background);
		
		backButton.draw(g);
		newGameButton.draw(g);
		
		if(falseCodes < gameGlobals.numberOfTries && !hasWon) {
			/* draw rectangle where solution will appear */
			g.setColor(GameColor.hidden_solution);
			g.fillRect(distanceToLeft-4, distanceToTop-12, width-16, distanceBetweenLines-8);
			
			/* highlight current line */
			g.setColor(GameColor.highlighted_line);
			g.fillRect(distanceToLeft-4,
					distanceToBottom - falseCodes*distanceBetweenLines - 4,
					width + ((int) (gameGlobals.getFields()+1)/2) * (2*analysisRadius+4) + 4,
					distanceBetweenLines - 4);
			
			/* draw check button */
			checkButton.draw(g);
		}
		else {
			/* draw solution */
			g.setColor(GameColor.hidden_solution);
			g.drawRect(distanceToLeft-4, distanceToTop-12, width-16, distanceBetweenLines-8);
			drawSolution(g);
		}
		
		
		
		/* draw input circles */
		for(int i=0; i<gameGlobals.numberOfTries; i++) {
			for(int j=0; j<gameGlobals.getFields(); j++) {
				triedCodes[i][j].draw(g);
			}
		}
		
		/* draw analysis circles */
		for(int i=0; i<gameGlobals.numberOfTries; i++) {
			for(int j=0; j<gameGlobals.getFields(); j++) {
				analysis[i][j].draw(g);
			}
		}
		
	}

	
	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBaseGame,
			int delta) throws SlickException {
		
		Input input = gameContainer.getInput();
		
		inputState.setMousePosition(input.getMouseX(), input.getMouseY());
		inputState.setMouseWheel(Mouse.getDWheel());
		inputState.mouseButtonLeft.setMouseState(input.isMousePressed(Input.MOUSE_LEFT_BUTTON),
				input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON));
		inputState.mouseButtonRight.setMouseState(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON),
				input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON));
		
		backButton.update(inputState, delta);
		if(backButton.isReleased()) {
			backButton.resetReleased();
			stateBaseGame.enterState(GameWindow.MENU, GameGlobals.transitionOut(), GameGlobals.transitionIn());
		}
		
		newGameButton.update(inputState, delta);
		if(newGameButton.isReleased()) {
			newGameButton.resetReleased();
			setCode();
		}
		
		
		if(falseCodes < gameGlobals.numberOfTries && !hasWon) {

			/* circles */
			for(int j=0; j<gameGlobals.getFields(); j++) {
				triedCodes[falseCodes][j].update(inputState, delta);
				analysis[falseCodes][j].update(inputState, delta);
			}


			/* button */
			checkButton.update(inputState, delta);
			if(checkButton.isReleased()) {
				checkButton.resetReleased();
				int[] result = checkCode(falseCodes);
				setAnalysis(falseCodes, result);

				if(result[0] == gameGlobals.getFields()) {
					// player won
					hasWon = true;
					SoundEffect.RIGHTCODE.play();
				}
				else {
					nextTry();
				}
			}
		}
		
		
		/* esc: end the game */
		if(input.isKeyDown(Input.KEY_ESCAPE)) {
			gameContainer.exit();
		}

	}
	

	@Override
	public int getID() {
		return GameWindow.GAME4;
	}
	
	
	/**
	 * set a new random code
	 * @throws SlickException 
	 */
	public void setCode() throws SlickException {
		
		hasWon = false;
		
		distanceToTop     = 48;
		distanceToLeft    = 144; 
		inputCircleRadius = 18;
		distanceBetweenLines = 2*inputCircleRadius+12;
		analysisRadius = 8;
		analysisX = distanceToLeft + gameGlobals.getFields()*(2*inputCircleRadius+8) + 16;
		height = gameGlobals.numberOfTries*distanceBetweenLines;
		distanceToBottom = distanceToTop + height;
		width = analysisX - distanceToLeft;
		buttonX = analysisX + ((int) (gameGlobals.getFields()+1)/2) * (2*analysisRadius + 4) + 32;

		
		/* create new random code */
		code = new int[gameGlobals.getFields()];
		for(int i=0; i<gameGlobals.getFields(); i++) {
			code[i] = (int) (Math.random() * gameGlobals.getColors());
		}
		
		falseCodes = 0;
		triedCodes = new ColorCircle[gameGlobals.numberOfTries][gameGlobals.getFields()];
		analysis   = new ColorCircle[gameGlobals.numberOfTries][gameGlobals.getFields()];
		
		
		/* create circles for user input */
		for(int i=0; i<gameGlobals.numberOfTries; i++) {
			for(int j=0; j<gameGlobals.getFields(); j++) {
				
				triedCodes[i][j] = new ColorCircle(distanceToLeft + j*(2*inputCircleRadius+8),
						distanceToBottom - i*distanceBetweenLines,
						inputCircleRadius,
						gameGlobals);
				
				analysis[i][j] = new ColorCircle(analysisX+((int) j/2)*(2*analysisRadius+4),
						distanceToBottom - i*distanceBetweenLines + (j % 2) * (2*analysisRadius+4),
						analysisRadius,
						gameGlobals);
				analysis[i][j].setColorID(100);
			}
		}
		
		/* activate first line and draw in blue */
		enableLine(0, true);
		colorLine(0, GameColor.getColorId(GameColor.blue));
		
		SpriteSheet sprButton = new SpriteSheet("res/sprites/check_strip3.png", 32, 32);
		checkButton = new Button(buttonX, distanceToBottom, sprButton);
		
		SpriteSheet sprBack = new SpriteSheet("res/sprites/back_strip3.png", 32, 32);
		backButton = new Button(48, distanceToBottom, sprBack);
		
		SpriteSheet sprNewGameButton = new SpriteSheet("res/sprites/newgame_strip3.png", 32, 32);
		newGameButton = new Button(distanceToLeft+width+2, distanceToTop-8, sprNewGameButton);
	}
	
	
	private void setButtonPosition(int line) {
		checkButton.setY(distanceToBottom - line*distanceBetweenLines);
	}
	
	private void enableLine(int line, boolean activate) {
		for(int i=0; i<gameGlobals.getFields(); i++){
			triedCodes[line][i].setActive(activate);
		}
	}
	
	private void colorLine(int line, int colorID) {
		for(int i=0; i<gameGlobals.getFields(); i++) {
			triedCodes[line][i].setColorID(colorID);
		}
	}
	
	private void nextTry() {
		enableLine(falseCodes, false);
		falseCodes++;
		if(falseCodes < gameGlobals.numberOfTries) {
			enableLine(falseCodes, true);
			colorLine(falseCodes, GameColor.getColorId(GameColor.blue));
			setButtonPosition(falseCodes);
			SoundEffect.WRONGCODE.play();
		}
		else {
			SoundEffect.GAMEOVER.play();
		}
	}
	
	
	private void drawSolution(Graphics g) {
		for(int i=0; i<gameGlobals.getFields(); i++) {
			g.setColor(GameColor.getColor(code[i]));
			g.fillOval(distanceToLeft + i*(2*inputCircleRadius+8),
					distanceToBottom - gameGlobals.numberOfTries * distanceBetweenLines - 10,
					2*inputCircleRadius,
					2*inputCircleRadius);
		}
	}
	
	
	/**
	 * check the code in the line line
	 * @param line
	 * @return {number of right colors, number of colors in the code}
	 */
	private int[] checkCode(int line) {
		
		int[] result = new int[2];
		result[0] = 0;
		result[1] = 0;
		
		
		/* get code */
		List<Integer> currentCode = new LinkedList<Integer>();
		List<Integer> correctCode = new LinkedList<Integer>();
		for(int i=0; i<gameGlobals.getFields(); i++) {
			int colorID = triedCodes[line][i].getColorID();
			
			if(colorID == code[i]) {
				/* equal */
				result[0]++;
			}
			else {
				currentCode.add(colorID);
				correctCode.add(code[i]);
			}
		}
		
		/* check if color exists in code */
		for(int i = currentCode.size()-1; i>=0; i--) {
			Integer currentInt = currentCode.get(i);
			
			if(correctCode.contains(currentInt)) {
				result[1]++;
				correctCode.remove(currentInt);
				currentCode.remove(currentInt);
			}
		}
		
		return result;
	}
	
	
	public void setAnalysis(int line, int[] result) {
		for(int i=0; i<result[0]; i++) {
			analysis[line][i].setColorID(GameColor.getColorId(GameColor.right));
		}
		for(int i=result[0]; i<result[0]+result[1]; i++) {
			analysis[line][i].setColorID(GameColor.getColorId(GameColor.existing));
		}
	}

}
