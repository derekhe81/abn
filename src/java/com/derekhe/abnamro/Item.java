package com.derekhe.abnamro;

/**
 * This class represent a item of the transaction record like client type,
 * client number.
 * 
 * @author derekhe
 *
 */
class Item {
	// item name
	private String name;

	/**
	 * The begin index of this item in a transaction record
	 */
	private int beginIndex;

	/**
	 * The end index of this item in a transaction record
	 */
	private int endIndex;

	/**
	 * Operator for this item, value could be +,-,* and /. some values derive from
	 * item calculation like Total_Transaction_Amount.
	 */
	private String operator;

	public Item() {
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the beginIndex
	 */
	public int getBeginIndex() {
		return beginIndex;
	}

	/**
	 * @param beginIndex
	 *            the beginIndex to set
	 */
	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	/**
	 * @return the endIndex
	 */
	public int getEndIndex() {
		return endIndex;
	}

	/**
	 * @param endIndex
	 *            the endIndex to set
	 */
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator
	 *            the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
}
