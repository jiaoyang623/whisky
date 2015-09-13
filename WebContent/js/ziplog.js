function parse(file, data) {
	eval("logData=" + data);
	console.log(logData);
	var table = $("#table")[0];
	clearTable(table);
	fillTable(table, logData);
}

function fillTable(table, logData) {
	for ( var i = 0; i < logData.length; i++) {
		var row = document.createElement("tr");
		var data = logData[i];
		row.appendChild(newCell("10%", data.rom));
		row.appendChild(newCell("10%", data.device));
		row.appendChild(newCell("10%", data.errorCode));
		row.appendChild(newCell("10%", data.nativeCode));
		row.appendChild(newCell("20", data.nativeDesc));
		row.appendChild(newCell("40%", data.path));

		table.appendChild(row);
	}
}

function newCell(percent, content) {
	var cell = document.createElement("td");
	cell.appendChild(document.createTextNode(content));
	cell.style.width = percent;
	cell.style.wordBreak = "break-all";

	return cell;
}

function clearTable(table) {
	while (table.rows.length > 0) {
		table.deleteRow(table.rows[0]);
	}
}