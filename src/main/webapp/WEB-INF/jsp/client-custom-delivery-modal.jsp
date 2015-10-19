<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="modal fade" id="sendCustomDeliveryModal" tabindex="-1" role="dialog" aria-labelledby="sendCustomDeliveryModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="groupModalLabel">Разослать сообщения <span id="send-delivery-clients-count"></span> клиентам</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" url-selector="#url_send_custom_delivery" style="margin-top: 30px" method="post" role="form" id="send-delivery-form">
                    <input type="hidden" name="limit" value="1000">
                    <input type="hidden" name="offset" value="0">
                    <input type="hidden" name="oder" value="asc">
                    <input type="hidden" id="send-delivery-selected-clients" name="clients">
                    <input type="hidden" class="form-control" id="type" name="type">

                    <div class="form-group">
                        <label class="col-md-3 control-label" for="subject">Заголовок</label>
                        <div class="col-md-9">
                            <input class="form-control" id="subject" name="subject" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label" for="text">Текст</label>
                        <div class="col-md-9">
                            <textarea rows="10" id="text" name="text" required class="form-control"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Отмена</button>
                <button data-loading-text="Отправляем письма..." id="send-custom-templates-btn" type="button" class="btn btn-primary">Отправить</button>
            </div>
        </div>
    </div>
</div>