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

	});
	$('a#my_app_ph').click(function(){
		console.log(email);
			customAjax({
	        method:'GET',
	        url:'/pharmacy/getCounselingByPatienetId/'+email,
	        contentType: 'application/json',
	        success: function(data){
				console.log(data);
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
		var date=term.split("T")[0];
		var time=term.split("T")[1];
		var duration="30";
		alert(time)
		obj = JSON.stringify({pharmacistEmail:pom,startDate:date,startTime:time,patientEmail:email,duration:duration});
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
}

function showPharmaciesForConsulting(data){
	$('#pharmacy-details').attr('hidden',true);
	let temp='';
	for (i in data){
		temp+=`<tr id="`+data[i].id+`">
			<td >`+data[i].pharmacyName+` `+data[i].dermatologistLastName+`</td>
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
}
});