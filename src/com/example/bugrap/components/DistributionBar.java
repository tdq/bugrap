package com.example.bugrap.components;

import com.example.bugrap.components.client.distributionbar.DistributionBarClientRpc;
import com.example.bugrap.components.client.distributionbar.DistributionBarServerRpc;
import com.vaadin.shared.MouseEventDetails;
import com.example.bugrap.components.client.distributionbar.DistributionBarState;

@SuppressWarnings("serial")
public class DistributionBar extends com.vaadin.ui.AbstractComponent {
	
	private DistributionBarServerRpc rpc = new DistributionBarServerRpc() {
		private int clickCount = 0;

		public void clicked(MouseEventDetails mouseDetails) {
			// nag every 5:th click using RPC
			if (++clickCount % 5 == 0) {
				getRpcProxy(DistributionBarClientRpc.class).alert(
						"Ok, that's enough!");
			}
			// update shared state
			//getState().text = "You have clicked " + clickCount + " times";
		}
	};  

	public DistributionBar() {
		registerRpc(rpc);
	}
	
	public void setClosedAmount(int closedAmount) {
		if(closedAmount >= 0)
			getState().closedAmount = closedAmount;
	}
	
	public int getClosedAmount() {
		return getState().closedAmount;
	}
	
	public void setNonResolvedAmount(int nonResolvedAmount) {
		if(nonResolvedAmount >= 0)
			getState().nonResolvedAmount = nonResolvedAmount;
	}
	
	public int getNonResolvedAmount() {
		return getState().nonResolvedAmount;
	}
	
	public void setUnassignedAmount(int unassignedAmount) {
		if(unassignedAmount >= 0)
			getState().unassignedAmount = unassignedAmount;
	}
	
	public int getUnassignedAmount() {
		return getState().unassignedAmount;
	}

	@Override
	public DistributionBarState getState() {
		return (DistributionBarState) super.getState();
	}
}
