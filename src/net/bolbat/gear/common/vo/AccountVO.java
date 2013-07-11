package net.bolbat.gear.common.vo;

/**
 * Account data.
 * 
 * @author Alexandr Bolbat
 */
public class AccountVO extends AbstractEntityVO {

	/**
	 * Generated SerialVersionUID.
	 */
	private static final long serialVersionUID = 4957254145120125765L;

	/**
	 * Account identifier.
	 */
	private AccountId id = AccountId.EMPTY_ID;

	/**
	 * Default constructor.
	 */
	public AccountVO() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param aId
	 *            account identifier
	 */
	public AccountVO(final AccountId aId) {
		setId(aId);
	}

	public AccountId getId() {
		return id;
	}

	public void setId(final AccountId aId) {
		this.id = aId == null ? AccountId.EMPTY_ID : aId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof AccountVO))
			return false;
		AccountVO other = (AccountVO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" [id=").append(id);
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}

	@Override
	protected AccountVO clone() {
		AccountVO result = AccountVO.class.cast(super.clone());
		result.setId(id.clone());
		return result;
	}

}
