package com.example.bugrap.components;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

/**
 * 
 * @author nikolaigorokhov
 *
 */
public class UploadFileComponent extends HorizontalLayout implements FailedListener, ProgressListener, SucceededListener {
	private Label fileName = new Label();
	private ProgressBar progressBar = new ProgressBar();
	private Button closeButton = new Button("X");
	private CloseListener closeListener;
	
	/**
	 * 
	 * @author nikolaigorokhov
	 *
	 */
	public interface CloseListener {
		public void onClose();
	}

	public UploadFileComponent(Upload upload, String name) {
		fileName.setValue(name);
		closeButton.setStyleName("borderless");
		
		closeButton.addClickListener(listener -> {
			upload.interruptUpload();
			this.removeComponent(progressBar);
			
			if(closeListener != null)
				closeListener.onClose();
		});
		
		this.addComponent(fileName);
		this.setComponentAlignment(fileName, Alignment.MIDDLE_LEFT);
		this.addComponent(progressBar);
		this.setComponentAlignment(progressBar, Alignment.MIDDLE_LEFT);
		this.addComponent(closeButton);
		this.setComponentAlignment(closeButton, Alignment.MIDDLE_LEFT);
		
		upload.addFailedListener(this);
		upload.addProgressListener(this);
		upload.addSucceededListener(this);
	}

	@Override
	public void uploadFailed(FailedEvent event) {
		this.removeComponent(progressBar);
		
		Notification.show("Upload error", event.toString(), Notification.Type.ERROR_MESSAGE);
	}

	@Override
	public void updateProgress(long readBytes, long contentLength) {
		progressBar.setValue(new Float(readBytes / (float) contentLength));
	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {
		this.removeComponent(progressBar);
	}
	
	public void setCloseListener(CloseListener listener) {
		this.closeListener = listener;
	}
}
