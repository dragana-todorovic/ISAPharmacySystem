var email = localStorage.getItem('email')
$(document).ready(function(e){ 
	
	$("#medicinePriceList").click(function () {
		 customAjax({
		      url: '/pharmacy/getMedicinePriceList/' + email,
		      method: 'GET',
		      contentType: 'application/json',
		      success: function(data){
		    	  console.log(data)
		    	  showPriceList(data)
		      },
		      error: function(){
		      }

	 });
	 });
	
});

let showPriceList = function(data) {
	
	let sviLijekovi = '';
	for (i in getAllMedicines) {
		sviLijekovi += `<tr style="display: table;
    width: 100%;
    table-layout: fixed;"><td><div class="ui checkbox">
  <input type="checkbox" id = "`+getAllMedicines[i].id+`" name="lijek">
  <label>`+getAllMedicines[i].name+`</label></td><td>
    <div class="ui input small left icon">
     <input type="number" min="1" value="1" placeholder="Price..." id="price`+getAllMedicines[i].id+`">
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
		<br>
		<button id = "`+data[i].id+`" name="izmijeniCjenovnik" class="ui secondary button">
		<i class="edit icon"></i>
		Edit price list
		</button>
			 </td></tr>`;
	}
	
	$("#showData").html(`<table class="ui very padded scrollable table" id="medicineTable" style="width:70%; margin-left:auto; 
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
	All offers
  </div>
  <div class="content">

    	<table class="ui black table">
  <thead>
    <tr><th>Delevery time</th>
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
	
	$('#addNew').click(function() {
		 $('#modalniZaNovuPonudu')
		  .modal('show')
	})
	
	$('#addPriceList').click(function(){
		var date = formatDate($('#startDate').val())
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
    	    	alert("Failed")
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
