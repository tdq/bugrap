package com.example.bugrap.views;

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
	public FeatureDescriptionLayout() {
		expandFeature.addClickListener((event) -> {
			openInWindow();
		});
	}

	/**
	 * 
	 */
	public void openInWindow() {
		final Window expandedFeature = new Window();
		expandedFeature.setContent(this);
		expandedFeature.setModal(true);
		expandedFeature.setWidth("70%");
		expandedFeature.setHeight("70%");
		expandFeature.setEnabled(false);
		
		UI.getCurrent().addWindow(expandedFeature);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
