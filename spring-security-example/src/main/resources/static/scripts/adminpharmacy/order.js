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


let showOrders = function(data) {
	
		
	
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
		<td>${lijekovi}</td>
					   
	`;
	}
	temp+=`</tr>`
		
		$("#showData").html(`<table class="ui very padded scrollable table" id="medicineTable" style="width:70%; margin-left:auto; 
			    margin-right:auto; margin-top: 40px;display:block;">
			    
	  <thead style="display: table;
    width: 100%;
    table-layout: fixed;
     width: calc( 100% - 1em )">
     <tr>
     <th colspan="4">
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
			      <th colspan="4">
					   <input id = "addNew" class="ui right floated teal button" type = "button" value = "Add new medicine"></input>	    
			      </th>
			    </tr>
			  </tfoot>
	</table>`);
	$('#tabelaPonuda').html(temp)
	
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
}