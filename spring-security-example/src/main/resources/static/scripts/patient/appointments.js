$(document).ready(function() {
	$('a#dermatology_appointment_scedule').click(function(){
		$('#derm_appointments').attr('hidden', false);
			customAjax({
		        method:'GET',
		        url:'/patient/getAllAppointmentsDermatology',
		        data : obj,
		        contentType: 'application/json',
		        success: function(data){
					showDermatologyAppointments(data)
				},
				error: function(){
					console.log("error");
				}
		     });
	});
});