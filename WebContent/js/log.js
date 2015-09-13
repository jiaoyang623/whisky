function onChanged() {

}

function format() {
	$("#source").hide();
	var content = $("#text").val();
	var lines = content.split("\n");
	var type = $("#type").val();
	var level = $("#level").val();
	if (level != "") {
		level = level.toUpperCase();
	}
	var tag = $("#tag").val();
	if (tag != "") {
		tag = tag.toUpperCase();
	}
	var keyword = $("#keyword").val();
	if (keyword != "") {
		keyword = keyword.toUpperCase();
	}

	if (type == "eclipse") {
		analyzeEclipse(lines, level, tag, keyword);
	} else {
		// logcat
		analyzeLogcat(lines, level, tag, keyword);
	}
	var table = document.getElementById("table");
	clearTable(table);
	fillTable(table, lines.length, 4, lines);
}

function analyzeEclipse(lines, level, tag, keyword) {
	var separator = ": ";
	for ( var i = 0; i < lines.length; i++) {
		var content = lines[i].split(separator);
		if (content.length > 3) {
			for ( var j = 3; j < content.length; j++) {
				content[2] += separator + content[j];
			}
			content.length = 3;
		} else if (content.length < 3) {
			lines[i] = null;
			continue;
		}

		if (content.length >= 3 && content[1].indexOf('/') >= 0) {
			content.unshift(1);
			content[0] = content[1];
			var x = content[2].split("/");
			content[1] = x[0];
			content[2] = x[1];
			// 过滤level
			if (level != "" && level.indexOf(content[1].toUpperCase()) < 0) {
				lines[i] = null;
				continue;
			}
			// 过滤tag
			if (tag != "" && content[2].toUpperCase().indexOf(tag) < 0) {
				lines[i] = null;
				continue;
			}
			// 过滤关键字
			if (keyword != "" && content[3].toUpperCase().indexOf(keyword) < 0) {
				lines[i] = null;
				continue;
			}
		}
		lines[i] = content;
	}
}

function analyzeLogcat(lines, level, tag, keyword) {
	for ( var i = 0; i < lines.length; i++) {
		var line = lines[i];
		if (line == null || line.trim().length == 0 || line[0] == "-") {
			line = null;
			continue;
		}
		var content = [];
		var char0 = line.charAt(0);
		if (char0 >= 0 && char0 <= 9) {
			content[0] = line.substring(0, 18);
			line = line.substring(19);
		} else {
			content[0] = i;
		}
		content[1] = line.substring(0, 1);
		var sepIndex = line.indexOf("):");
		content[2] = line.substring(2, sepIndex + 1).trim();
		content[3] = line.substring(sepIndex + 2, line.length).trim();

		// 过滤level
		if (level != "" && level.indexOf(content[1].toUpperCase()) < 0) {
			lines[i] = null;
			continue;
		}
		// 过滤tag
		if (tag != "" && content[2].toUpperCase().indexOf(tag) < 0) {
			lines[i] = null;
			continue;
		}
		// 过滤关键字
		if (keyword != "" && content[3].toUpperCase().indexOf(keyword) < 0) {
			lines[i] = null;
			continue;
		}
		lines[i] = content;
	}
}

function getTable() {
	return document.getElementById("table");
}

function fillTable(table, rows, cols, data) {

	for ( var i = 0; i < rows; i++) {
		var row = document.createElement("tr");
		row.id = i;
		if (data[i] != null && data[i].length >= 3) {
			row.style.color = getColor(data[i][1]);
		} else {
			continue;
		}

		for ( var j = 0; j < cols; j++) {
			var cell = document.createElement("td");
			switch (j) {
			case 0:
				cell.style.width = "18%";
				break;
			case 1:
				cell.style.width = "2%";
				break;
			case 2:
				cell.style.width = "20%";
				cell.style.wordBreak = "break-all";
				break;
			case 3:
				cell.style.width = "60%";
				cell.style.wordBreak = "break-all";
				break;
			}
			cell.id = i + "/" + j;
			var txt = data[i][j];
			if (txt == null) {
				txt = "";
			}
			var txtNode = document.createTextNode(txt);
			cell.appendChild(txtNode);
			row.appendChild(cell);
		}
		table.appendChild(row);
	}
}

function getColor(level) {
	var color = "black";
	switch (level) {
	case "E":
		color = "#ff0000";
		break;
	case "W":
		color = "#ff7100";
		break;
	case "I":
		color = "#418841";
		break;
	case "D":
		color = "#1c008d";
		break;
	default:
		break;
	}

	return color;
}

function clearTable(table) {
	while (table.rows.length > 0) {
		table.deleteRow(table.rows[0]);
	}
}

function onKeyUp(msg) {
	var keyCode = window.event.keyCode;
	if (keyCode == 13) {
		format();
	}
}

function showBox() {
	$("#source").show();
}

$(function() {
	var brs = $(window);
	var top = $("#control");

	function pos() {
		top.offset({
			top : brs.scrollTop()
		});
	}

	pos();

	brs.bind("resize scroll", function() {
		pos();
	});
});
