$(document).ready(function(){

    var clientId = $("#clientId").val();

    function updateContracts() {
        $.get( "contracts/client?id=" + clientId, function( data ) {
            $( "#contracts-container" ).html( data );

            $('#contracts-table').bootstrapTable({
                onClickRow: function(row, $elem) {
                    $elem.addClass('selected-row').siblings().removeClass('selected-row');
                    updateInvoices(row['0'].trim());
                    updatePayments(row['0'].trim());
                },

                onSort: function(row) {
                    removeInvoices();
                    removePayments();
                },
                onSearch: function(row) {
                    removeInvoices();
                    removePayments();
                },
                onPageChange: function(row) {
                    removeInvoices();
                    removePayments();
                }
            });
        });
    }

    updateContracts();
    removeInvoices();
    removePayments();

    function removeInvoices() {
        $( "#invoices-container" ).html( $("#default-invoices").html() );
    }

    function updateInvoices(contractId) {
        $.get( "invoices/contract?id=" + contractId, function( data ) {
            $( "#invoices-container" ).html( data );

            $('#invoices-table').bootstrapTable({
            });
        });
    }

    function removePayments() {
        $( "#payments-container" ).html('');
    }

    function updatePayments(contractId) {
        $.get( "payments/contract?id=" + contractId, function( data ) {
            $( "#payments-container" ).html( data );

            $('#payments-table').bootstrapTable({
            });
        });
    }


});


