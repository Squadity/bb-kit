package net.bolbat.kit.event.common;

import java.io.Serializable;

/**
 * Common, entity deleted event listener.
 *
 * @param <Deleted>
 *            event entity type
 * @param <DeletedEvent>
 *            event itself, {@link EntityDeletedEvent} any instance
 * @author h3llka
 */
public interface EntityDeletedEventListener<Deleted extends Serializable, DeletedEvent extends EntityDeletedEvent<Deleted>> {

	/**
	 * General {@link DeletedEvent} handling point.<br>
	 * Important: don't forget to use {@link com.google.common.eventbus.Subscribe} annotation inside implementation.<br>
	 * Details: <a></>https://github.com/google/guava/issues/1431</a>.
	 *
	 * @param deletedEvent
	 *            event itself
	 */
	void listen(final DeletedEvent deletedEvent);

}
