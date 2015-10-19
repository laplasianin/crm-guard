$(function() {
    $(document).ready(function() {

        $('#updateBtn').click(function(){

            if (!$('#group-form').valid()) {
                return;
            }

            $(this).button('loading');
            var data = {};
            $("#group-form").serializeArray().map(function(x){data[x.name] = x.value;});

            var url = "update";
            var request = $.post(url, data );

            request.success(RequestUtils.reloadOrShowMessages);
        });

        $('#removeGroupBtn').click(function(){
            $(this).button('loading');

            var data = {code: $(this).attr('code')};
            var url = "remove";
            var request = $.post(url, data );

            request.success(function(data) {
                if (data.result) {
                    window.location = '../groups/';
                } else {
                    RequestUtils.showMessages(data);
                }
            });
        });

        $('#addClientBtn').on('click', function(){
            if (!$('#group-form').valid()) {
                return;
            }

            $(this).button('loading');
            var url = "add_client";
            var request = $.post(url, $('#add-client-form').serialize() );

            request.success(RequestUtils.reloadOrShowMessages);
            request.error(RequestUtils.showErrorMessage);

        });

        $('.deleteClientBtn').on('click', function(){

            $(this).button('loading');
            var group = $(this).attr('group');
            var client = $(this).attr('client');

            var url = "remove_client";
            var request = $.post(url, {'group' : group, 'client': client} );

            request.success(RequestUtils.reloadOrShowMessages);
            request.error(RequestUtils.showErrorMessage);

        });

        $('#group-form').validate();
        $('#add-client-form').validate();

        $('#clientToAdd').select2({width: '250px', placeholder: 'Выберите клиента для включения в группу', allowClear: true});

    } );

});
