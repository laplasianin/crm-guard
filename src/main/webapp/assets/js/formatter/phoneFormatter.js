var PhoneFormatter = PhoneFormatter || {

    format: function(number) {
        var result = '';
        var cl = '';
        if (number) {
            cl = isValidNumber(number, 'ru') ? '' : 'style="color:red;" '
            result = '<span ' + cl + '>' + formatLocal('ru', number) + "</span>";
        }
        return result;
    }
};