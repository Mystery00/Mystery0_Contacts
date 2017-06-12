package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import init.Initialization;
import util.UserUtil;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (!UserUtil.haveUser(username))
        {
            response.sendRedirect("index.jsp?incorrect=username");
            return;
        }
        String sql = "INSERT INTO user(username, password, userType) VALUES (?,?,1)";
        int code=Initialization.getJDBCUtil().update(sql, new String[]{username, password});
        if (code == 1)
        {
            response.sendRedirect("main.jsp");
        } else
        {
            response.sendRedirect("index.jsp?incorrect=password");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
