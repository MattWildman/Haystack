package com.haystack.entities;

import java.util.List;

public class ItemConnection extends Connection {
	
	private List<Item> items;

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

}
