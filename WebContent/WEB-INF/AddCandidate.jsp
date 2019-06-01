<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Candidate</title>
</head>
<body>
        <c:if test="${permissions != null}">
            Permissions: ${permissions} </br> <a href="Login?logout=1"> Logout</a>
        </c:if>
        <c:if test="${permissions == null}">
            <a href="Login?logout=0"> Login</a>
        </c:if>
    <form action="AddCandidate" method ="post">
        Name: <input type='text' name='name' /><br />
        Specialties: <input type='text' name='special' /><br />
        Presentation: <input type='text' name='pres' /><br />        
        <input type='submit' name='add' value='Add' /> <br />
    </form>
</body>
</html>