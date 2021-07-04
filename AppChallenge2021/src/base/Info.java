package base;

import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * 
 * @author Sam Hooper
 *
 */
public class Info extends ImageView {

	private static final String TOOLTIP_CSS = "info-tooltip";
	
	private final Tooltip tooltip;
	
	public Info() {
		this("");
	}
	
	public Info(String text) {
		setImage(Main.INFO);
		tooltip = new Tooltip(text);
		initTooltip();
	}

	private void initTooltip() {
		Tooltip.install(this, tooltip);
		tooltip.setShowDelay(Duration.ZERO);
		tooltip.getStyleClass().add(TOOLTIP_CSS);
	}
	
}
