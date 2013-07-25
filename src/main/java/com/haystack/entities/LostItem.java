package com.haystack.entities;

import java.util.List;

public class LostItem extends Connection {

	private List<Item> items;

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public void addItem(Item item) {
		this.items.add(item);
	}
	
	public void removeContext(Integer itemId) {
		for (Item i : items) {
			if (i.getId() == itemId)
				items.remove(i);
		}
	}
	
}
