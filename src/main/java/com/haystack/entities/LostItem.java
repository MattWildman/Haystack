package com.haystack.entities;

import lombok.Data;

@Data public class LostItem extends Connection {

	private Item[] lostItems;
	
}
