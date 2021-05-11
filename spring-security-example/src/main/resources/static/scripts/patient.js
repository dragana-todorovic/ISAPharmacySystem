$(document).ready(function() {
		$('#edit-profile').attr('hidden', true);
		$('a#logout').click(function(){
		localStorage.removeItem('jwt')		
		location.href = "login.html";
		});
		
	$('a#profile').click(function(){
			var id = localStorage.getItem('email')
			customAjax({
				method:'GET',
		        url:'/api/profilePatient/' + id,
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
      url: '/api/editProfile',
      method: 'POST',
      data:obj,
	  contentType: 'application/json',
	        success: function(){
	        	alert("Sucess.")
	        	
			},
		      error: function(){
		       	alert('Error');
		      }
    });
		
   
    })
		
	});
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
function parseJwt (token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
};