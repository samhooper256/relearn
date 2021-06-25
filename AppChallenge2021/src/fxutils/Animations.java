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
	
	public static <N extends Node> Timeline animationWithDelay(
			List<N> nodes,
			Function<N, WritableValue<Number>> valueFunction,
			Duration durationPerNode,
			Duration delayBetweenNodes,
			Number startValue,
			Number endValue) {
		Timeline timeline = new Timeline();
		Duration start = Duration.ZERO;
		Duration end = durationPerNode;
		Duration offset = delayBetweenNodes;
		for(int i = 0; i < nodes.size(); i++) {
			N n = nodes.get(i);
			KeyValue startKeyValue = new KeyValue(valueFunction.apply(n), startValue);
			KeyFrame startFrame = new KeyFrame(start, startKeyValue);
			KeyValue endKeyValue = new KeyValue(valueFunction.apply(n), endValue);
			KeyFrame endFrame = new KeyFrame(end, endKeyValue);
			timeline.getKeyFrames().addAll(startFrame, endFrame);
			start = start.add(offset);
			end = end.add(offset);
		}
		return timeline;
	}
	
}
