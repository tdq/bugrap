package com.example.bugrap.views;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.bugrap.controllers.ReportsController;
import com.example.bugrap.model.Distribution;
import com.example.bugrap.model.Project;
import com.example.bugrap.model.Task;
import com.example.bugrap.model.User;
import com.example.bugrap.model.Version;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.MouseEventDetails.MouseButton;

/**
 * 
 * @author nikolaigorokhov
 *
 */
@SuppressWarnings("serial")
public class MainView extends MainPageDesign implements View {
	
	private ReportsController controller;
	private int currentVersionId;
	private Project currentProject;
	
	/**
	 * 
	 * @param controller
	 */
	public MainView(ReportsController controller) {
		this.controller = controller;
	
		projects.setImmediate(true);
		
		bugsTable.setSelectable(true);
		bugsTable.setImmediate(true);
		bugsTable.setMultiSelect(true);
		
		bugsTable.addContainerProperty("version", String.class, null);
		bugsTable.addContainerProperty("priority", Integer.class, 0);
		bugsTable.addContainerProperty("type", String.class, null);
		bugsTable.addContainerProperty("summary", String.class, null);
		bugsTable.addContainerProperty("assigned to", String.class, null);
		bugsTable.addContainerProperty("last modified", String.class, null);
		bugsTable.addContainerProperty("reported", String.class, null);
		
		setUser();
		
		bugsTable.addItemClickListener((event) -> {
			Set<Integer> tasks = getSelectedTasks();
			tasks.add((Integer) event.getItemId());
			
			if(event.getButton() == MouseButton.LEFT && event.isDoubleClick() == false) {
				final FeatureDescriptionView featureDescription = new FeatureDescriptionView();
				featureDescription.integratedView();
				featureDescription.setExpandListener(() -> controller.openTaskDescription(tasks));
				featureDescription.setTasks(tasks, currentProject.getId());

				tableLayout.setSecondComponent(featureDescription);
				tableLayout.setSplitPosition(50f);
			} else if(event.isDoubleClick()) {
				tableLayout.setSplitPosition(100f);
				controller.openTaskDescription(tasks);
			}
		});
		
		bugsTable.addShortcutListener(new ShortcutListener("Enter", ShortcutAction.KeyCode.ENTER, null) {
			
			@Override
			public void handleAction(Object sender, Object target) {
				Set<Integer> tasks = getSelectedTasks();
				tableLayout.setSplitPosition(100f);
				controller.openTaskDescription(tasks);
			}
		});
		
		projects.addValueChangeListener(event -> {
			Project project = (Project) event.getProperty().getValue();
			
			if(project == null)
				return;
			
			currentProject = project;
			
			setDistribution(currentProject.getId());
			setVersions(currentProject.getId());
			setBugTableDataSource(currentProject.getId(), currentVersionId);
		});
		
		versions.addValueChangeListener(event -> {
			Version version = (Version) event.getProperty().getValue();
			
			if(version == null)
				return;
			
			currentVersionId = version.getId();
			
			setBugTableDataSource(currentProject.getId(), currentVersionId);
		});
		
		logoutButton.addClickListener(event -> {
			controller.logout();
		});
	}

	private Set<Integer> getSelectedTasks() {
		Set<Integer> tasks = new HashSet<>();
		
		bugsTable.getItemIds().forEach(id -> {if(bugsTable.isSelected(id)) tasks.add((Integer) id);});
		
		return tasks;
	}

	private void setUser() {
		User user = controller.getCurrentUser();
		
		userButton.setCaption(user.getName());
		setProjects(user.getId());
	}

	private void setVersions(int projectId) {
		List<Version> items = controller.getProjectVersions(projectId);
		
		versions.removeAllItems();
		
		if(items.size() > 1) {
			versions.addItem(new Version(0, currentProject, "All versions"));
		}
		
		items.forEach(item -> versions.addItem(item));
		
		if(items.size() > 0) {
			versions.setValue(items.get(0));
			currentVersionId = items.get(0).getId();
		}
	}

	private void setDistribution(int projectId) {
		Distribution distribution = controller.getDistribution(projectId);
		
		distributionBar.setClosedAmount(distribution.getClosedAmount());
		distributionBar.setNonResolvedAmount(distribution.getNonResolvedAmount());
		distributionBar.setUnassignedAmount(distribution.getUnassignedAmount());
	}

	private void setProjects(int userId) {
		List<Project> items = controller.getProjects(userId);
		
		projects.removeAllItems();
		
		items.forEach(item -> projects.addItem(item));

		if(items.size() > 0) {
			currentProject = items.get(0);
			projects.setValue(currentProject);
			
			setDistribution(currentProject.getId());
			setVersions(currentProject.getId());
			setBugTableDataSource(currentProject.getId(), currentVersionId);
		}
	}

	private void setBugTableDataSource(int projectId, int currentVersionId) {
		List<Task> tasks = controller.getTasks(projectId, currentVersionId);
		
		bugsTable.removeAllItems();
		tableLayout.setSplitPosition(100f);
		
		if(currentVersionId == 0) {
			bugsTable.setVisibleColumns(new Object[]{"version", "priority", "type", "summary", "assigned to", "last modified", "reported"});
			bugsTable.setColumnHeaders(new String[]{"Version", "Priority", "Type", "Summary", "Assigned to", "Last modified", "Reported"});
			
			tasks.forEach(task -> bugsTable.addItem(
				new Object[] {
					task.getVersion().getName(), 
					task.getPriority(), 
					task.getType().getName(), 
					task.getSummary(), 
					task.getUser().getName(), 
					task.getLastModified(), 
					task.getReported()
				}, 
				task.getId()
			));
		} else {
			bugsTable.setVisibleColumns(new Object[]{"priority", "type", "summary", "assigned to", "last modified", "reported"});
			bugsTable.setColumnHeaders(new String[]{"Priority", "Type", "Summary", "Assigned to", "Last modified", "Reported"});
			
			tasks.forEach(task -> bugsTable.addItem(
				new Object[] {
					task.getPriority(), 
					task.getType().getName(), 
					task.getSummary(), 
					task.getUser().getName(), 
					task.getLastModified(), 
					task.getReported()
				}, 
				task.getId()
			));
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}
}
