var useRange = false;

//build and set full date time strings on submit
function setDateTime() {
	var eDate, lDate, eTime, lTime;
	if (!useRange) {
		eDate = lDate = $('#only-date').val();
		eTime = $('#earliest-time').val();
		lTime = $('#latest-time').val();
	}
	else {
		eDate = $('#earliest-date').val();
		lDate = $('#latest-date').val();
		eTime = $('#range-earliest-time').val();
		lTime = $('#range-latest-time').val();
	}
	$('#earliest').val(eDate + " " + eTime);
	$('#latest').val(lDate + " " + lTime);
}

//set up datepickers and placeholders
$(function() {
    var $dateInputs = $("#only-date, #earliest-date, #latest-date");
    $dateInputs.datepicker({ maxDate: "+0d",
    						 dateFormat: 'dd/mm/yy',
    						 changeYear: true });
    $dateInputs.attr('placeholder','dd/mm/yyyy');
  });

//toggle single date and date range usage
$('input[name="use-range"').change(function() {
	var $this = $(this);
	if ($this.val() == 'yes') {
		$('#single-date-fields').addClass('hidden');
		$('#date-range-fields').removeClass('hidden');
		useRange = false;
	}
	else if ($this.val() == 'no') {
		$('#date-range-fields').addClass('hidden');
		$('#single-date-fields').removeClass('hidden');
		useRange = true;
	}
});

