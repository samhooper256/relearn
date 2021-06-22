open module appchallenge2021 {
	
	requires transitive java.desktop;
	
	requires transitive javafx.base;
	requires transitive javafx.graphics;
	requires transitive jlatexmath;
	requires transitive org.jfree.fxgraphics2d;
	requires transitive javafx.swing;
	requires javafx.controls;
	
	exports base;
	exports base.sets;
	exports topics;
	exports math;
	
}