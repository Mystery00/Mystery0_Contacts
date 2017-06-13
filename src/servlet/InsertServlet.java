package servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Contact;

@WebServlet(name = "InsertServlet", urlPatterns = {"/InsertServlet"})
public class InsertServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String type = request.getParameter("data-type");
        Enumeration<String> enumeration = request.getParameterNames();
        switch (type)
        {
            case "contact":
                Contact contact=new Contact();
                while (enumeration.hasMoreElements())
                {
                    String name = enumeration.nextElement();
                    System.out.println(name);
                    System.out.println(request.getParameter(name));
                }
                break;
            case "tag":
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
