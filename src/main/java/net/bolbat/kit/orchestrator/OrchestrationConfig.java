package net.bolbat.kit.orchestrator;

import static net.bolbat.utils.lang.StringUtils.isNotEmpty;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.configureme.annotations.AfterConfiguration;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;
import org.configureme.annotations.DontConfigure;

import net.bolbat.kit.config.AbstractConfiguration;
import net.bolbat.kit.config.ConfigurationManager;
import net.bolbat.kit.orchestrator.annotation.Orchestrate;
import net.bolbat.kit.orchestrator.annotation.OrchestrationExecutor;
import net.bolbat.kit.orchestrator.annotation.OrchestrationLimits;
import net.bolbat.kit.orchestrator.annotation.OrchestrationMode;
import net.bolbat.kit.orchestrator.annotation.OrchestrationMode.Mode;
import net.bolbat.kit.orchestrator.impl.callable.CallableFactory;
import net.bolbat.kit.orchestrator.impl.callable.DefaultCallableFactory;
import net.bolbat.kit.orchestrator.impl.executor.DefaultExecutorServiceFactory;
import net.bolbat.kit.orchestrator.impl.executor.ExecutorServiceFactory;
import net.bolbat.utils.annotation.Mark.ToDo;

/**
 * Orchestration configuration.
 * 
 * @author Alexandr Bolbat
 */
@ConfigureMe(allfields = false)
public class OrchestrationConfig extends AbstractConfiguration {

	/**
	 * Generated SerialVersionUID.
	 */
	@DontConfigure
	private static final long serialVersionUID = -1895395871760275588L;

	/**
	 * Configuration source.
	 */
	@DontConfigure
	private Source source = Source.ANNOTATIONS;

	/**
	 * Mode configuration.
	 */
	@Configure
	private ModeConfig modeConfig = new ModeConfig();

	/**
	 * Limits configuration.
	 */
	@Configure
	private LimitsConfig limitsConfig = new LimitsConfig();

	/**
	 * {@link ExecutorService} configuration.
	 */
	@Configure
	private ExecutorConfig executorConfig = new ExecutorConfig();

	public Source getSource() {
		return source;
	}

	private void setSource(final Source aSource) {
		this.source = aSource;
	}

	public ModeConfig getModeConfig() {
		return modeConfig;
	}

	public void setModeConfig(final ModeConfig aModeConfig) {
		this.modeConfig = aModeConfig != null ? aModeConfig : new ModeConfig();
	}

	public LimitsConfig getLimitsConfig() {
		return limitsConfig;
	}

	public void setLimitsConfig(final LimitsConfig aLimitsConfig) {
		this.limitsConfig = aLimitsConfig != null ? aLimitsConfig : new LimitsConfig();
	}

	public ExecutorConfig getExecutorConfig() {
		return executorConfig;
	}

	public void setExecutorConfig(final ExecutorConfig aExecutorConfig) {
		this.executorConfig = aExecutorConfig != null ? aExecutorConfig : new ExecutorConfig();
	}

	/**
	 * Configure.
	 * 
	 * @param orchestrate
	 *            {@link Orchestrate}
	 * @param limits
	 *            {@link OrchestrationLimits}
	 * @param executor
	 *            {@link OrchestrationExecutor}
	 * @return {@link OrchestrationConfig}
	 */
	public static OrchestrationConfig configure(final Orchestrate orchestrate, final OrchestrationLimits limits, final OrchestrationExecutor executor) {
		return configure(orchestrate, null, limits, executor);
	}

	/**
	 * Configure.
	 * 
	 * @param orchestrate
	 *            {@link Orchestrate}
	 * @param mode
	 *            {@link OrchestrationMode}
	 * @param limits
	 *            {@link OrchestrationLimits}
	 * @param executor
	 *            {@link OrchestrationExecutor}
	 * @return {@link OrchestrationConfig}
	 */
	public static OrchestrationConfig configure( //
			final Orchestrate orchestrate, //
			final OrchestrationMode mode, //
			final OrchestrationLimits limits, //
			final OrchestrationExecutor executor) {
		OrchestrationConfig config = null;
		if (orchestrate != null && isNotEmpty(orchestrate.configName())) {
			config = ConfigurationManager.getInstanceForConf(OrchestrationConfig.class, orchestrate.configName());
		} else {
			config = new OrchestrationConfig();
			config.configure(mode, limits, executor);
		}
		return config;
	}

