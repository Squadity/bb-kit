package net.bolbat.kit.vo;

import java.io.Serializable;

/**
 * {@link EntityVO} filtering settings.
 * 
 * @author Alexandr Bolbat
 */
public class EntityVOFiltering implements Serializable {

	/**
	 * Generated SerialVersionUID.
	 */
	private static final long serialVersionUID = 8969954001912149389L;

	/**
	 * Accounts creation timestamp.
	 */
	private Long created;

	/**
	 * Accounts created before timestamp.
	 */
	private Long createdBefore;

	/**
	 * Accounts created after timestamp.
	 */
	private Long createdAfter;

	/**
	 * Account update timestamp.
	 */
	private Long updated;

	/**
	 * Accounts updated before timestamp.
	 */
	private Long updatedBefore;

	/**
	 * Accounts updated after timestamp.
	 */
	private Long updatedAfter;

	public Long getCreated() {
		return created;
	}

	public void setCreated(final Long aCreated) {
		this.created = aCreated;
	}

	public Long getCreatedBefore() {
		return createdBefore;
	}

	public void setCreatedBefore(final Long aCreatedBefore) {
		this.createdBefore = aCreatedBefore;
	}

	public Long getCreatedAfter() {
		return createdAfter;
	}

	public void setCreatedAfter(final Long aCreatedAfter) {
		this.createdAfter = aCreatedAfter;
	}

	public Long getUpdated() {
		return updated;
	}

	public void setUpdated(final Long aUpdated) {
		this.updated = aUpdated;
	}

	public Long getUpdatedBefore() {
		return updatedBefore;
	}

	public void setUpdatedBefore(final Long aUpdatedBefore) {
		this.updatedBefore = aUpdatedBefore;
	}

	public Long getUpdatedAfter() {
		return updatedAfter;
	}

	public void setUpdatedAfter(final Long aUpdatedAfter) {
		this.updatedAfter = aUpdatedAfter;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(", created=").append(created);
		builder.append(", createdBefore=").append(createdBefore);
		builder.append(", createdAfter=").append(createdAfter);
		builder.append(", updated=").append(updated);
		builder.append(", updatedBefore=").append(updatedBefore);
		builder.append(", updatedAfter=").append(updatedAfter);
		return builder.toString();
	}

}
