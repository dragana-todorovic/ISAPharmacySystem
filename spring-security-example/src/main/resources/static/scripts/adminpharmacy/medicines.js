var email = localStorage.getItem('email')
$(document).ready(function(e){ 
	
	$("#medicines").click(function () {
		 customAjax({
		      url: '/pharmacy/getAllMedicinesWithQuantity/' + email,
		      method: 'GET',
		      contentType: 'application/json',
		      success: function(data){
		    	  showMedicines(data);
		      },
		      error: function(){
		      }

	 });
	  
	 });
	

});

let izaberiLijekove;
customAjax({
    url: '/pharmacy/getAllMedicineExceptAlreadyExisted/' + email,
    method: 'GET',
    contentType: 'application/json',
    success: function(data){
  	  izaberiLijekove = data
  	  console.log(izaberiLijekove)
    },
    error: function(){
    }

});

let showMedicines = function(data) {
	
	let lijekovi = ""
      for (i in izaberiLijekove) {
          lijekovi += `<div class="item" data-value="` + izaberiLijekove[i].name + `">` + izaberiLijekove[i].name + `</div>`
      }
	
	let temp='';
	for (i in data){
		temp+=`<tr style="display: table;
    width: 100%;
    table-layout: fixed;"><td>`+
		data[i].medicine.code+`</td>
		<td>`+data[i].medicine.name+`</td>	
		<td>`+
		data[i].medicine.producer+`</td>
		<td>`+
		data[i].medicine.shape+`</td>
		<td>`+
		data[i].quantity+`</td>						   
		<td>
		<button id = "`+data[i].id+`" name="obrisiLijek" class="ui red button">
		<i class="close icon"></i>
		Delete medicine
		</button>
		<br>
		<button id = "`+data[i].id+`" name="izmijeniLijek" class="ui secondary button">
		<i class="edit icon"></i>
		Edit medicine
		</button>
			 </td>`;
	}
	temp+=`</tr>`
		
		$("#showData").html(`<table class="ui very padded scrollable table" id="medicineTable" style="width:70%; margin-left:auto; 
			    margin-right:auto; margin-top: 40px;display:block;">
			    
	  <thead style="display: table;
    width: 100%;
    table-layout: fixed;
     width: calc( 100% - 1em )">
	  <tr>
			<th colspan="6">
			 <div class="ui input left">
      <input type="text" placeholder="Search by name..." id="firstNameSearch">
    </div>
    <div class="ui input left">
      <input type="text" placeholder="Search by code..." id="lastNameSearch">
    </div>
			 
			</th>
			
			
			
			</tr>
	    <tr><th>Code</th>
	    <th>Name</th>
	    <th>Producer</th>
	    <th>Shape</th>
	    <th>Quantity</th>
	    <th></th>
	  </tr></thead><tbody style="display: block;
    height: 500px;
    overflow: auto;" id="tabelaLijekova">
	  </tbody>
	  <tfoot style="display: table;
    width: 100%;
    table-layout: fixed;
     width: calc( 100% - 1em )" class="full-width">
			    <tr>
			      <th></th>
			      <th colspan="4">
					   <input id = "addNew" class="ui right floated teal button" type = "button" value = "Add new medicine"></input>	    
			      </th>
			    </tr>
			  </tfoot>
	</table>
	
	<div id="modalniZaNoviLijek" class="ui modal">
	  <i class="close icon"></i>
	  <div class="header">
		Add medicine in pharmacy order
	  </div>
	  <div class="content">

	    <table class="ui basic large table" style="width:50%; margin-left:auto; 
				    margin-right:auto; margin-top: 40px;">
	    <tbody>
				 <tr>
						        	
						                <td>Medicine:</td>
						                <td> <td class="ui input"> <div class="ui selection dropdown">
  <input type="hidden" id="medicineComboAdd">
  <i class="dropdown icon"></i>
  <div class="default text">Choose medicine from codebook...</div>
  <div class="menu">
  `+lijekovi+`
  </div>
</div>
<script>
$('.ui.dropdown')
  .dropdown()
;
</script></td>
						            </tr>
						            <tr>
						            <td>Quantity:</td>
						            <td><td class="ui input"> <input type="number" id="txtQuantityAdd"/></td>

						            </tr>
						           
						           
						        </tbody>
						        
						    </table>
	  </div>
	  <div class="actions">
	    <div class="ui black deny button">
	      Nope
	    </div>
	      <input class="ui right floated positive button" type = "button" value = "Add medicine" id="addMedicineWithQuantity"></input>
				 
	     
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
    We're sorry, you cannot delete that medicine.
  </div>
  <p>That medicine is reserved!
</p></div>
	  </div>
	</div>
	
	
	`);
	function RefreshTable() {
	    $( "#medicineTable" ).load( "adminpharmacy.html #medicineTable" );
	}
	$('#tabelaLijekova').html(temp)
	
	$("#addNew").click(function() {
		
		 $('#modalniZaNoviLijek')
		  .modal('show')
		
	 });
	
	$("#addMedicineWithQuantity").click(function() {
		var medicineName = $('#medicineComboAdd').val()
		var quantity = $('#txtQuantityAdd').val();
		customAjax({
		    url: '/pharmacy/addMedicineWithQuantityInPharmacy/' + email + '/' + medicineName + '/' + quantity,
		    method: 'POST',
		    contentType: 'application/json',
		    success: function(){
		    	refreshujTabeluZaLijekove()
		    },
		    error: function(){
		    	alert("Failed")
		    }

		});
		
	 });
	
	$("button[name=obrisiLijek]").click(function() {
		idSelected = this.id
		customAjax({
		      url: '/pharmacy/deleteMedicineFromPharmacy/' + idSelected + '/' + email,
		      method: 'GET',
			  contentType: 'application/json',
			        success: function(){
			        	refreshujTabeluZaLijekove()
					},
				      error: function(){
				    	  $('#errorDelete')
						  .modal('show')
						
				      }
		    });
		
	 });
}

let refreshujTabeluZaLijekove = function(){
	customAjax({
	      url: '/pharmacy/getAllMedicinesWithQuantity/' + email,
	      method: 'GET',
	      contentType: 'application/json',
	      success: function(data){
	    	  showMedicines(data);
	      },
	      error: function(){
	      }

});
}



