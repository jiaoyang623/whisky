function request() {
	var xstm = $("#url").val();
	$.post("xstm", {
		"xstm" : xstm
	}, function(data) {
		$("#result").val(data);
		toTable(data);
	}).error(function() {
		$("#result").val("request error");
	});
}

function parse() {
	var data = $("#result").val();
	toTable(data);
}

function toTable(data) {
	eval("tableContent=" + data);
	var table = $("#table");
	table.empty();
	for ( var i = 0; i < tableContent.videos.length; i++) {
		var url = tableContent.videos[i].url;
		table.append($("<tr><td><a href='" + url + "'>" + url
				+ "</a></td></tr>"));
	}

	$("table a").click(playLink);
}

function playLink(event) {
	stopDefault(event);
	var videoFrame = $("#videoFrame");
	var type = "mp4";
	if (this.href.search("flv") >= 0) {
		type = "flv";
	}
	videoFrame.empty();
	videoFrame
			.append($("<p>"
					+ this.href
					+ "</p><video width='400px' height='300px' controls> <source type='video/"
					+ type + "' src='" + this.href + "'/></video>"));
}

function stopDefault(e) {
	if (e && e.preventDefault) {
		// Prevent the default browser action (W3C)
		e.preventDefault();
	} else {
		// A shortcut for stoping the browser action in IE
		window.event.returnValue = false;
	}
	return false;
}
