'use strict';

var rootUrl = "/java_s04/api/v1.1/expense";

findAll();

function findAll() {
	console.log('findAll start.')
	$.ajax({
		type : "GET",
		url : rootUrl,
		dataType : "json",
		success : renderTable
	});
}

function renderTable(data) {
	var headerRow = '<tr><th>申請ID</th><th>申請日</th><th>更新日</th><th>申請者</th><th>タイトル</th><th>金額</th><th>ステータス</th></tr>';

	$('#expense').children().remove();

	if (data.length === 0) {
		$('#expense').append('<p>現在データが存在していません。</p>')
	} else {
		var table = $('<table>').attr('border', 1);
		table.append(headerRow);

		$.each(data, function(index, expense) {
			var row = $('<tr>');
			row.append($('<td>').text(expense.id));
			row.append($('<td>').text(expense.appDate));
			row.append($('<td>').text(expense.updateDate));
			row.append($('<td>').text(expense.applicant));
			row.append($('<td>').text(expense.title));
			row.append($('<td>').text(expense.amount));
			row.append($('<td>').text(expense.status));


			table.append(row);
		});

		$('#expense').append(table);
	}




}