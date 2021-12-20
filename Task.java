package org.cloudsimplus.testbeds;

public class Task {
	private int taskSize;
	private int deadline;
	private Image requiredImage;
	
	private int requiredMips;

	public Task(int taskSize, int deadline, Image requiredImage) {
		super();
		this.taskSize = taskSize;
		this.deadline = deadline;
		this.requiredImage = requiredImage;
		
		this.requiredMips = this.taskSize / this.deadline;
		this.requiredImage.setTask(this);
	}

	public int getTaskSize() {
		return taskSize;
	}

	public void setTaskSize(int taskSize) {
		this.taskSize = taskSize;
	}

	public int getDeadline() {
		return deadline;
	}

	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}

	public Image getRequiredImage() {
		return requiredImage;
	}

	public void setRequiredImage(Image requiredImage) {
		this.requiredImage = requiredImage;
	}
	
	public int getRequiredMips() {
		return requiredMips;
	}

}
