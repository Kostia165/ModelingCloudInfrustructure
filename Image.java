package org.cloudsimplus.testbeds;

public class Image {
	private String name;
	private int actualSize;
	private int deploymentSize;
	private int deploymentRom;
	private int deploymentMips;
	private int deploymentPesNumber;
	private int accessedCount;
	private Task task;
	private int fromRepository;
	private int AimedForHost = -1;

	public Image(int actualSize, int deploymentSize, int deploymentRom, int deploymentMips, int deploymentPesNumber, String name) {
		super();
		this.actualSize = actualSize;
		this.deploymentSize = deploymentSize;
		this.deploymentRom = deploymentRom;
		this.deploymentMips = deploymentMips;
		this.deploymentPesNumber = deploymentPesNumber;
		this.accessedCount = 0;
		this.name = name;
		this.fromRepository = 0;
	}

	public int getActualSize() {
		return actualSize;
	}

	public void setActualSize(int actualSize) {
		this.actualSize = actualSize;
	}

	public int getDeploymentSize() {
		return deploymentSize;
	}

	public void setDeploymentSize(int deploymentSize) {
		this.deploymentSize = deploymentSize;
	}

	public int getDeploymentRom() {
		return deploymentRom;
	}

	public void setDeploymentRom(int deploymentRom) {
		this.deploymentRom = deploymentRom;
	}

	public int getDeploymentMips() {
		return deploymentMips;
	}

	public void setDeploymentMips(int deploymentMips) {
		this.deploymentMips = deploymentMips;
	}

	public int getDeploymentPesNumber() {
		return deploymentPesNumber;
	}

	public void setDeploymentPesNumber(int deploymentPesNumber) {
		this.deploymentPesNumber = deploymentPesNumber;
	}
	
	public int getAccessedCount() {
		return accessedCount;
	}

	public void accessImage() {
		this.accessedCount++;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public int getFromRepository() {
		return fromRepository;
	}

	public void setFromRepository(int fromRepository) {
		this.fromRepository = fromRepository;
	}

	public int getAimedForHost() {
		return AimedForHost;
	}

	public void setAimedForHost(int aimedForHost) {
		AimedForHost = aimedForHost;
	}

	@Override
	public String toString() {
		return "Image [name=" + name + ", actualSize=" + actualSize + ", deploymentSize=" + deploymentSize
				+ ", deploymentRom=" + deploymentRom + ", deploymentMips=" + deploymentMips + ", deploymentPesNumber="
				+ deploymentPesNumber + ", accessedCount=" + accessedCount + ", task=" + task + "]";
	}
}
