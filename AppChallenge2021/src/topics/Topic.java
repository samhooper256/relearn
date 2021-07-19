/**
 * 
 */
package topics;

import java.io.Serializable;
import java.util.List;

import base.*;
import base.problems.Problem;
import javafx.beans.property.*;
import topics.settings.TopicSetting;

/**
 * <p>Every implementing concrete class should have a {@code public static final} field named {@code FACTORY} that has
 * that is the {@link TopicFactory} for the {@code Topic}.</p>
 * 
 * <p><b>Classes should not implement {@code Topic} directly. Instead, they should extend {@link AbstractTopic}.
 * </b></p>
 * 
 * @author Sam Hooper
 *
 */
public interface Topic extends Named, Serializable {
	
	List<TopicSetting> settings();
	
	IntegerProperty countProperty();
	
	int count();
	
	void setCount(int newCount);
	
	Problem generate();
	
	/**
	 * Returns {@code true} iff {@code this} and {@code o} are both {@link Topic Topics} with the same {@link #name()}.
	 */
	@Override
	boolean equals(Object o);
	
}
