<%@ page import="java.util.ArrayList" %>
<%@ page import="classes.Tag" %>
<%@ page import="classes.Contact" %>
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
<%!
    String username;
    ArrayList<Tag> tagArrayList;
    ArrayList<Contact> contactArrayList;
%>
<%
    if (request.getAttribute("tagList") == null || request.getAttribute("contactList") == null)
    {
        tagArrayList = new ArrayList<>();
        contactArrayList = new ArrayList<>();
    } else
    {
        tagArrayList = (ArrayList<Tag>) request.getAttribute("tagList");
        contactArrayList = (ArrayList<Contact>) request.getAttribute("contactList");
    }
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
        <div id="reset-nav-wrapper" class="row nav-wrapper blue">
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
            <a href="#"><img class="circle" src="img/account.png"></a>
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
                <%
                    for (Tag tag : tagArrayList)
                    {
                %>
                <ul class="collapsible-body">
                    <li><a href="#"><i class="material-icons">label</i><%=tag.getTagName()%>
                    </a></li>
                </ul>
                <%
                    }
                %>
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
        <%
            for (Contact contact : contactArrayList)
            {
        %>
        <li class="collection-item avatar valign-wrapper">
            <img src="img/head/<%=(int) (Math.random() * 19 + 1)%>.png" class="circle
            reset-img-head">
            <span class="title"><%=contact.getContactName()%></span>
            <span class="reset-content"><%=contact.getPhoneNumberList()%></span>
            <div class="reset-secondary-content valign-wrapper row">
                <a href="#" class="valign-wrapper col s6"><i class="material-icons">grade</i></a>
                <a href="#" class="valign-wrapper col s6"><i
                        class="material-icons">more_vert</i></a>
            </div>
        </li>
        <%
            }
        %>
    </ul>

    <!-- Modal Structure -->
    <div id="modal1" class="modal modal-fixed-footer">
        <div class="modal-content row">
            <h5>New Contact</h5>
            <div class="input-field col s12">
                <i class="material-icons prefix reset-prefix valign-wrapper reset-color">account_circle</i>
                <input id="contactName" name="contactName" type="text" class="validate">
                <label for="contactName">Name</label>
            </div>
            <div class="input-field col s12">
                <i class="material-icons prefix reset-prefix valign-wrapper reset-color">smartphone</i>
                <input id="phoneNumber" name="phoneNumber" type="text" class="validate">
                <label for="phoneNumber">Phone</label>
            </div>
            <%--<div class="input-field col s6">--%>
            <%--<i class="material-icons prefix reset-prefix valign-wrapper reset-color">language</i>--%>
            <%--<input id="countryCode" name="countryCode" type="text" class="validate">--%>
            <%--<label for="countryCode">Country</label>--%>
            <%--</div>--%>
            <div class="input-field col s6">
                <i class="material-icons prefix reset-prefix valign-wrapper reset-color">language</i>
                <select id="countryCode" class="icons" name="countryCode">
                    <option value="null" disabled selected>Choose your Country</option>
                    <option value="+86">China (+86)</option>
                    <option value="+1">US (+1)</option>
                </select>
                <label for="countryCode">Country</label>
            </div>
            <div class="input-field col s6">
                <i class="material-icons prefix reset-prefix valign-wrapper reset-color">label</i>
                <input id="tag" name="tag" type="text" class="validate">
                <label for="tag">Tag</label>
            </div>
            <div class="input-field col s12">
                <i class="material-icons prefix reset-prefix valign-wrapper reset-color">email</i>
                <input id="emailList" name="emailList" type="text" class="validate">
                <label for="emailList">E-mail</label>
            </div>
        </div>
        <div class="modal-footer">
            <a href="#" class="modal-action modal-close waves-effect waves-green btn-flat">Done</a>
        </div>
    </div>

    <a id="reset-floating-button" href="#modal1"
       class="btn-floating btn-large waves-effect waves-light red right"><i
            class="material-icons">add</i></a>
</main>

<footer class="page-footer blue">
    <div class="container">
        <ul class="pagination">
            <li class="disabled blue"><a href="#"><i class="material-icons">chevron_left</i></a>
            </li>
            <li class="active blue darken-1"><a href="#">1</a></li>
            <li class="waves-effect blue"><a href="#">2</a></li>
            <li class="waves-effect blue"><a href="#">3</a></li>
            <li class="waves-effect blue"><a href="#">4</a></li>
            <li class="waves-effect blue"><a href="#">5</a></li>
            <li class="waves-effect blue"><a href="#"><i
                    class="material-icons">chevron_right</i></a>
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
<script>
    // Initialize collapse button
    $(".button-collapse").sideNav();
    // Initialize collapsible (uncomment the line below if you use the dropdown variation)
    $('.collapsible').collapsible();

    $(document).ready(function () {
        // the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
        $('.modal').modal();
    });

    $(document).ready(function () {
        $('select').material_select();
    });
</script>
</body>
</html>