var isCitySelected = false;
var isGradeSelected = false;
var selectedGrade ='';
var selectedCity = '';
$(document).ready(function(){
			$('a#s_p').click(function(){
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
	$("#searchPharmacies").click(function () {
		$('#all_pharmacies_show').attr('hidden',true);
		$('#all_medicine_show').attr('hidden',true);
		    customAjax({
	        url: '/auth/getAllCities' ,
	        method: 'GET',
	        success: function (data) {
	            addCities(data);
	        },
	        error: function () {
				console.log("error")
	        }
	
	   	 });
	});
		$('i[name="searchPharmacies"]').click(function(){
		var let= $("#pharmaciesSearch").val();
		    customAjax({
	        url: '/auth/searchPharmacies/' + let ,
	        method: 'GET',
	        success: function (data) {
	            showPharmacies(data);
		    customAjax({
	        url: '/auth/getAllCities' ,
	        method: 'GET',
	        success: function (data) {
	            addCities(data);
	        },
	        error: function () {
				console.log("error")
	        }
	
	   	 });
	        },
	        error: function () {
				console.log("error")
	        }
	
	   	 });
		});

function addCities(data){
	let temp='';
	for(i in data){
		console.log(data[i])
		temp+= `<input type="radio" id="`+data[i]+`"name="ph_postageC"
		 value="`+data[i]+`" />`+data[i];
	}
	temp+=`<input type="radio" id="0" name="ph_postageC" value="NO_FILTER_CITY" /> NO FILTER`
	$('p[name="city_filters"]').html(temp);
			$('input:radio[name="ph_postageR"]').change(function(){
				if ($(this).is(':checked') ) {
					selectedGrade = $(this).val();
			        var let= $("#pharmaciesSearch").val();
					if( selectedGrade == 'NO_FILTER' ){
				        customAjax({
				            url: '/auth/searchPharmacies/' + let ,
				            method: 'GET',
				            success: function (data) {
								console.log(data)
				                showPharmacies(data);
				            },
				            error: function () {
								console.log("error")
				            }
				
				       	 });
					}else if(selectedGrade != '' && selectedCity == '' ) {
							var let= $("#pharmaciesSearch").val();
						    customAjax({
				            url: '/auth/searchPharmacies/' + let ,
				            method: 'GET',
				            success: function (data) {
				                showPharmaciesGrade(data,selectedGrade);
				            },
				            error: function () {
								console.log("error")
				            }
				
				       	 });
					}
					else if(selectedGrade != '' && selectedCity != '' ) {
							if (selectedGrade == 'NO_FILTER'){
								var let= $("#pharmaciesSearch").val();
							    customAjax({
					            url: '/auth/searchPharmacies/' + let ,
					            method: 'GET',
					            success: function (data) {
					                showPharmaciesCity(data,selectedCity);
					            },
					            error: function () {
									console.log("error")
					            }
					
					       	 });
						}
						else if(selectedCity == "NO_FILTER_CITY"){
								var let= $("#pharmaciesSearch").val();
							    customAjax({
					            url: '/auth/searchPharmacies/' + let ,
					            method: 'GET',
					            success: function (data) {
					                showPharmaciesGrade(data,selectedGrade);
					            },
					            error: function () {
									console.log("error")
					            }
					
					       	 });
						}
						else{
							   var let= $("#pharmaciesSearch").val();
							    customAjax({
					            url: '/auth/searchPharmacies/' + let ,
					            method: 'GET',
					            success: function (data) {
					                showPharmaciesCityGrade(data,selectedCity,selectedGrade);
					            },
					            error: function () {
									console.log("error")
					            }
					
					       	 });
							
						}
					}else if((selectedGrade != '' && selectedGrade !='NO_FILTER') &&  selectedCity == 'NO_FILTER_CITY'){
		 				var let= $("#pharmaciesSearch").val();				    
						customAjax({
				            url: '/auth/searchPharmacies/' + let ,
				            method: 'GET',
				            success: function (data) {
								
				                showPharmaciesGrade(data,selectedGrade);
				            },
				            error: function () {
								console.log("error")
				            }
				
				       	 });
					
					}
				}
	   		})
	    $('input:radio[name="ph_postageC"]').change(function(){
		console.log("PRITISAK")
                 if ($(this).is(':checked') ) {
                    selectedCity = $(this).val();
                    if( selectedCity == 'NO_FILTER_CITY' ){
                    var let= $("#pharmaciesSearch").val();	
						customAjax({
				            url: '/auth/searchPharmacies/' + let ,
				            method: 'GET',
				            success: function (data) {
				                showPharmacies(data);
				            },
				            error: function () {
								console.log("error")
				            }
				
				       	 });
                    }else if(selectedCity != '' && selectedGrade == '' ) {
                      var let= $("#pharmaciesSearch").val();	
                        customAjax({
                            url: '/auth/searchPharmacies/' + let ,
                            method: 'GET',
                            success: function (data) {
                                showPharmaciesCity(data, selectedCity);
                            },
                            error: function () {
                            }
                        });
                    }else  if(selectedGrade != '' && selectedCity != ''){
                       if (selectedGrade== 'NO_FILTER'){
                               var let= $("#pharmaciesSearch").val();	
                               //selektuj samo shape
                               customAjax({
                                  url: '/auth/searchPharmacies/' + let ,
                                   method: 'GET',
                                   success: function (data) {
                                       showPharmaciesCity(data, selectedCity);
                                   },
                                   error: function () {
                                   }
                               });
                         }else if (selectedCity == "NO_FILTER_CITY"){
                              var let= $("#pharmaciesSearch").val();	
	                           //selektuj samo type
	                           customAjax({
	                               url: '/auth/searchPharmacies/' + let ,
	                               method: 'GET',
	                               success: function (data) {
	                                  showPharmaciesGrade(data, selectedGrade);
	                               },
	                               error: function () {
	                               }
	                           });
                             }else{ //nadji presek
                            var let= $("#pharmaciesSearch").val();	
                               customAjax({
                                   url: '/auth/searchPharmacies/' + let ,
                                   method: 'GET',
                                   success: function (data) {
                                       showPharmaciesCityGrade(data, selectedCity, selectedGrade);
                                   },
                                   error: function () {
                                   }
                               });
                                           }
                    }else if((selectedCity != '' && selectedCity !='NO_FILTER_CITY') &&  selectedGrade == 'NO_FILTER'){
                        var let= $("#pharmaciesSearch").val();	
                        customAjax({
                           url: '/auth/searchPharmacies/' + let ,
                            method: 'GET',
                            success: function (data) {
                                showPharmaciesCity(data, selectedCity);
                            },
                            error: function () {
                            }
                        });
                    }
                }

       		 })
	}
 });
function showPharmacies(data){
	let temp='';
	for (i in data){
	    var averageRating= 0.0;
	    var countOfRatings = 0.0;
        if(data[i].ratings.length >0){
            for(x in data[i].ratings){
                countOfRatings += data[i].ratings[x].rating;
            }
            averageRating = countOfRatings/data[i].ratings.length ;
        }
		temp+=`<tr id="`+data[i].id+`">
			<td>`+data[i].name+`</td>
			<td>`+data[i].address.city+`</td>
			<td>`+data[i].address.street+`</td>
			<td>`+data[i].description+`</td>
			<td>`+averageRating+`</td>
			</tr>`;
	}
	$('#all_pharmacies_table').html(temp);
	$('#all_pharmacies_show').attr('hidden',false);
}
function showPharmaciesGrade(data,grade){
	let temp='';
	for (i in data){
	    var averageRating= 0.0;
	    var countOfRatings = 0.0;
        if(data[i].ratings.length >0){
            for(x in data[i].ratings){
                countOfRatings += data[i].ratings[x].rating;
            }
            averageRating = countOfRatings/data[i].ratings.length ;
        }
		if(averageRating<=grade && averageRating>=(grade-1)){
		temp+=`<tr id="`+data[i].id+`">
			<td>`+data[i].name+`</td>
			<td>`+data[i].address.city+`</td>
			<td>`+data[i].address.street+`</td>
			<td>`+data[i].description+`</td>
			<td>`+averageRating+`</td>
			</tr>`;
			}
	}
	$('#all_pharmacies_table').html(temp);
	$('#all_pharmacies_show').attr('hidden',false);
}
function showPharmaciesCity(data,city){
	let temp='';
	for (i in data){
		if(data[i].address.city == city){
		    var averageRating= 0.0;
		    var countOfRatings = 0.0;
	        if(data[i].ratings.length >0){
	            for(x in data[i].ratings){
	                countOfRatings += data[i].ratings[x].rating;
	            }
	            averageRating = countOfRatings/data[i].ratings.length ;
	        }
			temp+=`<tr id="`+data[i].id+`">
				<td>`+data[i].name+`</td>
				<td>`+data[i].address.city+`</td>
				<td>`+data[i].address.street+`</td>
				<td>`+data[i].description+`</td>
				<td>`+averageRating+`</td>
				</tr>`;
		}
	}
	$('#all_pharmacies_table').html(temp);
	$('#all_pharmacies_show').attr('hidden',false);
}
function showPharmaciesCityGrade(data,city,grade){
	let temp='';
	for (i in data){
		if(data[i].address.city == city){
		    var averageRating= 0.0;
		    var countOfRatings = 0.0;
	        if(data[i].ratings.length >0){
	            for(x in data[i].ratings){
	                countOfRatings += data[i].ratings[x].rating;
	            }
	            averageRating = countOfRatings/data[i].ratings.length ;
	        }
			if(averageRating<=grade && averageRating>=(grade-1)){
				temp+=`<tr id="`+data[i].id+`">
					<td>`+data[i].name+`</td>
					<td>`+data[i].address.city+`</td>
					<td>`+data[i].address.street+`</td>
					<td>`+data[i].description+`</td>
					<td>`+averageRating+`</td>
					</tr>`;
					}
		}
	}
	
	$('#all_pharmacies_table').html(temp);
	$('#all_pharmacies_show').attr('hidden',false);
}