	/**
	 * Configure from annotations values.
	 * 
	 * @param mode
	 *            {@link OrchestrationMode}
	 * @param limits
	 *            {@link OrchestrationLimits}
	 * @param executor
	 *            {@link OrchestrationExecutor}
	 */
	public void configure(final OrchestrationMode mode, final OrchestrationLimits limits, final OrchestrationExecutor executor) {
		setSource(Source.ANNOTATIONS);
		if (mode != null) {
			getModeConfig().setMode(mode.value());
		}
		if (limits != null) {
			getLimitsConfig().setTime(limits.time());
			getLimitsConfig().setTimeUnit(limits.timeUnit());
			getLimitsConfig().setConcurrent(limits.concurrent());
		}
		if (executor != null) {
			getExecutorConfig().setFactory(executor.factory());
			getExecutorConfig().setCallableFactory(executor.callableFactory());
			getExecutorConfig().setCoreSize(executor.coreSize());
			getExecutorConfig().setMaxSize(executor.maxSize());
			getExecutorConfig().setQueueSize(executor.queueSize());
			getExecutorConfig().setKeepAlive(executor.keepAlive());
			getExecutorConfig().setKeepAliveUnit(executor.keepAliveUnit());
			getExecutorConfig().setNameFormat(executor.nameFormat());
		}
	}

	/**
	 * Executed after each {@code ConfigureMe} based configuration.
	 */
	@AfterConfiguration
	public void afterConfiguration() {
		setSource(Source.CONFIGURE_ME);
		fireConfigurationChanged();
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" [source=").append(source);
		builder.append(", modeConfig=[").append(getModeConfig()).append("]");
		builder.append(", limitsConfig=[").append(limitsConfig).append("]");
		builder.append(", executorConfig=[").append(executorConfig).append("]");
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Orchestration mode configuration.
	 * 
	 * @author Alexandr Bolbat
	 */
	@ConfigureMe(allfields = false)
	public static class ModeConfig implements Serializable {

		/**
		 * Generated SerialVersionUID.
		 */
		private static final long serialVersionUID = -4263680889324891991L;

		/**
		 * Execution mode.
		 */
		@Configure
		private Mode mode = Mode.DEFAULT;

		public Mode getMode() {
			return mode;
		}

