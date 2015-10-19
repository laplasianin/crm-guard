<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="ru">
<jsp:include page="head.jsp"/>
<script type="text/javascript" src="<c:url value="/assets/js/contact.js"/>"></script>
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
          <li class="active">${contact.firstName} ${contact.lastName} ${contact.middleName}</li>
          <jsp:include page="logo.jsp"/>
        </ol>

        <jsp:include page="contact_actions.jsp"/>
          <tags:operation
                  client="${client.id}"
                  contact="${contact.id}"
                  url="contact/action/save"
                  commentName="Результат"/>

        <div class="col-md-7" style="padding-left: 0px">
          <table class="table">
            <tbody>
            <tr>
              <td>Фамилия</td>
              <td class="active_record">${contact.firstName}</td>
            </tr>
            <tr>
              <td>Имя</td>
              <td class="active_record">${contact.lastName}</td>
            </tr>
            <tr>
              <td>Отчество</td>
              <td class="active_record">${contact.middleName}</td>
            </tr>
            <tr>
              <td>Дата рождения</td>
              <td class="active_record">${contact.birthDate}</td>
            </tr>
            <tr>
              <td>Пол</td>
              <td class="active_record">
                <c:if test="${contact.sex == 'M'}">Муж.</c:if>
                <c:if test="${contact.sex == 'F'}">Жен.</c:if>
              </td>
            </tr>
            <tr>
              <td>Номер телефона1</td>
              <td class="active_record">${contact.mobileNumber2}</td>
            </tr>
            <tr>
              <td>Номер телефона2</td>
              <td class="active_record">${contact.mobileNumber3}</td>
            </tr>
            <tr>
              <td>Домашний адрес</td>
              <td class="active_record">${contact.homeAddress}</td>
            </tr>
            <tr>
              <td>Почтовый адрес</td>
              <td class="active_record">${contact.postAddress}</td>
            </tr>
            </tbody>
          </table>
        </div>


      </div>
    </div>

  </div>

  <div class='notifications bottom-right'></div>

  <jsp:include page="footer.jsp"/>
</div>
</body>
</html>