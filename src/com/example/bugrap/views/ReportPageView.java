package com.example.bugrap.views;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

import com.example.bugrap.components.UploadFileComponent;
import com.example.bugrap.controllers.ReportsController;
import com.example.bugrap.model.Task;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload.StartedEvent;

public class ReportPageView extends ReportPageDesign implements View {
	
	private ReportsController controller = new ReportsController();
	
	public ReportPageView() {
		//breadcrums.setValue("Project name that is rather long pellentesque habitant morbi › 1.2.3-pre12");
		
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
				}
			};
		});
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		if(event.getParameters() != null && event.getParameters().isEmpty() == false) {
			int taskId = Integer.parseInt(event.getParameters());
			
			Task task = controller.getTask(taskId);
			Set<Integer> tasks = new HashSet<>();
			tasks.add(taskId);
			featureDescription.setTasks(tasks, task.getProject().getId());

			breadcrums.setValue(task.getProject().getName()+" › "+task.getVersion().getName());
		}
	}

}
