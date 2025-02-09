package com.javaweb.converter;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.utils.MapUtil;

@Component
public class BuildingSearchBuilderConverter {
	public BuildingSearchBuilder toBuildingSearchBuilder(Map<String, Object> param, List<String> typeCode) {
		BuildingSearchBuilder buildingSearchBuilder = new BuildingSearchBuilder.Builder()
				.setName(MapUtil.getObject(param, "name", String.class))
				.setFloorArea(MapUtil.getObject(param, "floatarea", Integer.class))
				.setNumberOfBasement(MapUtil.getObject(param, "numberOfBasement", Integer.class))
				.setWard(MapUtil.getObject(param, "ward", String.class))
				.setDistrict(MapUtil.getObject(param, "district", String.class))
				.setStreet(MapUtil.getObject(param, "street", String.class))
				.setManagerName(MapUtil.getObject(param, "managerName", String.class))
				.setManagerPhoneNumber(MapUtil.getObject(param, "managerPhoneNumber", String.class))
				.setDirection(MapUtil.getObject(param, "direction", String.class))
				.setLevel(MapUtil.getObject(param, "level", String.class))
				.setRentPriceFrom(MapUtil.getObject(param, "rentPriceFrom", Integer.class))
				.setRentPriceTo(MapUtil.getObject(param, "rentPriceTo", Integer.class))
				.setAreaFrom(MapUtil.getObject(param, "areaFrom", Integer.class))
				.setAreaTo(MapUtil.getObject(param, "areaTo", Integer.class))
				.setStaffId(MapUtil.getObject(param, "staffId", Integer.class)).setTypeCode(typeCode).build();
		;
		return buildingSearchBuilder;
	}
}
