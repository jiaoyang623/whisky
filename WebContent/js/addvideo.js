/**
 * 
 */

function alpha() {
	var textBox = document.getElementById("text");
	var userBox = document.getElementById("user");
	sendMsg(getUrl(textBox.value).join("\n"), userBox.value);
}

function onUrlChanged() {
	var textBox = document.getElementById("text");
	var result = document.getElementById("result");
	var urlArray = getUrl(textBox.value);
	var len = urlArray.length;
	var content = "";
	for ( var i = 0; i < len; i++) {
		content += urlArray[i] + "<br />";
	}
	result.innerHTML = content;
}

function getUrl(content) {
	var urlArray = [];

	var reg = /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&amp;:\/~\+#]*[\w\-\@?^=%&amp;\/~\+#])?/gi;
	content.replace(reg, function(url) {
		urlArray[urlArray.length] = url;
		return url;
	});

	return urlArray;
}

function sendMsg(msg, user) {
	var xmlhttp;
	if (window.XMLHttpRequest) {
		// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else {
		// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	xmlhttp.onreadystatechange = function() {
		var result = document.getElementById("result");
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			result.innerText = xmlhttp.responseText.replace("\n", "<br />");
		} else {
			result.innerText = "Error: " + xmlhttp.readyState + ", "
					+ xmlhttp.status + ", " + xmlhttp.responseText;
		}
	};

	xmlhttp.open("POST", "LiveSource", true, "", "");
	xmlhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlhttp.send("url=" + encodeURIComponent(msg) + "&act=add");
}