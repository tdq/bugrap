package com.example.bugrap.views;

import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class FeatureDescriptionLayout extends FeatureDescriptionDesign {
	public FeatureDescriptionLayout() {
		expandFeature.addClickListener((event) -> {
			openInWindow();
		});
	}

	public void openInWindow() {
		final Window expandedFeature = new com.vaadin.ui.Window();
		expandedFeature.setContent(this);
		expandedFeature.setModal(true);
		expandedFeature.setWidth("70%");
		expandedFeature.setHeight("70%");
		expandedFeature.setEnabled(false);
		
		UI.getCurrent().addWindow(expandedFeature);
	}
}
