var isShapeSelected = false;
var isTypeSelected = false;
var selectedType ='';
var selectedShape = '';
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

    // modalni za detials abotu medicines
    var modalMedicines = document.getElementById('modal-medicines')
    var spanMedicine = document.getElementsByClassName("closeMedicine")[0];
      spanMedicine.onclick = function () {
            modalMedicines.style.display = "none";
        }

    window.onclick = function (event) {
        if (event.target == modalMedicines) {
            modalMedicines.style.display = "none";
        }
    }
    $('input:radio[name="postage"]').change(function(){
            if ($(this).is(':checked') ) {
                selectedType = $(this).val();
                if( selectedType == 'NO_FILTER' ){
                var let= $("#medicineSearch").val();
                   customAjax({
                       url: '/medicine/searchMedicine/' + let ,
                       method: 'GET',
                    success: function (data) {
                        showMedicine(data);
                    },
                    error: function () {
                    }
                });
                }else if(selectedType != '' && selectedShape == '' ) {
                   var let= $("#medicineSearch").val();
                    customAjax({
                        url: '/medicine/searchMedicine/' + let ,
                        method: 'GET',
                        success: function (data) {
                            showMedicineType(data, selectedType);
                        },
                        error: function () {
                        }
                    });
                }else  if(selectedType != '' && selectedShape != ''){
                        if (selectedType == 'NO_FILTER'){
                            //selektuj samo shape
                               var let= $("#medicineSearch").val();
                            customAjax({
                                url: '/medicine/searchMedicine/' + let ,
                                method: 'GET',
                                success: function (data) {
                                    showMedicineShape(data, selectedShape);
                                },
                                error: function () {
                                }
                            });
                        }else if (selectedShape == "NO_FILTER_SHAPE"){
                               var let= $("#medicineSearch").val();
                            //selektuj samo type
                            customAjax({
                                url: '/medicine/searchMedicine/' + let ,
                                method: 'GET',
                                success: function (data) {
                                    showMedicineType(data, selectedType);
                                },
                                error: function () {
                                }
                            });
                        }else{ //nadji presek
                               var let= $("#medicineSearch").val();
                                customAjax({
                                    url: '/medicine/searchMedicine/' + let ,
                                    method: 'GET',
                                    success: function (data) {
                                        showMedicineShapeType(data, selectedShape, selectedType);
                                    },
                                    error: function () {
                                    }
                                });
                    }
                }else if((selectedType != '' && selectedType !='NO_FILTER') &&  selectedShape == 'NO_FILTER_SHAPE'){
                    var let= $("#medicineSearch").val();
                    customAjax({
                        url: '/medicine/searchMedicine/' + let ,
                        method: 'GET',
                        success: function (data) {
                            showMedicineType(data, selectedType);
                        },
                        error: function () {
                        }
                    });
                }
            }
    })

    $('input:radio[name="postageS"]').change(function(){
                 if ($(this).is(':checked') ) {
                    selectedShape = $(this).val();
                    if( selectedShape == 'NO_FILTER_SHAPE' ){
                    var let= $("#medicineSearch").val();
                       customAjax({
                           url: '/medicine/searchMedicine/' + let ,
                           method: 'GET',
                        success: function (data) {
                            showMedicine(data);
                        },
                        error: function () {
                        }
                    });
                    }else if(selectedShape != '' && selectedType == '' ) {
                       var let= $("#medicineSearch").val();
                        customAjax({
                            url: '/medicine/searchMedicine/' + let ,
                            method: 'GET',
                            success: function (data) {
                                showMedicineShape(data, selectedShape);
                            },
                            error: function () {
                            }
                        });
                    }else  if(selectedType != '' && selectedShape != ''){
                       if (selectedType == 'NO_FILTER'){
                               var let= $("#medicineSearch").val();
                                                   //selektuj samo shape
                                                   customAjax({
                                                       url: '/medicine/searchMedicine/' + let ,
                                                       method: 'GET',
                                                       success: function (data) {
                                                           showMedicineShape(data, selectedShape);
                                                       },
                                                       error: function () {
                                                       }
                                                   });
                                               }else if (selectedShape == "NO_FILTER_SHAPE"){
                               var let= $("#medicineSearch").val();
                                                   //selektuj samo type
                                                   customAjax({
                                                       url: '/medicine/searchMedicine/' + let ,
                                                       method: 'GET',
                                                       success: function (data) {
                                                           showMedicineType(data, selectedType);
                                                       },
                                                       error: function () {
                                                       }
                                                   });
                                               }else{ //nadji presek
                                                      var let= $("#medicineSearch").val();
                                                       customAjax({
                                                           url: '/medicine/searchMedicine/' + let ,
                                                           method: 'GET',
                                                           success: function (data) {
                                                               showMedicineShapeType(data, selectedShape, selectedType);
                                                           },
                                                           error: function () {
                                                           }
                                                       });
                                           }
                    }else if((selectedShape != '' && selectedShape !='NO_FILTER_SHAPE') &&  selectedType == 'NO_FILTER'){
                        var let= $("#medicineSearch").val();
                        customAjax({
                            url: '/medicine/searchMedicine/' + let ,
                            method: 'GET',
                            success: function (data) {
                                showMedicineShape(data, selectedShape);
                            },
                            error: function () {
                            }
                        });
                    }
                }

        })
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
				}else if ($(event.target).attr('id')=="show-details"){
				    trid = $(event.target).closest('tr').attr('id');
                    customAjax({
                        url: '/medicine/getMedicineById/' + trid ,
                        method: 'GET',
                        success: function (data) {
                                var substituteMedArray = new Array();
                                for(i in data.substituteMedicineCodes){
                                    substituteMedArray += data.substituteMedicineCodes[i] + " ,"
                                }
                                $('#txtContent').val(data.content)
                                $('#txtProducer').val(data.producer)
                                $("#withPrescriptionTrue").val(data.withprescription)
                                $('#txtSubstituteMedicineCodes').val(substituteMedArray)
                                $('#txtNotes').val(data.notes)
                                $('#txtAdvisedDailyDose').val(data.adviseddailydose)
                                $('#txtContradiction').val(data.contradiction)
                           modalMedicines.style.display = "block";

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
				 elem = document.getElementById("pickupdate")
			    var iso = new Date().toISOString();
			    var minDate = iso.substring(0,iso.length-1);
			    elem.value = minDate
			    elem.min = minDate
			}
		})
		
		$('#reserved_medicine_table').on('click','button',function(event){
			if($(event.target).attr('id')=="cancel-reservation"){
				pom = $(event.target).closest('tr').attr('id')
					    customAjax({
			            url: '/medicine/cancelReservation/' + pom ,
			            method: 'POST',
			            success: function (data) {
			               alert("Sucessfully canceled reservation!");
			            },
			            error: function () {
							console.log("error")
			            }
		
		        	});
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
		
		if(dueTo!=""){
		
		
			obj = JSON.stringify({medicineId:medicineId,dueTo:dueTo,patientEmail:patientEmail,quantity:quantity,pharmacyId:pharmacyId});
			customAjax({
		        method:'POST',
		        url:'/medicine/makeReservation',
		        data : obj,
		        contentType: 'application/json',
		        success: function(){
		        	alert("Success making reservation,check your email!")
					location.href = "patient.html";
				},
				error: function(){
					alert("Not enough medicine on stock,choose less quantity or another pharmacy")
				}
		            });
			}else{
				alert("You have to choose pick up date before making your reservation!");
			}
		})
		

function showReservedMedicine(data){
	let temp='';
	for (i in data){
		if(data[i].isReservationExpired){
			temp+=`<tr id="`+data[i].id+`">
				<td>`+data[i].medicineName+`</td>
				<td>`+data[i].quanity+`</td>
				<td>`+data[i].dueTo+` `+data[i].dueToTime+`</td>
				<td>`+data[i].pharmacyName+`</td>
				<td>`+data[i].pharmacyStreet+`</td>
				<td>`+data[i].pharmacyCity+`</td>	
				<td><button id="cancel-reservation" class="ui primary basic button" disabled>Cancel reservation</button>
	      			</td>
				</tr>`;
			}
			else{
				temp+=`<tr id="`+data[i].id+`">
				<td>`+data[i].medicineName+`</td>
				<td>`+data[i].quanity+`</td>
				<td>`+data[i].dueTo+` `+data[i].dueToTime+`</td>
				<td>`+data[i].pharmacyName+`</td>
				<td>`+data[i].pharmacyStreet+`</td>
				<td>`+data[i].pharmacyCity+`</td>	
				<td><button id="cancel-reservation" class="ui primary basic button" >Cancel reservation</button>
	      			</td>
				</tr>`;
				
			}
	}
	$('#reserved_medicine_table').html(temp);
	$('#ph_con').attr('hidden',true)
}

function showMedicineType(data, selectedType){
	let temp='';
	for (i in data){
        if(data[i].type == selectedType)
        {
		temp+=`<tr id="`+data[i].id+`">
			<td>`+data[i].code+`</td>
			<td>`+data[i].name+`</td>
			<td>`+data[i].shape+`</td>
			<td>`+data[i].type+`</td>
             <td><button id="show-details" class="ui primary basic button">Details</button>
                        </td>
			 <td><button id="show-pharamcies" class="ui primary basic button">Show pharamcies</button>
      			</td>
			</tr>`;
			}
	}
	$('#medicine_table').html(temp);
	$('#medicine_show').attr('hidden',false);
	$('#ph_con').attr('hidden',true)
}
function showMedicineShape(data, selectedShape){
	let temp='';
	for (i in data){
        if(data[i].shape == selectedShape)
        {
		temp+=`<tr id="`+data[i].id+`">
			<td>`+data[i].code+`</td>
			<td>`+data[i].name+`</td>
			<td>`+data[i].shape+`</td>
			<td>`+data[i].type+`</td>
             <td><button id="show-details" class="ui primary basic button">Details</button>
                        </td>
			 <td><button id="show-pharamcies" class="ui primary basic button">Show pharamcies</button>
      			</td>
			</tr>`;
			}
	}
	$('#medicine_table').html(temp);
	$('#medicine_show').attr('hidden',false);
	$('#ph_con').attr('hidden',true)
}

function showMedicineShapeType(data,  selectedShape, selectedType){
	let temp='';
	for (i in data){
        if(data[i].type == selectedType && data[i].shape == selectedShape)
        {
		temp+=`<tr id="`+data[i].id+`">
			<td>`+data[i].code+`</td>
			<td>`+data[i].name+`</td>
			<td>`+data[i].shape+`</td>
			<td>`+data[i].type+`</td>
             <td><button id="show-details" class="ui primary basic button">Details</button>
                        </td>
			 <td><button id="show-pharamcies" class="ui primary basic button">Show pharamcies</button>
      			</td>
			</tr>`;
			}
	}
	$('#medicine_table').html(temp);
	$('#medicine_show').attr('hidden',false);
	$('#ph_con').attr('hidden',true)
}
function showMedicine(data){
	let temp='';
	for (i in data){

		temp+=`<tr id="`+data[i].id+`">
			<td>`+data[i].code+`</td>
			<td>`+data[i].name+`</td>
			<td>`+data[i].shape+`</td>
			<td>`+data[i].type+`</td>
             <td><button id="show-details" class="ui primary basic button">Details</button>
                        </td>
			 <td><button id="show-pharamcies" class="ui primary basic button">Show pharamcies</button>
      			</td>
			</tr>`;
	}
	$('#medicine_table').html(temp);
	$('#medicine_show').attr('hidden',false);
	$('#ph_con').attr('hidden',true)
}

function showPharmaciesWithMedicine(data){
	$('#medicine_show').attr('hidden',true);
	let temp='';
	for (i in data){
		temp+=`<tr id="`+data[i].id+`">
			<td>`+data[i].pharmacyName+`</td>
			<td>`+data[i].street+`</td>
			<td>`+data[i].city+`</td>
			<td>`+data[i].medicinePrice+`</td>
			 <td><button id="reserve-medicine" class="ui primary basic button">Reserve Medicine</button>
      			</td>
			</tr>`;
	}
	$('#ph_med_table').html(temp);
	$('#pharamcies_with_medicine_show').attr('hidden',false);
	$('#ph_con').attr('hidden',true)
}


});