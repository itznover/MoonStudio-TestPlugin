package ru.Nover.TestPlugin;

import org.bukkit.plugin.java.JavaPlugin;
import ru.Nover.TestPlugin.Threads.BroadcastThread;
import ru.Nover.TestPlugin.Utils.Database.MySQL;

public class JavaMain
extends JavaPlugin
{
    private static JavaMain javaMain;
    private MySQL mySQL;

    @Override
    public void onEnable() {
        saveDefaultConfig(); javaMain = this;

        mySQL = MySQL.newBuilder()
                .host(getConfig().getString("mysql.host"))
                .port(getConfig().getInt("mysql.port"))
                .user(getConfig().getString("mysql.user"))
                .password(getConfig().getString("mysql.pass"))
                .database(getConfig().getString("mysql.database"))
                .create();
        try{ mySQL.execute("CREATE TABLE `" + getConfig().getString("mysql.database") +"`.`" + getConfig().getString("mysql.table") +"` ( `Name` TEXT NOT NULL , `PostId` INT(255) NOT NULL ) ENGINE = InnoDB;"); }catch (NullPointerException ignored) {}

        getCommand("news").setExecutor(new Command());
        getServer().getScheduler().runTaskAsynchronously(this, new BroadcastThread());
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        super.onDisable();
    }

    public MySQL getMySQL ( ) { return mySQL; }
    public static JavaMain get ( ) { return javaMain; }
}
