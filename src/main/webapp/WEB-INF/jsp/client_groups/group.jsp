<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%--@elvariable id="group" type="com.crm.guard.entity.ClientGroup"--%>
<%--@elvariable id="clients" type="java.util.List<com.crm.guard.entity.Client>"--%>

<html lang="ru">
<jsp:include page="../head.jsp"/>
<body>
<div class="container-fluid">

    <div id="wrapper" class="active">

        <jsp:include page="../navbar.jsp"/>
        <jsp:include page="../sidebar.jsp"/>
        <div id="page-content-wrapper">

            <div class="row">
                <ol class="breadcrumb clearfix">
                    <li><a href="<c:url value="/"/>">Главная</a></li>
                    <li><a href="<c:url value="/groups/"/>">Группы клиентов</a></li>
                    <li class="active">${group.code}</li>
                    <jsp:include page="../logo.jsp"/>
                </ol>

                <div class="col-md-12">
                    <h1>Группа ${group.code}
                        <button data-loading-text="Удаляем..." id="removeGroupBtn" code="${group.code}" type="button" class="btn btn-warning btn-sm">
                            <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                            Удалить
                        </button>
                        <button id="editGroupBtn" type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#groupModal">
                            <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                            Редактировать
                        </button>
                        <button id="addClient" type="button" class="btn btn-success btn-sm" data-toggle="modal" data-target="#addClientModal">
                            <span class="glyphicon glyphicon-add" aria-hidden="true"></span>
                            Добавить участника
                        </button>
                    </h1>
                </div>

                <div class="row">
                    <div class="col-md-8 col-md-offset-1">
                        <h3>${group.description}</h3>
                    </div>
                </div>

                <div class="col-md-6">
                    <h1>Участники</h1>
                </div>

                <div id="filter-bar"> </div>
                <div class="bootstrap-table">
                    <div>
                        <table id="clients-table"
                               class="table table-hover"
                               data-pagination="true"
                               data-page-list="[5, 10, 20, 50, 100, 200]"
                               data-search="true"
                               data-toolbar="#filter-bar"
                               data-click-to-select="true"
                               data-show-toggle="true"
                               data-show-columns="false"
                               data-show-refresh="true"
                               data-flat="true"
                               data-show-filter="false">
                            <thead>
                            <tr>
                                <th data-align="left" data-sortable="true">Клиент</th>
                                <th data-align="center" data-sortable="false"></th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach items="${group.clients}" var="client">
                                <tr>
                                    <td><a href="<c:url value="/client"/>?id=${client.id}">${client.fullName}</a></td>
                                    <td>
                                        <button data-loading-text="Удаляем..."  group="${group.code}" client="${client.id}" type="button" class="btn btn-warning btn-sm deleteClientBtn">
                                            <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                                            Удалить
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>

                    </div>
                </div>
            </div>

            <div class="modal fade" id="groupModal" tabindex="-1" role="dialog" aria-labelledby="groupModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="groupModalLabel">Редактировать группу</h4>
                        </div>
                        <div class="modal-body">
                            <form id="group-form" class="form-horizontal">
                                <input type="hidden" class="form-control" maxLength="45" id="group-code" name="code" value="${group.code}" required>

                                <div class="form-group">

                                    <label for="group-description" class="col-md-3 control-label">Описание</label>
                                    <div class="col-md-8">
                                        <textarea type="text" class="form-control" maxLength="250" id="group-description" required name="description" cols="3">${group.description}</textarea>
                                    </div>

                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Отмена</button>
                            <button data-loading-text="Редактируем..." id="updateBtn" type="button" class="btn btn-primary">ОК</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="addClientModal" tabindex="-1" role="dialog" aria-labelledby="addClientModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="addClientModalLabel">Добавить участника</h4>
                        </div>
                        <div class="modal-body">
                            <form id="add-client-form" class="form-horizontal">
                                <input type="hidden" class="form-control" id="groupForAddingClient" name="group" value="${group.code}">

                                <div class="form-group">
                                    <label class="col-md-3 control-label">Клиент</label>
                                    <select id="clientToAdd" name="client" required>
                                        <c:forEach items="${clients}" var="client">
                                            <option value="${client.id}">${client.fullName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                            <button data-loading-text="Добавляем..." id="addClientBtn" type="button" class="btn btn-primary">Добавить</button>
                        </div>
                    </div>
                </div>
            </div>



            <div class='notifications bottom-right'></div>
            <jsp:include page="../footer.jsp"/>
        </div>
    </div>

</div>
</body>

<script type="text/javascript" src="<c:url value="/assets/js/client_group.js"/>"></script>

</html>



