$(document).ready(function(){
    $('#birthDate').datetimepicker({
        pickTime: false
    });

    $('#updateContactBtn').on('click', function(){
        var form = $('#userForm');
        var $btn = $(this).button('loading');
        $.ajax({
            url: "update",
            data: form.serialize(),
            type: "POST",
            dataType : "json",
            success: function( data ) {
                if (data.result) {
                    $('.bottom-right').notify({
                        message: { text: 'Действие успешно выполнено' },
                        type: 'success',
                        fadeOut: {
                            delay: Math.floor(Math.random() * 500) + 2500
                        }
                    }).show();
                    $btn.button('reset');
                } else {
                    $.each(data.messages, function(i, val) {
                        $('.bottom-right').notify({
                            message: { text: val.message },
                            type: 'danger',
                            fadeOut: {
                                delay: 2000
                            }
                        }).show();
                        $btn.button('reset');
                    });
                }


            }
        });
    });

});