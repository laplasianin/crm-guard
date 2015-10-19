function nameFormatterContact(value, row) {
    var clientId = $('#clientId').val();
    return '<a href="contact?client_id=' + clientId + '&id=' + row.id + '">' + row.lastName + ' ' + row.firstName + ' ' + (row.middleName || '') + '</a>';
}

function editButtonFormatter(value, row) {
    var clientId = $('#clientId').val();
    return '<a href="contact/edit?client_id=' + clientId + '&id=' + row.id + '"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>';
}

function masterContactFormatter(value, row) {
    var clientId = $('#clientId').val();
    if (!value) {
        return '<span onclick="setMaster(\'' + clientId + '\', ' + row.id + ');"style="cursor: pointer" class="glyphicon glyphicon-star-empty" aria-hidden="true"></span>';
    }
    return '<span class="glyphicon glyphicon-star" aria-hidden="true"></span>';
}

function setMaster(clientId, contactId) {
    $.ajax({
        url: "contact/set_master",
        data: {
            client: clientId,
            contact: contactId
        },
        type: "POST",
        dataType: "json",
        success: function( data ) {
            if (data.result) {
                $('#contacts-table').bootstrapTable('refresh');
            }
            messages.addMessages(data.messages);
        }
    });
}

function phoneFormatterContact(value, row) {

    var result = PhoneFormatter.format(row.mobileNumber2);

    if (result) {
        result += '; ';
    }

    result += PhoneFormatter.format(row.mobileNumber3);
    return result;
}
$(document).ready(function(){
    $table = $('#contacts-table').bootstrapTable().on('load-success.bs.table', setNewMasterContactToActionFrom);

    function setNewMasterContactToActionFrom(e, data) {
        for (i in data) {
            var contact = data[i];
            if (contact.master) {
                $('form#actionForm input[name="contact"]').val(contact.id);
                $('#masterContactActionFromLastName').text(contact.lastName || '');
                $('#masterContactActionFromFirstName').text(contact.firstName || '');
                $('#masterContactActionFromMiddleName').text(contact.middleName || '');
            }
        }
    }
});