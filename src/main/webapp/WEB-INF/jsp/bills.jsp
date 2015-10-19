<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--@elvariable id="client" type="com.crm.guard.entity.Client"--%>

<div class="fluid-container">

    <c:if test="${client.totalInvoice.invoice != null }">
        <div class="row">
            <div class="col-md-12 col-lg-12" style="margin-top: 20px">
                <div class="alert alert-info" role="alert">
                    Суммарный долг клиента: ${client.totalInvoice.invoice} р.
                </div>
            </div>
        </div>
    </c:if>

    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div id="contracts-container"></div>
        </div>
        <div class="col-md-10 col-lg-10">
            <div style="display: none;" id="default-invoices">
                <div style="margin-top: 55px" class="alert alert-success" role="alert">Выберите договор, чтобы увидеть счета</div>
            </div>
            <div id="invoices-container">

            </div>
        </div>
        <div class="col-md-2 col-lg-2">
            <div id="payments-container">

            </div>
        </div>
    </div>
</div>

