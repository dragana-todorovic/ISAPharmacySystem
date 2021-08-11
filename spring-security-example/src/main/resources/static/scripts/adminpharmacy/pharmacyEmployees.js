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

let showWorkingDays = function(data) {
	let temp='';
	for(i in data) {
		temp+=`<tr><td class="firstname">`+
		data[i].day+`</td><td class="soba">`+data[i].startTime+`</td><td>`+data[i].endTime+`</td>`;
	}
	temp+=`</tr>`
		$('#bodyTime').html(temp)
}

let showDermatologists = function(data) {
	let temp='';
	for (i in data){
		temp+=`<tr><td class="firstname">`+
		data[i].user.firstName+`</td><td class="soba">`+data[i].user.lastName+`</td><td></td>								   
		<td>
		<button id = "`+data[i].id+`" name="dodajVrijeme" class="ui primary button">
		<i class="plus icon"></i>
		Add working time
		</button>
		<br>
		<button id = "`+data[i].id+`" name="prikaziVrijeme" class="ui inverse button">
		<i class="show icon"></i>
		Show working times
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
	 <div id="modalniZaNovoVrijeme" class="ui modal">
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
    <div class="ui input left icon">
      <input type="time" placeholder="Time" id="startTime">
  </div></td>

					            </tr>
					            <tr>
					            <td>End time:</td>
					            <td>
					            
    <div class="ui input left icon">
      <input type="time" placeholder="Time" id="endTime"></div>
 </td>
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
</div>


<div id="modalZaRadnoVrijeme" class="ui modal">
  <i class="close icon"></i>
  <div class="header">
	Working times
  </div>
  <div class="content">
<table class="ui black table" id="tabelaZaRadneDane">
										  <thead>
										    <tr><th>Date</th>
										    <th>Start time</th>
										    <th>End time</th>
										  </tr></thead><tbody id="bodyTime">
										   
										  </tbody>
										   <tfoot class="full-width">
  </div>
</div>
`)
let idSelected;
$('#tabelaDermatologa').html(temp)
$("button[name=dodajVrijeme]").click(function() {
		idSelected = this.id
		 $('#modalniZaNovoVrijeme')
		  .modal('show')
		
	 });


$("button[name=prikaziVrijeme]").click(function() {
	idSelected = this.id
	customAjax({
	      url: '/pharmacy/getAllWorkingTimes/' + idSelected + '/' + email,
	      method: 'GET',
		  contentType: 'application/json',
		        success: function(data){
		        	showWorkingDays(data)
				},
			      error: function(){
			       	alert('Error');
			      }
	    });
	 $('#modalZaRadnoVrijeme')
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
	let workingDate = formatDate($('#workingDate').val())
		let startTime = $('#startTime').val()
		let endTime = $('#endTime').val()
		console.log(startTime)
		
		obj = JSON.stringify({

			workingDate:workingDate,
			startTime:startTime,
		endTime: endTime
		});
	
	
	customAjax({
	      url: '/pharmacy/addWorkingDayForDermatologist/' + idSelected + '/' + email,
	      method: 'POST',
	      data:obj,
		  contentType: 'application/json',
		        success: function(){
		        	alert('Successfully added!')
		        	
				},
			      error: function(){
			       	alert('Error');
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

function formatAMPM(date) {
	  var hours = date.getHours();
	  var minutes = date.getMinutes();
	  var ampm = hours >= 12 ? 'pm' : 'am';
	  hours = hours % 12;
	  hours = hours ? hours : 12; // the hour '0' should be '12'
	  minutes = minutes < 10 ? '0'+minutes : minutes;
	  var strTime = hours + ':' + minutes + ' ' + ampm;
	  return strTime;
	}


