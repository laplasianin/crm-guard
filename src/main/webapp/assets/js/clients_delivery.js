$(document).ready(function(){

    var requests = $.get(UrlUtils.getUrl('url_all_templates'));
    var templates = [];
    var $templatePreview = $('#templatePreview');
    $templatePreview.wysihtml5({
        "font-styles": false, //Font styling, e.g. h1, h2, etc. Default true
        "emphasis": false, //Italics, bold, etc. Default true
        "lists": false, //(Un)ordered lists, e.g. Bullets, Numbers. Default true
        "html": false, //Button which allows you to edit the generated HTML. Default false
        "link": false, //Button to insert a link. Default true
        "image": false, //Button to insert an image. Default true,
        "color": false, //Button to change color of font
        "blockquote": false, //Blockquote
        "size": 'lg' //default: none, other options are xs, sm, lg
    });
    $templatePreview.data('wysihtml5').editor.composer.disable();

    requests.success(function(data){
        templates = data;
        $('#send-delivery-form').find('#template').select2({
            data : templates.map(function(elem) {return {
                id: elem.id,
                text: elem.name
            }})
        }).on('change', function(e) {
            var filtered = templates.filter(function(elem){
                return elem.id == e.val;
            });
            if (filtered.length) {
                var template = filtered[0];
                var $form = $('#send-delivery-form');
                $form.find('#type').val(template.type);
                $form.find('#subject').val(template.subject);
                $('iframe').contents().find('.wysihtml5-editor').html(template.template);

            }
        });
    });

    $("a[data-target='#sendDeliveryModal'], a[data-target='#sendCustomDeliveryModal']").click(function () {

        var selectedClients = $('#clients-table').bootstrapTable('getSelections');
        var selected = selectedClients.length;

        var pagingText = $('.pagination-info').text();
        var pagingCount = pagingText.substr(pagingText.length - 1);

        var clients = selected || pagingCount;
        var $modal = $($(this).attr('data-target'));
        var deliveryType = $(this).attr('delivery-type');
        if (deliveryType) {
            $modal.find('input[name="type"]').val(deliveryType);
        }
        $modal.find('#send-delivery-clients-count').text(clients);
        $modal.find('#send-delivery-selected-clients').val(selectedClients.map(function(e){return e.id}).join(','));

        $modal.modal();
    });

    $('#send-templates-btn, #send-custom-templates-btn').click(function() {
        var $form = $(this).parents('.modal').find('form');

        if (!$form.valid()) {
            return;
        }
        $(this).button('loading');
        var data = $form.serializeArray();
        var filter = ClientsTable.getFilter();
        if (!jQuery.isEmptyObject(filter)) {
            data['filter'] = JSON.stringify(filter);
        }
        var url = $('form#base-urls ' + $form.attr('url-selector')).val();
        var request = $.post(url, data );
        request.success(RequestUtils.custom(RequestUtils.closeModalAndShowMessages, RequestUtils.showMessages));
        request.error(RequestUtils.showErrorMessage);
    });



});


