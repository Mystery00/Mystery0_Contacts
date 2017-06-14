package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Contact;
import util.DBUtil;

@WebServlet(name = "UpdateServlet", urlPatterns = {"/UpdateServlet"})
public class UpdateServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String type = request.getParameter("data-type");
        int code = -1;
        switch (type)
        {
            case "contact":
                Contact contact = (Contact) DBUtil.getObject(request, request.getParameterNames(), Contact.class);
                if (contact == null)
                    return;
                String id = DBUtil.getContactID(contact.getContactName(), contact.getUserID());
                code = DBUtil.updateObject(contact, id);
                break;
            case "tag":
                break;
        }
        if (code != -1)
        {
            response.sendRedirect("GetDataServlet");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
