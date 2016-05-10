package com.example.bugrap.views;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.example.bugrap.components.FilterMenu.FilterMenuItem;
import com.example.bugrap.controllers.ReportsController;
import com.example.bugrap.model.Distribution;
import com.example.bugrap.model.Status;
import com.example.bugrap.model.Task;
import com.example.bugrap.model.User;
import com.example.bugrap.model.Version;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.incubator.bugrap.model.projects.Project;
import com.vaadin.incubator.bugrap.model.projects.ProjectVersion;
import com.vaadin.incubator.bugrap.model.reports.Report;
import com.vaadin.incubator.bugrap.model.reports.ReportStatus;
import com.vaadin.incubator.bugrap.model.users.Reporter;
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
	
	private ReportsController controller = new ReportsController();
	private long currentVersionId;
	private Project currentProject;
	private BeanContainer<Long, Report> container = new BeanContainer<>(Report.class);
	
	/**
	 * 
	 * @param controller
	 */
	public MainView() {
		projects.setImmediate(true);
		
		bugsTable.setSelectable(true);
		bugsTable.setImmediate(true);
		bugsTable.setMultiSelect(true);
		
		bugsTable.addItemClickListener((event) -> {
			if(event.getButton() == MouseButton.LEFT && event.isDoubleClick()) {
				tableLayout.setSplitPosition(100f, Unit.PERCENTAGE);
				controller.openTaskDescription((long) event.getItemId());
			}
		});
		
		setStatuses();
		
		bugsTable.addValueChangeListener(event -> {
			Set<Long> tasks = (Set<Long>) bugsTable.getValue();
			
			if(tasks == null || tasks.isEmpty())
				return;
			
			final FeatureDescriptionView featureDescription = new FeatureDescriptionView();
			featureDescription.integratedView();
			featureDescription.setTasks(tasks, currentProject);
			featureDescription.setUpdateTasksListener(updatedTasks -> {
				updatedTasks.forEach(updated -> {
					Item item = bugsTable.getItem(updated.getId());
					item.getItemProperty("priority").setValue(updated.getPriority());
					item.getItemProperty("type").setValue(updated.getType());
					item.getItemProperty("summary").setValue(updated.getSummary());
					item.getItemProperty("assigned").setValue(updated.getAssigned());
					item.getItemProperty("timestamp").setValue(updated.getTimestamp());
					//item.getItemProperty("reported").setValue(updated.getResolution());
					
					if(item.getItemProperty("version") != null)
						item.getItemProperty("version").setValue(updated.getVersion());
				});
				
				setDistribution(currentProject);
			});
			
			if(tasks.size() == 1) {
				featureDescription.setExpandListener(() -> controller.openTaskDescription(tasks.iterator().next()));
				tableLayout.setSplitPosition(featureDescription.getHeaderHeight()+230, Unit.PIXELS, true);
				tableLayout.setLocked(false);
			} else {
				tableLayout.setLocked(true);
				tableLayout.setSplitPosition(featureDescription.getHeaderHeight(), Unit.PIXELS, true);
			}

			tableLayout.setSecondComponent(featureDescription);
			
		});
		
		bugsTable.addShortcutListener(new ShortcutListener("Enter", ShortcutAction.KeyCode.ENTER, null) {
			
			@Override
			public void handleAction(Object sender, Object target) {
				Set<Long> tasks = (Set<Long>) bugsTable.getValue();
				
				if(tasks == null || tasks.isEmpty())
					return;
				
				tableLayout.setSplitPosition(100f, Unit.PERCENTAGE);
				controller.openTaskDescription(tasks.iterator().next());
			}
		});
		
		projects.addValueChangeListener(event -> {
			Project project = (Project) event.getProperty().getValue();
			
			if(project == null)
				return;
			
			currentProject = project;
			
			setDistribution(currentProject);
			setVersions(currentProject);
			setBugTableDataSource(currentProject, currentVersionId);
		});
		
		versions.addValueChangeListener(event -> {
			ProjectVersion version = (ProjectVersion) event.getProperty().getValue();
			
			if(version == null)
				return;
			
			currentVersionId = version.getId();
			
			setBugTableDataSource(currentProject, currentVersionId);
		});
		
		logoutButton.addClickListener(event -> {
			controller.logout();
		});
		
		final FieldsFilter fieldsFilter = new FieldsFilter("summary");
		search.addShortcutListener(new ShortcutListener("Enter", ShortcutAction.KeyCode.ENTER, null) {
			
			@Override
			public void handleAction(Object sender, Object target) {
				if(search.getValue().isEmpty() == false) {
					fieldsFilter.setValue(search.getValue());
					container.addContainerFilter(fieldsFilter);
				} else {
					container.removeContainerFilter(fieldsFilter);
				}
			}
		});
		
		searchButton.addClickListener(event -> {
			if(search.getValue().isEmpty() == false) {
				fieldsFilter.setValue(search.getValue());
				container.addContainerFilter(fieldsFilter);
			}
		});
		
		clearSearch.addClickListener(event -> {
			container.removeContainerFilter(fieldsFilter);
		});
	}

	/**
	 * 
	 */
	private void setStatuses() {
		List<ReportStatus> statuses = controller.getStatuses();
		
		FilterMenuItem openItem = statusFilterMenu.addItem("Open");
		FilterMenuItem allItem = statusFilterMenu.addItem("All kinds");
		FilterMenuItem customItem = statusFilterMenu.addItem("Custom");
		customItem.setPopupCaption("Status");
		
		Filter openFilter = new SimpleStringFilter("status", "Open", true, true);
		StatusesFilter statusesFilter = new StatusesFilter("status");
		
		openItem.addClickListener(event -> {
			container.addContainerFilter(openFilter);
			container.removeContainerFilter(statusesFilter);
		});
		
		allItem.addClickListener(event -> {
			container.removeContainerFilter(openFilter);
			container.removeContainerFilter(statusesFilter);
		});
		
		customItem.setValueChangeListener(() -> {
			container.removeContainerFilter(openFilter);
			List<Status> selectedStatuses = (List<Status>) customItem.getValue();
			
			statusesFilter.setStatuses(selectedStatuses);
			container.addContainerFilter(statusesFilter);
		});
		
		statuses.forEach(status -> customItem.addOption(status, status.name()));
	}

	/**
	 * 
	 */
	private void setUser() {
		Reporter user = controller.getCurrentUser();
		
		userButton.setCaption(user.getName());
		setProjects(user.getId());
		
		FilterMenuItem meItem = userFilterMenu.addItem("Only me");
		FilterMenuItem allItem = userFilterMenu.addItem("Everyone");
		
		Filter filter = new SimpleStringFilter("user", user.getName(), true, true);
		
		meItem.addClickListener(event -> {
			container.addContainerFilter(filter);
		});
		
		allItem.addClickListener(event -> { 
			container.removeContainerFilter(filter);
		});
	}

	/**
	 * 
	 * @param projectId
	 */
	private void setVersions(Project project) {
		List<ProjectVersion> items = controller.getProjectVersions(project);
		
		versions.removeAllItems();
		
		if(items.size() > 1) {
			ProjectVersion allVersions = new ProjectVersion();
			allVersions.setId(0);
			allVersions.setProject(project);
			allVersions.setVersion("All versions");
			versions.addItem(allVersions);
		}
		
		items.forEach(item -> versions.addItem(item));
		
		if(items.size() > 0) {
			versions.setValue(items.get(0));
			currentVersionId = items.get(0).getId();
		}
	}

	/**
	 * 
	 * @param projectId
	 */
	private void setDistribution(Project project) {
		Distribution distribution = controller.getDistribution(project);
		
		distributionBar.setClosedAmount(distribution.getClosedAmount());
		distributionBar.setNonResolvedAmount(distribution.getNonResolvedAmount());
		distributionBar.setUnassignedAmount(distribution.getUnassignedAmount());
	}

	/**
	 * 
	 * @param userId
	 */
	private void setProjects(long userId) {
		List<Project> items = controller.getProjects(userId);
		
		projects.removeAllItems();
		
		items.forEach(item -> projects.addItem(item));

		if(items.size() > 0) {
			currentProject = items.get(0);
			projects.setValue(currentProject);
			
			setDistribution(currentProject);
			setVersions(currentProject);
			setBugTableDataSource(currentProject, currentVersionId);
		}
	}

	/**
	 * 
	 * @param projectId
	 * @param versionId
	 */
	private void setBugTableDataSource(Project project, long versionId) {
		List<Report> tasks = controller.getTasks(project, versionId);
		
		container.setBeanIdProperty("id");
		bugsTable.setContainerDataSource(container);
		
		tableLayout.setSplitPosition(100f, Unit.PERCENTAGE);
		
		if(versionId == 0) {
			bugsTable.setVisibleColumns(new Object[]{"version", "priority", "type", "summary", "assigned", "timestamp"});
			bugsTable.setColumnHeaders(new String[]{"Version", "Priority", "Type", "Summary", "Assigned to", "Last modified"});
		} else {
			bugsTable.setVisibleColumns(new Object[]{"priority", "type", "summary", "assigned", "timestamp"});
			bugsTable.setColumnHeaders(new String[]{"Priority", "Type", "Summary", "Assigned to", "Last modified"});	
		}
		
		container.removeAllItems();
		container.addAll(tasks);
		
		if(versionId == 0) {
			container.sort(new Object[]{"version", "priority"}, new boolean[] {true, false}); 
		} else {
			container.sort(new Object[]{"priority"}, new boolean[] {false});
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		setUser();
	}
}
