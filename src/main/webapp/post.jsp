<div class="ui massive active loader transition hidden" id="load"></div>

<div class="ui mini modal" id="modal">
  <div class="header" id="modal-header">Deletar</div>
  <div class="content" id="modal-content">
	Deseja realmente deletar esse artigo?
  </div>
  <div class="actions">
  	<div class="ui positive button">Não</div>
    <div class="ui deny red button">Sim</div>
  </div>
</div>

<div class="ui grid">
  <div class="one wide column"></div>
  <div class="ten wide column">
  
	<c:if test="${ auth.admin }">
	    <div class="ui clearing basic segment">
			<button class="ui right floated button red" onclick="deletePost(${ post.id })">Deletar</button>
		 	<button class="ui right floated button orange disabled"">Editar</button>
		 
		 </div>
	 </c:if>
	 
	<div class="ui piled segment">
	  <h4 class="ui header">${ requestScope.post.title }</h4>
	  
	  ${ requestScope.post.article }
	</div>
	
	Artigo #<span id="id_post">${ post.id }</span>
	
	<div class="ui blue segment" style="margin-top: 30px">
		<a class="avatar">
	      <img class="ui avatar image tiny" src="static/img/square-image.png">
	    </a>
	    Autor: ${ requestScope.user.first_name }  ${ requestScope.user.last_name } -  ${ requestScope.user.email }
	</div>
	
	<div class="ui comments">
	  <h3 class="ui dividing header">Comentários</h3>
	  
	  <div id="container-comments">

	  </div>
	  
	  <c:choose>
		  <c:when test="${ auth.logged }">
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
	<c:import url="static/scriptPost.js" />
 </script>