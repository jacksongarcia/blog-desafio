$(document).ready(function(){
	var postHTML = function(id, title, date, article, name, email) {
		return '<div class="ui piled very padded segment transition hidden" id="'+id+'">'+
			'<div class="ui divided items">'+
			  '<div class="item">'+
			    '<div class="content">'+
			      '<a class="header" href="post?ACTION=postview&title='+title+'">'+title+'</a>'+
			      
			      '<div class="meta">'+
			        '<span>'+
	        			'Publicado em '+ date +
			        '</span>'+
			      '</div>'+
			      '<div class="extra" id="preview-preview-article">'+
			      	article +
			      '</div>'+
			      '<div class="description">'+
	        		'Autor: '+name+' < '+email+' > '+
			      '</div>'+
			    '</div>'+
			  '</div>'+
			'</div>'+
		'</div>';
	};
	var INDEX = 0;
	var TOTAL = 5;
	var send = function(data, funcSuccess) {
		$.post('index',data,
				function(response){
					if (response === null) {
						console.log('response nulo');
					} 
					else if (response.length) {
						console.log(response);
						funcSuccess(response);
	
					}
					else {
						console.log('response igual a 0');
					}
				}
		);
	};
	
	var funcListTotal = function(response) {
		console.log("TOTAL DE POSTS: "+ response[0].total);
		TOTAL = response[0].total;
	};
	
	var funcListPost = function(response) {
		for (key in response) {
			$('#div-list-posts').append(postHTML(
					response[key].id, 
					response[key].title, 
					response[key].date_publication, 
					response[key].preview_article, 
					response[key].first_name +' '+response[key].last_name, 
					response[key].email
					));
			$('#'+response[key].id).transition('scale');
		}
	};
	
	send({ACTION: 'LIST_POST_TOTAL'}, funcListTotal);
	send({ACTION: 'LIST_POST', INDEX: INDEX}, funcListPost);
	
	$(window).scroll(function(){
        if  ($(window).scrollTop() == $(document).height() - $(window).height()){
        	if (INDEX < TOTAL) {
            	INDEX += 5;
            	send({ACTION: 'LIST_POST', INDEX: INDEX}, funcListPost);
        	}
        }
}); 

});