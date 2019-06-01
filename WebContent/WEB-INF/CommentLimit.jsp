<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Comment Limit</title>
</head>
<body>   
    <c:if test="${permissions != null}">
            Permissions: ${permissions} </br> <a href="Login?logout=1"> Logout</a>
        </c:if>
        <c:if test="${permissions == null}">
            <a href="Login?logout=0"> Login</a>
     </c:if>
    <h1>User Can Only Post One Review Per Candidate</h1>     
    <a href="Candidate">Back To Candidates</a>
</body>
</html>