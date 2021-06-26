open module appchallenge2021 {
	
	requires transitive java.desktop;
	
	requires transitive javafx.base;
	requires transitive javafx.graphics;
	requires transitive jlatexmath;
	requires transitive org.jfree.fxgraphics2d;
	requires transitive javafx.swing;
	requires javafx.controls;
	requires org.controlsfx.controls;
	
	exports base;
	exports base.sets;
	exports base.graphics;
	exports topics;
	exports math;
	
}