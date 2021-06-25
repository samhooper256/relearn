/**
 * 
 */
package base.graphics;

import javafx.animation.*;
import javafx.animation.Animation.Status;
import javafx.scene.image.*;
import javafx.util.Duration;

/**
 * @author Sam Hooper
 *
 */
public class MainMenuButton extends ImageView {
	
	private static final int BUTTON_EXTENSION = 80;
	private static final Duration TRANSLATE_DURATION = Duration.millis(200);
	
	private final TranslateTransition slide;
	
	public MainMenuButton(Image image) {
		super(image);
		this.setTranslateX(-BUTTON_EXTENSION);
		slide = new TranslateTransition(TRANSLATE_DURATION, this);
		initSlide();
		initHover();
	}
	
	private void initSlide() {
		slide.setFromX(-BUTTON_EXTENSION);
		slide.setToX(0);
	}
	
	private void initHover() {
		this.setOnMouseEntered(e -> {
			if(slide.getRate() < 0)
				slide.setRate(-slide.getRate());
			if(slide.getCurrentTime().compareTo(Duration.ZERO) == 0)
				slide.play();
		});
		this.setOnMouseExited(e -> {
			if(slide.getRate() > 0)
				slide.setRate(-slide.getRate());
			if(slide.getCurrentTime().compareTo(Duration.ZERO) > 0)
				slide.play();
		});
	}
	
	public void setOnAction(Runnable r) {
		this.setOnMouseClicked(e -> r.run());
	}
	
}
