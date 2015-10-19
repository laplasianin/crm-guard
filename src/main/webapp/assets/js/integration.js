$(function() {
    $(document).ready(function () {

        $('#run').click(function(){
            var button = $(this);
            button.button('loading');
            var run = $.post(UrlUtils.getUrl("url_integration_run"));
            run.success(function(){messages.successMessage('Интеграция завершена успешно!')});
            run.error(errorText);
            run.always(function(){button.button('reset');});
        });

        $('#clients').click(function(){
            var button = $(this);
            button.button('loading');
            var run = $.post(UrlUtils.getUrl("url_integration_clients"));
            run.success(function(){messages.successMessage('Интеграция завершена успешно!')});
            run.error(errorText);
            run.always(function(){button.button('reset');});

        });

        $('#contracts').click(function(){
            var button = $(this);
            button.button('loading');
            var run = $.post(UrlUtils.getUrl("url_integration_contracts"));
            run.success(function(){messages.successMessage('Интеграция завершена успешно!')});
            run.error(errorText);
            run.always(function(){button.button('reset');});
        });

        $('#total_invoices').click(function(){
            var button = $(this);
            button.button('loading');
            var run = $.post(UrlUtils.getUrl("url_integration_total_invoices"));
            run.success(function(){messages.successMessage('Интеграция завершена успешно!')});
            run.error(errorText);
            run.always(function(){button.button('reset');});
        });

        $('#invoices').click(function(){
            var button = $(this);
            button.button('loading');
            var run = $.post(UrlUtils.getUrl("url_integration_invoices"));
            run.success(function(){messages.successMessage('Интеграция завершена успешно!')});
            run.error(errorText);
            run.always(function(){button.button('reset');});
        });

        $('#payments').click(function(){
            var button = $(this);
            button.button('loading');
            var run = $.post(UrlUtils.getUrl("url_integration_payments"));
            run.success(function(){messages.successMessage('Интеграция завершена успешно!')});
            run.error(errorText);
            run.always(function(){button.button('reset');});

        });

        var errorText = function(data)  {
            messages.errorMessage(data.responseText);
        }

        $('#change-integration-path').click(function(){

            var $form = $('#change-integration-path-form');
            if (!$form.valid()) {
                return;
            }

            var button = $(this);
            button.button('loading');
            var run = $.post(UrlUtils.getUrl("url_settings_change_integration_path"), $form.serialize());
            run.success(RequestUtils.showMessages);
            run.error(errorText);
            run.always(function(){button.button('reset');});
        });

    });
});
