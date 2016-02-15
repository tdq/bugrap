package com.example.bugrap.components.client.distributionbar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

import com.example.bugrap.components.DistributionBar;
import com.example.bugrap.components.client.distributionbar.DistributionBarWidget;
import com.example.bugrap.components.client.distributionbar.DistributionBarServerRpc;
import com.vaadin.client.communication.RpcProxy;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.example.bugrap.components.client.distributionbar.DistributionBarClientRpc;
import com.example.bugrap.components.client.distributionbar.DistributionBarState;
import com.vaadin.client.communication.StateChangeEvent;

@SuppressWarnings("serial")
@Connect(DistributionBar.class)
public class DistributionBarConnector extends AbstractComponentConnector {

	DistributionBarServerRpc rpc = RpcProxy
			.create(DistributionBarServerRpc.class, this);
	
	public DistributionBarConnector() {
		registerRpc(DistributionBarClientRpc.class, new DistributionBarClientRpc() {
			public void alert(String message) {
				// TODO Do something useful
				Window.alert(message);
			}
		});

		// TODO ServerRpc usage example, do something useful instead
		getWidget().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				final MouseEventDetails mouseDetails = MouseEventDetailsBuilder
					.buildMouseEventDetails(event.getNativeEvent(),
								getWidget().getElement());
				rpc.clicked(mouseDetails);
			}
		});

	}

	@Override
	protected Widget createWidget() {
		return GWT.create(DistributionBarWidget.class);
	}

	@Override
	public DistributionBarWidget getWidget() {
		return (DistributionBarWidget) super.getWidget();
	}

	@Override
	public DistributionBarState getState() {
		return (DistributionBarState) super.getState();
	}

	@Override
	public void onStateChanged(StateChangeEvent stateChangeEvent) {
		super.onStateChanged(stateChangeEvent);

		// TODO do something useful
		//final String text = getState().text;
		//getWidget().setText(text);
		
		getWidget().setState(getState());
	}

}

