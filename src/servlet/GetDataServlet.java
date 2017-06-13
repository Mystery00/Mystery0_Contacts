package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Contact;
import classes.Tag;
import init.Initialization;

@WebServlet(name = "GetDataServlet", urlPatterns = {"/GetDataServlet"})
public class GetDataServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String username = null;
        for (Cookie cookie : request.getCookies())
        {
            if (cookie.getName().equals("username"))
            {
                username = cookie.getValue();
                break;
            }
        }
        if (username == null)
        {
            return;
        }
        String tagSql = "SELECT tagName FROM tag,user WHERE username=? AND tag.userID=user.userID";
        List<Object> tagList = Initialization.getJDBCUtil().getObject(tagSql, new String[]{username}, Tag.class);
        String contactSql = "SELECT contactName,phoneNumberList,initials,countryCode,tag,emailList FROM contact,user WHERE username=? AND contact.userID=user.userID";
        List<Object> contactList = Initialization.getJDBCUtil().getObject(contactSql, new String[]{username}, Contact.class);
        request.setAttribute("tagList", tagList);
        request.setAttribute("contactList", contactList);
        getServletConfig().getServletContext().getRequestDispatcher("/main.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
