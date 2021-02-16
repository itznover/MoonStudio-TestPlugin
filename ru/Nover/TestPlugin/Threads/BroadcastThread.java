package ru.Nover.TestPlugin.Threads;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import ru.Nover.TestPlugin.JavaMain;
import ru.Nover.TestPlugin.Utils.VKUtil;

public class BroadcastThread
implements Runnable
{
    private int lastPostId = 0;

    @Override
    public void run() {
        while (JavaMain.get().isEnabled()) {
            try {
                VKUtil.VKResponse vkResponse = VKUtil.getLastPost(JavaMain.get().getConfig().getString("groupDomain"));
                if (vkResponse.getLastPostId().intValue() != lastPostId) {
                    lastPostId = vkResponse.getLastPostId().intValue();
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', JavaMain.get().getConfig().getString("messages.broadcast")));
                }

                Thread.sleep(15000);
            }catch (Exception ex){ex.printStackTrace();}
        }
    }
}
