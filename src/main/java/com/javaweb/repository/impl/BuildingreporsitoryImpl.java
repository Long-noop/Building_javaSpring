package com.javaweb.repository.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.ConnectionJDBCUtil;
import com.javaweb.utils.NumberUtil;

@Repository
public class BuildingreporsitoryImpl implements BuildingRepository {

	public void joinTable(BuildingSearchBuilder building, StringBuilder sql) {
		Integer staffId = building.getStaffId();
		if (staffId != null) {
			sql.append(" INNER JOIN assignmentbuilding on b.id = assignmentbuilding.buildingid");
		}
		List<String> typeCode = building.getTypeCode();
		if (typeCode != null && typeCode.size() != 0) {
			sql.append(" INNER JOIN buildingrenttype on b.id = buildingrenttype.buildingid");
			sql.append(" INNER JOIN renttype on renttype.id = buildingrenttype.renttypeid");
		}
		Integer areaFrom = building.getAreaFrom();
		Integer areaTo = building.getAreaTo();
		if (areaFrom != null || areaTo != null) {
			sql.append(" INNER JOIN rentarea on rentarea.buildingid = b.id");
		}

	}

	public static void querySpecial(BuildingSearchBuilder building, StringBuilder where) {
		Integer staffId = building.getStaffId();
		if (staffId != null) {
			where.append(" AND assignmentbuilding.staffid =" + staffId);
		}
		Integer rentAreaTo = building.getAreaTo();
		Integer rentAreaFrom = building.getAreaFrom();
		if (rentAreaFrom != null || rentAreaFrom != null) {
			where.append(" AND EXISTS (SELECT * FROM rentarea r WHERE b.id = r.buildingid");
			if (rentAreaFrom != null) {
				where.append(" AND r.value >=" + rentAreaFrom);

			}
			if (rentAreaTo != null) {
				where.append(" AND r.value <=" + rentAreaFrom);
			}
			where.append(")");
		}

		Integer rentPriceTo = building.getRentPriceTo();
		Integer rentPriceFrom = building.getRentPriceFrom();
		if (rentPriceFrom != null || rentPriceTo != null) {
			if (rentPriceFrom != null) {
				where.append(" AND rentprice.value >=" + rentPriceFrom);

			}
			if (rentPriceTo != null) {
				where.append(" AND rentprice.value <=" + rentPriceFrom);
			}
		}

//		if (typeCode != null && typeCode.size() != 0) {
//			List<String> code = new ArrayList<String>();
//			for (String item : typeCode) {
//				code.add("'" + item + "'");
//			}
//			where.append(" AND renttype.code IN(" + String.join(", ", code) + ")");
//		}
		List<String> typeCode = building.getTypeCode();
		if (typeCode != null && typeCode.size() != 0) {
			where.append(" AND (");
			String sql = typeCode.stream().map(it -> "renttype.code LIKE '%" + it + "%'")
					.collect(Collectors.joining(" OR "));
			where.append(sql);
			where.append(")");
		}

	}

	public void queryNormal(BuildingSearchBuilder building, StringBuilder where) {
		try {
			Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
			for (Field item : fields) {
				item.setAccessible(true);
				String fieldName = item.getName();
				if (!fieldName.equals("staffid") && !fieldName.equals("typeCode") && !fieldName.startsWith("area")
						&& !fieldName.startsWith("rentPrice")) {
					Object value = item.get(building);
					if (value != null) {
						if (NumberUtil.isNumber(value.toString())) {
							where.append(" AND b." + fieldName + " = " + value);
						} else {
							where.append(" AND b." + fieldName + " LIKE '%" + value + "%'");

						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder building) {
		StringBuilder sql = new StringBuilder(
				"SELECT b.id, b.name, b.ward, b.street, b.districtid, b.numberofbasement, b.floorarea, b.rentprice, b.managername, b.managerphonenumber, b.servicefee, b.brokeragefee, b.createddate FROM building b");
		joinTable(building, sql);
		StringBuilder where = new StringBuilder(" WhERE 1=1");
		queryNormal(building, where);
		querySpecial(building, where);
		where.append(" GROUP BY b.id;");
		sql.append(where);
		List<BuildingEntity> result = new ArrayList<BuildingEntity>();
		try (Connection conn = ConnectionJDBCUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString());) {
			while (rs.next()) {
				BuildingEntity buildingEntity = new BuildingEntity();
				buildingEntity.setId(rs.getLong("b.id"));
				buildingEntity.setName(rs.getString("b.name"));
				buildingEntity.setWard(rs.getString("b.ward"));
				buildingEntity.setDistrictid(rs.getInt("b.districtid"));
				buildingEntity.setStreet(rs.getString("b.street"));
				buildingEntity.setFloorArea(rs.getLong("b.floorarea"));
				buildingEntity.setRentPrice(rs.getLong("b.rentprice"));
				buildingEntity.setServiceFee(rs.getString("b.servicefee"));
				buildingEntity.setBrokerageFee(rs.getLong("b.brokeragefee"));
				buildingEntity.setManagerName(rs.getString("b.managername"));
				buildingEntity.setManagerPhoneNumber(rs.getString("b.managerphonenumber"));
				result.add(buildingEntity);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Connect to DB failed...");

		}
		return result;
	}
}
