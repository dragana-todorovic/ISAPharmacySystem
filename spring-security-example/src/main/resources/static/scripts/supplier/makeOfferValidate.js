$(document).ready(function(e){
    btnAdd = document.getElementById("submitOffer")
        btnAdd.disabled = true


         $('#txtPrice').keyup(function () {
                    if(   $('#txtPrice').val()!= '' && $('#timeOfOrder').val() !=''    && $('#validFrom').val()!=''){
                        btnAdd.disabled = false
                        }
                      if($('#txtPrice').val() == ''){
                          btnAdd.disabled = true
                          $(this).addClass(`alert-danger`);
                          $('#txtPrice').css('border-color', 'red');
                          $("#error").text("Please enter all fields!")
                          $('#error').css('color', 'red');
                      }else {
                          $(this).removeClass(`alert-danger`);
                          $('#txtPrice').css('border-color', '');
                          $("#error").text("")
                      }
                });
         $('#timeOfOrder').keyup(function () {
          if(   $('#txtPrice').val()!= '' && $('#timeOfOrder').val()!=''    && $('#validFrom').val()!=''){
                                 btnAdd.disabled = false
                                 }
           if($('#timeOfOrder').val() == ''){
               btnAdd.disabled = true
               $(this).addClass(`alert-danger`);
               $('#timeOfOrder').css('border-color', 'red');
               $("#error").text("Please enter all fields!")
               $('#error').css('color', 'red');
           }else {
               $(this).removeClass(`alert-danger`);
               $('#timeOfOrder').css('border-color', '');
               $("#error").text("")
           }
     });




                $('#validFrom').keyup(function () {
                               if(   $('#txtPrice').val()!= '' && $('#timeOfOrder').val()!=''   && $('#validFrom').val()!=''){
                                                      btnAdd.disabled = false
                                                      }
                                    if($('#validFrom').val() == ''){
                                        btnAdd.disabled = true
                                        $(this).addClass(`alert-danger`);
                                        $('#validFrom').css('border-color', 'red');
                                        $("#error").text("Please enter all fields!")
                                        $('#error').css('color', 'red');
                                    }else {
                                        $(this).removeClass(`alert-danger`);
                                        $('#validFrom').css('border-color', '');
                                        $("#error").text("")
                                    }
                              });


})
