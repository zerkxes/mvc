<%@ include file="common/header.jspf" %>
<body>
    <%@ include file="common/navigation.jspf" %>
    Here are the list of Todos for ${name}:
    <form:form method="post" modelAttribute="todo">
        <form:hidden path="id"/>
        <fieldset class="form-group">
            <form:label path="desc">Description</form:label>
            <form:input path="desc" type="text" class="form-control" required="required"/>
            <form:errors path="desc" cssClass="text-warning"/>
        </fieldset>
        <fieldset class="form-group">
            <form:label path="targetDate">Target Date</form:label>
            <form:input path="targetDate" type="text" class="form-control" required="required"/>
            <form:errors path="targetDate" cssClass="text-warning"/>
        </fieldset>
        <input type="submit"/>
    </form:form>
</body>
    <%@ include file="common/footer.jspf" %>
