package net.bolbat.kit.event.common;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.eventbus.Subscribe;
import net.bolbat.kit.event.guava.EventBusManager;
import org.junit.Assert;
import org.junit.Test;

/**
 * Update event check point.
 *
 * @author h3llka
 */
public class EntitySavedFacilityTest {

	public static final String TEST_CHANNEL_NAME = "SavedEventTest-TestEntitySavedEvent";

	@Test
	public void createdEventTest() {
		// create & register receiver!
		TestEventReceiver receiver = new TestEventReceiver();
		// create & register other receiver
		OtherEventReceiver otherReceiver = new OtherEventReceiver();
		// defaults check!
		Assert.assertEquals("Should not contains any events", 0, receiver.getReceivedEventsAmount());
		Assert.assertEquals("Should not contains any events", 0, otherReceiver.getReceivedEventsAmount());
		// Sending some event!
		String identifier = UUID.randomUUID().toString();
		TestEntity testEntity = new TestEntity(identifier);
		TestEntity testEntity2 = new TestEntity(identifier);
		testEntity2.setModifiable("Saved!!!");
		EventBusManager.getEventBus(TEST_CHANNEL_NAME).post(new TestEntitySavedEvent(testEntity, testEntity2));
		// sending other event!
		String identifier2 = UUID.randomUUID().toString();
		OtherEntity otherEntity = new OtherEntity(identifier2);
		OtherEntity otherEntity2 = new OtherEntity(identifier2);
		otherEntity2.setModifiable("Saved");
		EventBusManager.getEventBus(TEST_CHANNEL_NAME).post(new OtherEntitySavedEvent(otherEntity, otherEntity2));
		// checking expectations
		Assert.assertEquals("Should contains any events", 1, receiver.getReceivedEventsAmount());
		Assert.assertEquals("Should not contains any events", 1, otherReceiver.getReceivedEventsAmount());

		// Tear down
		// TEAR down
		EventBusManager.getEventBus(TEST_CHANNEL_NAME).unregister(receiver);
		EventBusManager.getEventBus(TEST_CHANNEL_NAME).unregister(otherReceiver);
	}

	/**
	 * Test event receiver.
	 */
	public static class TestEventReceiver implements EntitySavedEventListener<TestEntity, TestEntitySavedEvent> {
		/**
		 * Internal counter.
		 */
		private final AtomicInteger eventCounter = new AtomicInteger(0);

		public TestEventReceiver() {
			EventBusManager.getEventBus(TEST_CHANNEL_NAME).register(this);
		}

		/**
		 * Return amount of created events.
		 *
		 * @return counter value
		 */
		public int getReceivedEventsAmount() {
			return eventCounter.get();
		}

		@Override
		@Subscribe
		public void listen(TestEntitySavedEvent updatedEvent) {
			if (updatedEvent == null)
				throw new IllegalArgumentException("createdEvent is null");
			eventCounter.incrementAndGet();
		}
	}

	/**
	 * Other event receiver.
	 */
	public static class OtherEventReceiver implements EntitySavedEventListener<OtherEntity, OtherEntitySavedEvent> {
		/**
		 * Internal counter.
		 */
		private final AtomicInteger eventCounter = new AtomicInteger(0);

		public OtherEventReceiver() {
			EventBusManager.getEventBus(TEST_CHANNEL_NAME).register(this);
		}

		/**
		 * Return amount of created events.
		 *
		 * @return counter value
		 */
		public int getReceivedEventsAmount() {
			return eventCounter.get();
		}

		@Override
		@Subscribe
		public void listen(OtherEntitySavedEvent updatedEvent) {
			if (updatedEvent == null)
				throw new IllegalArgumentException("updatedEvent is null");
			eventCounter.incrementAndGet();
		}
	}

	/**
	 * Common - test entity.
	 */
	public static class TestEntity implements Serializable {
		/**
		 * Basic serial version UID.
		 */
		private static final long serialVersionUID = -7025622064543716270L;

		/**
		 * Some common id.
		 */
		private String identifier;
		/**
		 * Modifiable value.
		 */
		private String modifiable;

		/**
		 * Constructor.
		 *
		 * @param identifier
		 * 		id
		 */
		public TestEntity(final String identifier) {
			this.identifier = identifier;
		}

		public String getModifiable() {
			return modifiable;
		}

		public void setModifiable(String modifiable) {
			this.modifiable = modifiable;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;

			TestEntity that = (TestEntity) o;

			if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null)
				return false;

			return true;
		}

		@Override
		public int hashCode() {
			return identifier != null ? identifier.hashCode() : 0;
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
			sb.append("[identifier=").append(identifier);
			sb.append(']');
			return sb.toString();
		}
	}

	/**
	 * Entity created event.
	 */
	public static class TestEntitySavedEvent extends EntitySavedEvent<TestEntity> {
		/**
		 * Basic serial version UID.
		 */
		private static final long serialVersionUID = -5278486650734097676L;

		/**
		 * Constructor.
		 *
		 * @param before
		 * 		- before update
		 * @param current
		 * 		- after update
		 */
		protected TestEntitySavedEvent(final TestEntity before, final TestEntity current) {
			super(before, current);
		}
	}

	/**
	 * Common - other entity.
	 */
	public static class OtherEntity implements Serializable {
		/**
		 * Basic serial version UID.
		 */
		private static final long serialVersionUID = 5163709489832352144L;

		/**
		 * Some common id.
		 */
		private String identifier;

		/**
		 * Modifiable value.
		 */
		private String modifiable;

		/**
		 * Constructor.
		 *
		 * @param identifier
		 * 		id
		 */
		public OtherEntity(final String identifier) {
			this.identifier = identifier;
		}

		public String getModifiable() {
			return modifiable;
		}

		public void setModifiable(String modifiable) {
			this.modifiable = modifiable;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;

			OtherEntity that = (OtherEntity) o;

			if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null)
				return false;

			return true;
		}

		@Override
		public int hashCode() {
			return identifier != null ? identifier.hashCode() : 0;
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
			sb.append("[identifier=").append(identifier);
			sb.append(']');
			return sb.toString();
		}
	}

	/**
	 * Entity created event.
	 */
	public static class OtherEntitySavedEvent extends EntitySavedEvent<OtherEntity> {
		/**
		 * Basic serial version UID.
		 */
		private static final long serialVersionUID = -2855854509927997750L;

		/**
		 * Constructor.
		 *
		 * @param before
		 * 		- before update
		 * @param current
		 * 		- after update
		 */
		protected OtherEntitySavedEvent(final OtherEntity before, final OtherEntity current) {
			super(before, current);
		}
	}

}
