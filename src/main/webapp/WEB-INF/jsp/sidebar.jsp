<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/sidebar.css"/>" >
<script type="text/javascript" src="<c:url value="/assets/js/sidebar.js"/>"></script>

    <div id="sidebar-wrapper">
        <ul id="sidebar_menu" class="sidebar-nav">
            <li class="sidebar-brand"><a id="menu-toggle" href="#">Меню<span id="main_icon" class="glyphicon glyphicon-align-justify"></span></a></li>
        </ul>
        <ul class="sidebar-nav" id="sidebar">

            <li><a href="<c:url value="/"/>">Клиенты<span class="sub_icon glyphicon glyphicon-user"></span></a></li>
            <%--<li><a>Отчеты<span class="sub_icon glyphicon glyphicon-link"></span></a></li>--%>
            <sec:authorize ifAnyGranted="ROLE_OPERATOR">
            <li>
                <a href="<c:url value="/todo_by_operator/"/>">Задачи
                    <span id="menu_todo_count" class="badge"></span>
                    <span id="menu_todo_icon" class="sub_icon glyphicon glyphicon-tasks"></span>
                </a></li>
            </sec:authorize>
            <li><a href="<c:url value="/groups/"/>">Группы<span class="sub_icon glyphicon glyphicon-th-list"></span></a></li>
            <li><a href="<c:url value="/templates/"/>">Шаблоны<span class="sub_icon glyphicon glyphicon-envelope"></span></a></li>

            <li><a href="<c:url value="/settings/"/>"><small>Настройки</small><span class="sub_icon glyphicon glyphicon-cog"></span></a></li>

            <li><a href="<c:url value="/j_spring_security_logout"/>">Выйти<span class="sub_icon glyphicon glyphicon-off"></span></a></li>
        </ul>
    </div>

    <form id="base-urls">
        <input type="hidden" id="url_todo_by_operator" value="<c:url value="/todo_by_operator/count"/>">
        <input type="hidden" id="url_all_templates" value="<c:url value="/templates/all"/>">
        <input type="hidden" id="url_show_template" value="<c:url value="/templates/template"/>">
        <input type="hidden" id="url_send_delivery" value="<c:url value="/delivery/send"/>">
        <input type="hidden" id="url_send_custom_delivery" value="<c:url value="/delivery/send_custom"/>">
        <input type="hidden" id="url_report_stop_contracts" value="<c:url value="/report/stop_contracts_template"/>">

        <input type="hidden" id="url_get_file" value="<c:url value="/files/get/"/>">
        <input type="hidden" id="url_remove_file" value="<c:url value="/files/remove/"/>">
        <input type="hidden" id="url_upload_file" value="<c:url value="/client/file/upload"/>">

        <input type="hidden" id="url_integration_run" value="<c:url value="/integration/run"/>">
        <input type="hidden" id="url_integration_clients" value="<c:url value="/integration/clients"/>">
        <input type="hidden" id="url_integration_contracts" value="<c:url value="/integration/contracts"/>">
        <input type="hidden" id="url_integration_total_invoices" value="<c:url value="/integration/total_invoices"/>">
        <input type="hidden" id="url_integration_invoices" value="<c:url value="/integration/invoices"/>">
        <input type="hidden" id="url_integration_payments" value="<c:url value="/integration/payments"/>">

        <input type="hidden" id="url_integration_check_uncompleted" value="<c:url value="/integration/check_uncompleted"/>">
        <input type="hidden" id="url_integration_check_last_completed" value="<c:url value="/integration/check_last_completed"/>">

        <input type="hidden" id="url_settings_chane_password" value="<c:url value="/settings/change/password"/>">
        <input type="hidden" id="url_settings_change_sms_login" value="<c:url value="/settings/change/sms/login"/>">
        <input type="hidden" id="url_settings_change_sms_password" value="<c:url value="/settings/change/sms/password"/>">
        <input type="hidden" id="url_settings_change_integration_path" value="<c:url value="/settings/change/integration/path"/>">
        <input type="hidden" id="url_settings_change_files_path" value="<c:url value="/settings/change/files/path"/>">
    </form>
