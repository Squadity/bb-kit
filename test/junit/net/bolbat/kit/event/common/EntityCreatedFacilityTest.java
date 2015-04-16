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
public class EntityCreatedFacilityTest {

	public static final String TEST_CHANNEL_NAME = "CreatedEventTest-TestEntityCreatedEvent";

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
		EventBusManager.getEventBus(TEST_CHANNEL_NAME).post(new TestEntityCreatedEvent(testEntity));
		// sending other event!
		OtherEntity otherEntity = new OtherEntity(UUID.randomUUID().toString());
		EventBusManager.getEventBus(TEST_CHANNEL_NAME).post(new OtherEntityCreatedEvent(otherEntity));
		// checking expectations
		Assert.assertEquals("Should contain 1 events", 1, receiver.getReceivedEventsAmount());
		Assert.assertEquals("Last received object is wrong", testEntity, receiver.getLastReceived());
		Assert.assertEquals("Should contain 1 events", 1, otherReceiver.getReceivedEventsAmount());
		Assert.assertEquals("Last received object is wrong", otherEntity, otherReceiver.getLastReceived());

		// TEAR down
		EventBusManager.getEventBus(TEST_CHANNEL_NAME).unregister(receiver);
		EventBusManager.getEventBus(TEST_CHANNEL_NAME).unregister(otherReceiver);
	}

	/**
	 * Test event receiver.
	 */
	public static class TestEventReceiver implements EntityCreatedEventListener<TestEntity, TestEntityCreatedEvent> {
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
		public void listen(TestEntityCreatedEvent createdEvent) {
			if (createdEvent == null)
				throw new IllegalArgumentException("createdEvent is null");

			// temporary debug
			System.out.println("TestEventReceiver: " + createdEvent);

			eventCounter.incrementAndGet();
			lastReceived = createdEvent.getEntity();
		}
	}

	/**
	 * Other event receiver.
	 */
	public static class OtherEventReceiver implements EntityCreatedEventListener<OtherEntity, OtherEntityCreatedEvent> {
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
		public void listen(OtherEntityCreatedEvent createdEvent) {
			if (createdEvent == null)
				throw new IllegalArgumentException("createdEvent is null");

			// temporary debug
			System.out.println("OtherEventReceiver: " + createdEvent);

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
		private static final long serialVersionUID = -9015460030828865972L;
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
	public static class TestEntityCreatedEvent extends EntityCreatedEvent<TestEntity> {
		/**
		 * Basic serial version UID.
		 */
		private static final long serialVersionUID = -3086867545310710268L;

		/**
		 * Constructor.
		 *
		 * @param aEntity
		 *            - created entity itself
		 */
		protected TestEntityCreatedEvent(final TestEntity aEntity) {
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
		private static final long serialVersionUID = -5593262026440628865L;

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
	public static class OtherEntityCreatedEvent extends EntityCreatedEvent<OtherEntity> {
		/**
		 * Basic serial version UID.
		 */
		private static final long serialVersionUID = 6354036116056196740L;

		/**
		 * Constructor.
		 *
		 * @param aEntity
		 *            - created entity itself
		 */
		protected OtherEntityCreatedEvent(final OtherEntity aEntity) {
			super(aEntity);
		}
	}

}
