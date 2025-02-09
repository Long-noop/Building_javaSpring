package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

@Repository
public class BuildingreporsitoryImpl implements BuildingRepository {
	private final String url = "jdbc:mysql://localhost:3306/build";
	static final String username = "root";
	static final String password = "Long@12345";

	@Override
	public List<BuildingEntity> findAll(String name, Integer districtID) {
		StringBuilder sql = new StringBuilder("SELECT * FROM building b WHERE 1 = 1 ");
		if (name != null && !name.equals("")) {
			sql.append("AND b.name like '%" + name + "%'");
		}
		if (districtID != null) {
			sql.append("AND b.districtid =" + districtID);

		}
		List<BuildingEntity> result = new ArrayList<BuildingEntity>();
		try (Connection conn = DriverManager.getConnection(url, username, password);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString());) {
			while (rs.next()) {
				BuildingEntity building = new BuildingEntity();
				building.setName(rs.getString("name"));
				building.setNumberOfBasement(rs.getInt("numberofbasement"));
				building.setStreet(rs.getString("street"));
				building.setWard(rs.getString("ward"));
				result.add(building);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Connect to DB failed...");

		}
		return result;
	}
}
