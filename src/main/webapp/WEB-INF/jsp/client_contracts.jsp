<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--@elvariable id="contracts" type="java.util.List<com.crm.guard.entity.Contract>"--%>

<h3>Договоры</h3>

<table id="contracts-table" class="table table-hover "
       data-pagination="true"
       data-page-list="[5, 10, 20, 50, 100, 200]"
       data-search="true">
    <thead>
    <tr>
        <th>Номер</th>
        <th data-sortable="true">Долг</th>
        <th data-sortable="true">Открыт</th>
        <th data-sortable="true">Закрыт</th>
        <th data-sortable="true">Филиал</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${contracts}" var="contract">
        <tr>
            <td data-checkbox="true">
                ${contract.id}
            </td>
            <td>
                ${contract.totalInvoice}
            </td>
            <td>
                <fmt:formatDate value="${contract.contractStartDate}" pattern="dd.MM.yyyy"/>
            </td>
            <td>
                <fmt:formatDate value="${contract.contractEndDate}" pattern="dd.MM.yyyy"/>
            </td>
            <td>
                ${contract.entity.shortName}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
