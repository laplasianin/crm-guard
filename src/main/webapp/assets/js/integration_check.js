$(function() {
    $(document).ready(function () {
        function checkCompleted() {
            $.get(UrlUtils.getUrl('url_integration_check_last_completed'))
                .error(function (error) {
                    messages.addMessage({type: 'error', message: error.responseText});
                });
        }

        $.get(UrlUtils.getUrl('url_integration_check_uncompleted')).success(function (result) {
            if (result) {
                messages.addMessage({type: 'error', message: 'Внимание! В данный момент проходит интеграция с 1с, данные в системе могут быть не актуальны'});
            } else {
                checkCompleted();
            }
        });
    });
});
