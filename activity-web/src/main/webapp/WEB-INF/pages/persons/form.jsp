
<form:form id="personForm"method="POST"action="SaveUpdate"modelAttribute="personForm">
    <table align="center" class="table-condensed table-striped">
        <form:hidden path="id" value="${userForm.id}"/>
        <spring:bind path="names">
            <tr><td><spring:message code="label.firstname"/></td>
            <td> <form:input path="names.first_name" required="true"/></td>
            <td> <form:errors path="names.first_name" class="errors"/> </td>    
            </tr>
            <tr><td><spring:message code="label.lastname"/> </td>
            <td><form:input path="names.last_name"required="true"/></td>
            <td><form:errors path="names.last_name" class="errors"/></td>
            </tr>
        </spring:bind>                

        <tr><td><spring:message code="label.address"/></td>
        <td><form:input path="address" id="address" placeholder="address"required="true"/></td>
        <td><form:errors path="address" class="errors"/></td>        
        </tr>
        <tr><td><spring:message code="label.bday"/></td>
        <td><form:input type="date" path="bday" name="bday" placeHolder="dd/MM/yyyy"required="true"/></td>
        <td><form:errors path="bday" class="errors"/></td>
        </tr>
        <tr><td><spring:message code="label.age"/> </td>
        <td><form:input path="age" name="age"required="true"/></td>
        <td><form:errors path="age" class="errors"/></td>
        </tr>
        <tr><td><spring:message code="label.contact"/></td>
        <td><select id="contact">
                <option value="email" id="e-mail">E-mail</option>
                <option value="cellphone" id="cellphone">Cellphone#</option>
                <option value="telephone" id="telephone">Telephone#</option>
            </select>

            <div id="contactNumber">                
                <c:forEach var="contacts" items="${contact}">
                    <div> 
                        ${contacts.type}: <input type="text" name="contactDetail" class="contactDetail" size="10" value="${contacts.contact}"required="true"/>
                        <input type="button" value="Remove" class="btn btn-danger remove_field" />  
                        <input type="hidden" name="contactType" class="contactType" value="${contacts.type}"/> <br/>
                    </div>
                </c:forEach>
            </div>                        
        </td><td><form:errors path="contact" class="errors"/></tr></tr>

        <spring:bind path="gender">
            <tr><td><spring:message code="label.gender"/> </td>
            <td><form:radiobutton path="gender" value="male"required="true"/>Male
            <form:radiobutton path="gender" value="female"required="true"/>Female</td>
            <td><form:errors path="gender" class="errors"/></td>
            </tr>    
        </spring:bind>                

        <tr><td><spring:message code="label.grade"/> </td>
        <td><form:input path="grade" name="grade" required="true"/></td>
        <td><form:errors path="grade" class="errors"/></td>
        </tr>
        <tr><td><spring:message code="label.datehired"/> </td>
        <td><form:input type="date" path="date_hired" name="date_hired" placeHolder="dd/MM/yyyy"required="true"/></td>
        <td><form:errors path="date_hired" class="errors"/></td>
        </tr>

        <spring:bind path="currently_employed">                
            <tr><td><spring:message code="label.currentlyemployed"/> </td><td>
            <form:radiobutton path="currently_employed" value="yes"required="true"/>Yes 
            <form:radiobutton path="currently_employed" value="no"required="true"/>No
            </td>
            <td><form:errors path="currently_employed" class="errors"/></td>
            </tr>
        </spring:bind>

        <tr><td><spring:message code="label.roles"/> </td><td>
        <c:forEach var="rId" items="${roleId}">
            <input type="checkbox" name="r" value="${rId}"
                   <c:forEach var="role" items="${roles}" varStatus="loop">
                <c:if test="${role.roleName == rId}">
                    checked
                </c:if>
            </c:forEach>
            />${rId}
            <br/>
        </c:forEach>
        </td></tr>

        <tr><td></td><td colspan="2">
                <input type="submit" value="Submit" class="btn btn-success" id="pass"/>
        <spring:url value="/Persons" var="cancel" />
        <input type="button" onclick="location.href = '${cancel}'" class="btn btn-warning"
               value="Cancel">
        </td></tr>

    </table>
</form:form>    
