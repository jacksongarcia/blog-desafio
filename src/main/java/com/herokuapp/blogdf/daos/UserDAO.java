package com.herokuapp.blogdf.daos;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.herokuapp.blogdf.models.User;

public class UserDAO {
	public boolean insert(User user, Connection connection) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
		 String sql = "INSERT INTO user " +
				"(first_name, last_name, email, password, admin) " +
				"VALUES (?, ?, ?, ?, ?)";
			
		PreparedStatement stmt = connection.prepareStatement(sql);
	
		stmt.setString(1, user.getFirst_name());
		stmt.setString(2, user.getLast_name());
		stmt.setString(3, user.getEmail());
		stmt.setString(4, getEncryptPassword(user.getPassword()));
		stmt.setBoolean(5, false);
		
		return stmt.execute();		
	}
	
	public boolean hasEmail(String email, Connection connection) throws SQLException {
		String sql = "SELECT id FROM user WHERE email = ? LIMIT 1";

		PreparedStatement stmt = connection.prepareStatement(sql);

		stmt.setString(1, email);
		ResultSet rs = stmt.executeQuery();
	
		return rs.first();

	}
	
	public User haveUserWithEmailAndPassword(String email, String password,
			Connection connection) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
		String sql = "SELECT * FROM user WHERE email = ? AND password = ? LIMIT 1";
		
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, email);
		stmt.setString(2, getEncryptPassword(password));
		
		ResultSet rs = stmt.executeQuery();
		
		User user = null;
		while(rs.next()) {
			user = new User();
			
			user.setId(rs.getInt("id"));
			user.setFirst_name(rs.getString("first_name"));
			user.setLast_name(rs.getString("last_name"));
			user.setEmail(rs.getString("email"));
			user.setAdmin(rs.getBoolean("admin"));
		}
		
		return user;
	}

	private String getEncryptPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest algorithm;
		StringBuilder hexString = null;
		
		algorithm = MessageDigest.getInstance("SHA-256");

		byte messageDigest[] = algorithm.digest(password.getBytes("UTF-8"));

		hexString = new StringBuilder();
		for (byte b : messageDigest) {
		  hexString.append(String.format("%02X", 0xFF & b));
		}
		
		return hexString.toString();
	}

}
