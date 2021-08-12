$(document).ready(function(e){
	
var email = localStorage.getItem('email')
	 $("#startAppointment").click(function () {
		
		customAjax({
		      url: '/derm/getPatientsForAppointment/' + email,
		      method: 'GET',
		      success: function(patients){
	let temp='';
				for (var i in patients){
			
					console.log(patients[i].myPatientId)
					
			temp+=`<tr><td>`+patients[i].name+`</td><td>`+patients[i].surname+`</td><td>`+patients[i].startDateTime.split("T")[0]+`</td><td> <input name="start" id="btnStart` + patients[i].myPatientId + `" name = "start" class="btn btn-primary" type="button" value="Start appointment"></td>`;
			
			temp+=`</tr>`;
		}
					 $("#showData").html(`
				<table id="table" class="ui large table" style="width:50%; margin-left:auto; 
    margin-right:auto; margin-top: 40px;">
		        <thead>
		            <tr class="success">
		                <th colspan="4" class = "text-info" style= "text-align:center;">Appointments</th>
		            </tr>
						<tr>
						<th >Name</th>
						<th >Surname</th>
						<th >Start date time</th>
						<th></th>
						</tr>
		        </thead>
		       <tbody id="myPatientsList">
	        </tbody>
		        <tfoot class="full-width">
    <tr>

    </tr>
  </tfoot>
		    </table> <p id="er"> </p>`);
$('#myPatientsList').html(temp);
 $("input:button[name=start]").click(function () {
	var id= this.id;
	console.log(this.id)
	customAjax({
		      url: '/derm/getPatientById/' + id,
		      method: 'GET',
		      success: function(p){
			console.log(p)
			var patient = p;
			var id = p.id
			customAjax({
		      url: '/derm/getMedicinesOnWhichPatientIsNotAllergic/' + id,
		      method: 'GET',
		      success: function(medicines){
			
				console.log(medicines)
					for (var i = 0; i < medicines.length; i++) { 
						console.log(medicines[i].name)
					 var option = document.createElement("option");
					 var dropdown = document.getElementById("dropdownMedicines");
					 option.text = (medicines[i].name);
					 dropdown.add(option); }},
				 error: function(medicines){
		
		      }
});
				
			 $("#showData").html(`
				<table id="table" class="ui large table" style="width:50%; margin-left:auto; 
    margin-right:auto; margin-top: 40px;">
		       <thead>
		            <tr class="success">
		                <th colspan="2" class = "text-info" style= "text-align:center;">Appointment</th>
		            </tr>
		        </thead>
		        <tbody>
		            <tr>
		                <td>Patient name:</td>
		               <td>
                    <input disabled="disabled" type="text" name="txtPatient" id="txtPatient" value="`+ ((p.firstName != null) ? p.firstName:`-`) + `"/>
                </td>
		            </tr>
		            <tr>
		                <td>Diagnosis:</td>
						<td> <input  type="text" name="txtDiagnosis" id="txtDiagnosis" /></td>
		                </tr>
		            <tr>
					 <tr>
		                <td>Duration:</td>
						<td> <input  type="text" name="txtDuration" id="txtDuration" /></td>
		                </tr>
		            <tr>
		                <td>Recommend medicine:</td>
						<td><select id="dropdownMedicines" >
      <option value="" ></option></select></td>
		                
		            </tr>
		            
		        </tbody>
	        <tfoot class="full-width">
			    <tr>
			      <th></th>
			      <th colspan="2">
					   <input id = "save" class="ui right floated positive basic button" type = "button" value = "Save"></input>
			    
			      </th>
			    </tr>
 <p id="errorInput"> </p>
			  </tfoot>
		    </table> <p id="errorInput"> </p>`);
btnAcceptChange = document.getElementById("save")
	btnAcceptChange.disabled = true
	
	
	 input_duration=$('#txtDuration');
	input_diagnosis=$('#txtDiagnosis');
	
input_duration.keyup(function () {
	  	if(  input_diagnosis.val()!="" && validateNumber(input_duration.val())) {
			btnAcceptChange.disabled = false
	  	}
	  	if(!validateNumber(input_duration.val())){
	  		btnAcceptChange.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#txtDuration').css('border-color', 'red');
	  		$("#errorInput").text("You can only use numbers for duration!")
	  		$('#errorInput').css('color', 'red');
	  	}else {
	  		$(this).removeClass(`alert-danger`);
	  		$('#txtDuration').css('border-color', '');
	  		$("#errorInput").text("")
	  	}
  });
input_diagnosis.keyup(function () {
	console.log("Dijagnoza"+input_diagnosis.val())
	  	if( input_diagnosis.val()!="" && validateNumber(input_duration.val())) {
			btnAcceptChange.disabled = false
	  	}
	  	if(input_diagnosis.val()==""){
	  		btnAcceptChange.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#txtDiagnosis').css('border-color', 'red');
	  		$("#errorInput").text("You need to fill diagnosis!")
	  		$('#errorInput').css('color', 'red');
	  	}else {
	  		$(this).removeClass(`alert-danger`);
	  		$('#txtDiagnosis').css('border-color', '');
	  		$("#errorInput").text("")
	  	}
  });
$('#save').click(function(){	
			var patientId = patient.id
			var patientEmail = patient.email;
			var dermatologistEmail = email;
			var startDateTime;
			console.log(dermatologistEmail)
			console.log(patientEmail)
	
		
			
			var diagnosis = $('#txtDiagnosis').val();
			var duration = $('#txtDuration').val();
			console.log(diagnosis);
			console.log(startDateTime)
			var e = document.getElementById("dropdownMedicines");
			var medicineName = e.value;
			obj = JSON.stringify({patientEmail:patientEmail,dermatologistEmail:dermatologistEmail});
			customAjax({
		        method:'POST',
		        url:'/derm/getStartDateTime',
				data:obj,
		        contentType: 'application/json',
		        success: function(start){
					startDateTime = start;
					console.log("STARTTTTTTTTTTTT "	+startDateTime)
					var endDateTime = new Date();
					console.log(endDateTime.toLocaleDateString);
				},
				error: function(){
					alert("Can't get startDateTime'")
				}
		            });
			
			obj = JSON.stringify({duration:duration,patientEmail:patientEmail,dermatologistEmail:dermatologistEmail,patientId:patientId,diagnosis:diagnosis,medicineName:medicineName});
			customAjax({
		        method:'POST',
		        url:'/derm/saveAppointment',
		        data : obj,
		        contentType: 'application/json',
		        success: function(){					
		        	alert("Success saved appointment!")
					location.href = "dermatologist.html";
				},
				error: function(){
					alert("Can't save appointment'")
				}
		            });

			
	 });
			 },	 
			       
		      error: function(){
			
		      }

	
	});
});
		
	
}
});	
});	
});
function validateNumber(name) {
	    const re = /^[0-9]+$/;
	    return re.test(String(name));
}