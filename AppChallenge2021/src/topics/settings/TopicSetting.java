/**
 * 
 */
package topics.settings;

import java.io.Serializable;

import base.Named;
import javafx.scene.Node;

/**
 * @author Sam Hooper
 *
 */
public interface TopicSetting extends Named, Serializable {
	
	String SETTING_SLIDER_CSS = "setting-slider";
	
	static Node settingNodeFor(TopicSetting setting) {
		if(setting instanceof BooleanSetting) {
			BooleanSetting bs = (BooleanSetting) setting;
			return new BooleanSettingDisplay(bs);
		}
		if(setting instanceof IntSetting) {
			IntSetting is = (IntSetting) setting;
			return new IntSettingDisplay(is);
		}
		if(setting instanceof IntRange) {
			IntRange ir = (IntRange) setting;
			return new IntRangeDisplay(ir);
		}
		throw new UnsupportedOperationException(String.format("Unrecognized setting : %s", setting.getClass()));
	}
	
}
