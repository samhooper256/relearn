/**
 * 
 */
package base.graphics;

import static fxutils.Shapes.setLayoutCoords;

import java.util.*;

import base.Main;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
	
	public Growth() {
		setOnMouseClicked(e -> {
			if(e.getButton() == MouseButton.SECONDARY)
				regrow();
		});
		grow();
	}

	private void grow() {
		Rectangle r0 = new Rectangle();
		r0.setWidth(START_SIZE);
		r0.setHeight(START_SIZE);
		r0.setLayoutX(-START_SIZE);
		r0.setLayoutY(0);
		r0.setFill(randomColor());
		addRects(r0);
		q.add(r0);
		while(!q.isEmpty())
			spawnFrom(q.remove());
		q.clear();
	}
	
	private void regrow() {
		getChildren().clear();
		q.clear();
		count = 0;
		grow();
	}
	
	private Color randomColor() {
		return new Color(RNG.high(), RNG.next(), RNG.next(), RNG.high());
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
		r.setFill(randomColor());
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
	}

	private double x(Rectangle r) {
		return r.getLayoutX();
	}
	
	private double y(Rectangle r) {
		return r.getLayoutY();
	}
}
