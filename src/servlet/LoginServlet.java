package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import init.Initialization;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String type = request.getParameter("type");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String sql = "SELECT * FROM user WHERE username=?";
        List list = Initialization.getJDBCUtil().getList(sql, new String[]{username});
        if (list.size() != 1)
        {
            switch (type)
            {
                case "forget":
                    response.sendRedirect("forget.jsp?incorrect=username");
                    break;
                case "login":
                    response.sendRedirect("index.jsp?incorrect=username");
                    break;
            }
            return;
        }
        switch (type)
        {
            case "forget":
                sql = "UPDATE user SET password=? WHERE username=?";
                int result = Initialization.getJDBCUtil().update(sql, new String[]{password, username});
                if (result == 1)
                {
                    response.sendRedirect("index.jsp?incorrect=forget");
                } else
                {
                    response.sendRedirect("forget.jsp?incorrect=error");
                }
                break;
            case "login":
                sql = "SELECT * FROM user WHERE username=? AND password=?";
                list = Initialization.getJDBCUtil().getList(sql, new String[]{username, password});
                if (list.size() == 1)
                {
                    response.sendRedirect("main.jsp");
                } else
                {
                    response.sendRedirect("index.jsp?incorrect=password");
                }
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    }
}
