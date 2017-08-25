<jsp:useBean id="posts" class="com.herokuapp.blogdf.controllers.PostController"/>

<div class="ui grid">
  <div class="one wide column"></div>
  <div class="ten wide column">
	<div class="ui divided items">
	
	  <c:forEach var="post" items="${posts.listPost}">
	
		  <div class="item">
		    <div class="content">
		      <a class="header" href="post?article=${post.title}">${post.title}</a>
		      <div class="meta">
		        <span>${ post.preview_article }</span>
		      </div>
		      <div class="extra">
		        <p>Publicado em <fmt:formatDate value="${post.datePublication}" pattern="dd/MM/yyyy" /></p>
		      </div>
		    </div>
		  </div>
		  
  	</c:forEach>

	</div>
  </div>
  <div class="four wide column"></div>
  <div class="one wide column"></div>
  
</div>


