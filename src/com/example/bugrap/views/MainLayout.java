package com.example.bugrap.views;

import com.example.bugrap.controllers.ReportsController;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.MouseEventDetails.MouseButton;

@SuppressWarnings("serial")
public class MainLayout extends MainPageDesign implements View {
	
	private ReportsController controller;
	
	public MainLayout(ReportsController controller) {
		this.controller = controller;
	
		distributionBar.setClosedAmount(5);
		distributionBar.setNonResolvedAmount(15);
		distributionBar.setUnassignedAmount(180);
		
		bugsTable.setSelectable(true);
		bugsTable.setImmediate(true);
		
		bugsTable.addContainerProperty("Version", String.class, null);
		bugsTable.addContainerProperty("Priority", Integer.class, 0);
		bugsTable.addContainerProperty("Type", String.class, null);
		bugsTable.addContainerProperty("Summary", String.class, null);
		bugsTable.addContainerProperty("Assigned to", String.class, null);
		bugsTable.addContainerProperty("Last modified", String.class, null);
		bugsTable.addContainerProperty("Reported", String.class, null);
		
		bugsTable.addItem(new Object[]{"1.2.3-pre12", 5, "Bug", "Panel child component hierarchy is invalid", "Marc Manager", null, "15 mins ago"}, 1);
		
		bugsTable.setVisibleColumns(new Object[]{"Version", "Priority", "Type", "Summary", "Assigned to", "Last modified", "Reported"});
		bugsTable.setColumnHeaders(new String[]{"Version", "Priority", "Type", "Summary", "Assigned to", "Last modified", "Reported"});
		
		bugsTable.addItemClickListener((event) -> {
			if(event.getButton() == MouseButton.LEFT && event.isDoubleClick() == false) {
				final FeatureDescriptionLayout featureDescription = new FeatureDescriptionLayout();
				featureDescription.integratedView();
				featureDescription.setExpandListener(controller::openDescription);

				tableLayout.setSecondComponent(featureDescription);
				tableLayout.setSplitPosition(50f);
			} else if(event.isDoubleClick()) {
				tableLayout.setSplitPosition(100f);
				controller.openDescription();
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}
}
