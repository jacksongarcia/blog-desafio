package com.herokuapp.blogdf.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.herokuapp.blogdf.models.UserSession;

public class UserSessionDAO {
	public UserSession getUser(String email) throws SQLException {
		String sql = "SELECT id, first_name, last_name, email, admin FROM user " +
					"WHERE email = ?";
		
		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement stmt = connection.prepareStatement(sql);

		stmt.setString(1, email);
		ResultSet rs = stmt.executeQuery();
		
		UserSession userSession = new UserSession();
		if(rs.first()) {
			userSession.setId(rs.getInt("id"));
			userSession.setName(rs.getString("first_name"));
			userSession.setLastName(rs.getString("last_name"));
			userSession.setEmail(rs.getString("email"));
			userSession.setAdmin(rs.getBoolean("admin"));
			
			return userSession;
		}
		
		
		return null;
	}
}
