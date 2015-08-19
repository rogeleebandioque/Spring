
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
    <head>  
        <sec:csrfMetaTags/> 
        <spring:url value="/resources/css/servlets.css" var="ServletsCss" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <spring:url value="/resources/js/users/users.js" var="users" />
        <spring:url value="/resources/js/users/updateuser.js" var="update" />
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script> 
        <script src="${users}"></script>
        <script src="${update}"></script>
        <link rel="stylesheet" type="text/css" href="${ServletsCss}"/>
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
                <br/><br/>

                <form id="userform">
                    <table border ="1" class="table-condensed" align="right">
                        <th colspan="2">Add User</th>
                        <tr>
                            <td>Username:</td>
                            <td><input id="username" required="true"/></td>
                        </tr>
                        <tr>
                            <td>Password:</td>
                            <td><input type="password" id="password"required="true"/></td>
                        </tr>                    
                        <sec:authorize access="hasRole('ROLE_ADMIN')">                    
                            <tr>
                                <td>Role:</td>
                                <td>
                                    <select id="role">
                                        <option value="ROLE_ADMIN">ADMIN</option>
                                        <option value="ROLE_USER">USER</option>
                                    </select>
                                </td>
                            </tr>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_USER')">                    
                            <tr>
                                <td>Role:</td>
                                <td>
                                    <select id="role">
                                        <option value="ROLE_USER">USER</option>
                                    </select>
                                </td>
                            </tr>
                        </sec:authorize>
                        <tr>
                            <td colspan="2">
                                <input class="btn btn-success btn-block" type="submit" value="Submit"/>
                            </td>
                        </tr>
                    </table>
                </form>
                <div id="search">
                    <c:url value="../logout" var="logoutUrl" />
                    <form action="${logoutUrl}" method="post" id="logoutForm">
                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}" />
                    </form>
                </div>
            </div> 

            <div id="list" class="col-sm-9">
                <table id="userTable" class="usertable table table-condensed table-hover">
                    <thead>
                        <tr>
                            <th colspan="6"><spring:message code="label.users"/></th>
                        </tr>
                        <tr>
                            <th>ID</th>
                            <th><spring:message code="label.username"/></th>
                            <th><spring:message code="label.role"/></th>
                            <th><spring:message code="label.action"/></th>
                        </tr>
                    </thead>
                    <c:forEach var="users" items="${user}">
                        <tr>
                            <td>${users.id}</td>
                            <td>${users.username}</td>
                            <td>${users.role}</td>
                            <td>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <button class="btn btn-primary update" value= "${users.id}">Update</button>
                                    <button class="btn btn-danger deleteuser" value = "${users.id}">Delete</button>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_USER')">
                                    NONE
                                </sec:authorize>
                            </td>
                        </tr>
                    </c:forEach>     
                </table>
            </div>
        </div>

        <c:if test="${not empty pageContext.request.userPrincipal}">
            <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
                <script>var role = "ROLE_ADMIN"</script>
            </c:if>
            <c:if test="${pageContext.request.isUserInRole('ROLE_USER')}">
                <script>var role = "ROLE_USER"</script>
            </c:if>
        </c:if>
    </body>
</html>
