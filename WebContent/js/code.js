function encode() {
	var result = $("#codeResult");
	result.html("");
	var value = $("#code").val();
	result.append(encodeURIComponent(value));
}

function decode() {
	var result = $("#codeResult");
	result.html("");
	var value = $("#code").val();
	result.append(decodeURIComponent(value));
}