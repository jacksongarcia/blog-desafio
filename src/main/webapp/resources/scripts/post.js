$(function(){
	var getComment = function(response) {
		var textComment = '<div class="comment">'+
	    '<a class="avatar">'+
	      //'<img src="static/img/square-image.png">'+
	    '</a>'+
	    '<div class="content">'+
	      '<a class="author">'+response.first_name+ ' '+response.last_name+' < '+response.email+' > </a>'+
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
	
	var send = function(data, func) {

		$.post('post', data,
				function(response) {
					console.log(response);
					if (response === null)
						console.log('RESPONSE NULO');
					else if (response.length)
						func(response);
					else
						console.log('RESPONSE IGUAL A 0');
					
				}
			);
	};
	
	var listComment = function(response) {
		for (key in response) {
			$('#container-comments').append(getComment(response[key]));
		}
	};
	

	send({
		ACTION : 'GET_COMMENT',
		ID: $('.ui.segment').attr('id'),
		INDEX: 0
	}, listComment);
	

	var sendComment = function(response) {
		$('#container-comments').append(getComment(response[0]));
		$('#input-comment').val('');
	};
	
	$('#button-send-comment').click(function(){
		send({
			ACTION : 'REGISTER_COMMENT',
			ID: $('.ui.segment').attr('id'),
			INPUT_TEXT: $('#input-comment').val()
		}, sendComment);
	});
	
});