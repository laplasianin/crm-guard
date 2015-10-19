<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--@elvariable id="client" type="com.crm.guard.entity.Client"--%>

<script type="text/javascript" src="<c:url value="/assets/lib/moment/moment.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/assets/lib/fullcalendar/fullcalendar.js"/>"></script>
<script type="text/javascript" src="<c:url value="/assets/lib/fullcalendar/lang-all.js"/>"></script>
<link type="text/css" rel="stylesheet" href="<c:url value="/assets/lib/fullcalendar/fullcalendar.css"/>" >
<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/client_todo_calendar.css"/>" >

<script type="text/javascript" src="<c:url value="/assets/js/client_todo.js"/>"></script>

<div class="modal fade" id="toDoModal" tabindex="-1" role="dialog" aria-labelledby="toDoModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="toDoModalLabel">Новая задача</h4>
            </div>
            <div class="modal-body">
                <form id="todo-form" class="form-horizontal">
                    <input type="hidden" class="form-control" id="todo-start-date" name="start">
                    <input type="hidden" class="form-control" id="todo-end-date" name="end">
                    <input type="hidden" class="form-control" id="todo-client-id" name="client" value="${client.id}">

                    <div class="form-group">
                        <label for="todo-title" class="col-md-3 control-label">Название задачи</label>
                        <div class="col-md-8">
                            <input type="text" class="form-control" id="todo-title" maxLength="150" required name="title" cols="3">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="todo-description" class="col-md-3 control-label">Описание</label>
                        <div class="col-md-8">
                            <textarea type="text" class="form-control" id="todo-description" maxLength="250" name="description" cols="3"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Отмена</button>
                <button data-loading-text="Создаем задачу..." id="createToDoBtn" type="button" class="btn btn-primary">Создать</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="toDoActionsModal" tabindex="-1" role="dialog" aria-labelledby="toDoActionsModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="toDoActionsModalLabel">Задача</h4>
            </div>
            <div class="modal-body">
                <form id="todo-actions-form" class="form-horizontal">
                    <input type="hidden" class="form-control" id="todo-client-todo" name="clientToDo">

                    <div class="form-group">
                        <label for="todo-actions-title" class="col-md-3 control-label">Название задачи</label>
                        <div class="col-md-8">
                            <input type="text" class="form-control" id="todo-actions-title" requred maxLength="150" required name="title" cols="3">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="todo-actions-description" class="col-md-3 control-label">Описание</label>
                        <div class="col-md-8">
                            <textarea type="text" class="form-control" id="todo-actions-description" maxLength="250" name="description" cols="3"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button data-loading-text="Удаляем..." id="deleteToDoBtn" type="button" class="btn btn-primary">Удалить</button>
                <button data-loading-text="Сохраняем..." id="updateToDoBtn" type="button" class="btn btn-primary">Сохранить</button>
                <button data-loading-text="Обработка..." id="finishToDoBtn" type="button" class="btn btn-primary">Выполнить</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
            </div>
        </div>
    </div>
</div>

<div style="margin-top: 7px;" class="col-md-10 col-md-offset-1" id="calendar"></div>

