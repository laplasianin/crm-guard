


var RequestUtils = RequestUtils || {

        reloadPage : function () {
            location.reload(false);
        },
        showErrorMessage : function (data) {
            $('button').each(function(){$(this).button('reset')});
            messages.errorMessage(data.responseText);
        },
        showMessages : function (data) {
            $('button').each(function(){$(this).button('reset')});
            messages.addMessages(data.messages);
        },

        closeModalAndShowMessages: function(data) {
            $('.modal[aria-hidden=false]').modal('hide');
            $('button').each(function(){$(this).button('reset')});
            RequestUtils.showMessages(data);
        },

        reloadOrShowMessages : function (data) {
            RequestUtils.custom(RequestUtils.reloadPage, RequestUtils.showMessages)(data);
        },

        custom : function (successCallback, errorCallback) {
            return function(data) {
                if (data.result) {
                    if (typeof(successCallback) === 'function') {
                        successCallback(data);
                    }
                } else {
                    if (typeof(errorCallback) === 'function') {
                        errorCallback(data);
                    }
                }
            }
        }
    };