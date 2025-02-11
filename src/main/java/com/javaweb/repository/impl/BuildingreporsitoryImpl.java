package com.javaweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

@Repository
public class BuildingreporsitoryImpl implements BuildingRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public List<BuildingEntity> findAll(BuildingSearchBuilder building) {
		// TODO Auto-generated method stub
		String sql = "FROM BuildingEntity b";
		Query query = entityManager.createQuery(sql, BuildingEntity.class);
		return query.getResultList();
	}

}
