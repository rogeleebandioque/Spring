<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
    <head>
    <sec:csrfMetaTags/>
    <spring:url value="/resources/css/servlets.css" var="ServletsCss" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <spring:url value="/resources/js/persons/deleteperson.js" var="DeleteJs" />
    <link rel="stylesheet" type="text/css" href="${ServletsCss}"/>

    <script src="${DeleteJs}"></script>   

    <title>Spring Activity</title>

    </head>

    <body>
        <div id="container">    
            <span style="float: left; text-align: right">
                <a href="../projects"> View Projects</a>
                <a href="../users"> View Users</a>
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
                <button onClick="location.href='AddPerson'">
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
                    </tr>
                </thead>
                <c:forEach var="user" items="${person}">
                    <tr>
                        <td>${user.id}</td>
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
                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <button class="delete" value = "${user.id}">Delete</button>
                            </sec:authorize>
                        </td>
                    </tr>
			    </c:forEach>     
            </table>
            <br/><br/><br/>
        </div>
        
        <input type="hidden" value="${role}" id="r"/>
        <script> var roles = $("#r").val(); </script> 
    </body>
</html>
