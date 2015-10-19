$(document).ready(function(){

    var clientId = $("#clientId").val();

    $.get( "event/history/client?id=" + clientId, function( data ) {
        $( "#history" ).html( data );

        $('#history-table').bootstrapTable();
    });

});


