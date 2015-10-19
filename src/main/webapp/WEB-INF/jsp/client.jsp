<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--@elvariable id="myClient" type="java.lang.Boolean"--%>

<html lang="ru">
<jsp:include page="head.jsp"/>
<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/client.css"/>" >
<body>
<style>
    .active_record {
        font-weight: bold;
    }

    .table tr td:first-child {
        width: 25%;
    }
</style>
    <form id="client-form">
        <input type="hidden" name="id" value="${client.id}">
    </form>

    <div class="container-fluid">
        <input type="hidden" id="clientId" value="${client.id}">

        <div id="wrapper" class="active">

                <jsp:include page="navbar.jsp"/>
                <jsp:include page="sidebar.jsp"/>
                <div id="page-content-wrapper">
                    <div class="row">
                        <ol class="breadcrumb clearfix">
                            <li><a href="<c:url value="/"/>">Главная</a></li>
                            <li><a href="<c:url value="/"/>">Клиенты</a></li>
                            <li class="active">${client.shortName}</li>
                            <jsp:include page="logo.jsp"/>
                        </ol>

                        <h2>
                            ${client.shortName}
                                <small>
                                    <c:if test="${client.totalInvoice.invoice != null}">
                                        <span id="mainFullDebt" class="label label-primary">
                                            ${client.totalInvoice.invoice} р.
                                        </span>
                                    </c:if>
                                    <c:if test="${client.operator != null}">
                                        <span style="margin-left: 5px;" class="label label-primary">
                                            ${client.operator.lastName} ${client.operator.firstName} ${client.operator.middleName}
                                        </span>
                                    </c:if>
                                    <c:if test="${not empty disabledContractMessage}">
                                        <span style="margin-left: 5px;" class="label label-danger">
                                            ${disabledContractMessage}
                                        </span>
                                    </c:if>
                                </small>
                        </h2>
                        <ul class="nav nav-tabs">
                            <li role="presentation" class="active"><a data-toggle="tab" aria-expanded="true" href="#client">Информация</a></li>
                            <li role="presentation"><a data-toggle="tab" aria-expanded="true" href="#contacts">Ответственные лица</a></li>
                            <li role="presentation"><a data-toggle="tab" aria-expanded="true" href="#bills">Долги</a></li>
                            <li role="presentation"><a data-toggle="tab" aria-expanded="true" href="#history">История</a></li>
                            <li role="presentation"><a data-toggle="tab" aria-expanded="true" href="#files">Файлы</a></li>
                            <li role="presentation"><a data-toggle="tab" aria-expanded="true" href="#todo">Задачи</a></li>
                        </ul>
                        <div class="tab-content">
                            <div id="contacts" class="tab-pane">
                                <jsp:include page="contacts.jsp"/>
                            </div>
                            <div id="bills" class="tab-pane">
                                <jsp:include page="bills.jsp"/>
                            </div>
                            <div id="history" class="tab-pane">

                            </div>

                            <div id="files" class="tab-pane">
                                <jsp:include page="file.jsp"/>
                            </div>
                            <div id="todo" class="tab-pane">
                                <jsp:include page="client_todo/client_todo.jsp"/>
                            </div>
                            <div id="client" class="tab-pane active">
                                <div class="col-md-6" style="padding-left: 0px; margin-top: 20px">
                                    <jsp:include page="client-info.jsp"/>
                                    <form class="form-horizontal" id="client-update-form-id">
                                        <input type="hidden" name="id" value="${client.id}">
                                        <div class="form-group">
                                            <label for="mobileNumber" class="col-sm-2 control-label">Телефон</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="mobileNumber" name="mobileNumber" value="${client.mobileNumber}" placeholder="Телефон">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="email" class="col-sm-2 control-label">Эл. почта</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="email" name="email" value="${client.email}" placeholder="Эл. почта">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="disabledClient" class="col-sm-2 control-label">Отключен</label>
                                            <div class="col-sm-1">
                                                <input type="checkbox" class="form-control" id="disabledClient" name="disabled" ${client.disabled ? 'checked' : ''}>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <button type="button" class="btn btn-success col-sm-offset-1" id="save-client">Сохранить</button>
                                        </div>

                                    </form>
                                </div>
                                <div class="col-md-6">
                                    <jsp:include page="contact_actions.jsp"/>
                                    <tags:operation
                                            client="${client.id}"
                                            contact="1"
                                            url="contact/action/save"
                                            commentName="Результат"/>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>

            </div>

        <div class='notifications bottom-right'></div>

        <jsp:include page="footer.jsp"/>
    </div>
</body>

<script type="text/javascript" src="<c:url value="/assets/js/client_history.js"/>"></script>
<script type="text/javascript" src="<c:url value="/assets/js/client_operator.js"/>"></script>
<script type="text/javascript" src="<c:url value="/assets/js/client_invoices.js"/>"></script>

</html>