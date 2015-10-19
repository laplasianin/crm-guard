<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--@elvariable id="todos" type="java.util.List<com.crm.guard.entity.ClientToDo>"--%>

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
                    <li class="active">Задачи</li>
                    <jsp:include page="../logo.jsp"/>
                </ol>

                <h2>
                    Незавершенные задачи
                </h2>
                <div>

                    <table id="todos-table" class="table table-hover"
                           data-pagination="true"
                           data-page-list="[5, 10, 20, 50, 100, 200]"
                           data-search="true"
                           data-show-header="false"
                           data-card-view="true">
                        <tbody>
                        <c:forEach items="${todos}" var="todo">
                            <tr>
                                <td>
                                    <h3>${todo.title}</h3>
                                    <h5>${todo.description}</h5>
                                    <fmt:formatDate value="${todo.start}" pattern="dd.MM.yyyy HH:mm"/>
                                    <c:if test="${todo.end != null}">
                                        - <fmt:formatDate value="${todo.end}" pattern="dd.MM.yyyy HH:mm"/>
                                    </c:if>
                                    <br><a href="<c:url value="/client"/>?id=${todo.client.id}">Перейти к клиенту</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </div>

            </div>
        </div>

    </div>

    <div class='notifications bottom-right'></div>

    <jsp:include page="../footer.jsp"/>
</div>
</body>

</html>