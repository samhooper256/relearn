/**
 * 
 */
package base.sets;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Consumer;

import base.Main;
import javafx.beans.property.*;
import topics.*;
import utils.*;

/**
 * <p>{@code ProblemSet} {@link #equals(Object) equality} is based solely on each set's unique {@link #id() identifier},
 * without regard to the set's {@link #name() name}, which may {@link #setName(String) change}, or its
 * {@link #config() configuration}. Upon creation, each {@code ProblemSet} is given a unique ID. A unique ID is
 * needed so that two sets can still be considered equal even after being serialized and deserialized.</p>
 * @author Sam Hooper
 *
 */
public final class ProblemSet implements Serializable {
	//TODO make transitive ReadOnlyStringProperty
	private static final long serialVersionUID = 415601596062566192L;
	private static final AudibleSet<ProblemSet> SETS = new AudibleSet<>(HashSet::new);
	private static final Set<String> NAMES = new HashSet<>();
	private static int NEXT_ID = readUID();
	
	private static boolean loaded = false;
	private static List<Consumer<ProblemSet>> onRegisterActions;
	
	public static synchronized void loadSets() {
		if(loaded)
			return; //don't load if we've already done it earlier.
		loaded = true;
		for(File f : Main.SETS_FOLDER.listFiles()) {
			if(f.getName().equals(Main.SET_UID_FILE.getName()))
				continue;
			try {
				ProblemSet set = IO.readObject(f);
				SETS.add(set);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		SETS.forEach(s -> NAMES.add(s.name()));
	}
	
	public static ReadOnlyAudibleSet<ProblemSet> all() {
		return SETS;
	}	
	
	private static void runOnRegisterActions(ProblemSet set) {
		if(onRegisterActions == null)
			return;
		for(Consumer<ProblemSet> action : onRegisterActions)
			action.accept(set);
	}
	
	private static int readUID() {
		int nextID;
		try {
			BufferedReader br = new BufferedReader(new FileReader(Main.SET_UID_FILE));
			String line = br.readLine();
			br.close();
			if(line == null)
				nextID = 0;
			else
				nextID = Integer.parseInt(line);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return nextID;
	}
	
	public static void saveUID() {
		try {
			Files.write(Main.SET_UID_FILE.toPath(), List.of(String.valueOf(NEXT_ID)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private transient StringProperty nameProperty;
	
	private final SetConfiguration config;
	private final int id;
	
	private String name;
	
	/** <p>Creates a new {@link ProblemSet} whose {@link #name() name} is the empty string.</p> */
	public ProblemSet() {
		this("");
	}
	
	public ProblemSet(String name) {
		this(name, new SetConfiguration());
	}
	
	public ProblemSet(String name, SetConfiguration config) {
		id = NEXT_ID++;
		this.name = Objects.requireNonNull(name);
		nameProperty = new SimpleStringProperty(name);
		this.config = config;
	}
	
	public String name() {
		return name;
	}
	
	/** The property returned by this method is read-only. The {@link #setName(String)} method can be used to set the
	 * {@link #name() name} of this {@link ProblemSet}, and the changes will be reflected through this property. */
	public ReadOnlyStringProperty nameProperty() {
		return nameProperty;
	}
	
	public void setName(String newName) {
		if(name().equals(newName))
			return;
		if(NAMES.contains(newName))
			throw new IllegalStateException(String.format("Name already used: %s", newName));
		NAMES.remove(name);
		name = Objects.requireNonNull(newName);
		nameProperty.set(name);
		NAMES.add(name);
	}
	
	public SetConfiguration config() {
		return config;
	}
	
	public int id() {
		return id;
	}
	
	public Deck createDeck() {
		return config().createDeck();
	}
	
	public void addTopics(Collection<Topic> topics) {
		config().addTopics(topics);
	}

	public boolean isRegistered() {
		return SETS.contains(this);
	}
	
	/** Registers this {@link ProblemSet} so that it becomes {@link #isRegistered() registered} and will be in the
	 * {@link Set} returned by {@link #all()}.
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
		File f = new File(Main.SETS_FOLDER, String.format("%s.set", oldName));
		try {
			if(!name().equals(oldName))
				if(f.exists())
					f = IO.renamed(f, String.format("%s.set", name()));
				else
					f = new File(Main.SETS_FOLDER, String.format("%s.set", name()));
			if(!f.exists())
				f.createNewFile();
			IO.writeObject(f, this);
		}
		catch(Exception e) {
			System.err.printf("Could not save set \"%s\" to file.%n", name());
			throw new RuntimeException(e); //TODO better error handling?
		}
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		nameProperty = new SimpleStringProperty(name);
	}
	 
	@Override
	public String toString() {
		return String.format("ProblemSet[id=%d, name=%s]", id(), name());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj || obj instanceof ProblemSet p && id() == p.id();
	}
	
}
