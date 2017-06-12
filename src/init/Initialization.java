package init;

import util.JDBCUtil;

public class Initialization
{
    private static JDBCUtil jdbcUtil;

    public static void initSQL()
    {
        String sql1 = "CREATE TABLE IF NOT EXISTS `mystery0_contacts`.`user`(\n" +
                "  `userID` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `username` VARCHAR(45) NOT NULL,\n" +
                "  `password` VARCHAR(45) NOT NULL,\n" +
                "  `userType` VARCHAR(45) NOT NULL,\n" +
                "  PRIMARY KEY (`userID`))\n" +
                "DEFAULT CHARACTER SET = utf8;";
        getJDBCUtil().update(sql1, null);

        String sql2 = "CREATE TABLE IF NOT EXISTS `mystery0_contacts`.`contact` (\n" +
                "  `contactID` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `phoneNumberList` VARCHAR(45) NULL,\n" +
                "  `initials` VARCHAR(45) NOT NULL,\n" +
                "  `countryCode` VARCHAR(45) NOT NULL,\n" +
                "  `tag` VARCHAR(45) NULL,\n" +
                "  `emailList` VARCHAR(45) NULL,\n" +
                "  `userID` VARCHAR(45) NOT NULL,\n" +
                "  PRIMARY KEY (`contactID`))\n" +
                "  DEFAULT CHARACTER SET = utf8;";
        getJDBCUtil().update(sql2, null);

        String sql3 = "CREATE TABLE IF NOT EXISTS `mystery0_contacts`.`tag` (\n" +
                "  `tagID` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `tagName` VARCHAR(45) NOT NULL,\n" +
                "  `userID` VARCHAR(45) NOT NULL,\n" +
                "  PRIMARY KEY (`tagID`))\n" +
                "  DEFAULT CHARACTER SET = utf8;";
        getJDBCUtil().update(sql3, null);
    }

    public static JDBCUtil getJDBCUtil()
    {
        if (jdbcUtil==null)
        {
            jdbcUtil=new JDBCUtil("mystery0_contacts");
        }
        return jdbcUtil;
    }
}
