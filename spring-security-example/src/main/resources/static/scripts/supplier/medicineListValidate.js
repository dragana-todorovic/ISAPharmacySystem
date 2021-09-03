$(document).ready(function(e){
    btnAdd = document.getElementById("submitAddNewMedicineWithQuantity")
        btnAdd.disabled = true


         $('#txtNewMedicineQuantity').keyup(function () {
                    if( $('#txtNewMedicineQuantity').val()!= '' && $("input[name='newMedicineButton']:checked").val() >0 ){
                        btnAdd.disabled = false
                        }
                      if($('#txtNewMedicineQuantity').val() == ''){
                          btnAdd.disabled = true
                          $(this).addClass(`alert-danger`);
                          $('#txtNewMedicineQuantity').css('border-color', 'red');
                          $("#error").text("Please enter all fields!")
                          $('#error').css('color', 'red');
                      }else {
                          $(this).removeClass(`alert-danger`);
                          $('#txtNewMedicineQuantity').css('border-color', '');
                          $("#error").text("")
                      }
                });

                                 if(   $("input[name='newMedicineButton']:checked").length ==0){
                                        btnAdd.disabled = true
                                        $(this).addClass(`alert-danger`);
                                        $("#error").text("Please enter medicine you want to add!")
                                        $('#error').css('color', 'red');
                                    }else {
                                        $(this).removeClass(`alert-danger`);
                                        $("#error").text("")
                                    }


//allMedicineTable
    $('#allMedicineTable').on('click',function(event){
     if($('#txtNewMedicineQuantity').val() == ''){
                              btnAdd.disabled = true
                              $(this).addClass(`alert-danger`);
                              $('#txtNewMedicineQuantity').css('border-color', 'red');
                              $("#error").text("Please enter all fields!")
                              $('#error').css('color', 'red');
                          }else{
        btnAdd.disabled = false
         $(this).removeClass(`alert-danger`);
        $("#error").text("")
        }
     })

})
