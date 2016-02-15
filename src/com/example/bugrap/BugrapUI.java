package com.example.bugrap;

import javax.servlet.annotation.WebServlet;

import com.example.bugrap.views.MainLayout;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("bugrap")
@Widgetset("com.example.bugrap.components.BugrapWidgetset")
public class BugrapUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = BugrapUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		final MainLayout layout = new MainLayout();
		//final FeatureDescriptionLayout layout = new FeatureDescriptionLayout();
		
		setContent(layout);
	}

}