<%@ page import="init.Initialization" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <title>Login</title>
</head>
<body>
<%
    Initialization.initSQL();//初始化数据库
    if (request.getParameter("incorrect") != null && !request.getParameter("incorrect").equals(""))
    {
%>
<script>
    alert("Username or Password is error! ");
</script>
<%
    }
%>
<div class="loginFieldBackground" role="presentation">
    <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
        <div class="loginField">
            <div><img src="img/google_logo.png" alt="Google"></div>
            <br>
            <div class="login_username_div">
                <input class="login_input" type="text" name="username" title="username"
                       placeholder="Please Enter Username">
            </div>
            <br>
            <div class="login_password_div">
                <input class="login_input" type="text" name="password" title="password"
                       placeholder="Please Enter Password">
            </div>
            <br>
            <div class="login_button_div">
                <input class="login_input" type="submit" value="Login">
            </div>
        </div>
    </form>
</div>
<footer>
    footer
</footer>
</body>

</html>
