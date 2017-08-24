 <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.25.0/codemirror.min.css">

 <link href="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.6.5/css/froala_editor.pkgd.min.css" rel="stylesheet" type="text/css" />
 <link href="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.6.5/css/froala_style.min.css" rel="stylesheet" type="text/css" />
 
   <div class="ui grid">
     <div class="one wide column"></div>
 	  <div class="eleven wide column">
   	<textarea></textarea>
    .</div>
     <div class="three wide column">
     	<button class="fluid ui green button" id="get-text">
     	  <i class="icon save"></i>
	  Publicar
	</button>
	<div class="ui divider"></div>
	<button class="fluid ui red button">
	  <i class="icon trash outline"></i>
	  Excluir
	</button>
     </div>
 	  <div class="one wide column"></div>
   </div>
 
 <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.25.0/codemirror.min.js"></script>
 <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.25.0/mode/xml/xml.min.js"></script>

 <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.6.5/js/froala_editor.pkgd.min.js"></script>

 <script> 
 	$(function() { $('textarea')
	      .on('froalaEditor.initialized', function (e, editor) {
	          editor.events.bindClick($('body'), '#get-text', function () {
	            console.log(editor.html.get());
	            editor.events.focus();
	          });
	        })
 		.froalaEditor(
 		{
		      height: $(window).height() - 180
		    }		
 	)}); 
 </script>