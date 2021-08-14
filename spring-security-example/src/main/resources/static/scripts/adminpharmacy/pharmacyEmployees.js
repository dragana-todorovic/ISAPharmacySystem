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
	
	$("#pharmacist").click(function () {
  	  customAjax({
		      url: '/pharmacy/getAllPharmacists/' + email,
		      method: 'GET',
		      contentType: 'application/json',
		      success: function(data){	
		    	  showPharmacists(data);
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
		<button id = "`+data[i].id+`" name="prikaziVrijeme" class="ui secondary button">
		<i class="eye icon"></i>
		Show working times
		</button>
			 </td>
		<td>
			<button id = "`+data[i].id+`" name="obrisiDermatologa" class="ui red button">
		<i class="close icon"></i>
		Delete
		</button>
		</td>`;
	}
	temp+=`</tr>`
	
	 $("#showData").html(`<table class="ui very padded selectable table" id="table" style="width:70%; margin-left:auto; 
			    margin-right:auto; margin-top: 40px;">
			    
	  <thead>
	  <tr>
			<th colspan="5">
			 <div class="ui input left">
      <input type="text" placeholder="Search by first name..." id="firstNameSearch">
    </div>
    <div class="ui input left">
      <input type="text" placeholder="Search by last name..." id="lastNameSearch">
    </div>
			 
			</th>
			
			
			
			</tr>
	    <tr><th>First name</th>
	    <th>Last name</th>
	    <th>Avarage rating</th>
	    <th></th>
	    <th>Delete dermatologist</th>
	  </tr></thead><tbody id="tabelaDermatologa">
	  </tbody>
	  <tfoot class="full-width">
			    <tr>
			      <th></th>
			      <th colspan="4">
					   <input id = "addNew" class="ui right floated teal button" type = "button" value = "Add new dermatologist"></input>
			    
			      </th>
			    </tr>
			  </tfoot>
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
										   <tfoot class="full-width"></tfoot>
										   </table>
  </div>
</div>



`)

$("#firstNameSearch").keyup(function () {
        var firstNameSearch = ($('#firstNameSearch').val()).toLowerCase();
       
        $("#table tbody tr").each(function () {
            var firstName = ($('td:eq(0)', this).text()).toLowerCase();
            console.log(firstName)
            if (firstName.includes(firstNameSearch) || firstNameSearch == "") {
                $(this).show()
            } else {
                $(this).hide()
            }
        });
    });
	
	$("#lastNameSearch").keyup(function () {
        var lastNameSearch = ($('#lastNameSearch').val()).toLowerCase();
       
        $("#table tbody tr").each(function () {
            var lastName = ($('td:eq(1)', this).text()).toLowerCase();
            if (lastName.includes(lastNameSearch) || lastNameSearch == "") {
                $(this).show()
            } else {
                $(this).hide()
            }
        });
    });


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
	      async: false,
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

$("button[name=obrisiDermatologa]").click(function() {
	idSelected = this.id
	customAjax({
	      url: '/pharmacy/deleteDermatologist/' + idSelected + '/' + email,
	      method: 'GET',
		  contentType: 'application/json',
		        success: function(){
		        	refreshujTabeluZaDermatologe()
				},
			      error: function(){
			       	alert('Error');
			      }
	    });
	
 });

$('#addNew').click(function() {
	 $('#modalniZaNovogDermatologa')
	  .modal('show')
})

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


let showPharmacists = function(data) {
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
		<button id = "`+data[i].id+`" name="prikaziVrijeme" class="ui secondary button">
		<i class="eye icon"></i>
		Show working times
		</button>
			 </td>
		<td>
			<button id = "`+data[i].id+`" name="obrisiDermatologa" class="ui red button">
		<i class="close icon"></i>
		Delete
		</button>
		</td>`;
	}
	temp+=`</tr>`
	
	 $("#showData").html(`<table class="ui very padded selectable table" id="table" style="width:70%; margin-left:auto; 
			    margin-right:auto; margin-top: 40px;">
			    
	  <thead>
	  <tr>
			<th colspan="5">
			 <div class="ui input left">
      <input type="text" placeholder="Search by first name..." id="firstNameSearch">
    </div>
    <div class="ui input left">
      <input type="text" placeholder="Search by last name..." id="lastNameSearch">
    </div>
			 
			</th>
			
			
			
			</tr>
	    <tr><th>First name</th>
	    <th>Last name</th>
	    <th>Avarage rating</th>
	    <th></th>
	    <th>Delete dermatologist</th>
	  </tr></thead><tbody id="tabelaFarmaceuta">
	  </tbody>
	  <tfoot class="full-width">
			    <tr>
			      <th></th>
			      <th colspan="4">
					   <input id = "addNew" class="ui right floated teal button" type = "button" value = "Add new dermatologist"></input>
			    
			      </th>
			    </tr>
			  </tfoot>
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
										   <tfoot class="full-width"></tfoot>
										   </table>
  </div>
</div>
`)

$("#firstNameSearch").keyup(function () {
        var firstNameSearch = ($('#firstNameSearch').val()).toLowerCase();
       
        $("#table tbody tr").each(function () {
            var firstName = ($('td:eq(0)', this).text()).toLowerCase();
            console.log(firstName)
            if (firstName.includes(firstNameSearch) || firstNameSearch == "") {
                $(this).show()
            } else {
                $(this).hide()
            }
        });
    });
	
	$("#lastNameSearch").keyup(function () {
        var lastNameSearch = ($('#lastNameSearch').val()).toLowerCase();
       
        $("#table tbody tr").each(function () {
            var lastName = ($('td:eq(1)', this).text()).toLowerCase();
            if (lastName.includes(lastNameSearch) || lastNameSearch == "") {
                $(this).show()
            } else {
                $(this).hide()
            }
        });
    });


let idSelected;
$('#tabelaFarmaceuta').html(temp)
$("button[name=dodajVrijeme]").click(function() {
		idSelected = this.id
		 $('#modalniZaNovoVrijeme')
		  .modal('show')
		
	 });


$("button[name=prikaziVrijeme]").click(function() {
	idSelected = this.id
	customAjax({
	      url: '/pharmacy/getAllWorkingTimesPharmacist/' + idSelected + '/' + email,
	      method: 'GET',
	      async: false,
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

$("button[name=obrisiDermatologa]").click(function() {
	idSelected = this.id
	customAjax({
	      url: '/pharmacy/deletePharmacist/' + idSelected + '/' + email,
	      method: 'GET',
		  contentType: 'application/json',
		        success: function(){
		        	refreshujTabeluZaFarmaceute()
				},
			      error: function(){
			       	alert('Error');
			      }
	    });
	
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
	      url: '/pharmacy/addWorkingDayForPharmacist/' + idSelected + '/' + email,
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

let refreshujTabeluZaDermatologe = function(){
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
}

let refreshujTabeluZaFarmaceute = function(){
	customAjax({
	      url: '/pharmacy/getAllPharmacists/' + email,
	      method: 'GET',
	      contentType: 'application/json',
	      success: function(data){	 
	    	  console.log(data)
	    	  showPharmacists(data);
	      },
	      error: function(){
	      }

});
}



