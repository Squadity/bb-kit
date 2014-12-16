package net.bolbat.kit.event.common;


/**
 * Common, entity deleted event listener.
 *
 * @param <DeletedEvent>
 * 		event itself, {@link EntityDeletedEvent} any instance.
 * @author h3llka
 */
public interface EntityDeletedEventListener<DeletedEvent extends EntityDeletedEvent> {

	/**
	 * General {@link DeletedEvent} handling point.
	 * Important : don't forget to use {@link com.google.common.eventbus.Subscribe} annotation inside implementation, cause of
	 * <a></>https://github.com/google/guava/issues/1431</a> - issue.
	 *
	 * @param deletedEvent
	 * 		event itself
	 */
	void listen(final DeletedEvent deletedEvent);

}
