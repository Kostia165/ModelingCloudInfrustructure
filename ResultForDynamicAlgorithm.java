package org.cloudsimplus.testbeds;

import java.util.List;

public class ResultForDynamicAlgorithm {
	private int funcRes;
	private List<Image> imageList;

	public ResultForDynamicAlgorithm() {
	}

	public ResultForDynamicAlgorithm(int funcRes, List<Image> imageList) {
		super();
		this.funcRes = funcRes;
		this.imageList = imageList;
	}

	public int getFuncRes() {
		return funcRes;
	}

	public void setFuncRes(int funcRes) {
		this.funcRes = funcRes;
	}

	public List<Image> getImageList() {
		return imageList;
	}

	public void setImageList(List<Image> imageList) {
		this.imageList = imageList;
	}
}
