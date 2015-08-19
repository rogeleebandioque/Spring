<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<html>
    <head>
        <spring:url value="/resources/css/servlets.css" var="ServletsCss" />
        <spring:url value="/resources/js/Servlets.js" var="ServletsJs" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script> 
        <spring:url value="/resources/js/jq.js" var="JqJs" />
        <link rel="stylesheet" type="text/css" href="${ServletsCss}"/>
        <script src="${ServletsJs}"></script>
        <script src="${JqJs}"></script>

        <title>Spring Activity</title>
    </head>

    <body>
        <div id="container">

            <div id="login-box">
                <br><br><br>
                <h3>Login</h3>

                <c:if test="${not empty error}">
                    <div class="error">${error}</div>
                </c:if>
                <c:if test="${not empty msg}">
                    <div class="msg">${msg}</div>
                </c:if>

                <form name='loginForm'
                      action="<c:url value='/login' />" method='POST'>

                    <table id="tablelogin" align="center">
                        <tr>
                            <td>User:</td>
                            <td><input type='text' name='username'></td>
                        </tr>
                        <tr>
                            <td>Password:</td>
                            <td><input type='password' name='password' /></td>
                        </tr>
                        <tr>
                            <td colspan='2'><br>
                                <input class="btn btn-primary btn-block" name="submit" type="submit" value="submit" />
                            </td>
                        </tr>
                    </table>
                    <input type="hidden" name="${_csrf.parameterName}"
                           value="${_csrf.token}" />
                </form>
            </div>
        </div>
    </body>
</html>
