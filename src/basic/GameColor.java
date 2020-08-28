package basic;

import org.newdawn.slick.Color;


public class GameColor {
	
	/* colors for the fields */
	public static final Color blue = Color.blue;
	public static final Color red  = Color.red;
	public static final Color green = Color.green;
	public static final Color orange = Color.orange;
	public static final Color cyan = Color.cyan;
	public static final Color magenta = Color.magenta;
	public static final Color dark_green = new Color(0,150,0);
	public static final Color brown = new Color(180,100,40);
	public static final Color turquois = new Color(0,180,150);
	public static final Color yellow = Color.yellow;
	
	
	
	public static final Color marked = new Color(255,255,255);
	public static final Color white = Color.white;
	public static final Color right = Color.black;
	public static final Color existing = new Color(130,130,140);
	

	
	public static final Color background = new Color(200,200,255);
	public static final Color hidden_solution = new Color(150,150,255);
	public static final Color highlighted_line = new Color(0,0,0,200);
	
	
	/* select color */
	public static Color getColor(int id) {
		switch(id) {
		case 0: return blue;
		case 1: return red;
		case 2: return green;
		case 3: return orange;
		case 4: return cyan;
		case 5: return magenta;
		case 6: return dark_green;
		case 7: return brown;
		case 8: return turquois;
		case 9: return yellow;
		case 100: return white;
		case 101: return right;
		case 102: return existing;
		default: return null;
		}
	}
	
	/* get id */
	public static int getColorId(Color color) {
		if(color.equals(blue)) {
			return 0;
		}
		else if(color.equals(red)) {
			return 1;
		}
		else if(color.equals(green)) {
			return 2;
		}
		else if(color.equals(orange)) {
			return 3;
		}
		if(color.equals(cyan)) {
			return 4;
		}
		else if(color.equals(magenta)) {
			return 5;
		}
		else if(color.equals(dark_green)) {
			return 6;
		}
		else if(color.equals(brown)) {
			return 7;
		}
		else if(color.equals(turquois)) {
			return 8;
		}
		else if(color.equals(yellow)) {
			return 9;
		}
		else if(color.equals(white)) {
			return 100;
		}
		else if(color.equals(right)) {
			return 101;
		}
		else if(color.equals(existing)) {
			return 102;
		}
		else {
			return -1;
		}
	}
	
}
