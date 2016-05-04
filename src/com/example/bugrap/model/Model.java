package com.example.bugrap.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 
 * @author nikolaigorokhov
 *
 */
public class Model {
	private static List<User> users = Arrays.asList(new User[] {
			new User(1, "Nikolai Gorokhov"), 
			new User(2, "Marc Manager"), 
			new User(3, "King of Zimbabve"), 
			new User(4, "Linus Torvalds")
		});
	
	private static List<Project> projects = Arrays.asList(new Project[] {
			new Project(1, "Project name that is rather long pellentesque habitant morbi"),
			new Project(2, "Another, but very cool project")
		});
	
	private static List<Version> versions = Arrays.asList(new Version[] {
			new Version(1, projects.get(0), "1.2.3-pre12"),
			new Version(2, projects.get(0), "1.3"),
			new Version(3, projects.get(1), "0.9.5")
		});
	
	private static List<Type> types = Arrays.asList(new Type[] {
			new Type(1, "Bug"),
			new Type(2, "Feature")
		});
	
	private static List<Status> statuses = Arrays.asList(new Status[] {
			new Status(1, "Open"),
			new Status(2, "Closed"),
			new Status(3, "Reopened"),
			new Status(4, "Fixed"),
			new Status(5, "Works for me")
		});
	
	private static List<Task> tasks = new LinkedList<>();
	
	static {
		Map<Integer, Project> projects = getProjects().stream().collect(Collectors.toMap(Project::getId, Function.identity()));
		Map<Integer, Version> versions = getVersion().stream().collect(Collectors.toMap(Version::getId, Function.identity()));
		Map<Integer, Type> types = getTypes().stream().collect(Collectors.toMap(Type::getId, Function.identity()));
		Map<Integer, User> users = getUsers().stream().collect(Collectors.toMap(User::getId, Function.identity()));
		Map<Integer, Status> statuses = getStatuses().stream().collect(Collectors.toMap(Status::getId, Function.identity()));
		
		Task task1 = new Task(1, versions.get(1), 5, types.get(1), "Panel child component hierarchy is invalid", users.get(2), null, "15 mins ago");
		task1.setStatus(statuses.get(1));
		task1.setComments("Comment 1");
		task1.setProject(projects.get(1));
		tasks.add(task1);
		
		Task task2 = new Task(2, versions.get(1), 3, types.get(1), "Menubar \"bottleneck\" usability problem", users.get(2), "30 mins ago", "2 hours ago");
		task2.setStatus(statuses.get(1));
		task2.setComments("Comment 1");
		task2.setProject(projects.get(1));
		tasks.add(task2);
		
		Task task3 = new Task(3, versions.get(1), 2, types.get(2), "Improve layout support", users.get(2), null, "6 days ago");
		task3.setStatus(statuses.get(1));
		task3.setComments("Comment 1");
		task3.setProject(projects.get(1));
		tasks.add(task3);
		
		Task task4 = new Task(4, versions.get(1), 2, types.get(1), "Fix chrome theme identifier", users.get(2), "2 weeks ago", "1 month ago");
		task4.setStatus(statuses.get(1));
		task4.setComments("Comment 1");
		task4.setProject(projects.get(1));
		tasks.add(task4);
		
		Task task5 = new Task(5, versions.get(2), 4, types.get(1), "One bug in this version", users.get(1), null, "1 month ago");
		task5.setStatus(statuses.get(3));
		task5.setComments("Comment 1");
		task5.setProject(projects.get(1));
		tasks.add(task5);
		
		Task task6 = new Task(6, versions.get(3), 2, types.get(2), "Improve layout support P2", users.get(2), null, "6 days ago");
		task6.setStatus(statuses.get(1));
		task6.setComments("Comment 1");
		task6.setProject(projects.get(2));
		tasks.add(task6);
		
		Task task7 = new Task(7, versions.get(3), 2, types.get(1), "Fix chrome theme identifier P2", users.get(4), "2 weeks ago", "1 month ago");
		task7.setStatus(statuses.get(1));
		task7.setComments("Comment 1");
		task7.setProject(projects.get(2));
		tasks.add(task7);
		
		Task task8 = new Task(8, versions.get(3), 4, types.get(1), "One bug in this version P2", users.get(3), null, "1 month ago");
		task8.setStatus(statuses.get(3));
		task8.setComments("Comment 1");
		task8.setProject(projects.get(2));
		tasks.add(task8);
	}
	
	public static List<User> getUsers() {
		return users;
	}
	
	public static List<Project> getProjects() {
		return projects;
	}
	
	public static List<Version> getVersion() {
		return versions;
	}
	
	public static List<Type> getTypes() {
		return types;
	}
	
	public static List<Status> getStatuses() {
		return statuses;
	}
	
	public static List<Task> getTasks() {
		return tasks;
	}
}
