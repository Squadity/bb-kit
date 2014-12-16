package net.bolbat.kit.event.common;


/**
 * Common, entity created event listener.
 *
 * @param <CreatedEvent>
 * 		event itself, {@link EntityCreatedEvent} any instance.
 * @author h3llka
 */
public interface EntityCreatedEventListener<CreatedEvent extends EntityCreatedEvent> {

	/**
	 * General {@link CreatedEvent} handling point.
	 * Important : don't forget to use {@link com.google.common.eventbus.Subscribe} annotation inside implementation, cause of
	 * <a></>https://github.com/google/guava/issues/1431</a> - issue.
	 *
	 * @param createdEvent
	 * 		event itself
	 */
	void listen(final CreatedEvent createdEvent);

}
