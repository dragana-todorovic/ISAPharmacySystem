var email = localStorage.getItem('email')
$(document).ready(function(e){ 
	
	$("#dermatologist").click(function () {
		    	  customAjax({
				      url: '/pharmacy/getAllDermatologist/' + email,
				      method: 'GET',
				      contentType: 'application/json',
				      success: function(data){	 
				    	  console.log(data)
				    	  showDermatologists(data);
				      },
				      error: function(){
				      }

			 });
			  
		 });
	
	
});

let showDermatologists = function(data) {
	let temp='';
	for (i in data){
		temp+=`<tr><td class="firstname">`+
		data[i].user.firstName+`</td><td class="soba">`+data[i].user.lastName+`</td><td></td>
		<td><button id = "`+data[i].id+`" name="dodajVrijeme" class="ui primary button">
		<i class="plus icon"></i>
		Add working time
		</button>
			 </td>`;
	}
	temp+=`</tr>`
	
	 $("#showData").html(`<table class="ui very padded selectable table" style="width:70%; margin-left:auto; 
			    margin-right:auto; margin-top: 40px;">
	  <thead>
	    <tr><th>First name</th>
	    <th>Last name</th>
	    <th>Avarage rating</th>
	    <th></th>
	  </tr></thead><tbody id="tabelaDermatologa">
	  </tbody>
	</table>
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
</div>`)
let idSelected;
$('#tabelaDermatologa').html(temp)
$("button[name=dodajVrijeme]").click(function() {
		idSelected = this.id
		 $('.ui.modal')
		  .modal('show')
		
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

$("#addWorkingDay").click(function() {
	console.log(idSelected)
	let workingDate = $('#workingDate').val()
		let startTime = $('#startTime').val()
		let endTime = $('#endTime').val()
		
		obj = JSON.stringify({

			workingDate:workingDate,
			startTime:startTime,
		endTime: endTime
		});
	
	
	customAjax({
	      url: '/pharmacy/addWorkingDayForDermatologist/' + idSelected,
	      method: 'POST',
	      data:obj,
		  contentType: 'application/json',
		        success: function(){
		        	alert('Successfully published!')
		        	location.href = "adminpharmacy.html";
		        	
				},
			      error: function(){
			       	alert('Error');
			      }
	    });
});
	
}

