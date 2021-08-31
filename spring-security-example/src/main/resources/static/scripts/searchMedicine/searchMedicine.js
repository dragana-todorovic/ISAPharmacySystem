function drawTableSearchMedicine(data) {
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
  			<td class="med_shape">`+data[i].shape+`</td>
  			<td class="med_type">`+data[i].type+`</td>
               <td><button id="show-search-details" class="ui primary basic button">Details</button>
                          </td>
  			 <td><button id="show-search-pharamcies" class="ui primary basic button">Show pharamcies</button>
        			</td>
  			</tr>`;
  	}
    $('#searchMedicineTable').html(temp);

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
			<td class="med_type">`+data[i].type+`</td>
                   <td><button id="show-search-details" class="ui primary basic button">Details</button>
                              </td>
      			 <td><button id="show-search-pharamcies" class="ui primary basic button">Show pharamcies</button>
            			</td>
      			</tr>`;
      	}
}
        $('#searchMedicineTable').html(temp);
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
			<td >`+data[i].type+`</td>
                   <td><button id="show-search-details" class="ui primary basic button">Details</button>
                              </td>
      			 <td><button id="show-search-pharamcies" class="ui primary basic button">Show pharamcies</button>
            			</td>
      			</tr>`;
      	}
}
        $('#searchMedicineTable').html(temp);
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
               <td><button id="show-search-details" class="ui primary basic button">Details</button>
                                       </td>
               			 <td><button id="show-search-pharamcies" class="ui primary basic button">Show pharamcies</button>
                     			</td>
			</tr>`;
			}
	}
   $('#searchMedicineTable').html(temp);
}
function showSearchPharmacies(data){
	let temp='';
	for (i in data){
		temp+=`<tr id="`+data[i].id+`">
			<td>`+data[i].pharmacyName+`</td>
			<td>`+data[i].street+`</td>
			<td>`+data[i].city+`</td>
			<td>`+data[i].medicinePrice+`</td>
			</tr>`;
	}
	$('#searchPharmacyTable').html(temp);
}

var isShapeSelected = false;
var isTypeSelected = false;
var selectedType ='';
var selectedShape = '';
$(document).ready(function(e){

      customAjax({
          url: '/auth/getAllMedicine',
          method: 'GET',
            contentType: 'application/json',
            success: function (data) {
                drawTableSearchMedicine(data);
            },
            error: function (message) {
                //alert("Failed")
            }
    })


    $("#nameSearchMedicine").keyup(function () {
        var firstNameSearch2 = ($('#nameSearchMedicine').val()).toLowerCase();

        $("#searchMedTable tbody tr").each(function () {
            var name = ($('td:eq(1)', this).text()).toLowerCase();
            if (name.includes(firstNameSearch2) || firstNameSearch2 == "") {
                $(this).show()
            } else {
                $(this).hide()
            }
        });
    });

    // modalni za detials abotu medicines
    var modalSearchMedicines = document.getElementById('modal-search-medicines')
    var spanSearchMedicine = document.getElementsByClassName("closeSearchMedicine")[0];
      spanSearchMedicine.onclick = function () {
            modalSearchMedicines.style.display = "none";
        }

    window.onclick = function (event) {
        if (event.target == modalSearchMedicines) {
            modalSearchMedicines.style.display = "none";
        }
    }

     // modalni za show pharmacies
        var modalSearchPharmacy = document.getElementById('modal-search-pharmacy')
        var spanSearchPharmacy = document.getElementsByClassName("closeSearchPharmacy")[0];
          spanSearchPharmacy.onclick = function () {
                modalSearchPharmacy.style.display = "none";
            }

        window.onclick = function (event) {
            if (event.target == modalSearchPharmacy) {
                modalSearchPharmacy.style.display = "none";
            }
        }

    $('#searchMedicineTable').on('click','button',function(event){
        if($(event.target).attr('id')=="show-search-pharamcies"){
             trid = $(event.target).closest('tr').attr('id');
            customAjax({
                url: '/auth/getPharamcyWithMedicine/' + trid ,
                method: 'GET',
                success: function (data) {
                    showSearchPharmacies(data);
                    modalSearchPharmacy.style.display = "block";
                },
                error: function () {
                    console.log("error")
                }

            });
        }else if ($(event.target).attr('id')=="show-search-details"){
            trid = $(event.target).closest('tr').attr('id');
            customAjax({
                url: '/auth/getMedicineById/' + trid ,
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
                        modalSearchMedicines.style.display = "block";
                },
                error: function () {
                    console.log("error")
                }

            });
        }
    })

    // filtriranje po tipu leka i obliku leka
  /*  $("#filterMedicineType").click(function () {
    	        var imaCekiranih = false;
    	        $('input[name="postage"]:checked').each(function () {
    	            selectedSearchType = $(this).val()
    	            if($(this).val() == 'NO_FILTER'){
    	                  $("#searchMedTable").show();
    	            }else {
                        $("#searchMedTable td.med_type:not(:contains('" + $(this).val() + "'))").parent().hide();
                        $("#searchMedTable td.med_type:contains('" + $(this).val() + "')").parent().show();
                        imaCekiranih = true;
    	            }
    	        });
    	        if (!imaCekiranih)
    	            $("#searchMedTable td.med_type:contains('" + $(this).val() + "')").parent().show();
    	    });

            $("#filterMedicineShape").click(function () {
    	        var imaCekiranih = false;
    	        $('input[name="medicineShape"]:checked').each(function () {
    	            selectedSearchShape =  $(this).val()
    	            if($(this).val() == 'NO_FILTER'){
                          $("#searchMedTable").show();
                    }else {
    	            $("#searchMedTable td.med_shape:not(:contains('" + $(this).val() + "'))").parent().hide();
    	            $("#searchMedTable td.med_shape:contains('" + $(this).val() + "')").parent().show();
    	            imaCekiranih = true;
    	            }
    	        });
    	        if (!imaCekiranih){
    	            $("#searchMedTable td.med_shape:contains('" + $(this).val() + "')").parent().show();
    	        }
    	    });*/

        $('input:radio[name="postage"]').change(function(){
            if ($(this).is(':checked') ) {
                selectedType = $(this).val();
                if( selectedType == 'NO_FILTER' ){
                var let= $("#medicineSearch").val();
                   customAjax({
                                      url: '/auth/getAllMedicine',
                       method: 'GET',
                    success: function (data) {
                        drawTableSearchMedicine(data);
                    },
                    error: function () {
                    }
                });
                }else if(selectedType != '' && selectedShape == '' ) {
                   var let= $("#medicineSearch").val();
                    customAjax({
                             url: '/auth/getAllMedicine',
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
                                url: '/auth/getAllMedicine',
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
                                url: '/auth/getAllMedicine',
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
                                     url: '/auth/getAllMedicine',
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
                        url: '/auth/getAllMedicine',
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
                                url: '/auth/getAllMedicine',
                              method: 'GET',
                           success: function (data) {
                               drawTableSearchMedicine(data);
                           },
                           error: function () {
                           }
                       });
                       }else if(selectedShape != '' && selectedType == '' ) {
                          var let= $("#medicineSearch").val();
                           customAjax({
                                   url: '/auth/getAllMedicine',
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
                                                             url: '/auth/getAllMedicine',
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
                                                             url: '/auth/getAllMedicine',
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
                                                                 url: '/auth/getAllMedicine',
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
                                 url: '/auth/getAllMedicine',
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

});