/**
 * 
 */
package topics;

import javafx.scene.control.Slider;

/**
 * @author Sam Hooper
 *
 */
class IntSettingDisplay extends Slider {
	
	private final IntSetting setting;
	
	public IntSettingDisplay(IntSetting setting) {
		this.setting = setting;
	}
	
}
