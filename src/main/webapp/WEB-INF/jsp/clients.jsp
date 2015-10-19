<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%--@elvariable id="entities" type="java.util.List<com.crm.guard.entity.Entity>"--%>
<%--@elvariable id="groups" type="java.util.List<com.crm.guard.entity.ClientGroup>"--%>
<%--@elvariable id="operators" type="java.util.List<com.crm.guard.entity.User>"--%>

<script type="text/javascript" src="<c:url value="/assets/js/table/ClientsTable.js"/>"></script>
<script type="text/javascript" src="<c:url value="/assets/js/clients.js"/>"></script>
<script type="text/javascript" src="<c:url value="/assets/js/clients_delivery.js"/>"></script>
<script type="text/javascript" src="<c:url value="/assets/js/formatter/ClientTypeEditor.js"/>"></script>
<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/client.css"/>" >

<div class="row">
    <ol class="breadcrumb clearfix">
        <li><a href="<c:url value="/"/>">Главная</a></li>
        <li class="active">Клиенты</li>
        <jsp:include page="logo.jsp"/>
    </ol>

    <div>

        <div id="clientsActionBar" class="btn-group" role="group">
            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                <span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span> Действия
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu" role="menu">
                <li>
                    <a data-title="СМС" data-toggle="modal" data-target="#sendDeliveryModal"
                       href="javascript:void(0);" id="sendTemplateBtn">Разослать шаблоны</a>
                </li>
                <li>
                    <a delivery-type="SMS" data-title="СМС" data-toggle="modal" data-target="#sendCustomDeliveryModal"
                       href="javascript:void(0);" id="smsBtn">СМС</a>
                </li>
                <li>
                    <a delivery-type="EMAIL" data-title="Электронное письмо" data-toggle="modal" data-target="#sendCustomDeliveryModal"
                       href="javascript:void(0);" id="emailBtn">Электронное письмо</a>
                </li>
            </ul>
        </div>

        <div id="entitiesBar" class="btn-group" role="group">
            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                <span class="" aria-hidden="true"></span> Фильтр
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu" role="menu">
                <sec:authorize ifAnyGranted="ROLE_OPERATOR">
                    <li>
                        <input type="checkbox" id="onlyMine" to="<sec:authentication property="principal.name"/>"> <label for="onlyMine">Мои клиенты</label>
                    </li>
                    <li>
                        <input type="checkbox" id="onlyWithToDo"><label for="onlyWithToDo">Есть задачи</label>
                    </li>
                    <li>
                        <input type="checkbox" id="onlyDisabled"><label for="onlyDisabled">Только отключенные</label>
                    </li>
                </sec:authorize>
                <li>
                    <select id="entities" multiple="true">
                        <c:forEach items="${entities}" var="entity">
                            <option value="${entity.id}">${entity.shortName}</option>
                        </c:forEach>
                    </select>
                </li>
                <li>
                    <select id="groups" multiple="true">
                        <c:forEach items="${groups}" var="group">
                            <option value="${group.code}">${group.description}</option>
                        </c:forEach>
                    </select>
                </li>
                <li>
                    <select id="operators" multiple="true">
                        <c:forEach items="${operators}" var="operator">
                            <option value="${operator.name}">${operator.lastName} ${operator.firstName} ${operator.middleName}</option>
                        </c:forEach>
                    </select>
                </li>
            </ul>
        </div>


    </div>

    <div id="filter-bar">

    </div>

    <div class="bars pull-right" style="margin-top: 10px;">
        <button id="xlsExportBtn" type="button" class="btn btn-default"
                data-toggle="dropdown">
            <span class="glyphicon glyphicon-download-alt"></span> Выгрузить в Excel
        </button>
    </div>

    <div class="bootstrap-table">
        <div>
            <table id="clients-table"
                   class="table table-hover"
                   data-url="clients/table/data"
                   data-side-pagination="server"
                   data-pagination="true"
                   data-search="true"
                   data-page-list="[5, 10, 20, 50, 100, 200]"
                   data-toolbar="#filter-bar"
                   data-click-to-select="true"
                   data-show-export="true"
                   data-flat="true"
                   data-show-filter="true">
                <thead>
                <tr>
                    <th data-field="" data-checkbox="true"></th>
                    <th data-field="id"></th>
                    <th data-field="shortName" data-formatter="nameFormatter" data-align="right" data-sortable="true">Наименование</th>
                    <th data-field="type" data-align="center" data-formatter="clientTypeFormatter" data-sortable="true">Тип</th>
                    <th data-field="inn" data-sortable="true">ИНН</th>
                    <th data-field="registrationAddress" data-sortable="true">Адрес</th>
                    <th data-field="totalInvoice.invoice" data-sortable="true" data-formatter="totalInvoiceFormatter">Задолженность, р.</th>
                    <th data-field="detailed-info" data-sortable="false" data-formatter="clientInfoFormatter"></th>
                </tr>
                </thead>
            </table>
        </div>
    </div>

    <div id="client-info-container" style="display: none;">
        <button type="button" class="btn btn-info btn-sm client-info-toggle"
                content-down="Информация (развернуть)" content-up="Информация (свернуть)"
                icon-down="glyphicon-collapse-down" icon-up="glyphicon-collapse-up">
            <span class="glyphicon" aria-hidden="true"></span>
            <span class="content"></span>
        </button>
        <jsp:include page="client-info.jsp"/>
    </div>
</div>

<jsp:include page="client-delivery-modal.jsp"/>
<jsp:include page="client-custom-delivery-modal.jsp"/>

<div class='notifications bottom-right'></div>



