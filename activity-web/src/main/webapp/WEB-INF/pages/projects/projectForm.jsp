
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
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <spring:url value="/resources/js/projects/addproject.js" var="addproject" />

        <link rel="stylesheet" type="text/css" href="${ServletsCss}"/>
        <script src="${addproject}"></script>

        <title>Spring Activity</title>
    </head>

    <body>

        <div id="container">
            <h1 align="center">Spring Activity</h1>
            <h1>Add Project</h1>
            <br/>
            <div id="message"></div>
            <br/>
            <%@ include file="form.jsp" %>     
                        
        </div>
    </body>
</html>
