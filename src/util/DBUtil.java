package util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
            String[] params = new String[fields.length];
            StringBuilder paramsString = new StringBuilder();
            for (int i = 0; i < fields.length; i++)
            {
                paramsString.append("?");
                sql.append(fields[i].getName());
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

    public static String getContactID(String contactName, String userID)
    {
        String sql = "SELECT * FROM contact WHERE contactName=? AND userID=?";
        Map map = Initialization.getJDBCUtil().getMap(sql, new String[]{contactName, userID});
        return (String) map.get("contactID");
    }
}
