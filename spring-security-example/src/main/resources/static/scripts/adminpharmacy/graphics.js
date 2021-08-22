let labelsYearDerm 
let dataYearDerm 
let labelsMonthDerm 
let dataMonthDerm
let labelsQDerm 
let dataQDerm 
let labelsYearPharm 
let dataYearPharm 
let labelsMonthPharm 
let dataMonthPharm 
let labelsQPharm 
let dataQPharm
let labelsYearMed
let dataYearMed
let labelsMonthMed
let dataMonthMed
let labelsQMed
let dataQMed
$(document).ready(function(e){ 
	$('#appointmentDStatistic').click(function() {
		labelsYearDerm = []
		dataYearDerm =[]
		labelsMonthDerm =[]
		dataMonthDerm=[]
		labelsQDerm =[]
		dataQDerm =[]
		 customAjax({
		      url: '/pharmacy/getDermatologistAppointmentByYear/' + email,
		      method: 'GET',
		      async: false,
		      contentType: 'application/json',
		      success: function(data){
		    	  for(i in data) {
		    		  labelsYearDerm.push(data[i].time)
		    		  dataYearDerm.push(data[i].data)
		    	  }
		    	  console.log(labelsYearDerm)
		      },
		      error: function(){
		      }
		 });
		 customAjax({
		      url: '/pharmacy/getDermatologistAppointmentByMonth/' + email,
		      method: 'GET',
		      async: false,
		      contentType: 'application/json',
		      success: function(data){
		    	  console.log(data)
		    	  for(i in data) {
		    		  labelsMonthDerm.push(data[i].time)
		    		  dataMonthDerm.push(data[i].data)
		    	  }
		      },
		      error: function(){
		      }
		 });
		 customAjax({
		      url: '/pharmacy/getDermatologistAppointmentByQuarter/' + email,
		      method: 'GET',
		      async: false,
		      contentType: 'application/json',
		      success: function(data){
		    	  for(i in data) {
		    		  labelsQDerm.push(data[i].time)
		    		  dataQDerm.push(data[i].data)
		    	  }
		      },
		      error: function(){
		      }
		 });
		 console.log()
		 let temp = ''
		temp += `<div style="width:20%; margin-left:25%; 
			    margin-right:auto; margin-top: 40px;"><h3>Dermatologists apointment per year</h3><canvas id="myChart"></canvas></div>`;
		temp += `<div style="width:25%; margin-left:25%; 
		    margin-right:auto; margin-top: 30px;"><h3>Dermatologists apointment per month</h3><canvas id="myChart1"></canvas></div>`;
		temp += `<div style="width:25%; margin-left:50%; 
		    margin-right:auto; margin-top: -500px;"><h3>Dermatologists apointment per quarter</h3><canvas id="myChart2"></canvas></div>`;
		
		$('#showData').html(temp)
		
		var ctx = document.getElementById('myChart');
		console.log(labelsYearDerm)
		var myChart = new Chart(ctx, {
		    type: 'doughnut',
		    data: {
		    	 labels: labelsYearDerm,
		    		  datasets: [{
		    		    label: 'Count of dermatologists appoitnments per year',
		    		    data: dataYearDerm,
		    		    backgroundColor: [
		    		      'rgb(20, 99, 132)',
		    		      'rgb(25, 162, 235)',
		    		      'rgb(30, 18, 235)',
		    		      'rgb(35, 100, 235)',
		    		      'rgb(57, 50, 235)',
		    		      'rgb(255, 205, 86)'
		    		    ],
		    		    hoverOffset: 4
		    		  }]
		    },
		    options: {
		        scales: {
		            y: {
		                beginAtZero: true
		            }
		        }
		    }
		});

	
	
	var ctx1 = document.getElementById('myChart1');
	var myChart1 = new Chart(ctx1, {
	    type: 'bar',
	    data: {
	    	 labels: labelsMonthDerm,
	    		  datasets: [{
	    		    label: 'Count of appoitnments per month',
	    		    data: dataMonthDerm,
	    		    backgroundColor: [
	    		      'rgb(200, 99, 132)',
	    		      'rgb(205, 162, 235)',
	    		      'rgb(210, 18, 235)',
	    		      'rgb(215, 100, 235)',
	    		      'rgb(220, 50, 235)',
	    		      'rgb(225, 205, 86)'
	    		    ],
	    		    hoverOffset: 4
	    		  }]
	    },
	    options: {
	        scales: {
	            y: {
	                beginAtZero: true
	            }
	        }
	    }
	});
	
	var ctx2 = document.getElementById('myChart2');
	var myChart2 = new Chart(ctx2, {
	    type: 'bar',
	    data: {
	    	 labels: labelsQDerm,
	    		  datasets: [{
	    		    label: 'Count of appoitnments per quarter',
	    		    data: dataQDerm,
	    		    backgroundColor: [
	    		      'rgb(100, 99, 132)',
	    		      'rgb(105, 16, 235)',
	    		      'rgb(110, 18, 235)',
	    		      'rgb(115, 100, 235)',
	    		      'rgb(120, 50, 235)',
	    		      'rgb(125, 205, 86)'
	    		    ],
	    		    hoverOffset: 4
	    		  }]
	    },
	    options: {
	        scales: {
	            y: {
	                beginAtZero: true
	            }
	        }
	    }
	});

	})
	
	
	$('#appointmentPStatistic').click(function() {
		labelsYearPharm =[]
		dataYearPharm =[]
		labelsMonthPharm =[]
		dataMonthPharm =[]
		labelsQPharm =[]
		dataQPharm=[]
		 customAjax({
		      url: '/pharmacy/getPharmacistConselingByYear/' + email,
		      method: 'GET',
		      async: false,
		      contentType: 'application/json',
		      success: function(data){
		    	  for(i in data) {
		    		  labelsYearPharm.push(data[i].time)
		    		  dataYearPharm.push(data[i].data)
		    	  }
		    	  console.log(labelsYearDerm)
		      },
		      error: function(){
		      }
		 });
		 customAjax({
		      url: '/pharmacy/getPharmacistConselingtByMonth/' + email,
		      method: 'GET',
		      async: false,
		      contentType: 'application/json',
		      success: function(data){
		    	  for(i in data) {
		    		  labelsMonthPharm.push(data[i].time)
		    		  dataMonthPharm.push(data[i].data)
		    	  }
		      },
		      error: function(){
		      }
		 });
		 customAjax({
		      url: '/pharmacy/getPharmacistConselingByQuarter/' + email,
		      method: 'GET',
		      async: false,
		      contentType: 'application/json',
		      success: function(data){
		    	  for(i in data) {
		    		  labelsQPharm.push(data[i].time)
		    		  dataQPharm.push(data[i].data)
		    	  }
		      },
		      error: function(){
		      }
		 });
		let temp =''
		temp += `<div style="width:20%; margin-left:25%; 
			    margin-right:auto; margin-top: 40px;"><h3>Pharmacist conseling per year</h3><canvas id="myChart"></canvas></div>`;
		temp += `<div style="width:25%; margin-left:25%; 
		    margin-right:auto; margin-top: 30px;"><h3>Pharmacist conseling per month</h3><canvas id="myChart1"></canvas></div>`;
		temp += `<div style="width:25%; margin-left:50%; 
		    margin-right:auto; margin-top: -500px;"><h3>Pharmacist conseling per quarter</h3><canvas id="myChart2"></canvas></div>`;
		
		$('#showData').html(temp)
		
		var ctx = document.getElementById('myChart');
		console.log(labelsYearDerm)
		var myChart = new Chart(ctx, {
		    type: 'doughnut',
		    data: {
		    	 labels: labelsYearPharm,
		    		  datasets: [{
		    		    label: 'Count of pharmacist conseling per year',
		    		    data: dataYearPharm,
		    		    backgroundColor: [
		    		      'rgb(20, 99, 132)',
		    		      'rgb(25, 162, 235)',
		    		      'rgb(30, 18, 235)',
		    		      'rgb(35, 100, 235)',
		    		      'rgb(57, 50, 235)',
		    		      'rgb(255, 205, 86)'
		    		    ],
		    		    hoverOffset: 4
		    		  }]
		    },
		    options: {
		        scales: {
		            y: {
		                beginAtZero: true
		            }
		        }
		    }
		});

	
	
	var ctx1 = document.getElementById('myChart1');
	var myChart1 = new Chart(ctx1, {
	    type: 'bar',
	    data: {
	    	 labels: labelsMonthPharm,
	    		  datasets: [{
	    		    label: 'Count of pharmacist conseling per month',
	    		    data: dataMonthPharm,
	    		    backgroundColor: [
	    		      'rgb(200, 99, 132)',
	    		      'rgb(205, 162, 235)',
	    		      'rgb(210, 18, 235)',
	    		      'rgb(215, 100, 235)',
	    		      'rgb(220, 50, 235)',
	    		      'rgb(225, 205, 86)'
	    		    ],
	    		    hoverOffset: 4
	    		  }]
	    },
	    options: {
	        scales: {
	            y: {
	                beginAtZero: true
	            }
	        }
	    }
	});
	
	var ctx2 = document.getElementById('myChart2');
	var myChart2 = new Chart(ctx2, {
	    type: 'bar',
	    data: {
	    	 labels: labelsQPharm,
	    		  datasets: [{
	    		    label: 'Count of pharmacist conseling per quarter',
	    		    data: dataQPharm,
	    		    backgroundColor: [
	    		      'rgb(100, 99, 132)',
	    		      'rgb(105, 16, 235)',
	    		      'rgb(110, 18, 235)',
	    		      'rgb(115, 100, 235)',
	    		      'rgb(120, 50, 235)',
	    		      'rgb(125, 205, 86)'
	    		    ],
	    		    hoverOffset: 4
	    		  }]
	    },
	    options: {
	        scales: {
	            y: {
	                beginAtZero: true
	            }
	        }
	    }
	});

	})
	
	$("#medicineConsumption").click(function() {

		labelsYearMed = []
		dataYearMed = []
		labelsMonthMed = []
		dataMonthMed = []
		labelsQMed = []
		dataQMed = []
		 customAjax({
		      url: '/pharmacy/getMedicineConsumptionByYear/' + email,
		      method: 'GET',
		      async: false,
		      contentType: 'application/json',
		      success: function(data){
		    	  for(i in data) {
		    		  labelsYearMed.push(data[i].time)
		    		  dataYearMed.push(data[i].data)
		    	  }
		      },
		      error: function(){
		      }
		 });
		 
		 customAjax({
		      url: '/pharmacy/getMedicineConsumptionByMonth/' + email,
		      method: 'GET',
		      async: false,
		      contentType: 'application/json',
		      success: function(data){
		    	  for(i in data) {
		    		  labelsMonthMed.push(data[i].time)
		    		  dataMonthMed.push(data[i].data)
		    	  }
		      },
		      error: function(){
		      }
		 });
		 
		customAjax({
		      url: '/pharmacy/getMedicineConsumptionByQuarter/' + email,
		      method: 'GET',
		      async: false,
		      contentType: 'application/json',
		      success: function(data){
		    	  for(i in data) {
		    		  labelsQMed.push(data[i].time)
		    		  dataQMed.push(data[i].data)
		    	  }
		      },
		      error: function(){
		      }
		 });
		
		let temp =''
			temp += `<div style="width:20%; margin-left:25%; 
				    margin-right:auto; margin-top: 40px;"><h3>Medicine consumption per year</h3><canvas id="myChart"></canvas></div>`;
			temp += `<div style="width:25%; margin-left:25%; 
			    margin-right:auto; margin-top: 30px;"><h3>Medicine consumption per month</h3><canvas id="myChart1"></canvas></div>`;
			temp += `<div style="width:25%; margin-left:50%; 
			    margin-right:auto; margin-top: -500px;"><h3>Medicine consumption per quarter</h3><canvas id="myChart2"></canvas></div>`;
			
			$('#showData').html(temp)
			
			var ctx = document.getElementById('myChart');
			console.log(labelsYearDerm)
			var myChart = new Chart(ctx, {
			    type: 'doughnut',
			    data: {
			    	 labels: labelsYearMed,
			    		  datasets: [{
			    		    label: 'Medicine consumption per year',
			    		    data: dataYearMed,
			    		    backgroundColor: [
			    		      'rgb(20, 99, 132)',
			    		      'rgb(25, 162, 235)',
			    		      'rgb(30, 18, 235)',
			    		      'rgb(35, 100, 235)',
			    		      'rgb(57, 50, 235)',
			    		      'rgb(255, 205, 86)'
			    		    ],
			    		    hoverOffset: 4
			    		  }]
			    },
			    options: {
			        scales: {
			            y: {
			                beginAtZero: true
			            }
			        }
			    }
			    
			});
			
		
		
		var ctx1 = document.getElementById('myChart1');
		var myChart1 = new Chart(ctx1, {
		    type: 'bar',
		    data: {
		    	 labels: labelsMonthMed,
		    		  datasets: [{
		    		    label: 'Medicine consumption per month',
		    		    data: dataMonthMed,
		    		    backgroundColor: [
		    		      'rgb(200, 99, 132)',
		    		      'rgb(205, 162, 235)',
		    		      'rgb(210, 18, 235)',
		    		      'rgb(215, 100, 235)',
		    		      'rgb(220, 50, 235)',
		    		      'rgb(225, 205, 86)'
		    		    ],
		    		    hoverOffset: 4
		    		  }]
		    },
		    options: {
		        scales: {
		            y: {
		                beginAtZero: true
		            }
		        }
		    }
		});
		
		var ctx2 = document.getElementById('myChart2');
		var myChart2 = new Chart(ctx2, {
		    type: 'bar',
		    data: {
		    	 labels: labelsQMed,
		    		  datasets: [{
		    		    label: 'Medicine consumption per quarter',
		    		    data: dataQMed,
		    		    backgroundColor: [
		    		      'rgb(100, 99, 132)',
		    		      'rgb(105, 16, 235)',
		    		      'rgb(110, 18, 235)',
		    		      'rgb(115, 100, 235)',
		    		      'rgb(120, 50, 235)',
		    		      'rgb(125, 205, 86)'
		    		    ],
		    		    hoverOffset: 4
		    		  }]
		    },
		    options: {
		        scales: {
		            y: {
		                beginAtZero: true
		            }
		        }
		    }
		});
	});


});