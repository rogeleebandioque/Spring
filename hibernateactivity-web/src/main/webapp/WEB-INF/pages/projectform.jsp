<form:form id="projectForm" modelAttribute="projectForm">
    <table align="center">
        <form:hidden path="project_id" value="${projectForm.project_id}"/>
            <tr><td><spring:message code="label.prname"/></td>
                <td> <form:input path="project_name" required="true"/></td>
                <td> <form:errors path="project_name" class="errors"/> </td>    
            </tr>
            <tr><td><spring:message code="label.start"/> </td>
                <td><form:input path="start_date"required="true"placeHolder="dd/MM/yyyy"/></td>
                <td><form:errors path="start_date" class="errors"/></td>
            </tr>

        <tr><td><spring:message code="label.end"/></td>
            <td><form:input path="end_date" placeHolder="dd/MM/yyyy" required="true"/></td>
            <td><form:errors path="end_date" class="errors"/></td>        
        </tr>
        <tr><td><spring:message code="label.team"/></td>
            <td><select id="team" multiple="multiple" size="10">
            <c:forEach var="p" items="${person}">
                <option value="${p.id}" 
                    <c:forEach var="t" items="${team}" varStatus="loop">
                        <c:if test="${p.id == t.id}">
                            selected="true"
                        </c:if>
                    </c:forEach>
                >${p.names.first_name} ${p.names.last_name}</option>                
            </c:forEach>
        </select>       
        </td></tr>


        <tr><td></td><td colspan="2">
            <input type="submit" value="Submit" id="pass"/>
            <spring:url value="/projects" var="cancel" />
            <input type="button" onclick="location.href='${cancel}'" value="Cancel">
        </td></tr>

    </table>
</form:form>    
