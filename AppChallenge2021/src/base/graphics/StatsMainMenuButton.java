package base.graphics;

import base.Main;
import javafx.animation.*;

final class StatsMainMenuButton extends MainMenuButton {
	
	public static final String TEXT = "Stats";
	public static final double STARTING_OPACITY = 0.8;
	private final FadeTransition fade;
	
	StatsMainMenuButton() {
		super(Main.STATS_ICON, TEXT);
		iconView().setOpacity(STARTING_OPACITY);
		fade = new FadeTransition(SLIDE_DURATION, iconView());
		initFade();
	}

	private void initFade() {
		fade.setInterpolator(Interpolator.EASE_BOTH);
		fade.setFromValue(STARTING_OPACITY);
		fade.setToValue(1);
		hoverAnimations.add(fade);
	}
	
}
