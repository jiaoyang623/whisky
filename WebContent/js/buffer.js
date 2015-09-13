function upload() {
	var content = $("#upload").val();
	$.post("buffer", {
		// json object as params
		"content" : content,
		"act" : "post",
		"user" : $("#user").val()
	}, function(data) {
		// success
		$("#download").val(data);
	}).error(function() {
		// error
		$("#download").val("request error");
	});
}

function download() {
	$.post("buffer", {
		// json object as params
		"act" : "get",
		"user" : $("#user").val()
	}, function(data) {
		// success
		$("#download").val(data);
	}).error(function() {
		// error
		$("#download").val("request error");
	});
}