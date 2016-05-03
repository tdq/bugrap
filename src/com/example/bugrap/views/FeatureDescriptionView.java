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

/**
 * 
 * @author nikolaigorokhov
 *
 */
@SuppressWarnings("serial")
public class FeatureDescriptionView extends FeatureDescriptionDesign implements View {
	
	private ReportsController controller = new ReportsController();
	private ExpandListener expandListener;
	
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
	 */
	public FeatureDescriptionView() {
		expandFeature.addClickListener((event) -> {
			if(expandListener != null)
				expandListener.onExpand();
		});
		
		expandFeature.setVisible(false);
	}

	private void setVersions(int projectId, Version selectedVersion) {
		List<Version> items = controller.getProjectVersions(projectId);
		
		versions.removeAllItems();
		items.forEach(item -> versions.addItem(item));
		
		versions.setValue(selectedVersion);
	}

	private void setUsers(User selectedUser) {
		List<User> items = controller.getUsers();
		
		users.removeAllItems();
		items.forEach(item -> users.addItem(item));
		
		users.setValue(selectedUser);
	}

	private void setStatuses(Status status) {
		List<Status> items = controller.getStatuses();
		
		statuses.removeAllItems();
		items.forEach(item -> statuses.addItem(item));

		statuses.setValue(status);
	}

	private void setTypes(Type selectedType) {
		List<Type> items = controller.getTypes();
		
		types.removeAllItems();
		items.forEach(item -> types.addItem(item));
		
		types.setValue(selectedType);
	}

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

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
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
		} else
			commentsList.setVisible(false);
	}

	private void setComments(String comments) {
		commentsList.setValue(comments);
	}

	private void setLogo(String logo) {
		summary.setValue(logo);
	}
}
