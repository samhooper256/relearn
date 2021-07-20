package base.practice;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.util.Duration;

final class ProgressBar extends StackPane {
	
	private static final double WIDTH = 400, HEIGHT = 50;
	private static final String 
			PROGRESS_BAR_CSS = "progress-bar",
			BAR_CSS = "bar",
			BORDER_CSS = "border";
	private static final Duration ANIMATION_DURATION = AccuracyBar.SLIDE_DURATION;
	
	private final class Slide extends Transition {
		
		private double oldWidth;
		private double destWidth;
		
		Slide() {
			setCycleDuration(ANIMATION_DURATION);
			setInterpolator(Interpolator.EASE_OUT);
		}
		
		@Override
		protected void interpolate(double frac) {
			double diff = destWidth - oldWidth;
			setBarWidth(oldWidth + frac * diff);
		}
		
		void requestSlide(double destWidth) {
			setDestWidth(destWidth);
			if(getStatus() != Status.RUNNING) {
				oldWidth = getBarWidth();
				playFromStart();
			}
		}
		
		private void setDestWidth(double newDestWidth) {
			destWidth = newDestWidth;
		}
		
	}
	
	private final class Fade extends Transition {
		
		Fade() {
			setCycleDuration(ANIMATION_DURATION);
			setInterpolator(Interpolator.EASE_OUT);
		}

		@Override
		protected void interpolate(double frac) {
			bar.setOpacity(frac);
		}
		
		void requestFade() {
			if(getStatus() != Status.RUNNING)
				playFromStart();
		}
	}
	
	private final Region bar, border;
	private final Slide slide;
	private final Fade fade;
	
	private double total;
	private double finished;
	
	ProgressBar() {
		bar = new Region();
		bar.getStyleClass().add(BAR_CSS);
		StackPane.setAlignment(bar, Pos.CENTER_LEFT);
		border = new Region();
		border.getStyleClass().add(BORDER_CSS);
		slide = new Slide();
		fade = new Fade();
		setPrefSize(WIDTH, HEIGHT);
		getStyleClass().add(PROGRESS_BAR_CSS);
		getChildren().addAll(bar, border);
	}
	
	void resetWithTotal(int total) {
		this.total = total;
		finished = 0;
		setBarWidth(0);
		bar.setVisible(false);
	}
	
	void addFinished() {
		finished++;
		double endWidth = WIDTH * finished / total;
		if(finished == 1)
			fadeIn(endWidth);
		else
			slide.requestSlide(endWidth);
	}

	/** Assumes the bar is not visible. */
	private void fadeIn(double endWidth) {
		setBarWidth(endWidth);
		fade.requestFade();
		bar.setVisible(true);
	}
	
	private void setBarWidth(double width) {
		bar.setMaxWidth(width);
	}
	
	private double getBarWidth() {
		return bar.getMaxWidth();
	}
	
}
