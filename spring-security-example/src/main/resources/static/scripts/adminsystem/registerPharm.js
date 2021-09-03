$(document).ready(function(e){
        btnAdd = document.getElementById("submitRegisterPharmacy")
        btnAdd.disabled = true

       
         $('#txtName').keyup(function () {
                    if(   $('#txtName').val()!= '' && $('#txtStreet').val() &&  $('#txtCity').val()!= ''   && $('#txtDescription').val()!=''){
                        btnAdd.disabled = false
                        }
                      if($('#txtName').val() == ''){
                          btnAdd.disabled = true
                          $(this).addClass(`alert-danger`);
                          $('#txtName').css('border-color', 'red');
                          $("#error").text("Please enter all fields!")
                          $('#error').css('color', 'red');
                      }else {
                          $(this).removeClass(`alert-danger`);
                          $('#txtName').css('border-color', '');
                          $("#error").text("")
                      }
                });
         $('#txtStreet').keyup(function () {
          if(   $('#txtName').val()!= '' && $('#txtStreet').val()  &&   $('#txtCity').val()!= ''   && $('#txtDescription').val()!=''){
                                 btnAdd.disabled = false
                                 }
           if($('#txtStreet').val() == ''){
               btnAdd.disabled = true
               $(this).addClass(`alert-danger`);
               $('#txtStreet').css('border-color', 'red');
               $("#error").text("Please enter all fields!")
               $('#error').css('color', 'red');
           }else {
               $(this).removeClass(`alert-danger`);
               $('#txtStreet').css('border-color', '');
               $("#error").text("")
           }
     });


          $('#txtCity').keyup(function () {
           if(   $('#txtName').val()!= '' && $('#txtStreet').val() &&    $('#txtCity').val()!= ''   && $('#txtDescription').val()!=''){
                                  btnAdd.disabled = false
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
            
                $('#txtDescription').keyup(function () {
                               if(   $('#txtName').val()!= '' && $('#txtStreet').val() &&    $('#txtCity').val()!= ''   && $('#txtDescription').val()!=''){
                                                      btnAdd.disabled = false
                                                      }
                                    if($('#txtDescription').val() == ''){
                                        btnAdd.disabled = true
                                        $(this).addClass(`alert-danger`);
                                        $('#txtDescription').css('border-color', 'red');
                                        $("#error").text("Please enter all fields!")
                                        $('#error').css('color', 'red');
                                    }else {
                                        $(this).removeClass(`alert-danger`);
                                        $('#txtDescription').css('border-color', '');
                                        $("#error").text("")
                                    }
                              });


})
