$(function() {
	$('#edit-texarea')
      .on('froalaEditor.initialized', function (e, editor) {
          editor.events.bindClick($('body'), '#get-text', function () {
            //console.log(editor.html.get());
            //gridEditVisualization();
            //editor.events.focus();
            //messageModal();
           publishPost(editor.html.get());
          });
        })
	.froalaEditor(
	{
		height: $(window).height() - 195
	 });
	
	gridEditAndLoadVisualization = function() {
		$('#grid-edit')
		  .transition('scale');
		
		setTimeout(function() {
			$('#load')
			  .transition('scale');	
		}, 300);
	};
	
	messageModal = function(title, text) {
		$('#modal-header').html(title);

		$('#modal-content').html(text);
				
		$('#modal')
		  .modal('show');
	};

	publishPost = function(articleText) {
		var title 			= $('#input-title').val();
		var preview_article = $('#input-preview-article').val();
		var article			= articleText;

		gridEditAndLoadVisualization();
		
		$.post('edit',
				{
					type : 'publish',
					title : title,
					preview_article : preview_article,
					article : article
				},
				function(response) {

					if (response.erro == true) {
						var text = '<div class="ui error message">';
						for (key in response) {
							if (key !== 'erro') {
								$('#field-'+key).addClass('error');
								text += '<p>'+response[key]+'</p>';
							}
						}
						
						text += '</div>';
													
						messageModal('Campos incorretos', text);
						
					} else if (response.seccess === true) {
						messageModal('Sucesso', '<h4>Artigo publicado com sucesso!</h4>');
					}
					
					gridEditAndLoadVisualization();
				}
			);
	};
	
});

// Mudar view entre edito e preview
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

// Iniciar as modificaçoes em tempo real da preview
$(function(){
	$('#input-title').keyup(function(){
		$('#preview-title').html($('#input-title').val());
	});
	
	$('#input-preview-article').keyup(function(){
		$('#preview-preview-article').html($('#input-preview-article').val());
	});
});
