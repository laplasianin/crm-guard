var ClientTypeFormatter = ClientTypeFormatter || {

    types : {
        P : 'Юридическое лицо',
        I : 'Физическое лицо'
    },

    format: function(type) {
        if (type) {
            return this.types[type.toUpperCase()];
        }
        return '';
    }
};