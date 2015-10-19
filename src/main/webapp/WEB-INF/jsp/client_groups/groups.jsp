<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%--@elvariable id="groups" type="java.util.List<com.crm.guard.entity.ClientGroup>"--%>

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
                    <li class="active">Группы клиентов</li>
                    <jsp:include page="../logo.jsp"/>
                </ol>

                <div class="col-md-6">
                    <h1>Группы клиентов
                    <button id="createGroup" type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#groupModal">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                        Создать группу
                    </button>
                    </h1>
                </div>

                <div id="filter-bar"> </div>
                <div class="bootstrap-table">
                    <div>
                        <table id="groups-table"
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
                                <th data-align="left" data-sortable="true">Код группы</th>
                                <th data-align="left" data-sortable="true">Название</th>
                                <th data-align="center" data-sortable="false"></th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach items="${groups}" var="group">
                                <tr>
                                    <td><a href="<c:url value="/groups/show"/>?code=${group.code}">${group.code}</a></td>
                                    <td>${group.description}</td>
                                    <td>
                                        <button data-loading-text="Удаление..." id="removeGroupBtn" code="${group.code}" type="button" class="btn btn-warning btn-sm">
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
                            <h4 class="modal-title" id="groupModalLabel">Создать группу</h4>   // TODO почему форма поехала?
                        </div>
                        <div class="modal-body">
                            <form id="group-form" class="form-horizontal">
                                <div class="form-group">
                                    <label for="group-code" class="control-label">Код</label>
                                    <div class="col-md-8">
                                        <input type="text" class="form-control" maxLength="45" id="group-code" name="code" required>
                                    </div>
                                </div>
                                <div class="form-group">

                                    <label for="group-description" class="control-label">Название</label>
                                    <div class="col-md-8">
                                        <textarea class="form-control" id="group-description" maxLength="250" name="description" cols="3" required></textarea>
                                    </div>

                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Отмена</button>
                            <button data-loading-text="Создание..." id="saveUpdateBtn" type="button" class="btn btn-primary">Создать</button>
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

<script type="text/javascript" src="<c:url value="/assets/js/client_groups.js"/>"></script>

</html>



