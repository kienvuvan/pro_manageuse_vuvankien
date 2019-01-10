function showOrHideLevelJapan() {
	var levelJapan = document.getElementsByClassName('japan');
	var status = levelJapan[0].style.display;
	if (status == 'none') {
		status = '';
	} else {
		status = 'none';
	}
	for (var i = 0; i < levelJapan.length; i++) {
		levelJapan[i].style.display = status;
	}
}
