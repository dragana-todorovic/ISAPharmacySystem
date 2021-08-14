$(document).ready(function() {
	var trid;
	$('a#all_pharmacies').click(function(){
				customAjax({
		        method:'GET',
		        url:'/pharmacy/getAll',
		        contentType: 'application/json',
		        success: function(data){
					showPharmacies(data)
				},
				error: function(){
					console.log("error");
				}
		     });

	});
	 $('#pharmacies_tableBody').on('click','button',function(event){
			trid = $(event.target).closest('tr').attr('id');
			customAjax({
				method:'GET',
		        url:'/pharmacy/getPharmacyById/' + trid,
		        contentType: 'application/json',
	    		success: function(data) { 	
					showPharmacy(data)
	    		},
	    		error:function(message){
					alert("Error")
	    			console.log(message)
	    		}
	    	});	
	})
	$('#appointments_derm').on('click',function(){
		alert(trid);
				customAjax({
				method:'GET',
		        url:'/appointment/getAllByPharmacyId/' + trid,
		        contentType: 'application/json',
	    		success: function(data) { 	
					alert(success);
	    		},
	    		error:function(message){
					alert("Error")
	    			console.log(message)
	    		}
	    	});	
	})
function showPharmacies(data){
	$('#pharmacy-details').attr('hidden',true);
	let temp='';
	for (i in data){
		temp+=`<tr id="`+data[i].id+`">
			<td >`+data[i].name+`</td>
			<td>`+data[i].address.city+`</td>
			<td>`+data[i].address.street+`</td>
			<td>`+data[i].description+`</td>
			<td><button  class="ui primary basic button">Details</button>
			</tr>`;
	}
	$('#pharmacies_tableBody').html(temp);
	$('#pharmacies_for_derm_appointments').attr('hidden',false);	
	$('#edit-profile').attr('hidden', true);
	$('#show').attr('hidden',true);
}
function showPharmacy(data){
	$('#pharmacy-name').text("Name:"+data.name);
	$('#pharmacy-description').text("Description:"+data.description);
	$('#pharmacy-city').text("City:"+data.address.city);
	$('#pharmacy-address').text("Street:"+data.address.street);
	$('#pharmacy-details').attr('hidden',false);
	$('#pharmacies_for_derm_appointments').attr('hidden',true);
	
}
function showDermatologyAppointments(data){
	console.log(data);
		let temp='';
	for (i in data){
		temp+=`<tr id="`+data[i].id+`">
			<td>`+data[i].dermatologist_id+`</td>
			<td>`+data[i].dermatologist_id+`</td>
			<td>`+data[i].start_date_time,+`</td>
			<td>`+data[i].start_date_time,+`</td>
			</tr>`;
	}
	$('#appointments_dermatology_table').html(temp);
}

});