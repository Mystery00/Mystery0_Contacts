<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logout</title>
</head>
<body>
<%
    for (Cookie cookie : request.getCookies())
    {
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    session.invalidate();
    response.sendRedirect("login.jsp");
%>
</body>
</html>
