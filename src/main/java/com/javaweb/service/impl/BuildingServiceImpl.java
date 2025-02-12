package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.Model.BuildingDTO;
import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.converter.BuildingDTOConverter;
import com.javaweb.converter.BuildingSearchBuilderConverter;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {
	@Autowired
	private BuildingRepository buildRepo;

	@Autowired
	private BuildingDTOConverter buildDTOConvert;

	@Autowired
	private BuildingSearchBuilderConverter buildingSearchBuilderConverter;

	@Override
	public List<BuildingDTO> findAll(Map<String, Object> param, List<String> typeCode) {
		// TODO Auto-generated method stub
		BuildingSearchBuilder buildingSearchBuilder = buildingSearchBuilderConverter.toBuildingSearchBuilder(param,
				typeCode);
		List<BuildingEntity> buildingEntities = buildRepo.findAll(buildingSearchBuilder);
		List<BuildingDTO> result = new ArrayList<BuildingDTO>();
		for (BuildingEntity item : buildingEntities) {
			BuildingDTO building = buildDTOConvert.toBuildingDTO(item);
			result.add(building);
		}
		return result;
	}

}
