package ru.Nover.TestPlugin.Utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;

public class ItemUtil
{
    private ItemUtil () {}

    public static ItemStack createBook (String title, String author, List<String> pages) {
        ItemStack stack = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) stack.getItemMeta();

        bookMeta.setTitle(title);
        bookMeta.setAuthor(author);
        bookMeta.setPages(pages);

        stack.setItemMeta(bookMeta);
        return stack;
    }
}
