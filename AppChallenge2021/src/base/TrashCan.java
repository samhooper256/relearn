/**
 * 
 */
package base;

import base.sets.ImageButton;

/**
 * @author Sam Hooper
 *
 */
public class TrashCan extends ImageButton {
	
	public TrashCan() {
		super(Main.trashCanImage());
	}
	
	public TrashCan(Runnable action) {
		super(Main.trashCanImage(), action);
	}
	
}