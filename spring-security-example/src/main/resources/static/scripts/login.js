var input_email;
var input_password;
var button_login;


var p_log;
function parseJwt (token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
};

function authentification(){
    var role =localStorage.getItem('role');
    if (role == 'ROLE_ADMIN_SYSTEM')
        window.location.href = "/html/adminsystem.html";
    else if ( role == 'ROLE_PATIENT')
        window.location.href = "/html/patient.html";
    else if ( role == 'ROLE_PHARMACIST')
        window.location.href = "/html/pharmacist.html";
    else if ( role == 'ROLE_DERMATOLOGIST')
        window.location.href = "/html/dermatologist.html";
    else if ( role == 'ROLE_ADMIN_PHARMACY')
    	window.location.href = "/html/adminpharmacy.html";
    else if ( role == 'ROLE_SUPPLIER')
        window.location.href = "/html/supplier.html";
}
$(document).ready(function(e){

  input_email = $('#id_email');
  input_password = $('#id_password');
  var btnLogin = document.getElementById("id_button")
//  btnLogin.disabled = true
  
  input_email.keyup(function () {
	  	if(validateEmail(input_email.val()) && validatePassword(input_password.val())) {
	  		btnLogin.disabled = false
	  	}
	  	if(!validateEmail(input_email.val())){
	  		btnLogin.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#id_email').css('border-color', 'red');
	  		$("#errorEmail").text("Email is in wrong format!")
	  		$('#errorEmail').css('color', 'red');
	  	}else {
	  		$(this).removeClass(`alert-danger`);
	  		$('#id_email').css('border-color', '');
	  		$("#errorEmail").text("")
	  	}
  });
  
  input_password.keyup(function () {
	  if(validateEmail(input_email.val()) && validatePassword(input_password.val())) {
	  		btnLogin.disabled = false
	  	}
		if(!validatePassword(input_password.val())) {
			//btnLogin.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#id_password').css('border-color', 'red');
	  		$("#errorPassword").text("Password must have at least 8 characters, lower case, upper case, digit, special character!")
	  		$('#errorPassword').css('color', 'red');
		} else {
			$(this).removeClass(`alert-danger`);
	  		$('#id_password').css('border-color', '');
	  		$("#errorPassword").text("")
	  		
		}
	});
	  	
		
  button_login = $('#id_button');
  p_log = $('#id_p_log');

  button_login.on('click', function(e){
	
    var email = input_email.val();
    var password = input_password.val();
    localStorage.setItem('email', email);
    customAjax({
          url: '/auth/checkIfLogged',
          method: 'GET',
          data: { email: email, password: password },
          success: function(data){
        	  console.log(data)
        	  if(data.responseText == "LOGGED") {
               customAjax({
                                     url: '/auth/login',
                                     method: 'POST',
                                     data: { email: email, password: password },
                                     success: function(jwt, status, xhr){
                               	        if(xhr.status == 200){
                               	        var decoded = parseJwt(jwt.accessToken);
                                           console.log(decoded);
                               	        localStorage.setItem('email', email);
                               	        localStorage.setItem('jwt', jwt.accessToken);
                               	        localStorage.setItem('role', decoded.role)
                               	        authentification();
                                   	}



                                     },
                                     error: function(){

                                       p_log.text('You entered wrong email or password. Try again!');
                                     }
               
                                   }) }
        	  else if (data.responseText == "NOT LOGGED") {
        		  location.href = "changePassword.html";
        	  } else {
        		  p_log.text('You entered wrong email or password. Try again!');
        	  }
                  },
          error: function(){
          }
        });
  });
  btnAcceptChange = document.getElementById("acceptChangeC")
	//btnAcceptChange.disabled = true


$('#txtNewPasswordC').keyup(function () {
	  	if(!validatePassword($('#txtNewPasswordC').val())){
	  		btnAcceptChange.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#txtNewPasswordC').css('border-color', 'red');
	  		$("#errorPasswordC").text("Password must have at least 8 characters, lower case, upper case, digit, special character!")
	  		$('#errorPasswordC').css('color', 'red');
	  	}else {
	  		$(this).removeClass(`alert-danger`);
	  		$('#txtNewPasswordC').css('border-color', '');
	  		$("#errorPasswordC").text("")
	  	}
});
$('#txtNewPasswordRepeatC').keyup(function () {
	  	if($('#txtNewPasswordC').val()!=$('#txtNewPasswordRepeatC').val()){
	  		btnAcceptChange.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#txtNewPasswordRepeatC').css('border-color', 'red');
	  		$("#errorPasswordC").text("Passwords must match!")
	  		$('#errorPasswordC').css('color', 'red');
	  	}else {

	  		$(this).removeClass(`alert-danger`);
	  		$('#txtNewPasswordRepeatC').css('border-color', '');
	  		$("#errorPasswordC").text("")
			btnAcceptChange.disabled = false;
	  	}
});

$('#acceptChangeC').click(function() {
		var newPassword = $('#txtNewPasswordC').val()
		var confirmPassword = $('#txtNewPasswordRepeatC').val()
		var email = localStorage.getItem('email')
	
		obj = JSON.stringify({email:email,newPass:newPassword,confirmPass:confirmPassword});
		customAjax({
	        method:'POST',
	        url:'/auth/changePassword',
	        data : obj,
	        contentType: 'application/json',
	        success: function(){
	        	localStorage.removeItem('email')
	        	alert("Success changed password!")
				location.href = "login.html";
			},
			error: function(){
				localStorage.removeItem('email');
				alert("User with that email doesn't exist")
			}
	            });

		});


  
  
  function validateEmail(email) {
	    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	    return re.test(String(email).toLowerCase());
}
  
  function validatePassword(password) {
	  
	  var strongRegex = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})");
	  	if(password.match(strongRegex)) {
	  		return true;
	  	}
	  	else {
	  		return false;
	  	}
}

  function sanatize(input) {
    var output = input.replace(/<script[^>]*?>.*?<\/script>/gi, '').
           replace(/<[\/\!]*?[^<>]*?>/gi, '').
           replace(/<style[^>]*?>.*?<\/style>/gi, '').
           replace(/<![\s\S]*?--[ \t\n\r]*>/gi, '');
      return output;
  };

});