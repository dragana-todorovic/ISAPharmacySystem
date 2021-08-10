$(document).ready(function() {
	$('a#dermatology_appointment_scedule').click(function(){
		$('#derm_appointments').attr('hidden', false);
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
		     });
	});
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