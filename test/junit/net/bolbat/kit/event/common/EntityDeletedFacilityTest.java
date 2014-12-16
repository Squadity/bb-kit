package net.bolbat.kit.event.common;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import net.bolbat.kit.event.guava.EventBusManager;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.eventbus.Subscribe;

/**
 * Created event check point.
 *
 * @author h3llka
 */
public class EntityDeletedFacilityTest {

	public static final String TEST_CHANNEL_NAME = "DeletedEventTest-TestEntityDeletedEvent";

	@Test
	public void createdEventTest() {
		// create & register receiver!
		TestEventReceiver receiver = new TestEventReceiver();
		// create & register other receiver
		OtherEventReceiver otherReceiver = new OtherEventReceiver();
		// defaults check!
		Assert.assertEquals("Should not contains any events", 0, receiver.getReceivedEventsAmount());
		Assert.assertNull("NOT NULL", receiver.getLastReceived());
		Assert.assertEquals("Should not contains any events", 0, otherReceiver.getReceivedEventsAmount());
		Assert.assertNull("NOT NULL", otherReceiver.getLastReceived());
		// Sending some event!
		TestEntity testEntity = new TestEntity(UUID.randomUUID().toString());
		EventBusManager.getEventBus(TEST_CHANNEL_NAME).post(new TestEntityDeletedEvent(testEntity));
		// sending other event!
		OtherEntity otherEntity = new OtherEntity(UUID.randomUUID().toString());
		EventBusManager.getEventBus(TEST_CHANNEL_NAME).post(new OtherEntityDeletedEvent(otherEntity));
		// checking expectations
		Assert.assertEquals("Should contain 1  evens", 1, receiver.getReceivedEventsAmount());
		Assert.assertEquals("Last received object is wrong", testEntity, receiver.getLastReceived());
		Assert.assertEquals("Should contain 1  evens", 1, otherReceiver.getReceivedEventsAmount());
		Assert.assertEquals("Last received object is wrong", otherEntity, otherReceiver.getLastReceived());

		// TEAR down
		EventBusManager.getEventBus(TEST_CHANNEL_NAME).unregister(receiver);
		EventBusManager.getEventBus(TEST_CHANNEL_NAME).unregister(otherReceiver);
	}

	/**
	 * Test event receiver.
	 */
	public static class TestEventReceiver implements EntityDeletedEventListener<TestEntity, TestEntityDeletedEvent> {
		/**
		 * Internal counter.
		 */
		private final AtomicInteger eventCounter = new AtomicInteger(0);
		/**
		 * Last received bean.
		 */
		private TestEntity lastReceived;

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

		public TestEntity getLastReceived() {
			return lastReceived;
		}

		@Override
		@Subscribe
		public void listen(TestEntityDeletedEvent createdEvent) {
			if (createdEvent == null)
				throw new IllegalArgumentException("createdEvent is null");
			eventCounter.incrementAndGet();
			lastReceived = createdEvent.getEntity();
		}
	}

	/**
	 * Other event receiver.
	 */
	public static class OtherEventReceiver implements EntityDeletedEventListener<OtherEntity, OtherEntityDeletedEvent> {
		/**
		 * Internal counter.
		 */
		private final AtomicInteger eventCounter = new AtomicInteger(0);
		/**
		 * Last received bean.
		 */
		private OtherEntity lastReceived;

		public OtherEventReceiver() {
			EventBusManager.getEventBus(TEST_CHANNEL_NAME).register(this);
		}

		@Override
		@Subscribe
		public void listen(OtherEntityDeletedEvent createdEvent) {
			if (createdEvent == null)
				throw new IllegalArgumentException("createdEvent is null");
			eventCounter.incrementAndGet();
			lastReceived = createdEvent.getEntity();
		}

		/**
		 * Return amount of created events.
		 *
		 * @return counter value
		 */
		public int getReceivedEventsAmount() {
			return eventCounter.get();
		}

		public OtherEntity getLastReceived() {
			return lastReceived;
		}

	}

	/**
	 * Common - test entity.
	 */
	public static class TestEntity implements Serializable {
		/**
		 * Basic serial version UID.
		 */
		private static final long serialVersionUID = -7982363810733839949L;

		/**
		 * Some common id.
		 */
		private String identifier;

		/**
		 * Constructor.
		 *
		 * @param identifier
		 *            id
		 */
		public TestEntity(final String identifier) {
			this.identifier = identifier;
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
	public static class TestEntityDeletedEvent extends EntityDeletedEvent<TestEntity> {
		/**
		 * Basic serial version UID.
		 */
		private static final long serialVersionUID = 3435149787026646715L;

		/**
		 * Constructor.
		 *
		 * @param aEntity
		 *            - created entity itself
		 */
		protected TestEntityDeletedEvent(final TestEntity aEntity) {
			super(aEntity);
		}
	}

	/**
	 * Common - other entity.
	 */
	public static class OtherEntity implements Serializable {
		/**
		 * Basic serial version UID.
		 */
		private static final long serialVersionUID = -2457494299818432703L;

		/**
		 * Some common id.
		 */
		private String identifier;

		/**
		 * Constructor.
		 *
		 * @param identifier
		 *            id
		 */
		public OtherEntity(final String identifier) {
			this.identifier = identifier;
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
	public static class OtherEntityDeletedEvent extends EntityDeletedEvent<OtherEntity> {
		/**
		 * Basic serial version UID.
		 */
		private static final long serialVersionUID = 1633965257057333945L;

		/**
		 * Constructor.
		 *
		 * @param aEntity
		 *            - created entity itself
		 */
		protected OtherEntityDeletedEvent(final OtherEntity aEntity) {
			super(aEntity);
		}
	}

}
