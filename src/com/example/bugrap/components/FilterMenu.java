package com.example.bugrap.components;

import java.util.LinkedList;
import java.util.List;

import org.vaadin.hene.popupbutton.PopupButton;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * 
 * @author nikolaigorokhov
 *
 */
public class FilterMenu extends CssLayout {
	private List<FilterMenuItem> buttons = new LinkedList<>();
	
	public class FilterMenuItem extends PopupButton {
		private VerticalLayout layout;
		private Label caption = new Label();
		private List<Object> selectedOptions = new LinkedList<>();
		private ValueChangeListener listener;
		
		public FilterMenuItem(String buttonCaption) {
			super(buttonCaption);
			this.setHeight("25px");
		}

		public void setPopupCaption(String caption) {
			this.caption.setValue(caption);
		}
		
		public void addOption(final Object option, final String caption) {
			if(layout == null) {
				layout = new VerticalLayout();
				this.setContent(layout);
				
				layout.addComponent(this.caption);
				this.removeStyleName("disable-indicator");
			}
			
			CheckBox checkBox = new CheckBox(caption);
			layout.addComponent(checkBox);
			
			checkBox.addValueChangeListener(event -> {
				if((boolean) event.getProperty().getValue()) {
					selectedOptions.add(option);
				} else {
					selectedOptions.remove(option);
				}
				
				listener.onChange();
			});
		}
		
		public List<?> getValue() {
			return selectedOptions;
		}
		
		public void setValueChangeListener(ValueChangeListener listener) {
			this.listener = listener;
		}
	}
	
	public interface ValueChangeListener {
		public void onChange();
	}
	
	public FilterMenu() {
		setStyleName("filtermenu v-component-group");
	}
	
	public FilterMenuItem addItem(String buttonCaption) {
		FilterMenuItem item = new FilterMenuItem(buttonCaption);
		item.setStyleName("disable-indicator");
		
		item.addClickListener(event -> {
			buttons.forEach(button -> button.removeStyleName("selected"));
			item.setStyleName("selected", true);
		});
		
		buttons.add(item);
		this.addComponent(item);
		
		return item;
	}
}
