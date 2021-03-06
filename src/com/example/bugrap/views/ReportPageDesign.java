package com.example.bugrap.views;

import com.example.bugrap.views.FeatureDescriptionView;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

/** 
 * !! DO NOT EDIT THIS FILE !!
 * 
 * This class is generated by Vaadin Designer and will be overwritten.
 * 
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { … }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class ReportPageDesign extends VerticalLayout {
	protected Label breadcrums;
	protected FeatureDescriptionView featureDescription;
	protected CssLayout uploadsContainer;
	protected Button doneButton;
	protected Upload attachmentButton;
	protected Button cancelButton;

	public ReportPageDesign() {
		Design.read(this);
	}
}
