package ru.Nover.TestPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.Nover.TestPlugin.Threads.AsyncThread;

public class Command
implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        if ( sender instanceof Player ){
            Player player = (Player) sender;
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', JavaMain.get().getConfig().getString("messages.loading")));
            Bukkit.getScheduler().runTaskAsynchronously(JavaMain.get(), new AsyncThread(player));
            return true;
        }
        sender.sendMessage("Команда только для игроков!");
        return true;
    }
}
