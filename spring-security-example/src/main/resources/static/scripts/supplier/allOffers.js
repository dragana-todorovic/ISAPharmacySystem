function drawAllOfferTable(data) {
    let table = '';
    for (i in data) {
    //data[i].medicineOrder..status == PROCESSED
        var shouldDisable = false;
        if(data[i].medicineOrder.status == 'PROCESSED'){
            shouldDisable = true;
        }else  if(data[i].medicineOrder.status == 'ON_HOLD'){
            shouldDisable = false;
        }

        var medicinesName = "";
        var medicinesQuantities = "";
		for(x in data[i].medicineOrder.medicines){
            medicinesName += "<b>" +data[i].medicineOrder.medicines[x].medicine.name +"</b></br>"
            medicinesQuantities += data[i].medicineOrder.medicines[x].quantity +"</br>"
		}
	     var limit = data[i].deleveryTime.split('T');
	      var deadLineLimit = data[i].medicineOrder.timeLimit.split('T');
        table += `<tr>
            <td>`+ limit[0] + " " + limit[1] + `</td>
            <td>`+ data[i].price + `</td>
            <td>`+ data[i].status + `</td>
            <td>`+ deadLineLimit[0] + " " + deadLineLimit[1] +`</td>
            <td>`+data[i].medicineOrder.status + `</td>
			<td>`+ medicinesName+ `</td>
			<td>`+ medicinesQuantities + `</td>`;
			shouldDisable ?
            table +=
            `<td >
                <input disabled name="medicinesButton" id="`+ data[i].id + `"
                 class="ui right floated primary basic button" type = "button"
                 style = "background-color:coral"  value="Edit Offer" ></input >
                 </td >
			</tr>` :
		     table +=
                `<td >
                    <input  name="medicinesButton" id="`+ data[i].id + `"
                     class="ui right floated primary basic button" type = "button"
                     style = "background-color:coral"  value="Edit Offer" ></input >
                     </td >
                </tr>`
			;
    }
    $('#offerTable').html(table);

}
function drawOfferTable(data, selectedStatus) {
    let table = '';
    for (i in data) {
        var shouldDisable = false;
                if(data[i].medicineOrder.status == 'PROCESSED'){
                    shouldDisable = true;
                }else  if(data[i].medicineOrder.status == 'ON_HOLD'){
                    shouldDisable = false;
                }

        var medicinesName = "";
        var medicinesQuantities = "";
		for(x in data[i].medicineOrder.medicines){
            medicinesName += "<b>" +data[i].medicineOrder.medicines[x].medicine.name +"</b></br>"
            medicinesQuantities += data[i].medicineOrder.medicines[x].quantity +"</br>"
		}

	     var limit = data[i].deleveryTime.split('T');
	     var deadLineLimit = data[i].medicineOrder.timeLimit.split('T');
	     if(data[i].status == selectedStatus)
        {
            table += `<tr>
            <td>`+ limit[0] + " " + limit[1] + `</td>
            <td>`+ data[i].price + `</td>
            <td>`+ data[i].status + `</td>
            <td>`+ deadLineLimit[0] + " " + deadLineLimit[1]  + `</td>
            <td>`+data[i].medicineOrder.status + `</td>
			<td>`+ medicinesName+ `</td>
			<td>`+ medicinesQuantities + `</td>`;
            shouldDisable ?
            table +=
            `<td >
                <input disabled name="medicinesButton" id="`+ data[i].id + `"
                 class="ui right floated primary basic button" type = "button"
                 style = "background-color:coral"  value="Edit Offer" ></input >
                 </td >
            </tr>` :
             table +=
                `<td >
                    <input  name="medicinesButton" id="`+ data[i].id + `"
                     class="ui right floated primary basic button" type = "button"
                     style = "background-color:coral"  value="Edit Offer" ></input >
                     </td >
                </tr>`
            ;
		}
    }
    $('#offerTable').html(table);

}
var suplierOfferId = '';
$(document).ready(function(e){
    $('input:radio[name="postage"]').change(
        function(){
        if ($(this).is(':checked') && $(this).val() == 'ON_HOLD') {
             customAjax({
                    url: '/suplierOffer/getAllOrdersBySuplier/' + email,
                    method: 'GET',
                    contentType: 'application/json',
                    success: function(data){
                        drawOfferTable(data, 'ON_HOLD');
                    },
                    error: function (message) {
                        //alert("Failed")
                    }
                })
        }else if ($(this).is(':checked') && $(this).val() == 'ACCEPTED') {
              customAjax({
                                 url: '/suplierOffer/getAllOrdersBySuplier/' + email,
                                 method: 'GET',
                                 contentType: 'application/json',
                                 success: function(data){
                                     drawOfferTable(data, 'ACCEPTED');
                                 },
                                 error: function (message) {
                                     //alert("Failed")
                                 }
                             })
        }else if ($(this).is(':checked') && $(this).val() == 'REJECTED') {
               customAjax({
                    url: '/suplierOffer/getAllOrdersBySuplier/' + email,
                    method: 'GET',
                    contentType: 'application/json',
                    success: function(data){
                        drawOfferTable(data, 'REJECTED');
                    },
                    error: function (message) {
                        //alert("Failed")
                    }
                })
        }else{
        customAjax({
                            url: '/suplierOffer/getAllOrdersBySuplier/' + email,
                            method: 'GET',
                            contentType: 'application/json',
                            success: function(data){
                                drawAllOfferTable(data);
                            },
                            error: function (message) {
                                //alert("Failed")
                            }
                        })
        }
    });

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
        url: '/suplierOffer/getAllOrdersBySuplier/' + email,
        method: 'GET',
        contentType: 'application/json',
        success: function(data){
            drawAllOfferTable(data);
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
      $('#offerTable').on('click', 'input:button[name=medicinesButton]', function (event) {
      //DOVAVLJAM I SETUJEM CENU VREME I DATUM
       suplierOfferId = this.id;
       var supplierId = this.id;
                customAjax({
                          url: '/suplierOffer/getSupplierOfferById/' + supplierId,
                          method: 'GET',
                          contentType: 'application/json',
                            success: function(data){
                               $('#txtPrice').val(data.price)
                                 var limit = data.deleveryTime.split('T');
                              formatDate($('#validFrom').val(limit[0]));
                                $('#timeOfOrder').val(limit[1])
                           },
                          error: function(){
                          }
                    });
           modalMedicines.style.display = "block";

      })
        var today = new Date();
	 $('#rangestart').calendar({
		  type: 'date',
		  minDate: new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1),
		});
       $('#submitEditOffer').click(function(){
          let price=$('#txtPrice').val()
          let deliveryDate =formatDate($('#validFrom').val());
          let time = $('#timeOfOrder').val()

          obj = JSON.stringify({
              medicineOrderId:suplierOfferId,
              price:price,
              deliveryDate:deliveryDate,
              supplierEmail:email,
              time:time
          });

             customAjax({
                    url: '/suplierOffer/editOffer',
                    method: 'POST',
                    data:obj,
                    contentType: 'application/json',
                      success: function(){
                         $('#txtPrice').val('')
                        formatDate($('#validFrom').val(''));
                         modalMedicines.style.display = "none";
                          alert("Success edit offer.")
		                location.href = "allOffers.html";

                     },
                    error: function(){
                     //   $('#txtPrice').val('')
                        formatDate($('#validFrom').val(''));
                         //modalMedicines.style.display = "none";
                        alert('Delivery Date must be before Order Deadline.');
                    }
              });
          });



	$('#logout').click(function(){
		localStorage.removeItem('jwt')
		location.href = "login.html";
	});

});
function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [year, month, day].join('-');
}
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
		        	location.href = "adminpharmacy.html";

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
					location.href = "adminpharmacy.html";
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