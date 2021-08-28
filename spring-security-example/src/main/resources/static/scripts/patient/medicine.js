var isShapeSelected = false;
var isTypeSelected = false;
var selectedType ='';
var selectedShape = '';
var file;
var eMedicinesCodes = '';
var eMedicinesCodesQuantity = '';
function readURL(input) {
  if (input.files && input.files[0]) {
    var reader = new FileReader();
     file=input.files[0];
    reader.onload = function(e) {
      $('#blah').attr('src', e.target.result);
    }
    reader.readAsDataURL(input.files[0]); // convert to base64 string
  }

}
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
		     //mili for ePrecription
    $('a#ePrescription').click(function(){
	    $('#qr_code_show').attr('hidden', false);
        $('#search-box-medicine').attr('hidden',true);
        $('#edit-profile').attr('hidden', true);
        $('#show').attr('hidden',true);
        $('#pharmacy-details').attr('hidden',true);
        $('#pharmacies_for_derm_appointments').attr('hidden',true);
        $('#reserved_medicine_div').attr('hidden',true);
     });

     $("#file").change(function() {
        readURL(this);
    });

    $('#submitQrCode').click(function(){
        var image=$('#blah').attr('src');
         var formData = new FormData();
         formData.append("file", file);
                formData.append("type","image");
        // ovdde da decodujemo qr codde
        // procitamo tekst i ocitamo apoteke koje imaju
        customAjax({
            url: '/patient/sendQrCode/' ,
            method: 'POST',
            data : formData,
            processData: false,
            contentType: false,
            success: function (data) {
               console.log(data)
               showPharmaciesForQr(data)
            },
            error: function () {
                console.log("error")
            }

        });
    })

    $('#qr_pharmacy_table').on('click','button',function(event){
        if($(event.target).attr('id')=="buy-medicine"){
        //email
            pharmacyId = $(event.target).closest('tr').attr('id')
            eMedicinesCodesQuantity = $(event.target).parent().parent().children().first().next().next().next().next()[0].innerText;
            eMedicinesCodes =  $(event.target).parent().parent().children().first().next().next().next()[0].innerText;

             var newECodesQuantity = eMedicinesCodesQuantity.split("\n")
             var q= newECodesQuantity.slice(0, -1)

              var newECodes = eMedicinesCodes.split("\n")
              var m = newECodes.slice(0, -1);

            obj = JSON.stringify({
                pharmacyId:pharmacyId,
                patientEmail:email,
                medicineCodes: m,
                medicineCodesQuantity: q,
            });

            customAjax({
            url: '/patient/buyEPrescription',
            method: 'POST',
            data:obj,
            contentType: 'application/json',
            success: function(){
                alert("Success bought medicine list by ePrescription.")
            },
             error: function(){
               alert('Error');
             }
            });
        }
    })

    $("th[name=sortByName]").click(function () {
            if ($(this.getElementsByTagName("span")).attr(`class`) == "glyphicon glyphicon-arrow-down") {
                $(this.getElementsByTagName("span")).removeClass("glyphicon glyphicon-arrow-down");
                $(this.getElementsByTagName("span")).toggleClass("glyphicon glyphicon-arrow-up");
            } else {
                $(this.getElementsByTagName("span")).removeClass("glyphicon glyphicon-up-down");
                $(this.getElementsByTagName("span")).toggleClass("glyphicon glyphicon-arrow-down");
            }
            var table = $(this).parents('table').eq(0)
            var rows = table.find('tr:gt(1)').toArray().sort(comparer($(this).index()))
            this.asc = !this.asc
            if (!this.asc) { rows = rows.reverse() }
            for (var i = 0; i < rows.length; i++) { table.append(rows[i]) }
        });

      $("th[name=sortByAddress]").click(function () {
            console.log('CLICKEED')
                if ($(this.getElementsByTagName("span")).attr(`class`) == "glyphicon glyphicon-arrow-down") {
                    $(this.getElementsByTagName("span")).removeClass("glyphicon glyphicon-arrow-down");
                    $(this.getElementsByTagName("span")).toggleClass("glyphicon glyphicon-arrow-up");
                } else {
                    $(this.getElementsByTagName("span")).removeClass("glyphicon glyphicon-up-down");
                    $(this.getElementsByTagName("span")).toggleClass("glyphicon glyphicon-arrow-down");
                }
                var table = $(this).parents('table').eq(0)
                var rows = table.find('tr:gt(1)').toArray().sort(comparer($(this).index()))
                this.asc = !this.asc
                if (!this.asc) { rows = rows.reverse() }
                for (var i = 0; i < rows.length; i++) { table.append(rows[i]) }
      });
    $("th[name=sortByRating]").click(function () {
            console.log('CLICKEED RATING')
                if ($(this.getElementsByTagName("span")).attr(`class`) == "glyphicon glyphicon-arrow-down") {
                    $(this.getElementsByTagName("span")).removeClass("glyphicon glyphicon-arrow-down");
                    $(this.getElementsByTagName("span")).toggleClass("glyphicon glyphicon-arrow-up");
                } else {
                    $(this.getElementsByTagName("span")).removeClass("glyphicon glyphicon-up-down");
                    $(this.getElementsByTagName("span")).toggleClass("glyphicon glyphicon-arrow-down");
                }
                var table = $(this).parents('table').eq(0)
                var rows = table.find('tr:gt(1)').toArray().sort(comparer($(this).index()))
                this.asc = !this.asc
                if (!this.asc) { rows = rows.reverse() }
                for (var i = 0; i < rows.length; i++) { table.append(rows[i]) }
      });
    $("th[name=sortByPrice]").click(function () {
                console.log('CLICKEED price')
                    if ($(this.getElementsByTagName("span")).attr(`class`) == "glyphicon glyphicon-arrow-down") {
                        $(this.getElementsByTagName("span")).removeClass("glyphicon glyphicon-arrow-down");
                        $(this.getElementsByTagName("span")).toggleClass("glyphicon glyphicon-arrow-up");
                    } else {
                        $(this.getElementsByTagName("span")).removeClass("glyphicon glyphicon-up-down");
                        $(this.getElementsByTagName("span")).toggleClass("glyphicon glyphicon-arrow-down");
                    }
                    var table = $(this).parents('table').eq(0)
                    var rows = table.find('tr:gt(1)').toArray().sort(comparer($(this).index()))
                    this.asc = !this.asc
                    if (!this.asc) { rows = rows.reverse() }
                    for (var i = 0; i < rows.length; i++) { table.append(rows[i]) }
          });
     /// mili end of ePRecription
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
		    var averageRating= 0.0;
        	    var countOfRatings = 0.0;
                if(data[i].ratings.length    >0){
                    for(x in data[i].ratings){
                        countOfRatings += data[i].ratings[x].rating;
                    }
                    averageRating = countOfRatings/data[i].ratings.length ;
                }
        		temp+=`<tr id="`+data[i].id+`">
        			<td>`+data[i].code+`</td>
        			<td>`+data[i].name+`</td>
        			<td>`+averageRating+`</td>
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
		    var averageRating= 0.0;
        	    var countOfRatings = 0.0;
                if(data[i].ratings.length    >0){
                    for(x in data[i].ratings){
                        countOfRatings += data[i].ratings[x].rating;
                    }
                    averageRating = countOfRatings/data[i].ratings.length ;
                }
        		temp+=`<tr id="`+data[i].id+`">
        			<td>`+data[i].code+`</td>
        			<td>`+data[i].name+`</td>
        			<td>`+averageRating+`</td>
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
	    var averageRating= 0.0;
    	    var countOfRatings = 0.0;
            if(data[i].ratings.length    >0){
                for(x in data[i].ratings){
                    countOfRatings += data[i].ratings[x].rating;
                }
                averageRating = countOfRatings/data[i].ratings.length ;
            }
    		temp+=`<tr id="`+data[i].id+`">
    			<td>`+data[i].code+`</td>
    			<td>`+data[i].name+`</td>
    			<td>`+averageRating+`</td>
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
	    var averageRating= 0.0;
	    var countOfRatings = 0.0;
        if(data[i].ratings.length    >0){
            for(x in data[i].ratings){
                countOfRatings += data[i].ratings[x].rating;
            }
            averageRating = countOfRatings/data[i].ratings.length ;
        }
		temp+=`<tr id="`+data[i].id+`">
			<td>`+data[i].code+`</td>
			<td>`+data[i].name+`</td>
			<td>`+averageRating+`</td>
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
//qr_pharmacy_table
function showPharmaciesForQr(data){
	let temp='';
	if(data.length >0)
	{
        for (i in data){
                var medicinesCodes = "";
                var medicinesQuantities = "";
                for(x in data[i].medicineCodes){
                    medicinesCodes += "<b>" +data[i].medicineCodes[x] +"</b></br>"
                }
                for(x in data[i].medicineCodesQuantity){
                    medicinesQuantities += "<b>" +data[i].medicineCodesQuantity[x] +"</b></br>"
                }
            temp+=`<tr id="`+data[i].pharmacyId+`">
                <td>`+data[i].pharmacyName+`</td>
                <td>`+data[i].pharmacyAddress+`</td>
                <td>`+data[i].pharmacyAverageRating+`</td>
                <td id ="` + medicinesCodes + `">`+medicinesCodes+`</td>
                <td id ="` + medicinesQuantities + `">`+medicinesQuantities+`</td>
                <td>`+data[i].totalPrice+`</td>
                 <td><button id="buy-medicine" class="ui primary basic button">Buy</button>
                    </td>
                </tr>`;
        }
    }else{
        temp += `<tr ><td colspan="7">No pharmacy found!</td></tr>`;
    }
	$('#qr_pharmacy_table').html(temp);
	$('#pharamcies_with_medicine_show').attr('hidden',true);
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
function comparer(index) { //ZA SORTIRANJE!
    return function (a, b) {
        var valA = getCellValue(a, index), valB = getCellValue(b, index)
        return $.isNumeric(valA) && $.isNumeric(valB) ? valA - valB : valA.toString().localeCompare(valB)
    }
};
function getCellValue(row, index) {
    return $(row).children('td').eq(index).text()
};


});