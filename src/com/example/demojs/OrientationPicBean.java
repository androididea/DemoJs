package com.example.demojs;

public class OrientationPicBean {
	private Integer heading;
	private String id;

	public OrientationPicBean() {

	}

	public OrientationPicBean(Integer heading,String id) {
		this.heading = heading;
		this.id = id;
		
	}

	public Integer getHeading() {
		return heading;
	}

	public void setHeading(Integer heading) {
		this.heading = heading;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
