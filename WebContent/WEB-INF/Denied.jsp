<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Access Denied</title>
</head>
<body>
    <c:if test="${permissions != null}">
            Permissions: ${permissions} </br> <a href="Login?logout=1"> Logout</a>
            <h1>Access Denied!</h1>
        </c:if>
     <c:if test="${permissions == null}">
            <a href="Login?logout=0"> Login</a>
            <h1>Access Denied!</h1>    
     </c:if>
    <a href="Candidate">Back To Candidates</a>
</body>
</html>