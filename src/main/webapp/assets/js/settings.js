$(function() {
    $(document).ready(function () {

        $('#change_password').click(function(){

            var $form = $('#change-password-form');
            if (!$form.valid()) {
                return;
            }

            var button = $(this);
            button.button('loading');
            var run = $.post(UrlUtils.getUrl("url_settings_chane_password"), $form.serialize());
            run.success(RequestUtils.showMessages);
            run.error(errorText);
            run.always(function(){button.button('reset');});
        });

        $('#change_sms_login').click(function(){

            var $form = $('#change-sms-login-form');
            if (!$form.valid()) {
                return;
            }

            var button = $(this);
            button.button('loading');
            var run = $.post(UrlUtils.getUrl("url_settings_change_sms_login"), $form.serialize());
            run.success(RequestUtils.showMessages);
            run.error(errorText);
            run.always(function(){button.button('reset');});
        });

        $('#change_sms_password').click(function(){

            var $form = $('#change-sms-password-form');
            if (!$form.valid()) {
                return;
            }

            var button = $(this);
            button.button('loading');
            var run = $.post(UrlUtils.getUrl("url_settings_change_sms_password"), $form.serialize());
            run.success(RequestUtils.showMessages);
            run.error(errorText);
            run.always(function(){button.button('reset');});
        });

        $('#change-files-path').click(function(){

            var $form = $('#change-files-path-form');
            if (!$form.valid()) {
                return;
            }

            var button = $(this);
            button.button('loading');
            var run = $.post(UrlUtils.getUrl("url_settings_change_files_path"), $form.serialize());
            run.success(RequestUtils.showMessages);
            run.error(errorText);
            run.always(function(){button.button('reset');});
        });

        var errorText = function(data)  {
            messages.errorMessage(data.responseText);
        }

    });
});
