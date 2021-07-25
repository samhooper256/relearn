/**
 * 
 */
package base.graphics;

import javafx.animation.*;
import javafx.animation.Animation.Status;
import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

/**
 * @author Sam Hooper
 *
 */
public class FadePopup extends StackPane {
	
	public static final double DEFAULT_WIDTH = 400, DEFAULT_HEIGHT = 300;
	public static final boolean DEFAULT_GLASS_CLOSEABLE = true;
	
	private static final Duration
			RISING_DURATION = Duration.millis(200),
			FALLING_DURATION = Duration.millis(30),
			OUT_DURATON = Duration.millis(180);
	private static final String GLASS_PANE_CSS = "glass-pane";
	private static final double STARTING_OPACITY = 0, GLASS_PANE_OPACITY = 0.7;
	private static final double START_SIZE_PERCENT = 0.70, PEAK_SIZE_PERCENT = 1.02;
	
	private final StackPane glassPane;
	private final FadeTransition fadeIn, fadeOut, glassFadeIn, glassFadeOut;
	private final ScaleTransition rise, fall, scaleOut;
	private final  StackPane over;
	private final EventHandler<? super MouseEvent> glassClickHandler = e -> {
		if(!isFadingIn() && isGlassCloseable()) {
			fadeOut();
			if(getGlassCloseAction() != null)
				getGlassCloseAction().run();
		}
	};
	
	private boolean glassCloseable;
	private Runnable glassCloseAction;
	
	protected FadePopup(StackPane over) {
		this.over = over;
		this.glassCloseable = DEFAULT_GLASS_CLOSEABLE;
		this.glassCloseAction = null;
		
		glassPane = new StackPane();
		glassPane.setPickOnBounds(true);
		glassPane.getStyleClass().add(GLASS_PANE_CSS);
		glassPane.setOnMouseClicked(glassClickHandler);
		
		fadeIn = new FadeTransition(RISING_DURATION, this);
		fadeIn.setFromValue(STARTING_OPACITY);
		fadeIn.setToValue(1);
		
		glassFadeIn = new FadeTransition(RISING_DURATION.add(FALLING_DURATION), glassPane);
		glassFadeIn.setFromValue(STARTING_OPACITY);
		glassFadeIn.setToValue(GLASS_PANE_OPACITY);
		
		rise = new ScaleTransition(RISING_DURATION, this);
		rise.setFromX(START_SIZE_PERCENT);
		rise.setFromY(START_SIZE_PERCENT);
		rise.setToX(PEAK_SIZE_PERCENT);
		rise.setToY(PEAK_SIZE_PERCENT);
		
		fall = new ScaleTransition(FALLING_DURATION, this);
		fall.setFromX(PEAK_SIZE_PERCENT);
		fall.setFromY(PEAK_SIZE_PERCENT);
		fall.setToX(1);
		fall.setToY(1);
		
		rise.setOnFinished(e -> fall.play());
		fall.setOnFinished(e -> this.setMouseTransparent(false));
		
		fadeOut = new FadeTransition(OUT_DURATON, this);
		fadeOut.setFromValue(1);
		fadeOut.setToValue(0);
		
		glassFadeOut = new FadeTransition(OUT_DURATON, glassPane);
		glassFadeOut.setFromValue(GLASS_PANE_OPACITY);
		glassFadeOut.setToValue(0);
		
		scaleOut = new ScaleTransition(OUT_DURATON, this);
		scaleOut.setFromX(1);
		scaleOut.setFromY(1);
		scaleOut.setToX(START_SIZE_PERCENT);
		scaleOut.setToY(START_SIZE_PERCENT);
		
		scaleOut.setOnFinished(e -> hidePopup());
	}
	
	public void fadeIn() {
		this.setMouseTransparent(true);
		this.setOpacity(STARTING_OPACITY);
		over.getChildren().add(glassPane);
		over.getChildren().add(this);
		
		glassFadeIn.play();
		fadeIn.play();
		rise.play();
	}
	
	/** Does <em>not</em> run the {@link #getGlassCloseAction() glass close action}.*/
	public void fadeOut() {
		glassFadeOut.play();
		fadeOut.play();
		scaleOut.play();
	}
	
	/** Hides this {@link FadePopup}.
	 * Does <em>not</em> run the {@link #getGlassCloseAction() glass close action} and does not play any animations.*/
	public void hidePopup() {
		over.getChildren().remove(this);
		over.getChildren().remove(glassPane);
	}
	
	public boolean isFadingIn() {
		return glassFadeIn.getStatus() == Status.RUNNING;
	}
	
	public void setGlassCloseAction(Runnable glassCloseAction) {
		this.glassCloseAction = glassCloseAction;
	}
	
	public Runnable getGlassCloseAction() {
		return glassCloseAction;
	}
	
	public void setGlassCloseable(boolean glassCloseable) {
		this.glassCloseable = glassCloseable;
	}
	
	public boolean isGlassCloseable() {
		return glassCloseable;
	}
	
}
