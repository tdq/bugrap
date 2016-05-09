package com.example.bugrap.views;

import java.util.List;
import java.util.Set;

import com.example.bugrap.controllers.ReportsController;
import com.example.bugrap.model.Status;
import com.example.bugrap.model.Task;
import com.example.bugrap.model.Type;
import com.example.bugrap.model.User;
import com.example.bugrap.model.Version;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;

/**
 * 
 * @author nikolaigorokhov
 *
 */
@SuppressWarnings("serial")
public class FeatureDescriptionView extends FeatureDescriptionDesign {
	
	private ReportsController controller = new ReportsController();
	private ExpandListener expandListener;
	private UpdateTaskListener updateTaskListener;
	private Set<Integer> currentTasks;
	
	/**
	 * 
	 * @author nikolaigorokhov
	 *
	 */
	public interface ExpandListener {
		public void onExpand();
	}
	
	/**
	 * 
	 * @author nikolaigorokhov
	 *
	 */
	public interface UpdateTaskListener {
		public void onUpdate(List<Task> updatedTasks);
	}
	
	/**
	 * 
	 */
	public FeatureDescriptionView() {
		summary.setContentMode(ContentMode.HTML);
		
		expandFeature.addClickListener((event) -> {
			if(expandListener != null)
				expandListener.onExpand();
		});
		
		expandFeature.setVisible(false);
		
		updateButton.addClickListener(event -> {
			if(currentTasks == null) {
				return;
			}
			
			// TODO store current values somethere
			
			Integer priority = (Integer) priorities.getValue();
			Type type = (Type) types.getValue();
			Status status = (Status) statuses.getValue();
			User user = (User) users.getValue();
			Version version = (Version) versions.getValue();
			
			List<Task> tasks = controller.getTasks(currentTasks);
			tasks.forEach(task -> {
				if(priority != null)
					task.setPriority(priority);
				if(type != null)
					task.setType(type);
				if(status != null)
					task.setStatus(status);
				if(user != null)
					task.setUser(user);
				if(version != null)
					task.setVersion(version);
			});
			
			if(updateTaskListener != null)
				updateTaskListener.onUpdate(tasks);
		});
	}

	/**
	 * 
	 * @param projectId
	 * @param selectedVersion
	 */
	private void setVersions(int projectId, Version selectedVersion) {
		List<Version> items = controller.getProjectVersions(projectId);
		
		versions.removeAllItems();
		items.forEach(item -> versions.addItem(item));
		
		versions.setValue(selectedVersion);
	}

	/**
	 * 
	 * @param selectedUser
	 */
	private void setUsers(User selectedUser) {
		List<User> items = controller.getUsers();
		
		users.removeAllItems();
		items.forEach(item -> users.addItem(item));
		
		users.setValue(selectedUser);
	}

	/**
	 * 
	 * @param status
	 */
	private void setStatuses(Status status) {
		List<Status> items = controller.getStatuses();
		
		statuses.removeAllItems();
		items.forEach(item -> statuses.addItem(item));

		statuses.setValue(status);
	}

	/**
	 * 
	 * @param selectedType
	 */
	private void setTypes(Type selectedType) {
		List<Type> items = controller.getTypes();
		
		types.removeAllItems();
		items.forEach(item -> types.addItem(item));
		
		types.setValue(selectedType);
	}

	/**
	 * 
	 * @param selectedPriority
	 */
	private void setPriorities(int selectedPriority) {
		priorities.removeAllItems();
		
		for(int i=5; i>0; --i) {
			priorities.addItem(i);
		}
		
		priorities.setValue(selectedPriority);
	}
	
	/**
	 * 
	 * @param listener
	 */
	public void setExpandListener(ExpandListener listener) {
		this.expandListener = listener;
	}
	
	/**
	 * 
	 * @return
	 */
	public float getHeaderHeight() {
		return 125f;
	}
	
	/**
	 * 
	 * @param listener
	 */
	public void setUpdateTasksListener(UpdateTaskListener listener) {
		this.updateTaskListener = listener;
	}

	/**
	 * 
	 */
	public void integratedView() {
		expandFeature.setVisible(true);
	}

	/**
	 * 
	 * @param tasks
	 * @param versionId 
	 * @param projectId 
	 */
	public void setTasks(Set<Integer> tasks, int projectId) {
		Task avgTask = controller.getAvgTask(tasks);
		
		setPriorities(avgTask.getPriority());
		setTypes(avgTask.getType());
		setStatuses(avgTask.getStatus());
		setUsers(avgTask.getUser());
		setVersions(projectId, avgTask.getVersion());
		setLogo(avgTask.getSummary());
		
		if(tasks.size() == 1) {
			commentsList.setVisible(true);
			setComments(avgTask.getComments());
		} else {
			commentsList.setVisible(false);
			setLogo("<b>"+tasks.size()+" reports selected</b> - Select a single report to view contents");
		}
		
		currentTasks = tasks;
	}

	/**
	 * 
	 * @param comments
	 */
	private void setComments(String comments) {
		commentsList.setValue(comments);
	}

	/**
	 * 
	 * @param logo
	 */
	private void setLogo(String logo) {
		summary.setValue(logo);
	}
}
