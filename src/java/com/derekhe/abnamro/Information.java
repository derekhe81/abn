package com.derekhe.abnamro;

import java.util.ArrayList;
import java.util.List;

/**
 * Information represents a column in transaction report like Client Info.F
 * 
 * 
 * @author derekhe
 *
 */
class Information {

	/**
	 * Information type.e.g: client info, product info
	 */
	private String type;

	/**
	 * information mode. Value could be concat which will concat all item value or
	 * calculate which means that value needs to be calculate.
	 */
	private String mode;

	/**
	 * List of item that information include. e.g: CLIENT TYPE, CLIENT NUMBER,
	 * ACCOUNT NUMBER, SUBACCOUNT NUMBER
	 */
	List<Item> items;

	public Information() {
		this.items = new ArrayList<Item>();
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the items
	 */
	public List<Item> getItems() {
		return items;
	}

	public void addItem(Item item) {
		this.items.add(item);
	}
}
