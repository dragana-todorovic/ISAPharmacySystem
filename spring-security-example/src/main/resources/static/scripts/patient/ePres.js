
function drawHistoryComplaintTable(data) {
    let table = '';
        for (i in data){
                var medicinesName = "";
                var medicinesCodes = "";
                var medicinesQuantities = "";
                for(x in data[i].medicines){
                    medicinesName += "<b>" +data[i].medicines[x].medicine.name +"</b></br>"
                }
                for(x in data[i].medicines){
                    medicinesCodes += "<b>" +data[i].medicines[x].medicine.code +"</b></br>"
                }
                for(x in data[i].medicines){
                    medicinesQuantities += "<b>" +data[i].medicines[x].quantity +"</b></br>"
                }
            table+=`<tr>
                <td >`+medicinesName+`</td>
                <td >`+medicinesCodes+`</td>
                <td >`+medicinesQuantities+`</td>
            <td>`+data[i].pharmacy.name+`</td>
                <td>`+data[i].issuedDate+`</td>
                </tr>`;

        }

	$('#history_table').html(table);
}
$(document).ready(function() {
	var email = localStorage.getItem('email')
        customAjax({
            method:'GET',
            url:'/patient/getAllEprescriptionByUser/'+email,
            contentType: 'application/json',
            success: function(data){
                console.log("*******************")
                console.log(data)
                drawHistoryComplaintTable(data)
            },
            error: function(){
                console.log("error");
            }
         });

    $('a#historyEpres').click(function(){
            $('#history_div').attr('hidden',false);
            $('#dermatologistComplaintDiv').attr('hidden', true);
            $('#eval').attr('hidden',true);
            $('#my_derm_appointments').attr('hidden',true);
            $('#pharmacy-details').attr('hidden',true);
            $('#all_pharmacies_show').attr('hidden',true);
            $('#edit-profile').attr('hidden', true);
            $('#show').attr('hidden',true);
            $('#derm_appointments').attr('hidden',true);
            $('#ph_av_con').attr('hidden',true)
            $('#ph_con').attr('hidden',true)
            $('#pharmacies_for_derm_appointments').attr('hidden',true);
            $('#shedule_consulting').attr('hidden',true);
            $('#my_ph_appointments').attr('hidden',true);
            $('#medicine_show').attr('hidden',true);
            $('#reserved_medicine_div').attr('hidden',true);
            $('#search-box-medicine').attr('hidden',true);
            $('#qr_code_show').attr('hidden', true);
            $('#pharamcies_with_medicine_show').attr('hidden',true);
            $('#pharmacyComplaintDiv').attr('hidden', true);
            $('#pharmacistComplaintDiv').attr('hidden', true);
     });

});