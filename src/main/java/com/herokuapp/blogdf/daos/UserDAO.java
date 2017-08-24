package com.herokuapp.blogdf.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.herokuapp.blogdf.models.User;

public class UserDAO {

	public void insert(User user) {
		String sql = "INSERT INTO user " +
					"(first_name, last_name, email, password, admin) " +
					"VALUES (?, ?, ?, ?, ?)";
		
		Connection connection = ConnectionFactory.getConnection();

		try (PreparedStatement stmt = connection.prepareStatement(sql)){

			stmt.setString(1, user.getFirst_name());
			stmt.setString(2, user.getLast_name());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getPassword());
			stmt.setBoolean(5, false);
			
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean hasEmail(String email) {
		String sql = "SELECT id FROM user " +
					"WHERE email = ?";
		
		Connection connection = ConnectionFactory.getConnection();

		try (PreparedStatement stmt = connection.prepareStatement(sql)){

			stmt.setString(1, email);
			
			ResultSet rs = stmt.executeQuery();

			return rs.first();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean isValidatePassword(String email, String password) {
		String sql = "SELECT id FROM user " +
				"WHERE email = ? AND password = ?";
	
	Connection connection = ConnectionFactory.getConnection();

	try (PreparedStatement stmt = connection.prepareStatement(sql)){

		stmt.setString(1, email);
		stmt.setString(2, password);
		
		ResultSet rs = stmt.executeQuery();

		return rs.first();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	return false;
	}

}
