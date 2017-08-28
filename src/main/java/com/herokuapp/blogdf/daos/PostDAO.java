package com.herokuapp.blogdf.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.herokuapp.blogdf.models.Post;
import com.herokuapp.blogdf.models.User;

public class PostDAO {
		
	public boolean insert(Post post, Connection connection) throws SQLException {
		String sql = "INSERT INTO post " +
					"(title, preview_article, article, date_publication, user_id) " +
					"VALUES (?, ?, ?, ?, ?)";
		
		PreparedStatement stmt = connection.prepareStatement(sql);

		stmt.setString(1, post.getTitle());
		stmt.setString(2, post.getPreview_article());
		stmt.setString(3, post.getArticle());
		stmt.setDate(4, post.getDatePublication());
		stmt.setInt(5, post.getUserId());
		
		return stmt.execute();
	}
	
	public boolean edit(int postId, Post post, Connection connection) throws SQLException {
		String sql = "UPDATE post " +
				"SET title = ?, preview_article = ?, article = ?, date_publication = ?, user_id = ? "+
				"WHERE id = ? ";
	
		PreparedStatement stmt = connection.prepareStatement(sql);
	
		stmt.setString(1, post.getTitle());
		stmt.setString(2, post.getPreview_article());
		stmt.setString(3, post.getArticle());
		stmt.setDate(4, post.getDatePublication());
		stmt.setInt(5, post.getUserId());
		stmt.setInt(6, postId);

		return stmt.execute();
		
	}
			
	public boolean hasTitile(String title, Connection connection) throws SQLException {
		String sql = "SELECT id FROM post WHERE title = ? LIMIT 1";

		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, title);

		ResultSet rs = stmt.executeQuery();
		
		if (rs != null)
			return rs.first();
		
		return false;
	}

	public Map<Post, User> getListPost(int index, int max, Connection connection) throws SQLException {
		String sql = "SELECT post.id, post.title, post.preview_article, post.date_publication, post.user_id, "+ 
					"user.first_name, user.last_name, user.email "+
					"FROM post INNER JOIN user ON post.user_id = user.id ORDER BY id DESC LIMIT ?, ? ";
		
		PreparedStatement stmt = connection.prepareStatement(sql);
			
		stmt.setInt(1, index);
		stmt.setInt(2, max);

		ResultSet rs = stmt.executeQuery();
		
		Map<Post, User> listPost = new LinkedHashMap<Post, User>();

		while(rs.next()) {
			Post post = new Post();
			User user = new User();
			
			post.setId(rs.getInt("id"));
			post.setTitle(rs.getString("title"));
			post.setPreview_article(rs.getString("preview_article"));
			post.setDatePublication(rs.getDate("date_publication"));
			post.setUserId(rs.getInt("user_id"));
			
			user.setFirst_name(rs.getString("first_name"));
			user.setLast_name(rs.getString("last_name"));
			user.setEmail(rs.getString("email"));
			
			listPost.put(post, user);
		}

		return listPost;
	}
	
	public Map<Post, User> getPost(String title, Connection connection) throws SQLException {
		String sql = "SELECT post.*, user.* "+
					"FROM post INNER JOIN user ON post.title = ? LIMIT 1 ";
		
		PreparedStatement stmt = connection.prepareStatement(sql);
			
		stmt.setString(1, title);

		ResultSet rs = stmt.executeQuery();
		
		Map<Post, User> listPost = new LinkedHashMap<Post, User>();

		while(rs.next()) {
			Post post = new Post();
			User user = new User();
			
			post.setId(rs.getInt("id"));
			post.setTitle(rs.getString("title"));
			post.setPreview_article(rs.getString("preview_article"));
			post.setArticle(rs.getString("article"));
			post.setDatePublication(rs.getDate("date_publication"));
			post.setUserId(rs.getInt("user_id"));
			
			user.setFirst_name(rs.getString("first_name"));
			user.setLast_name(rs.getString("last_name"));
			user.setEmail(rs.getString("email"));
			
			listPost.put(post, user);
		}

		return listPost;
	}


	public int getLength(Connection connection) throws SQLException {
		String sql = "SELECT COUNT(id) FROM post WHERE 1 ";
	
		PreparedStatement stmt = connection.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();
		
		if (rs.next())
			return rs.getInt("COUNT(id)");
		
		return 0;

	}

	public boolean delete(int postId, Connection connection) throws SQLException {
		String sql = "DELETE post, comment FROM post INNER JOIN comment ON post.id = comment.post_id WHERE post.id = ? ";
	
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, postId);

		return stmt.execute();		
	}

}
