let eventsForCalendar
let events;
$(document).ready(function(e){ 
	$('#appointments').click(function() {
		
		console.log("Usao");
		events = []
	
	var email = localStorage.getItem('email');
	

	customAjax({
		      url: '/pharm/getPharmacistsCounseling/' + email,
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
					morecontent:"Start time:"+data[i].startDateTime.split("T")[1],
					pharmacyName :"Pharmacy name:"+data[i].pharmacyName
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
            element.find('.fc-title').append("<br/>" + event.description).append("<br/>" + event.morecontent).append("<br/>" + event.pharmacyName); 
        },
		eventClick: function(event){
			$('#calendar').html(eventFire(document.getElementById('startAppointment'), 'click'));
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