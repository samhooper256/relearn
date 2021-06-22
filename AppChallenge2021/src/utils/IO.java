/**
 * 
 */
package utils;

import java.io.*;
import java.nio.file.*;

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
	
	public static File renamed(File f, String newFilename) throws IOException {
		Path path = f.toPath();
		Path newPath = Files.move(path, path.resolveSibling(newFilename));
		return newPath.toFile();
	}
	
	
}
