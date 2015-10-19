<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--@elvariable id="template" type="com.crm.guard.entity.Template"--%>

<html lang="ru">
<jsp:include page="../head.jsp"/>

<body>
<div class="container-fluid">

  <div id="wrapper" class="active">
    <jsp:include page="../navbar.jsp"/>
    <jsp:include page="../sidebar.jsp"/>
    <div id="page-content-wrapper">
      <div class="row">
        <ol class="breadcrumb clearfix">
          <li><a href="<c:url value="/"/>">Главная</a></li>
          <li><a href="<c:url value="/templates/"/>">Шаблоны</a></li>
          <li class="active">Шаблон</li>
            <jsp:include page="../logo.jsp"/>
        </ol>

        <h1>Шаблон отправки</h1>

        <div class="row">
          <form id="form-template" role="form" class="form-horizontal" style="margin-top: 30px">
            <input type="hidden" id="template-id" name="entity" value="${template.id}">

            <div class="form-group">
              <label for="template" class="col-md-2 control-label">Название шаблона</label>
              <div class="col-md-6">
                <input class="form-control" type="text" required name="name" id="name" value="${template.name}">
              </div>
            </div>

            <div class="form-group">
              <label for="type" class="col-md-2 control-label">Тип шаблона</label>
              <div class="col-md-4">

                <select id="type" required name="type" class="form-control">
                  <c:forEach items="${types}" var="value">
                    <option <c:if test="${template.type == value}">selected</c:if>  value="${value}">${value}</option>
                  </c:forEach>
                </select>

              </div>
            </div>

              <div class="form-group">
                  <label for="subject" class="col-md-2 control-label">Заголовок</label>
                  <div class="col-md-7">
                      <input required class="form-control" maxlength="100" id="subject" name="subject" required value="${template.subject}">
                  </div>
              </div>

            <div class="form-group">
              <label for="template" class="col-md-2 control-label">Шаблон сообщения</label>
              <div class="col-md-7">
                <textarea rows="10" required class="form-control" minlength="1" maxlength="9999" id="template" name="template" required>${template.template}</textarea>
              </div>
            </div>

          </form>
        </div>
          <div class="row">
              <button data-loading-text="Сохранение..." id="save-or-update-btn" type="button" class="col-md-offset-2 btn btn-primary btn-sm">
                  <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                  Сохранить
              </button>
          </div>
      </div>
    </div>
  </div>

    <div style="display: none">
        <li id="fieldList" class="dropdown">
            <a class="btn btn-default dropdown-toggle " data-toggle="dropdown">
                <span class="glyphicon glyphicon-list-alt"></span>
                <span class="current-font">Клиент</span>
                <b class="caret"></b>
            </a>
            <ul class="dropdown-menu">

                <c:forEach items="${patterns}" var="pattern">
                    <li><a data-wysihtml5-command="insertHTML" data-wysihtml5-command-value="%${pattern.path}%">${pattern.description}</a></li>
                </c:forEach>
            </ul>
        </li>
    </div>

    <div class='notifications bottom-right'></div>

  <jsp:include page="../footer.jsp"/>
</div>
</body>
</html>

<link type="text/css" rel="stylesheet" href="<c:url value="/assets/lib/wysihtml5/bootstrap3-wysihtml5.min.css"/>">
<script type="text/javascript" src="<c:url value="/assets/lib/wysihtml5/bootstrap3-wysihtml5.all.min.js"/>"></script>

<script type="text/javascript" src="<c:url value="/assets/js/template.js"/>"></script>