
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
    <head>
        <sec:csrfMetaTags/> 
        <spring:url value="/resources/css/servlets.css" var="ServletsCss" />
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.8/angular.min.js"></script>
        <spring:url value="/resources/js/projects/deleteproject.js" var="deleteproject" />
        <script src="${deleteproject}"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="${ServletsCss}"/>
        <title>Spring Activity</title>
    </head>

    <body>
        <div id="container" ng-app="myApp">
            <span style="float: right; text-align: right">
                <a href="?lang=en">en</a>|<a href="?lang=tlg">tlg</a>           
                <br/>   
                Welcome : <b>${pageContext.request.userPrincipal.name} </b>| 
                <button id="logout" value="logout">Logout</button>
                <br/>
            </span> 

            <span style="float: left; text-align: right">
                <a href="../Persons"> View Persons</a>
                <a href="../userslist"> View Users</a>
            </span>

            <br/><br/>
            <h1>Spring Activity</h1>
            <div id="search">
                <c:url value="../logout" var="logoutUrl" />
                <form action="${logoutUrl}" method="post" id="logoutForm">
                    <input type="hidden" name="${_csrf.parameterName}"
                           value="${_csrf.token}" />
                </form>
            </div>
            <div id="add">
                <button onClick="location.href = 'AddProject'">
                    <spring:message code="label.addproject"/> 
                </button>
            </div><br/> 

            <div id="usermessage">
                <c:if test="${empty projects}">
                    <h2>No Projects Found!</h2>
                </c:if>
            </div>
            <div ng-controller="projController">
                <table border="1"align="center">
                    <thead>
                        <tr>
                            <th colspan="6"><spring:message code="label.projectname"/> 
                            </th>
                        </tr>
                        <tr>
                            <th>ID</th>
                            <th><spring:message code="label.prname"/></th>
                            <th><spring:message code="label.start"/></th>
                            <th><spring:message code="label.end"/></th>
                            <th><spring:message code="label.team"/></th>
                            <th><spring:message code="label.action"/></th>
                        </tr>
                    </thead>
                    <c:forEach var="project" items="${projects}">
                        <tr>
                            <td>
                                ${project.project_id}
                            </td>
                            <td>${project.project_name} ${user.names.last_name}</td>
                            <td>${project.start_date}</td>
                            <td>${project.end_date}</td>
                            <td>
                                <c:forEach var="team" items="${project.per_proj}" varStatus="loop">
                                    ${team.names.first_name} ${team.names.last_name} <br/>
                                    <c:if test="${not loop.last}"><br/></c:if>
                                </c:forEach>
                            </td>
                            <td>
                                <button id="${project.project_id}"
                                        onclick="location.href = '/upproject${project.project_id}'">
                                    Update
                                </button>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <button class="deleteproj" value = "${project.project_id}">
                                        Delete
                                    </button>
                                </sec:authorize>
                            </td>
                        </tr>
                    </c:forEach>     

                </table>
            </div>
        </div>
    </body>
</html>
