function showOrHideLevelJapan() {
	var levelJapan = document.getElementById("japan");
	var status = levelJapan.style.display;
	if (status == "none") {
		status = "block";
	} else {
		status = "none";
	}
	levelJapan.style.display = status;
}
