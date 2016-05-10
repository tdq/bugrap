package com.example.bugrap.views;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

public class FieldsFilter implements Container.Filter {
	private String propertyId;
	private String value;
	
	public FieldsFilter(String propertyId) {
		this.propertyId = propertyId;
	}
	
	@Override
	public boolean passesFilter(Object itemId, Item item) throws UnsupportedOperationException {
		Property property = item.getItemProperty(propertyId);
		
		if(property == null || property.getType().equals(String.class) == false)
			return false;
		
		String value = (String) property.getValue();
		
		return value.toLowerCase().contains(this.value.toLowerCase());
	}

	@Override
	public boolean appliesToProperty(Object propertyId) {
		return propertyId != null && propertyId.equals(this.propertyId);
	}

	public void setValue(String value) {
		this.value = value;
	}
}
