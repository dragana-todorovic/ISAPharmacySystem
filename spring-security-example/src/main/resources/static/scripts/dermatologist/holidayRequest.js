$(document).ready(function(e){	
	 $("#holidayRequest").click(function () {
		$("#showData").html(`<table class="ui large table" style="width:50%; margin-left:auto; 
    margin-right:auto; margin-top: 40px;">
		        <thead>
		            <tr class="success">
		                <th colspan="2" class = "text-info" style= "text-align:center;">Holiday request</th>
		            </tr>
		        </thead>
		        <tbody>
		            <tr>
		                <td>Start date:</td>		
						<td><input type="text" id="datepickerStartDate"></td>	
							                
		            </tr>
		            <tr>
		                <td>End date:</td>
						<td><input type="text" id="datepickerEndDate"></td>
						
		                
		            </tr>
		           
		        </tbody>
		        <tfoot class="full-width">
    <tr>
      <th></th>
      <th colspan="2">     
          <input id = "sendRequest" class="ui right floated teal basic button" type = "button" value = "Send request"></input>
		  
       
      </th>
    </tr>
  </tfoot>
		    </table> <p id="er"> </p>`);

 


$( "#datepickerStartDate" ).datepicker({
	format: 'yyyy-mm-dd'
});
  $( function() {
    $( "#datepickerEndDate" ).datepicker({
	format: 'yyyy-mm-dd'
});
  } );


		$('#sendRequest').click(function(){	
			var startDate = $('#datepickerStartDate').val()
			var endDate = $('#datepickerEndDate').val()
			
			var email = localStorage.getItem('email')
			if(startDate!="" && endDate!="" && email!=null){
			
			var todayDate = new Date();
			startDate = new Date(startDate);
			
			endDate = new Date(endDate);
			if(endDate>startDate){
			if(startDate>=todayDate){
			
			//startDate=startDate.getFullYear()+'-' +(startDate.getMonth()+1) + '-'+startDate.getDate();
			startDate=formatDate(startDate)
			console.log(startDate)
			endDate=formatDate(endDate)
			console.log(endDate)
			
			
			obj = JSON.stringify({startDate:startDate,endDate:endDate,email:email});
			customAjax({
		        method:'POST',
		        url:'/derm/holidayRequest',
		        data : obj,
		        contentType: 'application/json',
		        success: function(){
					
		        	alert("Success sent request!")
					location.href = "dermatologist.html";
				},
				error: function(){
					
					alert("Can't send holiday request. It's not dermatologist's working time.'")
				}
		            });
		}else{
			alert("Start date needs to be after today!")
			
		}}else{
			alert("End date needs to be after start date!")
			
		}}else{
			alert("You haven't filled all fields or you are not logged in! Please try again!")
			
		}
			
	 });
			
	 

		
	});

	});
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