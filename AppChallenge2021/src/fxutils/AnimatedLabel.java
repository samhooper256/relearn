package fxutils;

import javafx.animation.Transition;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class AnimatedLabel extends Label {
	
	private static final Duration DEFAULT_LR_DURATION = Duration.millis(200);
	
	private static class LRAnimation extends Transition {
		
		private final AnimatedLabel label;
		
		private String destText;
		private int lastIndex;
		
		LRAnimation(AnimatedLabel label) {
			this(DEFAULT_LR_DURATION, label);
		}

		LRAnimation(Duration duration, AnimatedLabel label) {
			setCycleDuration(duration);
			this.label = label;
		}
		
		void animateTo(String destText) {
			this.destText = destText;
			lastIndex = 0;
			playFromStart();
		}
		
		@Override
		protected void interpolate(double frac) {
			int index = (int) Math.ceil(frac * destText.length());
			if(index == lastIndex)
				return;
			String oldText = label.getText();
			String newText = destText.substring(0, index);
			if(index < oldText.length())
					newText += oldText.substring(index);
			label.setText(newText);
		}
		
	}
	
	private LRAnimation lr;
	
	public AnimatedLabel() {
		this("");
	}
	
	public AnimatedLabel(String text) {
		super(text);
	}
	
	public void animateLRto(String newText) {
		lr().animateTo(newText);
	}
	
	private LRAnimation lr() {
		if(lr == null)
			lr = new LRAnimation(this);
		return lr;
	}
	
}
