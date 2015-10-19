/** @namespace client.totalInvoice */
/** @namespace client.totalInvoice.invoice */
/** @namespace client.shortName */
/** @namespace client.id */
/** @namespace client.mobileNumber */
/** @namespace client.okpo */
/** @namespace client.inn */
/** @namespace client.registrationAddress */
/** @namespace client.personalAccount */


function nameFormatter(value, client) {
    return '<a href="client?id=' + client.id + '">' + value + '</a>';
}

function clientTypeFormatter(value, client) {
    return ClientTypeFormatter.format(value);
}

function totalInvoiceFormatter(value, client) {
    return client.totalInvoice ? client.totalInvoice.invoice : '';
}

function clientInfoFormatter(value, client) {

    var container = $('#client-info-container').clone();
    container.find(".client-info-shortName").text(client.shortName);
    container.find(".client-info-fullName").text(client.fullName);
    container.find(".client-info-code").text(client.code);
    container.find(".client-info-type").text(client.type);
    container.find(".client-info-mobileNumber").text(client.mobileNumber);
    container.find(".client-info-registrationAddress").text(client.registrationAddress);
    container.find(".client-info-personalAccount").text(client.personalAccount);
    container.find(".client-info-inn").text(client.inn);
    container.find(".client-info-okpo").text(client.okpo);
    container.find(".client-info-operator-row").remove();

    container.find(".client-info-table").hide();
    var toggleBtn = container.find(".client-info-toggle");
    var toggleIcon = toggleBtn.find("span.glyphicon");
    var toggleText = toggleBtn.find("span.content");
    toggleIcon.addClass(toggleBtn.attr('icon-down'));
    toggleText.text(toggleBtn.attr('content-down'));

    return container.html();
}

$(document).ready(function(){
    ClientsTable.create();
    ClientsTable.setupClientInfoToggle();

    $('#xlsExportBtn').click(function(){
        var $table = $('#clients-table');
        $table.bootstrapTable('hideColumn', 'detailed-info');
        $table.tableExport({
                    type:'excel',
                    ignoreColumn: [0, 6],
                    fileName: 'Клиенты'
                });
        $table.bootstrapTable('showColumn', 'detailed-info');
    });
});


