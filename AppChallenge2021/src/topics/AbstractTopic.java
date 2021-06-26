/**
 * 
 */
package topics;

import java.io.*;
import java.util.*;

import javafx.beans.property.*;

/**
 * @author Sam Hooper
 *
 */
abstract class AbstractTopic implements Topic {
	
	private static final long serialVersionUID = -3884983155589210552L;
	
	protected static final int DEFAULT_COUNT = 10;

	private transient SimpleIntegerProperty countProperty;
	
	private List<TopicSetting> settings;
	
	/**
	 * @throws IllegalArgumentException if {@code (count <= 0)}. 
	 */
	public AbstractTopic(int count) {
		if(count <= 0)
			throw new IllegalArgumentException(String.format("count must be positive (was: %d)", count));
		countProperty = new SimpleIntegerProperty(count);
	}
	
	protected void createSettings(TopicSetting... settings) {
		if(this.settings != null)
			throw new IllegalStateException("Settings can only be created once");
		this.settings = List.of(settings);
	}
	
	@Override
	public IntegerProperty countProperty() {
		return countProperty;
	}

	@Override
	public int count() {
		return countProperty.get();
	}
	
	@Override
	public void setCount(int count) {
		countProperty.set(count);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name());
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Topic t && Objects.equals(name(), t.name());
	}

	@Override
	public List<TopicSetting> settings() {
		return settings;
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
	    // default serialization 
	    oos.defaultWriteObject();
	    // write the object
	    oos.writeObject(Integer.valueOf(count()));
	}

	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
	    // default deserialization
	    ois.defaultReadObject();
	    Integer count = (Integer) ois.readObject();
	    countProperty = new SimpleIntegerProperty(count);

	}
	
}
