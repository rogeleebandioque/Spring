<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="hibernateactivity.web.Operations" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<html>
    <head>
    <spring:url value="/resources/css/servlets.css" var="ServletsCss" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <spring:url value="/resources/js/jq.js" var="JqJs" />
    <link rel="stylesheet" type="text/css" href="${ServletsCss}"/>
    <title>Spring Activity</title>
    </head>

    <body>
        <div id="container">
            <c:if test="${not empty msg}">     
                <div id="alertUser">
                <button type="button" onClick="removeAlert()">&times;</button>
                <strong>${msg}</strong>
                </div>
            </c:if>
            <span style="float: right; text-align: right">
                Welcome : <b>${pageContext.request.userPrincipal.name} </b>| <a
                href="javascript:formSubmit()"> Logout</a>
                <br/>
                <a href="?lang=en">en</a>|
                <a href="?lang=tlg">tlg</a>
            </span>

            <span style="float: left; text-align: right">
                <a href="Persons"> View Persons</a>
            </span>

            <br/><br/>
            <h1 align="center">Spring Activity</h1>
            <div id="search">

                <c:url value="/logout" var="logoutUrl" />
                <form action="${logoutUrl}" method="post" id="logoutForm">
                    <input type="hidden" name="${_csrf.parameterName}"
                value="${_csrf.token}" />
                </form>
    
                <div id="add"><button onClick="location.href='AddProject'">
                    <spring:message code="label.addproject"/> </button></div><br/>   
                    <form>  
                    <spring:message code="label.search"/>:
                    <input type="text" placeHolder="Project Name.." size="10"/>
                    <input type="submit" value="Submit"/>
                </div>
            <div id="usermessage"></div>

            <c:if test="${empty project}">
                <h2>No Projects Found!</h2>
            </c:if>

            <table border="1"align="center">
                 <thead><tr><th colspan="6"><spring:message code="label.projectname"/> </th></tr>
               <tr><th>ID</th>
                    <th><spring:message code="label.prname"/></th>
                    <th><spring:message code="label.start"/></th>
                    <th><spring:message code="label.end"/></th>
                    <th><spring:message code="label.team"/></th>
                </tr></thead>
                
            </table>
            <br/><br/><br/>
        </div>
    </body>
</html>
