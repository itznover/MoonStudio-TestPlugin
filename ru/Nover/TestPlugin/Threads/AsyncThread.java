package ru.Nover.TestPlugin.Threads;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.Nover.TestPlugin.JavaMain;
import ru.Nover.TestPlugin.Utils.ItemUtil;
import ru.Nover.TestPlugin.Utils.ListUtil;
import ru.Nover.TestPlugin.Utils.UserUtil;
import ru.Nover.TestPlugin.Utils.VKUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class AsyncThread
implements Runnable
{
    private final Player player;
    private final boolean onJoin;

    public AsyncThread ( Player player, boolean onJoin ) {
        this.player = player;
        this.onJoin = onJoin;
    }

    public AsyncThread ( Player player ) {
        this ( player, false );
    }

    @Override
    public void run() {
        VKUtil.VKResponse vkResponse = VKUtil.getLastPost(JavaMain.get().getConfig().getString("groupDomain"));
        if ( vkResponse.isError() )
            return;
        if ( onJoin ) {
            int lastPostId = UserUtil.getPostId(player.getName().toLowerCase(Locale.ROOT));
            if ( lastPostId == vkResponse.getLastPostId().intValue() )
                return;
            else
                UserUtil.updatePostId(player.getName().toLowerCase(Locale.ROOT), vkResponse.getLastPostId().intValue());
        }

        List<String> pages = ListUtil.getList(vkResponse.getText(), 36, 8);
        ItemStack stack = ItemUtil.createBook("MoonStudio", "nover", pages);

        try { // Редактирование инвентаря
            int slot = player.getInventory().getHeldItemSlot();
            ItemStack old = player.getInventory().getItem(slot);

            player.getInventory().setItem(slot, stack);

            { // Отправление пакета на открытие книги
                PacketContainer pc = ProtocolLibrary.getProtocolManager().
                        createPacket(PacketType.Play.Server.CUSTOM_PAYLOAD);
                pc.getModifier().writeDefaults();
                ByteBuf bf = Unpooled.buffer(256);
                bf.setByte(0, (byte) 0);
                bf.writerIndex(1);
                pc.getModifier().write(1, MinecraftReflection.getPacketDataSerializer(bf));
                pc.getStrings().write(0, "MC|BOpen");
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, pc);
            }

            player.getInventory().setItem(slot, old);
        } catch (Exception ex) {ex.printStackTrace();}
    }
}
