$(document).ready(function(e){ 
	
	$("#dermatologist").click(function () {
			  console.log("POGODIO")
			  showDermatologists();
		 });
	
	
});
let temp = ''

let showDermatologists = function() {
	 $("#showData").html(`<table class="ui basic large table" style="width:50%; margin-left:auto; 
			    margin-right:auto; margin-top: 40px;">
					        <thead>
					            <tr class="success">
					                <th colspan="2" class = "text-info" style= "text-align:center;">Create action and benefit</th>
					            </tr>
					        </thead>
					        <tbody>
					        <tr>
					        	
					                <td>Name:</td>
					                <td>
					               
			 						</td>

					            </tr>
					        	<tr>
					        	
					                <td>Valid:</td>
					                <td>
					                
			 						</td>
			 						   

					            </tr>
					            <tr>
					            <td colspan="2">
					            <div >
					           <table class="ui black table" id="tabelaZaRadneDane">
										  <thead>
										    <tr><th>Date</th>
										    <th>Start time</th>
										    <th>End time</th>
										  </tr></thead><tbody id="bodyTime">
										   
										  </tbody>
										   <tfoot class="full-width">
    <tr>
      <th></th>
      <th colspan="2">
           <input id = "showModal" class="ui right floated primary button" type = "button" value = "Add time"></input>
			    
       
      </th>
    </tr>
  </tfoot>
										</table></div>
					            </td>
					            </td>
					            
					        </tbody>
					        <tfoot class="full-width">
			    <tr>
			      <th></th>
			      <th colspan="2">
					   <input id = "addNew" class="ui right floated positive button" type = "button" value = "Add new action or benefit"></input>
			    
			      </th>
			    </tr>
			  </tfoot>
					    </table>
					            <p id="er"> </p>
					            <div class="ui modal">
  <i class="close icon"></i>
  <div class="header">
	Add working day
  </div>
  <div class="content">

    <table class="ui basic large table" style="width:50%; margin-left:auto; 
			    margin-right:auto; margin-top: 40px;">
    <tbody>
			 <tr>
					        	
					                <td>Date:</td>
					                <td>
  <div class="ui calendar" id="datum">
    <div class="ui input left icon">
      <i class="calendar icon"></i>
      <input type="text" placeholder="Date" id="workingDate">
    </div>
  </div></td>
					            </tr>
					            <tr>
					            <td>Start time:</td>
					            <td>
					             <div class="ui calendar" id="start">
    <div class="ui input left icon">
      <i class="time icon"></i>
      <input type="text" placeholder="Time" id="startTime">
    </div>
  </div></td>

					            </tr>
					            <tr>
					            <td>End time:</td>
					            <td>
					             <div class="ui calendar" id="end">
    <div class="ui input left icon">
      <i class="time icon"></i>
      <input type="text" placeholder="Time" id="endTime">
    </div>
  </div></td>
					            </tr>
					           
					           
					        </tbody>
					    </table>
  </div>
  <div class="actions">
    <div class="ui black deny button">
      Nope
    </div>
      <input class="ui right floated positive button" type = "button" value = "Add time" id="addWorkingDay"></input>
			 
     
  </div>
</div>`);
	 
	 $('#showModal').click(function() {
		 $('.ui.modal')
		  .modal('show')
		;
	 });
	 $('#datum').calendar({
		  type: 'date'
		});
	 $('#start').calendar({
		  type: 'time'
		});
	 $('#end').calendar({
		  type: 'time'
		});
	
	 $('#addWorkingDay').click(function() {
		let workingDate = $('#workingDate').val()
		let startTime = $('#startTime').val()
		let endTime = $('#endTime').val()
		
	temp+=`<tr><td>`+workingDate+`</td><td>`+startTime+`</td><td>`+endTime+`</td></tr>`;		
	$('#bodyTime').html(temp)	
		popuniTabelu(workingDate, startTime, endTime)

		 
	 });
	 
	 
}

