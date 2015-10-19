<%--@elvariable id="client" type="com.crm.guard.entity.Client"--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="bootstrap-table">
  <div>
    <table id="file-table" class="table table-hover"
           data-url="files/table/data?id=${client.id}" data-side-pagination="server"
           data-search="true">
      <thead>
      <tr>
        <th data-field="master" data-width="15" data-formatter="imageExtFormatter"></th>
        <th data-align="left" data-sortable="true" data-field="name" data-formatter="downloadButtonFormatter">Наименование файла</th>
        <th data-align="left" class="col-md-1" data-sortable="true" data-field="name" data-formatter="removeFileButtonFormatter">Действия</th>
      </tr>
      </thead>
    </table>
  </div>
</div>

<script type="text/javascript" src="<c:url value="/assets/js/files.js"/>"></script>
