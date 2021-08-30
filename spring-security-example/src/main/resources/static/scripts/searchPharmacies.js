function showPharmaciesHome(data){
	let temp='';
	for (i in data){
		temp+=`<tr id="`+data[i].id+`">
			<td>`+data[i].name+`</td>
			<td>`+data[i].address.street+`</td>
			<td>`+data[i].address.city+`</td>
			<td>`+data[i].description+`</td>
			</tr>`;
	}
	$('#pharmacyHomeTable').html(temp);
}

$(document).ready(function(){
	/*$("#searchPharmacies").click(function () {
		$('#all_pharmacies_show').attr('hidden',true);
		$('#all_medicine_show').attr('hidden',true);
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
function showPharmacies(data){
	let temp='';
	for (i in data){
		temp+=`<tr id="`+data[i].id+`">
			<td>`+data[i].name+`</td>
			<td>`+data[i].address.city+`</td>
			<td>`+data[i].address.street+`</td>
			<td>`+data[i].description+`</td>
			</tr>`;
	}
	$('#all_pharmacies_table').html(temp);
	$('#all_pharmacies_show').attr('hidden',false);
}
*/
       //MILICA RADILA ZA SVE APOTEKE
       customAjax({
             url: '/auth/getAllPharmacies',
             method: 'GET',
               contentType: 'application/json',
               success: function (data) {
                   showPharmaciesHome(data);
               },
               error: function (message) {
                   //alert("Failed")
               }
       })

       $("#nameSearchPharm").keyup(function () {
           var firstNameSearch2 = ($('#nameSearchPharm').val()).toLowerCase();

           $("#searchPharmTable tbody tr").each(function () {
               var name = ($('td:eq(0)', this).text()).toLowerCase();
               if (name.includes(firstNameSearch2) || firstNameSearch2 == "") {
                   $(this).show()
               } else {
                   $(this).hide()
               }
           });
       });

});