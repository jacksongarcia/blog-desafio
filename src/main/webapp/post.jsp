<div class="ui massive active text loader transition hidden" id="load">Publicando comentário...</div>

<div class="ui mini modal" id="modal">
  <div class="header" id="modal-header"></div>
  <div class="content" id="modal-content">

  </div>
  <div class="actions">
    <div class="ui positive button">OK</div>
  </div>
</div>

<div class="ui grid">
  <div class="one wide column"></div>
  <div class="ten wide column">
  
	<div class="ui piled segment">
	  <h4 class="ui header">${ requestScope.post.title }</h4>
	  
	  ${ requestScope.post.article }
	</div>
	
	<div class="ui blue segment" style="margin-top: 30px">
		<a class="avatar">
	      <img class="ui avatar image tiny" src="static/img/square-image.png">
	    </a>
	    Autor: ${ requestScope.user.first_name }  ${ requestScope.user.last_name } -  ${ requestScope.user.email }
		 - #<span id="id_post">${ post.id }</span>
	</div>
	
	<div class="ui comments">
	  <h3 class="ui dividing header">Comentários</h3>
	  
	  <div id="container-comments">

	  </div>
	  
	  
	  <c:choose>
		  <c:when test="${ auth.loged }">
			  <form class="ui reply form">
			    <div class="field">
			      <textarea id="input-comment"></textarea>
			    </div>
			    <div class="ui blue labeled submit icon button" id="buttom-send-comment">
			      <i class="icon edit"></i> Enviar Resposta
			    </div>
			  </form>
		   </c:when>
		   <c:otherwise>
		    <div class="ui divider"></div>
		   	<a class="fluid ui button" href="login">Fazer login</a>
		   </c:otherwise>
	   </c:choose>
	</div>
	
  </div>
  
  <div class="four wide column"></div>
  <div class="one wide column"></div>
</div>

 <script> 
	<c:import url="static/scriptPost.js" />
 </script>