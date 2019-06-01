<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Candidate</title>
</head>
<body>

    <c:if test="${permissions != null}">
        Permissions: ${permissions} </br> <a href="Login?logout=1"> Logout</a>
    </c:if>
    <c:if test="${permissions == null}">
        <a href="Login?logout=0"> Login</a>
    </c:if>            
    <form action="EditCandidate" method ="post">
        Id: <input id="num" type='text' name='id' value="<c:out value="${entry.id}"></c:out>" readonly/><br />
        Name: <input id="name" type='text' name='name' value="<c:out value="${entry.name}"></c:out>"/><br />
        Specialties: <input id="special" type='text' name='special' value="<c:out value="${entry.specialties}"></c:out>" /><br />
        Presentation: <input id="pres" type='text' name='pres' value="<c:out value="${entry.presentation}"></c:out>" /><br />        
        <input type='submit' name='save' value='Save' /> <br />
    </form>
</body>
</html>