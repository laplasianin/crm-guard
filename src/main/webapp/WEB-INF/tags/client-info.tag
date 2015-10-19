<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ tag pageEncoding="UTF-8" %>

<%@ attribute name="fullname" required="true" type="java.lang.String" description="Text to use in the first cell." %>
<%@ attribute name="inn" required="true" type="java.lang.String" description="Text to use in the first cell." %>
<%@ attribute name="okpo" required="true" type="java.lang.String" description="Text to use in the first cell." %>
<%@ attribute name="code" required="true" type="java.lang.String" description="Text to use in the first cell." %>
<%@ attribute name="personalAccount" required="true" type="java.lang.String" description="Text to use in the first cell." %>

<div class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title"><strong>${fullname}</strong></h3>
    </div>
    <div class="panel-body">
        <div class="row">
            <div class=" col-md-11 col-lg-11 hidden-xs hidden-sm">
                <table class="table table-user-information">
                    <tbody>
                    <tr>
                        <td>ИНН</td>
                        <td>${inn}</td>
                    </tr>
                    <tr>
                        <td>ОКПО</td>
                        <td>${okpo}</td>
                    </tr>
                    <tr>
                        <td>Код</td>
                        <td>${code}</td>
                    </tr>
                    <tr>
                        <td>Лицевой счет</td>
                        <td>${personalAccount}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</div>
