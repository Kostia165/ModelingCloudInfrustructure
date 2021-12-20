package org.cloudsimplus.testbeds;

import org.cloudbus.cloudsim.brokers.DatacenterBroker;
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple;
import org.cloudbus.cloudsim.cloudlets.Cloudlet;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.datacenters.Datacenter;
import org.cloudbus.cloudsim.datacenters.DatacenterSimple;
import org.cloudbus.cloudsim.distributions.ContinuousDistribution;
import org.cloudbus.cloudsim.distributions.UniformDistr;
import org.cloudbus.cloudsim.hosts.Host;
import org.cloudbus.cloudsim.hosts.HostSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.ResourceProvisionerSimple;
import org.cloudbus.cloudsim.resources.Pe;
import org.cloudbus.cloudsim.resources.PeSimple;
import org.cloudbus.cloudsim.schedulers.cloudlet.CloudletSchedulerSpaceShared;
import org.cloudbus.cloudsim.schedulers.vm.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelFull;
import org.cloudbus.cloudsim.vms.Vm;
import org.cloudbus.cloudsim.vms.VmSimple;
import org.cloudsimplus.builders.tables.CloudletsTableBuilder;
import org.cloudsimplus.listeners.CloudletVmEventInfo;
import org.cloudsimplus.listeners.EventInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public final class MyAlgorithm {
    private static final int    SCHEDULE_INTERVAL = 5;
    private static final double TIME_TO_TERMINATE_SIMULATION = 120;

    private static final int HOSTS = 3;

    private static final int    HOST_MIPS = 10000; //for each PE

    private static final int    HOST_INITIAL_PES = 4;
    private static final long   HOST_RAM = 100000; //host memory (MB)
    private static final long   HOST_STORAGE = 20000; //host storage

    private static final long   HOST_BW = 40000L; //Mb/s

    private static final double VM_BW = 1000L;
 

    private static final long   CLOUDLET_FILESIZE = 300;
    
    private int downSpeed = 2000;

    private CloudSim simulation;
    private List<Host> hostList;
    private DatacenterBroker broker;
    
    private List<Cloudlet> additionalCloudletFinishedList = new ArrayList<>();
    private final ContinuousDistribution random;
    
    private List<Task> tasks = new ArrayList<>();
    Datacenter datacenter1;

    private List<ImageRepository> repositoryList = new ArrayList<>();
    ImageRepository remoteRepository;
    ImageRepository remoteRepository2;
    ImageRepository remoteRepository3;
    
    int [][] hostNetwork = { { 0, 100, 10000 }, { 100, 0, 2000 }, { 10000, 2000, 0 } };
    
    private int tasksForCompleting = 0;

    public static void main(String[] args) {
        new MyAlgorithm();
    }

    private MyAlgorithm(){
        /*Enables just some level of log messages.
          Make sure to import org.cloudsimplus.util.Log;*/
        //Log.setLevel(ch.qos.logback.classic.Level.WARN);

        System.out.println("Starting " + getClass().getSimpleName());
        simulation = new CloudSim();

        datacenter1 = createDatacenter();
        random = new UniformDistr();
        simulation.terminateAt(TIME_TO_TERMINATE_SIMULATION);
        broker = new DatacenterBrokerSimple(simulation);

        remoteRepository = new ImageRepository(1, 400000);
        remoteRepository2 = new ImageRepository(2, 300000);
        remoteRepository3 = new ImageRepository(3, 200000);
        repositoryList.add(remoteRepository);
        repositoryList.add(remoteRepository2);
        repositoryList.add(remoteRepository3);

        remoteRepository.addImage(new Image(20000, 12000, 4000, 9000, 1, "image1"));
        remoteRepository.addImage(new Image(45000, 4000, 5600, 2000, 1, "image4"));
        remoteRepository2.addImage(new Image(12000, 7000, 3000, 7000, 1, "image3"));
        remoteRepository3.addImage(new Image(30000, 13000, 4500, 12000, 1, "image2"));

        tasks.add(new Task(10000, 12, new Image(24000, 1000, 1000, 9000, 1, "image5")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image3")));
        tasks.add(new Task(3000, 9, new Image(30000, 13000, 4500, 12000, 1, "image2")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image1")));
        tasks.add(new Task(10000, 12, new Image(24000, 1000, 1000, 9000, 1, "image5")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image3")));
        tasks.add(new Task(3000, 9, new Image(30000, 13000, 4500, 12000, 1, "image2")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image4")));
        tasks.add(new Task(10000, 12, new Image(24000, 1000, 1000, 9000, 1, "image5")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image3")));
        tasks.add(new Task(3000, 9, new Image(30000, 13000, 4500, 12000, 1, "image2")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image1")));
        tasks.add(new Task(10000, 12, new Image(24000, 1000, 1000, 9000, 1, "image5")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image3")));
        tasks.add(new Task(3000, 9, new Image(30000, 13000, 4500, 12000, 1, "image2")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image4")));
        tasks.add(new Task(10000, 12, new Image(24000, 1000, 1000, 9000, 1, "image5")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image3")));
        tasks.add(new Task(3000, 9, new Image(30000, 13000, 4500, 12000, 1, "image2")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image1")));
        tasks.add(new Task(10000, 12, new Image(24000, 1000, 1000, 9000, 1, "image5")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image3")));
        tasks.add(new Task(3000, 9, new Image(30000, 13000, 4500, 12000, 1, "image2")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image4")));
        tasks.add(new Task(10000, 12, new Image(24000, 1000, 1000, 9000, 1, "image5")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image4")));
        tasks.add(new Task(3000, 9, new Image(30000, 13000, 4500, 12000, 1, "image2")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image3")));
        tasks.add(new Task(10000, 12, new Image(24000, 1000, 1000, 9000, 1, "image5")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image4")));
        tasks.add(new Task(3000, 9, new Image(30000, 13000, 4500, 12000, 1, "image2")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image1")));
        tasks.add(new Task(10000, 12, new Image(24000, 1000, 1000, 9000, 1, "image5")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image1")));
        tasks.add(new Task(3000, 9, new Image(30000, 13000, 4500, 12000, 1, "image2")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image4")));
        tasks.add(new Task(10000, 12, new Image(24000, 1000, 1000, 9000, 1, "image5")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image1")));
        tasks.add(new Task(3000, 9, new Image(30000, 13000, 4500, 12000, 1, "image2")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image4")));
        tasks.add(new Task(10000, 12, new Image(24000, 1000, 1000, 9000, 1, "image5")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image3")));
        tasks.add(new Task(3000, 9, new Image(30000, 13000, 4500, 12000, 1, "image2")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image3")));
        tasks.add(new Task(10000, 12, new Image(24000, 1000, 1000, 9000, 1, "image5")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image1")));
        tasks.add(new Task(3000, 9, new Image(30000, 13000, 4500, 12000, 1, "image2")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image3")));
        tasks.add(new Task(10000, 12, new Image(24000, 1000, 1000, 9000, 1, "image5")));
        tasks.add(new Task(17000, 5, new Image(12000, 7000, 3000, 7000, 1, "image4")));
        tasks.add(new Task(3000, 9, new Image(30000, 13000, 4500, 12000, 1, "image2")));

        createAndSubmitVms(broker);

        simulation.addOnClockTickListener(this::createRandomCloudlets);
		simulation.start();

		final List<Cloudlet> finishedList = additionalCloudletFinishedList;

		System.out.println("Amount of completed tasks: " + Integer.toString(finishedList.size()));
		
		finishedList.sort( Comparator.comparingLong((Cloudlet c) ->
		c.getVm().getHost().getId()) .thenComparingLong(c -> c.getVm().getId())); new
		CloudletsTableBuilder(finishedList).build();
		System.out.println(getClass().getSimpleName() + " finished!");
		countAndShowTimeOfExecutingAllTasks();
		countAndShowPossibleCompletedTasksWithoutReschedling();
    }
    
    private void countAndShowPossibleCompletedTasksWithoutReschedling () {
    	int startTime =  1;
    	
    	int count = 0;
    	for ( int i = 0; i < additionalCloudletFinishedList.size(); i++ ) {
    		if ( (int) additionalCloudletFinishedList.get(i).getExecStartTime() == startTime ) {
    			count++;
    		}
    	}
    	
    	System.out.println("Amount of tasks executed in the first queue: ");
    	System.out.println(count);
    }
    
    private void countAndShowTimeOfExecutingAllTasks () {
    	double startTime =  1;
    	
    	double max = 1;
    	for ( int i = 0; i < additionalCloudletFinishedList.size(); i++ ) {
    		if ( (int) additionalCloudletFinishedList.get(i).getFinishTime() > max ) {
    			max = additionalCloudletFinishedList.get(i).getFinishTime();
    		}
    	}
    	
    	double result = max - startTime;
    	System.out.println("Time of executing all tasks: ");
    	System.out.println(result);
    }

    public Cloudlet createCloudlet(Vm vm, DatacenterBroker broker, Image image) {
        final Cloudlet cloudlet =
            new ExtendedCloudlet(image.getTask().getTaskSize(), 1)
                .setFileSize(CLOUDLET_FILESIZE)
                .setOutputSize(CLOUDLET_FILESIZE)
                .setUtilizationModel(new UtilizationModelFull());
        broker.bindCloudletToVm(cloudlet, vm);
        cloudlet.addOnFinishListener(this::cloudletFinishListener);
        ((ExtendedCloudlet)cloudlet).setFromRepository(image.getFromRepository());
        return cloudlet;
    }
    
    private void cloudletFinishListener(final CloudletVmEventInfo info) {
    	ExtendedCloudlet cl = (ExtendedCloudlet) info.getCloudlet();
    	int loadImageTime = CountImageLoadingTime(cl.getFromRepository() - 1,  (int) cl.getVm().getHost().getId(), cl.getVm().getStorage().getCapacity());
    	cl.addTimeForLoadingImage(loadImageTime);
    	additionalCloudletFinishedList.add(cl);
    	info.getCloudlet().getBroker().destroyVm(info.getCloudlet().getVm());
    	
    	tasksForCompleting--;
    	if ( tasksForCompleting == 0 ) {
    		createAndSubmitVms(broker);
    	}
    }

    private void ReplicateImagesInRepos() {
    	int from, to;
    	do {
    		from = randInt(0, 2);
        	to = randInt(0, 2);
    	} while (from == to);

    	ImageRepository fromRep = repositoryList.get(from);
        ImageRepository toRep = repositoryList.get(to);
        
        double replBorder = 0.1d;
        double delBorder = 0.01d;
        
        int maxAccess = fromRep.getMaxAccessAmount();
        if ( maxAccess == 0 ) {
        	return;
        }
        
        List <Image>repImages = fromRep.getImageList();
        for ( int i = 0; i < repImages.size(); i++ ) {
        	Image im = repImages.get(i);
        	double popul = im.getAccessedCount() / maxAccess;
        	if ( popul > replBorder ) {
        		if ( toRep.addImage(im) == -1 ) {
        			//System.out.println("Image is not replicated to another repository");
        		}
        	} else if ( popul < delBorder ) {
        		fromRep.deleteImageById(i);
        	}
        }
	}

	private int CountImageLoadingTime(int i, int id, long capacity) {
		if ( i == id ) {
			return 0;
		} else if ( i == -1 ) {
			return (int) (capacity / downSpeed);
		} else {
			int getSpeed = hostNetwork[i][id];
			return (int) (capacity / getSpeed);
		}
		
	}

	public void createAndSubmitVms(DatacenterBroker broker) {
		if (tasks.size() == 0) {
			return;
		};
    	List<Image> chosenImages = new ArrayList<>();
    	System.out.println("TASK SIZE: ");
    	System.out.println(tasks.size());
    	for ( int i = 0; i < 3; i++ ) {
    		if ( tasks.size() < 3 ) {
    			return;
    		}
    		Image im = tasks.get(i).getRequiredImage();
    		int idOfIm = -1;
    		for ( int j = 0; j < repositoryList.size(); j++ ) {
    			idOfIm = repositoryList.get(j).getImageIdByName(im.getName());
    			if ( idOfIm != -1 ) {
    				Image image1 = repositoryList.get(j).getImageById(idOfIm);
    				image1.setTask(tasks.get(i));
    				image1.setFromRepository(j + 1);
        			chosenImages.add(image1);
        			tasks.remove(tasks.get(i));
        			break;
    			}
    		}
    		if ( idOfIm == -1 ) {
    			chosenImages.add(tasks.get(i).getRequiredImage());
    			repositoryList.get(randInt(0, 2)).addImage(tasks.get(i).getRequiredImage());
    			tasks.remove(tasks.get(i));
    		}
    	}
    	
    	System.out.println("TASK SIZE: ");
    	System.out.println(tasks.size());
    	
    	List<Vm> Vmlist = new ArrayList<>();
    	List<Cloudlet> ClList = new ArrayList<>();
    	for (int i = 0; i < chosenImages.size(); i++) {
    		System.out.println(chosenImages.get(i));
    	}
    	
    	List<ResultForDynamicAlgorithm> results = new ArrayList<>();

    	//To check all placements for virtual machines
    	List<Image> tempImageList;
    	// First case
    	tempImageList = new ArrayList<>();
    	tempImageList.add(chosenImages.get(0));
    	tempImageList.add(chosenImages.get(1));
    	tempImageList.add(chosenImages.get(2));
    	results.add(CalculateFunctionValueForImageAllocation(tempImageList));
    	
    	// Second case
    	tempImageList = new ArrayList<>();
    	tempImageList.add(chosenImages.get(0));
    	tempImageList.add(chosenImages.get(2));
    	tempImageList.add(chosenImages.get(1));
    	results.add(CalculateFunctionValueForImageAllocation(tempImageList));
    	
    	// Third case
    	tempImageList = new ArrayList<>();
    	tempImageList.add(chosenImages.get(1));
    	tempImageList.add(chosenImages.get(0));
    	tempImageList.add(chosenImages.get(2));
    	results.add(CalculateFunctionValueForImageAllocation(tempImageList));
    	
    	// Fourth case
    	tempImageList = new ArrayList<>();
    	tempImageList.add(chosenImages.get(1));
    	tempImageList.add(chosenImages.get(2));
    	tempImageList.add(chosenImages.get(0));
    	results.add(CalculateFunctionValueForImageAllocation(tempImageList));
    	
    	// Fifth case
    	tempImageList = new ArrayList<>();
    	tempImageList.add(chosenImages.get(2));
    	tempImageList.add(chosenImages.get(1));
    	tempImageList.add(chosenImages.get(0));
    	results.add(CalculateFunctionValueForImageAllocation(tempImageList));
    	
    	// Sixth case
    	tempImageList = new ArrayList<>();
    	tempImageList.add(chosenImages.get(2));
    	tempImageList.add(chosenImages.get(0));
    	tempImageList.add(chosenImages.get(1));
    	results.add(CalculateFunctionValueForImageAllocation(tempImageList));
    	
    	//Choose minimal function value
    	int val = 100000;
    	int id = -1;
    	for ( int i = 0; i < results.size(); i++) {
    		int res_val = results.get(i).getFuncRes();
    		if ( res_val < val ) {
    			val = res_val;
    			id = i;
    		}
    	}
    	if ( val == 100000 || id == -1 ) {
    		return;
    	}
    	chosenImages = results.get(id).getImageList();

    	for ( int i = 0; i < chosenImages.size(); i++ ) {
    		chosenImages.get(i).setFromRepository(i + 1);
    		Vm newVm = createVMac(chosenImages.get(i));
    		int hostId = chosenImages.get(i).getAimedForHost();
    		newVm.setHost(hostList.get(hostId));
    		Cloudlet cl = createCloudlet(newVm, broker, chosenImages.get(i));
    		Vmlist.add(newVm);
    		ClList.add(cl);
    	}
    	tasksForCompleting = chosenImages.size();
    	broker.submitVmList(Vmlist);
    	broker.submitCloudletList(ClList);
    }
	
	private ResultForDynamicAlgorithm CalculateFunctionValueForImageAllocation(List<Image> images) {
		List<Host> hostList = datacenter1.getHostList();
		List<Host> myHostList = new ArrayList<>();
		
		for ( int i = 0; i < hostList.size(); i++ ) {
			Host curHost = hostList.get(i);
			
			if ( curHost.getFreePesNumber() == 0 ) {
				continue;
			}
			List<Pe> peList = createPeList(curHost.getFreePesNumber(), HOST_MIPS);
            Host newHost =
                new HostSimple(curHost.getRam().getAvailableResource(), HOST_BW, curHost.getStorage().getAvailableResource(), peList);
            newHost
                .setRamProvisioner(new ResourceProvisionerSimple())
                .setBwProvisioner(new ResourceProvisionerSimple())
                .setVmScheduler(new VmSchedulerTimeShared());
			myHostList.add(newHost);
		}

		List<Image> tasksToPlace = new ArrayList<>();
		if ( myHostList.size() < 3 ) {
			return new ResultForDynamicAlgorithm(0, tasksToPlace);
		}
		int func_res = 0;
		for ( int i = 0; i < images.size(); i++ ) {
			Vm vm = createVMac(images.get(i));
		    Host host = myHostList.get(i);

			if ( host.isSuitableForVm(vm) ) {
				if ( repositoryList.get(i).getImageIdByName(images.get(i).getName()) != -1 ) {
					func_res += 0;
				} else if ( getRepositoryIdWhereImageWithName(images.get(i).getName()) != -1 ) {
					int repId = getRepositoryIdWhereImageWithName(images.get(i).getName());
					int getSpeed = hostNetwork[repId][i];
					func_res += (int) (images.get(i).getActualSize() / getSpeed);
				} else {
					func_res += (int) (images.get(i).getActualSize() / downSpeed);
				}
				tasksToPlace.add(images.get(i));
			} else {
				tasks.add(images.get(i).getTask());
			}
			images.get(i).setAimedForHost(i);
		}

		return new ResultForDynamicAlgorithm(func_res, tasksToPlace);
	}
	
	private int getRepositoryIdWhereImageWithName ( String name ) {
		int repSize = repositoryList.size();
		
		for ( int i = 0; i < repSize; i++ ) {
			if ( repositoryList.get(i).getImageIdByName(name) != -1 ) {
				return i;
			}
		}
		
		return -1;
	}

	public Vm createVMac( Image im ) {
        Vm vm = new VmSimple(im.getDeploymentMips(), im.getDeploymentPesNumber());
        vm
          .setRam(im.getDeploymentRom()).setBw((long)VM_BW).setSize(im.getDeploymentSize())
          .setCloudletScheduler(new CloudletSchedulerSpaceShared());
        return vm;
    }

    private Datacenter createDatacenter() {
        this.hostList = new ArrayList<>();
        for(int i = 0; i < HOSTS; i++){
            final int pes = HOST_INITIAL_PES + i;
            Host host = createHost(pes, HOST_MIPS);
            hostList.add(host);
        }
        System.out.println();

        DatacenterSimple dc = new DatacenterSimple(simulation, hostList);

        hostList.forEach(host -> System.out.printf("#Created %s with %d PEs%n", host, host.getNumberOfPes()));
        System.out.println();

        dc.setSchedulingInterval(SCHEDULE_INTERVAL);
        return dc;
    }

    public Host createHost(int numberOfPes, long mipsByPe) {
            List<Pe> peList = createPeList(numberOfPes, mipsByPe);
            Host host =
                new HostSimple(HOST_RAM, HOST_BW, HOST_STORAGE, peList);
            host
                .setRamProvisioner(new ResourceProvisionerSimple())
                .setBwProvisioner(new ResourceProvisionerSimple())
                .setVmScheduler(new VmSchedulerTimeShared());
            return host;
    }

    public List<Pe> createPeList(int numberOfPEs, long mips) {
        List<Pe> list = new ArrayList<>(numberOfPEs);
        for(int i = 0; i < numberOfPEs; i++) {
            list.add(new PeSimple(mips, new PeProvisionerSimple()));
        }
        return list;
    }
    
    private void createRandomCloudlets(final EventInfo evt) {
    	if((int)evt.getTime() == 10){
    		//ReplicateImages
        	ReplicateImagesInRepos();
    	}
    }
    
    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
    
}
