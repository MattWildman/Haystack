var useRange = false;

function setDateTime() {
	
}

//toggle single date and date range fieldsets
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

