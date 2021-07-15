package topics.settings;

import javafx.scene.control.CheckBox;

final class BooleanSettingDisplay extends CheckBox {
	
	BooleanSettingDisplay(BooleanSetting setting) {
		super(setting.name());
		setSelected(setting.value());
		selectedProperty().addListener((x, y, newValue) -> {
			setting.set(newValue);
		});
	}
	
}
