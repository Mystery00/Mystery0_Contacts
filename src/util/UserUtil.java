package util;

import java.util.List;
import java.util.Map;

import classes.User;
import init.Initialization;

public class UserUtil
{
    public static boolean haveUser(String username)
    {
        String sql = "SELECT * FROM user WHERE username=?";
        List<Object> list = Initialization.getJDBCUtil().getObject(sql, new String[]{username}, User.class);
        return list.size() == 1;
    }

    public static String getUserID(String username)
    {
        String sql = "SELECT * FROM user WHERE username=?";
        Map map = Initialization.getJDBCUtil().getMap(sql, new String[]{username});
        return (String) map.get("userID");
    }
}
