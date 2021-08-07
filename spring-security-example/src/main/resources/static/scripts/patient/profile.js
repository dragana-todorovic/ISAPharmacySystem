$(document).ready(function() {
		$('#edit-profile').attr('hidden', true);
		$('#show').attr('hidden',true);
		
		$('a#logout').click(function(){
		localStorage.removeItem('jwt')		
		location.href = "login.html";
		});
		

		$('a#changePassword').click(function(){
		$('#show').attr('hidden',false);
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
		        url:'/patient/getAllMedicine/',
		        contentType: 'application/json',
	    		success: function(data) { 	
			for(var i = 0; i < data.length; i++) {
   				$('#dropdown select').append('<option value='+i+'>'+data[i].name+'</option>');
			}
			},
	    		error:function(message){
					alert("Error")
	    		}
	    				    	    	    	
	    })
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
	        	location.href="patient.html";
				$('#edit-profile').attr('hidden', false);
				
			},
		      error: function(){
		       	alert('Error');
		      }
    });
		
   
    })
		
	});
	});
function showProfile(data,result){
	$('#edit-profile').attr('hidden', false);
		$('#show').attr('hidden',true);
    			    $('#id_first_name').val(data.firstName);
    		    	$('#id_last_name').val(data.lastName);
    		    	$('#id_country').val(data.country);
    		    	$('#id_city').val(data.city);
					$('#id_address').val(data.address);
					$('#id_phone').val(data.phone);
					$('#id_email').val(data.email);
	    var table = $('<table></table>').addClass('foo');
        for (var i = 0; i < result.length; i++) {
                row = $('<tr></tr>');
                    var rowData = $('<td></td>').addClass('bar').text(result[i]);
                    row.append(rowData);
               	    table.append(row);
            }

        if ($('table').length) {
             $("#id_alergies tr:first").after(row);
        }
        else {
            $('#id_alergies').append(table);
        }
};
function parseJwt (token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
};