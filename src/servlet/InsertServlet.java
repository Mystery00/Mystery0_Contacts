package servlet;

import java.io.IOException;
import java.util.Enumeration;
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

@WebServlet(name = "InsertServlet", urlPatterns = {"/InsertServlet"})
public class InsertServlet extends HttpServlet
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
                code = DBUtil.saveObject(contact);
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
