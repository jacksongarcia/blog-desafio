$(function(){
	 CKEDITOR.replace( 'edittexarea', {
		    language: 'pt',
		    height: '480px'
		});
	 
	 $('#button-publish').click(function(){
		 var INPUT_POST_TITLE = $('#INPUT_POST_TITLE').val();
		 var INPUT_POST_PREVIEW_ARTICLE = $('#INPUT_POST_PREVIEW_ARTICLE').val();
		 var INPUT_POST_ARTICLE = CKEDITOR.instances.edittexarea.getData();
		 
			var text = validateFormatInput(
					INPUT_POST_TITLE, 
					INPUT_POST_PREVIEW_ARTICLE, 
					INPUT_POST_ARTICLE);
			
			if (text !== '') {
				$('#modal-header').html("Dados incorretos");
				$('#modal-content').html(text);
				$('#modal').modal('show');
				
				return;
			}

			$('#grid-edit').transition('scale');
			$('#load').text("Publicando artigo...");
			$('#load').transition('scale');
			
			var data = {
					ACTION : "PUBLISH",
					POST_TITLE : INPUT_POST_TITLE,
					POST_PREVIEW_ARTICLE : INPUT_POST_PREVIEW_ARTICLE,
					POST_ARTICLE : INPUT_POST_ARTICLE
				};
			
			var funcSucess = function() {
				clearAllInput();
				
				$('#load').transition('scale');
				
				setTimeout(function(){
					$('#grid-edit').transition('scale');				
					
					$('#modal-header').html('Publicado');
					$('#modal-content').html('<h2 class="ui header">Publicado com sucesso!</h2>');
					$('#modal').modal('show');
				}, 300);
				
			};
			
			send(data, funcSucess);
	 });
	 
	 $('#button-delete').click(function(){
		 var data = {
				 ACTION: 'DELETE',
				 POST_ID: $('.container-post-id').attr('id')
		 };
		 
		 var funcSucess = function() {
			 clearAllInput();
				
				var path = $(location).attr('href');
				path = path.substring(0, path.lastIndexOf('/')+1);
				$(location).attr('href', path+'edit');
		 };

		$('#grid-edit').transition('scale');
		$('#load').text("Deletando artigo...");
		$('#load').transition('scale');
		
		 send(data, funcSucess);
		 
	 });
	 
	 $('#button-save-edition-post').click(function(){
		 var INPUT_POST_TITLE = $('#INPUT_POST_TITLE').val();
		 var INPUT_POST_PREVIEW_ARTICLE = $('#INPUT_POST_PREVIEW_ARTICLE').val();
		 var INPUT_POST_ARTICLE = CKEDITOR.instances.edittexarea.getData();
		 
			var text = validateFormatInput(
					INPUT_POST_TITLE, 
					INPUT_POST_PREVIEW_ARTICLE, 
					INPUT_POST_ARTICLE);
			
			if (text !== '') {
				$('#modal-header').html("Dados incorretos");
				$('#modal-content').html(text);
				$('#modal').modal('show');
				
				return;
			}

			$('#grid-edit').transition('scale');
			$('#load').text("Editando artigo...");
			$('#load').transition('scale');
			
			var data = {
					ACTION : "EDIT",
					POST_TITLE : INPUT_POST_TITLE,
					POST_PREVIEW_ARTICLE : INPUT_POST_PREVIEW_ARTICLE,
					POST_ARTICLE : INPUT_POST_ARTICLE,
					POST_ID: $('.container-post-id').attr('id')
				};
			
			var funcSucess = function() {				
				$('#load').transition('scale');
				
				setTimeout(function(){
					$('#grid-edit').transition('scale');				
					
					$('#modal-header').html('Editado');
					$('#modal-content').html('<h2 class="ui header">Editado com sucesso!</h2>');
					$('#modal').modal('show');
				}, 300);
				
			};
			
			send(data, funcSucess);
	 });
	 
	$('#button-clear').click(function(){
		clearAllInput();
	});
	 
	 var clearAllInput = function() {
		$('#INPUT_POST_TITLE').val('');
		$('#INPUT_POST_PREVIEW_ARTICLE').val('');
		CKEDITOR.instances.edittexarea.setData('');
	 };
	 
	 var validateFormatInput = function(
			 INPUT_POST_TITLE, 
				INPUT_POST_PREVIEW_ARTICLE, 
				INPUT_POST_ARTICLE) {
			var text = '';
			if (INPUT_POST_TITLE === '')
				text += '<p>Campo de titulo vazio</p>';
			else if (INPUT_POST_TITLE.length < 6)
				text += '<p>Campo de titulo deve ter no minimo 6 letras</p>';
			
			if (INPUT_POST_PREVIEW_ARTICLE === '')
				text += '<p>Campo de preview do artigo vazio</p>';
			else if (INPUT_POST_PREVIEW_ARTICLE.length < 6)
				text += '<p>Campo de preview do artigo deve ter no minimo 10 letras</p>';
			
			if (INPUT_POST_ARTICLE === '')
				text += '<p>Artigo não pode estar vazio</p>';
			
			return text;
		};
		
		var send = function(data, funcSucess) {
			$.post('edit', data,
		    		function(response) {
						if (response === null) {
							$('#load').transition('scale');
							setTimeout(function(){
								$('#grid-edit').transition('scale');
								if (text !== '') {
									$('#modal-header').html("Falha");
									$('#modal-content').html("");
									$('#modal').modal('<h4 class="ui header">Falha na rede</h4>');
								}
							}, 300);
						}
						else if(response.length) {
							var text = '';
							for(key in response) {
								for(key2 in response[key]) {
	        						$('#FIELD-'+key2).addClass('error');

									text += '<p>'+response[key][key2]+'</p>';

								}
							}
							
							
							$('#load').transition('scale');
							setTimeout(function(){
								$('#grid-edit').transition('scale');
								if (text !== '') {
									$('#modal-header').html("Dados incorretos");
									$('#modal-content').html(text);
									$('#modal').modal('show');
								}
							}, 300);

						} 
						else {
							funcSucess();
						}

		    		}
				);		
		};
});

//Mudar view entre edito e preview
$(function(){
	$('#buttom-preview').click(function(){
		showSegment('preview', 'editor');
	});
	
	$('#buttom-editor').click(function(){
		showSegment('editor', 'preview');
	});
	

	showSegment = function(show, hiden) {
		if($('#buttom-'+show).hasClass('active'))
			return;
		
		$('#buttom-'+hiden).removeClass('active');
		$('#buttom-'+show).addClass('active disabled');
		
		$('#segment-'+hiden)
		  .transition('scale');
		
		setTimeout(function() {
			$('#segment-'+show)
			  .transition('scale');	
		}, 300);

	};
	
});

//Iniciar as modificaçoes em tempo real da preview
$(function(){
	$('#INPUT_POST_TITLE').keyup(function(){
		$('#preview-title').html($('#INPUT_POST_TITLE').val());
	});
	
	$('#INPUT_POST_PREVIEW_ARTICLE').keyup(function(){
		$('#preview-preview-article').html($('#INPUT_POST_PREVIEW_ARTICLE').val());
	});
});