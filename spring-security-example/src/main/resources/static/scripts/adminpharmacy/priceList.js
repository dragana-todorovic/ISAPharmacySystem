var email = localStorage.getItem('email')
$(document).ready(function(e){ 
	
	$("#medicinePriceList").click(function () {
		var existedIds = []
		 customAjax({
		      url: '/pharmacy/getMedicinePriceList/' + email,
		      method: 'GET',
		      contentType: 'application/json',
		      success: function(data){
		    	  console.log(data)
		    	  for(i in data.medicinePriceList) {
		    		  existedIds.push(data.medicinePriceList[i].medicine.id)
		    	  }
		    	  customAjax({
				      url: '/pharmacy/getMedicinePricesExceptAlreadyExisted/' + email + '/' + existedIds + '/' + data.id,
				      method: 'GET',
				      contentType: 'application/json',
				      success: function(medicinesEx) {
				    	  console.log(medicinesEx)
				    	  
				    	 showPriceListPharmacy(data,medicinesEx)
				      },
				      error: function(){
				      }

			 });
		    	 // showPriceList(data)
		      },
		      error: function(){
		      }

	 });
	 });
	
	$("#appointmentPriceList").click(function () {
		 customAjax({
		      url: '/appointment/getAllAppointmentPrices/' + email,
		      method: 'GET',
		      contentType: 'application/json',
		      success: function(data){
		    	  console.log(data)
		    	  showAllAppointments(data)
		      },
		      error: function(){
		      }

	 });
	 });
	
});

let getMedicineFromPharmacy 

