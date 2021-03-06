$(document).ready(function(e){ 
	
	$("#actionsAndBenefits").click(function () {
		  actionandbenefit()
	 });
	

});

let actionandbenefit = function() {
	 $("#showData").html(`<table class="ui basic large table" style="width:50%; margin-left:auto; 
			    margin-right:auto; margin-top: 40px;">
					        <thead>
					            <tr class="success">
					                <th colspan="2" class = "text-info" style= "text-align:center;">Create action and benefit</th>
					            </tr>
					        </thead>
					        <tbody>
					        	<tr>
					        	
					                <td>Valid:</td>
					                <td>
  <div class="ui form">
    <div class="two fields">
      <div class="field">
        <div class="ui calendar" id="rangestart">
          <div class="ui input left icon">
            <i class="calendar icon"></i>
            <input type="text" placeholder="From" id="validFrom" value="`+new Date()+`">
          </div>
        </div>
      </div>
      <div class="field">
        <div class="ui calendar" id="rangeend">
          <div class="ui input left icon">
            <i class="calendar icon"></i>
            <input type="text" placeholder="To" id="validTo" value="`+new Date()+`">
          </div>
        </div>
      </div>
    </div>
  </div></td>
					            </tr>
					            <tr>
					                <td>Description:</td>
					                <td> <div class="ui form">
  <div class="field">
    <textarea rows="2" id = "txtDescription"></textarea>
  </div>
</div></td>

					            </tr>
					           
					           
					        </tbody>
					        <tfoot class="full-width">
					        <tr>
			    <th colspan="2">  <p id="error"></p></th>
			    </tr>
			    <tr>
			      <th></th>
			      <th colspan="2">
					   <input id = "addNew" class="ui right floated positive button" type = "button" value = "Add new action or benefit"></input>
			    
			      </th>
			    </tr>
			    
			  </tfoot>
			 
					    </table>  `);
	 
	 
	 var today = new Date();
	 $('#rangestart').calendar({
		  type: 'date',
		  minDate: new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1),
		  endCalendar: $('#rangeend'),
		  initialDate: new Date()
		});
		$('#rangeend').calendar({
		  type: 'date',
		  startCalendar: $('#rangestart')
		});
		
		btnAdd = document.getElementById("addNew")
		btnAdd.disabled = true


	  $('#txtDescription').keyup(function () {
		  	if($('#txtDescription').val() == ''){
		  		btnAdd.disabled = true
				$(this).addClass(`alert-danger`);
		  		$('#txtDescription').css('border-color', 'red');
		  		$("#error").text("Please, enter description!")
		  		$('#error').css('color', 'red');
		  	}else {
		  		console.log( $('#txtDescription').val())

		  		$(this).removeClass(`alert-danger`);
		  		$('#txtDescription').css('border-color', '');
		  		$("#error").text("")
				btnAdd.disabled = false;
		  	}
	  });	
		
		
	$('#addNew').click(function() {
		
		let validFrom=formatDate($('#validFrom').val())
		let validTo=formatDate($('#validTo').val())
		let description=$('#txtDescription').val()
		let pharmacyAdmin=localStorage.getItem('email')

		obj = JSON.stringify({

		startDate:validFrom,
		endDate:validTo,
		description: description,
		pharmacyAdminEmail: pharmacyAdmin
		});
		
		customAjax({
		      url: '/actionsAndBenefits/addNew',
		      method: 'POST',
		      data:obj,
			  contentType: 'application/json',
			        success: function(){
			        	alert('Successfully published!')
			        	location.href = "adminpharmacy.html";
			        	
					},
				      error: function(){
				       	$("#error").text("Please enter valid date and time!")
				  		$('#error').css('color', 'red');
				      }
		    });
	});
	
}

function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) 
        month = '0' + month;
    if (day.length < 2) 
        day = '0' + day;

    return [year, month, day].join('-');
}


