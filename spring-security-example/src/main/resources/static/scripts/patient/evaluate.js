$(document).ready(function() {
	var pom;
	var modalEv = document.getElementById("modal_for_evaluation");
	// When the user clicks on <span> (x), close the modal
	// Get the <span> element that closes the modal
	var span = document.getElementsByClassName("close_evaluation")[0];
	span.onclick = function() {
	  modalEv.style.display = "none";
	}
	
	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
	  if (event.target == modalEv) {
	    modalEv.style.display = "none";
	  }
}
	var email = localStorage.getItem('email')
	$('a#ev_derm').click(function(){
				pom="derm";
				customAjax({
		        method:'GET',
		        url:'/derm/getAllDermPatientCanEvaluate/'+email,
		        contentType: 'application/json',
		        success: function(data){
					show(data)
				},
				error: function(){
					console.log("error");
				}
		     });

	});
		$('a#ev_med').click(function(){
				pom="med";
				customAjax({
		        method:'GET',
		        url:'/medicine/getAllMedicinePatientCanEvaluate/'+email,
		        contentType: 'application/json',
		        success: function(data){
					show(data)
				},
				error: function(){
					console.log("error");
				}
		     });

	});
		$('a#ev_pharmacist').click(function(){
				pom="pharmacist"
				customAjax({
		        method:'GET',
		        url:'/pharm/getAllPharmacistsPatientCanEvaluate/'+email,
		        contentType: 'application/json',
		        success: function(data){
					show(data)
				},
				error: function(){
					console.log("error");
				}
		     });

	});
		$('a#ev_pharmacy').click(function(){
				pom="derm";
				customAjax({
		        method:'GET',
		        url:'/pharmacy/getAllPharmaciesPatientCanEvaluate/'+email,
		        contentType: 'application/json',
		        success: function(data){
					show(data)
				},
				error: function(){
					console.log("error");
				}
		     });

	});
	$('#ev_table').on('click','button',function(event){
		console.log(pom);
		if($(event.target).attr('id')=="add_rating"){
			id = $(event.target).closest('tr').attr('id')
			modalEv.style.display = "block"
		}

	})
		$('#send_rating').click(function(){
		var decoded = parseJwt(localStorage.getItem('jwt'));
		var patientEmail = decoded.email
		var rating=$("#rating :selected").text();
		console.log(id);
		console.log(rating)
		console.log(patientEmail);	
		if(pom=="derm"){
		    customAjax({
	            url: '/derm/changeRating/' + rating + '/'+ email + '/' + id ,
	            method: 'POST',
	            success: function () {
	               alert("Sucessfully changed dermatologists rating!");
	            },
	            error: function () {
					console.log("error")
	            }
	
	    	});
		}else if(pom=="pharmacist"){
			 customAjax({
	            url: '/pharm/changeRating/' + rating + '/'+ email + '/' +id ,
	            method: 'POST',
	            success: function () {
	               alert("Sucessfully changed pharmacists rating!");
	            },
	            error: function () {
					console.log("error")
	            }
	
	    	});
		}
		else if(pom=="med"){
			 customAjax({
	            url: '/medicine/changeRating/' + rating + '/'+ email + '/' +id ,
	            method: 'POST',
	            success: function () {
	               alert("Sucessfully changed medicines rating!");
	            },
	            error: function () {
					console.log("error")
	            }
	
	    	});
		}
		
				
		})
	

	
})
function show(data){
	let temp='';
	for (i in data){
		if(data[i].patientsGrade==0){
			temp+=`<tr id="`+data[i].id+`">
			<td >`+data[i].firstName+` `+data[i].lastName+`</td>
			<td>x</td>
			<td><button id="add_rating" class="ui primary basic button">Add/Change rating</button>
			</tr>`;
		}else{
			temp+=`<tr id="`+data[i].id+`">
				<td >`+data[i].firstName+` `+data[i].lastName+`</td>
				<td>`+data[i].patientsGrade+`</td>
				<td><button id="add_rating" class="ui primary basic button">Add/Change rating</button>
				</tr>`;
				}
	}
	
	$('#ev_body').html(temp);
	$('#eval').attr('hidden',false);	
	$('#pharmacy-details').attr('hidden',true);		
	$('#all_pharmacies_show').attr('hidden',true);	
	$('#edit-profile').attr('hidden', true);
	$('#show').attr('hidden',true);
	$('#my_derm_appointments').attr('hidden',true);
	$('#ph_av_con').attr('hidden',true)
	$('#ph_con').attr('hidden',true)
}