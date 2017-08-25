package com.herokuapp.blogdf.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.herokuapp.blogdf.models.Comment;
import com.herokuapp.blogdf.models.User;

public class CommentDAO {
	public void insert(Comment comment) throws SQLException {
		String sql = "INSERT INTO comment " +
					"(text, user_id, post_id, publish) " +
					"VALUES (?, ?, ?, ?)";
		
		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement stmt = connection.prepareStatement(sql);

		stmt.setString(1, comment.getText());
		stmt.setInt(2, comment.getUser_id());
		stmt.setInt(3, comment.getPost_id());
		stmt.setDate(4, comment.getPublish());
		
		stmt.execute();
	
	}
	
	public Map<Comment, User> getListCommentByIdPost(int postId) throws SQLException {
		String sql = "SELECT comment.*, user.* FROM comment "+
					"INNER JOIN user ON comment.post_id = ? AND comment.user_id = user.id "+
					"ORDER BY comment.id DESC";
		
		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement stmt = connection.prepareStatement(sql);
			
		stmt.setInt(1, postId);
		ResultSet rs = stmt.executeQuery();
		Map<Comment, User> commentsAndUser = new HashMap<Comment, User>();
		
		while(rs.next()) {
			Comment comment = new Comment();
			User user = new User();
			
			comment.setText(rs.getString("text"));
			comment.setPublish(rs.getDate("publish"));
			

			user.setFirst_name(rs.getString("first_name"));
			user.setLast_name(rs.getString("last_name"));
			
			commentsAndUser.put(comment, user);
		}
		
		return commentsAndUser;		
	}
	

}
