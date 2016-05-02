package com.example.bugrap.views;

import com.example.bugrap.controllers.ReportsController;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

/**
 * 
 * @author nikolaigorokhov
 *
 */
@SuppressWarnings("serial")
public class FeatureDescriptionLayout extends FeatureDescriptionDesign implements View {
	
	private ReportsController controller;
	private ExpandListener expandListener;
	
	/**
	 * 
	 * @author nikolaigorokhov
	 *
	 */
	public interface ExpandListener {
		public void onExpand();
	}
	
	/**
	 * 
	 */
	public FeatureDescriptionLayout() {
		expandFeature.addClickListener((event) -> {
			if(expandListener != null)
				expandListener.onExpand();
		});
		
		expandFeature.setVisible(false);
	}
	
	/**
	 * 
	 * @param listener
	 */
	public void setExpandListener(ExpandListener listener) {
		this.expandListener = listener;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 */
	public void integratedView() {
		expandFeature.setVisible(true);
	}
}
