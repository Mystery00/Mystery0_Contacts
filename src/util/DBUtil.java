package util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import classes.Contact;
import classes.Tag;
import init.Initialization;

public class DBUtil
{
    public static Object getObject(HttpServletRequest request, Enumeration<String> enumeration, Class c)
    {
        try
        {
            Object object = c.newInstance();
            while (enumeration.hasMoreElements())
            {
                String name = enumeration.nextElement();
                for (Method method : c.getMethods())
                {
                    if (method.getName().toLowerCase().contains("set") && method.getName().toLowerCase().contains(name.toLowerCase()))
                    {
                        method.invoke(object, request.getParameter(name));
                    }
                }
            }
            return object;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static int saveObject(Object object)
    {
        try
        {
            Class c = object.getClass();
            StringBuilder sql = new StringBuilder("INSERT INTO " + c.getSimpleName().toLowerCase() + "(");
            Field[] fields = c.getDeclaredFields();
            List<String> params = new ArrayList<>();
            StringBuilder paramsString = new StringBuilder();
            for (int i = 0; i < fields.length; i++)
            {
                if (fields[i].getName().toLowerCase().contains(c.getSimpleName().toLowerCase() + "id"))
                {
                    continue;
                }
                paramsString.append("?");
                sql.append(fields[i].getName());
                for (Method method : c.getMethods())
                {
                    if (method.getName().toLowerCase().contains("get") && method.getName().toLowerCase().contains(fields[i].getName().toLowerCase()))
                    {
                        params.add(String.valueOf(method.invoke(object)));
                    }
                }
                if (i < fields.length - 1)
                {
                    sql.append(",");
                    paramsString.append(",");
                }
            }
            sql.append(") VALUES (").append(paramsString).append(")");
            return Initialization.getJDBCUtil().update(sql.toString(), params);
        } catch (IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public static int updateObject(Object object, String id)
    {
        try
        {
            Class c = object.getClass();
            StringBuilder sql = new StringBuilder("UPDATE " + c.getSimpleName().toLowerCase() + " SET ");
            Field[] fields = c.getDeclaredFields();
            String[] params = new String[fields.length];
            for (int i = 0; i < fields.length; i++)
            {
                sql.append(fields[i].getName()).append("=?");
                for (Method method : c.getMethods())
                {
                    if (method.getName().toLowerCase().contains("get") && method.getName().toLowerCase().contains(fields[i].getName().toLowerCase()))
                    {
                        params[i] = String.valueOf(method.invoke(object));
                    }
                }
                if (i < fields.length - 1)
                {
                    sql.append(",");
                }
            }
            sql.append(" WHERE ").append(c.getSimpleName().toLowerCase()).append("ID=").append(id);
            return Initialization.getJDBCUtil().update(sql.toString(), params);
        } catch (IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public static List<String> getInfoList(Object object, String type)
    {
        List<String> list = new ArrayList<>();
        try
        {
            Class c = object.getClass();
            String source = "";
            for (Method method : c.getMethods())
            {
                if (method.getName().toLowerCase().contains(type) && method.getName().toLowerCase().contains("get") && method.getName().toLowerCase().contains("list"))
                {
                    source += String.valueOf(method.invoke(object));
                    break;
                }
            }
            String[] temps = source.split("!");
            list.addAll(Arrays.asList(temps));
        } catch (IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public static int deleteObject(Object object)
    {
        int result = -1;
        Class c = object.getClass();
        String sql = "DELETE FROM " + c.getSimpleName().toLowerCase() + " WHERE ";
        switch (c.getSimpleName().toLowerCase())
        {
            case "tag":
                Tag tag = (Tag) object;
                sql += "tagID=?";
                result = Initialization.getJDBCUtil().update(sql, new String[]{tag.getTagID()});
                break;
            case "contact":
                Contact contact = (Contact) object;
                sql += "contactID=?";
                result = Initialization.getJDBCUtil().update(sql, new String[]{contact.getContactID()});
                break;
        }
        return result;
    }

    public static PageBean searchContacts(String username, String searchString, int curPage)
    {
        String contactSql = "SELECT\n" +
                "  contactName,\n" +
                "  phoneNumberList,\n" +
                "  countryCode,\n" +
                "  tag,\n" +
                "  emailList\n" +
                "FROM contact, user\n" +
                "WHERE username = '" + username + "' AND contact.userID = user.userID AND\n" +
                "      (contact.contactName LIKE '%" + searchString + "%' OR contact.phoneNumberList LIKE '%" + searchString + "%')";
        return Initialization.getJDBCUtil().getPageBean(contactSql, new String[]{}, curPage);
    }

    public static String getContactID(String contactName, String userID)
    {
        String sql = "SELECT *\n" +
                "FROM contact\n" +
                "WHERE contactName = ? AND userID = ?";
        Map map = Initialization.getJDBCUtil().getMap(sql, new String[]{contactName, userID});
        return (String) map.get("contactID");
    }
}
