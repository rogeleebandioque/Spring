
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
        <spring:url value="/resources/js/projects/project.js" var="project" />
        <script src="${project}"></script>
        <title>Spring Activity</title>
    </head>
    <body><br><br>
        <div id="container">
            <c:choose>
                <c:when test="${projectForm.project_id == 0}">
                    <h1>Add Project</h1>
                </c:when>
                <c:otherwise>
                    <h1>Update Project</h1>
                </c:otherwise>
            </c:choose>
            <%@ include file="form.jsp" %>     
        </div>
    </body>
</html>
