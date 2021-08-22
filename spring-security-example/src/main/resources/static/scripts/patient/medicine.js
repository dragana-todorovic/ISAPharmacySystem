$(document).ready(function() {
	var decoded = parseJwt(localStorage.getItem('jwt'));
	var email = decoded.email
	var trid2;
	var trid;
	var modal = document.getElementById("myModal");
	// When the user clicks on <span> (x), close the modal
	// Get the <span> element that closes the modal
	var span = document.getElementsByClassName("close")[0];
	span.onclick = function() {
	  modal.style.display = "none";
	}
	
	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
	  if (event.target == modal) {
	    modal.style.display = "none";
	  }
	}
	$('a#reserve_medicine').click(function(){
				$('#search-box-medicine').attr('hidden',false);
				$('#edit-profile').attr('hidden', true);
				$('#show').attr('hidden',true);
				$('#pharmacy-details').attr('hidden',true);
				$('#pharmacies_for_derm_appointments').attr('hidden',true);
				$('#reserved_medicine_div').attr('hidden',true);
		     });
	$('a#reserved_medicine').click(function(){
			$('#reserved_medicine_div').attr('hidden',false);
			$('#search-box-medicine').attr('hidden',true);
			$('#edit-profile').attr('hidden', true);
			$('#show').attr('hidden',true);
			$('#pharmacy-details').attr('hidden',true);
			$('#pharmacies_for_derm_appointments').attr('hidden',true);
			customAjax({
		            url: '/medicine/getReservationsByPatient/' + email ,
		            method: 'GET',
		            success: function (data) {
						console.log(data)
		                showReservedMedicine(data);
		            },
		            error: function () {
						console.log("error")
		            }
		
		     });
			
	    });

		$("#searchMedicine").click(function () {
			$('#medicine_show').attr('hidden',true);
		        var let= $("#medicineSearch").val();
		        console.log(let)
		        customAjax({
		            url: '/medicine/searchMedicine/' + let ,
		            method: 'GET',
		            success: function (data) {
						console.log(data)
		                showMedicine(data);
		            },
		            error: function () {
						console.log("error")
		            }
		
		        });
	
	    });
			 $('#medicine_table').on('click','button',function(event){

				if($(event.target).attr('id')=="show-pharamcies"){
					 trid = $(event.target).closest('tr').attr('id');
				    customAjax({
			            url: '/pharmacy/getPharamcyWithMedicine/' + trid ,
			            method: 'GET',
			            success: function (data) {
			                showPharmaciesWithMedicine(data);
			            },
			            error: function () {
							console.log("error")
			            }
		
		        	});
					
				}
		})
		$('#ph_med_table').on('click','button',function(event){
			if($(event.target).attr('id')=="reserve-medicine"){
				trid2 = $(event.target).closest('tr').attr('id')
				modal.style.display = "block"
			}
		})
		
	   $('#send_reservation').click(function(){
		var pharmacyId=trid2;
		var medicineId=trid;
		var dueTo = $('#pickupdate').val();
		console.log(dueTo);
		var decoded = parseJwt(localStorage.getItem('jwt'));
		var patientEmail = decoded.email
		var quantity=$("#med_quantity :selected").text();
		console.log(quantity)

		obj = JSON.stringify({medicineId:medicineId,dueTo:dueTo,patientEmail:patientEmail,quantity:quantity,pharmacyId:pharmacyId});
		customAjax({
	        method:'POST',
	        url:'/medicine/makeReservation',
	        data : obj,
	        contentType: 'application/json',
	        success: function(){
	        	alert("Success making reservation")
				location.href = "patient.html";
			},
			error: function(){
				alert("Error while making reservation")
			}
	            });
		})

function showReservedMedicine(data){
	console.log(new Date());
	let temp='';
	for (i in data){
		console.log(data[i].dueTo)	
		temp+=`<tr id="`+data[i].id+`">
			<td>`+data[i].medicineName+`</td>
			<td>`+data[i].quanity+`</td>
			<td>`+data[i].dueTo+`</td>
			<td>`+data[i].pharmacyName+`</td>
			<td>`+data[i].pharmacyStreet+`</td>
			<td>`+data[i].pharmacyCity+`</td>	
			<td><button id="cancel-reservation" class="ui primary basic button">Cancel reservation</button>
      			</td>
			</tr>`;
	}
	$('#reserved_medicine_table').html(temp);
}

function showMedicine(data){
	let temp='';
	for (i in data){
		temp+=`<tr id="`+data[i].id+`">
			<td>`+data[i].code+`</td>
			<td>`+data[i].name+`</td>
			<td>`+data[i].shape+`</td>
			<td>`+data[i].content+`</td>
			<td>`+data[i].producer+`</td>
			<td>`+data[i].withprescription+`</td>
			<td>`+data[i].notes+`</td>
			 <td><button id="show-pharamcies" class="ui primary basic button">Show pharamcies</button>
      			</td>
			</tr>`;
	}
	$('#medicine_table').html(temp);
	$('#medicine_show').attr('hidden',false);
}

function showPharmaciesWithMedicine(data){
	$('#medicine_show').attr('hidden',true);
	let temp='';
	for (i in data){
		temp+=`<tr id="`+data[i].id+`">
			<td>`+data[i].pharmacyName+`</td>
			<td>`+data[i].street+`</td>
			<td>`+data[i].city+`</td>
			 <td><button id="reserve-medicine" class="ui primary basic button">Reserve Medicine</button>
      			</td>
			</tr>`;
	}
	$('#ph_med_table').html(temp);
	$('#pharamcies_with_medicine_show').attr('hidden',false);
}


});