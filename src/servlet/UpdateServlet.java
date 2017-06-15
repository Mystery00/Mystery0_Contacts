package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Contact;
import classes.Tag;
import util.DBUtil;

@WebServlet(name = "UpdateServlet", urlPatterns = {"/UpdateServlet"})
public class UpdateServlet extends HttpServlet
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
        String type = request.getParameter("data-type");
        int code = -1;
        switch (type)
        {
            case "contact":
                Contact contact = (Contact) DBUtil.getObject(request, request.getParameterNames(), Contact.class);
                if (contact == null)
                    return;
                String contactID = DBUtil.getContactID(contact.getContactName(), username);
                code = DBUtil.updateObject(contact, contactID);
                break;
            case "tag":
                Tag tag = (Tag) DBUtil.getObject(request, request.getParameterNames(), Contact.class);
                if (tag == null)
                    return;
                String tagID = DBUtil.getContactID(tag.getTagName(), username);
                code = DBUtil.updateObject(tag, tagID);
                break;
        }
        if (code > 0)
        {
            request.getSession().setAttribute("message", "Update Successful!");
        } else
        {
            request.getSession().setAttribute("message", "Update Failed!");
        }
        response.sendRedirect("index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
