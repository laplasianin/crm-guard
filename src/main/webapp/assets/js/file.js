$(document).ready(function(){

    var input = document.getElementById("choose");
    var clientId = $('#clientId').val();
    FileAPI.event.on(input, "change", function (){
        var list = FileAPI.getFiles(input); // Получаем список файлов

        // Загружаем на сервер
        FileAPI.upload({
            url: UrlUtils.getUrl("url_upload_file"),
            files: { userFiles: list },
            data: {'client': clientId},
            complete: function (err, xhr){
                var data = JSON.parse(xhr.responseText);
                messages.addMessages(data.messages);
                $('#file-table').bootstrapTable('refresh');
            }
        });
    });

    $('#file-table').on('click', '.remove-file', function(){
        var button = $(this);
        button.button('loading');
        var data = button.attr('directory');
        var run = $.post(UrlUtils.getUrl("url_remove_file") + button.attr('directory') + '/' + button.attr('name'));
        run.success(function() {
                $('#file-table').bootstrapTable('refresh');
                messages.successMessage('Файл удален!')}
        );
        run.error(errorText);
        run.always(function(){button.button('reset');});
    });

    var errorText = function(data)  {
        messages.errorMessage(data.responseText);
    }

});