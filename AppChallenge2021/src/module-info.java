open module appchallenge2021 {
	
	requires transitive java.desktop;
	
	requires transitive javafx.base;
	requires transitive javafx.graphics;
	requires transitive javafx.web;
	
	requires javafx.controls;
	requires org.controlsfx.controls;
	
	exports base;
	exports base.sets;
	exports base.graphics;
	exports base.problems;
	exports topics;
	exports math;
	exports math.expressions;
	
}