package com.example.bugrap.controllers;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.bugrap.model.Distribution;
import com.example.bugrap.model.Model;
import com.example.bugrap.model.Project;
import com.example.bugrap.model.Status;
import com.example.bugrap.model.Task;
import com.example.bugrap.model.Type;
import com.example.bugrap.model.User;
import com.example.bugrap.model.Version;
import com.vaadin.navigator.Navigator;

public class ReportsController {
	
	private Navigator navigator;

	public ReportsController() {
		
	}
	
	/**
	 * 
	 * @param navigator
	 */
	public ReportsController(Navigator navigator) {
		this.navigator = navigator;
	}

	/**
	 * @param tasks 
	 * 
	 */
	public void openTaskDescription(int taskId) {
		navigator.navigateTo("report/"+taskId);
	}

	/**
	 * 
	 * @param projectId
	 * @return
	 */
	public List<Version> getProjectVersions(int projectId) {
		return Model.getVersion().stream()
				.filter(version -> version.getProject().getId() == projectId)
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<Project> getProjects(int userId) {
		return Model.getProjects();
	}

	/**
	 * 
	 * @param projectId
	 * @return
	 */
	public Distribution getDistribution(int projectId) {
		int closed = 0;
		int notClosed = 0;
		int unnasigned = 0;
		
		Iterator<Task> iter = Model.getTasks().stream().filter(task -> task.getProject().getId() == projectId).iterator();
		while(iter.hasNext()) {
			Task task = iter.next();
			if(task.getStatus().getId() == Status.CLOSED) {
				++closed;
			} else {
				++notClosed;
			}
			
			if(task.getUser() == null)
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
	public List<Task> getTasks(int projectId, int versionId) {
		return Model.getTasks().stream()
				.filter(task -> task.getProject().getId() == projectId)
				.filter(task -> task.getVersion().getId() == versionId || versionId == 0)
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @return
	 */
	public User getCurrentUser() {
		return Model.getUsers().stream().filter(user -> user.getId() == 1).findFirst().get();
	}

	public void logout() {
		// TODO logout
		
	}

	public Task getAvgTask(Set<Integer> tasks) {
		Task res = null;
		
		Iterator<Task> iter = Model.getTasks().stream().filter(task -> tasks.contains(task.getId())).iterator();
		while(iter.hasNext()) {
			Task task = iter.next();
			
			if(res == null) {
				res = new Task(task.getId(), 
								task.getVersion(), 
								task.getPriority(), 
								task.getType(), 
								task.getSummary(), 
								task.getUser(), 
								task.getLastModified(), 
								task.getReported());
				res.setComments(task.getComments());
				res.setProject(task.getProject());
				res.setStatus(task.getStatus());
				continue;
			} else {
				res.setId(0);
			}
			
			if(res.getPriority() != task.getPriority())
				res.setPriority(0);
			if(res.getType() != null && res.getType().equals(task.getType()) == false)
				res.setType(null);
			if(res.getStatus() != null && res.getStatus().equals(task.getStatus()) == false)
				res.setStatus(null);
			if(res.getUser() != null && res.getUser().equals(task.getUser()) == false)
				res.setUser(null);
			if(res.getVersion() != null && res.getVersion().equals(task.getVersion()) == false)
				res.setVersion(null);
		}
		
		return res;
	}

	public List<User> getUsers() {
		return Model.getUsers();
	}

	public List<Type> getTypes() {
		return Model.getTypes();
	}

	public List<Status> getStatuses() {
		return Model.getStatuses();
	}

	public Task getTask(int taskId) {
		return Model.getTasks().stream().filter(task -> task.getId() == taskId).findFirst().orElse(null);
	}

	public List<Task> getTasks(Set<Integer> currentTasks) {
		return Model.getTasks().stream().filter(task -> currentTasks.contains(task.getId())).collect(Collectors.toList());
	}
}
