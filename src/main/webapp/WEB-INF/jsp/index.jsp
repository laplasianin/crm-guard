<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="ru">
<jsp:include page="head.jsp"/>
<body>
    <div class="container-fluid">
        <div id="wrapper" class="active">
            <jsp:include page="navbar.jsp"/>
            <jsp:include page="sidebar.jsp"/>

            <div id="page-content-wrapper">
                <jsp:include page="clients.jsp"/>
            </div>

        </div>
    </div>
    <jsp:include page="footer.jsp"/>
</body>
</html>