		public void setMode(final Mode aMode) {
			this.mode = aMode;
		}

		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder();
			builder.append("mode=").append(mode);
			return builder.toString();
		}

	}

	/**
	 * Orchestration limits configuration.
	 * 
	 * @author Alexandr Bolbat
	 */
	@ConfigureMe(allfields = false)
	public static class LimitsConfig implements Serializable {

		/**
		 * Generated SerialVersionUID.
		 */
		@DontConfigure
		private static final long serialVersionUID = 1082218846823612100L;

		/**
		 * Execution time limit.
		 */
		@Configure
		private int time = OrchestrationConstants.TIME_LIMIT;

		/**
		 * Execution time limit unit.
		 */
		@Configure
		private TimeUnit timeUnit = OrchestrationConstants.TIME_LIMIT_UNIT;

		/**
		 * Concurrent executions limit.
		 */
		@Configure
		private int concurrent = OrchestrationConstants.CONCURRENT_LIMIT;

		public int getTime() {
			return time;
		}

		public void setTime(int time) {
			this.time = time;
		}

		public TimeUnit getTimeUnit() {
			return timeUnit;
		}

		public void setTimeUnit(TimeUnit timeUnit) {
			this.timeUnit = timeUnit;
		}

		public int getConcurrent() {
			return concurrent;
		}

		public void setConcurrent(int concurrent) {
			this.concurrent = concurrent;
		}

		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder();
			builder.append("time=").append(time == OrchestrationConstants.TIME_LIMIT ? OrchestrationConstants.UNLIMITED : time);
			builder.append(", timeUnit=").append(timeUnit);
			builder.append(", concurrent=").append(concurrent == OrchestrationConstants.CONCURRENT_LIMIT ? OrchestrationConstants.UNLIMITED : concurrent);
			return builder.toString();
		}

	}

	/**
	 * Orchestration {@link ExecutorService} configuration.
	 * 
	 * @author Alexandr Bolbat
	 */
	@ToDo("Implement support for custom factory configuration from file")
	@ConfigureMe(allfields = false)
	public static class ExecutorConfig implements Serializable {

		/**
		 * Generated SerialVersionUID.
		 */
		@DontConfigure
		private static final long serialVersionUID = 9058169478068158733L;

		/**
		 * {@link ExecutorService} factory class.
		 */
		@DontConfigure
		private Class<? extends ExecutorServiceFactory> factory = DefaultExecutorServiceFactory.class;

		/**
		 * {@link Callable} factory class.
		 */
		@DontConfigure
		private Class<? extends CallableFactory> callableFactory = DefaultCallableFactory.class;

		/**
		 * Core pool size.
		 */
		@Configure
		private int coreSize = OrchestrationConstants.POOL_CORE_SIZE;

		/**
		 * Max pool size.
		 */
		@Configure
		private int maxSize = OrchestrationConstants.POOL_MAX_SIZE;

		/**
		 * Pool queue size.
		 */
		@Configure
		private int queueSize = OrchestrationConstants.POOL_QUEUE_SIZE;

		/**
		 * Pool keep alive time.<br>
		 * When the number of threads is greater than the core, this is the maximum time that excess idle threads will wait for new tasks before terminating.
		 */
		@Configure
		private long keepAlive = OrchestrationConstants.POOL_KEEP_ALIVE;

		/**
		 * Pool keep alive time unit.
		 */
		@Configure
		private TimeUnit keepAliveUnit = OrchestrationConstants.POOL_KEEP_ALIVE_UNIT;

		/**
		 * Thread name format.
		 */
		@Configure
		private String nameFormat = OrchestrationConstants.THREAD_NAME_FORMAT;

		public Class<? extends ExecutorServiceFactory> getFactory() {
			return factory;
		}

		public void setFactory(final Class<? extends ExecutorServiceFactory> aFactory) {
			this.factory = aFactory;
		}

		public Class<? extends CallableFactory> getCallableFactory() {
			return callableFactory;
		}

		public void setCallableFactory(final Class<? extends CallableFactory> aCallableFactory) {
			this.callableFactory = aCallableFactory;
		}

		public int getCoreSize() {
			return coreSize;
		}

		public void setCoreSize(int coreSize) {
			this.coreSize = coreSize;
		}

		public int getMaxSize() {
			return maxSize;
		}

		public void setMaxSize(int maxSize) {
			this.maxSize = maxSize;
		}

		public int getQueueSize() {
			return queueSize;
		}

		public void setQueueSize(int queueSize) {
			this.queueSize = queueSize;
		}

		public long getKeepAlive() {
			return keepAlive;
		}

		public void setKeepAlive(long keepAlive) {
			this.keepAlive = keepAlive;
		}

		public TimeUnit getKeepAliveUnit() {
			return keepAliveUnit;
		}

		public void setKeepAliveUnit(TimeUnit keepAliveUnit) {
			this.keepAliveUnit = keepAliveUnit;
		}

		public String getNameFormat() {
			return nameFormat;
		}

		public void setNameFormat(String nameFormat) {
			this.nameFormat = nameFormat;
		}

		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder();
			builder.append("factory=").append(factory);
			builder.append(", callableFactory=").append(callableFactory);
			builder.append(", coreSize=").append(coreSize);
			builder.append(", maxSize=").append(maxSize == OrchestrationConstants.POOL_MAX_SIZE ? OrchestrationConstants.UNLIMITED : maxSize);
			builder.append(", queueSize=").append(queueSize == OrchestrationConstants.POOL_QUEUE_SIZE ? OrchestrationConstants.UNLIMITED : queueSize);
			builder.append(", keepAlive=").append(keepAlive);
			builder.append(", keepAliveUnit=").append(keepAliveUnit);
			builder.append(", nameFormat=").append(nameFormat);
			return builder.toString();
		}

	}

	/**
	 * Configuration sources.
	 * 
	 * @author Alexandr Bolbat
	 */
	public enum Source {

		/**
		 * Configured from annotations.
		 */
		ANNOTATIONS,

		/**
		 * Configured by {@code ConfigureMe}.
		 */
		CONFIGURE_ME;

	}

}
