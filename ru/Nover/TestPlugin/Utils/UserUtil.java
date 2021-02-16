package ru.Nover.TestPlugin.Utils;

import ru.Nover.TestPlugin.JavaMain;

import java.sql.ResultSet;

public class UserUtil
{
    private UserUtil ( ) {}

    private static boolean isExists ( String userName ) {
        try {
            return JavaMain.get().getMySQL().executeQuery("SELECT * FROM `" + JavaMain.get().getConfig().getString("mysql.table") + "` WHERE `Name` = ?", ResultSet::next, userName);
        }catch (Exception ex){
            return false;
        }
    }

    public static void updatePostId ( String userName, int postId ) {
        if ( !isExists(userName) ) { JavaMain.get().getMySQL().execute("INSERT INTO `" + JavaMain.get().getConfig().getString("mysql.table") + "`(`Name`, `PostId`) VALUES (?, ?)", userName, postId); }
        else JavaMain.get().getMySQL().execute("UPDATE `" + JavaMain.get().getConfig().getString("mysql.table") + "` SET `PostId` = ? WHERE `Name` = ?", postId, userName);
    }

    public static int getPostId ( String userName ) {
        if ( !isExists(userName) ) return 0;
        else return JavaMain.get().getMySQL().executeQuery("SELECT * FROM `" + JavaMain.get().getConfig().getString("mysql.table") +"` WHERE `Name` = ?", rs -> rs.next() ? rs.getInt("PostId") : 0, userName);
    }
}
