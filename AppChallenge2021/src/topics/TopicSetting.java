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
	
	static Node settingNodeFor(TopicSetting setting) {
		if(setting instanceof IntSetting is)
			return new IntSettingDisplay(is);
		else {
			throw new UnsupportedOperationException(
					String.format("Unrecognized TopicSetting type: %s", setting.getClass()));
		}
	}
	
}
