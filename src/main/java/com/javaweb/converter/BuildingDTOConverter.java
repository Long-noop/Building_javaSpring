package com.javaweb.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaweb.Model.BuildingDTO;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.repository.entity.RentAreaEntity;

@Component
public class BuildingDTOConverter {
	@Autowired
	private DistrictRepository districtRepo;

	@Autowired
	private RentAreaRepository rentAreaRepo;
	@Autowired
	private ModelMapper modelMapper;

	public BuildingDTO toBuildingDTO(BuildingEntity item) {
		BuildingDTO building = modelMapper.map(item, BuildingDTO.class);
		building.setName(item.getName());
		DistrictEntity districtEntity = districtRepo.findNameById(item.getDistrictid());
		building.setAddress(item.getStreet() + " " + item.getWard() + " " + districtEntity.getName());
		List<RentAreaEntity> entity = rentAreaRepo.getValueByBuildingId(item.getId());
		String rentResult = entity.stream().map(it -> it.getValue().toString()).collect(Collectors.joining(","));
		building.setRentArea(rentResult);
		return building;
	}
}
