/**
 * 
 */
package fxutils;

import java.util.List;
import java.util.function.Function;

import javafx.animation.*;
import javafx.beans.value.WritableValue;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * @author Sam Hooper
 *
 */
public final class Animations {

	private Animations() {
		
	}
	
	public static void setTo(ScaleTransition scale, double x, double y) {
		scale.setToX(x);
		scale.setToY(y);
	}
	
	public static void setFrom(ScaleTransition scale, double x, double y) {
		scale.setFromX(x);
		scale.setFromY(y);
	}
	
	/** If the {@link Animation} is currently playing with a positive {@link Animation#getRate() rate}, reverses its
	 * direction (and lets it continue playing). Otherwise, does nothing.*/
	public static void deactivate(Animation animation) {
		if(animation.getRate() > 0)
			animation.setRate(-animation.getRate());
		if(animation.getCurrentTime().compareTo(Duration.ZERO) > 0)
			animation.play();
	}

	/** If the {@link Animation} is currently playing with a negative {@link Animation#getRate() rate}, reverses its
	 * direction (and lets it continue playing). Otherwise, does nothing.*/
	public static void activate(Animation animation) {
		if(animation.getRate() < 0)
			animation.setRate(-animation.getRate());
		if(animation.getCurrentTime().compareTo(Duration.ZERO) == 0)
			animation.play();
	}
	
	public static <N extends Node> Timeline animationWithDelay(
			List<N> nodes,
			Function<N, WritableValue<Number>> valueFunction,
			Duration durationPerNode,
			Duration delayBetweenNodes,
			Number startValue,
			Number endValue) {
		Timeline t = new Timeline();
		addAnimationWithDelay(t, nodes, valueFunction, durationPerNode, delayBetweenNodes, startValue, endValue);
		return t;
	}
	
	public static <N extends Node> void addAnimationWithDelay(
			Timeline timeline,
			List<N> nodes,
			Function<N, WritableValue<Number>> valueFunction,
			Duration durationPerNode,
			Duration delayBetweenNodes,
			Number startValue,
			Number endValue) {
		addAnimationWithDelay(timeline, nodes, valueFunction, Duration.ZERO, durationPerNode, delayBetweenNodes,
				startValue, endValue, Interpolator.LINEAR);
	}
	
	public static <N extends Node> void addAnimationWithDelay(
			Timeline timeline,
			List<N> nodes,
			Function<N, WritableValue<Number>> valueFunction,
			Duration startingDelay,
			Duration durationPerNode,
			Duration delayBetweenNodes,
			Number startValue,
			Number endValue,
			Interpolator endInterpolator) {
		Duration start = startingDelay;
		Duration end = durationPerNode;
		Duration offset = delayBetweenNodes;
		for(int i = 0; i < nodes.size(); i++) {
			N n = nodes.get(i);
			KeyValue startKeyValue = new KeyValue(valueFunction.apply(n), startValue);
			KeyFrame startFrame = new KeyFrame(start, startKeyValue);
			KeyValue endKeyValue = new KeyValue(valueFunction.apply(n), endValue, endInterpolator);
			KeyFrame endFrame = new KeyFrame(end, endKeyValue);
			timeline.getKeyFrames().addAll(startFrame, endFrame);
			start = start.add(offset);
			end = end.add(offset);
		}
	}
}
