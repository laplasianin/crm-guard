<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="ru">
<jsp:include page="../head.jsp"/>
<body>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">

<div id="login-overlay" class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <h4 class="modal-title" id="myModalLabel">Войти в систему <strong class="text-info">CRM GUARD</strong></h4>
        </div>
        <div class="modal-body">
            <div class="row">
                <div class="col-xs-6">
                    <div class="well">
                        <form id="loginForm" method="POST" action="<c:url value='/j_spring_security_check' />" method="POST" novalidate="novalidate">
                            <div class="form-group">
                                <label for="username" class="control-label">Имя пользователя</label>
                                <input type="text" class="form-control" id="username" name="j_username" value="" required="" title="Please enter you username" placeholder="example@gmail.com">
                                <span class="help-block"></span>
                            </div>
                            <div class="form-group">
                                <label for="password" class="control-label">Пароль</label>
                                <input type="password" class="form-control" id="password" name="j_password" value="" required="" title="Please enter your password">
                                <span class="help-block"></span>
                            </div>
                            <c:if test="${not empty error}">
                                <div id="loginErrorMsg" class="alert alert-error hide">Вы ввели неправильный логин или пароль</div>
                            </c:if>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="remember" id="remember"> Запомнить меня
                                </label>
                                <p class="help-block">(если это ваш компьютер)</p>
                            </div>
                            <button type="submit" name="submit" class="btn btn-info btn-block">ВОЙТИ</button>
                        </form>
                    </div>
                </div>
                <div class="col-xs-6">
                    <p class="lead">Широкие возможности работы с <span class="text-info">клиентами</span></p>
                    <ul class="list-unstyled" style="line-height: 2">
                        <li><span class="fa fa-check text-info"></span> Просмотр клиентов</li>
                        <li><span class="fa fa-check text-info"></span> Фильтрация</li>
                        <li><span class="fa fa-check text-info"></span> Бизнес отчеты</li>
                        <li><span class="fa fa-check text-info"></span> Аналитика</li>
                        <li><span class="fa fa-check text-info"></span> Проведения операции с клиентами</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../footer.jsp"/>
</body>
</html>


