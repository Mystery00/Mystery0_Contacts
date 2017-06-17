package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBUtil;

@WebServlet(name = "ImportServlet", urlPatterns = {"/ImportServlet"})
public class ImportServlet extends HttpServlet
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
        String fileName = String.valueOf(request.getSession().getAttribute("fileName"));
        int code = DBUtil.importExcel(getServletContext().getRealPath("/upload/" + fileName), username);
        if (code == 0)
        {
            request.getSession().setAttribute("message", "Import Successful!");
        } else
        {
            request.getSession().setAttribute("message", "Import Failed!");
        }
        response.sendRedirect("index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
