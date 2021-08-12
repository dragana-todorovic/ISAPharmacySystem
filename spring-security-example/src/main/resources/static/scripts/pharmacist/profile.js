$(document).ready(function(e){
	var email = localStorage.getItem('email')
	
	  $("#profileInfo").click(function () {
		  customAjax({
		      url: '/user/getByEmail/' + email,
		      method: 'GET',
		      success: function(data){
			     showProfile(data)
		      },
		      error: function(){
		      }
	
	 });
	  });  
	
	$('#logout').click(function(){
		console.log("blabla")
		localStorage.removeItem('jwt')		
		location.href = "login.html";
		});
	
	
	
  });
let showProfile = function(user) {

	 $("#showData").html(`<table class="ui large table" style="width:50%; margin-left:auto; 
    margin-right:auto; margin-top: 40px;">
		        <thead>
		            <tr class="success">
		                <th colspan="2" class = "text-info" style= "text-align:center;">My profile</th>
		            </tr>
		        </thead>
		        <tbody>
		            <tr>
		                <td>Email:</td>
		                <td>` + ((user.username != null) ? user.username:`-`) + `</td>
		            </tr>
		            <tr>
		                <td>First name:</td>
		                <td>` + ((user.firstName != null) ? user.firstName:`-`) + `</td>
		            </tr>
		            <tr>
		                <td>Last name:</td>
		                <td>` + ((user.lastName != null) ? user.lastName:`-`) + `</td>
		            </tr>
		            <tr>
		                <td>Country:</td>
		                <td>` + ((user.country != null) ? user.country:`-`) + `</td>
		            </tr>
		             <tr>
		                <td>City:</td>
		                <td>` + ((user.city != null) ? user.city:`-`) + `</td>
		            </tr>
		             <tr>
		                <td>Address:</td>
		                <td>` + ((user.address != null) ? user.address:`-`) + `</td>
		            </tr>
		             <tr>
		                <td>Phone number:</td>
		                <td>` + ((user.phone != null) ? user.phone:`-`) + `</td>
		            </tr>
		        </tbody>
		        <tfoot class="full-width">
    <tr>
      <th></th>
      <th colspan="2">
		   <input id = "changePassword" class="ui right floated teal basic button" type = "button" value = "Change password"></input>
      
          <input id = "changeData" class="ui right floated teal basic button" type = "button" value = "Edit profile"></input>
		  
       
      </th>
    </tr>
  </tfoot>
		    </table> <p id="er"> </p>`);
	 
	 $("#changeData").click(function () {
		 editProfile(user)
	 });
	 
	 $("#changePassword").click(function () {
		 changePassword()
	 });
	
	 
};


