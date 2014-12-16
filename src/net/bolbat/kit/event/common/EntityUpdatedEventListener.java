package net.bolbat.kit.event.common;

/**
 * Common, entity updated event listener.
 *
 * @param <UpdatedEvent>
 * 		event itself, {@link EntityUpdatedEvent} any instance.
 * @author h3llka
 */
public interface EntityUpdatedEventListener<UpdatedEvent extends EntityUpdatedEvent> {

	/**
	 * General {@link UpdatedEvent} handling point.
	 * Important : don't forget to use {@link com.google.common.eventbus.Subscribe} annotation inside implementation, cause of
	 * <a></>https://github.com/google/guava/issues/1431</a> - issue.
	 *
	 * @param updatedEvent
	 * 		event itself
	 */
	void listen(final UpdatedEvent updatedEvent);
}
