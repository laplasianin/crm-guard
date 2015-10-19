$(function() {
    $(document).ready(function() {

        $table = $('#groups-table').bootstrapTable();


        $('#saveUpdateBtn').click(function(){

            if (!$('#group-form').valid()) {
                return;
            }

            $(this).button('loading');
            var data = {};
            $("#group-form").serializeArray().map(function(x){data[x.name] = x.value;});

            var url = "save";
            var request = $.post(url, data );

            request.success(RequestUtils.reloadOrShowMessages);
        });

        $('#removeGroupBtn').click(function(){

            $(this).button('loading');
            var data = {code: $(this).attr('code')};
            var url = "remove";
            var request = $.post(url, data );

            request.success(RequestUtils.reloadOrShowMessages);
        });

        $('#group-form').validate();
    } );

});
