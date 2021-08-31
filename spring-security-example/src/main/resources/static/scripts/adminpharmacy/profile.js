let jsonObjekat;
var pomocnaP;
$(document).ready(function(e){
	
	

	/*function getLocation() {
	    if (navigator.geolocation) {
	        navigator.geolocation.getCurrentPosition(showPosition);
	    } else {
	        x.innerHTML = "Geolocation is not supported by this browser.";
	    }
	}

	function showPosition(position) {
	    console.log(position);
	}*/
	
	function reverseGeocode(coords) {
        fetch('https://nominatim.openstreetmap.org/reverse?format=json&lon=' + coords[0] + '&lat=' + coords[1])
            .then(function (response) {
                //alert(response);
                return response.json();
            }).then(function (json) {
            	let street = json["address"]["road"] + ` ` + json["address"]["house_number"];
            	let city = json["address"]["city"];
            //let location = json["address"]["road"] + ` ` + json["address"]["house_number"] + ` , ` + json["address"]["city"] + ` , ` + json["address"]["country"];
            $('#txtStreet').val(street)
            $('#txtCity').val(city)


            // $('#street-number').val(json["address"]["house_number"])
            //$('#city').val(json["address"]["city"])
            //$('#zip-code').val(json["address"]["postcode"])


            //$('#location-longitude').val(json["lon"]);
            //$('#location-latitude').val(json["lat"]);


            jsonObjekat = json;
        });
    };

    pomocnaP = function () {
        var map = new ol.Map({

            target: 'map',
            layers: [
                new ol.layer.Tile({
                    source: new ol.source.OSM()
                })
            ],
            view: new ol.View({
                center: ol.proj.fromLonLat([19.8424, 45.2541]),
                zoom: 15
            })
        });
        //var jsonObjekat;
        map.on('singleclick', function (evt) {
            var coord = ol.proj.toLonLat(evt.coordinate);
            console.log(coord)
            reverseGeocode(coord);
            var iconFeatures = [];
            var lon = coord[0];
            var lat = coord[1];
           var icon = "https://img.icons8.com/ios-glyphs/30/000000/marker--v1.png";
            var iconGeometry = new ol.geom.Point(ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:3857'));
            var iconFeature = new ol.Feature({
                geometry: iconGeometry
            });
            iconFeatures.push(iconFeature);

            var vectorSource = new ol.source.Vector({
                features: iconFeatures //add an array of features
            });


            var iconStyle = new ol.style.Style({
                image: new ol.style.Icon(/** @type {olx.style.IconOptions} */({
                    anchor: [0.5, 46],
                    anchorXUnits: 'fraction',
                    anchorYUnits: 'pixels',
                    opacity: 0.95,
                    src: icon
                }))
            });

            var vectorLayer = new ol.layer.Vector({
                source: vectorSource,
                style: iconStyle
            });

            map.addLayer(vectorLayer);
            
        });
    }

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
		localStorage.removeItem('jwt')
		location.href = "login.html";
		});

	$("#pharmacyProfile").click(function () {
		  customAjax({
		      url: '/user/getByEmail/' + email,
		      method: 'GET',
		      success: function(user){

		    	  console.log(JSON.stringify(user))
		    	  customAjax({
				      url: '/pharmacy/getPharmacyByAdmin',
				      method: 'POST',
				      data:JSON.stringify(user),
				      contentType: 'application/json',
				      success: function(pharmacy){
				    	  editPharmacy(pharmacy);
				      },
				      error: function(){
				      }

			 });
		      },
		      error: function(){
		      }

	 });
	  });

  });
let showProfile = function(user) {

	 $("#showData").html(`<table class="ui large basic table" style="width:50%; margin-left:auto;
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
		   <input id = "changePassword" class="ui right floated teal button" type = "button" value = "Change password"></input>

          <input id = "changeData" class="ui right floated teal button" type = "button" value = "Edit profile"></input>


      </th>
    </tr>
  </tfoot>
		    </table> <p id="er"> </p>`);

	 $("#changeData").click(function () {
		 editProfile(user)
	 });

	 $("#changePassword").click(function () {
		 changePasswordC(user)
	 });


};


