var email = localStorage.getItem('email')
const days = ["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY","SATURDAY","SUNDAY"];


let izaberiDermatologa;
	customAjax({
	    url: '/pharmacy/getAllDermatologistsExpectAlreadyExisted/' + email,
	    method: 'GET',
	    contentType: 'application/json',
	    success: function(data){
	  	  izaberiDermatologa = data
	  	  console.log(izaberiDermatologa)
	    },
	    error: function(){
	    }
	});
	
	let izaberiFarmaceuta;
	customAjax({
	    url: '/pharmacy/getAllPharmacistsExpectAlreadyExisted/' + email,
	    method: 'GET',
	    contentType: 'application/json',
	    success: function(data){
	    	izaberiFarmaceuta = data
	  	  console.log(izaberiFarmaceuta)
	    },
	    error: function(){
	    }
	});




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
		data[i].day+`</td><td>`+data[i].startTime+`</td><td>`+data[i].endTime+`</td>`;
	}
	temp+=`</tr>`
		$('#bodyTime').html(temp)
}

let showDermatologists = function(data) {
	
	let radnoVrijeme = '';
	for (i in days) {
		radnoVrijeme += `<tr><td><div class="ui checkbox">
  <input type="checkbox" id = "`+days[i]+`" name="radniDan">
  <label>`+days[i]+`</label></td><td>
    <div class="ui input left icon">
     <div class="ui input left icon">
      <input type="time" value="07:30:00" placeholder="Time" id = "start`+days[i]+`">
  </div>
</div></td><td>
    <div class="ui input left icon">
      <div class="ui input left icon">
      <input type="time" value="16:00:00" placeholder="Time" id = "end`+days[i]+`">
  </div>
</div>
</td></tr>`
	}
	
	let dermatologistCombo = ''
		  for (i in izaberiDermatologa) {
			  dermatologistCombo += `<div class="item" data-value="` + izaberiDermatologa[i].id + `">` + izaberiDermatologa[i].user.firstName + ` `+  izaberiDermatologa[i].user.lastName + `</div>`
	      }
	
	let temp='';
	for (i in data){
		temp+=`<tr><td class="firstname">`+
		data[i].user.firstName+`</td><td class="soba">`+data[i].user.lastName+`</td><td></td><td></td>								   
		
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
	    <th>Pharmacies</th>
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





 <div id="modalniZaNovogDermatologa" class="ui modal">
  <i class="close icon"></i>
  <div class="header">
	Add dermatologist to pharmacy
  </div>
  <div class="content">

    <table class="ui basic large table" style="width:100%; margin-left:auto; 
			    margin-right:auto; margin-top: 40px;">
    <tbody>
    
    <tr>
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
    	`+radnoVrijeme+`
					        </tbody>
					        
					    </table>
  </div>
  <div class="actions">
    <div class="ui black deny button">
      Nope
    </div>
      <input class="ui right floated positive button" type = "button" value = "Add" id="addDermatologist"></input>
			 
     
  </div>
</div>


<div id="errorDelete" class="ui modal">
	  <i class="close icon"></i>
	  <div class="header">
		Error
	  </div>
	  <div class="content">
	  <div class="ui negative message">
  <div class="header">
    We're sorry, you cannot delete that dermatologist.
  </div>
  <p>That dermatologist has an appointment scheduled!
</p></div>
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


$("button[name=obrisiDermatologa]").click(function() {
	idSelected = this.id
	customAjax({
	      url: '/pharmacy/deleteDermatologist/' + idSelected + '/' + email,
	      method: 'GET',
		  contentType: 'application/json',
		        success: function(){
		        	alert("Success deleted dermatologist in from phamacy")
					  location.href = "adminpharmacy.html"
				},
			      error: function(){
			    	  $('#errorDelete')
					  .modal('show')
					
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

$("#addDermatologist").click(function() {
	var dermatologistId = $('#dermatologistCombo').val()
	 
	customAjax({
	    url: '/pharmacy/addDermatologistInPharmacy/' + email + '/' + dermatologistId,
	    method: 'POST',
	    contentType: 'application/json',
	    success: function(){
	    	$('input[name="radniDan"]:checked').each(function () {
	    		//var workingDay = document.getElementById(this.id);
	    		var workingDay = this.id;
	    		var startTime = $("#start"+this.id).val();
	    		var endTime = $("#end"+this.id).val();
	    		obj = JSON.stringify({
	    			startTime:startTime,
	    			endTime:endTime
	    			});
	    		console.log(obj);
	    		customAjax({
		    	    url: '/pharmacy/addWorkingDayDermatologist/' + dermatologistId + '/' + email + '/' + workingDay,
		    	    method: 'POST',
		    	    data : obj,
		    	    contentType: 'application/json',
		    	    success: function(){
		    	    	
		    	    },
		    	    error: function(){
		    	    	console.log(obj)
		    	    	console.log(data)
		    	    	alert("Failed")
		    	    }

		    	});
	    	
	    	});
		      alert("Success added dermatologist in pharmacy")
				  location.href = "adminpharmacy.html"
	    },
	    error: function(){
	    	alert("Failed")
	    }

	});
});
}


let showPharmacists = function(data) {
	
	let radnoVrijeme = '';
	for (i in days) {
		radnoVrijeme += `<tr><td><div class="ui checkbox">
  <input type="checkbox" id = "`+days[i]+`" name="radniDan">
  <label>`+days[i]+`</label></td><td>
    <div class="ui input left icon">
     <div class="ui input left icon">
      <input type="time" value="07:30:00" placeholder="Time" id = "start`+days[i]+`">
  </div>
</div></td><td>
    <div class="ui input left icon">
      <div class="ui input left icon">
      <input type="time" value="16:00:00" placeholder="Time" id = "end`+days[i]+`">
  </div>
</div>
</td></tr>`
	}
	
	let pharmacistCombo = ''
		  for (i in izaberiFarmaceuta) {
			  pharmacistCombo += `<div class="item" data-value="` + izaberiFarmaceuta[i].id + `">` + izaberiFarmaceuta[i].user.firstName + ` `+  izaberiDermatologa[i].user.lastName + `</div>`
	      }
	
	let temp='';
	for (i in data){
		temp+=`<tr><td class="firstname">`+
		data[i].user.firstName+`</td><td class="soba">`+data[i].user.lastName+`</td><td></td><td></td>								   
		
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
	    <th>Pharmacies</th>
	    <th>Delete pharmacist</th>
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





 <div id="modalniZaNovogDermatologa" class="ui modal">
  <i class="close icon"></i>
  <div class="header">
	Add dermatologist to pharmacy
  </div>
  <div class="content">

    <table class="ui basic large table" style="width:100%; margin-left:auto; 
			    margin-right:auto; margin-top: 40px;">
    <tbody>
    
    <tr>
			 <td class="ui input"> <div class="ui selection dropdown">
  <input type="hidden" id="dermatologistCombo">
  <i class="dropdown icon"></i>
  <div class="default text">Choose dermatologist...</div>
  <div class="menu">
  `+pharmacistCombo+`
  </div>
</div>
<script>
$('.ui.dropdown')
  .dropdown()
;
</script></td>
    </tr>	
    	`+radnoVrijeme+`
					        </tbody>
					        
					    </table>
  </div>
  <div class="actions">
    <div class="ui black deny button">
      Nope
    </div>
      <input class="ui right floated positive button" type = "button" value = "Add" id="addDermatologist"></input>
			 
     
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


$("button[name=obrisiDermatologa]").click(function() {
	idSelected = this.id
	customAjax({
	      url: '/pharmacy/deletePharmacist/' + idSelected + '/' + email,
	      method: 'GET',
		  contentType: 'application/json',
		        success: function(){
		        	alert("Success deleted dermatologist in from phamacy")
					  location.href = "adminpharmacy.html"
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

$("#addDermatologist").click(function() {
	var dermatologistId = $('#pharmacistCombo').val()
	 
	customAjax({
	    url: '/pharmacy/addPharmacistInPharmacy/' + email + '/' + dermatologistId,
	    method: 'POST',
	    contentType: 'application/json',
	    success: function(){
	    	$('input[name="radniDan"]:checked').each(function () {
	    		//var workingDay = document.getElementById(this.id);
	    		var workingDay = this.id;
	    		var startTime = $("#start"+this.id).val();
	    		var endTime = $("#end"+this.id).val();
	    		obj = JSON.stringify({
	    			startTime:startTime,
	    			endTime:endTime
	    			});
	    		console.log(obj);
	    		customAjax({
		    	    url: '/pharmacy/addWorkingDayPharmacist/' + dermatologistId + '/' + email + '/' + workingDay,
		    	    method: 'POST',
		    	    data : obj,
		    	    contentType: 'application/json',
		    	    success: function(){
		    	    	
		    	    },
		    	    error: function(){
		    	    	console.log(obj)
		    	    	console.log(data)
		    	    	alert("Failed")
		    	    }

		    	});
	    	
	    	});
		      alert("Success added dermatologist in pharmacy")
				  location.href = "adminpharmacy.html"
	    },
	    error: function(){
	    	alert("Failed")
	    }

	});
});
}




