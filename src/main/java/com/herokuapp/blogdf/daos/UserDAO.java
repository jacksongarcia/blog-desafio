package com.herokuapp.blogdf.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.herokuapp.blogdf.models.User;

public class UserDAO {

	public void insert(User user) throws SQLException {
		String sql = "INSERT INTO user " +
					"(first_name, last_name, email, password, admin) " +
					"VALUES (?, ?, ?, ?, ?)";
		
		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement stmt = connection.prepareStatement(sql);

		stmt.setString(1, user.getFirst_name());
		stmt.setString(2, user.getLast_name());
		stmt.setString(3, user.getEmail());
		stmt.setString(4, user.getPassword());
		stmt.setBoolean(5, false);
		
		stmt.execute();
		
	}
	
	public boolean hasEmail(String email) throws SQLException {
		String sql = "SELECT id FROM user " +
					"WHERE email = ?";
		
		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, email);
		ResultSet rs = stmt.executeQuery();

		return rs.first();
	}
	
	public boolean isValidatePassword(String email, String password) throws SQLException {
		String sql = "SELECT id FROM user " +
				"WHERE email = ? AND password = ?";
	
		Connection connection = ConnectionFactory.getConnection();
	
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, email);
		stmt.setString(2, password);
		
		ResultSet rs = stmt.executeQuery();

		return rs.first();		
	}

	public User getUserById(int id) throws SQLException {
		String sql = "SELECT * FROM user " +
				"WHERE id = ?";
	
		Connection connection = ConnectionFactory.getConnection();
	
		PreparedStatement stmt = connection.prepareStatement(sql);

		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		User user = null;
		
		if(rs.next()) {
			user = new User();
			
			user.setFirst_name(rs.getString("first_name"));
			user.setLast_name(rs.getString("last_name"));
			user.setEmail(rs.getString("email"));
		}
		
		return user;
	}
}
