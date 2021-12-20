package org.cloudsimplus.testbeds;

import org.cloudbus.cloudsim.cloudlets.CloudletSimple;

public class ExtendedCloudlet extends CloudletSimple {

	public ExtendedCloudlet(long length, int pesNumber) {
		super(length, pesNumber);
	}

	private int fromRepository;

	public int getFromRepository() {
		return fromRepository;
	}

	public void setFromRepository(int fromRepository) {
		this.fromRepository = fromRepository;
	}
	
	public void addTimeForLoadingImage(int delta) {
		this.setFinishTime(this.getFinishTime() + delta);
	}

}
