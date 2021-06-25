/**
 * 
 */
package base.graphics;

import javafx.animation.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

/**
 * @author Sam Hooper
 *
 */
public class FadePopup extends StackPane {
	
	public static final double DEFAULT_WIDTH = 400, DEFAULT_HEIGHT = 300;
	
	private static final double RISING_DURATION = 200, FALLING_DURATION = 30, OUT_DURATON = 180; //millis
	private static final double STARTING_OPACITY = 0;
	private static final double START_SIZE_PERCENT = 0.70, PEAK_SIZE_PERCENT = 1.02;
	
	private final StackPane glassPane;
	
	protected FadePopup() {
		glassPane = new StackPane();
		glassPane.setPickOnBounds(true);
	}
	
	public void fadeOnto(StackPane pane) {
//		pane.getChildren().get(pane.getChildren().size() - 1).setMouseTransparent(true);
		pane.getChildren().add(glassPane);
		this.setMouseTransparent(true);
		
		FadeTransition ft = new FadeTransition(Duration.millis(RISING_DURATION), this);
		ft.setFromValue(STARTING_OPACITY);
		ft.setToValue(1);
		
		ScaleTransition st = new ScaleTransition(Duration.millis(RISING_DURATION), this);
		st.setFromX(START_SIZE_PERCENT);
		st.setFromY(START_SIZE_PERCENT);
		st.setToX(PEAK_SIZE_PERCENT);
		st.setToY(PEAK_SIZE_PERCENT);
		st.play();
		
		ScaleTransition st2 = new ScaleTransition(Duration.millis(FALLING_DURATION), this);
		st2.setFromX(PEAK_SIZE_PERCENT);
		st2.setFromY(PEAK_SIZE_PERCENT);
		st2.setToX(1);
		st2.setToY(1);
		
		st.setOnFinished(e -> st2.play());
		st2.setOnFinished(e -> this.setMouseTransparent(false));
		
		this.setOpacity(STARTING_OPACITY);
		pane.getChildren().add(this);
		
		ft.play();
		st.play();
	}
	
	public void fadeOutFrom(StackPane pane) {
		FadeTransition ft = new FadeTransition(Duration.millis(OUT_DURATON), this);
		ft.setFromValue(1);
		ft.setToValue(0);
		
		ScaleTransition st = new ScaleTransition(Duration.millis(OUT_DURATON), this);
		st.setFromX(1);
		st.setFromY(1);
		st.setToX(START_SIZE_PERCENT);
		st.setToY(START_SIZE_PERCENT);
		
		st.setOnFinished(e -> hideFrom(pane));
		
		ft.play();
		st.play();
		
	}
	
	public void hideFrom(StackPane pane) {
		pane.getChildren().remove(this);
		pane.getChildren().remove(glassPane);
	}
	
}
