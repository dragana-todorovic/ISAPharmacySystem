let eventsForCalendar
let events;
let pharmacy;
$(document).ready(function(e){ 
	$('#appointments').click(function() {
		
		console.log("Usao");
		events = []
	
	var email = localStorage.getItem('email');
	customAjax({
		      url: '/derm/getPharmaciesForDermatologist/' + email,
		      method: 'GET',
		      async: false,
		      contentType: 'application/json',
		      success: function(pharmacies){
			 $("#showData").html(`
				<table id="table" class="ui large table" style="width:50%; margin-left:auto; 
    margin-right:auto; margin-top: 40px;">
		       
		        <tbody>
		              
		            <tr>
		                <td>Choose pharmacy:</td>
						<td><select id="dropdownPharmacies" >
      <option value="" ></option></select>

</td>		                
	            
		        </tbody>
 <tfoot class="full-width">
			    <tr>
			      <th></th>
			      <th colspan="2">
 						<input id = "choosePharmacy" class="ui right floated positive basic button" type = "button" value = "Choose pharmacy"></input>			    
			      </th>
			    </tr>
 <p id="errorInput"> </p>
			  </tfoot>
		    </table><div id="showCalendar"></div>

 						
			    
			     <p id="errorInput"> </p>`);

			for (var i = 0; i < pharmacies.length; i++) { 
						console.log(pharmacies[i].name)
						console.log(pharmacies[i].id)
					 var option = document.createElement("option");
					 var dropdown = document.getElementById("dropdownPharmacies");
					 option.text = (pharmacies[i].name+",sifra "+pharmacies[i].id);
					 dropdown.add(option); }
				
				
			
			
		},
			error:function(){
				
			}
			});

$('#choosePharmacy').click(function(){
	
var e = document.getElementById("dropdownPharmacies");
console.log(e.value);
pharmacy = e.value
if(e.value!=""){
	$("#errorInput").text("")
	customAjax({
		      url: '/derm/getDermatologistAppointments/' + email+"/"+pharmacy,
		      method: 'GET',
		      async: false,
		      contentType: 'application/json',
		      success: function(data){
			console.log("Usao u success")
		    	  for(i in data) {
					console.log(data[i].patientLastName)
					if(data[i].patientLastName!=null){
					events.push({
			          id: data[i].id,
			          title: data[i].patientName+" "+ data[i].patientLastName,
			          start: data[i].startTime,
					 description: "Duration: "+data[i].duration,
					morecontent:"Start time:"+data[i].startDateTime.split("T")[1]
			          // end: doc.to_date
        });}

					
		    	  }console.log(events)
		    	  console.log(eventsForCalendar)

		      },
		      error: function(){
			console.log("Usao u error")
		      }
		 });
let temp = ''
		temp += `<div  id="calendar"></div>`;
		
	$('#showData').html(temp)
	
	
	 $('#calendar').fullCalendar({
              weekend:true,
editable: false,
                selectable: true,
                selectMirror: true,
                dayMaxEvents: true,
			  events:{ events},
 
      eventRender: function(event, element) { 
            element.find('.fc-title').append("<br/>" + event.description).append("<br/>" + event.morecontent); 
        },
		eventClick: function(event){
			$('#calendar').html(eventFire(document.getElementById('startAppointment'), 'click'));
		}
                
  });
}
else{
	$("#errorInput").text("Choose pharmacy!")
	$('#errorInput').css('color', 'red');
}


	
			
	});		
	


	});
	});
	function eventFire(el, etype){
  if (el.fireEvent) {
    el.fireEvent('on' + etype);
  } else {
    var evObj = document.createEvent('Events');
    evObj.initEvent(etype, true, false);
    el.dispatchEvent(evObj);
  }
}