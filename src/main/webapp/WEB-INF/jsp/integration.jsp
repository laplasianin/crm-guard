<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

                <h3>Последняя выполненная интеграция</h3>

                <div class="row">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>Создан</th>
                            <th>Тип</th>
                            <th>Результат</th>
                            <th>Лог операций</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                                <%--@elvariable id="last" type="com.crm.guard.entity.IntegrationAudit"--%>
                                ${last.created}
                            </td>
                            <td>
                                ${last.type}
                            </td>
                            <td>
                                <c:if test="${not empty last.completed}">Завершено успешно в ${last.completed}</c:if>
                                <c:if test="${not empty last.terminated}">Выполнено с ошибками в ${last.terminated}</c:if>
                            </td>
                            <td class="col-md-6" style="  height: 150px;  overflow: auto;">
                                <pre class="pre-scrollable" style="height: 150px;">${last.log}</pre>
                            </td>

                        </tr>
                        </tbody>
                    </table>

                </div>

                <%--@elvariable id="lastUncompleted" type="com.crm.guard.entity.IntegrationAudit"--%>
                <c:if test="${not empty lastUncompleted}">
                    <div class="row">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>Создан</th>
                                <th>Тип</th>
                                <th>Результат</th>
                                <th>Лог операций</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>
                                        ${lastUncompleted.created}
                                </td>
                                <td>
                                        ${lastUncompleted.type}
                                </td>
                                <td>
                                    Выполняется...
                                </td>
                                <td class="col-md-6" style="  height: 150px;  overflow: auto;">
                                    <pre class="pre-scrollable" style="height: 150px;">${lastUncompleted.log}</pre>
                                </td>

                            </tr>
                            </tbody>
                        </table>

                    </div>
                </c:if>


                <h3>Ручное управление интеграцией</h3>
                <h4>В нормальном режиме работы интеграция запускается автоматически и не требует вмешательства.
                    Используйте ручное управление только в экстренных случаях</h4>

                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-7 control-label">Запускает процесс полной интеграции данных</label>
                        <div class="col-sm-5">
                            <button id="run" type="button" class="btn btn-primary">Запустить полную интеграцию</button>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-9 control-label">Операции ниже выполняют частичную интеграцию</label>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-7 control-label">Загружает контрагентов из файла ${clientsPath}</label>
                        <div class="col-sm-5">
                            <button id="clients" type="button" class="btn btn-danger">Запустить интеграцию контрагентов</button>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-7 control-label">Загружает договора из файла ${contractsPath}</label>
                        <div class="col-sm-5">
                            <button id="contracts" type="button" class="btn btn-danger">Запустить интеграцию договоров</button>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-7 control-label">Загружает долги из файла ${totalInvoicesPath}</label>
                        <div class="col-sm-5">
                            <button id="total_invoices" type="button" class="btn btn-danger">Запустить интеграцию долгов</button>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-7 control-label">Загружает счета из файла ${invoicesPath}</label>
                        <div class="col-sm-5">
                            <button id="invoices" type="button" class="btn btn-danger">Запустить интеграцию счетов</button>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-7 control-label">Загружает оплаты из файла ${paymentsPath}</label>
                        <div class="col-sm-5">
                            <button id="payments" type="button" class="btn btn-danger">Запустить интеграцию оплат</button>
                        </div>
                    </div>
                </form>

                <form class="form-horizontal">

                </form>

                <h3>Параметры интеграции</h3>
                    <form id="change-integration-path-form" class="form-horizontal">
                        <div class="form-group">
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Введите путь 1с интеграции</label>
                            <div class="col-sm-5">
                                <input required class="form-control"
                                       name="path" placeholder="Директория 1с выгрузки" value="${integration1cPath}">
                            </div>
                            <button id="change-integration-path" type="button" class="btn btn-default">Изменить</button>
                        </div>

                    </form>

<script type="text/javascript" src="<c:url value="/assets/js/integration.js"/>"></script>