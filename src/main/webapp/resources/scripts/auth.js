$(function() {
	// ========================================================================================
	// MUDA A VIEW
	$('#button-segment-login').click(function(e){
		e.preventDefault();
		$('#segment-register').transition('scale');
		setTimeout(function(){$('#segment-login').transition('scale');}, 500);

	});
	
	$('#button-segment-register').click(function(e){
		e.preventDefault();
		$('#segment-login').transition('scale');
		setTimeout(function(){$('#segment-register').transition('scale');}, 500);

	});
	//=========================================================================================
	
	var send = function(data, funcSucess) {
		$.post('auth', data,
	    		function(response) {
					if (response === null) {
						$('#div-list-errors').html('<h4 class="ui header">Falha na rede</h4>');
						$('#div-alert-erro').show();
						
						setTimeout(function(){
							$('#div-list-errors').html("");
							$('#div-alert-erro').hide();
						}, 5000);
					}
					else if(response.length) {
						var text = '';
						for(key in response) {
							for(key2 in response[key]) {
        						$('#FIELD-'+key2).addClass('error');

								text += '<p>'+response[key][key2]+'</p>';

							}
						}
						
						if (text !== '') {
							$('#div-list-errors').html(text);
							$('#div-alert-erro').show();
							
							setTimeout(function(){
								$('#div-list-errors').html("");
								$('#div-alert-erro').hide();
							}, 5000);
						}
						else {
							$('#div-list-errors').html("");
							$('#div-alert-erro').hide();
						}

					} 
					else {
						funcSucess();
					}

					if (data.ACTION !== 'LOGIN') {
						$('#load').text("");
						$('#load').transition('scale');
						$('#container').transition('scale');
					}

	    		}
			);		
	};
	
	// CADASTRAR ============================================================
	$('#button-register').click(function(e){
		e.preventDefault();
		
		var INPUT_REGISTER_NAME = $('#INPUT_REGISTER_NAME').val();
		var INPUT_REGISTER_LAST_NAME = $('#INPUT_REGISTER_LAST_NAME').val();
		var INPUT_REGISTER_EMAIL = $('#INPUT_REGISTER_EMAIL').val();
		var INPUT_REGISTER_PASSWORD = $('#INPUT_REGISTER_PASSWORD').val();
		var INPUT_REGISTER_CONFIRM_PASSWORD = $('#INPUT_REGISTER_CONFIRM_PASSWORD').val();
		
		var text = validateFormatInput(
		INPUT_REGISTER_NAME, 
		INPUT_REGISTER_LAST_NAME, 
		INPUT_REGISTER_EMAIL, 
		INPUT_REGISTER_PASSWORD,
		INPUT_REGISTER_CONFIRM_PASSWORD);
		
		if (text !== '') {
			$('#div-list-errors').html(text);
			$('#div-alert-erro').show();
			
			return;
		}

		$('#container').transition('scale');
		$('#load').text("Cadastrando...");
		$('#load').transition('scale');
		
		var data = {
				ACTION : "REGISTER",
				REGISTER_NAME : INPUT_REGISTER_NAME,
				REGISTER_LAST_NAME : INPUT_REGISTER_LAST_NAME, 
				REGISTER_EMAIL : INPUT_REGISTER_EMAIL, 
				REGISTER_PASSWORD : INPUT_REGISTER_PASSWORD,
				REGISTER_CONFIRM_PASSWORD : INPUT_REGISTER_CONFIRM_PASSWORD
			};
		
		var funcSucess = function() {
			$('#form-register input').val('');
			$('#div-list-errors').html('<h2 class="ui header">Cadastrado com sucesso!</h2>');
			$('#div-alert-erro').removeClass('error');
			$('#div-alert-erro').addClass('positive');
			$('#div-alert-erro').show();
			
			$('#button-segment-login').trigger('click');
			
			setTimeout(function(){
				$('#div-list-errors').html("");
				$('#div-alert-erro').hide();
			}, 3000);
		};
		
		send(data, funcSucess);
	});
	
	var validateFormatInput = function(INPUT_REGISTER_NAME, 
			INPUT_REGISTER_LAST_NAME, 
			INPUT_REGISTER_EMAIL, 
			INPUT_REGISTER_PASSWORD,
			INPUT_REGISTER_CONFIRM_PASSWORD) {
		var text = '';
		if (INPUT_REGISTER_NAME === '')
			text += '<p>Campo de nome vazio</p>';
		else if (INPUT_REGISTER_NAME.length < 3)
			text += '<p>Campo de nome deve ter no minimo de letras</p>';
		
		if(INPUT_REGISTER_LAST_NAME === '')
			text += '<p>Campo de sobrenome vazio</p>';
		else if (INPUT_REGISTER_LAST_NAME.length < 3)
			text += '<p>Campo de sobrenome deve ter no minimo de letras</p>';
		
		if(INPUT_REGISTER_EMAIL === '')
			text += '<p>Campo de email vazio</p>';
		
		if(INPUT_REGISTER_PASSWORD === '')
			text += '<p>Campo de senha vazio</p>';
		else if(INPUT_REGISTER_PASSWORD.length < 6)
			text += '<p>A senha deve ser maior que 6 digitos</p>';
		
		if(INPUT_REGISTER_CONFIRM_PASSWORD === '')
			text += '<p>Campo de confirmar senha vazio</p>';
		else if(INPUT_REGISTER_CONFIRM_PASSWORD.length < 6)
			text += '<p>O campo de confirmar senha deve ser maior que 6 digitos</p>';
		
		if(INPUT_REGISTER_PASSWORD !== '' && INPUT_REGISTER_CONFIRM_PASSWORD !== '') {
			if (INPUT_REGISTER_PASSWORD !== INPUT_REGISTER_CONFIRM_PASSWORD)
				text += '<p>As senhas são diferentes</p>';
		}
		
		return text;
	};
	
	// LOGIN ========================================================
	$('#button-login').click(function(e){
		e.preventDefault();
		
		var INPUT_LOGIN_EMAIL = $('#INPUT_LOGIN_EMAIL').val();
		var INPUT_LOGIN_PASSWORD = $('#INPUT_LOGIN_PASSWORD').val();
		
		var text = '';
		if(INPUT_LOGIN_EMAIL === '')
			text += '<p>Campo de email vazio</p>';
		
		if(INPUT_LOGIN_PASSWORD === '')
			text += '<p>Campo de senha vazio</p>';
		else if(INPUT_LOGIN_PASSWORD.length < 6)
			text += '<p>A senha deve ser maior que 6 digitos</p>';
		
		if (text !== '') {
			$('#div-list-errors').html(text);
			$('#div-alert-erro').show();
			
			return;
		}

		$('#container').transition('scale');
		$('#load').text("Fazendo login...");
		$('#load').transition('scale');
		
		var data = {
				ACTION : "LOGIN",
				LOGIN_EMAIL : INPUT_LOGIN_EMAIL, 
				LOGIN_PASSWORD : INPUT_LOGIN_PASSWORD
			};
		
		var funcSucess = function() {
			var path = $(location).attr('href');
			path = path.substring(0, path.lastIndexOf('/')+1);
			$(location).attr('href', path+'index');
		};
		
		send(data, funcSucess);
	});
});