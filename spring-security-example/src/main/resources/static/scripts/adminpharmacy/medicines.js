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

let showMedicines = function(data) {
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
		
		$("#showData").html(`<table class="ui very padded scrollable table" id="table" style="width:70%; margin-left:auto; 
			    margin-right:auto; margin-top: 40px;display:block;">
			    
	  <thead style="display: table;
    width: 100%;
    table-layout: fixed;
     width: calc( 100% - 1em )">
	  <tr>
			<th colspan="5">
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
	</table>`);
	$('#tabelaLijekova').html(temp)
	
}

let modalniZaNoviLijek = function() {
	
}