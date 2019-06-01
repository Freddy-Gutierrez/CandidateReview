<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
    <c:if test="${signUp == '1'}">
        <c:if test="${msg != null}">
            <p>${msg}</p>
        </c:if>
        <c:if test="${msg == null}">
            <p>Please enter your credentials</p>
        </c:if>      
            <form action="Login" method="post">
            Username: <input type="text" name="name"> </br>
            Password: <input type="password" name="pass"></br>
            <input type="hidden" value="create" name="hid">
            <input type="submit" value="create">              
        </form>
        <a href="Login?signUp=0">Have an account? Login</a>
    </c:if>
    <c:if test="${signUp == '0'}">
        <p>${msg}</p>
        <form action="Login" method="post">
            Username: <input type="text" name="name"> </br>
            Password: <input type="password" name="pass"></br>
            <input type="submit" value="login">
            <input type="hidden" value="check" name="hid">      
        </form>
        <a href="Login?signUp=1">Don't have an account? Create One</a>
    </c:if>
</body>
</html>