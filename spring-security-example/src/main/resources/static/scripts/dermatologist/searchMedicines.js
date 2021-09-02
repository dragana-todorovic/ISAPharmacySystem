$(document).ready(function(e){

	var email = localStorage.getItem('email')
	 $("#searchMedicinesAdmin").click(function () {
		console.log("Usao u klik za search medicines")
		$("#showData").html(` <div  class="container4" style = "color : white">
        <table  class="ui large table"
                style="width:50%; margin-left:auto;
			    margin-right:auto; margin-top: 40px;"
                id="searchMedTable" >
            <thead >
            <tr>
                <th colspan="7">
                    <div class="ui input left">
                        <input type="text" placeholder="Search by name..." id="nameSearchMedicine">
                    </div>
                    <h4 >Filter Medicine By Type :</h4>
                    <p >
                        <input type="radio" id="ON_HOLD" name="postage" value="ANTIBIOTIC" /> ANTIBIOTIC
                        <input type="radio" id="ACCEPTED" name="postage" value="ANESTHETIC" /> ANESTHETIC
                        <input type="radio" id="REJECTED" name="postage" value="ANTIHISTAMINE" /> ANTIHISTAMINE
                        <input type="radio" id="NO_FILTER" name="postage" value="NO_FILTER" /> NO FILTER
                    </p>
                    <h4 >Filter Medicine By Shape :</h4>
                    <p >
                        <input type="radio" id="CAPSULE" name="postageS" value="CAPSULE" /> CAPSULE
                        <input type="radio" id="TABLET" name="postageS" value="TABLET" /> TABLET
                        <input type="radio" id="POWDER" name="postageS" value="POWDER" /> POWDER
                        <input type="radio" id="CREAM" name="postageS" value="CREAM" /> CREAM
                        <input type="radio" id="OIL" name="postageS" value="OIL" /> OIL
                        <input type="radio" id="SYRUP" name="postageS" value="SYRUP" /> SYRUP
                        <input type="radio" id="NO_FILTER_SHAPE" name="postageS" value="NO_FILTER_SHAPE" /> NO FILTER
                    </p>
                </th>
            </tr>
            <tr class="success"><th>Code</th>
                <th>Name</th>
                <th>Ratings</th>
                <th>Shape</th>
                <th>Type</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody
                   id="searchMedicineTable">
            </tbody>
        </table>
    </div>

    <div id="modal-search-medicines" class="modal">
        <div class="modal-content" style="text-align: center;">
            <span class="closeSearchMedicine">&times;</span>
            <table class="centered">
                <thead >
                <tr>
                    <th colspan="2">
                        <div>
                            <b>Details About Medicine </b>
                        </div>
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>Content:</td>
                    <td class="ui input small"> <input disabled type="text" id="txtContent" /></td>

                </tr>
                <tr>
                    <td>Producer:</td>
                    <td class="ui input small"> <input disabled type="text" id="txtProducer" /></td>

                </tr>
                <tr>
                    <td>Can Medicine be Taken With Prescription:</td>
                    <td class="ui input small"> <input disabled type="text" id="withPrescriptionTrue" /></td>
                </tr>
                <tr>
                    <td>Substitute Medicine Codes:</td>
                    <td class="ui input small"> <input disabled type="text" id="txtSubstituteMedicineCodes" /></td>

                </tr>
                <tr>
                    <td>Notes: </td>
                    <td class="ui input small"> <input disabled type="text" id="txtNotes" /></td>
                </tr>
                <tr>
                    <td>Contradiction: </td>
                    <td class="ui input small"> <input disabled type="text" id="txtContradiction" /></td>
                </tr>
                <tr>
                    <td>Advised daily dose: </td>
                    <td class="ui input small"> <input disabled type="number" id="txtAdvisedDailyDose" /></td>
                </tr>
                </tbody>
                <tfoot class="full-width">
                </tfoot>
            </table>
        </div>
    </div>

    <!-- modal for all pharamcies-->
    <div id="modal-search-pharmacy" class="modal">
        <div class="modal-content" style="text-align: center;">
            <span class="closeSearchPharmacy">&times;</span>
            <table  class="ui centered large table"
                   style="width:50%; margin-left:auto;
			    margin-right:auto; margin-top: 40px;">
                <thead>
                <tr>
                    <th colspan="4">
                            <b>All Pharmacies Where Medicine Is Available</b>
                    </th>
                </tr>
                <tr><th>Pharmacy Name</th>
                    <th colspan="2">Address</th>
                    <th>Price</th>
                </tr></thead>
                <tbody id="searchPharmacyTable" >
                </tbody>
            </table>
        </div>
    </div>`);
		});
		});