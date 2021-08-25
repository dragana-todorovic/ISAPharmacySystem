$(document).ready(function(e){
	var email = localStorage.getItem('email');
	$("#scheduleAPredefinedAppointment").click(function () {
		console.log("Usao u klik")
	 $("#showData").html(`
				<table id="table" class="ui large table" style="width:50%; margin-left:auto; 
    margin-right:auto; margin-top: 40px;">
		       <thead>
		            <tr class="success">
		                <th colspan="3" class = "text-info" style= "text-align:center;">Appointment</th>
		            </tr>
		        </thead>
		        <tbody>
		            <tr>
		                <td>Patient email:</td>
						<td><select id="dropdownPatients" >
      <option  ></option></select></td>
		              
               <td></td>
		            </tr>		        					
		          <tr>
		            <td>Predefined appointments:</td>
						<td><select id="dropdownAppointments" >
      <option  ></option></select></td>
		              
               <td></td> </tr>
				 <tr>
		                <td>Duration:</td>
						<td> <input  type="text" name="txtDuration" id="txtDuration" /></td>
 <td></td>		                
</tr>
				
		            
		        </tbody>
	        <tfoot class="full-width">
			    <tr>
			     
			      <th colspan="3">
					
					   <input id = "schedule" class="ui right floated positive basic button" type = "button" value = "Schedule"></input>
			    
			      </th>
			    </tr>
 <p id="errorInput"> </p>
			  </tfoot>
		    </table> <p id="errorInput"> </p>`);
	btnAcceptChange = document.getElementById("schedule")
	btnAcceptChange.disabled = true
	var input_Duration = $('#txtDuration');
	input_Duration.keyup(function () {
	  	if(validateNumber(input_Duration.val())) {
			btnAcceptChange.disabled = false
	  	}
	  	if(!validateNumber(input_Duration.val())){
	  		btnAcceptChange.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#txtDuration').css('border-color', 'red');
	  		$("#errorInput").text("Duration must have from one to three  digits!")
	  		$('#errorInput').css('color', 'red');
	  	}else {
	  		$(this).removeClass(`alert-danger`);
	  		$('#txtDuration').css('border-color', '');
	  		$("#errorInput").text("")
	  	}
  });
customAjax({
		      url: '/derm/getAllPatients',
		      method: 'GET',
		      success: function(patients){
			
				console.log(patients)
					for (var i = 0; i < patients.length; i++) { 
						console.log(patients[i])
					 var option = document.createElement("option");
					 var dropdown = document.getElementById("dropdownPatients");
					 option.text = (patients[i]);
					 dropdown.add(option); }},
				 error: function(){
		
		      }
});
customAjax({
		      url: '/derm/getAllPredefinedAppointments/'+email,
		      method: 'GET',
		      success: function(appointments){
			for (var i = 0; i < appointments.length; i++) { 
						console.log(appointments[i])
					 var option = document.createElement("option");
					 var dropdown = document.getElementById("dropdownAppointments");
					 option.text = (appointments[i].startDateTime.split("T")[0]+" "+ appointments[i].startDateTime.split("T")[1]+" sifra "+appointments[i].id);
					 dropdown.add(option); }
				},
				 error: function(){
		
		      }
});
$('#schedule').click(function(){	
	var dermatologistEmail = email;
	
	var e = document.getElementById("dropdownPatients");
	var app = document.getElementById("dropdownAppointments");

	if(app.value!="" && e.value!=""){
	var patientEmail = e.value;
	var startDateTimeId = app.value;
	var duration = $('#txtDuration').val();
	
	
	$("#errorInput").text("")
	
	obj = JSON.stringify({dermatologistEmail:dermatologistEmail,startDateTimeId:startDateTimeId,patientEmail:patientEmail,duration:duration});
			customAjax({
		        method:'POST',
		        url:'/derm/scheduleAPredefinedAppointment',
		        data : obj,
		        contentType: 'application/json',
		        success: function(){
					
		        	alert("Success scheduled an appointment!")
					location.href ="dermatologist.html";
				
				},
				error: function(){
					
					alert("Can't schedule an appointment. Date and time aren't correct. You are not working then or now, patient and you are not available ot it passed")
				}
		            });}
else{
			
			$("#errorInput").text("Choose patient and predefined appointment!")
	  		$('#errorInput').css('color', 'red');
}

});
});	

	
	});
	function validateNumber(name) {
	    const re = /^[0-9]{1,3}$/;
	    return re.test(String(name));
}