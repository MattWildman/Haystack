Date.prototype.dateNow = function(){ 
    return ((this.getDate() < 10)? "0" : "") + 
    	   this.getDate() + "/" +
    	   (((this.getMonth() + 1) < 10) ? "0" : "") + 
    	   (this.getMonth() + 1) + "/" + 
    	   this.getFullYear();
};
Date.prototype.timeNow = function(){
     return ((this.getHours() < 10) ? "0" : "") + 
     		this.getHours() + ":" + 
     		((this.getMinutes() < 10) ? "0" : "") + 
     		this.getMinutes();
};

function getNowDate() {
	var nowDate = new Date();
	return nowDate.dateNow();
}

function getNowTime() {
	var nowDate = new Date();
	return nowDate.timeNow();
}

function setCurrentDateTime() {
	document.getElementById('ldate').value = getNowDate();
	document.getElementById('ltime').value = getNowTime();
}

function processDateInput() {
	var edate = document.getElementById('edate').value + ' ' +
				document.getElementById('etime').value;	
	var ldate = document.getElementById('ldate').value + ' ' +
				document.getElementById('ltime').value;	
	document.getElementById('earliest').value = edate;
	document.getElementById('latest').value = ldate;
}