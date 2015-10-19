$(document).ready(function(){

    var $clientForm = $("#client-form");
    var $clientUpdateForm = $("#client-update-form-id");

    $('#takeOperatorToWork').click(function(){
        $(this).button('loading');

        var url = "client/take_to_work";
        var request = $.post(url, $clientForm.serialize() );
        request.success(RequestUtils.reloadPage);
        request.error(RequestUtils.showErrorMessage);
    });

    $('#kickOperatorFromWork').click(function(){
        $(this).button('loading');

        var url = "client/kick_from_work";
        var request = $.post(url, $clientForm.serialize() );
        request.success(RequestUtils.reloadPage);
        request.error(RequestUtils.showMessages);
    });

    $('#save-client').click(function(){
        $(this).button('loading');

        var url = "client/update";
        var request = $.post(url, $clientUpdateForm.serialize() );
        request.success(RequestUtils.showMessages);
        request.error(RequestUtils.showMessages);
    });

});


