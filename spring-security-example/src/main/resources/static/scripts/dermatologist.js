function parseJwt (token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
};
var input_first_name;
var input_last_name;
var input_country;
var input_city;
var input_address;
var input_phone;
var input_email;
var btnSubmit;
$(document).ready(function() {	    				    	    	   	
		$('a#logout').click(function(){
		localStorage.removeItem('jwt')		
		location.href = "login.html";
		});
		$('a#appointment').click(function(){
		$('#edit-profile').attr('hidden', true);
		$('#show').attr('hidden',true);
		$('#patientAppointement').attr('hidden',false);
		customAjax({
				method:'GET',
		        url:'/patient/getAll',
		        contentType: 'application/json',
	    		success: function(data) {
				for (var i = 0; i < data.length; i++) { 
					 var option = document.createElement("option");
					 var dropdown = document.getElementById("dropdownPatients");
					 option.text = (data[i].user.email);
					 dropdown.add(option); }
				$('#dropdownPatients').on('change',function(){	
				var patient = $('#dropdownPatients option:selected').text();
				console.log("*********"+patient)
				var email = patient
		
		customAjax({
				method:'GET',
		        url:'/patient/profileP/' + email,
		        contentType: 'application/json',
	    		success: function(data) {
					list = data					
					var drugs = list.join('');
					
					var listString = "";
					for (var i = 0; i < data.length; i++) {
						listString += data[i] + "*";
					 }
				drugs=listString
				console.log(listString)
					

		customAjax({
				method:'GET',
		        url:'/patient/getDrugs/' + drugs,
		        contentType: 'application/json',
	    		success: function(data) {					
				console.log("Usao u lijekove")	
				console.log(data)
	    		},
	    		error:function(message){
					alert("Error")
	    			console.log('Error')
	    		}
	    	});	
						
					
	    		},
	    		error:function(message){
					alert("Error")
	    			console.log('Error')
	    		}
	    	});				  					    		
					});
	    		},
	    		error:function(message){
					alert("Error")
	    		}
	    	});
		});
		$('a#profile').click(function(){
			$('#edit-profile').attr('hidden', false);
			$('#show').attr('hidden',true)
			$('#patientAppointement').attr('hidden',true);
			
			console.log("Usao")		
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
	 btnSubmit = document.getElementById("id_submit_changes")
  	 btnSubmit.disabled = true
	 input_first_name=$('#id_first_name');
	 input_last_name= $('#id_last_name')
	 input_country = $('#id_country')
 	 input_city=$('#id_city')
	 input_address = $('#id_address')
	 input_phone = $('#id_phone')
	 input_email = $('#id_email')
	 input_first_name.keyup(function () {
	  	if(validateEmail(input_email.val()) && validateName(input_first_name.val()) && validateName(input_last_name.val())  && validateName(input_country.val()) && validateName(input_city.val()) && validateAddress(input_address.val()) && validateNumber(input_phone.val())) {
			btnSubmit.disabled = false
	  	}
	  	if(!validateName(input_first_name.val())){
	  		btnSubmit.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#id_first_name').css('border-color', 'red');
	  		$("#errorFirstName").text("You can only use letters for first name!")
	  		$('#errorFirstName').css('color', 'red');
	  	}else {
	  		$(this).removeClass(`alert-danger`);
	  		$('#id_first_name').css('border-color', '');
	  		$("#errorFirstName").text("")
	  	}
  });
	input_last_name.keyup(function () {
	  	if(validateEmail(input_email.val()) && validateName(input_first_name.val()) && validateName(input_last_name.val())  && validateName(input_country.val()) && validateName(input_city.val()) && validateAddress(input_address.val()) && validateNumber(input_phone.val())) {
			btnSubmit.disabled = false
	  	}
	  	if(!validateName(input_last_name.val())){
	  		btnSubmit.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#id_last_name').css('border-color', 'red');
	  		$("#errorLastName").text("You can only use letters for last name!")
	  		$('#errorLastName').css('color', 'red');
	  	}else {
	  		$(this).removeClass(`alert-danger`);
	  		$('#id_last_name').css('border-color', '');
	  		$("#errorLastName").text("")
	  	}
  });
	input_country.keyup(function () {
	  	if(validateEmail(input_email.val()) && validateName(input_first_name.val()) && validateName(input_last_name.val())  && validateName(input_country.val()) && validateName(input_city.val()) && validateAddress(input_address.val()) && validateNumber(input_phone.val())) {
			btnSubmit.disabled = false
	  	}
	  	if(!validateName(input_country.val())){
	  		btnSubmit.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#id_country').css('border-color', 'red');
	  		$("#errorCountry").text("You can only use letters for country!")
	  		$('#errorCountry').css('color', 'red');
	  	}else {
	  		$(this).removeClass(`alert-danger`);
	  		$('#id_country').css('border-color', '');
	  		$("#errorCountry").text("")
	  	}
  });
	input_city.keyup(function () {
	  	if(validateEmail(input_email.val()) && validateName(input_first_name.val()) && validateName(input_last_name.val())  && validateName(input_country.val()) && validateName(input_city.val()) && validateAddress(input_address.val()) && validateNumber(input_phone.val())) {
			btnSubmit.disabled = false
	  	}
	  	if(!validateName(input_city.val())){
	  		btnSubmit.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#id_city').css('border-color', 'red');
	  		$("#errorCity").text("You can only use letters for city!")
	  		$('#errorCity').css('color', 'red');
	  	}else {
	  		$(this).removeClass(`alert-danger`);
	  		$('#id_city').css('border-color', '');
	  		$("#errorCity").text("")
	  	}
  });
input_address.keyup(function () {
	  	if(validateEmail(input_email.val()) && validateName(input_first_name.val()) && validateName(input_last_name.val())  && validateName(input_country.val()) && validateName(input_city.val()) && validateAddress(input_address.val()) && validateNumber(input_phone.val())) {
			btnSubmit.disabled = false
	  	}
	  	if(!validateAddress(input_address.val())){
	  		btnSubmit.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#id_address').css('border-color', 'red');
	  		$("#errorAddress").text("You can only use letters and numbers without spaces at the end!")
	  		$('#errorAddress').css('color', 'red');
	  	}else {
	  		$(this).removeClass(`alert-danger`);
	  		$('#id_address').css('border-color', '');
	  		$("#errorAddress").text("")
	  	}
  });
input_phone.keyup(function () {
	  	if(validateEmail(input_email.val()) && validateName(input_first_name.val()) && validateName(input_last_name.val())  && validateName(input_country.val()) && validateName(input_city.val()) && validateAddress(input_address.val()) && validateNumber(input_phone.val())) {
			btnSubmit.disabled = false
	  	}
	  	if(!validateNumber(input_phone.val())){
	  		btnSubmit.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#id_phone').css('border-color', 'red');
	  		$("#errorPhone").text("You can only use numbers for phone!")
	  		$('#errorPhone').css('color', 'red');
	  	}else {
	  		$(this).removeClass(`alert-danger`);
	  		$('#id_phone').css('border-color', '');
	  		$("#errorPhone").text("")
	  	}
  });
	  $('#id_submit_changes').click(function(){
		let firstName=$('#id_first_name').val()
		let lastName=$('#id_last_name').val()
		let country=$('#id_country').val()
		let city=$('#id_city').val()
		let address=$('#id_address').val()
		let phone=$('#id_phone').val()
		let email=$('#id_email').val()

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
	        	alert("Sucess.")
				location.href = "dermatologist.html";
	        	
			},
		      error: function(){
		       	alert('Error');
		      }
    });
		
   
    })
		
		
		$('a#changePassword').click(function(){	
		$('#edit-profile').attr('hidden', true);
		$('#show').attr('hidden',false);
		$('#patientAppointement').attr('hidden',true);
		
		input_password = $('#id_password');
		console.log(input_password)
		input_passwordConf = $('#id_passwordConf');
  		var btnChange = document.getElementById("btnChange")
		console.log(btnChange)
  		btnChange.disabled = true
  
  input_password.keyup(function () {
	  	if(!validatePassword(input_password.val())){
	  		btnChange.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#id_password').css('border-color', 'red');
	  		$("#errorPassword").text("Password must have at least 8 characters, lower case, upper case, digit, special character!")
	  		$('#errorPassword').css('color', 'red');
	  	}else {
	  		$(this).removeClass(`alert-danger`);
	  		$('#id_password').css('border-color', '');
	  		$("#errorPassword").text("")
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
		var newPassword = $('#id_password').val()
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
				location.href = "dermatologist.html";
			},
			error: function(){
				localStorage.removeItem('email');
				alert("User with that email doesn't exist")
			}
	            });

		});
	
		});
		});

function validatePassword(password) {
	  
	  var strongRegex = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})");
	  	if(password.match(strongRegex)) {
	  		return true;
	  	}
	  	else {
	  		return false;
	  	}
}
function showProfile(data){
	$('#edit-profile').attr('hidden', false);
    			 $('#id_first_name').val(data.firstName);
    		    	$('#id_last_name').val(data.lastName);
    		    	$('#id_country').val(data.country);
    		    	$('#id_city').val(data.city);
					$('#id_address').val(data.address);
					$('#id_phone').val(data.phone);
					$('#id_email').val(data.email);
};
function validateName(name) {
	    const re =/^[A-Za-z ]+$/;
	    return re.test(String(name));
}
function validateAddress(address) {
	    const re =/^\w+( \w+)*$/ ;
	    return re.test(String(address));
}

  
function validateEmail(email) {
	    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	    return re.test(String(email).toLowerCase());
}
function validateNumber(name) {
	    const re = /^[0-9]+$/;
	    return re.test(String(name));
}

	
