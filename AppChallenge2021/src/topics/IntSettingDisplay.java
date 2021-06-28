/**
 * 
 */
package topics;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;

/**
 * @author Sam Hooper
 *
 */
class IntSettingDisplay extends HBox {
	
	private final IntSetting setting;
	private final Slider slider;
	private final Label label;
	
	public IntSettingDisplay(IntSetting setting) {
		this.setting = setting;
		label = new Label(setting.name());
		slider = new Slider();
		initSlider();
		getChildren().addAll(label, slider);
	}

	private void initSlider() {
		slider.setValue(setting.value());
		slider.setMax(setting.max());
		slider.setMin(setting.min());
		slider.setSnapToTicks(true);
		slider.setBlockIncrement(1);
		slider.setMajorTickUnit(1);
		slider.setMinorTickCount(0);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.valueProperty().addListener(ov -> {
			setting.set(Math.toIntExact(Math.round(slider.getValue())));
		});
		slider.getStyleClass().add(TopicSetting.SETTING_SLIDER_CSS);
	}
	
}
