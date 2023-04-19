package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller obj) {

	}

	@Override
	public void update(Seller obj) {

	}

	@Override
	public void deleteById(Integer id) {

	}

	@Override
	public Seller findById(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;
				
		try {
			st = conn.prepareStatement(
				  "SELECT "  
				+    "seller.*, "
				+    "department.Name as DepName "
				+ "FROM seller_ww seller, department_ww department "
				+ "WHERE seller.department_id = department.id "
				+ "    and seller.id = ? "
					
					);
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				
				Department dep = new Department();
				dep.setId(rs.getInt("department_id"));
				dep.setName(rs.getString("DepName"));
				
				Seller obj = new Seller();
				obj.setId(rs.getInt("id"));
				obj.setName(rs.getString("name"));
				obj.setEmail(rs.getString("email"));
				obj.setBirthDate(rs.getDate("birthDate"));
				obj.setBaseSalary(rs.getDouble("baseSalary"));
				obj.setDepartmment(dep);
				
				return obj;
			}
			return null;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResult(rs);
		}
		
	}

	@Override
	public List<Seller> findAll() {

		return null;
	}

}
