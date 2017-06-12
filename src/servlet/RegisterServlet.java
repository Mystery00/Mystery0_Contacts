package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import init.Initialization;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String sql = "SELECT * FROM user WHERE username=?";
        List list = Initialization.getJDBCUtil().getList(sql, new String[]{username});
        if (list.size() != 0)
        {
            response.sendRedirect("register.jsp?incorrect=username");
            return;
        }
        sql = "INSERT INTO user(username, password, userType) VALUES (?,?,?)";
        list = Initialization.getJDBCUtil().getList(sql, new String[]{username, password,});
        if (list.size() == 1)
        {
            response.sendRedirect("main.jsp");
        } else
        {
            response.sendRedirect("index.jsp?incorrect=password");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }
}
