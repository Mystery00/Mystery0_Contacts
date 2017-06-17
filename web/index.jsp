<%@ page import="classes.Tag" %>
<%@ page import="classes.Contact" %>
<%@ page import="util.UserUtil" %>
<%@ page import="util.DBUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="init.Initialization" %>
<%@ page import="util.PageBean" %>
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
<%
    String username = null;
    String message = null;
    int pageIndex = 1;
    int totalPages = 1;
    int totalRows = 0;
    if (request.getSession().getAttribute("message") != null && !request.getSession().getAttribute("message").equals(""))
    {
        message = String.valueOf(request.getSession().getAttribute("message"));
        request.getSession().removeAttribute("message");
    }
    if (request.getParameter("index") != null && !request.getParameter("index").equals(""))
    {
        pageIndex = Integer.parseInt(request.getParameter("index"));
    }
    Cookie[] cookies = request.getCookies();
    for (Cookie cookie : cookies)
    {
        if (cookie.getName().equals("username"))
        {
            username = cookie.getValue();
            break;
        }
    }
    if (username == null)
    {
        response.sendRedirect("login.jsp");
        return;
    }
    String tagSql = "SELECT tagID,tagName FROM tag,user WHERE username=? AND tag.userID=user.userID";
    List<Object> tagList = Initialization.getJDBCUtil().getObject(tagSql, new String[]{username}, Tag.class);
    List<Object> contactList;
    if (request.getSession().getAttribute("contactList") == null)
    {
        String contactSql = "SELECT contactID,contactName,phoneNumberList,countryCode,tag,emailList FROM contact,user WHERE username=? AND contact.userID=user.userID";
        PageBean pageBean = Initialization.getJDBCUtil().getPageBean(contactSql, new String[]{username}, pageIndex);
        totalPages = pageBean.getTotalPages();
        totalRows = pageBean.getTotalRows();
        contactList = Initialization.getJDBCUtil().getObjectFromList(Contact.class, pageBean.getData());
    } else
    {
        contactList = (List<Object>) request.getSession().getAttribute("contactList");
        request.getSession().removeAttribute("contactList");
    }
%>
<header>
    <nav>
        <div id="reset-nav-wrapper" class="row nav-wrapper blue">
            <a href="#" data-activates="slide-out" class="button-collapse left">
                <i class="material-icons">dehaze</i>
            </a>
            <form class="col s6" action="SearchServlet" method="post">
                <div class="input-field">
                    <input name="searchString" id="search" type="search">
                    <label class="label-icon" for="search">
                        <i class="material-icons">search</i>
                    </label>
                    <i class="material-icons">close</i>
                </div>
            </form>
            <ul id="reset-right" class="col s2 right">
                <li class="hide" id="delete-nav-btn">
                    <a href="#" onclick="delete_data(arr,'contact',0)">
                        <i class="material-icons">delete</i>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <i class="material-icons">account_circle</i>
                    </a>
                </li>
                <li>
                    <a href="#" class="dropdown-button" data-beloworigin="true"
                       data-activates='dropdown'>
                        <i class="material-icons">more_vert</i>
                    </a>
                </li>
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
            <a href="#">
                <img class="circle" src="img/account.png">
            </a>
            <a href="#">
                <span class="white-text name"><%=username%></span>
            </a>
            <a href="#">
                <span class="white-text email"></span>
            </a>
        </div>
    </li>
    <li>
        <a href="#">
            <i class="material-icons">contacts</i>
            Contacts(<%=totalRows%>)
        </a>
    </li>
    <li>
        <a href="#">
            <i class="material-icons">content_copy</i>
            Repeat Contacts
        </a>
    </li>
    <li>
        <div class="divider"></div>
    </li>
    <li>
        <ul class="collapsible" data-collapsible="accordion">
            <li>
                <a href="#" id="reset-collapsible-header" class="collapsible-header tag-list">
                    <i class="material-icons down-pointer">keyboard_arrow_down</i>
                    <i class="material-icons up-pointer hide">keyboard_arrow_up</i>
                    Tags
                </a>
                <%
                    int index = -1;
                    for (Object object : tagList)
                    {
                        Tag tag = (Tag) object;
                        index++;
                %>
                <ul class="collapsible-body tag-item">
                    <li>
                        <a href="#">
                            <i class="material-icons">label</i>
                            <%=tag.getTagName()%>
                            <i class="material-icons right tag-delete hide"
                               onclick="delete_data('<%=tag.getTagName()%>','tag',<%=tag.getTagID()%>)">delete</i>
                            <i class="material-icons right tag-delete hide"
                               onclick="showEditTag(<%=index%>)">edit</i>
                        </a>
                    </li>
                </ul>
                <%
                    }
                %>
                <ul class="collapsible-body">
                    <li>
                        <a href="#modal-new-tag">
                            <i class="material-icons">add</i>
                            Create Tag
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
    </li>
    <li>
        <div class="divider"></div>
    </li>
    <li>
        <a class="subheader">Subheader</a>
    </li>
    <li>
        <a class="waves-effect" href="#">Third Link With Waves</a>
    </li>
