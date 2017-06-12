package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import init.Initialization;

@WebServlet(name = "ForgetServlet", urlPatterns = {"/ForgetServlet"})
public class ForgetServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String sql = "SELECT * FROM user WHERE username=?";
        List list = Initialization.getJDBCUtil().getList(sql, new String[]{username});
        if (list.size() != 1)
        {
            response.sendRedirect("forget.jsp?incorrect=username");
            return;
        }
        sql = "UPDATE user SET password=? WHERE username=?";
        int result = Initialization.getJDBCUtil().update(sql, new String[]{password, username});
        if (result == 1)
        {
            response.sendRedirect("index.jsp?incorrect=forget");
        } else
        {
            response.sendRedirect("forget.jsp?incorrect=error");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }
}
