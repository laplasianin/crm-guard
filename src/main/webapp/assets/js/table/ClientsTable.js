
var ClientsTable = ClientsTable || {

        timestampFormat : 'DD.MM.YYYY HH:mm',

        getFilter: function() {

            var filter = $('#filter-bar').bootstrapTableFilter('getData');
            if (!filter) {
                filter = {};
            }

            var entities = $('#entities').val();
            if (entities) {
                filter['contracts.entity.id'] = {'in': entities.join(',')};
            }

            var groups = $('#groups').val();
            if (groups) {
                filter['groups.code'] = {'in': groups.join(',')};
            }

            var operators = $('#operators').val();
            if (operators) {
                filter['operator.name'] = {'eq': operators.join(',')};
            }

            var $onlyMine = $('#onlyMine');
            var onlyMine = $onlyMine.is(':checked');
            if (onlyMine) {
                var principal = $onlyMine.attr('to');
                filter['operator.name'] = {'eq': principal};
            }

            var withToDos = $('#onlyWithToDo').is(':checked');
            if (withToDos) {
                filter['clientToDos.finished'] = {'null': 'null'};
                filter['clientToDos.start'] = {'lte': moment().format(ClientsTable.timestampFormat)};
            }

            var onlyDisabled = $('#onlyDisabled').is(':checked');
            if (onlyDisabled) {
                filter['disabled'] = {'nn': 'nn'};
            }

            return filter;
        },

        initFilter: function() {
            var $filter = $('#filter-bar');
            $filter.bootstrapTableFilter({
                connectTo: '#clients-table',

                filters:[
                    {
                        field: 'shortName',    // field identifier
                        label: 'Наименование',    // filter label
                        type: 'search',   // filter type
                        enabled: false
                    },
                    {
                        field: 'totalInvoice.invoice',    // field identifier
                        label: 'Долг',    // filter label
                        type: 'range',   // filter type
                        enabled: false
                    }
                ],

                onSubmit: function() {
                    $('#clients-table').bootstrapTable('refresh');
                }
            });


            //$filter.bootstrapTableFilter('setupFilter', 'shortName', {});
            $filter.bootstrapTableFilter('setupFilter', 'totalInvoice.invoice', {});
        },

        initTable:function() {
            var $table = $('#clients-table').bootstrapTable(
                {
                    queryParams: function (params) {

                        var filter = ClientsTable.getFilter();

                        if (!jQuery.isEmptyObject(filter)) {
                            params['filter'] = JSON.stringify(filter);
                        }

                        return params;
                    }
                }
            );

            $table.bootstrapTable('hideColumn', 'id');
        },

        populateFilter: function() {
            var $groupFilters = $(".btn-group-filters");
            $groupFilters.append($("#entitiesBar"));
            $groupFilters.append($("#clientsActionBar"));
            var $search = $(".search");
            $search.removeClass('pull-right').addClass('marg-top-zero pull-left');
            $(".btn-group-filter-refresh").before($search);


            $('#entities').select2({
                placeholder: "По обслуживающей компании",
                allowClear: true,
                width: '350px'
            });
            $('#groups').select2({
                placeholder: "По группе клиентов",
                allowClear: true,
                width: '350px'
            });
            $('#operators').select2({
                placeholder: "По ответственному оператору",
                allowClear: true,
                width: '350px'
            });
        },

        create: function() {
            ClientsTable.initFilter();
            ClientsTable.initTable();
            ClientsTable.populateFilter();
        },

        setupClientInfoToggle: function() {
            $("#clients-table").on('click', '.client-info-toggle', function(){
                var toggleIcon = $(this).find("span.glyphicon");
                var toggleText = $(this).find("span.content");

                if (toggleIcon.hasClass($(this).attr('icon-down'))) {
                    toggleIcon.removeClass($(this).attr('icon-down'));
                    toggleIcon.addClass($(this).attr('icon-up'));
                    toggleText.text($(this).attr('content-up'));
                    $(this).parent().find('.client-info-table').show();
                } else {
                    toggleIcon.removeClass($(this).attr('icon-up'));
                    toggleIcon.addClass($(this).attr('icon-down'));
                    toggleText.text($(this).attr('content-down'));
                    $(this).parent().find('.client-info-table').hide();
                }
            });
        }

    };