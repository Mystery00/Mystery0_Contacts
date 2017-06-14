<%@ page import="init.Initialization" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!--Import Google Icon Font-->
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="css/materialize.min.css"
          media="screen,projection"/>
    <link rel="stylesheet" type="text/css" href="css/main.css">

    <!--Let browser know website is optimized for mobile-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Login</title>
    <script type="text/javascript">
        function checkInput(thisForm)
        {
            with (thisForm)
            {
                if (username.value === null || username.value === "")
                {
                    alert("Username cannot be null!");
                    username.focus();
                    return false;
                } else if (password.value === null || password.value === "")
                {
                    alert("Password cannot be null!");
                    password.focus();
                    return false;
                } else
                {
                    return true;
                }
            }
        }
        function doHref()
        {
            window.location.href = "register.jsp";
        }
    </script>
</head>
<body>
    <%
        Initialization.initSQL();//初始化数据库
        if (request.getParameter("incorrect") != null && !request.getParameter("incorrect").equals(""))
        {
            String incorrect = request.getParameter("incorrect");
            switch (incorrect)
            {
                case "username":
    %>
    <script>
        alert("There is no this user! ");
    </script>
    <%
            break;
        case "password":
    %>
    <script>
        alert("Password is error! ");
    </script>
    <%
                    break;
            }
        }
    %>
    <div class="svg_div" aria-hidden="true">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 810" aria-hidden="true">
            <path fill="#efefee"
                  d="M592.66 0c-15 64.092-30.7 125.285-46.598 183.777C634.056 325.56 748.348 550.932 819.642 809.5h419.672C1184.518 593.727 1083.124 290.064 902.637 0H592.66z"></path>
            <path fill="#f6f6f6"
                  d="M545.962 183.777c-53.796 196.576-111.592 361.156-163.49 490.74 11.7 44.494 22.8 89.49 33.1 134.883h404.07c-71.294-258.468-185.586-483.84-273.68-625.623z"></path>
            <path fill="#f7f7f7"
                  d="M153.89 0c74.094 180.678 161.088 417.448 228.483 674.517C449.67 506.337 527.063 279.465 592.56 0H153.89z"></path>
            <path fill="#fbfbfc"
                  d="M153.89 0H0v809.5h415.57C345.477 500.938 240.884 211.874 153.89 0z"></path>
            <path fill="#ebebec"
                  d="M1144.22 501.538c52.596-134.583 101.492-290.964 134.09-463.343 1.2-6.1 2.3-12.298 3.4-18.497 0-.2.1-.4.1-.6 1.1-6.3 2.3-12.7 3.4-19.098H902.536c105.293 169.28 183.688 343.158 241.684 501.638v-.1z"></path>
            <path fill="#e1e1e1"
                  d="M1285.31 0c-2.2 12.798-4.5 25.597-6.9 38.195C1321.507 86.39 1379.603 158.98 1440 257.168V0h-154.69z"></path>
            <path fill="#e7e7e7"
                  d="M1278.31,38.196C1245.81,209.874 1197.22,365.556 1144.82,499.838L1144.82,503.638C1185.82,615.924 1216.41,720.211 1239.11,809.6L1439.7,810L1439.7,256.768C1379.4,158.78 1321.41,86.288 1278.31,38.195L1278.31,38.196z"></path>
        </svg>
    </div>
    <div class="loginFieldBackground" role="presentation">
        <form action="LoginServlet" method="post"
              onsubmit="return checkInput(this)"
              style=" margin-top: 10%;">
            <div class="loginField row">
                <div>
                    <img src="img/google_logo.png" alt="Google">
                </div>
                <br>
                <div class="input-field col s12">
                    <input id="username" name="username" type="text" class="validate">
                    <label for="username">Username</label>
                </div>
                <br>
                <div class="input-field col s12">
                    <input id="password" name="password" type="password" class="validate">
                    <label for="password">Password</label>
                </div>
                <br>
                <div class="col s12">
                    <a class="left valign-wrapper" href="forget.jsp">Forget?</a>
                    <button class="btn waves-effect waves-light center" type="button" name="action"
                            onclick="doHref()">
                        Register
                    </button>
                    <button class="btn waves-effect waves-light right" type="submit" name="action">
                        Login
                    </button>
                </div>
            </div>
        </form>
    </div>
    <!--Import jQuery before materialize.js-->
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>
</body>
</html>
