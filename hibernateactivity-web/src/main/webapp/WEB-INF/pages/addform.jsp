<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
    <head>
        <sec:csrfMetaTags/> 
        <spring:url value="/resources/css/servlets.css" var="ServletsCss" />
        <spring:url value="/resources/js/Servlets.js" var="ServletsJs" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <spring:url value="/resources/js/jq.js" var="JqJs" />
        <spring:url value="/resources/js/addperson.js" var="AddJs" />

        <link rel="stylesheet" type="text/css" href="${ServletsCss}"/>
        <script src="${ServletsJs}"></script>
        <script src="${JqJs}"></script>
        <script src="${AddJs}"></script>

        <title>Spring Activity</title>
    </head>

    <body>

        <div id="container">
            <h1 align="center">Spring Activity</h1>
            <h1>Add Person</h1>
            <div id="fileUpload">
            ${message}
                <form id="uploadForm" action="/uploadForm?${_csrf.parameterName}=${_csrf.token}" method="POST"enctype="multipart/form-data" >
                    Upload File: <input type="file" name="file"/><br/>
                    Name: <input type="text" name="name" required><br />
                    <input type="submit" value="Add File"/>
                    <input type="hidden" name="${_csrf.parameterName}"value="${_csrf.token}"/>
                </form>
            </div>
            <br/>
            or
            <br/>
            <div id="message"></div>
            <%@ include file="form.jsp" %>     
                        
        </div>
    </body>
</html>
