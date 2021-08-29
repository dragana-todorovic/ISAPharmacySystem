
$(document).ready(function() {
	var decoded = parseJwt(localStorage.getItem('jwt'));
	var email = decoded.email

    $('a#dermatologist_complaint').click(function(){
         $('#dermatologistComplaintDiv').attr('hidden', false);

        console.log("DERRMATROLOGIS")
        $('#search-box-medicine').attr('hidden',true);
        $('#edit-profile').attr('hidden', true);
        $('#show').attr('hidden',true);
        $('#pharmacy-details').attr('hidden',true);
        $('#pharmacies_for_derm_appointments').attr('hidden',true);
        $('#reserved_medicine_div').attr('hidden',true);
     });

      $('a#pharmacist_complaint').click(function(){
         $('#pharmacistComplaintDiv').attr('hidden', false);
         console.log("pharmacist_complaint")
         $('#search-box-medicine').attr('hidden',true);
         $('#edit-profile').attr('hidden', true);
         $('#show').attr('hidden',true);
         $('#pharmacy-details').attr('hidden',true);
         $('#pharmacies_for_derm_appointments').attr('hidden',true);
         $('#reserved_medicine_div').attr('hidden',true);
      });

       $('a#pharmacy_complaint').click(function(){
            $('#pharmacyComplaintDiv').attr('hidden', false);

              $('#search-box-medicine').attr('hidden',true);
              $('#edit-profile').attr('hidden', true);
              $('#show').attr('hidden',true);
              $('#pharmacy-details').attr('hidden',true);
              $('#pharmacies_for_derm_appointments').attr('hidden',true);
              $('#reserved_medicine_div').attr('hidden',true);
           });
});