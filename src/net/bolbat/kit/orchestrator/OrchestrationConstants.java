package net.bolbat.kit.orchestrator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * {@link Orchestrator} constants.
 * 
 * @author Alexandr Bolbat
 */
public final class OrchestrationConstants {

	/**
	 * Default for: execution time limit, zero (unlimited) by default.
	 */
	public static final long TIME_LIMIT = 0;

	/**
	 * Default for: execution time limit unit.
	 */
	public static final TimeUnit TIME_LIMIT_UNIT = TimeUnit.MILLISECONDS;

	/**
	 * Default for: concurrent executions limit, zero (unlimited) by default.
	 */
	public static final int CONCURRENT_LIMIT = 0;

	/**
	 * Default for: {@link ExecutorService} core pool size, zero by default.
	 */
	public static final int POOL_CORE_SIZE = 0;

	/**
	 * Default for: {@link ExecutorService} max pool size, zero (unlimited) by default.
	 */
	public static final int POOL_MAX_SIZE = 0;

	/**
	 * Default for: {@link ExecutorService} pool queue size, zero (unlimited) by default.
	 */
	public static final int POOL_QUEUE_SIZE = 0;

	/**
	 * Default for: {@link ExecutorService} pool keep alive time.<br>
	 * When the number of threads is greater than the core, this is the maximum time that excess idle threads will wait for new tasks before terminating.
	 */
	public static final long POOL_KEEP_ALIVE = 60L;

	/**
	 * Default for: {@link ExecutorService} pool keep alive time unit.
	 */
	public static final TimeUnit POOL_KEEP_ALIVE_UNIT = TimeUnit.SECONDS;

	/**
	 * Default for: {@link ExecutorService} thread name format.<br>
	 * Format arguments:<br>
	 * - execution identifier;<br>
	 * - execution name;<br>
	 * - thread number.<br>
	 * Example: Orchestrator[27368321-729757985]-n[MailServiceImpl.sendMail(open.source.MailMessage)]-thread[1].
	 */
	public static final String THREAD_NAME_FORMAT = "Orchestrator[%1$s]-n[%2$s]-thread[%3$d]";

	/**
	 * Error message template.
	 */
	public static final String ERR_MSG_TEMPLATE = new StringBuilder() //
			.append("\n\t").append("Execution[%s, %s]") //
			.append("\n\t\t").append("limits[%s]") //
			.append("\n\t\t").append("executor[%s]") //
			.append("\n\t\t").append("message[%s]").toString();

	/**
	 * String with: unlimited.
	 */
	public static final String UNLIMITED = "unlimited";

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private OrchestrationConstants() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

}