let editProfile = function(user) {

	 $("#showData").html(`<table class="ui large table" style="width:50%; margin-left:auto; 
			    margin-right:auto; margin-top: 40px;">
					        <thead>
					            <tr class="success">
					                <th colspan="2" class = "text-info" style= "text-align:center;">Edit profile</th>
					            </tr>
					        </thead>
					        <tbody>
					            <tr>
					                <td>Email:</td>
					                <td class="ui input small"> <input type="text" id="txtUsername" disabled="disabled" value="`+ ((user.username != null) ? user.username:`` ) + `"/></td>
					              
					            </tr>
					            <tr>
					                <td>First name:</td>
					                <td class="ui input small"> <input type="text" id="txtFirstName" value="`+ ((user.firstName != null) ? user.firstName:`` ) + `"/></td>
					              
					            </tr>
					            <tr>
					                <td>Last name:</td>
					                <td class="ui input small"> <input type="text" id="txtLastName" value="`+ ((user.lastName != null) ? user.lastName:`` ) + `"/></td>
					              
					            </tr>
					           <tr>
					                <td>Country:</td>
					                <td class="ui input small"> <input type="text" id="txtCountry" value="`+ ((user.country != null) ? user.country:`` ) + `"/></td>
					              
					            </tr>
					           <tr>
					                <td>City:</td>
					                <td class="ui input small"> <input type="text" id="txtCity" value="`+ ((user.city != null) ? user.city:`` ) + `"/></td>
					              
					            </tr>
					           <tr>
					                <td>Address:</td>
					                <td class="ui input small"> <input type="text" id="txtAddress" value="`+ ((user.address != null) ? user.address:`` ) + `"/></td>
					              
					            </tr>
					           <tr>
					                <td>Phone number:</td>
					                <td class="ui input small"> <input type="text" id="txtPhoneNumber" value="`+ ((user.phone != null) ? user.phone:`` ) + `"/></td>
					              
					            </tr>
					           
					        </tbody>
					        <tfoot class="full-width">
			    <tr>
			      <th></th>
			      <th colspan="2">
					   <input id = "acceptChange" class="ui right floated positive basic button" type = "button" value = "Accept changes"></input>
			    
			      </th>
			    </tr>
<p id="errorInput"> </p>
			  </tfoot>
					    </table> <p id="er"> </p>`);
		btnAcceptChange = document.getElementById("acceptChange")
		btnAcceptChange.disabled = true
	
	
	 input_first_name=$('#txtFirstName');
	 input_last_name= $('#txtLastName')
	 input_country = $('#txtCountry')
 	 input_city=$('#txtCity')
	 input_address = $('#txtAddress')
	 input_phone = $('#txtPhoneNumber')
	 input_email=user.email
	 input_first_name.keyup(function () { console.log("Usao")
	  	if(validateName(input_first_name.val()) && validateName(input_last_name.val())  && validateName(input_country.val()) && validateName(input_city.val()) && validateAddress(input_address.val()) && validateNumber(input_phone.val())) {
			btnAcceptChange.disabled = false
	  	}
	  	if(!validateName(input_first_name.val())){
	  		btnAcceptChange.disabled = true
				$(this).addClass(`alert-danger`);
		  		$('#txtFirstName').css('border-color', 'red');
		  		$("#errorInput").text("You can only use letters for first name!")
		  		$('#errorInput').css('color', 'red');
	  	}else {
	  			$(this).removeClass(`alert-danger`);
		  		$('#txtFirstName').css('border-color', '');
		  		$("#errorInput").text("")
				btnAcceptChange.disabled = false;
	  	}
  });
input_last_name.keyup(function () {
	  	if(validateName(input_first_name.val()) && validateName(input_last_name.val())  && validateName(input_country.val()) && validateName(input_city.val()) && validateAddress(input_address.val()) && validateNumber(input_phone.val())) {
			btnAcceptChange.disabled = false
	  	}
	  	if(!validateName(input_last_name.val())){
	  		btnAcceptChange.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#txtLastName').css('border-color', 'red');
	  		$("#errorInput").text("You can only use letters for last name!")
	  		$('#errorInput').css('color', 'red');
	  	}else {
	  		$(this).removeClass(`alert-danger`);
	  		$('#txtLastName').css('border-color', '');
	  		$("#errorInput").text("")
	  	}
  });
	input_country.keyup(function () {
	  	if(validateName(input_first_name.val()) && validateName(input_last_name.val())  && validateName(input_country.val()) && validateName(input_city.val()) && validateAddress(input_address.val()) && validateNumber(input_phone.val())) {
			btnAcceptChange.disabled = false
	  	}
	  	if(!validateName(input_country.val())){
	  		btnAcceptChange.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#txtCountry').css('border-color', 'red');
	  		$("#errorInput").text("You can only use letters for country!")
	  		$('#errorInput').css('color', 'red');
	  	}else {
	  		$(this).removeClass(`alert-danger`);
	  		$('#txtCountry').css('border-color', '');
	  		$("#errorInput").text("")
	  	}
  });
	input_city.keyup(function () {
	  	if( validateName(input_first_name.val()) && validateName(input_last_name.val())  && validateName(input_country.val()) && validateName(input_city.val()) && validateAddress(input_address.val()) && validateNumber(input_phone.val())) {
			btnAcceptChange.disabled = false
	  	}
	  	if(!validateName(input_city.val())){
	  		btnAcceptChange.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#txtCity').css('border-color', 'red');
	  		$("#errorInput").text("You can only use letters for city!")
	  		$('#errorInput').css('color', 'red');
	  	}else {
	  		$(this).removeClass(`alert-danger`);
	  		$('#txtCity').css('border-color', '');
	  		$("#errorInput").text("")
	  	}
  });
input_address.keyup(function () {
	  	if( validateName(input_first_name.val()) && validateName(input_last_name.val())  && validateName(input_country.val()) && validateName(input_city.val()) && validateAddress(input_address.val()) && validateNumber(input_phone.val())) {
			btnAcceptChange.disabled = false
	  	}
	  	if(!validateAddress(input_address.val())){
	  		btnAcceptChange.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#txtAddress').css('border-color', 'red');
	  		$("#errorInput").text("You can only use letters and numbers without spaces at the end!")
	  		$('#errorInput').css('color', 'red');
	  	}else {
	  		$(this).removeClass(`alert-danger`);
	  		$('#txtAddress').css('border-color', '');
	  		$("#errorInput").text("")
	  	}
  });
input_phone.keyup(function () {
	  	if( validateName(input_first_name.val()) && validateName(input_last_name.val())  && validateName(input_country.val()) && validateName(input_city.val()) && validateAddress(input_address.val()) && validateNumber(input_phone.val())) {
			btnAcceptChange.disabled = false
	  	}
	  	if(!validateNumber(input_phone.val())){
	  		btnAcceptChange.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#txtPhoneNumber').css('border-color', 'red');
	  		$("#errorInput").text("You can only use numbers for phone!")
	  		$('#errorInput').css('color', 'red');
	  	}else {
	  		$(this).removeClass(`alert-danger`);
	  		$('#txtPhoneNumber').css('border-color', '');
	  		$("#errorInput").text("")
	  	}
  });
	
	 
	 $('#acceptChange').click(function(){
		 	
			let firstName=$('#txtFirstName').val()
			let lastName=$('#txtLastName').val()
			let country=$('#txtCountry').val()
			let city=$('#txtCity').val()
			let address=$('#txtAddress').val()
			let phone=$('#txtPhoneNumber').val()
			let email=user.email

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
		        	location.href = "pharmacist.html";
		        	
				},
			      error: function(){
			       	alert('Error');
			      }
	    });
	 });
			
	 
};
let changePassword = function(){
	
	$("#showData").html(`<table class="ui large table" style="width:50%; margin-left:auto; 
		    margin-right:auto; margin-top: 40px;">
				        <thead>
				            <tr class="success">
				                <th colspan="2" class = "text-info" style= "text-align:center;">Change password</th>
				            </tr>
				        </thead>
				        <tbody>
				            <tr>
				                <td>New password:</td>
				                 <td class="ui input small"> <input type="password" id="txtNewPassword" value=""/></td>
				            </tr>
				            
				            <tr>
				                <td>Repeat new password:</td>
				                 <td class="ui input small" > <input type="password" id="txtNewPasswordRepeat" value=""/></td>
				              
				            </tr>
				          
				        </tbody>
				        <tfoot class="full-width">
		    <tr>
		      <th></th>
		      <th colspan="2">
				   <input id = "acceptChange" class="ui right floated positive basic button" type = "button" value = "Accept changes"></input>
		    
		      </th>
		    </tr>
		    
		  <p id="errorPassword"> </p>
		  </tfoot>
				    </table> `);
	
		btnAcceptChange = document.getElementById("acceptChange")
		btnAcceptChange.disabled = true
	

	  $('#txtNewPassword').keyup(function () {
		  	if(!validatePassword($('#txtNewPassword').val())){
		  		btnAcceptChange.disabled = true
				$(this).addClass(`alert-danger`);
		  		$('#txtNewPassword').css('border-color', 'red');
		  		$("#errorPassword").text("Password must have at least 8 characters, lower case, upper case, digit, special character!")
		  		$('#errorPassword').css('color', 'red');
		  	}else {
		  		$(this).removeClass(`alert-danger`);
		  		$('#txtNewPassword').css('border-color', '');
		  		$("#errorPassword").text("")
		  	}
	  });	
	  $('#txtNewPasswordRepeat').keyup(function () {
		  	if($('#txtNewPassword').val()!=$('#txtNewPasswordRepeat').val()){
		  		btnAcceptChange.disabled = true
				$(this).addClass(`alert-danger`);
		  		$('#txtNewPasswordRepeat').css('border-color', 'red');
		  		$("#errorPassword").text("Passwords must match!")
		  		$('#errorPassword').css('color', 'red');
		  	}else {
			
		  		$(this).removeClass(`alert-danger`);
		  		$('#txtNewPasswordRepeat').css('border-color', '');
		  		$("#errorPassword").text("")
				btnAcceptChange.disabled = false;
		  	}
	  });
	  
	  $('#acceptChange').click(function() {
			var newPassword = $('#txtNewPassword').val()
			var confirmPassword = $('#txtNewPasswordRepeat').val()
			var email = localStorage.getItem('email')
			obj = JSON.stringify({email:email,newPass:newPassword,confirmPass:confirmPassword});
			customAjax({
		        method:'POST',
		        url:'/auth/changePassword',
		        data : obj,
		        contentType: 'application/json',
		        success: function(){
					localStorage.removeItem('email');
		        	alert("Success changed password!")
					location.href = "pharmacist.html";
				},
				error: function(){
					localStorage.removeItem('email');
					alert("User with that email doesn't exist")
				}
		            });

			});
		
	
}


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
function validatePassword(password) {
	  
	  var strongRegex = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})");
	  	if(password.match(strongRegex)) {
	  		return true;
	  	}
	  	else {
	  		return false;
	  	}
}
	
	




