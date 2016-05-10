package com.example.bugrap.controllers;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.bugrap.model.Distribution;
import com.example.bugrap.model.Model;
import com.example.bugrap.model.Status;
import com.example.bugrap.model.Task;
import com.example.bugrap.model.Type;
import com.example.bugrap.model.User;
import com.example.bugrap.model.Version;
import com.vaadin.incubator.bugrap.model.projects.Project;
import com.vaadin.incubator.bugrap.model.projects.ProjectVersion;
import com.vaadin.incubator.bugrap.model.reports.Comment;
import com.vaadin.incubator.bugrap.model.reports.Report;
import com.vaadin.incubator.bugrap.model.reports.ReportPriority;
import com.vaadin.incubator.bugrap.model.reports.ReportStatus;
import com.vaadin.incubator.bugrap.model.reports.ReportType;
import com.vaadin.incubator.bugrap.model.users.Reporter;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class ReportsController {

	/**
	 * @param tasks 
	 * 
	 */
	public void openTaskDescription(Long taskId) {
		UI.getCurrent().getNavigator().navigateTo("report/"+taskId);
	}

	/**
	 * 
	 * @param projectId
	 * @return
	 */
	public List<ProjectVersion> getProjectVersions(Project project) {
		return Model.getVersions(project);
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<Project> getProjects(long userId) {
		return Model.getProjects().stream()
				.filter(project -> project.getManager().getId() == userId)
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @param projectId
	 * @return
	 */
	public Distribution getDistribution(Project project) {
		int closed = 0;
		int notClosed = 0;
		int unnasigned = 0;
		
		List<Report> tasks = Model.getTasks(project);
		for(Report task : tasks) {
			if(task.getStatus() == ReportStatus.CLOSED) {
				++closed;
			} else {
				++notClosed;
			}
			
			if(task.getAssigned() == null)
				++unnasigned;
		}
		
		return new Distribution(closed, notClosed, unnasigned);
	}

	/**
	 * 
	 * @param projectId
	 * @param versionId 
	 * @return
	 */
	public List<Report> getTasks(Project project, long versionId) {
		return Model.getTasks(project).stream()
				.filter(task -> task.getVersion().getId() == versionId || versionId == 0)
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @return
	 */
	public Reporter getCurrentUser() {
		return Model.getUsers().stream().filter(user -> user.getId() == 2).findFirst().get();
	}

	public void logout() {
		// TODO logout
		
	}

	public Report getAvgTask(Project project, Set<Long> tasks) {
		Report res = null;
		
		Iterator<Report> iter = Model.getTasks(project).stream().filter(task -> tasks.contains(task.getId())).iterator();
		while(iter.hasNext()) {
			Report task = iter.next();
			
			if(res == null) {
				res = new Report();
				res.setId(task.getId());
				res.setAssigned(task.getAssigned());
				res.setAuthor(task.getAuthor());
				res.setConsistencyVersion(task.getConsistencyVersion());
				res.setDescription(task.getDescription());
				res.setOccursIn(task.getOccursIn());
				res.setPriority(task.getPriority());
				res.setProject(task.getProject());
				res.setResolution(task.getResolution());
				res.setStatus(task.getStatus());
				res.setSummary(task.getSummary());
				res.setTimestamp(task.getTimestamp());
				res.setType(task.getType());
				res.setVersion(task.getVersion());
				continue;
			} else {
				res.setId(0);
			}
			
			if(res.getPriority() != null && res.getPriority().equals(task.getPriority()) == false)
				res.setPriority(null);
			if(res.getType() != null && res.getType().equals(task.getType()) == false)
				res.setType(null);
			if(res.getStatus() != null && res.getStatus().equals(task.getStatus()) == false)
				res.setStatus(null);
			if(res.getAssigned() != null && res.getAssigned().equals(task.getAssigned()) == false)
				res.setAssigned(null);
			if(res.getVersion() != null && res.getVersion().equals(task.getVersion()) == false)
				res.setVersion(null);
		}
		
		return res;
	}

	public List<Reporter> getUsers() {
		return Model.getUsers();
	}

	public List<ReportType> getTypes() {
		return Model.getTypes();
	}

	public List<ReportStatus> getStatuses() {
		return Model.getStatuses();
	}

	public Report getTask(long taskId) {
		return Model.getTask(taskId);
	}

	public List<Report> getTasks(Project project, Set<Long> currentTasks) {
		return Model.getTasks(project).stream().filter(task -> currentTasks.contains(task.getId())).collect(Collectors.toList());
	}

	public List<ReportPriority> getPriorities() {
		return Model.getPriorities();
	}

	public List<Comment> getComments(Report task) {
		return Model.getComments(task);
	}
}
