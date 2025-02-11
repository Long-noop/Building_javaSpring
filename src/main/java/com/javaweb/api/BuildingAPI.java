package com.javaweb.api;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.Model.BuildingDTO;
import com.javaweb.Model.BuildingRequestDTO;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.service.BuildingService;

@RestController
public class BuildingAPI {
	@Autowired
	private BuildingService buildingSer;

	@PersistenceContext
	private EntityManager entity;

	@GetMapping(value = "/api/building/")
	public Object getBuilding(@RequestParam Map<String, Object> param,
			@RequestParam(name = "typeCode", required = false) List<String> typeCode) {
		List<BuildingDTO> result = buildingSer.findAll(param, typeCode);
		return result;
	}

//	public void validate(BuildingDTO buildingDTO) {
//		if (buildingDTO.getName().equals("") | buildingDTO.getName() == null
//				| buildingDTO.getNumberOfBasement() == null)
//			throw new CustomRequiredException("Name or num not null");
//	}

	@PostMapping(value = "/api/building")
	@Transactional
	public void createBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
		BuildingEntity buildEntity = new BuildingEntity();
		buildEntity.setName(buildingRequestDTO.getName());
		buildEntity.setStreet(buildingRequestDTO.getStreet());
		buildEntity.setWard(buildingRequestDTO.getWard());

		DistrictEntity districtEntity = new DistrictEntity();
		districtEntity.setId(buildingRequestDTO.getDistrictid());
		buildEntity.setDistrict(districtEntity);
		entity.persist(buildEntity);

	}

	@PutMapping(value = "/api/building")
	@Transactional
	public void updateBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
		BuildingEntity buildEntity = new BuildingEntity();
		buildEntity.setId(7L);
		buildEntity.setName(buildingRequestDTO.getName());
		buildEntity.setStreet(buildingRequestDTO.getStreet());
		buildEntity.setWard(buildingRequestDTO.getWard());

		DistrictEntity districtEntity = new DistrictEntity();
		districtEntity.setId(buildingRequestDTO.getDistrictid());
		buildEntity.setDistrict(districtEntity);
		entity.merge(buildEntity);

	}

	@DeleteMapping(value = "/api/building/{id}")
	@Transactional
	public void deleteBuilding(@PathVariable Long id) {
		BuildingEntity buildingEntity = entity.find(BuildingEntity.class, id);
		entity.remove(buildingEntity);
	}

}