</ul>

<main>
    <!-- Dropdown Structure -->
    <ul id='dropdown' class='dropdown-content'>
        <li>
            <a href="logout.jsp">Logout</a>
        </li>
    </ul>

    <ul class="collection">
        <%
            index = -1;
            for (Object object : contactList)
            {
                Contact contact = (Contact) object;
                index++;
        %>
        <li class="collection-item avatar valign-wrapper list-item">
            <img src="img/head/<%=(int) (Math.random() * 19 + 1)%>.png" class="circle
            reset-img-head check-img">
            <div class="circle check-checkbox hide">
                <input type="checkbox" class="filled-in" id="filled-in-box<%=index%>"/>
                <label class="reset-checkbox" for="filled-in-box<%=index%>"></label>
            </div>
            <span id="contactShowName<%=index%>"
                  class="title reset-content"><%=contact.getContactName()%></span>
            <span><%=(contact.getCountryCode().equals("null") ? "" : (contact.getCountryCode() + " ")) + DBUtil.getInfoList(contact, "phone").get(0)%></span>
            <div class="reset-secondary-content valign-wrapper check-edit hide">
                <a href="#modal-edit-contact-<%=index%>" class="valign-wrapper">
                    <i class="material-icons">edit</i>
                </a>
            </div>
        </li>

        <!-- Modal Structure -->
        <div id="modal-edit-contact-<%=index%>" class="modal modal-fixed-footer">
            <form id="form-edit-contact-<%=index%>" action="UpdateServlet" class="submit-form"
                  method="post">
                <input type="text" name="data-type" value="contact" title="contact" hidden>
                <input type="text" name="contactID" value="<%=contact.getContactID()%>"
                       title="contact" hidden>
                <input type="text" name="userID" value="<%=UserUtil.getUserID(username)%>"
                       title="userID" hidden>
                <div class="modal-content row">
                    <h5>Edit Contact</h5>
                    <div class="input-field col s12">
                        <i class="material-icons prefix reset-prefix valign-wrapper reset-color">account_circle</i>
                        <input id="contactName<%=index%>" value="<%=contact.getContactName()%>"
                               name="contactName" type="text" class="validate" required>
                        <label for="contactName<%=index%>">Name</label>
                    </div>
                    <div class="input-field col s12">
                        <i class="material-icons prefix reset-prefix valign-wrapper reset-color">smartphone</i>
                        <input id="phoneNumber<%=index%>"
                               value="<%=contact.getPhoneNumberList()%>"
                               name="phoneNumber" type="number" class="validate"
                               data-error="wrong" required>
                        <label for="phoneNumber<%=index%>">Phone</label>
                    </div>
                    <div class="input-field col s6">
                        <i class="material-icons prefix reset-prefix valign-wrapper reset-color">language</i>
                        <select id="countryCode<%=index%>" class="icons" name="countryCode">
                            <option value="null" <%=contact.getCountryCode().equals("null") ? "selected" : ""%>>Choose your Country</option>
                            <option value="+86" <%=contact.getCountryCode().equals("+86") ? "selected" : ""%>>China (+86)</option>
                            <option value="+1" <%=contact.getCountryCode().equals("+1") ? "selected" : ""%>>US (+1)</option>
                            <option value="other" <%=contact.getCountryCode().equals("other") ? "selected" : ""%>>Other</option>
                        </select>
                        <label for="countryCode<%=index%>">Country</label>
                    </div>
                    <div class="input-field col s6">
                        <i class="material-icons prefix reset-prefix valign-wrapper reset-color">label</i>
                        <select id="tag<%=index%>" class="icons" name="tag">
                            <option value="null" <%=contact.getTag().equals("null") ? "selected" : ""%>>Please Choose Tag
                            </option>
                            <%
                                for (Object tagObject : tagList)
                                {
                                    Tag tag = (Tag) tagObject;
                            %>
                            <option value="<%=tag.getTagName()%>" <%=contact.getTag().equals(tag.getTagName()) ? "selected" : ""%>><%=tag.getTagName()%>
                            </option>
                            <%
                                }
                            %>
                        </select>
                        <label for="tag<%=index%>">Tag</label>
                    </div>
                    <div class="input-field col s12">
                        <i class="material-icons prefix reset-prefix valign-wrapper reset-color">email</i>
                        <input id="emailList<%=index%>" value="<%=contact.getEmailList()%>"
                               name="emailList" type="email" class="validate"
                               data-error="wrong">
                        <label for="emailList<%=index%>">E-mail</label>
                    </div>
                </div>
                <div class="modal-footer">
                    <a href="#" onclick="checkForm('form-edit-contact-',<%=index%>);"
                       class="modal-action waves-effect waves-green btn">
                        <i class="material-icons">done_all</i>
                    </a>
                </div>
            </form>
        </div>
        <%
            }

            index = -1;
            for (Object object : tagList)
            {
                Tag tag = (Tag) object;
                index++;
        %>

        <!-- Modal Structure -->
        <div id="modal-edit-tag-<%=index%>" class="modal">
            <form id="form-edit-tag-<%=index%>" class="submit-form"
                  action="UpdateServlet" method="post">
                <input type="text" name="data-type" value="tag" title="tag" hidden>
                <input type="text" name="tagID" value="<%=tag.getTagID()%>" title="tag" hidden>
                <input type="text" name="userID" value="<%=UserUtil.getUserID(username)%>"
                       title="userID" hidden>
                <div class="modal-content">
                    <h4>Edit Tag</h4>
                    <div class="input-field col s12">
                        <input id="tagName<%=index%>" name="tagName" type="text"
                               class="validate" value="<%=tag.getTagName()%>" required>
                        <label for="tagName<%=index%>">Tag Name</label>
                    </div>
                </div>
                <div class="modal-footer">
                    <a href="#" onclick="checkForm('form-edit-tag-',<%=index%>);"
                       class=" modal-action modal-close waves-effect waves-green btn-flat">Done</a>
                </div>
            </form>
        </div>
        <%
            }
        %>
    </ul>

    <!-- Modal Structure -->
    <div id="modal-new-contact" class="modal modal-fixed-footer">
        <form id="form-new-contact" class="submit-form" action="InsertServlet" method="post">
            <input type="text" name="data-type" value="contact" title="contact" hidden>
            <input type="text" name="userID" value="<%=UserUtil.getUserID(username)%>"
                   title="userID" hidden>
            <div class="modal-content row">
                <h5>New Contact</h5>
                <div class="input-field col s12">
                    <i class="material-icons prefix reset-prefix valign-wrapper reset-color">account_circle</i>
                    <input id="contactName" name="contactName" type="text" class="validate"
                           required>
                    <label for="contactName">Name</label>
                </div>
                <div class="input-field col s12">
                    <i class="material-icons prefix reset-prefix valign-wrapper reset-color">smartphone</i>
                    <input id="phoneNumber" name="phoneNumber" type="number" class="validate"
                           required data-error="wrong">
                    <label for="phoneNumber">Phone</label>
                </div>
                <div class="input-field col s6">
                    <i class="material-icons prefix reset-prefix valign-wrapper reset-color">language</i>
                    <select id="countryCode" class="icons" name="countryCode">
                        <option value="null" selected>Choose your Country</option>
                        <option value="+86">China (+86)</option>
                        <option value="+1">US (+1)</option>
                        <option value="other">Other</option>
                    </select>
                    <label for="countryCode">Country</label>
                </div>
                <div class="input-field col s6">
                    <i class="material-icons prefix reset-prefix valign-wrapper reset-color">label</i>
                    <select id="tag" class="icons" name="countryCode">
                        <option selected value="null">Please Choose Tag
                        </option>
                        <%
                            for (Object tagObject : tagList)
                            {
                                Tag tag = (Tag) tagObject;
                        %>
                        <option value="<%=tag.getTagName()%>"><%=tag.getTagName()%>
                        </option>
                        <%
                            }
                        %>
                    </select>
                    <label for="tag">Tag</label>
                </div>
                <div class="input-field col s12">
                    <i class="material-icons prefix reset-prefix valign-wrapper reset-color">email</i>
                    <input id="emailList" name="emailList" type="email" class="validate"
                           data-error="wrong">
                    <label for="emailList">E-mail</label>
                </div>
            </div>
            <div class="modal-footer">
                <a href="#" onclick="checkForm('form-new-contact',-1);"
                   class="modal-action waves-effect waves-green btn">
                    <i class="material-icons">done_all</i>
                </a>
            </div>
        </form>
    </div>

    <!-- Modal Structure -->
    <div id="modal-new-tag" class="modal">
        <form id="form-new-tag" class="submit-form" action="InsertServlet" method="post">
            <input type="text" name="data-type" value="tag" title="contact" hidden>
            <input type="text" name="userID" value="<%=UserUtil.getUserID(username)%>"
                   title="userID" hidden>
            <div class="modal-content row">
                <h4>Create New Tag</h4>
                <div class="input-field col s12">
                    <input id="tagName" name="tagName" type="text" class="validate">
                    <label for="tagName">Tag Name</label>
                </div>
            </div>
            <div class="modal-footer">
                <a href="#" onclick="checkForm('form-new-tag',-1);"
                   class=" modal-action modal-close waves-effect waves-green btn-flat">Create</a>
            </div>
        </form>
    </div>

    <a id="reset-floating-button" href="#modal-new-contact"
       class="btn-floating btn-large waves-effect waves-light red right">
        <i class="material-icons">add</i>
    </a>
