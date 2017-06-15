package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.User;
import init.Initialization;
import util.UserUtil;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (!UserUtil.haveUser(username))
        {
            response.sendRedirect("login.jsp?incorrect=username");
            return;
        }
        String sql = "SELECT * FROM user WHERE username=? AND password=?";
        List<Object> list = Initialization.getJDBCUtil().getObject(sql, new String[]{username, password}, User.class);
        if (list.size() == 1)
        {
            Cookie cookie = new Cookie("username", username);
            response.addCookie(cookie);
            response.sendRedirect("index.jsp");
        } else
        {
            response.sendRedirect("login.jsp?incorrect=password");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
