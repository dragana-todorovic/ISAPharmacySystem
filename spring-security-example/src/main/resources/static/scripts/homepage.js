$(document).ready(function(){
//$.get({
//	url : "ProjectRents/currentUser",
//	 contentType: 'application/json',
//	success : function(data){
//		 if(data){
//             if(data.role == "ADMIN"){
//                 window.location.href="./admin.html";
//             }else if(data.role == "GUEST"){
//                 window.location.href="./guest.html";
//             }
//         }else{
//              window.location.href="./login.html";
//         }
//	}	
//})
	$("#searchPharmacies").click(function () {
		$('#pharmacies_show').attr('hidden',true);
		$('#medicine_show').attr('hidden',true);
	        var let= $("#pharmaciesSearch").val();
	        console.log(let)
	        customAjax({
	            url: '/auth/searchPharmacies/' + let ,
	            method: 'GET',
	            success: function (data) {
					console.log(data)
	                showPharmacies(data);
	            },
	            error: function () {
					console.log("error")
	            }
	
	        });
	
	    });
	$("#searchMedicine").click(function () {
		$('#pharmacies_show').attr('hidden',true);
		$('#medicine_show').attr('hidden',true);
	        var let= $("#medicineSearch").val();
	        console.log(let)
	        customAjax({
	            url: '/auth/searchMedicine/' + let ,
	            method: 'GET',
	            success: function (data) {
					console.log(data)
	                showMedicine(data);
	            },
	            error: function () {
					console.log("error")
	            }
	
	        });
	
	    });
function showPharmacies(data){
	let temp='';
	for (i in data){
		temp+=`<tr id="`+data[i].id+`">
			<td>`+data[i].name+`</td>
			<td>`+data[i].address+`</td>
			<td>`+data[i].description+`</td>
			</tr>`;
	}
	$('#pharmacies_table').html(temp);
	$('#pharmacies_show').attr('hidden',false);
}
function showMedicine(data){
	let temp='';
	for (i in data){
		temp+=`<tr id="`+data[i].id+`">
			<td>`+data[i].code+`</td>
			<td>`+data[i].name+`</td>
			<td>`+data[i].shape+`</td>
			<td>`+data[i].content+`</td>
			<td>`+data[i].producer+`</td>
			<td>`+data[i].withprescription+`</td>
			<td>`+data[i].notes+`</td>
			</tr>`;
	}
	$('#medicine_table').html(temp);
	$('#medicine_show').attr('hidden',false);
}
 
});