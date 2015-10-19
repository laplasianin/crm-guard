<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h2><span id="masterContactActionFromLastName">${contact.lastName}</span>
    <span id="masterContactActionFromFirstName">${contact.firstName}</span>
    <span id="masterContactActionFromMiddleName">${contact.middleName}</span></h2>

<div style="padding: 10px 0">
    <div class="btn-group" role="group" aria-label="...">
        <button data-code="CALL" data-title="Звонок" data-toggle="modal" data-target="#actionModal" type="button" class="col-md-3 btn btn-default"><span class="glyphicon glyphicon-earphone" aria-hidden="true"></span> Звонок</button>
        <button data-code="SMS" data-title="Сообщение" data-toggle="modal" data-target="#actionModal" type="button" class="col-md-3 btn btn-default"><span class="glyphicon glyphicon-phone" aria-hidden="true"></span> Сообщение</button>
        <button data-code="EMAIL" data-title="E-mail" data-toggle="modal" data-target="#actionModal" type="button" class="col-md-3 btn btn-default"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span> E-mail</button>
        <button data-code="CLAIM" data-title="Претензия" data-toggle="modal" data-target="#actionModal" type="button" class="col-md-3 btn btn-default"><span class="glyphicon glyphicon-thumbs-down" aria-hidden="true"></span> Претензия</button>
        <button data-code="DISABLE_NOTIFY" data-title="Уведомление об отключении" data-toggle="modal" data-target="#actionModal" type="button" class="col-md-6 btn btn-default"><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> Уведомление об отключении</button>
        <button data-code="DISABLED" data-title="Объект отключен" data-toggle="modal" data-target="#actionModal" type="button" class="col-md-6 btn btn-default"><span class="glyphicon glyphicon-fire" aria-hidden="true"></span> Объект отключен</button>
        <a class="col-md-6 btn btn-default" href="<c:url value="/report/stop_contracts_template"/>?id=${client.id}" role="button">
            <span class="glyphicon glyphicon-print" aria-hidden="true"></span> Печать расторжения договора
        </a>
        <a class="col-md-6 btn btn-default" href="<c:url value="/report/stop_guarding_template"/>?id=${client.id}" role="button">
            <span class="glyphicon glyphicon-print" aria-hidden="true"></span> Печать прекращения охраны
        </a>
        <button data-code="BILL_DUPLICATION" data-title="Продублирован счет" data-toggle="modal" data-target="#actionModal" type="button" class="col-md-6 btn btn-default"><span class="glyphicon glyphicon-credit-card" aria-hidden="true"></span> Продублирован счет</button>
    </div>
</div>