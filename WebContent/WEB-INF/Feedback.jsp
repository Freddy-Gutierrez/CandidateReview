<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Feedback</title>
</head>
<body>
    <p><a href = "Candidate">Back to Candidates</a></p>
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
            </tr>
            <tr>
                <td>${cand.id}
                <td>${cand.name}
                <td>${cand.specialties}
                <td>${cand.presentation}
                <td>${cand.rating}
            </tr>
        </table>
        
    <c:if test="${numOfComments > 0}">
        
    <p>Comments: </p>
    
    <table border = '1'cellspacing = '0'>
        <c:forEach items = "${comments}" var="com">
           <tr>
               <td>Rating: <c:out value = "${com.rating}" ></c:out></td>
               <td>Posted by <c:out value = "${com.name}" ></c:out> on <c:out value = "${com.date}" ></c:out></td>
               <c:if test="${permissions != null }">
                    <c:if test="${com.name == user}">
                        <td rowspan=2> <a href="EditComment?parentId=${parentId}&rating=${com.rating}&com=${com.comment}">Edit Comment</a></td>
                    </c:if>
                </c:if>            
           </tr>
           <tr>
               <td colspan = "2"><c:out value = "${com.comment}" ></c:out></td>
           </tr>           
        </c:forEach>
    </table>
     </c:if>   
    
    <br><c:out value="Please give your feedback:"></c:out><br/>
        <br/><form action="Feedback" method="post">
        <table border = '1'cellspacing = '0'>
            <tr>
                <td id="colName">Rating</td>
                <td>1 
                    <input type="radio" name="rate" value ="1" required>
                    <input type="radio" name="rate" value ="2" required>
                    <input type="radio" name="rate" value ="3" required>
                    <input type="radio" name="rate" value ="4" required>
                    <input type="radio" name="rate" value ="5" required>
                    5
                </td>
            </tr>
            <tr>
                <td id="colName">Name</td>
                <td><input type="text" name="name" value="${user}" readonly></td>
            </tr>
            <tr>
                <td id="colName">Comments</td>
                <td><textarea name="comments" rows="5" cols="40" required></textarea></td>
            </tr>
            <tr>
                <td><input type="submit" value="Submit"></td>
                <td><input type = "hidden" name="id" value="${cand.id}"></td>
            </tr>
        </table>        
    </form>
</body>
</html>