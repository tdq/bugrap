package com.example.bugrap.views;

import java.util.List;

import com.example.bugrap.model.Status;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

/**
 * 
 * @author nikolaigorokhov
 *
 */
public class StatusesFilter implements Container.Filter {
	private List<Status> statuses;
	private String propertyId;
	
	public StatusesFilter(String propertyId) {
		this.propertyId = propertyId;
	}
	
	/**
	 * 
	 * @param statuses
	 */
	public void setStatuses(List<Status> statuses) {
		this.statuses = statuses;
	}
	
	@Override
	public boolean passesFilter(Object itemId, Item item) throws UnsupportedOperationException {
		Property property = item.getItemProperty(propertyId);
		
		if(property == null || property.getType().equals(Status.class) == false)
			return false;
		
		Status value = (Status) property.getValue();
		
		if(statuses.size() == 0)
			return true;
		
		return statuses.stream().anyMatch(status -> status.equals(value));
	}

	@Override
	public boolean appliesToProperty(Object propertyId) {
		return propertyId != null && propertyId.equals(this.propertyId);
	}
}
