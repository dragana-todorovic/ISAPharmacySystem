$(document).ready(function(e){

	var email = localStorage.getItem('email')
	 $("#allPatients").click(function () {
		customAjax({
		      url: '/pharm/getAllPatientsForSearch',
		      method: 'GET',
		      success: function(patients){
	let temp='';
				for (var i in patients){
					
					console.log(patients[i].firstName)
					
			temp+=`<tr><td>`+patients[i].firstName+`</td><td>`+patients[i].lastName+`</td>`;
			
			temp+=`</tr>`;
		}
					 $("#showData").html(`<br><label>Pretrazi po imenu pacijenta:</label><input style="color: black;" type="text"  min="0" id="name" style="width:10%" placeholder="Name"/>
				<br><label>Pretrazi po prezimenu pacijenta:</label><input style="color: black;" type="text"  min="0" id="surname" style="width:10%" placeholder="Surname"/>
				<table id="table" class="ui large table" style="width:50%; margin-left:auto; 
    margin-right:auto; margin-top: 40px;">
		        <thead>
		            <tr class="success">
		                <th colspan="3" class = "text-info" style= "text-align:center;">My patients</th>
		            </tr>
						
		        </thead>
		       <tbody id="myPatientsList">
	        </tbody>
		        <tfoot class="full-width">
    <tr>

    </tr>
  </tfoot>
		    </table> <p id="er"> </p>`);
$('#myPatientsList').html(temp);
$("#name").keyup(function () {
        var nadji = ($('#name').val()).toLowerCase();
        $("#table tbody tr").each(function () {
            var gost = ($('td:eq(0)', this).text()).toLowerCase();
            if (gost.includes(nadji) || nadji == "") {
                $(this).show()
            } else {
                $(this).hide()
            }
        });
    });
$("#surname").keyup(function () {
        var nadji = ($('#surname').val()).toLowerCase();
        $("#table tbody tr").each(function () {
            var gost = ($('td:eq(1)', this).text()).toLowerCase();
            if (gost.includes(nadji) || nadji == "") {
                $(this).show()
            } else {
                $(this).hide()
            }
        });
    });
	
 
		    },	 
			       
		      error: function(){
		      }
	
	});
	
		
	});
	

});

function getCellValue(row, index) {
    return $(row).children('td').eq(index).text()
};


