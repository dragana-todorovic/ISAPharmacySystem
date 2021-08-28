var email = localStorage.getItem('email')

let radnoVrijemeSelektovanog;
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
	
	let sveApoteke;
	customAjax({
	    url: '/pharmacy/getAll',
	    method: 'GET',
	    contentType: 'application/json',
	    success: function(data){
	    	sveApoteke = data
	  	  console.log(sveApoteke)
	    },
	    error: function(){
	    }
	});





$(document).ready(function(e){ 
	
	$("#dermatologist").click(function () {
		console.log("USAO")
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
		    	  console.log(data)
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
	
	  var t = ``;
	    for (ap2 in sveApoteke) {
	        t += (`<input type="checkbox" id="${sveApoteke[ap2].id}" name="apoteke" value="${sveApoteke[ap2].name}">${sveApoteke[ap2].name}</input></br>`);
	    }
	
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
		var apoteke = [];
		
		for(l in data[i].workingTimes) {
			apoteke[l] = data[i].workingTimes[l].pharmacy.name + "\n";
		}
		
		var pomocna = 0
		var averageRating = 0
		for(m in data[i].ratings) {
			pomocna = pomocna + data[i].ratings[m].rating;
		}
		averageRating = pomocna/(data[i].ratings.length)
	
		temp+=`<tr><td class="firstname">`+
		data[i].user.firstName+`</td><td class="soba">`+data[i].user.lastName+`</td><td class="ocjena">${averageRating}</td>
		<td class="ap">${apoteke}</td>								   
		
		<td>
			<button id = "`+data[i].id+`" name="obrisiDermatologa" class="ui red button">
		<i class="close icon"></i>
		Delete
		</button>
		</td><td>
			<button id = "`+data[i].id+`" name="prikaziZahjeve" class="ui secondary button">
		<i class="eye icon"></i>
		Show vacation requests
		</button>
		</td>`;
	}
	temp+=`</tr>`
	
	 $("#showData").html(`<table class="ui very basic padded selectable table" id="table" style="width:70%; margin-left:auto; 
			    margin-right:auto; margin-top: 40px;">
			    
	  <thead>
	  <tr> <th colspan="6">
	  Filter by rating:
	  <div class="ui input left">
               <input type="number" name="filter" placeholder = "Od..." id="odOcjena" min="0" style="width:80px;"/></div>
               <div class="ui input left">
               <input type="number" name="filter" id="doOcjena" placeholder = "Do..." min="0" style="width:80px;"/></div>
           </th>
           </tr><tr>
           <th colspan="6">
             <div id="filterApoteke">
    </br><b>Filtriraj po apoteci:</b><br/>
        ${t}
    </div>
           </th>
            </tr>
	  <tr>
			<th colspan="6">
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
	    <th>Options</th>
	     <th>Vaction requests</th>
	  </tr></thead><tbody id="tabelaDermatologa">
	  </tbody>
	  <tfoot class="full-width">
			    <tr>
			      <th></th>
			      <th colspan="6">
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
	
	
	
<div id="modalniZaPrikazZahtjeva" class="ui modal">
  <i class="close icon"></i>
  <div class="header">
	All requests
  </div>
  <div class="content">

    	<table class="ui black table">
  <thead>
    <tr><th>Start date</th>
    <th>End date</th>
    <th>Status</th>
    <th>Options</th>
  </tr></thead><tbody id="zahtjeviTabela">

  </tbody>
</table>
  </div>
</div>

<div id="modalniZaOdbijanje" class="ui modal">
  <i class="close icon"></i>
  <div class="header">
	Reason why you deline that request
  </div>
  <div class="content">

    	 <div class="ui input left">
      <textArea placeholder="Reason..." id="reason"></textArea>
    </div>
     <div class="actions">
      <input class="ui right floated positive button" type = "button" value = "Send" id="decline"></input>
			 
     
  </div>
  </div>
</div>

<div id="errorAdd" class="ui modal">
	  <i class="close icon"></i>
	  <div class="header">
		Error
	  </div>
	  <div class="content">
	  <div class="ui negative message">
  <div class="header">
    We're sorry, you cannot add that dermatologist.
  </div>
  <p>That dermatologist has already work in another pharmacy!
</p></div>
	  </div>
	</div>

`)

$("#firstNameSearch").keyup(function () {
        var firstNameSearch = ($('#firstNameSearch').val()).toLowerCase();
       
        $("#table tbody tr").each(function () {
            var firstName = ($('td:eq(0)', this).text()).toLowerCase();
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

$("#filterApoteke").click(function () {
	        var imaCekiranih = false;
	        $('input[name="apoteke"]:checked').each(function () {
	            $("#tabelaDermatologa td.ap:not(:contains('" + $(this).val() + "'))").parent().hide();
	            $("#tabelaDermatologa td.ap:contains('" + $(this).val() + "')").parent().show();
	            imaCekiranih = true;
	        });
	        if (!imaCekiranih)
	            $("#tabelaDermatologa td.ap:contains('" + $(this).val() + "')").parent().show();
	    });

 $("#showData").on('change paste keyup','[name=filter]',function (event) {
        var odc=$("#odOcjena").val();
        var doc=$("#doOcjena").val();
        console.log(odc)
        console.log(doc)
       if(odc==""){
      		var ocjenaOd=$("#tabelaDermatologa td.ocjena").parent();
    	}else {
    		var ocjenaOd=$("#tabelaDermatologa td.ocjena").filter(function() { return $(this).text()-odc>=0}).parent();
    	}
    	if(doc==""){
    		var ocjenaDo=$("#tabelaDermatologa td.ocjena").parent();
    	}else {
    		var ocjenaDo=$("#tabelaDermatologa td.ocjena").filter(function() {return $(this).text()-doc<=0}).parent();
    	}
    	ocjenaOd.filter(ocjenaDo).show();
        $("#tabelaDermatologa td.ocjena").parent().not(ocjenaOd.filter(ocjenaDo)).hide();
    });


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

$("button[name=prikaziZahjeve]").click(function() {
	idSelected = this.id
	customAjax({
	      url: '/pharmacy/getHolidayRequests/' + idSelected + '/' + email,
	      method: 'GET',
		  contentType: 'application/json',
		        success: function(data){
					 showRequestsD(idSelected,data)
					  $('#modalniZaPrikazZahtjeva')
					  .modal('show')
					  
				},
			      error: function(){
			      }
	    });
	
 });

$("button[name=izmijeniDermatologa]").click(function() {
	idSelected = this.id	
		        	customAjax({
		      	      url: '/pharmacy/getDermatologistWorkingTimes/' + idSelected + '/' + email,
		      	      method: 'GET',
		      		  contentType: 'application/json',
		      		        success: function(data){
		      		        	editDermatologist(data)
		      					  
		      				},
		      			      error: function(){
		      			      }
		      	    });
		        		
					 
					  $('#modalniZaIzmjenuDermatologa')
					  .modal('show')
					  
	
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
	var workingTimes = []
	$('input[name="radniDan"]:checked').each(function () {
		var day = String(this.id);
		console.log(this.id)
		var startTime = $("#start"+this.id).val();
		var endTime = $("#end"+this.id).val();
		obj = {
			day:day,
			startTime:startTime,
			endTime:endTime
			}
		workingTimes.push(obj)
		
	})
	console.log(workingTimes)
	customAjax({
	    url: '/pharmacy/addDermatologistInPharmacy/' + email,
	    method: 'POST',
	    data: JSON.stringify({dermatologistId:dermatologistId,workingTimes:workingTimes}),
	    contentType: 'application/json',
	    success: function(){
	    	alert("Success added dermatologist in pharmacy!")
	    	 location.href = "adminpharmacy.html"
	    },
	    error: function(){
	    	 $('#errorAdd')
			  .modal('show')
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
	
	
	let temp='';
	for (i in data){
		
		
		var pomocna = 0
		var averageRating = 0
		for(m in data[i].ratings) {
			pomocna = pomocna + data[i].ratings[m].rating;
		}
		averageRating = pomocna/(data[i].ratings.length)
		
		temp+=`<tr><td class="firstname">`+
		data[i].user.firstName+`</td><td class="soba">`+data[i].user.lastName+`</td><td class="ocjena">${averageRating}</td><td>${data[i].workingTimes.pharmacy.name}</td>								   
		
		<td>
			<button id = "`+data[i].id+`" name="obrisiDermatologa" class="ui red button">
		<i class="close icon"></i>
		Delete
		</button>
		</td>
		<td>
			<button id = "`+data[i].id+`" name="prikaziZahjeve" class="ui secondary button">
		<i class="eye icon"></i>
		Show vacation requests
		</button>
		</td>`;
	}
	temp+=`</tr>`
	
	 $("#showData").html(`<table class="ui very basic padded selectable table" id="table" style="width:70%; margin-left:auto; 
			    margin-right:auto; margin-top: 40px;">
			    
	  <thead>
	  <tr> <th colspan="6">
	  Filter by rating:
	  <div class="ui input left">
               <input type="number" name="filter" placeholder = "Od..." id="odOcjena" min="0" style="width:80px;"/></div>
               <div class="ui input left">
               <input type="number" name="filter" id="doOcjena" placeholder = "Do..." min="0" style="width:80px;"/></div>
           </th> </tr>
	  <tr>
			<th colspan="6">
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
	     <th>Vaction requests</th>
	  </tr></thead><tbody id="tabelaFarmaceuta">
	  </tbody>
	  <tfoot class="full-width">
			    <tr>
			      <th></th>
			      <th colspan="6">
					   <input id = "addNew" class="ui right floated teal button" type = "button" value = "Add new pharmacist"></input>
			    
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
    <td colspan="3"><div class="ui fluid input"><input type="text" id="txtFirstName" placeholder="Enter pharmacist's first name..."/>
    </div></td>
</tr>
 <tr>
    <td colspan="3"><div class="ui fluid input"><input type="text" id="txtLastName" placeholder="Enter pharmacist's last name..."/>
    </div></td>
</tr>
 <tr>
    <td colspan="3"><div class="ui fluid input"><input type="text" id="txtEmail" placeholder="Enter pharmacist's email..."/>
    </div></td>
</tr>
 <tr>
    <td colspan="3"><div class="ui fluid input"><input type="text" id="txtPassword" placeholder="Enter pharmacist's password..."/>
    </div></td>
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


	
<div id="modalniZaPrikazZahtjeva" class="ui modal">
  <i class="close icon"></i>
  <div class="header">
	All requests
  </div>
  <div class="content">

    	<table class="ui black table">
  <thead>
    <tr><th>Start date</th>
    <th>End date</th>
    <th>Status</th>
    <th>Options</th>
  </tr></thead><tbody id="zahtjeviTabela">

  </tbody>
</table>
  </div>
</div>

<div id="modalniZaOdbijanje" class="ui modal">
  <i class="close icon"></i>
  <div class="header">
	Reason why you deline that request
  </div>
  <div class="content">

    	 <div class="ui input left">
      <textArea placeholder="Reason..." id="reason"></textArea>
    </div>
     <div class="actions">
      <input class="ui right floated positive button" type = "button" value = "Send" id="decline"></input>
			 
     
  </div>
  </div>
</div>


<div id="errorDeleteP" class="ui modal">
	  <i class="close icon"></i>
	  <div class="header">
		Error
	  </div>
	  <div class="content">
	  <div class="ui negative message">
  <div class="header">
    We're sorry, you cannot delete that pharmacist.
  </div>
  <p>That pharmacist has an counseling scheduled!
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
$('#tabelaFarmaceuta').html(temp)


 $("#showData").on('change paste keyup','[name=filter]',function (event) {
        var odc=$("#odOcjena").val();
        var doc=$("#doOcjena").val();
        console.log(odc)
        console.log(doc)
       if(odc==""){
      		var ocjenaOd=$("#tabelaFarmaceuta td.ocjena").parent();
    	}else {
    		var ocjenaOd=$("#tabelaFarmaceuta td.ocjena").filter(function() { return $(this).text()-odc>=0}).parent();
    	}
    	if(doc==""){
    		var ocjenaDo=$("#tabelaFarmaceuta td.ocjena").parent();
    	}else {
    		var ocjenaDo=$("#tabelaFarmaceuta td.ocjena").filter(function() {return $(this).text()-doc<=0}).parent();
    	}
    	ocjenaOd.filter(ocjenaDo).show();
        $("#tabelaFarmaceuta td.ocjena").parent().not(ocjenaOd.filter(ocjenaDo)).hide();
    });


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
			    	  $('#errorDeleteP')
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
	var firstName = $('#txtFirstName').val();
	var lastName = $('#txtLastName').val();
	var pharmacistEmail = $('#txtEmail').val();
	var password = $('#txtPassword').val();
	var workingTimes = []
	$('input[name="radniDan"]:checked').each(function () {
		var day = String(this.id);
		console.log(this.id)
		var startTime = $("#start"+this.id).val();
		var endTime = $("#end"+this.id).val();
		obj = {
			day:day,
			startTime:startTime,
			endTime:endTime
			}
		workingTimes.push(obj)
		
	})
	customAjax({
	    url: '/pharmacy/addPharmacistInPharmacy/' + email,
	    method: 'POST',
	    data:JSON.stringify({firstName:firstName,lastName:lastName,email:pharmacistEmail,password:password,workingTimes:workingTimes}),
	    contentType: 'application/json',
	    success: function(){
	    		alert("Success added pharmacist in pharmacy!")
				  location.href = "adminpharmacy.html"
	    },
	    error: function(){
	    	alert("Failed")
	    }

	});
});


$("button[name=prikaziZahjeve]").click(function() {
	idSelected = this.id
	customAjax({
	      url: '/pharmacy/getHolidayRequestsP/' + idSelected + '/' + email,
	      method: 'GET',
		  contentType: 'application/json',
		        success: function(data){
					 showRequestsP(idSelected,data)
					  $('#modalniZaPrikazZahtjeva')
					  .modal('show')
					  
				},
			      error: function(){
			      }
	    });
	
 });
}




