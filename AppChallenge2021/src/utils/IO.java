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
	
	public static <T> T readObject(File f)  throws IOException, ClassNotFoundException {
		FileInputStream fileIn = new FileInputStream(f);
		ObjectInputStream objectIn = new ObjectInputStream(fileIn);
		T obj = (T) objectIn.readObject();
		objectIn.close();
		return obj;
	}
	
}
