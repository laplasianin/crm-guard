<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--@elvariable id="invoices" type="java.util.List<com.crm.guard.entity.Invoice>"--%>

<h3>Счета по договору</h3>

<table id="invoices-table" class="table table-hover "
       data-pagination="true"
       data-page-list="[5, 10, 20, 50, 100, 200]"
       data-search="true">
    <thead>
    <tr>
        <th data-sortable="true" class="col-sm-1">Номер счета</th>
        <th data-sortable="true" class="col-sm-1">Дата выставления счета</th>
        <th data-sortable="true" class="col-sm-2">Сумма</th>
        <th data-sortable="true" class="col-sm-4">Описание</th>
        <th data-sortable="true" class="col-sm-4">Период</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${invoices}" var="invoice">
        <tr>
            <td>
                ${invoice.number}
            </td>
            <td>
                <fmt:formatDate value="${invoice.invoiceDate}" pattern="dd.MM.yyyy"/>
            </td>
            <td>
                ${invoice.debt} р.
            </td>
            <td>
                ${invoice.description}
            </td>
            <td>
                ${invoice.period}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
