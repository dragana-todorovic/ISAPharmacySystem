$(document).ready(function(e){
	
var email = localStorage.getItem('email')
var idStart;
var result=false;


	 $("#startAppointment").click(function () {
		
		customAjax({
		      url: '/derm/getPatientsForAppointment/' + email,
		      method: 'GET',
		      success: function(patients){
	let temp='';
				for (var i in patients){
			
					console.log(patients[i].myPatientId)
					
			temp+=`<tr><td>`+patients[i].name+`</td><td>`+patients[i].surname+`</td><td>`+patients[i].startDateTime.split("T")[0]+`</td><td> <input name="start" id="btnStart` + patients[i].myPatientId +`k`+patients[i].startDateTime + `" name = "start" class="btn btn-primary" type="button" value="Start appointment"><br> <input name="finish" id="btnFinish` + patients[i].myPatientId+`k`+patients[i].startDateTime + `" name = "finish" class="btn btn-danger" type="button" value="Patient hasn't apperead"></td>`;
			
			temp+=`</tr>`;
		}
					 $("#showData").html(`
				<br><label>Pretrazi po imenu pacijenta:</label><input style="color: black;" type="text"  min="0" id="name" style="width:10%" placeholder="Name"/>
				<br><label>Pretrazi po prezimenu pacijenta:</label><input style="color: black;" type="text"  min="0" id="surname" style="width:10%" placeholder="Surname"/>
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
$("#name").keyup(function () {
        var nadji = ($('#name').val()).toLowerCase();
        $("#table tbody tr").each(function () {
            var gost = ($('td:eq(0)', this).text()).toLowerCase();
            if (gost.includes(nadji) || nadji == "") {
                $(this).show()
            } else {
                $(this).hide()
            }
        });
    });
$("#surname").keyup(function () {
        var nadji = ($('#surname').val()).toLowerCase();
        $("#table tbody tr").each(function () {
            var gost = ($('td:eq(1)', this).text()).toLowerCase();
            if (gost.includes(nadji) || nadji == "") {
                $(this).show()
            } else {
                $(this).hide()
            }
        });
    });
 $("input:button[name=finish]").click(function () {
	var id= this.id;
	console.log(this.id)
	customAjax({
		      url: '/derm/giveOnePenalForPatient/' + id,
		      method: 'POST',
		      success: function(){
				alert("You give patient one penal")
				location.href="dermatologist.html";
				},
				 error: function(medicines){
		
		      }
});
});
 $("input:button[name=start]").click(function () {
	
	var id= this.id;
	idStart= id;
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
					 option.text = (medicines[i].name+",sifra "+medicines[i].id);
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
		                <td>Appointment price:</td>
						<td> <input  type="text" name="txtPrice" id="txtPrice" /></td>
		                </tr>
		            <tr>
					<tr>
		                <td>Therapy duration:</td>
						<td> <input  type="text" name="txtTherapyDuration" id="txtTherapyDuration" /></td>
		                </tr>
		            <tr>
		                <td>Recommend medicine:</td>
						<td><select id="dropdownMedicines" >
      <option value="" ></option></select>
<br> <input name="specification" id="btnSpecification" name = "specification" class="btn btn-info" type="button" value="Get specification">
<br> <input name="specification" id="btnAvailability" name = "checkAvailability" class="btn btn-primary" type="button" value="Check availability">
</td>
		                
		            </tr>
		            
		        </tbody>
	        <tfoot class="full-width">
			    <tr>
			      <th></th>
			      <th colspan="2">
 						<input id = "scheduleAppointment" class="ui right floated positive basic button" type = "button" value = "Schedule appointment"></input>
					   <input id = "save" class="ui right floated positive basic button" type = "button" value = "Save"></input>
			    
			      </th>
			    </tr>
 <p id="errorInput"> </p>
			  </tfoot>
		    </table> <p id="errorInput"> </p>`);
$("#scheduleAppointment").click(function () {
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
	btnSchedule = document.getElementById("schedule")
	btnSchedule.disabled = true
	
	
	 input_email=$('#txtPatient');
	var input_Date = $('#datepickerStartDate');
    var input_Time = $('#txtTime');
	var input_Duration = $('#txtDuration');
	
customAjax({
		      url: '/derm/getPatientWhoIsAtAppointment/'+idStart,
		      method: 'GET',
		      success: function(patient){
					 var option = document.createElement("option");
					 var dropdown = document.getElementById("dropdownPatients");
					 option.text = (patient.email);
					 dropdown.add(option);
					 },
				 error: function(){
		
		      }
});


input_Duration.keyup(function () {
	  	if(validateDuration(input_Duration.val())) {
			btnSchedule.disabled = false
	  	}
	  	if(!validateDuration(input_Duration.val())){
	  		btnSchedule.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#txtDuration').css('border-color', 'red');
	  		$("#errorInput").text("Duration must have from one to three digits!")
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
					
				
				},
				error: function(){
					
					alert("Can't schedule an appointment. Date and time aren't correct. You are not working then or patient and you are not available")
				}
		            });}
else{
			
			$("#errorInput").text("Choose date, time and email!")
	  		$('#errorInput').css('color', 'red');
}

});
});	

	btnAcceptChange = document.getElementById("save")
	btnAcceptChange.disabled = true


	

	
	 console.log("Result"+result)
	input_diagnosis=$('#txtDiagnosis');
	input_price=$('#txtPrice');
	input_therapy_duration=$('#txtTherapyDuration');



$('#btnAvailability').click(function(){
	console.log("Usao u availability")
	var e = document.getElementById("dropdownMedicines");
	var medicineName = e.value;
	console.log(medicineName);
	if(medicineName==""){
		$("#errorInput").text("Choose medicine!")
	  	$('#errorInput').css('color', 'red');
		
	}else{
		$("#errorInput").text("");
		var medicineId = medicineName.split(",sifra ")[1];
		console.log(medicineId)
		customAjax({
		      url: '/derm/checkMedicineAvailability/' + medicineId + "/"+email,
		      method: 'POST',
		      success: function(res){
			
			result = res;
			
			console.log(result)
			if(result==true){
				if(  input_diagnosis.val()!="" && validateNumberPrice(input_price.val()) && validateDuration(input_therapy_duration.val())){
				btnAcceptChange.disabled = false
				}
			}else{
				btnAcceptChange.disabled = true
				console.log("Substitute"+idStart)
				customAjax({
		      url: '/derm/getSubstituteMedicine/' + medicineId +"/"+idStart,
		      method: 'GET',
		      success: function(med){
			console.log("Med"+med.length);
			if(med.length==0){
			btnAcceptChange.disabled = true
	  		$("#errorInput").text("This medicine doesn't exist in pharmacy and this pharmacy hasn't replacement medicine on which patient isn't alergic!")
			$('#errorInput').css('color', 'red');
			}else{
			$("#errorInput").text("Replacement medicines!")
			$('#errorInput').css('color', 'green');
			var select = document.getElementById("dropdownMedicines");
			var length = select.options.length;
			for (i = length-1; i >= 0; i--) {
 				 select.options[i] = null;
}
			for (var i in med){
				console.log("Zamjenski"+med[i].name)

			
			var option = document.createElement("option")
			 var dropdown = document.getElementById("dropdownMedicines");
			 option.text = (med[i].name+",sifra "+med[i].id);
			 dropdown.add(option); 
		 
			
				}			
		}
		},
				error:function(){}
			});
			}
			
			
		},
			error:function(){}});
		
		}
});
	input_therapy_duration.keyup(function () {
		if(result==true){
				if(  input_diagnosis.val()!="" && validateNumberPrice(input_price.val()) && validateDuration(input_therapy_duration.val())){
				btnAcceptChange.disabled = false
				}}
	  	if(!validateDuration(input_therapy_duration.val())){
	  		btnAcceptChange.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#txtTherapyDuration').css('border-color', 'red');
	  		$("#errorInput").text("You can only use one to three digits for duration!")
	  		$('#errorInput').css('color', 'red');
	  	}else {
	  		$(this).removeClass(`alert-danger`);
	  		$('#txtTherapyDuration').css('border-color', '');
	  		$("#errorInput").text("")
	  	}
  });
input_price.keyup(function () {
	if(result==true){
				if(  input_diagnosis.val()!="" && validateNumberPrice(input_price.val()) && validateDuration(input_therapy_duration.val())){
				btnAcceptChange.disabled = false
				}}
	console.log("Validate"+validateNumberPrice(input_price.val()));
	console.log(input_price.val());
	  	if(!validateNumberPrice(input_price.val())){
	  		btnAcceptChange.disabled = true
			$(this).addClass(`alert-danger`);
	  		$('#txtPrice').css('border-color', 'red');
	  		$("#errorInput").text("You can only use numbers for price!")
	  		$('#errorInput').css('color', 'red');
	  	}else {
	  		$(this).removeClass(`alert-danger`);
	  		$('#txtPrice').css('border-color', '');
	  		$("#errorInput").text("")
	  	}
  });


input_diagnosis.keyup(function () {
	if(result==true){
				if(  input_diagnosis.val()!="" && validateNumberPrice(input_price.val()) && validateDuration(input_therapy_duration.val())){
				btnAcceptChange.disabled = false
				}}
	console.log("Dijagnoza"+input_diagnosis.val())
	  
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
$('#btnSpecification').click(function(){
	console.log(result)
	var e = document.getElementById("dropdownMedicines");
	var medicineName = e.value;
	console.log(medicineName);
	if(medicineName==""){
		$("#errorInput").text("Choose medicine!")
	  	$('#errorInput').css('color', 'red');
		
	}else{
		$("#errorInput").text("");
		var medicineId = medicineName.split(",sifra ")[1];
		console.log(medicineId)
		customAjax({
		      url: '/derm/getSpecificationForMedicine/' + medicineId,
		      method: 'GET',
		      success: function(specification){
			console.log(specification)
			$("#errorInput").html("Specification:"+`<br>`
			+"Name: "+specification.name
			+`<br>`
			+"Shape: "+specification.shape+`<br>`
			+"Type: "+specification.type+`<br>`
			+"Content: "+specification.content+`<br>`
			+"Contradiction: "+specification.contradiction);
			
			
			$('#errorInput').css('color', 'green');
			
			
		},
			error:function(){
	
}
});
		
	}
	
});
$('#save').click(function(){
			console.log(idStart.split("k")[1]);
			var startDate = idStart.split("k")[1];
			
			var patientId = patient.id
			var patientEmail = patient.email;
			var dermatologistEmail = email;
			var startDateTime;
			console.log(dermatologistEmail)
			console.log(patientEmail)
	
		
			
			var diagnosis = $('#txtDiagnosis').val();
			var price = $('#txtPrice').val();
			var therapyDuration = $('#txtTherapyDuration').val();
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
			
			obj = JSON.stringify({therapyDuration:therapyDuration,startDate:startDate,patientEmail:patientEmail,dermatologistEmail:dermatologistEmail,patientId:patientId,diagnosis:diagnosis,medicineName:medicineName,price:price});
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
function validateNumberPrice(name) {
	    const re =/^\d+$/;;
	    return re.test(String(name));
}
function validateDuration(name) {
	    const re = /^[0-9]{1,3}$/;
	    return re.test(String(name));
}