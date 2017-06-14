package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        System.out.println(deleteString);
        Tag tag = new Tag();
        tag.setTagName(deleteString);
        if (DBUtil.deleteObject(tag, username) != -1)
        {
            request.getSession().setAttribute("message","Delete Done!");
        } else
        {
            request.getSession().setAttribute("message","Delete failed!");
        }
        response.sendRedirect("/GetDataServlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
