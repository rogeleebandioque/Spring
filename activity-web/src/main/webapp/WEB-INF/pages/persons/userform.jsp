
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
    <head>
        <%@ include file="import.jsp" %>  
        <spring:url value="/resources/js/persons/addperson.js" var="AddJs" />
        <script src="${JqJs}"></script>
        <script src="${AddJs}"></script>
        <title>Spring Activity</title>
    </head>

    <body>
        <div id="container"><br><br>
            <c:choose>
                <c:when test="${personForm.id == 0}">
                    <h1>Add Person</h1>
                    <form:form method="post" action="/uploadForm?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">
                        Upload File:<center><input type="file" name="data"/></center><br/>
                        Name: <input type="text" name="name" required><br />
                        <input class="btn btn-success" type="submit" value="Add File"/>
                    </form:form>
                    <br/>
                    or
                </c:when>
                <c:otherwise>
                    <h1>Update Person</h1>
                </c:otherwise>
            </c:choose>
            <%@ include file="form.jsp" %>     
        </div>
    </body>
</html>
