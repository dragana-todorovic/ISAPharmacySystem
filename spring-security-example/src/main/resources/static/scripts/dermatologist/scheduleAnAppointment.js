$(document).ready(function(e){
	var email = localStorage.getItem('email');
	$("#scheduleAnAppointment").click(function () {
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
		               <td>
                    
                </td>
		            </tr>
		         
					 <tr>
		                <td>Duration:</td>
						<td> <input  type="text" name="txtDuration" id="txtDuration" /></td>
		                </tr>
		          <tr>
		                <td>Date for appointment:</td>		
						<td><input type="text" id="datepickerStartDate"></td>		                
		            </tr>
				<td>Time for appointment:</td>		
						<td><input value="08:00" type="time" id="txtTime"></td>		                
		            </tr>
		            
		        </tbody>
	        <tfoot class="full-width">
			    <tr>
			      <th></th>
			      <th colspan="2">
					   <input id = "schedule" class="ui right floated positive basic button" type = "button" value = "Schedule"></input>
			    
			      </th>
			    </tr>
 <p id="errorInput"> </p>
			  </tfoot>
		    </table> <p id="errorInput"> </p>`);
$( "#datepickerStartDate" ).datepicker({
	format: 'yyyy-mm-dd'
});
btnAcceptChange = document.getElementById("schedule")
	btnAcceptChange.disabled = true
	
	
	 input_email=$('#txtPatient');
	var input_Date = $('#datepickerStartDate');
    var input_Time = $('#txtTime');
	var input_Duration = $('#txtDuration');

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
				 error: function(medicines){
		
		      }
});


input_Duration.keyup(function () {
	  	if(validateNumber(input_Duration.val())) {
			btnAcceptChange.disabled = false
	  	}
	  	if(!validateNumber(input_Duration.val())){
	  		btnAcceptChange.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#txtDuration').css('border-color', 'red');
	  		$("#errorInput").text("Duration must have from one to  digits!")
	  		$('#errorInput').css('color', 'red');
	  	}else {
	  		$(this).removeClass(`alert-danger`);
	  		$('#txtDuration').css('border-color', '');
	  		$("#errorInput").text("")
	  	}
  });

$('#schedule').click(function(){	
	var dermatologistEmail = email;
	var startDate =  $('#datepickerStartDate').val();
	var startTime = $('#txtTime').val();
	var duration = $('#txtDuration').val();
	var e = document.getElementById("dropdownPatients");

	
	console.log(e.value);	
	if(startDate!="" && e.value!=""){
	var patientEmail = e.value;
	
	
	$("#errorInput").text("")
	
	obj = JSON.stringify({dermatologistEmail:dermatologistEmail,startDate:startDate,startTime:startTime,patientEmail:patientEmail,duration:duration});
			customAjax({
		        method:'POST',
		        url:'/derm/scheduleAnAppointment',
		        data : obj,
		        contentType: 'application/json',
		        success: function(){
					
		        	alert("Success scheduled an appointment!")
					location.href ="dermatologist.html";
				
				},
				error: function(){
					
					alert("Can't schedule an appointment. Date and time aren't correct. You are not working then, patient and you are not available ot it passed")
				}
		            });}
else{
			
			$("#errorInput").text("Choose date, time and email!")
	  		$('#errorInput').css('color', 'red');
}

});
});	
});
function validateEmail(email) {
	    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	    return re.test(String(email).toLowerCase());
}
function validateNumber(name) {
	    const re = /^[0-9]{1,3}$/;
	    return re.test(String(name));
}