let showPriceList = function(data) {
	getMedicineFromPharmacy = []
	customAjax({
	      url: '/medicine/getMedicineFromPharmacy/' + email,
	      method: 'GET',
	      async: false,
	      contentType: 'application/json',
	      success: function(data){
	    	  getMedicineFromPharmacy = data
	      },
	      error: function(){
	      }

});
	
	let sviLijekovi = '';
	for (i in getMedicineFromPharmacy) {
		sviLijekovi += `<tr style="display: table;
    width: 100%;
    table-layout: fixed;"><td><div class="ui checkbox">
  <input type="checkbox" id = "`+getMedicineFromPharmacy[i].medicine.id+`" name="lijek">
  <label>`+getMedicineFromPharmacy[i].medicine.name+`</label></td><td>
    <div class="ui input small left icon">
     <input type="number" min="1" value="1" placeholder="Price..." id="price`+getMedicineFromPharmacy[i].medicine.id+`">
</div>
</td></tr>`
	}
	
	let temp='';
	for (i in data){
	
		temp+=`<tr style="display: table;
    width: 100%;
    table-layout: fixed;"><td>`+
		data[i].startDate+`</td>
	
		<td>
		<button id = "`+data[i].id+`" name="prikaziLijekove" class="ui button">
		<i class="eye icon"></i>
		Show list
		</button>
		
			 </td></tr>`;
	}
	
	$("#showData").html(`<table class="ui very basic padded scrollable table" id="medicineTable" style="width:70%; margin-left:auto; 
		    margin-right:auto; margin-top: 40px;display:block;">
		    
  <thead style="display: table;
width: 100%;
table-layout: fixed;
 width: calc( 100% - 1em )">
    <tr><th>Start date</th>
    <th>Options</th>
  </tr></thead><tbody style="display: block;
height: 500px;
overflow: auto;" id="tabelaCjenovnika">
  </tbody>
  <tfoot style="display: table;
width: 100%;
table-layout: fixed;
 width: calc( 100% - 1em )" class="full-width">
		    <tr>
		      <th></th>
		      <th colspan="4">
				   <input id = "addNew" class="ui right floated teal button" type = "button" value = "Add new price list"></input>	    
		      </th>
		    </tr>
		  </tfoot>
</table>


<div id="modalniZaPrikazLijekova" class="ui modal">
  <i class="close icon"></i>
  <div class="header">
	All medicines 
  </div>
  <div class="content">

    	<table class="ui black table">
  <thead>
    <tr><th>Medicine name</th>
    <th>Price</th>
    <th></th>
  </tr></thead><tbody id="lijekoviTabela">

  </tbody>
</table>
  </div>
</div>


	
 <div id="modalniZaNovuPonudu" class="ui modal">
  <i class="close icon"></i>
  <div class="header">
	Add new price list
  </div>
  <div class="content">

    <table class="ui basic large table" style="width:100%; margin-left:auto; 
			    margin-right:auto; margin-top: 40px;display:block">
    <tbody style="display: block;
    height: 300px;
    overflow: auto;">
    
    	`+sviLijekovi+`
    	
					        </tbody>
					        
					    </table>
					    <table>
					    <tr><td>
					    <form class="ui fluid form">
  <div class="two field">
    	<div class="ui calendar" id="date">
          <div class="ui input left icon">
            <i class="calendar icon"></i>
            <input type="text" placeholder="Start date" id="startDate">
           
          </div></div>
        </div></td></tr>
					    </table>
  </div>
  <div class="actions">
    <div class="ui black deny button">
      Nope
    </div>
      <input class="ui right floated positive button" type = "button" value = "Add" id="addPriceList"></input>
			 
     
  </div>
</div>


 <div id="modalniZaIzmjenu" class="ui modal">
  <i class="close icon"></i>
  <div class="header">
	Edit price list
  </div>
  <div class="content">

    <table class="ui basic large table" style="width:100%; margin-left:auto; 
			    margin-right:auto; margin-top: 40px;display:block">
    <tbody style="display: block;
    height: 300px;
    overflow: auto;" id = "editTable">
        </tbody>
					        
					    </table>
					    <table>
					    <tr><td>
					    <form class="ui fluid form">
  <div class="two field">
    	<div class="ui calendar" id="date">
          <div class="ui input left icon">
            <i class="calendar icon"></i>
            <input type="text" placeholder="Start date" id="startDateEdit">
           
          </div></div>
        </div></td></tr>
					    </table>
  </div>
  <div class="actions">
    <div class="ui black deny button">
      Nope
    </div>
      <input class="ui right floated positive button" type = "button" value = "Add" id="editPriceList"></input>
			 
     
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
    We're sorry, you cannot add price list.
  </div>
  <p>You already have price list with that date. Please edit the same.
</p></div>
	  </div>
	</div>

`);
	
	$('#tabelaCjenovnika').html(temp)
	
	var today = new Date();
	 $('#date').calendar({
		  type: 'date',
		  initialDate:  new Date(today.getFullYear(), today.getMonth(), today.getDate()),
		  minDate: new Date(today.getFullYear(), today.getMonth(), today.getDate())
		});
	
	$("button[name=prikaziLijekove]").click(function() {
		var id = this.id;
		 customAjax({
		      url: '/pharmacy/getListMedicines/' + id,
		      method: 'GET',
		      contentType: 'application/json',
		      success: function(data){
		    	  prikaziListu(data)
		    	  $('#modalniZaPrikazLijekova')
				  .modal('show')
				  
		    	  
		      },
		      error: function(){
		      }

	 });
	});
	
	
	$("button[name=izmijeniCjenovnik]").click(function() {
		var idSelected = this.id;
		 customAjax({
		      url: '/pharmacy/getListMedicines/' + idSelected,
		      method: 'GET',
		      contentType: 'application/json',
		      success: function(data){
				console.log(data)
				editPriceList(idSelected,data)
		    	  
		      },
		      error: function(){
		      }
		      

	 });
		 $('#modalniZaIzmjenu')
		 .modal('show')
		  
	});
	
	$('#addNew').click(function() {
		 $('#modalniZaNovuPonudu')
		  .modal('show')
	})
	
	$('#addPriceList').click(function(){
		var date = formatDate($('#startDate').val())
		console.log(date)
		var medicines = []
		console.log(medicines)
		$('input[name="lijek"]:checked').each(function () {
			console.log("usao")
    		var medicineId = this.id;
    		var price = $("#price"+this.id).val();
    		
    		obj = {
    			medicineId:medicineId,
    			price:price
    			}
    		medicines.push(obj)
    		
    	
    	});
		customAjax({
    	    url: '/pharmacy/addMedicinePriceToPriceList/' + email,
    	    method: 'POST',
    	    data: JSON.stringify({medicines:medicines,date:date}),
    	    contentType: 'application/json',
    	    success: function(){
    	    	alert("Success")
    	    	location.href = "adminpharmacy.html"
    	    },
    	    error: function(){
    	    	  $('#errorAdd')
				  .modal('show')
    	    }

    	});
	})
	
}

