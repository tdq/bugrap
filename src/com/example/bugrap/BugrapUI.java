package com.example.bugrap;

import javax.servlet.annotation.WebServlet;

import com.example.bugrap.controllers.ReportsController;
import com.example.bugrap.views.MainView;
import com.example.bugrap.views.ReportPageView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("bugrap")
@Widgetset("com.example.bugrap.components.BugrapWidgetset")
public class BugrapUI extends UI {
	
	Navigator navigator;
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = BugrapUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("BugRap");
		
		navigator = new Navigator(this, this);
		//ReportsController reportsController = new ReportsController(navigator);
		
		navigator.addView("", MainView.class);
		navigator.addView("report", ReportPageView.class);
	}
}