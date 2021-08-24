$(document).ready(function() {
	var trid;
	var email = localStorage.getItem('email')
	$('a#all_pharmacies').click(function(){
				customAjax({
		        method:'GET',
		        url:'/pharmacy/getAll',
		        contentType: 'application/json',
		        success: function(data){
					showPharmacies(data)
				},
				error: function(){
					console.log("error");
				}
		     });

	});
	
	$('a#my_app_derm').click(function(){
		console.log(email);
			customAjax({
	        method:'GET',
	        url:'/appointment/getAllDermAppointmentsByPatient/'+email,
	        contentType: 'application/json',
	        success: function(data){
				showMyAppointmentsAtDermatologists(data)
			},
			error: function(){
				console.log("error");
			}
	     });

	});

	$('a#all_subscribed').click(function(){
        customAjax({
            method:'GET',
            url:'/patient/getUserAllSubscribedPharmacies/' + email,
            contentType: 'application/json',
            success: function(data){
                showSubscribedPharmacies(data)
            },
            error: function(){
                console.log("error");
            }
         });

    });

	$('#submitSubscribe').click(function(){
        obj = JSON.stringify({
            patientEmail:email,
            pharmacyId:trid
        });

        console.log(obj)
        customAjax({
            method:'POST',
            url:'/patient/subscribePatientOnActionsAndBenefits' ,
            data:obj,
            contentType: 'application/json',
            success: function() {
                //poziv da se useru doda apoteka na listu subscribovanih apoteka
                // treba provera da li je vec subscraboan

                alert('Success subscribed on pharmacy')
            },
            error:function(message){
                alert("You have already been subscribed to pharmacy")

            }
        });
	})
	 $('#pharmacies_tableBody').on('click','button',function(event){
			trid = $(event.target).closest('tr').attr('id');
            if(this.id == 'details'){
                customAjax({
                    method:'GET',
                    url:'/pharmacy/getPharmacyById/' + trid,
                    contentType: 'application/json',
                    success: function(data) {
                        showPharmacy(data)
                    },
                    error:function(message){
                        alert("Error")
                        console.log(message)
                    }
                });
            }else if( this.id == 'subscribed'){
                obj = JSON.stringify({
                    patientEmail:email,
                    pharmacyId:trid
                });

                customAjax({
                    method:'POST',
                    url:'/patient/unsubscribePatientOnActionsAndBenefits' ,
                    data:obj,
                    contentType: 'application/json',
                    success: function() {
                     customAjax({
                            method:'GET',
                            url:'/patient/getUserAllSubscribedPharmacies/' + email,
                            contentType: 'application/json',
                            success: function(data){
                                showSubscribedPharmacies(data)
                            },
                            error: function(){
                                console.log("error");
                            }
                         });
                       alert("Successfully unsubscribed from Pharmacy Actions&Benefits");
                    },
                    error:function(message){
                        alert("You are still subscribed to Pharmacy Actions&Benefits");
                    }
                });
            }

	})
	$('#appointments_derm').on('click',function(){
				customAjax({
				method:'GET',
		        url:'/appointment/getAvailableAppointmentsByPharmacyId/' + trid,
		        contentType: 'application/json',
	    		success: function(data) { 	
					console.log(data);
					showAvailableAppointments(data);
	    		},
	    		error:function(message){
					alert("Error")
	    			console.log(message)
	    		}
	    	});	
	})
	 $('#appointments_dermatology_body').on('click','button',function(event){
		pom = $(event.target).closest('tr').attr('id');
		            customAjax({
                    method:'POST',
                    url:'/appointment/scheduleDermatologistAppointment/'+pom+'/'+email ,
                    contentType: 'application/json',
                    success: function() {
                       alert("Successfully scheduled appointment!");
                    },
                    error:function(){
                        alert("Could not schedule appointment,try later");
                    }
                });
		
	})
	
	 $('#my_appointments_dermatology_body').on('click','button',function(event){
		pom = $(event.target).closest('tr').attr('id');
        customAjax({
            method:'POST',
            url:'/appointment/cancelDermatologistAppointment/'+pom ,
            contentType: 'application/json',
            success: function() {
               alert("Successfully canceled appointment!");
            },
            error:function(){
                alert("Could not cancel appointment,try later");
            }
        });
		
	})

function showAvailableAppointments(data){
	$('#pharmacy-details').attr('hidden',true);
	let temp='';
	for (i in data){
		temp+=`<tr id="`+data[i].id+`">
			<td >`+data[i].dermatologistFirstName+` `+data[i].dermatologistLastName+`</td>
			<td>`+data[i].grade+`</td>
			<td>`+data[i].date+` `+data[i].time+`</td>
			<td>`+data[i].duration+` min </td>
			<td>`+data[i].price+` din </td>
			<td><button id="schedule_appointment" class="ui primary basic button">Schedule appointment</button>
			</tr>`;
	}
	
	$('#appointments_dermatology_body').html(temp);
	$('#derm_appointments').attr('hidden',false);
	$('#pharmacy-details').attr('hidden',true);
	$('#pharmacies_for_derm_appointments').attr('hidden',true);
	$('#edit-profile').attr('hidden', true);
	$('#show').attr('hidden',true);
	$('#my_derm_appointments').attr('hidden',true);
	$('#ph_av_con').attr('hidden',true)
	$('#ph_con').attr('hidden',true)
}
function showSubscribedPharmacies(data){
	$('#pharmacy-details').attr('hidden',true);
	let temp='';
	for (i in data){
		temp+=`<tr id="`+data[i].id+`">
			<td >`+data[i].name+`</td>
			<td>`+data[i].address.city+`</td>
			<td>`+data[i].address.street+`</td>
			<td>`+data[i].description+`</td>
			<td><button id="subscribed" class="ui primary basic button">Unsubscribed</button>
			</tr>`;
	}
	$('#pharmacies_tableBody').html(temp);
	$('#pharmacies_for_derm_appointments').attr('hidden',false);
	$('#edit-profile').attr('hidden', true);
	$('#show').attr('hidden',true);
	$('#my_derm_appointments').attr('hidden',true);
	$('#ph_av_con').attr('hidden',true)
	$('#ph_con').attr('hidden',true)
}
function showPharmacies(data){
	$('#pharmacy-details').attr('hidden',true);
	let temp='';
	for (i in data){
		temp+=`<tr id="`+data[i].id+`">
			<td >`+data[i].name+`</td>
			<td>`+data[i].address.city+`</td>
			<td>`+data[i].address.street+`</td>
			<td>`+data[i].description+`</td>
			<td><button id="details" class="ui primary basic button">Details</button>
			</tr>`;
	}
	$('#pharmacies_tableBody').html(temp);
	$('#pharmacies_for_derm_appointments').attr('hidden',false);	
	$('#edit-profile').attr('hidden', true);
	$('#show').attr('hidden',true);
	$('#my_derm_appointments').attr('hidden',true);
	$('#ph_av_con').attr('hidden',true)
	$('#ph_con').attr('hidden',true)
}
function showPharmacy(data){
	$('#pharmacy-name').text("Name:"+data.name);
	$('#pharmacy-description').text("Description:"+data.description);
	$('#pharmacy-city').text("City:"+data.address.city);
	$('#pharmacy-address').text("Street:"+data.address.street);
	$('#pharmacy-details').attr('hidden',false);
	$('#pharmacies_for_derm_appointments').attr('hidden',true);
	$('#my_derm_appointments').attr('hidden',true);	
	$('#ph_av_con').attr('hidden',true)
	$('#ph_con').attr('hidden',true)
	
}

function showMyAppointmentsAtDermatologists(data){
	$('#pharmacy-details').attr('hidden',true);
	let temp='';
	for (i in data){
		temp+=`<tr id="`+data[i].id+`">
			<td >`+data[i].dermatologistFirstName+` `+data[i].dermatologistLastName+`</td>
			<td>`+data[i].grade+`</td>
			<td>`+data[i].date+` `+data[i].time+`</td>
			<td>`+data[i].duration+` min </td>
			<td>`+data[i].price+` din </td>
			<td><button id="cancel_appointment" class="ui primary basic button">Cancel appointment</button>
			</tr>`;
	}
	
	$('#my_appointments_dermatology_body').html(temp);
	$('#my_derm_appointments').attr('hidden',false);
	$('#derm_appointments').attr('hidden',true);
	$('#pharmacy-details').attr('hidden',true);
	$('#pharmacies_for_derm_appointments').attr('hidden',true);
	$('#edit-profile').attr('hidden', true);
	$('#show').attr('hidden',true);
	$('#ph_av_con').attr('hidden',true)
	$('#ph_con').attr('hidden',true)
}

});