<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<div class="row">

    <div class="col-md-6 col-lg-6">
        <div class="js-fileapi-wrapper upload-btn" style="margin-top: 10px; margin-bottom: 20px">
            <input id="choose" name="files" type="file" multiple class="filestyle" data-size="sm" data-buttonText=" Загрузить файл" data-buttonBefore="true"/>
        </div>
    </div>

  <jsp:include page="files.jsp"/>


</div>

<script type="text/javascript" src="<c:url value="/assets/js/fileapi.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/assets/js/file.js"/>"></script>
<script type="text/javascript" src="<c:url value="/assets/lib/bootstrap/js/bootstrap-filestyle.min.js"/>"></script>