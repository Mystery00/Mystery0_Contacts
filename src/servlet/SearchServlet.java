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
        List<Contact> contactList = DBUtil.searchContacts(username, searchString);
        request.getSession().setAttribute("contactList", contactList);
        if (!request.getParameter("searchString").equals(""))
            request.getSession().setAttribute("message", "Searched " + contactList.size() + " items!");
        response.sendRedirect("index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
