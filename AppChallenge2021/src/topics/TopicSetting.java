/**
 * 
 */
package topics;

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
		if(setting instanceof IntSetting is)
			return new IntSettingDisplay(is);
		else if(setting instanceof IntRange ir)
			return new IntRangeDisplay(ir);
		else
			throw new UnsupportedOperationException(String.format("Unrecognized setting : %s", setting.getClass()));
	}
	
}
