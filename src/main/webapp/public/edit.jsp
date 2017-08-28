<script src="${pageContext.request.contextPath}/resources/scripts/ckeditor/ckeditor.js" /></script>
 
<div class="ui mini modal" id="modal">
  <div class="header" id="modal-header"></div>
  <div class="content" id="modal-content">

  </div>
  <div class="actions">
    <div class="ui positive button">OK</div>
  </div>
</div>

	<c:if test="${ not empty post }">
	  	<div class="ui basic tertiary inverted red center aligned segment">
		  <h2>Editando Artigo : ${ post.title }</h2>
		</div>
	</c:if>
	
<div class="ui grid" id="grid-edit">
  <div class="twelve wide stretched column container-post-id" id="${ post.id }">
    <div class="ui basic segment" id="segment-editor">
  		<textarea id="edittexarea"  name="edittexarea">${ post.article }</textarea>
    </div>
    
    <div class="ui basic segment transition hidden" id="segment-preview">
    	
		<div class="ui form">
		 <h2 class="header">User TAGS html para organizar o texto</h2>
		  <div class="field" id="FIELD_POST_TITLE">
		  	<label>Título</label>
			<div class="ui fluid big input">
			  <input type="text" placeholder="Título" value="${ post.title }" id="INPUT_POST_TITLE">
			</div>
		  </div>
		
		  <div class="field" id="FIELD_POST_PREVIEW_ARTICLE">
		    <label>Texto curto do artigo</label>
		    <textarea id="INPUT_POST_PREVIEW_ARTICLE">${ post.preview_article }</textarea>
		  </div>
		</div>

		<h4 class="ui horizontal divider header">
		  Preview
		</h4>

		<div class="ui piled very padded segment">
			<div class="ui divided items">
			  <div class="item">
			    <div class="content">
			      <a class="header" id="preview-title">Título</a>
			      
			      <div class="meta">
			      <c:choose>
				    <c:when test="${ not empty post }}">
				        <span>
		        			Publicado em ${ post.date_publication }
				        </span>
				    </c:when>    
				    <c:otherwise>
				        <span>
				        	<jsp:useBean id="now" class="java.util.Date" />
		        			Publicado em <fmt:formatDate value="${now}" pattern="dd/MM/yyyy" />
				        </span>
				    </c:otherwise>
				</c:choose>
			        
			      </div>
			      
			      <div class="extra" id="preview-preview-article">
			        <p>Texto curto do artigo</p>
			      </div>
			      
			      <c:choose>
				    <c:when test="${ not empty user }">
				          <div class="description">
			        		Autor: ${ user.first_name } ${ user.last_name } - ${ user.email }
					      </div>
				    </c:when>    
				    <c:otherwise>
				          <div class="description">
			        		Autor: ${ auth.first_name } ${ auth.last_name } - ${ auth.email }
					      </div>
				    </c:otherwise>
				</c:choose>
			      
			    </div>
			  </div>
			</div>
		</div>

    </div>
  </div>
  
  <!-- MENU -->
  <div class="four wide column">
    <div class="ui vertical fluid right tabular menu">
      <a class="item active" id="buttom-editor">
        EDITAR ARTIGO
      </a>
      <a class="item" id="buttom-preview">
        INFORMAÇÃO DO ARTIGO
      </a>
      	
      	<h4 class="ui horizontal divider header">
		  //
		</h4>

       <div class="item">
       
  		<c:choose>
		  <c:when test="${ not empty post }">
			  
		      <button class="fluid ui green button" id="button-save-edition-post">
	    	  		<i class="icon save"></i>
			  	SALVAR EDIÇÃO
			  </button>
		   </c:when>
		   <c:otherwise>
			   
		      <button class="fluid ui green button" id="button-publish">
	    	  		<i class="icon save"></i>
			  	PUBLICAR
			  </button>
		   </c:otherwise>
	   </c:choose>
		  
		  <div class="ui divider"></div>
		  
		  <button class="fluid ui blue button" id="button-clear">
		  	<i class="icon trash outline"></i>
		  	LIMPAR CAMPOS
		  </button>
		  
		  <div class="ui divider"></div>
		  
		  <c:if test="${ not empty post }">
			  <button class="fluid ui red button" id="button-delete">
			  	<i class="icon trash outline"></i>
			  	EXCLUIR
			  </button>
		  </c:if>
		</div>
		<div class="ui divider"></div>
    </div>
  </div>
</div>
 
 <script> 
	<c:import url="../resources/scripts/edit.js" />
 </script>