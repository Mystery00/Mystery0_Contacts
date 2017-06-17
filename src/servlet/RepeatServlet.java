package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Contact;
import init.Initialization;

@WebServlet(name = "RepeatServlet", urlPatterns = {"/RepeatServlet"})
public class RepeatServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String sql = "SELECT *\n" +
                "FROM contact\n" +
                "WHERE phoneNumber IN (SELECT phoneNumber\n" +
                "                          FROM contact\n" +
                "                          GROUP BY phoneNumber\n" +
                "                          HAVING count(phoneNumber) > 1)";
        List<Object> objectList = Initialization.getJDBCUtil().getObject(sql, new String[]{}, Contact.class);
        List<Contact> contactList = new ArrayList<>();
        for (Object obj1 : objectList)
        {
            for (Object obj2 : objectList)
            {
                if ((!obj1.equals(obj2) && ((Contact) obj1).getCountryCode().equals(((Contact) obj2).getCountryCode())))
                {
                    contactList.add((Contact) obj1);
                    break;
                }
            }
        }
        request.getSession().setAttribute("isCheckRepeat", true);
        request.getSession().setAttribute("repeatList", contactList);
        response.sendRedirect("index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
