$(function(){
	$('#buttom-send-comment').click(function(){
		publishComment();
	});
	
	var messageModal = function(title, text) {
		$('#modal-header').html(title);

		$('#modal-content').html(text);
				
		$('#modal')
		  .modal('show');
	};
	
	var loadVisualization = function() {
		$('#grid-edit')
		  .transition('scale');
		
		setTimeout(function() {
			$('#load')
			  .transition('scale');	
		}, 300);
	};
	
	var getComment = function(response) {
		var textComment = '<div class="comment">'+
	    '<a class="avatar">'+
	      '<img src="static/img/square-image.png">'+
	    '</a>'+
	    '<div class="content">'+
	      '<a class="author">'+response.name+'</a>'+
	      '<div class="metadata">'+
	        '<span class="date">Publicado em '+response.publishDate+'</span>'+
	      '</div>'+
	      '<div class="text">'+
	      	$('#input-comment').val()+
	      '</div>'+
	    '</div>'+
	  '</div>';
		
		return textComment;
	};
	
	publishComment = function() {
		var comment = $('#input-comment').val();
		var id = $('#id_post').text();

		loadVisualization();
		
		$.post('post',
				{
					type : 'comment',
					id: id,
					comment : comment
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
													
						messageModal('Falha', text);
						
					} else if (response.seccess === true) {
						$('#container-comments').append(getComment(response));
						$('#input-comment').val('');
					}
					
					loadVisualization();
				}
			);
	};

});

$(function(){
	var getComment = function(response) {
		var textComment = '<div class="comment">'+
	    '<a class="avatar">'+
	      '<img src="static/img/square-image.png">'+
	    '</a>'+
	    '<div class="content">'+
	      '<a class="author">'+response.name+'</a>'+
	      '<div class="metadata">'+
	        '<span class="date">Publicado em '+response.publish+'</span>'+
	      '</div>'+
	      '<div class="text">'+
	      	response.text+
	      '</div>'+
	    '</div>'+
	  '</div>';
		
		return textComment;
	};
	
	listComment = function() {
		var id = $('#id_post').text();
		
		$.post('post',
				{
					type : 'list-comment',
					id: id
				},
				function(response) {
					
					for (key in response) {
						$('#container-comments').append(getComment(response[key]));
					}
					
				}
			);
	};
	
	listComment();
});