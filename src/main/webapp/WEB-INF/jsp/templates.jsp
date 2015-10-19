<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
            <li class="active">Шаблоны для рассылки</li>
              <jsp:include page="logo.jsp"/>
          </ol>

            <div class="row">
                <div class="col-md-7">
                    <h1>Список шаблонов для рассылки</h1>
                </div>
                <div class="col-md-5">
                <a href="<c:url value="/templates/template"/>" class="btn btn-primary btn-sm" style="margin-top: 20px;">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                    Добавить
                </a>
                </div>
            </div>


          <div class="bootstrap-table">
            <div>
              <table id="templates-table" class="table table-hover"
                     data-url="<c:url value="/templates/table/data"/>" data-side-pagination="server"
                     data-search="true">
                <thead>
                <tr>
                  <th data-field="name" data-formatter="nameFormatter" >Название</th>
                  <th data-field="type">Тип</th>
                  <th data-field="template" data-sortable="true">Шаблон</th>
                </tr>
                </thead>
              </table>
            </div>
          </div>

        </div>
      </div>
    </div>

    <jsp:include page="footer.jsp"/>
  </div>
</body>
</html>

<script type="text/javascript" src="<c:url value="/assets/js/templates.js"/>"></script>