package org.cloudsimplus.testbeds;

import java.util.ArrayList;
import java.util.List;

public class ImageRepository {
	private int id;
	private List<Image> images;
	private int fullSize;
	private int freeSize;

	public ImageRepository(int id, int fullSize) {
		super();
		this.fullSize = fullSize;
		this.freeSize = fullSize;
		this.images = new ArrayList<Image>();
	}

	public ImageRepository(int id, List<Image> images, int fullSize) {
		super();
		this.images = images;
		this.fullSize = fullSize;
		this.freeSize = fullSize;
	}
	
	public int addImage( Image obj ) {
		if ( this.freeSize - obj.getActualSize() < 0 ) {
			return -1;
		} else {
			this.images.add(obj);
			this.freeSize = this.freeSize - obj.getActualSize();
			return 1;
		}
	}
	
	public Image getImageById ( int id ) {
		this.images.get( id ).accessImage();
		return this.images.get( id );
	}
	
	public int getImageIdByName ( String name ) {
		for ( int i = 0; i < this.images.size(); i++  ) {
			Image im = this.images.get(i);
			if ( im.getName().equals( name ) ) {
				return i;
			}
		}
		return -1;
	}
	
	public void deleteImageById ( int id ) {
		Image obj = this.images.get( id );
		this.freeSize = this.freeSize + obj.getActualSize();
		this.images.remove( id );
	}
	
	public List<Image> getImageList () {
		return this.images;
	}

	public int getFullSize() {
		return fullSize;
	}

	public void setFullSize(int fullSize) {
		this.fullSize = fullSize;
	}

	public int getFreeSize() {
		return freeSize;
	}

	public void setFreeSize(int freeSize) {
		this.freeSize = freeSize;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getAllAccessAmount() {
		int sum = 0;
		for ( int i = 0; i < images.size(); i++ ) {
			sum += images.get(i).getAccessedCount();
		}
		return sum;
	}
	
	public int getMaxAccessAmount() {
		int max = 0;
		for ( int i = 0; i < images.size(); i++ ) {
			if ( images.get(i).getAccessedCount() > max ) {
				max = images.get(i).getAccessedCount();
			}
		}
		return max;
	}

}
