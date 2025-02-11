package com.javaweb.Model;

public class BuildingRequestDTO {
	private String name;
	private String street;
	private String ward;
	private Long districtid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public Long getDistrictid() {
		return districtid;
	}

	public void setDistricid(Long districtid) {
		this.districtid = districtid;
	}

}
