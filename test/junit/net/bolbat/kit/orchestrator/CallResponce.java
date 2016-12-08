package net.bolbat.kit.orchestrator;

public class CallResponce {

	private State state = State.DEFAULT;

	private final String threadName = Thread.currentThread().getName();

	public State getState() {
		return state;
	}

	public CallResponce setState(final State aState) {
		this.state = aState;
		return this;
	}

	public String getThreadName() {
		return threadName;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("[").append(state).append("|").append(threadName).append("]").toString();
	}

	public enum State {

		UNKNOWN,

		INITIATED,

		EXECUTED;

		public static final State DEFAULT = State.UNKNOWN;

	}

}
