$(function() {

    $(document).ready(function(){
        var $form = $('#form-template');
        var $type = $('#type');
        var $template = $('#template');

        initPage();

        function initPage() {
            $template.wysihtml5({
                toolbar: {
                    "custom1": function(context) {
                        return $('#fieldList');
                    }
                }
            });
            $type.select2();
            $type.select2('readonly', $form.find('#template-id').val());
            $('#save-or-update-btn').click(saveOrUpdate);
        }

        function saveOrUpdate(){

            var $formToSend = $('#form-template');

            if (!$formToSend.valid()) {
                return;
            }

            $(this).button('loading');
            var url = "save_or_update";
            var request = $.post(url, $formToSend.serializeArray());

            request.success(RequestUtils.custom(reloadPageAfterUpdate, RequestUtils.showMessages));
            request.error(RequestUtils.showErrorMessage);
        }

        function reloadPageAfterUpdate(data){
            if (data.data) {
                window.location = window.location + '?id=' +data.data;
            } else {
                RequestUtils.reloadPage();
            }
        }
    });

});

