function drawPharmacyTable(data) {
    let table = '';
    for (i in data) {
        table += `<tr id="myform">
			<td >
                    <input type="radio" name = "medicinesButton" id="` + data[i].id + `" value="` + data[i].id + `">
			</td>
			<td>`+ data[i].name + `</td>
			</tr>`;
    }
    $('#pharmacyTable').html(table);

}
var pharmacyIdd ='';
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

     customAjax({
          url: '/pharmacy/getAll',
          method: 'GET',
          contentType: 'application/json',
            success: function(data){
                drawPharmacyTable(data)
           },
          error: function(){
          }
        });


    $('input[name="medicinesButton"]:checked').each(function() {
        pharmacyIdd = this.value;
     });

     $('#pharmacyTable').on('click',function(event){
     btn =document.getElementById("submitRegisterPharmacyAdmin")
        btn.disabled = false
         $(this).removeClass(`alert-danger`);
        $('#pharmacyTable').css('border-color', '');
        $("#errorAdmin").text("")
     })

      $('#submitRegisterPharmacyAdmin').click(function(){
            let password=$('#txtPassword').val()
             let firstName=$('#txtFirstName').val()
             let lastName=$('#txtLastName').val()
             let email1=$('#txtEmail').val()
             let country=$('#txtCountry').val()
             let city=$('#txtCity').val()
             let address=$('#txtAddress').val()
             let phoneNumber=$('#txtNumber').val()
             let pharmacyId =  $("#myform input[type='radio']:checked").val();
         obj = JSON.stringify({
             password:password,
             firstName : firstName,
             lastName :lastName,
             email :email1,
             country : country,
             city :city,
             address : address,
             phoneNumber:phoneNumber,
             pharmacyId : pharmacyId
         });


         customAjax({
               url: '/register/pharmacyAdmin',
               method: 'POST',
               data:obj,
               contentType: 'application/json',
                 success: function(){
                     alert("Sucess register new pharmacy admin.")
                     location.href = "adminsystem.html";
                },
               error: function(){
                      alert("Pharmacy Admin with that email already exists.")
                      location.href = "registerPharmacyAdmin.html";
               }
         });
     });
    $('#submitRegisterDermatologist').click(function(){
     let password=$('#txtPassword').val()
            let firstName=$('#txtFirstName').val()
            let lastName=$('#txtLastName').val()
            let email1=$('#txtEmail').val()
            let country=$('#txtCountry').val()
            let city=$('#txtCity').val()
            let address=$('#txtAddress').val()
            let phoneNumber=$('#txtNumber').val()
        obj = JSON.stringify({
            password:password,
            firstName : firstName,
            lastName :lastName,
            email :email1,
            country : country,
            city :city,
            address : address,
            phoneNumber:phoneNumber
        });

        customAjax({
              url: '/register/dermatologist',
              method: 'POST',
              data:obj,
              contentType: 'application/json',
                success: function(){
                    alert("Sucess register new dermatologist.")
                    location.href = "adminsystem.html";
               },
              error: function(){
                     alert("Dermatologist with that email already exists.")
                     location.href = "registerDermatologist.html";
              }
        });
    });


    $('#submitRegisterPharmacy').click(function(){
        let name=$('#txtName').val()
        let street=$('#txtStreet').val()
        let city = $('#txtCity').val()
        let description=$('#txtDescription').val()

        obj = JSON.stringify({
            name:name,
            street:street,
            city:city,
            description: description,
        });
        console.log(obj)
        customAjax({
              url: '/register/pharmacy',
              method: 'POST',
              data:obj,
              contentType: 'application/json',
                success: function(){
                    alert("Sucess register new Pharmacy.")
                    location.href = "adminsystem.html";
               },
              error: function(){
                     alert("Error by registering New Pharmacy.")
                     location.href = "registerPharmacy.html";
              }
        });
    });
    $('#submitRegisterSystemAdmin').click(function(){
        let password=$('#txtPassword').val()
        let firstName=$('#txtFirstName').val()
        let lastName=$('#txtLastName').val()
        let email1=$('#txtEmail').val()
        let country=$('#txtCountry').val()
        let city=$('#txtCity').val()
        let address=$('#txtAddress').val()
        let phoneNumber=$('#txtNumber').val()
        obj = JSON.stringify({
            password:password,
            firstName : firstName,
            lastName :lastName,
            email :email1,
            country : country,
            city :city,
            address : address,
            phoneNumber:phoneNumber
        });
        console.log(obj)
        customAjax({
              url: '/register/systemAdmin',
              method: 'POST',
              data:obj,
              contentType: 'application/json',
                success: function(){
                    alert("Sucess register new System Admin.")
                    location.href = "adminsystem.html";
               },
              error: function(){
                     alert("System Admin with that email already exists.")
                     location.href = "registerAdminSystem.html";
              }
        });
    });
    $('#submitRegisterSupplier').click(function(){
     let password=$('#txtPassword').val()
            let firstName=$('#txtFirstName').val()
            let lastName=$('#txtLastName').val()
            let email1=$('#txtEmail').val()
            let country=$('#txtCountry').val()
            let city=$('#txtCity').val()
            let address=$('#txtAddress').val()
            let phoneNumber=$('#txtNumber').val()
        obj = JSON.stringify({
            password:password,
            firstName : firstName,
            lastName :lastName,
            email :email1,
            country : country,
            city :city,
            address : address,
            phoneNumber:phoneNumber
        });
        console.log(obj)
        customAjax({
              url: '/register/supplier',
              method: 'POST',
              data:obj,
              contentType: 'application/json',
                success: function(){
                    alert("Sucess register new supplier.")
                    location.href = "adminsystem.html";
               },
              error: function(){
                     alert("Supplier with that email already exists.")
                     location.href = "registerSupplier.html";
              }
        });
    });
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