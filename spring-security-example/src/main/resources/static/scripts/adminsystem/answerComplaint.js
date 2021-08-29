function drawComplaintTable(data) {
    let table = '';
    for (i in data) {
        let disabled;
        if(data[i].answered){
            disabled = true
        }

        table += `<tr >
			<td>`+ data[i].userName + `</td>
			<td>`+ data[i].type + `</td>
			<td>`+ data[i].complainedOnName + `</td>
			<td>`+ data[i].content + `</td>
            <td ><input name="medicinesButton" id="`+ data[i].userName + `"  type = 'button' style = "background-color:coral" class="btn btn-primary" value="Answer" ></input ></td >
			</tr>`;
    }
    $('#complaintTable').html(table);
}
var userEmail;
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
   $('#submitSendAnswer').click(function(){
            let answer=$('#txtAnswer').val()
            let emailToSend = userEmail;
            let emailFromSending = email;
//if success modal.style.display = "none";
//$('#txtAnswer').value() = ''

         /*   customAjax({
                  url: '/patient/saveLoyaltyProgram',
                  method: 'POST',
                  data:obj,
                  contentType: 'application/json',
                    success: function(){
                        alert("Sucess defined loyalty Program.")
                        location.href = "loyaltyScale.html";
                   },
                  error: function(){
                    alert('Error by defining loyalty prorgam.');
                  }
            });*/
        });
    customAjax({
      url: '/complaint/getAllComplaint',
      method: 'GET',
        contentType: 'application/json',
        success: function (data) {
            drawComplaintTable(data);
        },
        error: function (message) {
            //alert("Failed")
        }
    })
    var modalMedicines = document.getElementById('modal-medicines')
    var spanMedicine = document.getElementsByClassName("closeMedicine")[0];
      spanMedicine.onclick = function () {
            modalMedicines.style.display = "none";
        }

    window.onclick = function (event) {
        if (event.target == modalMedicines) {
            modalMedicines.style.display = "none";
        }
    }
      $('#complaintTable').on('click', 'input:button[name=medicinesButton]', function (event) {
            modalMedicines.style.display = "block";
            userEmail = this.id;
      })

	$('#logout').click(function(){
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
			  </tfoot>
					    </table> <p id="er"> </p>`);

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
		        	location.href = "adminsystem.html";

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
					location.href = "adminsystem.html";
				},
				error: function(){
					localStorage.removeItem('email');
					alert("User with that email doesn't exist")
				}
		            });

			});


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