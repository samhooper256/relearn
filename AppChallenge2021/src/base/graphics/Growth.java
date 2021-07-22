/**
 * 
 */
package base.graphics;

import static fxutils.Nodes.setLayout;

import java.util.*;

import base.Main;
import fxutils.*;
import javafx.animation.*;
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
	private static final Duration FADE_DURATION = Duration.millis(2000);
	private static final Duration DELAY_BETWEEN_RECTS = Duration.millis(10);
	
	private final Queue<Rectangle> q = new ArrayDeque<>();
	private final List<Rectangle> drawOrder = new ArrayList<>();
	private final Timeline timeline;
	
	private int count = 0;
	public Growth() {
		setOnMouseClicked(e -> {
			if(e.getButton() == MouseButton.SECONDARY)
				regrow();
		});
		this.setVisible(false);
		grow();
		timeline = Animations.animationWithDelay
				(getChildren(), Node::opacityProperty, FADE_DURATION, DELAY_BETWEEN_RECTS, 0, 1);
	}
	
	
	private void grow() {
		Rectangle r0 = new Rectangle();
		r0.setWidth(START_SIZE);
		r0.setHeight(START_SIZE);
		r0.setLayoutX(-START_SIZE);
		r0.setLayoutY(0);
		r0.setFill(randomColor(x(r0), y(r0)));
		addRect(r0);
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
		return new Color(RNG.low(), RNG.low(), RNG.high(), opacity(x, y));
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
			setLayout(r1, x(r) - halfWidth, y(r));
			Rectangle r2 = new Rectangle(halfWidth, halfHeight);
			setLayout(r2, x(r) - halfWidth, y(r) + halfHeight);
			colorAndAddRectsIfPossible(r1, r2);
		}
		else {
			Rectangle r1 = new Rectangle(r.getWidth(), r.getWidth());
			setLayout(r1, x(r) - r.getWidth(), y(r));
			colorAndAddRectsIfPossible(r1);
		}
		//do bottom:
		if(halfWidth >= MIN_SIZE && RNG.next() <= splitProb) {
			Rectangle r1 = new Rectangle(halfWidth, halfHeight);
			setLayout(r1, x(r), y(r) + r.getHeight());
			Rectangle r2 = new Rectangle(halfWidth, halfHeight);
			setLayout(r2, x(r) + halfWidth, y(r) + r.getHeight());
			colorAndAddRectsIfPossible(r1, r2);
		}
		else {
			Rectangle r1 = new Rectangle(r.getWidth(), r.getWidth());
			setLayout(r1, x(r), y(r) + r.getHeight());
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
	
	public void animateIn() {
		Nodes.setOpacity(getChildren(), 0);
		this.setVisible(true);
	    timeline.play();
	}
	
}
