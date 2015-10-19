var CountUpdater = CountUpdater || {


        updateCountToDo: function() {
            var url = UrlUtils.getUrl('url_todo_by_operator');
            var request = $.get(url);
            request.success(function(data) {
                if (data.data) {
                    $('#menu_todo_count').text(data.data).show();
                    $('#menu_todo_icon').hide();
                } else {
                    $('#menu_todo_count').hide();
                    $('#menu_todo_icon').show();
                }
            });
        }
    };

$(function() {

    $(document).ready(function() {
        CountUpdater.updateCountToDo();

    });

});
