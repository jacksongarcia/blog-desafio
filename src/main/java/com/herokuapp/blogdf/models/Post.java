package com.herokuapp.blogdf.models;

import java.sql.Date;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import com.herokuapp.blogdf.controllers.AuthController;
import com.herokuapp.blogdf.controllers.EditController;

public class Post {
	private int id;
	private String title;
	private String preview_article;
	private String article;
	private Date datePublication;
	private int userId;
	
	public Post() {}
	
	public Post(HttpServletRequest request) {
		title = request.getParameter(EditController.PARAMETER_POST_TITLE).toString();
		preview_article = request.getParameter(EditController.PARAMETER_POST_PREVIEW_ARTICLE).toString();
		article = request.getParameter(EditController.PARAMETER_POST_ARTICLE).toString();
		datePublication = new Date(Calendar.getInstance().getTimeInMillis());
		userId = new AuthController().getUserIdOfSession(request);
	}
	
	public int getId(){
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getPreview_article(){
		return preview_article;
	}
	public String getArticle() {
		return article;
	}
	public Date getDatePublication() {
		return datePublication;
	}
	public int getUserId() {
		return userId;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setPreview_article(String preview_article){
		this.preview_article = preview_article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public void setDatePublication(Date datePublication) {
		this.datePublication = datePublication;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
