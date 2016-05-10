package com.example.bugrap.views;

import java.util.List;
import java.util.Set;

import com.example.bugrap.controllers.ReportsController;
import com.vaadin.incubator.bugrap.model.projects.Project;
import com.vaadin.incubator.bugrap.model.projects.ProjectVersion;
import com.vaadin.incubator.bugrap.model.reports.Comment;
import com.vaadin.incubator.bugrap.model.reports.Report;
import com.vaadin.incubator.bugrap.model.reports.ReportPriority;
import com.vaadin.incubator.bugrap.model.reports.ReportStatus;
import com.vaadin.incubator.bugrap.model.reports.ReportType;
import com.vaadin.incubator.bugrap.model.users.Reporter;
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
	private Set<Long> currentTasks;
	private Project currentProject;
	
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
		public void onUpdate(List<Report> tasks);
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
			
			ReportPriority priority = (ReportPriority) priorities.getValue();
			ReportType type = (ReportType) types.getValue();
			ReportStatus status = (ReportStatus) statuses.getValue();
			Reporter user = (Reporter) users.getValue();
			ProjectVersion version = (ProjectVersion) versions.getValue();
			
			List<Report> tasks = controller.getTasks(currentProject, currentTasks);
			tasks.forEach(task -> {
				if(priority != null)
					task.setPriority(priority);
				if(type != null)
					task.setType(type);
				if(status != null)
					task.setStatus(status);
				if(user != null)
					task.setAssigned(user);
				if(version != null)
					task.setVersion(version);
				
				controller.saveTask(task);
			});
			
			if(updateTaskListener != null)
				updateTaskListener.onUpdate(tasks);
		});
		
		revertButton.addClickListener(event -> {
			setTasks(currentTasks, currentProject);
		});
	}
	
	/**
	 * 
	 * @param projectId
	 * @param selectedVersion
	 */
	private void setVersions(ProjectVersion selectedVersion) {
		List<ProjectVersion> items = controller.getProjectVersions(currentProject);
		
		versions.removeAllItems();
		items.forEach(item -> versions.addItem(item));
		
		versions.setValue(selectedVersion);
	}

	/**
	 * 
	 * @param selectedUser
	 */
	private void setUsers(Reporter selectedUser) {
		List<Reporter> items = controller.getUsers();
		
		users.removeAllItems();
		items.forEach(item -> users.addItem(item));
		
		users.setValue(selectedUser);
	}

	/**
	 * 
	 * @param status
	 */
	private void setStatuses(ReportStatus status) {
		List<ReportStatus> items = controller.getStatuses();
		
		statuses.removeAllItems();
		items.forEach(item -> statuses.addItem(item));

		statuses.setValue(status);
	}

	/**
	 * 
	 * @param selectedType
	 */
	private void setTypes(ReportType selectedType) {
		List<ReportType> items = controller.getTypes();
		
		types.removeAllItems();
		items.forEach(item -> types.addItem(item));
		
		types.setValue(selectedType);
	}

	/**
	 * 
	 * @param selectedPriority
	 */
	private void setPriorities(ReportPriority selectedPriority) {
		List<ReportPriority> items = controller.getPriorities();
		
		priorities.removeAllItems();
		
		items.forEach(item -> priorities.addItem(item));
		
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
	public void setTasks(Set<Long> tasks, Project project) {
		Report avgTask = controller.getAvgTask(project, tasks);
		currentProject = project;
		
		setPriorities(avgTask.getPriority());
		setTypes(avgTask.getType());
		setStatuses(avgTask.getStatus());
		setUsers(avgTask.getAssigned());
		setVersions(avgTask.getVersion());
		setLogo(avgTask.getSummary());
		
		if(tasks.size() == 1) {
			commentsList.setVisible(true);
			setComments(controller.getComments(avgTask));
			expandFeature.setVisible(true);
		} else {
			commentsList.setVisible(false);
			setLogo("<b>"+tasks.size()+" reports selected</b> - Select a single report to view contents");
			expandFeature.setVisible(false);
		}
		
		currentTasks = tasks;
	}

	/**
	 * 
	 * @param list
	 */
	private void setComments(List<Comment> list) {
		//commentsList.setValue(list);
	}

	/**
	 * 
	 * @param logo
	 */
	private void setLogo(String logo) {
		summary.setValue(logo);
	}
}
