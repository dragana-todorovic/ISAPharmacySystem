$(document).ready(function(e){
	var email = localStorage.getItem('email')
	 $("#reservedMedicines").click(function () {
	$("#showData").html(`<table class="ui large table" style="width:50%; margin-left:auto; 
    margin-right:auto; margin-top: 40px;">
		        <thead>
		            <tr class="success">
		                <th colspan="2" class = "text-info" style= "text-align:center;">Search reserved medicines</th>
		            </tr>
		        </thead>
		        <tbody>
		              
		            <tr>
		                <td>Search by reservation:</td>		
						<td><div class="ui icon input">
          <input  class="prompt" type="text" id="medicineSearch" placeholder="Reservation number..">
          <i class="search link icon" id="searchMedicines"></i>
          </div></td>		                
		            </tr>
      <th></th>
      <th colspan="2">     
		  
       
      </th>
    </tr>
  </tfoot>
		    </table><div id = "showReservations" class="container4" style = "color : white"></div> <p id="errorInput"> </p>`);
		console.log("Usao u rezervisane lijekove");
			$("#searchMedicines").click(function () {
	        var resNumber= $("#medicineSearch").val();
	        console.log(resNumber)
	 		       customAjax({
	            url: '/pharm/searchReservedMedicines/' + resNumber+"/"+email ,
	            method: 'GET',
	            success: function (data) {
		if(data.length==0){
			$("#errorInput").text("Reservation with that number doesn't exist'!")
	  		$('#errorInput').css('color', 'red');
			$("#showReservations").html(``);
		}else{
			$("#errorInput").text("");
					console.log(data)
			let temp='';
			for (var i in data){
			console.log(data[i].id)
					
			temp+=`<tr><td>`+data[i].numberOfReservation+`</td><td>`+data[i].dueTo+`</td>`+`<td> <input name="start" id="btnTaken` + data[i].id+ `" name = "taken" class="btn btn-primary" type="button" value="Successfully taken medicine"></td>`;
			
			temp+=`</tr>`;
		}
					 $("#showReservations").html(`
				<table id="table" class="ui large table" style="width:50%; margin-left:auto; 
    margin-right:auto; margin-top: 40px;">
		        <thead>
		            <tr class="success">
		                <th colspan="4" class = "text-info" style= "text-align:center;">Rezervations</th>
		            </tr>
						<tr>
						<th >Number of reservation</th>
						<th >Date</th>
						<th></th>
						</tr>
		        </thead>
		       <tbody id="myPatientsList">
	        </tbody>
		        <tfoot class="full-width">
    <tr>

    </tr>
  </tfoot>
		    </table> <p id="er"> </p>`);
$('#myPatientsList').html(temp);
$("input:button[name=start]").click(function () {
	
	var id= this.id;
	idStart= id;
	console.log(this.id)
	customAjax({
		      url: '/pharm/takeReservedMedicine/' + id,
		      method: 'GET',
		      success: function(medicines){
			alert("Successfully taken rezervation")
			location.href= "pharmacist.html";
		},
				error:function(){
					$("#er").text("Reservation number isnot valid. It has been expired!")
	  				$('#er').css('color', 'red');
				}
	              }); 
}); 
	          }  },
	            error: function () {
					console.log("error")
	            }
	
	        });
	
	    });
	});
	
});