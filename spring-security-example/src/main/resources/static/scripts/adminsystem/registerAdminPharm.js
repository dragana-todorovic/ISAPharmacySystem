$(document).ready(function(e){
    btnAdd = document.getElementById("submitRegisterPharmacyAdmin")
        btnAdd.disabled = true

          $('#txtEmail').keyup(function () {
              if($('#txtEmail').val() != '' && validateEmail($('#txtEmail').val()) && validatePassword($('#txtPassword').val())  &&  $('#txtPassword').val()!= '' && $('#txtFirstName').val()!= '' && $('#txtLastName').val() &&   $('#txtCountry').val() != ''  &&  $('#txtCity').val()!= '' && $('#txtAddress').val()!= ''  && $('#txtNumber').val()!=''  ){
                                                                  btnAdd.disabled = false
                                           }else if( $("#myform input[type='radio']:checked").length == 0){
                                                        btnAdd.disabled = true
                                                        $(this).addClass(`alert-danger`);
                                                        $('#pharmacyTable').css('border-color', 'red');
                                                        $("#errorAdmin").text("Please select one  Pharmacy!")
                                                        $('#errorAdmin').css('color', 'red');
                                                    }else {
                                                        $(this).removeClass(`alert-danger`);
                                                        $('#pharmacyTable').css('border-color', '');
                                                        $("#errorAdmin").text("")
                                                    }
                if(!validateEmail($('#txtEmail').val()) ){
                    btnAdd.disabled = true
                    $(this).addClass(`alert-danger`);
                    $('#txtEmail').css('border-color', 'red');
	  		        $("#errorEmail").text("Email is in wrong format!")
                    $('#errorEmail').css('color', 'red');
                }else{
                    $(this).removeClass(`alert-danger`);
                    $('#txtEmail').css('border-color', '');
                    $("#errorEmail").text("")
                }

          });
           $('#txtPassword').keyup(function () {
             if($('#txtEmail').val() != '' && validateEmail($('#txtEmail').val()) && validatePassword($('#txtPassword').val())  &&  $('#txtPassword').val()!= '' && $('#txtFirstName').val()!= '' && $('#txtLastName').val() &&   $('#txtCountry').val() != ''  &&  $('#txtCity').val()!= '' && $('#txtAddress').val()!= ''  && $('#txtNumber').val()!=''  ){
                                                                 btnAdd.disabled = false
                                          }else if( $("#myform input[type='radio']:checked").length == 0){

                                                       btnAdd.disabled = true
                                                       $(this).addClass(`alert-danger`);
                                                       $('#pharmacyTable').css('border-color', 'red');
                                                       $("#errorAdmin").text("Please select one  Pharmacy!")
                                                       $('#errorAdmin').css('color', 'red');
                                                   }else {
                                                       $(this).removeClass(`alert-danger`);
                                                       $('#pharmacyTable').css('border-color', '');
                                                       $("#errorAdmin").text("")
                                                   }
              if(!validatePassword($('#txtPassword').val()) ){
                  btnAdd.disabled = true
                  $(this).addClass(`alert-danger`);
                  $('#txtPassword').css('border-color', 'red');
              $("#errorPassword").text("Password must have at least 8 characters, lower case, upper case, digit, special character!")
              	  		$('#errorPassword').css('color', 'red');
              		} else {
              			$(this).removeClass(`alert-danger`);
              	  		$('#txtPassword').css('border-color', '');
              	  		$("#errorPassword").text("")
              }
        });
         $('#txtFirstName').keyup(function () {
                    if($('#txtEmail').val() != '' && validateEmail($('#txtEmail').val()) && validatePassword($('#txtPassword').val())  &&  $('#txtPassword').val()!= '' && $('#txtFirstName').val()!= '' && $('#txtLastName').val() &&   $('#txtCountry').val() != ''  &&  $('#txtCity').val()!= '' && $('#txtAddress').val()!= ''  && $('#txtNumber').val()!=''  ){
                                                                        btnAdd.disabled = false
                                                 }else if( $("#myform input[type='radio']:checked").length == 0){

                                                              btnAdd.disabled = true
                                                              $(this).addClass(`alert-danger`);
                                                              $('#pharmacyTable').css('border-color', 'red');
                                                              $("#errorAdmin").text("Please select one  Pharmacy!")
                                                              $('#errorAdmin').css('color', 'red');
                                                          }else {
                                                              $(this).removeClass(`alert-danger`);
                                                              $('#pharmacyTable').css('border-color', '');
                                                              $("#errorAdmin").text("")
                                                          }
                      if($('#txtFirstName').val() == ''){
                          btnAdd.disabled = true
                          $(this).addClass(`alert-danger`);
                          $('#txtFirstName').css('border-color', 'red');
                          $("#error").text("Please enter all fields!")
                          $('#error').css('color', 'red');
                      }else {
                          $(this).removeClass(`alert-danger`);
                          $('#txtFirstName').css('border-color', '');
                          $("#error").text("")
                      }
                });
         $('#txtLastName').keyup(function () {
            if($('#txtEmail').val() != '' && validateEmail($('#txtEmail').val()) && validatePassword($('#txtPassword').val())  &&  $('#txtPassword').val()!= '' && $('#txtFirstName').val()!= '' && $('#txtLastName').val() &&   $('#txtCountry').val() != ''  &&  $('#txtCity').val()!= '' && $('#txtAddress').val()!= ''  && $('#txtNumber').val()!=''  ){
                                                                btnAdd.disabled = false
                                         }else if( $("#myform input[type='radio']:checked").length == 0){

                                                      btnAdd.disabled = true
                                                      $(this).addClass(`alert-danger`);
                                                      $('#pharmacyTable').css('border-color', 'red');
                                                      $("#errorAdmin").text("Please select one  Pharmacy!")
                                                      $('#errorAdmin').css('color', 'red');
                                                  }else {
                                                      $(this).removeClass(`alert-danger`);
                                                      $('#pharmacyTable').css('border-color', '');
                                                      $("#errorAdmin").text("")
                                                  }
           if($('#txtLastName').val() == ''){
               btnAdd.disabled = true
               $(this).addClass(`alert-danger`);
               $('#txtLastName').css('border-color', 'red');
               $("#error").text("Please enter all fields!")
               $('#error').css('color', 'red');
           }else {
               $(this).removeClass(`alert-danger`);
               $('#txtLastName').css('border-color', '');
               $("#error").text("")
           }
     });

         $('#txtSubstituteMedicineCodes').keyup(function () {
          if($('#txtEmail').val() != '' && validateEmail($('#txtEmail').val()) && validatePassword($('#txtPassword').val())  &&  $('#txtPassword').val()!= '' && $('#txtFirstName').val()!= '' && $('#txtLastName').val() &&   $('#txtCountry').val() != ''  &&  $('#txtCity').val()!= '' && $('#txtAddress').val()!= '' && $('#txtNumber').val()!='' ){
                                 btnAdd.disabled = false
                                 }
                if($('#txtSubstituteMedicineCodes').val() == ''){
                    btnAdd.disabled = true
                    $(this).addClass(`alert-danger`);
                    $('#txtSubstituteMedicineCodes').css('border-color', 'red');
                    $("#error").text("Please enter all fields!")
                    $('#error').css('color', 'red');
                }else {
                    $(this).removeClass(`alert-danger`);
                    $('#txtSubstituteMedicineCodes').css('border-color', '');
                    $("#error").text("")
                }
          });


              $('#txtCountry').keyup(function () {
                if($('#txtEmail').val() != '' && validateEmail($('#txtEmail').val()) && validatePassword($('#txtPassword').val())  &&  $('#txtPassword').val()!= '' && $('#txtFirstName').val()!= '' && $('#txtLastName').val() &&   $('#txtCountry').val() != ''  &&  $('#txtCity').val()!= '' && $('#txtAddress').val()!= ''  && $('#txtNumber').val()!=''  ){
                                                                    btnAdd.disabled = false
                                             }else if( $("#myform input[type='radio']:checked").length == 0){

                                                          btnAdd.disabled = true
                                                          $(this).addClass(`alert-danger`);
                                                          $('#pharmacyTable').css('border-color', 'red');
                                                          $("#errorAdmin").text("Please select one  Pharmacy!")
                                                          $('#errorAdmin').css('color', 'red');
                                                      }else {
                                                          $(this).removeClass(`alert-danger`);
                                                          $('#pharmacyTable').css('border-color', '');
                                                          $("#errorAdmin").text("")
                                                      }
                      if($('#txtCountry').val() == ''){
                          btnAdd.disabled = true
                          $(this).addClass(`alert-danger`);
                          $('#txtCountry').css('border-color', 'red');
                          $("#error").text("Please enter all fields!")
                          $('#error').css('color', 'red');
                      }else {
                          $(this).removeClass(`alert-danger`);
                          $('#txtCountry').css('border-color', '');
                          $("#error").text("")
                      }
                });
          $('#txtCity').keyup(function () {
           if($('#txtEmail').val() != '' && validateEmail($('#txtEmail').val()) && validatePassword($('#txtPassword').val())  &&  $('#txtPassword').val()!= '' && $('#txtFirstName').val()!= '' && $('#txtLastName').val() &&   $('#txtCountry').val() != ''  &&  $('#txtCity').val()!= '' && $('#txtAddress').val()!= ''  && $('#txtNumber').val()!=''  ){
                                                               btnAdd.disabled = false
                                        }else if( $("#myform input[type='radio']:checked").length == 0){

                                                     btnAdd.disabled = true
                                                     $(this).addClass(`alert-danger`);
                                                     $('#pharmacyTable').css('border-color', 'red');
                                                     $("#errorAdmin").text("Please select one  Pharmacy!")
                                                     $('#errorAdmin').css('color', 'red');
                                                 }else {
                                                     $(this).removeClass(`alert-danger`);
                                                     $('#pharmacyTable').css('border-color', '');
                                                     $("#errorAdmin").text("")
                                                 }
                              if($('#txtCity').val() == ''){
                                  btnAdd.disabled = true
                                  $(this).addClass(`alert-danger`);
                                  $('#txtCity').css('border-color', 'red');
                                  $("#error").text("Please enter all fields!")
                                  $('#error').css('color', 'red');
                              }else {
                                  $(this).removeClass(`alert-danger`);
                                  $('#txtCity').css('border-color', '');
                                  $("#error").text("")
                              }
                        });
               $('#txtAddress').keyup(function () {
                 if($('#txtEmail').val() != '' && validateEmail($('#txtEmail').val()) && validatePassword($('#txtPassword').val())  &&  $('#txtPassword').val()!= '' && $('#txtFirstName').val()!= '' && $('#txtLastName').val() &&   $('#txtCountry').val() != ''  &&  $('#txtCity').val()!= '' && $('#txtAddress').val()!= ''  && $('#txtNumber').val()!=''  ){
                                                                     btnAdd.disabled = false
                                              }else if( $("#myform input[type='radio']:checked").length == 0){

                                                           btnAdd.disabled = true
                                                           $(this).addClass(`alert-danger`);
                                                           $('#pharmacyTable').css('border-color', 'red');
                                                           $("#errorAdmin").text("Please select one  Pharmacy!")
                                                           $('#errorAdmin').css('color', 'red');
                                                       }else {
                                                           $(this).removeClass(`alert-danger`);
                                                           $('#pharmacyTable').css('border-color', '');
                                                           $("#errorAdmin").text("")
                                                       }
                     if($('#txtAddress').val() == ''){
                         btnAdd.disabled = true
                         $(this).addClass(`alert-danger`);
                         $('#txtAddress').css('border-color', 'red');
                         $("#error").text("Please enter all fields!")
                         $('#error').css('color', 'red');
                     }else {
                         $(this).removeClass(`alert-danger`);
                         $('#txtAddress').css('border-color', '');
                         $("#error").text("")
                     }
               });
                $('#txtNumber').keyup(function () {
                               if($('#txtEmail').val() != '' && validateEmail($('#txtEmail').val()) && validatePassword($('#txtPassword').val())  &&  $('#txtPassword').val()!= '' && $('#txtFirstName').val()!= '' && $('#txtLastName').val() &&   $('#txtCountry').val() != ''  &&  $('#txtCity').val()!= '' && $('#txtAddress').val()!= ''  && $('#txtNumber').val()!=''  ){
                                                      btnAdd.disabled = false
                               }else if( $("#myform input[type='radio']:checked").length == 0){
                                            btnAdd.disabled = true
                                            $(this).addClass(`alert-danger`);
                                            $('#pharmacyTable').css('border-color', 'red');
                                            $("#errorAdmin").text("Please select one  Pharmacy!")
                                            $('#errorAdmin').css('color', 'red');
                                        }else {
                                            $(this).removeClass(`alert-danger`);
                                            $('#pharmacyTable').css('border-color', '');
                                            $("#errorAdmin").text("")
                                        }
                                    if($('#txtNumber').val() == ''){
                                        btnAdd.disabled = true
                                        $(this).addClass(`alert-danger`);
                                        $('#txtNumber').css('border-color', 'red');
                                        $("#error").text("Please enter all fields!")
                                        $('#error').css('color', 'red');
                                    }else {
                                        $(this).removeClass(`alert-danger`);
                                        $('#txtNumber').css('border-color', '');
                                        $("#error").text("")
                                    }
                              });

                if( $("#myform input[type='radio']:checked").length == 0){
                                                        btnAdd.disabled = true
                                                        $(this).addClass(`alert-danger`);
                                                        $('#pharmacyTable').css('border-color', 'red');
                                                        $("#errorAdmin").text("Please select one  Pharmacy!")
                                                        $('#errorAdmin').css('color', 'red');
                                                    }else {

                                                        $(this).removeClass(`alert-danger`);
                                                        $('#pharmacyTable').css('border-color', '');
                                                        $("#errorAdmin").text("")
                                                    }

})

  function validateEmail(email) {
	    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	    return re.test(String(email).toLowerCase());
}

  function validatePassword(password) {

	  var strongRegex = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})");
	  	if(password.match(strongRegex)) {
	  		return true;
	  	}
	  	else {
	  		return false;
	  	}
}