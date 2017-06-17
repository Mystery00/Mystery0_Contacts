package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCUtil
{
    private String USERNAME;
    private String PASSWORD;
    private String URL;
    private Connection connection;
    private PreparedStatement preparedStatement;

    public JDBCUtil(String databaseName)
    {
        USERNAME = "root";
        PASSWORD = "root";
        String DRIVER = "com.mysql.jdbc.Driver";
        URL = "jdbc:mysql://localhost:3306/" + databaseName;
        try
        {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public Connection getConnection()
    {
        if (connection == null)
        {
            try
            {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return connection;
    }

    private PreparedStatement getPreparedStatement(String sql)
    {
        try
        {
            this.preparedStatement = getConnection().prepareStatement(sql);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    private void setParams(String sql, String[] params)
    {
        try
        {
            preparedStatement = getPreparedStatement(sql);
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    preparedStatement.setObject(i + 1, params[i]);
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void setParams(String sql, List<String> params)
    {
        try
        {
            preparedStatement = getPreparedStatement(sql);
            if (params != null)
            {
                for (int i = 0; i < params.size(); i++)
                {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public List<Object> getObject(String sql, String[] params, Class c)
    {
        try
        {
            List<Object> list = new ArrayList<>();
            setParams(sql, params);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            while (resultSet.next())
            {
                Object object = c.newInstance();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                {
                    String colName = resultSetMetaData.getColumnName(i);
                    for (Method method : c.getMethods())
                    {
                        if (method.getName().toLowerCase().contains("set") && method.getName().toLowerCase().contains(colName.toLowerCase()))
                        {
                            method.invoke(object, resultSet.getString(colName));
                        }
                    }
                }
                list.add(object);
            }
            resultSet.close();
            return list;
        } catch (IllegalAccessException | InstantiationException | SQLException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public List<Object> getObjectFromList(Class c, List list)
    {
        try
        {
            List<Object> tempList = new ArrayList<>();
            for (Object obj1 : list)
            {
                Map map = (Map) obj1;
                Object object = c.newInstance();
                for (Object obj2 : map.keySet())
                {
                    String key = String.valueOf(obj2);
                    for (Method method : c.getMethods())
                    {
                        if (method.getName().toLowerCase().contains("set") && method.getName().toLowerCase().contains(key.toLowerCase()))
                        {
                            method.invoke(object, map.get(key));
                        }
                    }
                }
                tempList.add(object);
            }
            return tempList;
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public List getList(String sql, String[] params)
    {
        List list = new ArrayList();
        try
        {
            setParams(sql, params);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            while (resultSet.next())
            {
                Map map = new HashMap();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                {
                    String colName = resultSetMetaData.getColumnName(i);
                    map.put(colName, resultSet.getString(colName));
                }
                list.add(map);
            }
            resultSet.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public Map getMap(String sql, String[] params)
    {
        List list = getList(sql, params);
        if (list.isEmpty())
            return new HashMap();
        return (Map) list.get(0);
    }

    public int update(String sql, String[] params)
    {
        int recNo = -1;
        try
        {
            setParams(sql, params);
            recNo = preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return recNo;
    }

    public int update(String sql, List<String> params)
    {
        int recNo = -1;
        try
        {
            setParams(sql, params);
            recNo = preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return recNo;
    }

    private int getTotalRows(String sql, String[] params)
    {
        int totalRows;
        sql = sql.toLowerCase();
        String countSql;
        if (sql.contains("group"))
        {
            countSql = "select count(*) from (" + sql + ") as tempNum";
        } else
        {
            countSql = "select count(*) as tempNum " + sql.substring(sql.indexOf("from"));
        }
        String count = (String) getMap(countSql, params).get("tempNum");
        totalRows = Integer.parseInt(count);
        return totalRows;
    }

    public PageBean getPageBean(String sql, String[] params, int curPage)
    {
        int pageSize = 10;
        String newSql = sql + " limit " + (curPage - 1) * pageSize + "," + pageSize;
        List data = getList(newSql, params);
        PageBean pageBean = new PageBean();
        pageBean.setCurPage(curPage);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRows(getTotalRows(sql, params));
        pageBean.setData(data);
        return pageBean;
    }
}
