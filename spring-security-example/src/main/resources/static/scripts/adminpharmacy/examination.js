var email = localStorage.getItem('email')
$(document).ready(function(e){ 
	
	$("#predefinedExamination").click(function () {
		 customAjax({
		      url: '/pharmacy/getAllDermatologist/' + email,
		      method: 'GET',
		      contentType: 'application/json',
		      success: function(data){
		    	  createExaminationForm(data);
		      },
		      error: function(){
		      }

	 });
	  
	 });
	

});

let createExaminationForm = function(data) {
	let dermatologistCombo = ''
		  for (i in data) {
			  dermatologistCombo += `<div class="item" data-value="` + data[i].id + `">` + data[i].user.firstName + ` `+  data[i].user.lastName + `</div>`
	      }
	
	 $("#showData").html(`<table class="ui basic large table" style="width:50%; margin-left:auto; 
			    margin-right:auto; margin-top: 40px;">
					        <thead>
					            <tr class="success">
					                <th colspan="2" class = "text-info" style= "text-align:center;">Create examination</th>
					            </tr>
					        </thead>
					        <tbody>
					          <tr><td>Choose dermatologist: </td>
			 <td class="ui input"> <div class="ui selection dropdown">
  <input type="hidden" id="dermatologistCombo">
  <i class="dropdown icon"></i>
  <div class="default text">Choose dermatologist...</div>
  <div class="menu">
  `+dermatologistCombo+`
  </div>
</div>
<script>
$('.ui.dropdown')
  .dropdown()
;
</script></td>
    </tr>	
     <tr><td>Date</td><td>
    	<div class="ui calendar" id="date">
          <div class="ui input left icon">
            <i class="calendar icon"></i>
            <input type="text" placeholder="Date of examination" id="dateOfExamination">
           
          </div></div>
        </div></td></tr>
        <tr><td>Start time</td>
        <td>
         <div class="ui input left icon">
     <div class="ui input left icon">
      <input type="time" value="07:30:00" placeholder="Time" id = "timeOfExamination">
  </div></div>
  </td></tr>
					            <tr>
					                <td>Duration in minutes:</td>
					                <td> <div class="ui input left icon">
      <input type="number" placeholder="Duration..." id = "duration">
  </div></td>

					            </tr>
					            <tr>
					                <td>Price:</td>
					                <td> <div class="ui input left icon">
      <input type="number" placeholder="Price..." id = "price">
  </div></td>

					            </tr>
					           
					           
					        </tbody>
					        <tfoot class="full-width">
			    <tr>
			      <th></th>
			      <th colspan="2">
					   <input id = "addNew" class="ui right floated positive button" type = "button" value = "Create predefined examination"></input>
			    
			      </th>
			    </tr>
			  </tfoot>
					    </table> 
					    
					    <div id="error" class="ui modal">
	  <i class="close icon"></i>
	  <div class="header">
		Error
	  </div>
	  <div class="content">
	  <div class="ui negative message">
  <div class="header">
    We're sorry, you cannot create examination.
  </div>
  <p>Dermatologist is not available or does not work in a pharmacy at that time!
</p></div>
	  </div>
	</div>
	`);
	 
	 var today = new Date();
	 $('#date').calendar({
		  type: 'date',
		  initialDate:  new Date(today.getFullYear(), today.getMonth(), today.getDate()),
		  minDate: new Date(today.getFullYear(), today.getMonth(), today.getDate())
		});
	 
	 $('#addNew').click(function() {
		 	var dermatologistId = $('#dermatologistCombo').val();
		 	var date = formatDate($('#dateOfExamination').val());
		 	var time = $('#timeOfExamination').val();
		 	var duration = $('#duration').val();
		 	var price = $('#price').val();

			obj = JSON.stringify({
				dermatologistId:dermatologistId,
				date:date,
				time: time,
				duration: duration,
				price :price
			});
			
			customAjax({
			      url: '/appointment/createPredefinedAppointmet/' + email,
			      method: 'POST',
			      data:obj,
				  contentType: 'application/json',
				        success: function(){
				        	alert('Successfully added!')
				        	location.href = "adminpharmacy.html";
				        	
						},
					      error: function(){
					    	  $('#error')
							  .modal('show')
							
					      }
			    });
		});
}


function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) 
        month = '0' + month;
    if (day.length < 2) 
        day = '0' + day;

    return [year, month, day].join('-');
}

