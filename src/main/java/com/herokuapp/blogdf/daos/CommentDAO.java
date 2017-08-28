package com.herokuapp.blogdf.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.herokuapp.blogdf.models.Comment;
import com.herokuapp.blogdf.models.User;

public class CommentDAO {
	
	public int getLength(int postId, Connection connection) throws SQLException {
		String sql = "SELECT COUNT(id) FROM comment WHERE post_id = ? LIMIT 1";
		
		PreparedStatement stmt = connection.prepareStatement(sql);
			
		stmt.setInt(1, postId);
		ResultSet rs = stmt.executeQuery();
	
		if (rs.next())
			return rs.getInt("COUNT(id)");
		
		return 0;

	}

	public Map<Comment, User> getListComment(int postId, int index, int max,
			Connection connection) throws SQLException {
		String sql = "SELECT comment.*, user.* "+
				"FROM comment INNER JOIN user ON comment.post_id = ? AND user.id = comment.user_id ORDER BY comment.id DESC LIMIT ?, ? ";
	
		PreparedStatement stmt = connection.prepareStatement(sql);
			
		stmt.setInt(1, postId);
		stmt.setInt(2, index);
		stmt.setInt(3, max);

		ResultSet rs = stmt.executeQuery();
		
		Map<Comment, User> listComment = new LinkedHashMap<Comment, User>();
	
		while(rs.next()) {
			Comment comment = new Comment();
			User user = new User();
			
			comment.setText(rs.getString("text"));
			comment.setPublish(rs.getDate("publish"));

			user.setFirst_name(rs.getString("first_name"));
			user.setLast_name(rs.getString("last_name"));
			user.setEmail(rs.getString("email"));
			
			listComment.put(comment, user);
		}
	
		return listComment;
	}

	public void insertComment(int postId, int user_id, String text,
			Date publish,Connection connection) throws SQLException {
		String sql = "INSERT INTO comment " +
				"(text, user_id, post_id, publish) " +
				"VALUES (?, ?, ?, ?)";
		
		PreparedStatement stmt = connection.prepareStatement(sql);
	
		stmt.setString(1, text);
		stmt.setInt(2, user_id);
		stmt.setInt(3, postId);
		stmt.setDate(4, publish);
		
		stmt.execute();
		
	}
	

}
