$(document).ready(function(){

	 $('#btnActivate').on('click', function(e){
		 customAjax({
		      url: '/auth/activateAccount',
		      method: 'POST',
		      data:localStorage.getItem('obj'),
			  contentType: 'application/json',
			        success: function(){
			        		localStorage.removeItem('obj')
			        		alert("Success registration! Now you can log in!")
                            location.href = "login.html";
					},
				      error: function(){
				    	  if(localStorage.getItem('obj') == null) {
				    		  $('#id_p_log').text('Validation link expired. Try again!')
				        	} else {
				        		alert('User with that email already exists')
				        	}
				      }
		    });
	    });

});