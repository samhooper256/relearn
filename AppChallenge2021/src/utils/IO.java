/**
 * 
 */
package utils;

import java.io.*;

/**
 * @author Sam Hooper
 *
 */
public final class IO {

	private IO() {
		
	}
	
	public static void writeObject(File f, Object obj) throws IOException {
		writeObject(f, obj, false);
	}
	
	public static void writeObject(File f, Object obj, boolean append) throws IOException {
		ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(f, append));
        objectOut.writeObject(obj);
        objectOut.close();
	}
	
}
