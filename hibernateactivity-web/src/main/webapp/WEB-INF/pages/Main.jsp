<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="hibernateactivity.core.service.Service" %>
<%@ page import="hibernateactivity.core.model.Person" %>
<%@ page import="hibernateactivity.core.model.Contacts" %>
<%@ page import="hibernateactivity.core.model.Name" %>
<%@ page import="hibernateactivity.core.model.Roles" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="hibernateactivity.web.Operations" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<html>
    <head>
    <spring:url value="/resources/css/servlets.css" var="ServletsCss" />
	<spring:url value="/resources/js/Servlets.js" var="ServletsJs" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <spring:url value="/resources/js/jq.js" var="JqJs" />
    <spring:url value="/resources/js/deleteperson.js" var="DeleteJs" />
    <link rel="stylesheet" type="text/css" href="${ServletsCss}"/>
    <script src="${ServletsJs}"></script>
    <script src="${JqJs}"></script>
    <script src="${DeleteJs}"></script>
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
                <a href="projects"> View Projects</a>
            </span>

            <br/><br/>
            <h1 align="center">Spring Activity</h1>
            <div id="search">

                <c:url value="/logout" var="logoutUrl" />
                <form action="${logoutUrl}" method="post" id="logoutForm">
                    <input type="hidden" name="${_csrf.parameterName}"
                value="${_csrf.token}" />
                </form>
    
                <div id="add"><button onClick="location.href='AddPerson'">
                    <spring:message code="label.addperson"/> </button></div><br/>                
                    <spring:message code="label.search"/>  
                        <select>
                            <option id="person">Person</option>
                            <option id="prole">Role</option>
                        </select>   
                    <div id="s">                      
                    <form id="displist">
                    </form>
                    </div>
                </div>
           
            <div id="usermessage"></div>

            <c:if test="${empty person}">
                <h2>No Persons Found!</h2>
            </c:if>
            <table border="1"align="center"id="persons">
                <thead><tr><th colspan="6"><spring:message code="label.tablename"/> </th></tr>
                <tr><th>ID</th>
                    <th><spring:message code="label.name"/></th>
                    <th><spring:message code="label.datehired"/></th>
                    <th><spring:message code="label.grade"/></th>
                    <th><spring:message code="label.contact"/></th>
                    <th><spring:message code="label.action"/></th></tr>
                </tr></thead>
                <c:forEach var="user" items="${person}">
                    <tr>
                        <td>
	                        ${user.id}
                        </td>
                        <td>${user.names.first_name} ${user.names.last_name}</td>
                        <td>${user.date_hired}</td>
                        <td>${user.grade}</td>
                        <td>
                            <c:forEach var="cont" items="${user.contact}" varStatus="loop">
	                        ${cont.type} : ${cont.contact}
            				    <c:if test="${not loop.last}"><br/></c:if>
                          </c:forEach>
                        </td>
                        <td>
                            <button id="${user.id}" onclick="location.href='update/${user.id}'">Update</button>
                            <button class="delete" value = "${user.id}">Delete</button>
                        </td>
                    </tr>
			    </c:forEach>     
            </table>
            <br/><br/><br/>
        </div>
    </body>
</html>
