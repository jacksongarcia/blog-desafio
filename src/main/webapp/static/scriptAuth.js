$(document).ready(function() {

    $('#submit-register').click(function(event) {
    	event.preventDefault();
    	
    	var arrayIdInput = ['first_name', 'last_name', 'email', 'password', 'confirm_password'];
    	for (key in arrayIdInput) {
    		$('#field-'+arrayIdInput[key]).removeClass('error');
    	}
    	
        var first_name 		 = $('#'+arrayIdInput[0]).val();
        var last_name 		 = $('#'+arrayIdInput[1]).val();
        var email 			 = $('#'+arrayIdInput[2]).val();
        var password 		 = $('#'+arrayIdInput[3]).val();
        var confirm_password = $('#'+arrayIdInput[4]).val();

        $('#loading-form-register').addClass('loading');
        $.post('auth',
        		{
        			type : 'register',
        			first_name : first_name,
        			last_name : last_name,
        			email : email,
        			password : password,
        			confirm_password : confirm_password,
        		},
        		function(response) {

        			if (response.erro == true) {
        				var text = '';
        				for (key in response) {
        					if (key !== 'erro') {
        						$('#field-'+key).addClass('error');
        						text += '<p>'+response[key]+'</p>';
        					}
        				}
        					
        				$('#errors').html(text);
        				$('#alert-error').show('scale');
        				        				
        			} else if (response.seccess === true) {
        				$('#loading-form-register input').val("");
        				 $('#button-login').trigger("click");
        			}
        			
        	        $('#loading-form-register').removeClass('loading');
        		}
        	);
    });
    
    // ------------------------- LOGIN -----------------------------------
    $('#submit-login').click(function(event) {
    	event.preventDefault();
    	
    	var arrayIdInput = ['email', 'password'];
    	for (key in arrayIdInput) {
    		$('#field-login-'+arrayIdInput[key]).removeClass('error');
    	}
    	
        var email		= $('#login-'+arrayIdInput[0]).val();
        var password	= $('#login-'+arrayIdInput[1]).val();

        $('#loading-form-login').addClass('loading');
        $.post('auth',
        		{
        			type  : 'login',
        			email : email,
        			password : password
        		},
        		function(response) {

        			if (response.erro == true) {
        				var text = '';
        				for (key in response) {
        					if (key !== 'erro') {
        						$('#field-login-'+key).addClass('error');
        						text += '<p>'+response[key]+'</p>';
        					}
        				}
        					
        				$('#errors').html(text);
        				$('#alert-error').show('scale');
        				
            	        $('#loading-form-login').removeClass('loading');
            	        
        			} else if (response.seccess === true) {
        				$('#form-login input').val("");
        				
        				$(location).attr('href', response.url+'/index');
        			}
        			
        		}
        	);
    });
    
    $('#button-register').click(function(e) {
    	e.preventDefault();
    	$('#errors').html('');
    	$('#alert-error').hide();
    	
    	$('#form-login')
    	  .transition('scale', '500ms', function() {
    		  $('#form-register').transition('scale');
    	  });
    });

    $('#button-login').click(function(e) {
    	e.preventDefault();
    	$('#errors').html('');
    	$('#alert-error').hide();

    	$('#form-register')
    	  .transition('scale', '500ms', function() {
    		  $('#form-login').transition('scale');
    	  });
    });
});