</main>

<footer class="page-footer blue">
    <div class="container center-align">
        <ul class="pagination">
            <li class="<%=((pageIndex==1)?"disabled":"")%> blue">
                <a href="<%=((pageIndex==1)?"#":"index.jsp?index="+(pageIndex-1))%>">
                    <i class="material-icons">chevron_left</i>
                </a>
            </li>
            <%
                for (int a = 1; a <= totalPages; a++)
                {
            %>
            <li class="<%=((pageIndex==a)?"active darken-1":"waves-effect")%> blue">
                <a href="<%=((pageIndex==a)?"#":"index.jsp?index="+a)%>"><%=a%>
                </a>
            </li>
            <%
                }
            %>
            <li class="<%=((pageIndex==totalPages)?"disabled":"")%> waves-effect blue">
                <a href="<%=((pageIndex==totalPages)?"#":"index.jsp?index="+(pageIndex+1))%>">
                    <i class="material-icons">chevron_right</i>
                </a>
            </li>
        </ul>
    </div>
    <div class="footer-copyright">
        <div class="container">@ 2017 Copyright WeiLy Lab
            <a class="grey-text text-lighten-4 right" href="#">More Links</a>
        </div>
    </div>
</footer>

<!--Import jQuery before materialize.js-->
<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="js/materialize.min.js"></script>
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript">
    // Initialize collapse button
    $(".button-collapse").sideNav({
        menuWidth: 360 // Default is 240
    });
    // Initialize collapsible (uncomment the line below if you use the dropdown variation)
    $('.collapsible').collapsible();

    $(document).ready(function ()
    {
        // the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
        $('.modal').modal();

        $('select').material_select();
    });
    <%
    if (message!=null)
        {
    %>
    Materialize.toast('<%=message%>', 3000);
    <%
        }
    %>
</script>
</body>
</html>