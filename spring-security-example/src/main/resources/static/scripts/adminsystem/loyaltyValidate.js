$(document).ready(function(e){
        btnProgram= document.getElementById("submitLoyaltyProgram")
        btnProgram.disabled = true

        btnScale= document.getElementById("submitLoyalty")
        btnScale.disabled = true
          $('#txtAppointmentPoints').keyup(function () {
            if($('#txtAppointmentPoints').val() != ''  && $('#txtAdvisingDiscount').val()!=''){
                                   btnProgram.disabled = false
               }
                if($('#txtAppointmentPoints').val() == '' ){
                    btnProgram.disabled = true
                    $(this).addClass(`alert-danger`);
                    $('#txtAppointmentPoints').css('border-color', 'red');
	  		        $("#errorProgram").text("Please enter points!")
                    $('#errorProgram').css('color', 'red');
                }else{
                    $(this).removeClass(`alert-danger`);
                    $('#txtAppointmentPoints').css('border-color', '');
                    $("#errorProgram").text("")
                }

          });
         $('#txtAdvisingDiscount').keyup(function () {
                      if($('#txtAppointmentPoints').val() != ''  && $('#txtAdvisingDiscount').val()!=''){
                                                       btnProgram.disabled = false
                                   }
                      if($('#txtAdvisingDiscount').val() == ''){
                          btnProgram.disabled = true
                          $(this).addClass(`alert-danger`);
                          $('#txtAdvisingDiscount').css('border-color', 'red');
                          $("#errorProgram").text("Please enter discount!")
                          $('#errorProgram').css('color', 'red');
                      }else {
                          $(this).removeClass(`alert-danger`);
                          $('#txtAdvisingDiscount').css('border-color', '');
                          $("#errorProgram").text("")
                      }
                });

            $('#txtSilverNeededPoints').keyup(function () {
                    if(   $('#txtSilverNeededPoints').val()!= '' && $('#txtSilverDiscount').val() &&  $('#txtGoldNeededPoints').val()!= ''   && $('#txtGoldDiscount').val()!=''){
                        btnScale.disabled = false
                        }
                      if($('#txtSilverNeededPoints').val() == ''){
                          btnScale.disabled = true
                          $(this).addClass(`alert-danger`);
                          $('#txtSilverNeededPoints').css('border-color', 'red');
                          $("#error").text("Please enter all fields!")
                          $('#error').css('color', 'red');
                      }else {
                          $(this).removeClass(`alert-danger`);
                          $('#txtSilverNeededPoints').css('border-color', '');
                          $("#error").text("")
                      }
                });
         $('#txtSilverDiscount').keyup(function () {
          if(   $('#txtSilverNeededPoints').val()!= '' && $('#txtSilverDiscount').val()  &&   $('#txtGoldNeededPoints').val()!= ''   && $('#txtGoldDiscount').val()!=''){
                                 btnScale.disabled = false
                                 }
           if($('#txtSilverDiscount').val() == ''){
               btnScale.disabled = true
               $(this).addClass(`alert-danger`);
               $('#txtSilverDiscount').css('border-color', 'red');
               $("#error").text("Please enter all fields!")
               $('#error').css('color', 'red');
           }else {
               $(this).removeClass(`alert-danger`);
               $('#txtSilverDiscount').css('border-color', '');
               $("#error").text("")
           }
     });


          $('#txtGoldNeededPoints').keyup(function () {
           if(   $('#txtSilverNeededPoints').val()!= '' && $('#txtSilverDiscount').val()  &&   $('#txtGoldNeededPoints').val()!= ''   && $('#txtGoldDiscount').val()!=''){
                                  btnScale.disabled = false
                                  }
                              if(!validateLogic($('#txtSilverNeededPoints').val(), $('#txtGoldNeededPoints').val())){
                                  btnScale.disabled = true
                                  $(this).addClass(`alert-danger`);
                                  $('#txtGoldNeededPoints').css('border-color', 'red');
                                  $("#error").text("Gold points must be greater then silver points!")
                                  $('#error').css('color', 'red');
                              }else {
                                  $(this).removeClass(`alert-danger`);
                                  $('#txtGoldNeededPoints').css('border-color', '');
                                  $("#error").text("")
                              }
                        });

                $('#txtGoldDiscount').keyup(function () {
                               if( $('#txtSilverNeededPoints').val()!= '' && $('#txtSilverDiscount').val() &&    $('#txtGoldNeededPoints').val()!= ''   && $('#txtGoldDiscount').val()!=''){
                                                      btnScale.disabled = false
                                                      }
                                 if(!validateLogic($('#txtSilverDiscount').val(), $('#txtGoldDiscount').val())){
                                        btnScale.disabled = true
                                        $(this).addClass(`alert-danger`);
                                        $('#txtGoldDiscount').css('border-color', 'red');
                                        $("#error").text("Gold discount must be greater then silver discount!")
                                        $('#error').css('color', 'red');
                                    }else {
                                        $(this).removeClass(`alert-danger`);
                                        $('#txtGoldDiscount').css('border-color', '');
                                        $("#error").text("")
                                    }
                              });

})

function validateLogic(silver, gold) {
console.log("************")
        console.log(parseInt(silver))
        console.log(parseInt(gold))
	  	if(parseInt(silver) < parseInt(gold) ) {
	  		return true;
	  	}
	  	else {
	  		return false;
	  	}
}
