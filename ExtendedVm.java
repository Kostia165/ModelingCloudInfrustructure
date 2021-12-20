package org.cloudsimplus.testbeds;

import org.cloudbus.cloudsim.schedulers.cloudlet.CloudletScheduler;
import org.cloudbus.cloudsim.vms.Vm;
import org.cloudbus.cloudsim.vms.VmSimple;

public class ExtendedVm extends VmSimple {
	
	Image requiredImage;

	public ExtendedVm(Vm sourceVm) {
		super(sourceVm);
		// TODO Auto-generated constructor stub
	}

	public ExtendedVm(double mipsCapacity, long numberOfPes) {
		super(mipsCapacity, numberOfPes);
		// TODO Auto-generated constructor stub
	}

	public ExtendedVm(double mipsCapacity, long numberOfPes, CloudletScheduler cloudletScheduler) {
		super(mipsCapacity, numberOfPes, cloudletScheduler);
		// TODO Auto-generated constructor stub
	}

	public ExtendedVm(long id, double mipsCapacity, long numberOfPes) {
		super(id, mipsCapacity, numberOfPes);
		// TODO Auto-generated constructor stub
	}

	public ExtendedVm(long id, long mipsCapacity, long numberOfPes) {
		super(id, mipsCapacity, numberOfPes);
		// TODO Auto-generated constructor stub
	}

	public Image getRequiredImage() {
		return requiredImage;
	}

	public void setRequiredImage(Image requiredImage) {
		this.requiredImage = requiredImage;
	}

}
