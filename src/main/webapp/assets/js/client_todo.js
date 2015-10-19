$(function() {

    var format = 'DD.MM.YYYY HH:mm';

    var popupDateFormat = 'DD MMM HH:mm';


    $(document).ready(function() {

        $('#calendar').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            editable: true,
            defaultView: 'month',
            lang: 'ru',
            selectable: true,
            selectHelper: true,
            eventLimit: true,
            //allDayDefault: false,
            timezone: 'local',
            weekends: false,
            fixedWeekCount: false,
            height: 600,

            //events:
            select: openNewToDoModal,
            events: allEvents,
            eventClick: openActionModal,
            eventResize: updateDates,
            eventDrop: updateDates,
            eventMouseover: showHint,
            eventMouseout: hideHint,
            eventRender: makeItBeautiful

        });

        $('a[href="#todo"]').on('shown.bs.tab', function (e) {
            $('#calendar').fullCalendar( 'render' );
            return true;
        });
    });

    function makeItBeautiful ( event, element, view ) {
        $(element).removeClass('todo-event-finished');
        $(element).removeClass('todo-event-missed');
        $(element).removeClass('todo-event-normal');
        if (event.finished) { // завершенные
            $(element).addClass('todo-event-finished');
        } else {
            if (!event.finished && moment().isAfter(event.start) && moment().isAfter(event.end)) { // пропущенные
                $(element).addClass('todo-event-missed');
            } else {
                $(element).addClass('todo-event-normal');
            }
        }
    };

    var $2;
    function showHint(event, jsEvent, view ) {
        $2 = $(jsEvent.toElement);
        $2.popover({
            title: event.title,
            content:
            (event.description || '') +
                '<br>' + (event.allDay ? 'Весь день ' : (event.start.format(popupDateFormat) + ' - ' + event.end.format(popupDateFormat))) +
                (event.finished ? ('<br>' + 'Завершено ' + moment(event.finished).format(popupDateFormat)) : ''),
            animation: false,
            trigger: 'manual',
            html: true,
            container: 'body',
            template:
                '<div class="popover" role="tooltip">' +
                '<div class="arrow">' +
                '</div><h3 class="popover-title">' +
                '</h3>' +
                '<div class="popover-content">' +
                '</div></div>'
        }).popover('show');
    }

    function hideHint(event, jsEvent, view ) {
        $2.popover('hide').popover('destroy');
    }

    function openNewToDoModal(start, end) {
        $('#toDoModal #todo-start-date').val(moment(start).format(format));
        $('#toDoModal #todo-end-date').val(moment(end).format(format));
        $('#toDoModal').modal('show');
    }

    function allEvents(start, end, timezone, callback) {
        $.ajax({
            url: 'client/calendar/get',
            data: {
                client: $('#todo-form #todo-client-id').val()
            },
            success: function(doc) {
                callback(doc.data);
            }
        });
    };

    function openActionModal(event, jsEvent, view) {
        $('#toDoActionsModal #todo-actions-title').val(event.title);
        $('#toDoActionsModal #todo-actions-description').val(event.description);
        $('#toDoActionsModal #todo-client-todo').val(event.id);
        $('#toDoActionsModal').modal('show');
    };

    function updateDates(event, delta, revertFunc) {

        $.ajax({
            type: "post",
            dataType: "json",
            cache: false,
            url: 'client/calendar/update_dates',
            data: {
                start: event.start.format(format),
                end: event.end.format(format),
                clientToDo: event.id
            },
            success: function(data) {
                messages.addMessages(data.messages);
            }
        });
    };

    $('#createToDoBtn').click(function(){
        var $form = $("#todo-form");
        if (!$form.valid()) {
            return;
        }

        $(this).button('loading');
        var data = {};
        $form.serializeArray().map(function(x){data[x.name] = x.value;});

        var url = "client/calendar/new";
        var request = $.post(url, data);

        request.success(function(data){
            if (data.result) {
                CountUpdater.updateCountToDo();

                var $calendar = $('#calendar');
                $calendar.fullCalendar('removeEvents', data.data.id);

                var calendar = $calendar.fullCalendar('getCalendar');

                data.data.start = calendar.moment(data.data.start);
                data.data.end = calendar.moment(data.data.end);
                $calendar.fullCalendar('renderEvent', data.data);
                $calendar.fullCalendar('unselect');
                $('#toDoModal').modal('hide');
            }

            $('#createToDoBtn').button('reset');
            messages.addMessages(data.messages);
        });
    });

    $('#deleteToDoBtn').click(function(){
        $(this).button('loading');
        var data = {};
        $("#todo-actions-form").serializeArray().map(function(x){data[x.name] = x.value;});

        var id = data.clientToDo;

        var url = "client/calendar/delete";
        var request = $.post(url, data);

        request.success(function(data){
            if (data.result) {
                CountUpdater.updateCountToDo();

                $('#calendar').fullCalendar('removeEvents', id);
                $('#toDoActionsModal').modal('hide');
            }

            $('#deleteToDoBtn').button('reset');
            messages.addMessages(data.messages);
        });
    });

    $('#updateToDoBtn').click(function(){

        if (!$('#todo-actions-form').valid()) {
            return;
        }

        $(this).button('loading');
        var data = {};
        $("#todo-actions-form").serializeArray().map(function(x){data[x.name] = x.value;});

        var id = data.clientToDo;

        var url = "client/calendar/update_text";
        var request = $.post(url, data);

        request.success(function(data){
            if (data.result) {
                CountUpdater.updateCountToDo();

                var original = $('#calendar').fullCalendar('clientEvents', id)[0];
                var updated = data.data;
                original.title = updated.title;
                original.description = updated.description;
                $('#calendar').fullCalendar('updateEvent', original);

                $('#toDoActionsModal').modal('hide');
            }

            $('#updateToDoBtn').button('reset');
            messages.addMessages(data.messages);
        });
    });

    $('#finishToDoBtn').click(function(){

        var $form = $("#todo-actions-form");
        if (!$form.valid()) {
            return;
        }

        $(this).button('loading');
        var data = {};
        $form.serializeArray().map(function(x){data[x.name] = x.value;});

        var id = data.clientToDo;

        var url = "client/calendar/finish";
        var request = $.post(url, data);

        request.success(function(data){
            if (data.result) {
                CountUpdater.updateCountToDo();

                var original = $('#calendar').fullCalendar('clientEvents', id)[0];
                var updated = data.data;
                original.title = updated.title;
                original.description = updated.description;
                original.finished = updated.finished;
                $('#calendar').fullCalendar('updateEvent', original);

                $('#toDoActionsModal').modal('hide');
            }

            $('#finishToDoBtn').button('reset');
            messages.addMessages(data.messages);
        });
    });

});
