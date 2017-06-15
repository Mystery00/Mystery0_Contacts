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

@WebServlet(name = "DeleteServlet", urlPatterns = {"/DeleteServlet"})
public class DeleteServlet extends HttpServlet
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
        String deleteString = request.getParameter("deleteString");
        String type = request.getParameter("type");
        switch (type)
        {
            case "tag":
                Tag tag = new Tag();
                tag.setTagName(deleteString);
                if (DBUtil.deleteObject(tag, username) != -1)
                {
                    request.getSession().setAttribute("message", "Tag Delete Done!");
                } else
                {
                    request.getSession().setAttribute("message", "Tag Delete failed!");
                }
                break;
            case "contact":
                String[] deleteStrings = deleteString.split(",");
                Contact contact = new Contact();
                int result = 0;
                for (String temp : deleteStrings)
                {
                    contact.setContactName(temp);
                    result += DBUtil.deleteObject(contact, username);
                }
                if (result >= deleteStrings.length)
                {
                    request.getSession().setAttribute("message", "Contact Delete Done!");
                } else
                {
                    request.getSession().setAttribute("message", "Contact Delete failed!");
                }
                break;
        }
        response.sendRedirect("index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
