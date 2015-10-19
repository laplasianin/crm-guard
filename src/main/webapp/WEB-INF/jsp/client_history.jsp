<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--@elvariable id="history" type="java.util.List<com.crm.guard.entity.UserEvent>"--%>

<table id="history-table" class="table table-hover"
       data-pagination="true"
       data-page-list="[5, 10, 20, 50, 100, 200]"
       data-search="true"
       data-show-header="false"
       data-card-view="true">
    <thead>
    <tr>
        <th>Дата</th>
        <th>Событие</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${history}" var="event">
        <tr>
            <td>
                <fmt:formatDate value="${event.eventDate}" pattern="dd.MM.yyyy HH:mm"/>
                (${event.user.name})
            </td>
            <td>
                <strong>${event.eventType.name} ${event.contact.lastName} ${event.contact.firstName} ${event.contact.middleName}</strong><br>
                ${event.description}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
