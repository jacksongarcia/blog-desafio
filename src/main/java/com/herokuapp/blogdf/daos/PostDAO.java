package com.herokuapp.blogdf.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.herokuapp.blogdf.models.Post;

public class PostDAO {
	public void insert(Post post) {
		String sql = "INSERT INTO post " +
					"(title, preview_article, article, date_publication, user_id) " +
					"VALUES (?, ?, ?, ?, ?)";
		
		Connection connection = ConnectionFactory.getConnection();

		try (PreparedStatement stmt = connection.prepareStatement(sql)){

			stmt.setString(1, post.getTitle());
			stmt.setString(2, post.getPreview_article());
			stmt.setString(3, post.getArticle());
			stmt.setDate(4, post.getDatePublication());
			stmt.setInt(5, post.getUserId());
			
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean hasTitle(String title) {
		String sql = "SELECT id FROM post " +
					"WHERE title = ?";
		
		Connection connection = ConnectionFactory.getConnection();

		try (PreparedStatement stmt = connection.prepareStatement(sql)){

			stmt.setString(1, title);
			
			ResultSet rs = stmt.executeQuery();

			return rs.first();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public List<Post> getListPost() {
		String sql = "SELECT * FROM post ORDER BY id DESC LIMIT 10";
		
		Connection connection = ConnectionFactory.getConnection();

		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			
			ResultSet rs = stmt.executeQuery();

			List<Post> posts = new ArrayList<Post>();
			
			while(rs.next()) {
				Post post = new Post();
				post.setTitle(rs.getString("title"));
				post.setPreview_article(rs.getString("preview_article"));
				post.setArticle(rs.getString("article"));
				post.setDatePublication(rs.getDate("date_publication"));
				post.setUserId(rs.getInt("user_id"));
				
				posts.add(post);
			}
			
			return posts;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
