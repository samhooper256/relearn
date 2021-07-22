package fxutils;

import javafx.scene.paint.Color;

public final class Colors {

	private Colors() {
		
	}
	
	public static Color getBlackOrWhiteContrasting(Color background) {
		double[] rgb = {background.getRed(), background.getGreen(), background.getBlue()};
		for(int i = 0; i < rgb.length; i++) {
			if(rgb[i] <= 0.03928)
				rgb[i] = rgb[i] / 12.92;
			else
				rgb[i] = Math.pow((rgb[i]+0.055)/1.055, 2.4);
		}
		double L = 0.2126 * rgb[0] + 0.7152 * rgb[1] + 0.0722 * rgb[2];
		return L > 0.179 ? Color.BLACK : Color.WHITE;
	}
}
