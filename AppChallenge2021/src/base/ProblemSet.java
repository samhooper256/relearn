/**
 * 
 */
package base;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.Consumer;

import javafx.beans.property.*;
import topics.*;
import utils.IO;

/**
 * <p>The {@code equals} and {@code hashCode} methods are not overridden from {@code Object}.</p>
 * @author Sam Hooper
 *
 */
public final class ProblemSet {
	
	private static final Set<ProblemSet> SETS = new HashSet<>();
	private static boolean loaded = false;
	private static List<Consumer<ProblemSet>> onRegisterActions;
	
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
	
	public static void addOnRegisterAction(Consumer<ProblemSet> action) {
		if(onRegisterActions == null)
			onRegisterActions = new ArrayList<>();
		onRegisterActions.add(action);
	}
	
	private static void runOnRegisterActions(ProblemSet set) {
		if(onRegisterActions == null)
			return;
		for(Consumer<ProblemSet> action : onRegisterActions)
			action.accept(set);
	}

	private StringProperty name;
	private final SetConfiguration config;
	
	/** <p>Creates a new {@link ProblemSet} whose {@link #name() name} is the empty string.</p> */
	public ProblemSet() {
		this("");
	}
	
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
		nameProperty().set(Objects.requireNonNull(name));
	}
	
	public SetConfiguration config() {
		return config;
	}
	
	public Deck createDeck() {
		return config().createDeck();
	}
	
	public void addTopics(Collection<Topic> topics) {
		config().addTopics(topics);
	}
	
	private ProblemSetData data() {
		return new ProblemSetData(name.get(), config());
	}

	public boolean isRegistered() {
		return SETS.contains(this);
	}
	
	/** Registers this {@link ProblemSet} so that it becomes {@link #isRegistered() registered} and will be in the
	 * {@link Set} returned by {@link #allSets()}.
	 * @throws IllegalStateException if this {@link ProblemSet} is already {@link #isRegistered() registered}.*/
	public void register() {
		if(isRegistered())
			throw new IllegalStateException("This ProblemSet is already registered");
		SETS.add(this);
		runOnRegisterActions(this);
	}
	
	public void saveToFile() {
		saveToFile(name());
	}
	
	public void saveToFile(String oldName) {
		System.out.printf("saving %s to file, oldName=%s, name()=%s%n", this, oldName, name());
		File f = new File(Main.SETS_FOLDER, String.format("%s.dat", oldName));
		try {
			if(!name().equals(oldName)) {
				if(f.exists()) {
					//rename the file to its new name:
					Path path = f.toPath();
					Path p = Files.move(path, path.resolveSibling(String.format("%s.dat", name())));
					f = p.toFile();
				}
				else {
					//nothing to do except change the file object to one with the new name:
					f = new File(Main.SETS_FOLDER, String.format("%s.dat", name()));
				}
			}
			if(!f.exists())
				f.createNewFile();
			IO.writeObject(f, data());
		}
		catch(Exception e) {
			throw new RuntimeException(e); //TODO better error handling?
		}
	}
	
	
}
