$(document).ready(function() {
		$('#edit-profile').attr('hidden', true);
		$('#show').attr('hidden',true);
		
		$('a#logout').click(function(){
		localStorage.removeItem('jwt')		
		location.href = "login.html";
		});
		

		$('a#changePassword').click(function(){
		$('#show').attr('hidden',false);
		$('#pharmacies_for_derm_appointments').attr('hidden',true);	
		$('#edit-profile').attr('hidden', true);
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
				location.href = "patient.html";
			},
			error: function(){
				localStorage.removeItem('email');
				alert("User with that email doesn't exist")
			}
	            });

		});
	
		});
	$('a#profile').click(function(){
			var id = localStorage.getItem('email')
			customAjax({
				method:'GET',
		        url:'/user/profilePatient/' + id,
		        contentType: 'application/json',
	    		success: function(data) { 	
					customAjax({
					method:'GET',
			        url:'/patient/profileP/' + id,
			        contentType: 'application/json',
		    		success: function(result) { 	
			
					showProfile(data,result)	
			
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
	
			customAjax({
				method:'GET',
		        url:'/patient/getAllMedicineForAllergies/'+id,
		        contentType: 'application/json',
	    		success: function(data) { 	
						showAllergies(data);
			},
	    		error:function(message){
					alert("Error")
	    		}
	    				    	    	    	
	    })
			customAjax({
				method:'GET',
		        url:'/patient/getPatientById/'+id,
		        contentType: 'application/json',
	    		success: function(data) { 	
						showPatientData(data);
			},
	    		error:function(message){
					alert("Error")
	    		}
	    				    	    	    	
	    })
	  $('#id_submit_changes').click(function(){
			var firstName=$('#id_first_name').val()
			var lastName=$('#id_last_name').val()
			var country=$('#id_country').val()
			var city=$('#id_city').val()
			var address=$('#id_address').val()
			var phone=$('#id_phone').val()
			var email=$('#id_email').val()

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
		        	location.href="patient.html";
					$('#edit-profile').attr('hidden', false);
					
				},
			      error: function(){
			       	alert('Error');
			      }
    });
		
   
    })
		 $('#my_allergies').on('click','button',function(event){

		if($(event.target).attr('id')=="remove-allergie"){
			var trid = $(event.target).closest('tr').attr('id');
			var firstName=$('#id_first_name').val()
			var lastName=$('#id_last_name').val()
			var country=$('#id_country').val()
			var city=$('#id_city').val()
			var address=$('#id_address').val()
			var phone=$('#id_phone').val()
			var email=$('#id_email').val()
					obj = JSON.stringify({
					firstname:firstName,
					lastname:lastName,
					country: country,
					city:city,
					address:address,
					phone :phone,
					email:email});
	
				customAjax({
				url:"/patient/removeAllergie/"+trid,
				method : "POST",
				data:obj,
		 		contentType: 'application/json',
				success:function(){
					alert("Successfully removed allergie. ");
				}
			})
		}
	})
		 $('#medicine_for_allergies').on('click','button',function(event){

		if($(event.target).attr('id')=="add-allergie"){
			var trid = $(event.target).closest('tr').attr('id');
			var firstName=$('#id_first_name').val()
			var lastName=$('#id_last_name').val()
			var country=$('#id_country').val()
			var city=$('#id_city').val()
			var address=$('#id_address').val()
			var phone=$('#id_phone').val()
			var email=$('#id_email').val()
					obj = JSON.stringify({
					firstname:firstName,
					lastname:lastName,
					country: country,
					city:city,
					address:address,
					phone :phone,
					email:email});
	
				customAjax({
				url:"/patient/addAllergie/"+trid,
				method : "POST",
				data:obj,
		 		contentType: 'application/json',
				success:function(){
					alert("Successfully added allergie. ");
				}
			})
		}
	})
	});
	});
function showPatientData(data){
		$('#category').text("Category:"+data.category);
		$('#mypoints').text("My points:"+data.points);
		/*	customAjax({
				method:'GET',
		        url:'/patient/getActionsAndBenefitsByCategory/'+category,
		        contentType: 'application/json',
	    		success: function(data) { 	
						console.log(data)
			},
	    		error:function(message){
					alert(message)
	    		}
	    				    	    	    	
	    })*/
		
};
function showAllergies(data){
	let temp='';
	for (i in data){
		temp+=`<tr id="`+data[i].name+`">
			<td><h4 class="ui image header"> <div class="content">
             `+data[i].name+`</div>
      			</h4></td>
     			 <td><button id="add-allergie" class="ui primary basic button">Add Allergie</button>
      			</td></tr>`;
	}
	$('#medicine_for_allergies').html(temp);
	
};
function showProfile(data,result){
	
	$('#pharmacies_for_derm_appointments').attr('hidden',true);	
	$('#edit-profile').attr('hidden', false);
		$('#show').attr('hidden',true);
    			    $('#id_first_name').val(data.firstName);
    		    	$('#id_last_name').val(data.lastName);
    		    	$('#id_country').val(data.country);
    		    	$('#id_city').val(data.city);
					$('#id_address').val(data.address);
					$('#id_phone').val(data.phone);
					$('#id_email').val(data.email);
		let temp='';
			for (i in result){
				temp+=`<tr id="`+result[i]+`">
					<td><h4 class="ui image header"> <div class="content">
		             `+result[i]+`</div>
		      			</h4></td>
		     			 <td><button id="remove-allergie" class="ui negative basic button">Remove Allergie</button>
		      			</td></tr>`;
			}
		$('#my_allergies').html(temp);
};
function parseJwt (token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
};