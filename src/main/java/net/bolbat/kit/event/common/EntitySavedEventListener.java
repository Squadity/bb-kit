package net.bolbat.kit.event.common;

import java.io.Serializable;

/**
 * Common, entity updated event listener.
 *
 * @param <Saved>
 * 		event entity type
 * @param <SavedEvent>
 * 		event itself, {@link EntitySavedEvent} any instance
 * @author h3llka
 */
public interface EntitySavedEventListener<Saved extends Serializable, SavedEvent extends EntitySavedEvent<Saved>> {

	/**
	 * General {@link EntitySavedEvent} handling point.<br>
	 * Important: don't forget to use {@link com.google.common.eventbus.Subscribe} annotation inside implementation.<br>
	 * Details: <a></>https://github.com/google/guava/issues/1431</a>.
	 *
	 * @param savedEvent
	 *            event itself
	 */
	void listen(final SavedEvent savedEvent);

}
