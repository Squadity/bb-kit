package net.bolbat.kit.event.common;

import java.io.Serializable;

/**
 * Common, entity updated event listener.
 *
 * @param <Updated>
 *            event entity type
 * @param <UpdatedEvent>
 *            event itself, {@link EntityUpdatedEvent} any instance
 * @author h3llka
 */
public interface EntityUpdatedEventListener<Updated extends Serializable, UpdatedEvent extends EntityUpdatedEvent<Updated>> {

	/**
	 * General {@link UpdatedEvent} handling point.<br>
	 * Important: don't forget to use {@link com.google.common.eventbus.Subscribe} annotation inside implementation.<br>
	 * Details: <a></>https://github.com/google/guava/issues/1431</a>.
	 *
	 * @param updatedEvent
	 *            event itself
	 */
	void listen(final UpdatedEvent updatedEvent);

}
