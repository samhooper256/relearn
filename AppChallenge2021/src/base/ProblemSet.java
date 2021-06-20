/**
 * 
 */
package base;

import java.io.*;
import java.util.*;

import javafx.beans.property.*;
import topics.Addition;

/**
 * @author Sam Hooper
 *
 */
public final class ProblemSet {
	
	private static final Set<ProblemSet> SETS = new HashSet<>();
	private static boolean loaded = false;
	
	static synchronized void loadSets() {
		if(loaded)
			return; //don't load if we've already done it earlier.
		loaded = true;
		for(File f : Main.SETS_FOLDER.listFiles()) {
			ProblemSetData data = null;
			try {
				FileInputStream fileIn = new FileInputStream(f);
				ObjectInputStream objectIn = new ObjectInputStream(fileIn);
				data = (ProblemSetData) objectIn.readObject();
				objectIn.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			SETS.add(data.toProblemSet());
		}
		if(SETS.isEmpty()) //add pre-built set
			SETS.add(new ProblemSet("Prebuilt Set", new SetConfiguration(new Addition(10))));
	}
	
	public static Set<ProblemSet> allSets() {
		return Collections.unmodifiableSet(SETS);
	}
	
	private StringProperty name;
	private final SetConfiguration config;
	
	public ProblemSet(String name) {
		this(name, new SetConfiguration());
	}
	
	public ProblemSet(String name, SetConfiguration config) {
		this.name = new SimpleStringProperty(name);
		this.config = config;
	}
	
	public StringProperty nameProperty() {
		return name;
	}
	
	public String name() {
		return nameProperty().get();
	}
	
	public void setName(String name) {
		nameProperty().set(name);
	}
	
	public SetConfiguration config() {
		return config;
	}
	
	public Deck createDeck() {
		return config().createDeck();
	}
	
	public void saveToFile() {
		System.out.printf("saving %s to file%n", this);
		File f = new File(Main.SETS_FOLDER, String.format("%s.dat", name()));
		ProblemSetData data = data();
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException(e); //TODO better error handling?
			}
		}
		try {
			FileOutputStream fileOut = new FileOutputStream(f, false);
	        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
	        objectOut.writeObject(data);
	        objectOut.close();
		}
		catch(Exception e) {
			throw new RuntimeException(e); //TODO better error handling?
		}
	}
	
	private ProblemSetData data() {
		return new ProblemSetData(name.get(), config());
	}
	
}