let prikaziListu = function(data) {
	let temp = '';
	for(i in data) {
		temp += `<tr>
		<td>`+data[i].medicine.name+`</td>
		<td>`+data[i].price+`</td>
		</tr>`;
	}
	$('#lijekoviTabela').html(temp)
	
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



let showAllAppointments = function(data) {
	let temp = ''
	for (i in data){
	
		temp+=`<tr style="display: table;
    width: 100%;
    table-layout: fixed;"><td>`+
		data[i].appoitment.startDateTime+`</td>
		<td>`+
		data[i].appoitment.dermatologist.user.firstName + ` ` +data[i].appoitment.dermatologist.user.lastName +`</td>
		<td>`+
		data[i].price+ `</td>
	</tr>`;
	}
	
	$("#showData").html(`<table class="ui very basic padded scrollable table" id="medicineTable" style="width:70%; margin-left:auto; 
		    margin-right:auto; margin-top: 40px;display:block;">
		    
  <thead style="display: table;
width: 100%;
table-layout: fixed;
 width: calc( 100% - 1em )">
    <tr><th>Start date and time</th>
    <th>Dermatologist</th>
    <th>Price</th>
  </tr></thead><tbody style="display: block;
height: 500px;
overflow: auto;" id="tabelaPregleda">
  </tbody>
  <tfoot style="display: table;
width: 100%;
table-layout: fixed;
 width: calc( 100% - 1em )" class="full-width">
		   
		  </tfoot>
</table>


`);
	
	$('#tabelaPregleda').html(temp)
	
	
}


let showPriceListPharmacy = function(data,medicineEx) {
	
	let temp = '';

		for(i in data.medicinePriceList) {
				temp += `<tr><td><div class="ui checkbox">
					  <input type="checkbox" id = "`+data.medicinePriceList[i].medicine.id+`" name="lijekIzm" checked>
					  <label>`+data.medicinePriceList[i].medicine.name+`</label></td>
					   <td>
    <div class="ui input small left icon">
     <input type="number" min="1" value="`+data.medicinePriceList[i].price+`" placeholder="Price..." id="price`+data.medicinePriceList[i].medicine.id+`">
</div>
</td></tr>`
		}
	for(i in medicineEx) {
		temp += `<tr><td><div class="ui checkbox">
			  <input type="checkbox" id = "`+medicineEx[i].id+`" name="lijekIzm">
			  <label>`+medicineEx[i].name+`</label></td>
			   <td>
    <div class="ui input small left icon">
     <input type="number" min="1" placeholder="Price..." id="price`+medicineEx[i].id+`">
</div>
</td></tr>`;
	}
	
	 $("#showData").html(`<table class="ui basic large table" style="width:50%; margin-left:auto; 
			    margin-right:auto; margin-top: 40px;">
					        <thead>
					            <tr class="success">
					                <th colspan="2" class = "text-info" style= "text-align:center;">Price list</th>
					            </tr>
					        </thead>
					        <tbody id = "bodyMedicine">
					          
					        </tbody>
					     
			   
					    <tr><td>
					    <form class="ui fluid form">
  <div class="two field">
    	<div class="ui calendar" id="date">
          <div class="ui input left icon">
            <i class="calendar icon"></i>
            <input type="text" placeholder="Start date" value="`+data.startDate+`" id="startDate">
           
          </div></div>
        </div></td></tr>
           <tfoot class="full-width">
         <tr>
			      <th></th>
			      <th colspan="2">
					   <input id = "addPriceList" class="ui right floated positive button" type = "button" value = "Edit price list"></input>
			    
			      </th>
			    </tr>
			  </tfoot>
					    </table> 
					    
					    
					   `)
					   $('#bodyMedicine').html(temp)
					   
						var today = new Date();
	 $('#date').calendar({
		  type: 'date',
		  initialDate:  new Date(today.getFullYear(), today.getMonth(), today.getDate()),
		  minDate: new Date(today.getFullYear(), today.getMonth(), today.getDate())
		});
	 
	 $('#addPriceList').click(function(){
			var date = formatDate($('#startDate').val())
			var medicines = []
			console.log(medicines)
			$('input[name="lijekIzm"]:checked').each(function () {
	    		var medicineId = this.id;
	    		var price = $("#price"+this.id).val();
	    		
	    		obj = {
	    			medicineId:medicineId,
	    			price:price
	    			}
	    		medicines.push(obj)
	    		
	    	
	    	});
			customAjax({
	    	    url: '/pharmacy/addMedicinePriceToPriceList/' + email,
	    	    method: 'POST',
	    	    data: JSON.stringify({medicines:medicines,date:date}),
	    	    contentType: 'application/json',
	    	    success: function(){
	    	    	alert("Success")
	    	    	location.href = "adminpharmacy.html"
	    	    },
	    	    error: function(){
	    	    	 alert("Sorry! You cannot add price list because you didn't enter date or medicine list is empty")
	    	    }

	    	});
		})
	
}

