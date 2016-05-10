package com.example.bugrap.views;

import java.util.List;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.incubator.bugrap.model.reports.ReportStatus;

/**
 * 
 * @author nikolaigorokhov
 *
 */
@SuppressWarnings("serial")
public class StatusesFilter implements Container.Filter {
	private List<ReportStatus> statuses;
	private String propertyId;
	
	public StatusesFilter(String propertyId) {
		this.propertyId = propertyId;
	}
	
	/**
	 * 
	 * @param statuses
	 */
	public void setStatuses(List<ReportStatus> statuses) {
		this.statuses = statuses;
	}
	
	@Override
	public boolean passesFilter(Object itemId, Item item) throws UnsupportedOperationException {
		Property property = item.getItemProperty(propertyId);
		
		if(property == null || property.getType().equals(ReportStatus.class) == false)
			return false;
		
		ReportStatus value = (ReportStatus) property.getValue();
		
		if(statuses.size() == 0)
			return true;
		
		return statuses.stream().anyMatch(status -> status.equals(value));
	}

	@Override
	public boolean appliesToProperty(Object propertyId) {
		return propertyId != null && propertyId.equals(this.propertyId);
	}
}
