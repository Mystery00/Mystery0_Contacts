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
import util.DBUtil;

@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String searchString = request.getParameter("searchString");
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
        String tagSql = "SELECT tagName\n" +
                "FROM tag, user\n" +
                "WHERE username = ? AND tag.userID = user.userID";
        List<Object> tagList = Initialization.getJDBCUtil().getObject(tagSql, new String[]{username}, Tag.class);
        List<Contact> contactList = DBUtil.searchContacts(username, searchString);
        request.getSession().setAttribute("tagList", tagList);
        request.getSession().setAttribute("contactList", contactList);
        request.getSession().setAttribute("message", "Searched " + contactList.size() + " items!");
        response.sendRedirect("/GetDataServlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
