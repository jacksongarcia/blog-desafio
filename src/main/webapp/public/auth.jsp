
<div class="ui two column centered grid" id="container">
  <div class="column">
	<h2 class="ui center aligned icon header">
	  Seja Bem-Vindo(a)!
	</h2>

  	<!-- Alert de erro -->
    <div class="ui error message hidden" id="div-alert-erro">
	    <div class="header">Atenção</div><hr/>
	    <div id="div-list-errors">
	    
	    </div>
	</div>
	
	 <div class="ui piled very padded segment" id="segment-login">
	 
		<form class="ui piled form" id="form-login">
		  <h4 class="ui dividing header">Entrar</h4>
		  
		  <div class="field" id="FIELD_LOGIN_EMAIL">
		    <label>Email</label>
		    <input type="email" name="INPUT_LOGIN_EMAIL" id="INPUT_LOGIN_EMAIL" placeholder="email@email.com">
		  </div>
		  
		  <div class="field" id="FIELD_LOGIN_PASSWORD">
		    <label>Senha</label>
		    <input type="password" name="INPUT_LOGIN_PASSWORD" id="INPUT_LOGIN_PASSWORD" placeholder="senha">
		  </div>
		  
		  	<div class="ui fluid buttons">
			  	<button class="ui button" id="button-segment-register">Cadastrar uma conta</button>
			  	<div class="or"></div>
			  	<button class="ui positive button" id="button-login">Entrar</button>
			</div>
		</form>
		
	</div>

  	<div class="ui piled very padded segment transition hidden" id="segment-register">
  
		<form class="ui form" id="form-register">
		
			<h3 class="ui dividing header">Cadastrar</h3>
		
			<div class="field">
	    		<label>Nome</label>
				<div class="two fields">
				
				  <div class="field" id="FIELD_REGISTER_NAME">
				    <input type="text" name="INPUT_REGISTER_NAME" id="INPUT_REGISTER_NAME" placeholder="Nome">
				  </div>
				  
				  <div class="field" id="FIELD_REGISTER_LAST_NAME">
				    <input type="text" name="INPUT_REGISTER_LAST_NAME" id="INPUT_REGISTER_LAST_NAME" placeholder="Sobrenome">
				  </div>
				  
				 </div>
			</div>
			
		   <div class="field" id="FIELD_REGISTER_EMAIL">
		   		<label>Email</label>
		   		<input type="email" name="INPUT_REGISTER_EMAIL" id="INPUT_REGISTER_EMAIL" placeholder="email@email.com">
		   </div>
		  
	  	  <div class="field">
	   			<label>Senha</label>
				<div class="two fields">
				  <div class="field" id="FIELD_REGISTER_PASSWORD">
				    <input type="password" name="INPUT_REGISTER_PASSWORD" id="INPUT_REGISTER_PASSWORD" placeholder="Senha">
				  </div>
				  
				  <div class="field" id="FIELD_REGISTER_CONFIRM_PASSWORD">
				    <input type="password" name="INPUT_REGISTER_CONFIRM_PASSWORD" id="INPUT_REGISTER_CONFIRM_PASSWORD" placeholder="Confirme a senha">
				  </div>
				</div>
		  </div>
		  
		  	<div class="ui fluid buttons">
			  	<button class="ui button" id="button-segment-login">Entrar com minha conta</button>
			  	<div class="or"></div>
			  	<button class="ui positive button" id="button-register">Cadastrar</button>
			</div>

		</form>

  	</div>
  
  </div>
</div>

<script>
	<c:import url="/resources/scripts/auth.js" />
</script>