let editProfile = function(user) {

	 $("#showData").html(`<table class="ui large basic table" style="width:50%; margin-left:auto;
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
					   <input id = "acceptChange" class="ui right floated positive button" type = "button" value = "Accept changes"></input>

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
let changePasswordC = function(user){

	$("#showData").html(`<table class="ui large basic table" style="width:50%; margin-left:auto;
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
				   <input id = "acceptChange" class="ui right floated positive button" type = "button" value = "Accept changes"></input>

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
			var email = user.email
			obj = JSON.stringify({email:email,newPass:newPassword,confirmPass:confirmPassword});
			customAjax({
		        method:'POST',
		        url:'/auth/changePassword',
		        data : obj,
		        contentType: 'application/json',
		        success: function(){
		        	localStorage.removeItem('email');
					localStorage.removeItem('jwt');
		        	alert("Success changed password! Please login again")
		        	location.href = "login.html";
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


let showPharmacyBasicInfo = function(pharmacy){

	$("#showData").html(`<table class="ui large table" style="width:50%; margin-left:auto;
		    margin-right:auto; margin-top: 40px;">
				        <thead>
				            <tr class="success">
				                <th colspan="3" class = "text-info" style= "text-align:center;">Pharmacy basic information</th>
				            </tr>
				            <tr>
				            	<th>Name</th>
				            	<th>Address</th>
				            	<th>Description</th>
				            </tr>

				        </thead>
				        <tbody>

				            <tr>
				                <td>` + ((pharmacy.name != null) ? pharmacy.name:`-`) + `</td>
				               <td>` + ((pharmacy.address != null) ? pharmacy.address:`-`) + `</td>
				              <td>` + ((pharmacy.description != null) ? pharmacy.description:`-`) + `</td>

				            </tr>


				        </tbody>
				        <tfoot class="full-width">
		    <tr>
		      <th></th>
		      <th colspan="2">
				   <input id = "changeData" class="ui right floated teal basic button" type = "button" value = "Change data"></input>

		      </th>
		    </tr>

		  <p id="errorPassword"> </p>
		  </tfoot>
				    </table> `);
	 $("#changeData").click(function () {
		 editPharmacy(pharmacy)
	 });


}

let editPharmacy = function (pharmacy) {
	let averageRating = 0;
	let pomocna =0
	for(i in pharmacy.ratings) {
		pomocna = pomocna + pharmacy.ratings[i].rating;
	}
	averageRating = pomocna/(pharmacy.ratings.length)
	console.log(pharmacy)
	 $("#showData").html(`<table class="ui basic large table" style="width:50%; margin-left:auto;
			    margin-right:auto; margin-top: 40px;">
					        <thead>
					            <tr class="success">
					                <th colspan="3" class = "text-info" style= "text-align:center;">Edit pharmacy</th>
					            </tr>
					        </thead>
					        <tbody>
					            <tr>
					                <td>Name:</td>
					                <td class="ui input"> <input type="text" id="txtName" value="`+ ((pharmacy.name != null) ? pharmacy.name:`` ) + `"/></td>

					            </tr>
					            <tr>
					                <td>Address:</td>
					                <td class="ui fluid icon input"> <input type="text" disabled="true" id="txtStreet" value="`+ ((pharmacy.address != null) ? pharmacy.address.street:`` ) + `"/></td>
					                <td class="ui fluid icon input"> <input type="text" disabled="true" id="txtCity" value="`+ ((pharmacy.address != null) ? pharmacy.address.city:`` ) + `"/></td>
					                <td><div id="map" class="map"  style="width:350px;"></div>
                                            <script>pomocnaP();</script></td>
					            </tr>
					            <tr>
					                <td>Description:</td>
					                <td class="ui input"> <input type="text" id="txtDescription" value="`+ ((pharmacy.description != null) ? pharmacy.description:`` ) + `"/></td>

					            </tr>
					             <tr>
					                <td>Average rating:</td>
					                <td class="ui input"> <input type="text" disabled="true" id="txtRating" value="`+ ((!Object.is(NaN, averageRating)) ? averageRating:`-`) +`"/></td>

					            </tr>

					        </tbody>

					        <tfoot class="full-width">
			    <tr>
			      <th></th>
			      <th colspan="2">
					   <input id = "acceptChange" class="ui right floated positive button" type = "button" value = "Accept changes"></input>

			      </th>
			    </tr>
			  </tfoot>
					    </table> <p id="er"> </p>`);

	 $('#acceptChange').click(function(){

			let name=$('#txtName').val()
			let street=$('#txtStreet').val()
			let city = $('#txtCity').val()
			let description=$('#txtDescription').val()

			obj = JSON.stringify({
			id:pharmacy.id,
			name:name,
			street:street,
			city:city,
			description: description,
			});
			
			console.log(obj)
			
			    customAjax({
	      url: '/pharmacy/editPharmacy',
	      method: 'POST',
	      data:obj,
		  contentType: 'application/json',
		        success: function(){
		        	alert("Sucess change data.")
		        	location.href = "adminpharmacy.html";
		        	
				},
			      error: function(){
			       	alert('Error');
			      }
	    });
	 });
	
	
}



