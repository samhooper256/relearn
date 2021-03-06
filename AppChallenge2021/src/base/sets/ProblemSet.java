/**
 * 
 */
package base.sets;

import java.io.*;
import java.util.*;

import base.Main;
import base.stats.Data;
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
	
	private static final long serialVersionUID = 415601596062566192L;
	private static final AudibleSet<ProblemSet> SETS = AudibleSet.create(LinkedHashSet::new);
	private static final Set<String> NAMES = new HashSet<>();
	private static int NEXT_ID;
	
	private static boolean loaded = false;
	
	public static synchronized void load() {
		if(loaded)
			return; //don't load if we've already done it earlier.
		loaded = true;
		try {
			if(IO.isEmpty(Main.SETS_FILE)) {
				NEXT_ID = 0;
			}
			else {
				ProblemSetData data = IO.readObject(Main.SETS_FILE);
				SETS.addAll(data.sets());
				NEXT_ID = data.nextUID();
			}
		}
		catch(Exception e) {
			e.printStackTrace(); //TODO better error handling?
		}
		SETS.forEach(s -> NAMES.add(s.name()));
	}
	
	public static synchronized void save() {
		ProblemSetData data = new ProblemSetData(new LinkedHashSet<>(SETS), NEXT_ID);
		try {
			IO.writeObject(Main.SETS_FILE, data);
		} catch (IOException e) {
			e.printStackTrace(); //TODO better error handling?
		}
	}
	
	public static ReadOnlyAudibleSet<ProblemSet> all() {
		return SETS;
	}	
	
	/** Un-{@link #isRegistered() registers} the given {@link ProblemSet}, removing it from {@link #all()} and making
	 * its name no long {@link #isInUse(String) in use}. This method also deletes all of the stats for the given set
	 * via {@link base.stats.Data#removeStatsFor(ProblemSet)}.*/
	public static void remove(ProblemSet set) {
		NAMES.remove(set.name());
		SETS.remove(set);
		Data.removeStatsFor(set);
	}
	
	/** Returns {@code true} iff a {@link ProblemSet} with the given name is already
	 * {@link #isRegistered() registered}.*/
	public static boolean isInUse(String name) {
		return NAMES.contains(name);
	}

	private transient StringProperty nameProperty;
	
	private final SetConfiguration config;
	private final int id;
	
	private String name;
	private int practiceCount;
	
	/** <p>Creates a new {@link ProblemSet} whose {@link #name() name} is the empty string.</p> */
	public ProblemSet() {
		this("");
	}
	
	public ProblemSet(String name) {
		this(name, new SetConfiguration());
	}
	
	private ProblemSet(String name, SetConfiguration config) {
		id = NEXT_ID++;
		this.name = Objects.requireNonNull(name);
		nameProperty = new SimpleStringProperty(name);
		this.config = config;
		practiceCount = 0;
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
	
	public void addTopics(Iterable<Topic> topics) {
		config().addTopics(topics);
	}

	public boolean isRegistered() {
		return SETS.contains(this);
	}
	
	public ReadOnlyAudibleSet<Topic> topics() {
		return config().topics();
	}
	
	/** Registers this {@link ProblemSet} so that it becomes {@link #isRegistered() registered} and will be in the
	 * {@link Set} returned by {@link #all()}.
	 * @throws IllegalStateException if this {@link ProblemSet} is already {@link #isRegistered() registered}.*/
	public void register() {
		if(isRegistered())
			throw new IllegalStateException("This ProblemSet is already registered");
		SETS.add(this);
	}

	/** Increments {@link #practiceCount()} by {@code 1}.*/
	public void addPractice() {
		practiceCount++;
	}
	
	public int practiceCount() {
		return practiceCount;
	}
	
	public void clearPractices() {
		practiceCount = 0;
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
		return this == obj || obj instanceof ProblemSet && id() == ((ProblemSet) obj).id();
	}
	
}
