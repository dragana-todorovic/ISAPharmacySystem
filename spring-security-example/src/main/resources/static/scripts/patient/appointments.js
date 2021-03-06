$(document).ready(function() {
	var trid;
	var email = localStorage.getItem('email')
	var tableToShow;

	$('a#history_derm').click(function(){
		tableToShow="history"
			customAjax({
	        method:'GET',
	        url:'/appointment/getAllDermAppointmentsByPatient/'+email,
	        contentType: 'application/json',
	        success: function(data){
				console.log(data)
				showMyAppointmentsAtDermatologists(data)
			},
			error: function(){
				console.log("error");
			}
	     });

	});
	
	$('a#my_app_derm').click(function(){
		tableToShow="current"
			customAjax({
	        method:'GET',
	        url:'/appointment/getAllDermAppointmentsByPatient/'+email,
	        contentType: 'application/json',
	        success: function(data){
				console.log(data)
				showMyAppointmentsAtDermatologists(data)
			},
			error: function(){
				console.log("error");
			}
	     });

	});

	$('a#all_subscribed').click(function(){
        customAjax({
            method:'GET',
            url:'/patient/getUserAllSubscribedPharmacies/' + email,
            contentType: 'application/json',
            success: function(data){
                showSubscribedPharmacies(data)
            },
            error: function(){
                console.log("error");
            }
         });

    });
	$('a#all_pharmacies').click(function(){
        customAjax({
            method:'GET',
            url:'/pharmacy/getAll/' ,
            contentType: 'application/json',
            success: function(data){
                showPharmacies(data)
            },
            error: function(){
                console.log("error");
            }
         });

    });

	$('#submitSubscribe').click(function(){
        obj = JSON.stringify({
            patientEmail:email,
            pharmacyId:trid
        });

        console.log(obj)
        customAjax({
            method:'POST',
            url:'/patient/subscribePatientOnActionsAndBenefits' ,
            data:obj,
            contentType: 'application/json',
            success: function() {
                //poziv da se useru doda apoteka na listu subscribovanih apoteka
                // treba provera da li je vec subscraboan

                alert('Success subscribed on pharmacy')
            },
            error:function(message){
                alert("You have already been subscribed to pharmacy")

            }
        });
	})
	 $('#all_pharmacies_table').on('click','button',function(event){
			trid = $(event.target).closest('tr').attr('id');
            if(this.id == 'details'){
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
            }else if( this.id == 'subscribed'){
                obj = JSON.stringify({
                    patientEmail:email,
                    pharmacyId:trid
                });

                customAjax({
                    method:'POST',
                    url:'/patient/unsubscribePatientOnActionsAndBenefits' ,
                    data:obj,
                    contentType: 'application/json',
                    success: function() {
                     customAjax({
                            method:'GET',
                            url:'/patient/getUserAllSubscribedPharmacies/' + email,
                            contentType: 'application/json',
                            success: function(data){
                                showSubscribedPharmacies(data)
                            },
                            error: function(){
                                console.log("error");
                            }
                         });
                       alert("Successfully unsubscribed from Pharmacy Actions&Benefits");
                    },
                    error:function(message){
                        alert("You are still subscribed to Pharmacy Actions&Benefits");
                    }
                });
            }

	})
	$('#appointments_derm').on('click',function(){
				customAjax({
				method:'GET',
		        url:'/appointment/getAvailableAppointmentsByPharmacyId/' + trid,
		        contentType: 'application/json',
	    		success: function(data) {
					console.log(data);
					showAvailableAppointments(data);
	    		},
	    		error:function(message){
					alert("Error")
	    			console.log(message)
	    		}
	    	});
	})
	 $('#appointments_dermatology_body').on('click','button',function(event){
		pom = $(event.target).closest('tr').attr('id');
		            customAjax({
                    method:'POST',
                    url:'/appointment/scheduleDermatologistAppointment/'+pom+'/'+email ,
                    contentType: 'application/json',
                    success: function() {
                       alert("Successfully scheduled appointment!");
                    },
                    error:function(){
                        alert("Could not schedule appointment,try later");
                    }
                });

	})

	 $('#my_appointments_dermatology_body').on('click','button',function(event){
		pom = $(event.target).closest('tr').attr('id');
        customAjax({
            method:'POST',
            url:'/appointment/cancelDermatologistAppointment/'+pom ,
            contentType: 'application/json',
            success: function() {
               alert("Successfully canceled appointment!");
            },
            error:function(){
                alert("Could not cancel appointment,try later");
            }
        });

	})
	$("th[name=sortByGrade]").click(function () {
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
	$("th[name=sortByCity]").click(function () {
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
	$("th[name=sortByDuration]").click(function () {
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
	$("th[name=sortByDate]").click(function () {
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

function showAvailableAppointments(data){
	$('#pharmacy-details').attr('hidden',true);
	let temp='';
	for (i in data){
		temp+=`<tr id="`+data[i].id+`">
			<td >`+data[i].dermatologistFirstName+` `+data[i].dermatologistLastName+`</td>
			<td>`+data[i].grade+`</td>
			<td>`+data[i].date+` `+data[i].time+`</td>
			<td>`+data[i].duration+` min </td>
			<td>`+data[i].price+` din </td>
			<td><button id="schedule_appointment" class="ui primary basic button">Schedule appointment</button>
			</tr>`;
	}

	$('#appointments_dermatology_body').html(temp);
	$('#derm_appointments').attr('hidden',false);
	$('#eval').attr('hidden',true);	
	$('#my_derm_appointments').attr('hidden',true);
	$('#pharmacy-details').attr('hidden',true);		
	$('#all_pharmacies_show').attr('hidden',true);	
	$('#edit-profile').attr('hidden', true);
	$('#show').attr('hidden',true);
	$('#ph_av_con').attr('hidden',true)
	$('#ph_con').attr('hidden',true)
	$('#pharmacies_for_derm_appointments').attr('hidden',true);
	$('#shedule_consulting').attr('hidden',true);
	$('#my_ph_appointments').attr('hidden',true);
	$('#medicine_show').attr('hidden',true);
	$('#reserved_medicine_div').attr('hidden',true);
	$('#search-box-medicine').attr('hidden',true);
	$('#qr_code_show').attr('hidden', true);
	$('#pharamcies_with_medicine_show').attr('hidden',true);
    $('#pharmacyComplaintDiv').attr('hidden', true);
    $('#dermatologistComplaintDiv').attr('hidden', true);
    $('#pharmacistComplaintDiv').attr('hidden', true);
}
function showSubscribedPharmacies(data){
	$('#pharmacy-details').attr('hidden',true);
	let temp='';
	for (i in data){
		temp+=`<tr id="`+data[i].id+`">
			<td >`+data[i].name+`</td>
			<td>`+data[i].address.city+`</td>
			<td>`+data[i].address.street+`</td>
			<td>`+data[i].description+`</td>
			<td><button id="subscribed" class="ui primary basic button">Unsubscribed</button>
			</tr>`;
	}
	$('#all_pharmacies_table').html(temp);


	$('#eval').attr('hidden',true);	
	$('#my_derm_appointments').attr('hidden',true);
	$('#pharmacy-details').attr('hidden',true);		
	$('#all_pharmacies_show').attr('hidden',false);
	$('#edit-profile').attr('hidden', true);
	$('#show').attr('hidden',true);
	$('#derm_appointments').attr('hidden',true);
	$('#ph_av_con').attr('hidden',true)
	$('#ph_con').attr('hidden',true)
	$('#shedule_consulting').attr('hidden',true);
	$('#my_ph_appointments').attr('hidden',true);
	$('#medicine_show').attr('hidden',true);
	$('#reserved_medicine_div').attr('hidden',true);
	$('#search-box-medicine').attr('hidden',true);
	$('#qr_code_show').attr('hidden', true);
	$('#pharamcies_with_medicine_show').attr('hidden',true);
    $('#pharmacyComplaintDiv').attr('hidden', true);
    $('#dermatologistComplaintDiv').attr('hidden', true);
    $('#pharmacistComplaintDiv').attr('hidden', true);
}
function showPharmacies(data){
	$('#pharmacy-details').attr('hidden',true);
	let temp='';
	for (i in data){
	    var averageRating= 0.0;
	    var countOfRatings = 0.0;
        if(data[i].ratings.length >0){
            for(x in data[i].ratings){
				console.log(x);
                countOfRatings += data[i].ratings[x].rating;
            }
            averageRating = countOfRatings/data[i].ratings.length ;
        }
		console.log(averageRating);
		temp+=`<tr id="`+data[i].id+`">
			<td >`+data[i].name+`</td>
			<td>`+data[i].address.city+`</td>
			<td>`+data[i].address.street+`</td>
			<td>`+data[i].description+`</td>
			<td>`+averageRating+`</td>
			<td><button id="details" class="ui primary basic button">Details</button>
			</tr>`;
	}
	$('#all_pharmacies_table').html(temp);
	$('#all_pharmacies_show').attr('hidden',false);	
	$('#eval').attr('hidden',true);	
	$('#my_derm_appointments').attr('hidden',true);
	$('#pharmacy-details').attr('hidden',true);		
	$('#edit-profile').attr('hidden', true);
	$('#show').attr('hidden',true);
	$('#derm_appointments').attr('hidden',true);
	$('#ph_av_con').attr('hidden',true)
	$('#ph_con').attr('hidden',true)
	$('#pharmacies_for_derm_appointments').attr('hidden',true);
	$('#shedule_consulting').attr('hidden',true);
	$('#my_ph_appointments').attr('hidden',true);
	$('#medicine_show').attr('hidden',true);
	$('#reserved_medicine_div').attr('hidden',true);
	$('#search-box-medicine').attr('hidden',true);
	$('#qr_code_show').attr('hidden', true);
	$('#pharamcies_with_medicine_show').attr('hidden',true);
    $('#pharmacyComplaintDiv').attr('hidden', true);
    $('#dermatologistComplaintDiv').attr('hidden', true);
    $('#pharmacistComplaintDiv').attr('hidden', true);
}
function showPharmacy(data){
	$('#pharmacy-name').text("Name:"+data.name);
	$('#pharmacy-description').text("Description:"+data.description);
	$('#pharmacy-city').text("City:"+data.address.city);
	$('#pharmacy-address').text("Street:"+data.address.street);
	$('#pharmacy-details').attr('hidden',false);
	$('#eval').attr('hidden',true);	
	$('#my_derm_appointments').attr('hidden',true);
	$('#all_pharmacies_show').attr('hidden',true);	
	$('#edit-profile').attr('hidden', true);
	$('#show').attr('hidden',true);
	$('#derm_appointments').attr('hidden',true);
	$('#ph_av_con').attr('hidden',true)
	$('#ph_con').attr('hidden',true)
	$('#pharmacies_for_derm_appointments').attr('hidden',true);
	$('#shedule_consulting').attr('hidden',true);
	$('#my_ph_appointments').attr('hidden',true);
	$('#medicine_show').attr('hidden',true);
	$('#reserved_medicine_div').attr('hidden',true);
	$('#search-box-medicine').attr('hidden',true);
	$('#qr_code_show').attr('hidden', true);
	$('#pharamcies_with_medicine_show').attr('hidden',true);
    $('#pharmacyComplaintDiv').attr('hidden', true);
    $('#dermatologistComplaintDiv').attr('hidden', true);
    $('#pharmacistComplaintDiv').attr('hidden', true);
	
}

function showMyAppointmentsAtDermatologists(data){
	$('#pharmacy-details').attr('hidden',true);
	let temp='';
	for (i in data){
		var date=data[i].dateAndtime.split("T")[0];
		var time=data[i].dateAndtime.split("T")[1];
		if(tableToShow=="history"){
			if(data[i].isHistory){
				$('#label_derm').text("HISTORY OF MY APPOINTMENTS AT DERMATOLOGIST");
				if(data[i].isAppointmentExpired){
				temp+=`<tr id="`+data[i].id+`">
					<td >`+data[i].dermatologistsFirstName+` `+data[i].dermatologistsLastName+`</td>
					<td>`+data[i].pharmacyName+`</td>
					<td>`+data[i].pharmacyCity+`</td>
					<td>`+data[i].pharmacyStreet+`</td>
					<td>`+date+` `+time+`</td>
					<td>`+data[i].duration+` min </td>
					<td>`+data[i].price+` din </td>
					<td><button id="cancel_appointment" class="ui primary basic button" disabled>Cancel appointment</button>
					</tr>`;
				}
				else{
					
				temp+=`<tr id="`+data[i].id+`">
					<td >`+data[i].dermatologistsFirstName+` `+data[i].dermatologistsLastName+`</td>
					<td>`+data[i].pharmacyName+`</td>
					<td>`+data[i].pharmacyCity+`</td>
					<td>`+data[i].pharmacyStreet+`</td>
					<td>`+date+` `+time+`</td>
					<td>`+data[i].duration+` min </td>
					<td>`+data[i].price+` din </td>
					<td><button id="cancel_appointment" class="ui primary basic button">Cancel appointment</button>
					</tr>`;
				}
			}
		}else{
			$('#label_derm').text("MY APPOINTMENTS AT DERMATOLOGISTS");
			if(!data[i].isHistory){
				if(data[i].isAppointmentExpired){
				temp+=`<tr id="`+data[i].id+`">
					<td >`+data[i].dermatologistsFirstName+` `+data[i].dermatologistsLastName+`</td>
					<td>`+data[i].pharmacyName+`</td>
					<td>`+data[i].pharmacyCity+`</td>
					<td>`+data[i].pharmacyStreet+`</td>
					<td>`+date+` `+time+`</td>
					<td>`+data[i].duration+` min </td>
					<td>`+data[i].price+` din </td>
					<td><button id="cancel_appointment" class="ui primary basic button" disabled>Cancel appointment</button>
					</tr>`;
				}
				else{
				temp+=`<tr id="`+data[i].id+`">
					<td >`+data[i].dermatologistsFirstName+` `+data[i].dermatologistsLastName+`</td>
					<td>`+data[i].pharmacyName+`</td>
					<td>`+data[i].pharmacyCity+`</td>
					<td>`+data[i].pharmacyStreet+`</td>
					<td>`+date+` `+time+`</td>
					<td>`+data[i].duration+` min </td>
					<td>`+data[i].price+` din </td>
					<td><button id="cancel_appointment" class="ui primary basic button">Cancel appointment</button>
					</tr>`;
				}
			}
		}

	}
	
	$('#my_appointments_dermatology_body').html(temp);
	$('#my_derm_appointments').attr('hidden',false);
	$('#eval').attr('hidden',true);	
	$('#pharmacy-details').attr('hidden',true);		
	$('#all_pharmacies_show').attr('hidden',true);	
	$('#edit-profile').attr('hidden', true);
	$('#show').attr('hidden',true);
	$('#derm_appointments').attr('hidden',true);
	$('#ph_av_con').attr('hidden',true)
	$('#ph_con').attr('hidden',true)
	$('#pharmacies_for_derm_appointments').attr('hidden',true);
	$('#shedule_consulting').attr('hidden',true);
	$('#my_ph_appointments').attr('hidden',true);
	$('#medicine_show').attr('hidden',true);
	$('#reserved_medicine_div').attr('hidden',true);
	$('#search-box-medicine').attr('hidden',true);
	$('#qr_code_show').attr('hidden', true);
	$('#pharamcies_with_medicine_show').attr('hidden',true);
    $('#pharmacyComplaintDiv').attr('hidden', true);
    $('#dermatologistComplaintDiv').attr('hidden', true);
    $('#pharmacistComplaintDiv').attr('hidden', true);
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