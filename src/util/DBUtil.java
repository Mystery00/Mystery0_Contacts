package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import classes.Contact;
import classes.Tag;
import init.Initialization;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

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
                "  phoneNumber,\n" +
                "  countryCode,\n" +
                "  tag,\n" +
                "  email\n" +
                "FROM contact, user\n" +
                "WHERE username = '" + username + "' AND contact.userID = user.userID AND\n" +
                "      (contact.contactName LIKE '%" + searchString + "%' OR contact.phoneNumber LIKE '%" + searchString + "%')";
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

    public static int exportExcel(Class c, List<Object> objectList, String fileName)
    {
        int code = 0;
        try
        {
            File file = new File(fileName);
            if (file.exists() || file.createNewFile())
            {
                WritableWorkbook writableWorkbook = Workbook.createWorkbook(file);
                WritableSheet sheet = writableWorkbook.createSheet(c.getSimpleName(), 0);
                int i = 0;
                for (Field field : c.getDeclaredFields())
                {
                    if (field.getName().toLowerCase().contains("id"))
                    {
                        continue;
                    }
                    Label label = new Label(i, 0, field.getName());
                    sheet.addCell(label);
                    i++;
                }
                for (i = 0; i < objectList.size(); i++)
                {
                    int j = 0;
                    for (Field field : c.getDeclaredFields())
                    {
                        if (field.getName().toLowerCase().contains("id"))
                        {
                            continue;
                        }
                        for (Method method : c.getMethods())
                        {
                            if (method.getName().toLowerCase().contains("get") && method.getName().toLowerCase().contains(field.getName().toLowerCase()))
                            {
                                Label label = new Label(j, i + 1, String.valueOf(method.invoke(objectList.get(i))));
                                sheet.addCell(label);
                            }
                        }
                        j++;
                    }
                }
                writableWorkbook.write();
                writableWorkbook.close();
            }
        } catch (Exception e)
        {
            code = -1;
            e.printStackTrace();
        }
        return code;
    }

    public static int importExcel(String path, String username)
    {
        int code = 0;
        int result = 0;
        try
        {
            InputStream inputStream = new FileInputStream(new File(path));
            Workbook workbook = Workbook.getWorkbook(inputStream);
            int sheet_size = workbook.getNumberOfSheets();
            for (int index = 0; index < sheet_size; index++)
            {
                Sheet sheet = workbook.getSheet(index);
                List<String> columns = new ArrayList<>();
                for (int i = 0; i < sheet.getColumns(); i++)
                {
                    columns.add(sheet.getCell(i, 0).getContents());
                }
                for (int i = 1; i < sheet.getRows(); i++)
                {
                    Contact contact = new Contact();
                    for (int j = 0; j < sheet.getColumns(); j++)
                    {
                        for (Method method : contact.getClass().getMethods())
                        {
                            if (method.getName().toLowerCase().contains("set") && method.getName().toLowerCase().contains(columns.get(j).toLowerCase()))
                            {
                                method.invoke(contact, sheet.getCell(j, i).getContents());
                            }
                        }
                    }
                    contact.setUserID(UserUtil.getUserID(username));
                    code += saveObject(contact);
                }
                result = sheet.getRows() - 1;
            }
        } catch (BiffException | IOException | IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
            return -1;
        }
        if (code == result)
        {
            return 0;
        } else
            return -1;
    }
}
