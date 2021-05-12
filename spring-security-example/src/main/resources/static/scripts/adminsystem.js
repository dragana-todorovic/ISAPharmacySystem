var input_email;
var input_first_name;
var input_last_name;
var input_password;
var input_password_repeat;
var button_register;

var p_log;
function parseJwt (token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
};
function showProfile(data){
	$('#edit-profile').attr('hidden', false);
	  $('#register_admin_system').attr('hidden', true);
        $('#show').attr('hidden',true);
    			 $('#id_first_name_e').val(data.firstName);
    		    	$('#id_last_name_e').val(data.lastName);
    		    	$('#id_country_e').val(data.country);
    		    	$('#id_city_e').val(data.city);
					$('#id_address_e').val(data.address);
					$('#id_phone_e').val(data.phone);
					$('#id_email_e').val(data.email);
};
$(document).ready(function(e){
    $('#register_admin_system').attr('hidden', true);
	$('#edit-profile').attr('hidden', true);
    $('#show').attr('hidden',true);

    $('a#logout').click(function(){
		localStorage.removeItem('jwt')
		location.href = "login.html";
	});
    $('a#a_register_admin_system').click(function(){
    	 $('#register_admin_system').attr('hidden', false);
	     $('#edit-profile').attr('hidden', true);
        $('#show').attr('hidden',true);
    });
      input_first_name=$('#id_first_name');
      input_last_name=$('#id_last_name');
      input_country = $('#id_country');
      input_city = $('#id_city');
      input_address = $('#id_address');
      input_phone = $('#id_phone');
      input_email = $('#id_email');
      input_password = $('#id_password');
      input_password_repeat = $('#id_password_repeat');
      var btnRegister = document.getElementById("register_adminsystem_button")
      btnRegister.disabled = true

  input_last_name.keyup(function () {
	  	if(validateEmail(input_email.val()) && validatePassword(input_password.val()) && validateName(input_first_name.val())  && validateName(input_last_name.val())) {
	  		btnRegister.disabled = false
	  	}
	  	if(!validateName(input_last_name.val())){
	  		btnRegister.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#id_last_name').css('border-color', 'red');
	  		$("#errorLastName").text("You can only use letters for first and last name!")
	  		$('#errorLastName').css('color', 'red');
	  	}else {
	  		$(this).removeClass(`alert-danger`);
	  		$('#id_last_name').css('border-color', '');
	  		$("#errorLastName").text("")
	  	}
  });

  input_first_name.keyup(function () {
	  	if(validateEmail(input_email.val()) && validatePassword(input_password.val()) && validateName(input_first_name.val())  && validateName(input_last_name.val())) {
	  		btnRegister.disabled = false
	  	}
	  	if(!validateName(input_first_name.val())){
	  		btnRegister.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#id_first_name').css('border-color', 'red');
	  		$("#errorFirstName").text("You can only use letters for first and last name!")
	  		$('#errorFirstName').css('color', 'red');
	  	}else {
	  		$(this).removeClass(`alert-danger`);
	  		$('#id_first_name').css('border-color', '');
	  		$("#errorFirstName").text("")
	  	}
  });

  input_email.keyup(function () {
	  	if(validateEmail(input_email.val()) && validatePassword(input_password.val()) && validateName(input_first_name.val()) && validateName(input_last_name.val())) {
	  		btnRegister.disabled = false
	  	}
	  	if(!validateEmail(input_email.val())){
	  		btnRegister.disabled = true
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
	  if(validateEmail(input_email.val()) && validatePassword(input_password.val()) && validateName(input_first_name.val()) && validateName(input_last_name.val())) {
	  		btnRegister.disabled = false
	  	}
		if(!validatePassword(input_password.val())) {
			btnRegister.disabled = true
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
	input_password_repeat.keyup(function () {
		if(input_password.val()!=input_password_repeat.val()){
			btnRegister.disabled = true
			$("#errorPasswordRepeat").text("Passwords do not match!")
			$(this).addClass(`alert-danger`);
	  		$('#id_password_repeat').css('border-color', 'red');
			$('#errorPasswordRepeat').css('color', 'red');
		}
		else {
			btnRegister.disabled = false
			$(this).removeClass(`alert-danger`);
	  		$('#id_password_repeat').css('border-color', '');
	  		$("#errorPasswordRepeat").text("")
		}


	});
    button_register = $('#register_adminsystem_button');
    p_log = $('#id_p_log');

    button_register.on('click', function(e){

	var first_name=input_first_name.val();
	var last_name=input_last_name.val();
	var country=input_country.val();
	var city=input_city.val();
	var address=input_address.val();
	var phoneNumber=input_phone.val();
    var email = input_email.val();
    var password = input_password.val();

	obj = JSON.stringify({
		firstname:first_name,
		lastname:last_name,
		country: country,
		city:city,
		address:address,
		phone :phoneNumber,
		email:email,
		password:password});

    customAjax({
      url: '/auth/registerAdminSystem',
      method: 'POST',
      data:obj,
	  contentType: 'application/json',
	        success: function(){
		  	    p_log.text('')
		  	    input_first_name.val('');
                	input_last_name.val('');
                	input_country.val('');
                	input_city.val('');
                	input_address.val('');
                	input_phone.val('');
                    input_email.val('');
                    input_password.val('');
	        	alert("You have successfully registered another admin system.")
			},
		      error: function(){
		       	p_log.text('Error');
		      }
    });


  });

$('a#profile').click(function(){
			var id = localStorage.getItem('email')
			customAjax({
				method:'GET',
		        url:'/user/profilePatient/' + id,
		        contentType: 'application/json',
	    		success: function(data) {
				console.log("uspesno profil");
				console.log(data);
				showProfile(data)

	    		},
	    		error:function(message){
					alert("Error")
	    			console.log('Error')
	    		}
	    	});

	    })
	  $('#id_submit_changes').click(function(){
		let firstName=$('#id_first_name_e').val()
		let lastName=$('#id_last_name_e').val()
		let country=$('#id_country_e').val()
		let city=$('#id_city_e').val()
		let address=$('#id_address_e').val()
		let phone=$('#id_phone_e').val()
		let email=$('#id_email_e').val()

		obj = JSON.stringify({
		firstname:firstName,
		lastname:lastName,
		country: country,
		city:city,
		address:address,
		phone :phone,
		email:email});

		    customAjax({
      url: '/user/editProfile',
      method: 'POST',
      data:obj,
	  contentType: 'application/json',
	        success: function(){
	        	alert("Sucessfully edited profile.")

			},
		      error: function(){
		       	alert('Error');
		      }
    });


    })

  //[a-zA-Z]+
 function validateName(name) {
	    const re = /^[A-Za-z]+$/;
	    return re.test(String(name));
}

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

	function timer() {
	  localStorage.removeItem('obj')
	}

	$('a#changePassword').click(function(){
    		$('#show').attr('hidden',false);
    		  $('#register_admin_system').attr('hidden', true);
            	$('#edit-profile').attr('hidden', true);
    		input_password = $('#id_password_change');
    		console.log(input_password)
    		input_passwordConf = $('#id_passwordConf');
      		var btnChange = document.getElementById("btnChange")
    		console.log(btnChange)
      		btnChange.disabled = true

      input_password.keyup(function () {
    	  	if(!validatePassword(input_password.val())){
    	  		btnChange.disabled = true
    			$(this).addClass(`alert-danger`);
    	  		$('#id_password_change').css('border-color', 'red');
    	  		$("#errorPasswordChange").text("Password must have at least 8 characters, lower case, upper case, digit, special character!")
    	  		$('#errorPasswordChange').css('color', 'red');
    	  	}else {
    	  		$(this).removeClass(`alert-danger`);
    	  		$('#id_password_change').css('border-color', '');
    	  		$("#errorPasswordChange").text("")
    	  	}
      });
    	input_passwordConf.keyup(function () {
    	  	if(input_password.val()!=input_passwordConf.val()){
    	  		btnChange.disabled = true
    			$(this).addClass(`alert-danger`);
    	  		$('#id_passwordConf').css('border-color', 'red');
    	  		$("#errorPasswordConf").text("Passwords must match!")
    	  		$('#errorPasswordConf').css('color', 'red');
    	  	}else {

    	  		$(this).removeClass(`alert-danger`);
    	  		$('#id_passwordConf').css('border-color', '');
    	  		$("#errorPasswordConf").text("")
    			btnChange.disabled = false;
    	  	}
      });
    	$('#btnChange').click(function() {
    		var newPassword = $('#id_password_change').val()
    		var confirmPassword = $('#id_passwordConf').val()
    		var decoded = parseJwt(localStorage.getItem('jwt'));
    		var email = decoded.email
    		obj = JSON.stringify({email:email,newPass:newPassword,confirmPass:confirmPassword});
    		customAjax({
    	        method:'POST',
    	        url:'/auth/changePassword',
    	        data : obj,
    	        contentType: 'application/json',
    	        success: function(){
    				localStorage.removeItem('email');
    	        	alert("Success changed password!")
    				location.href = "adminsystem.html";
    			},
    			error: function(){
    				localStorage.removeItem('email');
    				alert("User with that email doesn't exist")
    			}
    	            });

    		});

    		});

});