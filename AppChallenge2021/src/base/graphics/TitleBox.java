/**
 * 
 */
package base.graphics;

import fxutils.*;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 * <p>{@link #visibleProperty() Visibility} is set to {@code false} by default.</p>
 * @author Sam Hooper
 *
 */
public final class TitleBox extends HBox {
	
	private static final Duration CHARACTER_FADE_DURATION = Duration.millis(1000);
	private static final Duration DELAY_BETWEEN_CHARACTERS = CHARACTER_FADE_DURATION.divide(2);
	private static final String TITLE_CHARACTER_CSS = "title-character";
	
	private final String text;
	private final Timeline timeline;
	
	public TitleBox(String text) {
		this.setVisible(false);
		this.text = text;
		for(int i = 0; i < text.length(); i++) {
			Label l = new Label(String.valueOf(text.charAt(i)));
			l.getStyleClass().add(TITLE_CHARACTER_CSS);
			getChildren().addAll(l);
		}
		timeline = Animations.animationWithDelay(
				getChildren(), Node::opacityProperty, CHARACTER_FADE_DURATION, DELAY_BETWEEN_CHARACTERS, 0, 1);
	}
	
	public void fadeIn() {
		Nodes.setOpacities(getChildren(), 0);
		this.setVisible(true);
		timeline.playFromStart();
	}
	
	public String text() {
		return text;
	}
	
}
