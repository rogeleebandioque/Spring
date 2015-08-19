
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
    <head>
        <sec:csrfMetaTags/>
        <spring:url value="/resources/css/servlets.css" var="ServletsCss" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.8/angular.min.js"></script>

        <spring:url value="/resources/js/persons/deleteperson.js" var="DeleteJs" />
        <link rel="stylesheet" type="text/css" href="${ServletsCss}"/>
        <script src="${DeleteJs}"></script>   
        <title>Spring Activity</title>
    </head>

    <body>
        <div id="container" ng-app="myApp">    
            <span style="float: left; text-align: right">
                <a href="../projects"> View Projects</a>
                <a href="../userslist"> View Users</a>
            </span>

            <span style="float: right; text-align: right">
                <a href="?lang=en">en</a>|<a href="?lang=tlg">tlg</a>           
                <br/>   
                Welcome : <b>${pageContext.request.userPrincipal.name} </b>| 
                <button id="logout" value="logout">Logout</button>
                <br/>
            </span> 


            <br/><br/>
            <h1>Spring Activity</h1>

            <div id="search">
                <c:url value="../logout" var="logoutUrl" />
                <form action="${logoutUrl}" method="post" id="logoutForm">
                    <input type="hidden" name="${_csrf.parameterName}"value="${_csrf.token}" />
                </form>
            </div>      

            <div id="add">
                <button onClick="location.href = 'AddPerson'">
                    <spring:message code="label.addperson"/> 
                </button> <br/>                

                <spring:message code="label.search"/>:
                <select>
                    <option id="person">Person</option>
                    <option id="prole">Role</option>
                </select>   

                <div id="s">                      
                    <form id="displist">
                    </form>
                    <form id="disp">
                    </form>
                </div>
            </div>

                <div id="usermessage"><h2>${msg}</h2></div>

            <div >
                <div ng-controller="personController">
                    <table border="1"align="center"id="persons">
                        <thead><tr><th colspan="6"><spring:message code="label.tablename"/> </th></tr>
                            <tr><th>ID</th>
                                <th><spring:message code="label.name"/></th>
                                <th><spring:message code="label.datehired"/></th>
                                <th><spring:message code="label.grade"/></th>
                                <th><spring:message code="label.contact"/></th>
                                <th><spring:message code="label.action"/></th></tr>
                            </tr>
                        </thead>
                        <tr ng-repeat="persons in personsList">
                            <td>{{persons.id}}</td>
                            <td>{{persons.names.first_name}} {{persons.names.last_name}}</td>
                            <td ng-bind="persons.date_hired | date:'yyyy-MM-dd'">{{persons.date_hired}}</td>
                            <td>{{persons.grade}}</td>
                            <td>
                                <ul>
                                    <li ng-repeat="contacts in persons.contact">
                                        {{contacts.type}} : {{contacts.contact}}
                                    </li>
                                </ul
                            </td>
                            <td>
                                <button id="{{persons.id}}" onclick="location.href ='update{{persons.id}}'">Update</button>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <button class="delete" value = "{{persons.id}}">Delete</button>
                                </sec:authorize>
                            </td>

                        </tr>
                    </table>
                </div>
            </div>
        </div>

        <c:if test="${not empty pageContext.request.userPrincipal}">
            <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
                <script>var roles = "ROLE_ADMIN";</script>
            </c:if>
            <c:if test="${pageContext.request.isUserInRole('ROLE_USER')}">
                <script>var roles = "ROLE_USER";</script>
            </c:if>
        </c:if>
    </body>
</html>
