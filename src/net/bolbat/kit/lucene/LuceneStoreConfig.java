package net.bolbat.kit.lucene;

import net.bolbat.kit.config.AbstractConfiguration;
import net.bolbat.utils.test.TestUtils;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;
import org.configureme.annotations.DontConfigure;

/**
 * {@link LuceneStore} configuration.
 * 
 * @author Alexandr Bolbat
 */
@ConfigureMe
public class LuceneStoreConfig extends AbstractConfiguration {

	/**
	 * Generated SerialVersionUID.
	 */
	@DontConfigure
	private static final long serialVersionUID = 858856402007800753L;

	/**
	 * Default configuration name.
	 */
	@DontConfigure
	public static final String DEFAULT_CONFIGURATION_NAME = "kit-lucene-store";

	/**
	 * Default lucene version.
	 */
	@DontConfigure
	public static final Version DEF_VERSION = Version.LUCENE_47;

	/**
	 * Default lucene directory type.
	 */
	@DontConfigure
	public static final DirectoryType DEF_DIRECTORY_TYPE = DirectoryType.RAM;

	/**
	 * Configured version.
	 */
	@Configure
	private Version version = DEF_VERSION;

	/**
	 * Configured directory type.
	 */
	@Configure
	private DirectoryType directoryType = DEF_DIRECTORY_TYPE;

	/**
	 * Configured directory path.
	 */
	@Configure
	private String directoryPath = TestUtils.getTemporaryFolder(LuceneStoreConfig.class) + System.currentTimeMillis();

	public Version getVersion() {
		return version;
	}

	public void setVersion(final Version aVersion) {
		this.version = aVersion;
	}

	public DirectoryType getDirectoryType() {
		return directoryType;
	}

	public void setDirectoryType(final DirectoryType aDirectoryType) {
		this.directoryType = aDirectoryType;
	}

	public String getDirectoryPath() {
		return directoryPath;
	}

	public void setDirectoryPath(final String directoryPath) {
		this.directoryPath = directoryPath;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" [version=").append(version);
		builder.append(", directoryType=").append(directoryType);
		builder.append(", directoryPath=").append(directoryPath);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * {@link Directory} types.
	 * 
	 * @author Alexandr Bolbat
	 */
	public enum DirectoryType {

		/**
		 * {@link RAMDirectory}.
		 */
		RAM,

		/**
		 * {@link FSDirectory}.
		 */
		FS

	}

}
