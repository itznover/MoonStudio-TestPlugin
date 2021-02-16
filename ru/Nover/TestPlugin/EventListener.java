package ru.Nover.TestPlugin;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.Nover.TestPlugin.Threads.AsyncThread;

public class EventListener
implements Listener
{
    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event){
        Bukkit.getScheduler().runTaskAsynchronously(JavaMain.get(), new AsyncThread(event.getPlayer(), true));
    }
}
