<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--@elvariable id="patterns" type="java.util.List<com.crm.guard.service.delivery.parse.DeliveryPatterns>"--%>

<html lang="ru">
<jsp:include page="head.jsp"/>
<body>
<div class="container-fluid">

    <div id="wrapper" class="active">
        <jsp:include page="navbar.jsp"/>
        <jsp:include page="sidebar.jsp"/>
        <div id="page-content-wrapper">
            <div class="row">
                <ol class="breadcrumb clearfix">
                    <li><a href="<c:url value="/"/>">Главная</a></li>
                    <li class="active">Настройки</li>
                    <jsp:include page="logo.jsp"/>
                </ol>
            </div>


            <ul class="nav nav-tabs">
                <li role="presentation" class="active"><a data-toggle="tab" aria-expanded="true" href="#profile">Профиль</a></li>
                <sec:authorize ifAnyGranted="ROLE_ADMIN">
                    <li role="presentation"><a data-toggle="tab" aria-expanded="true" href="#sms">СМС</a></li>
                    <li role="presentation"><a data-toggle="tab" aria-expanded="true" href="#integration">1С интеграция</a></li>
                    <li role="presentation"><a data-toggle="tab" aria-expanded="true" href="#other">Другое</a></li>
                </sec:authorize>
            </ul>

            <div class="tab-content">
                <div id="profile" class="tab-pane active">
                    <form id="change-password-form" class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-5 control-label">Изменить пароль</label>
                            <div class="col-sm-5">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="oldPass" class="col-sm-3 control-label">Введите старый пароль</label>
                            <div class="col-sm-5">
                                <input required type="password" class="form-control" name="oldPass" id="oldPass" placeholder="Старый пароль">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="newPass" class="col-sm-3 control-label">Введите новый пароль</label>
                            <div class="col-sm-5">
                                <input required type="password" class="form-control" name="newPass" id="newPass" placeholder="Новый пароль">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-2">
                                <button id="change_password" type="button" class="btn btn-default">Изменить</button>
                            </div>
                        </div>
                    </form>
                </div>

                <div id="sms" class="tab-pane">

                    <form id="change-sms-login-form" class="form-horizontal">
                        <div class="form-group">
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Введите новый логин</label>
                            <div class="col-sm-5">
                                <input required type="password" class="form-control"
                                       name="login" placeholder="Логин" value="${smsLogin}">
                            </div>
                            <button id="change_sms_login" type="button" class="btn btn-default">Изменить</button>
                        </div>

                    </form>

                    <form id="change-sms-password-form" class="form-horizontal">
                        <div class="form-group">
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Введите новый пароль</label>
                            <div class="col-sm-5">
                                <input required type="password" class="form-control"
                                       name="password" placeholder="Пароль" value="${smsPassword}">
                            </div>
                            <button id="change_sms_password" type="button" class="btn btn-default">Изменить</button>

                        </div>
                    </form>

                </div>

                <div id="integration" class="tab-pane">
                    <jsp:include page="integration.jsp"/>
                </div>

                <div id="other" class="tab-pane">
                    <h3>Путь хранения файлов контрагентов</h3>
                    <form id="change-files-path-form" class="form-horizontal">
                        <div class="form-group">
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Введите путь</label>
                            <div class="col-sm-5">
                                <input required class="form-control"
                                       name="path" placeholder="Директория" value="${filesPath}">
                            </div>
                            <button id="change-files-path" type="button" class="btn btn-default">Изменить</button>
                        </div>

                    </form>
                </div>
            </div>

        </div>
    </div>

    <jsp:include page="footer.jsp"/>

    <div class='notifications bottom-right'></div>

</div>
</body>
</html>

<script type="text/javascript" src="<c:url value="/assets/js/settings.js"/>"></script>