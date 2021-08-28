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
	
	$("#requests").click(function () {
		 customAjax({
		      url: '/pharmacy/getRequestForMedicineAvailability/' + email,
		      method: 'GET',
		      contentType: 'application/json',
		      success: function(data){
		    	  showRequests(data)
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
		
		$("#showData").html(`<table class="ui very basic padded scrollable table" id="medicineTable" style="width:70%; margin-left:auto; 
			    margin-right:auto; margin-top: 40px;display:block;">
			    
	  <thead style="display: table;
    width: 100%;
    table-layout: fixed;
     width: calc( 100% - 1em )">
	  <tr>
			<th colspan="6">
			 <div class="ui input left">
      <input type="text" placeholder="Search by name..." id="nameSearch">
    </div>
    <div class="ui input left">
      <input type="text" placeholder="Search by code..." id="codeSearch">
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
						                <td class="ui input"> <div class="ui selection dropdown">
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
						            <td class="ui input"> <input type="number" id="txtQuantityAdd"/></td>

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
	
	<div id="modalniZaIzmjenuLijeka" class="ui modal">
	  <i class="close icon"></i>
	  <div class="header">
		Edit medicine
	  </div>
	 <div class="content">

	    <table class="ui basic large table" style="width:50%; margin-left:auto; 
				    margin-right:auto; margin-top: 40px;">
	    <tbody>
						            <tr>
						            <td>Quantity:</td>
						           <td class="ui input"> <input type="number" id="txtQuantityEdit"></td>

						            </tr>
						           
						           
						        </tbody>
						        
						    </table>
	  </div>
	  <div class="actions">
	    <div class="ui black deny button">
	      Nope
	    </div>
	      <input class="ui right floated positive button" type = "button" value = "Edit medicine" id="editMedicine"></input>
				 
	     
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
	
	
	<div id="successAdd" class="ui modal">
	  <i class="close icon"></i>
	  <div class="header">
		Error
	  </div>
	  <div class="content">
	  <div class="ui positive message">
  <div class="header">
   Success.
  </div>
  <p>Medicine is added in pharmacy offer!
</p></div>
	  </div>
	</div>
	
	`);
	
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
		    	//refreshujTabeluZaLijekove()
		      alert("Success added medicine in pharmacy offer")
				  location.href = "adminpharmacy.html"
		    },
		    error: function(){
		    	alert("Failed")
		    }

		});
		
	 });
	
	$("#nameSearch").keyup(function () {
        var firstNameSearch = ($('#nameSearch').val()).toLowerCase();
       
        $("#medicineTable tbody tr").each(function () {
            var name = ($('td:eq(1)', this).text()).toLowerCase();
            if (name.includes(firstNameSearch) || firstNameSearch == "") {
                $(this).show()
            } else {
                $(this).hide()
            }
        });
    });
	
	$("#codeSearch").keyup(function () {
        var codeSearch = ($('#codeSearch').val()).toLowerCase();
       
        $("#medicineTable tbody tr").each(function () {
            var code = ($('td:eq(0)', this).text()).toLowerCase();
            if (code.includes(codeSearch) || codeSearch == "") {
                $(this).show()
            } else {
                $(this).hide()
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
			        	alert("Success deleted medicine from pharmacy offer")
						  location.href = "adminpharmacy.html"
			        	//refreshujTabeluZaLijekove()
					},
				      error: function(){
				    	  $('#errorDelete')
						  .modal('show')
						
				      }
		    });
		
	 });
	
	$("button[name=izmijeniLijek]").click(function() {
		idSelected = this.id
		 $('#modalniZaIzmjenuLijeka')
		  .modal('show')
		  editMedicine(idSelected)
		
	 });
	
	let editMedicine = function(idSelected) {
		
		$('#editMedicine').click(function() {
			var quantity = $('#txtQuantityEdit').val()
			console.log(quantity)
			customAjax({
			    url: '/pharmacy/editMedicineWithQuantityInPharmacy/' + email + '/' + idSelected + '/' + quantity,
			    method: 'POST',
			    contentType: 'application/json',
			    success: function(){
			      alert("Success edit medicine")
					  location.href = "adminpharmacy.html"
			    },
			    error: function(){
			    	alert("Failed")
			    }

			});
		})
	}
}

let showRequests = function(data) {
	let temp='';
	for (i in data){
		temp+=`<tr style="display: table;
    width: 100%;
    table-layout: fixed;"><td>`+
		data[i].createdAt+`</td>
		<td>`+data[i].medicineWithQuantity.medicine.name+`</td>	
		<td>`+
		+data[i].medicineWithQuantity.quantity+`</td>
		`;
	}
	temp+=`</tr>`
		
		$("#showData").html(`<table class="ui very basic padded scrollable table" id="medicineTable" style="width:70%; margin-left:auto; 
			    margin-right:auto; margin-top: 40px;display:block;">
			    
	  <thead style="display: table;
    width: 100%;
    table-layout: fixed;
     width: calc( 100% - 1em )">
	  
	    <tr>
	    <th>Created at</th>
	    <th>Medicine name</th>
	    <th>Medicine quantity</th>
	   
	  </tr></thead><tbody style="display: block;
    height: 500px;
    overflow: auto;" id="tabelaLijekova">
	  </tbody>
	  <tfoot style="display: table;
    width: 100%;
    table-layout: fixed;
     width: calc( 100% - 1em )" class="full-width">
			   
			  </tfoot>
	</table>`)
	
	$('#tabelaLijekova').html(temp)
}



/*let refreshujTabeluZaLijekove = function(){
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
}*/



