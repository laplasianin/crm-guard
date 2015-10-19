<%--@elvariable id="client" type="com.crm.guard.entity.Client"--%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--@elvariable id="myClient" type="java.lang.Boolean"--%>

<table class="table client-info-table">
    <tbody>
    <tr>
        <td>Краткое наименование</td>
        <td class="active_record client-info-shortName">${client.shortName}</td>
    </tr>
    <tr>
        <td>Полное наименование</td>
        <td class="active_record client-info-fullName">${client.fullName}</td>
    </tr>
    <tr>
        <td>Код</td>
        <td class="active_record client-info-code">${client.id}</td>
    </tr>
    <tr>
        <td>Тип</td>
        <td class="active_record client-info-type">
            <c:if test="${client.type == 'I'}">ФЛ</c:if>
            <c:if test="${client.type == 'P'}">ЮЛ</c:if>
        </td>
    </tr>
    <tr>
        <td>Номер телефона</td>
        <td class="active_record client-info-mobileNumber">${client.mobileNumber}</td>
    </tr>
    <tr>
        <td>Адрес регистрации</td>
        <td class="active_record client-info-registrationAddress">${client.registrationAddress}</td>
    </tr>
    <tr>
        <td>Счет</td>
        <td class="active_record client-info-personalAccount">${client.personalAccount}</td>
    </tr>
    <tr>
        <td>ИНН</td>
        <td class="active_record client-info-inn">${client.inn}</td>
    </tr>
    <tr>
        <td>ОКПО</td>
        <td class="active_record client-info-okpo">${client.okpo}</td>
    </tr>
    <tr class="client-info-operator-row">
        <td>Ответственный оператор</td>
        <td class="active_record">
            <c:choose>
                <c:when test="${client.operator != null}">
                    ${client.operator.lastName} ${client.operator.firstName} ${client.operator.middleName}
                    (${client.operator.name})
                    <c:if test="${myClient}">
                        <button data-loading-text="Обрабатываем..." id="kickOperatorFromWork" type="button" class="btn btn-danger btn-sm">
                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                            Отказаться от клиента
                        </button>
                    </c:if>
                </c:when>
                <c:otherwise>
                    Общий клиент
                    <button data-loading-text="Обрабатываем..." id="takeOperatorToWork" type="button" class="btn btn-primary btn-sm">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                        Взять клиента в работу
                    </button>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    </tbody>
</table>