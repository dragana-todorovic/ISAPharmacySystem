$(document).ready(function() {
	var email = localStorage.getItem('email')
	var term;
	var tableToShow;
	$('a#schedule_consulting').click(function(){
			
		
		$('#shedule_consulting').attr('hidden',false);
	$('#eval').attr('hidden',true);	
	$('#my_derm_appointments').attr('hidden',true);
	$('#pharmacy-details').attr('hidden',true);		
	$('#all_pharmacies_show').attr('hidden',true);	
	$('#edit-profile').attr('hidden', true);
	$('#show').attr('hidden',true);
	$('#derm_appointments').attr('hidden',true);
	$('#ph_av_con').attr('hidden',true)
	$('#ph_con').attr('hidden',true)
	$('#pharmacies_for_derm_appointments').attr('hidden',true);
	$('#my_ph_appointments').attr('hidden',true);
	$('#medicine_show').attr('hidden',true);
	$('#reserved_medicine_div').attr('hidden',true);
	$('#search-box-medicine').attr('hidden',true);
	$('#qr_code_show').attr('hidden', true);
	$('#pharamcies_with_medicine_show').attr('hidden',true);
    $('#pharmacyComplaintDiv').attr('hidden', true);
    $('#dermatologistComplaintDiv').attr('hidden', true);
    $('#pharmacistComplaintDiv').attr('hidden', true);
	});
		$('a#history_cons').click(function(){
			tableToShow="history";
			customAjax({
	        method:'GET',
	        url:'/pharmacy/getCounselingByPatienetId/'+email,
	        contentType: 'application/json',
	        success: function(data){
				console.log(data);
				showMyCounslingAppointments(data);
			},
			error: function(){
				console.log("error");
			}
	     });

	});
	$('a#my_app_ph').click(function(){
		tableToShow="current";
			customAjax({
	        method:'GET',
	        url:'/pharmacy/getCounselingByPatienetId/'+email,
	        contentType: 'application/json',
	        success: function(data){
				console.log(data);
				showMyCounslingAppointments(data);
			},
			error: function(){
				console.log("error");
			}
	     });

	});
	$('#pharamciesForConsulting').on('click',function(){
		console.log($('#date').val())
		term=$('#date').val();
		customAjax({
			method:'GET',
	        url:'/pharmacy/getPharamciesWithAvailablePharmacists/' + term,
	        contentType: 'application/json',
    		success: function(data) {
				showPharmaciesForConsulting(data);
    		},
    		error:function(message){
				alert("Error")
    			console.log(message)
    		}
    	});
	})
	
	 $('#ph_av_con_body').on('click','button',function(event){
		var pharmacy = $(event.target).closest('tr').attr('id');
		customAjax({
			method:'GET',
	        url:'/pharm/getAvailablePharmacistsByPharmacy/'+pharmacy+'/'+term,
	        contentType: 'application/json',
    		success: function(data) {
				showPharmacistsForConsulting(data);
    		},
    		error:function(message){
				alert("Error")
    			console.log(message)
    		}
    	});
		
	})
	
	 $('#ph_con_body').on('click','button',function(event){
		var pom = $(event.target).closest('tr').attr('id');
		var pharmacistEmail;
		var date=term.split("T")[0];
		var time=term.split("T")[1];
		var duration="30";
		customAjax({
			method:'GET',
	        url:'/pharm/getPharmacistsById/'+pom,
	        contentType: 'application/json',
    		success: function(data) {
				console.log(data);
				pharmacistEmail=data.user.email;
				obj = JSON.stringify({pharmacistEmail:pharmacistEmail,startDate:date,startTime:time,patientEmail:email,duration:duration});
	    		customAjax({
		        method:'POST',
		        url:'/pharm/scheduleAnAppointment',
		        data : obj,
		        contentType: 'application/json',
		        success: function(){
					
		        	alert("Success scheduled an appointment!")
				
				},
				error: function(){
					
					alert("Can't schedule an appointment. Date and time aren't correct. You are not working then, patient and you are not available ot it passed")
				}
		      });
    		},
    		error:function(message){
				alert("Error")
    			console.log(message)
    		}
    	});
		$('#my_ph_appointments_body').on('click','button',function(event){
		pom = $(event.target).closest('tr').attr('id');
        customAjax({
            method:'POST',
            url:'/pharmacy/cancelCounselingAppointment/'+pom ,
            contentType: 'application/json',
            success: function() {
               alert("Successfully canceled counseling!");
            },
            error:function(){
                alert("You can not cancel counseling within less than 24 before appointment!");
            }
        });
		
	})

		
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
function showPharmacistsForConsulting(data){
	$('#pharmacy-details').attr('hidden',true);
	let temp='';
	for (i in data){
		temp+=`<tr id="`+data[i].id+`">
			<td >`+data[i].firstName+` `+data[i].lastName+`</td>
			<td>`+data[i].avrageGrade+`</td>
			<td><button id="schedule_counsling" class="ui primary basic button">Schedule counsling</button>
			</tr>`;
	}
	
	$('#ph_con_body').html(temp);	
	$('#ph_con').attr('hidden',false)
	$('#ph_av_con').attr('hidden',true)
	$('#eval').attr('hidden',true);	
	$('#my_derm_appointments').attr('hidden',true);
	$('#pharmacy-details').attr('hidden',true);		
	$('#all_pharmacies_show').attr('hidden',true);	
	$('#edit-profile').attr('hidden', true);
	$('#show').attr('hidden',true);
	$('#derm_appointments').attr('hidden',true);
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

function showPharmaciesForConsulting(data){
	$('#pharmacy-details').attr('hidden',true);
	let temp='';
	for (i in data){
		console.log(data[i])
		temp+=`<tr id="`+data[i].id+`">
			<td >`+data[i].pharmacyName+`</td>
			<td>`+data[i].pharmacyCity+`</td>
			<td>`+data[i].pharmacyStreet+`</td>
			<td>`+data[i].pharmacyGrade+`</td>
			<td><button id="show_pharmacists" class="ui primary basic button">Show available pharmacists</button>
			</tr>`;
	}
	
	$('#ph_av_con_body').html(temp);	
	$('#ph_av_con').attr('hidden',false)
	$('#eval').attr('hidden',true);	
	$('#my_derm_appointments').attr('hidden',true);
	$('#pharmacy-details').attr('hidden',true);		
	$('#all_pharmacies_show').attr('hidden',true);	
	$('#edit-profile').attr('hidden', true);
	$('#show').attr('hidden',true);
	$('#derm_appointments').attr('hidden',true);
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

function showMyCounslingAppointments(data){
	$('#pharmacy-details').attr('hidden',true);
	let temp='';
	for (i in data){
		var date=data[i].time.split("T")[0];
		var time=data[i].time.split("T")[1];
		if(tableToShow=="current"){
			$('#label_couns').text("MY APPOINTMENTS AT PHARMACISTS");
			if(!data[i].isHistory){
				if(data[i].isCounslingExpired){
					temp+=`<tr id="`+data[i].id+`">
						<td >`+data[i].pharmacistsFirstName+` `+data[i].pharmacistsLastName+`</td>
						<td>`+data[i].pharmacyName+`</td>
						<td>`+data[i].pharmacyCity+`</td>
						<td>`+data[i].pharmacyStreet+`</td>
						<td>`+date+` `+time+`</td>
						<td>`+data[i].duration+`</td>
						<td>`+data[i].price+`</td>
						<td><button id="cancel_counsling" class="ui primary basic button" disabled>Cancel appointment</button>
						</tr>`;
					}
					else{
					temp+=`<tr id="`+data[i].id+`">
					<td >`+data[i].pharmacistsFirstName+` `+data[i].pharmacistsLastName+`</td>
					<td>`+data[i].pharmacyName+`</td>
					<td>`+data[i].pharmacyCity+`</td>
					<td>`+data[i].pharmacyStreet+`</td>
					<td>`+data[i].time+`</td>
					<td>`+data[i].duration+`</td>
					<td>`+data[i].price+`</td>
					<td><button id="cancel_counsling" class="ui primary basic button">Cancel appointment</button>
					</tr>`;
				}
			}
			
		}else{
			$('#label_couns').text("HISTORY OF MY COUNSLINGS AT PHARAMCISTS");
			if(data[i].isHistory){
				if(data[i].isCounslingExpired){
					temp+=`<tr id="`+data[i].id+`">
						<td >`+data[i].pharmacistsFirstName+` `+data[i].pharmacistsLastName+`</td>
						<td>`+data[i].pharmacyName+`</td>
						<td>`+data[i].pharmacyCity+`</td>
						<td>`+data[i].pharmacyStreet+`</td>
						<td>`+date+` `+time+`</td>
						<td>`+data[i].duration+`</td>
						<td>`+data[i].price+`</td>
						<td><button id="cancel_counsling" class="ui primary basic button" disabled>Cancel appointment</button>
						</tr>`;
					}
					else{
					temp+=`<tr id="`+data[i].id+`">
					<td >`+data[i].pharmacistsFirstName+` `+data[i].pharmacistsLastName+`</td>
					<td>`+data[i].pharmacyName+`</td>
					<td>`+data[i].pharmacyCity+`</td>
					<td>`+data[i].pharmacyStreet+`</td>
					<td>`+data[i].time+`</td>
					<td>`+data[i].duration+`</td>
					<td>`+data[i].price+`</td>
					<td><button id="cancel_counsling" class="ui primary basic button">Cancel appointment</button>
					</tr>`;
				}
			}
		}
	}
	
	$('#my_ph_appointments_body').html(temp);	
	$('#my_ph_appointments').attr('hidden',false)
	$('#eval').attr('hidden',true);	
	$('#my_derm_appointments').attr('hidden',true);
	$('#pharmacy-details').attr('hidden',true);		
	$('#all_pharmacies_show').attr('hidden',true);	
	$('#edit-profile').attr('hidden', true);
	$('#show').attr('hidden',true);
	$('#derm_appointments').attr('hidden',true);
	$('#ph_av_con').attr('hidden',true)
	$('#ph_con').attr('hidden',true)
	$('#pharmacies_for_derm_appointments').attr('hidden',true);
	$('#shedule_consulting').attr('hidden',true);
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