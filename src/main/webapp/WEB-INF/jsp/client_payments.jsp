<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--@elvariable id="payments" type="com.crm.guard.entity.Payment"--%>

<h3>Оплата</h3>

<table id="payments-table" class="table table-hover "
       data-pagination="true"
       data-page-list="[5, 10, 20, 50, 100, 200]"
       data-search="true">
    <thead>
    <tr>
        <th data-sortable="true" class="col-sm-1">Сумма оплаты</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${payments}" var="payment">
        <tr>
            <td>
                ${payment.sum} р.
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
