<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--@elvariable id="bills" type="java.util.List<com.crm.guard.entity.Bill>"--%>

<table id="bills-table" class="table table-hover"
       data-pagination="true"
       data-page-list="[5, 10, 20, 50, 100, 200]"
       data-search="true">
    <thead>
    <tr>
        <th data-sortable="true">Дата</th>
        <th data-sortable="true">Счет</th>
        <th data-sortable="true">Оплачено</th>
        <th data-sortable="true">К оплате</th>
        <th data-sortable="true">Описание</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${bills}" var="bill">
        <tr>
            <td>
                <fmt:formatDate value="${bill.billDate}" pattern="dd.MM.yyyy"/>
            </td>
            <td>
                ${bill.price} р.
            </td>
            <td>
                ${bill.debt} р.
            </td>
            <td>
                ${bill.price} - ${bill.debt} р.
            </td>
            <td>
                ${bill.description} р.
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
