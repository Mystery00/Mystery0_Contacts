package util;

import java.util.List;

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
}
