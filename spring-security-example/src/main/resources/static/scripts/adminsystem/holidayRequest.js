function showDermatologists(data) {
    let temp = '';

    for (i in data){
    		var apoteke = [];

    		for(l in data[i].workingTimes) {
    			apoteke[l] = data[i].workingTimes[l].pharmacy.name + "\n";
    		}

    		var pomocna = 0
    		var averageRating = 0
    		for(m in data[i].ratings) {
    			pomocna = pomocna + data[i].ratings[m].rating;
    		}
    		averageRating = pomocna/(data[i].ratings.length)

    		temp+=`<tr><td class="firstname">`+
    		data[i].user.firstName+`</td><td class="soba">`+data[i].user.lastName+`</td><td class="ocjena">`+ ((!Object.is(NaN, averageRating)) ? averageRating:`-`) +`</td>
    		<td class="ap">${apoteke}</td>
             <td>
    			<button id = "`+data[i].id+`" name="prikaziZahjeve" class="ui floated positive basic button">
    		    Show vacation requests
    		</button>
    		</td></tr>`;
    	}

    $('#tabelaDermatologa').html(temp);
}

let showRequestsD = function(idSelected,data) {
	let temp = '';
	for(i in data) {
		temp += `<tr>
		<td>`+data[i].startDate+`</td>
		<td>`+data[i].endDate+`</td>
		<td>`+data[i].status+`</td>`;
		if(data[i].status == "ON_HOLD"){
                temp+=`<td>
               <td ><input name="acceptButton" id="`+ data[i].id +`"  type = 'button' class="ui floated positive basic button" value="Accept" ></input >
                <input name="declineButton" id="`+ data[i].id +`"  type = 'button' class="ui floated negative basic button" value="Decline" ></input >

		        </td>`
		} else {
			temp+=`<td></td>`
		}
		temp+=`</tr>`;
	}
	$('#zahtjeviTabela').html(temp)

}
var idSelected = '';
var id = '';
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
              url: '/derm/getAllDermatologist' ,
              method: 'GET',
              contentType: 'application/json',
              success: function(data){
                  console.log(data)
                  showDermatologists(data);
              },
              error: function(){
              }
     });

      var modalMedicines = document.getElementById('modal-allRequest')
     var spanMedicine = document.getElementsByClassName("closeAllRequest")[0];
       spanMedicine.onclick = function () {
             modalMedicines.style.display = "none";
         }

     window.onclick = function (event) {
         if (event.target == modalMedicines) {
             modalMedicines.style.display = "none";
         }
     }

     //modal-declineRequest
      var modalDecline = document.getElementById('modal-declineRequest')
      var spanDecline = document.getElementsByClassName("closeDeclineRequest")[0];
        spanDecline.onclick = function () {
              modalDecline.style.display = "none";
          }

      window.onclick = function (event) {
          if (event.target == modalDecline) {
              modalDecline.style.display = "none";
          }
      }
        $('#tabelaDermatologa').on('click', 'button[name=prikaziZahjeve]', function (event) {
     	idSelected = this.id
        modalMedicines.style.display = "block";
        //crtanje tabele zahteva
              customAjax({
                   	      url: '/pharmacy/getHolidayRequestsForDerm/' + idSelected,
                   	      method: 'GET',
                   		  contentType: 'application/json',
                   		        success: function(data){
                   					 showRequestsD(idSelected,data)
                   				},
                   			      error: function(){
                   			      }
                   	    });
      });

// accepting request
    $('#zahtjeviTabela').on('click', 'input:button[name=acceptButton]', function (event) {
        	var id = this.id;
        // holidayRequest id i dermatologist Id
        customAjax({
              url: '/pharmacy/acceptHolidayRequest/' + id + '/' + idSelected,
              method: 'POST',
              contentType: 'application/json',
              success: function(data){
                  alert("Success accept!" + data)
                  location.href = "holidayRequest.html"

              },
              error: function(){
                  alert("You cant accept request because dermatologist have reserved appointment in that period")
              }
            });
      })

// declining request and opening modal to get information wy
    $('#zahtjeviTabela').on('click', 'input:button[name=declineButton]', function (event) {
        	id = this.id;
        // holidayRequest id i dermatologist Id
          modalMedicines.style.display = "none";
          modalDecline.style.display = "block";
      })

    	$('#decline').click(function() {
            var reason = $('#reason').val()
            customAjax({
                  url: '/pharmacy/declineHolidayRequest/' + id + '/' + idSelected + '/' + reason,
                  method: 'POST',
                  contentType: 'application/json',
                  success: function(){
                      alert("Success declined and sent email to dermatologist!")
                      location.href = "holidayRequest.html"

                  },
                  error: function(){

                      alert("Error")
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