open module appchallenge2021 {
	
	requires transitive javafx.base;
	requires transitive javafx.graphics;
	requires transitive javafx.web;
	
	requires javafx.controls;
	requires org.controlsfx.controls;
	requires javafx.media;
	
	exports base;
	exports base.sets;
	exports base.graphics;
	exports base.problems;
	exports topics;
	exports topics.settings;
	exports math;
	exports math.expressions;
	
}