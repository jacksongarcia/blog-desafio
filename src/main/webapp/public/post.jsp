
<div class="ui grid">
  <div class="one wide column"></div>
  <div class="ten wide column">
  	 
	<div class="ui piled segment" id="${ requestScope.post.id }">
		<c:if test="${ auth.admin }">
			<div class="ui basic clearing segment" style="margin-bottom:-50px; padding:0">
				 <a class="right floated circular ui icon red button" href="edit?ACTION=edit&title=${ post.title }">
				  <i class="icon settings"></i>
				</a>
			</div>
		</c:if>

	  <h2 class="ui header">${ requestScope.post.title }</h2>
	  
	  ${ requestScope.post.article }
	</div>
		
	<div class="ui blue segment" style="margin-top: 30px" id="autor">
		<!-- <a class="avatar">
	      <img class="ui avatar image tiny" src="static/img/square-image.png">
	    </a> -->
	    Autor: ${ requestScope.user.first_name }  ${ requestScope.user.last_name } -  ${ requestScope.user.email }
	</div>
	
	<div class="ui comments">
	  <h3 class="ui dividing header">Comentários</h3>
	  
	  <div id="container-comments">

	  </div>
	  
  		<c:choose>
		  <c:when test="${ not empty auth }">
			  <form class="ui reply form">
			    <div class="field">
			      <textarea id="input-comment"></textarea>
			    </div>
			    <div class="ui blue labeled submit icon button" id="button-send-comment">
			      <i class="icon edit"></i> Enviar Resposta
			    </div>
			  </form>
		   </c:when>
		   <c:otherwise>
		    <div class="ui divider"></div>
		   	<a class="fluid ui button" href="auth">Fazer login</a>
		   </c:otherwise>
	   </c:choose>
	   
	</div>
	
  </div>
  
  <div class="four wide column">
  	<div class="ui segment">
	  <h2 class="ui right floated header">Em desenvolvimento</h2>
	  <div class="ui clearing divider"></div>
	  <p>categorias</p>
	</div>
  </div>
  <div class="one wide column"></div>
</div>


<script>
	<c:import url="/resources/scripts/post.js" />
</script>
