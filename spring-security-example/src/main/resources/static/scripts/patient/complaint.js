function drawDermatologistComplaintTable(data) {
    let table = '';
    for (i in data) {
        table += `<tr id="myform">
			<td >
                    <input type="radio" name = "newMedicineButton" id="` + data[i].id + `" value="` + data[i].id + `">
			</td>
			<td>`+ data[i].firstName + `</td>
			<td>`+ data[i].lastName + `</td>
			</tr>`;
    }
    $('#dermatologistComplaintTable').html(table);
}

function drawPharmacistComplaintTable(data) {
    let table = '';
    for (i in data) {
        table += `<tr id="myform">
			<td >
                    <input type="radio" name = "pharmacistButton" id="` + data[i].id + `" value="` + data[i].id + `">
			</td>
			<td>`+ data[i].firstName + `</td>
			<td>`+ data[i].lastName + `</td>
			</tr>`;
    }
    $('#pharmacistComplaintTable').html(table);
}
$(document).ready(function() {
	var email = localStorage.getItem('email')
        customAjax({
            method:'GET',
            url:'/derm/getAllDermPatientCanEvaluate/'+email,
            contentType: 'application/json',
            success: function(data){
                drawDermatologistComplaintTable(data)
            },
            error: function(){
                console.log("error");
            }
         });

          customAjax({
                 method:'GET',
                 url:'/pharm/getAllPharmacistsPatientCanEvaluate/'+email,
                 contentType: 'application/json',
                 success: function(data){
                     drawPharmacistComplaintTable(data)
                 },
                 error: function(){
                     console.log("error");
                 }
              });


    /*   customAjax({
                  method:'GET',
                  url:'/derm/getAllDermPatientCanEvaluate/'+email,
                  contentType: 'application/json',
                  success: function(data){
                      drawDermatologistComplaintTable(data)
                  },
                  error: function(){
                      console.log("error");
                  }
               });*/

    $('a#dermatologist_complaint').click(function(){
         $('#dermatologistComplaintDiv').attr('hidden', false);


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
	$('#my_ph_appointments').attr('hidden',true);
	$('#medicine_show').attr('hidden',true);
	$('#reserved_medicine_div').attr('hidden',true);
	$('#search-box-medicine').attr('hidden',true);
	$('#qr_code_show').attr('hidden', true);
	$('#pharamcies_with_medicine_show').attr('hidden',true);
    $('#pharmacyComplaintDiv').attr('hidden', true);
    $('#pharmacistComplaintDiv').attr('hidden', true);
     });

        $('#submitDermatologistComplaint').click(function(){
                let content=$('#txtDermatologistComplaintContent').val()

                let complainOnId =    $("input[name='newMedicineButton']:checked").val();
                obj = JSON.stringify({
                    content:content,
                    complainedOnName:complainOnId,
                    userName:email
                });
                console.log(obj)
                customAjax({
                      url: '/derm/saveDermatologistComplaint',
                      method: 'POST',
                      data:obj,
                      contentType: 'application/json',
                        success: function(){
                            alert("Sucess written complain. Check your email to admins answer.")
                            location.href = "patient.html";
                       },
                      error: function(){
                             alert("Error by writing complain.")
                         }
                });

        })

            $('#submitPharmacistComplaint').click(function(){
                let content=$('#txtPharmacistComplaintContent').val()

                let complainOnId =    $("input[name='pharmacistButton']:checked").val();
                obj = JSON.stringify({
                    content:content,
                    complainedOnName:complainOnId,
                    userName:email
                });
                console.log(obj)
                customAjax({
                      url: '/pharm/savePharmacistComplaint',
                      method: 'POST',
                      data:obj,
                      contentType: 'application/json',
                        success: function(){
                            alert("Sucess written complain. Check your email to admins answer.")
                            location.href = "patient.html";
                       },
                      error: function(){
                             alert("Error by writing complain.")
                         }
                });

        })


      $('a#pharmacist_complaint').click(function(){
         $('#pharmacistComplaintDiv').attr('hidden', false);
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
	$('#my_ph_appointments').attr('hidden',true);
	$('#medicine_show').attr('hidden',true);
	$('#reserved_medicine_div').attr('hidden',true);
	$('#search-box-medicine').attr('hidden',true);
	$('#qr_code_show').attr('hidden', true);
	$('#pharamcies_with_medicine_show').attr('hidden',true);
    $('#pharmacyComplaintDiv').attr('hidden', true);
    $('#dermatologistComplaintDiv').attr('hidden', true);
  
      });

       $('a#pharmacy_complaint').click(function(){
            $('#pharmacyComplaintDiv').attr('hidden', false);

     
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
	$('#my_ph_appointments').attr('hidden',true);
	$('#medicine_show').attr('hidden',true);
	$('#reserved_medicine_div').attr('hidden',true);
	$('#search-box-medicine').attr('hidden',true);
	$('#qr_code_show').attr('hidden', true);
	$('#pharamcies_with_medicine_show').attr('hidden',true);
    $('#pharmacistComplaintDiv').attr('hidden', true);
    $('#dermatologistComplaintDiv').attr('hidden', true);
           });
});