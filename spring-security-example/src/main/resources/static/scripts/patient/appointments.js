$(document).ready(function() {
	$('a#dermatology_appointment_scedule').click(function(){
	/*	$('#derm_appointments').attr('hidden', false);
			customAjax({
		        method:'GET',
		        url:'/appointment/getAll',
		        contentType: 'application/json',
		        success: function(data){
					showDermatologyAppointments(data)
				},
				error: function(){
					console.log("error");
				}
		     });*/
		//$('#pharmacies_for_derm_appointments').attr('hidden', false);
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
function showPharmacies(data){
	let temp='';
	for (i in data){
		temp+=`<tr id="`+data[i].id+`">
			<td >`+data[i].name+`</td>
			<td>`+data[i].address.city+`</td>
			<td>`+data[i].address.street+`</td>
			<td>`+data[i].description+`</td>
			<td><button  class="ui primary basic button">Show available appointments</button>
			</tr>`;
	}
	$('#pharmacies_tableBody').html(temp);
	$('#pharmacies_for_derm_appointments').attr('hidden',false);
}
function showDermatologyAppointments(data){
	console.log(data);
		let temp='';
	for (i in data){
		temp+=`<tr id="`+data[i].id+`">
			<td>`+data[i].dermatologist_id+`</td>
			<td>`+data[i].dermatologist_id+`</td>
			<td>`+data[i].start_date_time,+`</td>
			<td>`+data[i].start_date_time,+`</td>
			</tr>`;
	}
	$('#appointments_dermatology_table').html(temp);
}

});