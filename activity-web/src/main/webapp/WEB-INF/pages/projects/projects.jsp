
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html>
    <head>
        <%@ include file="import.jsp" %>  
        <spring:url value="/resources/js/projects/deleteproject.js" var="deleteproject" />
        <script src="${deleteproject}"></script>
        <title>Spring Activity</title>
    </head>

    <body>
        <div id="container" class="row">
            <div id="local" class="col-sm-3">
                <a href="?lang=en">en</a>|<a href="?lang=tlg">tlg</a><br/>   
                Welcome : <b>${pageContext.request.userPrincipal.name} </b>| 
                <button id="logout" value="logout" class="btn btn-warning">Logout</button>
                <br/><br/>
                <a href="../Persons"> View Persons</a> |
                <a href="../projects"> View Projects</a> |
                <a href="../userslist"> View Users</a>
                <br><br>
                <div id="add">
                    <button onClick="location.href = 'AddProject'" class="btn btn-success">
                        <spring:message code="label.addproject"/> 
                    </button>
                </div>
                <div id="search">
                    <c:url value="../logout" var="logoutUrl" />
                    <form action="${logoutUrl}" method="post" id="logoutForm">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    </form>
                </div>
            </div> 

            <div class="col-sm-9">
                <div id="usermessage">
                    <h2>${msg}</h2>
                </div>
                <table class="usertable table table-condensed table-hover">
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
                            <td><fmt:formatDate pattern="yyyy-MM-dd" value="${project.start_date}"/></td>
                            <td><fmt:formatDate pattern="yyyy-MM-dd" value="${project.end_date}"/></td>
                            <td>
                                <c:forEach var="team" items="${project.per_proj}" varStatus="loop">
                                    ${team.names.first_name} ${team.names.last_name} <br/>
                                    <c:if test="${not loop.last}"></c:if>
                                </c:forEach>
                            </td>
                            <td>
                                <button  class="btn btn-primary" id="${project.project_id}" onclick="location.href = '/upproject${project.project_id}'">
                                    Update
                                </button>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <button class="btn btn-danger deleteproj" value = "${project.project_id}">
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
