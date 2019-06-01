<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Comment</title>
</head>
<body>
    <c:if test="${permissions != null}">
       Permissions: ${permissions} </br> <a href="Login?logout=1"> Logout</a>
    </c:if>
    <c:if test="${permissions == null}">
       <a href="Login?logout=0"> Login</a>
    </c:if>
    <form action="EditComment" method="post">
        <table border = '1'cellspacing = '0'>
            <tr>
                <td id="colName">Rating</td>
                <td>1 
                    <input type="radio" name="rate" value ="1" required <c:if test="${rating == 1}">checked</c:if>>
                    <input type="radio" name="rate" value ="2" required <c:if test="${rating == 2}">checked</c:if>>
                    <input type="radio" name="rate" value ="3" required <c:if test="${rating == 3}">checked</c:if>>
                    <input type="radio" name="rate" value ="4" required <c:if test="${rating == 4}">checked</c:if>>
                    <input type="radio" name="rate" value ="5" required <c:if test="${rating == 5}">checked</c:if>>
                    5
                </td>
            </tr>
            <tr>
                <td id="colName">Name</td>
                <td><input type="text" name="name" value="${user}" readonly></td>
            </tr>
            <tr>
                <td id="colName">Comments</td>
                <td><textarea name="comments" rows="5" cols="40" required>${com}</textarea></td>
            </tr>
            <tr>                      
                <td><input type="submit" value="Submit"></td>
                <td><input type = "hidden" name="id" value="${parentId}"></td>
            </tr>
        </table>
     </form>
</body>
</html>