<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ tag pageEncoding="UTF-8" %>

<%@ attribute name="client" required="false" type="java.lang.String"%>
<%@ attribute name="contact" required="false" type="java.lang.String"%>
<%@ attribute name="url" required="true" type="java.lang.String"%>
<%@ attribute name="commentName" required="true"%>

<div class="modal fade" id="actionModal" tabindex="-1" role="dialog" aria-labelledby="actionModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <form action="" name="actionForm" method="post" role="form" id="actionForm">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title" id="actionModalLabel"></h4>
          </div>

          <div class="modal-body">

            <input type="hidden" id="actionCode" name="eventType"/>
            <input type="hidden" name="contact" value="${contact}"/>
            <input type="hidden" name="client" value="${client}"/>

            <div class="form-group">
              <label for="comment">${commentName}</label>
              <textarea class="form-control" id="comment" name="description" rows="3"></textarea>
            </div>

          </div>

          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Отмена</button>
            <button data-loading-text="Сохранение..." id="addActionBtn" type="button" class="btn btn-primary">Выполнить действие</button>
          </div>
        </form>
      </div>
    </div>
  </div>

<script>

    $('#actionModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var title = button.data('title');
        var code = button.data('code');
        var modal = $(this);
        modal.find('.modal-title').text(title);
        modal.find('.modal-body #actionCode').val(code);
    });

    $('#addActionBtn').on('click', function(){
        var form = $('#actionForm');
        var $btn = $(this).button('loading');
        $.ajax({
            url: "${url}",
            data: form.serialize(),
            type: "POST",
            dataType : "json",
            success: function( data ) {
                $('#actionModal').modal('hide');
                messages.addMessages(data.messages);
                $btn.button('reset');
            }
        });
    });
</script>