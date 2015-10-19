<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="ru">
<jsp:include page="head.jsp"/>
<script type="text/javascript" src="<c:url value="/assets/js/contact_edit.js"/>"></script>
<link type="text/css" rel="stylesheet" href="<c:url value="/assets/lib/bootstrap/css/bootstrap-datetimepicker.min.css"/>" >
<body>
<style>
    form {
        background-color: #fff;
        -webkit-box-shadow: none;
        box-shadow: none;
    }
</style>
<div class="container-fluid">

    <div id="wrapper" class="active">

        <jsp:include page="navbar.jsp"/>
        <jsp:include page="sidebar.jsp"/>
        <div id="page-content-wrapper">
            <div class="row">
                <ol class="breadcrumb clearfix">
                    <li><a href="<c:url value="/"/>">Главная</a></li>
                    <li><a href="<c:url value="/"/>">Клиенты</a></li>
                    <li><a href="<c:url value="/client?id=${client.id}"/>">${client.shortName}</a></li>
                    <c:if test="${contact != null}">
                        <li class="active">${contact.firstName} ${contact.lastName} ${contact.middleName}</li>
                    </c:if>
                    <c:if test="${contact == null}">
                        <li class="active">Новый контакт</li>
                    </c:if>
                    <jsp:include page="logo.jsp"/>
                </ol>

                <c:if test="${contact != null}">
                    <h2>${contact.firstName} ${contact.lastName} ${contact.middleName}</h2>
                </c:if>
                <c:if test="${contact == null}">
                    <h2>Новый контакт</h2>
                </c:if>


            </div>
            <hr/>
            <div class="row">
                <form id="userForm" name="userForm" role="form" class="form-horizontal " style="margin-top: 20px">
                    <input type="hidden" name="id" value="<c:if test="${contact != null}">${contact.id}</c:if>">
                    <input type="hidden" name="order" value="<c:if test="${contact != null}">${contact.order}</c:if>">
                    <input type="hidden" name="client" value="${client.id}">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="firstName">Фамилия</label>
                        <div class=" col-sm-6 col-lg-6 col-md-6">
                            <input value="${contact.firstName}" name="firstName" type="text" class="form-control" id="firstName" placeholder="Фамилия">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="lastName">Имя</label>
                        <div class=" col-sm-6 col-lg-6 col-md-6">
                            <input value="${contact.lastName}" name="lastName" type="text" class="form-control" id="lastName" placeholder="Имя">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="middleName">Отчество</label>
                        <div class=" col-sm-6 col-lg-6 col-md-6">
                            <input value="${contact.middleName}" name="middleName" type="text" class="form-control" id="middleName" placeholder="Отчество">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sex1" class="control-label col-sm-2">Пол</label>
                        <div id="sex1" class="btn-group col-sm-6 col-lg-6 col-md-6" style="padding-top: 6px">
                            <input id="Mvalue" type="radio" name="sex" value="M" <c:if test="${contact != null && contact.sex == 'M'}">checked="checked"</c:if>>
                            <label for="Mvalue">Муж.</label>
                            <input id="Fvalue" type="radio" name="sex" value="F" <c:if test="${contact != null && contact.sex == 'F'}">checked="checked"</c:if>>
                            <label for="Fvalue">Жен.</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="birthDate">Дата рождения</label>
                        <div class='input-group date col-sm-3' id='birthDate' style="padding: 0 15px">
                            <input value="${contact.birthDate}"  name="birthDate" type='text' class="form-control" data-date-format="DD-MM-YYYY"/>
                            <span class="input-group-addon">
						        <span class="glyphicon glyphicon-calendar"></span>
					        </span>
                            </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="mobileNumber2">Номер телефона1</label>
                        <div class=" col-sm-3 col-lg-3 col-md-3">
                            <input value="${contact.mobileNumber2}" name="mobileNumber2" type="text" class="form-control" id="mobileNumber2" placeholder="Номер телефона">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="mobileNumber3">Номер телефона2</label>
                        <div class=" col-sm-3 col-lg-3 col-md-3">
                            <input value="${contact.mobileNumber3}" name="mobileNumber3" type="text" class="form-control" id="mobileNumber3" placeholder="Номер телефона">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="homeAddress">Домашний адрес</label>
                        <div class=" col-sm-6 col-lg-6 col-md-6">
                            <input  value="${contact.homeAddress}" name="homeAddress" type="text" class="form-control" id="homeAddress" placeholder="Домашний адрес">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="postAddress">Почтовый адрес</label>
                        <div class=" col-sm-6 col-lg-6 col-md-6">
                            <input  value="${contact.postAddress}" name="postAddress" type="text" class="form-control" id="postAddress" placeholder="Почтовый адрес">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-6 col-lg-6 col-md-6">
                            <button data-loading-text="Сохранение..." id="updateContactBtn" type="button" class="btn btn-success">
                                <c:if test="${contact == null}">
                                    Добавить пользователя
                                </c:if>
                                <c:if test="${contact != null}">
                                    Сохранить изменения
                                </c:if>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

    </div>

    <div class='notifications bottom-right'></div>

    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
