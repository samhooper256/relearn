package base.settings;

import java.io.Serializable;

/**
 * 
 * @author Sam Hooper
 *
 */
final class GeneralSettingsRecord implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7621716899676221816L;

	static final GeneralSettingsRecord DEFAULT = new GeneralSettingsRecord(false);
	
	private final boolean doNotShowEditWarning;
	
	public GeneralSettingsRecord(boolean doNotShowEditWarning) {
		this.doNotShowEditWarning = doNotShowEditWarning;
	}
	
	public boolean doNotShowEditWarning() {
		return doNotShowEditWarning;
	}
	
}
