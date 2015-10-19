function nameFormatter(value, row) {
    var url = UrlUtils.getUrl('url_show_template');
    return '<a href="' + url + '?id=' + row.id + '">' + value + '</a>';
}

$(function() {


    $(document).ready(function(){
        $('#templates-table').bootstrapTable();
    });

});


