function getExt(filename) {
    var a = filename.split(".");
    if( a.length === 1 || ( a[0] === "" && a.length === 2 ) ) {
        return "";
    }
    return a.pop();
}

function imageExtFormatter(value, row) {
    return '<img src="assets/img/file-ext/' + getExt(row.name) +'.png" ' +
        'onerror="this.src=\'assets/img/file-ext/_blank.png\'"' + '>';
}

function downloadButtonFormatter(value, row) {
    var clientId = $('#clientId').val();
    var url = UrlUtils.getUrl('url_get_file');
    return '<a href="' + url + clientId + '/' + row.name + '/">' + row.name + ' <span class="glyphicon glyphicon-download" aria-hidden="true"></span></a>';
}
function removeFileButtonFormatter(value, row) {
    var clientId = $('#clientId').val();
    var url = UrlUtils.getUrl('url_remove_file');
    return '<button type="button" class="btn btn-danger remove-file" directory="' + clientId + '" name="' + row.name + '/">' + ' <span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>';
}

$(document).ready(function(){
    $table = $('#file-table').bootstrapTable();
});