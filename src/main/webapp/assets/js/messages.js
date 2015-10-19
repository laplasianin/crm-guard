


var messages = messages || {

    selector : '.bottom-right',

    addMessage: function(message) {
        var type = message.type === 'error' ? 'danger' : message.type;
        $(messages.selector).notify({
            message: { text: message.message },
            type: type,
            fadeOut: {
                delay: 3000
            }
        }).show();
    },

    removeAll: function() {
        $(messages.selector).html('');
    },

        successMessage: function(msg) {
            messages.removeAll();
            messages.addMessage({type: 'success', message: msg});
        },

    errorMessage: function(msg) {
        messages.removeAll();
        messages.addMessage({type: 'danger', message: msg});
    },

    addMessages: function(msgs) {
        messages.removeAll();
        if (msgs) {
            msgs.forEach(function(message) {
                messages.addMessage(message);
            });
        }
    },

    hasErrors: function(msgs) {
        var hasErrors = false;
        if (!msgs) {
            return hasErrors;
        }

        msgs.forEach(function(message) {
            if (message.type === 'error') {
                hasErrors = true;
            }
        });
        return hasErrors;
    }

};