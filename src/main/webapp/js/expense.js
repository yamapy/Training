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

function findById(id) {
	console.log('findByID start - id:' + id);
	$.ajax({
		type : "GET",
		url : rootUrl + '/' + id,
		dataType : "json",
		success : function(data) {
			console.log('findById success: ' + data.id);
			renderDetails(data)
		}
	});
}

function deleteById(id) {
	console.log('delete start - id:' + id);
	$.ajax({
		type : "DELETE",
		url : rootUrl + '/' + id,
		success : function() {
			alert('経費データの削除に成功しました');
			findAll();
			renderDetails({});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('経費データの削除に失敗しました');
		}
	});
}

function renderTable(data) {
	var headerRow = '<tr><th>申請ID</th><th>申請日</th><th>更新日</th><th>申請者</th><th>タイトル</th><th>金額</th><th>ステータス</th></tr>';

	$('#expense').children().remove();

	console.log('パクチソン',data)

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
			row.append($('<td>').append(
					$('<button>').text("編集").attr("type","button").attr("onclick", "findById("+expense.id+')')
				));
			row.append($('<td>').append(
					$('<button>').text("削除").attr("type","button").attr("onclick", "deleteById("+expense.id+')')
				));


			table.append(row);
		});

		$('#expense').append(table);
	}





}

function renderDetails(expense) {
	$('.error').text('');
	$('#appId').val(expense.id);
	$('#appDate').val(expense.appDate);
	$('#updateDate').val(expense.updateDate);
	$('#applicant').val(expense.applicant);
	$('#title').val(expense.title);
	$('#payee').val(expense.payee);
	$('#amount').val(expense.amount);
	$('#status').val(expense.status);
	$('#changer').val(expense.changer);
}
