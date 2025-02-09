package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.utils.ConnectionJDBCUtil;

@Repository
public class DistrictRepositoryImpl implements DistrictRepository {
	@Override
	public DistrictEntity findNameById(Integer id) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder("SELECT d.name FROM district d WHERE d.id = " + id + ";");
		DistrictEntity districtEntity = new DistrictEntity();
		try (Connection conn = ConnectionJDBCUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString());) {
			while (rs.next()) {
				districtEntity.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Connect to DB failed...");

		}
		return districtEntity;
	}

}
