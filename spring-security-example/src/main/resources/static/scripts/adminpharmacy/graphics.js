$(document).ready(function(e){ 
	$('#appointmentStatistic').click(function() {
		let temp = `<div style="width:30%; margin-left:auto; 
			    margin-right:auto; margin-top: 40px;"><canvas id="myChart"></canvas></div>`;
		
		$('#showData').html(temp)
		
		var ctx = document.getElementById('myChart');
		var myChart = new Chart(ctx, {
		    type: 'doughnut',
		    data: {
		    	 labels: [
		    		    'Red',
		    		    'Blue',
		    		    'Yellow'
		    		  ],
		    		  datasets: [{
		    		    label: 'My First Dataset',
		    		    data: [300, 50, 100],
		    		    backgroundColor: [
		    		      'rgb(255, 99, 132)',
		    		      'rgb(54, 162, 235)',
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
	})
});