package com.example.bugrap.components.client.distributionbar;

import com.google.gwt.user.client.ui.HTML;

// TODO extend any GWT Widget
public class DistributionBarWidget extends HTML {

	public static final String CLASSNAME = "distributionbar";
	private DistributionBarState state = new DistributionBarState();

	public DistributionBarWidget() {

		// setText("DistributionBar sets the text via DistributionBarConnector using DistributionBarState");
		setStyleName(CLASSNAME);
		
		System.out.println("Create distribution bar");
		
		display();
	}
	
	public void setState(DistributionBarState state) {
		this.state = state;
		
		System.out.println("Update state");
		
		display();
	}
	
	private void display() {
		int closed = state.closedAmount;
		int nonResolved = state.nonResolvedAmount;
		int unassigned = state.unassignedAmount;
		int sum = closed+nonResolved+unassigned;
		
		if(sum == 0)
			sum = 1;
		
		float closedLength = (float)closed / sum * 100;
		float nonResolvedLength = (float)nonResolved / sum * 100;
		float unassignedLength = (float)unassigned / sum *100;
		
		setHTML("<bar>"+
					"<item style=\"width:"+closedLength+"%\">"+closed+"</item>"+
					"<item style=\"width:"+nonResolvedLength+"%\">"+nonResolved+"</item>"+
					"<item style=\"width:"+unassignedLength+"%\">"+unassigned+"</item>"+
				"</bar>");
	}
}