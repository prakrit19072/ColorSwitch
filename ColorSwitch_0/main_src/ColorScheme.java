package main_src;

import javafx.scene.paint.Color;

public final class ColorScheme {
	
	private ColorScheme() {};
	
	private final static Color Color1 = Color.YELLOW;
	private final static Color Color2 = Color.AQUA;
	private final static Color Color3 = Color.DARKVIOLET;
	private final static Color Color4 = Color.DEEPPINK;
	
	public static Color getColor(int colorNo) {
		switch (colorNo) {
		case 1: return Color1;
		case 2: return Color2;
		case 3: return Color3;
		case 4: return Color4;
			
		default:
			return Color1;
		}
	}

}

