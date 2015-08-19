
<form:form method="POST" action="SaveUpdateProject" id="projectForm" modelAttribute="projectForm">
    <table align="center" class="table-condensed table-striped">
        <form:hidden path="project_id" value="${projectForm.project_id}"/>
        <tr>
            <td><spring:message code="label.prname"/></td>
            <td><form:input path="project_name" required="true"/></td>
            <td><form:errors path="project_name" class="errors"/> </td>    
        </tr>
        <tr>
            <td><spring:message code="label.start"/> </td>
            <td><form:input path="start_date"required="true"placeHolder="yyyy-MM-dd"/></td>
            <td><form:errors path="start_date" class="errors"/></td>
        </tr>
        <tr>
            <td><spring:message code="label.end"/></td>
            <td><form:input path="end_date" placeHolder="yyyy-MM-dd" required="true"/></td>
            <td><form:errors path="end_date" class="errors"/></td>        
        </tr>
        <tr>
            <td><spring:message code="label.team"/></td>
            <td>
                <select id="team" multiple="multiple" size="10">
                    <c:forEach var="p" items="${person}">
                        <c:set var="join" value="true"/>
                        <c:forEach var="t" items="${team}" varStatus="loop">
                            <c:if test="${(p.id == t.id)}">
                                <c:set var="join" value="false"/>     
                            </c:if>
                        </c:forEach>
                        <c:if test="${(join == 'true')}">
                            <option class="choice" value="${p.id}">
                                ${p.names.first_name} ${p.names.last_name}
                            </option>
                        </c:if>
                    </c:forEach>
                </select>  
                <select id="chosen" multiple="multiple" size="10" name="members">
                    <c:forEach var="t" items="${team}" varStatus="loop">
                        <option class="remove" value="${t.id}">
                            ${t.names.first_name} ${t.names.last_name}
                        </option>
                    </c:forEach>
                </select>      
            </td>
        </tr>


        <tr><td></td><td colspan="2">
                <input type="submit" value="Submit" id="pass" class="btn btn-success"/>
        <spring:url value="/projects" var="cancel" />
        <input type="button" onclick="location.href = '${cancel}'" class="btn btn-warning"value="Cancel">
        </td></tr>

    </table>
</form:form>    
