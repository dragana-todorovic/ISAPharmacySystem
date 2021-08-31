var email = localStorage.getItem('email')

$(document).ready(function(e){ 
	
	$("#orders").click(function () {
		 customAjax({
		      url: '/medicineOrder/getAllMedicineOrderByPharmacy/' + email,
		      method: 'GET',
		      contentType: 'application/json',
		      success: function(data){
		    	  console.log(data)
		    	 	showOrders(data)
		      },
		      error: function(){
		      }

	 });
	  
	 });
	

});

let getAllMedicines;
customAjax({
    url: '/medicine/getAllMedicine',
    method: 'GET',
    contentType: 'application/json',
    success: function(data){
  	  console.log(data)
  	  getAllMedicines = data
    },
    error: function(){
    }

});



let showOrders = function(data) {
	
	let sviLijekovi = '';
	for (i in getAllMedicines) {
		sviLijekovi += `<tr style="display: table;
    width: 100%;
    table-layout: fixed;"><td><div class="ui checkbox">
  <input type="checkbox" id = "`+getAllMedicines[i].id+`" name="lijek">
  <label>`+getAllMedicines[i].name+`</label></td><td>
    <div class="ui input small left icon">
     <input type="number" min="1" value="1" placeholder="Quantity..." id="quantity`+getAllMedicines[i].id+`">
</div>
</td></tr>`
	}
	
	let temp='';
	for (i in data){
		var lijekovi = [];
		
		for(l in data[i].medicines) {
			lijekovi[l] = data[i].medicines[l].medicine.name + "\n";
		}
	
		temp+=`<tr style="display: table;
    width: 100%;
    table-layout: fixed;"><td>`+
		data[i].id+`</td>
		<td class="col1">`+data[i].status+`</td>	
		<td>`+
		data[i].timeLimit+`</td>
		<td>${lijekovi}</td>`;
		if(data[i].status == "ON_HOLD") {
		temp += `<td><button id = "`+data[i].id+`" name="prikaziPonude" class="ui secondary button">
		<i class="eye icon"></i>
		Show offers
		</button></td>`
		temp += `<td><button id = "`+data[i].id+`" name="izmijeniNar" class="ui button">
		<i class="edit icon"></i>
		Edit order
		</button><br>
		<button id = "`+data[i].id+`" name="obrisiNar" class="ui red button">
		<i class="close icon"></i>
		Delete order
		</button>
		</td>`
		}else {
		temp+=`<td></td>`
		temp+=`<td></td>`
		}
		
		
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
    <div class="ui selection dropdown">
  <input type="hidden" name="filter" id= "filterStatus">
  <i class="dropdown icon"></i>
  <div class="default text">Filter by status</div>
  <div class="menu">
    <div class="item" data-value="Without filter">Without filter</div>
    <div class="item" data-value="ON_HOLD">On hold</div>
    <div class="item" data-value="PROCESSED">Proccesed</div>
  </div>
</div>
<script>
$('.ui.dropdown')
  .dropdown()
;
</script>
     </th>
     <tr>
	    <th>Serial number</th>
	    <th>Status</th>
	    <th>Time limit</th>
		<th>Medicines</th>
		<th>Offers</th>
		<th>Options</th>
	  
	  </tr></thead><tbody style="display: block;
    height: 500px;
    overflow: auto;" id="tabelaPonuda">
	  </tbody>
	  <tfoot style="display: table;
    width: 100%;
    table-layout: fixed;
     width: calc( 100% - 1em )" class="full-width">
			    <tr>
			      <th></th>
			      <th colspan="6">
					   <input id = "addNew" class="ui right floated teal button" type = "button" value = "Create new order"></input>	    
			      </th>
			    </tr>
			  </tfoot>
	</table>
	
	
	
	
 <div id="modalniZaNovuPonudu" class="ui modal">
  <i class="close icon"></i>
  <div class="header">
	Create order
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
            <input type="text" placeholder="Date of order" id="dateOfOrder">
           
          </div></div>
        </div></td><td>
         <div class="ui input left icon">
     <div class="ui input left icon">
      <input type="time" value="07:30:00" placeholder="Time" id = "timeOfOrder">
       <div class="ui left pointing label">
      Please enter deadline
    </div>
  </div></div>
  </td></tr>
   <tr>
			    <td colspan="2">  <p id="errorAddOrder"></p></td>
			    </tr>
						           
					    </table>
  </div>
  <div class="actions">
    <div class="ui black deny button">
      Nope
    </div>
      <input class="ui right floated positive button" type = "button" value = "Add" id="addOrder"></input>
			 
     
  </div>
</div>


<div id="modalniZaPrikazPonuda" class="ui modal">
  <i class="close icon"></i>
  <div class="header">
	All offers
  </div>
  <div class="content">

    	<table class="ui black table">
  <thead>
    <tr><th>Delevery time</th>
    <th>Price</th>
    <th></th>
  </tr></thead><tbody id="offersTabela">

  </tbody>
</table>
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
    We're sorry, you cannot accept that offer.
  </div>
  <p>Delevery time hasn't pass!
</p></div>
	  </div>
	</div>
	
	
	
	<div id="errorEdit" class="ui modal">
	  <i class="close icon"></i>
	  <div class="header">
		Error
	  </div>
	  <div class="content">
	  <div class="ui negative message">
  <div class="header">
    We're sorry, you cannot edit that order.
  </div>
  <p>That order already has offers!
</p></div>
	  </div>
	</div>
	
	
	<div id="errorDel" class="ui modal">
	  <i class="close icon"></i>
	  <div class="header">
		Error
	  </div>
	  <div class="content">
	  <div class="ui negative message">
  <div class="header">
    We're sorry, you cannot delete that order.
  </div>
  <p>That order has already offers!
</p></div>
	  </div>
	</div>
	
	
	
	<div id="modalniZaIzmjenu" class="ui modal">
  <i class="close icon"></i>
  <div class="header">
	Edit order
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
    	<div class="ui calendar" id="dateEdit">
          <div class="ui input left icon">
            <i class="calendar icon"></i>
            <input type="text" placeholder="Date of order" id="dateOfOrderEdit">
           
          </div></div>
        </div></td><td>
         <div class="ui input left icon">
     <div class="ui input left icon">
      <input type="time" value="07:30:00" placeholder="Time" id = "timeOfOrderEdit">
       <div class="ui left pointing label">
      Please enter deadline
    </div>
  </div></div>
  </td></tr>
					    </table>
  </div>
  <div class="actions">
    <div class="ui black deny button">
      Nope
    </div>
      <input class="ui right floated positive button" type = "button" value = "Edit" id="editOrder"></input>
			 
     
  </div>
</div>
	`);
	
	$('#tabelaPonuda').html(temp)
	var today = new Date();
	 $('#date').calendar({
		  type: 'date',
		  initialDate:  new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1),
		  minDate: new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1)
		});
	 
	 var today = new Date();
	 $('#dateEdit').calendar({
		  type: 'date',
		  initialDate:  new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1),
		  minDate: new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1)
		});
	 
	 
	$('#filterStatus').change(function () { //filter po tipu ap
		if ($(this).val() == "Without filter") {
            $("#tabelaPonuda td.col1").parent().show();
        } else if ($(this).val() == "ON_HOLD") {
            $("#tabelaPonuda td.col1:not(:contains('" + $(this).val() + "'))").parent().hide();
            $("#tabelaPonuda td.col1:contains('" + $(this).val() + "')").parent().show();
        }
        else {
            $("#tabelaPonuda td.col1:not(:contains('" + $(this).val() + "'))").parent().hide();
            $("#tabelaPonuda td.col1:contains('" + $(this).val() + "')").parent().show();
        }
	});
	
	$('#addNew').click(function() {
		 $('#modalniZaNovuPonudu')
		  .modal('show')
	})
	
	
	$('#addOrder').click(function() {
		var date = formatDate($('#dateOfOrder').val())
		var time = $('#timeOfOrder').val()
		var medicines = []
		console.log(medicines)
		$('input[name="lijek"]:checked').each(function () {
			console.log("usao")
    		var medicineId = this.id;
    		var quantity = $("#quantity"+this.id).val();
    		
    		obj = {
    			medicineId:medicineId,
    			quantity:quantity
    			}
    		medicines.push(obj)
    		
    	
    	});
		console.log(medicines)
		customAjax({
    	    url: '/medicineOrder/addMedicineToOffer/' + email,
    	    method: 'POST',
    	    data: JSON.stringify({medicines:medicines,date:date,time:time}),
    	    contentType: 'application/json',
    	    success: function(){
    	    	alert("Success")
    	    	location.href = "adminpharmacy.html"
    	    },
    	    error: function(){
    	    	alert("Sorry! You cannot add new order because you didn't enter date or time, or medicine list is empty")
    	    }

    	});
	})
	
	$("button[name=prikaziPonude]").click(function() {
		var id = this.id;
		 customAjax({
		      url: '/medicineOrder/getAllOffersByOrder/' + id,
		      method: 'GET',
		      contentType: 'application/json',
		      success: function(data){
		    	  prikaziPonude(data)
		    	  $('#modalniZaPrikazPonuda')
				  .modal('show')
				  
		    	  
		      },
		      error: function(){
		      }

	 });
	});
	
	$("button[name=izmijeniNar]").click(function() {
		idSelected = this.id	
		customAjax({
    	      url: '/medicineOrder/orderHaveOffer/' + idSelected,
    	      method: 'GET',
    	      async: false,
    		  contentType: 'application/json',
    		        success: function(){
    		        	customAjax({
			      	      url: '/medicineOrder/getMedicineFromOrder/' + idSelected,
			      	      method: 'GET',
			      	      async: false,
			      		  contentType: 'application/json',
			      		        success: function(data){
			      		        	console.log(data)
			      		        	editOrder(idSelected,data)
			      					  
			      				},
			      			      error: function(){
			      			      }
			      	    });
						  $('#modalniZaIzmjenu')
						  .modal('show')
						  
    					  
    				},
    			      error: function(){
    			    	  $('#errorEdit')
						  .modal('show')
    			      }
    	    });
			        	
		
	 });
	
	
	$("button[name=obrisiNar]").click(function() {
		idSelected = this.id	
		customAjax({
    	      url: '/medicineOrder/orderHaveOffer/' + idSelected,
    	      method: 'GET',
    	      async: false,
    		  contentType: 'application/json',
    		        success: function(){
    		        	customAjax({
			      	      url: '/medicineOrder/deleteMedicineOrder/' + email + '/'+ idSelected,
			      	      method: 'DELETE',
			      	      async: false,
			      		  contentType: 'application/json',
			      		        success: function(){
			      		        	alert("Success deleted medicine order!")
			      		        	location.href = "adminpharmacy.html"
			      					  
			      				},
			      			      error: function(){
			      			      }
			      	    });
						  
    					  
    				},
    			      error: function(){
    			    	  $('#errorDel')
						  .modal('show')
    			      }
    	    });
			        	
		
	 });
}

let prikaziPonude = function(data) {
	let temp = '';
	for(i in data) {
		temp += `<tr>
		<td>`+data[i].deleveryTime+`</td>
		<td>`+data[i].price+`</td>
		<td><button id = "`+data[i].id+`" name="prihvatiPonudu" class="ui positive button">
		<i class="check icon"></i>
		Accept
		</button>
		</td>
		</tr>`;
	}
	$('#offersTabela').html(temp)
	
	$("button[name=prihvatiPonudu]").click(function() {
		var id = this.id;
		console.log(id)
		customAjax({
		      url: '/medicineOrder/acceptOffer/' + id + '/' + email,
		      method: 'POST',
		      contentType: 'application/json',
		      success: function(){
		    	  alert("Success accept!")
		    	  location.href = "adminpharmacy.html"
		    	  
		      },
		      error: function(){
		    	  $('#errorDelete')
				  .modal('show')
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
let pomocna
let editOrder = function(idSelected,lijekovi) {
	pomocna = []
	customAjax({
	    url: '/medicine/getAllMedicine',
	    method: 'GET',
	    async:false,
	    contentType: 'application/json',
	    success: function(data){
	    	pomocna = data
	    },
	    error: function(){
	    }

	});
	let radnoVrijeme = '';

		for(i in lijekovi) {
				radnoVrijeme += `<tr><td><div class="ui checkbox">
					  <input type="checkbox" id = "`+lijekovi[i].medicine.id+`" name="lijekIzm" checked>
					  <label>`+lijekovi[i].medicine.name+`</label></td>
					   <td>
    <div class="ui input small left icon">
     <input type="number" min="1" value="`+lijekovi[i].quantity+`" placeholder="Quantity..." id="quantityEdit`+lijekovi[i].medicine.id+`">
</div>
</td></tr>`
					for(var k = 0; k < pomocna.length; k++) {
						if(pomocna[k].name === lijekovi[i].medicine.name) {
							pomocna.splice(k, 1);
						}
					}   
			}
	for(i in pomocna) {
		radnoVrijeme += `<tr><td><div class="ui checkbox">
			  <input type="checkbox" id = "`+pomocna[i].id+`" name="lijekIzm">
			  <label>`+pomocna[i].name+`</label></td>
			   <td>
    <div class="ui input small left icon">
     <input type="number" min="1" placeholder="Quantity..." id="quantityEdit`+pomocna[i].id+`">
</div>
</td></tr>`;
	}
			
	console.log(pomocna)
	
	$('#editTable').html(radnoVrijeme)
	
	$('#editOrder').click(function() {
		var dateEdit = formatDate($('#dateOfOrderEdit').val())
		var timeEdit = $('#timeOfOrderEdit').val()
		var medicines = []
		$('input[name="lijekIzm"]:checked').each(function () {
		
    		var medicineId = this.id;
    		var quantity = $("#quantityEdit"+this.id).val();
    		
    		obj = {
    			medicineId:medicineId,
    			quantity:quantity
    			}
    		medicines.push(obj)
    		
    	
    	});
		console.log(medicines)
		customAjax({
    	    url: '/medicineOrder/editMedicineOrder/' + email + '/' + idSelected,
    	    method: 'POST',
    	    data: JSON.stringify({medicines:medicines,date:dateEdit,time:timeEdit}),
    	    contentType: 'application/json',
    	    success: function(){
    	    	alert("Success edit medicine order")
    	    	location.href = "adminpharmacy.html"
    	    },
    	    error: function(){
    	    	alert("Failed")
    	    }

    	});
	})
	
}