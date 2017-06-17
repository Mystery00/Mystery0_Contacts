package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Contact;
import init.Initialization;
import util.DBUtil;

@WebServlet(name = "ExportServlet", urlPatterns = {"/ExportServlet"})
public class ExportServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String username = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies)
        {
            if (cookie.getName().equals("username"))
            {
                username = cookie.getValue();
            }
        }
        if (username == null)
        {
            response.sendRedirect("login.jsp");
            return;
        }
        String sql = "SELECT contactID,contactName,phoneNumber,countryCode,tag,email FROM contact,user WHERE username=? AND contact.userID=user.userID";
        List<Object> list = Initialization.getJDBCUtil().getObject(sql, new String[]{username}, Contact.class);
        if (DBUtil.exportExcel(Contact.class, list, getServletContext().getRealPath("/temp/" + username + ".xls")) == 0)
        {
            request.getSession().setAttribute("message", "Export Successful!");
        } else
        {
            request.getSession().setAttribute("message", "Export Failed!");
        }
        response.sendRedirect("index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
