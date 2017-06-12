<!DOCTYPE html>
<html>
<head>
    <!--Import Google Icon Font-->
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="css/materialize.min.css"
          media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="css/reset.css">

    <!--Let browser know website is optimized for mobile-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Index</title>
</head>
<body>
<%!String username;%>
<%
    for (Cookie cookie : request.getCookies())
    {
        if (cookie.getName().equals("username"))
        {
            username = cookie.getValue();
            break;
        }
    }
%>
<header>
    <nav>
        <div id="reset-nav-wrapper" class="row nav-wrapper">
            <a href="#" data-activates="slide-out"
               class="button-collapse left"><i
                    class="material-icons">dehaze</i></a>
            <form class="col s6">
                <div class="input-field">
                    <input id="search" type="search" required>
                    <label class="label-icon" for="search"><i
                            class="material-icons">search</i></label>
                    <i class="material-icons">close</i>
                </div>
            </form>
            <ul id="reset-right" class="col s2 right">
                <li><a href="#"><i class="material-icons">account_circle</i></a></li>
                <li><a href="#"><i class="material-icons dropdown-button" data-beloworigin="true"
                                   href='#' data-activates='dropdown1'>more_vert</i></a></li>
            </ul>
        </div>
    </nav>
</header>

<ul id="slide-out" class="side-nav">
    <li>
        <div class="userView">
            <div class="background">
                <img src="img/material_background.png">
            </div>
            <a href="#"><img class="circle" src="img/material_background.png"></a>
            <a href="#"><span class="white-text name"><%=username%></span></a>
            <a href="#"><span class="white-text email"></span></a>
        </div>
    </li>
    <li><a href="#"><i class="material-icons">contacts</i>Contacts</a></li>
    <li><a href="#"><i class="material-icons">content_copy</i>Repeat Contacts</a></li>
    <li>
        <div class="divider"></div>
    </li>
    <li>
        <ul class="collapsible" data-collapsible="accordion">
            <li>
                <a href="#" id="reset-collapsible-header" class="collapsible-header"><i
                        class="material-icons">keyboard_arrow_down</i>Tags</a>
                <ul class="collapsible-body">
                    <li><a href="#"><i class="material-icons">label</i>tag1</a></li>
                </ul>
            </li>
        </ul>
    </li>
    <li>
        <div class="divider"></div>
    </li>
    <li><a class="subheader">Subheader</a></li>
    <li><a class="waves-effect" href="#">Third Link With Waves</a></li>
</ul>

<main>
    <!-- Dropdown Structure -->
    <ul id='dropdown1' class='dropdown-content'>
        <li><a href="#">Logout</a></li>
    </ul>

    <ul class="collection">
        <li class="collection-item avatar valign-wrapper">
            <img src="img/head/1.png" class="circle reset-img-head">
            <span class="title">title</span>
            <span class="reset-content">line1</span>
            <div class="reset-secondary-content valign-wrapper row">
                <a href="#" class="valign-wrapper col s6"><i class="material-icons">grade</i></a>
                <a href="#" class="valign-wrapper col s6"><i
                        class="material-icons">more_vert</i></a>
            </div>
        </li>
    </ul>
    <a id="reset-floating-button"
       class="btn-floating btn-large waves-effect waves-light red right-aligned"><i
            class="material-icons">add</i></a>
</main>

<footer class="page-footer">
    <div class="container">
        <ul class="pagination">
            <li class="disabled"><a href="#"><i class="material-icons">chevron_left</i></a></li>
            <li class="active"><a href="#">1</a></li>
            <li class="waves-effect"><a href="#">2</a></li>
            <li class="waves-effect"><a href="#">3</a></li>
            <li class="waves-effect"><a href="#">4</a></li>
            <li class="waves-effect"><a href="#">5</a></li>
            <li class="waves-effect"><a href="#"><i class="material-icons">chevron_right</i></a>
            </li>
        </ul>
    </div>
    <div class="footer-copyright">
        <div class="container">@ 2014 Copyright WeiLy Lab
            <a class="grey-text text-lighten-4 right" href="#">More Links</a>
        </div>
    </div>
</footer>

<!--Import jQuery before materialize.js-->
<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="js/materialize.min.js"></script>
<script type="text/javascript" src="js/index.js"></script>
<script>
    init();
</script>
</body>
</html>