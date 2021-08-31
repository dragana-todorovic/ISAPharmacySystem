$(document).ready(function(){
	/*$("#searchMedicine").click(function () {
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
 */
});