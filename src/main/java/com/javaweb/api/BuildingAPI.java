package com.javaweb.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.Model.BuildingDTO;
import com.javaweb.customExcept.CustomRequiredException;
import com.javaweb.service.BuildingService;

@RestController
public class BuildingAPI {
	@Autowired
	private BuildingService buildingSer;

	@GetMapping(value = "/api/building/")
	public Object getBuilding(@RequestParam Map<String, Object> param,
			@RequestParam(name = "typeCode") List<String> typeCode) {
		List<BuildingDTO> result = buildingSer.findAll(param, typeCode);
		return result;
	}

	public void validate(BuildingDTO buildingDTO) {
		if (buildingDTO.getName().equals("") | buildingDTO.getName() == null
				| buildingDTO.getNumberOfBasement() == null)
			throw new CustomRequiredException("Name or num not null");
	}

}
