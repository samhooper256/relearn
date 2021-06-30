/**
 * 
 */
package base;

import base.graphics.ImageButton;

/**
 * @author Sam Hooper
 *
 */
public class TrashCan extends ImageButton {
	
	public TrashCan() {
		super(Main.TRASH_CAN_IMAGE);
	}
	
	public TrashCan(Runnable action) {
		super(Main.TRASH_CAN_IMAGE, action);
	}
	
}