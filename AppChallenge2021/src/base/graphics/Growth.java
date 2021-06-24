/**
 * 
 */
package base.graphics;

import static fxutils.Shapes.setLayoutCoords;

import java.util.*;

import base.Main;
import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.scene.*;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import utils.RNG;

/**
 * @author Sam Hooper
 *
 */
public class Growth extends Group {
	
	private static final int START_SIZE = 80;
	private static final int RECT_COUNT = 1000;
	private static final double MIN_SIZE = 10;
	
	private int count = 0;
	private final Queue<Rectangle> q = new ArrayDeque<>();
	private final List<Rectangle> drawOrder = new ArrayList<>();
	
	public Growth() {
		setOnMouseClicked(e -> {
			if(e.getButton() == MouseButton.SECONDARY)
				regrow();
		});
		this.setVisible(false);
		grow();
	}
	
	
	private void grow() {
		Rectangle r0 = new Rectangle();
		r0.setWidth(START_SIZE);
		r0.setHeight(START_SIZE);
		r0.setLayoutX(-START_SIZE);
		r0.setLayoutY(0);
		r0.setFill(randomColor(x(r0), y(r0)));
		addRects(r0);
		q.add(r0);
		while(!q.isEmpty())
			spawnFrom(q.remove());
		q.clear();
	}
	
	private void regrow() {
		getChildren().clear();
		drawOrder.clear();
		q.clear();
		count = 0;
		grow();
	}
	
	private Color randomColor(double x, double y) {
		return new Color(RNG.high(), RNG.low(), RNG.low(), opacity(x, y));
	}


	private double opacity(double x, double y) {
		return RNG.high();
//		return RNG.high() * (1 - clamped(x, y));
	}
	
	void spawnFrom(Rectangle r) {
		if(count >= RECT_COUNT)
			return;
		double splitProb = splitProb(x(r), y(r));
		double halfWidth = r.getWidth() / 2;
		double halfHeight = r.getHeight() / 2;
		//do left:
		if(halfWidth >= MIN_SIZE && RNG.next() <= splitProb) {
			Rectangle r1 = new Rectangle(halfWidth, halfHeight);
			setLayoutCoords(r1, x(r) - halfWidth, y(r));
			Rectangle r2 = new Rectangle(halfWidth, halfHeight);
			setLayoutCoords(r2, x(r) - halfWidth, y(r) + halfHeight);
			colorAndAddRectsIfPossible(r1, r2);
		}
		else {
			Rectangle r1 = new Rectangle(r.getWidth(), r.getWidth());
			setLayoutCoords(r1, x(r) - r.getWidth(), y(r));
			colorAndAddRectsIfPossible(r1);
		}
		//do bottom:
		if(halfWidth >= MIN_SIZE && RNG.next() <= splitProb) {
			Rectangle r1 = new Rectangle(halfWidth, halfHeight);
			setLayoutCoords(r1, x(r), y(r) + r.getHeight());
			Rectangle r2 = new Rectangle(halfWidth, halfHeight);
			setLayoutCoords(r2, x(r) + halfWidth, y(r) + r.getHeight());
			colorAndAddRectsIfPossible(r1, r2);
		}
		else {
			Rectangle r1 = new Rectangle(r.getWidth(), r.getWidth());
			setLayoutCoords(r1, x(r), y(r) + r.getHeight());
			colorAndAddRectsIfPossible(r1);
		}
	}
	
	private double splitProb(double x, double y) {
		return clamped(x, y);
	}


	private double clamped(double x, double y) {
		return Math.min(1, Math.hypot(x, y) / (Main.screenWidth() * 0.75));
	}
	
	private void colorAndAddRectsIfPossible(Rectangle... rects) {
		for(Rectangle r : rects)
			colorAndAddRectIfPossible(r);
	}

	private void colorAndAddRectIfPossible(Rectangle r) {
		double 	centerX = x(r) + r.getWidth() / 2,
				centerY = y(r) + r.getHeight() / 2;
		if(contains(centerX, centerY))
			return;
		colorAndAddRect(r);
	}
	
	private void colorAndAddRect(Rectangle r) {
		r.setFill(randomColor(x(r), y(r)));
		addRect(r);
	}
	
	private void addRects(Rectangle... rects) {
		for(Rectangle r : rects)
			addRect(r);
	}
	
	private void addRect(Rectangle r) {
		count++;
		getChildren().add(r);
		q.add(r);
		drawOrder.add(r);
	}

	private double x(Rectangle r) {
		return r.getLayoutX();
	}
	
	private double y(Rectangle r) {
		return r.getLayoutY();
	}
	
	private void setOpacities(double opacity) {
		for(Node n : getChildren())
			n.setOpacity(opacity);
	}
	
//	public void drawIn() {
//		setOpacities(0);
//		this.setVisible(true);
//		int[] indexHolder = {0};
//		
//		FadeTransition fade = new FadeTransition(Duration.millis(10));
//		fade.setFromValue(0);
//		fade.setToValue(1);
//		fade.setNode(drawOrder.get(indexHolder[0]));
//		fade.setOnFinished(e -> {
//			int i = indexHolder[0];
//			if(i == drawOrder.size() - 1)
//				return;
//			indexHolder[0]++;
//			fade.setNode(drawOrder.get(indexHolder[0]));
//			fade.playFromStart();
//		});
//		fade.playFromStart();
//	}
	
	public void drawIn2() {
		setOpacities(0);
		this.setVisible(true);
		newFade(0);
	}
	
	private void newFade(int i) {
		Node n = drawOrder.get(i);
		final double duration = 500;
		FadeTransition f = new FadeTransition(Duration.millis(duration));
		f.setNode(n);
		f.setFromValue(0);
		f.setToValue(1);
		f.setOnFinished(e -> {
			int i1 = i + 1;
			int i2 = i * 2;
			if(i2 < drawOrder.size()) {
				Rectangle r = drawOrder.get(i2);
				if(r.getOpacity() == 0.0)
					newFade(i2);
			}
			if(i1 < drawOrder.size()) {
				Rectangle r = drawOrder.get(i1);
				if(r.getOpacity() == 0.0)
					newFade(i1);
			}
		});
		f.playFromStart();
	}
	
	public void drawIn3() {
		setOpacities(0);
		this.setVisible(true);
		List<Node> components = getChildren();
	    Timeline time = new Timeline();

	    Duration startTime = Duration.ZERO ;
	    Duration endTime = Duration.millis(2000);

	    Duration offset = Duration.millis(10);
	    
	    for (Node component : components) {
	        KeyValue startValue = new KeyValue(component.opacityProperty(), 0, Interpolator.LINEAR);
	        KeyValue endValue = new KeyValue(component.opacityProperty(), 1, Interpolator.LINEAR);
	        KeyFrame start = new KeyFrame(startTime, startValue);
	        KeyFrame end = new KeyFrame(endTime, endValue);
	        time.getKeyFrames().add(start);
	        time.getKeyFrames().add(end);
	        startTime = startTime.add(offset);
	        endTime = endTime.add(offset);
	    }
	    time.play();
	}
}
