$(document).ready(function() {
	var email = localStorage.getItem('email')
	var term;
	$('a#schedule_consulting').click(function(){
			
		$('#pharmacies_for_derm_appointments').attr('hidden',true);
		$('#shedule_consulting').attr('hidden',false);
		$('#ph_av_con').attr('hidden',true)
		$('#my_derm_appointments').attr('hidden',true);
		$('#derm_appointments').attr('hidden',true);
		$('#pharmacy-details').attr('hidden',true);
		$('#pharmacies_for_derm_appointments').attr('hidden',true);
		$('#edit-profile').attr('hidden', true);
		$('#show').attr('hidden',true);
		$('#my_ph_appointments').attr('hidden',true)
	});
	$('a#my_app_ph').click(function(){
		console.log(email);
			customAjax({
	        method:'GET',
	        url:'/pharmacy/getCounselingByPatienetId/'+email,
	        contentType: 'application/json',
	        success: function(data){
				console.log(data);
				showMyCounslingAppointments(data);
			},
			error: function(){
				console.log("error");
			}
	     });

	});
	$('#pharamciesForConsulting').on('click',function(){
		console.log($('#date').val())
		term=$('#date').val();
		customAjax({
			method:'GET',
	        url:'/pharmacy/getPharamciesWithAvailablePharmacists/' + term,
	        contentType: 'application/json',
    		success: function(data) {
				showPharmaciesForConsulting(data);
    		},
    		error:function(message){
				alert("Error")
    			console.log(message)
    		}
    	});
	})
	
	 $('#ph_av_con_body').on('click','button',function(event){
		var pharmacy = $(event.target).closest('tr').attr('id');
		customAjax({
			method:'GET',
	        url:'/pharm/getAvailablePharmacistsByPharmacy/'+pharmacy+'/'+term,
	        contentType: 'application/json',
    		success: function(data) {
				showPharmacistsForConsulting(data);
    		},
    		error:function(message){
				alert("Error")
    			console.log(message)
    		}
    	});
		
	})
	
	 $('#ph_con_body').on('click','button',function(event){
		var pom = $(event.target).closest('tr').attr('id');
		var pharmacistEmail;
		var date=term.split("T")[0];
		var time=term.split("T")[1];
		var duration="30";
		customAjax({
			method:'GET',
	        url:'/pharm/getPharmacistsById/'+pom,
	        contentType: 'application/json',
    		success: function(data) {
				console.log(data);
				pharmacistEmail=data.user.email;
				obj = JSON.stringify({pharmacistEmail:pharmacistEmail,startDate:date,startTime:time,patientEmail:email,duration:duration});
	    		customAjax({
		        method:'POST',
		        url:'/pharm/scheduleAnAppointment',
		        data : obj,
		        contentType: 'application/json',
		        success: function(){
					
		        	alert("Success scheduled an appointment!")
				
				},
				error: function(){
					
					alert("Can't schedule an appointment. Date and time aren't correct. You are not working then, patient and you are not available ot it passed")
				}
		      });
    		},
    		error:function(message){
				alert("Error")
    			console.log(message)
    		}
    	});
		$('#my_ph_appointments_body').on('click','button',function(event){
		pom = $(event.target).closest('tr').attr('id');
        customAjax({
            method:'POST',
            url:'/pharmacy/cancelCounselingAppointment/'+pom ,
            contentType: 'application/json',
            success: function() {
               alert("Successfully canceled counseling!");
            },
            error:function(){
                alert("You can not cancel counseling within less than 24 before appointment!");
            }
        });
		
	})

		
	})

function showPharmacistsForConsulting(data){
	$('#pharmacy-details').attr('hidden',true);
	let temp='';
	for (i in data){
		temp+=`<tr id="`+data[i].id+`">
			<td >`+data[i].firstName+` `+data[i].lastName+`</td>
			<td>`+data[i].avrageGrade+`</td>
			<td><button id="schedule_counsling" class="ui primary basic button">Schedule counsling</button>
			</tr>`;
	}
	
	$('#ph_con_body').html(temp);	
	$('#ph_con').attr('hidden',false)
	$('#ph_av_con').attr('hidden',true)
	$('#my_derm_appointments').attr('hidden',true);
	$('#derm_appointments').attr('hidden',true);
	$('#pharmacy-details').attr('hidden',true);
	$('#pharmacies_for_derm_appointments').attr('hidden',true);
	$('#edit-profile').attr('hidden', true);
	$('#show').attr('hidden',true);
	$('#shedule_consulting').attr('hidden',true);
	$('#my_ph_appointments').attr('hidden',true)
}

function showPharmaciesForConsulting(data){
	$('#pharmacy-details').attr('hidden',true);
	let temp='';
	for (i in data){
		temp+=`<tr id="`+data[i].id+`">
			<td >`+data[i].pharmacyName+`</td>
			<td>`+data[i].pharmacyCity+`</td>
			<td>`+data[i].pharmacyStreet+`</td>
			<td>`+data[i].pharmacyGrade+`</td>
			<td>`+data[i].pharmacyPrice+`</td>
			<td><button id="show_pharmacists" class="ui primary basic button">Show available pharmacists</button>
			</tr>`;
	}
	
	$('#ph_av_con_body').html(temp);	
	$('#ph_av_con').attr('hidden',false)
	$('#ph_con').attr('hidden',true)
	$('#my_derm_appointments').attr('hidden',true);
	$('#derm_appointments').attr('hidden',true);
	$('#pharmacy-details').attr('hidden',true);
	$('#pharmacies_for_derm_appointments').attr('hidden',true);
	$('#edit-profile').attr('hidden', true);
	$('#show').attr('hidden',true);
	$('#shedule_consulting').attr('hidden',true);	
	$('#my_ph_appointments').attr('hidden',true)
}

function showMyCounslingAppointments(data){
	$('#pharmacy-details').attr('hidden',true);
	let temp='';
	for (i in data){
		var date=data[i].time.split("T")[0];
		var time=data[i].time.split("T")[1];
		if(data[i].isCounslingExpired){
			temp+=`<tr id="`+data[i].id+`">
				<td >`+data[i].pharmacistsFirstName+` `+data[i].pharmacistsLastName+`</td>
				<td>`+data[i].pharmacyName+`</td>
				<td>`+data[i].pharmacyCity+`</td>
				<td>`+data[i].pharmacyStreet+`</td>
				<td>`+date+` `+time+`</td>
				<td>`+data[i].duration+`</td>
				<td>`+data[i].price+`</td>
				<td><button id="cancel_counsling" class="ui primary basic button" disabled>Cancel appointment</button>
				</tr>`;
			}
			else{
			temp+=`<tr id="`+data[i].id+`">
			<td >`+data[i].pharmacistsFirstName+` `+data[i].pharmacistsLastName+`</td>
			<td>`+data[i].pharmacyName+`</td>
			<td>`+data[i].pharmacyCity+`</td>
			<td>`+data[i].pharmacyStreet+`</td>
			<td>`+data[i].time+`</td>
			<td>`+data[i].duration+`</td>
			<td>`+data[i].price+`</td>
			<td><button id="cancel_counsling" class="ui primary basic button">Cancel appointment</button>
			</tr>`;
		}
			
	}
	
	$('#my_ph_appointments_body').html(temp);	
	$('#my_ph_appointments').attr('hidden',false)
	$('#ph_av_con').attr('hidden',true)
	$('#ph_con').attr('hidden',true)
	$('#my_derm_appointments').attr('hidden',true);
	$('#derm_appointments').attr('hidden',true);
	$('#pharmacy-details').attr('hidden',true);
	$('#pharmacies_for_derm_appointments').attr('hidden',true);
	$('#edit-profile').attr('hidden', true);
	$('#show').attr('hidden',true);
	$('#shedule_consulting').attr('hidden',true);
}
});