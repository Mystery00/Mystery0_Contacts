package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBUtil;
import util.PageBean;

@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String searchString = request.getParameter("searchString");
        int index;
        try
        {
            index = Integer.parseInt(request.getParameter("index"));
        }catch (Exception e)
        {
            index=1;
        }
        String username = null;
        for (Cookie cookie : request.getCookies())
        {
            if (cookie.getName().equals("username"))
            {
                username = cookie.getValue();
                break;
            }
        }
        if (username == null)
        {
            return;
        }
        PageBean pageBean = DBUtil.searchContacts(username, searchString, index);
        request.getSession().setAttribute("PageBean", pageBean);
        if (!request.getParameter("searchString").equals(""))
        {
            request.getSession().setAttribute("message", "Searched " + pageBean.getTotalRows() + " items!");
            request.getSession().setAttribute("isSearch", true);
            request.getSession().setAttribute("searchString", searchString);
        }
        response.sendRedirect("index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
