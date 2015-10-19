<%--@elvariable id="client" type="com.crm.guard.entity.Client"--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<div class="bootstrap-table">
    <input type="hidden" id="clientId" value="${client.id}"/>
    <div>
        <table id="contacts-table" class="table table-hover"
               data-url="contacts/table/data?id=${client.id}" data-side-pagination="server"
               data-search="true">
            <thead>
            <tr>
                <th data-field="master" data-width="15" data-formatter="masterContactFormatter"></th>
                <th data-field="lastName" data-sortable="true" data-formatter="nameFormatterContact">ФИО</th>
                <th data-formatter="phoneFormatterContact">Телефон</th>
                <th data-field="homeAddress" data-sortable="true">Адрес</th>
                <th data-align="center" data-formatter="editButtonFormatter"></th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<a style="margin-top: 10px" href="<c:url value="contact/edit?client_id=${client.id}"></c:url>" class="btn btn-success" role="button">Добавить новый контакт</a>

<script type="text/javascript" src="<c:url value="/assets/js/phoneFormat.js"/>"></script>
<script type="text/javascript" src="<c:url value="/assets/js/formatter/phoneFormatter.js"/>"></script>
<script type="text/javascript" src="<c:url value="/assets/js/contacts.js"/>"></script>
