
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
    <head>
        <%@ include file="import.jsp" %>  
        <spring:url value="/resources/js/persons/deleteperson.js" var="DeleteJs" />
        <script src="${DeleteJs}"></script>   
        <title>Spring Activity</title>
    </head>

    <body>
        <div class="row" id="container" ng-app="myApp">   
            <div id="local" class="col-sm-3">
                <a href="?lang=en">en</a>|<a href="?lang=tlg">tlg</a>           
                <br/>   
                Welcome : <b>${pageContext.request.userPrincipal.name} </b>| 
                <button id="logout" value="logout" class="btn btn-warning">Logout</button>
                <br/><br/>
                <a href="../Persons"> View Persons</a> |
                <a href="../projects"> View Projects</a> |
                <a href="../userslist"> View Users</a>
                <br><br>
                <button class="btn btn-success" onClick="location.href = 'AddPerson'">
                    <spring:message code="label.addperson"/> 
                </button><br/><br> 
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
                
            <div class="col-sm-9" >
                <div id="search">
                    <c:url value="../logout" var="logoutUrl" />
                    <form action="${logoutUrl}" method="post" id="logoutForm">
                        <input type="hidden" name="${_csrf.parameterName}"value="${_csrf.token}" />
                    </form>
                </div>      
                <div id="usermessage"><h2>${msg}</h2></div>
                <div ng-controller="personController">
                    <table align="center"id="persons" class="usertable table table-condensed table-hover">
                        <thead><tr>
                                <th colspan="6"><spring:message code="label.tablename"/> </tr>
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
                            <td ng-bind="persons.date_hired | date:'yyyy - MM - dd'">{{persons.date_hired}}</td>
                            <td>{{persons.grade}}</td>
                            <td>
                                <ul>
                                    <li ng-repeat="contacts in persons.contact">
                                        {{contacts.type}} : {{contacts.contact}}
                                    </li>
                                </ul
                            </td>
                            <td>
                                <button type="button" class="btn btn-primary" id="{{persons.id}}" onclick="location.href ='update{{persons.id}}'">Update</button>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <button class="btn btn-danger delete" value = "{{persons.id}}">Delete</button>
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
