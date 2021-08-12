$(document).ready(function(e){

	var email = localStorage.getItem('email')
	 $("#myPatients").click(function () {
		customAjax({
		      url: '/derm/getMyPatients/' + email,
		      method: 'GET',
		      success: function(patients){
	let temp='';
				for (var i in patients){
					
					console.log(patients[i].name)
					
			temp+=`<tr><td>`+patients[i].name+`</td><td>`+patients[i].surname+`</td><td>`+patients[i].startDateTime.split("T")[0]+`</td>`;
			
			temp+=`</tr>`;
		}
					 $("#showData").html(`<br><label>Pretrazi po imenu pacijenta:</label><input style="color: black;" type="text"  min="0" id="name" style="width:10%" placeholder="Name"/>
				<br><label>Pretrazi po imenu pacijenta:</label><input style="color: black;" type="text"  min="0" id="surname" style="width:10%" placeholder="Surname"/>
				<table id="table" class="ui large table" style="width:50%; margin-left:auto; 
    margin-right:auto; margin-top: 40px;">
		        <thead>
		            <tr class="success">
		                <th colspan="3" class = "text-info" style= "text-align:center;">My patients</th>
		            </tr>
						<tr>
						<th name="sortByName">Name<span  name="strelica" class="glyphicon glyphicon-arrow-down" /></th>
						<th name="sortBySurname">Surname <span  name="strelica" class="glyphicon glyphicon-arrow-down" /></th>
						<th name="sortByDate">Start date time<span  name="strelica" class="glyphicon glyphicon-arrow-down" /></th>
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
$("th[name=sortByDate]").click(function () {
        if ($(this.getElementsByTagName("span")).attr(`class`) == "glyphicon glyphicon-arrow-down") {
            $(this.getElementsByTagName("span")).removeClass("glyphicon glyphicon-arrow-down");
            $(this.getElementsByTagName("span")).toggleClass("glyphicon glyphicon-arrow-up");
        } else {
            $(this.getElementsByTagName("span")).removeClass("glyphicon glyphicon-up-down");
            $(this.getElementsByTagName("span")).toggleClass("glyphicon glyphicon-arrow-down");
        }
        var table = $(this).parents('table').eq(0)
        var rows = table.find('tr:gt(1)').toArray().sort(comparer($(this).index()))
        this.asc = !this.asc
        if (!this.asc) { rows = rows.reverse() }
        for (var i = 0; i < rows.length; i++) { table.append(rows[i]) }
    });
$("th[name=sortBySurname]").click(function () {
        if ($(this.getElementsByTagName("span")).attr(`class`) == "glyphicon glyphicon-arrow-down") {
            $(this.getElementsByTagName("span")).removeClass("glyphicon glyphicon-arrow-down");
            $(this.getElementsByTagName("span")).toggleClass("glyphicon glyphicon-arrow-up");
        } else {
            $(this.getElementsByTagName("span")).removeClass("glyphicon glyphicon-up-down");
            $(this.getElementsByTagName("span")).toggleClass("glyphicon glyphicon-arrow-down");
        }
        var table = $(this).parents('table').eq(0)
        var rows = table.find('tr:gt(1)').toArray().sort(comparer($(this).index()))
        this.asc = !this.asc
        if (!this.asc) { rows = rows.reverse() }
        for (var i = 0; i < rows.length; i++) { table.append(rows[i]) }
    });
$("th[name=sortByName]").click(function () {
        if ($(this.getElementsByTagName("span")).attr(`class`) == "glyphicon glyphicon-arrow-down") {
            $(this.getElementsByTagName("span")).removeClass("glyphicon glyphicon-arrow-down");
            $(this.getElementsByTagName("span")).toggleClass("glyphicon glyphicon-arrow-up");
        } else {
            $(this.getElementsByTagName("span")).removeClass("glyphicon glyphicon-up-down");
            $(this.getElementsByTagName("span")).toggleClass("glyphicon glyphicon-arrow-down");
        }
        var table = $(this).parents('table').eq(0)
        var rows = table.find('tr:gt(1)').toArray().sort(comparer($(this).index()))
        this.asc = !this.asc
        if (!this.asc) { rows = rows.reverse() }
        for (var i = 0; i < rows.length; i++) { table.append(rows[i]) }
    });
				
 
		    },	 
			       
		      error: function(){
		      }
	
	});
	
		
	});
	

});
function comparer(index) { //ZA SORTIRANJE!
    return function (a, b) {
        var valA = getCellValue(a, index), valB = getCellValue(b, index)
        return $.isNumeric(valA) && $.isNumeric(valB) ? valA - valB : valA.toString().localeCompare(valB)
    }
};
function getCellValue(row, index) {
    return $(row).children('td').eq(index).text()
};


