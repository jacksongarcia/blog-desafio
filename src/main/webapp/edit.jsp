 <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.25.0/codemirror.min.css">

 <link href="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.6.5/css/froala_editor.pkgd.min.css" rel="stylesheet" type="text/css" />
 <link href="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.6.5/css/froala_style.min.css" rel="stylesheet" type="text/css" />

<div class="ui massive active text loader transition hidden" id="load">Publicando artigo...</div>

<div class="ui mini modal" id="modal">
  <div class="header" id="modal-header"></div>
  <div class="content" id="modal-content">

  </div>
  <div class="actions">
    <div class="ui positive button">OK</div>
  </div>
</div>

<div class="ui grid" id="grid-edit">
  <div class="twelve wide stretched column">
    <div class="ui basic segment" id="segment-editor">
  		<textarea id="edit-texarea"></textarea>
    </div>
    
    <div class="ui segment transition hidden" id="segment-preview">
    	
		<div class="ui form">
		 <h4 class="header">User TAGS html para organizar o texto</h4>
		  <div class="field">
		  	<label>Título</label>
			<div class="ui fluid big input">
			  <input type="text" placeholder="Título" id="input-title">
			</div>
		  </div>
		
		  <div class="field">
		    <label>Texto curto do artigo</label>
		    <textarea id="input-preview-article"></textarea>
		  </div>
		</div>

		<h4 class="ui horizontal divider header">
		  Preview
		</h4>

		<div class="ui divided items">
		  <div class="item">
		    <div class="content">
		      <a class="header" id="preview-title">Título</a>
		      <div class="meta">
		        <span>
		        	<jsp:useBean id="now" class="java.util.Date" />
        			Publicado em <fmt:formatDate value="${now}" pattern="dd/MM/yyyy" />
		        </span>
		      </div>
		      <div class="extra" id="preview-preview-article">
		        <p>Texto curto do artigo</p>
		      </div>
		      <div class="description">
        		Autor: ${ auth.name } ${ auth.lastName } - ${ auth.email }
		      </div>
		    </div>
		  </div>
		</div>

    </div>
  </div>
  <div class="four wide column">
    <div class="ui vertical fluid right tabular menu">
      <a class="item active" id="buttom-editor">
        Editor
      </a>
      <a class="item" id="buttom-preview">
        Preview do artigo
      </a>
      	
      	<h4 class="ui horizontal divider header">
		  //
		</h4>

       <div class="item">
	      <button class="fluid ui green button" id="get-text">
    	  		<i class="icon save"></i>
		  	Publicar
		  </button>
		  <div class="ui divider"></div>
		  <button class="fluid ui red button disabled">
		  	<i class="icon trash outline"></i>
		  	Excluir
		  </button>
		</div>
		<div class="ui divider"></div>
    </div>
  </div>
</div>
 
 <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.25.0/codemirror.min.js"></script>
 <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.25.0/mode/xml/xml.min.js"></script>

 <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.6.5/js/froala_editor.pkgd.min.js"></script>

 <script> 
	<c:import url="static/scriptEdit.js" />
 </script>