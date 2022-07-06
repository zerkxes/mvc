<%@ include file="common/header.jspf" %>
            <body>
                <%@ include file="common/navigation.jspf" %>
                <div class="container-fluid">
                    <H1>Here are the list of Todos:</H1>
                    <table class="table">
                        <tr>
                            <th>Description</th>
                            <th>Target Date</th>
                            <th>Is it Done?</th>
                            <th>Update</th>
                            <th>Delete</th>
                        </tr>
                        <c:forEach items="${todos}" var="todo">
                            <tr>
                                <td>${todo.desc}</td>
                                <td><fmt:formatDate value="${todo.targetDate}" pattern="dd/MM/yyyy"/></td>
                                <td>${todo.done}</td>
                                <td><a type="button" class="btn btn-success" href="update-todo?id=${todo.id}">Update</a></td>
                                <td><a type="button" class="btn btn-warning" href="delete-todo?id=${todo.id}">Delete</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                    <br>Your name is: ${name}<br>
                    <a href="add-todo">Click here to add a Todo.</a>
                </div>
            </body>
            <%@ include file="common/footer.jspf" %>