package net.bolbat.kit.event.common;

import java.io.Serializable;

/**
 * Common, entity created event listener.
 *
 * @param <Created>
 *            event entity type
 * @param <CreatedEvent>
 *            event itself, {@link EntityCreatedEvent} any instance
 * @author h3llka
 */
public interface EntityCreatedEventListener<Created extends Serializable, CreatedEvent extends EntityCreatedEvent<Created>> {

	/**
	 * General {@link CreatedEvent} handling point.<br>
	 * Important: don't forget to use {@link com.google.common.eventbus.Subscribe} annotation inside implementation.<br>
	 * Details: <a></>https://github.com/google/guava/issues/1431</a>.
	 *
	 * @param createdEvent
	 *            event itself
	 */
	void listen(final CreatedEvent createdEvent);

}
