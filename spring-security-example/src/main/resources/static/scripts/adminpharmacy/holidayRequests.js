
let showRequestsD = function(idSelected,data) {
	let temp = '';
	for(i in data) {
		temp += `<tr>
		<td>`+data[i].startDate+`</td>
		<td>`+data[i].endDate+`</td>
		<td>`+data[i].status+`</td>`;
		if(data[i].status == "ON_HOLD"){
		temp+=`<td><button id = "`+data[i].id+`" name="prihvatiZahtjev" class="ui positive button">
		<i class="check icon"></i>
		Accept
		</button>
		<button id = "`+data[i].id+`" name="odbijZahtjev" class="ui negative button">
		<i class="close icon"></i>
		Decline
		</button>
		</td>`} else {
			temp+=`<td></td>`
		}
		temp+=`</tr>`;
	}
	$('#zahtjeviTabela').html(temp)
	
	$("button[name=prihvatiZahtjev]").click(function() {
		var id = this.id;
		console.log(id)
		customAjax({
		      url: '/pharmacy/acceptHolidayRequest/' + id + '/' + idSelected,
		      method: 'POST',
		      contentType: 'application/json',
		      success: function(){
		    	  alert("Success accept!")
		    	  location.href = "adminpharmacy.html"
		    	  
		      },
		      error: function(){

		    	  alert("Error")
		      }

	 });
	});
		
		$("button[name=odbijZahtjev]").click(function() {
			id = this.id;
			  $('#modalniZaOdbijanje')
			  .modal('show')
		});
		$('#decline').click(function() {
			var reason = $('#reason').val()
			customAjax({
			      url: '/pharmacy/declineHolidayRequest/' + id + '/' + idSelected + '/' + reason,
			      method: 'POST',
			      contentType: 'application/json',
			      success: function(){
			    	  alert("Success declined!")
			    	  location.href = "adminpharmacy.html"
			    	  
			      },
			      error: function(){

			    	  alert("Error")
			      }

		 });
			
		});
}


let showRequestsP = function(idSelected,data) {
	let temp = '';
	for(i in data) {
		temp += `<tr>
		<td>`+data[i].startDate+`</td>
		<td>`+data[i].endDate+`</td>
		<td>`+data[i].status+`</td>`;
		if(data[i].status == "ON_HOLD"){
		temp+=`<td><button id = "`+data[i].id+`" name="prihvatiZahtjev" class="ui positive button">
		<i class="check icon"></i>
		Accept
		</button>
		<button id = "`+data[i].id+`" name="odbijZahtjev" class="ui negative button">
		<i class="close icon"></i>
		Decline
		</button>
		</td>`} else {
			temp+=`<td></td>`
		}
		temp+=`</tr>`;
	}
	$('#zahtjeviTabela').html(temp)
	
	$("button[name=prihvatiZahtjev]").click(function() {
		var id = this.id;
		console.log(id)
		customAjax({
		      url: '/pharmacy/acceptHolidayRequestP/' + id + '/' + idSelected,
		      method: 'POST',
		      contentType: 'application/json',
		      success: function(){
		    	  alert("Success accept!")
		    	  location.href = "adminpharmacy.html"
		    	  
		      },
		      error: function(){

		    	  alert("Error")
		      }

	 });
	});
		
	
	$("button[name=odbijZahtjev]").click(function() {
		id = this.id;
		  $('#modalniZaOdbijanje')
		  .modal('show')
	});
	$('#decline').click(function() {
		var reason = $('#reason').val()
		customAjax({
		      url: '/pharmacy/declineHolidayRequestP/' + id + '/' + idSelected + '/' + reason,
		      method: 'POST',
		      contentType: 'application/json',
		      success: function(){
		    	  alert("Success declined!")
		    	  location.href = "adminpharmacy.html"
		    	  
		      },
		      error: function(){

		    	  alert("Error")
		      }

	 });
		
	});
}

