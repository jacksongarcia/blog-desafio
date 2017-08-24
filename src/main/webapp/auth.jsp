
<div class="ui two column centered grid">
  <div class="column">
    <div class="ui error message hidden" id="alert-error">
	    <div class="header">Atenção</div><hr/>
	    <div id="errors">
	    </div>
	</div>
	
	<!-- loading -->
  	<div class="ui form" id="loading-form-login">
		<form class="ui form" id="form-login">
		  <h4 class="ui dividing header">Entrar</h4>
		  <div class="field" id="field-login-email">
		    <label>Email</label>
		    <input type="email" name="email" id="login-email" placeholder="email@email.com">
		  </div>
		  <div class="field" id="field-login-password">
		    <label>Senha</label>
		    <input type="password" name="password" id="login-password" placeholder="senha">
		  </div>
		  
		  	<div class="ui buttons">
			  	<button class="fluid ui button" id="button-register">Registrar</button>
			  	<div class="or"></div>
			  	<button class="fluid ui positive button" id="submit-login">Entrar</button>
			</div>
		</form>
	</div>
	
	 <div class="ui form transition hidden" id="form-register">
		<form class="ui form" id="loading-form-register">
			<h4 class="ui dividing header">Registrar</h4>
		
			<div class="field">
	    		<label>Nome</label>
				<div class="two fields">
				  <div class="field" id="field-first_name">
				    <input type="text" name="first_name" id="first_name" placeholder="Nome">
				  </div>
				  <div class="field" id="field-last_name">
				    <input type="text" name="last_name" id="last_name" placeholder="Sobrenome">
				  </div>
				 </div>
			</div>
			
		  <div class="field" id="field-email">
		    <label>Email</label>
		    <input type="email" name="email" id="email" placeholder="email@email.com">
		  </div>
		  
	  	  <div class="field">
	   		<label>Senha</label>
			<div class="two fields">
			  <div class="field" id="field-password">
			    <input type="password" name="password" id="password" placeholder="Senha">
			  </div>
			  
			  <div class="field" id="field-confirm_password">
			    <input type="password" name="confirm_password" id="confirm_password" placeholder="Confirme a senha">
			  </div>
			 </div>
			</div>
		  
		  	<div class="ui buttons">
			  	<button class="fluid ui button" id="button-login">Entrar</button>
			  	<div class="or"></div>
			  	<button class="fluid ui positive button" id="submit-register">Cadastrar</button>
			</div>
		</form>
	</div>
  </div>
</div>
