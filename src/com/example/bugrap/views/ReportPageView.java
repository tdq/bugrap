package com.example.bugrap.views;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.example.bugrap.components.UploadFileComponent;
import com.example.bugrap.controllers.ReportsController;
import com.vaadin.incubator.bugrap.model.reports.Report;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

public class ReportPageView extends ReportPageDesign implements View {
	
	private ReportsController controller = new ReportsController();
	
	public ReportPageView() {
		attachmentButton.setButtonCaption("Attachment...");
		attachmentButton.addStartedListener(event -> {
			UploadFileComponent uploadComponent = new UploadFileComponent(attachmentButton, event.getFilename());
			uploadComponent.setCloseListener(() -> {
				uploadsContainer.removeComponent(uploadComponent);
				
				if(uploadsContainer.getComponentCount() == 0) {
					uploadsContainer.setVisible(false);
				}
			});
			
			uploadsContainer.setVisible(true);
			uploadsContainer.addComponent(uploadComponent);
			cancelButton.setEnabled(true);
			
			UI.getCurrent().setPollInterval(500);
		});
		
		attachmentButton.addFinishedListener(event -> {
			cancelButton.setEnabled(false);
		});
		
		attachmentButton.setReceiver((String filename, String mimeType) -> {
			return new OutputStream() {
				
				@Override
				public void write(int b) throws IOException {
					// TODO
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		});
		
		cancelButton.addClickListener(event -> {
			Iterator<Component> components = uploadsContainer.iterator();
			
			while(components.hasNext()) {
				UploadFileComponent component = (UploadFileComponent) components.next();
				
				component.close();
			}
		});
		
		doneButton.addClickListener(event -> {
			// TODO store in the model
			comment.clear();
			uploadsContainer.removeAllComponents();
			uploadsContainer.setVisible(false);
		});
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		if(event.getParameters() != null && event.getParameters().isEmpty() == false) {
			long taskId = Long.parseLong(event.getParameters());
			
			Report task = controller.getTask(taskId);
			Set<Long> tasks = new HashSet<>();
			tasks.add(taskId);
			featureDescription.setTasks(tasks, task.getProject());

			breadcrums.setValue(task.getProject().getName()+" › "+task.getVersion().getVersion());
		}
	}

}
