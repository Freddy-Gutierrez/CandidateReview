<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Candidate Book</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
</head>
<body>
          
        <c:if test="${permissions != null}">
            Permissions: ${permissions} </br> <a href="Login?logout=1"> Logout</a>
        </c:if>
        <c:if test="${permissions == null}">
            <a href="Login?logout=0"> Login</a>
        </c:if>
        <table border = '1'cellspacing = '0'>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Specialties</th>
                <th>Presentation</th>
                <th>Rating</th>
                <c:if test="${permissions == 'admin'}">
                    <th>Operation</th>
                </c:if>                
            </tr>
            <c:forEach items = "${entries}" var="candidate" >
                <tr>
                    <td id="id">
                        <c:out value = "${candidate.id}"></c:out>
                    </td>
                    <td>
                    <a href = "<c:url value = "/Feedback?id=${candidate.id}"/>">${candidate.name}</a>
                        <c:if test="${permissions != null}">                            
                        </c:if>                                
                    </td>
                    <td id="special">
                        <c:out value = "${candidate.specialties}"></c:out>
                    </td>
                    <td id="pres">
                        <c:out value = "${candidate.presentation}"></c:out>
                    </td>
                    <td id="rating">
                        <c:out value = "${candidate.rating}"></c:out>
                    </td>
                    <c:if test="${permissions == 'admin'}">
                        <td>       
                            <a href = "<c:url value = "/EditCandidate?id=${candidate.id}&name=${candidate.name}"/>">Edit</a>                                                                 
                        </td>
                    </c:if>  
                </tr>
            </c:forEach>
        </table>
        <c:if test="${permissions == 'admin'}">
            <a href = "<c:url value = "AddCandidate"/>">Add Candidate</a>
        </c:if>         
</body>
</html>
