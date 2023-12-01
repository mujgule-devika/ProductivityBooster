package application;

public class TaskItem {
	
	String title;
	
	String description;
	
	public TaskItem(String title, String description) {
		this.title = title;
		this.description = description;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
}
